package tests.testhelpers.submodules.manage.features.configureExpense;

import com.aventstack.extentreports.Status;

import dto.submodules.manage.configureExpense.createExceptionRol.ExceptionRolResponseDTO;
import endpoints.submodules.manage.features.ConfigureExpenseEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;

/**
 * @author german.massello
 *
 */
public class DeleteExceptionRolTestHelper extends ManageTestHelper {

	public DeleteExceptionRolTestHelper(String userName) throws Exception {
		super(userName);
		this.userName = userName;
	}

	/**
	 * This method will perform a DELETE in order to delete a exception rol.
	 * @param createExceptionRolResponseDTO
	 * @return response
	 * @link http://10.54.151.0:8669/swagger-ui.html#/exceptions-controller/deleteRoleUsingDELETE
	 * @author german.massello
	 */
	public Response deleteExceptionRol(ExceptionRolResponseDTO createExceptionRolResponseDTO) {
		String requestURL = ExpenseBaseTest.baseUrl + ConfigureExpenseEndpoints.deleteExceptionRol
				+createExceptionRolResponseDTO.getContent().getId();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ExpenseBaseTest.sessionId).contentType(ContentType.JSON);
		Response response = restUtils.executeDELETE(requestSpecification, requestURL);
		ExpenseBaseTest.test.log(Status.INFO, "User: " + userName);
		ExpenseBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		return response;
	}
}
