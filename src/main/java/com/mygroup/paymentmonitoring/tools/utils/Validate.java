package com.mygroup.paymentmonitoring.tools.utils;

import java.util.List;

import com.mygroup.paymentmonitoring.tools.model.ActualAgentCommission;
import com.mygroup.paymentmonitoring.tools.model.Agent;
import com.mygroup.paymentmonitoring.tools.model.Contract;
import com.mygroup.paymentmonitoring.tools.model.Customer;
import com.mygroup.paymentmonitoring.tools.model.Expenses;
import com.mygroup.paymentmonitoring.tools.model.Lot;
import com.mygroup.paymentmonitoring.tools.model.Seller;

public class Validate {
	
	public static boolean checkCustomer(List<Customer> customerList) {
		
		if(customerList.get(0).getCustNo() == "" || customerList.get(0).getFirstName()== ""
				|| customerList.get(0).getFirstName() == "" || customerList.get(0).getFirstName() == ""
				|| customerList.get(0).getBirthDay() == "" || customerList.get(0).getPhoneNo() == ""
				|| customerList.get(0).getEmailAdd() == "") {
			
			return true;
			
		}else {
			return false;
		}	
		
	}

	public static boolean checkContract(List<Contract> contractList) {
		// TODO Auto-generated method stub
		
		if(contractList.get(0).getAgentNo() == "" || contractList.get(0).getBalance() == ""
				|| contractList.get(0).getBlockNo() == null || contractList.get(0).getContractNo() == null
				|| contractList.get(0).getCustomerNo() == null || contractList.get(0).getDateEnd() == null
				|| contractList.get(0).getDateStart() == null || contractList.get(0).getDuration() == ""
				|| contractList.get(0).getLotArea() == "" || contractList.get(0).getLotNo() == ""
				|| contractList.get(0).getPayment() == "" || contractList.get(0).getPrice() == ""
				|| contractList.get(0).getSellerNo() == "" || contractList.get(0).getTotalPrice() =="") {
	
			return true;
		}else {
			return false;
		}
	}

	public static boolean checkLot(List<Lot> lotList) {
		// TODO Auto-generated method stub
		
		if(lotList.get(0).getBlockNum() == "" || lotList.get(0).getLotNum() == "" ||
				lotList.get(0).getLotAreasqm() == "" || lotList.get(0).getPricesqm() == "" ||
				lotList.get(0).getTotalPricesqm() == "" || lotList.get(0).getColorCode() == null) {
	
			return true;
			
		}else {
			//System.out.println("Color Code: " + lotList.get(0).getColorCode());
			return false;
		}
		
	}

	public static boolean checkSeller(List<Seller> sellerList) {
		// TODO Auto-generated method stub
		
		if(sellerList.get(0).getSellerNumber() == "" || sellerList.get(0).getSellerLastName() == "" 
				|| sellerList.get(0).getSellerFirstName() == "" || sellerList.get(0).getSellerMiddleName() == "") {
			
			return true;
			
		}else {
			return false;
		}	
	}

	public static boolean checkAgent(List<Agent> agentList) {
		// TODO Auto-generated method stub
		
		if(agentList.get(0).getAgentNumber() == "" || agentList.get(0).getAgentLastName() == "" 
				|| agentList.get(0).getAgentFirstName() == "" || agentList.get(0).getAgentMiddleName() == ""
				|| agentList.get(0).getAgentPhoneNumber() == "" || agentList.get(0).getAgentCategory() == null) {
			
			return true;
			
		}else {
			return false;
		}
	}

	public static boolean checkExpense(List<Expenses> expenseList) {
		// TODO Auto-generated method stub
		if(expenseList.get(0).getExp_no() == "" || expenseList.get(0).getExp_date() == "" 
				|| expenseList.get(0).getExp_cat() == null || expenseList.get(0).getExp_title() == ""
				|| expenseList.get(0).getExp_descp() == "" || expenseList.get(0).getExp_amount() == "") {
			
			return true;
			
		}else {
			return false;
		}
	}

	public static boolean checkCommission(List<ActualAgentCommission> commList) {
		// TODO Auto-generated method stub
		if(commList.get(0).getComm_no() == "" || commList.get(0).getComm_date() == null 
				|| commList.get(0).getComm_agentNo() == "" || commList.get(0).getComm_agentName() == ""
				|| commList.get(0).getComm_descp() == "" || commList.get(0).getComm_agentNo() == "") {
			
			return true;
			
		}else {
			return false;
		}
	}

	public static boolean checkCustomerInfo(List<Customer> customerList) {
		// TODO Auto-generated method stub
		if(customerList.get(0).getCustNo() == "" || customerList.get(0).getLastName() == "" 
				|| customerList.get(0).getFirstName() == "" || customerList.get(0).getMiddleName() == ""
				|| customerList.get(0).getBirthDay() == null || customerList.get(0).getPhoneNo() == ""
				|| customerList.get(0).getEmailAdd() == "") {
			
			return true;
			
		}else {
			return false;
		}
	}

}
