package com.tr.pvs.core.bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.tr.pvs.core.dao.EDMPriceDAO;
import com.tr.pvs.core.dao.InstrumentDAO;
import com.tr.pvs.core.dbo.Instrument;

public class EDMReader implements Callable<Boolean> {
	private static final Logger log = Logger.getLogger(EDMReader.class);
	
	private InstrumentDAO instrumentDAO;
	private EDMPriceDAO edmPriceDAO;
	
	private String fileName;
	
	public void startEDMReader(String fileName) throws Exception {
		log.info(":::: Start EDMReader ");
		
		Edmenv edmenv = new Edmenv();
		TimeUtil tu = new TimeUtil();
		Sqlloader sqlldr = new Sqlloader();
		
		ResourceBundle rb = ResourceBundle.getBundle("pvs_" + edmenv.getEnvironment());
		String edm_file_path = rb.getString("edm_path");
		
		
		String sqlldrPath = rb.getString("sqlldr_upload_path");
		String osSystem = rb.getString("os_system");
	
		boolean appendType = false;
		
		//FileInputStream file = null;
		
		try {
			//file = new FileInputStream(new File(edm_file_path + File.separator + fileName));
			//HSSFWorkbook workbook = new HSSFWorkbook(file);
			Workbook workbook = WorkbookFactory.create(new File(edm_file_path + File.separator + fileName));
			String field = "MID";
			
			String check = fileName.substring(0, 3).toUpperCase();
			if("BID".equals(check)) {
				field = "BID";
			} else if("ASK".equals(check)) {
				field = "ASK";
			}
			
			edmPriceDAO.truncate();
			
			String ldrname = "EDM_" + field + tu.getDateFormat(new Date(), "_yyyyMMddHHmmss");
			
			Integer totalSheet = workbook.getNumberOfSheets();
			
			List<String> dataLines = new ArrayList<String>();
			
			for(int s = 0; s < totalSheet; s++) {
				Sheet sheet = workbook.getSheetAt(s);
	        	log.info("Read Sheet: " + s + " Sheet name: " + sheet.getSheetName());
	    		Row firstRow = sheet.getRow(0);
				Integer colCount = firstRow.getLastCellNum() + 1;
				Integer rowCount = sheet.getLastRowNum() + 1;
				
				for (int i = 0; i < colCount; i += 3) {
					String ricName = null;
					String currency = null;
					try {
						Cell headerCell = sheet.getRow(0).getCell(i);
						if(headerCell != null) {
							headerCell.setCellType(Cell.CELL_TYPE_STRING);
							ricName = headerCell.getStringCellValue();
						}
					} catch(Exception e) {
						log.error("debug", e);
					}
					
					try {
						Cell curCell = sheet.getRow(1).getCell(i);
						if(curCell != null) {
							curCell.setCellType(Cell.CELL_TYPE_STRING);
							currency = curCell.getStringCellValue();
						}
					} catch(Exception e) {
						log.error("debug", e);
					}
					
					log.info("Read Instrument: " + ricName + " corCount : " + colCount + " rowCount: " + rowCount);
					int dataSize = 0;
					if(ricName != null) {
						Instrument inst = instrumentDAO.findByIdentifierOne(ricName);
						
						if(inst == null) inst = createInstrument(ricName, currency);
						
						for (int j = 2; j < rowCount; j++) {
							Row row = sheet.getRow(j);
							if (row.getCell(i) != null && !row.getCell(i).equals("")) {
								dataSize++;
								Cell tradeCell = row.getCell(i);
								
								Date tradeDate = null;
								
								if(tradeCell.getCellType() == Cell.CELL_TYPE_STRING) {
									String tradeStr = tradeCell.getStringCellValue();
									tradeDate = tu.getDate2(tradeStr, "yyyy/MM/dd");
								} else {
									tradeDate = tradeCell.getDateCellValue();
								}
								
								if(tradeDate != null) {
									String dataline = "," + 
											tu.getDateFormat(tradeDate, "yyyy/MM/dd HH:mm:ss") + "," +
											row.getCell(i+1) + "," +
											field + "," +
											inst.getInstrumentId() + ",";
									
									dataLines.add(dataline);
								}
							}
						}
						if(dataLines.size() > 50000) {
							sqlldr.createDataFile(sqlldrPath, ldrname, appendType, dataLines);
							if(!appendType) {
								appendType = true;
							}
							dataLines.clear();
						}
					}
					log.info("Read Complete Instrument: " + ricName + " Total Size: " + dataSize );
				}
				if(dataLines.size() > 0) {
					sqlldr.createDataFile(sqlldrPath, ldrname, appendType, dataLines);
					if(!appendType) {
						appendType = true;
					}
					dataLines.clear();
				}
			}
			//file.close();
			
			String tableFormat = "EDM_ID \"EDM_SEQ.NEXTVAL\",TRADE_DATE DATE \"yyyy/MM/dd HH24:MI:SS\",VALUE,FIELD,INSTRUMENT_ID";
			
			sqlldr.createCtrlFile(sqlldrPath, ldrname, "PVS_EDM", tableFormat);
			
			sqlldr.createCmdOrShFile(osSystem, sqlldrPath, ldrname);
			
			log.info("trigger sqlldr");
			//String commandLine = sqlldr.getCommandLine(osSystem, sqlldrPath, fileName);
			
			String osType = "sh";
			if(osSystem.equals("windows")) {
				osType = "bat";
			} else {
				sqlldrPath = "/bin/sh "+sqlldrPath;
			}
			
			Process p = null;
			try {
//				String commandLine = sqlldr.getCommandLine(osSystem, sqlldrPath, fileName);
				p = Runtime.getRuntime().exec(sqlldrPath+ldrname+"."+osType);
				
				BufferedReader stdInput = new BufferedReader(new 
				InputStreamReader(p.getInputStream()));

				BufferedReader stdError = new BufferedReader(new 
				InputStreamReader(p.getErrorStream()));

				// read the output from the command
				log.info("SQLLoader standard output :");
				String s = null;
				while ((s = stdInput.readLine()) != null) {
//					log.info(s);
				}

				// read any errors from the attempted command
				log.info("SQLLoader error output (if any):");
				while ((s = stdError.readLine()) != null) {
					log.info(s);
				}
				
				p.waitFor();
			} catch (Exception e) {
				log.info("sqlldr runtime error!", e);
			}
			
			log.info("delete ctl file");
			
			try {
				File delfile = new File(sqlldrPath + ldrname + "." + osType);
				delfile.delete();
			} catch(Exception e) {
				log.error("delete failed", e);
			}
			
			try {
				File delfile = new File(sqlldrPath + ldrname + ".data");
				delfile.delete();
			} catch(Exception e) {
				log.error("delete failed", e);
			}
			
			try {
				File delfile = new File(sqlldrPath + ldrname + ".ctl");
				delfile.delete();
			} catch(Exception e) {
				log.error("delete failed", e);
			}
			
			try {
				File delfile = new File(sqlldrPath + ldrname + ".bad");
				delfile.delete();
			} catch(Exception e) {
				log.error("delete failed", e);
			}
			
		} catch(Exception e) {
			log.error("!!! Failed", e);
			throw e;
		} 
		
		log.info(":::: End EDMReader ");
	}
	
	private Instrument createInstrument(String ricName, String currency) {
		Instrument inst = new Instrument();
		inst.setIdentifier(ricName);
		inst.setCurrency(currency);
		
		instrumentDAO.save(inst);
		
		return inst;
	}

	public void setInstrumentDAO(InstrumentDAO instrumentDAO) {
		this.instrumentDAO = instrumentDAO;
	}

	public void setEdmPriceDAO(EDMPriceDAO edmPriceDAO) {
		this.edmPriceDAO = edmPriceDAO;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public static void main(String[] args) {
		EDMReader reader = new EDMReader();
		
		try {
			reader.startEDMReader("stockall_20141204_20141205201356.xls");
		} catch(Exception e) {
			
		}
		
	}

	@Override
	public Boolean call() throws Exception {
		// TODO Auto-generated method stub
		try {
			startEDMReader(fileName);
		} catch(Exception e) {
			log.error("!!! Failed", e);
		}
		return true;
	}
}
