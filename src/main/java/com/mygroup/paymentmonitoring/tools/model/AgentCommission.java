package com.mygroup.paymentmonitoring.tools.model;

public class AgentCommission {
	
	private String customerNo;
	private String agentNo;
	private String amount;
	private String datePay;
	
	
	public AgentCommission(String customerNo, String agentNo, String amount, String datePay) {
		
		this.customerNo = customerNo;
		this.agentNo = agentNo;
		this.amount = amount;
		this.datePay = datePay;
	}


	public String getCustomerNo() {
		return customerNo;
	}


	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}


	public String getAgentNo() {
		return agentNo;
	}


	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}


	public String getAmount() {
		return amount;
	}


	public void setAmount(String amount) {
		this.amount = amount;
	}


	public String getDatePay() {
		return datePay;
	}


	public void setDatePay(String datePay) {
		this.datePay = datePay;
	}
	
	

}
