package com.hc360.mobile.webservice.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hc360.mobile.webservice.pojo.Ad114Config;
import com.hc360.mobile.webservice.pojo.AppShareBcIdNum;
import com.hc360.mobile.webservice.pojo.InsertSearchAppKeyword;
import com.hc360.mobile.webservice.pojo.InsertSearchAppResult;
import com.hc360.mobile.webservice.pojo.MobileAdConfigData;
import com.hc360.mobile.webservice.pojo.MobileMainIndInfo;
import com.hc360.mobile.webservice.utils.MobileUtils;

@Repository
public class MobileAdConfigDao {

	@Resource(name = "mobileWriteJdbcTemplate")
	private JdbcTemplate mobileWriteTemplate;

	public List<MobileAdConfigData> getMobileAdData(int type, int kind, String mainind) {

		String sql = "select id,title,imgurl,url,userid,mainind,sharetitle,sharecontent from mobile_ad_config where type = ? and kind = ? ";

		if (MobileUtils.IsEmpty(mainind) == false) {
			sql += "and mainind like '%" + mainind.trim() + "%'";
		}

		sql += " order by imgtype desc,createtime desc";
		return mobileWriteTemplate.query(sql, new Object[] { type, kind }, new RowMapper<MobileAdConfigData>() {

			@Override
			public MobileAdConfigData mapRow(ResultSet rs, int rowNum) throws SQLException {
				MobileAdConfigData a = new MobileAdConfigData();
				a.setId(rs.getInt(1));
				a.setTitle(rs.getString(2));
				a.setImgUrl(rs.getString(3));
				a.setUrl(rs.getString(4));
				a.setUserId(rs.getString(5));
				a.setMainind(rs.getString(6));
				a.setSharetitle(rs.getString(7));
				a.setSharecontent(rs.getString(8));
				return a;
			}
		});
	}

	/*
	 * 获取信息的运营数据接口，与之前接口读取同一个表，只是搜索条件不一样
	 */
	public List<MobileAdConfigData> getMobileOtherAdData(int type, String mainind) {

		String sql = "select a.id,a.title,a.imgurl,a.url,a.userid,a.mainind,a.sharetitle,a.sharecontent,b.modulename,b.level "
				+ " from mobile_ad_config a,mobile_ad_module b " + " where a.type = ? and kind > 3 and a.kind=b.id ";

		if (MobileUtils.IsEmpty(mainind) == false) {
			sql += "and mainind like '%" + mainind.trim() + "%'";
		}

		sql += " order by b.level desc,a.imgtype desc,a.id asc";
		return mobileWriteTemplate.query(sql, new Object[] { type }, new RowMapper<MobileAdConfigData>() {

			@Override
			public MobileAdConfigData mapRow(ResultSet rs, int rowNum) throws SQLException {
				MobileAdConfigData a = new MobileAdConfigData();
				a.setId(rs.getInt(1));
				a.setTitle(rs.getString(2));
				a.setImgUrl(rs.getString(3));
				a.setUrl(rs.getString(4));
				a.setUserId(rs.getString(5));
				a.setMainind(rs.getString(6));
				a.setSharetitle(rs.getString(7));
				a.setSharecontent(rs.getString(8));
				a.setModulename(rs.getString(9));
				a.setLevel(rs.getString(10));
				return a;
			}
		});
	}

	public int getUpdateMobileAdData(int type, int kind, String mainind, long updatetime) {

		String sql = "select count(1) from mobile_ad_updateinfo where type = ? and kind = ? and updatetime > ?";
		if (MobileUtils.IsEmpty(mainind) == false) {
			sql += " and mainind like '%" + mainind + "%'";
		}
		return mobileWriteTemplate.queryForInt(sql, new Object[] { type, kind, updatetime });
	}

	public List<AppShareBcIdNum> getAppShareDataList(String userid) {
		String sql = "select bcid, sharetimes,time from mobile_app_share_stat where userid = ?";
		return mobileWriteTemplate.query(sql, new Object[] { userid }, new RowMapper<AppShareBcIdNum>() {

			@Override
			public AppShareBcIdNum mapRow(ResultSet rs, int rowNum) throws SQLException {
				AppShareBcIdNum a = new AppShareBcIdNum();
				a.setBcid(rs.getString(1));
				a.setSharetimes(rs.getInt(2));
				a.setTime(rs.getString(3));
				return a;
			}
		});
	}

	public AppShareBcIdNum getAppShareData(final String userid, String bcId) {
		String sql = "select bcid,sharetimes,time from mobile_app_share_stat where userid = ? and bcid = ?";
		List<AppShareBcIdNum> ll = mobileWriteTemplate.query(sql, new Object[] { userid, bcId },
				new RowMapper<AppShareBcIdNum>() {

					@Override
					public AppShareBcIdNum mapRow(ResultSet rs, int rowNum) throws SQLException {
						AppShareBcIdNum a = new AppShareBcIdNum();
						a.setUserid(userid);
						a.setBcid(rs.getString(1));
						a.setSharetimes(rs.getInt(2));
						a.setTime(rs.getString(3));
						return a;
					}
				});
		if (ll != null && ll.size() > 0) {
			return ll.get(0);
		} else {
			return null;
		}
	}

	public int saveAppShareDate(AppShareBcIdNum a, int isEmpty) {
		try {
			String sql = "";
			if (isEmpty == 0) {
				sql = "insert into mobile_app_share_stat (userid,bcid,sharetimes,time) values (?,?,1,?)";
				mobileWriteTemplate.update(sql, new Object[] { a.getUserid(), a.getBcid(), a.getTime() });
			} else {
				sql = "update mobile_app_share_stat set sharetimes = sharetimes + 1,time = ? where userid = ? and bcid = ?";
				mobileWriteTemplate.update(sql, new Object[] { a.getTime(), a.getUserid(), a.getBcid() });
			}
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	public InsertSearchAppResult getInsertSearchResult(String keyword) {
		String ss = "select keyword,imgurl,linkurl,type,value,title from mobile_app_insertsearch where (keyword = ? or keyword ='all') order by keyword asc";
		List<InsertSearchAppResult> cc = mobileWriteTemplate.query(ss, new Object[] { keyword.trim() },
				new RowMapper<InsertSearchAppResult>() {

					@Override
					public InsertSearchAppResult mapRow(ResultSet rs, int rowNum) throws SQLException {
						InsertSearchAppResult c = new InsertSearchAppResult();
						c.setName(rs.getString(1));
						c.setImgurl(rs.getString(2));
						c.setLinkurl(rs.getString(3));
						c.setType(rs.getString(4));
						c.setValue(rs.getString(5));
						c.setTitle(rs.getString(6));
						return c;
					}
				});
		if (cc != null && cc.size() > 0) {
			String sql = "select b.skeyword,b.type,b.minprice,b.maxprice from mobile_app_insertsearch a , mobile_app_insertsearch_info b where (a.id = b.sid and (a.keyword = '"
					+ keyword.trim() + "' or a.keyword = 'all')) or b.type = '3' order by b.id asc";

			List<InsertSearchAppKeyword> aa = mobileWriteTemplate.query(sql, new RowMapper<InsertSearchAppKeyword>() {
				@Override
				public InsertSearchAppKeyword mapRow(ResultSet rs, int rowNum) throws SQLException {
					InsertSearchAppKeyword a = new InsertSearchAppKeyword();
					a.setKeyword(rs.getString(1));
					a.setType(rs.getString(2));
					a.setMinprice(rs.getString(3));
					a.setMaxprice(rs.getString(4));
					return a;
				}
			});
			cc.get(0).setL(aa);
			return cc.get(0);
		} else {
			String sql = "select b.skeyword,b.type,b.minprice,b.maxprice from mobile_app_insertsearch a , mobile_app_insertsearch_info b where (a.id = b.sid and a.keyword = 'all') or b.type = '3' order by b.id asc";

			List<InsertSearchAppKeyword> aa = mobileWriteTemplate.query(sql, new RowMapper<InsertSearchAppKeyword>() {
				@Override
				public InsertSearchAppKeyword mapRow(ResultSet rs, int rowNum) throws SQLException {
					InsertSearchAppKeyword a = new InsertSearchAppKeyword();
					a.setKeyword(rs.getString(1));
					a.setType(rs.getString(2));
					a.setMinprice(rs.getString(3));
					a.setMaxprice(rs.getString(4));
					return a;
				}
			});
			cc.get(0).setL(aa);
			return cc.get(0);
		}
	}

	public List<MobileMainIndInfo> getMobileMainInduInfo(String type, String kind) {

		String sql = "select a.mainind,a.code,b.type,b.kind,b.imgurlon,b.imgurloff,b.iconurl,b.desc,b.mimgurl from mobile_mainind a, mobile_app_customindustry b"
				+ " where a.code = b.mcode and b.type = ? and b.kind = ? order by b.id desc";
		return mobileWriteTemplate.query(sql, new Object[] { type, kind }, new RowMapper<MobileMainIndInfo>() {

			@Override
			public MobileMainIndInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				MobileMainIndInfo a = new MobileMainIndInfo();
				a.setName(rs.getString(1));
				a.setCode(rs.getString(2));
				a.setType(rs.getString(3));
				a.setKind(rs.getString(4));
				a.setImgurlon(rs.getString(5));
				a.setImgurloff(rs.getString(6));
				a.setIconurl(rs.getString(7));
				a.setDesc(rs.getString(8));
				a.setMimgurl(rs.getString(9));
				return a;
			}
		});

	}

	public List<Ad114Config> getAd114ConfigList(int type, int kind) {
		String sql = "select url,title,enable from p_114_adconfig where type=? and kind = ?";

		return mobileWriteTemplate.query(sql, new Object[] { type, kind }, new RowMapper<Ad114Config>() {

			@Override
			public Ad114Config mapRow(ResultSet rs, int rowNum) throws SQLException {
				Ad114Config a = new Ad114Config();
				a.setUrl(rs.getString(1));
				a.setTitle(rs.getString(2));
				a.setEnable(rs.getInt(3));
				return a;
			}
		});
	}
}
