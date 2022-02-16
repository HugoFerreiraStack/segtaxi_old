package br.com.seg.maxipago.datacontract.transactional;

public class Phone {
	
	private String phoneType;
	private int phoneCountryCode;
	private int phoneAreaCode;
	private int phoneNumber;
	private int phoneExtension;
	
	public String getPhoneType(){
		return this.phoneType;
	}
	
	public int getPhoneCountryCode(){
		return this.phoneCountryCode;
	}
	
	public int getPhoneAreaCode(){
		return this.phoneAreaCode;
	}
	
	public int getPhoneNumber(){
		return this.phoneNumber;
	}
	
	public int getPhoneExtension(){
		return this.phoneExtension;
	}
	
	public void setPhoneType (String phoneType){
		this.phoneType = phoneType;
	}
	
	public void setPhoneCountryCode (int phoneCountryCode){
		this.phoneCountryCode = phoneCountryCode;
	}
	
	public void setPhoneAreaCode (int phoneAreaCode){
		this.phoneAreaCode = phoneAreaCode;
	}
	
	public void setPhoneNumber (int phoneNumber){
		this.phoneNumber = phoneNumber;
	}
	
	public void setPhoneExtension (int phoneExtension){
		this.phoneExtension = phoneExtension;
	}
}