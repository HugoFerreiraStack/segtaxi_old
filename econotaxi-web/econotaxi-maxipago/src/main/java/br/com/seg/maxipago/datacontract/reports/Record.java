package br.com.seg.maxipago.datacontract.reports;

public class Record {

	private String referenceNumber;
	String taxAmount;
	String shippingAmount;
	String userId;
	String bankRoutingNumber;
	String achAccountNumber;
	
	//Campos de retorno de uma consulta de transacao
    private String approvalCode; // Codigo de autorizacao da Adquirente
    private String comments; //Comentarios
    private String companyName; //Nome da empresa
    private String creditCardType; // Codigo de meio de pagamento
    private String customerId; //ID do cliente
    private String orderId; //ID do Pedido
    private String processorID; //Id da Processadora
    private String paymentType; //Meio de pagamento utilizado
    private String recurringPaymentFlag; //Flag de pagamento recorrente
    private String responseCode; //Codigo de resposta da maxiPago!
    private String transactionAmount; // Valor do pedido, em centavos 
    private String transactionId; // ID da transacao 
    private String transactionStatus; //Status da transacao
    private String transactionState; //Status da transacao
    private String transactionType;//Operacao realizada
    private String transactionDate; //Data da transacao
    private String avsResponseCode;
    private String billingAddress1;
    private String billingAddress2;
    private String billingCity;
    private String billingCountry;
    private String billingEmail;
    private String billingName;
    private String billingPhone;
    private String billingState;
    private String billingZip;

    //Campos de retorno de uma consulta de boleto
    private String boletoNumber; //Numero identificador do boleto ("Nosso Numero").
    private String expirationDate; //Data de vencimento do boleto. Formato mm/dd/aaaa.
    private String dateOfPayment; //Data de pagamento do boleto, se o banco a informou. Formato mm/dd/aaaa.
    private String dateOfFunding; // Data de liquidacao do valor, se o banco a informou. Formato mm/dd/aaaa.
    private String bankOfPayment; //Codigo FEBRABAN do banco onde foi feito o pagamento.
    private String branchOfPayment; // Agencia onde foi feito o pagamento.
    private String paidAmount; //Valor pago pelo cliente.
    private String bankFee; //Taxa de cobranca do boleto, se o banco a informou.
    private String netAmount; //Valor liquido a receber (valor pago - taxa)
    private String returnCode; // Codigo de pagamento do boleto no banco.
    private String clearingCode; // Codigo de liquidacao do banco, se informado.
    
    private String customField1;
    private String customField2;
    private String customField3;
    private String customField4;
    private String customField5;
    private String boletoUrl;
    private String numberOfInstallments;
    private String chargeInterest;

    //Getters and Setters
    public String getReferenceNumber() {
		return referenceNumber;
	}
    
	public String getApprovalCode() {
		return approvalCode;
	}

	public String getComments() {
		return comments;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getCreditCardType() {
		return creditCardType;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public String getRecurringPaymentFlag() {
		return recurringPaymentFlag;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public String getTransactionAmount() {
		return transactionAmount;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}
	
	public String getTransactionState() {
		return transactionState;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public String getAvsResponseCode() {
		return avsResponseCode;
	}

	public String getBillingAddress1() {
		return billingAddress1;
	}

	public String getBillingAddress2() {
		return billingAddress2;
	}

	public String getBillingCity() {
		return billingCity;
	}

	public String getBillingCountry() {
		return billingCountry;
	}

	public String getBillingEmail() {
		return billingEmail;
	}

	public String getBillingName() {
		return billingName;
	}

	public String getBillingPhone() {
		return billingPhone;
	}

	public String getBillingState() {
		return billingState;
	}

	public String getBillingZip() {
		return billingZip;
	}

	public String getBoletoNumber() {
		return boletoNumber;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public String getDateOfPayment() {
		return dateOfPayment;
	}

	public String getDateOfFunding() {
		return dateOfFunding;
	}

	public String getBankOfPayment() {
		return bankOfPayment;
	}

	public String getBranchOfPayment() {
		return branchOfPayment;
	}

	public String getPaidAmount() {
		return paidAmount;
	}

	public String getBankFee() {
		return bankFee;
	}

	public String getNetAmount() {
		return netAmount;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public String getClearingCode() {
		return clearingCode;
	}

	public String getProcessorID() {
		return processorID;
	}
	
	public String getCustomField1() {
		return customField1;
	}

	public String getCustomField2() {
		return customField2;
	}

	public String getCustomField3() {
		return customField3;
	}

	public String getCustomField4() {
		return customField4;
	}

	public String getCustomField5() {
		return customField5;
	}

	public String getBoletoUrl() {
		return boletoUrl;
	}

	public String getNumberOfInstallments() {
		return numberOfInstallments;
	}

	public String getChargeInterest() {
		return chargeInterest;
	}	
}