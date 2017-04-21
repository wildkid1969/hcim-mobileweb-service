package com.hc360.mobile.webservice.pojo;

import java.util.ArrayList;
import java.util.List;

public class InsertSearchAppResult {
	
	private String name;
	
	private String imgurl; // 图片链接地址
	
	private String linkurl; // 内容链接地址	
	
	private String type; // 1 活动页 2 产品终极页 3 公司首页
	
	private String value; // type =2 时，这个问产品id  type =3 时 这个为公司名称 如 jason139
	
	private List<InsertSearchAppKeyword> l = new ArrayList<InsertSearchAppKeyword>();
	
	private String title; // 活动页时，要填写活动标题

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<InsertSearchAppKeyword> getL() {
		return l;
	}

	public void setL(List<InsertSearchAppKeyword> l) {
		this.l = l;
	}
	
	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getLinkurl() {
		return linkurl;
	}

	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


}
