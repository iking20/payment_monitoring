package com.mygroup.paymentmonitoring.tools.model;

public class SummaryContract {
	
	private String summarycustomerNo;
	private String summarycustomerName;
	private String summaryblockNo;
	private String summarylotNo;
	private String summarylotArea;
	private String summaryduration;
	private String summaryprice;
	private String summarytotalPrice;
	private String summarypayment;
	private String summarybalance;
	private String summarydateStart;
	private String summarydateEnd;
	private String summarysellerName;
	private String summarysellerComm;
	private String summaryagentName;
	private String summaryLastPayment;
	
	public SummaryContract(String summarycustomerNo, String summarycustomerName, String summaryblockNo,
			String summarylotNo, String summarylotArea, String summaryduration, String summaryprice,
			String summarytotalPrice, String summarypayment, String summarybalance, String summarydateStart,
			String summarydateEnd, String summarysellerName, String summarysellerComm, String summaryagentName,
			String summaryLastPayment) {
		
		this.summarycustomerNo = summarycustomerNo;
		this.summarycustomerName = summarycustomerName;
		this.summaryblockNo = summaryblockNo;
		this.summarylotNo = summarylotNo;
		this.summarylotArea = summarylotArea;
		this.summaryduration = summaryduration;
		this.summaryprice = summaryprice;
		this.summarytotalPrice = summarytotalPrice;
		this.summarypayment = summarypayment;
		this.summarybalance = summarybalance;
		this.summarydateStart = summarydateStart;
		this.summarydateEnd = summarydateEnd;
		this.summarysellerName = summarysellerName;
		this.summarysellerComm = summarysellerComm;
		this.summaryagentName = summaryagentName;
		this.summaryLastPayment = summaryLastPayment;
	}

	public String getSummarycustomerNo() {
		return summarycustomerNo;
	}

	public void setSummarycustomerNo(String summarycustomerNo) {
		this.summarycustomerNo = summarycustomerNo;
	}

	public String getSummarycustomerName() {
		return summarycustomerName;
	}

	public void setSummarycustomerName(String summarycustomerName) {
		this.summarycustomerName = summarycustomerName;
	}

	public String getSummaryblockNo() {
		return summaryblockNo;
	}

	public void setSummaryblockNo(String summaryblockNo) {
		this.summaryblockNo = summaryblockNo;
	}

	public String getSummarylotNo() {
		return summarylotNo;
	}

	public void setSummarylotNo(String summarylotNo) {
		this.summarylotNo = summarylotNo;
	}

	public String getSummarylotArea() {
		return summarylotArea;
	}

	public void setSummarylotArea(String summarylotArea) {
		this.summarylotArea = summarylotArea;
	}

	public String getSummaryduration() {
		return summaryduration;
	}

	public void setSummaryduration(String summaryduration) {
		this.summaryduration = summaryduration;
	}

	public String getSummaryprice() {
		return summaryprice;
	}

	public void setSummaryprice(String summaryprice) {
		this.summaryprice = summaryprice;
	}

	public String getSummarytotalPrice() {
		return summarytotalPrice;
	}

	public void setSummarytotalPrice(String summarytotalPrice) {
		this.summarytotalPrice = summarytotalPrice;
	}

	public String getSummarypayment() {
		return summarypayment;
	}

	public void setSummarypayment(String summarypayment) {
		this.summarypayment = summarypayment;
	}

	public String getSummarybalance() {
		return summarybalance;
	}

	public void setSummarybalance(String summarybalance) {
		this.summarybalance = summarybalance;
	}

	public String getSummarydateStart() {
		return summarydateStart;
	}

	public void setSummarydateStart(String summarydateStart) {
		this.summarydateStart = summarydateStart;
	}

	public String getSummarydateEnd() {
		return summarydateEnd;
	}

	public void setSummarydateEnd(String summarydateEnd) {
		this.summarydateEnd = summarydateEnd;
	}

	public String getSummarysellerName() {
		return summarysellerName;
	}

	public void setSummarysellerName(String summarysellerName) {
		this.summarysellerName = summarysellerName;
	}

	public String getSummarysellerComm() {
		return summarysellerComm;
	}

	public void setSummarysellerComm(String summarysellerComm) {
		this.summarysellerComm = summarysellerComm;
	}

	public String getSummaryagentName() {
		return summaryagentName;
	}

	public void setSummaryagentName(String summaryagentName) {
		this.summaryagentName = summaryagentName;
	}

	public String getSummaryLastPayment() {
		return summaryLastPayment;
	}

	public void setSummaryLastPayment(String summaryLastPayment) {
		this.summaryLastPayment = summaryLastPayment;
	}
	
}
