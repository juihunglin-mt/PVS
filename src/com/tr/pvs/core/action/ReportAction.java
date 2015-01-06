package com.tr.pvs.core.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.tr.pvs.core.bean.BBGReader;
import com.tr.pvs.core.bean.EDMReader;
import com.tr.pvs.core.bean.Edmenv;
import com.tr.pvs.core.bean.ReportGenerator;
import com.tr.pvs.core.bean.TimeUtil;
import com.tr.pvs.core.dao.BBGPriceDAO;
import com.tr.pvs.core.dao.EDMPriceDAO;
import com.tr.pvs.core.dao.ReportDAO;
import com.tr.pvs.core.dbo.Report;

public class ReportAction extends ActionSupport {
	private static final Logger log = Logger.getLogger(ReportAction.class);
	private ReportDAO reportDAO;
	private BBGPriceDAO bbgPriceDAO;
	private EDMPriceDAO edmPriceDAO;
	
	private BBGReader bbgReader;
	private EDMReader edmReader;
	private ReportGenerator reportGenerator;
	
	private Integer reportId;
	private Integer triggerBBG;
	private Integer triggerEDM;
	private Integer triggerReport;
	private Double diffValue;
	private Double diffPct;
	private Double diffRatio;
	private Integer maxCount;
	
	private Long contentLength;
	private InputStream downloadFile;
	
	//upload file
	private File bbgFile;
	private String bbgContentType;
	private String bbgFileName;
	private File edmFile;
	private String edmContentType;
	private String edmFileName;
	
	private String fileName;
	
	public String allRecord() {
		
		List<Report> reportList = reportDAO.findAllByInsertDateDesc();
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		request.setAttribute("reportList", reportList);
		
		return SUCCESS;
	}

	public String getRecord() {
		
		Report report = reportDAO.findById(reportId);
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		request.setAttribute("report", report);
		
		return SUCCESS;
	}
	
	public String saveRecord() {
		TimeUtil tu = new TimeUtil();
		Edmenv edmenv = new Edmenv();
		
		ResourceBundle rb = ResourceBundle.getBundle("pvs_" + edmenv.getEnvironment());
		String bbg_path = rb.getString("bbg_path");
		String edm_path = rb.getString("edm_path");
		
		String time = tu.getDateFormat(new Date(), "_yyyyMMddHHmmss");
		
		String uploadBBG = bbgFileName.substring(0, bbgFileName.lastIndexOf(".")) + time + bbgFileName.substring(bbgFileName.lastIndexOf("."), bbgFileName.length());
		String uploadEDM = edmFileName.substring(0, edmFileName.lastIndexOf(".")) + time + edmFileName.substring(edmFileName.lastIndexOf("."), edmFileName.length());
		
		
		File file = new File(bbg_path, uploadBBG);
		File file2 = new File(edm_path, uploadEDM);
		try {
			FileUtils.copyFile(bbgFile, file);
			FileUtils.copyFile(edmFile, file2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("!!! Failed", e);
			return INPUT;
		}
		
		Report report = new Report();
		report.setBbgFile(uploadBBG);
		report.setEdmFile(uploadEDM);
		report.setBbgStatus(0);
		report.setEdmStatus(0);
		report.setReportStatus(0);
		if(diffValue == null) diffValue = 0.01;
		report.setDiffValue(diffValue);
		if(diffPct == null) diffPct = 1.0;
		report.setDiffPct(diffPct);
		if(diffRatio == null) diffRatio = 0.01;
		report.setDiffRatio(diffRatio);
		if(maxCount == null) maxCount = 0;
		report.setMaxCount(maxCount);
		
		report.setReportName("report_" + uploadBBG);
		
		report.setInsertDate(new Timestamp(System.currentTimeMillis()));
		report.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		
		reportDAO.save(report);
		
		return SUCCESS;
	}
	
	public String deleteRecord() {
		Edmenv edmenv = new Edmenv();
		
		try {
			ResourceBundle rb = ResourceBundle.getBundle("pvs_" + edmenv.getEnvironment());
			String bbg_path = rb.getString("bbg_path");
			String edm_path = rb.getString("edm_path");
			String report_path = rb.getString("report_path");
			
			Report report = reportDAO.findById(reportId);
			
			File file1 = new File(bbg_path + File.separator + report.getBbgFile());
			if(file1 != null) file1.delete();
			
			File file2 = new File(edm_path + File.separator + report.getEdmFile());
			if(file2 != null) file2.delete();
			
			File file3 = new File(report_path + File.separator + report.getReportName());
			if(file3 != null) file3.delete();
			
			reportDAO.delete(report);
		
		} catch(Exception e) {
			log.error("!!! Failed", e);
		}
		
		return SUCCESS;
	}
	
	public String callWSGenerator() {
		Report report = reportDAO.findById(reportId);
		
		report.setReportStatus(1);
		reportDAO.attachDirty(report);
		
		if(triggerBBG == null || triggerBBG != 0) {
			try {
				bbgReader.startBBGReader(report.getBbgFile());
				report.setBbgStatus(1);
				reportDAO.attachDirty(report);
			} catch(Exception e) {
				report.setBbgStatus(2);
				reportDAO.attachDirty(report);
				log.error("Upload Failed",e);
			}
		}
		
		if(triggerEDM == null || triggerEDM != 0) {
			try {
				edmReader.startEDMReader(report.getEdmFile());
				report.setEdmStatus(1);
				reportDAO.attachDirty(report);
			} catch(Exception e) {
				report.setEdmStatus(2);
				reportDAO.attachDirty(report);
				log.error("Upload Failed",e);
			}
		}
		
		if(triggerReport == null || triggerReport != 0) {
			try {
				reportGenerator.startGenerate(report.getReportId());
				report.setReportStatus(2);
				reportDAO.attachDirty(report);
			} catch(Exception e) {
				report.setReportStatus(4);
				reportDAO.attachDirty(report);
				log.error("Generate Failed", e);
			}
		}
		return SUCCESS;
	}
	
	public String downloadOutputFile() {
		try {
			Edmenv edmenv = new Edmenv();
			ResourceBundle rb = ResourceBundle.getBundle("pvs_" + edmenv.getEnvironment());
			
			String dss_local_download_path = rb.getString("report_path");
			
			Report report = reportDAO.findById(reportId);
			
			fileName = report.getReportName();
			
			File fileToDownload = new File(dss_local_download_path + report.getReportName());
			downloadFile = new FileInputStream(fileToDownload);
//			downloadFile = new FileInputStream(dss_local_download_path + fileName);
	        contentLength = fileToDownload.length();
	        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public ReportDAO getReportDAO() {
		return reportDAO;
	}

	public void setReportDAO(ReportDAO reportDAO) {
		this.reportDAO = reportDAO;
	}

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public File getBbgFile() {
		return bbgFile;
	}

	public void setBbgFile(File bbgFile) {
		this.bbgFile = bbgFile;
	}

	public String getBbgContentType() {
		return bbgContentType;
	}

	public void setBbgFileContentType(String bbgContentType) {
		this.bbgContentType = bbgContentType;
	}

	public String getBbgFileName() {
		return bbgFileName;
	}

	public void setBbgFileFileName(String bbgFileName) {
		this.bbgFileName = bbgFileName;
	}

	public File getEdmFile() {
		return edmFile;
	}

	public void setEdmFile(File edmFile) {
		this.edmFile = edmFile;
	}

	public String getEdmContentType() {
		return edmContentType;
	}

	public void setEdmFileContentType(String edmContentType) {
		this.edmContentType = edmContentType;
	}

	public String getEdmFileName() {
		return edmFileName;
	}

	public void setEdmFileFileName(String edmFileName) {
		this.edmFileName = edmFileName;
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

	public void setReportGenerator(ReportGenerator reportGenerator) {
		this.reportGenerator = reportGenerator;
	}

	public void setBbgPriceDAO(BBGPriceDAO bbgPriceDAO) {
		this.bbgPriceDAO = bbgPriceDAO;
	}

	public void setEdmPriceDAO(EDMPriceDAO edmPriceDAO) {
		this.edmPriceDAO = edmPriceDAO;
	}

	public Long getContentLength() {
		return contentLength;
	}

	public void setContentLength(Long contentLength) {
		this.contentLength = contentLength;
	}

	public InputStream getDownloadFile() {
		return downloadFile;
	}

	public void setDownloadFile(InputStream downloadFile) {
		this.downloadFile = downloadFile;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getTriggerBBG() {
		return triggerBBG;
	}

	public void setTriggerBBG(Integer triggerBBG) {
		this.triggerBBG = triggerBBG;
	}

	public Integer getTriggerEDM() {
		return triggerEDM;
	}

	public void setTriggerEDM(Integer triggerEDM) {
		this.triggerEDM = triggerEDM;
	}

	public Integer getTriggerReport() {
		return triggerReport;
	}

	public void setTriggerReport(Integer triggerReport) {
		this.triggerReport = triggerReport;
	}

	public Double getDiffValue() {
		return diffValue;
	}

	public void setDiffValue(Double diffValue) {
		this.diffValue = diffValue;
	}

	public Double getDiffPct() {
		return diffPct;
	}

	public void setDiffPct(Double diffPct) {
		this.diffPct = diffPct;
	}

	public Double getDiffRatio() {
		return diffRatio;
	}

	public void setDiffRatio(Double diffRatio) {
		this.diffRatio = diffRatio;
	}

	public Integer getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}

	
	
}
