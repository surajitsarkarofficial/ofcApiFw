package tests.testhelper.submodules.homepage.features;
/**
 * @author rachana.jadhav
 */

import endpoints.submodules.homepage.features.PodsEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testhelper.submodules.homepage.HomePageBaseTestHelper;
import tests.testscase.PodsViewBaseTests;
import tests.testscase.submodules.homepageTestcases.features.PodsTest;
import utils.RestUtils;

/**
 * This helper is for pods details verification
 *  To verify the pods details from Glow and Firebase are same  
 */
public class PodTestHelper extends HomePageBaseTestHelper {
	/**
	 * This method will return the firebaseResponse 
	 * @return firebaseResponse
	 * @param name
	 * @param id
	 * @throws Exception 
	 */
	public Response getFirebasePodDetails(String name,String id) throws Exception
	{
		ContactTestHelper ContactTestHelper = new ContactTestHelper();
		AccountTestHelper AccountsHelper = new AccountTestHelper();
		int accountId =AccountsHelper.getAccountId(name,id);
		int contactId =ContactTestHelper.getContactId(name,id);
		String firebaseEndpointUrl = PodsTest.firebaseUrl+  String.format(PodsEndpoints.firebasePodsOfContacts,accountId,contactId);
		RequestSpecification firebaseRequestSpec = RestAssured.with().header("token",name).header("globerid",id).contentType(ContentType.JSON);
		Response firebaseResponse =new RestUtils().executeGET(firebaseRequestSpec, firebaseEndpointUrl);
		return firebaseResponse;

	}
	/**
	 * This method will return the glowResponse 
	 * @return glowResponse
	 * @param name
	 * @param id
	 * @param position
	 * @throws Exception 
	 */
	public Response getGlowPodDetails(String name,String id,String position) throws Exception
	{
		ContactTestHelper ContactTestHelper = new ContactTestHelper();
		AccountTestHelper AccountsHelper = new AccountTestHelper();
		int accountId =AccountsHelper.getAccountId(name,id);
		int contactId =ContactTestHelper.getContactId(name,id);
		String glowEndpointUrl = PodsViewBaseTests.baseUrl + String.format(PodsEndpoints.glowPodsOfContacts,accountId,contactId);
		RequestSpecification glowRequestSpec = RestAssured.with().header("token",name).header("userPosition",position).contentType(ContentType.JSON);
		Response glowResponse =new RestUtils().executeGET(glowRequestSpec, glowEndpointUrl);
		return glowResponse;
	}
}
