package br.com.seg.maxipago.datacontract.nontransactional;

import br.com.seg.maxipago.datacontract.ResponseBase;

public class ApiResponse extends ResponseBase {

	// Attributes
	private String errorCode; // Command return code. Success = 0
	private String errorMessage; // Error message, if there is one
	private String command; // Confirmation of the command sent
	private String time;// Transaction date/time in epoch format
	private ApiResult result;

	// Getters
	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getCommand() {
		return command;
	}

	public String getTime() {
		return time;
	}

	public ApiResult getResult() {
		return result;
	}

}
