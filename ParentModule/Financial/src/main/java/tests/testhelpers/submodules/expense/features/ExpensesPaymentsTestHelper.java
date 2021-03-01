package tests.testhelpers.submodules.expense.features;

import com.aventstack.extentreports.Status;

import endpoints.submodules.expense.features.ExpensesPaymentsEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.expense.features.expensesPayments.ExpensesPaymentsPayload;
import tests.testcases.submodules.benefit.BenefitBaseTest;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.ExpenseTestHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class ExpensesPaymentsTestHelper extends ExpenseTestHelper {

	public ExpensesPaymentsTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a PUT in order to change to preparing payment a benefit
	 * @param payLoad
	 * @return Response
	 * @throws Exception
	 */
	public Response expensesPayments(ExpensesPaymentsPayload payLoad) throws Exception {
		String requestURL = ExpenseBaseTest.baseUrl + ExpensesPaymentsEndpoints.expensesPayments;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ExpenseBaseTest.sessionId).contentType(ContentType.JSON).body(payLoad);
		Response response = new RestUtils().executePUT(requestSpecification, requestURL);
		BenefitBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		BenefitBaseTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(payLoad));
		BenefitBaseTest.test.log(Status.INFO, "The benefit id "+payLoad.getReportIds()[0]+" was change to "+payLoad.getStatus()+" status");
		return response;
	}

}
