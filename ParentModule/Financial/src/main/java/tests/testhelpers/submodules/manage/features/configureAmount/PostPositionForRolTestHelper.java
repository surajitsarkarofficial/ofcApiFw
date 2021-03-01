package tests.testhelpers.submodules.manage.features.configureAmount;

import com.aventstack.extentreports.Status;

import dto.submodules.manage.configureAmount.getPosition.Content;
import dto.submodules.manage.configureAmount.postPosition.PostPositionForSafeMatrixRolResponseDTO;
import endpoints.submodules.manage.features.ConfigureAmountEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.manage.features.configureAmount.postPosition.PostPositionForRolPayLoadHelper;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class PostPositionForRolTestHelper extends ManageTestHelper {
	
	private String user;
	
	public PostPositionForRolTestHelper(String userName) throws Exception {
		super(userName);
		this.user=userName;
	}

	/**
	 * This method will perform a POST in order to add a position to a safe matrix rol.
	 * @param payLoad
	 * @return response
	 */
	public Response postPosition(PostPositionForRolPayLoadHelper payLoad) {
		String requestURL = ExpenseBaseTest.baseUrl + String.format(ConfigureAmountEndpoints.postPosition);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ManageBaseTest.sessionId).contentType(ContentType.JSON).body(payLoad);
		Response response = restUtils.executePOST(requestSpecification, requestURL);
		ManageBaseTest.test.log(Status.INFO, "POST - User: " + userName);
		ManageBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		ManageBaseTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(payLoad));
		return response;
	}
	
	/**
	 * This method will select a random position and add it to a safe matrix role.
	 * @return PostPositionForSafeMatrixRolResponseDTO
	 * @throws Exception
	 */
	public PostPositionForSafeMatrixRolResponseDTO postPositionFromScratch() throws Exception {
		GetPositionForRolTestHelper positionHelper = new GetPositionForRolTestHelper(user);
		Content position = positionHelper.getRandomPosition();
		return postPositionFromScratchCore(position) ;
	}
	
	/**
	 * This method will add a particular position to a safe matrix role.
	 * @param position
	 * @return PostPositionForSafeMatrixRolResponseDTO
	 * @throws Exception
	 */
	public PostPositionForSafeMatrixRolResponseDTO postPositionFromScratchCore(Content position) throws Exception {
		GetSafeMatrixRolTestHelper rolHelper = new GetSafeMatrixRolTestHelper(user);
		dto.submodules.manage.configureAmount.getSafeMatrixRoles.Content rol = rolHelper.getRandomRol();
		PostPositionForRolPayLoadHelper payload = new PostPositionForRolPayLoadHelper(position, rol);
		Response response = postPosition(payload);
		PostPositionForSafeMatrixRolResponseDTO positionRol = response.as(PostPositionForSafeMatrixRolResponseDTO.class, ObjectMapperType.GSON);
		return positionRol;
	}
}
