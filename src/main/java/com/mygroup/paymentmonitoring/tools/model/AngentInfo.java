package com.mygroup.paymentmonitoring.tools.model;

public class AngentInfo {
	
	private String AgentNo;
	private String AgentName;
	private String AgentCommission;
	private String AgentDescription;
	private String AgentDate;
	


	public AngentInfo(String agentNo, String agentName, String agentCommission, String agentDescription, String agentDate) {
		
		AgentNo = agentNo;
		AgentName = agentName;
		AgentCommission = agentCommission;
		AgentDescription = agentDescription;
		AgentDate = agentDate;
	}
	
	


	public String getAgentDate() {
		return AgentDate;
	}


	public void setAgentDate(String agentDate) {
		AgentDate = agentDate;
	}



	public String getAgentDescription() {
		return AgentDescription;
	}


	public void setAgentDescription(String agentDescription) {
		AgentDescription = agentDescription;
	}


	public String getAgentNo() {
		return AgentNo;
	}


	public void setAgentNo(String agentNo) {
		AgentNo = agentNo;
	}


	public String getAgentName() {
		return AgentName;
	}


	public void setAgentName(String agentName) {
		AgentName = agentName;
	}


	public String getAgentCommission() {
		return AgentCommission;
	}


	public void setAgentCommission(String agentCommission) {
		AgentCommission = agentCommission;
	}



}
