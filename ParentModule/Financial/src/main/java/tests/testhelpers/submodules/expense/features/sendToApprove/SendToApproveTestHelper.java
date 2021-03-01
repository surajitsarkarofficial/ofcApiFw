package tests.testhelpers.submodules.expense.features.sendToApprove;

import com.aventstack.extentreports.Status;

import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;
import endpoints.submodules.expense.features.sendToApprove.SendToApproveEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.expense.features.sendToApprove.SendToApprovePayload;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.ExpenseTestHelper;
import tests.testhelpers.submodules.expense.features.addExpenseToReport.AddRemoveExpenseToReportTestHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class SendToApproveTestHelper extends ExpenseTestHelper {

	private String userName;
	
	public SendToApproveTestHelper(String userName) throws Exception {
		super(userName);
		this.userName = userName;
	}

	/**
	 * This method will perform a POST and send to approve a report.
	 * @param reportWithExpenseDTO
	 * @return sendToApproveResponseDTO
	 * @throws Exception
	 */
	public Response sendToApprove (ReportWithExpenseDTO reportWithExpenseDTO) throws Exception {
		SendToApprovePayload sendToApprovePayload = new SendToApprovePayload(reportWithExpenseDTO);
		RestUtils restUtils = new RestUtils();
		String requestURL = ExpenseBaseTest.baseUrl + SendToApproveEndpoints.sendToApprove;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ExpenseBaseTest.sessionId).contentType(ContentType.JSON).body(sendToApprovePayload);
		Response response = restUtils.executePOST(requestSpecification, requestURL);
		ExpenseBaseTest.test.log(Status.INFO, "User: " + userName);
		ExpenseBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		ExpenseBaseTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(sendToApprovePayload));
		ExpenseBaseTest.test.log(Status.INFO, "The report was sent to approve");
		return response;
	}
	
	/**
	 * This method will create a report from scratch and send it to approve.
	 * @return reportWithExpense
	 * @throws Exception
	 * 	 */
	public ReportWithExpenseDTO sendToApproveFromScratch () throws Exception {
		AddRemoveExpenseToReportTestHelper addRemoveExpenseToReportTestHelper = 
				new AddRemoveExpenseToReportTestHelper(userName);
		ReportWithExpenseDTO reportWithExpense = addRemoveExpenseToReportTestHelper.createReportWithExpense();
		sendToApprove(reportWithExpense);	
		return reportWithExpense;
	}
	
}
