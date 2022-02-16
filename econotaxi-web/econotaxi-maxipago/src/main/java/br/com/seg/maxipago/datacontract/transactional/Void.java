package br.com.seg.maxipago.datacontract.transactional;

public class Void {
	//Attributes
	private String transactionID;
	private String ipAddress;

	//Getters and Setters
	public String getTransactionID() {
		return this.transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}