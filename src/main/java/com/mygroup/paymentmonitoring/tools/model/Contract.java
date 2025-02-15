package com.mygroup.paymentmonitoring.tools.model;

public class Contract {
	
	private String contractNo;
	private String customerNo;
	private String blockNo;
	private String lotNo;
	private String lotArea;
	private String duration;
	private String price;
	private String totalPrice;
	private String payment;
	private String balance;
	private String dateStart;
	private String dateEnd;
	private String sellerNo;
	private String agentNo;
	private String status;
	
	public Contract(String contractNo, String customerNo, String blockNo, String lotNo, String lotArea, String duration,
			String price, String totalPrice, String payment, String balance, String dateStart, String dateEnd,
			String sellerNo, String agentNo, String status) {
		super();
		this.contractNo = contractNo;
		this.customerNo = customerNo;
		this.blockNo = blockNo;
		this.lotNo = lotNo;
		this.lotArea = lotArea;
		this.duration = duration;
		this.price = price;
		this.totalPrice = totalPrice;
		this.payment = payment;
		this.balance = balance;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.sellerNo = sellerNo;
		this.agentNo = agentNo;
		this.status = status;
	}
	
	
	public String getStatus() {
		return status;
	}




	public void setStatus(String status) {
		this.status = status;
	}




	public String getContractNo() {
		return contractNo;
	}


	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}


	public String getCustomerNo() {
		return customerNo;
	}


	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}


	public String getBlockNo() {
		return blockNo;
	}


	public void setBlockNo(String blockNo) {
		this.blockNo = blockNo;
	}


	public String getLotNo() {
		return lotNo;
	}


	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}


	public String getLotArea() {
		return lotArea;
	}


	public void setLotArea(String lotArea) {
		this.lotArea = lotArea;
	}


	public String getDuration() {
		return duration;
	}


	public void setDuration(String duration) {
		this.duration = duration;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public String getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}


	public String getPayment() {
		return payment;
	}


	public void setPayment(String payment) {
		this.payment = payment;
	}


	public String getBalance() {
		return balance;
	}


	public void setBalance(String balance) {
		this.balance = balance;
	}


	public String getDateStart() {
		return dateStart;
	}


	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}


	public String getDateEnd() {
		return dateEnd;
	}


	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}


	public String getSellerNo() {
		return sellerNo;
	}


	public void setSellerNo(String sellerNo) {
		this.sellerNo = sellerNo;
	}


	public String getAgentNo() {
		return agentNo;
	}


	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}
	
	
	

}
