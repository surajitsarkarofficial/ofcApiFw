package tests.testhelpers.submodules.expense.features.approveAsManager;

import com.aventstack.extentreports.Status;

import database.submodules.expense.features.ApproveAsManagerDBHelper;
import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;
import endpoints.submodules.expense.features.approveAsManager.ApproveAsManagerEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.expense.features.approveAsManager.ApproveAsManagerPayload;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.ExpenseTestHelper;
import tests.testhelpers.submodules.expense.features.sendToApprove.SendToApproveTestHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class ApproveAsManagerTestHelper extends ExpenseTestHelper {
	
	private String userName;
	
	public ApproveAsManagerTestHelper(String userName) throws Exception {
		super(userName);
		this.userName = userName;
	}
	

	/**
	 * This method is going to perform a POST in order to approve or reject a report.
	 * 
	 * @param approveAsManagerPayload
	 * @return response
	 * @throws Exception
	 */
	public Response approveAsManager (ApproveAsManagerPayload approveAsManagerPayload) throws Exception { 
		RestUtils restUtils = new RestUtils();
		String requestURL = ExpenseBaseTest.baseUrl + ApproveAsManagerEndpoints.approveAsManager;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ExpenseBaseTest.sessionId).contentType(ContentType.JSON).body(approveAsManagerPayload);
		Response response = restUtils.executePOST(requestSpecification, requestURL);
		ExpenseBaseTest.test.log(Status.INFO, "User: " + userName);
		ExpenseBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		ExpenseBaseTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(approveAsManagerPayload));
		ExpenseBaseTest.test.log(Status.INFO, "The report was "+approveAsManagerPayload.getAction()+"ed"+" by manager");
		return response;
	}
	
	/**
	 * This method will create a report from scratch and send it to approve.
	 * Then is going to perform an approve by manager and return the report.
	 * @return ReportWithExpenseDTO
	 * @throws Exception
	 */
	public ReportWithExpenseDTO approveAsManagerFromScratch () throws Exception {
		ApproveAsManagerDBHelper approveAsManagerDBHelper = new ApproveAsManagerDBHelper();
		SendToApproveTestHelper sendToApproveTestHelper = new SendToApproveTestHelper(userName);
		ReportWithExpenseDTO approveByManagerReport = sendToApproveTestHelper.sendToApproveFromScratch();	
		String notificationId = approveAsManagerDBHelper.getNotificationId(approveByManagerReport.getContent().getId());
		ApproveAsManagerPayload approveAsManagerPayload = new ApproveAsManagerPayload ("approve", notificationId);
		String safeApprover = approveAsManagerDBHelper.getSafeApprover(notificationId);
		ApproveAsManagerTestHelper approveAsManagerTestHelper = new ApproveAsManagerTestHelper(safeApprover);
		approveAsManagerTestHelper.approveAsManager(approveAsManagerPayload);
		return approveByManagerReport;	
		}

}
