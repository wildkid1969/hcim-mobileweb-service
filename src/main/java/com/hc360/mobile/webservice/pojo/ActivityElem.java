package com.hc360.mobile.webservice.pojo;

import com.hc360.rsf.my.message.BusinChance;

public class ActivityElem extends BusinChance{
	

	private static final long serialVersionUID = -575281019025353363L;

	private String activityId;
	
	private String oldprice;
	
	private String imgUrl;
	
	private String ptitle;
	
	private String mainind;

	public String getOldprice() {
		return oldprice;
	}

	public void setOldprice(String oldprice) {
		this.oldprice = oldprice;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getMainind() {
		return mainind;
	}

	public void setMainind(String mainind) {
		this.mainind = mainind;
	}

	public String getPtitle() {
		return ptitle;
	}

	public void setPtitle(String ptitle) {
		this.ptitle = ptitle;
	}
}
