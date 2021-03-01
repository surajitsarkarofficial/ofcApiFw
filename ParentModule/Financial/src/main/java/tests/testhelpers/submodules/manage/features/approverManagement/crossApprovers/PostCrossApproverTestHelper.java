package tests.testhelpers.submodules.manage.features.approverManagement.crossApprovers;

import com.aventstack.extentreports.Status;

import endpoints.submodules.manage.features.CrossApproversEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import parameters.submodules.manage.features.approverManagement.crossApprovers.PostCrossApproverParameters;
import payloads.submodules.manage.features.approverManagement.crossApprover.CrossApproverPayLoadHelper;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class PostCrossApproverTestHelper extends ManageTestHelper {

	public PostCrossApproverTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a POST in order to create a new cross approver.
	 * @param payLoad
	 * @return Response
	 * @throws Exception
	 */
	public Response postCrossApprover(CrossApproverPayLoadHelper payLoad) throws Exception {
		String requestURL = ManageBaseTest.baseUrl + String.format(CrossApproversEndpoints.postCrossApprover);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ManageBaseTest.sessionId).contentType(ContentType.JSON).body(payLoad);
		Response response = restUtils.executePOST(requestSpecification, requestURL);
		ManageBaseTest.test.log(Status.INFO, "User: " + userName);
		ManageBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		ManageBaseTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(payLoad));
		return response;
	}

	/**
	 * This method will create a cross approver from scratch.
	 * @param parameters
	 * @return String
	 * @throws Exception
	 */
	public String postCrossApproverFromScratch(PostCrossApproverParameters parameters) throws Exception {
		CrossApproverPayLoadHelper payload = new CrossApproverPayLoadHelper(parameters);
		Response response = new PostCrossApproverTestHelper(parameters.getUsername()).postCrossApprover(payload);
		validateResponseToContinueTest(response, 200, "POST cross approver api call was not successful.", true);
		return payload.getApprovers().get(0).getGloberId();
	}

}
