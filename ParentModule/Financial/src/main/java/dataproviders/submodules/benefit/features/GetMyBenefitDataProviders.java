package dataproviders.submodules.benefit.features;

import java.util.Map;

import org.testng.annotations.DataProvider;

import database.submodules.expense.features.GetExpenseReportDetailsDBHelper;
import dataproviders.submodules.benefit.BenefitDataProviders;
import parameters.submodules.expense.features.getReports.GetExpenseReportDetailsParameters;
import parameters.submodules.expense.features.getReports.GetExpenseReportParameters;
import parameters.submodules.expense.features.getReports.GetMyReportsStatusFilter;

/**
 * @author german.massello
 *
 */
public class GetMyBenefitDataProviders extends BenefitDataProviders {
	/**
	 * This data provider will return an user name and a benefit report id in draft status.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "getMyReportDetails")
	public Object[][] getMyReportDetails() throws Exception {
		Object[][] dataObject = new Object[1][1];
		String isBenefit = "true";
		dataObject [0][0] = new GetExpenseReportDetailsParameters(new GetExpenseReportDetailsDBHelper().getExpensesReport("draft", isBenefit));
		return dataObject;
	}
	
	/**
	 * This data provider will return an user name and a benefit report id in draft status.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "getMyDraftReport")
	public Object[][] getMyDraftReport() throws Exception {
		Object[][] dataObject = new Object[1][1];
		String isBenefit = "true", type = "Glober", isGlobalView = "false";
		Map<String,String> m = new GetExpenseReportDetailsDBHelper().getExpensesReport("draft", isBenefit);
		dataObject [0][0] = new GetExpenseReportParameters(new GetMyReportsStatusFilter(m.get("username"), m.get("reportId"), "Draft", isBenefit, type, m.get("title"), isGlobalView));
		return dataObject;
	}

	/**
	 * This data provider will return an user name and a benefit report id in approved by manager status.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "getMyApprovedByManagerReport")
	public Object[][] getMyApprovedByManagerReport() throws Exception {
		Object[][] dataObject = new Object[1][1];
		String isBenefit = "true", type = "Glober", isGlobalView = "false";
		Map<String,String> m = new GetExpenseReportDetailsDBHelper().getExpensesReport("approved_by_manager", isBenefit);
		dataObject [0][0] = new GetExpenseReportParameters(new GetMyReportsStatusFilter(m.get("username"), m.get("reportId"), "APPROVED", isBenefit, type, m.get("title"), isGlobalView));
		return dataObject;
	}
	
	/**
	 * This data provider will return an user name and a benefit report id in approved by finance status.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "getMyApprovedByFinanceReport")
	public Object[][] getMyApprovedByFinanceReport() throws Exception {
		Object[][] dataObject = new Object[1][1];
		String isBenefit = "true", type = "Glober", isGlobalView = "false";
		Map<String,String> m = new GetExpenseReportDetailsDBHelper().getExpensesReport("approved_by_finance", isBenefit);
		dataObject [0][0] = new GetExpenseReportParameters(new GetMyReportsStatusFilter(m.get("username"), m.get("reportId"), "APPROVED", isBenefit, type, m.get("title"), isGlobalView));
		return dataObject;
	}

	/**
	 * This data provider will return an user name and a benefit report id in rejected by finance status.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "getMyRejectedByFinanceReport")
	public Object[][] getMyRejectedByFinanceReport() throws Exception {
		Object[][] dataObject = new Object[1][1];
		String isBenefit = "true", type = "Glober", isGlobalView = "false";;
		Map<String,String> m = new GetExpenseReportDetailsDBHelper().getExpensesReport("rejected_by_finance", isBenefit);
		dataObject [0][0] = new GetExpenseReportParameters(new GetMyReportsStatusFilter(m.get("username"), m.get("reportId"), "REJECTED", isBenefit, type, m.get("title"), isGlobalView));
		return dataObject;
	}
}
