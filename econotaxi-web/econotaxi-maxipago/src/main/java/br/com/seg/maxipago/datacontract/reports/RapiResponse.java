package br.com.seg.maxipago.datacontract.reports;

import br.com.seg.maxipago.datacontract.ResponseBase;

public class RapiResponse extends ResponseBase {
	//Attributes 
    private HeaderResponse header;
    private ReportResult result;
	
  //Getters and Setters
    public HeaderResponse getHeader() {
		return header;
	}
	public ReportResult getResult() {
		return result;
	}
}