package tests.testhelper.submodules.homepage.features;
/**
 * @author rachana.jadhav
 */

import java.util.List;

import endpoints.submodules.homepage.HomePageBaseEndpoints;
import endpoints.submodules.homepage.features.ContactsEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.homepage.features.ContSchemaPayloadHelper;
import tests.testhelper.submodules.homepage.HomePageBaseTestHelper;
import tests.testscase.PodsViewBaseTests;
import tests.testscase.submodules.homepageTestcases.features.ContactsTest;
import utils.RestUtils;

/**
 * This helper is for contact details verification  
 * To verify the contacts details from Glow and Firebase are same
 */

public class ContactTestHelper extends HomePageBaseTestHelper {
	/**
	 * This method will return the firebaseResponse 
	 * @return firebaseResponse
	 * @param name
	 * @param id
	 * @throws Exception 
	 */
	public Response getFirebaseContactDetails(String name,String id) throws Exception
	{
		AccountTestHelper accountsHelper = new AccountTestHelper();
		int accountId =accountsHelper.getAccountId(name,id);
		String firebaseEndpointUrl = ContactsTest.firebaseUrl +  String.format(ContactsEndpoints.firebaseContacts, accountId);
		RequestSpecification firebaseRequestSpec = RestAssured.with().header("token",name).header("globerid",id).contentType(ContentType.JSON);
		Response firebaseResponse =new RestUtils().executeGET(firebaseRequestSpec, firebaseEndpointUrl);
		return firebaseResponse;

	}
	/**
	 * This method will return the glowResponse 
	 * @return glowResponse
	 * @throws Exception 
	 */
	public Response getGlowContactDetails(String name,String id,String position) throws Exception
	{
		AccountTestHelper accountsHelper = new AccountTestHelper();
		int accountId =accountsHelper.getAccountId(name,id);
		String glowEndpointUrl = PodsViewBaseTests.baseUrl +  String.format(ContactsEndpoints.glowContacts, accountId);
		RequestSpecification glowRequestSpec = RestAssured.with().header("token",name).header("userPosition",position).contentType(ContentType.JSON);
		Response glowResponse =new RestUtils().executeGET(glowRequestSpec, glowEndpointUrl);
		return glowResponse;
	}

	/**
	 * This method will return the contactId 
	 * @return contactId
	 * @param name
	 * @param id
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public int getContactId(String name,String id) throws Exception
	{
		RestUtils restUtils = new RestUtils();
		AccountTestHelper accountsHelper = new AccountTestHelper();
		int accountId =accountsHelper.getAccountId(name,id);
		String firebaseEndpointUrl = ContactsTest.firebaseUrl +  String.format(ContactsEndpoints.firebaseContacts, accountId);
		RequestSpecification firebaseRequestSpec = RestAssured.with().header("token",name).header("globerid",id).contentType(ContentType.JSON);
		Response firebaseResponse =new RestUtils().executeGET(firebaseRequestSpec, firebaseEndpointUrl);
		List<Integer> contactIds = (List<Integer>) restUtils.getValueFromResponse(firebaseResponse, "data..contacts..clientId");
		int contactId= contactIds.get(0);
		return contactId;

	}

	/**
	 * This method will return the postRequestResponse for contact schema validation 
	 * @return postRequestResponse
	 * @param name
	 * @param id
	 * @param token
	 * @throws Exception 
	 */
	public Response contactSchemaValidation(String name,String id,String token) throws Exception
	{
		String schemaValidationUrl = PodsViewBaseTests.schemaValidationBaseUrl + HomePageBaseEndpoints.verifySchema;
		ContactTestHelper contactTestHelper= new ContactTestHelper();
		Response response=contactTestHelper.getFirebaseContactDetails(name, id);
		ContSchemaPayloadHelper contSchemaPayloadHelper = new ContSchemaPayloadHelper();
		String jsonBody=contSchemaPayloadHelper.getContactSchemaValidation(response);
		RequestSpecification requestSpec = RestAssured.with().header("token",token).contentType(ContentType.JSON).body(jsonBody);
		Response postRequestResponse =new RestUtils().executePOST(requestSpec, schemaValidationUrl);
		return postRequestResponse;

	}
}

