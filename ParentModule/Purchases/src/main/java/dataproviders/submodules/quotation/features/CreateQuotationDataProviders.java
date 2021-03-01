package dataproviders.submodules.quotation.features;

import org.testng.annotations.DataProvider;

import database.PurchasesDBHelper;
import dataproviders.submodules.quotation.QuotationDataProviders;
import parameters.submodules.quotation.QuotationParameters;

/**
 * @author german.massello
 *
 */
public class CreateQuotationDataProviders extends QuotationDataProviders {

	/**
	 * This data provider will return a user with the facilities rol, a country name and a group name.
	 * @return dataObject
	 * @throws Exception
	 * @author german.massello
	 */
	@DataProvider(name = "quotationDataProvider")
	public Object[][] quotationDataProvider() throws Exception {
		Object[][] dataObject = new Object[1][1];
		QuotationParameters parameters = ((QuotationParameters) (new QuotationParameters()).setUserName(new PurchasesDBHelper().getRandomGloberByRol("Facilities")))		
				.setCountryName("Argentina").setGroupName("Cell Phones & Tablet").setPurchaseUserName(new PurchasesDBHelper().getRandomGloberByRol("Purchases"));
		dataObject [0][0] = parameters;
		return dataObject;
	}
	
}
