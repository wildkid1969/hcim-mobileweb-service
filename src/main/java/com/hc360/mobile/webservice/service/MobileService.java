package com.hc360.mobile.webservice.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.hc360.im.common.EncodeUtil;
import com.hc360.im.wxservice.common.WxConstant;
import com.hc360.mobile.webservice.dao.MobileDao;
import com.hc360.mobile.webservice.pojo.Activity;
import com.hc360.mobile.webservice.pojo.First;
import com.hc360.mobile.webservice.pojo.ImUserInfo;
import com.hc360.mobile.webservice.pojo.Keyword1;
import com.hc360.mobile.webservice.pojo.Keyword2;
import com.hc360.mobile.webservice.pojo.Keyword3;
import com.hc360.mobile.webservice.pojo.Keyword4;
import com.hc360.mobile.webservice.pojo.Keyword5;
import com.hc360.mobile.webservice.pojo.MobileWxMenuStat;
import com.hc360.mobile.webservice.pojo.MobileWxRosterOpportunity;
import com.hc360.mobile.webservice.pojo.Remark;
import com.hc360.mobile.webservice.pojo.Result;
import com.hc360.mobile.webservice.pojo.UserInfo;
import com.hc360.mobile.webservice.pojo.WxAccessToken;
import com.hc360.mobile.webservice.pojo.WxAppInfo;
import com.hc360.mobile.webservice.pojo.WxOpenId;
import com.hc360.mobile.webservice.pojo.WxTemplateMsg;
import com.hc360.mobile.webservice.pojo.WxTemplateMsgData;
import com.hc360.mobile.webservice.pojo.WxTemplatePojo;
import com.hc360.mobile.webservice.utils.MobileUtils;
import com.hc360.redis.RedisClusterClient;
import com.hc360.redis.types.cluster.CRedisString;
import com.hc360.rsf.config.ConfigLoader;
import com.hc360.rsf.config.RsfListener;
import com.hc360.rsf.kvdb.service.KVDBResult;
import com.hc360.rsf.kvdb.service.KVDBbcService;
import com.hc360.rsf.my.bo.OneKeyRepeatedParam;
import com.hc360.rsf.my.service.RSFMyMobileService;
import com.hc360.rsf.my.service.RsfOneKeyRepeatedService;
import com.hc360.rsf.my.vo.OneKeyRepeatedVO;

@Service
public class MobileService {

	@Resource
	private MobileDao mobiledao;
	
	private CRedisString redisString = RedisClusterClient.doString();

	public final String SALT = "0x707y5be55b3z7e5cda11afdbf37e";
	public final String DEFAULT_SSO = "http://sso.hc360.com/LoginProxy?sign=";
	public final String GET_WX_OPENID = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	public final String SSO = "http://sso.hc360.com/ticket4im?UserID=USERID";
	public final String DEFAULT_GET_MAINBUSINESS = "http://imyz.b2b.hc360.com/ams/getactivity.aspx?uid=USERID";
	
	private static Logger log = LoggerFactory.getLogger(MobileService.class);


	
	public static String getWxAccount(String openid) {
		String url = "http://madata.hc360.com/wxservice/m/get/accesstoken?openid=" + openid;
		String result = MobileUtils.doGet(url, "UTF-8");
		try {
			WxAccessToken wxAT = MobileUtils.getGson().fromJson(result, WxAccessToken.class);
			return wxAT.getAccessToken();
		} catch (Exception e) {
			return null;
		}
	}
	
	public String getOpenIdByUserName(String username){
		return mobiledao.getOpenIdByUserName(username);
	}
	
	public static String sendWxModelMsg(String param) {
		param = "{\"touser\":\"o8Tw0t9_BOFP7p3WKm2lUTrJunsI\",\"template_id\":\"w_gPXXWH1FGUo3IwY4fNWF7tHMkhjBnYTvV0SNJxxJU\","
				+ "\"url\":\"http://m.hc360.com/\",\"topcolor\":\"#FF0000\",\"data\":"
				+ "{	\"first\":{	\"value\":\"尊敬的移动互联事业部您好\",	\"color\":\"#173177\"	},	"
				+ "\"keyword1\":{	\"value\":\"移动互联事业部旗舰店\",	\"color\":\"#173177\"	},	"
				+ "\"keyword2\":{	\"value\":\"100小时免费工作\",	\"color\":\"#173177\"	},	"
				+ "\"keyword3\":{	\"value\":\"2015-11-24 09:35:35\",	\"color\":\"#173177\"	},	"
				+ "\"keyword4\":{	\"value\":\"人民币260.00元\",	\"color\":\"#173177\"	},	"
				+ "\"keyword5\":{	\"value\":\"付款成功\",	\"color\":\"#173177\"	},	"
				+ "\"remark\":{	\"value\":\"谢谢您的使用\",	\"color\":\"#173177\"	}}}";
		Map<String,String> map = new HashMap<String, String>();
		map.put("k1","北京宏伟一方经贸有限公司");
		map.put("k2", "2016-07-08");	
		map.put("k3", "北京宏伟一方经贸有限公司");	
		map.put("k4", "15011113333");	
		map.put("k6", "北京宏伟一方经贸有限公司");	
		map.put("username", "o8Tw0t3GrGzQ1OFwu5Du-jsVpP5M");//o8Tw0t3GrGzQ1OFwu5Du-jsVpP5M 孟亚、o8Tw0t-zjzaNsvF-4RT1N0QV5ypA 晓雨
		map.put("type", "2");
		System.out.println(map);
		WxTemplatePojo pojo = new WxTemplatePojo(map);
		
		System.out.println(pojo.getUrl());
		
		WxTemplateMsg wmm = new WxTemplateMsg(); 
		wmm.setTemplate_id(pojo.getTemplateId());
		wmm.setTopcolor("#FF0000");
		wmm.setTouser(pojo.getUsername());
		wmm.setUrl(pojo.getUrl());
		WxTemplateMsgData wmdno = new WxTemplateMsgData();
		wmdno.setFirst(new First("尊敬的移动互联事业部您好 ","#173177"));
		wmdno.setKeyword1(new Keyword1(pojo.getK1(),"#173177"));
		wmdno.setKeyword2(new Keyword2(pojo.getK2(),"#173177"));
		wmdno.setKeyword3(new Keyword3(pojo.getK3(),"#173177"));
		wmdno.setKeyword4(new Keyword4(pojo.getK4(),"#173177"));
		wmdno.setKeyword5(new Keyword5("付款成功","#173177"));
		wmdno.setRemark(new Remark("谢谢您的使用","#173177"));
		wmm.setData(wmdno);
		
		String accessToken = getWxAccount(WxConstant.WSC_OPENID);
		System.out.println(accessToken);
		if (accessToken != null) {
			String s = MobileUtils.sendPost(WxConstant.TEMPLATE_MSG_SEND_URL + accessToken, MobileUtils.getGson().toJson(wmm));
			System.out.println(s);
			return s;
		} else {
			return null;
		}
	}

	public String getOpenId(String code, String type) {
		WxAppInfo appInfo = mobiledao.getAppConfigInfo(type);
		String url = MobileUtils.getParameterizedUrl(GET_WX_OPENID, "APPID", appInfo.getAppId(), "SECRET",
				appInfo.getAppSecret(), "CODE", code);
		System.out.println("mobileweb sso get openId : url =" + url);
		String result = MobileUtils.doGet(url, "UTF-8");
		System.out.println("mobileweb get openid : type = " + type + "  result: " + result);
		try {
			WxOpenId openId = new Gson().fromJson(result, WxOpenId.class);
			return openId.getOpenid();
		} catch (Exception e) {
			return "";
		}
	}

	public String getOpenIdByAppId(String code, String appId) {
		WxAppInfo appInfo = mobiledao.getAppConfigInfoByWxId(appId);
		String url = MobileUtils.getParameterizedUrl(GET_WX_OPENID, "APPID", appInfo.getAppId(), "SECRET",
				appInfo.getAppSecret(), "CODE", code);
		String result = MobileUtils.doGet(url, "UTF-8");
		System.out.println(result);
		WxOpenId openId = new Gson().fromJson(result, WxOpenId.class);
		return openId.getOpenid();
	}

	public String getAccountBindStatus(String imId,String mpopenid) {
		String state = redisString.get("mobileweb:bind:userState:"+mpopenid+"-"+imId);
		
		int num = 0;
		if(StringUtils.isEmpty(state)){
			num = mobiledao.getAccountBindStatus(imId,mpopenid);
			if(num>0){
				num=1;
			}
			
			redisString.set("mobileweb:bind:userState:"+mpopenid+"-"+imId, String.valueOf(num));
		}else{
			num=Integer.valueOf(state);
		}
		
		Result result = new Result();
		if (num > 0) {
			result.setCode(200);
		} else {
			result.setCode(-1);
		}
		return new Gson().toJson(result);
	}
	

	/*
	 * 微店根据用户对用公众号code获取用户微信ID
	 */
	public String getWxUserId(String code, String state) {
		Result result = new Result();
		//根据code码和state获取openId
		String openId = getOpenIdByAppId(code, state);
		//根据appid获取公众号的信息
		WxAppInfo appInfo = mobiledao.getAppConfigInfoByWxId(state);
		if (appInfo != null) {
			result.setUserid(appInfo.getImid());
			result.setNickname(appInfo.getNickname());
		}
		if (openId != null && !openId.isEmpty()) {
			result.setCode(200);
			result.setOpenId(openId);
			result.setAppId(state);
			result.setUopenId(appInfo.getOpenId());

		} else {
			result.setCode(-1);
			result.setReturnMsg("数据出现问题，请联系系统人员!");
		}
		return new Gson().toJson(result);

	}

	public String getSSOSign(String code, String type) throws Exception {
		String openId = getOpenId(code, type);

		Result result = new Result();
		if (openId != null) {
			result.setOpenId(openId);
		} else {
			return new Gson().toJson(result);
		}

		String userId = mobiledao.getUserIdByOpenId(openId);
		System.out.println(openId);
		System.out.println(userId);
		// 接口添加用户微信昵称的接口
		result.setNickname(mobiledao.getNickNameByOpenId(openId));
		if (userId == null || userId.isEmpty()) {
			result.setCode(0);
			result.setImid(userId);
		} else {
			String getUrl = MobileUtils.getParameterizedUrl(SSO, "USERID", userId);
			String business = MobileUtils.getParameterizedUrl(DEFAULT_GET_MAINBUSINESS, "USERID", userId);
			String aa = MobileUtils.doGet(business, "UTF-8");
			if (aa.isEmpty()) {
				result.setCode(200);
				result.setImid(userId);
				result.setMainInd("");
			} else {
				Element mainbusiness = MobileUtils.praseXml(aa);
				Element ele = MobileUtils.praseXml(MobileUtils.doGet(getUrl, "UTF-8"));

				if (ele != null) {
					String ticket = ele.element("Ticket").getTextTrim();
					MessageDigest mdInst = MessageDigest.getInstance("MD5");
					result.setCode(200);
					result.setResult(DEFAULT_SSO
							+ EncodeUtil.hex(mdInst.digest((ticket + userId + this.SALT).getBytes())));
					result.setType(type);
					result.setImid(userId);
					if (mainbusiness.element("mainbusiness") != null)
						result.setMainInd(mainbusiness.element("mainbusiness").getTextTrim());
					else
						result.setMainInd("");
				}
			}
		}
		return new Gson().toJson(result);
	}

	public String bindWxAccount(String openId, String imId, int type, String oldImId, String pwd)
			throws NoSuchAlgorithmException {
		int ret = 0;
		Result result = new Result();
		imId = getMyAccountByPhoneNum(imId);
		result.setOpenId(openId);
		result.setImid(imId);
		if (imId != null && !imId.isEmpty()) {
			String auth = MobileUtils.doGet("http://sso.hc360.com/mobilelogin?LoginID="+imId+"&Passwd="+pwd+"&LoginType=json&source=msite", "GBK");
			log.info("--------ssoimid----:"+imId);
			log.info("--------ssopwd----:"+pwd);
			log.info("--------ssoresult----:"+auth);
			// 验证用户名密码
			if(auth.indexOf("success") != -1){
				//type 1 绑定 2 解绑 3 替换
				if (type == 1) {
					//是否已绑定   2016.4.20开始限制imid只能绑定一次
					int isHasImid = 0;
					String mpopenid = "";
					//在当前用户关注的公众号里查询是否绑定 2016.12.12
					List<UserInfo> roster = mobiledao.getUserInfo(null, openId);
					if(!roster.isEmpty() && !StringUtils.isEmpty(roster.get(0).getMpopenid())){
						isHasImid = mobiledao.getAccountBindStatus(imId,roster.get(0).getMpopenid());
						mpopenid = roster.get(0).getMpopenid();
					}
					log.info("--------isHasImid----:"+isHasImid);
					if(isHasImid==0){
						String getUrl = MobileUtils.getParameterizedUrl(SSO, "USERID", imId);
						Element ele = MobileUtils.praseXml(MobileUtils.doGet(getUrl, "UTF-8"));
						if (ele != null) {
							String ticket = ele.element("Ticket").getTextTrim();
							MessageDigest mdInst = MessageDigest.getInstance("MD5");
							result.setResult(DEFAULT_SSO
									+ EncodeUtil.hex(mdInst.digest((ticket + imId + this.SALT).getBytes())));
							ret = mobiledao.bindWxAccount(openId, imId, null);
							
							try {
								String re = redisString.set("mobileweb:bind:userState:"+mpopenid+"-"+imId, "1");
							    System.out.println("账号绑定redis保存结果："+re);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}else{
						ret = -2;
						result.setReturnMsg("此账号已被其他用户绑定！");
					}
				} else if (type == 2) {
					//	微商城 wsc 
					ret = mobiledao.unBindWxAccount(openId, imId, null);
					
					//在当前用户关注的公众号里查询是否绑定 2016.12.12
					String mpopenid = "";
					List<UserInfo> roster = mobiledao.getUserInfo(null, openId);
					if(!roster.isEmpty() && !StringUtils.isEmpty(roster.get(0).getMpopenid())){
						mpopenid = roster.get(0).getMpopenid();
					}
					
					try {
						String re = redisString.set("mobileweb:bind:userState:"+mpopenid+"-"+imId, "0");
					    System.out.println("返回值为OK则成功！保存结果："+re);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (type == 3) {
					ret = mobiledao.updBindWxAccount(openId, imId, oldImId);
				}
			}else{
				ret = -1;
				String msg = "";
				
				auth = auth.substring(5, auth.length()-1);
				JSONObject json = JSONObject.fromObject(auth);
				String value = json.getString("value");
				
				if("0".equalsIgnoreCase(value)){//被冻结
					msg ="此账号已被冻结，请联系客服：400-6360-888！";
				}else if("1".equalsIgnoreCase(value)){//黑名单
					msg ="此账号已被列入黑名单，请联系客服：400-6360-888！";
				}else if("2".equalsIgnoreCase(value)){//邮箱重复
					msg ="此账号邮箱重复，请更换账号重新登陆！";
				}else if("3".equalsIgnoreCase(value)){//密码过于简单
					msg ="此账号密码过于简单，请在PC端修改密码！";
				}else{
					msg ="登录错误，请重新输入！";
				}
				
				result.setReturnMsg(msg);
				
			}
		}

		if (ret > 0) {
			result.setCode(200);
		} else {
			result.setCode(ret);
		}
		return new Gson().toJson(result);
	}

	public String getTemplate() {
		return MobileUtils.doGet("http://openapi.m.hc360.com/openapi/v1/template/getInfo/1/20", "UTF-8");
	}

	public String getUserTemplate(String userId) {
		return MobileUtils.doGet("http://openapi.m.hc360.com/openapi/v1/template/queryUserTemplate/" + userId, "UTF-8");
	}

	public String setUserTemplate(String userId, String tempId) {
		return MobileUtils.doGet("http://openapi.m.hc360.com/openapi/v1/template/regUserTemplate/" + userId + "/" + tempId,
				"UTF-8");
	}

	public String updUserHeadImage(String userId, String imgBase64) {
		int ret = mobiledao.updIMHeadImgUrl(userId, imgBase64);
		Result result = new Result();
		if (ret > 0) {
			result.setCode(200);
		} else {
			result.setCode(-1);
		}
		return new Gson().toJson(result);
	}

	public String updUserNickName(String userId, String nickName) {
		int ret = mobiledao.updIMNickName(getMyAccountByPhoneNum(userId), nickName);
		Result result = new Result();
		if (ret > 0) {
			result.setCode(200);
		} else {
			result.setCode(-1);
		}
		return new Gson().toJson(result);
	}

	public String getUserNickName(String userId) {

		String nickname = mobiledao.getImUserNickName(getMyAccountByPhoneNum(userId));
		ImUserInfo imInfo = new ImUserInfo();
		imInfo.setNickname(nickname);
		imInfo.setUsername(userId);
		return new Gson().toJson(imInfo);
	}

	public String getMyAccountByPhoneNum(String phoneNum) {
		if (!MobileUtils.getUserIdByPhoneNum(phoneNum)) {
			return phoneNum;
		}
		
		//RsfListener监听器内部也是使用ConfigLoader完成的启动。
		//ConfigLoader类是在com.hc360.rsf.config.RsfListener完成实例化的，全局只有一个实例。
		//可以通过RsfListener. getConfigLoader()静态方法取得，一般客户端需要取得。
		//在容器关闭时会自己执行监听器中的退出方法，释放资源。Kill进程除外。其实RsfListener类就是调用ConfigLoader.destroy()方法完成的停止工作。
		ConfigLoader configLoader = RsfListener.getConfigLoader();

		//以下代码只有客户端需要执行，getServiceProxyBean()返回的远程服务接口的本地代理对象已缓存，多次执行返回的都是同一个对象。 
		RSFMyMobileService userService = (RSFMyMobileService) configLoader.getServiceProxyBean("clientUserServiceImpl");//配置文件中的id 
		
		//像调用本地方法一样调用远程方法。 
		String userName = userService.getUsernameByMobileNum(phoneNum);
		
		if(StringUtils.isEmpty(userName)){
			userName = phoneNum;
		}
		
		return userName;
	}

	public String getActivityData(String maininds) {
		List<Activity> ls = new ArrayList<Activity>();
		ls = mobiledao.getBusinProActivityVO(maininds);
		return new Gson().toJson(ls);
	}

	public String getProdInfo(String bcid) {
		ConfigLoader configLoader = RsfListener.getConfigLoader();
		KVDBbcService service = (KVDBbcService) configLoader.getServiceProxyBean("KVDBbcServicea");
		try {
			KVDBResult result = service.getOn("mobile", bcid);
			String info = new String(result.getValue(), "gbk");
			Document doc = Jsoup.parse(info);
			Elements element = doc.select("img");
			List<String> imgList = new ArrayList<String>();
			try {
				for (int i = 0; i < element.size(); i++) {
					imgList.add(element.get(i).toString());
				}
			} catch (Exception e) {
			}
			info = doc.toString();
			info = info.replaceAll("width=\"(.[^\"]*)\"", "width='100%'")
					.replaceAll("width:\"(.[^\"]*)\"", "width:;100%'").replaceAll("width:(.[^\"]*)px", "width:'100%'");
			for (String img : imgList) {
				if (img.indexOf("width") < 0) {
					String aac = img.substring(0, img.length() - 2) + " width='100%'" + ">";
					info = info.replaceAll(img, aac);
				}
			}

			return info;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public String getProdInfowx(String bcid) {
		ConfigLoader configLoader = RsfListener.getConfigLoader();
		KVDBbcService service = (KVDBbcService) configLoader.getServiceProxyBean("KVDBbcServicea");
		try {
			KVDBResult result = service.getOn("mobile", bcid);
			String info = new String(result.getValue(), "gbk");
			info = info.replaceAll("width=", "").replaceAll("width:", "");

			return info;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	//保存用户在页面的离开时间（统计停留时间）
	public String saveUserPageDuration(String openId,String eventKey,String starttime,String endtime){
		int r = mobiledao.saveUserPageDuration(openId, eventKey,starttime, endtime);
		
		Result result = new Result();
		
		if(r>0){
			result.setCode(200);
		}else{
			result.setCode(-1);
		}
		
		return new Gson().toJson(result);
		
	}
	
	//保存模版消息发送记录
	public int saveTemplateMsgStat(String templateId,String rosterId,int type){
		return mobiledao.saveTemplateMsgStat(templateId, rosterId, type);
	}
	
	//保存用户打开模版消息记录
	public String saveTamplateClickStat(String rosterId,int type){
		int r = mobiledao.saveTamplateClickStat(rosterId,type);
		Result result = new Result();
		
		if(r>0){
			result.setCode(200);
		}else{
			result.setCode(-1);
		}
		
		return new Gson().toJson(result);
	}
	
	public String saveWxUserClickstat(MobileWxMenuStat stat){
		int r = mobiledao.saveWxUserClickstat(stat);
		Result result = new Result();
		
		if(r>0){
			result.setCode(200);
		}else{
			result.setCode(-1);
		}
		
		return new Gson().toJson(result);
	}
	
	public String resend(String username, String sort){
		String rt = "";
		if(username.isEmpty() || sort.isEmpty()){
			rt = "{\"code\":\"400\",\"msg\":\"参数错误.\",\"count\":\"0\"}";
		}else{
			ConfigLoader configLoader = RsfListener.getConfigLoader();
			RsfOneKeyRepeatedService rokpService = (RsfOneKeyRepeatedService) configLoader.getServiceProxyBean("RsfOneKeyRepeatedService");
			OneKeyRepeatedParam  okrp = new OneKeyRepeatedParam();
			okrp.setUsername(username);
			okrp.setSort(sort);
			try{
				OneKeyRepeatedVO  okrv = rokpService.oneKeyRepeated(okrp);
				if(okrv.isIssucess()){
					rt = "{\"code\":\"200\",\"msg\":\""+okrv.getErrmsg()+"\",\"count\":\""+okrv.getSucesscount()+"\"}";
				}else{
					rt = "{\"code\":\"300\",\"msg\":\""+okrv.getErrmsg()+"\",\"count\":\""+okrv.getSucesscount()+"\"}";
				}
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}

		return rt;
	}
	
	public List<MobileWxRosterOpportunity> getRosterOpportunityList(String openid){
		return mobiledao.getRosterOpportunityList(openid);
	}
	
	public int insertRosterOpportunity(MobileWxRosterOpportunity opt){
		return mobiledao.insertRosterOpportunity(opt);
	}
	
	public MobileWxRosterOpportunity getRosterOpportunityById(String id){
		return mobiledao.getRosterOpportunityById(id);
	}
	
	public int updateRosterOpportunityByParam(MobileWxRosterOpportunity opt){
		return mobiledao.updateRosterOpportunityByParam(opt);
	}
	public int updateRosterOpportunityIsUsedFreeByParam(String openid){
		return mobiledao.updateRosterOpportunityIsUsedFreeByParam(openid);
	}
	public List<String> getRosterOpportunityOpenidList(){
		return mobiledao.getRosterOpportunityOpenidList();
	}
	
	public String getImidByOpenid(String openId){
		return mobiledao.getUserIdByOpenId(openId);
	}
	
	public MobileWxRosterOpportunity getRosterOpportunityByOpenidAndKey(String openid,String keyword){
		return mobiledao.getRosterOpportunityByOpenidAndKey(openid,keyword);
	}
	
	public int deleteRosterOpportunityById(String id,String openid){
		return mobiledao.deleteRosterOpportunityById(id, openid);
	}
	
	/**
	 * 查询粉丝信息
	 * @param imId
	 * @param openId
	 * @return
	 */
	public List<UserInfo> getUserInfo(String imId, String openId) {
		return mobiledao.getUserInfo(imId, openId);
	}
}