package br.com.seg.maxipago.datacontract.transactional;

public class OnFile {
	//Attributes
	private String customerId;
	private String token;
	private String cvvNumber;
	
	//Getters and Setters
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getCvvNumber() {
		return cvvNumber;
	}
	public void setCvvNumber(String cvvNumber) {
		this.cvvNumber = cvvNumber;
	}
}