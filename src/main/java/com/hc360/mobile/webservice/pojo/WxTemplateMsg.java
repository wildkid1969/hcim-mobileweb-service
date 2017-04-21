package com.hc360.mobile.webservice.pojo;

public class WxTemplateMsg {
	
	private String touser;
	
	private String template_id;
	
	private String url;
	
	private String topcolor;
	
	private WxTemplateMsgData data = new WxTemplateMsgData();

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}

	public WxTemplateMsgData getData() {
		return data;
	}

	public void setData(WxTemplateMsgData data) {
		this.data = data;
	}

}
