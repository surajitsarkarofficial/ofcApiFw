package test.testhelpers.submodules.myPods.features;

import java.util.List;

import endpoints.submodules.myPods.MyPodsEndpoints;
import endpoints.submodules.myPods.features.PodListEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.myPods.features.PodListPayloadHelper;
import test.testcases.GlobantPodsTest;
import test.testcases.submodules.myPods.features.PodListTest;
import test.testhelpers.submodules.myPods.MyPodsTestHelpers;
import utils.RestUtils;

/**
 * @author ankita.manekar
 *
 */
/**
 * This helper is for PodList details verification To verify the PodList details
 * from Glow and Firebase are same
 */
public class PodListTestHelper extends MyPodsTestHelpers {
	RestUtils restUtils;
	/**
	 * This method will get pod list details from firebase
	 * 
	 * @param authorization
	 * @return firebaseResponse
	 */
	public Response getFirebasePodList(String authorization) {
		String firebasepodListUrl = PodListTest.firebaseUrl + PodListEndpoints.firebasePodListUrl;
		RequestSpecification firebaseReqSpecific = RestAssured.with().header("Authorization", authorization);
		Response firebaseResponse = new RestUtils().executeGET(firebaseReqSpecific, firebasepodListUrl);
		return firebaseResponse;

	}

	/**
	 * This method will get pod list details from glow
	 * 
	 * @param podListEmail
	 * @param token
	 * @return glowResponse
	 * @throws Exception 
	 */
	public Response getGlowPodList(String podListEmail, String token) throws Exception {
		String glowClientDetailsUrl = GlobantPodsTest.baseUrl + PodListEndpoints.glowPodListUrl + podListEmail;
		PodListTestHelper podlist = new PodListTestHelper();
		String glowToken= podlist.getToken(token);
		RequestSpecification GlowReqSpecific = RestAssured.with().header("token", "token;"+glowToken);
		Response glowResponse = new RestUtils().executeGET(GlowReqSpecific, glowClientDetailsUrl);
		return glowResponse;

	}
	
	/**
	 * This method will get token from glow
	 * 
	 * @param token
	 * @return glowToken
	 * @throws Exception 
	 */
	public String getToken(String token) throws Exception {
		restUtils = new RestUtils();
		String glowGenerateTokenUrl = GlobantPodsTest.generateTokenBaseUrl + MyPodsEndpoints.generateToken;
		RequestSpecification GlowGenerateTokenReqSpecific = RestAssured.with().header("Authorization", token);
		Response glowTokenResponse = new RestUtils().executeGET(GlowGenerateTokenReqSpecific, glowGenerateTokenUrl);
		String glowToken =restUtils.getValueFromResponse(glowTokenResponse, "$.access_token").toString();
		return glowToken;

	}

	/**
	 * This method will get pod id from firebase
	 * 
	 * @param authorization
	 * @return podId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Long getPodId(String authorization) throws Exception {
		RestUtils restUtil = new RestUtils();
		String firebasepodListUrl = PodListTest.firebaseUrl + PodListEndpoints.firebasePodListUrl;
		RequestSpecification firebaseReqSpecific = RestAssured.with().header("Authorization", authorization);
		Response firebaseResponse = new RestUtils().executeGET(firebaseReqSpecific, firebasepodListUrl);
		List<Long> firebasePodIds =  (List<Long>) restUtil.getValueFromResponse(firebaseResponse,
				"data..pods..podId");
		Long podId = Long.parseLong(String.valueOf(firebasePodIds.get(0)));
		return podId;

	}

	/**
	 * This method will get pod area details from glowPodConstitutionAreas api
	 * 
	 * @param podIds
	 * @param token
	 * @return glowResponse
	 * @throws Exception
	 */
	public Response getGlowPodConstitutionDetails(Long podIds, String token) throws Exception {
		String glowPodConstitutionUrl = GlobantPodsTest.baseUrl + PodListEndpoints.glowPodConstitutionAreasUrl + podIds
				+ "&focusType=A";
		PodListTestHelper podlist = new PodListTestHelper();
		String glowToken= podlist.getToken(token);
		RequestSpecification glowreqSpec = RestAssured.with().header("token", "token;"+glowToken);
		Response glowResponse = new RestUtils().executeGET(glowreqSpec, glowPodConstitutionUrl);
		return glowResponse;

	}

	/**
	 * This method will get pod area details from glowAssesmentAreas api
	 * 
	 * @param podIds
	 * @param token
	 * @return glowResponse
	 * @throws Exception
	 */
	public Response getGlowAssesmentAreasDetails(Long podIds, String token) throws Exception {
		String glowAssesmentDetailUrl = GlobantPodsTest.baseUrl + PodListEndpoints.glowAssesmentAreasUrl + podIds;
		PodListTestHelper podlist = new PodListTestHelper();
		String glowToken= podlist.getToken(token);
		RequestSpecification glowreqSpec = RestAssured.with().header("token", "token;"+glowToken);
		Response glowResponse = new RestUtils().executeGET(glowreqSpec, glowAssesmentDetailUrl);
		return glowResponse;

	}

	/**
	 * This method will return the postRequestResponse for PodList schema validation
	 * 
	 * @param authorization
	 * @param token
	 * @return postRequestResponse
	 * @throws Exception
	 */
	public Response podListSchemaValidation(String authorization, String token) throws Exception {
		String schemaValidationUrl = GlobantPodsTest.schemaValidationBaseUrl + MyPodsEndpoints.verifySchema;
		PodListTestHelper podListHelper = new PodListTestHelper();
		Response response = podListHelper.getFirebasePodList(authorization);
		PodListPayloadHelper podListSchemaValidation = new PodListPayloadHelper();
		String jsonBody = podListSchemaValidation.getPodListSchemaValidation(response);
		RequestSpecification requestSpec = RestAssured.with().header("token", token).contentType(ContentType.JSON)
				.body(jsonBody);
		Response postRequestResponse = new RestUtils().executePOST(requestSpec, schemaValidationUrl);
		return postRequestResponse;
	}

}
