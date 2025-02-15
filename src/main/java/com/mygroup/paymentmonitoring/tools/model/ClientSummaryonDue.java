package com.mygroup.paymentmonitoring.tools.model;

public class ClientSummaryonDue {
	
	private String custID;
	private String custName;
	private String custTotalPrice;
	private String custDueDate;
	private String custlastPaymentDate;
	private String custAmountDue;
	private String custStartDate;
	private String custMonthly;
	private String custLeftOver;
	private String custNoDue;
	
	
	public ClientSummaryonDue(String custID, String custName, String custTotalPrice, String custDueDate, 
			String custlastPaymentDate, String custAmountDue,String custStartDate,String custMonthly,String custLeftOver,String custNoDue) {
		
		this.custID = custID;
		this.custName = custName;
		this.custTotalPrice = custTotalPrice;
		this.custDueDate = custDueDate;
		this.custlastPaymentDate = custlastPaymentDate;
		this.custAmountDue = custAmountDue;
		this.custStartDate = custStartDate;
		this.custMonthly = custMonthly;
		this.custLeftOver = custLeftOver;
		this.custNoDue = custNoDue;
	}


	public String getCustID() {
		return custID;
	}


	public void setCustID(String custID) {
		this.custID = custID;
	}


	public String getCustName() {
		return custName;
	}


	public void setCustName(String custName) {
		this.custName = custName;
	}


	public String getCustTotalPrice() {
		return custTotalPrice;
	}


	public void setCustTotalPrice(String custTotalPrice) {
		this.custTotalPrice = custTotalPrice;
	}


	public String getCustlastPaymentDate() {
		return custlastPaymentDate;
	}
	
	
	public String getCustDueDate() {
		return custDueDate;
	}


	public void setCustDueDate(String custDueDate) {
		this.custDueDate = custDueDate;
	}


	public void setCustlastPaymentDate(String custlastPaymentDate) {
		this.custlastPaymentDate = custlastPaymentDate;
	}
	
	
	public String getCustAmountDue() {
		return custAmountDue;
	}


	public void setCustAmountDue(String custAmountDue) {
		this.custAmountDue = custAmountDue;
	}


	public String getCustStartDate() {
		return custStartDate;
	}


	public void setCustStartDate(String custStartDate) {
		this.custStartDate = custStartDate;
	}


	public String getCustMonthly() {
		return custMonthly;
	}


	public void setCustMonthly(String custMonthly) {
		this.custMonthly = custMonthly;
	}


	public String getCustLeftOver() {
		return custLeftOver;
	}


	public void setCustLeftOver(String custLeftOver) {
		this.custLeftOver = custLeftOver;
	}


	public String getCustNoDue() {
		return custNoDue;
	}


	public void setCustNoDue(String custNoDue) {
		this.custNoDue = custNoDue;
	}
	
	
	
	

}
