package tests.testcases.submodules.myTrips.features;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import dataproviders.submodules.myTrips.features.TripDataProvider;
import io.restassured.response.Response;
import tests.testcases.submodules.myTrips.MyTripsBaseTest;
import tests.testhelpers.submodules.myTrips.features.TravelReasonTestsHelper;
import tests.testhelpers.submodules.myTrips.features.TripTestsHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * This class contains test cases for Trip Api's
 * @author surajit.sarkar
 *
 */
public class TripTests extends MyTripsBaseTest {

	private static String tripId, tripName, tripStatus, purposeAndGoals, creatorMail;
	private static LinkedHashMap<String,Object> travelReason;
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("TripTests");
	}

	/**
	 * This Test will Create a Trip will valid data and verify the response
	 * 
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority=0,dataProvider = "createTripWithValidData", dataProviderClass = TripDataProvider.class)
	public void verifyCreateTripWithValidData(Map<Object, Object> dtoMap) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		TripTestsHelper tripTestsHelper = new TripTestsHelper();
		RestUtils restUtils = new RestUtils();
		
		travelReason =new TravelReasonTestsHelper().getTravelReason();
		dtoMap.put("travelReason", travelReason);		
		
		Response response = tripTestsHelper.createTrip(dtoMap);
		validateResponseToContinueTest(response, 200, "Unable to create Trip.", true);

		tripId = String.valueOf(restUtils.getValueFromResponse(response, "content.tripId"));
		String responseMessage = restUtils.getValueFromResponse(response, "message").toString();
		tripName = restUtils.getValueFromResponse(response, "content.tripName").toString();
		creatorMail = restUtils.getValueFromResponse(response, "content.creatorMail").toString();
		purposeAndGoals = restUtils.getValueFromResponse(response, "content.purposeAndGoals").toString();
		tripStatus = restUtils.getValueFromResponse(response, "content.status").toString();
		String expectedResponseMessage = "Execution Successful";
		String expectedTripStatus = "DRAFT";
		
		String expectedTravelReasonId = String.valueOf(travelReason.get("id"));
		String actualTravelReasonId = String.valueOf(restUtils.getValueFromResponse(response, "content.travelReason.id"));
		softAssert.assertEquals(actualTravelReasonId, expectedTravelReasonId, String
				.format("Expected Travel Reason Id was '%s' and actual was '%s'", expectedTravelReasonId, actualTravelReasonId));
		String expectedTravelReasonCode = (String) travelReason.get("code");
		String actualTravelReasonCode = (String)restUtils.getValueFromResponse(response, "content.travelReason.code");
		softAssert.assertEquals(actualTravelReasonCode, expectedTravelReasonCode, String
				.format("Expected Travel Reason Code was '%s' and actual was '%s'", expectedTravelReasonCode, actualTravelReasonCode));
		String expectedTravelReasonDescription = (String) travelReason.get("description");
		String actualTravelReasonDescription = (String)restUtils.getValueFromResponse(response, "content.travelReason.description");
		softAssert.assertEquals(actualTravelReasonDescription, expectedTravelReasonDescription, String
				.format("Expected Travel Reason Description was '%s' and actual was '%s'", expectedTravelReasonDescription, actualTravelReasonDescription));
		boolean expectedTravelReasonActiveStatus = (boolean) travelReason.get("active");
		boolean actualTravelReasonActiveStatus = (boolean)restUtils.getValueFromResponse(response, "content.travelReason.active");
		softAssert.assertEquals(actualTravelReasonActiveStatus, expectedTravelReasonActiveStatus, String
				.format("Expected Travel Reason Active Status was '%s' and actual was '%s'", expectedTravelReasonActiveStatus, actualTravelReasonActiveStatus));

		softAssert.assertEquals(responseMessage, expectedResponseMessage, String
				.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage, responseMessage));
		softAssert.assertEquals(tripName, dtoMap.get("tripName"),
				String.format("Expected trip name was '%s' and actual was '%s'", tripName, dtoMap.get("tripName")));
		softAssert.assertEquals(creatorMail, dtoMap.get("creatorMail"), String
				.format("Expected creator mail was '%s' and actual was '%s'", creatorMail, dtoMap.get("creatorMail")));
		softAssert.assertEquals(purposeAndGoals, dtoMap.get("purposeAndGoals"),
				String.format("Expected purpose and goals was '%s' and actual was '%s'", purposeAndGoals,
						dtoMap.get("purposeAndGoals")));
		softAssert.assertEquals(tripStatus, expectedTripStatus,
				String.format("Expected trip status was '%s' and actual was '%s'", tripStatus, expectedTripStatus));
		softAssert.assertTrue(String.valueOf(tripId) != null && (!String.valueOf(tripId).equals("")),
				"Trip Id was not present.");
		
		
		softAssert.assertAll();
		test.log(Status.PASS, "Create Trip verification was successful. Created Trip with id- " + tripId);

	}

	/**
	 * This test will verify the Create trip negative testing by passing invalid
	 * data
	 * 
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority=1,dataProvider = "createTripWithInvalidData", dataProviderClass = TripDataProvider.class)
	public void verifyCreateTripWithInvalidData(Map<Object, Object> dtoMap) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String testScenario = dtoMap.get("dataScenario").toString();
		test.log(Status.INFO,
				String.format("Validating Create Trip Negative Scenario by providing '%s'", testScenario));
		dtoMap.remove("dataScenario");
		String expectedMessage = dtoMap.get("expectedMessage").toString();
		dtoMap.remove("expectedMessage");
		TripTestsHelper tripTestsHelper = new TripTestsHelper();

		RestUtils restUtils = new RestUtils();
		LinkedHashMap<String,Object> travelReasonData =new TravelReasonTestsHelper().getTravelReason();
		dtoMap.put("travelReason", travelReasonData);		
		Response response = tripTestsHelper.createTrip(dtoMap);
		dtoMap.put("dataScenario",testScenario);
		int statusCode = response.getStatusCode();

		softAssert.assertEquals(statusCode, 200, String.format(
				"Expected status code was - 200 and actual was %d. Response - %s", statusCode, response.prettyPrint()));

		String actualStatus = restUtils.getValueFromResponse(response, "status").toString();
		String expectedStatus = "FAILURE";
		softAssert.assertEquals(actualStatus, expectedStatus,
				String.format("Expected status was '%s' , actual status was '%s' .Response - %s", expectedStatus,
						actualStatus, response.prettyPrint()));
		String responseMessage = restUtils.getValueFromResponse(response, "message").toString();
		
		softAssert.assertEquals(responseMessage, expectedMessage, String
				.format("Expected message was '%s' , actual status was '%s' .", expectedMessage, responseMessage));
		softAssert.assertAll();
		test.log(Status.PASS, "Create Trip Negative verification was successful.");
	}

	/**
	 * This test will verify the create trip by passing already existing trip name
	 * 
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority=2,dataProvider = "createTripWithValidData", dataProviderClass = TripDataProvider.class, dependsOnMethods = "verifyCreateTripWithValidData")
	public void verifyCreateTripWithExistingTripName(Map<Object, Object> dtoMap) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		test.log(Status.INFO, String
				.format("Validating Create Trip Negative Scenario by providing existing Trip name - " + tripName));

		TripTestsHelper tripTestsHelper = new TripTestsHelper();
		dtoMap.put("tripName", tripName);

		RestUtils restUtils = new RestUtils();
		LinkedHashMap<String,Object> travelReasonData =new TravelReasonTestsHelper().getTravelReason();
		dtoMap.put("travelReason", travelReasonData);
		Response response = tripTestsHelper.createTrip(dtoMap);
		validateResponseToContinueTest(response, 200, "Unable to verify create trip for invalid data.", true);

		String actualStatus = restUtils.getValueFromResponse(response, "status").toString();
		String expectedStatus = "FAILURE";
		softAssert.assertEquals(actualStatus, expectedStatus,
				String.format("Expected status was '%s' , actual status was '%s' .Response - %s", expectedStatus,
						actualStatus, response.prettyPrint()));

		String responseMessage = restUtils.getValueFromResponse(response, "message").toString();
		String expectedMessage = "Trip's name is already in use. Please enter a different one.";
		softAssert.assertEquals(responseMessage, expectedMessage, String
				.format("Expected message was '%s' , actual status was '%s' .", expectedMessage, responseMessage));
		softAssert.assertAll();
		test.log(Status.PASS, "Create Trip with existing trip name verification was successful.");
	}

	/**
	 * This test will verify the Get Trip details api
	 * 
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority=3,dependsOnMethods = "verifyCreateTripWithValidData")
	public void verifyGetTripDetailsWithValidData() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		TripTestsHelper tripTestsHelper = new TripTestsHelper();

		RestUtils restUtils = new RestUtils();

		Response response = tripTestsHelper.getTripDetails(tripId);

		validateResponseToContinueTest(response, 200, "Unable to fetch Trip details for Trip Id " + tripId, true);

		String expectedPurposeandGoals = purposeAndGoals;
		String expectedCreatorMail = creatorMail;
		String expectedResponseMessage = "Execution Successful";
		String expectedTripStatus = "DRAFT";

		String actualResponseMessage = restUtils.getValueFromResponse(response, "message").toString();
		String actualTripName = restUtils.getValueFromResponse(response, "content.tripName").toString();
		String actualTripId = restUtils.getValueFromResponse(response, "content.tripId").toString();
		String actualCreatorMail = restUtils.getValueFromResponse(response, "content.creatorMail").toString();
		String actualTripStatus = restUtils.getValueFromResponse(response, "content.status").toString();
		String actualPurposeAndGoals = restUtils.getValueFromResponse(response, "content.purpose").toString();

		softAssert.assertEquals(actualResponseMessage, expectedResponseMessage, String.format(
				"Expected message was '%s' and actual was '%s'", expectedResponseMessage, actualResponseMessage));
		softAssert.assertEquals(actualTripId, String.valueOf(tripId),
				String.format("Expected trip status was '%s' and actual was '%s'", tripId, actualTripId));
		softAssert.assertEquals(actualTripName, tripName,
				String.format("Expected trip name was '%s' and actual was '%s'", tripName, actualTripName));
		softAssert.assertEquals(actualTripStatus, expectedTripStatus, String
				.format("Expected trip status was '%s' and actual was '%s'", expectedTripStatus, actualTripStatus));
		softAssert.assertEquals(actualPurposeAndGoals, expectedPurposeandGoals,
				String.format("Expected purpose and goals was '%s' and actual was '%s'", expectedPurposeandGoals,
						actualPurposeAndGoals));
		softAssert.assertEquals(actualCreatorMail, expectedCreatorMail, String
				.format("Expected Creator mail was '%s' and actual was '%s'", actualCreatorMail, expectedCreatorMail));
		
		String expectedTravelReasonId = String.valueOf(travelReason.get("id"));
		String actualTravelReasonId = String.valueOf(restUtils.getValueFromResponse(response, "content.travelReason.id"));
		softAssert.assertEquals(actualTravelReasonId, expectedTravelReasonId, String
				.format("Expected Travel Reason Id was '%s' and actual was '%s'", expectedTravelReasonId, actualTravelReasonId));
		String expectedTravelReasonCode = (String) travelReason.get("code");
		String actualTravelReasonCode = (String)restUtils.getValueFromResponse(response, "content.travelReason.code");
		softAssert.assertEquals(actualTravelReasonCode, expectedTravelReasonCode, String
				.format("Expected Travel Reason Code was '%s' and actual was '%s'", expectedTravelReasonCode, actualTravelReasonCode));
		String expectedTravelReasonDescription = (String) travelReason.get("description");
		String actualTravelReasonDescription = (String)restUtils.getValueFromResponse(response, "content.travelReason.description");
		softAssert.assertEquals(actualTravelReasonDescription, expectedTravelReasonDescription, String
				.format("Expected Travel Reason Description was '%s' and actual was '%s'", expectedTravelReasonDescription, actualTravelReasonDescription));
		boolean expectedTravelReasonActiveStatus = (boolean) travelReason.get("active");
		boolean actualTravelReasonActiveStatus = (boolean)restUtils.getValueFromResponse(response, "content.travelReason.active");
		softAssert.assertEquals(actualTravelReasonActiveStatus, expectedTravelReasonActiveStatus, String
				.format("Expected Travel Reason Active Status was '%s' and actual was '%s'", expectedTravelReasonActiveStatus, actualTravelReasonActiveStatus));
		softAssert.assertAll();
		test.log(Status.PASS, "All data was fetched successfully.Get Trip details verification was successful.");

	}

	/**
	 * This test will validate the get details trip for negative scenarios
	 * 
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority=4,dataProvider = "getTripDetailsWithInvalidData", dataProviderClass = TripDataProvider.class)
	public void verifyGetTripDetailsWithInvalidData(Map<Object, Object> dtoMap) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String testScenario = dtoMap.get("dataScenario").toString();
		test.log(Status.INFO,
				String.format("Validating Get Trip details Negative Scenario by providing '%s'", testScenario));
		TripTestsHelper tripTestsHelper = new TripTestsHelper();

		RestUtils restUtils = new RestUtils();
		String tId=(String) dtoMap.get("tripId");

		Response response = tripTestsHelper.getTripDetails(tId);
		dtoMap.put("dataScenario",testScenario);
		if (testScenario.contains("Trip Id which does not exits")) {
			String actualStatus = restUtils.getValueFromResponse(response, "status").toString();
			String expectedStatus = "FAILURE";
			softAssert.assertEquals(actualStatus, expectedStatus,
					String.format("Expected status was '%s' , actual status was '%s' .Response - %s", expectedStatus,
							actualStatus, response.prettyPrint()));
			String responseMessage = restUtils.getValueFromResponse(response, "message").toString();
			String expectedMessage = dtoMap.get("expectedMessage").toString();
			softAssert.assertEquals(responseMessage, expectedMessage, String
					.format("Expected message was '%s' , actual status was '%s' .", expectedMessage, responseMessage));
		} else {
			int statusCode = response.getStatusCode();
			softAssert.assertEquals(statusCode, 404,
					String.format("Expected status code was - 404 and actual was %d. Response - %s", statusCode,
							response.prettyPrint()));
		}
		softAssert.assertAll();
		test.log(Status.PASS, "Get Trip Details Negative verification was successful.");

	}

	/**
	 * This Test will verify the Update Trip api by passing valid data.
	 * 
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority=5,dataProvider = "createTripWithValidData", dataProviderClass = TripDataProvider.class, dependsOnMethods = "verifyGetTripDetailsWithValidData")
	public void verifyUpdateTripWithValidData(Map<Object, Object> dtoMap) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		TripTestsHelper tripTestsHelper = new TripTestsHelper();
		String expectedTripName = tripName;
		String expectedPurposeandGoals = "Automation testing - update -  "
				+ Utilities.getCurrentDateAndTime("MMddyyhhmmss");
		purposeAndGoals = expectedPurposeandGoals;
		dtoMap.put("purposeAndGoals", expectedPurposeandGoals);
		RestUtils restUtils = new RestUtils();
		dtoMap.put("tripId", tripId);
		dtoMap.put("status", "DRAFT");
		dtoMap.put("tripName", tripName);
		travelReason =new TravelReasonTestsHelper().getTravelReason();
		dtoMap.put("travelReason", travelReason);	
		Response response = tripTestsHelper.updateTrip(dtoMap);
		validateResponseToContinueTest(response, 200, "Unable to update Trip.", true);
		String responseMessage = restUtils.getValueFromResponse(response, "message").toString();
		String actualTripName = restUtils.getValueFromResponse(response, "content.tripName").toString();
		String tripStatus = restUtils.getValueFromResponse(response, "content.status").toString();
		String expectedResponseMessage = "Execution Successful";
		String expectedTripStatus = "DRAFT";
		softAssert.assertEquals(responseMessage, expectedResponseMessage, String
				.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage, responseMessage));
		softAssert.assertEquals(actualTripName, expectedTripName,
				String.format("Expected trip name was '%s' and actual was '%s'", expectedTripName, actualTripName));
		softAssert.assertEquals(tripStatus, expectedTripStatus,
				String.format("Expected trip status was '%s' and actual was '%s'", tripStatus, expectedTripStatus));
		softAssert.assertTrue(String.valueOf(tripId) != null && (!String.valueOf(tripId).equals("")),
				"Trip Id was not present.");
		String purposeAndGoals = restUtils.getValueFromResponse(response, "content.purposeAndGoals").toString();
		softAssert.assertEquals(purposeAndGoals, expectedPurposeandGoals, String.format(
				"Expected purpose and goals was '%s' and actual was '%s'", purposeAndGoals, expectedPurposeandGoals));
		String expectedTravelReasonId = String.valueOf(travelReason.get("id"));
		String actualTravelReasonId = String.valueOf(restUtils.getValueFromResponse(response, "content.travelReason.id"));
		softAssert.assertEquals(actualTravelReasonId, expectedTravelReasonId, String
				.format("Expected Travel Reason Id was '%s' and actual was '%s'", expectedTravelReasonId, actualTravelReasonId));
		String expectedTravelReasonCode = (String) travelReason.get("code");
		String actualTravelReasonCode = (String)restUtils.getValueFromResponse(response, "content.travelReason.code");
		softAssert.assertEquals(actualTravelReasonCode, expectedTravelReasonCode, String
				.format("Expected Travel Reason Code was '%s' and actual was '%s'", expectedTravelReasonCode, actualTravelReasonCode));
		String expectedTravelReasonDescription = (String) travelReason.get("description");
		String actualTravelReasonDescription = (String)restUtils.getValueFromResponse(response, "content.travelReason.description");
		softAssert.assertEquals(actualTravelReasonDescription, expectedTravelReasonDescription, String
				.format("Expected Travel Reason Description was '%s' and actual was '%s'", expectedTravelReasonDescription, actualTravelReasonDescription));
		boolean expectedTravelReasonActiveStatus = (boolean) travelReason.get("active");
		boolean actualTravelReasonActiveStatus = (boolean)restUtils.getValueFromResponse(response, "content.travelReason.active");
		softAssert.assertEquals(actualTravelReasonActiveStatus, expectedTravelReasonActiveStatus, String
				.format("Expected Travel Reason Active Status was '%s' and actual was '%s'", expectedTravelReasonActiveStatus, actualTravelReasonActiveStatus));
		softAssert.assertAll();
		test.log(Status.PASS, "Purpose And Goal for the trip was updated successfully.");
		test.log(Status.PASS, "Update Trip verification was successful.");

	}
	/**
	 * Thei test will verify update trips for invalid data
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority=6,dataProvider = "updateTripWithInvalidData", dataProviderClass = TripDataProvider.class)
	public void verifyUpdateTripWithInvalidData(Map<Object, Object> dtoMap) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		TripTestsHelper tripTestsHelper = new TripTestsHelper();
		String testScenario = dtoMap.get("dataScenario").toString();
		test.log(Status.INFO,
				String.format("Validating Update Trip details Negative Scenario by providing '%s'", testScenario));
		dtoMap.remove("dataScenario");
		String expectedMessage = dtoMap.get("expectedMessage").toString();
		dtoMap.remove("expectedMessage");
		dtoMap = tripTestsHelper.constructDtoForUpdateTripInvalidScenarios(dtoMap, testScenario,tripName,tripId);

		

		RestUtils restUtils = new RestUtils();
		LinkedHashMap<String,Object> travelReasonData =new TravelReasonTestsHelper().getTravelReason();
		dtoMap.put("travelReason", travelReasonData);	
		Response response = tripTestsHelper.updateTrip(dtoMap);
		dtoMap.put("dataScenario",testScenario);
		int statusCode = response.getStatusCode();

		softAssert.assertEquals(statusCode, 200, String.format(
				"Expected status code was - 200 and actual was %d. Response - %s", statusCode, response.prettyPrint()));

		String actualStatus = restUtils.getValueFromResponse(response, "status").toString();
		String expectedStatus = "FAILURE";
		softAssert.assertEquals(actualStatus, expectedStatus,
				String.format("Expected status was '%s' , actual status was '%s' .Response - %s", expectedStatus,
						actualStatus, response.prettyPrint()));
		String responseMessage = restUtils.getValueFromResponse(response, "message").toString();
		
		softAssert.assertEquals(responseMessage, expectedMessage, String
				.format("Expected message was '%s' , actual message was '%s' .", expectedMessage, responseMessage));
		softAssert.assertAll();
		test.log(Status.PASS, "Update Trip Negative verification was successful.");
	}
	
	/**
	 * This test will validate the get details trip for positive scenarios 
	 * @throws Exception
	 */
	@Test(priority=7,dependsOnMethods = "verifyCreateTripWithValidData")
	public void verifyDeleteTripWithValidData() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		TripTestsHelper tripTestsHelper = new TripTestsHelper();

		RestUtils restUtils = new RestUtils();
		
		Response response = tripTestsHelper.deleteTrip(tripId);
		String actualStatus = restUtils.getValueFromResponse(response, "status").toString();
		String expectedStatus = "SUCCESS";
			softAssert.assertEquals(actualStatus, expectedStatus,
		String.format("Expected status was '%s' , actual status was '%s'", expectedStatus,
							actualStatus));
		String responseMessage = restUtils.getValueFromResponse(response, "message").toString();
		String expectedMessage = "Execution Successful";
			softAssert.assertEquals(responseMessage, expectedMessage, String
					.format("Expected message was '%s' , actual status was '%s' .", expectedMessage, responseMessage));
		softAssert.assertAll();
		test.log(Status.PASS, "Delete Trip positive verification was successful.");

	}
	/**
	 * This test will verify delete trip for already deleted trip  
	 * @throws Exception
	 */
	@Test(priority=8,dependsOnMethods = "verifyDeleteTripWithValidData")
	public void verifyDeleteTripAlreadyDeletedTrip() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		TripTestsHelper tripTestsHelper = new TripTestsHelper();

		RestUtils restUtils = new RestUtils();
		
		Response response = tripTestsHelper.deleteTrip(tripId);
		String actualStatus = restUtils.getValueFromResponse(response, "status").toString();
		String expectedStatus = "FAILURE";
			softAssert.assertEquals(actualStatus, expectedStatus,
		String.format("Expected status was '%s' , actual status was '%s'", expectedStatus,
							actualStatus));
		String responseMessage = restUtils.getValueFromResponse(response, "message").toString();
		String expectedMessage = "The trip doesn't exist in the database";
			softAssert.assertEquals(responseMessage, expectedMessage, String
					.format("Expected message was '%s' , actual status was '%s' .", expectedMessage, responseMessage));
		softAssert.assertAll();
		test.log(Status.PASS, "Delete Trip for already deleted trip verification was successful.");

	}
	/**
	 * This test will validate the get details trip for negative scenarios 
	 * @throws Exception
	 */
	@Test(priority=9,dataProvider = "deleteTripWithInvalidData",dataProviderClass = TripDataProvider.class)
	public void verifyDeleteTripWithInvalidData(Map<Object, Object> dtoMap) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		TripTestsHelper tripTestsHelper = new TripTestsHelper();

		RestUtils restUtils = new RestUtils();
		
		String testScenario = dtoMap.get("dataScenario").toString();
		test.log(Status.INFO,
				String.format("Validating Delete Trip details Negative Scenario by providing '%s'", testScenario));
		String tId = dtoMap.get("tripId").toString();
		Response response = tripTestsHelper.deleteTrip(tId);
		String actualStatus = restUtils.getValueFromResponse(response, "status").toString();
		String expectedStatus = dtoMap.get("expectedStatus").toString();
			softAssert.assertEquals(actualStatus, expectedStatus,
		String.format("Expected status was '%s' , actual status was '%s'", expectedStatus,
							actualStatus));
		String responseMessage = restUtils.getValueFromResponse(response, "message").toString();
		String expectedMessage = dtoMap.get("expectedMessage").toString();
			softAssert.assertEquals(responseMessage, expectedMessage, String
					.format("Expected message was '%s' , actual status was '%s' .", expectedMessage, responseMessage));
		softAssert.assertAll();
		test.log(Status.PASS, "Delete Trip negative verification was successful.");

	}

	

}
