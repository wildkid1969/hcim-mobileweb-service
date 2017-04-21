package com.hc360.mobile.webservice.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hc360.mobile.webservice.pojo.ResultMsg;
import com.hc360.mobile.webservice.pojo.WxUserFeedBack;
import com.hc360.mobile.webservice.service.UserFeedBackService;
import com.hc360.mobile.webservice.utils.MobileUtils;

/**
 * 用户询盘反馈评价控制器
 * @author lvyuxue
 *
 */
@RequestMapping(value="feedBack")
@Controller
public class UserFeedBackController {
	//注入service
	@Resource
	private UserFeedBackService userFeedBackService;
	/**
	 * 保存商机订阅的模板消息中用户的询盘访问信息
	 * @param sellerId 用户名
	 * @param openId 用户openid
	 * @param labelNames 选择的标签（数组形式）
	 * @param satisfied 满意度
	 * @param userContent 用户评价
	 * @param product 采购产品
	 * @param company 公司名称
	 * @param phone 买家联系方式
	 * @param endTime 采购截止时间
	 * @param type 类型 1、用户反馈评价 2、用户留言 3、商机订阅  4、买家公众号买家留言
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="saveFeedBack")
	@ResponseBody
	public String saveUserFeedBack(HttpServletRequest request,HttpServletResponse response,String callback,String sellerId,String openId,
			String[] labelNames,String satisfied,String userContent,String product,String company,String phone,String endTime,int type) throws UnsupportedEncodingException{
		ResultMsg rm = new ResultMsg();
		WxUserFeedBack feedBack = new WxUserFeedBack();
		String stype = String.valueOf(type);
		//判断该用户是否已经评价
		if(!sellerId.isEmpty() && !"".equals(openId) && !stype.isEmpty() ){
			
			int flag = userFeedBackService.feedBackInfo(sellerId, openId,type);
			if(flag>0){
				rm.setMsg("您已经评价过了，请不要重复评价");
				rm.setCode(100);
			}else{
				if(type == 1){//用户询盘反馈信息
					feedBack.setUserName(URLDecoder.decode(sellerId, "utf-8"));
					feedBack.setCompany(URLDecoder.decode(company,"utf-8"));
					feedBack.setProduct(URLDecoder.decode(product,"utf-8"));
				}else if(type == 2){//买家留言信息
					feedBack.setMsgId(URLDecoder.decode(sellerId, "utf-8"));
					feedBack.setTheme(URLDecoder.decode(company,"utf-8"));
					feedBack.setMessage(URLDecoder.decode(product,"utf-8"));
				}else if(type ==3){//商机订阅信息
					feedBack.setBcid(URLDecoder.decode(sellerId, "utf-8"));
					feedBack.setCompany(URLDecoder.decode(company,"utf-8"));
					feedBack.setProduct(URLDecoder.decode(product,"utf-8"));
				}else if(type ==4){//买家公众号买家留言
					feedBack.setMsgId(URLDecoder.decode(sellerId, "utf-8"));
					feedBack.setCompany(URLDecoder.decode(company,"utf-8"));
					feedBack.setProduct(URLDecoder.decode(product,"utf-8"));
				}else if(type ==5){//买家公众号买家留言
					feedBack.setBcid(URLDecoder.decode(sellerId, "utf-8"));
					feedBack.setCompany(URLDecoder.decode(company,"utf-8"));
					feedBack.setProduct(URLDecoder.decode(product,"utf-8"));
				}
				feedBack.setType(type);
				feedBack.setTime(URLDecoder.decode(endTime,"utf-8"));
				feedBack.setRosterId(openId);
				feedBack.setPhone(URLDecoder.decode(phone,"utf-8"));
				feedBack.setSatisfied(URLDecoder.decode(satisfied,"utf-8"));
				feedBack.setUserContent(URLDecoder.decode(userContent,"utf-8"));
				String str1="";
				for(int i=0;i<labelNames.length;i++){
				    str1+=labelNames[i];
				}
				System.out.println(URLDecoder.decode(str1,"utf-8"));
				feedBack.setLabelName(URLDecoder.decode(str1,"utf-8"));
				int result = userFeedBackService.saveUserFeedBack(feedBack);
				if(result >0){
					//保存成功
					rm.setCode(400);
					rm.setMsg("评价反馈成功");
				}else{
					//保存失败
					rm.setCode(300);	
					rm.setMsg("评价反馈失败");
				}
			}
		}
		if(callback != null){
			return callback + "(" + MobileUtils.getGson().toJson(rm) + ")";
		}
		return MobileUtils.getGson().toJson(rm);
	}
}
