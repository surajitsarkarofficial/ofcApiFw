package tests.testcases.submodules.frequentFlyerNumbers;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import dataproviders.submodules.frequentFlyerNumbers.FrequentFlyerNumbersDataproviders;
import io.restassured.response.Response;
import tests.testcases.TravelMobileBaseTest;
import tests.testhelpers.submodules.frequentFlyerNumbers.FrequentFlyerNumbersTestHelper;
import tests.testhelpers.submodules.myTrips.features.PassengerTestsHelper;
import tests.testhelpers.submodules.travelProgram.TravelProgramTestHelper;
import utils.RestUtils;
import utils.Utilities;

public class FrequentFlyerNumbersTests extends TravelMobileBaseTest {

	public static String freqFlyerId, freqFlyerNumber, passengerId;
	public static String passengerEmail = "surajit.sarkar@globant.com";
	public static Map<String, Object> airlineDetails;

	@BeforeTest(alwaysRun = true)
	public void beforeTest() {
		subModuleTest = moduleTest.createNode("FrequentFlyerNumbersTests");
	}

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("FrequentFlyerNumbersTests");
	}

	/**
	 * This test method will verify add freq flyer number api with valid data
	 * 
	 * @throws Exception
	 */
	@Test(priority = 0)
	public void verifyAddFrequentFlyerNumbersWithValidData() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		FrequentFlyerNumbersTestHelper testHelper = new FrequentFlyerNumbersTestHelper();
		RestUtils restUtils = new RestUtils();

		String name = "Auto" + Utilities.getRandomNumberBetween(10000, 9999999);

		// Fetch the passenger info to verify in the response of add Passenger
		Response passengerInfoResponse = new PassengerTestsHelper().getUserInfo(passengerEmail);
		validateResponseToContinueTest(passengerInfoResponse, 200,
				"Unable to fetch passenger info for user with email id - " + passengerEmail, true);
		passengerId = String.valueOf(restUtils.getValueFromResponse(passengerInfoResponse, "content.passengerId"));

		// Delete All Freq Flyers which already exists
		testHelper.deleteAllFrequentFlyerNumbers(passengerEmail);

		airlineDetails = new TravelProgramTestHelper().getTravelProgramData("AIRLINE");
		String jsonBody = testHelper.createBodyForFrequentFlyerNumber(airlineDetails, name);

		Response response = testHelper.addFrequentFlyerNumber(passengerId, jsonBody);

		validateResponseToContinueTest(response, 200, String.format("Unable to add Frequent Flyer Number."), true);

		String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));

		freqFlyerId = String.valueOf(restUtils.getValueFromResponse(response, "content.frequentId"));
		softAssert.assertTrue(freqFlyerId != null && freqFlyerId != "", "Frequent Flyer Id was null or empty");

		freqFlyerNumber = (String) restUtils.getValueFromResponse(response, "content.frequentNumber");
		softAssert.assertEquals(freqFlyerNumber, name,
				String.format("Expected Frequent Flyer Number was '%s' and actual was '%s'", freqFlyerNumber, name));

		String actualFreqCategoryId = String
				.valueOf(restUtils.getValueFromResponse(response, "content.frequentCategory.id"));
		String expectedFreqCategoryId = String.valueOf(airlineDetails.get("id"));
		softAssert.assertEquals(actualFreqCategoryId, expectedFreqCategoryId,
				String.format("Expected Frequent Category Id was '%s' and actual was '%s'", expectedFreqCategoryId,
						actualFreqCategoryId));

		String actualFreqCategoryDescription = String
				.valueOf(restUtils.getValueFromResponse(response, "content.frequentCategory.description"));
		String expectedFreqCategoryDescription = (String) airlineDetails.get("description");
		softAssert.assertEquals(actualFreqCategoryDescription, expectedFreqCategoryDescription,
				String.format("Expected Frequent Category Description was '%s' and actual was '%s'",
						expectedFreqCategoryDescription, actualFreqCategoryDescription));

		String actualFreqNumberTypeId = String
				.valueOf(restUtils.getValueFromResponse(response, "content.frequentNumType.id"));
		String expectedFreqNumberTypeId = "1";
		softAssert.assertEquals(actualFreqNumberTypeId, expectedFreqNumberTypeId,
				String.format("Expected Frequent Number Type Id was '%s' and actual was '%s'", expectedFreqNumberTypeId,
						actualFreqNumberTypeId));

		String actualFreqNumberTypeDescription = String
				.valueOf(restUtils.getValueFromResponse(response, "content.frequentNumType.description"));
		String expectedFreqNumberTypeDescription = "AIRLINE";
		softAssert.assertEquals(actualFreqNumberTypeDescription, expectedFreqNumberTypeDescription,
				String.format("Expected Frequent Category Description was '%s' and actual was '%s'",
						expectedFreqNumberTypeDescription, actualFreqNumberTypeDescription));
		softAssert.assertAll();

		test.log(Status.PASS, "Add Frequent Flyer Number verification was successful.");

	}

	/**
	 * This method will verify edit freq flyer number with valid data
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1, dependsOnMethods = "verifyAddFrequentFlyerNumbersWithValidData")
	public void verifyEditFrequentFlyerNumbersWithValidData() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		FrequentFlyerNumbersTestHelper testHelper = new FrequentFlyerNumbersTestHelper();
		RestUtils restUtils = new RestUtils();

		String name = "Auto" + Utilities.getRandomNumberBetween(10000, 9999999);

		airlineDetails = new TravelProgramTestHelper().getTravelProgramData("AIRLINE");
		String jsonBody = testHelper.createBodyForEditFrequentFlyerNumber(airlineDetails, freqFlyerId, name);

		Response response = testHelper.editFrequentFlyerNumber(passengerId, freqFlyerId, jsonBody);

		validateResponseToContinueTest(response, 200, String.format("Unable to edit Frequent Flyer Number."), true);

		String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));

		String actualfreqFlyerId = String.valueOf(restUtils.getValueFromResponse(response, "content.frequentId"));
		softAssert.assertEquals(actualfreqFlyerId, freqFlyerId, String
				.format("Expected Frequent Flyer Id was '%s' and actual was '%s'", actualfreqFlyerId, freqFlyerId));

		freqFlyerNumber = (String) restUtils.getValueFromResponse(response, "content.frequentNumber");
		softAssert.assertEquals(freqFlyerNumber, name,
				String.format("Expected Frequent Flyer Number was '%s' and actual was '%s'", freqFlyerNumber, name));

		String actualFreqCategoryId = String
				.valueOf(restUtils.getValueFromResponse(response, "content.frequentCategory.id"));
		String expectedFreqCategoryId = String.valueOf(airlineDetails.get("id"));
		softAssert.assertEquals(actualFreqCategoryId, expectedFreqCategoryId,
				String.format("Expected Frequent Category Id was '%s' and actual was '%s'", expectedFreqCategoryId,
						actualFreqCategoryId));

		String actualFreqCategoryDescription = String
				.valueOf(restUtils.getValueFromResponse(response, "content.frequentCategory.description"));
		String expectedFreqCategoryDescription = (String) airlineDetails.get("description");
		softAssert.assertEquals(actualFreqCategoryDescription, expectedFreqCategoryDescription,
				String.format("Expected Frequent Category Description was '%s' and actual was '%s'",
						expectedFreqCategoryDescription, actualFreqCategoryDescription));

		String actualFreqNumberTypeId = String
				.valueOf(restUtils.getValueFromResponse(response, "content.frequentNumType.id"));
		String expectedFreqNumberTypeId = "1";
		softAssert.assertEquals(actualFreqNumberTypeId, expectedFreqNumberTypeId,
				String.format("Expected Frequent Number Type Id was '%s' and actual was '%s'", expectedFreqNumberTypeId,
						actualFreqNumberTypeId));

		String actualFreqNumberTypeDescription = String
				.valueOf(restUtils.getValueFromResponse(response, "content.frequentNumType.description"));
		String expectedFreqNumberTypeDescription = "AIRLINE";
		softAssert.assertEquals(actualFreqNumberTypeDescription, expectedFreqNumberTypeDescription,
				String.format("Expected Frequent Category Description was '%s' and actual was '%s'",
						expectedFreqNumberTypeDescription, actualFreqNumberTypeDescription));
		softAssert.assertAll();

		test.log(Status.PASS, "Edit Frequent Flyer Number verification was successful.");

	}

	
	
	/**
	 * This test method will test add freq flyer number for incorrect dto
	 * 
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority = 2, dataProvider = "addFreqFlyerNumberWithMissingDTO", dataProviderClass = FrequentFlyerNumbersDataproviders.class)
	public void verifyAddFreqFlyerNumberForMissingDTOs(Map<Object, Object> dtoMap) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		FrequentFlyerNumbersTestHelper testHelper = new FrequentFlyerNumbersTestHelper();
		RestUtils restUtils = new RestUtils();
		String testScenario = (String) dtoMap.get("dataScenario");
		String expectedStatus = (String) dtoMap.get("expectedStatus");
		String expectedResponseMessage = (String) dtoMap.get("expectedMessage");
		String name = "Auto" + Utilities.getRandomNumberBetween(10000, 9999999);
		dtoMap.put("frequentNumber", name);
		dtoMap.remove("dataScenario");
		dtoMap.remove("expectedStatus");
		dtoMap.remove("expectedMessage");
		// Fetch the passenger info to verify in the response of add Passenger
		Response passengerInfoResponse = new PassengerTestsHelper().getUserInfo(passengerEmail);
		validateResponseToContinueTest(passengerInfoResponse, 200,
				"Unable to fetch passenger info for user with email id - " + passengerEmail, true);
		passengerId = String.valueOf(restUtils.getValueFromResponse(passengerInfoResponse, "content.passengerId"));
		dtoMap = testHelper.constructBodyForMissingDTOFreqFlyerNumberTests(dtoMap, testScenario);

		String jsonBody = Utilities.createJsonBodyFromMap(dtoMap);
		Response response = testHelper.addFrequentFlyerNumber(passengerId, jsonBody);

		dtoMap.put("dataScenario", testScenario);
		dtoMap.put("expectedStatus", expectedStatus);
		dtoMap.put("expectedMessage", expectedResponseMessage);

		validateResponseToContinueTest(response, 200, String.format("Unable to add Frequent Flyer Number."), true);

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));

		softAssert.assertAll();

		test.log(Status.PASS, "Add Frequent Flyer Number for Missing DTos verification was successful.");

	}
	/**
	 * This test method will test add freq flyer number for invalid data
	 * 
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority = 3, dependsOnMethods = "verifyAddFrequentFlyerNumbersWithValidData", dataProvider = "addFreqFlyerNumberForInvalidData", dataProviderClass = FrequentFlyerNumbersDataproviders.class)
	public void verifyAddFreqFlyerNumberForInvaliData(Map<Object, Object> dtoMap) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		FrequentFlyerNumbersTestHelper testHelper = new FrequentFlyerNumbersTestHelper();
		RestUtils restUtils = new RestUtils();
		String testScenario = (String) dtoMap.get("dataScenario");
		String expectedStatus = (String) dtoMap.get("expectedStatus");
		String expectedResponseMessage = (String) dtoMap.get("expectedMessage");
		String name = "Auto" + Utilities.getRandomNumberBetween(10000, 9999999);
		dtoMap.put("frequentNumber", name);
		dtoMap.remove("dataScenario");
		dtoMap.remove("expectedStatus");
		dtoMap.remove("expectedMessage");
		dtoMap = testHelper.constructBodyForFreqFlyerNumberWithInvalidData(dtoMap, testScenario);
		if (testScenario.equals("Already Existing Frequent Number")) {
			dtoMap.put("frequentNumber", freqFlyerNumber);
		}
		// Fetch the passenger info to verify in the response of add Passenger
		Response passengerInfoResponse = new PassengerTestsHelper().getUserInfo(passengerEmail);
		validateResponseToContinueTest(passengerInfoResponse, 200,
				"Unable to fetch passenger info for user with email id - " + passengerEmail, true);
		passengerId = String.valueOf(restUtils.getValueFromResponse(passengerInfoResponse, "content.passengerId"));
		String jsonBody = Utilities.createJsonBodyFromMap(dtoMap);
		Response response = testHelper.addFrequentFlyerNumber(passengerId, jsonBody);

		dtoMap.put("dataScenario", testScenario);
		dtoMap.put("expectedStatus", expectedStatus);
		dtoMap.put("expectedMessage", expectedResponseMessage);

		validateResponseToContinueTest(response, 200, String.format("Unable to add Frequent Flyer Number."), true);

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));

		softAssert.assertAll();

		test.log(Status.PASS, "Add Frequent Flyer Number for in valid data verification was successful.");

	}
	/**
	 * This method will verify delete freq flyer number with valid data
	 * 
	 * @throws Exception
	 */
	@Test(priority = 4, dependsOnMethods = "verifyEditFrequentFlyerNumbersWithValidData")
	public void verifyDeleteFrequentFlyerNumbersWithValidData() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		FrequentFlyerNumbersTestHelper testHelper = new FrequentFlyerNumbersTestHelper();
		RestUtils restUtils = new RestUtils();

		Response response = testHelper.deleteFrequentFlyerNumber(passengerId, freqFlyerId);

		validateResponseToContinueTest(response, 200, String.format("Unable to delete Frequent Flyer Number."), true);

		String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));

		String expectedContent = "1 deleted successfully.";
		String actualContent = (String) restUtils.getValueFromResponse(response, "content");
		softAssert.assertEquals(actualContent, expectedContent,
				String.format("Expected Content was '%s' and actual was '%s'", expectedContent, actualContent));

		softAssert.assertAll();

		test.log(Status.PASS, "Delete Frequent Flyer Number verification was successful.");

	}
	

	/**
	 * This method will test the maximum allowed FFN to be added for an user.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 5)
	public void verifyMaximumFreqFlyerNumberForAPassenger() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		FrequentFlyerNumbersTestHelper testHelper = new FrequentFlyerNumbersTestHelper();
		RestUtils restUtils = new RestUtils();
		testHelper.deleteAllFrequentFlyerNumbers(passengerEmail);
		// Fetch the passenger info to verify in the response of add Passenger
		Response passengerInfoResponse = new PassengerTestsHelper().getUserInfo(passengerEmail);
		validateResponseToContinueTest(passengerInfoResponse, 200,
				"Unable to fetch passenger info for user with email id - " + passengerEmail, true);
		passengerId = String.valueOf(restUtils.getValueFromResponse(passengerInfoResponse, "content.passengerId"));
		int maxAllowedCount = 10;
		for (int i = 0; i < maxAllowedCount; i++) {
			String name = "Auto" + Utilities.getRandomNumberBetween(10000, 9999999);

			airlineDetails = new TravelProgramTestHelper().getTravelProgramData("AIRLINE");
			String jsonBody = testHelper.createBodyForFrequentFlyerNumber(airlineDetails, name);

			Response response = testHelper.addFrequentFlyerNumber(passengerId, jsonBody);

			validateResponseToContinueTest(response, 200, String.format("Unable to add Frequent Flyer Number."), true);

			String expectedStatus = "SUCCESS";
			softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
					String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
							restUtils.getValueFromResponse(response, "status")));

			String expectedResponseMessage = "Execution Successful";
			softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
					String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
							restUtils.getValueFromResponse(response, "message")));

		}

		// For the 11th Freq Flyer
		String name = "Auto" + Utilities.getRandomNumberBetween(10000, 9999999);

		airlineDetails = new TravelProgramTestHelper().getTravelProgramData("AIRLINE");
		String jsonBody = testHelper.createBodyForFrequentFlyerNumber(airlineDetails, name);

		Response response = testHelper.addFrequentFlyerNumber(passengerId, jsonBody);

		validateResponseToContinueTest(response, 200, String.format("Unable to add Frequent Flyer Number."), true);

		String expectedStatus = "FAILURE";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = "You can save up to 10 frequent flyer numbers only";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));

		// Delete all added Freq Flyer Number
		testHelper.deleteAllFrequentFlyerNumbers(passengerEmail);

		softAssert.assertAll();

		test.log(Status.PASS, "Add Max Frequent Flyer Number verification was successful.");

	}

	/**
	 * This test method will test update freq flyer number for incorrect dto
	 * 
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority = 6, dependsOnMethods = "verifyEditFrequentFlyerNumbersWithValidData", dataProvider = "updateFreqFlyerNumberWithMissingDTO", dataProviderClass = FrequentFlyerNumbersDataproviders.class)
	public void verifyUpdateFreqFlyerNumberForMissingDTOs(Map<Object, Object> dtoMap) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		FrequentFlyerNumbersTestHelper testHelper = new FrequentFlyerNumbersTestHelper();
		RestUtils restUtils = new RestUtils();
		String testScenario = (String) dtoMap.get("dataScenario");
		String expectedStatus = (String) dtoMap.get("expectedStatus");
		String expectedResponseMessage = (String) dtoMap.get("expectedMessage");
		String name = "Auto" + Utilities.getRandomNumberBetween(10000, 9999999);
		dtoMap.put("frequentNumber", name);
		dtoMap.put("frequentId", freqFlyerId);
		dtoMap.remove("dataScenario");
		dtoMap.remove("expectedStatus");
		dtoMap.remove("expectedMessage");
		dtoMap = testHelper.constructBodyForMissingDTOFreqFlyerNumberTests(dtoMap, testScenario);
		String testFFNid = String.valueOf(dtoMap.get("frequentId"));
		if (testScenario.contains("Missing Frequent Id")) {
			testFFNid = freqFlyerId;
		}

		String jsonBody = Utilities.createJsonBodyFromMap(dtoMap);
		Response response = testHelper.editFrequentFlyerNumber(passengerId, testFFNid, jsonBody);

		dtoMap.put("dataScenario", testScenario);
		dtoMap.put("expectedStatus", expectedStatus);
		dtoMap.put("expectedMessage", expectedResponseMessage);

		validateResponseToContinueTest(response, 200, String.format("Unable to update Frequent Flyer Number."), true);

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));

		softAssert.assertAll();

		test.log(Status.PASS, "Update Frequent Flyer Number for Missing DTos verification was successful.");

	}

	/**
	 * This test method will test add freq flyer number for invalid data
	 * 
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority = 7, dependsOnMethods = "verifyEditFrequentFlyerNumbersWithValidData", dataProvider = "updateFreqFlyerNumberForInvalidData", dataProviderClass = FrequentFlyerNumbersDataproviders.class)
	public void verifyUpdateFreqFlyerNumberForInvaliData(Map<Object, Object> dtoMap) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		FrequentFlyerNumbersTestHelper testHelper = new FrequentFlyerNumbersTestHelper();
		RestUtils restUtils = new RestUtils();
		String testScenario = (String) dtoMap.get("dataScenario");
		String expectedStatus = (String) dtoMap.get("expectedStatus");
		String expectedResponseMessage = (String) dtoMap.get("expectedMessage");
		String name = "Auto" + Utilities.getRandomNumberBetween(10000, 9999999);
		dtoMap.put("frequentNumber", name);
		dtoMap.put("frequentId", freqFlyerId);
		dtoMap.remove("dataScenario");
		dtoMap.remove("expectedStatus");
		dtoMap.remove("expectedMessage");
		if (testScenario.contains("Invalid Frequent id")) {
			dtoMap.put("frequentId", "99999");
		}
		if (testScenario.contains("Empty Frequent Number Type id")) {
		}
		dtoMap = testHelper.constructBodyForFreqFlyerNumberWithInvalidData(dtoMap, testScenario);
		String jsonBody = Utilities.createJsonBodyFromMap(dtoMap);
		Response response = testHelper.editFrequentFlyerNumber(passengerId, freqFlyerId, jsonBody);
		dtoMap.put("dataScenario", testScenario);
		dtoMap.put("expectedStatus", expectedStatus);
		dtoMap.put("expectedMessage", expectedResponseMessage);

		validateResponseToContinueTest(response, 200, String.format("Unable to update Frequent Flyer Number."), true);

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));

		softAssert.assertAll();

		test.log(Status.PASS, "Update Frequent Flyer Number for in valid verification was successful.");

	}

	/**
	 * This method will verify delete freq flyer number with invalid data
	 * 
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority = 8, dependsOnMethods = "verifyDeleteFrequentFlyerNumbersWithValidData", dataProvider = "deleteFreqFlyerNumberWithInvalidData", dataProviderClass = FrequentFlyerNumbersDataproviders.class)
	public void verifyDeleteFrequentFlyerNumbersWithInvalidData(Map<Object, Object> dtoMap) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		FrequentFlyerNumbersTestHelper testHelper = new FrequentFlyerNumbersTestHelper();
		RestUtils restUtils = new RestUtils();

		String testScenario = (String) dtoMap.get("dataScenario");
		String expectedStatus = (String) dtoMap.get("expectedStatus");
		String expectedResponseMessage = (String) dtoMap.get("expectedMessage");
		String testPassengerId = passengerId;
		String testFFNId = freqFlyerId;
		if (testScenario.contains("Invalid Passenger Id")) {
			testPassengerId = "99999";
		}
		if (testScenario.contains("Invalid Frequent Flyer Id")) {
			testFFNId = "99999";
		}

		Response response = testHelper.deleteFrequentFlyerNumber(testPassengerId, testFFNId);

		validateResponseToContinueTest(response, 200, String.format("Unable to delete Frequent Flyer Number."), true);

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));

		softAssert.assertAll();

		test.log(Status.PASS, "Delete Frequent Flyer Number for invalid data verification was successful.");

	}

}
