package com.hc360.mobile.webservice.pojo;

import java.util.List;

public class Result {
    
    private int code;
    
    private String result;
    
    private String type;
    
    private String openId = "";
    
    private String uopenId; // 公众号openid

    private String imid;
    
    private String mainInd;
    
    private String nickname = "";
    
    private List<MobileAdConfigData> adData;
    
    private String userid;
    
    private List<AppShareBcIdNum> shareData;
    
    private AppShareBcIdNum share;
    
    private String returnMsg;
    
    private String appId;
    
    public String getImid() {
        return imid;
    }

    public void setImid(String imid) {
        this.imid = imid;
    }

    public String getMainInd() {
        return mainInd;
    }

    public void setMainInd(String mainInd) {
        this.mainInd = mainInd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

	public List<MobileAdConfigData> getAdData() {
		return adData;
	}

	public void setAdData(List<MobileAdConfigData> adData) {
		this.adData = adData;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public List<AppShareBcIdNum> getShareData() {
		return shareData;
	}

	public void setShareData(List<AppShareBcIdNum> shareData) {
		this.shareData = shareData;
	}

	public AppShareBcIdNum getShare() {
		return share;
	}

	public void setShare(AppShareBcIdNum share) {
		this.share = share;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getUopenId() {
		return uopenId;
	}

	public void setUopenId(String uopenId) {
		this.uopenId = uopenId;
	}

}
