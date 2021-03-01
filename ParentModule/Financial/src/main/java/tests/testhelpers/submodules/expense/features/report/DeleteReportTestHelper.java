package tests.testhelpers.submodules.expense.features.report;

import com.aventstack.extentreports.Status;

import endpoints.submodules.expense.features.report.DeleteReportEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.ExpenseTestHelper;
import utils.RestUtils;

/**
 * @author german.massello
 *
 */
public class DeleteReportTestHelper extends ExpenseTestHelper {
	
	private String userName;
	
	public DeleteReportTestHelper(String userName) throws Exception {
		super(userName);
		this.userName = userName;
	}

	/**
	 * This method will perform a DELETE in order to delete a report.
	 * 
	 * @param reportId
	 * @return Response
	 * @throws Exception
	 */
	public Response deleteReport(String reportId) throws Exception {
		RestUtils restUtils = new RestUtils();
		String requestURL = ExpenseBaseTest.baseUrl + String.format(
				DeleteReportEndpoints.deleteReport, reportId);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ExpenseBaseTest.sessionId).contentType(ContentType.JSON);
		Response response = restUtils.executeDELETE(requestSpecification, requestURL);
		ExpenseBaseTest.test.log(Status.INFO, "User: " + userName);
		ExpenseBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);		
		ExpenseBaseTest.test.log(Status.INFO, "The report was deleted successful.");
		return response;
	}
	
}
