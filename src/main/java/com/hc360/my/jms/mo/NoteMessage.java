package com.hc360.my.jms.mo;

import java.io.Serializable;

/**
 * class_description
 * 
 * @author dongjian
 * @version 1.0 
 * @date 2016��2��27��
 * @param 
 * @result 
 */
public class NoteMessage implements Serializable{
	
	/***/
	private static final long serialVersionUID = 1L;

	//����ID
	private String messageid;
	//�ɹ�����
	private String title;
	//���Է���ʱ��
	private String createTime;
	//���ҵ�¼��
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
