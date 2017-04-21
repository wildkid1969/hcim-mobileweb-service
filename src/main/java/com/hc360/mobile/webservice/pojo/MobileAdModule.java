package com.hc360.mobile.webservice.pojo;

import java.util.ArrayList;
import java.util.List;

public class MobileAdModule {
	
	private String modulename;
	
	private String level;
	
	private List<MobileAdConfigData> ls = new ArrayList<MobileAdConfigData>();

	public String getModulename() {
		return modulename;
	}

	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	public List<MobileAdConfigData> getLs() {
		return ls;
	}

	public void setLs(List<MobileAdConfigData> ls) {
		this.ls = ls;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}
