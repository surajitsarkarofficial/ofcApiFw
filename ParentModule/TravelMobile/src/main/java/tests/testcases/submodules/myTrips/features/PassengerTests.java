package tests.testcases.submodules.myTrips.features;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import database.submodules.myTrips.features.PassengerDBHelper;
import dataproviders.submodules.myTrips.features.PassengerDataProviders;
import dataproviders.submodules.myTrips.features.TripDataProvider;
import io.restassured.response.Response;
import tests.testcases.submodules.myTrips.MyTripsBaseTest;
import tests.testhelpers.submodules.myTrips.features.PassengerTestsHelper;
import tests.testhelpers.submodules.myTrips.features.TravelReasonTestsHelper;
import tests.testhelpers.submodules.myTrips.features.TripStepperInfoTestsHelper;
import tests.testhelpers.submodules.myTrips.features.TripTestsHelper;
import utils.RestUtils;

/**
 * This class contains Test cases for Passenger Api's
 * @author surajit.sarkar
 *
 */
public class PassengerTests extends MyTripsBaseTest {

	private static String tripId, tripPassengerId,passengerId,passengerEmailId,creatorEmailId;
	private static LinkedHashMap<String,Object> travelReason;
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("PassengerTests");
	}
	static PassengerDBHelper dbHelper;
	/**
	 * This Test will add passenger with valid data to a trip and verify the response
	 * 
	 * @param dtoMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(priority = 0,dataProvider = "addPassengerWithValidData", dataProviderClass = PassengerDataProviders.class)
	public void verifyAddPassengerValidData(String pEmailId) throws Exception {
		passengerEmailId=pEmailId;
		SoftAssert softAssert = new SoftAssert();
		Map<Object, Object> createTripDto  = (Map<Object, Object>) TripDataProvider.createTripWithValidData()[0][0];

		//Connection to db
		dbHelper = new PassengerDBHelper();

		RestUtils restUtils = new RestUtils();

		//Create a trip to use it for the rest of the testing
		travelReason =new TravelReasonTestsHelper().getTravelReason();
		createTripDto.put("travelReason", travelReason);	
		Response createTripResponse = new TripTestsHelper().createTrip(createTripDto);
		validateResponseToContinueTest(createTripResponse, 200, "Unable to create Trip.", true);
		tripId = String.valueOf(restUtils.getValueFromResponse(createTripResponse, "content.tripId"));
		creatorEmailId = String.valueOf(restUtils.getValueFromResponse(createTripResponse, "content.creatorMail"));
		//Fetch the passenger info to verify in the response of add Passenger 
		Response passengerInfoResponse = new PassengerTestsHelper().getUserInfo(pEmailId);
		validateResponseToContinueTest(passengerInfoResponse, 200, 
				"Unable to fetch passenger info for user with email id - "+pEmailId, true);
		List<String> passengerList = new LinkedList<String>();
		passengerList.add(pEmailId);

		//Hit the add passenger api
		Response response = new PassengerTestsHelper().addPassenger(tripId, passengerList);
		validateResponseToContinueTest(response, 200, "Unable to add passenger to trip id "+tripId, true);

		//verifications

		String expectedResponseMessage = "Execution Successful";

		tripPassengerId = String.valueOf(restUtils.getValueFromResponse(response, "content[1].tripPassengerId"));
		String actualPassengerEmail=(String) restUtils.getValueFromResponse(response, "content[1].passengerDTO.email");		

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage, String
				.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,restUtils.getValueFromResponse(response, "message")));
		softAssert.assertTrue(String.valueOf(tripPassengerId) != null && (!String.valueOf(tripPassengerId).equals("")),
				"Trip Passenger Id was not present.");
		softAssert.assertEquals(actualPassengerEmail, pEmailId, String
				.format("Expected email id was '%s' and actual was '%s'", pEmailId,actualPassengerEmail));

		String actualFirstName  =(String) restUtils.getValueFromResponse(response, "content[1].passengerDTO.fname");
		String expectedFirstName = (String) restUtils.getValueFromResponse(passengerInfoResponse, "content.customerDTO.firstName");
		softAssert.assertEquals(actualFirstName, expectedFirstName, String
				.format("Expected First name was '%s' and actual was '%s'", expectedFirstName,actualFirstName));

		String actualLastName=(String) restUtils.getValueFromResponse(response, "content[1].passengerDTO.lname");
		String expectedLastName = (String) restUtils.getValueFromResponse(passengerInfoResponse, "content.customerDTO.lastName");
		softAssert.assertEquals(actualLastName, expectedLastName, String
				.format("Expected Last name was '%s' and actual was '%s'", expectedLastName,actualLastName));

		passengerId=String.valueOf(restUtils.getValueFromResponse(response, "content[1].passengerDTO.passengerId"));
		String expectedPassengerId = String.valueOf(restUtils.getValueFromResponse(passengerInfoResponse, "content.passengerId"));
		softAssert.assertEquals(passengerId, expectedPassengerId, String
				.format("Expected Passenger ID was '%s' and actual was '%s'", expectedPassengerId,passengerId));

		String actualType = (String) restUtils.getValueFromResponse(response, "content[1].passengerDTO.type");
		String expectedType="PASSENGER";
		softAssert.assertEquals(actualType, expectedType, String
				.format("Expected Type was '%s' and actual was '%s'", expectedType,actualType));		

		String actualPassengerStatus = (String) restUtils.getValueFromResponse(response, "content[1].passengerDTO.status");
		String expectedPassengerStatus="ACTIVE";
		softAssert.assertEquals(actualPassengerStatus, expectedPassengerStatus, String
				.format("Expected Passenger Status was '%s' and actual was '%s'", expectedPassengerStatus,actualPassengerStatus));

		String actualSociety = (String) restUtils.getValueFromResponse(response, "content[1].passengerDTO.society");
		String expectedSociety = (String) dbHelper.getCustomerInfo(pEmailId,"society");
		softAssert.assertEquals(actualSociety, expectedSociety, String
				.format("Expected Passenger's Society was '%s' and actual was '%s'", expectedSociety,actualSociety));

		String actualSocietyName= (String) restUtils.getValueFromResponse(response, "content[1].passengerDTO.societyName");
		String expectedSocietyName = (String) dbHelper.getCustomerInfo(pEmailId,"society_name");
		softAssert.assertEquals(actualSocietyName, expectedSocietyName, String
				.format("Expected Passenger's Society Name was '%s' and actual was '%s'", expectedSocietyName,actualSocietyName));

		String actualTripId  =String.valueOf(restUtils.getValueFromResponse(response, "content[1].tripId"));
		softAssert.assertEquals(actualTripId, tripId, String
				.format("Expected Trip Id was '%s' and actual was '%s'", tripId,actualTripId));

		softAssert.assertAll();
		test.log(Status.PASS, String.format("Add Passenger verification was successful. Added Passenger with trip passenger id %s to the trip with id- %s",tripPassengerId,tripId));

	}
	/**
	 * This test will verify add Passenger negative scenarios
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority = 1,dataProvider = "addPassengerWithInvalidData", dataProviderClass = PassengerDataProviders.class,dependsOnMethods = "verifyAddPassengerValidData")
	public void verifyAddPassengerWithInvalidData(Map<Object, Object> dtoMap) throws Exception {
		SoftAssert softAssert = new SoftAssert();

		String testScenario = (String) dtoMap.get("dataScenario");
		test.log(Status.INFO,
				String.format("Validating Get Passenger Info for '%s'", testScenario));
		String expectedEmailId = (String) dtoMap.get("passengerMailId");
		if(expectedEmailId==null)
		{
			expectedEmailId="";
		}
		RestUtils restUtils = new RestUtils();
		String tripIdForTest = tripId;
		if(testScenario.equals("Invalid Trip Id"))
		{
			tripIdForTest=(String) dtoMap.get("tripId");
		}
		List<String> passengerList = new LinkedList<String>();
		passengerList.add(expectedEmailId);
		//Hit the add passenger api
		Response response = new PassengerTestsHelper().addPassenger(tripIdForTest, passengerList);
		int statusCode = response.getStatusCode();

		Assert.assertEquals(statusCode, 200, String.format(
				"Expected status code was - 200 and actual was %d. Response - %s", statusCode, response.prettyPrint()));

		String actualStatus = restUtils.getValueFromResponse(response, "status").toString();
		String expectedStatus = "FAILURE";
		softAssert.assertEquals(actualStatus, expectedStatus,
				String.format("Expected status was '%s' , actual status was '%s' .Response - %s", expectedStatus,
						actualStatus, response.prettyPrint()));
		String responseMessage = restUtils.getValueFromResponse(response, "message").toString();
		String expectedMessage = dtoMap.get("expectedMessage").toString();

		softAssert.assertTrue(responseMessage.contains(expectedMessage), String
				.format("Expected message was '%s' was not present in the actual message '%s' .", expectedMessage, responseMessage));
		softAssert.assertAll();
		test.log(Status.PASS, "Add Passenger Negative verification was successful.");
	}
	/**
	 * This test will verify the Get passenger Info api
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority = 2,dependsOnMethods = "verifyAddPassengerValidData",dataProvider = "getPassengerInfoForValidData", dataProviderClass = PassengerDataProviders.class)
	public void verifyGetPassengerInfoValidData(Map<Object, Object> dtoMap) throws Exception
	{
		SoftAssert softAssert = new SoftAssert();
		RestUtils restUtils = new RestUtils();

		String testScenario = dtoMap.get("dataScenario").toString();
		test.log(Status.INFO,
				String.format("Validating Get Passenger Info for '%s'", testScenario));
		String expectedEmailId = (String) dtoMap.get("passengerMailId");

		Response response = new PassengerTestsHelper().getPassengerInfo(tripId, expectedEmailId);
		validateResponseToContinueTest(response, 200,
				String.format("Unable to fetch passenger info with email id '%s' for trip id '%s'.",expectedEmailId,tripId), true);

		String expectedResponseMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage, String
				.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,restUtils.getValueFromResponse(response, "message")));

		String actualPassengerEmail=(String) restUtils.getValueFromResponse(response, "content.email");
		softAssert.assertEquals(actualPassengerEmail, expectedEmailId, String
				.format("Expected email id was '%s' and actual was '%s'", expectedEmailId,actualPassengerEmail));

		boolean actualIsPartOfTrip = (boolean) restUtils.getValueFromResponse(response, "content.isPartOfTrip");
		softAssert.assertTrue(actualIsPartOfTrip, String
				.format("Passenger with email id '%s' is not a part of trip id '%s'", expectedEmailId,tripId));

		softAssert.assertAll();
		test.log(Status.PASS, String.format("Get Passenger Info verification was successful."));		
	}
	/**
	 * This test will verify the Get passenger Info api for negative  scenarios
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority = 3,dependsOnMethods = "verifyAddPassengerValidData",dataProvider = "getPassengerInfoForInvalidData", dataProviderClass = PassengerDataProviders.class)
	public void verifyGetPassengerInfoForInvalidData(Map<Object, Object> dtoMap) throws Exception
	{
		SoftAssert softAssert = new SoftAssert();
		RestUtils restUtils = new RestUtils();

		String testScenario = (String) dtoMap.get("dataScenario");
		test.log(Status.INFO,
				String.format("Validating Get Passenger Info for '%s'", testScenario));

		String expectedEmailId = (String) dtoMap.get("passengerMailId");
		String tripIdForTest= tripId;

		if(testScenario.equals("Non Existing Trip Id"))
		{
			tripIdForTest=(String) dtoMap.get("tripId");
		}

		Response response = new PassengerTestsHelper().getPassengerInfo(tripIdForTest, expectedEmailId);
		int statusCode = response.getStatusCode();

		softAssert.assertEquals(statusCode, 200, String.format(
				"Expected status code was - 200 and actual was %d. Response - %s", statusCode, response.prettyPrint()));

		String actualStatus = restUtils.getValueFromResponse(response, "status").toString();
		String expectedStatus = "FAILURE";
		softAssert.assertEquals(actualStatus, expectedStatus,
				String.format("Expected status was '%s' , actual status was '%s' .Response - %s", expectedStatus,
						actualStatus, response.prettyPrint()));

		String responseMessage = restUtils.getValueFromResponse(response, "message").toString();
		String expectedMessage = dtoMap.get("expectedMessage").toString();

		softAssert.assertTrue(responseMessage.contains(expectedMessage), String
				.format("Expected message was '%s' was not present in the actual message '%s' .", expectedMessage, responseMessage));
		softAssert.assertAll();
		test.log(Status.PASS, "Get Passenger Info Negative verification was successful.");	
	}

	/**
	 * This Test will verify the delete passenger api
	 * @throws Exception
	 */
	@Test(priority = 4,dependsOnMethods = "verifyAddPassengerValidData")
	public void verifyDeletePassengerFromTrip() throws Exception
	{
		SoftAssert softAssert = new SoftAssert();
		RestUtils restUtils = new RestUtils();
		Response response = new PassengerTestsHelper().deletePassenger(tripPassengerId);
		validateResponseToContinueTest(response, 200,
				String.format("Unable to delete passenger with Trip Passenger Id '%s'.",tripPassengerId), true);

		String expectedResponseMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage, String
				.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,restUtils.getValueFromResponse(response, "message")));

		String expectedContent = "Deleted successfully";
		String actualContent = (String) restUtils.getValueFromResponse(response, "content");
		softAssert.assertEquals(actualContent, expectedContent, String
				.format("Expected Content was '%s' and actual was '%s'.", expectedContent,actualContent));

		response = new PassengerTestsHelper().getPassengerInfo(tripId, passengerEmailId);
		validateResponseToContinueTest(response, 200,
				String.format("Unable to fetch passenger info with email id '%s' for trip id '%s'.",passengerEmailId,tripId), true);

		boolean actualIsPartOfTrip = (boolean) restUtils.getValueFromResponse(response, "content.isPartOfTrip");
		softAssert.assertFalse(actualIsPartOfTrip, String
				.format("Passenger with trip passenger id '%s' was not delted successfully. Passenger with email id '%s' is still a part of trip id '%s'", tripPassengerId, passengerEmailId,tripId));

		softAssert.assertAll();
		test.log(Status.PASS, String.format("Delete Passenger verification was successful."));	
	}
	/**
	 * This Test will verify the delete creator as passenger from the trip
	 * @throws Exception
	 */
	@Test(priority = 5)
	public void verifyDeleteTripCreatorFromTripPassenger() throws Exception
	{
		SoftAssert softAssert = new SoftAssert();
		@SuppressWarnings("unchecked")
		Map<Object, Object> createTripDto  = (Map<Object, Object>) TripDataProvider.createTripWithValidData()[0][0];

		RestUtils restUtils = new RestUtils();

		//Create a trip to use it for the rest of the testing
		travelReason =new TravelReasonTestsHelper().getTravelReason();
		createTripDto.put("travelReason", travelReason);	
		Response createTripResponse = new TripTestsHelper().createTrip(createTripDto);
		validateResponseToContinueTest(createTripResponse, 200, "Unable to create Trip.", true);
		tripId = String.valueOf(restUtils.getValueFromResponse(createTripResponse, "content.tripId"));
		creatorEmailId = String.valueOf(restUtils.getValueFromResponse(createTripResponse, "content.creatorMail"));

		Response userInfoResponse = new PassengerTestsHelper().getUserInfo(creatorEmailId);
		validateResponseToContinueTest(userInfoResponse, 200, 
				"Unable to fetch user info for user with email id - "+creatorEmailId, true);

		
		Response tripStepperInfoResponse = new TripStepperInfoTestsHelper().getTripStepperInfo(tripId, "PASSENGERS");
		String tripPassengerIdOfCreator = 
				String.valueOf(restUtils.getValueFromResponse(tripStepperInfoResponse, "content.passengerDTOs[0].tripPassengerId"));

		Response response = new PassengerTestsHelper().deletePassenger(tripPassengerIdOfCreator);
		validateResponseToContinueTest(response, 200,
				String.format("Unable to delete passenger with Trip Passenger Id '%s'.",tripPassengerIdOfCreator), true);

		String expectedResponseMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage, String
				.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,restUtils.getValueFromResponse(response, "message")));

		String expectedContent = "Deleted successfully";
		String actualContent = (String) restUtils.getValueFromResponse(response, "content");
		softAssert.assertEquals(actualContent, expectedContent, String
				.format("Expected Content was '%s' and actual was '%s'.", expectedContent,actualContent));

		response = new PassengerTestsHelper().getPassengerInfo(tripId, creatorEmailId);
		validateResponseToContinueTest(response, 200,
				String.format("Unable to fetch passenger info with email id '%s' for trip id '%s'.",creatorEmailId,tripId), true);

		boolean actualIsPartOfTrip = (boolean) restUtils.getValueFromResponse(response, "content.isPartOfTrip");
		softAssert.assertFalse(actualIsPartOfTrip, String
				.format("Passenger with trip passenger id '%s' was not delted successfully. Passenger with email id '%s' is still a part of trip id '%s'", tripPassengerIdOfCreator, creatorEmailId,tripId));

		softAssert.assertAll();
		test.log(Status.PASS, String.format("Trip Creator was succesfully deleted as passenger form the trip."));
	}
	/**
	 * This test will verify delete passenger for negative scenarios
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority = 6,dependsOnMethods = {"verifyAddPassengerValidData","verifyDeletePassengerFromTrip"},
			dataProvider = "deletePassengerWithInvalidData", dataProviderClass = PassengerDataProviders.class)
	public void verifyDeletePassengerForInvalidData(Map<Object, Object> dtoMap) throws Exception
	{
		SoftAssert softAssert = new SoftAssert();
		RestUtils restUtils = new RestUtils();

		String testScenario = dtoMap.get("dataScenario").toString();
		test.log(Status.INFO,
				String.format("Validating Delete Passenger From Trip for '%s'", testScenario));
		String tripPassengerIdForTest;
		if(testScenario.contains("Already deleted Trip Passenger Id"))
		{
			tripPassengerIdForTest=tripPassengerId;
		}else {
			tripPassengerIdForTest= (String) dtoMap.get("tripPassengerId");
		}

		Response response = new PassengerTestsHelper().deletePassenger(tripPassengerIdForTest);

		int expectedStatusCode = Integer.parseInt(dtoMap.get("statusCode").toString());
		int actualStatusCode = response.getStatusCode();

		softAssert.assertEquals(actualStatusCode, expectedStatusCode, String.format(
				"Expected status code was - %d and actual was %d. Response - %s", expectedStatusCode,actualStatusCode, response.prettyPrint()));

		if(!(testScenario.contains("Empty Trip Passenger Id") || testScenario.contains("Invalid Trip Passenger Id")))
		{
			String actualStatus = restUtils.getValueFromResponse(response, "status").toString();
			String expectedStatus = "FAILURE";
			softAssert.assertEquals(actualStatus, expectedStatus,
					String.format("Expected status was '%s' , actual status was '%s' .Response - %s", expectedStatus,
							actualStatus, response.prettyPrint()));

			String responseMessage = restUtils.getValueFromResponse(response, "message").toString();
			String expectedMessage = dtoMap.get("expectedMessage").toString();

			softAssert.assertTrue(responseMessage.contains(expectedMessage), String
					.format("Expected message was '%s' was not present in the actual message '%s' .", expectedMessage, responseMessage));

		}

		softAssert.assertAll();

		test.log(Status.PASS, String.format("Delete Passenger Negative verification was successful."));	
	}
	/**
	 * This test will verify adding deleted passenger back to the trip
	 * @throws Exception
	 */
	@Test(priority = 7,dependsOnMethods = "verifyDeleteTripCreatorFromTripPassenger")
	public void verifyAddDeletedPassengerBackToTrip() throws Exception {

		SoftAssert softAssert = new SoftAssert();

		RestUtils restUtils = new RestUtils();

		//Check if passenger is not a part of the Trip
		Response response = new PassengerTestsHelper().getPassengerInfo(tripId, creatorEmailId);
		validateResponseToContinueTest(response, 200,
				String.format("Unable to fetch passenger info with email id '%s' for trip id '%s'.",creatorEmailId,tripId), true);
		boolean actualIsPartOfTrip = (boolean) restUtils.getValueFromResponse(response, "content.isPartOfTrip");
		softAssert.assertFalse(actualIsPartOfTrip, String
				.format("Passenger with email id '%s' is already a part of trip id '%s'", creatorEmailId,tripId));	
		test.log(Status.INFO, String.format("Passenger is not a part of the trip."));
		List<String> passengerList = new LinkedList<String>();
		passengerList.add(creatorEmailId);

		//Hit the add passenger api
		response = new PassengerTestsHelper().addPassenger(tripId, passengerList);
		validateResponseToContinueTest(response, 200, 
				String.format("Unable to add passenger '%s' back to trip id '%s'",creatorEmailId,tripId), true);
		String expectedResponseMessage = "Execution Successful";

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage, String
				.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,restUtils.getValueFromResponse(response, "message")));
		test.log(Status.INFO, String.format("Add Passenger api call was successful."));

		//Verify if passenger is added back.
		response = new PassengerTestsHelper().getPassengerInfo(tripId, creatorEmailId);
		validateResponseToContinueTest(response, 200,
				String.format("Unable to fetch passenger info with email id '%s' for trip id '%s'.",creatorEmailId,tripId), true);

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage, String
				.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,restUtils.getValueFromResponse(response, "message")));

		String actualPassengerEmail=(String) restUtils.getValueFromResponse(response, "content.email");
		softAssert.assertEquals(actualPassengerEmail, creatorEmailId, String
				.format("Expected email id was '%s' and actual was '%s'", creatorEmailId,actualPassengerEmail));

		actualIsPartOfTrip = (boolean) restUtils.getValueFromResponse(response, "content.isPartOfTrip");
		softAssert.assertTrue(actualIsPartOfTrip, String
				.format("Passenger with email id '%s' is not a part of trip id '%s'", creatorEmailId,tripId));
		softAssert.assertAll();
		test.log(Status.PASS, String.format("Deleted Passenger is Successfully Added back to the trip with id- %s",tripId));

	}
	/**
	 * This test will add multiple passenger to a trip
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(priority = 8)
	public void verifyAddMultiplePassengerWithValidData() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		Map<Object, Object> createTripDto  = (Map<Object, Object>) TripDataProvider.createTripWithValidData()[0][0];

		RestUtils restUtils = new RestUtils();

		//Create a trip to use it for the rest of the testing
		travelReason =new TravelReasonTestsHelper().getTravelReason();
		createTripDto.put("travelReason", travelReason);	
		Response createTripResponse = new TripTestsHelper().createTrip(createTripDto);
		validateResponseToContinueTest(createTripResponse, 200, "Unable to create Trip.", true);
		//String testTripId = String.valueOf(restUtils.getValueFromResponse(createTripResponse, "content.tripId"));
		tripId = String.valueOf(restUtils.getValueFromResponse(createTripResponse, "content.tripId"));

		List<String> passengerList = new LinkedList<String>();
		passengerList.add("jinit.bansod@globant.com");
		passengerList.add("pravin.kumbhar@globant.com");
		passengerList.add("rashmi.parshetty@globant.com");


		//Hit the add passenger api
		Response response = new PassengerTestsHelper().addPassenger(tripId, passengerList);
		validateResponseToContinueTest(response, 200, "Unable to add passenger to trip id "+tripId, true);

		//verifications

		String expectedResponseMessage = "Execution Successful";

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage, String
				.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,restUtils.getValueFromResponse(response, "message")));
		List<Object> passengeDTOs = (List<Object>) restUtils.getValueFromResponse(response, "content[*].passengerDTO");
		int actaulPassengersCount = passengeDTOs.size();
		int expectedPassengersCount = passengerList.size()+1;//added 1 because creator of the trip is also added as passenger
		softAssert.assertEquals(actaulPassengersCount, expectedPassengersCount,
				String.format("Expected Passenger count for the trip is %s was %d and actual was %d", tripId,expectedPassengersCount,actaulPassengersCount));

		softAssert.assertAll();
		test.log(Status.PASS, String.format("Add Passenger verification was successful. Added Passenger with trip passenger id %s to the trip with id- %s",tripPassengerId,tripId));

	}
	/**
	 * This test will replace a existing passenger from a trip
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(priority = 9)
	public void verifyReplacePassengerWithValidData() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		Map<Object, Object> createTripDto  = (Map<Object, Object>) TripDataProvider.createTripWithValidData()[0][0];

		RestUtils restUtils = new RestUtils();

		//Create a trip to use it for the rest of the testing
		travelReason =new TravelReasonTestsHelper().getTravelReason();
		createTripDto.put("travelReason", travelReason);	
		Response createTripResponse = new TripTestsHelper().createTrip(createTripDto);
		validateResponseToContinueTest(createTripResponse, 200, "Unable to create Trip.", true);
		tripId = String.valueOf(restUtils.getValueFromResponse(createTripResponse, "content.tripId"));
		List<String> passengerList = new LinkedList<String>();
		passengerList.add("jinit.bansod@globant.com");
		passengerList.add("pravin.kumbhar@globant.com");

		String emailOfNewPassenger = "rashmi.parshetty@globant.com";

		Response userInfoResponse = new PassengerTestsHelper().getUserInfo(emailOfNewPassenger);
		String expectedPassengerId = String.valueOf(restUtils.getValueFromResponse(userInfoResponse, "content.passengerId"));

		//Hit the add passenger api
		Response addPassengerresponse = new PassengerTestsHelper().addPassenger(tripId, passengerList);
		validateResponseToContinueTest(addPassengerresponse, 200, "Unable to add passenger to trip id "+tripId, true);

		String tripPassengerIdToBeReplaced =String.valueOf(restUtils.getValueFromResponse(addPassengerresponse, "content[0].tripPassengerId"));
		String emailOfThePassengerIdToBeReplaced = (String)restUtils.getValueFromResponse(addPassengerresponse, "content[0].passengerDTO.email");

		//Replace Passenger
		Response response = new PassengerTestsHelper().replacePassenger(tripPassengerIdToBeReplaced, emailOfNewPassenger);
		validateResponseToContinueTest(response, 200, "Unable to replace passenger to passengerTrip id "+tripPassengerIdToBeReplaced, true);
		//verifications

		String expectedResponseMessage = "Execution Successful";

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage, String
				.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,restUtils.getValueFromResponse(response, "message")));
		HashMap<String, Object> passengeDTO = (HashMap<String, Object>) restUtils.getValueFromResponse(response, "content.passengerDTO");

		String actualEmail = (String)passengeDTO.get("email");
		String actualpassengerId = String.valueOf(passengeDTO.get("passengerId"));

		softAssert.assertEquals(actualpassengerId, expectedPassengerId,
				String.format("Expected Passenger Id was %s and actual was %s",expectedPassengerId,actualpassengerId));
		softAssert.assertEquals(actualEmail, emailOfNewPassenger,
				String.format("Expected email was %s and actual was %s",emailOfNewPassenger,actualEmail));

		softAssert.assertAll();
		test.log(Status.PASS, String.format("Replace Passenger verification was successful. Replaced Passenger for trip passenger id %s form email %s to %s",tripPassengerIdToBeReplaced,emailOfThePassengerIdToBeReplaced,actualEmail));

	}
	/**
	 * This test will verify the replace passenger trip api by replacing the creator of the trip
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(priority = 10)
	public void verifyReplacePassengerByReplacingCreatorOfTheTrip() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		Map<Object, Object> createTripDto  = (Map<Object, Object>) TripDataProvider.createTripWithValidData()[0][0];

		RestUtils restUtils = new RestUtils();

		//Create a trip to use it for the rest of the testing
		travelReason =new TravelReasonTestsHelper().getTravelReason();
		createTripDto.put("travelReason", travelReason);	
		Response createTripResponse = new TripTestsHelper().createTrip(createTripDto);
		validateResponseToContinueTest(createTripResponse, 200, "Unable to create Trip.", true);
		tripId = String.valueOf(restUtils.getValueFromResponse(createTripResponse, "content.tripId"));
		String emailOfNewPassenger = "mahesh.hatolkar@globant.com";

		Response userInfoResponse = new PassengerTestsHelper().getUserInfo(emailOfNewPassenger);
		String expectedPassengerId = String.valueOf(restUtils.getValueFromResponse(userInfoResponse, "content.passengerId"));

		Response tripStepperInfoResponse = new TripStepperInfoTestsHelper().getTripStepperInfo(tripId, "PASSENGERS");
		String tripPassengerIdOfCreator = 
				String.valueOf(restUtils.getValueFromResponse(tripStepperInfoResponse, "content.passengerDTOs[0].tripPassengerId"));

		//Replace Passenger
		Response response = new PassengerTestsHelper().replacePassenger(tripPassengerIdOfCreator, emailOfNewPassenger);
		validateResponseToContinueTest(response, 200, "Unable to replace creator of trip with passengerTrip id "+tripPassengerIdOfCreator, true);

		//verifications

		String expectedResponseMessage = "Execution Successful";

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage, String
				.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,restUtils.getValueFromResponse(response, "message")));
		HashMap<String, Object> passengeDTO = (HashMap<String, Object>) restUtils.getValueFromResponse(response, "content.passengerDTO");

		String actualEmail = (String)passengeDTO.get("email");
		String actualpassengerId = String.valueOf(passengeDTO.get("passengerId"));
		softAssert.assertEquals(actualpassengerId, expectedPassengerId,
				String.format("Expected Passenger Id was %s and actual was %s",expectedPassengerId,actualpassengerId));
		softAssert.assertEquals(actualEmail, emailOfNewPassenger,
				String.format("Expected email was %s and actual was %s",emailOfNewPassenger,actualEmail));

		softAssert.assertAll();
		test.log(Status.PASS,"Replace Creator of the trip verification was successful.");

	}
	/**
	 * This test will verify the replace passenger trip api with invalid data
	 * @param dtoMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(priority = 11,dataProvider = "getReplacePassengerInfoForInvalidData", dataProviderClass = PassengerDataProviders.class)
	public void verifyReplacePassengerWithInvalidData(Map<Object, Object> dtoMap) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String testScenario = (String) dtoMap.get("dataScenario");
		test.log(Status.INFO,
				String.format("Validating Replace Passenger Info for '%s'", testScenario));
		Map<Object, Object> createTripDto  = (Map<Object, Object>) TripDataProvider.createTripWithValidData()[0][0];

		RestUtils restUtils = new RestUtils();

		//Create a trip to use it for the rest of the testing
		travelReason =new TravelReasonTestsHelper().getTravelReason();
		createTripDto.put("travelReason", travelReason);	
		Response createTripResponse = new TripTestsHelper().createTrip(createTripDto);
		validateResponseToContinueTest(createTripResponse, 200, "Unable to create Trip.", true);
		tripId = String.valueOf(restUtils.getValueFromResponse(createTripResponse, "content.tripId"));
		List<String> passengerList = new LinkedList<String>();
		passengerList.add("jinit.bansod@globant.com");

		//Hit the add passenger api
		Response addPassengerresponse = new PassengerTestsHelper().addPassenger(tripId, passengerList);
		validateResponseToContinueTest(addPassengerresponse, 200, "Unable to add passenger to trip id "+tripId, true);
		String tripPassengerIdToBeReplaced;
		if(testScenario.equals("Invalid Trip Passenger Id"))
		{
			tripPassengerIdToBeReplaced="9999999";
		}
		else {
			tripPassengerIdToBeReplaced =String.valueOf(restUtils.getValueFromResponse(addPassengerresponse, "content[0].tripPassengerId"));
		}
		String emailOfNewPassenger = (String) dtoMap.get("passengerEmail");

		//Replace Passenger
		Response response = new PassengerTestsHelper().replacePassenger(tripPassengerIdToBeReplaced, emailOfNewPassenger);
		validateResponseToContinueTest(response, 200, "Unable to replace passenger to passengerTrip id "+tripPassengerIdToBeReplaced, true);

		//verifications

		String expectedResponseMessage = (String) dtoMap.get("expectedMessage");
		String expectedStatus = (String) dtoMap.get("status");

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus, String
				.format("Expected Status was '%s' and actual was '%s'", expectedStatus,restUtils.getValueFromResponse(response, "status")));
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage, String
				.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,restUtils.getValueFromResponse(response, "message")));

		softAssert.assertAll();
		test.log(Status.PASS, "Replace Passenger invalid data verification was successful.");

	}


}
