package com.thang.model;

import java.util.List;

public class QData {

	private int totalCount;
	private List<MData> resultList;
	private String errorMsg;
	private boolean errorFlag;
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List<MData> getResultList() {
		return resultList;
	}
	public void setResultList(List<MData> resultList) {
		this.resultList = resultList;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public boolean isErrorFlag() {
		return errorFlag;
	}
	public void setErrorFlag(boolean errorFlag) {
		this.errorFlag = errorFlag;
	}
	
}
