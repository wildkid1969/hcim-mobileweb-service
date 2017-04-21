package com.hc360.mobile.webservice.pojo;

import java.io.Serializable;
import java.net.URLEncoder;
import java.util.Map;

import com.hc360.im.wxservice.common.WxConstant;

public class WxTemplatePojo implements Serializable {

	private static final long serialVersionUID = -3227538812430603704L;
	
	public final String HUIYUANTIXING = "ZkhB7VvBayunNMrZ5tKdcPJuY7y7Fri9tu1sQSczmDQ";
	public final String XUNPANBAO = "gqV8EdAAJJnz-W2FI4-vd6NHmMvt3KJkAbivEliWQFY";
	public final String NEWORDER = "w_gPXXWH1FGUo3IwY4fNWF7tHMkhjBnYTvV0SNJxxJU";

	
	
	public WxTemplatePojo(Map<String, String> map) {
		if (map != null && map.get("type") != null) {
			this.type = Integer.valueOf(map.get("type"));
			this.username = map.get("username") != null ? map.get("username") : null;
			this.openid = map.get("openid") != null ? map.get("openid") : null;
			this.url = map.get("url") != null ? map.get("url") : null;
			this.k1 = map.get("k1") != null ? map.get("k1") : null;
			this.k2 = map.get("k2") != null ? map.get("k2") : null;
			this.k3 = map.get("k3") != null ? map.get("k3") : null;
			this.k4 = map.get("k4") != null ? map.get("k4") : null;
			this.k5 = map.get("k5") != null ? map.get("k5") : null;
			this.k6 = map.get("k6") != null ? map.get("k6") : null;
			this.k7 = map.get("k7") != null ? map.get("k7") : null;
			this.k8 = map.get("k8") != null ? map.get("k8") : null;
			this.title = map.get("title") != null ? map.get("title") : null;
		}
	}

	private String templateId;

	private int type; // 1 新订单 2 询盘宝 3 标王 4 周报 5留言 6商机订阅(3、4 、5、6共用模板会员提醒) 9收到的买家留言

	private String title;

	@SuppressWarnings("unused")
	private String url;

	private String username;
	
	private String openid;

	private String k1;

	private String k2;

	private String k3;

	private String k4;

	private String k5;

	private String k6;

	private String k7;

	private String k8;

	private String k9;

	private String k10;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getK1() {
		return k1;
	}

	public void setK1(String k1) {
		this.k1 = k1;
	}

	public String getK2() {
		return k2;
	}

	public void setK2(String k2) {
		this.k2 = k2;
	}

	public String getK3() {
		return k3;
	}

	public void setK3(String k3) {
		this.k3 = k3;
	}

	public String getK4() {
		return k4;
	}

	public void setK4(String k4) {
		this.k4 = k4;
	}

	public String getK5() {
		return k5;
	}

	public void setK5(String k5) {
		this.k5 = k5;
	}

	public String getK6() {
		return k6;
	}

	public void setK6(String k6) {
		this.k6 = k6;
	}

	public String getK7() {
		return k7;
	}

	public void setK7(String k7) {
		this.k7 = k7;
	}

	public String getK8() {
		return k8;
	}

	public void setK8(String k8) {
		this.k8 = k8;
	}

	public String getK9() {
		return k9;
	}

	public void setK9(String k9) {
		this.k9 = k9;
	}

	public String getK10() {
		return k10;
	}

	public void setK10(String k10) {
		this.k10 = k10;
	}

	public String getTitle() {
		if(this.type == 1){
			title= "尊敬的用户您好:您收到1条订单消息";
		}else if(this.type == 2) {
			title= "尊敬的用户您好，您收到“买家意向采购询盘”";
		}else if(this.type == 3) {
			title= "尊敬的标王用户您好，您有未绑定的标王关键词";
		}else if(this.type == 4) {
			title= "尊敬的标王用户您好：";
		}else if(this.type==5){
			title= "尊敬的用户您好，您收到1条留言信息";
		}else if(this.type==6){
			title= "尊敬的用户，您有一条最新的采购信息";
		}else if(this.type==9){
			title= "您好，有一可联系买家对您的店铺感兴趣，点开消息即可联系买家";
		}
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WxConstant.WSC_APPID+"&redirect_uri=";
		//中转页
		String redirectURI = "http://style.org.hc360.com/weixin/build/personal/redirect.html";
		try {
			//type 1 新订单  2 询盘宝  3 标王 4 周报  5 预约提醒 6 支付成功 7 验证码8留言 9收到的买家留言
			if (this.type == 2) {//询盘宝
				redirectURI += "?params="+URLEncoder.encode(URLEncoder.encode(URLEncoder.encode("sellerId@" + this.username
										 + "$sellerName@" + this.k6
										 + "$productName@" +this.k1
										 + "$corpName@" + this.k3
										 + "$inquiryDate@" + this.k2
										 + "$linkPhone@" + this.k4,"UTF-8"),"UTF-8"),"UTF-8");
				url += redirectURI + "&response_type=code&scope=snsapi_base&state=6#wechat_redirect";
			} else if (this.type == 3) {//标王
				redirectURI += "?params=sellerId@" + this.username + "$sellerName@" +this.k4;
				url += URLEncoder.encode(redirectURI,"UTF-8") + "&response_type=code&scope=snsapi_base&state=5#wechat_redirect";
			} else if (this.type == 1) {//订单
				url += redirectURI+ "&response_type=code&scope=snsapi_base&state=7#wechat_redirect";
			}else if (this.type==5) {//留言
				redirectURI += "?params=msgID@" + this.k3;
				url += redirectURI+ "&response_type=code&scope=snsapi_base&state=11#wechat_redirect";
			}else if (this.type==6) {//商机订阅
				redirectURI += "?params=optId@" + this.k3 + "$sign@" + this.k4;
				url +=	redirectURI+ "&response_type=code&scope=snsapi_base&state=13#wechat_redirect";
			}else if (this.type==9) {//收到的买家留言
				redirectURI += "?params=buyname@" + this.k3+"$sellname@"+this.k4+"$infoId@"+this.k5;
				url +=	redirectURI+ "&response_type=code&scope=snsapi_base&state=17#wechat_redirect";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTemplateId() {
		if (this.type == 1){
			this.templateId = this.NEWORDER;
		}else if(this.type == 2) {
			this.templateId = this.HUIYUANTIXING;
		}else if(this.type == 3 || this.type == 4 || this.type == 7) {
			this.templateId = this.HUIYUANTIXING;
		}else if(this.type== 5){
			this.templateId=this.HUIYUANTIXING;
		}else if(this.type== 6){
			this.templateId=this.HUIYUANTIXING;
		}else if(this.type== 9){
			this.templateId=this.HUIYUANTIXING;
		}
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
}
