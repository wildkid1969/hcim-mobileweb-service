package com.hc360.mobile.webservice.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *持久类BusNote
 *@author Created By b2btool 
 */

public class BusNote implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1850938353636964737L;
	/**id*/
	private long id;
	/**主题*/
	private String title;
	/**发布时间*/
	private Timestamp pubdate;
	/**infoid*/
	private long infoid;
	/**sendproviderid*/
	private long sendproviderid;
	/**recvproviderid*/
	private long recvproviderid;
	/**lstate*/
	private String lstate;
	/**snoteid*/
	private long snoteid;
	/**states*/
	private String states;
	/**whodel*/
	private String whodel;
	/**deldate*/
	private Timestamp deldate;
	/**sendcorname*/
	private String sendcorname;
	/**recvcorname*/
	private String recvcorname;
	/**sendusername*/
	private String sendusername;
	/**recvusername*/
	private String recvusername;
	/**notetype*/
	private String notetype;
	/**fmailid*/
	private long fmailid;
	/**corname*/
	private String corname;
	/**联系方式*/
	private String cortel;
	/**isopen*/
	private String isopen;
	/**recvdel*/
	private String recvdel;
	/**recvdeldate*/
	private Timestamp recvdeldate;
	/**senddel*/
	private String senddel;
	/**senddeldate*/
	private Timestamp senddeldate;
	/**联系人*/
	private String sendcontacter;
	/**recvcontacter*/
	private String recvcontacter;
	/**notekind*/
	private String notekind;
	/**operstate*/
	private String operstate;
	/**filename*/
	private String filename;
	/**issendmail*/
	private String issendmail;
	/**ytmp*/
	private String ytmp;
	/**content*/
	private String content;
	/**tmpflag*/
	private String tmpflag;
	/**smsreminded*/
	private long smsreminded;
	/**sendsmsdate*/
	private Timestamp sendsmsdate;
	/**smsid*/
	private long smsid;
	/**showcontact*/
	private long showcontact;
	/**infotype*/
	private String infotype;
	/**ishis*/
	private long ishis;
	/**updatetime*/
	private Timestamp updatetime;
	/**smsremind*/
	private long smsremind;
	/**mailremind*/
	private long mailremind;
	/**imremind*/
	private long imremind;
	/**busnotetype*/
	private long busnotetype;
	/**lastreply*/
	private Timestamp lastreply;
	/**isgarbage*/
	private long isgarbage;
	/**groupid*/
	private long groupid;
	/**isonekey*/
	private long isonekey;
	/**readdate*/
	private Timestamp readdate;
	/**sendareaid*/
	private String sendareaid;
	/**recvareaid*/
	private String recvareaid;
	/**sendinfoid*/
	private long sendinfoid;
	/**sendinfoidtype*/
	private String sendinfoidtype;
	/**price*/
	private BigDecimal price = new BigDecimal(0);
	/**brand*/
	private String brand;
	/**integrity*/
	private long integrity;
	/**bargain*/
	private String bargain;
	/**wantoknow*/
	private String wantoknow;
	/**wantoknowothers*/
	private String wantoknowothers;
	/**procount*/
	private BigDecimal procount;
	/**报价留言的留言质量得分*/
	private long score;
	/**议价有效期*/
	private Timestamp indate;
    /**产品名称*/
    private String productname;
    /**规格*/
    private String specification;
    /**产品数量*/
    private long productnum;
    /**产品单位*/
    private String productunit;
	/**关联商机标题*/
	private String businTitle;
	/**关联商机是否联系*/
	private String businIsContacted;
	/**关联商机的sorttag*/
	private String businSortTag;
	/**商机来源 */
	private String businSource;
	/**商机入库时间*/
	private Timestamp businPubdate;
	
	private Integer page;//当前页码
	private Integer totalPage;//总页数

	public Timestamp getBusinPubdate() {
		return businPubdate;
	}
	public void setBusinPubdate(Timestamp businPubdate) {
		this.businPubdate = businPubdate;
	}
	public String getBusinSource() {
		return businSource;
	}
	public void setBusinSource(String businSource) {
		this.businSource = businSource;
	}
	public Timestamp getIndate() {
		return indate;
	}
	public void setIndate(Timestamp indate) {
		this.indate = indate;
	}
	public long getScore() {
		return score;
	}
	public void setScore(long score) {
		this.score = score;
	}
	public BigDecimal getProcount() {
		return procount;
	}
	public void setProcount(BigDecimal procount) {
		this.procount = procount;
	}
	/**
	 * @return the wantoknow
	 */
	public String getWantoknow() {
		return wantoknow;
	}
	/**
	 * @param wantoknow the wantoknow to set
	 */
	public void setWantoknow(String wantoknow) {
		this.wantoknow = wantoknow;
	}
	/**
	 * @return the wantoknowothers
	 */
	public String getWantoknowothers() {
		return wantoknowothers;
	}
	/**
	 * @param wantoknowothers the wantoknowothers to set
	 */
	public void setWantoknowothers(String wantoknowothers) {
		this.wantoknowothers = wantoknowothers;
	}
	public String getBargain() {
		return bargain;
	}
	public void setBargain(String bargain) {
		this.bargain = bargain;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public long getIntegrity() {
		return integrity;
	}
	public void setIntegrity(long integrity) {
		this.integrity = integrity;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public long getSendinfoid() {
		return sendinfoid;
	}
	public void setSendinfoid(long sendinfoid) {
		this.sendinfoid = sendinfoid;
	}
	public String getSendinfoidtype() {
		return sendinfoidtype;
	}
	public void setSendinfoidtype(String sendinfoidtype) {
		this.sendinfoidtype = sendinfoidtype;
	}
	public String getRecvareaid() {
		return recvareaid;
	}
	public void setRecvareaid(String recvareaid) {
		this.recvareaid = recvareaid;
	}
	public String getSendareaid() {
		return sendareaid;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public void setSendareaid(String sendareaid) {
		this.sendareaid = sendareaid;
	}
	/**
	 * 取得id
	 * @return id
	 */
	public long getId () {
		return id;
	}
	/**
	 * 设置id
	 * @param id 待设置id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 取得title
	 * @return title
	 */
	public String getTitle () {
		return title;
	}
	/**
	 * 设置title
	 * @param title 待设置title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 取得pubdate
	 * @return pubdate
	 */
	public Timestamp getPubdate () {
		return pubdate;
	}
	/**
	 * 设置pubdate
	 * @param pubdate 待设置pubdate
	 */
	public void setPubdate(Timestamp pubdate) {
		this.pubdate = pubdate;
	}

	/**
	 * 取得infoid
	 * @return infoid
	 */
	public long getInfoid () {
		return infoid;
	}
	/**
	 * 设置infoid
	 * @param infoid 待设置infoid
	 */
	public void setInfoid(long infoid) {
		this.infoid = infoid;
	}

	/**
	 * 取得sendproviderid
	 * @return sendproviderid
	 */
	public long getSendproviderid () {
		return sendproviderid;
	}
	/**
	 * 设置sendproviderid
	 * @param sendproviderid 待设置sendproviderid
	 */
	public void setSendproviderid(long sendproviderid) {
		this.sendproviderid = sendproviderid;
	}

	/**
	 * 取得recvproviderid
	 * @return recvproviderid
	 */
	public long getRecvproviderid () {
		return recvproviderid;
	}
	/**
	 * 设置recvproviderid
	 * @param recvproviderid 待设置recvproviderid
	 */
	public void setRecvproviderid(long recvproviderid) {
		this.recvproviderid = recvproviderid;
	}

	/**
	 * 取得lstate
	 * @return lstate
	 */
	public String getLstate () {
		return lstate;
	}
	/**
	 * 设置lstate
	 * @param lstate 待设置lstate
	 */
	public void setLstate(String lstate) {
		this.lstate = lstate;
	}

	/**
	 * 取得snoteid
	 * @return snoteid
	 */
	public long getSnoteid () {
		return snoteid;
	}
	/**
	 * 设置snoteid
	 * @param snoteid 待设置snoteid
	 */
	public void setSnoteid(long snoteid) {
		this.snoteid = snoteid;
	}

	/**
	 * 取得states
	 * @return states
	 */
	public String getStates () {
		return states;
	}
	/**
	 * 设置states
	 * @param states 待设置states
	 */
	public void setStates(String states) {
		this.states = states;
	}

	/**
	 * 取得whodel
	 * @return whodel
	 */
	public String getWhodel () {
		return whodel;
	}
	/**
	 * 设置whodel
	 * @param whodel 待设置whodel
	 */
	public void setWhodel(String whodel) {
		this.whodel = whodel;
	}

	/**
	 * 取得deldate
	 * @return deldate
	 */
	public Timestamp getDeldate () {
		return deldate;
	}
	/**
	 * 设置deldate
	 * @param deldate 待设置deldate
	 */
	public void setDeldate(Timestamp deldate) {
		this.deldate = deldate;
	}

	/**
	 * 取得sendcorname
	 * @return sendcorname
	 */
	public String getSendcorname () {
		return sendcorname;
	}
	/**
	 * 设置sendcorname
	 * @param sendcorname 待设置sendcorname
	 */
	public void setSendcorname(String sendcorname) {
		this.sendcorname = sendcorname;
	}

	/**
	 * 取得recvcorname
	 * @return recvcorname
	 */
	public String getRecvcorname () {
		return recvcorname;
	}
	/**
	 * 设置recvcorname
	 * @param recvcorname 待设置recvcorname
	 */
	public void setRecvcorname(String recvcorname) {
		this.recvcorname = recvcorname;
	}

	/**
	 * 取得sendusername
	 * @return sendusername
	 */
	public String getSendusername () {
		return sendusername;
	}
	/**
	 * 设置sendusername
	 * @param sendusername 待设置sendusername
	 */
	public void setSendusername(String sendusername) {
		this.sendusername = sendusername;
	}

	/**
	 * 取得recvusername
	 * @return recvusername
	 */
	public String getRecvusername () {
		return recvusername;
	}
	/**
	 * 设置recvusername
	 * @param recvusername 待设置recvusername
	 */
	public void setRecvusername(String recvusername) {
		this.recvusername = recvusername;
	}

	/**
	 * 取得notetype
	 * @return notetype
	 */
	public String getNotetype () {
		return notetype;
	}
	/**
	 * 设置notetype
	 * @param notetype 待设置notetype
	 */
	public void setNotetype(String notetype) {
		this.notetype = notetype;
	}

	/**
	 * 取得fmailid
	 * @return fmailid
	 */
	public long getFmailid () {
		return fmailid;
	}
	/**
	 * 设置fmailid
	 * @param fmailid 待设置fmailid
	 */
	public void setFmailid(long fmailid) {
		this.fmailid = fmailid;
	}

	/**
	 * 取得corname
	 * @return corname
	 */
	public String getCorname () {
		return corname;
	}
	/**
	 * 设置corname
	 * @param corname 待设置corname
	 */
	public void setCorname(String corname) {
		this.corname = corname;
	}

	/**
	 * 取得cortel
	 * @return cortel
	 */
	public String getCortel () {
		return cortel;
	}
	/**
	 * 设置cortel
	 * @param cortel 待设置cortel
	 */
	public void setCortel(String cortel) {
		this.cortel = cortel;
	}

	/**
	 * 取得isopen
	 * @return isopen
	 */
	public String getIsopen () {
		return isopen;
	}
	/**
	 * 设置isopen
	 * @param isopen 待设置isopen
	 */
	public void setIsopen(String isopen) {
		this.isopen = isopen;
	}

	/**
	 * 取得recvdel
	 * @return recvdel
	 */
	public String getRecvdel () {
		return recvdel;
	}
	/**
	 * 设置recvdel
	 * @param recvdel 待设置recvdel
	 */
	public void setRecvdel(String recvdel) {
		this.recvdel = recvdel;
	}

	/**
	 * 取得recvdeldate
	 * @return recvdeldate
	 */
	public Timestamp getRecvdeldate () {
		return recvdeldate;
	}
	/**
	 * 设置recvdeldate
	 * @param recvdeldate 待设置recvdeldate
	 */
	public void setRecvdeldate(Timestamp recvdeldate) {
		this.recvdeldate = recvdeldate;
	}

	/**
	 * 取得senddel
	 * @return senddel
	 */
	public String getSenddel () {
		return senddel;
	}
	/**
	 * 设置senddel
	 * @param senddel 待设置senddel
	 */
	public void setSenddel(String senddel) {
		this.senddel = senddel;
	}

	/**
	 * 取得senddeldate
	 * @return senddeldate
	 */
	public Timestamp getSenddeldate () {
		return senddeldate;
	}
	/**
	 * 设置senddeldate
	 * @param senddeldate 待设置senddeldate
	 */
	public void setSenddeldate(Timestamp senddeldate) {
		this.senddeldate = senddeldate;
	}

	/**
	 * 取得sendcontacter
	 * @return sendcontacter
	 */
	public String getSendcontacter () {
		return sendcontacter;
	}
	/**
	 * 设置sendcontacter
	 * @param sendcontacter 待设置sendcontacter
	 */
	public void setSendcontacter(String sendcontacter) {
		this.sendcontacter = sendcontacter;
	}

	/**
	 * 取得recvcontacter
	 * @return recvcontacter
	 */
	public String getRecvcontacter () {
		return recvcontacter;
	}
	/**
	 * 设置recvcontacter
	 * @param recvcontacter 待设置recvcontacter
	 */
	public void setRecvcontacter(String recvcontacter) {
		this.recvcontacter = recvcontacter;
	}

	/**
	 * 取得notekind
	 * @return notekind
	 */
	public String getNotekind () {
		return notekind;
	}
	/**
	 * 设置notekind
	 * @param notekind 待设置notekind
	 */
	public void setNotekind(String notekind) {
		this.notekind = notekind;
	}

	/**
	 * 取得operstate
	 * @return operstate
	 */
	public String getOperstate () {
		return operstate;
	}
	/**
	 * 设置operstate
	 * @param operstate 待设置operstate
	 */
	public void setOperstate(String operstate) {
		this.operstate = operstate;
	}

	/**
	 * 取得filename
	 * @return filename
	 */
	public String getFilename () {
		return filename;
	}
	/**
	 * 设置filename
	 * @param filename 待设置filename
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * 取得issendmail
	 * @return issendmail
	 */
	public String getIssendmail () {
		return issendmail;
	}
	/**
	 * 设置issendmail
	 * @param issendmail 待设置issendmail
	 */
	public void setIssendmail(String issendmail) {
		this.issendmail = issendmail;
	}

	/**
	 * 取得ytmp
	 * @return ytmp
	 */
	public String getYtmp () {
		return ytmp;
	}
	/**
	 * 设置ytmp
	 * @param ytmp 待设置ytmp
	 */
	public void setYtmp(String ytmp) {
		this.ytmp = ytmp;
	}

	/**
	 * 取得content
	 * @return content
	 */
	public String getContent () {
		return content;
	}
	/**
	 * 设置content
	 * @param content 待设置content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 取得tmpflag
	 * @return tmpflag
	 */
	public String getTmpflag () {
		return tmpflag;
	}
	/**
	 * 设置tmpflag
	 * @param tmpflag 待设置tmpflag
	 */
	public void setTmpflag(String tmpflag) {
		this.tmpflag = tmpflag;
	}

	/**
	 * 取得smsreminded
	 * @return smsreminded
	 */
	public long getSmsreminded () {
		return smsreminded;
	}
	/**
	 * 设置smsreminded
	 * @param smsreminded 待设置smsreminded
	 */
	public void setSmsreminded(long smsreminded) {
		this.smsreminded = smsreminded;
	}

	/**
	 * 取得sendsmsdate
	 * @return sendsmsdate
	 */
	public Timestamp getSendsmsdate () {
		return sendsmsdate;
	}
	/**
	 * 设置sendsmsdate
	 * @param sendsmsdate 待设置sendsmsdate
	 */
	public void setSendsmsdate(Timestamp sendsmsdate) {
		this.sendsmsdate = sendsmsdate;
	}

	/**
	 * 取得smsid
	 * @return smsid
	 */
	public long getSmsid () {
		return smsid;
	}
	/**
	 * 设置smsid
	 * @param smsid 待设置smsid
	 */
	public void setSmsid(long smsid) {
		this.smsid = smsid;
	}

	/**
	 * 取得showcontact
	 * @return showcontact
	 */
	public long getShowcontact () {
		return showcontact;
	}
	/**
	 * 设置showcontact
	 * @param showcontact 待设置showcontact
	 */
	public void setShowcontact(long showcontact) {
		this.showcontact = showcontact;
	}

	/**
	 * 取得infotype
	 * @return infotype
	 */
	public String getInfotype () {
		return infotype;
	}
	/**
	 * 设置infotype
	 * @param infotype 待设置infotype
	 */
	public void setInfotype(String infotype) {
		this.infotype = infotype;
	}

	/**
	 * 取得ishis
	 * @return ishis
	 */
	public long getIshis () {
		return ishis;
	}
	/**
	 * 设置ishis
	 * @param ishis 待设置ishis
	 */
	public void setIshis(long ishis) {
		this.ishis = ishis;
	}

	/**
	 * 取得updatetime
	 * @return updatetime
	 */
	public Timestamp getUpdatetime () {
		return updatetime;
	}
	/**
	 * 设置updatetime
	 * @param updatetime 待设置updatetime
	 */
	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	/**
	 * 取得smsremind
	 * @return smsremind
	 */
	public long getSmsremind () {
		return smsremind;
	}
	/**
	 * 设置smsremind
	 * @param smsremind 待设置smsremind
	 */
	public void setSmsremind(long smsremind) {
		this.smsremind = smsremind;
	}

	/**
	 * 取得mailremind
	 * @return mailremind
	 */
	public long getMailremind () {
		return mailremind;
	}
	/**
	 * 设置mailremind
	 * @param mailremind 待设置mailremind
	 */
	public void setMailremind(long mailremind) {
		this.mailremind = mailremind;
	}

	/**
	 * 取得imremind
	 * @return imremind
	 */
	public long getImremind () {
		return imremind;
	}
	/**
	 * 设置imremind
	 * @param imremind 待设置imremind
	 */
	public void setImremind(long imremind) {
		this.imremind = imremind;
	}

	/**
	 * 取得busnotetype
	 * @return busnotetype
	 */
	public long getBusnotetype () {
		return busnotetype;
	}
	/**
	 * 设置busnotetype
	 * @param busnotetype 待设置busnotetype
	 */
	public void setBusnotetype(long busnotetype) {
		this.busnotetype = busnotetype;
	}

	/**
	 * 取得lastreply
	 * @return lastreply
	 */
	public Timestamp getLastreply () {
		return lastreply;
	}
	/**
	 * 设置lastreply
	 * @param lastreply 待设置lastreply
	 */
	public void setLastreply(Timestamp lastreply) {
		this.lastreply = lastreply;
	}

	/**
	 * 取得isgarbage
	 * @return isgarbage
	 */
	public long getIsgarbage () {
		return isgarbage;
	}
	/**
	 * 设置isgarbage
	 * @param isgarbage 待设置isgarbage
	 */
	public void setIsgarbage(long isgarbage) {
		this.isgarbage = isgarbage;
	}

	/**
	 * 取得groupid
	 * @return groupid
	 */
	public long getGroupid () {
		return groupid;
	}
	/**
	 * 设置groupid
	 * @param groupid 待设置groupid
	 */
	public void setGroupid(long groupid) {
		this.groupid = groupid;
	}

	/**
	 * 取得isonekey
	 * @return isonekey
	 */
	public long getIsonekey () {
		return isonekey;
	}
	/**
	 * 设置isonekey
	 * @param isonekey 待设置isonekey
	 */
	public void setIsonekey(long isonekey) {
		this.isonekey = isonekey;
	}

	/**
	 * 取得readdate
	 * @return readdate
	 */
	public Timestamp getReaddate () {
		return readdate;
	}
	/**
	 * 设置readdate
	 * @param readdate 待设置readdate
	 */
	public void setReaddate(Timestamp readdate) {
		this.readdate = readdate;
	}
	public String getBusinTitle() {
		return businTitle;
	}
	public void setBusinTitle(String businTitle) {
		this.businTitle = businTitle;
	}
	public String getBusinIsContacted() {
		return businIsContacted;
	}
	public void setBusinIsContacted(String businIsContacted) {
		this.businIsContacted = businIsContacted;
	}

	public String getBusinSortTag() {
		return businSortTag;
	}
	public void setBusinSortTag(String businSortTag) {
		this.businSortTag = businSortTag;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public long getProductnum() {
		return productnum;
	}
	public void setProductnum(long productnum) {
		this.productnum = productnum;
	}
	public String getProductunit() {
		return productunit;
	}
	public void setProductunit(String productunit) {
		this.productunit = productunit;
	}
	
	
}