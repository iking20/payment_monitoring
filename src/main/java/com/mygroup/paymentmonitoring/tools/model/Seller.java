package com.mygroup.paymentmonitoring.tools.model;

public class Seller {
	
	private String sellerNumber;
	private String sellerLastName;
	private String sellerFirstName;
	private String sellerMiddleName;
	
	public Seller(String sellerNumber, String sellerLastName, String sellerFirstName, String sellerMiddleName) {
		
		this.sellerNumber = sellerNumber;
		this.sellerLastName = sellerLastName;
		this.sellerFirstName = sellerFirstName;
		this.sellerMiddleName = sellerMiddleName;
	}

	public String getSellerNumber() {
		return sellerNumber;
	}

	public void setSellerNumber(String sellerNumber) {
		this.sellerNumber = sellerNumber;
	}

	public String getSellerLastName() {
		return sellerLastName;
	}

	public void setSellerLastName(String sellerLastName) {
		this.sellerLastName = sellerLastName;
	}

	public String getSellerFirstName() {
		return sellerFirstName;
	}

	public void setSellerFirstName(String sellerFirstName) {
		this.sellerFirstName = sellerFirstName;
	}

	public String getSellerMiddleName() {
		return sellerMiddleName;
	}

	public void setSellerMiddleName(String sellerMiddleName) {
		this.sellerMiddleName = sellerMiddleName;
	}
	
	
	

}
