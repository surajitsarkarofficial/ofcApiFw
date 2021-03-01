package tests.testhelpers.submodules.manage.features.configureAmount;

import com.aventstack.extentreports.Status;

import dto.submodules.manage.configureAmount.getPosition.Content;
import dto.submodules.manage.configureAmount.getPosition.GetPositionForSafeMatrixRolResponseDTO;
import endpoints.submodules.manage.features.ConfigureAmountEndpoints;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class GetPositionForRolTestHelper extends ManageTestHelper {

	public GetPositionForRolTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a GET in order to fetch positions.
	 * @return response
	 */
	public Response getPosition() {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(ManageBaseTest.sessionId);
		String url = ManageBaseTest.baseUrl + String.format(ConfigureAmountEndpoints.getPosition, "");
		Response response = restUtils.executeGET(requestSpecification, url);
		ManageBaseTest.test.log(Status.INFO, "GET - User: " + userName);
		ManageBaseTest.test.log(Status.INFO, "RequestURL: " + url);
		return response;
	}

	/**
	 * This method will return the GetPositionForSafeMatrixRolResponseDTO list.
	 * @return positions
	 */
	public GetPositionForSafeMatrixRolResponseDTO getPositionDTOList() {
		Response response = getPosition();
		GetPositionForSafeMatrixRolResponseDTO positions = response.as(GetPositionForSafeMatrixRolResponseDTO.class, ObjectMapperType.GSON);
		return positions;
	}
	
	/**
	 * This method will return a random position.
	 * @return position
	 */
	public Content getRandomPosition() {
		GetPositionForSafeMatrixRolResponseDTO positions = getPositionDTOList();
		Content position = positions.getContent().get(Utilities.getRandomNumberBetween(0, positions.getContent().size()));
		return position;
	}
	
}
