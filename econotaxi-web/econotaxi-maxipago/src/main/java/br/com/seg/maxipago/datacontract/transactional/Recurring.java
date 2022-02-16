package br.com.seg.maxipago.datacontract.transactional;

public class Recurring {
	//Attributes
	private String action;
	private String startDate;
	private String period;
	private String frequency;
	private String installments;
	private String failureThreshold;
	private String processorID;
	private String nextFireDate;
	private String fireDay;
	private String lastDate;
	private double lastAmount;
	
	//Getters and Setters 	
	public void setProcessorID(String processorID) {
		this.processorID = processorID;
	}
	public String getNextFireDate() {
		return nextFireDate;
	}
	public void setNextFireDate(String nextFireDate) {
		this.nextFireDate = nextFireDate;
	}
	public String getFireDay() {
		return fireDay;
	}
	public void setFireDay(String fireDay) {
		this.fireDay = fireDay;
	}
	public String getLastDate() {
		return lastDate;
	}
	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}
	public double getLastAmount() {
		return lastAmount;
	}
	public void setLastAmount(double lastAmount) {
		this.lastAmount = lastAmount;
	}
	public String getProcessorID() {
		return processorID;
	}
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getPeriod() {
		return period;
	}
	
	public void setPeriod(String period) {
		this.period = period;
	}
	
	public String getFrequency() {
		return frequency;
	}
	
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	public String getInstallments() {
		return installments;
	}
	
	public void setInstallments(String installments) {
		this.installments = installments;
	}
	
	public String getFailureThreshold() {
		return failureThreshold;
	}
	
	public void setFailureThreshold(String failureThreshold) {
		this.failureThreshold = failureThreshold;
	}	
}