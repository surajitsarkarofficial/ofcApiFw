package tests.testhelpers.submodules.manage.features.configureExpense;

import com.aventstack.extentreports.Status;

import dto.submodules.manage.configureExpense.createExceptionRol.ExceptionRolResponseDTO;
import endpoints.submodules.manage.features.ConfigureExpenseEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.manage.features.configureExpense.ExceptionRolPayLoadHelper;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class CreateExceptionRolTestHelper extends ManageTestHelper {

	public CreateExceptionRolTestHelper(String userName) throws Exception {
		super(userName);
		this.userName = userName;
	}

	/**
	 * This method will perform a POST in order to create a exception rol.
	 * @param createExceptionRolPayLoad
	 * @return response
	 * @link http://10.54.151.0:8669/swagger-ui.html#/exceptions-controller/saveRoleUsingPOST
	 * @author german.massello
	 */
	public Response createExceptionRol (ExceptionRolPayLoadHelper createExceptionRolPayLoad) {
		String requestURL = ManageBaseTest.baseUrl + ConfigureExpenseEndpoints.createExceptionRol;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ExpenseBaseTest.sessionId).contentType(ContentType.JSON).body(createExceptionRolPayLoad);
		Response response = restUtils.executePOST(requestSpecification, requestURL);
		ExpenseBaseTest.test.log(Status.INFO, "POST - User: " + userName);
		ExpenseBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		ExpenseBaseTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(createExceptionRolPayLoad));
		return response;
	}
	
	/**
	 * This method will create a new exception rol from scratch.
	 * @return createExceptionRolResponseDTO
	 * @throws Exception
	 * @author german.massello
	 */
	public ExceptionRolResponseDTO createExceptionRolFromScratch () throws Exception {
		ExceptionRolPayLoadHelper createExceptionRolPayLoad = new ExceptionRolPayLoadHelper();
		Response response = createExceptionRol(createExceptionRolPayLoad);
		new ManageBaseTest().validateResponseToContinueTest(response, 201, "Create Exception api call was not successful.", false);
		ExceptionRolResponseDTO createExceptionRolResponseDTO = response.as(ExceptionRolResponseDTO.class, ObjectMapperType.GSON);
		return createExceptionRolResponseDTO;
	}
}
