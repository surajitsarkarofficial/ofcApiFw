package tests.testhelpers.submodules.expense;

import java.util.List;

import dto.submodules.expense.report.get.ExpensesReportList;
import tests.testhelpers.FinancialTestHelper;
import utils.RestUtils;

/**
 * @author german.massello
 *
 */
public class ExpenseTestHelper extends FinancialTestHelper {

	protected RestUtils restUtils = new RestUtils();

	public ExpenseTestHelper(String userName) throws Exception {
		super(userName);
	}
	
	/**
	 * This method will check if a report id is present in a reports list.
	 * @param reports
	 * @param reportId
	 * @return Boolean
	 */
	public Boolean isReportIdPresent(List<ExpensesReportList> reports, String reportId) {
		Boolean isPresent=false;
		for (ExpensesReportList report : reports) if (report.getId().equals(reportId)) isPresent=true;
		return isPresent;
	}

}
