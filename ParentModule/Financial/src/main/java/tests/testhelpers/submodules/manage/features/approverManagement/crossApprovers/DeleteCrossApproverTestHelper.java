package tests.testhelpers.submodules.manage.features.approverManagement.crossApprovers;

import com.aventstack.extentreports.Status;

import endpoints.submodules.manage.features.CrossApproversEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;

/**
 * @author german.massello
 *
 */
public class DeleteCrossApproverTestHelper extends ManageTestHelper {

	public DeleteCrossApproverTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a DELETE in order to delete a new cross approver.
	 * @param globerId
	 * @return Response
	 * @throws Exception
	 */
	public Response deleteCrossApprover(String globerId) throws Exception {
		String requestURL = ManageBaseTest.baseUrl + String.format(CrossApproversEndpoints.deleteCrossApprover, globerId);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ManageBaseTest.sessionId).contentType(ContentType.JSON);
		Response response = restUtils.executeDELETE(requestSpecification, requestURL);
		ManageBaseTest.test.log(Status.INFO, "User: " + userName);
		ManageBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		return response;
	}

}
