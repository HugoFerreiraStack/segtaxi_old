package br.com.seg.maxipago.datacontract.reports;

public class ReportResult {

	//Attributes 
    private String requestToken;
    private String statusMessage;
    private ResultSetInfo resultSetInfo;
    private Records records;
    
    //Getters
	public String getRequestToken() {
		return requestToken;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public ResultSetInfo getResultSetInfo() {
		return resultSetInfo;
	}

	public Records getRecords() {
		return records;
	}
}