package com.mygroup.paymentmonitoring.tools.model;

public class SellerAgentInfo {
	
	private String SellerAgentNo;
	private String SellerAgentName;
	private String SellerAgentCommission;

	public String getSellerAgentNo() {
		return SellerAgentNo;
	}


	public void setSellerAgentNo(String sellerAgentNo) {
		SellerAgentNo = sellerAgentNo;
	}


	public String getSellerAgentName() {
		return SellerAgentName;
	}


	public void setSellerAgentName(String sellerAgentName) {
		SellerAgentName = sellerAgentName;
	}


	public String getSellerAgentCommission() {
		return SellerAgentCommission;
	}


	public void setSellerAgentCommission(String sellerAgentCommission) {
		SellerAgentCommission = sellerAgentCommission;
	}

	

	public SellerAgentInfo(String sellerAgentNo, String sellerAgentName, String sellerAgentCommission
			) {
		
		SellerAgentNo = sellerAgentNo;
		SellerAgentName = sellerAgentName;
		SellerAgentCommission = sellerAgentCommission;
	}




	
	
	
	
	

}
