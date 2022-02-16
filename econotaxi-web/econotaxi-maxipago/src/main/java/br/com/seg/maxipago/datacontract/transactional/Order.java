package br.com.seg.maxipago.datacontract.transactional;

public class Order {

	//Attributes
	private RequestBase sale;
	private RequestBase auth;
	private CaptureOrReturn capture;
	private CaptureOrReturn _return;
	private Void _void;
	private RequestBase recurringPayment;
	private ClientData clientData;

	//Specials Methods 
	public Order(){
		this.clientData = new ClientData();
	}
	
	//Getters and Setters
	public ClientData getClientData() {
		return this.clientData;
	}
	
	public RequestBase getSale() {
		return this.sale;
	}

	public void setSale(RequestBase sale) {
		this.sale = sale;
	}

	public RequestBase getAuth() {
		return this.auth;
	}

	public void setAuth(RequestBase auth) {
		this.auth = auth;
	}

	public CaptureOrReturn getCapture() {
		return this.capture;
	}

	public void setCapture(CaptureOrReturn capture) {
		this.capture = capture;
	}

	public CaptureOrReturn getReturn() {
		return this._return;
	}

	public void setReturn(CaptureOrReturn _return) {
		this._return = _return;
	}

	public Void getVoid() {
		return this._void;
	}

	public void setVoid(Void _void) {
		this._void = _void;
	}

	public RequestBase getRecurringPayment(){
	 return this.recurringPayment;
	}
	
	public void setRecurringPayment(RequestBase recurringPayment){
		this.recurringPayment = recurringPayment;
	}
}