package br.com.seg.maxipago.gateway;

import br.com.seg.maxipago.datacontract.ServiceBase;
import br.com.seg.maxipago.datacontract.nontransactional.ApiRequest;
import br.com.seg.maxipago.datacontract.nontransactional.ApiResponse;
import br.com.seg.maxipago.datacontract.nontransactional.CommandRequest;
import br.com.seg.maxipago.datacontract.transactional.CardInfo;
import br.com.seg.maxipago.datacontract.transactional.PaymentInfo;
import br.com.seg.maxipago.datacontract.transactional.Recurring;

public class Api extends ServiceBase {
	
	//Attributes 
    private ApiRequest request;
    
	//Construct
	public Api() {
        this.setEnvironment("TEST");
    }

    //Specials Methods - (Adiciona um cliente na base)
    public ApiResponse AddConsumer(String merchantId, String merchantKey, String customerIdExt, String firstName, String lastName
                                    , String address1, String address2, String city, String state, String zip, String phone, String email
                                    , String dob, String ssn, String sex) throws Exception {


        this.request = new ApiRequest(merchantId, merchantKey);

        CommandRequest commandRequest = new CommandRequest();
        this.request.setCommand("add-consumer");
       
        commandRequest.setCustomerIdExt(customerIdExt);
        commandRequest.setFirstName(firstName);
        commandRequest.setLastName(lastName);
        commandRequest.setAddress1(address1);
        commandRequest.setAddress2(address2);
        commandRequest.setCity(city);
        commandRequest.setState(state);
        commandRequest.setZip(zip);
        commandRequest.setPhone(phone);
        commandRequest.setEmail(email);
        commandRequest.setDob(dob);
        commandRequest.setSsn(ssn);
        commandRequest.setSex(sex);
        
        this.request.setRequest(commandRequest);

        return (ApiResponse)new Utils().SendRequest(this.request, this.getEnvironment());
    }
    
    //Specials Methods - (Excluindo um cliente na base)
    public ApiResponse DeleteConsumer(String merchantId, String merchantKey, String customerId) throws Exception {

        this.request = new ApiRequest(merchantId, merchantKey);

        this.request.setCommand("delete-consumer");
        CommandRequest commandRequest = new CommandRequest();
        this.request.setRequest(commandRequest);

        commandRequest.setCustomerId(customerId);

        return (ApiResponse)new Utils().SendRequest(this.request, this.getEnvironment());
    }
    
    //Specials Methods - (Atualizar um cliente na base)
    public ApiResponse UpdateConsumer(String merchantId, String merchantKey, String customerId, String customerIdExt, String firstName
                                    , String lastName, String address1, String address2, String city, String state, String zip, String phone
                                    , String email, String dob, String ssn, String sex) throws Exception {

        this.request = new ApiRequest(merchantId, merchantKey);

        this.request.setCommand("update-consumer");
        CommandRequest commandRequest = new CommandRequest();
        this.request.setRequest(commandRequest);

        commandRequest.setCustomerId(customerId);
        commandRequest.setCustomerIdExt(customerIdExt);
        commandRequest.setFirstName(firstName);
        commandRequest.setLastName(lastName);
        commandRequest.setAddress1(address1);
        commandRequest.setAddress2(address2);
        commandRequest.setCity(city);
        commandRequest.setState(state);
        commandRequest.setZip(zip);
        commandRequest.setPhone(phone);
        commandRequest.setEmail(email);
        commandRequest.setDob(dob);
        commandRequest.setSsn(ssn);
        commandRequest.setSex(sex);

        return (ApiResponse)new Utils().SendRequest(this.request, this.getEnvironment());
    }
    

    //Specials Methods - (Salvar um cartao na base atrelado a um cliente)
    public ApiResponse AddCardOnFile(String merchantId, String merchantKey, String customerId, String creditCardNumber, String expirationMonth
                                    , String expirationYear, String billingName, String billingAddress1, String billingAddress2, String billingCity
                                    , String billingState, String billingZip, String billingCountry, String billingPhone, String billingEmail
                                    , String onFileEndDate, String onFilePermission, String onFileComment, String onFileMaxChargeAmount) throws Exception { 

        this.request = new ApiRequest(merchantId, merchantKey);

        this.request.setCommand("add-card-onfile");
        CommandRequest commandRequest = new CommandRequest();
        this.request.setRequest(commandRequest);

        commandRequest.setCustomerId(customerId);
        commandRequest.setCreditCardNumber(creditCardNumber);
        commandRequest.setExpirationMonth(expirationMonth);
        commandRequest.setExpirationYear(expirationYear);
        commandRequest.setBillingName(billingName);
        commandRequest.setBillingAddress1(billingAddress1);
        commandRequest.setBillingAddress2(billingAddress2);
        commandRequest.setBillingCity(billingCity);
        commandRequest.setBillingState(billingState);
        commandRequest.setBillingZip(billingZip);
        commandRequest.setBillingCountry(billingCountry);
        commandRequest.setBillingPhone(billingPhone);
        commandRequest.setBillingEmail(billingEmail);
        commandRequest.setOnFileEndDate(onFileEndDate);
        commandRequest.setOnFilePermission(onFilePermission);
        commandRequest.setOnFileComment(onFileComment);
        commandRequest.setOnFileMaxChargeAmount(onFileMaxChargeAmount);

        return (ApiResponse)new Utils().SendRequest(this.request, this.getEnvironment());
    }
    
    //Specials Methods - (Remover um cartao tokenizado)
    public ApiResponse DeleteCardOnFile(String merchantId, String merchantKey, String customerId, String token) throws Exception {

        this.request = new ApiRequest(merchantId, merchantKey);

        this.request.setCommand("delete-card-onfile");
        CommandRequest commandRequest = new CommandRequest();
        this.request.setRequest(commandRequest);

        commandRequest.setCustomerId(customerId);
        commandRequest.setToken(token);

        return (ApiResponse)new Utils().SendRequest(this.request, this.getEnvironment());
    }
    
    
    //Specials Methods - (Cancelar Recorrencia)
	public ApiResponse CancelRecurring(String merchantId, String merchantKey, String orderID) throws Exception{
			
		this.request = new ApiRequest(merchantId, merchantKey);

		this.request.setCommand("cancel-recurring");
		CommandRequest commandRequest = new CommandRequest();
		commandRequest.setOrderID(orderID);		
		this.request.setRequest(commandRequest);
		 
		return (ApiResponse)new Utils().SendRequest(this.request, this.getEnvironment());		
	}
	
	public ApiResponse modifyRecurring(String merchantId, String merchantKey, String orderID, double chargeTotal, String number, String expMonth, String expYear, String processorID, String action, String installments, String nextFireDate, String fireDay, String period, String lastDate, double lastAmount) throws Exception  {
		
		this.request = new ApiRequest(merchantId, merchantKey);
		
		this.request.setCommand("modify-recurring");
		
		CommandRequest commandRequest = new CommandRequest();
		PaymentInfo paymentInfo = new PaymentInfo();
		CardInfo cardInfo = new CardInfo();
		Recurring recurring = new Recurring();
		
		recurring.setProcessorID(processorID);
		recurring.setAction(action);
		recurring.setInstallments(installments);
		recurring.setNextFireDate(nextFireDate);
		recurring.setFireDay(fireDay);
		recurring.setPeriod(period);
		recurring.setLastDate(lastDate);
		recurring.setLastAmount(lastAmount);
		
		cardInfo.setCreditCardNumber(number);
		cardInfo.setExpirationMonth(expMonth);
		cardInfo.setExpirationYear(expYear);
		
		paymentInfo.setChargeTotal(chargeTotal);		
		paymentInfo.setCardInfo(cardInfo);
		
		commandRequest.setOrderID(orderID);
		commandRequest.setPaymentInfo(paymentInfo);
		commandRequest.setRecurring(recurring);
		
		this.request.setRequest(commandRequest);
		
		return (ApiResponse)new Utils().SendRequest(this.request, this.getEnvironment());
	}

    /*
     * Atualiza dados do cartao com o token
     */
    public ApiResponse TokenUpdateCreditAccountInfo(String merchantId, String merchantKey, String token, String customerIdExt,
                                                    String creditCardNumber, String expirationMonth, String expirationYear,
                                                    String billingName, String billingAddress1, String billingAddress2,
                                                    String billingCity, String billingState, String billingZip, String billingCountry,
                                                    String billingPhone, String billingEmail, String onFileEndDate, String onFilePermission,
                                                    String onFileComment, String onFileMaxChargeAmount) throws Exception{

        this.request = new ApiRequest(merchantId, merchantKey);

        this.request.setCommand("token-update-credit-account-info");
        CommandRequest commandRequest = new CommandRequest();
        this.request.setRequest(commandRequest);

        commandRequest.setToken(token);
        commandRequest.setCustomerIdExt(customerIdExt);
        commandRequest.setCreditCardNumber(creditCardNumber);
        commandRequest.setExpirationMonth(expirationMonth);
        commandRequest.setExpirationYear(expirationYear);
        commandRequest.setBillingName(billingName);
        commandRequest.setBillingAddress1(billingAddress1);
        commandRequest.setBillingAddress2(billingAddress2);
        commandRequest.setBillingCity(billingCity);
        commandRequest.setBillingState(billingState);
        commandRequest.setBillingZip(billingZip);
        commandRequest.setBillingCountry(billingCountry);
        commandRequest.setBillingPhone(billingPhone);
        commandRequest.setBillingEmail(billingEmail);
        commandRequest.setOnFileEndDate(onFileEndDate);
        commandRequest.setOnFilePermission(onFilePermission);
        commandRequest.setOnFileComment(onFileComment);
        commandRequest.setOnFileMaxChargeAmount(onFileMaxChargeAmount);

        return (ApiResponse)new Utils().SendRequest(this.request, this.getEnvironment());
    }	
}