package br.com.seg.maxipago.datacontract.transactional;

public class TransactionDetail {

	//Attributes 
	private PayType payType;
	
	//Construct
	public TransactionDetail(){
		this.payType = new PayType();
	}

	//Getters and Setters
	public PayType getPayType() {
		return this.payType;
	}
}