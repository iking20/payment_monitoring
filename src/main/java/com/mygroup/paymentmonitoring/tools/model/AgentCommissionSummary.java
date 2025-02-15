package com.mygroup.paymentmonitoring.tools.model;


public class AgentCommissionSummary {
	
	private String agentNo;
	private String agentName;
	private String agentCommAmount;
	
	
	public AgentCommissionSummary(String agentNo, String agentName, String agentCommAmount) {

		this.agentNo = agentNo;
		this.agentName = agentName;
		this.agentCommAmount = agentCommAmount;
	}


	public String getAgentNo() {
		return agentNo;
	}


	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}


	public String getAgentName() {
		return agentName;
	}


	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}


	public String getAgentCommAmount() {
		return agentCommAmount;
	}


	public void setAgentCommAmount(String agentCommAmount) {
		this.agentCommAmount = agentCommAmount;
	}
	
	
	

}
