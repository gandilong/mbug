package com.thang.model;

/**
 * 会员实体
 * @author Administrator
 *
 */
public class HData {

	private String loginId;
	private int pageCount;
	private boolean orderByAsc;
	private String gmtCreate;
	private String orderBy;
	private String gmtCreateStr;
	private int pageStart;
	private String searchType;
	private String companyName;
	private String city;
	private String country;
	private String nickName;
	private String name;
	private String province;
	private String memberId;
	
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	

	public String getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getGmtCreateStr() {
		return gmtCreateStr;
	}

	public void setGmtCreateStr(String gmtCreateStr) {
		this.gmtCreateStr = gmtCreateStr;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public boolean isOrderByAsc() {
		return orderByAsc;
	}
	public void setOrderByAsc(boolean orderByAsc) {
		this.orderByAsc = orderByAsc;
	}
	public int getPageStart() {
		return pageStart;
	}
	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}
	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	@Override
	public String toString() {
		return "HData [Login ID=" + loginId + ", 注册时间=" + gmtCreateStr
				+ ", 公司名称=" + companyName +",省份="+province +", 城市=" + city + ", 申请人="
				+ name + ", 会员ID=" + memberId + "]";
	}
	
	
	
	
	
	
}
