package br.com.seg.maxipago.datacontract.transactional;

import br.com.seg.maxipago.datacontract.ResponseBase;

public class TransactionResponse extends ResponseBase {

	//Attributes 
	private String authCode;
	private String orderID;
	private String referenceNum;
	private String transactionID;
	private String transactionTimestamp;
	private String responseCode;
	private String responseMessage;
	private String avsResponseCode;
	private String cvvResponseCode;
	private String processorCode;
	private String processorMessage;
	private String errorMessage;
	private String processorReferenceNumber;
	private String processorTransactionID;
	private String boletoUrl;
	private String authenticationURL;
	private String fraudScore;
	private SaveOnFileResponse saveOnFile;
	private String onlineDebitUrl;
    
	//Atributo somente usando nos EUA, por isso nao tem get e set.
	//mas precisa existir aqui para evitar erro no parse do xml de resposta.
	@SuppressWarnings("unused")
	private String partiallyApprovedAmount;
	
	public String getAuthCode() {
		return this.authCode;
	}

	public String getOrderID() {
		return this.orderID;
	}

	public String getReferenceNum() {
		return this.referenceNum;
	}

	public String getTransactionID() {
		return this.transactionID;
	}

	public void setTransactionTimestamp(String transactionTimestamp) {
		this.transactionTimestamp = transactionTimestamp;
	}

	public String getTransactionTimestamp() {
		return this.transactionTimestamp;
	}

	public String getResponseCode() {
		return this.responseCode;
	}

	public String getResponseMessage() {
		return this.responseMessage;
	}

	public String getAvsResponseCode() {
		return this.avsResponseCode;
	}

	public String getCvvResponseCode() {
		return this.cvvResponseCode;
	}

	public String getProcessorCode() {
		return this.processorCode;
	}

	public String getProcessorMessage() {
		return this.processorMessage;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public String getProcessorReferenceNumber() {
		return processorReferenceNumber;
	}

	public String getProcessorTransactionID() {
		return processorTransactionID;
	}
	
	public String getBoletoUrl() {
		return boletoUrl;
	}

	public String getAuthenticationURL() {
		return authenticationURL;
	}

	public String getFraudScore() {
		return fraudScore;
	}

	public SaveOnFileResponse getSaveOnFile() {
		return saveOnFile;
	}
	
	public String getOnlineDebitURL() {
		return onlineDebitUrl;
	}	
}