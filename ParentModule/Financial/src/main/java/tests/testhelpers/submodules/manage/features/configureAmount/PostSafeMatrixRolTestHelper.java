package tests.testhelpers.submodules.manage.features.configureAmount;

import com.aventstack.extentreports.Status;

import dto.submodules.manage.configureAmount.getPosition.Content;
import dto.submodules.manage.configureAmount.rol.RolResponseDTO;
import endpoints.submodules.manage.features.ConfigureAmountEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.manage.features.configureAmount.rol.RolPayLoadHelper;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class PostSafeMatrixRolTestHelper extends ManageTestHelper {
	
	private String userName;
	
	public PostSafeMatrixRolTestHelper(String userName) throws Exception {
		super(userName);
		this.userName=userName;
	}

	/**
	 * This method will perform a POST in order to create a new rol.
	 * @param payLoad
	 * @return response
	 * @author german.massello
	 */
	public Response postSafeMatrixRol(RolPayLoadHelper payLoad) {
		String requestURL = ExpenseBaseTest.baseUrl + String.format(ConfigureAmountEndpoints.postRol);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ManageBaseTest.sessionId).contentType(ContentType.JSON).body(payLoad);
		Response response = restUtils.executePOST(requestSpecification, requestURL);
		ManageBaseTest.test.log(Status.INFO, "POST - User: " + userName);
		ManageBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		ManageBaseTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(payLoad));
		return response;
	}
	
	/**
	 * This method will create a new rol from scratch.
	 * @return rol
	 * @throws Exception
	 */
	public RolResponseDTO postSafeMatrixRolFromScratch() throws Exception {
		GetPositionForRolTestHelper positionHelper = new GetPositionForRolTestHelper(userName);
		Content position = positionHelper.getRandomPosition();
		Response response = postSafeMatrixRol(new RolPayLoadHelper(position));
		validateResponseToContinueTest(response, 201, "Post rol api call was not successful.", true);
		return response.as(RolResponseDTO.class, ObjectMapperType.GSON);
	}
	
}
