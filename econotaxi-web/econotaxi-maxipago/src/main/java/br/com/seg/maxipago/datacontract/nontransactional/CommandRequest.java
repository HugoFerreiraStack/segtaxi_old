package br.com.seg.maxipago.datacontract.nontransactional;

import br.com.seg.maxipago.datacontract.transactional.BillingInfo;
import br.com.seg.maxipago.datacontract.transactional.CardInfo;
import br.com.seg.maxipago.datacontract.transactional.PaymentInfo;
import br.com.seg.maxipago.datacontract.transactional.Recurring;
import br.com.seg.maxipago.datacontract.transactional.ShippingInfo;

public class CommandRequest {
	
	//Attributes
    private String customerId; // ID Unico do cadastro, retornado quando o cliente foi adicionado na base.
    private String customerIdExt;
    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String email;
    private String dob; // Data de nascimento do cliente
    private String ssn; // CPF ou CNPJ do cliente
    private String sex; // F = Feminino | M = Masculino
    private String creditCardNumber;
    private String expirationMonth;
    private String expirationYear;
    private String billingName;
    private String billingAddress1;
    private String billingAddress2;
    private String billingCity;
    private String billingState;
    private String billingZip;
    private String billingCountry;
    private String billingPhone;
    private String billingEmail;
    private String orderID;
    private String token; /// Token unico associado ao cartao.
    private String onFileEndDate;
    private String onFilePermissions;
    private String onFileComment;
    private String onFileMaxChargeAmount;
    private Recurring recurring;
	private PaymentInfo paymentInfo;
    private CardInfo cardInfo;
    private BillingInfo billingInfo;
    private ShippingInfo shippingInfo;
    
    public void setRecurring(Recurring recurring) {
  		this.recurring = recurring;
  	}    
    
    public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}
	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}
	public void setBillingInfo(BillingInfo billingInfo) {
		this.billingInfo = billingInfo;
	}
	public void setShippingInfo(ShippingInfo shippingInfo) {
		this.shippingInfo = shippingInfo;
	}
	//Getters and Setters
    public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerIdExt() {
		return customerIdExt;
	}
	public void setCustomerIdExt(String customerIdExt) {
		this.customerIdExt = customerIdExt;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	public String getExpirationMonth() {
		return expirationMonth;
	}
	public void setExpirationMonth(String expirationMonth) {
		this.expirationMonth = expirationMonth;
	}
	public String getExpirationYear() {
		return expirationYear;
	}
	public void setExpirationYear(String expirationYear) {
		this.expirationYear = expirationYear;
	}
	public String getBillingName() {
		return billingName;
	}
	public void setBillingName(String billingName) {
		this.billingName = billingName;
	}
	public String getBillingAddress1() {
		return billingAddress1;
	}
	public void setBillingAddress1(String billingAddress1) {
		this.billingAddress1 = billingAddress1;
	}
	public String getBillingAddress2() {
		return billingAddress2;
	}
	public void setBillingAddress2(String billingAddress2) {
		this.billingAddress2 = billingAddress2;
	}
	public String getBillingCity() {
		return billingCity;
	}
	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}
	public String getBillingState() {
		return billingState;
	}
	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}
	public String getBillingZip() {
		return billingZip;
	}
	public void setBillingZip(String billingZip) {
		this.billingZip = billingZip;
	}
	public String getBillingCountry() {
		return billingCountry;
	}
	public void setBillingCountry(String billingCountry) {
		this.billingCountry = billingCountry;
	}
	public String getBillingPhone() {
		return billingPhone;
	}
	public void setBillingPhone(String billingPhone) {
		this.billingPhone = billingPhone;
	}
	public String getBillingEmail() {
		return billingEmail;
	}
	public void setBillingEmail(String billingEmail) {
		this.billingEmail = billingEmail;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getOnFileEndDate() {
		return onFileEndDate;
	}
	public void setOnFileEndDate(String onFileEndDate) {
		this.onFileEndDate = onFileEndDate;
	}
	public String getOnFilePermissions() {
		return onFilePermissions;
	}
	public void setOnFilePermission(String onFilePermission) {
		this.onFilePermissions = onFilePermission;
	}
	public String getOnFileComment() {
		return onFileComment;
	}
	public void setOnFileComment(String onFileComment) {
		this.onFileComment = onFileComment;
	}
	public String getOnFileMaxChargeAmount() {
		return onFileMaxChargeAmount;
	}
	public void setOnFileMaxChargeAmount(String onFileMaxChargeAmount) {
		this.onFileMaxChargeAmount = onFileMaxChargeAmount;
	}	
}