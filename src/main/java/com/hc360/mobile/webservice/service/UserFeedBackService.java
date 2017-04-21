package com.hc360.mobile.webservice.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hc360.mobile.webservice.dao.MobileDao;
import com.hc360.mobile.webservice.pojo.WxUserFeedBack;

/**
 * 用户询盘反馈信息的service
 * @author lvyuxue
 * 2016年10月9日14:32:59
 */
@Service
public class UserFeedBackService {
	//注入dao
	@Resource
	private MobileDao mobiledao;
	/**
	 * 保存用户的询盘反馈评价
	 */
	
	public int saveUserFeedBack(WxUserFeedBack feedBack){
		return mobiledao.saveFeedBack(feedBack);
	}
	/**
	 * 判断用户是否已经评价
	 * @param sellerId
	 * @param openId
	 * @return
	 */
	public int feedBackInfo(String sellerId,String openId,int type){
		return mobiledao.feedBackInfo(sellerId,openId,type);
	}
}
