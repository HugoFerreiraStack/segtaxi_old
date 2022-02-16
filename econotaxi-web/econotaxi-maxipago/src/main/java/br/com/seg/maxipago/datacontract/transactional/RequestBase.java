package br.com.seg.maxipago.datacontract.transactional;

public class RequestBase {
	//Attributes
	private String processorID;
	private String fraudCheck;
	private Authentication authentication;
	private String referenceNum;
	private String ipAddress;
	private Billing billing;
	private Shipping shipping;
	private TransactionDetail transactionDetail;
	private Payment payment;
	private String customerIdExt;
	private String userAgent;
	private Recurring recurring;
	private SaveOnFile saveOnFile;
	private ItemList itemList;
	private FraudDetails fraudDetails;


	public FraudDetails getFraudDetails() {
		return fraudDetails;
	}

	public void setFraudDetails(FraudDetails fraudDetails) {
		this.fraudDetails = fraudDetails;
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	public String getUserAgent() {
		return userAgent;
	}

	// Construct
	public RequestBase() {
		this.payment = new Payment();
		this.transactionDetail = new TransactionDetail();
	}

	// Getters and Setters
	
	public String getProcessorID() {
		return processorID;
	}

	public void setProcessorID(String processorID) {
		this.processorID = processorID;
	}

	public String getFraudCheck() {
		return fraudCheck;
	}

	public void setFraudCheck(String fraudCheck) {
		this.fraudCheck = fraudCheck;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

	public String getReferenceNum() {
		return this.referenceNum;
	}

	public void setReferenceNum(String referenceNum) {
		this.referenceNum = referenceNum;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Billing getBilling() {
		return this.billing;
	}

	public void setBilling(Billing billing) {
		this.billing = billing;
	}

	public Shipping getShipping() {
		return this.shipping;
	}

	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}

	public TransactionDetail getTransactionDetail() {
		return this.transactionDetail;
	}

	public void setTransactionDetail(TransactionDetail transactionDetail) {
		this.transactionDetail = transactionDetail;
	}

	public Payment getPayment() {
		return this.payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public String getCustomerIdExt() {
		return this.customerIdExt;
	}

	public void setCustomerIdExt(String customerIdExt) {
		this.customerIdExt = customerIdExt;
	}

	public Recurring getRecurring() {
		return recurring;
	}

	public void setRecurring(Recurring recurring) {
		this.recurring = recurring;
	}

	public SaveOnFile getSaveOnFile() {
		return saveOnFile;
	}

	public void setSaveOnFile(SaveOnFile saveOnFile) {
		this.saveOnFile = saveOnFile;
	}

	public ItemList getItemList() {
		return itemList;
	}

	public void setItemList(ItemList itemList) {
		this.itemList = itemList;
	}
	
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
}