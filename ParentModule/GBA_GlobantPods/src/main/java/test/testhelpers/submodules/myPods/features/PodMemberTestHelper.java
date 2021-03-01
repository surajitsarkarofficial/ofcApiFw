package test.testhelpers.submodules.myPods.features;

import endpoints.submodules.myPods.MyPodsEndpoints;
import endpoints.submodules.myPods.features.PodMemberEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.myPods.features.PodMemberPayloadHelper;
import test.testcases.GlobantPodsTest;
import test.testcases.submodules.myPods.features.PodMemberTest;
import test.testhelpers.submodules.myPods.MyPodsTestHelpers;
import utils.RestUtils;

/**
 * @author ankita.manekar
 *
 */
/**
 * This helper is for PodMember details verification To verify the PodMember
 * details from Glow and Firebase are same
 */
public class PodMemberTestHelper extends MyPodsTestHelpers {
	/**
	 * This method will get pod member details from glow
	 * 
	 * @param token
	 * @param authorization
	 * @return glowResponse
	 * @throws Exception
	 */
	public Response getGlowPodMembersDetails(String token, String authorization) throws Exception {
		PodListTestHelper podlist = new PodListTestHelper();
		Long podId = podlist.getPodId(authorization);
		String glowToken= podlist.getToken(token);
		String glowpodMembersUrl = GlobantPodsTest.baseUrl + String.format(PodMemberEndpoints.glowPodMembersUrl, podId);
		RequestSpecification glowReqSpecific = RestAssured.with().header("token", "token;"+glowToken);
		Response glowResponse = new RestUtils().executeGET(glowReqSpecific, glowpodMembersUrl);
		return glowResponse;

	}

	/**
	 * This method will get pod member details from firebase
	 * 
	 * @param authorization
	 * @return firebaseResponse
	 * @throws Exception
	 */
	public Response getFirebasePodMembersDetails(String authorization) throws Exception {
		PodListTestHelper podlist = new PodListTestHelper();
		Long podId = podlist.getPodId(authorization);
		String firebasePodMembersUrl = PodMemberTest.firebaseUrl
				+ String.format(PodMemberEndpoints.firebasePodMembersUrl, podId);
		RequestSpecification firebaseReqSpecific = RestAssured.with().header("Authorization", authorization);
		Response firebaseResponse = new RestUtils().executeGET(firebaseReqSpecific, firebasePodMembersUrl);
		return firebaseResponse;

	}

	/**
	 * This method will return the postRequestResponse for PodMember schema
	 * validation
	 * 
	 * @param authorization
	 * @param token
	 * @return postRequestResponse
	 * @throws Exception
	 */
	public Response podMemberSchemaValidation(String authorization, String token) throws Exception {
		String schemaValidationUrl = GlobantPodsTest.schemaValidationBaseUrl + MyPodsEndpoints.verifySchema;
		PodMemberTestHelper podMemberHelper = new PodMemberTestHelper();
		Response response = podMemberHelper.getFirebasePodMembersDetails(authorization);
		PodMemberPayloadHelper podMemberSchemaValidation = new PodMemberPayloadHelper();
		String jsonBody = podMemberSchemaValidation.getPodMemberSchemaValidation(response);
		RequestSpecification requestSpec = RestAssured.with().header("token", token).contentType(ContentType.JSON)
				.body(jsonBody);
		Response postRequestResponse = new RestUtils().executePOST(requestSpec, schemaValidationUrl);
		return postRequestResponse;
	}
}
