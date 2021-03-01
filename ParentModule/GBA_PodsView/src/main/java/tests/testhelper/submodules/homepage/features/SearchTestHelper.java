package tests.testhelper.submodules.homepage.features;

import java.util.List;

/**
 * @author rachana.jadhav
 */
import endpoints.submodules.homepage.features.SearchEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.homepage.features.RealSearchPayloadHelper;
import tests.testhelper.submodules.homepage.HomePageBaseTestHelper;
import tests.testscase.PodsViewBaseTests;
import utils.RestUtils;

/**
 * This helper is for search functionality verification  
 */
public class SearchTestHelper extends HomePageBaseTestHelper{

	Long contactId;
	int podId,accountId=0;
	String searchQueryString="glo";

	/**
	 * This method will return the id Of first data from RealSearch
	 * @return idToCompare
	 * @param name
	 * @param id
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public Object getRealSearchId(String name, String id) throws Exception
	{
		RestUtils restUtils = new RestUtils();
		String searchUrl = PodsViewBaseTests.firebaseUrl +String.format(SearchEndpoints.realSearchGetUrl, searchQueryString);          
		RequestSpecification fbRequestSpec = RestAssured.with().header("token",name).header("globerid",id).contentType(ContentType.JSON);
		Response realSearchResponse =new RestUtils().executeGET(fbRequestSpec, searchUrl);
		List<Object> responseId = (List<Object>) restUtils.getValueFromResponse(realSearchResponse, "data..result..id");
		Object idToCompare= responseId.get(0);
		return idToCompare;
	}

	/**
	 * This method will return the realSearchResponse 
	 * @return realSearchResponse
	 * @param name
	 * @param id
	 * @throws Exception 
	 */
	public Response getRealSearchRequest(String name, String id) throws Exception
	{
		String searchUrl = PodsViewBaseTests.firebaseUrl +String.format(SearchEndpoints.realSearchGetUrl, searchQueryString);          
		RequestSpecification fbRequestSpec = RestAssured.with().header("token",name).header("globerid",id).contentType(ContentType.JSON);
		Response realSearchResponse =new RestUtils().executeGET(fbRequestSpec, searchUrl);
		return realSearchResponse;

	}

	/**
	 * This method will return the Pod's AccountId 
	 * @return AccountId
	 * @param name
	 * @param id
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public int getPodAccountId(String name, String id) throws Exception
	{
		RestUtils restUtils = new RestUtils();
		String searchUrl = PodsViewBaseTests.firebaseUrl +String.format(SearchEndpoints.realSearchGetUrl, searchQueryString);          
		RequestSpecification fbRequestSpec = RestAssured.with().header("token",name).header("globerid",id).contentType(ContentType.JSON);
		Response realSearchResponse =new RestUtils().executeGET(fbRequestSpec, searchUrl);
		List<Integer> podsAccountIds = (List<Integer>) restUtils.getValueFromResponse(realSearchResponse, "data..result[?(@.type==2)]..accountId");
		accountId= podsAccountIds.get(0);
		return accountId;

	}

	/**
	 * This method will return the contactId 
	 * @return contactId
	 * @param name
	 * @param id
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public Long getContactId(String name, String id) throws Exception
	{
		RestUtils restUtils = new RestUtils();
		String searchUrl = PodsViewBaseTests.firebaseUrl +String.format(SearchEndpoints.realSearchGetUrl, searchQueryString);          
		RequestSpecification fbRequestSpec = RestAssured.with().header("token",name).header("globerid",id).contentType(ContentType.JSON);
		Response realSearchResponse =new RestUtils().executeGET(fbRequestSpec, searchUrl);
		List<Long> contactIds = (List<Long>) restUtils.getValueFromResponse(realSearchResponse, "data.result[?(@.type==2)]..id");
		contactId= contactIds.get(0);
		return contactId;

	}

	/**
	 * This method will return the Search accountId 
	 * @return accountId
	 * @param name
	 * @param id
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public int getSearchAccountId(String name, String id) throws Exception
	{
		RestUtils restUtils = new RestUtils();
		String searchUrl = PodsViewBaseTests.firebaseUrl +String.format(SearchEndpoints.realSearchGetUrl, searchQueryString);          
		RequestSpecification fbRequestSpec = RestAssured.with().header("token",name).header("globerid",id).contentType(ContentType.JSON);
		Response realSearchResponse =new RestUtils().executeGET(fbRequestSpec, searchUrl);
		List<Integer> searchAccountIds = (List<Integer>) restUtils.getValueFromResponse(realSearchResponse, "data.result[?(@.type==1)].id");
		accountId= searchAccountIds.get(0);
		return accountId;

	}

	/**
	 * This method will return the Contact's accountId 
	 * @return accountId
	 * @param name
	 * @param id
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public int getContactsAccountId(String name, String id) throws Exception
	{
		RestUtils restUtils = new RestUtils();
		String searchUrl = PodsViewBaseTests.firebaseUrl +String.format(SearchEndpoints.realSearchGetUrl, searchQueryString);          
		RequestSpecification fbRequestSpec = RestAssured.with().header("token",name).header("globerid",id).contentType(ContentType.JSON);
		Response realSearchResponse =new RestUtils().executeGET(fbRequestSpec, searchUrl);
		List<Integer> contactsAccountIds = (List<Integer>) restUtils.getValueFromResponse(realSearchResponse, "data..result[?(@.type==3)]..accountId");
		accountId= contactsAccountIds.get(1);
		return accountId;

	}

	/**
	 * This method will return the Contact's podId 
	 * @return podId
	 * @param name
	 * @param id
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public int ContactsPodId(String name, String id) throws Exception
	{
		RestUtils restUtils = new RestUtils();
		String searchUrl = PodsViewBaseTests.firebaseUrl +String.format(SearchEndpoints.realSearchGetUrl, searchQueryString);          
		RequestSpecification fbRequestSpec = RestAssured.with().header("token",name).header("globerid",id).contentType(ContentType.JSON);
		Response realSearchResponse =new RestUtils().executeGET(fbRequestSpec, searchUrl);
		List<Integer> contactsPodIds = (List<Integer>) restUtils.getValueFromResponse(realSearchResponse, "data..result[?(@.type==3)]..id");
		podId= contactsPodIds.get(1);
		return podId;

	}

	/**
	 * This method will return the post Search payload 
	 * @return payload
	 * @param name
	 * @param id
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public Object postSearchHelper(String name, String id) throws Exception
	{
		RestUtils restUtils = new RestUtils();
		String searchUrl = PodsViewBaseTests.firebaseUrl +String.format(SearchEndpoints.realSearchGetUrl, searchQueryString);          
		RequestSpecification fbRequestSpec = RestAssured.with().header("token",name).header("globerid",id).contentType(ContentType.JSON);
		Response realSearchResponse =new RestUtils().executeGET(fbRequestSpec, searchUrl);
		List<Object> response = (List<Object>) restUtils.getValueFromResponse(realSearchResponse, "data.result");
		Object payload= response.get(0);
		return payload;
	}

	/**
	 * This method will return the accountScreenResponse 
	 * @return accountScreenResponse
	 * @param name
	 * @param id
	 * @throws Exception 
	 */
	public Response verifyNavigationToContactsOfAccountScreen(String name, String id) throws Exception
	{

		SearchTestHelper SearchTestHelper = new SearchTestHelper();
		accountId =SearchTestHelper.getSearchAccountId(name, id);
		String accountScreenUrl = PodsViewBaseTests.firebaseUrl  +  String.format(SearchEndpoints.contactsOfAccount,accountId);
		RequestSpecification fbRequestSpec = RestAssured.with().header("token",name).header("globerid",id).contentType(ContentType.JSON);
		Response accountScreenResponse =new RestUtils().executeGET(fbRequestSpec, accountScreenUrl);
		return accountScreenResponse;

	}

	/**
	 * This method will return the contactScreenResponse 
	 * @return contactScreenResponse
	 * @param name
	 * @param id
	 * @throws Exception 
	 */
	public Response verifyNavigationToContactsOfPodScreen(String name, String id) throws Exception
	{

		SearchTestHelper SearchTestHelper = new SearchTestHelper();
		podId =SearchTestHelper.ContactsPodId(name, id);
		accountId=SearchTestHelper.getContactsAccountId(name,id);
		String contactOfPodUrl = PodsViewBaseTests.firebaseUrl  +  String.format(SearchEndpoints.contactOfPod,accountId,podId);
		RequestSpecification fbRequestSpec = RestAssured.with().header("token",name).header("globerid",id).contentType(ContentType.JSON);
		Response contactScreenResponse =new RestUtils().executeGET(fbRequestSpec, contactOfPodUrl);
		return contactScreenResponse;

	}

	/**
	 * This method will return the podScreenResponse 
	 * @return podScreenResponse
	 * @param name
	 * @param id
	 * @throws Exception 
	 */
	public Response verifyNavigationToPodsOfContactScreen(String name, String id) throws Exception
	{

		SearchTestHelper SearchTestHelper = new SearchTestHelper();
		accountId =SearchTestHelper.getPodAccountId(name, id);
		contactId=SearchTestHelper.getContactId(name,id);
		String podOfContactUrl = PodsViewBaseTests.firebaseUrl  +  String.format(SearchEndpoints.podsOfContact,accountId,contactId);
		RequestSpecification fbRequestSpec = RestAssured.with().header("token",name).header("globerid",id).contentType(ContentType.JSON);
		Response podScreenResponse =new RestUtils().executeGET(fbRequestSpec, podOfContactUrl);
		return podScreenResponse;

	}

	/**
	 * This method will return the postRealSearchResponse 
	 * @return postRealSearchResponse
	 * @param name
	 * @param id
	 * @throws Exception 
	 */
	public Response postRealSearch(String name,String id) throws Exception {
		RealSearchPayloadHelper RealSearchPayloadHelper=new RealSearchPayloadHelper();
		String jsonBody=RealSearchPayloadHelper.postSearchPayloadHelper(name, id);
		String postsearchUrl = PodsViewBaseTests.firebaseUrl +SearchEndpoints.realSearchGetUrl;
		RequestSpecification fbRequestSpec = RestAssured.with().header("token",name).header("globerid",id).contentType(ContentType.JSON).body(jsonBody);
		Response postRealSearchResponse =new RestUtils().executePOST(fbRequestSpec, postsearchUrl);
		return postRealSearchResponse;

	}

	/**
	 * This method will return the recentSearchResponse 
	 * @return recentSearchResponse
	 * @param name
	 * @param id
	 * @throws Exception 
	 */
	public Response verifyRecentSearch(String name, String id) throws Exception {

		String recentSearchUrl = PodsViewBaseTests.firebaseUrl +SearchEndpoints.recentSearchGetUrl;
		RequestSpecification fbRequestSpec = RestAssured.with().header("token",name).header("globerid",id).contentType(ContentType.JSON);
		Response recentSearchResponse =new RestUtils().executeGET(fbRequestSpec, recentSearchUrl);
		return recentSearchResponse;
	}

	/**
	 * This method will return the id Of first data from RecentSearch  
	 * @return idOfRecentSearch
	 * @param name
	 * @param id
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public Object getRecentSearchId(String name, String id) throws Exception {

		RestUtils restUtils = new RestUtils();
		String recentSearchUrl = PodsViewBaseTests.firebaseUrl +SearchEndpoints.recentSearchGetUrl;
		RequestSpecification fbRequestSpec = RestAssured.with().header("token",name).header("globerid",id).contentType(ContentType.JSON);
		Response recentSearchResponse =new RestUtils().executeGET(fbRequestSpec, recentSearchUrl);
		List<Object> responseId = (List<Object>) restUtils.getValueFromResponse(recentSearchResponse, "data..result..id");
		Object idOfRecentSearch= responseId.get(0);
		return idOfRecentSearch;
	}

}