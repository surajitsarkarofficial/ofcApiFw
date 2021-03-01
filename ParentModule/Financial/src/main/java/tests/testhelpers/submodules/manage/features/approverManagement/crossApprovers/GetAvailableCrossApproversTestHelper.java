package tests.testhelpers.submodules.manage.features.approverManagement.crossApprovers;

import com.aventstack.extentreports.Status;

import endpoints.submodules.manage.features.CrossApproversEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import parameters.submodules.manage.features.approverManagement.crossApprovers.GetAvailableCrossApproversParameters;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;

/**
 * @author german.massello
 *
 */
public class GetAvailableCrossApproversTestHelper extends ManageTestHelper {

	public GetAvailableCrossApproversTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a GET in order to fetch available cross approvers.
	 * @param parameters
	 * @return Response
	 * @throws Exception
	 */
	public Response getAvailableCrossApprovers(GetAvailableCrossApproversParameters parameters) throws Exception {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(ManageBaseTest.sessionId);
		String url = ManageBaseTest.baseUrl + String.format(CrossApproversEndpoints.getAvailableCrossApprovers, parameters.getLimit(), parameters.getUsernameToSearch());
		Response response = restUtils.executeGET(requestSpecification, url);
		ManageBaseTest.test.log(Status.INFO, "User: " + userName);
		ManageBaseTest.test.log(Status.INFO, "RequestURL: " + url);
		return response;
	}

}
