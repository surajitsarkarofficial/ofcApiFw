package dataproviders.submodules.quotation.features;

import org.testng.annotations.DataProvider;

import database.submodules.quotation.features.GetQuotationDBHelper;
import dataproviders.submodules.quotation.QuotationDataProviders;
import parameters.submodules.quotation.GetQuotationParameters;

/**
 * @author german.massello
 *
 */
public class GetQuotationDataProviders extends QuotationDataProviders {

	/**
	 * This data provider will return an user that have quotations in resolved status.
	 * @return dataObject
	 * @throws Exception
	 * @author german.massello
	 */
	@DataProvider(name = "userWithResolvedQuotations")
	public Object[][] userWithResolvedQuotations() throws Exception {
		GetQuotationParameters parameters = new GetQuotationParameters("QUOTATION", "Resolved", "false");
		new GetQuotationDBHelper().getGloberThatHaveQuotation(parameters);
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = parameters;
		return dataObject;
	}

	/**
	 * This data provider will return an user that have quotations in draft status.
	 * @return dataObject
	 * @throws Exception
	 * @author german.massello
	 */
	@DataProvider(name = "userWithDraftQuotations")
	public Object[][] userWithDraftQuotations() throws Exception {
		GetQuotationParameters parameters = new GetQuotationParameters("QUOTATION_DRAFT", "", "false");
		new GetQuotationDBHelper().getGloberThatHaveQuotation(parameters);
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = parameters;
		return dataObject;
	}

	/**
	 * This data provider will return an user that have quotations in unassigned status.
	 * @return dataObject
	 * @throws Exception
	 * @author german.massello
	 */
	@DataProvider(name = "userWithUnassignedQuotations")
	public Object[][] userWithUnassignedQuotations() throws Exception {
		GetQuotationParameters parameters = new GetQuotationParameters("QUOTATION", "Unassigned", "false");
		new GetQuotationDBHelper().getGloberThatHaveQuotation(parameters);
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = parameters;
		return dataObject;
	}
	
}
