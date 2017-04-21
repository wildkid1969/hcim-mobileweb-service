package com.hc360.mobile.webservice.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.google.gson.Gson;
import com.hc360.im.wxservice.common.WxConstant;
import com.hc360.mobile.webservice.pojo.First;
import com.hc360.mobile.webservice.pojo.Keyword1;
import com.hc360.mobile.webservice.pojo.Keyword2;
import com.hc360.mobile.webservice.pojo.Keyword3;
import com.hc360.mobile.webservice.pojo.Remark;
import com.hc360.mobile.webservice.pojo.WxAccessToken;
import com.hc360.mobile.webservice.pojo.WxOpenId;
import com.hc360.mobile.webservice.pojo.WxTemplateMsg;
import com.hc360.mobile.webservice.pojo.WxTemplateMsgData;
import com.hc360.mobile.webservice.pojo.WxTemplatePojo;

public class MobileUtils {
	
	private static Gson gson = null;
	
	public static String getWxAccount(String openid) {
		String url = "http://madata.hc360.com/wxservice/m/get/accesstoken?openid=" + openid;
		String result = doGet(url, "UTF-8");
		try {
			WxAccessToken wxAT = getGson().fromJson(result, WxAccessToken.class);
			return wxAT.getAccessToken();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String getWxOpenId(String username){
		String url = "http://madata.hc360.com/mobileweb/m/get/openid?username="+username;
		String result = doGet(url, "UTF-8");
		try{
			WxOpenId openId = getGson().fromJson(result, WxOpenId.class);
			return openId.getOpenid();
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 返回请求参数的个数
	 * @param request
	 * @return 有xss攻击信息的直接返回-1
	 */
	public static int requestParamCount(HttpServletRequest request){
		//提交参数有值的个数
		int count=0;
		Enumeration<?> names = request.getParameterNames();
		for (Enumeration<?> e = names;e.hasMoreElements();){
	        String thisName=e.nextElement().toString();
	        String value=request.getParameter(thisName);
	        
	        if(!StringUtils.isEmpty(value)){
	        	if(XssProtectUtils.isHaveXSS(value)){
	        		return -1;
	        	}
	        	count++;
	        }
		}
		return count;

	}

	public static Element praseXml(String xmlStr) {
		try {
			Document doc = DocumentHelper.parseText(xmlStr);
			return doc.getRootElement();
		} catch (DocumentException e) {
			return null;
		}
	}

	public static String getParameterizedUrl(String url, String... args) {
		String result = url;
		for (int i = 0; i < args.length; i += 2) {
			String p = args[i];
			String v = args[i + 1];
			result = result.replaceAll(p, v);
		}
		return result;
	}
	
	public static Long getNowTimeLong(){
		return new Date().getTime();
	}

	public static String longToGMTStr(long time, String format) {
		String pattern = "EEE, dd MMM yyyy HH:mm:ss z";
		if (format != null) {
			pattern = format;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.US);

		return formatter.format(new Date(time));
	}

	public static boolean getUserIdByPhoneNum(String phoneNum) {
		return phoneNum.matches("^(13|15|18|17)\\d{9}$");
	}

	public static long strGMTToLong(String strGMT, String pat) {

		String pattern = "EEE, dd MMM yyyy HH:mm:ss z";

		if (pat != null) {
			pattern = pat;
		}

		SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.US);

		try {
			Date date = formatter.parse(strGMT);
			return date.getTime();
		} catch (Exception e) {
			return 0;
		}
	}

	public static Gson getGson() {
		if(gson==null){
			gson = new Gson();
		}
		return gson;
	}

	/*
	 * 判断字符串是否为空
	 * 
	 * */
	public static boolean IsEmpty(String strTemp) {
		boolean ret = false;
		if (strTemp == null || strTemp == "" || strTemp.trim() == ""
				|| "null".equals(strTemp)) {
			ret = true;
		}
		return ret;
	}
	
	public static String doGet(String getURL, String charset) {

		StringBuffer buf = new StringBuffer();
		try {
			URL getUrl = new URL(getURL);
			HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
			connection.setRequestProperty("Accept-Charset", charset);
			connection.setConnectTimeout(2000);
			connection.setReadTimeout(2000);
			connection.connect();

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));// 设置编码,否则中文乱码
			String lines;
			while ((lines = reader.readLine()) != null) {
				buf.append(lines);
			}
			connection.disconnect();
			reader.close();
		} catch (Exception e) {
		}
		return buf.toString();
	}
	
	/**
	 * 向指定URL发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */
	public static String sendPost(String url, String param) {
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "close");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 设置 HttpURLConnection的字符编码
	        conn.setRequestProperty("Accept-Charset", "UTF-8");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			OutputStream outputStream = conn.getOutputStream();
			// 发送请求参数
			outputStream.write(param.getBytes("utf-8"));
			// flush输出流的缓冲
			outputStream.flush();
			outputStream.close();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常：");
			e.printStackTrace();
		}finally {// 使用finally块来关闭输出流、输入流
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	
	/**
	 * 根据名称查询Cookie值
	 * @param request 
	 * @param name cookie名称
	 * @return cookie值 没有查询到name对应的cookie则返回null
	 */
	public static String getCookieValue(HttpServletRequest request, String name) {
		String cookieValue = null;

		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}

		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals(name)) {
				return cookies[i].getValue();
			}
		}

		String cookieStr = request.getHeader("cookie");
		String[] cookiess = cookieStr.split(";");
		for (int i = 0; i < cookiess.length; i++) {
			String cookie = cookiess[i].trim();
//			System.out.println("@@@@@@@@@@@@@cookie:"+cookie);
			if (cookie.startsWith(name + "=")) {// 判断的是cookie的名称
				cookieValue = cookie.substring(name.length() + 1);
				break;
			}
		}
		return cookieValue;
	}
	
	
	/**
	 * 设置Cookie
	 * @param request
	 * @param response
	 * @param name
	 * @param value
	 * @param domain
	 * @param maxAge
	 */
	public static void setCookie(HttpServletRequest request,HttpServletResponse response, 
			String name, String value,String domain,int maxAge) {
		Cookie cookie = new Cookie(name, value == null ? "" : value);
		cookie.setMaxAge(maxAge);
		cookie.setPath("/");
		if(!StringUtils.isEmpty(domain)){
			cookie.setDomain(domain);
		}
		response.addCookie(cookie);
	}
	
	
	/**
	 * 给卖家发送模板消息
	 * @param pojo
	 * @return
	 */
	public static String sendMessageToSeller(WxTemplatePojo pojo) { 
		WxTemplateMsg msg = new WxTemplateMsg();
		msg.setTemplate_id(pojo.getTemplateId());
		msg.setTopcolor("#FF0000");
		
		String openid = pojo.getOpenid();//MobileUtils.getWxOpenId(pojo.getUsername());
		msg.setTouser(openid);
		msg.setUrl(pojo.getUrl());
		
		System.out.println(pojo.getUrl());
		
		WxTemplateMsgData data = new WxTemplateMsgData();
		data.setFirst(new First(pojo.getTitle()+"\n", "#282725"));
		data.setKeyword1(new Keyword1(pojo.getK1(), "#173177"));			
		data.setKeyword2(new Keyword2(pojo.getK2(), "#173177"));
		data.setKeyword3(new Keyword3(pojo.getK3(), "#173177")); 
		data.setRemark(new Remark("\n点击详情查看详细内容    ↓↓↓", "#990033"));
		msg.setData(data);

		String accessToken = MobileUtils.getWxAccount(WxConstant.WSC_OPENID);
		if (accessToken != null && !StringUtils.isEmpty(openid)) {
			String sendResult = MobileUtils.sendPost(WxConstant.TEMPLATE_MSG_SEND_URL + accessToken, MobileUtils.getGson().toJson(msg));
			System.out.println("re:"+sendResult);
			
			//统计发送的模版消息
			String sUrl = "http://madata.hc360.com/mobileweb/m/save/templateStat?templateId="+pojo.getTemplateId()+"&openid="+openid+"&type="+pojo.getType();
			MobileUtils.doGet(sUrl, "UTF-8");
			
			return sendResult;
		}
		return null;
	}
	
	public static void main(String[] args) {
		String content = "下班喽!";
		if(content.length()>10){
			content = content.substring(0, 10)+"...";
		}
		//发送模板消息
		Map<String,String> map = new HashMap<String,String>();
		map.put("openid","o8Tw0t3GrGzQ1OFwu5Du-jsVpP5M");//o8Tw0t3GrGzQ1OFwu5Du-jsVpP5M
		map.put("k1",content);
		map.put("k2", DateUtils.getCurrentTime());	
		map.put("k3", "jason139");
		map.put("k4", "jason186");
		map.put("k5", "410630431");
		map.put("type", "9");//买家回复
		
		String s = MobileUtils.sendMessageToSeller(new WxTemplatePojo(map));
		System.out.println(s);
		
	}
}
