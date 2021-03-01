package tests.testhelpers.submodules.benefit.features;

import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;
import io.restassured.response.Response;
import tests.testhelpers.submodules.benefit.BenefitTestHelper;
import tests.testhelpers.submodules.expense.features.sendToApprove.SendToApproveTestHelper;

/**
 * @author german.massello
 *
 */
public class SendBenefitToApproveTestHelper extends BenefitTestHelper {

	private String username;

	public SendBenefitToApproveTestHelper(String username) throws Exception {
		super(username);
		this.username=username;
	}

	/**
	 * This method will create a benefit from scratch and sent to approve it.
	 * @return ReportWithExpenseDTO
	 * @throws Exception
	 */
	public ReportWithExpenseDTO createAndSendBenefitToApproveFromScratch() throws Exception {
		ReportWithExpenseDTO benefit = new CreateBenefitWithReceiptTestHelper(username).createABenefitWithReceiptFromScratch();
		Response response = new SendToApproveTestHelper(username).sendToApprove(benefit);
		validateResponseToContinueTest(response, 200, "Send To Approve api call was not successful.", true);
		return benefit;
	   }

}
