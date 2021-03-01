package tests.testcases.submodules.expense.features.approveAsManager;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import database.submodules.expense.features.ApproveAsManagerDBHelper;
import dataproviders.FinancialDataProviders;
import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;
import dto.submodules.expense.approveAsManager.ApproveAsManagerResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import payloads.submodules.expense.features.approveAsManager.ApproveAsManagerPayload;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.features.approveAsManager.ApproveAsManagerTestHelper;
import tests.testhelpers.submodules.expense.features.sendToApprove.SendToApproveTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;


/**
 * @author german.massello
 *
 */
public class ApproveAsManagerTests extends ExpenseBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("ApproveAsManagerTests");
	}
	
	/**
	 * Goal: Check that the safe approver can approve a report.
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class, groups = {})
	public void approveAsManagerTest(String userName) throws Exception {
		SendToApproveTestHelper sendToApproveTestHelper = new SendToApproveTestHelper(userName);
		ApproveAsManagerDBHelper approveAsManagerDBHelper = new ApproveAsManagerDBHelper();
		//Given
		ReportWithExpenseDTO pendingApprovalReport = sendToApproveTestHelper.sendToApproveFromScratch();
		//When
		String notificationId = approveAsManagerDBHelper.getNotificationId(pendingApprovalReport.getContent().getId());
		ApproveAsManagerPayload approveAsManagerPayload = new ApproveAsManagerPayload ("approve", notificationId);
		String safeApprover = approveAsManagerDBHelper.getSafeApprover(notificationId);
		ApproveAsManagerTestHelper approveAsManagerTestHelper = new ApproveAsManagerTestHelper(safeApprover);
		Response response = approveAsManagerTestHelper.approveAsManager(approveAsManagerPayload);
		ApproveAsManagerResponseDTO sendToApproveResponse = response.as(ApproveAsManagerResponseDTO.class, ObjectMapperType.GSON);
		//Then
		new ExpenseBaseTest().validateResponseToContinueTest(response, 200, "Approve As Manager api call was not successful.", true);
		assertEquals(sendToApproveResponse.getStatus(), "Approved", "getStatus issue");
		test.log(Status.PASS, "The report was approved by manager successfully");
	}
	
	/**
	 * Goal: Check that the safe approver can reject a report.
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class, groups = {ExeGroups.Sanity})
	public void rejectAsManagerTest(String userName) throws Exception {
		SendToApproveTestHelper sendToApproveTestHelper = new SendToApproveTestHelper(userName);
		ApproveAsManagerDBHelper approveAsManagerDBHelper = new ApproveAsManagerDBHelper();
		//Given
		ReportWithExpenseDTO pendingApprovalReport = sendToApproveTestHelper.sendToApproveFromScratch();
		//When
		String notificationId = approveAsManagerDBHelper.getNotificationId(pendingApprovalReport.getContent().getId());
		ApproveAsManagerPayload approveAsManagerPayload = new ApproveAsManagerPayload ("reject", notificationId);
		String safeApprover = approveAsManagerDBHelper.getSafeApprover(notificationId);
		ApproveAsManagerTestHelper approveAsManagerTestHelper = new ApproveAsManagerTestHelper(safeApprover);
		Response response = approveAsManagerTestHelper.approveAsManager(approveAsManagerPayload);
		ApproveAsManagerResponseDTO sendToApproveResponse = response.as(ApproveAsManagerResponseDTO.class, ObjectMapperType.GSON);
		//Then
		new ExpenseBaseTest().validateResponseToContinueTest(response, 200, "Reject As Manager api call was not successful.", true);
		assertEquals(sendToApproveResponse.getStatus(), "Rejected", "getStatus issue");
		test.log(Status.PASS, "The report was rejected by manager successfully");
	}
}
