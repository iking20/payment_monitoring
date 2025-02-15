package com.mygroup.paymentmonitoring.tools.model;

public class Expenses {
	
	private String exp_no;
	private String exp_date;
	private String exp_cat;
	private String exp_title;
	private String exp_descp;
	private String exp_amount;
	
	
	public Expenses(String exp_no, String exp_date, String exp_cat, String exp_title, String exp_descp,
			String exp_amount) {
		
		this.exp_no = exp_no;
		this.exp_date = exp_date;
		this.exp_cat = exp_cat;
		this.exp_title = exp_title;
		this.exp_descp = exp_descp;
		this.exp_amount = exp_amount;
	}


	public String getExp_no() {
		return exp_no;
	}


	public void setExp_no(String exp_no) {
		this.exp_no = exp_no;
	}


	public String getExp_date() {
		return exp_date;
	}


	public void setExp_date(String exp_date) {
		this.exp_date = exp_date;
	}


	public String getExp_cat() {
		return exp_cat;
	}


	public void setExp_cat(String exp_cat) {
		this.exp_cat = exp_cat;
	}


	public String getExp_title() {
		return exp_title;
	}


	public void setExp_title(String exp_title) {
		this.exp_title = exp_title;
	}


	public String getExp_descp() {
		return exp_descp;
	}


	public void setExp_descp(String exp_descp) {
		this.exp_descp = exp_descp;
	}


	public String getExp_amount() {
		return exp_amount;
	}


	public void setExp_amount(String exp_amount) {
		this.exp_amount = exp_amount;
	}
	
	
	

}
