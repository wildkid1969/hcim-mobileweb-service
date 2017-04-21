package com.hc360.mobile.webservice.pojo;

import java.util.Date;

public class Ad114Config {
	
	private int id; //  序号
	
	private int type; //
	
	private int kind; //  类型
	
	private String title; // 显示标题
	
	private String url; // 页面地址 
	
	private Date datetime; // 创建时间
	
	private int enable = 0; // 1 可用 0 不可用

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}
	

}
