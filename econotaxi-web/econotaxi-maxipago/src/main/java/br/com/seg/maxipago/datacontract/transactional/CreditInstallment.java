package br.com.seg.maxipago.datacontract.transactional;

public class CreditInstallment {
	
	//Attributes 
	private String numberOfInstallments;
	private String chargeInterest;
	
	//Getters and Setters
	public String getNumberOfInstallments() {
		return numberOfInstallments;
	}
	public void setNumberOfInstallments(String numberOfInstallments) {
		this.numberOfInstallments = numberOfInstallments;
	}
	public String getChargeInterest() {
		return chargeInterest;
	}
	public void setChargeInterest(String chargeInterest) {
		this.chargeInterest = chargeInterest;
	}	
}