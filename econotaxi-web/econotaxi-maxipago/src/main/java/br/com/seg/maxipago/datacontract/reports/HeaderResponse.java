package br.com.seg.maxipago.datacontract.reports;

public class HeaderResponse {
	
	//Attributes 
    private String errorCode;
    private String errorMsg;
    private String command;
    private String time;

  //Getters and Setters
    public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public String getCommand() {
		return command;
	}

	public String getTime() {
		return time;
	}
}