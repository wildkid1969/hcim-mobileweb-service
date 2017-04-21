package com.hc360.mobile.webservice.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hc360.im.wxservice.common.WxConstant;
import com.hc360.mobile.webservice.pojo.MobileWxRosterOpportunity;
import com.hc360.mobile.webservice.pojo.ResultMsg;
import com.hc360.mobile.webservice.pojo.WxTemplatePojo;
import com.hc360.mobile.webservice.service.MobileService;
import com.hc360.mobile.webservice.utils.DateUtils;
import com.hc360.mobile.webservice.utils.MD5Utils;
import com.hc360.mobile.webservice.utils.MobileUtils;

/**
 * 商机订阅控制器
 * 
 * @author HC360
 *
 */
@Controller
@RequestMapping(value="opt")
public class OpportunityController {

	@Resource
	private MobileService mobileService;

	/**
	 * 商机列表
	 * @param openid
	 * @param callback
	 * @return
	 */
	@RequestMapping(value="subscribeList")
	@ResponseBody
	public String subscribeList(String openid, String callback){
		ResultMsg rm = new ResultMsg();
		if(StringUtils.isEmpty(openid)){
			rm.setCode(100);
			rm.setMsg("参数错误");
		}else{
			List<MobileWxRosterOpportunity> list = mobileService.getRosterOpportunityList(openid);
			rm.setCode(200);
			rm.setData(list);
		}

		if(callback != null){
			return callback + "(" + MobileUtils.getGson().toJson(rm) + ")";
		}
		return MobileUtils.getGson().toJson(rm);
	}

	/**
	 * 提交订阅信息
	 * @param opt
	 * @param callback
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="subscribe")
	@ResponseBody
	public String subscribe(HttpServletRequest request,MobileWxRosterOpportunity opt, String callback) throws UnsupportedEncodingException{
		ResultMsg rm = new ResultMsg();
		if(opt!=null && opt.getLimit_count()!=null){
			int limit = opt.getLimit_count();
			if(limit<5 || limit>20){
				opt.setLimit_count(5);
			}
		}
		
		if(MobileUtils.requestParamCount(request)<2){
			rm.setCode(600);
			rm.setMsg("您提交的信息不完整！");
			
			if(callback != null){
				return callback + "(" + MobileUtils.getGson().toJson(rm) + ")";
			}
			return MobileUtils.getGson().toJson(rm);
		}

		String imid = mobileService.getImidByOpenid(opt.getOpenid());

		if(StringUtils.isEmpty(imid)){
			rm.setCode(300);
			rm.setMsg("您还没有绑定账号！");
		}else if(StringUtils.isEmpty(opt.getKeyword())){
			rm.setCode(100);
			rm.setMsg("请填写关键词！");
		}else{
			opt.setKeyword(URLDecoder.decode(opt.getKeyword(), "utf-8"));
			if(!StringUtils.isEmpty(opt.getProvince()) && !StringUtils.isEmpty(opt.getCity())){
				opt.setProvince(URLDecoder.decode(opt.getProvince(), "utf-8"));
				opt.setCity(URLDecoder.decode(opt.getCity(), "utf-8"));
			}
			
			//是否重复
			MobileWxRosterOpportunity oldOpt = mobileService.getRosterOpportunityByOpenidAndKey(opt.getOpenid(), opt.getKeyword());
			if(oldOpt==null){
				//是否超限
				List<MobileWxRosterOpportunity> optList = mobileService.getRosterOpportunityList(opt.getOpenid());
				if(!optList.isEmpty() && optList.size()>=10){
					rm.setCode(500);
					rm.setMsg("您订阅的关键词已经足够多了！");
					rm.setData(opt);
				}else{
					mobileService.insertRosterOpportunity(opt);
					
					opt = mobileService.getRosterOpportunityByOpenidAndKey(opt.getOpenid(), opt.getKeyword());
					rm.setCode(200);
					rm.setData(opt);
					rm.setMsg("订阅成功！");
				}
				
			}else{
				rm.setCode(400);
				rm.setMsg("您已经订阅过这个关键词了！");
			}
			
		}

		if(callback != null){
			return callback + "(" + MobileUtils.getGson().toJson(rm) + ")";
		}
		return MobileUtils.getGson().toJson(rm);
	}

	/**
	 * 通过ID取得订阅商机信息
	 * @param id
	 * @param callback
	 * @return
	 */
	@RequestMapping(value="getSubscribeByID")
	@ResponseBody
	public String getSubscribeByID(String id, String callback){
		ResultMsg rm = new ResultMsg();
		
		if(StringUtils.isEmpty(id)){
			rm.setCode(100);
			rm.setMsg("参数错误.");
		}else{
			MobileWxRosterOpportunity opt = mobileService.getRosterOpportunityById(id);
			rm.setCode(200);
			rm.setData(opt);
		}

		if(callback != null){
			return callback + "(" + MobileUtils.getGson().toJson(rm) + ")";
		}
		return MobileUtils.getGson().toJson(rm);
	}

	/**
	 * 保存编辑的内容
	 * @param opt
	 * @param callback
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="save")
	@ResponseBody
	public String saveOpt(HttpServletRequest request,HttpServletResponse response,MobileWxRosterOpportunity opt, String callback) throws UnsupportedEncodingException{
		ResultMsg rm = new ResultMsg();
		if(opt!=null && opt.getLimit_count()!=null){
			int limit = opt.getLimit_count();
			if(limit<5 || limit>20){
				opt.setLimit_count(5);
			}
		}

		if(StringUtils.isEmpty(opt.getKeyword())){
			rm.setCode(100);
			rm.setMsg("关键词必填.");
		}else if(opt.getId() == 0 || StringUtils.isEmpty(opt.getOpenid())){
			rm.setCode(100);
			rm.setMsg("参数错误.");
		}else{
			opt.setKeyword(URLDecoder.decode(opt.getKeyword(), "utf-8"));
			if(!StringUtils.isEmpty(opt.getProvince()) && !StringUtils.isEmpty(opt.getCity())){
				opt.setProvince(URLDecoder.decode(opt.getProvince(), "utf-8"));
				opt.setCity(URLDecoder.decode(opt.getCity(), "utf-8"));
			}
			
			if(MobileUtils.requestParamCount(request)<2){
				rm.setCode(600);
				rm.setMsg("您提交的信息不完整！");
				
				if(callback != null){
					return callback + "(" + MobileUtils.getGson().toJson(rm) + ")";
				}
				return MobileUtils.getGson().toJson(rm);
			}
			
			//是否重复
			MobileWxRosterOpportunity oldOpt = mobileService.getRosterOpportunityByOpenidAndKey(opt.getOpenid(), opt.getKeyword());
			if(oldOpt==null){
				mobileService.updateRosterOpportunityByParam(opt);
				rm.setCode(200);
				rm.setData(opt);
			}else{
				rm.setCode(700);
				rm.setMsg("您已经订阅过这个关键词了！");
			}
			
			
		}

		if(callback != null){
			return callback + "(" + MobileUtils.getGson().toJson(rm) + ")";
		}
		return MobileUtils.getGson().toJson(rm);
	}
	
	
	/**
	 * 删除订阅的关键词
	 * @param opt
	 * @param callback
	 * @return
	 */
	@RequestMapping(value="delete")
	@ResponseBody
	public String deleteOpt(HttpServletResponse response,String id,String openid, String callback){
		ResultMsg rm = new ResultMsg();

		if(StringUtils.isEmpty(id) || StringUtils.isEmpty(openid)){
			rm.setCode(100);
			rm.setMsg("参数错误！");
		}else{
			mobileService.deleteRosterOpportunityById(id, openid);
			rm.setCode(200);
			rm.setMsg("删除成功！");
		}

		if(callback != null){
			return callback + "(" + MobileUtils.getGson().toJson(rm) + ")";
		}
		return MobileUtils.getGson().toJson(rm);
	}
	
	

	/**
	 * 定时发送商机
	 * 每天按照四个时段推送    9点   12点  15点 18点   不得重复发送消息   只发当天消息
	 * 0 0 9,12,15,18 * * ?
	 * @throws UnsupportedEncodingException 
	 */
//	@RequestMapping(value="sendMsg")
	@Scheduled(cron = "0 0 9,12,15,18 * * ?")
	public void doMessage() throws UnsupportedEncodingException {
		//查询所有有发送量的用户openid
		List<String> openidList = mobileService.getRosterOpportunityOpenidList();
		if(!openidList.isEmpty()){
			Map<String,String> map = new HashMap<String,String>();
			int limit = 0;
			int isUsedFree = 0;
			String province = "";
			String city = "";
			String zone = "";
			String openid = "";
			for(int k=0,z=openidList.size();k<z;k++){
				//查询用户订阅信息
				openid = openidList.get(k);
				List<MobileWxRosterOpportunity> list = mobileService.getRosterOpportunityList(openid);
				
				if(!list.isEmpty()){
					//获取当前几点钟 各个时间段查询的页码不一样
					Calendar can = Calendar.getInstance();
					int hour = can.get(Calendar.HOUR_OF_DAY);
					int page = 0;
					if(hour<=9){
						page = 0;
					}else if(hour<=12 && hour>9){
						page = 1;
					}else if(hour<=15 && hour>12){
						page = 2;
					}else if(hour<=18 && hour>15){
						page = 3;
					}else{
						page = 4;
					}
					
					//是否免费看过
					isUsedFree = list.get(0).getIs_used_free();
					
					List<WxTemplatePojo> pojoList = new ArrayList<WxTemplatePojo>();
					List<String> bcIdList = new ArrayList<String>();
					
					//遍历用户的订阅信息
					for(int i=0;i<list.size();i++){
						//限制发送条数 以防篡改
						limit = list.get(i).getLimit_count();
						if(limit<5 || limit>20){
							limit = 5;
						}
						
						//拼接查找地区
						province = list.get(i).getProvince();
						city = list.get(i).getCity();
						if(!StringUtils.isEmpty(province) && !province.equals("0")){
							//去掉直辖市的市
							if(province.equals(city) && province.endsWith("市")){
								zone = "中国:"+province.substring(0, province.length()-1);
							}else{
								zone = "中国:"+province+":"+city;
							}
						}else{
							zone = "中国";
						}
						
						//获取商机信息
						String url = "http://s.hc360.com/getmmtlast.cgi?c=求购信息&w="+list.get(i).getKeyword()+"&sys=mobileWsc&bus=subscribeOpt"
								+ "&n="+limit*page+"&e="+limit+"&z="+zone;
						String result = MobileUtils.doGet(url, "gbk");
						
						if(!StringUtils.isEmpty(result)){
							JSONObject jsonObject = JSONObject.parseObject(result);
							JSONArray infoArr = JSONObject.parseArray(jsonObject.getString("searchResultInfo"));
							
							//遍历接口返回的商机数组并组装信息，然后逐条发送
							for(int j=0,s=infoArr.size(); j<s; j++){
								//组装商机信息
								String title = infoArr.getJSONObject(j).getString("searchResultfoTitle");
								String pubTime = infoArr.getJSONObject(j).getString("searchResultfoPublicTime");
								String bcId = infoArr.getJSONObject(j).getString("searchResultfoId");
								if(!StringUtils.isEmpty(pubTime) && DateUtils.isToday(pubTime)){//只发布当天的商机
									if (!StringUtils.isEmpty(title) && title.length()>50) {
										title=title.substring(0,50)+"...";
									}
									
									map.clear();
									map.put("openid",openid);
									map.put("k1",title);
									map.put("k2", pubTime);	
									map.put("k3", bcId);
									if(isUsedFree==0 && j<3){//前三条用免费加密方式
										map.put("k4", MD5Utils.sign(openid+bcId+"123", WxConstant.MD5_KEY, "utf-8"));
										if(j==2){
											mobileService.updateRosterOpportunityIsUsedFreeByParam(openid);
										}
									}else{
										map.put("k4", MD5Utils.sign(openid+bcId, WxConstant.MD5_KEY, "utf-8"));
									}
									map.put("type", "6");//标注商机订阅

									//发送商机给卖家
//									sendMessage(new WxTemplatePojo(map));
									
									if(!bcIdList.contains(bcId)){
										pojoList.add(new WxTemplatePojo(map));
										bcIdList.add(bcId);
									}
								}
								
							}
							
						}
						
					}
					//发送商机给卖家
					for(WxTemplatePojo pojo:pojoList){
						MobileUtils.sendMessageToSeller(pojo);
					}
					
				}
			}
		}
		
		
		
	} 
	
	

	/**
	 * 通过bcid获取商机详情
	 * @param request
	 * @param bcid 商机id
	 * @param sign md5加密
	 * @param callback
	 * @return
	 */
	@RequestMapping(value="optInfo")
	@ResponseBody
	public String getQgInfo(HttpServletRequest request,String bcid,String openid,String sign, String callback){
		//超过限制条数后的加密串
		boolean isSign = MD5Utils.verify(openid+bcid, sign, WxConstant.MD5_KEY, "utf-8");
		//未超过限制的加密串
		boolean isFreeSign = MD5Utils.verify(openid+bcid+"123", sign, WxConstant.MD5_KEY, "utf-8");
		
		String result = "";
		
		if(isSign || isFreeSign){
			//查看商机详情
			String url = "http://168.mobile.hc360.com/mobileaccount/call/qginfo?bcid="+bcid;
			result = MobileUtils.doGet(url, "UTF-8");
			
			//查看商机类型 8：抓去而来  此类型重新获取手机号
			String getTypeUrl = "http://detail.b2b.hc360.com/detail/turbine/action/ajax.MobileBcidAjaxAction?bcid="+bcid;
			String typeResult = MobileUtils.doGet(getTypeUrl, "UTF-8");
			if(!StringUtils.isEmpty(typeResult)){
				net.sf.json.JSONObject typeResultJson = net.sf.json.JSONObject.fromObject(typeResult);
				String type = typeResultJson.getString("pubtype");
				String userPhone = typeResultJson.getString("phone_num");
				if(type.equals("8")){
					@SuppressWarnings("unchecked")
					Map<String,String> map = JSONObject.parseObject(result,Map.class);
					map.put("userPhone",userPhone);//改变值
					map.put("telphone",userPhone);//改变值
					result = MobileUtils.getGson().toJson(map);//重新转成json字符串
				}
			}
			
			//获取用户会员级别（0普通会员 1买家会员 2大买家会员 3试用买卖通会员 4买卖通会员 5VIP会员 6银牌会员 7金牌会员 8白金会员）
			String imid = mobileService.getImidByOpenid(openid);
			String getUserInfoUrl = "http://openapi.m.hc360.com/openapi/v1/company/getInfo/"+imid;
			String infoResult = MobileUtils.doGet(getUserInfoUrl, "UTF-8");
			
			if(!StringUtils.isEmpty(infoResult)){
				net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(infoResult);
				
				String level = json.getString("level");
				
				if(!isFreeSign && (StringUtils.isEmpty(level) || level.equals("null") ||level.equals("0") || level.equals("1"))){
					@SuppressWarnings("unchecked")
					Map<String,String> map = JSONObject.parseObject(result,Map.class);
					map.put("userPhone","***");//改变值
					map.put("telphone","***");//改变值
					result = MobileUtils.getGson().toJson(map);//重新转成json字符串
				}
			}
			
			
			//统计模板消息点击次数(type模版类型 1 新订单 2 询盘宝 3 标王 4 周报 5留言 6商机订阅)
			String sUrl = "http://madata.hc360.com/mobileweb/m/save/temaplateClickStat?openid="+openid+"&type=6";
			MobileUtils.doGet(sUrl, "UTF-8");
			
			
			if(callback != null){
				return callback + "(" + result + ")";
			}
			
		}
		return result;
		
	}
	
}