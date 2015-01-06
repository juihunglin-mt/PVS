package com.tr.pvs.core.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import com.tr.pvs.core.dao.BBGPriceDAO;
import com.tr.pvs.core.dao.EDMPriceDAO;
import com.tr.pvs.core.dao.InstrumentDAO;
import com.tr.pvs.core.dao.ReportDAO;
import com.tr.pvs.core.dbo.BBGPrice;
import com.tr.pvs.core.dbo.EDMPrice;
import com.tr.pvs.core.dbo.Instrument;
import com.tr.pvs.core.dbo.Report;

public class ReportGenerator {
	private static final Logger log = Logger.getLogger(ReportGenerator.class);
	
	private InstrumentDAO instrumentDAO;
	private ReportDAO reportDAO;
	private BBGPriceDAO bbgPriceDAO;
	private EDMPriceDAO edmPriceDAO;
	private BBGReader bbgReader;
	private EDMReader edmReader;
	
	public void checkGenerator() {
		List<Report> reports = reportDAO.findByReportStatus(0);
		
		if(reports.size() > 0) {
			List<Report> checkReports = reportDAO.findByReportStatus(1);
			if(checkReports.size() == 0) {
				Report report = reports.get(0);
				startUploadAndGenerate(report);
			}
		}
	}
	
	private void startUploadAndGenerate(Report report) {
		report.setReportStatus(1);
		reportDAO.attachDirty(report);
		
		try {
			if(report.getBbgFile() != null) {
				bbgReader.startBBGReader(report.getBbgFile());
			}
			report.setBbgStatus(1);
			reportDAO.attachDirty(report);
		} catch(Exception e) {
			report.setBbgStatus(2);
			reportDAO.attachDirty(report);
			log.error("Upload Failed",e);
		}
		
		try {
			if(report.getEdmFile() != null) {
				edmReader.startEDMReader(report.getEdmFile());
			}
			report.setEdmStatus(1);
			reportDAO.attachDirty(report);
		} catch(Exception e) {
			report.setEdmStatus(2);
			reportDAO.attachDirty(report);
			log.error("Upload Failed",e);
		}
		
		try {
			startGenerate(report.getReportId());
			report.setReportStatus(2);
			reportDAO.attachDirty(report);
		} catch(Exception e) {
			report.setReportStatus(4);
			reportDAO.attachDirty(report);
			log.error("Generate Failed", e);
		}
	}
	
	public void startGenerate(Integer id) throws Exception {
		log.info(":::: Start Generate report file");
		Report report = reportDAO.findById(id);
		
		Double diffValue = report.getDiffValue();
		Double diffPct = report.getDiffPct();
		Double diffRatio = report.getDiffRatio();
		Integer maxCount = report.getMaxCount();
		
		Map<String, String> diffMap = new LinkedHashMap<String, String>();
		Map<String, String> diffRatioMap = new LinkedHashMap<String, String>();
		
		TimeUtil tu = new TimeUtil();
		
		String field = "MID";
		
		String fileName = report.getBbgFile();
		
		String check = fileName.substring(0, 3).toUpperCase();
		if("BID".equals(check)) {
			field = "BID";
		} else if("ASK".equals(check)) {
			field = "ASK";
		}
		
		int totalSheet = 0;
		int maxIdentifier = 256 / 8;
		int totalIdentifier = 0;
		int rowNum = 0;
		List<Instrument> instList = instrumentDAO.findAll();
		
		Map<String, Instrument> instMap = new HashMap<String, Instrument>();
		
		for(int i = 0; i < instList.size(); i++) {
			instMap.put(instList.get(i).getIdentifier(), instList.get(i));
		}
		
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			
			HSSFSheet sheet = workbook.createSheet("report_" + totalSheet);
			Row headerRow = sheet.createRow(0);
			Row currRow = sheet.createRow(1);
			
			List<Integer> instIds = new ArrayList<Integer>();
			for(int i = 0; i < instList.size(); i++) {
				instIds.add(instList.get(i).getInstrumentId());
				
				boolean run = instIds.size() % 100 == 0;
				if(i == instList.size() - 1) run = true;
				
				if(run) {
					Map<String, Map<Date, Double[]>> reportMap = new HashMap<String, Map<Date, Double[]>>();
					
					List<BBGPrice> bbgList = bbgPriceDAO.findByInstrumentAndField(instIds, field);
					
					Map<String, Map<Timestamp, BBGPrice>> bbgMap = new HashMap<String, Map<Timestamp, BBGPrice>>();
					
					for(int x = 0; x < bbgList.size(); x++) {
						String identifier = bbgList.get(x).getInstrument().getIdentifier();
						BBGPrice price = bbgList.get(x);
						
						Timestamp tmpTradeDate = price.getTradeDate();
						
						Map<Timestamp, BBGPrice> dateMap = bbgMap.get(identifier);
						if(dateMap == null) dateMap = new LinkedHashMap<Timestamp, BBGPrice>();
						dateMap.put(tmpTradeDate, price);
						bbgMap.put(identifier, dateMap);
					}
					
					List<EDMPrice> edmList = edmPriceDAO.findByInstrumentAndField(instIds, field);
					
					String topIdentifier = "";
					int checkCount = 0;
					
					for(int x = 0; x < edmList.size(); x++) {
						String identifier = edmList.get(x).getInstrument().getIdentifier();
						if(!identifier.equals(topIdentifier)) {
							checkCount = 0;
						}
						topIdentifier = identifier;
						
						if(maxCount == 0 || checkCount < maxCount) {
							EDMPrice price = edmList.get(x);
							
							Map<Date, Double[]> dataMap = reportMap.get(identifier);
							if(dataMap == null) {
								log.info("New Data Map: " + identifier);
								dataMap = new TreeMap(Collections.reverseOrder());
							}
							Double[] values = dataMap.get(price.getTradeDate());
							
							if(values == null) values = new Double[7];
												
							Double edmValue = null;
							Double edmRatio = null;
							Timestamp tmpTradeDate = null;
							if(price.getValue() != null) edmValue = Double.valueOf(price.getValue());
							
							int key = x + 1;
							
							/*if(edmValue != null && key < edmList.size()) {
								boolean checkValue = true;
								while(checkValue) {
									EDMPrice price2 = edmList.get(key);
									String identifier2 = price2.getInstrument().getIdentifier();
									if(identifier.equals(identifier2) && price2.getValue() != null) {
										Double edmValue2 = Double.valueOf(price2.getValue());
										edmRatio = (edmValue - edmValue2) / edmValue2;
										tmpTradeDate = price2.getTradeDate();
										checkValue = false;
									} else if(!identifier.equals(identifier2) && price2.getValue() != null){
										checkValue = false;
									} else {
										key++;
									}
								}
							}*/
							
							values[0] = edmValue;
							values[4] = edmRatio;
							
							Map<Timestamp, BBGPrice> dateMap = bbgMap.get(identifier);
							if(dateMap != null) {
								BBGPrice bbgPrice = dateMap.get(price.getTradeDate());
								
								Double bbgValue = null;
								Double bbgRatio = null;
								if(bbgPrice != null && bbgPrice.getValue() != null) {
									bbgValue = Double.valueOf(bbgPrice.getValue());
									values[1] = bbgValue;
									
									/*BBGPrice bbgPrice2 = dateMap.get(tmpTradeDate);
									
									if(bbgPrice2 != null && bbgPrice2.getValue() != null) {
										Double bbgValue2 = Double.valueOf(bbgPrice2.getValue());
										bbgRatio = (bbgValue - bbgValue2) / bbgValue2;
										values[5] = bbgRatio;
									}*/
									if(edmValue != null && bbgValue != null) {
										Double sum = edmValue - bbgValue;
										Double percent = Math.abs(sum) / bbgValue * 100;
										if(percent >= diffPct && Math.abs(sum) >= diffValue) {
											values[2] = sum;
											values[3] = percent;
											
											String detail = diffMap.get(identifier);
											
											if(detail == null) {
												String strTrade= tu.getDateFormat(price.getTradeDate(), "yyyy/MM/dd");
												diffMap.put(identifier, strTrade+",1");
											} else {
												String[] details = detail.split(",");
												String strTrade = details[0];
												String count = details[1];
												Integer countNum = Integer.valueOf(count);
												diffMap.put(identifier, strTrade+","+(countNum+1));
											}
										}
									} 
									/*if(edmRatio != null && bbgRatio != null) {
										Double ratio = ((Math.abs(edmRatio) - Math.abs(bbgRatio)) / Math.abs(bbgRatio)) * 100;
										
										if(Math.abs(ratio) >= diffRatio) {	
											values[6] = ratio;
											
											String detail = diffRatioMap.get(identifier);
											
											if(detail == null) {
												String strTrade = tu.getDateFormat(price.getTradeDate(), "yyyy/MM/dd");
												diffRatioMap.put(identifier, strTrade+",1");
											} else {
												String[] details = detail.split(",");
												String strTrade = details[0];
												String count = details[1];
												Integer countNum = Integer.valueOf(count);
												diffRatioMap.put(identifier, strTrade+","+(countNum+1));
											}
										}
									}*/
								}
							}
							
							dataMap.put(price.getTradeDate(), values);
							reportMap.put(identifier, dataMap);
							checkCount++;
						}
					}
					
					
					for(String identifier: reportMap.keySet()) {
						Instrument inst = instMap.get(identifier);
						
						totalIdentifier += 1;
						if(totalIdentifier % maxIdentifier == 0) {
							totalSheet++;
							sheet = workbook.createSheet("report_" + totalSheet);
							log.info("create new sheet : report_" + totalSheet);
							headerRow = sheet.createRow(0);
							currRow = sheet.createRow(1);
							rowNum = 0;
							totalIdentifier = 0;
						}
						
						headerRow.createCell(rowNum).setCellValue(identifier);
						
						if(diffMap.get(identifier) != null) {
							headerRow.createCell(rowNum+1).setCellValue("Warning");
							String detail = diffMap.get(identifier);
							diffMap.put(identifier, detail+",report_"+totalSheet);
						}
						if(diffRatioMap.get(identifier) != null) {
							headerRow.createCell(rowNum+1).setCellValue("Warning");
							String detail = diffRatioMap.get(identifier);
							diffRatioMap.put(identifier, detail+",report_"+totalSheet);
						}
						
						currRow.createCell(rowNum).setCellValue(inst.getCurrency());
						currRow.createCell(rowNum+1).setCellValue("EDM");
						currRow.createCell(rowNum+2).setCellValue("BBG");
						currRow.createCell(rowNum+3).setCellValue("Diff");
						currRow.createCell(rowNum+4).setCellValue("Diff(%)");
						currRow.createCell(rowNum+5).setCellValue("EDM Ratio");
						currRow.createCell(rowNum+6).setCellValue("BBG Ratio");
						currRow.createCell(rowNum+7).setCellValue("Diff Ratio");
						
						Map<Date, Double[]> dataMap = reportMap.get(identifier);
						log.info("create : " + identifier + " Data size: " + dataMap.size());
						Integer idx = 2;
						for(Date tradeDate : dataMap.keySet()) {
							Row valueRow = sheet.getRow(idx);
							if(valueRow == null) valueRow = sheet.createRow(idx);
							String tradeStr = tu.getDateFormat(tradeDate, "yyyy/MM/dd");
							
							Double[] values = dataMap.get(tradeDate);
							valueRow.createCell(rowNum).setCellValue(tradeStr);
							
							for(int c = 0; c < values.length; c++) {
								int key = c + 1;
								if(values[c] != null) valueRow.createCell(rowNum + key).setCellValue(values[c]);
								
							}
							
							idx += 1;
						}
						rowNum += 8;
					}
					instIds.clear();
				}
			}
			
			sheet = workbook.createSheet("warning_list");
			Row hRow = sheet.createRow(0);
			hRow.createCell(0).setCellValue("Risk Factor");
			hRow.createCell(1).setCellValue("Sheet Name");
			hRow.createCell(2).setCellValue("Trade Date");
			hRow.createCell(3).setCellValue("Total Diff");
			
			int row = 1;
			for(String identifier: diffMap.keySet()) {
				Row vRow = sheet.getRow(row);
				if(vRow == null) vRow = sheet.createRow(row);
				
				String details = diffMap.get(identifier);
				if(details != null) {
					vRow.createCell(0).setCellValue(identifier);
					
					String[] values = details.split(","); 
					vRow.createCell(1).setCellValue(values[2]);
					vRow.createCell(2).setCellValue(values[0]);
					vRow.createCell(3).setCellValue(values[1]);
					row++;
				}	
			}
			
			sheet = workbook.createSheet("ratio_list");
			hRow = sheet.createRow(0);
			hRow.createCell(0).setCellValue("Risk Factor");
			hRow.createCell(1).setCellValue("Sheet Name");
			hRow.createCell(2).setCellValue("Trade Date");
			hRow.createCell(3).setCellValue("Diff Ratio");
			
			row = 1;
			for(String identifier: diffRatioMap.keySet()) {
				Row vRow = sheet.getRow(row);
				if(vRow == null) vRow = sheet.createRow(row);
				
				String details = diffRatioMap.get(identifier);
				if(details != null) {
					vRow.createCell(0).setCellValue(identifier);
					
					String[] values = details.split(","); 
					vRow.createCell(1).setCellValue(values[2]);
					vRow.createCell(2).setCellValue(values[0]);
					vRow.createCell(3).setCellValue(values[1]);
					row++;
				}	
			}
			
			Edmenv edmenv = new Edmenv();
			ResourceBundle rb = ResourceBundle.getBundle("pvs_"	+ edmenv.getEnvironment());
			String output_path = rb.getString("report_path");
			checkFolder(output_path);
			String filename = report.getReportName();
			File tempFile = new File(output_path+filename);
			FileOutputStream fileOut = new FileOutputStream(tempFile);
			//log.info("Writing workbook... " + rd.getFilename());
			workbook.write(fileOut);
			//log.info("Closing output stream... " + rd.getFilename());
			fileOut.close();
		} catch(Exception e) {
			throw e;
		}
		
		log.info(":::: End Generate report file");
	}
	
	public void checkFolder(String output_path){
		File outputPath = new File(output_path);
		if(!outputPath.exists()){
			outputPath.mkdirs();
		}
	}

	public InstrumentDAO getInstrumentDAO() {
		return instrumentDAO;
	}

	public void setInstrumentDAO(InstrumentDAO instrumentDAO) {
		this.instrumentDAO = instrumentDAO;
	}

	public ReportDAO getReportDAO() {
		return reportDAO;
	}

	public void setReportDAO(ReportDAO reportDAO) {
		this.reportDAO = reportDAO;
	}

	public BBGPriceDAO getBbgPriceDAO() {
		return bbgPriceDAO;
	}

	public void setBbgPriceDAO(BBGPriceDAO bbgPriceDAO) {
		this.bbgPriceDAO = bbgPriceDAO;
	}

	public EDMPriceDAO getEdmPriceDAO() {
		return edmPriceDAO;
	}

	public void setEdmPriceDAO(EDMPriceDAO edmPriceDAO) {
		this.edmPriceDAO = edmPriceDAO;
	}

	public BBGReader getBbgReader() {
		return bbgReader;
	}

	public void setBbgReader(BBGReader bbgReader) {
		this.bbgReader = bbgReader;
	}

	public EDMReader getEdmReader() {
		return edmReader;
	}

	public void setEdmReader(EDMReader edmReader) {
		this.edmReader = edmReader;
	}
	
	
}
