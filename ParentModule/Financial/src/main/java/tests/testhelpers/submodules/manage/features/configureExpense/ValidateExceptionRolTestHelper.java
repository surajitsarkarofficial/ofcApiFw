package tests.testhelpers.submodules.manage.features.configureExpense;

import com.aventstack.extentreports.Status;

import endpoints.submodules.manage.features.ConfigureExpenseEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;

/**
 * @author german.massello
 *
 */
public class ValidateExceptionRolTestHelper extends ManageTestHelper {

	public ValidateExceptionRolTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a GET in order to validate an exceptions rol.
	 * @return response
	 * @link http://10.54.151.0:8669/swagger-ui.html#/exceptions-controller/validateExceptionUsingGET
	 * @author german.massello
	 */
	public Response validateExceptionRol () {
		String requestURL = ManageBaseTest.baseUrl + ConfigureExpenseEndpoints.validateExceptionRol;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ExpenseBaseTest.sessionId).contentType(ContentType.JSON);
		Response response = restUtils.executeGET(requestSpecification, requestURL);
		ExpenseBaseTest.test.log(Status.INFO, "GET - User: " + userName);
		ExpenseBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		return response;
	}
	
}
