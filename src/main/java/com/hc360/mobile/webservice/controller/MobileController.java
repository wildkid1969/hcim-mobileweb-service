package com.hc360.mobile.webservice.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hc360.b2b.exception.MmtException;
import com.hc360.im.common.EncodeUtil;
import com.hc360.im.wxservice.common.WxConstant;
import com.hc360.mobile.webservice.pojo.MobileWxMenuStat;
import com.hc360.mobile.webservice.pojo.ResultMsg;
import com.hc360.mobile.webservice.pojo.UserInfo;
import com.hc360.mobile.webservice.pojo.WxTemplatePojo;
import com.hc360.mobile.webservice.service.MobileService;
import com.hc360.mobile.webservice.service.MobileWxRsfService;
import com.hc360.mobile.webservice.utils.DateUtils;
import com.hc360.mobile.webservice.utils.MobileUtils;
import com.hc360.rsf.config.ConfigLoader;
import com.hc360.rsf.config.RsfListener;
import com.hc360.rsf.manage.RSFBusNoteService;
import com.hc360.rsf.manage.bo.BusNoteWithTotalPage;
import com.hc360.rsf.my.bo.OneKeyRepeatedParam;
import com.hc360.rsf.my.service.RsfOneKeyRepeatedService;
import com.hc360.rsf.my.vo.OneKeyRepeatedVO;

@Controller
public class MobileController {

	@Resource
	private MobileService mobileService;
	
	@Resource
	private MobileWxRsfService mobileWxRsfService;
	
	private static Logger log = LoggerFactory.getLogger(MobileController.class);
	
	private static final String DEFAULT_CHARSET = "UTF-8";
	
	
	
	/**
	 * 系统部监控页
	 */
    @RequestMapping(value="/test/ok")
    @ResponseBody
    public String test(){
    	return "ok";
    }
    

	@RequestMapping(value = "/m/get/SSO", method = RequestMethod.GET)
	void handleMobileSSOBindRequest(@RequestParam(required = true, value = "code") String code,
			@RequestParam(required = true, value = "type") String type,
			@RequestParam(required = false, value = "callback") String callback, HttpServletResponse response,
			HttpServletRequest request) throws Exception {

		System.out.println("mobileweb get sso : code =" + code + "  type = " + type);
		String result = mobileService.getSSOSign(code, type);
		System.out.println(result);
		if (callback != null) {
			result = callback + "(" + result + ")";
		}
		System.out.println(result);
		byte[] b = result.getBytes("utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentLength(b.length);
		response.getOutputStream().write(b);
		response.flushBuffer();
	}
	/**
	 * 根据用户名获取用户的openId
	 * @param username
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/m/get/openid", method = RequestMethod.GET)
	void handleWxOpenIdByUsername(@RequestParam(required = true, value = "username") String username,
			HttpServletResponse response, HttpServletRequest request) throws Exception {

		String openid = mobileService.getOpenIdByUserName(username);
		
		String result = "";
		if(openid != null){		
			result = "{\"openid\":\""+openid+"\"}";
			
			List<UserInfo> user = mobileService.getUserInfo(null, openid);
			if(!user.isEmpty()){
				result = "{\"openid\":\""+openid+"\",\"nickname\":\""+user.get(0).getNickname()+"\",\"headerimg\":\""+user.get(0).getHeadimgurl()+"\"}";
			}
			
			
			
		}else{
			result = "{\"openid\":\"\"}";
		}
		byte[] b = result.getBytes("UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentLength(b.length);
		response.getOutputStream().write(b);
		response.flushBuffer();
	}
	/**
	 * 获取用户的openId
	 * @param code ：用于获取access_token
	 * @param state 
	 * @param callback
	 */
	@RequestMapping(value = "/m/get/wxinfo", method = RequestMethod.GET)
	void handleWxIdRequest(@RequestParam(required = true, value = "code") String code,
			@RequestParam(required = true, value = "state") String state,
			@RequestParam(required = false, value = "callback") String callback, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		String result = mobileService.getWxUserId(code, state);
		System.out.println(result);
		if (callback != null) {
			result = callback + "(" + result + ")";
		}
		System.out.println(result);
		byte[] b = result.getBytes("UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentLength(b.length);
		response.getOutputStream().write(b);
		response.flushBuffer();
	}

	/**
	 * 用户绑定
	 * @param openId
	 * @param imId
	 * @param pwd
	 * @param oldImId
	 * @param oldPwd
	 * @param type 1 绑定 2 解绑 3 替换
	 * @param kind
	 * @param callback
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/m/set/bind", method = RequestMethod.GET)
	void handleBindAccount(@RequestParam(required = true, value = "openid") String openId,
			@RequestParam(required = true, value = "imid") String imId,
			@RequestParam(required = true, value = "pwd") String pwd,
			@RequestParam(required = false, value = "oldimid") String oldImId,
			@RequestParam(required = false, value = "type") int type,
			@RequestParam(required = false, value = "callback") String callback, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		
		log.info("--------------bindopenid------------"+openId+";bindimid-----------"+imId);
		
		String result = mobileService.bindWxAccount(openId.trim(), imId.trim(), type, oldImId, pwd);
		if (callback != null) {
			result = callback + "(" + result + ")";
		}

		System.out.println(result);
		byte[] b = result.getBytes(DEFAULT_CHARSET);
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html;charset=utf-8");
		response.setContentLength(b.length);
		response.getOutputStream().write(b);
		response.flushBuffer();
	}

	/**
	 * 获取用户绑定状态
	 * 
	 * redis 缓存
	 * <key id="2" prefix="mobileweb:bind:" available="true" expire="172800" content="mobileweb项目:bind功能:key" comment="微商城用户绑定状态，有效期为2天"/>
	 * @param imId
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/m/get/bindstatus", method = RequestMethod.GET)
	void handleJustAccountBind(@RequestParam(required = true, value = "imid") String imId,String mpopenid,
			@RequestParam(required = false, value = "callback") String callback,
			HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		if(StringUtils.isEmpty(mpopenid)){
			mpopenid = WxConstant.WSC_OPENID;
		}
		
		String result = mobileService.getAccountBindStatus(imId,mpopenid);
		
		if (callback != null) {
			result = callback + "(" + result + ")";
		}
		
		byte[] b = result.getBytes();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentLength(b.length);
		response.getOutputStream().write(b);
		response.flushBuffer();
	}
	//获取模板
	@RequestMapping(value = "/m/get/template", method = RequestMethod.GET)
	void handleGetTemplate(@RequestParam(required = true, value = "callback") String callback,
			HttpServletResponse response, HttpServletRequest request) throws Exception {
		String result = mobileService.getTemplate();
		result = callback + "(" + result + ")";
		byte[] b = result.getBytes();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html;charset=UTF-8");
		response.setContentLength(b.length);
		response.getOutputStream().write(b);
		response.flushBuffer();
	}
	//根据用户的id获取模板
	@RequestMapping(value = "/m/get/usertemplate", method = RequestMethod.GET)
	void handleGetUserTemplate(@RequestParam(required = true, value = "userid") String userId,
			@RequestParam(required = true, value = "callback") String callback, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		String result = mobileService.getUserTemplate(userId);
		result = callback + "(" + result + ")";
		byte[] b = result.getBytes();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html;charset=UTF-8");
		response.setContentLength(b.length);
		response.getOutputStream().write(b);
		response.flushBuffer();
	}
	//设置用户模板
	@RequestMapping(value = "/m/set/usertemplate", method = RequestMethod.GET)
	void handleSetUserTemplate(@RequestParam(required = true, value = "userid") String userId,
			@RequestParam(required = true, value = "tempid") String tempId,
			@RequestParam(required = true, value = "callback") String callback, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		String result = mobileService.setUserTemplate(userId, tempId);
		result = callback + "(" + result + ")";
		byte[] b = result.getBytes();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html;charset=UTF-8");
		response.setContentLength(b.length);
		response.getOutputStream().write(b);
		response.flushBuffer();
	}
	//修改用户头像
	@RequestMapping(value = "/m/set/userheadimg", method = { RequestMethod.GET, RequestMethod.POST })
	void handleUpdUserHeadImg(@RequestParam(required = true, value = "userid") String userId,
			HttpServletResponse response, HttpServletRequest request) throws Exception {
		String ua = request.getHeader("User-Agent");

		MessageDigest mdInst = MessageDigest.getInstance("MD5");

		String md5 = EncodeUtil.hex(mdInst.digest(("*#hc360#" + userId).getBytes()));

		String yz = md5.substring(md5.length() - 2, md5.length());

		if (!ua.endsWith(yz)) {
			return;
		}
		String imgBase64 = request.getParameter("headimg");

		String result = mobileService.updUserHeadImage(userId, imgBase64);

		byte[] b = result.getBytes();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html;charset=UTF-8");
		response.setContentLength(b.length);
		response.getOutputStream().write(b);
		response.flushBuffer();
	}
	//获取或修改用户昵称
	@RequestMapping(value = "/m/op/usernickname", method = { RequestMethod.GET, RequestMethod.POST })
	void handleOpUserNickName(@RequestParam(required = true, value = "userid") String userId,
			@RequestParam(required = false, value = "nickname") String nickName,
			@RequestParam(required = true, value = "op") int Op, HttpServletResponse response,
			HttpServletRequest request) throws Exception {

		String ua = request.getHeader("User-Agent");

		MessageDigest mdInst = MessageDigest.getInstance("MD5");

		String md5 = EncodeUtil.hex(mdInst.digest(("*#hc360#" + userId).getBytes()));

		String yz = md5.substring(md5.length() - 2, md5.length());

		if (!ua.endsWith(yz)) {
			return;
		}

		String result = "";
		if (Op == 1) { // 修改用户名
			result = mobileService.updUserNickName(userId, nickName);
		} else if (Op == 2) { // 获取用户昵称
			result = mobileService.getUserNickName(userId);
		}
		byte[] b = result.getBytes();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html;charset=UTF-8");
		response.setContentLength(b.length);
		response.getOutputStream().write(b);
		response.flushBuffer();
	}

	// 通过手机号获取用户username 用户名
	@RequestMapping(value = "/m/exchange/phone", method = { RequestMethod.GET, RequestMethod.POST })
	void handleExchangePhone(@RequestParam(required = true, value = "phone") String phone,
			@RequestParam(required = false, value = "test") String test, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		//System.out.println(mobileService.sendWxModelMsg("asd"));
		 String ua = request.getHeader("User-Agent");
		
		 MessageDigest mdInst = MessageDigest.getInstance("MD5");
		
		 String md5 = EncodeUtil.hex(mdInst.digest(("*#hc360#" + phone)
		 .getBytes()));
		
		 String yz = md5.substring(md5.length() - 2, md5.length());
		
		 if (!ua.endsWith(yz) && !test.equals("test")) {
		 return;
		 }
		 String result = "";
		 result = mobileService.getMyAccountByPhoneNum(phone);
		 result = "{\"userid\" : \"" + result + "\",\"phone\":\"" + phone
		 + "\"}";
		
		 byte[] b = result.getBytes();
		 response.setStatus(HttpServletResponse.SC_OK);
		 response.setContentType("text/html;charset=UTF-8");
		 response.setContentLength(b.length);
		 response.getOutputStream().write(b);
		 response.flushBuffer();
	}

	@RequestMapping(value = "/redir/apk", method = { RequestMethod.GET, RequestMethod.POST })
	void redirectUrlDownLoadApk(HttpServletResponse response, HttpServletRequest request) {
		//String userAgent = request.getHeader("User-Agent");
		//if (true || userAgent.toLowerCase().indexOf("chrome") >= 0) {
			try {
				response.setContentType("text/html;charset=UTF-8");
				response.setHeader("Location",
						"http://www.app.hc360.com/download/android/cgt/HcCaigt_signed_aligned.apk");
				// byte[] b = "".getBytes();
				// response.setStatus(HttpServletResponse.SC_OK);
				// response.getOutputStream().write(b);
				// response.flushBuffer();
				response.sendRedirect("http://www.app.hc360.com/download/android/cgt/HcCaigt_signed_aligned.apk");
			} catch (IOException e) {
				e.printStackTrace();
			}
		//}
	}
	
	/**
	 * 保存用户在各菜单停留的时间
	 * @param openId 用户的openid	
	 * @param eventKey 页面对应的自定义菜单的eventkey
	 * @param endtime 用户离开时间
	 * @param callback 跨域回调参数
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="/m/save/pageDuration")
	public void savePageduration(@RequestParam(required=true,value="openid")String openId,
			@RequestParam(required=true,value="eventkey")String eventKey,
			@RequestParam(required=true,value="starttime")String starttime,
			@RequestParam(required=true,value="endtime")String endtime,
			@RequestParam(required=false,value="callback")String callback,HttpServletResponse response) throws IOException{
		//UPDATE mobile_wx_menu_stat SET starttime="2016-04-06 11:00:00",endtime="2016-04-06 12:00:00" 
		//WHERE rosterid="ocbOrsyljmApcUGmN4fu73HQ4M3E" AND event_key="vwsc" ORDER BY createtime DESC LIMIT 1
		
		String result = mobileService.saveUserPageDuration(openId, eventKey, starttime,endtime);
		
		if (callback != null) {
			result = callback + "(" + result + ")";
		}
		
		byte[] b = result.getBytes("utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentLength(b.length);
		response.getOutputStream().write(b);
		response.flushBuffer();
		
		
	}
	
	/**
	 * 保存模版消息的发送记录
	 * @param templateId 模版id
	 * @param openid 用户的openid
	 * @param type 模版类型
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/m/save/templateStat")
	public void saveTemplateStat(String templateId,String openid,String type,HttpServletResponse response) throws Exception{
		//INSERT INTO mobile_wx_templatemsg_stat (template_id,roster_id,type,createtime) VALUES (?,?,?,?)
		int re = mobileService.saveTemplateMsgStat(templateId, openid, Integer.valueOf(type));
		PrintWriter out = response.getWriter();
		out.print(re);
		out.flush();
		out.close();
	}
	
	
	/**
	 * 保存用户打开消息的记录
	 * @param openid
	 * @param type
	 * @param callback
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="/m/save/temaplateClickStat")
	public void saveTamplateClickStat(@RequestParam(required=true,value="openid")String openid,
			@RequestParam(required=true,value="type")Integer type,
			@RequestParam(required=false,value="callback")String callback,HttpServletResponse response) throws IOException{
		
		String result = mobileService.saveTamplateClickStat(openid,type);
		
		if (callback != null) {
			result = callback + "(" + result + ")";
		}
		
		byte[] b = result.getBytes("utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentLength(b.length);
		response.getOutputStream().write(b);
		response.flushBuffer();
		
	}
	
	/**
	 * 保存用户点击一键重发的记录
	 * @param openid
	 * @param callback
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="/m/save/onerepeatBtn")
	public void saveOnerepeatBtn(@RequestParam(required=true,value="openid")String openid,
			@RequestParam(required=false,value="callback")String callback,HttpServletResponse response) throws IOException{
		
		MobileWxMenuStat stat = new MobileWxMenuStat();
		stat.setOpenid(WxConstant.WSC_OPENID);//微商城openid
		stat.setRosterid(openid);
		stat.setMenuName("一键重发按钮");
		stat.setEventType("pBtn");
		stat.setEventKey("pyjcfbtn");
		stat.setIfbind(1);
		stat.setCreatetime(new Date());
		stat.setState(1);
		
		String result = mobileService.saveWxUserClickstat(stat);
		
		if (callback != null) {
			result = callback + "(" + result + ")";
		}
		
		byte[] b = result.getBytes("utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentLength(b.length);
		response.getOutputStream().write(b);
		response.flushBuffer();
		
	}

	/**
	 * 一键重发
	 * @param username
	 * @param sort
	 * @param callback
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value="/m/resend")
	public void resend(@RequestParam(required=true,value="username")String username,
			@RequestParam(required=true,value="sort")String sort, 
			@RequestParam(required=false,value="callback")String callback,
			HttpServletResponse response) throws Exception{
		String result = mobileService.resend(username, sort);

		if (callback != null) {
			result = callback + "(" + result + ")";
		}
		byte[] b = result.getBytes("utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentLength(b.length);
		response.getOutputStream().write(b);
		response.flushBuffer();
	}
	
	@RequestMapping(value="/m/filterResult")
	@ResponseBody
	public void resend(String result,String callback,HttpServletResponse response) throws IOException{
		if (callback != null) {
			result = callback + "(" + result + ")";
		}
		byte[] b = result.getBytes("utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentLength(b.length);
		response.getOutputStream().write(b);
		response.flushBuffer();
	}
	
	
	
	/**
	 * 查询留言
	 * @param imid
	 * @param page
	 * @return
	 * @throws MmtException
	 */
	@RequestMapping(value="/m/get/busNote")
	@ResponseBody
	public String getBusNote(String imid,Integer page,String callback) throws MmtException{
		ResultMsg msg = new ResultMsg();
		msg.setCode(300);
		
		if(StringUtils.isEmpty(imid)){
			msg.setCode(100);
			return MobileUtils.getGson().toJson(msg);
		}
		
		ConfigLoader configLoader = RsfListener.getConfigLoader();

		RSFBusNoteService busNoteService = (RSFBusNoteService) configLoader.getServiceProxyBean("busNote");//配置文件中的id 
		BusNoteWithTotalPage busNote = busNoteService.getBusNotes(imid, page);
		
		if(busNote!=null && busNote.getBnList()!=null && !busNote.getBnList().isEmpty()){
			msg.setCode(200);
			msg.setData(busNote.getBnList());
			msg.setPage(page);
			msg.setTotalPage(busNote.getTotalPage());
		}
		
		if(!StringUtils.isEmpty(callback)){
			return callback + "(" + MobileUtils.getGson().toJson(msg) +")";
		}
		
		return MobileUtils.getGson().toJson(msg);
		
	}
	
	
	/**
	 * 给卖家发模板消息
	 * @param buyname
	 * @param sellname
	 * @param callback
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="/purchase/touchMe")
	public void touchMe(String buyname,String sellname,String callback,HttpServletResponse response) throws IOException{
		ResultMsg msg = new ResultMsg();
		msg.setCode(300);
		
		if(StringUtils.isEmpty(buyname) || StringUtils.isEmpty(sellname)){
			msg.setCode(100);
			
			byte buf[] = MobileUtils.getGson().toJson(msg).toString().getBytes();
			if(!StringUtils.isEmpty(callback)){
				buf = (callback+"(" +MobileUtils.getGson().toJson(msg) + ")").toString().getBytes();
			}
			response.setContentLength(buf.length);
			response.getOutputStream().write(buf);
			response.setStatus(HttpServletResponse.SC_OK);
			response.flushBuffer();
		}
		
		String[] sellers = sellname.split(",");
		
		for(String seller:sellers){
			List<UserInfo> user = mobileService.getUserInfo(seller, null);
			
			if(!user.isEmpty()){
				msg.setCode(200);
				
				Map<String,String> map = new HashMap<String,String>();
				map.put("openid",user.get(0).getOpenid());
				map.put("k1","请联系我");
				map.put("k2", DateUtils.getCurrentTime());	
				map.put("k3", buyname);
				map.put("k4", sellname);
				map.put("k5", "0");
				map.put("type", "9");//给卖家发的留言
				
				MobileUtils.sendMessageToSeller(new WxTemplatePojo(map));
			}
		}
		
		
		byte buf[] = MobileUtils.getGson().toJson(msg).toString().getBytes();
		if(!StringUtils.isEmpty(callback)){
			buf = (callback+"(" +MobileUtils.getGson().toJson(msg) + ")").toString().getBytes();
		}
		response.setContentType("text/html;charset=UTF-8");
		response.setContentLength(buf.length);
		response.getOutputStream().write(buf);
		response.setStatus(HttpServletResponse.SC_OK);
		response.flushBuffer();
		
		
	}
	
	/**
	 * 
	 * @param buyerName
	 * @param sellerName
	 * @param content
	 * @param kind （0发给卖家，1发给买家）
	 * @param callback
	 * @param response
	 * @throws MmtException
	 * @throws IOException
	 */
	@RequestMapping(value="/purchase/leaveMsgToSeller")
	public void leaveMsg(String buyerName,String sellerName,String content,String kind,String infoId,String callback,HttpServletResponse response) throws MmtException, IOException{
		ResultMsg msg = new ResultMsg();
		msg.setCode(300);
		
		if(StringUtils.isEmpty(buyerName) || StringUtils.isEmpty(sellerName) || StringUtils.isEmpty(content)){
			msg.setCode(100);
			
			byte buf[] = MobileUtils.getGson().toJson(msg).toString().getBytes();
			if(!StringUtils.isEmpty(callback)){
				buf = (callback+"(" +MobileUtils.getGson().toJson(msg) + ")").toString().getBytes();
			}
			response.setContentLength(buf.length);
			response.getOutputStream().write(buf);
			response.setStatus(HttpServletResponse.SC_OK);
			response.flushBuffer();
		}
		
		List<UserInfo> user = mobileService.getUserInfo(sellerName, null);
		
		if(!user.isEmpty() && "0".equalsIgnoreCase(kind)){
			
			if(content.length()>10){
				content = content.substring(0, 10)+"...";
			}
			//发送模板消息
			Map<String,String> map = new HashMap<String,String>();
			map.put("openid",user.get(0).getOpenid());
			map.put("k1",content);
			map.put("k2", DateUtils.getCurrentTime());	
			map.put("k3", buyerName);
			map.put("k4", sellerName);
			map.put("k5", infoId);
			map.put("type", "9");//买家回复
			
			String result = MobileUtils.sendMessageToSeller(new WxTemplatePojo(map));
			
			if(result.contains("ok")){
				msg.setCode(200);
			}
		}
		
		byte buf[] = MobileUtils.getGson().toJson(msg).toString().getBytes();
		if(!StringUtils.isEmpty(callback)){
			buf = (callback+"(" +MobileUtils.getGson().toJson(msg) + ")").toString().getBytes();
		}
		response.setContentLength(buf.length);
		response.getOutputStream().write(buf);
		response.setStatus(HttpServletResponse.SC_OK);
		response.flushBuffer();
	}
	
	
	public static void main(String[] args) throws Exception {
		//以下代码在客户端或服务端系统启动时执行一次就够了 
		//url="192.168.44.194:63633"
		String xmlPath = "classpath:com/hc360/mobile/webservice/rsf.xml"; 
		ConfigLoader configLoader = new ConfigLoader(xmlPath); 
		configLoader.start(); 
//		
//		//以下代码只有客户端需要执行，getServiceProxyBean()返回的远程服务接口的本地代理对象已缓存，多次执行返回的都是同一个对象。 
//		RSFBusNoteService userService= (RSFBusNoteService) configLoader.getServiceProxyBean("busNote");//配置文件中的id 
//		BusNoteWithTotalPage busNote = userService.getBusNotes("liyeqing13", 1);
//		System.out.println(busNote.getBnList().get(0).getTitle());
		
		RsfOneKeyRepeatedService rokpService = (RsfOneKeyRepeatedService) configLoader.getServiceProxyBean("RsfOneKeyRepeatedService");
		OneKeyRepeatedParam  okrp = new OneKeyRepeatedParam();
		okrp.setUsername("liufang11");
		okrp.setSort("1");
		OneKeyRepeatedVO  okrv = rokpService.oneKeyRepeated(okrp);
		System.out.println(okrv.getSucesscount());
		System.out.println(okrv.isIssucess());
			
	}
			
}