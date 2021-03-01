package dataproviders.submodules.expense.features;

import org.testng.annotations.DataProvider;

import database.submodules.expense.features.GetExpenseReportDBHelper;
import dataproviders.submodules.expense.ExpenseDataProviders;
import parameters.submodules.expense.features.getReports.GetExpenseReportParameters;

/**
 * @author german.massello
 *
 */
public class GetExpenseReportDataProviders extends ExpenseDataProviders {

	/**
	 * This data provider will return all needed parameters in order to perform a GET reports,
	 *  using the usd filter.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "getExpensesReportUsdFilter")
	public Object[][] getExpensesReportUsdFilter() throws Exception {
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = new GetExpenseReportParameters(new GetExpenseReportDBHelper().getRandomReportIdAndUsdAmountsLimits());
		return dataObject;
	}

	/**
	 * This data provider will return all needed parameters in order to perform a GET reports,
	 *  using the is benefit filter.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "getExpensesReportIsBenefit")
	public Object[][] getExpensesReportIsBenefit() throws Exception {
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = new GetExpenseReportParameters(new GetExpenseReportDBHelper().getRandomReportIsBenefit());
		return dataObject;
	}
}
