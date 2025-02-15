package com.mygroup.paymentmonitoring.tools.model;

public class SellerCommission {
	
	private String customerNo;
	private String sellerNo;
	private String amount;
	private String datePay;
	
	public SellerCommission(String customerNo, String sellerNo, String amount, String datePay) {
	
		this.customerNo = customerNo;
		this.sellerNo = sellerNo;
		this.amount = amount;
		this.datePay = datePay;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getSellerNo() {
		return sellerNo;
	}

	public void setSellerNo(String sellerNo) {
		this.sellerNo = sellerNo;
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
