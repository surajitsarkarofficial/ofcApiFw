package dataproviders.submodules.invoice;

import org.testng.annotations.DataProvider;

import database.FinancialDBHelper;
import dataproviders.FinancialDataProviders;
import parameters.submodules.invoice.InvoiceParameter;
import properties.FinancialProperties;
import properties.invoice.InvoiceProperties;

/**
 * @author german.massello
 *
 */
public class InvoiceDataProviders extends FinancialDataProviders {

	/**
	 * This data provider will return a InvoiceParameters object.
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "invoice")
	public Object[][] invoice() throws Exception {
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = new InvoiceParameter(
				FinancialProperties.enzoServer, 
				FinancialProperties.coorporativeUsername, 
				FinancialProperties.coorporativePassword, 
				InvoiceProperties.invoiceOriginPath, 
				InvoiceProperties.invoiceTemplateName,
				FinancialProperties.batchServer, 
				InvoiceProperties.invoiceDestinationPath,
				"fileDestinationName",
				new FinancialDBHelper().getGloberThatHaveABankAccount()
				);
		return dataObject;
	}

}
