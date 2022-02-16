package br.com.seg.maxipago.datacontract;

public class Verification {

	// Attributes
	private String merchantId;
	private String merchantKey;

	// Constructor Methods
	public Verification(String merchantId, String merchantKey) {
		this.merchantId = merchantId;
		this.merchantKey = merchantKey;
	}

	// Modifying Methods
	public String getMerchantId() {
		return this.merchantId;
	}

	public String getMerchantKey() {
		return this.merchantKey;
	}
}
