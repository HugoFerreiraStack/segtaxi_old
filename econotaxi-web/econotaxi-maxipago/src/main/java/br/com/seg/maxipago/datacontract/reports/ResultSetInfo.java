package br.com.seg.maxipago.datacontract.reports;

public class ResultSetInfo {

	//Attributes
    private String totalNumberOfRecords;
    private String pageToken;
    private String pageNumber;
    private String processedTime;
    private String numberOfPages;
    
    //Getters
	public String getTotalNumberOfRecords() {
		return totalNumberOfRecords;
	}

	public String getPageToken() {
		return pageToken;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public String getProcessedTime() {
		return processedTime;
	}

	public String getNumberOfPages() {
		return numberOfPages;
	}
}