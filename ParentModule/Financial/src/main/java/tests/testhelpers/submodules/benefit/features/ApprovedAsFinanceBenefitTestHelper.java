package tests.testhelpers.submodules.benefit.features;

import database.submodules.expense.ExpenseDBHelper;
import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;
import io.restassured.response.Response;
import payloads.submodules.expense.features.approveAsFinance.ApproveAsFinancePayload;
import tests.testhelpers.submodules.benefit.BenefitTestHelper;
import tests.testhelpers.submodules.expense.features.approveAsFinance.ApproveAsFinanceTestHelper;

/**
 * @author german.massello
 *
 */
public class ApprovedAsFinanceBenefitTestHelper extends BenefitTestHelper {

	private String username;
	
	public ApprovedAsFinanceBenefitTestHelper(String username) throws Exception {
		super(username);
		this.username=username;
	}

	/**
	 * This method will approve a benefit from scratch.
	 * @return ReportWithExpenseDTO
	 * @throws Exception
	 */
	public ReportWithExpenseDTO approveAsFinanceBenefitFromScratch() throws Exception {
		ReportWithExpenseDTO benefit = new SendBenefitToApproveTestHelper(username).createAndSendBenefitToApproveFromScratch();
		ApproveAsFinancePayload approveAsFinancePayload = new ApproveAsFinancePayload("approve", benefit.getContent().getId(), 
				benefit.getContent().getExpenses().get(0).getId(), benefit.getContent().getExpenses().get(1).getId() );
		ApproveAsFinanceTestHelper approveAsFinanceTestHelper = new ApproveAsFinanceTestHelper(new ExpenseDBHelper().getRandomGloberByRol("APAndExpensesAnalyst"));
		Response response = approveAsFinanceTestHelper.approveAsFinance(approveAsFinancePayload);
		validateResponseToContinueTest(response, 200, "Approve As Finance api call was not successful.", true);	
		return benefit;
	}

}
