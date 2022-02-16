package br.com.seg.maxipago.datacontract.reports;

public class FilterOptions {

	//Attributes 
	private String transactionId;
	private String period;
    private String pageSize;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private String orderByName;
    private String orderByDirection;
    private String startRecordNumber;
    private String endRecordNumber;
    private String pageNumber;
    private String pageToken;
    private String orderId;	
    
    //Getters and Setters
    public String getTransactionId() {
		return transactionId;
	}
    public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
    public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getOrderByName() {
		return orderByName;
	}
	public void setOrderByName(String orderByName) {
		this.orderByName = orderByName;
	}
	public String getOrderByDirection() {
		return orderByDirection;
	}
	public void setOrderByDirection(String orderByDirection) {
		this.orderByDirection = orderByDirection;
	}
	public String getStartRecordNumber() {
		return startRecordNumber;
	}
	public void setStartRecordNumber(String startRecordNumber) {
		this.startRecordNumber = startRecordNumber;
	}
	public String getEndRecordNumber() {
		return endRecordNumber;
	}
	public void setEndRecordNumber(String endRecordNumber) {
		this.endRecordNumber = endRecordNumber;
	}
	public String getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getPageToken() {
		return pageToken;
	}
	public void setPageToken(String pageToken) {
		this.pageToken = pageToken;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}