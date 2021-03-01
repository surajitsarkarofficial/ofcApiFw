package tests.testhelpers.submodules.expense.features.approveAsFinance;

import com.aventstack.extentreports.Status;

import endpoints.submodules.expense.features.approveAsFinance.ApproveAsFinanceEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.expense.features.approveAsFinance.ApproveAsFinancePayload;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.ExpenseTestHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class ApproveAsFinanceTestHelper extends ExpenseTestHelper {
	
	private String userName;
	
	public ApproveAsFinanceTestHelper(String userName) throws Exception {
		super(userName);
		this.userName = userName;
	}

	/**
	 * This method will perform a POST in order to approve or reject a report by a finance user.
	 * 
	 * @param approveAsFinancePayload
	 * @return response
	 * @throws Exception
	 */
	public Response approveAsFinance (ApproveAsFinancePayload approveAsFinancePayload) throws Exception {
		RestUtils restUtils = new RestUtils();
		String requestURL = ExpenseBaseTest.baseUrl + ApproveAsFinanceEndpoints.approveAsFinance;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ExpenseBaseTest.sessionId).contentType(ContentType.JSON).body(approveAsFinancePayload);
		Response response = restUtils.executePOST(requestSpecification, requestURL);
		ExpenseBaseTest.test.log(Status.INFO, "User: " + userName);
		ExpenseBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		ExpenseBaseTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(approveAsFinancePayload));
		ExpenseBaseTest.test.log(Status.INFO, "The report was "+approveAsFinancePayload.getApprovalAction()+"ed"+" by finance");
		return response;		
	}
}
