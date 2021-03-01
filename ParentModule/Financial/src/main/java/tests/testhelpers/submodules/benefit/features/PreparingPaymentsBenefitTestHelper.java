package tests.testhelpers.submodules.benefit.features;

import database.submodules.expense.ExpenseDBHelper;
import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;
import io.restassured.response.Response;
import payloads.submodules.expense.features.expensesPayments.ExpensesPaymentsPayload;
import payloads.submodules.expense.features.expensesPayments.PreparingPayments;
import tests.testhelpers.submodules.benefit.BenefitTestHelper;
import tests.testhelpers.submodules.expense.features.ExpensesPaymentsTestHelper;

/**
 * @author german.massello
 *
 */
public class PreparingPaymentsBenefitTestHelper extends BenefitTestHelper {

	private String username;
	public PreparingPaymentsBenefitTestHelper(String username) throws Exception {
		super(username);
		this.username=username;
	}

	/**
	 * This method will create a Preparing Payments benefit from scratch.
	 * @return ReportWithExpenseDTO
	 * @throws Exception
	 */
	public ReportWithExpenseDTO preparingPaymentsBenefitFromScratch() throws Exception {
		ReportWithExpenseDTO benefit = new ApprovedAsFinanceBenefitTestHelper(username).approveAsFinanceBenefitFromScratch();
		ExpensesPaymentsPayload payload = new ExpensesPaymentsPayload(new PreparingPayments(benefit.getContent().getId()));
		String financeUser=new ExpenseDBHelper().getRandomGloberByRol("APAndExpensesAnalyst");
		Response response = new ExpensesPaymentsTestHelper(financeUser).expensesPayments(payload);
		validateResponseToContinueTest(response, 200, "Preparing Payments api call was not successful.", true);
		return benefit;
	}
}
