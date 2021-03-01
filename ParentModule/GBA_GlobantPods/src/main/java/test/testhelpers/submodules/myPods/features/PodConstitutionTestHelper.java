package test.testhelpers.submodules.myPods.features;

import endpoints.submodules.myPods.MyPodsEndpoints;
import endpoints.submodules.myPods.features.PodConstitutionEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.myPods.features.PodConstitutionPayloadHelper;
import test.testcases.GlobantPodsTest;
import test.testcases.submodules.myPods.features.PodConstitutionTest;
import test.testhelpers.submodules.myPods.MyPodsTestHelpers;
import utils.RestUtils;

/**
 * @author ankita.manekar
 *
 */
/**
 * This helper is for PodConstitution details verification To verify the
 * PodConstitution details from Glow and Firebase are same
 */

public class PodConstitutionTestHelper extends MyPodsTestHelpers {

	/**
	 * This method will get Pod constitution area details from firebase
	 * 
	 * @param authorization
	 * @return firebaseResponse
	 * @throws Exception
	 */
	public Response getFirebasePodConstitutionDetails(String authorization) throws Exception {
		PodListTestHelper podlist = new PodListTestHelper();
		Long podId = podlist.getPodId(authorization);
		String podConstitutionUrl = PodConstitutionTest.firebaseUrl
				+ String.format(PodConstitutionEndpoints.firebasePodConstitutiontUrl, podId);
		RequestSpecification firebaseReqSpecific = RestAssured.with().header("Authorization", authorization);
		Response firebaseResponse = new RestUtils().executeGET(firebaseReqSpecific, podConstitutionUrl);
		return firebaseResponse;

	}

	/**
	 * This method will get Pod constitution area details from glow
	 * 
	 * @param authorization
	 * @param token
	 * @return glowResponse
	 * @throws Exception
	 */
	public Response getGlowPodConstitutionDetails(String authorization, String token) throws Exception {
		PodListTestHelper podlist = new PodListTestHelper();
		Long podId = podlist.getPodId(authorization);
		String glowToken= podlist.getToken(token);
		String glowPodConstitutionUrl = GlobantPodsTest.baseUrl + PodConstitutionEndpoints.glowPodConstitutionAreasUrl
				+ podId + "&focusType=A";
		RequestSpecification glowreqSpec = RestAssured.with().header("token", "token;"+glowToken);
		Response glowResponse = new RestUtils().executeGET(glowreqSpec, glowPodConstitutionUrl);
		return glowResponse;

	}

	/**
	 * This method will get Pod role details from glowPodRoles api
	 * 
	 * @param authorization
	 * @param token
	 * @return getGlowApiResponse
	 * @throws Exception
	 */
	public Response getGlowPodRoleDetails(String authorization, String token) throws Exception {
		PodListTestHelper podlist = new PodListTestHelper();
		Long podId = podlist.getPodId(authorization);
		String glowPodRoleUrl = GlobantPodsTest.baseUrl
				+ String.format(PodConstitutionEndpoints.glowPodRolesUrl, podId);
		String glowToken= podlist.getToken(token);
		RequestSpecification glowreqSpec = RestAssured.with().header("token", "token;"+glowToken);
		Response getGlowApiResponse = new RestUtils().executeGET(glowreqSpec, glowPodRoleUrl);
		return getGlowApiResponse;

	}

	/**
	 * This method will get Pod values details from glowPodValue api
	 * 
	 * @param authorization
	 * @param token
	 * @return glowResponse
	 * @throws Exception
	 */
	public Response getGlowPodValuesUrl(String authorization, String token) throws Exception {
		PodListTestHelper podlist = new PodListTestHelper();
		Long podId = podlist.getPodId(authorization);
		String glowToken= podlist.getToken(token);
		String glowPodConstitutionUrl = GlobantPodsTest.baseUrl
				+ String.format(PodConstitutionEndpoints.glowPodValuesUrl, podId);
		RequestSpecification glowReqSpec = RestAssured.with().header("token", "token;"+glowToken);
		Response glowResponse = new RestUtils().executeGET(glowReqSpec, glowPodConstitutionUrl);
		return glowResponse;

	}

	/**
	 * This method will return the postRequestResponse for PodConstitution schema
	 * validation
	 * 
	 * @param authorization
	 * @param token
	 * @return postRequestResponse
	 * @throws Exception
	 */
	public Response podConstitutionSchemaValidation(String authorization, String token) throws Exception {
		String schemaValidationUrl = GlobantPodsTest.schemaValidationBaseUrl + MyPodsEndpoints.verifySchema;
		PodConstitutionTestHelper podConstitutionHelper = new PodConstitutionTestHelper();
		Response response = podConstitutionHelper.getFirebasePodConstitutionDetails(authorization);
		PodConstitutionPayloadHelper podConstitutionSchemaValidation = new PodConstitutionPayloadHelper();
		String jsonBody = podConstitutionSchemaValidation.getPodConstitutionSchemaValidation(response);
		RequestSpecification requestSpec = RestAssured.with().header("token", token).contentType(ContentType.JSON)
				.body(jsonBody);
		Response postRequestResponse = new RestUtils().executePOST(requestSpec, schemaValidationUrl);
		return postRequestResponse;
	}
}
