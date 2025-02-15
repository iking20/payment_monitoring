package com.mygroup.paymentmonitoring.tools.model;

public class CustomerPayment {
	
	private String customerNo;
	private String paymentNo;
	private String amount;
	private String datePay;
	
	
	
	public CustomerPayment(String customerNo, String paymentNo, String amount, String datePay) {
		
		this.customerNo = customerNo;
		this.paymentNo = paymentNo;
		this.amount = amount;
		this.datePay = datePay;
	}



	public String getCustomerNo() {
		return customerNo;
	}



	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}



	public String getPaymentNo() {
		return paymentNo;
	}



	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
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
