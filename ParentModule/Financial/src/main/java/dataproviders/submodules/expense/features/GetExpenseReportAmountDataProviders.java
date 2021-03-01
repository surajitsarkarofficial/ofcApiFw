package dataproviders.submodules.expense.features;

import java.util.Map;

import org.testng.annotations.DataProvider;

import database.submodules.expense.features.GetExpenseReportDetailsDBHelper;
import dataproviders.submodules.expense.ExpenseDataProviders;
import parameters.submodules.expense.features.getReports.GetExpenseReportParameters;
import parameters.submodules.expense.features.getReportsAmount.GetReportsAmountStatusFilter;

/**
 * @author german.massello
 *
 */
public class GetExpenseReportAmountDataProviders extends ExpenseDataProviders {

	/**
	 * This data provider will return expense report in approved by finance status.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "getReportAmountApproveAsFinance")
	public Object[][] getReportAmountApproveAsFinance() throws Exception {
		Object[][] dataObject = new Object[1][1];
		String isBenefit = "false";
		Map<String,String> m = new GetExpenseReportDetailsDBHelper().getExpensesReport("approved_by_finance", isBenefit);
		dataObject [0][0] = new GetExpenseReportParameters(new GetReportsAmountStatusFilter("APPROVED_BY_FINANCE", m.get("reportId"), isBenefit, m.get("usdFrom"), m.get("reportId")));
		return dataObject;
	}

	/**
	 * This data provider will return expense report in preparing payment status.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "getReportAmountPreparingPayment")
	public Object[][] getReportAmountPreparingPayment() throws Exception {
		Object[][] dataObject = new Object[1][1];
		String isBenefit = "false";
		Map<String,String> m = new GetExpenseReportDetailsDBHelper().getExpensesReport("preparing_payment", isBenefit);
		dataObject [0][0] = new GetExpenseReportParameters(new GetReportsAmountStatusFilter("PREPARING_PAYMENT", m.get("reportId"), isBenefit, m.get("usdFrom"), m.get("reportId")));
		return dataObject;
	}

	/**
	 * This data provider will return expense report in paid status.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "getReportAmountPaid")
	public Object[][] getReportAmountPaid() throws Exception {
		Object[][] dataObject = new Object[1][1];
		String isBenefit = "false";
		Map<String,String> m = new GetExpenseReportDetailsDBHelper().getExpensesReport("paid", isBenefit);
		dataObject [0][0] = new GetExpenseReportParameters(new GetReportsAmountStatusFilter("PAID", m.get("reportId"), isBenefit, m.get("usdFrom"), m.get("reportId")));
		return dataObject;
	}

}
