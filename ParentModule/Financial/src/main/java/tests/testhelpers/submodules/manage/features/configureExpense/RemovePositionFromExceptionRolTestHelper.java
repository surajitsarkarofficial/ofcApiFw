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
public class RemovePositionFromExceptionRolTestHelper extends ManageTestHelper {

	public RemovePositionFromExceptionRolTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a PUT in order to remove a position from an exception rol.
	 * @param removePositionFromExceptionRolPayLoad
	 * @return response
	 * @link http://10.54.151.0:8669/swagger-ui.html#/exceptions-controller/deleteRolePositionUsingPUT
	 * @author german.massello
	 */
	public Response removePositionFromExceptionRol(PositionExceptionRolPayLoadHelper removePositionFromExceptionRolPayLoad) {
		String requestURL = ExpenseBaseTest.baseUrl + ConfigureExpenseEndpoints.removePositionFromExceptionRol;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ExpenseBaseTest.sessionId).contentType(ContentType.JSON).body(removePositionFromExceptionRolPayLoad);
		Response response = restUtils.executePUT(requestSpecification, requestURL);
		ExpenseBaseTest.test.log(Status.INFO, "PUT - User: " + userName);
		ExpenseBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		ExpenseBaseTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(removePositionFromExceptionRolPayLoad));
		return response;
	}
}
