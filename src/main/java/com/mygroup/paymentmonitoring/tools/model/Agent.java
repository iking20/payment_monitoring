package com.mygroup.paymentmonitoring.tools.model;

public class Agent {
	
	private String agentNumber;
	private String agentLastName;
	private String agentFirstName;
	private String agentMiddleName;
	private String agentPhoneNumber;
	private String agentCategory;
	

	public Agent(String agentNumber, String agentLastName, String agentFirstName, String agentMiddleName,
			String agentPhoneNumber, String agentCategory) {
		
		this.agentNumber = agentNumber;
		this.agentLastName = agentLastName;
		this.agentFirstName = agentFirstName;
		this.agentMiddleName = agentMiddleName;
		this.agentPhoneNumber = agentPhoneNumber;
		this.agentCategory = agentCategory;
	}
	
	


	public String getAgentCategory() {
		return agentCategory;
	}




	public void setAgentCategory(String agentCategory) {
		this.agentCategory = agentCategory;
	}




	public String getAgentNumber() {
		return agentNumber;
	}


	public void setAgentNumber(String agentNumber) {
		this.agentNumber = agentNumber;
	}


	public String getAgentLastName() {
		return agentLastName;
	}


	public void setAgentLastName(String agentLastName) {
		this.agentLastName = agentLastName;
	}


	public String getAgentFirstName() {
		return agentFirstName;
	}


	public void setAgentFirstName(String agentFirstName) {
		this.agentFirstName = agentFirstName;
	}


	public String getAgentMiddleName() {
		return agentMiddleName;
	}


	public void setAgentMiddleName(String agentMiddleName) {
		this.agentMiddleName = agentMiddleName;
	}


	public String getAgentPhoneNumber() {
		return agentPhoneNumber;
	}


	public void setAgentPhoneNumber(String agentPhoneNumber) {
		this.agentPhoneNumber = agentPhoneNumber;
	}
	
	
	
	
}
