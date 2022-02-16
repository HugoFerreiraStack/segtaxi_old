package br.com.seg.maxipago.gateway;

import br.com.seg.maxipago.datacontract.ServiceBase;
import br.com.seg.maxipago.datacontract.reports.FilterOptions;
import br.com.seg.maxipago.datacontract.reports.RapiRequest;
import br.com.seg.maxipago.datacontract.reports.RapiResponse;

public class Report extends ServiceBase {

	public Report() {
        this.setEnvironment("LIVE");
    }

    private RapiRequest request;

    /*
     * Queries a list of transactions
     */
    public RapiResponse GetTransactionDetailReport(String merchantId, String merchantKey, String period, String pageSize, String startDate
                                                 , String endDate, String startTime, String endTime, String orderByName, String orderByDirection
                                                 , String startRecordNumber, String endRecordNumber) throws Exception {

        this.request = new RapiRequest(merchantId, merchantKey);
        this.request.setCommand("transactionDetailReport");

        FilterOptions filter = this.request.getRequest().getFilterOptions();

        filter.setPeriod(period);
        filter.setPageSize(pageSize);
        filter.setStartDate(startDate);
        filter.setEndDate(endDate);
        filter.setStartTime(startTime);
        filter.setEndTime(endTime);
        filter.setOrderByName(orderByName);
        filter.setOrderByDirection(orderByDirection);
        filter.setStartRecordNumber(startRecordNumber);
        filter.setEndRecordNumber(endRecordNumber);

        return (RapiResponse) new Utils().SendRequest(this.request, this.getEnvironment());
    }
    
    /*
     * Queries a single transaction
     */
    public RapiResponse GetTransactionDetailReport(String merchantId, String merchantKey, String transactionId) throws Exception {

        this.request = new RapiRequest(merchantId, merchantKey);
        this.request.setCommand("transactionDetailReport");

        FilterOptions filter = this.request.getRequest().getFilterOptions();
        filter.setTransactionId(transactionId);

        return (RapiResponse) new Utils().SendRequest(this.request, this.getEnvironment());
        
    }

	/*
     * Queries one or more transactions
     */
    public RapiResponse GetTransactionDetailReportByOrderId(String merchantId, String merchantKey, String orderId) throws Exception {

        this.request = new RapiRequest(merchantId, merchantKey);
        this.request.setCommand("transactionDetailReport");

        FilterOptions filter = this.request.getRequest().getFilterOptions();
        filter.setOrderId(orderId);

        return (RapiResponse) new Utils().SendRequest(this.request, this.getEnvironment());        
    }
    
    /*
     * Flips pages on previous query
     */
    public RapiResponse GetTransactionDetailReport(String merchantId, String merchantKey, String pageToken, String pageNumber) throws Exception {

        this.request = new RapiRequest(merchantId, merchantKey);
        this.request.setCommand("transactionDetailReport");

        FilterOptions filter = this.request.getRequest().getFilterOptions();

        filter.setPageToken(pageToken);
        filter.setPageNumber(pageNumber);

        return (RapiResponse) new Utils().SendRequest(this.request, this.getEnvironment());
    }

    /*
     * Queries a pending report
     */
    public RapiResponse CheckRequestStatus(String merchantId, String merchantKey, String requestToken) throws Exception {

        this.request = new RapiRequest(merchantId, merchantKey);
        this.request.setCommand("checkRequestStatus");

        this.request.getRequest().setRequestToken(requestToken);

        return (RapiResponse) new Utils().SendRequest(this.request, this.getEnvironment());
    }	
}