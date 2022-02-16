package br.com.seg.maxipago.datacontract.nontransactional;

import br.com.seg.maxipago.datacontract.Verification;

public class ApiRequest {

	// Attributes
	private Verification verification;
	private String command;
	private CommandRequest request;

	//Construct
	public ApiRequest(String merchantId, String merchantKey) {
		this.verification = new Verification(merchantId, merchantKey);
	}

	// Getters and Setters
	public String getCommand() {
		return command;
	}

	public Verification getVerification() {
		return verification;
	}

	public CommandRequest getRequest() {
		return request;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public void setRequest(CommandRequest request) {
		this.request = request;
	}
}
