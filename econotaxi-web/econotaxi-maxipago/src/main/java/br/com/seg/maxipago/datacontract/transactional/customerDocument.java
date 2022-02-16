package br.com.seg.maxipago.datacontract.transactional;

public class customerDocument {
	
	private String documentType;
	private String documentValue;
	
	public String getDocumentType() {
		return documentType;
	}
	
	public String getDocumentValue() {
		return documentValue;
	}
	
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	
	public void setDocumentValue(String documentValue) {
		this.documentValue = documentValue;
	}
}