package br.com.seg.maxipago.datacontract.reports;

public class ReportRequest {

	//Attributes 
    private FilterOptions filterOptions;
    private String requestToken;
    
    //Special Methods
    public ReportRequest() {
        this.filterOptions = new FilterOptions();
    }

    //Getters and Setters
    public FilterOptions getFilterOptions() {
		return filterOptions;
	}

	public void setFilterOptions(FilterOptions filterOptions) {
		this.filterOptions = filterOptions;
	}

	public String getRequestToken() {
		return requestToken;
	}

	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}
}