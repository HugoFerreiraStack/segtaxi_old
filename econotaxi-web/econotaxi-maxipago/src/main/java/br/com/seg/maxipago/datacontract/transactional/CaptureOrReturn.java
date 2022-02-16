package br.com.seg.maxipago.datacontract.transactional;

public class CaptureOrReturn {
	//Attributes 
	private String orderID;
	private String referenceNum;
	private Payment payment;
	
	//Special Methods
	public CaptureOrReturn() {
		this.payment = new Payment();
	}
	
	//Getters and Setters
	public String getOrderID() {
		return this.orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getReferenceNum() {
		return this.referenceNum;
	}

	public void setReferenceNum(String referenceNum) {
		this.referenceNum = referenceNum;
	}

	public Payment getPayment() {
		return this.payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
}