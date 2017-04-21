package com.hc360.mobile.webservice.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hc360.im.wxservice.common.WxConstant;
import com.hc360.jms.activemq.consumer.JMSMessageListener;
import com.hc360.mobile.webservice.pojo.First;
import com.hc360.mobile.webservice.pojo.Keyword1;
import com.hc360.mobile.webservice.pojo.Keyword2;
import com.hc360.mobile.webservice.pojo.Keyword3;
import com.hc360.mobile.webservice.pojo.Keyword4;
import com.hc360.mobile.webservice.pojo.Keyword5;
import com.hc360.mobile.webservice.pojo.Remark;
import com.hc360.mobile.webservice.pojo.WxTemplateMsg;
import com.hc360.mobile.webservice.pojo.WxTemplateMsgData;
import com.hc360.mobile.webservice.pojo.WxTemplatePojo;
import com.hc360.mobile.webservice.utils.MobileUtils;

public class OrderMessageListener extends JMSMessageListener{

	@Override
	public Serializable doMessage(Serializable message) {
		try{
			String msg = (String)message; //接收的消息要跟发送端对应一致			
			JSONObject jsonObject =JSONObject.parseObject(msg);
			String bcName = (String)jsonObject.get("bcName") != null ? (String)jsonObject.get("bcName") : "您的商品"; // 商品名称
			String price = (String) jsonObject.get("amount");// 订单金额
			//String buyerName = (String) jsonObject.get("buyerUserName"); // 买家username
			String sellerName = (String) jsonObject.get("sellerUserName");// 卖家username
			String buyerCompanyName = (String) jsonObject.get("buyer"); // 卖家公司名称
			String orderTime = (String) jsonObject.get("time"); // 下单时间
			Map<String,String> map = new HashMap<String, String>();
			map.put("username",sellerName);
			map.put("k1", buyerCompanyName);
			map.put("k2", bcName);			
			map.put("k3", orderTime);
			map.put("k4", price);
			map.put("k5", "未付款");
			map.put("type", "1");
			WxTemplatePojo pojo = new WxTemplatePojo(map);
			sendWxModelMsgToWx(pojo);
		}catch(Exception e){
		    e.printStackTrace();
		}
			return null;
	}
	
	public String sendWxModelMsgToWx(WxTemplatePojo pojo) {
		System.out.println(pojo.getK1());
		System.out.println(pojo.getUsername());
		WxTemplateMsg wmm = new WxTemplateMsg();
		wmm.setTemplate_id(pojo.getTemplateId());
		wmm.setTopcolor("#FF0000");
		String openid = MobileUtils.getWxOpenId(pojo.getUsername());
		wmm.setTouser(openid);  //正式环境 
//		wmm.setTouser("o8Tw0t1KzVKifhXoCH_jm3WN8IOY"); // 测试情况 --- 只发给李信
		wmm.setUrl(pojo.getUrl());
		WxTemplateMsgData wmdno = new WxTemplateMsgData();
		wmdno.setFirst(new First(pojo.getTitle(), "#282725"));
		wmdno.setKeyword1(new Keyword1(pojo.getK1(), "#173177"));			
		wmdno.setKeyword2(new Keyword2(pojo.getK2(), "#173177"));
		wmdno.setKeyword3(new Keyword3(pojo.getK3(), "#173177"));
		wmdno.setKeyword4(new Keyword4(pojo.getK4(), "#173177"));
		wmdno.setKeyword5(new Keyword5(pojo.getK5(), "#173177"));
		wmdno.setRemark(new Remark("谢谢您的使用", "#8d8d8d"));
		wmm.setData(wmdno);
		System.out.println(MobileUtils.getGson().toJson(wmm));
		String accessToken = MobileUtils.getWxAccount(WxConstant.WSC_OPENID);
		if (accessToken != null && !org.apache.commons.lang.StringUtils.isEmpty(openid)) {
			String sendResult = MobileUtils.sendPost(WxConstant.TEMPLATE_MSG_SEND_URL + accessToken, MobileUtils.getGson().toJson(wmm));
			String sUrl = "http://madata.hc360.com/mobileweb/m/save/templateStat?templateId="+pojo.getTemplateId()+"&openid="+openid+"&type="+pojo.getType();
			MobileUtils.doGet(sUrl, "UTF-8");
			return sendResult;
		}
		return null;
	}

}
