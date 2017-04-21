package com.hc360.mobile.webservice.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hc360.im.wxservice.common.WxConstant;
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

@Service
public class MobileWxRsfServiceImpl extends HttpServlet implements MobileWxRsfService {

	private static final long serialVersionUID = -7030575745792982792L;
	
	@Override
	public String sendWxModelMsg(Map<String, String> map) {
		if (map != null) {
			WxTemplatePojo pojo = new WxTemplatePojo(map);
			String cc = sendWxModelMsgToWx(pojo);
			System.out.println(cc);
		}
		return "ok";
	}

	public static String sendWxModelMsgToWx(WxTemplatePojo pojo) {
		WxTemplateMsg wmm = new WxTemplateMsg();
		wmm.setTemplate_id(pojo.getTemplateId());
		wmm.setTopcolor("#FF0000");
		String openid = MobileUtils.getWxOpenId(pojo.getUsername());
		wmm.setTouser(openid);
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
	
		String accessToken = MobileUtils.getWxAccount(WxConstant.WSC_OPENID);
		if (accessToken != null && !StringUtils.isEmpty(openid)) {
			String sendResult = MobileUtils.sendPost(WxConstant.TEMPLATE_MSG_SEND_URL + accessToken, MobileUtils.getGson().toJson(wmm));
			//记录发送的消息动作
			String sUrl = "http://madata.hc360.com/mobileweb/m/save/templateStat?templateId="+pojo.getTemplateId()+"&openid="+openid+"&type="+pojo.getType();
			MobileUtils.doGet(sUrl, "UTF-8");
			return sendResult;
		} else {
			return null;
		}
	}
	
	public static void main(String[] args) {//本地即可发送模版消息
		Map<String, String> map = new HashMap<String,String>();
		map.put("username","o8Tw0t3GrGzQ1OFwu5Du-jsVpP5M");//o8Tw0t-zjzaNsvF-4RT1N0QV5ypA 、ocbOrs85k74wpFcS3CFMryWrmmDY、o8Tw0t3GrGzQ1OFwu5Du-jsVpP5M
		map.put("k1","123");
		map.put("k2", "2016-06-30");	
		map.put("k3", "777");	
		map.put("k4", "777");	
		map.put("k5", "777");	
		map.put("k6", "cuijian");	
		map.put("type", "2");
		WxTemplatePojo pojo = new WxTemplatePojo(map);
		String cc = sendWxModelMsgToWx(pojo);
		System.out.println(cc);
	}
}
