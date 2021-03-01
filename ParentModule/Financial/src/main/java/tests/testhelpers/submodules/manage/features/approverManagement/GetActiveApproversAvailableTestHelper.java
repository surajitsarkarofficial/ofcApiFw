package tests.testhelpers.submodules.manage.features.approverManagement;

import com.aventstack.extentreports.Status;

import dto.submodules.manage.approverManagement.getActiveApproversAvailable.GetActiveApproversAvailableResponseDTO;
import endpoints.submodules.manage.features.ApproverManagementEndpoints;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import parameters.submodules.manage.features.approverManagement.GetActiveApproversAvailabletParameters;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;

/**
 * @author german.massello
 *
 */
public class GetActiveApproversAvailableTestHelper extends ManageTestHelper {

	public GetActiveApproversAvailableTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a GET in order to fetch active approvers available.
	 * @param parameters
	 * @return Response
	 * @throws Exception
	 */
	public Response getActiveApproversAvailable(GetActiveApproversAvailabletParameters parameters) throws Exception {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(ManageBaseTest.sessionId);
		String url = ManageBaseTest.baseUrl + String.format(ApproverManagementEndpoints.getActiveApproversAvailable, 
				parameters.getPageSize(), parameters.getPageNum(), parameters.getSearchValue());
		Response response = restUtils.executeGET(requestSpecification, url);
		ManageBaseTest.test.log(Status.INFO, "User: " + userName);
		ManageBaseTest.test.log(Status.INFO, "RequestURL: " + url);
		return response;
	}
	
	/**
	 * This method will return the available approvers list
	 * @param parameters
	 * @return GetActiveApproversAvailableResponseDTO
	 * @throws Exception
	 */
	public GetActiveApproversAvailableResponseDTO getActiveApproversAvailableFromScratch(GetActiveApproversAvailabletParameters parameters) throws Exception {
		Response response = getActiveApproversAvailable(parameters);
		validateResponseToContinueTest(response, 200, "Get active approvers available api call was not successful.", true);
		return response.as(GetActiveApproversAvailableResponseDTO.class, ObjectMapperType.GSON);
	}

}
