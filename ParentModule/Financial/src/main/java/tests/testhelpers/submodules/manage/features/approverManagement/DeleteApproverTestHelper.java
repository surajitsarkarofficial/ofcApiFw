package tests.testhelpers.submodules.manage.features.approverManagement;

import com.aventstack.extentreports.Status;

import endpoints.submodules.manage.features.ApproverManagementEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import parameters.submodules.manage.features.approverManagement.DeleteApproverParameters;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;

/**
 * @author german.massello
 *
 */
public class DeleteApproverTestHelper extends ManageTestHelper {

	public DeleteApproverTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a DELETE in order to remove a approver from a ceco.
	 * @param parameters
	 * @return response 
	 */
	public Response deleteApprover(DeleteApproverParameters parameters) {
		String requestURL = ManageBaseTest.baseUrl + String.format(ApproverManagementEndpoints.deleteApprover, parameters.getIdApprover(), parameters.getCecoNumber());
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ManageBaseTest.sessionId).contentType(ContentType.JSON).body(parameters);
		Response response = restUtils.executeDELETE(requestSpecification, requestURL);
		ManageBaseTest.test.log(Status.INFO, "DELETE - User: " + userName);
		ManageBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		return response;
	}
	
}
