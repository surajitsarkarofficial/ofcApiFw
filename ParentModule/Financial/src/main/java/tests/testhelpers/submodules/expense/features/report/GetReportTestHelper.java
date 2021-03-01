package tests.testhelpers.submodules.expense.features.report;

import endpoints.submodules.expense.features.report.GetReportReportEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import parameters.submodules.expense.features.getReports.GetExpenseReportParameters;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.expense.ExpenseTestHelper;

/**
 * @author german.massello
 *
 */
public class GetReportTestHelper extends ExpenseTestHelper {

	public GetReportTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a GET in order to obtain an expenses report list.
	 * @param parameters
	 * @return Response
	 * @throws Exception
	 */
	public Response getExpenseReport(GetExpenseReportParameters parameters) throws Exception {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(ManageBaseTest.sessionId);
		String url = ExpenseBaseTest.baseUrl + String.format(GetReportReportEndpoints.getReports, parameters.getStatus(), parameters.getIsEditable(), parameters.getIsApprovalView()
				, parameters.getIsGlobalView(), parameters.getPageSize(), parameters.getPageNum(), parameters.getType(), parameters.getSortAscending(), parameters.getSortCriteria()
				, parameters.getUsdFrom(), parameters.getUsdTo(), parameters.getIsBenefit(), parameters.getReportId());
		Response response = restUtils.executeGET(requestSpecification, url);
		validateResponseToContinueTest(response, parameters.getStatusCode(), "Get expense report api call was not successful.", true);
		return response;
	}

}
