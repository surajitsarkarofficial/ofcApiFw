package tests.testhelpers.submodules.manage.features.approverManagement;

import com.aventstack.extentreports.Status;

import endpoints.submodules.manage.features.ApproverManagementEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import parameters.submodules.manage.features.approverManagement.getProjectsByCeco.GetProjectsByCecoParameters;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;

/**
 * @author german.massello
 *
 */
public class GetProjectsByCecoTestHelper extends ManageTestHelper {

	public GetProjectsByCecoTestHelper(String userName) throws Exception {
		super(userName);
	}
	
	/**
	 * This method will perform a GET in order to fetch projects by ceco.
	 * @param parameters
	 * @return response
	 */
	public Response getProjectsByCeco(GetProjectsByCecoParameters parameters) {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(ManageBaseTest.sessionId);
		String url = ManageBaseTest.baseUrl + String.format(ApproverManagementEndpoints.getProjectsByCeco, parameters.getCecoNumber());
		Response response = restUtils.executeGET(requestSpecification, url);
		ManageBaseTest.test.log(Status.INFO, "GET - User: " + userName);
		ManageBaseTest.test.log(Status.INFO, "RequestURL: " + url);
		return response;
	}

}
