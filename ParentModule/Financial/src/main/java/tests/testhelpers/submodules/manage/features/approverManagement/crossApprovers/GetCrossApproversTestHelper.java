package tests.testhelpers.submodules.manage.features.approverManagement.crossApprovers;

import com.aventstack.extentreports.Status;

import endpoints.submodules.manage.features.CrossApproversEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import parameters.submodules.manage.features.approverManagement.crossApprovers.GetCrossApproversParameters;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;

/**
 * @author german.massello
 *
 */
public class GetCrossApproversTestHelper extends ManageTestHelper {

	public GetCrossApproversTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a GET in order to fetch cross approvers.
	 * @param parameters
	 * @return Response
	 * @throws Exception
	 */
	public Response getCrossApprovers(GetCrossApproversParameters parameters) throws Exception {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(ManageBaseTest.sessionId);
		String url = ManageBaseTest.baseUrl + String.format(CrossApproversEndpoints.getCrossApprovers, parameters.getPageSize(), parameters.getPageNum());
		Response response = restUtils.executeGET(requestSpecification, url);
		ManageBaseTest.test.log(Status.INFO, "User: " + userName);
		ManageBaseTest.test.log(Status.INFO, "RequestURL: " + url);
		return response;
	}

}
