package com.tom.mobileapp.transactionservice.model;

public class TransactionBody {

	private String fromMobileNo;

	private String toMobileNo;

	private String requestType;

	public String getFromMobileNo() {
		return fromMobileNo;
	}

	public void setFromMobileNo(String fromMobileNo) {
		this.fromMobileNo = fromMobileNo;
	}

	public String getToMobileNo() {
		return toMobileNo;
	}

	public void setToMobileNo(String toMobileNo) {
		this.toMobileNo = toMobileNo;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

}
