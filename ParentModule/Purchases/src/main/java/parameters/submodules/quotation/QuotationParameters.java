package parameters.submodules.quotation;

import parameters.submodules.PurchasesParameters;

/**
 * @author german.massello
 *
 */
public class QuotationParameters  extends PurchasesParameters{

	protected String purchaseUserName;
	protected String countryName;
	protected String groupName;
	
	public QuotationParameters() {
		super();
	}
	
	public String getCountryName() {
		return countryName;
	}
	
	public QuotationParameters setCountryName(String countryName) {
		this.countryName = countryName;
		return this;
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public QuotationParameters setGroupName(String groupName) {
		this.groupName = groupName;
		return this;
	}

	public String getPurchaseUserName() {
		return purchaseUserName;
	}

	public QuotationParameters setPurchaseUserName(String purchaseUserName) {
		this.purchaseUserName = purchaseUserName;
		return this;
	}
	
}
