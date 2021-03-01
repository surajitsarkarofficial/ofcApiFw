package dataproviders.submodules.expense.features;

import org.testng.annotations.DataProvider;

import database.submodules.expense.features.GetExpenseReportDetailsDBHelper;
import dataproviders.submodules.expense.ExpenseDataProviders;
import parameters.submodules.expense.features.getReports.GetExpenseReportDetailsParameters;

/**
 * @author german.massello
 *
 */
public class GetExpenseReportDetailsDataProviders extends ExpenseDataProviders {
	
	/**
	 * This data provider will return an user name, an expenses report id and a client name.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "getExpenseReportDetails")
	public Object[][] getExpenseReportDetails() throws Exception {
		Object[][] dataObject = new Object[1][1];
		String isBenefit = "false";
		dataObject [0][0] = new GetExpenseReportDetailsParameters(new GetExpenseReportDetailsDBHelper().getExpensesReport("paid", isBenefit));
		return dataObject;
	}

}
