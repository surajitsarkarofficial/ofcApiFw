package tests.testhelper.submodules.homepage.features;


/**
 * @author rachana.jadhav
 *
 */
import java.util.List;

import endpoints.submodules.homepage.HomePageBaseEndpoints;
import endpoints.submodules.homepage.features.AccountsEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.homepage.features.AcctSchemaPayloadHelper;
import tests.testhelper.submodules.homepage.HomePageBaseTestHelper;
import tests.testscase.PodsViewBaseTests;
import tests.testscase.submodules.homepageTestcases.features.AccountsTest;
import utils.RestUtils;

/**
 * This helper is for account details verification  
 *  To verify the accounts details from Glow and Firebase are same
 */
public class AccountTestHelper extends HomePageBaseTestHelper {
	/**
	 * This method will return the firebaseResponse 
	 * @return firebaseResponse
	 * @param name
	 * @param id
	 */
	public Response getFirebaseAccountDetails(String name,String id)
	{
		String firebaseEndpointUrl = AccountsTest.firebaseUrl + AccountsEndpoints.firebaseAccounts;
		RequestSpecification firebaseRequestSpec = RestAssured.with().header("token",name).header("globerid",id).contentType(ContentType.JSON);
		Response firebaseResponse =new RestUtils().executeGET(firebaseRequestSpec, firebaseEndpointUrl);
		return firebaseResponse;
	}

	/**
	 * This method will return the AccountId 
	 * @return AccountId
	 * @param name
	 * @param id
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public int getAccountId(String name,String id) throws Exception
	{
		RestUtils restUtils = new RestUtils();
		String firebaseEndpointUrl = AccountsTest.firebaseUrl + AccountsEndpoints.firebaseAccounts;
		RequestSpecification firebaseRequestSpec = RestAssured.with().header("token",name).header("globerid",id).contentType(ContentType.JSON);
		Response firebaseResponse =new RestUtils().executeGET(firebaseRequestSpec, firebaseEndpointUrl);
		List<Integer> firebaseAccountIds = (List<Integer>) restUtils.getValueFromResponse(firebaseResponse, "data..accounts..accountId");
		int accountId= firebaseAccountIds.get(0);
		return accountId;

	}
	/**
	 * This method will return the glowResponse 
	 * @return glowResponse
	 * @param name
	 * @param position
	 */
	public Response getGlowAccountDetails(String name,String position)
	{
		String glowEndpointUrl = PodsViewBaseTests.baseUrl + AccountsEndpoints.glowAccounts;
		RequestSpecification glowRequestSpec = RestAssured.with().header("token",name).header("userPosition",position).contentType(ContentType.JSON);
		Response glowResponse =new RestUtils().executeGET(glowRequestSpec, glowEndpointUrl);
		return glowResponse;
	}

	/**
	 * This method will return the postRequestResponse for account schema validation
	 * @return postRequestResponse
	 * @param name
	 * @param id
	 * @param token
	 * @throws Exception 
	 */
	public Response accountSchemaValidation(String name,String id,String token) throws Exception
	{
		String schemaValidationUrl = PodsViewBaseTests.schemaValidationBaseUrl + HomePageBaseEndpoints.verifySchema;
		AccountTestHelper accountsHelper = new AccountTestHelper();
		Response response=accountsHelper.getFirebaseAccountDetails(name,id);
		AcctSchemaPayloadHelper accountsSchemaValidation = new AcctSchemaPayloadHelper();
		String jsonBody=accountsSchemaValidation.getAccountSchemaValidation(response);
		RequestSpecification requestSpec = RestAssured.with().header("token",token).contentType(ContentType.JSON).body(jsonBody);
		Response postRequestResponse =new RestUtils().executePOST(requestSpec, schemaValidationUrl);
		return postRequestResponse;
	}
}
