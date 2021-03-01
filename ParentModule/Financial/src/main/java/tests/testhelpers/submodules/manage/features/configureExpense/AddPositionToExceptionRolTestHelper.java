package tests.testhelpers.submodules.manage.features.configureExpense;

import com.aventstack.extentreports.Status;

import endpoints.submodules.manage.features.ConfigureExpenseEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.manage.features.configureExpense.PositionExceptionRolPayLoadHelper;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class AddPositionToExceptionRolTestHelper extends ManageTestHelper {

	public AddPositionToExceptionRolTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a PUT in order to add a position to an exception rol.
	 * @param addPositionToExceptionRolPayLoad
	 * @return response
	 * @author german.massello
	 */
	public Response addPositionToExceptionRol(PositionExceptionRolPayLoadHelper addPositionToExceptionRolPayLoad) {
		String requestURL = ExpenseBaseTest.baseUrl + ConfigureExpenseEndpoints.addPositionToExceptionRol;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ExpenseBaseTest.sessionId).contentType(ContentType.JSON).body(addPositionToExceptionRolPayLoad);
		Response response = restUtils.executePUT(requestSpecification, requestURL);
		ExpenseBaseTest.test.log(Status.INFO, "PUT - User: " + userName);
		ExpenseBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		ExpenseBaseTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(addPositionToExceptionRolPayLoad));
		return response;
	}
}
