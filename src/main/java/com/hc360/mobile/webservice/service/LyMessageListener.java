package com.hc360.mobile.webservice.service; 

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.hc360.im.wxservice.common.WxConstant;
import com.hc360.jms.activemq.consumer.JMSMessageListener;
import com.hc360.mobile.webservice.pojo.First;
import com.hc360.mobile.webservice.pojo.Keyword1;
import com.hc360.mobile.webservice.pojo.Keyword2;
import com.hc360.mobile.webservice.pojo.Keyword3;
import com.hc360.mobile.webservice.pojo.Remark;
import com.hc360.mobile.webservice.pojo.WxTemplateMsgData;
import com.hc360.mobile.webservice.pojo.WxTemplateMsg;
import com.hc360.mobile.webservice.pojo.WxTemplatePojo;
import com.hc360.mobile.webservice.utils.MobileUtils;
import com.hc360.my.jms.mo.NoteMessage;

public class LyMessageListener extends JMSMessageListener{
 
	@Override
	public Serializable doMessage(Serializable message) {
			try{
				NoteMessage nm=(NoteMessage)message;//接收的消息要跟发送端对应一致
				Map<String,String> map = new HashMap<String, String>();
				String s;
				if (nm.getTitle().length()>50) {
					s=nm.getTitle().substring(0,50)+"...";
				}else{
					s=nm.getTitle();
				}
				map.put("username",nm.getUsername());
				map.put("k1",s);
				map.put("k2", nm.getCreateTime() );	
				map.put("k3", nm.getMessageid() );	
				map.put("type", "5");
				System.out.println(map);
				WxTemplatePojo pojo = new WxTemplatePojo(map);
				sendWxModelMsgToWx(pojo);
			}catch(Exception e){
			    e.printStackTrace();
			}
				return null;
		} 
	
	public String sendWxModelMsgToWx(WxTemplatePojo pojo) { 
		WxTemplateMsg msg = new WxTemplateMsg();
		msg.setTemplate_id(pojo.getTemplateId());
		msg.setTopcolor("#FF0000");
		
		String openid = MobileUtils.getWxOpenId(pojo.getUsername());
		msg.setTouser(openid);
		msg.setUrl(pojo.getUrl());
		
		WxTemplateMsgData data = new WxTemplateMsgData();
		data.setFirst(new First(pojo.getTitle(), "#282725"));
		data.setKeyword1(new Keyword1(pojo.getK1(), "#173177"));			
		data.setKeyword2(new Keyword2(pojo.getK2(), "#173177"));
		data.setKeyword3(new Keyword3(pojo.getK3(), "#173177")); 
		data.setRemark(new Remark("点击详情查看详细内容    ↓↓↓", "#990033"));
		msg.setData(data);
		
		String accessToken = MobileUtils.getWxAccount(WxConstant.WSC_OPENID);
		if (accessToken != null && !org.apache.commons.lang.StringUtils.isEmpty(openid)) {
			String sendResult = MobileUtils.sendPost(WxConstant.TEMPLATE_MSG_SEND_URL + accessToken, MobileUtils.getGson().toJson(msg));
			
			//统计发送的模版消息
			String sUrl = "http://madata.hc360.com/mobileweb/m/save/templateStat?templateId="+pojo.getTemplateId()+"&openid="+openid+"&type="+pojo.getType();
			MobileUtils.doGet(sUrl, "UTF-8");
			
			return sendResult;
		}
		return null;
	}


 }
