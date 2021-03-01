package tests.testscase.submodules.homepageTestcases.features;
/**
 * @author rachana.jadhav
 *
 */

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.PodsViewBaseDataProviders;
import io.restassured.response.Response;
import tests.testhelper.submodules.homepage.features.SearchTestHelper;
import tests.testscase.submodules.homepageTestcases.HomePageBaseTest;
import utils.RestUtils;
/**
 * This test will verify search functionality
 *
 */
public class SearchTest extends HomePageBaseTest{
	
	String searchSuccessMessage=null;
	
	RestUtils restUtils = new RestUtils();
	SoftAssert softAssert = new SoftAssert();
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("SearchTest");
	}
	
	/** This test will verify Real search api 
	 * @throws Exception
	 * @param name
	 * @param id
	 * @param position
	 */
	@Test(dataProvider = "userData", dataProviderClass = PodsViewBaseDataProviders.class)
	public void verifygetRealSearch(String name,String id,String position) throws Exception{

		
		SearchTestHelper SearchTestHelper = new SearchTestHelper();
		Response realSearchResponse =SearchTestHelper.getRealSearchRequest(name,id);
		searchSuccessMessage = (String) restUtils.getValueFromResponse(realSearchResponse, "message");
		softAssert.assertTrue(searchSuccessMessage.contains("Success"),"Failed in validating message");
		softAssert.assertAll();
	}
	
	/** This test will verify navigation from search screen to Account screen
	 * @throws Exception
	 * @param name
	 * @param id
	 * @param position
	 */
	@Test(dataProvider = "userData", dataProviderClass = PodsViewBaseDataProviders.class)
	public void verifyNavigationToContactsOfAccount(String name,String id,String position) throws Exception{

		SearchTestHelper SearchTestHelper = new SearchTestHelper();
		Response realSearchResponse =SearchTestHelper.verifyNavigationToContactsOfAccountScreen(name, id);
		searchSuccessMessage = (String) restUtils.getValueFromResponse(realSearchResponse, "message");
		softAssert.assertTrue(searchSuccessMessage.contains("Success"),"Failed in validating message");
		softAssert.assertAll();
	}
	
	/** This test will verify navigation from search screen to Contact screen
	 * @throws Exception
	 * @param name
	 * @param id
	 * @param position
	 */
	@Test(dataProvider = "userData", dataProviderClass = PodsViewBaseDataProviders.class)
	public void verifyNavigationToContactsOfPod(String name,String id,String position) throws Exception{

		SearchTestHelper SearchTestHelper = new SearchTestHelper();
		Response realSearchResponse =SearchTestHelper.verifyNavigationToContactsOfPodScreen(name, id);
		searchSuccessMessage = (String) restUtils.getValueFromResponse(realSearchResponse, "message");
		softAssert.assertTrue(searchSuccessMessage.contains("Success"),"Failed in validating message");
		softAssert.assertAll();
	}
	
	/** This test will verify navigation from search screen to Pod screen
	 * @throws Exception
	 * @param name
	 * @param id
	 * @param position
	 */
	@Test(dataProvider = "userData", dataProviderClass = PodsViewBaseDataProviders.class)
	public void verifyNavigationToPodsOfContact(String name,String id,String position) throws Exception{

		SearchTestHelper SearchTestHelper = new SearchTestHelper();
		Response realSearchResponse =SearchTestHelper.verifyNavigationToPodsOfContactScreen(name, id);
		searchSuccessMessage = (String) restUtils.getValueFromResponse(realSearchResponse, "message");
		softAssert.assertTrue(searchSuccessMessage.contains("Success"),"Failed in validating message");
		softAssert.assertAll();
	}
	
	/** This test will verify post recent search api
	 * @throws Exception
	 * @param name
	 * @param id
	 * @param position
	 */
	@Test(dataProvider = "userData", dataProviderClass = PodsViewBaseDataProviders.class)
	public void verifyPostSearch(String name,String id,String position) throws Exception{

		SearchTestHelper SearchTestHelper = new SearchTestHelper();
		Response postSearchResponse =SearchTestHelper.postRealSearch(name,id);
		searchSuccessMessage = (String) restUtils.getValueFromResponse(postSearchResponse, "message");
		softAssert.assertTrue(searchSuccessMessage.contains("Success"),"Failed in validating message");
		softAssert.assertAll();
		
	}
	
	/** This test will verify get recent search api
	 * @throws Exception
	 * @param name
	 * @param id
	 * @param position
	 */
	@Test(dataProvider = "userData", dataProviderClass = PodsViewBaseDataProviders.class)
	public void verifygetRecentSearch(String name,String id,String position) throws Exception{

		SearchTestHelper SearchTestHelper = new SearchTestHelper();
		Response recentSearchResponse =SearchTestHelper.verifyRecentSearch(name, id);
		searchSuccessMessage = (String) restUtils.getValueFromResponse(recentSearchResponse, "message");
		softAssert.assertTrue(searchSuccessMessage.contains("Success"),"Failed in validating message");
		softAssert.assertAll();
	}
	
	
	/** This test will verify post recent search is at the top position in the recent search response
	 * @throws Exception
	 * @param name
	 * @param id
	 * @param position
	 */
	@Test(dataProvider = "userData", dataProviderClass = PodsViewBaseDataProviders.class)
	public void verifyResponseOfRecentSearch(String name,String id,String position) throws Exception{

		
		SearchTestHelper SearchTestHelper = new SearchTestHelper();
		Object realSearchData= SearchTestHelper.getRealSearchId(name, id);
		Object recentSearchData=SearchTestHelper.getRecentSearchId(name, id);
		softAssert.assertEquals(realSearchData, recentSearchData,String.format("Expected data was '%s' and actual was '%s'", realSearchData,recentSearchData));
		softAssert.assertAll();
}
}