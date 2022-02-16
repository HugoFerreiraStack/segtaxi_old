package br.com.seg.maxipago.datacontract.transactional;

public class CreditCard {

	// Attributes
	private String number;
	private String expMonth;
	private String expYear;
	private String cvvInd;
	private String cvvNumber;
	private String eCommInd;

	// Specials Methods
	public CreditCard() {
		this.eCommInd = "eci";
	}

	// Getters and Setters
	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getExpMonth() {
		return this.expMonth;
	}

	public void setExpMonth(String expMonth) {
		this.expMonth = expMonth;
	}

	public String getExpYear() {
		return this.expYear;
	}

	public void setExpYear(String expYear) {
		this.expYear = expYear;
	}

	public String getCvvInd() {
		return this.cvvInd;
	}

	public void setCvvInd(String cvvInd) {
		this.cvvInd = cvvInd;
	}

	public String getCvvNumber() {
		return this.cvvNumber;
	}

	public void setCvvNumber(String cvvNumber) {
		this.cvvNumber = cvvNumber;
	}

	public String getECommInd() {
		return this.eCommInd;
	}
}