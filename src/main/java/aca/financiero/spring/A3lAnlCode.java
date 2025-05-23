package aca.financiero.spring;


public class A3lAnlCode {
	private String anlCatId;
	private String anlCode;
	private String updateCount;
	private String lastChangeUserId;
	private String lastChangeDateTime;
	private String status;
	private String lookup;
	private String name;
	private String dagCode;
	private String bdgtCheck;
	private String bdgtStop;
	private String prohibitPosting;
	private String navigationOption;
	private String combinedBdgtChck;
	
	public A3lAnlCode(){
		anlCatId					= "";	
		anlCode						= "";
		updateCount					= "";
		lastChangeUserId			= "";
		lastChangeDateTime			= "";
		status						= "";
		lookup						= "";
		name						= "";
		dagCode						= "";
		bdgtCheck					= "";
		bdgtStop					= "";
		prohibitPosting				= "";
		navigationOption			= "";
		combinedBdgtChck			= "";
	}

	public String getAnlCatId() {
		return anlCatId;
	}

	public void setAnlCatId(String anlCatId) {
		this.anlCatId = anlCatId;
	}

	public String getAnlCode() {
		return anlCode;
	}

	public void setAnlCode(String anlCode) {
		this.anlCode = anlCode;
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

	public String getLastChangeDateTime() {
		return lastChangeDateTime;
	}

	public void setLastChangeDateTime(String lastChangeDateTime) {
		this.lastChangeDateTime = lastChangeDateTime;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDagCode() {
		return dagCode;
	}

	public void setDagCode(String dagCode) {
		this.dagCode = dagCode;
	}

	public String getBdgtCheck() {
		return bdgtCheck;
	}

	public void setBdgtCheck(String bdgtCheck) {
		this.bdgtCheck = bdgtCheck;
	}

	public String getBdgtStop() {
		return bdgtStop;
	}

	public void setBdgtStop(String bdgtStop) {
		this.bdgtStop = bdgtStop;
	}

	public String getProhibitPosting() {
		return prohibitPosting;
	}

	public void setProhibitPosting(String prohibitPosting) {
		this.prohibitPosting = prohibitPosting;
	}

	public String getNavigationOption() {
		return navigationOption;
	}

	public void setNavigationOption(String navigationOption) {
		this.navigationOption = navigationOption;
	}

	public String getCombinedBdgtChck() {
		return combinedBdgtChck;
	}

	public void setCombinedBdgtChck(String combinedBdgtChck) {
		this.combinedBdgtChck = combinedBdgtChck;
	}

}