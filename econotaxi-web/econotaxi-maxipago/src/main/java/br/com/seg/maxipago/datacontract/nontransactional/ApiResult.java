package br.com.seg.maxipago.datacontract.nontransactional;

public class ApiResult {

    //Attributes
	public String customerId; // Unique ID returned by maxiPago! after saving a customer profile
	public String token; // Token associated with the credit card number
	
	
	//Getters
    public String getCustomerId() {
		return customerId;
	}
	public String getToken() {
		return token;
	}	
}
