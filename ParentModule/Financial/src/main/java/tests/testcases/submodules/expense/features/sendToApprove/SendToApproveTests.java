package tests.testcases.submodules.expense.features.sendToApprove;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import dataproviders.FinancialDataProviders;
import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;
import dto.submodules.expense.sendToApprove.SendToApproveResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.features.addExpenseToReport.AddRemoveExpenseToReportTestHelper;
import tests.testhelpers.submodules.expense.features.sendToApprove.SendToApproveTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class SendToApproveTests extends ExpenseBaseTest {
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("SendToApproveTests");
	}

	/**
	 * Goal: Check that is feasible to send to approve a report.
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class, groups = {ExeGroups.Sanity})
	public void sendToApproveTest(String userName) throws Exception {
		AddRemoveExpenseToReportTestHelper addRemoveExpenseToReportTestHelper = new AddRemoveExpenseToReportTestHelper(userName);
		SendToApproveTestHelper sendToApproveTestHelper = new SendToApproveTestHelper(userName);
		//Given
		ReportWithExpenseDTO reportWithExpense = addRemoveExpenseToReportTestHelper.createReportWithExpense();
		//When
		Response response = sendToApproveTestHelper.sendToApprove(reportWithExpense);
		SendToApproveResponseDTO sendToApproveResponse = response.as(SendToApproveResponseDTO.class, ObjectMapperType.GSON);
		//Then
		new ExpenseBaseTest().validateResponseToContinueTest(response, 200, "Send To Approve api call was not successful.", true);
		assertEquals(sendToApproveResponse.getStatus(), "OK", "getStatus issue");
		assertEquals(sendToApproveResponse.getMessage(), "Success", "getMessage issue");
		assertNull(sendToApproveResponse.getContent().getId(), "getId issue");
		assertNull(sendToApproveResponse.getContent().getError(), "getError issue");
		test.log(Status.PASS, "report was sent to approve successfully");
	}
}
