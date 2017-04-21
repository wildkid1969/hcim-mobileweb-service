package com.hc360.mobile.webservice.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hc360.mobile.webservice.dao.MobileAdConfigDao;
import com.hc360.mobile.webservice.pojo.Ad114Config;
import com.hc360.mobile.webservice.pojo.AppShareBcIdNum;
import com.hc360.mobile.webservice.pojo.InsertSearchAppResult;
import com.hc360.mobile.webservice.pojo.MobileAdConfigData;
import com.hc360.mobile.webservice.pojo.MobileAdModule;
import com.hc360.mobile.webservice.pojo.Result;
import com.hc360.mobile.webservice.utils.MobileUtils;

@Service
public class MobileAdConfigService {

	Logger logger = Logger.getLogger(MobileAdConfigService.class);

	@Resource
	private MobileAdConfigDao mobileAdConfigDao;

	public List<MobileAdConfigData> getMobileAdData(int type, int kind, String mainind) {
		return mobileAdConfigDao.getMobileAdData(type, kind, mainind);
	}

	public int getMobileAdUpdate(int type, int kind, String mainind, long updatetime) {
		return mobileAdConfigDao.getUpdateMobileAdData(type, kind, mainind, updatetime);
	}

	public String getAppShareBcIdData(String userid, String bcIds) {
		List<AppShareBcIdNum> shareTimes = new ArrayList<AppShareBcIdNum>();
		if (bcIds.indexOf(",") < 0) {
			shareTimes.add(mobileAdConfigDao.getAppShareData(userid, bcIds.trim()));
		} else {
			for (String bcId : bcIds.trim().split(",")) {
				shareTimes.add(mobileAdConfigDao.getAppShareData(userid, bcId.trim()));
			}
		}
		Result result = new Result();
		if (shareTimes != null && shareTimes.size() > 0) {
			result.setCode(200);
			result.setUserid(userid);
			result.setShareData(shareTimes);
		} else {
			result.setCode(-1);
			result.setReturnMsg("暂时没有分享数据");
		}
		return MobileUtils.getGson().toJson(result);
	}

	public String saveAppShareBcIdData(String userid, String bcId) {
		AppShareBcIdNum a = new AppShareBcIdNum();
		a.setUserid(userid.trim());
		a.setBcid(bcId.trim());
		AppShareBcIdNum sa = mobileAdConfigDao.getAppShareData(userid.trim(), bcId.trim());
		Result result = new Result();
		List<AppShareBcIdNum> shareTimes = new ArrayList<AppShareBcIdNum>();
		int empty = 0;
		if (sa == null) {
			empty = 0;
			a.setSharetimes(1);
		} else {
			empty = 1;
			a.setSharetimes(sa.getSharetimes() + 1);
		}
		a.setTime(String.valueOf(MobileUtils.getNowTimeLong()));
		int ret = mobileAdConfigDao.saveAppShareDate(a, empty);
		if (ret == 1) {
			result.setCode(200);
			shareTimes.add(a);
			result.setShareData(shareTimes);
		} else {
			result.setCode(-1);
			result.setReturnMsg("保存数据失败，具体原因联系开发人员!");
		}
		return MobileUtils.getGson().toJson(result);

	}

	public String getMobileOtherAdData(int type, String mainind) {
		List<MobileAdConfigData> ll = mobileAdConfigDao.getMobileOtherAdData(type, mainind);
		List<MobileAdModule> result = new ArrayList<MobileAdModule>();
		MobileAdModule m = new MobileAdModule();
		for (MobileAdConfigData l : ll) {
			if (m.getModulename() == null) {
				m.setModulename(l.getModulename());
				m.setLevel(l.getLevel());
			}
			if (m.getModulename() != null && m.getModulename().equals(l.getModulename())) {
				m.getLs().add(l);
			} else {
				result.add(m);
				m = new MobileAdModule();
				m.setModulename(l.getModulename());
				m.setLevel(l.getLevel());
				m.getLs().add(l);
			}
		}
		result.add(m);
		return MobileUtils.getGson().toJson(result);
	}

	public String getInsertSearchKeyword(String keyword) {
		InsertSearchAppResult result = mobileAdConfigDao.getInsertSearchResult(keyword);
		if (result == null) {
			return "";
		}
		return MobileUtils.getGson().toJson(result);
	}

	public String getMobileMainIndInfo(String type, String kind) {
		try {
			return MobileUtils.getGson().toJson(mobileAdConfigDao.getMobileMainInduInfo(type, kind));
		} catch (Exception e) {
			return "";
		}
	}
	
	public String getAd114ConfigToGsonString(int type, int kind){
		List<Ad114Config> list = mobileAdConfigDao.getAd114ConfigList(type, kind);
		if(list != null && list.size() > 0){
			return MobileUtils.getGson().toJson(list.get(0));
		}else{
			return MobileUtils.getGson().toJson(new Ad114Config());
		}
	}

}
