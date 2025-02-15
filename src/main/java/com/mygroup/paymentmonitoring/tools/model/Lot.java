package com.mygroup.paymentmonitoring.tools.model;

public class Lot {
	
	private String blockNum;
	private String lotNum;
	private String lotAreasqm;
	private String pricesqm;
	private String TotalPricesqm;
	private boolean lotAvail;
	private String colorCode;
	private String lotCode;
	
	public Lot(String blockNum, String lotNum, String lotAreasqm, String pricesqm, String totalPricesqm,
			boolean lotAvail, String colorCode, String lotCode) {
	
		this.blockNum = blockNum;
		this.lotNum = lotNum;
		this.lotAreasqm = lotAreasqm;
		this.pricesqm = pricesqm;
		TotalPricesqm = totalPricesqm;
		this.lotAvail = lotAvail;
		this.colorCode = colorCode;
		this.lotCode = lotCode;
	}
	
	


	public String getLotCode() {
		return lotCode;
	}

	public void setLotCode(String lotCode) {
		this.lotCode = lotCode;
	}




	public String getColorCode() {
		return colorCode;
	}


	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}


	public String getTotalPricesqm() {
		return TotalPricesqm;
	}


	public void setTotalPricesqm(String totalPricesqm) {
		TotalPricesqm = totalPricesqm;
	}


	public String getBlockNum() {
		return blockNum;
	}


	public void setBlockNum(String blockNum) {
		this.blockNum = blockNum;
	}


	public String getLotNum() {
		return lotNum;
	}


	public void setLotNum(String lotNum) {
		this.lotNum = lotNum;
	}


	public String getLotAreasqm() {
		return lotAreasqm;
	}


	public void setLotAreasqm(String lotAreasqm) {
		this.lotAreasqm = lotAreasqm;
	}


	public String getPricesqm() {
		return pricesqm;
	}


	public void setPricesqm(String pricesqm) {
		this.pricesqm = pricesqm;
	}


	public boolean getLotAvail() {
		return lotAvail;
	}


	public void setLotAvail(boolean lotAvail) {
		this.lotAvail = lotAvail;
	}
	
	
	
}
