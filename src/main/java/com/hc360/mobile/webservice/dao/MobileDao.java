package com.hc360.mobile.webservice.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hc360.mobile.webservice.pojo.Activity;
import com.hc360.mobile.webservice.pojo.ActivityElem;
import com.hc360.mobile.webservice.pojo.ImUserInfo;
import com.hc360.mobile.webservice.pojo.MobileWxMenuStat;
import com.hc360.mobile.webservice.pojo.MobileWxRosterOpportunity;
import com.hc360.mobile.webservice.pojo.UserInfo;
import com.hc360.mobile.webservice.pojo.WxAppInfo;
import com.hc360.mobile.webservice.pojo.WxUserFeedBack;

@Repository
public class MobileDao {
	@Resource(name = "archive1JdbcTemplate")
	private JdbcTemplate archiveJdbcTemplate;

	@Resource(name = "mobileReadJdbcTemplate")
	private JdbcTemplate mobileReadTemplate;

	@Resource(name = "mobileWriteJdbcTemplate")
	private JdbcTemplate mobileWriteTemplate;

	public int saveActivityMobile(ActivityElem v) {
		String sql = "insert into mobile_activity (`activityId`,`bcId`,`activityName`,`promotionType`,`basicType`,`price`,`limitNum`,`markWord`,`checked`,`startTime`,`endTime`,`oldprice`,`imgurl`,`ptitle`) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int ret = mobileWriteTemplate.update(
				sql,
				new Object[] { v.getActivityId(), v.getBcId(),
						v.getActivityName(), v.getPromotionType(),
						v.getBasicType(), v.getPrice(), v.getLimitNum(),
						v.getMarkWord(), v.getChecked(),
						v.getStartTime(), v.getEndTime(), v.getOldprice(),v.getImgUrl(),v.getPtitle()});
		return ret;
	}
	
	public void updateActivityMobileStatus(String bcid, String checkStatus){
		String sql = "update mobile_activity set `checked` = ? where bcId = ?";
		mobileWriteTemplate.update(sql, new Object[]{checkStatus,bcid});
	}
	
	public List<Activity> getBusinProActivityVO(String industry){
		String sql = "select `activityId`,`bcId`,`activityName`,`promotionType`,`basicType`,`price`,`limitNum`,`startTime`,`endTime`,`oldprice`,`imgurl`,`ptitle` from mobile_activity where checked = '1' and mainind like '%"+industry+"%' order by `promotionType`";
		List<Activity> ls = new ArrayList<Activity>();
		List<ActivityElem> ll = mobileReadTemplate.query(sql, new RowMapper<ActivityElem>(){
			@Override
			public ActivityElem mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ActivityElem a = new ActivityElem();
				a.setActivityId(rs.getString(1));
				a.setBcId(rs.getString(2));
				a.setActivityName(rs.getString(3));
				a.setPromotionType(rs.getString(4));
				a.setBasicType(rs.getString(5));				
				a.setPrice(rs.getString(6));
				a.setLimitNum(rs.getInt(7));
				a.setStartTime(rs.getTimestamp(8));
				a.setEndTime(rs.getTimestamp(9));
				a.setOldprice(rs.getString(10));
				a.setImgUrl(rs.getString(11));
				a.setPtitle(rs.getString(12));
				return a;
			}});
		
		int num = 0;
		Activity a = new Activity();
		for(ActivityElem l : ll){
			if(!a.getActivityName().equals(l.getPromotionType())){
				if(num!=0){
					a.setLen(num+1);				
					ls.add(a);
				}
				a = new Activity();
				num = 0;
				a.setActivityName(l.getPromotionType());
				a.getLs().add(l);
			}else{
				a.getLs().add(l);
				num++;
			}
		}
		a.setLen(num+1);
		ls.add(a);
		
		
		return ls;
	}

	public String getUserIdByOpenId(String openId) {
		String sql = "select imid from mobile_wx_roster_info where openid = ?";
		List<String> result = mobileReadTemplate.query(sql,
				new Object[] { openId }, new RowMapper<String>() {

					@Override
					public String mapRow(ResultSet arg0, int arg1)
							throws SQLException {
						return arg0.getString(1);
					}

				});

		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}
	}
	
	public String getOpenIdByUserName(String username) {
		String sql = "select openid from mobile_wx_roster_info where imid = ? and state=1";
		List<String> result = mobileReadTemplate.query(sql,
				new Object[] { username }, new RowMapper<String>() {

					@Override
					public String mapRow(ResultSet arg0, int arg1)
							throws SQLException {
						return arg0.getString(1);
					}

				});

		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}
	}

	public WxAppInfo getAppConfigInfo(String type) {
		String sql = "select appid,appsecret,token from mobile_wx_config where type = ?";
		List<WxAppInfo> result = mobileReadTemplate.query(sql,
				new Object[] { type }, new RowMapper<WxAppInfo>() {

					@Override
					public WxAppInfo mapRow(ResultSet arg0, int arg1)
							throws SQLException {
						WxAppInfo a = new WxAppInfo();
						a.setAppId(arg0.getString(1));
						a.setAppSecret(arg0.getString(2));
						a.setToken(arg0.getString(3));
						return a;
					}
				});
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}
	}
	
	public WxAppInfo getAppConfigInfoByWxId(String appId) {
		String sql = "select appid,appsecret,token,imid,nickname,openid from mobile_wx_config where appid = ?";
		List<WxAppInfo> result = mobileReadTemplate.query(sql,
				new Object[] { appId }, new RowMapper<WxAppInfo>() {

					@Override
					public WxAppInfo mapRow(ResultSet arg0, int arg1)
							throws SQLException {
						WxAppInfo a = new WxAppInfo();
						a.setAppId(arg0.getString(1));
						a.setAppSecret(arg0.getString(2));
						a.setToken(arg0.getString(3));
						a.setImid(arg0.getString(4));
						a.setNickname(arg0.getString(5));
						a.setOpenId(arg0.getString(6));
						return a;
					}
				});
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}
	}

	public ImUserInfo getImUserInfo(String username) {
		String sql = "select username, password from authreg where username = ?";
		try {
			List<ImUserInfo> result = archiveJdbcTemplate.query(sql,
					new Object[] { username }, new RowMapper<ImUserInfo>() {

						@Override
						public ImUserInfo mapRow(ResultSet arg0, int arg1)
								throws SQLException {
							ImUserInfo a = new ImUserInfo();
							a.setUsername(arg0.getString(1));
							a.setPassword(arg0.getString(2));
							return a;
						}
					});
			if (result != null && result.size() > 0) {
				return result.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int bindWxAccount(String openId, String imId, String serviceId) {
		String sql = "insert into mobile_wx_roster_info (openid, imid) values (?,?)";
		try {
			return mobileWriteTemplate.update(sql,new Object[] { openId, imId });
		} catch (DuplicateKeyException e) {
			String upd = "update mobile_wx_roster_info set imid = ? where openid = ?";
			return mobileWriteTemplate.update(upd,new Object[] { imId, openId });
		}
	}

	public String getNickNameByOpenId(String openId) {
		String sql = "select nickname from mobile_wx_roster_info where openid = ?";

		List<String> rs1 = mobileReadTemplate.query(sql,
				new Object[] { openId }, new RowMapper<String>() {

					@Override
					public String mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getString(1);
					}
				});
		if (rs1 != null && rs1.size() > 0) {
			System.out.println(rs1.get(0));
			return rs1.get(0);
		} else {
			return "";
		}
	}

	public int unBindWxAccount(String openId, String imId, String serviceId) {
		String sql = "update mobile_wx_roster_info set imid = null where openid = ? and imid = ?";
		return mobileWriteTemplate.update(sql, new Object[] { openId, imId });
	}

	public int updIMHeadImgUrl(String imId, String imgBase64) {
		String sql = "update  vcard set `bin-photoData` = ? where `collection-owner` = '"
				+ imId + "@hcim.b2b.hc360.com'";
		return archiveJdbcTemplate.update(sql, new Object[] { imgBase64 });
	}

	public int updIMNickName(String imId, String nickname) {
		String sql = "update  vcard set `base-NikeName` = ? where `collection-owner` = '"
				+ imId + "@hcim.b2b.hc360.com'";
		return archiveJdbcTemplate.update(sql, new Object[] { nickname });
	}

	public String getImUserNickName(String imId) {
		String sql = "select `base-NikeName`  from vcard where `collection-owner` = '"
				+ imId + "@hcim.b2b.hc360.com'";

		List<String> result = archiveJdbcTemplate.query(sql,
				new RowMapper<String>() {

					@Override
					public String mapRow(ResultSet arg0, int arg1)
							throws SQLException {
						return arg0.getString(1);
					}
				});
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	public int getAccountBindStatus(String imId,String mpopenid) {
		String sql = "select count(1) from mobile_wx_roster_info where imid = ? and mpopenid=? and state=1";
		return mobileReadTemplate.queryForInt(sql, new Object[] { imId,mpopenid });
	}

	public int updBindWxAccount(String openId, String imId, String oldImId) {
		String sql = "update mobile_wx_roster_info set imid = ? where openid = ? and imid = ?";
		return mobileWriteTemplate.update(sql, new Object[] { imId, openId,
				oldImId });
	}
	public int saveUserPageDuration(String openId,String eventKey,String starttime,String endtime) {
		String sql = "UPDATE mobile_wx_menu_stat SET starttime = ?,endtime = ? WHERE openid='gh_9269175af21a' AND rosterid = ? AND event_key = ? AND starttime is null ORDER BY createtime ASC LIMIT 1";
		return mobileWriteTemplate.update(sql, new Object[] { starttime,endtime,openId,eventKey });
	}
	
	public int saveTemplateMsgStat(String templateId,String rosterId,int type){
		String sql = "INSERT INTO mobile_wx_templatemsg_stat (template_id,roster_id,type,createtime) VALUES (?,?,?,?)";
		return mobileWriteTemplate.update(sql, new Object[] {templateId,rosterId,type,new Date()});
	}
	public int saveTamplateClickStat(String rosterId,int type){
		String sql = "UPDATE mobile_wx_templatemsg_stat SET open_cnt=open_cnt+1 WHERE roster_id=? AND type=? ORDER BY createtime DESC LIMIT 1";
		return mobileWriteTemplate.update(sql, new Object[]{rosterId,type});
	}
	
	public int saveWxUserClickstat(MobileWxMenuStat stat){
		String sql = "INSERT INTO mobile_wx_menu_stat (openid,rosterid,menu_name,event_type,event_key,ifbind,createtime,state)"
    			+ "VALUES (?,?,?,?,?,?,?,?)";
		
    	int result = 0;
    	try{
    		result = mobileWriteTemplate.update(
                    sql,
                    new Object[] { stat.getOpenid(), stat.getRosterid(),stat.getMenuName(),stat.getEventType(),stat.getEventKey(),
                    		stat.getIfbind(),stat.getCreatetime(),stat.getState()});
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return result;
	}
	
	//商机订阅
	public List<MobileWxRosterOpportunity> getRosterOpportunityList(String openid){
		String sql = "select id,openid,keyword,province,city,limit_count,createtime,is_used_free from mobile_wx_roster_opportunity where openid=? order by createtime DESC";
		return mobileReadTemplate.query(sql, new Object[] { openid }, new RowMapper<MobileWxRosterOpportunity>() {

			@Override
			public MobileWxRosterOpportunity mapRow(ResultSet rs, int rowNum) throws SQLException {
				MobileWxRosterOpportunity a = new MobileWxRosterOpportunity();
				a.setId(rs.getLong(1));
				a.setOpenid(rs.getString(2));
				a.setKeyword(rs.getString(3));
				a.setProvince(rs.getString(4));
				a.setCity(rs.getString(5));
				a.setLimit_count(rs.getInt(6));
				a.setCreatetime(rs.getTime(7));
				a.setIs_used_free(rs.getInt(8));
				return a;
			}
		});
	}
	
	public int insertRosterOpportunity(MobileWxRosterOpportunity opt){
		String sql = "INSERT INTO mobile_wx_roster_opportunity (openid,keyword,province,city,limit_count,createtime,is_used_free) VALUES (?,?,?,?,?,?,?)";
		return mobileWriteTemplate.update(sql, new Object[] {opt.getOpenid(),opt.getKeyword(),opt.getProvince(),opt.getCity(),opt.getLimit_count(),new Date(),0});
	}
	
	public MobileWxRosterOpportunity getRosterOpportunityById(String id){
		String sql = "select id,openid,keyword,province,city,limit_count,createtime,is_used_free from mobile_wx_roster_opportunity where id=?";
		
		List<MobileWxRosterOpportunity> list = mobileReadTemplate.query(sql, new Object[] { id }, new RowMapper<MobileWxRosterOpportunity>() {

            @Override
            public MobileWxRosterOpportunity mapRow(ResultSet rs, int rowNum) throws SQLException {
            	MobileWxRosterOpportunity a = new MobileWxRosterOpportunity();
            	a.setId(rs.getLong(1));
				a.setOpenid(rs.getString(2));
				a.setKeyword(rs.getString(3));
				a.setProvince(rs.getString(4));
				a.setCity(rs.getString(5));
				a.setLimit_count(rs.getInt(6));
				a.setCreatetime(rs.getTime(7));
				a.setIs_used_free(rs.getInt(8));
                return a;
            }
        });
		
		if(list != null && list.size()>0){
   		 	return list.get(0);
	   	}else{
	   		 return null;
	   	}
	}
	
	public int updateRosterOpportunityByParam(MobileWxRosterOpportunity opt){
		String sql = "UPDATE mobile_wx_roster_opportunity SET keyword=?,province=?,city=?,limit_count=?,createtime=? WHERE openid=? AND id=? ";
		return mobileWriteTemplate.update(sql, new Object[]{opt.getKeyword(),opt.getProvince(),opt.getCity(),opt.getLimit_count(),new Date(),opt.getOpenid(),opt.getId()});
	}
	public int updateRosterOpportunityIsUsedFreeByParam(String openid){
		String sql = "UPDATE mobile_wx_roster_opportunity SET is_used_free=? WHERE openid=?";
		return mobileWriteTemplate.update(sql, new Object[]{1,openid});
	}
	public List<String> getRosterOpportunityOpenidList(){
	String sql = "select o.openid from mobile_wx_roster_opportunity o LEFT JOIN mobile_wx_roster_info r ON o.openid=r.openid where o.limit_count>0 AND LENGTH(r.imid)>0 group by o.openid";
		
		List<String> list = mobileReadTemplate.query(sql, new Object[] {}, new RowMapper<String>() {

            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString(1);
            }
        });
		return list;
	}
	
	public MobileWxRosterOpportunity getRosterOpportunityByOpenidAndKey(String openid,String keyword){
		String sql = "select id,openid,keyword,province,city,limit_count,createtime from mobile_wx_roster_opportunity where openid=? and keyword=?";
		
		List<MobileWxRosterOpportunity> list = mobileReadTemplate.query(sql, new Object[] { openid,keyword }, new RowMapper<MobileWxRosterOpportunity>() {

            @Override
            public MobileWxRosterOpportunity mapRow(ResultSet rs, int rowNum) throws SQLException {
            	MobileWxRosterOpportunity a = new MobileWxRosterOpportunity();
            	a.setId(rs.getLong(1));
				a.setOpenid(rs.getString(2));
				a.setKeyword(rs.getString(3));
				a.setProvince(rs.getString(4));
				a.setCity(rs.getString(5));
				a.setLimit_count(rs.getInt(6));
				a.setCreatetime(rs.getTime(7));
                return a;
            }
        });
		
		if(list != null && list.size()>0){
   		 	return list.get(0);
	   	}else{
	   		 return null;
	   	}
	}
	
	public int deleteRosterOpportunityById(String id,String openid){
		String sql = "DELETE from mobile_wx_roster_opportunity WHERE id=? AND openid=? ";
		return mobileWriteTemplate.update(sql, new Object[]{id,openid});
	}
	/**
	 * 保存询盘宝、用户留言、商机订阅等用户反馈评价信息
	 * @return
	 */
	public int saveFeedBack(WxUserFeedBack feedBack){
		String sql = "INSERT INTO mobile_wx_user_feedback (user_name,roster_id,label_name,"
				+ "satisfied,user_content,createtime,product,company,time,"
				+ "phone,type,theme,contact,msg_id,bcid,message) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return mobileWriteTemplate.update(sql, new Object[] {feedBack.getUserName(),feedBack.getRosterId(),feedBack.getLabelName(),
				feedBack.getSatisfied(),feedBack.getUserContent(),new Date(),feedBack.getProduct(),
				feedBack.getCompany(),feedBack.getTime(),feedBack.getPhone(),feedBack.getType(),
				feedBack.getTheme(),feedBack.getContact(),feedBack.getMsgId(),feedBack.getBcid(),feedBack.getMessage()});
	}
	/**
	 * 判断用户是否已经评价
	 * @param userName
	 * @param openId
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int feedBackInfo(String userName,String openId,int type){
		String sql = "";
		if(type ==1){//用户反馈留言
			sql = "select count(1) from mobile_wx_user_feedback "
					+ "where user_name=? and roster_id=? and type=?";
		}if(type == 2 || type == 4){//用户留言
			sql = "select count(1) from mobile_wx_user_feedback "
					+ "where msg_id=? and roster_id=? and type=?";
		}if(type == 3){//商机订阅
			sql = "select count(1) from mobile_wx_user_feedback "
					+ "where bcid=? and roster_id=? and type=?";
		}else if(type == 5){//商机订阅
			sql = "select count(1) from mobile_wx_user_feedback "
					+ "where bcid=? and roster_id=? and type=?";
		}
		return mobileReadTemplate.queryForInt(sql, new Object[] { userName,openId,type});
	}
	
	/**
	 * 查询粉丝信息
	 * @param imId 用户慧聪账号
	 * @param openId 粉丝openid
	 * @return
	 */
	public List<UserInfo> getUserInfo(String imId, String openId) {
        String sql = "select id,openid,nickname,headimgurl,imid,createtime,endtime,state,mpopenid from mobile_wx_roster_info where ";
        if (imId != null)
            sql += "imid = '" + imId+ "'";
        else if (openId != null) {
            sql += "openid = '" + openId + "'";
        }
        
        return mobileReadTemplate.query(sql, new RowMapper<UserInfo>() {
            @Override
            public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            	UserInfo a = new UserInfo();
                a.setId(rs.getLong(1));
                a.setOpenid(rs.getString(2));
                a.setNickname(rs.getString(3));
                a.setHeadimgurl(rs.getString(4));
                a.setImid(rs.getString(5));
                a.setCreatetime(rs.getString(6));
                a.setEndtime(rs.getString(7));
                a.setState(rs.getInt(8));
                a.setMpopenid(rs.getString(9));
                return a;
            }
        });
    }
}
