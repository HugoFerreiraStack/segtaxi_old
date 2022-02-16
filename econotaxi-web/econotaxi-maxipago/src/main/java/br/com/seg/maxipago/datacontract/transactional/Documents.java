package br.com.seg.maxipago.datacontract.transactional;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class Documents {
	
	@XStreamAsAttribute
	private int documentCount;
	
	@XStreamImplicit
	private List<customerDocument> documents;
	
	public int getDocumentCount() {
		return documentCount;
	}
	
	public List<customerDocument> getDocuments() {
		return documents;
	}
	
	public void setDocumentCount(int documentCount) {
		this.documentCount = documentCount;
	}
	
	public void setDocuments(List<customerDocument> documents) {
		this.documents = documents;
	}
}