package com.tr.pvs.core.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.tr.pvs.core.bean.ReportGenerator;

public class GenerateSchedule extends QuartzJobBean{
	private static final Logger log = LoggerFactory.getLogger(GenerateSchedule.class);
	
	private ReportGenerator reportGenerator;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		try {
			reportGenerator.checkGenerator();
		} catch(Exception e) {
			log.error("Error", e);
		}
	}

	public ReportGenerator getReportGenerator() {
		return reportGenerator;
	}

	public void setReportGenerator(ReportGenerator reportGenerator) {
		this.reportGenerator = reportGenerator;
	}
	
	
}
