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
import io.restassured.response.Response;
import tests.testcases.submodules.myTrips.MyTripsBaseTest;
import tests.testhelpers.submodules.myTrips.features.PassengerTestsHelper;
import tests.testhelpers.submodules.myTrips.features.TravelReasonTestsHelper;
import tests.testhelpers.submodules.myTrips.features.TripStepperInfoTestsHelper;
import tests.testhelpers.submodules.myTrips.features.TripTestsHelper;
import tests.testhelpers.submodules.myTrips.features.WatchersTestsHelper;
import utils.RestUtils;

public class TripStepperInfoTests extends MyTripsBaseTest{
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("Trip Stepper Info Tests");
	}
	
	public String tripId,passengerEmail,passengerId,customerId;
	
	/**
	 * This Test will Verify Trip Stepper info for Step Parameter = ALL
	 * 
	 * @param dtoMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(priority=0,dataProvider = "createTripWithValidData", dataProviderClass = TripDataProvider.class)
	public void verifyTripStepperInfoWithStepParameterALL(Map<Object, Object> dtoMap) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		RestUtils restUtils = new RestUtils();
		TripStepperInfoTestsHelper helper = new TripStepperInfoTestsHelper();
		passengerEmail = (String) dtoMap.get("creatorMail");
		
		// Fetch the passenger info to verify in the response of add Passenger
		Response passengerInfoResponse = new PassengerTestsHelper().getUserInfo(passengerEmail);
		validateResponseToContinueTest(passengerInfoResponse, 200,
						"Unable to fetch passenger info for user with email id - " + passengerEmail, true);
		passengerId = String.valueOf(restUtils.getValueFromResponse(passengerInfoResponse, "content.passengerId"));
		customerId = String.valueOf(restUtils.getValueFromResponse(passengerInfoResponse, "content.customerDTO.id"));
		
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
		
		Response addWatcherResponse = new WatchersTestsHelper().addWatcherToTrip(tripId, watcherEmailIds);
		validateResponseToContinueTest(addWatcherResponse, 200,
				"Unable to add watcher to trip with id - " + tripId, true);		
		
		//Call the get trip stepper info api
		Response response = helper.getTripStepperInfo(tripId, "ALL");
		
		validateResponseToContinueTest(response, 200, String.format("Unable to get trip stepper info"), true);

		String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));
		
		//Verify passenger details
		List<LinkedHashMap<String, Object>> passengerDTOs =  (List<LinkedHashMap<String, Object>>) restUtils.getValueFromResponse(response, "content.passengerDTOs");
		softAssert.assertTrue(passengerDTOs.size()>0,"Passenger DTOs information was not displayed.");
		
		LinkedHashMap<String, Object> passengerDTO = passengerDTOs.get(0);
		String actualPassengerid = String.valueOf(passengerDTO.get("passengerId"));
		String actualPassengerEmail = passengerDTO.get("email").toString();
		
		softAssert.assertEquals(actualPassengerid, passengerId,
				String.format("Expected passenger id was {0} and actual was {1}.", passengerId,actualPassengerid));
		
		softAssert.assertEquals(actualPassengerEmail, passengerEmail,
				String.format("Expected passenger email id was {0} and actual was {1}.", passengerEmail,actualPassengerEmail));
		
		//Verify watchers details
		List<LinkedHashMap<String, Object>> watcherDTOs =  (List<LinkedHashMap<String, Object>>) restUtils.getValueFromResponse(response, "content.watcherDTOs");
		softAssert.assertTrue(watcherDTOs.size()>0,"Watcher DTOs information was not displayed.");
		
		LinkedHashMap<String, Object> watcherDTO = watcherDTOs.get(0); 
		String actualCustomerid = String.valueOf(watcherDTO.get("customerId"));
		String actualWatcherEmail = watcherDTO.get("emailId").toString();
				
		softAssert.assertEquals(actualCustomerid, customerId,
				String.format("Expected Customer id was {0} and actual was {1}.", customerId,actualCustomerid));
				
		softAssert.assertEquals(actualWatcherEmail, passengerEmail,
				String.format("Expected watcher email id was {0} and actual was {1}.", passengerEmail,actualWatcherEmail));
		
		//ToDO : Verify itinerarySummaryDTO, billability and Cost Allocation once respective api's are automated.
		
		softAssert.assertAll();
		test.log(Status.PASS, "Trip Stepper info verification was successful.");

	}
	
	/**
	 * This Test will Verify Trip Stepper info for Step Parameter = COSTALLOCATION_WATCHERS
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(priority=1,dependsOnMethods = "verifyTripStepperInfoWithStepParameterALL")
	public void verifyTripStepperInfoWithStepParameterCOSTALLOCATION_WATCHERS() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		RestUtils restUtils = new RestUtils();
		TripStepperInfoTestsHelper helper = new TripStepperInfoTestsHelper();
			
		
		//Call the get trip stepper info api
		Response response = helper.getTripStepperInfo(tripId, "COSTALLOCATION_WATCHERS");
		
		validateResponseToContinueTest(response, 200, String.format("Unable to get trip stepper info"), true);

		String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));
		
		//Verify passenger details
		List<LinkedHashMap<String, Object>> passengerDTOs =  (List<LinkedHashMap<String, Object>>) restUtils.getValueFromResponse(response, "content.passengerDTOs");
		softAssert.assertTrue(passengerDTOs.size()==0,"Passenger DTOs information was displayed.");
		
		
		//Verify watchers details
		List<LinkedHashMap<String, Object>> watcherDTOs =  (List<LinkedHashMap<String, Object>>) restUtils.getValueFromResponse(response, "content.watcherDTOs");
		softAssert.assertTrue(watcherDTOs.size()>0,"Watcher DTOs information was not displayed.");
		LinkedHashMap<String, Object> watcherDTO =  watcherDTOs.get(0);
		String actualCustomerid = String.valueOf(watcherDTO.get("customerId"));
		String actualWatcherEmail = watcherDTO.get("emailId").toString();
				
		softAssert.assertEquals(actualCustomerid, customerId,
				String.format("Expected Customer id was {0} and actual was {1}.", customerId,actualCustomerid));
				
		softAssert.assertEquals(actualWatcherEmail, passengerEmail,
				String.format("Expected watcher email id was {0} and actual was {1}.", passengerEmail,actualWatcherEmail));
		
		//ToDO : Verify itinerarySummaryDTO, billability and Cost Allocation once respective api's are automated.
		
		softAssert.assertAll();
		test.log(Status.PASS, "Trip Stepper info verification was successful.");

	}
	
	/**
	 * This Test will Verify Trip Stepper info for Step Parameter = PASSENGERS
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(priority=2,dependsOnMethods = "verifyTripStepperInfoWithStepParameterALL")
	public void verifyTripStepperInfoWithStepParameterPASSENGERS() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		RestUtils restUtils = new RestUtils();
		TripStepperInfoTestsHelper helper = new TripStepperInfoTestsHelper();
			
		
		//Call the get trip stepper info api
		Response response = helper.getTripStepperInfo(tripId, "PASSENGERS");
		
		validateResponseToContinueTest(response, 200, String.format("Unable to get trip stepper info"), true);

		String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));
		
		//Verify passenger details
		List<LinkedHashMap<String, Object>> passengerDTOs =  (List<LinkedHashMap<String, Object>>) restUtils.getValueFromResponse(response, "content.passengerDTOs");
		softAssert.assertTrue(passengerDTOs.size()>0,"Passenger DTOs information was not displayed.");
		LinkedHashMap<String, Object> passengerDTO = passengerDTOs.get(0);
		String actualPassengerid = String.valueOf(passengerDTO.get("passengerId"));
		String actualPassengerEmail = passengerDTO.get("email").toString();
		
		softAssert.assertEquals(actualPassengerid, passengerId,
				String.format("Expected passenger id was {0} and actual was {1}.", passengerId,actualPassengerid));
		
		softAssert.assertEquals(actualPassengerEmail, passengerEmail,
				String.format("Expected passenger email id was {0} and actual was {1}.", passengerEmail,actualPassengerEmail));
		
		//Verify watchers details
		List<LinkedHashMap<String, Object>> watcherDTOs =  (List<LinkedHashMap<String, Object>>) restUtils.getValueFromResponse(response, "content.watcherDTOs");
		softAssert.assertTrue(watcherDTOs.size()==0,"Watcher DTOs information was displayed.");
		
		//Verify Cost Allocation details
		List<LinkedHashMap<String, Object>> costAllocationDTOs =  (List<LinkedHashMap<String, Object>>) restUtils.getValueFromResponse(response, "content.costAllocationDTOs");
		softAssert.assertTrue(costAllocationDTOs.size()==0,"Cost Allocation DTOs information was displayed.");
		
		//ToDO : Verify itinerarySummaryDTO, billability and Cost Allocation once respective api's are automated.
		
		softAssert.assertAll();
		test.log(Status.PASS, "Trip Stepper info verification was successful.");

	}
	
	/**
	 * This Test will Verify Trip Stepper info for invalid Step Parameter
	 * 
	 * @throws Exception
	 */
	@Test(priority=3,dependsOnMethods = "verifyTripStepperInfoWithStepParameterALL")
	public void verifyTripStepperInfoWithInvalidStepParameter() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		RestUtils restUtils = new RestUtils();
		TripStepperInfoTestsHelper helper = new TripStepperInfoTestsHelper();
			
		
		//Call the get trip stepper info api
		Response response = helper.getTripStepperInfo(tripId, "XYZ");
		
		validateResponseToContinueTest(response, 200, String.format("Unable to execute Trip Stepper info"), true);

		String expectedStatus = "FAILURE";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = "Invalid step";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));
		softAssert.assertAll();
		
		test.log(Status.PASS, "Trip Stepper info verification for invalid step parameter was successful.");

	}

	/**
	 * This Test will Verify Trip Stepper info for invalid trip id
	 * 
	 * @throws Exception
	 */
	@Test(priority=4)
	public void verifyTripStepperInfoWithInvalidTripId() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		RestUtils restUtils = new RestUtils();
		TripStepperInfoTestsHelper helper = new TripStepperInfoTestsHelper();
			
		
		//Call the get trip stepper info api
		Response response = helper.getTripStepperInfo("00000", "ALL");
		
		validateResponseToContinueTest(response, 200, String.format("Unable to execute Trip Stepper info"), true);

		String expectedStatus = "FAILURE";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = "Invalid Parameters";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));
		softAssert.assertAll();
		
		test.log(Status.PASS, "Trip Stepper info verification for invalid trip id was successful.");

	}


}
