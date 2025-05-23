package aca.financiero.spring;


public class A3lAnlCat {
	private String anlCatId;
	private String updateCount;
	private String lastChangeUserId;
	private String lastChangeDatetime;
	private String status;
	private String lookup;
	private String useableAnlEntId;
	private String sHead;
	private String descr;
	private String dagCode;
	private String amendCode;
	private String validateInd;
	private String lngth;
	private String linked;
	private String anlCodeFrom;
	
	public A3lAnlCat(){
		anlCatId			= "";
		updateCount			= "";
		lastChangeUserId	= "";
		lastChangeDatetime	= "";
		status				= "";
		lookup				= "";
		useableAnlEntId		= "";
		sHead				= "";
		descr				= "";
		dagCode				= "";
		amendCode			= "";
		validateInd			= "";
		lngth				= "";
		linked				= "";
		anlCodeFrom			= "";
	}

	public String getAnlCatId() {
		return anlCatId;
	}

	public void setAnlCatId(String anlCatId) {
		this.anlCatId = anlCatId;
	}

	public String getUpdateCount() {
		return updateCount;
	}

	public void setUpdateCount(String updateCount) {
		this.updateCount = updateCount;
	}

	public String getLastChangeUserId() {
		return lastChangeUserId;
	}

	public void setLastChangeUserId(String lastChangeUserId) {
		this.lastChangeUserId = lastChangeUserId;
	}

	public String getLastChangeDatetime() {
		return lastChangeDatetime;
	}

	public void setLastChangeDatetime(String lastChangeDatetime) {
		this.lastChangeDatetime = lastChangeDatetime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLookup() {
		return lookup;
	}

	public void setLookup(String lookup) {
		this.lookup = lookup;
	}

	public String getUseableAnlEntId() {
		return useableAnlEntId;
	}

	public void setUseableAnlEntId(String useableAnlEntId) {
		this.useableAnlEntId = useableAnlEntId;
	}

	public String getsHead() {
		return sHead;
	}

	public void setsHead(String sHead) {
		this.sHead = sHead;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getDagCode() {
		return dagCode;
	}

	public void setDagCode(String dagCode) {
		this.dagCode = dagCode;
	}

	public String getAmendCode() {
		return amendCode;
	}

	public void setAmendCode(String amendCode) {
		this.amendCode = amendCode;
	}

	public String getValidateInd() {
		return validateInd;
	}

	public void setValidateInd(String validateInd) {
		this.validateInd = validateInd;
	}

	public String getLngth() {
		return lngth;
	}

	public void setLngth(String lngth) {
		this.lngth = lngth;
	}

	public String getLinked() {
		return linked;
	}

	public void setLinked(String linked) {
		this.linked = linked;
	}

	public String getAnlCodeFrom() {
		return anlCodeFrom;
	}

	public void setAnlCodeFrom(String anlCodeFrom) {
		this.anlCodeFrom = anlCodeFrom;
	}
	
}