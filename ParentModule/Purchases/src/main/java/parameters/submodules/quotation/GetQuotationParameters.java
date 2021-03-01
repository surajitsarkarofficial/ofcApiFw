package parameters.submodules.quotation;

import parameters.submodules.PurchasesParameters;

/**
 * @author german.massello
 *
 */
public class GetQuotationParameters  extends PurchasesParameters{
	
	private String quotationId;
	
	/**
	 * This method create a GetQuotationParameters object. 
	 * @param state
	 * @param status
	 * @param isGlobalView
	 * @author german.massello
	 */
	public GetQuotationParameters(String state, String status, String isGlobalView) {
		super();
		this.state = state;
		this.status = status;
		this.isGlobalView = isGlobalView;
	}

	public String getQuotationId() {
		return quotationId;
	}

	public GetQuotationParameters setQuotationId(String quotationId) {
		this.quotationId = quotationId;
		return this;
	}

}
