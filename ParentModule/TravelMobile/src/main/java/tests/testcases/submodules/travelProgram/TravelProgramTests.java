package tests.testcases.submodules.travelProgram;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import dataproviders.submodules.travelProgram.TravelProgramDataproviders;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import tests.testcases.TravelMobileBaseTest;
import tests.testhelpers.submodules.travelProgram.TravelProgramTestHelper;
import utils.RestUtils;

public class TravelProgramTests extends TravelMobileBaseTest{
	
	@BeforeTest(alwaysRun = true)
	public void beforeTest() {
		subModuleTest = moduleTest.createNode("TravelProgramTests");
	}

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("TravelProgramTests");
	}
	/**
	 * This test method will verify the travel program api with valid data
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority = 0, dataProvider = "getTravelProgramWithValidData", dataProviderClass = TravelProgramDataproviders.class)
	public void verifyGetTravelProgram(Map<Object, Object> dtoMap) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		TravelProgramTestHelper testHelper = new TravelProgramTestHelper();
		RestUtils restUtils = new RestUtils();
		String programType = dtoMap.get("programType").toString();
		Response response =testHelper.getTravelProgramDetails(programType);
		validateResponseToContinueTest(response, 200,
				String.format("Unable to fetch travel program details for program type '%s'.",programType), true);
		
		String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus, String
				.format("Expected status was '%s' and actual was '%s'", expectedStatus,restUtils.getValueFromResponse(response, "status")));
		
		String expectedResponseMessage = "Execution Successful";
		softAssert.assertTrue(restUtils.getValueFromResponse(response, "message").toString().contains(expectedResponseMessage), String
				.format("Actual message '%s'  does not contains expected message '%s'",restUtils.getValueFromResponse(response, "message"),expectedResponseMessage));
		JSONArray content =(JSONArray) restUtils.getValueFromResponse(response, "content");
		
		int len=content.size();
		
		softAssert.assertTrue(len>0,String.format("No %s data was fetched.",programType));
		
		if(len>0)
		{
			@SuppressWarnings("unchecked")
			LinkedHashMap<String,Object> object = (LinkedHashMap<String, Object>) content.get(0);
			String id  = String.valueOf(object.get("id"));
			String description = (String) object.get("description");
			softAssert.assertTrue(id!=null && id!="",String.format("'id' was not present for program type %s",programType));
			softAssert.assertTrue(description!=null && description!="",String.format("'description' was not present for program type %s",programType));
			
		}
		
		softAssert.assertAll();

		test.log(Status.PASS, "Get Travel Program verification was successful.");

	}
	/**
	 * This test method will verify the travel program api with invalid data
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority = 1, dataProvider = "getTravelProgramWithInvalidData", dataProviderClass = TravelProgramDataproviders.class)
	public void verifyGetTravelProgramForInvalidData(Map<Object, Object> dtoMap) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		TravelProgramTestHelper testHelper = new TravelProgramTestHelper();
		RestUtils restUtils = new RestUtils();
		String testScenario = dtoMap.get("dataScenario").toString();
		test.log(Status.INFO,
				String.format("Validating Get Passenger Info for '%s'", testScenario));
		
		String programType = dtoMap.get("programType").toString();
		Response response =testHelper.getTravelProgramDetails(programType);
		validateResponseToContinueTest(response, 200,
				String.format("Unable to fetch travel program details for program type '%s'.",programType), true);
		
		String expectedStatus = dtoMap.get("expectedStatus").toString();
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus, String
				.format("Expected status was '%s' and actual was '%s'", expectedStatus,restUtils.getValueFromResponse(response, "status")));
		
		String expectedResponseMessage = dtoMap.get("expectedMessage").toString();
		expectedResponseMessage=expectedResponseMessage.replace("{programType}", programType);
		softAssert.assertTrue(restUtils.getValueFromResponse(response, "message").toString().contains(expectedResponseMessage), String
				.format("Actual message '%s'  does not contains expected message '%s'",restUtils.getValueFromResponse(response, "message"),expectedResponseMessage));
		
		softAssert.assertAll();

		test.log(Status.PASS, "Get Travel Program verification for negative scenarios was successful.");

	}


}
