package com.hc360.mobile.webservice.pojo;




public class MobileWxMenuStat  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	private java.lang.Integer id;
    /**
     * 公众号openid       db_column: openid  
     * 
     * 
     * @Length(max=64)
     */	
	private java.lang.String openid;
    /**
     * 微信用户openid       db_column: rosterid  
     * 
     * 
     * @Length(max=64)
     */	
	private java.lang.String rosterid;
    /**
     * 菜单名       db_column: menu_name  
     * 
     * 
     * @Length(max=30)
     */	
	private java.lang.String menuName;
    /**
     * eventType       db_column: event_type  
     * 
     * 
     * @Length(max=20)
     */	
	private java.lang.String eventType;
    /**
     * eventKey       db_column: event_key  
     * 
     * 
     * @Length(max=20)
     */	
	private java.lang.String eventKey;
    /**
     * 是否绑定 0：未绑定 1：已绑定       db_column: ifbind  
     * 
     * 
     * @Max(127)
     */	
	private Integer ifbind;
    /**
     * createtime       db_column: createtime  
     * 
     * 
     * 
     */	
	private java.util.Date createtime;
    /**
     * 状态 0：未使用 1：在使用       db_column: state  
     * 
     * 
     * @Max(127)
     */	
	private Integer state;
	//columns END

	public MobileWxMenuStat(){
	}

	public MobileWxMenuStat(
		java.lang.Integer id
	){
		this.id = id;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setOpenid(java.lang.String value) {
		this.openid = value;
	}
	
	public java.lang.String getOpenid() {
		return this.openid;
	}
	public void setRosterid(java.lang.String value) {
		this.rosterid = value;
	}
	
	public java.lang.String getRosterid() {
		return this.rosterid;
	}
	public void setMenuName(java.lang.String value) {
		this.menuName = value;
	}
	
	public java.lang.String getMenuName() {
		return this.menuName;
	}
	public void setEventType(java.lang.String value) {
		this.eventType = value;
	}
	
	public java.lang.String getEventType() {
		return this.eventType;
	}
	public void setEventKey(java.lang.String value) {
		this.eventKey = value;
	}
	
	public java.lang.String getEventKey() {
		return this.eventKey;
	}
	public void setIfbind(Integer value) {
		this.ifbind = value;
	}
	
	public Integer getIfbind() {
		return this.ifbind;
	}
	
	public void setCreatetime(java.util.Date value) {
		this.createtime = value;
	}
	
	public java.util.Date getCreatetime() {
		return this.createtime;
	}
	public void setState(Integer value) {
		this.state = value;
	}
	
	public Integer getState() {
		return this.state;
	}

}

