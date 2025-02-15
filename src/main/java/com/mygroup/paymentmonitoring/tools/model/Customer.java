package com.mygroup.paymentmonitoring.tools.model;

public class Customer {
	
	private String CustNo;
	private String lastName;
	private String firstName;
	private String middleName;
	private String birthDay;
	private String phoneNo;
	private String emailAdd;
	private String status;

	public Customer(String custNo, String lastName, String firstName, String middleName, String birthDay,
			String phoneNo, String emailAdd, String status) {
	
		CustNo = custNo;
		this.lastName = lastName;
		this.firstName = firstName;
		this.middleName = middleName;
		this.birthDay = birthDay;
		this.phoneNo = phoneNo;
		this.emailAdd = emailAdd;
		this.status = status;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getBirthDay() {
		return birthDay;
	}



	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}



	public String getCustNo() {
		return CustNo;
	}


	public void setCustNo(String custNo) {
		CustNo = custNo;
	}


	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getMiddleName() {
		return middleName;
	}



	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}




	public String getPhoneNo() {
		return phoneNo;
	}



	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}



	public String getEmailAdd() {
		return emailAdd;
	}



	public void setEmailAdd(String emailAdd) {
		this.emailAdd = emailAdd;
	}
	
	
	
	
	
	

}
