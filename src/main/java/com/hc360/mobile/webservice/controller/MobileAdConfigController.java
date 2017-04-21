package com.hc360.mobile.webservice.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hc360.mobile.webservice.pojo.MobileAdConfigData;
import com.hc360.mobile.webservice.pojo.Result;
import com.hc360.mobile.webservice.service.MobileAdConfigService;
import com.hc360.mobile.webservice.service.MobileService;
import com.hc360.mobile.webservice.utils.MobileUtils;
import com.hc360.mobile.webservice.utils.XSSFilterUtils;
/**
 * 公众号的广告接口控制器
 *
 */
@Controller
public class MobileAdConfigController {

	@Resource
	private MobileAdConfigService mobileAdService;

	@Resource
	private MobileService mobileService;
	
	@RequestMapping(value = "/m/get/Ad114", method = RequestMethod.GET)
	public void handleGet114AdConfig(@RequestParam("type") int type, @RequestParam("kind") int kind,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String  ad114Config = mobileAdService.getAd114ConfigToGsonString(type, kind);
		byte[] b = ad114Config.getBytes();
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Last-Modified", MobileUtils.longToGMTStr(new Date().getTime(), null));
		response.setContentLength(b.length);
		response.getOutputStream().write(b);
		response.flushBuffer();
		
	}

	@RequestMapping(value = "/m/get/Ad", method = RequestMethod.GET)
	public void handleGetMobileAd(@RequestParam("type") int type, @RequestParam("kind") int kind,
			@RequestParam("mainind") String mainind, HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		Result result = new Result();
		if (type <= 0 || kind <= 0 || MobileUtils.IsEmpty(mainind) == true) {
			result.setCode(-1);
			result.setReturnMsg("参数不能为空，请检查！");
		} else {
			List<MobileAdConfigData> data = new ArrayList<MobileAdConfigData>();
			String lm = request.getHeader("If-Modified-Since");

			long updatetime = MobileUtils.strGMTToLong(lm, null);
			if (mobileAdService.getMobileAdUpdate(type, kind, mainind, updatetime) > 0) {
				data = mobileAdService.getMobileAdData(type, kind, mainind);

				if (data.size() > 0) {
					result.setCode(200);
					result.setAdData(data);
				} else {
					result.setCode(-1);
				}
				response.setStatus(HttpServletResponse.SC_OK);
			} else if (updatetime == 0) {
				result.setCode(200);
				result.setAdData(data);
				response.setStatus(HttpServletResponse.SC_OK);
			} else {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			}
		}

		byte[] b = MobileUtils.getGson().toJson(result).getBytes();
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Last-Modified", MobileUtils.longToGMTStr(new Date().getTime(), null));
		response.setContentLength(b.length);
		response.getOutputStream().write(b);
		response.flushBuffer();
	}

	public String pcPageToWebPageByBaidu(String url) throws Exception {

		String responseStr = MobileUtils.doGet(url,"UTF-8");

		Document doc = Jsoup.parse(responseStr);

		if (doc.select(".trans").first() == null) {
			return null;
		} else {
			return responseStr;
		}
	}

	public String parseBaiduReturnPage(String responseStr) {
		if (responseStr == null) {
			return "";
		}
		Document doc = Jsoup.parse(responseStr);
		try {
			Element header = doc.select(".trans").first();
			header.remove();
		} catch (Exception e) {
		}
		try {
			Element footer = doc.select(".footer").first();
			footer.remove();
		} catch (Exception e) {
		}
		try {
			Element head = doc.select(".c1").first();
			head.remove();
		} catch (Exception e) {
		}

		try {
			Elements links = doc.select("a");
			for (Element link : links) {
				link.remove();
			}
		} catch (Exception e) {
		}
		Element div = doc.select("div").first();
		StringBuffer sb = new StringBuffer();
		sb.append(div.toString());
		return sb.toString().replaceAll("alt=\"img\"", "alt=\"img\" style=\"width:100%\"");
	}
	
	public class ReturnMsg{
		private String msg;

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	@RequestMapping(value = "/mobile/pagewx", method = RequestMethod.GET)
	public void mobilepagewx(@RequestParam("bcid") String bcid, HttpServletResponse response,HttpServletRequest request) throws IOException{
		if(bcid != null){
			String info = mobileService.getProdInfo(bcid);
			
			info = XSSFilterUtils.cleanSimpleXSS(info);
			
			String callback = request.getParameter("callback");
			ReturnMsg retMsg = new ReturnMsg();
			retMsg.setMsg(info);
			String ret = MobileUtils.getGson().toJson(retMsg);
			if(callback != null){
				ret = callback+"("+ret+")";
			}
			byte[] b = ret.getBytes("gbk");
			response.setContentType("text/html;charset=gbk");
			response.setContentLength(b.length);
			response.getOutputStream().write(b);
			response.flushBuffer();
		}
	}
	
	@RequestMapping(value = "/mobile/page", method = RequestMethod.GET)
	public void mobilepage(@RequestParam("detail") String detail, HttpServletResponse response,HttpServletRequest request) throws Exception {
		
		if(detail == null || detail.isEmpty()){
			return;
		}
		
		String aa = URLDecoder.decode(detail,"UTF-8");
		
		String bcid = "";
		
		String [] params = aa.split("[?]");
		String [] param = params[1].split("&");
		for(String pa : param){
			if(pa.contains("bcid")){
				bcid = pa.split("=")[1];
				break;
			}
		}		
		
		String info = mobileService.getProdInfo(bcid);
		//String strURL = "http://gate.baidu.com/tc?from=opentc&src=" + detail;
		
//		String detailUrl = "http://detail.b2b.hc360.com/detail/turbine/template/moblie,vmoblie,getproduct_ultimate.html?id="+bcid;
//		System.out.println(detailUrl);
//		
//		String cc = mobileService.doGet(detailUrl,"GBK");
//		
//		System.out.println(cc);		
//		ProductInfo dd = new ProductInfo();
//		dd = new Gson().fromJson(cc, ProductInfo.class);
//		
//		if(dd.getDetailInfo() == null){
//			return;
//		}
		
		/*String responseStr = this.pcPageToWebPageByBaidu(strURL);

		for (int i = 0; i < 3; i++) {
			if (responseStr == null) {
				responseStr = this.pcPageToWebPageByBaidu(strURL);
			} else {
				break;
			}
		}
		byte[] b = this.parseBaiduReturnPage(responseStr).getBytes("gbk");*/
		String ee = "<style>.yuanbbcss img(width:100%;hight:100%)</style><div class=\"yuanbbcss\">"+info+"</div>";
		String callback = request.getParameter("callback");
		if(callback != null){
			ee = callback+"("+ee+")";
		}
		byte[] b = ee.getBytes("gbk");
		response.setContentType("text/html;charset=gbk");
		response.setContentLength(b.length);
		response.getOutputStream().write(b);
		response.flushBuffer();
	}

	@RequestMapping(value = "/mobile/activity", method = RequestMethod.GET)
	public void getMobileActivity(@RequestParam("industry") String industry,
			@RequestParam(required = false, value = "callback") String callback, HttpServletResponse response)
			throws IOException {		
		String result = mobileService.getActivityData(industry);
		if (callback != null && !callback.equals("")) {
			result = callback + "(" + result + ")";
		}
		byte[] b = result.getBytes();
		response.setContentType("text/html;charset=utf8");
		response.setContentLength(b.length);
		response.getOutputStream().write(b);
		response.flushBuffer();
	}

	@RequestMapping(value = "/mobile/app/getshare", method = RequestMethod.GET)
	public void getAppShareTimesData(@RequestParam(required = true, value = "userid") String userId,
			@RequestParam(required = true, value = "bcids") String bcIds,
			HttpServletResponse response) throws IOException {
		String result = mobileAdService.getAppShareBcIdData(userId,bcIds);
		byte[] b = result.getBytes();
		response.setContentType("text/html;charset=utf8");
		response.setContentLength(b.length);
		response.getOutputStream().write(b);
		response.flushBuffer();

	}
	
	@RequestMapping(value = "/mobile/app/saveshare", method = RequestMethod.GET)
	public void saveAppShareTimesData(@RequestParam(required = true, value = "userid") String userId,
			@RequestParam(required = true, value = "bcid") String bcId,
			HttpServletResponse response) throws IOException {
		String result = mobileAdService.saveAppShareBcIdData(userId, bcId);
		byte[] b = result.getBytes();
		response.setContentType("text/html;charset=utf8");
		response.setContentLength(b.length);
		response.getOutputStream().write(b);
		response.flushBuffer();
	}
	
	
	/*
	 * 插入式搜索
	 * */
	@RequestMapping(value = "/mobile/insertsearch", method = RequestMethod.GET)
	public void getMobileInsertSearch(@RequestParam("k") String k,
			@RequestParam(required = false, value = "callback") String callback, HttpServletResponse response)
			throws IOException {
		String result = mobileAdService.getInsertSearchKeyword(k);
		if (callback != null && !callback.equals("")) {
			result = callback + "(" + result + ")";
		}
		byte[] b = result.getBytes();
		response.setContentType("text/html;charset=utf8");
		response.setContentLength(b.length);
		response.getOutputStream().write(b);
		response.flushBuffer();
	}
	
	
	/*
	 * 定制行业 与 行业专区
	 * */
	@RequestMapping(value = "/mobile/mainindInfo", method = RequestMethod.GET)
	public void getMobileMainInduInfo(@RequestParam("type") String type,
			@RequestParam("kind") String kind,
			@RequestParam(required = false, value = "callback") String callback, HttpServletResponse response)
			throws IOException {
		String result = mobileAdService.getMobileMainIndInfo(type, kind);
		if (callback != null && !callback.equals("")) {
			result = callback + "(" + result + ")";
		}
		byte[] b = result.getBytes();
		response.setContentType("text/html;charset=utf8");
		response.setContentLength(b.length);
		response.getOutputStream().write(b);
		response.flushBuffer();
	}
	
	/*
	 * 获取采购通其他行业模块信息
	 * */
	@RequestMapping(value = "/get/mobile/otherAdInfo", method = RequestMethod.GET)
	public void getMobileOtherAdInfo(@RequestParam("type") int type,
			@RequestParam("mainind") String mainind,
			@RequestParam(required = false, value = "callback") String callback, HttpServletResponse response)
			throws IOException {
		String result = mobileAdService.getMobileOtherAdData(type, mainind);
		if (callback != null && !callback.equals("")) {
			result = callback + "(" + result + ")";
		}
		byte[] b = result.getBytes();
		response.setContentType("text/html;charset=utf8");
		response.setContentLength(b.length);
		response.getOutputStream().write(b);
		response.flushBuffer();
	}
}
