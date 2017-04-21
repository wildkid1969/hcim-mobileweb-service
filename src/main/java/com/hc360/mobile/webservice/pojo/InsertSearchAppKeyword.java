package com.hc360.mobile.webservice.pojo;

public class InsertSearchAppKeyword {
	
	private String keyword; // 关键词
	
	private String type; // 1 搜索词  2 价格区间
	
	private String minprice; // 区间价最小值
	
	private String maxprice; // 区间价最大值

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMinprice() {
		return minprice;
	}

	public void setMinprice(String minprice) {
		this.minprice = minprice;
	}

	public String getMaxprice() {
		return maxprice;
	}

	public void setMaxprice(String maxprice) {
		this.maxprice = maxprice;
	}
	

}
