package com.mygroup.paymentmonitoring.tools.model;

public class ActualAgentCommission {
	
	private String comm_no;
	private String comm_date;
	private String comm_agentNo;
	private String comm_agentName;
	private String comm_descp;
	private String comm_amount;
	
	public ActualAgentCommission(String comm_no, String comm_date, String comm_agentNo, String comm_agentName,
			String comm_descp, String comm_amount) {
		
		this.comm_no = comm_no;
		this.comm_date = comm_date;
		this.comm_agentNo = comm_agentNo;
		this.comm_agentName = comm_agentName;
		this.comm_descp = comm_descp;
		this.comm_amount = comm_amount;
	}

	public String getComm_no() {
		return comm_no;
	}

	public void setComm_no(String comm_no) {
		this.comm_no = comm_no;
	}

	public String getComm_date() {
		return comm_date;
	}

	public void setComm_date(String comm_date) {
		this.comm_date = comm_date;
	}

	public String getComm_agentNo() {
		return comm_agentNo;
	}

	public void setComm_agentNo(String comm_agentNo) {
		this.comm_agentNo = comm_agentNo;
	}

	public String getComm_agentName() {
		return comm_agentName;
	}

	public void setComm_agentName(String comm_agentName) {
		this.comm_agentName = comm_agentName;
	}

	public String getComm_descp() {
		return comm_descp;
	}

	public void setComm_descp(String comm_descp) {
		this.comm_descp = comm_descp;
	}

	public String getComm_amount() {
		return comm_amount;
	}

	public void setComm_amount(String comm_amount) {
		this.comm_amount = comm_amount;
	}
	
	
}
