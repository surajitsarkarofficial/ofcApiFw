package tests.testcases.submodules.hotelLoyaltyPrograms;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import dataproviders.submodules.hotelLoyaltyPrograms.HotelLoyaltyProgramsDataproviders;
import io.restassured.response.Response;
import tests.testcases.TravelMobileBaseTest;
import tests.testhelpers.submodules.hotelLoyalPrograms.HotelLoyaltyProgramsTestHelper;
import tests.testhelpers.submodules.myTrips.features.PassengerTestsHelper;
import tests.testhelpers.submodules.travelProgram.TravelProgramTestHelper;
import utils.RestUtils;
import utils.Utilities;

public class HotelLoyaltyProgramsTests extends TravelMobileBaseTest{
	
	public static String hotelLoyaltyProgId,hotelLoyaltyProgNumber,passengerId;
	public static String passengerEmail = "surajit.sarkar@globant.com";
	public static Map<String, Object> hotelDetails;
	@BeforeTest(alwaysRun = true)
	public void beforeTest() {
		subModuleTest = moduleTest.createNode("HotelLoyaltyProgramsTests");
	}

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("HotelLoyaltyProgramsTests");
	}
	/**
	 * This test method will verify add hotel loyalty prog number api with valid data
	 * @throws Exception
	 */
	@Test(priority = 0)
	public void verifyAddHotelLoyaltyProgramWithValidData() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		HotelLoyaltyProgramsTestHelper testHelper = new HotelLoyaltyProgramsTestHelper();
		RestUtils restUtils = new RestUtils();
		
		String name="Auto"+Utilities.getRandomNumberBetween(10000, 9999999);
		
		// Fetch the passenger info to verify in the response of add Passenger
		Response passengerInfoResponse = new PassengerTestsHelper().getUserInfo(passengerEmail);
		validateResponseToContinueTest(passengerInfoResponse, 200,
						"Unable to fetch passenger info for user with email id - " + passengerEmail, true);
		passengerId = String.valueOf(restUtils.getValueFromResponse(passengerInfoResponse, "content.passengerId"));
				
		
		hotelDetails =new TravelProgramTestHelper().getTravelProgramData("HOTEL");
		String jsonBody = testHelper.createBodyForHotelLoyaltyProgram(hotelDetails,name);
		
		Response response =testHelper.addHotelLoyaltyProgram(passengerId,jsonBody);
		
		validateResponseToContinueTest(response, 200,
				String.format("Unable to add Hotel Loyalty Program."), true);
		
		String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus, String
				.format("Expected status was '%s' and actual was '%s'", expectedStatus,restUtils.getValueFromResponse(response, "status")));
		
		String expectedResponseMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage, String
				.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,restUtils.getValueFromResponse(response, "message")));
		
		hotelLoyaltyProgId = String.valueOf(restUtils.getValueFromResponse(response, "content.frequentId"));
		softAssert.assertTrue(hotelLoyaltyProgId!=null && hotelLoyaltyProgId!="","Hotel Loyalty Program Id was null or empty");
		
		hotelLoyaltyProgNumber = (String) restUtils.getValueFromResponse(response, "content.frequentNumber");
		softAssert.assertEquals(hotelLoyaltyProgNumber, name, String
				.format("Expected Hotel Loyalty Program Number was '%s' and actual was '%s'", hotelLoyaltyProgNumber,name));
		
		
		String actualFreqCategoryId = String.valueOf(restUtils.getValueFromResponse(response, "content.frequentCategory.id"));
		String expectedFreqCategoryId = String.valueOf(hotelDetails.get("id"));
		softAssert.assertEquals(actualFreqCategoryId, expectedFreqCategoryId, String
				.format("Expected Frequent Category Id was '%s' and actual was '%s'", expectedFreqCategoryId,actualFreqCategoryId));
		
		String actualFreqCategoryDescription = String.valueOf(restUtils.getValueFromResponse(response, "content.frequentCategory.description"));
		String expectedFreqCategoryDescription = (String) hotelDetails.get("description");
		softAssert.assertEquals(actualFreqCategoryDescription, expectedFreqCategoryDescription, String
				.format("Expected Frequent Category Description was '%s' and actual was '%s'", expectedFreqCategoryDescription,actualFreqCategoryDescription));
		
		String actualFreqNumberTypeId = String.valueOf(restUtils.getValueFromResponse(response, "content.frequentNumType.id"));
		String expectedFreqNumberTypeId = "2";
		softAssert.assertEquals(actualFreqNumberTypeId, expectedFreqNumberTypeId, String
				.format("Expected Frequent Number Type Id was '%s' and actual was '%s'", expectedFreqNumberTypeId,actualFreqNumberTypeId));
		
		String actualFreqNumberTypeDescription = String.valueOf(restUtils.getValueFromResponse(response, "content.frequentNumType.description"));
		String expectedFreqNumberTypeDescription = "HOTEL";
		softAssert.assertEquals(actualFreqNumberTypeDescription, expectedFreqNumberTypeDescription, String
				.format("Expected Frequent Category Description was '%s' and actual was '%s'", expectedFreqNumberTypeDescription,actualFreqNumberTypeDescription));
		softAssert.assertAll();

		test.log(Status.PASS, "Add Hotel Loyalty Program verification was successful.");

	}
	
	
	/**
	 * This method will verify edit Hotel Loyalty Program with valid data
	 * @throws Exception
	 */
	@Test(priority = 1,dependsOnMethods = "verifyAddHotelLoyaltyProgramWithValidData")
	public void verifyEditHotelLoyaltyProgramWithValidData() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		HotelLoyaltyProgramsTestHelper testHelper = new HotelLoyaltyProgramsTestHelper();
		RestUtils restUtils = new RestUtils();
		
		String name="Auto"+Utilities.getRandomNumberBetween(10000, 9999999);
		
		hotelDetails =new TravelProgramTestHelper().getTravelProgramData("HOTEL");
		String jsonBody = testHelper.createBodyForEditHotelLoyaltyProgram(hotelDetails,hotelLoyaltyProgId,name);
		
		Response response =testHelper.editHotelLoyaltyProgram(passengerId,hotelLoyaltyProgId,jsonBody);
		
		validateResponseToContinueTest(response, 200,
				String.format("Unable to edit Hotel Loyalty Program."), true);
		
		String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus, String
				.format("Expected status was '%s' and actual was '%s'", expectedStatus,restUtils.getValueFromResponse(response, "status")));
		
		String expectedResponseMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage, String
				.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,restUtils.getValueFromResponse(response, "message")));
		
		String actualfreqFlyerId = String.valueOf(restUtils.getValueFromResponse(response, "content.frequentId"));
		softAssert.assertEquals(actualfreqFlyerId, hotelLoyaltyProgId, String
				.format("Expected Frequent Flyer Id was '%s' and actual was '%s'", actualfreqFlyerId,hotelLoyaltyProgId));
		
		hotelLoyaltyProgNumber = (String) restUtils.getValueFromResponse(response, "content.frequentNumber");
		softAssert.assertEquals(hotelLoyaltyProgNumber, name, String
				.format("Expected Hotel Loyalty Program Number was '%s' and actual was '%s'", hotelLoyaltyProgNumber,name));
		
		
		String actualFreqCategoryId = String.valueOf(restUtils.getValueFromResponse(response, "content.frequentCategory.id"));
		String expectedFreqCategoryId = String.valueOf(hotelDetails.get("id"));
		softAssert.assertEquals(actualFreqCategoryId, expectedFreqCategoryId, String
				.format("Expected Frequent Category Id was '%s' and actual was '%s'", expectedFreqCategoryId,actualFreqCategoryId));
		
		String actualFreqCategoryDescription = String.valueOf(restUtils.getValueFromResponse(response, "content.frequentCategory.description"));
		String expectedFreqCategoryDescription = (String) hotelDetails.get("description");
		softAssert.assertEquals(actualFreqCategoryDescription, expectedFreqCategoryDescription, String
				.format("Expected Frequent Category Description was '%s' and actual was '%s'", expectedFreqCategoryDescription,actualFreqCategoryDescription));
		
		String actualFreqNumberTypeId = String.valueOf(restUtils.getValueFromResponse(response, "content.frequentNumType.id"));
		String expectedFreqNumberTypeId = "2";
		softAssert.assertEquals(actualFreqNumberTypeId, expectedFreqNumberTypeId, String
				.format("Expected Frequent Number Type Id was '%s' and actual was '%s'", expectedFreqNumberTypeId,actualFreqNumberTypeId));
		
		String actualFreqNumberTypeDescription = String.valueOf(restUtils.getValueFromResponse(response, "content.frequentNumType.description"));
		String expectedFreqNumberTypeDescription = "HOTEL";
		softAssert.assertEquals(actualFreqNumberTypeDescription, expectedFreqNumberTypeDescription, String
				.format("Expected Frequent Category Description was '%s' and actual was '%s'", expectedFreqNumberTypeDescription,actualFreqNumberTypeDescription));
		softAssert.assertAll();

		test.log(Status.PASS, "Edit Hotel Loyalty Program verification was successful.");

	}
	/**
	 * This test method will test add hotel loyalty number for incorrect dto
	 * 
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority = 2, dataProvider = "addHotelLoyaltyProgramWithMissingDTO", dataProviderClass = HotelLoyaltyProgramsDataproviders.class)
	public void verifyAddHotelLoyaltyProgramForMissingDTOs(Map<Object, Object> dtoMap) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		HotelLoyaltyProgramsTestHelper testHelper = new HotelLoyaltyProgramsTestHelper();
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
		dtoMap = testHelper.constructBodyForMissingDTOHotelLoyaltyProgramsTests(dtoMap, testScenario);

		String jsonBody = Utilities.createJsonBodyFromMap(dtoMap);
		Response response = testHelper.addHotelLoyaltyProgram(passengerId, jsonBody);

		dtoMap.put("dataScenario", testScenario);
		dtoMap.put("expectedStatus", expectedStatus);
		dtoMap.put("expectedMessage", expectedResponseMessage);

		validateResponseToContinueTest(response, 200, String.format("Unable to add Hotel Loyalty Program."), true);

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));

		softAssert.assertAll();

		test.log(Status.PASS, "Add Hotel Loyalty Program for Missing DTos verification was successful.");

	}
	/**
	 * This test method will test add hotel loyalty program for invalid data
	 * 
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority = 3, dependsOnMethods = "verifyAddHotelLoyaltyProgramWithValidData", dataProvider = "addHotelLoyaltyProgramForInvalidData", dataProviderClass = HotelLoyaltyProgramsDataproviders.class)
	public void verifyAddHotelLoyaltyProgramForInvaliData(Map<Object, Object> dtoMap) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		HotelLoyaltyProgramsTestHelper testHelper = new HotelLoyaltyProgramsTestHelper();
		RestUtils restUtils = new RestUtils();
		String testScenario = (String) dtoMap.get("dataScenario");
		String expectedStatus = (String) dtoMap.get("expectedStatus");
		String expectedResponseMessage = (String) dtoMap.get("expectedMessage");
		String name = "Auto" + Utilities.getRandomNumberBetween(10000, 9999999);
		dtoMap.put("frequentNumber", name);
		dtoMap.remove("dataScenario");
		dtoMap.remove("expectedStatus");
		dtoMap.remove("expectedMessage");
		dtoMap = testHelper.constructBodyForHotelLoyaltyProgramWithInvalidData(dtoMap, testScenario);
		if (testScenario.equals("Already Existing Hotel Loyalty Program Number")) {
			dtoMap.put("frequentNumber", hotelLoyaltyProgNumber);
		}
		// Fetch the passenger info to verify in the response of add Passenger
		Response passengerInfoResponse = new PassengerTestsHelper().getUserInfo(passengerEmail);
		validateResponseToContinueTest(passengerInfoResponse, 200,
				"Unable to fetch passenger info for user with email id - " + passengerEmail, true);
		passengerId = String.valueOf(restUtils.getValueFromResponse(passengerInfoResponse, "content.passengerId"));
		String jsonBody = Utilities.createJsonBodyFromMap(dtoMap);
		Response response = testHelper.addHotelLoyaltyProgram(passengerId, jsonBody);

		dtoMap.put("dataScenario", testScenario);
		dtoMap.put("expectedStatus", expectedStatus);
		dtoMap.put("expectedMessage", expectedResponseMessage);

		validateResponseToContinueTest(response, 200, String.format("Unable to add Hotel Loyalty Program."), true);

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));

		softAssert.assertAll();

		test.log(Status.PASS, "Add Hotel Loyalty Program for Invalid data verification was successful.");

	}
	/**
	 * This method will verify delete Hotel Loyalty Program with valid data
	 * @throws Exception
	 */
	@Test(priority = 4,dependsOnMethods = "verifyEditHotelLoyaltyProgramWithValidData")
	public void verifyDeleteHotelLoyaltyProgramWithValidData() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		HotelLoyaltyProgramsTestHelper testHelper = new HotelLoyaltyProgramsTestHelper();
		RestUtils restUtils = new RestUtils();
		
		Response response =testHelper.deleteHotelLoyaltyProgram(passengerId,hotelLoyaltyProgId);
		
		validateResponseToContinueTest(response, 200,
				String.format("Unable to delete Hotel Loyalty Program."), true);
		
		String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus, String
				.format("Expected status was '%s' and actual was '%s'", expectedStatus,restUtils.getValueFromResponse(response, "status")));
		
		String expectedResponseMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage, String
				.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,restUtils.getValueFromResponse(response, "message")));
		
		String expectedContent = "1 deleted successfully.";
		String actualContent = (String) restUtils.getValueFromResponse(response, "content");
		softAssert.assertEquals(actualContent, expectedContent, String
				.format("Expected Content was '%s' and actual was '%s'", expectedContent,actualContent));
		
		softAssert.assertAll();

		test.log(Status.PASS, "Delete Hotel Loyalty Program verification was successful.");

	}
	/**
	 * This method will test the maximum allowed HLP to be added for an user.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 5)
	public void verifyMaximumHotelLoyaltyProgramForAPassenger() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		HotelLoyaltyProgramsTestHelper testHelper = new HotelLoyaltyProgramsTestHelper();
		RestUtils restUtils = new RestUtils();
		testHelper.deleteAllHotelLoyaltyPrograms(passengerEmail);
		// Fetch the passenger info to verify in the response of add Passenger
		Response passengerInfoResponse = new PassengerTestsHelper().getUserInfo(passengerEmail);
		validateResponseToContinueTest(passengerInfoResponse, 200,
				"Unable to fetch passenger info for user with email id - " + passengerEmail, true);
		passengerId = String.valueOf(restUtils.getValueFromResponse(passengerInfoResponse, "content.passengerId"));
		int maxAllowedCount = 10;
		for (int i = 0; i < maxAllowedCount; i++) {
			String name = "Auto" + Utilities.getRandomNumberBetween(10000, 9999999);

			hotelDetails = new TravelProgramTestHelper().getTravelProgramData("HOTEL");
			String jsonBody = testHelper.createBodyForHotelLoyaltyProgram(hotelDetails, name);

			Response response = testHelper.addHotelLoyaltyProgram(passengerId, jsonBody);

			validateResponseToContinueTest(response, 200, String.format("Unable to add Hotel Loyalty Program."), true);

			String expectedStatus = "SUCCESS";
			softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
					String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
							restUtils.getValueFromResponse(response, "status")));

			String expectedResponseMessage = "Execution Successful";
			softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
					String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
							restUtils.getValueFromResponse(response, "message")));

		}

		// For the 11th Hotel Loyalty Program
		String name = "Auto" + Utilities.getRandomNumberBetween(10000, 9999999);

		hotelDetails = new TravelProgramTestHelper().getTravelProgramData("HOTEL");
		String jsonBody = testHelper.createBodyForHotelLoyaltyProgram(hotelDetails, name);

		Response response = testHelper.addHotelLoyaltyProgram(passengerId, jsonBody);

		validateResponseToContinueTest(response, 200, String.format("Unable to add Hotel Loyalty Program."), true);

		String expectedStatus = "FAILURE";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = "You can save up to 10 hotel loyalty numbers only";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));

		// Delete all added Hotel Loyalty Program
		testHelper.deleteAllHotelLoyaltyPrograms(passengerEmail);

		softAssert.assertAll();

		test.log(Status.PASS, "Add Maximum Hotel Loyalty Program verification was successful.");

	}
	
	/**
	 * This test method will test update hotel loyalty number for incorrect dto
	 * 
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority = 6, dependsOnMethods = "verifyEditHotelLoyaltyProgramWithValidData", dataProvider = "updateHotelLoyaltyProgramWithMissingDTO", dataProviderClass = HotelLoyaltyProgramsDataproviders.class)
	public void verifyUpdateHotelLoyaltyProgramForMissingDTOs(Map<Object, Object> dtoMap) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		HotelLoyaltyProgramsTestHelper testHelper = new HotelLoyaltyProgramsTestHelper();
		RestUtils restUtils = new RestUtils();
		String testScenario = (String) dtoMap.get("dataScenario");
		String expectedStatus = (String) dtoMap.get("expectedStatus");
		String expectedResponseMessage = (String) dtoMap.get("expectedMessage");
		String name = "Auto" + Utilities.getRandomNumberBetween(10000, 9999999);
		dtoMap.put("frequentNumber", name);
		dtoMap.put("frequentId", hotelLoyaltyProgId);
		dtoMap.remove("dataScenario");
		dtoMap.remove("expectedStatus");
		dtoMap.remove("expectedMessage");
		dtoMap = testHelper.constructBodyForMissingDTOHotelLoyaltyProgramsTests(dtoMap, testScenario);
		String testHLPid = String.valueOf(dtoMap.get("frequentId"));
		if (testScenario.contains("Missing Frequent Id")) {
			testHLPid = hotelLoyaltyProgId;
		}

		String jsonBody = Utilities.createJsonBodyFromMap(dtoMap);
		Response response = testHelper.editHotelLoyaltyProgram(passengerId, testHLPid, jsonBody);

		dtoMap.put("dataScenario", testScenario);
		dtoMap.put("expectedStatus", expectedStatus);
		dtoMap.put("expectedMessage", expectedResponseMessage);

		validateResponseToContinueTest(response, 200, String.format("Unable to update Hotel Loyalty Program."), true);

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));

		softAssert.assertAll();

		test.log(Status.PASS, "Update Hotel Loyalty Program for Missing DTos verification was successful.");

	}

	/**
	 * This test method will test update hotel loyalty prog for invalid data
	 * 
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority = 7, dependsOnMethods = "verifyEditHotelLoyaltyProgramWithValidData", dataProvider = "updateHotelLoyaltyProgramForInvalidData", dataProviderClass = HotelLoyaltyProgramsDataproviders.class)
	public void verifyUpdateHotelLoyaltyProgramForInvaliData(Map<Object, Object> dtoMap) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		HotelLoyaltyProgramsTestHelper testHelper = new HotelLoyaltyProgramsTestHelper();
		RestUtils restUtils = new RestUtils();
		String testScenario = (String) dtoMap.get("dataScenario");
		String expectedStatus = (String) dtoMap.get("expectedStatus");
		String expectedResponseMessage = (String) dtoMap.get("expectedMessage");
		String name = "Auto" + Utilities.getRandomNumberBetween(10000, 9999999);
		dtoMap.put("frequentNumber", name);
		dtoMap.put("frequentId", hotelLoyaltyProgId);
		dtoMap.remove("dataScenario");
		dtoMap.remove("expectedStatus");
		dtoMap.remove("expectedMessage");
		if (testScenario.contains("Invalid Frequent id")) {
			dtoMap.put("frequentId", "99999");
		}
		dtoMap = testHelper.constructBodyForHotelLoyaltyProgramWithInvalidData(dtoMap, testScenario);
		String jsonBody = Utilities.createJsonBodyFromMap(dtoMap);
		Response response = testHelper.editHotelLoyaltyProgram(passengerId, hotelLoyaltyProgId, jsonBody);
		dtoMap.put("dataScenario", testScenario);
		dtoMap.put("expectedStatus", expectedStatus);
		dtoMap.put("expectedMessage", expectedResponseMessage);

		validateResponseToContinueTest(response, 200, String.format("Unable to update Hotel Loyalty Program."), true);

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));

		softAssert.assertAll();

		test.log(Status.PASS, "Update Hotel Loyalty Program for invalid data verification was successful.");

	}

	/**
	 * This method will verify delete Hotel Loyalty Program with invalid data
	 * 
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority = 8, dependsOnMethods = "verifyDeleteHotelLoyaltyProgramWithValidData", dataProvider = "deleteHotelLoyaltyProgramWithInvalidData", dataProviderClass = HotelLoyaltyProgramsDataproviders.class)
	public void verifyDeleteFrequentFlyerNumbersWithInvalidData(Map<Object, Object> dtoMap) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		HotelLoyaltyProgramsTestHelper testHelper = new HotelLoyaltyProgramsTestHelper();
		RestUtils restUtils = new RestUtils();

		String testScenario = (String) dtoMap.get("dataScenario");
		String expectedStatus = (String) dtoMap.get("expectedStatus");
		String expectedResponseMessage = (String) dtoMap.get("expectedMessage");
		String testPassengerId = passengerId;
		String testHLPId = hotelLoyaltyProgId;
		if (testScenario.contains("Invalid Passenger Id")) {
			testPassengerId = "99999";
		}
		if (testScenario.contains("Invalid Hotel Loyalty Program Id")) {
			testHLPId = "99999";
		}

		Response response = testHelper.deleteHotelLoyaltyProgram(testPassengerId, testHLPId);

		validateResponseToContinueTest(response, 200, String.format("Unable to delete Hotel Loyalty Program."), true);

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));

		softAssert.assertAll();

		test.log(Status.PASS, "Delete Hotel Loyalty Program for invalid data verification was successful.");

	}


}
