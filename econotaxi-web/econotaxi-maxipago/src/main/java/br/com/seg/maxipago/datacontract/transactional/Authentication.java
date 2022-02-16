package br.com.seg.maxipago.datacontract.transactional;

public class Authentication {
	
	private String mpiProcessorID;
	private String onFailure;
	
	public String getMpiProcessor() {
		return mpiProcessorID;
	}
	
	public String getOnFailure() {
		return onFailure;
	}
	
	public void setMpiProcessor(String mpiProcessor) {
		this.mpiProcessorID = mpiProcessor;
	}
	
	public void setOnFailure(String onFailure) {
		this.onFailure = onFailure;
	}
}