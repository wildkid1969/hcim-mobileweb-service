package com.hc360.mobile.webservice.pojo;

import java.io.Serializable;

/**
 * 粉丝的详细信息
 * 
 * @author HC360
 *
 */
public class UserInfo implements Serializable {
    
	private static final long serialVersionUID = -6039161797374453103L;

	private Long id;
	
	private String openid;//粉丝
	
	private String mpopenid;//公众号
    
    private String nickname;
    
    private int sex;
    
    private String city;
    
    private String country;
    
    private String province;
    
    private String headimgurl;
    
    private String imid;
    
    private String createtime;
    private String endtime;
    
    private Integer state;//关注状态 0已取消关注  1关注中
    
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImid() {
		return imid;
	}

	public void setImid(String imid) {
		this.imid = imid;
	}

	public String getCreatetime() {
		return createtime;
	}

	public String getMpopenid() {
		return mpopenid;
	}

	public void setMpopenid(String mpopenid) {
		this.mpopenid = mpopenid;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}
