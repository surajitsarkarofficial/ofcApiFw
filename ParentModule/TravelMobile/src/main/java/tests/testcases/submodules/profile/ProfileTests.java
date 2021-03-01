package tests.testcases.submodules.profile;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import dataproviders.submodules.profile.ProfileDataproviders;
import io.restassured.response.Response;
import tests.testcases.TravelMobileBaseTest;
import tests.testhelpers.submodules.profile.ProfileTestHelper;

public class ProfileTests extends TravelMobileBaseTest{
	
	@BeforeTest(alwaysRun = true)
	public void beforeTest() {
		subModuleTest = moduleTest.createNode("Profile Tests");
	}

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("Profile Tests");
	}
	
	/**
	 * This test method will verify get profile image api with valid data
	 * @param dtoMap
	 */
	@Test(priority = 0, dataProvider = "getProfileImageWithValidData", dataProviderClass = ProfileDataproviders.class)
	public void verifyProfileImageWithValidData(Map<Object, Object> dtoMap)
	{
		SoftAssert softAssert = new SoftAssert();
		ProfileTestHelper testHelper = new ProfileTestHelper();
		String testScenario = dtoMap.get("dataScenario").toString();
		String emailId = dtoMap.get("emailId").toString();
		test.log(Status.INFO,
				String.format("Validating Get Profile Image for '%s'", testScenario));
		
		Response response =testHelper.getProfileImage(emailId);
		
		
		int expectedStatusCode = Integer.parseInt(dtoMap.get("expectedStatusCode").toString());
		int actualStatusCode = response.getStatusCode();
		softAssert.assertEquals(actualStatusCode, expectedStatusCode, String
				.format("Expected status code was '%d' and actual was '%d'", expectedStatusCode,actualStatusCode));
		
		softAssert.assertAll();

		test.log(Status.PASS, "Get Profile Image verification for valid scenarios was successful.");

	}
	/**
	 * This test method will verify get profile image api with invalid data
	 * @param dtoMap
	 */
	@Test(priority = 1, dataProvider = "getProfileImageWithInvalidData", dataProviderClass = ProfileDataproviders.class)
	public void verifyProfileImageWithInvalidData(Map<Object, Object> dtoMap)
	{
		SoftAssert softAssert = new SoftAssert();
		ProfileTestHelper testHelper = new ProfileTestHelper();
		String testScenario = dtoMap.get("dataScenario").toString();
		String emailId = dtoMap.get("emailId").toString();
		test.log(Status.INFO,
				String.format("Validating Get Profile Image for '%s'", testScenario));
		
		Response response =testHelper.getProfileImage(emailId);
		
		
		int expectedStatusCode = Integer.parseInt(dtoMap.get("expectedStatusCode").toString());
		int actualStatusCode = response.getStatusCode();
		softAssert.assertEquals(actualStatusCode, expectedStatusCode, String
				.format("Expected status code was '%d' and actual was '%d'", expectedStatusCode,actualStatusCode));
		
		softAssert.assertAll();

		test.log(Status.PASS, "Get Profile Image verification for invalid scenarios was successful.");

	}

}
