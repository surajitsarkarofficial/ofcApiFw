package dataproviders.submodules.benefit.features;

import java.util.Map;

import org.testng.annotations.DataProvider;

import database.submodules.expense.features.GetExpenseReportDetailsDBHelper;
import dataproviders.submodules.benefit.BenefitDataProviders;
import parameters.submodules.expense.features.getReports.GetExpenseReportParameters;
import parameters.submodules.expense.features.getReportsAmount.GetReportsAmountStatusFilter;

/**
 * @author german.massello
 *
 */
public class GetReportAmountsBenefitDataProviders extends BenefitDataProviders {

	/**
	 * This data provider will return benefit report in approved by finance status.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "getReportAmountApproveAsFiananceBenefit")
	public Object[][] getReportAmountApproveAsFiananceBenefit() throws Exception {
		Object[][] dataObject = new Object[1][1];
		String isBenefit = "true";
		Map<String,String> m = new GetExpenseReportDetailsDBHelper().getExpensesReport("approved_by_finance", isBenefit);
		dataObject [0][0] = new GetExpenseReportParameters(new GetReportsAmountStatusFilter("APPROVED_BY_FINANCE", m.get("reportId"), isBenefit, m.get("usdFrom"), m.get("reportId")));
		return dataObject;
	}

	/**
	 * This data provider will return benefit report in preparing payment status.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "getReportAmountPreparingPaymentBenefit")
	public Object[][] getReportAmountPreparingPaymentBenefit() throws Exception {
		Object[][] dataObject = new Object[1][1];
		String isBenefit = "true";
		Map<String,String> m = new GetExpenseReportDetailsDBHelper().getExpensesReport("preparing_payment", isBenefit);
		dataObject [0][0] = new GetExpenseReportParameters(new GetReportsAmountStatusFilter("PREPARING_PAYMENT", m.get("reportId"), isBenefit, m.get("usdFrom"), m.get("reportId")));
		return dataObject;
	}

	/**
	 * This data provider will return benefit report in paid status.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "getReportAmountPaidBenefit")
	public Object[][] getReportAmountPaidBenefit() throws Exception {
		Object[][] dataObject = new Object[1][1];
		String isBenefit = "true";
		Map<String,String> m = new GetExpenseReportDetailsDBHelper().getExpensesReport("paid", isBenefit);
		dataObject [0][0] = new GetExpenseReportParameters(new GetReportsAmountStatusFilter("PAID", m.get("reportId"), isBenefit, m.get("usdFrom"), m.get("reportId")));
		return dataObject;
	}

}
