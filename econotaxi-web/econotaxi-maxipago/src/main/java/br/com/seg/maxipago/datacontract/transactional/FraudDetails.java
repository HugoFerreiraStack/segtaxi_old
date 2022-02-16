package br.com.seg.maxipago.datacontract.transactional;

public class FraudDetails {
	
	private String fraudProcessorID;
	private String captureOnLowRisk;
	private String voidOnHighRisk;
	private String fraudToken;
	private String websiteId;
	
	public String getFraudProcessorID() {
		return fraudProcessorID;
	}
	public void setFraudProcessorID(String fraudProcessorID) {
		this.fraudProcessorID = fraudProcessorID;
	}
	public String getCaptureOnLowRisk() {
		return captureOnLowRisk;
	}
	public void setCaptureOnLowRisk(String captureOnLowRisk) {
		this.captureOnLowRisk = captureOnLowRisk;
	}
	public String getVoidOnHighRisk() {
		return voidOnHighRisk;
	}
	public void setVoidOnHighRisk(String voidOnHighRisk) {
		this.voidOnHighRisk = voidOnHighRisk;
	}
	public String getFraudToken() {
		return fraudToken;
	}
	public void setFraudToken(String fraudToken) {
		this.fraudToken = fraudToken;
	}
	public String getWebsiteId() {
		return websiteId;
	}
	public void setWebsiteId(String websiteId) {
		this.websiteId = websiteId;
	}
}