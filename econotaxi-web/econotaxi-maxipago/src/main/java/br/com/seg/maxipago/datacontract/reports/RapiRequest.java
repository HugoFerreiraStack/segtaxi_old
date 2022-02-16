package br.com.seg.maxipago.datacontract.reports;

import br.com.seg.maxipago.datacontract.Verification;

public class RapiRequest {

    //Attributes 
    private Verification verification;
    private String command;
    private ReportRequest request;
    
    //Special Methods
    public RapiRequest(String merchantId, String merchantKey) {
        this.verification = new Verification(merchantId, merchantKey);
        this.request = new ReportRequest();
    }
	
  //Getters and Setters
    public Verification getVerification() {
		return verification;
	}
	public void setVerification(Verification verification) {
		this.verification = verification;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public ReportRequest getRequest() {
		return request;
	}
	public void setReportRequest(ReportRequest request) {
		this.request = request;
	}
}