package br.com.seg.maxipago.datacontract.transactional;

public class PayType {

	//Attributes
	private CreditCard creditCard;
	private DebitCard debitCard;
	private OnFile onFile;
	private Boleto boleto;
	private OnlineDebit onlineDebit;
	private EWallet eWallet;

	//Getters and Setters	
	public DebitCard getDebitCard() {
		return debitCard;
	}
	
	public void setDebitCard(DebitCard debitCard) {
		this.debitCard = debitCard;
	}
	
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public OnFile getOnFile() {
		return onFile;
	}

	public void setOnFile(OnFile onFile) {
		this.onFile = onFile;
	}
	
	public Boleto getBoleto() { 
		return this.boleto; 
	}
	 
	public void setBoleto(Boleto boleto) { 
		this.boleto = boleto; 
	}
	
	public OnlineDebit getOnlineDebit() { 
		return this.onlineDebit; 
	}
	 
	public void setOnlineDebit(OnlineDebit onlineDebit) { 
		this.onlineDebit = onlineDebit; 
	}

	public EWallet getEWallet() {
		return eWallet;
	}

	public void setEWallet(EWallet eWallet) {
		this.eWallet = eWallet;
	}	
}