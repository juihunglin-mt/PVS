package com.tr.pvs.core.bean;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class Sqlloader {
	private static final Logger log = Logger.getLogger(Sqlloader.class);
	
	public void createDataFile(String path, String fileName, boolean appendType, List<String> dataLines){
		log.info("Save Sqlldr Data File: " + fileName + " Size: " + dataLines.size());
		
		checkPath(path);
		
		try {
			FileWriter fwriter = new FileWriter(path + File.separator + fileName + ".data", appendType);
			if(dataLines != null) {
				for(int i = 0; i < dataLines.size(); i++) {
					String dataLine = dataLines.get(i);
					dataLine = dataLine.replaceAll("null", "");
					fwriter.append(dataLine + "\n");
				}
			}
			fwriter.close();
	    } catch(Exception e)  {
	    	log.error("Create sqlldr data file Error", e);
	    }
	}
	
	public void createCtrlFile(String path, String fileName, String tableName, String tableFormat) {
		checkPath(path);
		//Example: SCENARIO_SHOCK_ID \"SCENARIO_SHOCK_SEQ.NEXTVAL\",SCENARIO_SHOCK_TYPE_ID,NAME,PROB,COLOR,INSTRUMENT_ID,START_TIME,TYPE,VALUE,MONEYNESS,OPTION_TERM,UNDERLYING_TERM,TERM,OPTION_TIME,INSERT_DATE \"SYSDATE\"
		log.info("Save Sqlldr ctrl file");
		try {
			FileWriter fwriter = new FileWriter(path + File.separator + fileName + ".ctl");
			String ctrlLine = "load data\n" +
					"infile '" + path + File.separator + fileName + ".data'\n" +
					"APPEND\n" +
					"into table "+tableName+"\n" +
					"FIELDS terminated by ','\n" +
					"(" + tableFormat +
					")\n";
			fwriter.append(ctrlLine);
			fwriter.close();
	    } catch(Exception e)  {
	    	log.error("Create Sqlldr ctrl file Error!", e);
	    }
	}
	
	public void createCmdOrShFile(String osSystem, String path, String fileName) {
		Edmenv edmenv = new Edmenv();
		ResourceBundle rb = ResourceBundle.getBundle("config");
		ResourceBundle env = ResourceBundle.getBundle("pvs_" + edmenv.getEnvironment());
		String osCommandLine = env.getString("commandline");
		String osType = "sh";
		if(osSystem.equals("windows")) {
			osType = "bat";
		}
		
		checkPath(path);
		
		String sqlldrContext = rb.getString("sqlldr.context");
		log.info("Save Sqlldr "+osType+" file");
		String commandLine = "sqlldr userid="+sqlldrContext+" control=" + path + File.separator + fileName + ".ctl log=" + path + File.separator + fileName + ".log DIRECT=FALSE";
		
		try {
			FileWriter fwriter = new FileWriter(path + File.separator + fileName + "." + osType);
			/*if(!osSystem.equals("windows")){
				fwriter.append("source ~/.bash_profile");
				fwriter.append("\n");
			}*/
			fwriter.append(osCommandLine + commandLine);
			fwriter.close();
	    } catch(Exception e)  {
	    	log.error("Create Sqlldr "+osType+" file Error!", e);
	    }
	}
	
	public String getCommandLine(String osSystem, String path, String fileName) {
		String osCommandLine = "";
		String osType = "sh";
		if(osSystem.equals("windows")) {
			osType = "bat";
			osCommandLine = "cmd /c start/wait ";
		} else {
			osCommandLine = "/u01/app/oracle/product/11.2.0/xe/bin/";
		}
		
		ResourceBundle rb = ResourceBundle.getBundle("config");
		
		checkPath(path);
		
		String sqlldrContext = rb.getString("sqlldr.context");
		log.info("Save Sqlldr "+osType+" file");
		String commandLine = "sqlldr userid="+sqlldrContext+" control=" + path + File.separator + fileName + ".ctl log=" + path + File.separator + fileName + ".log";
		
		return osCommandLine + commandLine;
	}
	
	public void checkPath(String path) {
		File myFilePath = new File(path);
		if (!myFilePath.isDirectory()) {
			myFilePath.mkdirs();
		}
	}
}
