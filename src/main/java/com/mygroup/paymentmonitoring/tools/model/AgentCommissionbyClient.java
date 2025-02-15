package com.mygroup.paymentmonitoring.tools.model;

public class AgentCommissionbyClient {
	
	private String clientNo;
	private String clientName;
	private String clientPayment;
	private String sellerCommission;
	private String agentCommission;
	
	
	public AgentCommissionbyClient(String clientNo, String clientName, String clientPayment, String sellerCommission,
			String agentCommission) {
	
		this.clientNo = clientNo;
		this.clientName = clientName;
		this.clientPayment = clientPayment;
		this.sellerCommission = sellerCommission;
		this.agentCommission = agentCommission;
	}


	public String getClientNo() {
		return clientNo;
	}


	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}


	public String getClientName() {
		return clientName;
	}


	public void setClientName(String clientName) {
		this.clientName = clientName;
	}


	public String getClientPayment() {
		return clientPayment;
	}


	public void setClientPayment(String clientPayment) {
		this.clientPayment = clientPayment;
	}


	public String getSellerCommission() {
		return sellerCommission;
	}


	public void setSellerCommission(String sellerCommission) {
		this.sellerCommission = sellerCommission;
	}


	public String getAgentCommission() {
		return agentCommission;
	}


	public void setAgentCommission(String agentCommission) {
		this.agentCommission = agentCommission;
	}

}
