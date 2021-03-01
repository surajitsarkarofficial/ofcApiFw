package tests.testcases.submodules.myTrips.features;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import dataproviders.submodules.myTrips.features.TripDataProvider;
import dataproviders.submodules.myTrips.features.WatchersDataProvider;
import io.restassured.response.Response;
import tests.testcases.submodules.myTrips.MyTripsBaseTest;
import tests.testhelpers.submodules.myTrips.features.PassengerTestsHelper;
import tests.testhelpers.submodules.myTrips.features.TravelReasonTestsHelper;
import tests.testhelpers.submodules.myTrips.features.TripTestsHelper;
import tests.testhelpers.submodules.myTrips.features.WatchersTestsHelper;
import utils.RestUtils;

public class WatchersTests extends MyTripsBaseTest{
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("Watchers Tests");
	}
	
	public String tripId,passengerEmail,passengerId,customerId,passenger2Email="mahesh.hatolkar@globant.com";
	
	/**
	 * This Test will Verify add watcher with valid data
	 * 
	 * @param dtoMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(priority=0,dataProvider = "createTripWithValidData", dataProviderClass = TripDataProvider.class)
	public void verifyAddWatcherToATripUsingValidData(Map<Object, Object> dtoMap) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		RestUtils restUtils = new RestUtils();
		passengerEmail = (String) dtoMap.get("creatorMail");
		
		// Fetch the passenger info to verify in the response of add Passenger
		Response userInfoResponse = new PassengerTestsHelper().getUserInfo(passengerEmail);
		validateResponseToContinueTest(userInfoResponse, 200,
						"Unable to fetch passenger info for user with email id - " + passengerEmail, true);
		passengerId = String.valueOf(restUtils.getValueFromResponse(userInfoResponse, "content.passengerId"));
		customerId = String.valueOf(restUtils.getValueFromResponse(userInfoResponse, "content.customerDTO.id"));
		String firstName = (String) restUtils.getValueFromResponse(userInfoResponse, "content.customerDTO.firstName");
		String lastName = (String) restUtils.getValueFromResponse(userInfoResponse, "content.customerDTO.lastName");
		//Create a trip for the passenger assigned with KTN
		LinkedHashMap<String, Object> travelReason =new TravelReasonTestsHelper().getTravelReason();
		dtoMap.put("travelReason", travelReason);		
		
		Response tripResponse = new TripTestsHelper().createTrip(dtoMap);
		validateResponseToContinueTest(tripResponse, 200, "Unable to create Trip.", true);
		
		//Fetch the trip id
		tripId = String.valueOf(restUtils.getValueFromResponse(tripResponse, "content.tripId"));
		
		
		//Add watcher to the trip
		LinkedList<String> watcherEmailIds= new LinkedList<>();
		watcherEmailIds.add(passengerEmail);
		watcherEmailIds.add(passenger2Email.trim());
		
		Response response = new WatchersTestsHelper().addWatcherToTrip(tripId, watcherEmailIds);
		validateResponseToContinueTest(response, 200,
				"Unable to add watcher to trip with id - " + tripId, true);	
		
		String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));
		
		List<LinkedHashMap<String, Object>> watcherDTOs =  (List<LinkedHashMap<String, Object>>) restUtils.getValueFromResponse(response, "content");
		softAssert.assertTrue(watcherDTOs.size()>0,"Watcher DTOs information was not displayed.");
		LinkedHashMap<String, Object> watcherDTO = watcherDTOs.get(0); 
		String actualCustomerid = String.valueOf(watcherDTO.get("customerId"));
		String actualWatcherEmail = watcherDTO.get("emailId").toString();
		String actualFirstName = 	watcherDTO.get("firstName").toString();	
		String actualLastName = 	watcherDTO.get("lastName").toString();	
		
		softAssert.assertEquals(actualCustomerid, customerId,
				String.format("Expected Customer id was %s and actual was %s.", customerId,actualCustomerid));
		
		softAssert.assertEquals(actualFirstName, firstName,
				String.format("Expected first name was %s and actual was %s.", firstName,actualFirstName));
		
		softAssert.assertEquals(actualLastName, lastName,
				String.format("Expected last name was %s and actual was %s.", lastName,actualLastName));
				
		softAssert.assertEquals(actualWatcherEmail, passengerEmail,
				String.format("Expected watcher email id was %s and actual was %s.", passengerEmail,actualWatcherEmail));
		
		
		softAssert.assertAll();
		test.log(Status.PASS, "Add watcher to trip with valid data verification was successful.");

	}
	
	/**
	 * This Test will Verify add watcher with in valid data
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority=1,dependsOnMethods = "verifyAddWatcherToATripUsingValidData",
			dataProvider = "addWatcherWithInvalidData", dataProviderClass = WatchersDataProvider.class)
	public void verifyAddWatcherToATripUsingInValidData(Map<Object, Object> dtoMap) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		RestUtils restUtils = new RestUtils();
		String testScenario = dtoMap.get("dataScenario").toString();
		test.log(Status.INFO,
				String.format("Validating Add Watchers Negative Scenario by providing '%s'", testScenario));
		//Add watcher to the trip
		LinkedList<String> watcherEmailIds= new LinkedList<>();
		watcherEmailIds.add(dtoMap.get("emailId").toString());
		String testTripId=tripId;
		if(testScenario.contains("Invalid trip id"))
		{
			testTripId = dtoMap.get("tripId").toString();
		}
		
		Response response = new WatchersTestsHelper().addWatcherToTrip(testTripId, watcherEmailIds);
		validateResponseToContinueTest(response, 200,
				"Unable to add watcher to trip with id - " + tripId, true);	
		
		String expectedStatus = "FAILURE";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedMessage = dtoMap.get("expectedMessage").toString();
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedMessage,
						restUtils.getValueFromResponse(response, "message")));
		
		softAssert.assertAll();
		test.log(Status.PASS, "Add watcher to trip with in valid data verification was successful.");

	}
	
	/**
	 * This Test will Verify remove watcher with valid data
	 * @throws Exception
	 */
	@Test(priority=2,dependsOnMethods = "verifyAddWatcherToATripUsingValidData")
	public void verifyRemoveWatcherUsingValidData() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		RestUtils restUtils = new RestUtils();
		
		
		//Remove watcher from trip		
		Response response = new WatchersTestsHelper().removeWatcherFromTrip(tripId,passenger2Email.trim());
		validateResponseToContinueTest(response, 200,
				"Unable to remove watcher to trip with id - " + tripId, true);	
		
		String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));
		
		softAssert.assertAll();
		test.log(Status.PASS, "Remove watcher with valid data verification was successful.");

	}
	
	/**
	 * This Test will Verify remove watcher with in valid data
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority=3,dependsOnMethods = "verifyRemoveWatcherUsingValidData",
			dataProvider = "removeWatcherWithInvalidData", dataProviderClass = WatchersDataProvider.class)
	public void verifyRemoveWatcherUsingInValidData(Map<Object, Object> dtoMap) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		RestUtils restUtils = new RestUtils();
		String testScenario = dtoMap.get("dataScenario").toString();
		test.log(Status.INFO,
				String.format("Validating Remove Watchers Negative Scenario by providing '%s'", testScenario));
		
		String testTripId=tripId;
		if(testScenario.contains("Invalid trip id"))
		{
			testTripId = dtoMap.get("tripId").toString();
		}
		
		Response response = new WatchersTestsHelper().removeWatcherFromTrip(testTripId, passenger2Email);
		validateResponseToContinueTest(response, 200,
				"Unable to remove watcher to trip with id - " + tripId, true);	
		
		String expectedStatus = "FAILURE";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedMessage = dtoMap.get("expectedMessage").toString();
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedMessage,
						restUtils.getValueFromResponse(response, "message")));
		
		softAssert.assertAll();
		test.log(Status.PASS, "Remove watcher to trip with in valid data verification was successful.");

	}


}
