package tests.testcases.submodules.ktn;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import dataproviders.submodules.ktn.KtnDataproviders;
import dataproviders.submodules.myTrips.features.TripDataProvider;
import io.restassured.response.Response;
import tests.testcases.TravelMobileBaseTest;
import tests.testhelpers.submodules.ktn.KtnTestHelper;
import tests.testhelpers.submodules.myTrips.features.PassengerTestsHelper;
import tests.testhelpers.submodules.myTrips.features.TravelReasonTestsHelper;
import tests.testhelpers.submodules.myTrips.features.TripStepperInfoTestsHelper;
import tests.testhelpers.submodules.myTrips.features.TripTestsHelper;
import utils.RestUtils;
import utils.Utilities;

public class KtnTests extends TravelMobileBaseTest {

	public static String ktn, ktnId, passengerId;
	public static String passengerEmail = "surajit.sarkar@globant.com";

	@BeforeTest(alwaysRun = true)
	public void beforeTest() {
		subModuleTest = moduleTest.createNode("KnownTravellerNumberTests");
	}

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("KnownTravellerNumberTests");
	}

	/**
	 * This test method will verify add KTN api with valid data
	 * 
	 * @throws Exception
	 */
	@Test(priority = 0)
	public void verifyAddKTNWithValidData() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		KtnTestHelper testHelper = new KtnTestHelper();
		RestUtils restUtils = new RestUtils();

		ktn = Utilities.generateRandomAlphaNumericString(10).toUpperCase();

		// Fetch the passenger info to verify in the response of add Passenger
		Response passengerInfoResponse = new PassengerTestsHelper().getUserInfo(passengerEmail);
		validateResponseToContinueTest(passengerInfoResponse, 200,
				"Unable to fetch passenger info for user with email id - " + passengerEmail, true);
		passengerId = String.valueOf(restUtils.getValueFromResponse(passengerInfoResponse, "content.passengerId"));

		testHelper.deleteKtnIfExists(passengerId);

		Response response = testHelper.addKTN(passengerId, ktn);

		validateResponseToContinueTest(response, 200, String.format("Unable to add KTN."), true);

		String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));

		ktnId = String.valueOf(restUtils.getValueFromResponse(response, "content.id"));

		softAssert.assertTrue(ktnId != null && ktnId != "", "KTN Id was null or empty");

		String actualKTN = ((String) restUtils.getValueFromResponse(response, "content.knownTravelerNumber")).toUpperCase();
		softAssert.assertEquals(actualKTN, ktn,
				String.format("Expected KTN was '%s' and actual was '%s'", ktn, actualKTN));

		String actualPassengerId = String.valueOf(restUtils.getValueFromResponse(response, "content.passengerId"));
		softAssert.assertEquals(actualPassengerId, passengerId,
				String.format("Expected PassengerId was '%s' and actual was '%s'", passengerId, actualPassengerId));

		softAssert.assertAll();

		test.log(Status.PASS, "Add KTN with valid data verification was successful.");

	}

	/**
	 * This test method will verify get KTN api with valid data
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1, dependsOnMethods = "verifyAddKTNWithValidData")
	public void verifyGetKTNWithValidData() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		KtnTestHelper testHelper = new KtnTestHelper();
		RestUtils restUtils = new RestUtils();

		Response response = testHelper.getKTN(passengerId);

		validateResponseToContinueTest(response, 200,
				String.format("Unable to get KTN details for the specified passenger Id."), true);

		String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));

		String actualKtnId = String.valueOf(restUtils.getValueFromResponse(response, "content.id"));

		softAssert.assertEquals(actualKtnId, ktnId,
				String.format("Expected KTN Id was '%s' and actual was '%s'", ktnId, actualKtnId));

		String actualKTN = ((String) restUtils.getValueFromResponse(response, "content.knownTravelerNumber")).toUpperCase();
		softAssert.assertEquals(actualKTN, ktn,
				String.format("Expected KTN was '%s' and actual was '%s'", ktn, actualKTN));

		String actualPassengerId = String.valueOf(restUtils.getValueFromResponse(response, "content.passengerId"));
		softAssert.assertEquals(actualPassengerId, passengerId,
				String.format("Expected PassengerId was '%s' and actual was '%s'", passengerId, actualPassengerId));

		softAssert.assertAll();

		test.log(Status.PASS, "Get KTN details with valid data verification was successful.");

	}
	/**
	 * This test method will verify get KTN from profile api with valid data
	 * 
	 * @throws Exception
	 */
	@Test(priority = 2, dependsOnMethods = "verifyAddKTNWithValidData",dataProvider = "createTripWithValidData", dataProviderClass = TripDataProvider.class)
	public void verifyGetKTNFromProfileWithValidData(Map<Object, Object> dtoMap) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		KtnTestHelper testHelper = new KtnTestHelper();
		
		RestUtils restUtils = new RestUtils();
		
		//Create a trip for the passenger assigned with KTN
		LinkedHashMap<String, Object> travelReason =new TravelReasonTestsHelper().getTravelReason();
		dtoMap.put("travelReason", travelReason);		
		
		Response tripResponse = new TripTestsHelper().createTrip(dtoMap);
		validateResponseToContinueTest(tripResponse, 200, "Unable to create Trip.", true);
		
		//Fetch the trip id
		String tripId = String.valueOf(restUtils.getValueFromResponse(tripResponse, "content.tripId"));
		
	
		Response response = testHelper.getKtnFromProfile(tripId);
		
		validateResponseToContinueTest(response, 200,
				String.format("Unable to get KTN details from profile for the specified trip Id - "+tripId), true);

		String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));

		String actualKTN = (String.valueOf(restUtils.getValueFromResponse(response, "content[0].knownTravelerNumber"))).toUpperCase();

		softAssert.assertEquals(actualKTN, ktn,
				String.format("Expected KTN was '%s' and actual was '%s'", ktn, actualKTN));

		softAssert.assertAll();

		test.log(Status.PASS, "Get KTN details from profile with valid data verification was successful.");

	}
	/**
	 * This test method will verify add KTN for passenger who already has a KTN
	 * 
	 * @throws Exception
	 */
	@Test(priority = 3,dependsOnMethods = "verifyAddKTNWithValidData")
	public void verifyAddKtnForUserWhoAlreayHasKtn() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		KtnTestHelper testHelper = new KtnTestHelper();
		RestUtils restUtils = new RestUtils();

		String tesKtn = Utilities.generateRandomAlphaNumericString(10).toUpperCase();

		// Fetch the passenger info to verify in the response of add Passenger
		Response passengerInfoResponse = new PassengerTestsHelper().getUserInfo(passengerEmail);
		validateResponseToContinueTest(passengerInfoResponse, 200,
				"Unable to fetch passenger info for user with email id - " + passengerEmail, true);
		passengerId = String.valueOf(restUtils.getValueFromResponse(passengerInfoResponse, "content.passengerId"));

		Response response = testHelper.addKTN(passengerId, tesKtn);

		validateResponseToContinueTest(response, 200, String.format("Unable to add KTN."), true);

		String expectedStatus = "FAILURE";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = "This passenger already has a known traveler number";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));

		softAssert.assertAll();

		test.log(Status.PASS, "Add KTN for User who already has KTN, verification was successful.");

	}
	/**
	 * This test method will verify add KTN for passenger using already existing KTN
	 * 
	 * @throws Exception
	 */
	@Test(priority = 4,dependsOnMethods = "verifyAddKTNWithValidData")
	public void verifyAddKtnUsingAlreadyExistingKtn() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		KtnTestHelper testHelper = new KtnTestHelper();
		RestUtils restUtils = new RestUtils();

		String tesPassengeremail = "jinit.bansod@globant.com";
		// Fetch the passenger info to verify in the response of add Passenger
		Response passengerInfoResponse = new PassengerTestsHelper().getUserInfo(tesPassengeremail);
		validateResponseToContinueTest(passengerInfoResponse, 200,
				"Unable to fetch passenger info for user with email id - " + tesPassengeremail, true);
		String testPassengerId = String.valueOf(restUtils.getValueFromResponse(passengerInfoResponse, "content.passengerId"));
		testHelper.deleteKtnIfExists(testPassengerId);
		Response response = testHelper.addKTN(testPassengerId, ktn);

		validateResponseToContinueTest(response, 200, "Unable to add KTN.", true);

		String expectedStatus = "FAILURE";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = "The known traveler number "+ktn+" already exists";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));

		softAssert.assertAll();

		test.log(Status.PASS, "Add KTN for User using existing KTN, verification was successful.");

	}

	/**
	 * This test method will verify edit KTN api with valid data
	 * 
	 * @throws Exception
	 */
	@Test(priority = 5, dependsOnMethods = "verifyAddKTNWithValidData")
	public void verifyEditKTNWithValidData() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		KtnTestHelper testHelper = new KtnTestHelper();
		RestUtils restUtils = new RestUtils();

		String newKtn = Utilities.generateRandomAlphaNumericString(10).toUpperCase();

		Response response = testHelper.editKTN(passengerId, newKtn);

		validateResponseToContinueTest(response, 200, String.format("Unable to edit KTN."), true);

		String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));

		ktnId = String.valueOf(restUtils.getValueFromResponse(response, "content.id"));

		ktn = newKtn;

		softAssert.assertTrue(ktnId != null && ktnId != "", "KTN Id was null or empty");

		String actualKTN = ((String) restUtils.getValueFromResponse(response, "content.knownTravelerNumber")).toUpperCase();
		softAssert.assertEquals(actualKTN, newKtn,
				String.format("Expected KTN was '%s' and actual was '%s'", newKtn, actualKTN));

		String actualPassengerId = String.valueOf(restUtils.getValueFromResponse(response, "content.passengerId"));
		softAssert.assertEquals(actualPassengerId, passengerId,
				String.format("Expected PassengerId was '%s' and actual was '%s'", passengerId, actualPassengerId));

		softAssert.assertAll();

		test.log(Status.PASS, "Edit KTN with valid data verification was successful.");

	}

	/**
	 * This test method will verify delete KTN api with valid data
	 * 
	 * @throws Exception
	 */
	@Test(priority = 6, dependsOnMethods = "verifyAddKTNWithValidData")
	public void verifyDeleteKTNWithValidData() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		KtnTestHelper testHelper = new KtnTestHelper();
		RestUtils restUtils = new RestUtils();

		Response response = testHelper.deleteKTN(passengerId, ktn);

		validateResponseToContinueTest(response, 200, String.format("Unable to delete KTN."), true);

		String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));

		softAssert.assertAll();

		test.log(Status.PASS, "Delete KTN with valid data verification was successful.");

	}
	/**
	 * This test method will verify add KTN api with invalid data
	 * 
	 * @throws Exception
	 */
	@Test(priority = 7,dataProvider = "addKtnForInvalidData",dataProviderClass = KtnDataproviders.class)
	public void verifyAddKTNWithInValidData(Map<Object, Object> dtoMap) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		KtnTestHelper testHelper = new KtnTestHelper();
		RestUtils restUtils = new RestUtils();
		String testScenario = (String) dtoMap.get("dataScenario");
		dtoMap = testHelper.constructKtnForAddKtnWithInvalidData(dtoMap);
		
		Response passengerInfoResponse = new PassengerTestsHelper().getUserInfo(passengerEmail);
		validateResponseToContinueTest(passengerInfoResponse, 200,
				"Unable to fetch passenger info for user with email id - " + passengerEmail, true);
		passengerId = String.valueOf(restUtils.getValueFromResponse(passengerInfoResponse, "content.passengerId"));

		testHelper.deleteKtnIfExists(passengerId);
		
		String testKtn = (String) dtoMap.get("ktn");
		Response response = testHelper.addKTN(passengerId, testKtn);

		if(testScenario.equals("Empty KTN"))
		{
			validateResponseToContinueTest(response, 405, String.format("Invalid status code"), true);
		}
		else {
		validateResponseToContinueTest(response, 200, String.format("Unable to add KTN."), true);

		String expectedStatus = (String) dtoMap.get("expectedStatus");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = (String) dtoMap.get("expectedMessage");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));
		}
		softAssert.assertAll();

		test.log(Status.PASS, "Add KTN with in valid data verification was successful.");

	}	
	/**
	 * This test method will verify delete KTN api with in in valid data
	 * 
	 * @throws Exception
	 */
	@Test(priority = 8)
	public void verifyDeleteKTNWithInValidData() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		KtnTestHelper testHelper = new KtnTestHelper();
		RestUtils restUtils = new RestUtils();

		String testKtn = Utilities.generateRandomAlphaNumericString(10).toUpperCase();
		
		Response passengerInfoResponse = new PassengerTestsHelper().getUserInfo(passengerEmail);
		validateResponseToContinueTest(passengerInfoResponse, 200,
				"Unable to fetch passenger info for user with email id - " + passengerEmail, true);
		passengerId = String.valueOf(restUtils.getValueFromResponse(passengerInfoResponse, "content.passengerId"));

		Response response = testHelper.deleteKTN(passengerId, testKtn);

		validateResponseToContinueTest(response, 200, String.format("Unable to delete KTN."), true);

		String expectedStatus = "FAILURE";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = String.format("Traveler number %s for passenger not found",testKtn);
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));

		softAssert.assertAll();

		test.log(Status.PASS, "Delete KTN with in valid data verification was successful.");

	}
	
	/**
	 * This test will test the positive scenario for associate ktn to a trip
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(priority = 9)
	public void associateKtnToTrip() throws Exception
	{
		SoftAssert softAssert = new SoftAssert();
		KtnTestHelper testHelper = new KtnTestHelper();
		RestUtils restUtils = new RestUtils();
		
		Map<Object, Object> createTripDto  = (Map<Object, Object>) TripDataProvider.createTripWithValidData()[0][0];

		
		//Create a trip to use it for the rest of the testing
		LinkedHashMap<String,Object> travelReason =new TravelReasonTestsHelper().getTravelReason();
		createTripDto.put("travelReason", travelReason);	
		Response createTripResponse = new TripTestsHelper().createTrip(createTripDto);
		validateResponseToContinueTest(createTripResponse, 200, "Unable to create Trip.", true);
		int tripId = Integer.parseInt(restUtils.getValueFromResponse(createTripResponse, "content.tripId").toString());
		String creatorEmailId = String.valueOf(restUtils.getValueFromResponse(createTripResponse, "content.creatorMail"));
		
		Response passengerInfoResponse = new PassengerTestsHelper().getUserInfo(creatorEmailId);
		validateResponseToContinueTest(passengerInfoResponse, 200,
				"Unable to fetch passenger info for user with email id - " + creatorEmailId, true);
		passengerId = String.valueOf(restUtils.getValueFromResponse(passengerInfoResponse, "content.passengerId"));

		testHelper.deleteKtnIfExists(passengerId);
		
		Response tripStepperInfoResponse = new TripStepperInfoTestsHelper().getTripStepperInfo(String.valueOf(tripId), "ALL");
		
		validateResponseToContinueTest(tripStepperInfoResponse, 200, String.format("Unable to get trip stepper info"), true);
		
		int tripPassengerId = Integer.parseInt(restUtils.getValueFromResponse(tripStepperInfoResponse, "content.passengerDTOs[0].tripPassengerId").toString());
		
		String expectedKtn = Utilities.generateRandomAlphaNumericString(8).toUpperCase();
		Response response =testHelper.associateKtnToTrip(tripId, tripPassengerId,expectedKtn);
		
		validateResponseToContinueTest(response, 200, String.format("Unable associate ktn to trip"), true);
		
		String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus, String
				.format("Expected status was '%s' and actual was '%s'", expectedStatus,restUtils.getValueFromResponse(response, "status")));
		
		String expectedMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedMessage, String
				.format("Expected message for passenger was '%s' and actual was '%s'", expectedMessage,restUtils.getValueFromResponse(response, "content[0].message")));
		
		String expectedPassengerMessage = "";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content[0].message"), expectedPassengerMessage, String
				.format("Expected message for passenger was '%s' and actual was '%s'", expectedPassengerMessage,restUtils.getValueFromResponse(response, "content[0].message")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content[0].knownTravelerNumber"), expectedKtn, String
				.format("Expected KTN passenger was '%s' and actual was '%s'", expectedKtn,restUtils.getValueFromResponse(response, "content[0].knownTravelerNumber")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content[0].tripPassengerId"), tripPassengerId, String
				.format("Expected trip passenger id was '%s' and actual was '%s'", tripPassengerId,restUtils.getValueFromResponse(response, "content[0].tripPassengerId")));
		
		softAssert.assertAll();

		test.log(Status.PASS, "Associate KTN to trip verification was successful.");
	}
	
	/**
	 * This test will test the positive scenario for associate ktn to a trip
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(priority = 10,dataProvider = "associateKtnToTripForInvalidData",dataProviderClass = KtnDataproviders.class)
	public void associateKtnToTripWithInvalidData(Map<Object, Object> dtoMap) throws Exception
	{
		SoftAssert softAssert = new SoftAssert();
		KtnTestHelper testHelper = new KtnTestHelper();
		RestUtils restUtils = new RestUtils();
		String testScenario = dtoMap.get("dataScenario").toString();
		String expectedMessage = dtoMap.get("expectedMessage").toString();
		String expectedStatus = dtoMap.get("expectedStatus").toString();
		test.log(Status.INFO,
				String.format("Validating associate Ktn to trip by providing '%s'", testScenario));
		
		Map<Object, Object> createTripDto  = (Map<Object, Object>) TripDataProvider.createTripWithValidData()[0][0];

		
		//Create a trip to use it for the rest of the testing
		LinkedHashMap<String,Object> travelReason =new TravelReasonTestsHelper().getTravelReason();
		createTripDto.put("travelReason", travelReason);	
		Response createTripResponse = new TripTestsHelper().createTrip(createTripDto);
		validateResponseToContinueTest(createTripResponse, 200, "Unable to create Trip.", true);
		int tripId = Integer.parseInt(restUtils.getValueFromResponse(createTripResponse, "content.tripId").toString());
		String creatorEmailId = String.valueOf(restUtils.getValueFromResponse(createTripResponse, "content.creatorMail"));
		
		Response passengerInfoResponse = new PassengerTestsHelper().getUserInfo(creatorEmailId);
		validateResponseToContinueTest(passengerInfoResponse, 200,
				"Unable to fetch passenger info for user with email id - " + creatorEmailId, true);
		passengerId = String.valueOf(restUtils.getValueFromResponse(passengerInfoResponse, "content.passengerId"));

		testHelper.deleteKtnIfExists(passengerId);
		
		Response tripStepperInfoResponse = new TripStepperInfoTestsHelper().getTripStepperInfo(String.valueOf(tripId), "ALL");
		
		validateResponseToContinueTest(tripStepperInfoResponse, 200, String.format("Unable to get trip stepper info"), true);
		
		int tripPassengerId = Integer.parseInt(restUtils.getValueFromResponse(tripStepperInfoResponse, "content.passengerDTOs[0].tripPassengerId").toString());
		
		String expectedKtn = String.valueOf(dtoMap.get("ktn"));
		if(testScenario.equals("Empty KTN"))
		{
			expectedKtn="";
		}
		if(testScenario.equals("Invalid Trip Id"))
		{
			tripId=Integer.parseInt(dtoMap.get("tripId").toString());
			expectedMessage = String.format("Trip %d does not exists.",tripId);
		}
		if(testScenario.equals("Invalid Trip Passenger Id"))
		{
			tripPassengerId=Integer.parseInt(dtoMap.get("tripPassengerId").toString());
			expectedMessage = String.format("Passenger with id  %d does not associated with trip %d.",tripPassengerId,tripId);
		}
		Response response =testHelper.associateKtnToTrip(tripId, tripPassengerId,expectedKtn);
		validateResponseToContinueTest(response, 200, String.format("Unable associate ktn to trip"), true);
		
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus, String
				.format("Expected status was '%s' and actual was '%s'", expectedStatus,restUtils.getValueFromResponse(response, "status")));
		
		if(!testScenario.equals("Invalid Trip Id") && !testScenario.equals("Invalid Trip Passenger Id")&& !testScenario.equals("Empty KTN"))
		{
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content[0].message"), expectedMessage, String
				.format("Expected message for passenger was '%s' and actual was '%s'", expectedMessage,restUtils.getValueFromResponse(response, "content[0].message")));
		}
		else {
			softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedMessage, String
					.format("Expected message was '%s' and actual was '%s'", expectedMessage,restUtils.getValueFromResponse(response, "message")));
		}
		softAssert.assertAll();

		test.log(Status.PASS, "Associate KTN to trip verification for Invalid data was successful.");
	}
	/**
	 * This test will test the positive scenario for get ktn associated to a trip
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(priority = 11)
	public void getKtnAssociatedToTrip() throws Exception
	{
		SoftAssert softAssert = new SoftAssert();
		KtnTestHelper testHelper = new KtnTestHelper();
		RestUtils restUtils = new RestUtils();
		
		Map<Object, Object> createTripDto  = (Map<Object, Object>) TripDataProvider.createTripWithValidData()[0][0];

		
		//Create a trip to use it for the rest of the testing
		LinkedHashMap<String,Object> travelReason =new TravelReasonTestsHelper().getTravelReason();
		createTripDto.put("travelReason", travelReason);	
		Response createTripResponse = new TripTestsHelper().createTrip(createTripDto);
		validateResponseToContinueTest(createTripResponse, 200, "Unable to create Trip.", true);
		int tripId = Integer.parseInt(restUtils.getValueFromResponse(createTripResponse, "content.tripId").toString());
		String creatorEmailId = String.valueOf(restUtils.getValueFromResponse(createTripResponse, "content.creatorMail"));
		
		Response passengerInfoResponse = new PassengerTestsHelper().getUserInfo(creatorEmailId);
		validateResponseToContinueTest(passengerInfoResponse, 200,
				"Unable to fetch passenger info for user with email id - " + creatorEmailId, true);
		passengerId = String.valueOf(restUtils.getValueFromResponse(passengerInfoResponse, "content.passengerId"));

		testHelper.deleteKtnIfExists(passengerId);
		
		Response tripStepperInfoResponse = new TripStepperInfoTestsHelper().getTripStepperInfo(String.valueOf(tripId), "ALL");
		
		validateResponseToContinueTest(tripStepperInfoResponse, 200, String.format("Unable to get trip stepper info"), true);
		
		int tripPassengerId = Integer.parseInt(restUtils.getValueFromResponse(tripStepperInfoResponse, "content.passengerDTOs[0].tripPassengerId").toString());
		
		String expectedKtn = Utilities.generateRandomAlphaNumericString(8).toUpperCase();
		Response associateKtnResponse =testHelper.associateKtnToTrip(tripId, tripPassengerId,expectedKtn);
		
		int expectedKtnId = Integer.parseInt(restUtils.getValueFromResponse(associateKtnResponse, "content[0].id").toString());
		
		validateResponseToContinueTest(associateKtnResponse, 200, String.format("Unable associate ktn to trip"), true);
		
		
		Response response = testHelper.getKtnAssociatedToTrip(tripId);
		
		String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus, String
				.format("Expected status was '%s' and actual was '%s'", expectedStatus,restUtils.getValueFromResponse(response, "status")));
		
		String expectedMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedMessage, String
				.format("Expected message for passenger was '%s' and actual was '%s'", expectedMessage,restUtils.getValueFromResponse(response, "message")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content[0].id"), expectedKtnId, String
				.format("Expected KTN id was '%s' and actual was '%s'", expectedKtnId,restUtils.getValueFromResponse(associateKtnResponse, "content[0].id")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content[0].knownTravelerNumber"), expectedKtn, String
				.format("Expected KTN was '%s' and actual was '%s'", expectedKtn,restUtils.getValueFromResponse(associateKtnResponse, "content[0].knownTravelerNumber")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(associateKtnResponse, "content[0].tripPassengerId"), tripPassengerId, String
				.format("Expected trip passenger id was '%s' and actual was '%s'", tripPassengerId,restUtils.getValueFromResponse(associateKtnResponse, "content[0].tripPassengerId")));
		
		softAssert.assertAll();

		test.log(Status.PASS, "Get KTN associated to trip with valid data verification was successful.");
	}
	/**
	 * This test will test the positive scenario for get ktn associated to a trip with invalid data
	 * @throws Exception
	 */
	@Test(priority = 12)
	public void getKtnAssociatedToTripWithInvalidData() throws Exception
	{
		SoftAssert softAssert = new SoftAssert();
		KtnTestHelper testHelper = new KtnTestHelper();
		RestUtils restUtils = new RestUtils();
		
		int tripId = 999999;
		Response response = testHelper.getKtnAssociatedToTrip(tripId);
		
		String expectedStatus = "FAILURE";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus, String
				.format("Expected status was '%s' and actual was '%s'", expectedStatus,restUtils.getValueFromResponse(response, "status")));
		
		String expectedMessage = String.format("Trip %d does not exists.", tripId);
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedMessage, String
				.format("Expected message for passenger was '%s' and actual was '%s'", expectedMessage,restUtils.getValueFromResponse(response, "message")));
		
		softAssert.assertAll();

		test.log(Status.PASS, "Get KTN associated to trip with invalid data verification was successful.");
	}
	
}
