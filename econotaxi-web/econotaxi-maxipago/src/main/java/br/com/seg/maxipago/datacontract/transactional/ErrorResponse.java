package br.com.seg.maxipago.datacontract.transactional;

import br.com.seg.maxipago.datacontract.ResponseBase;

public class ErrorResponse extends ResponseBase {
	
	//Attributes
	private String errorCode;
	private String errorMsg;
	
	//Getters and Setters
	public String getErrorCode(){
		return this.errorCode;
	}
	
	public String getErrorMsg(){
		return this.errorMsg;
	}	
}