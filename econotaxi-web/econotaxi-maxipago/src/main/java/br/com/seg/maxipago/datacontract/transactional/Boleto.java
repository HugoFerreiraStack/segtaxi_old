package br.com.seg.maxipago.datacontract.transactional;

public class Boleto {
	
	//Attributes 
	private String expirationDate;
	private String number;
	private String instructions;
	
	//Getters and Setters
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
}