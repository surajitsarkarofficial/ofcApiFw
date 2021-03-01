package tests.testscase.submodules.homepageTestcases.features;
/**
 * @author rachana.jadhav
 *
 */

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.PodsViewBaseDataProviders;
import io.restassured.response.Response;
import tests.testhelper.submodules.homepage.features.AccountTestHelper;
import tests.testscase.submodules.homepageTestcases.HomePageBaseTest;
import utils.RestUtils;
public class AccountsTest extends HomePageBaseTest{
	String firebaseMessage, glowMessage=null;
	Boolean successMessage= null;
	
	RestUtils restUtils = new RestUtils();
	SoftAssert softAssert = new SoftAssert();
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("AccountsTest");
	}
	
	/** This test will verify Account details like account ID,Account name, total accounts count from Firebase and Glow are same
	 * @throws Exception
	 *  @param name
	 * @param id
	 * @param position
	 */
	@SuppressWarnings("unchecked")	
	@Test(dataProvider = "userData", dataProviderClass = PodsViewBaseDataProviders.class)
	public void verifyAccountsDetails(String name,String id,String position ) throws Exception{
		
		AccountTestHelper AccountsHelper = new AccountTestHelper();
		Response firebaseResponse =AccountsHelper.getFirebaseAccountDetails(name, id);
		Response glowResponse =AccountsHelper.getGlowAccountDetails(name, position);
		firebaseMessage = (String) restUtils.getValueFromResponse(firebaseResponse, "message");
		glowMessage = (String) restUtils.getValueFromResponse(glowResponse, "message");
		softAssert.assertTrue(firebaseMessage.contains("Success"),"Failed in validating message");
		softAssert.assertTrue(glowMessage.contains("success"),"Failed in validating message");

		List<String> firebaseAccountId = (List<String>) restUtils.getValueFromResponse(firebaseResponse, "data..accounts..accountId");
		List<String> glowAccountId = (List<String>) restUtils.getValueFromResponse(glowResponse, "details..accounts..id");
		softAssert.assertEquals(glowAccountId, firebaseAccountId,String.format("Expected data was '%s' and actual was '%s'", firebaseAccountId,glowAccountId));

		List<String> firebaseAccountName = (List<String>) restUtils.getValueFromResponse(firebaseResponse, "data..accounts..accountName");
		List<String> glowAccountName = (List<String>) restUtils.getValueFromResponse(glowResponse, "details..accounts..name");
		softAssert.assertEquals(glowAccountName, firebaseAccountName,String.format("Expected data was '%s' and actual was '%s'", firebaseAccountName,glowAccountName));

		List<String> firebaseAccountsCount = (List<String>) restUtils.getValueFromResponse(firebaseResponse, "data..accountsCount");
		List<String> glowAccountsCount = (List<String>) restUtils.getValueFromResponse(glowResponse, "details..totalCount");
		softAssert.assertEquals(glowAccountsCount, firebaseAccountsCount,String.format("Expected data was '%s' and actual was '%s'", firebaseAccountsCount,glowAccountsCount));
		softAssert.assertAll();
		

	}
	
	/** This test will validate Account schema response is as expected 
	 * @throws Exception
	 *  @param name
	 * @param id
	 * @param token
	 */
	@Test(dataProvider = "schemaData", dataProviderClass = PodsViewBaseDataProviders.class)
	public void verifyAccountSchema(String name,String id,String token) throws Exception{

		
		AccountTestHelper AccountTestHelper = new AccountTestHelper();
		Response firebaseResponse = AccountTestHelper.accountSchemaValidation(name,id,token);
		successMessage = (Boolean) restUtils.getValueFromResponse(firebaseResponse, "success");
		softAssert.assertTrue(Boolean.TRUE,"Failed in validating Success message");
		softAssert.assertAll();
}       
}