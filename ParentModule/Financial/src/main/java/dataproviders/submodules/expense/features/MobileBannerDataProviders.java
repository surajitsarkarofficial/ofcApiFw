package dataproviders.submodules.expense.features;

import org.testng.annotations.DataProvider;

import database.submodules.expense.features.MobileBannerDBHelper;
import dataproviders.submodules.expense.ExpenseDataProviders;

/**
 * @author german.massello
 *
 */
public class MobileBannerDataProviders extends ExpenseDataProviders {

	/**
	 * This data provider will return an user that do not have mobile expenses.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "globerWithoutMobileExpenses")
	public Object[][] globerWithoutMobileExpenses() throws Exception {
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = new MobileBannerDBHelper().getGloberWithoutMobileExpenses("Glober");
		return dataObject;
	}

}
