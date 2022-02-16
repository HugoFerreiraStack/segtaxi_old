package br.com.seg.maxipago.datacontract;

import br.com.seg.maxipago.datacontract.nontransactional.ApiResponse;
import br.com.seg.maxipago.datacontract.reports.RapiResponse;
import br.com.seg.maxipago.datacontract.transactional.ErrorResponse;
import br.com.seg.maxipago.datacontract.transactional.TransactionResponse;

public abstract class ResponseBase {

	// Special methods
	public boolean IsTransactionResponse() {
		return this instanceof TransactionResponse;
	}

	public boolean IsErrorResponse() {
		return this instanceof ErrorResponse;
	}

	public boolean IsApiResponse() {
		return this instanceof ApiResponse;
	}

	public boolean IsReportResponse() {
		return this instanceof RapiResponse;
	}
}
