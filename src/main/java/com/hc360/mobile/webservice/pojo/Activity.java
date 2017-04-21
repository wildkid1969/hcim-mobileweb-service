package com.hc360.mobile.webservice.pojo;

import java.util.ArrayList;
import java.util.List;

public class Activity {

	private String activityName ="";
	
	private int len ;
	
	public void init(){
		this.setActivityName("");
		this.setLen(0);
		this.setLs(new ArrayList<ActivityElem>());
	}
	
	private List<ActivityElem> ls= new ArrayList<ActivityElem>();

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public List<ActivityElem> getLs() {
		return ls;
	}

	public void setLs(List<ActivityElem> ls) {
		this.ls = ls;
	}
}
