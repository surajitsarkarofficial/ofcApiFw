package tests.testhelpers.submodules.expense.features.report;

import endpoints.submodules.expense.features.report.GetReportReportEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import parameters.submodules.expense.features.getReports.GetExpenseReportDetailsParameters;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.expense.ExpenseTestHelper;

/**
 * @author german.massello
 *
 */
public class GetExpenseReportDetailsTestHelper extends ExpenseTestHelper {

	public GetExpenseReportDetailsTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a GET in order to obtain an expenses report details.
	 * @param parameters
	 * @return Response
	 * @throws Exception
	 */
	public Response getExpenseReportDetails(GetExpenseReportDetailsParameters parameters) throws Exception {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(ManageBaseTest.sessionId);
		String url = ExpenseBaseTest.baseUrl + String.format(GetReportReportEndpoints.getReportDetails, parameters.getReportId());
		Response response = restUtils.executeGET(requestSpecification, url);
		validateResponseToContinueTest(response, parameters.getStatusCode(), "Get expense report details api call was not successful.", true);
		return response;
	}

}
