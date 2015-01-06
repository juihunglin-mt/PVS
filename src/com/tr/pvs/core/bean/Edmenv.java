package com.tr.pvs.core.bean;

import java.io.File;
import java.util.ResourceBundle;

public class Edmenv {
	private ResourceBundle rbenv;
	private String environment;
	
	public Edmenv() {
		this.rbenv = ResourceBundle.getBundle("edmenv");
		init();
	}
	
	public Edmenv(String path) {
		this.rbenv = ResourceBundle.getBundle(path + "edmenv");
		init();
	}
	
	public Edmenv(String path, String filename) {
		this.rbenv = ResourceBundle.getBundle(path + File.separator + filename);
	}
	
	private void init() {
		try {
			this.environment = rbenv.getString("environment");
		} catch (Exception e) {
			this.environment = null;
		}
	}

	public String getEnvironment() {
		return environment;
	}
}
