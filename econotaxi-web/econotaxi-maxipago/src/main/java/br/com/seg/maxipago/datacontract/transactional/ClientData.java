package br.com.seg.maxipago.datacontract.transactional;

public class ClientData {
	
	//Attributes 
	private String customField1;
	private String customField2;
	private String customField3;
	private String customField4;
	private String customField5;
	private String comments;
	
	//Special Methods 
	public ClientData(){
		this.comments = "JavaPlugin v1.1";
	}

	//Getters and Setters
	public String getCustomField1() {
		return this.customField1;
	}

	public void setCustomField1(String customField1) {
		this.customField1 = customField1;
	}

	public String getCustomField2() {
		return this.customField2;
	}

	public void setCustomField2(String customField2) {
		this.customField2 = customField2;
	}

	public String getCustomField3() {
		return this.customField3;
	}

	public void setCustomField3(String customField3) {
		this.customField3 = customField3;
	}

	public String getCustomField4() {
		return this.customField4;
	}

	public void setCustomField4(String customField4) {
		this.customField4 = customField4;
	}

	public String getCustomField5() {
		return this.customField5;
	}

	public void setCustomField5(String customField5) {
		this.customField5 = customField5;
	}

	public String getComments() {
		return this.comments;
	}
}