package com.hc360.mobile.webservice.pojo;


import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class MobileWxRosterOpportunity {
	
	//columns START
    /**
     * id       db_column: id  
     * 
     * 
     * 
     */	
	private java.lang.Long id;
    /**
     * openid       db_column: openid  
     * 
     * 
     * @Length(max=64)
     */	
	private java.lang.String openid;
    /**
     * 商机关键字       db_column: keyword  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String keyword;
    /**
     * province       db_column: province  
     * 
     * 
     * @Length(max=20)
     */	
	private java.lang.String province;
    /**
     * city       db_column: city  
     * 
     * 
     * @Length(max=30)
     */	
	private java.lang.String city;
    /**
     * 限制条数       db_column: limit_count  
     * 
     * 
     * 
     */	
	private java.lang.Integer limit_count;
    /**
     * createtime       db_column: createtime  
     * 
     * 
     * 
     */	
	private java.util.Date createtime;
	
	private java.lang.Integer is_used_free;
	//columns END

	public MobileWxRosterOpportunity(){
	}

	public MobileWxRosterOpportunity(
		java.lang.Long id
	){
		this.id = id;
	}

	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setOpenid(java.lang.String value) {
		this.openid = value;
	}
	
	public java.lang.String getOpenid() {
		return this.openid;
	}
	public void setKeyword(java.lang.String value) {
		this.keyword = value;
	}
	
	public java.lang.String getKeyword() {
		return this.keyword;
	}
	public void setProvince(java.lang.String value) {
		this.province = value;
	}
	
	public java.lang.String getProvince() {
		return this.province;
	}
	public void setCity(java.lang.String value) {
		this.city = value;
	}
	
	public java.lang.String getCity() {
		return this.city;
	}
	public void setLimit_count(java.lang.Integer value) {
		this.limit_count = value;
	}
	
	public java.lang.Integer getLimit_count() {
		return this.limit_count;
	}
	public void setCreatetime(java.util.Date value) {
		this.createtime = value;
	}
	
	public java.util.Date getCreatetime() {
		return this.createtime;
	}

	public java.lang.Integer getIs_used_free() {
		return is_used_free;
	}

	public void setIs_used_free(java.lang.Integer is_used_free) {
		this.is_used_free = is_used_free;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Openid",getOpenid())
			.append("Keyword",getKeyword())
			.append("Province",getProvince())
			.append("City",getCity())
			.append("LimitCount",getLimit_count())
			.append("Createtime",getCreatetime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MobileWxRosterOpportunity == false) return false;
		if(this == obj) return true;
		MobileWxRosterOpportunity other = (MobileWxRosterOpportunity)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

