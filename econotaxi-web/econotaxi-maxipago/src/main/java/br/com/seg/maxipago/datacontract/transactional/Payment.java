package br.com.seg.maxipago.datacontract.transactional;

public class Payment {
	// Attributes
	private CreditInstallment creditInstallment;
	private double chargeTotal;
	private String currencyCode;
	private String softDescriptor;
	private Double iataFee;
	@SuppressWarnings("unused")
	private CardInfo cardInfo;

	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}

	// Getters and Setters
	public CreditInstallment getCreditInstallment() {
		return creditInstallment;
	}

	public void setCreditInstallment(CreditInstallment creditInstallment) {
		this.creditInstallment = creditInstallment;
	}

	public double getChargeTotal() {
		return this.chargeTotal;
	}

	public void setChargeTotal(double chargeTotal) {
		this.chargeTotal = chargeTotal;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getSoftDescriptor() {
		return softDescriptor;
	}

	public void setSoftDescriptor(String softDescriptor) {
		this.softDescriptor = softDescriptor;
	}

	public Double getIataFee() {
		return iataFee;
	}

	public void setIataFee(Double iataFee) {
		this.iataFee = iataFee;
	}
}