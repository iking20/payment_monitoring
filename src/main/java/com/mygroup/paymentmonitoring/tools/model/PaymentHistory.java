package com.mygroup.paymentmonitoring.tools.model;

public class PaymentHistory {
	
	private String customerNo;
	private String customerName;
	private String paymentAmount;
	private String paymentDate;
	private String sellerComm;
	private String agentComm;
	
	
	public PaymentHistory(String customerNo, String customerName, String paymentAmount, String paymentDate,
			String sellerComm, String agentComm) {
	
		this.customerNo = customerNo;
		this.customerName = customerName;
		this.paymentAmount = paymentAmount;
		this.paymentDate = paymentDate;
		this.sellerComm = sellerComm;
		this.agentComm = agentComm;
	}


	public String getCustomerNo() {
		return customerNo;
	}


	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	

	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public String getPaymentAmount() {
		return paymentAmount;
	}


	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}


	public String getPaymentDate() {
		return paymentDate;
	}


	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}


	public String getSellerComm() {
		return sellerComm;
	}


	public void setSellerComm(String sellerComm) {
		this.sellerComm = sellerComm;
	}


	public String getAgentComm() {
		return agentComm;
	}


	public void setAgentComm(String agentComm) {
		this.agentComm = agentComm;
	}
	
	

}
