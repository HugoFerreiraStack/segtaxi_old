package br.com.seg.maxipago.datacontract.transactional;

public class SaveOnFile {

	//Attributes 
    private String customerToken; //ID unico do cadastro, retornado quando o cliente foi adicionado a base (customerId).
    private String onFileEndDate; //Data limite para manter o cart?o na base, Formato MM/DD/AAAA.
    private String onFilePermissions; //Duracao limite do uso do cartao salvo, ongoing = indefinidamente, use_once = apenas uma vez apos a 1a. cobranca
    private String onFileComment; //Comentarios adicionais sobre este cart?o
    private String onFileMaxChargeAmount; //Valor maximo que e permitido cobrar deste cartao

    //Getters and Setters
	public String getCustomerToken() {
		return customerToken;
	}

	public void setCustomerToken(String customerToken) {
		this.customerToken = customerToken;
	}

	public String getOnFileEndDate() {
		return onFileEndDate;
	}

	public void setOnFileEndDate(String onFileEndDate) {
		this.onFileEndDate = onFileEndDate;
	}

	public String getOnFilePermission() {
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