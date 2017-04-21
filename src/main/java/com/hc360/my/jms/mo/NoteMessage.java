package com.hc360.my.jms.mo;

import java.io.Serializable;

/**
 * class_description
 * 
 * @author dongjian
 * @version 1.0 
 * @date 2016年2月27日
 * @param 
 * @result 
 */
public class NoteMessage implements Serializable{
	
	/***/
	private static final long serialVersionUID = 1L;

	//留言ID
	private String messageid;
	//采购标题
	private String title;
	//留言发布时间
	private String createTime;
	//卖家登录名
	private String username;
	
	public NoteMessage(){}
	
	public NoteMessage(String messageid){
		this.messageid = messageid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMessageid() {
		return messageid;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
