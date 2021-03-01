package tests.testhelpers.submodules.manage.features.configureExpense;

import com.aventstack.extentreports.Status;

import endpoints.submodules.manage.features.ConfigureExpenseEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.manage.features.configureExpense.ExceptionRolPayLoadHelper;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class EditExceptionRolTestHelper extends ManageTestHelper {

	public EditExceptionRolTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a PUT in order to edit a exception rol.
	 * @param createdExceptionRol
	 * @return response
	 * @link http://10.54.151.0:8669/swagger-ui.html#/exceptions-controller/updateRolUsingPUT
	 * @author german.massello
	 */
	public Response editExceptionRol(ExceptionRolPayLoadHelper editExceptionRolPayLoad) {
		String requestURL = ExpenseBaseTest.baseUrl + ConfigureExpenseEndpoints.editExceptionRol;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ExpenseBaseTest.sessionId).contentType(ContentType.JSON).body(editExceptionRolPayLoad);
		Response response = restUtils.executePUT(requestSpecification, requestURL);
		ExpenseBaseTest.test.log(Status.INFO, "PUT - User: " + userName);
		ExpenseBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		ExpenseBaseTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(editExceptionRolPayLoad));
		return response;
	}	
}
