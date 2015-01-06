package com.tr.pvs.core.dbo;

import java.sql.Timestamp;



/**
 * Report entity. @author MyEclipse Persistence Tools
 */

public class Report  implements java.io.Serializable {


    // Fields    

     private Integer reportId;
     private String bbgFile;
     private Integer edmStatus;
     private Integer bbgStatus;
     private Integer reportStatus;
     private Timestamp insertDate;
     private Timestamp updateDate;
     private String edmFile;
     private String reportName;
     private Double diffValue;
     private Double diffPct;
     private Double diffRatio;
     private Integer maxCount;


    // Constructors

    /** default constructor */
    public Report() {
    }

	/** minimal constructor */
    public Report(Integer reportId, String bbgFile, Integer edmStatus, Integer bbgStatus, Integer reportStatus, String edmFile) {
        this.reportId = reportId;
        this.bbgFile = bbgFile;
        this.edmStatus = edmStatus;
        this.bbgStatus = bbgStatus;
        this.reportStatus = reportStatus;
        this.edmFile = edmFile;
    }
    
    /** full constructor */
    public Report(Integer reportId, String bbgFile, Integer edmStatus, Integer bbgStatus, Integer reportStatus, Timestamp insertDate, Timestamp updateDate, String edmFile) {
        this.reportId = reportId;
        this.bbgFile = bbgFile;
        this.edmStatus = edmStatus;
        this.bbgStatus = bbgStatus;
        this.reportStatus = reportStatus;
        this.insertDate = insertDate;
        this.updateDate = updateDate;
        this.edmFile = edmFile;
    }

   
    // Property accessors

    public Integer getReportId() {
        return this.reportId;
    }
    
    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public String getBbgFile() {
        return this.bbgFile;
    }
    
    public void setBbgFile(String bbgFile) {
        this.bbgFile = bbgFile;
    }

    public Integer getEdmStatus() {
        return this.edmStatus;
    }
    
    public void setEdmStatus(Integer edmStatus) {
        this.edmStatus = edmStatus;
    }

    public Integer getBbgStatus() {
        return this.bbgStatus;
    }
    
    public void setBbgStatus(Integer bbgStatus) {
        this.bbgStatus = bbgStatus;
    }

    public Integer getReportStatus() {
        return this.reportStatus;
    }
    
    public void setReportStatus(Integer reportStatus) {
        this.reportStatus = reportStatus;
    }

    public Timestamp getInsertDate() {
        return this.insertDate;
    }
    
    public void setInsertDate(Timestamp insertDate) {
        this.insertDate = insertDate;
    }

    public Timestamp getUpdateDate() {
        return this.updateDate;
    }
    
    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public String getEdmFile() {
        return this.edmFile;
    }
    
    public void setEdmFile(String edmFile) {
        this.edmFile = edmFile;
    }

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
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