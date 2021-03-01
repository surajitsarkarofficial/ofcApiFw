package tests.testcases.submodules.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import dataproviders.submodules.myTrips.features.TripDataProvider;
import dataproviders.submodules.services.ServicesDataproviders;
import io.restassured.response.Response;
import tests.testcases.TravelMobileBaseTest;
import tests.testhelpers.submodules.myTrips.features.TravelReasonTestsHelper;
import tests.testhelpers.submodules.myTrips.features.TripStepperInfoTestsHelper;
import tests.testhelpers.submodules.myTrips.features.TripTestsHelper;
import tests.testhelpers.submodules.services.ServicesTestHelper;
import utils.RestUtils;
import utils.Utilities;

public class ServicesTests extends TravelMobileBaseTest{
	
	private static int tripId , tripPassengerId;
	HashMap<Object, Object> body;
	RestUtils restUtils;
	SoftAssert softAssert;
	ServicesTestHelper testHelper;
	boolean isTripCreatedForNegativeAddService = false;
	
	@BeforeTest(alwaysRun = true)
	public void beforeTest() {
		subModuleTest = moduleTest.createNode("Services Tests");
	}

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("Services Tests");
	}
	
	@BeforeMethod(alwaysRun=true)
	public void beforeMethod()
	{
		body = new HashMap<Object, Object>();
		restUtils = new RestUtils();
		softAssert = new SoftAssert();
		testHelper = new ServicesTestHelper();
	}
	
	@SuppressWarnings("unchecked")
	public void createTripForTest() throws Exception
	{
		Map<Object, Object> createTripDto  = (Map<Object, Object>) TripDataProvider.createTripWithValidData()[0][0];

		
		//Create a trip to use it for the rest of the testing
		LinkedHashMap<String,Object> travelReason =new TravelReasonTestsHelper().getTravelReason();
		createTripDto.put("travelReason", travelReason);	
		Response createTripResponse = new TripTestsHelper().createTrip(createTripDto);
		validateResponseToContinueTest(createTripResponse, 200, "Unable to create Trip.", true);
		tripId = Integer.parseInt(restUtils.getValueFromResponse(createTripResponse, "content.tripId").toString());
		
		Response tripStepperInfoResponse = new TripStepperInfoTestsHelper().getTripStepperInfo(String.valueOf(tripId), "ALL");
		
		validateResponseToContinueTest(tripStepperInfoResponse, 200, String.format("Unable to get trip stepper info"), true);
		
		tripPassengerId = Integer.parseInt(restUtils.getValueFromResponse(tripStepperInfoResponse, "content.passengerDTOs[0].tripPassengerId").toString());
		
	}

	/**
	 * This test method will verify add KTN api with valid data
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(priority = 0)
	public void verifyGetOtherServices() throws Exception {
		
		Response response = testHelper.getOtherServices();

		validateResponseToContinueTest(response, 200, String.format("Unable fetch other services"), true);

		String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedResponseMessage,
				String.format("Expected message was '%s' and actual was '%s'", expectedResponseMessage,
						restUtils.getValueFromResponse(response, "message")));
		
		List<HashMap<Object,Object>> actualServices = (List<HashMap<Object, Object>>) restUtils.getValueFromResponse(response, "content");
		HashMap<Integer,String> expectedServices = testHelper.getOtherServicesExpectedData();
	
		softAssert.assertEquals(actualServices.size(),expectedServices.size(),
				String.format("Total no. of services expected was %d and actual was %d.", expectedServices.size(),actualServices.size()));
		
		for(HashMap<Object,Object> data : actualServices)
		{
			int id = Integer.parseInt(data.get("id").toString());
			String name = data.get("name").toString();
			
			softAssert.assertTrue(expectedServices.containsKey(id),String.format("Id %d is missing.", id));
			softAssert.assertEquals(name,expectedServices.get(id),
					String.format("Expected name for id %d was %s and actual was %s.", id,expectedServices.get(id),name));
		}
		softAssert.assertAll();

		test.log(Status.PASS, "Get Other Services verification was successful.");

	}
	/**
	 * This test method will test the add Insurance Service
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	@Test(priority = 1)
	public void addInsuranceService() throws Exception
	{
		
		createTripForTest();
		String startDate = Utilities.getCurrentDateAndTime("yyyy-MM-dd")+"T00:00:00";
		String endDate = Utilities.getFutureDate("yyyy-MM-dd", 10)+"T00:00:00";
		String serviceType = "TRAVEL_ASSISTANCE";
		String additionalDetails = "automation services";
		body.put("type", serviceType);		
	    body.put("startDate",startDate);
	    body.put("endDate", endDate);
	    body.put("additionalDetails", additionalDetails);
	    int[] countries = {1};
	    body.put("countries", countries);
	    int[] tripPassengersIds = {tripPassengerId};
	    body.put("tripPassengersIds", tripPassengersIds);
	    Response response = testHelper.addService(body, tripId);
	    
	    validateResponseToContinueTest(response, 200, "Unable to add service to trip.", true);
	    
	    String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus, String
				.format("Expected status was '%s' and actual was '%s'", expectedStatus,restUtils.getValueFromResponse(response, "status")));
		
		String expectedMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedMessage, String
				.format("Expected message for add service was '%s' and actual was '%s'", expectedMessage,restUtils.getValueFromResponse(response, "message")));
		
		softAssert.assertTrue(String.valueOf(restUtils.getValueFromResponse(response, "content.id"))!=null);
		softAssert.assertTrue(String.valueOf(restUtils.getValueFromResponse(response, "content.serviceRequired"))!=null);
		
		softAssert.assertEquals(Integer.parseInt(String.valueOf(restUtils.getValueFromResponse(response, "content.tripId"))), tripId, String
				.format("Expected tripId was '%d' and actual was '%s'", tripId,Integer.parseInt(String.valueOf(restUtils.getValueFromResponse(response, "content.tripId")))));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.type"), serviceType, String
				.format("Expected service Type was '%s' and actual was '%s'", serviceType,restUtils.getValueFromResponse(response, "content.type")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.startDate"), startDate, String
				.format("Expected start date was '%s' and actual was '%s'", startDate,restUtils.getValueFromResponse(response, "content.startDate")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.endDate"), endDate, String
				.format("Expected end Date was '%s' and actual was '%s'", endDate,restUtils.getValueFromResponse(response, "content.endDate")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.additionalDetails"), additionalDetails, String
				.format("Expected additional Details was '%s' and actual was '%s'", additionalDetails,restUtils.getValueFromResponse(response, "content.additionalDetails")));
		
		List<Integer> actualTripPassengerIds = (List<Integer>)restUtils.getValueFromResponse(response, "content.tripPassengersIds");
		List<Integer> expectedTripPassengerIds = Arrays.stream(tripPassengersIds).boxed().collect(Collectors.toList());
		softAssert.assertEquals(actualTripPassengerIds, expectedTripPassengerIds, String
				.format("Expected TripPassengerIds ids were '%s' and actual were '%s'", expectedTripPassengerIds.toString(),actualTripPassengerIds.toString()));
		
		List<Integer> actualCountryIds = (List<Integer>)restUtils.getValueFromResponse(response, "content.countryIds");
		List<Integer> expectedCountryIds = Arrays.stream(countries).boxed().collect(Collectors.toList());
		softAssert.assertEquals(actualCountryIds, expectedCountryIds, String
				.format("Expected country ids were '%s' and actual were '%s'", expectedCountryIds.toString(),actualCountryIds.toString()));
		
		
		softAssert.assertAll();
	}
	
	
	/**
	 * This test method will test the add additional Service
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	@Test(priority = 2,dependsOnMethods = "addInsuranceService")
	public void addAdditionalServices() throws Exception
	{
		
		String serviceType = "OTHER";
		String additionalDetails = "automation - additional services";
		int otherServiceId = 1;
		body.put("type", serviceType);		
	    body.put("additionalDetails", additionalDetails);
	    body.put("additionalDetails", additionalDetails);
	    int[] tripPassengersIds = {tripPassengerId};
	    body.put("tripPassengersIds", tripPassengersIds);
	    body.put("otherServiceId", otherServiceId);
	    
	    Response response = testHelper.addService(body, tripId);	    
	    
	    validateResponseToContinueTest(response, 200, "Unable to add service to trip.", true);
	    String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus, String
				.format("Expected status was '%s' and actual was '%s'", expectedStatus,restUtils.getValueFromResponse(response, "status")));
		
		String expectedMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedMessage, String
				.format("Expected message for add service was '%s' and actual was '%s'", expectedMessage,restUtils.getValueFromResponse(response, "message")));
		
		softAssert.assertTrue(String.valueOf(restUtils.getValueFromResponse(response, "content.id"))!=null);
		softAssert.assertTrue(String.valueOf(restUtils.getValueFromResponse(response, "content.serviceRequired"))!=null);
		
		softAssert.assertEquals(Integer.parseInt(String.valueOf(restUtils.getValueFromResponse(response, "content.tripId"))), tripId, String
				.format("Expected tripId was '%d' and actual was '%s'", tripId,Integer.parseInt(String.valueOf(restUtils.getValueFromResponse(response, "content.tripId")))));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.type"), serviceType, String
				.format("Expected service Type was '%s' and actual was '%s'", serviceType,restUtils.getValueFromResponse(response, "content.type")));
		
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.additionalDetails"), additionalDetails, String
				.format("Expected additional Details was '%s' and actual was '%s'", additionalDetails,restUtils.getValueFromResponse(response, "content.additionalDetails")));
		
		List<Integer> actualTripPassengerIds = (List<Integer>)restUtils.getValueFromResponse(response, "content.tripPassengersIds");
		List<Integer> expectedTripPassengerIds = Arrays.stream(tripPassengersIds).boxed().collect(Collectors.toList());
		softAssert.assertEquals(actualTripPassengerIds, expectedTripPassengerIds, String
				.format("Expected TripPassengerIds ids were '%s' and actual were '%s'", expectedTripPassengerIds.toString(),actualTripPassengerIds.toString()));
		
		softAssert.assertEquals(Integer.parseInt(String.valueOf(restUtils.getValueFromResponse(response, "content.otherServicesId.id"))), otherServiceId, String
				.format("Expected tripId was '%d' and actual was '%d'", otherServiceId,Integer.parseInt(String.valueOf(restUtils.getValueFromResponse(response, "content.otherServicesId.id")))));
		
		String expectedOtherServiceName="Early Check in";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.otherServicesId.name"), expectedOtherServiceName, String
				.format("Expected sother service name was '%s' and actual was '%s'", expectedOtherServiceName,restUtils.getValueFromResponse(response, "content.otherServicesId.name")));
		
		softAssert.assertAll();
	}
	
	/**
	 * This test method will test the add Car rental Service
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	@Test(priority = 3,dependsOnMethods = "addInsuranceService")
	public void addCarRentalService() throws Exception
	{
		
		String startDate = Utilities.getCurrentDateAndTime("yyyy-MM-dd")+"T00:00:00";
		String endDate = Utilities.getFutureDate("yyyy-MM-dd", 10)+"T00:00:00";
		String serviceType = "CAR_RENTAL";
		String additionalDetails = "automation - car rental services";
		body.put("type", serviceType);		
	    body.put("startDate",startDate);
	    body.put("endDate", endDate);
	    body.put("additionalDetails", additionalDetails);
	    int[] tripPassengersIds = {tripPassengerId};
	    body.put("tripPassengersIds", tripPassengersIds);
	    
	    int startLocation = 1234;
	    int endLocation = 4321;
	    body.put("startLocation", startLocation);
	    body.put("endLocation", endLocation);
	    Response response = testHelper.addService(body, tripId);	    
	    validateResponseToContinueTest(response, 200, "Unable to add service to trip.", true);
	    String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus, String
				.format("Expected status was '%s' and actual was '%s'", expectedStatus,restUtils.getValueFromResponse(response, "status")));
		
		String expectedMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedMessage, String
				.format("Expected message for add service was '%s' and actual was '%s'", expectedMessage,restUtils.getValueFromResponse(response, "message")));
		
		softAssert.assertTrue(String.valueOf(restUtils.getValueFromResponse(response, "content.id"))!=null);
		softAssert.assertTrue(String.valueOf(restUtils.getValueFromResponse(response, "content.serviceRequired"))!=null);
		
		softAssert.assertEquals(Integer.parseInt(String.valueOf(restUtils.getValueFromResponse(response, "content.tripId"))), tripId, String
				.format("Expected tripId was '%d' and actual was '%s'", tripId,Integer.parseInt(String.valueOf(restUtils.getValueFromResponse(response, "content.tripId")))));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.type"), serviceType, String
				.format("Expected service Type was '%s' and actual was '%s'", serviceType,restUtils.getValueFromResponse(response, "content.type")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.startDate"), startDate, String
				.format("Expected start date was '%s' and actual was '%s'", startDate,restUtils.getValueFromResponse(response, "content.startDate")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.endDate"), endDate, String
				.format("Expected end Date was '%s' and actual was '%s'", endDate,restUtils.getValueFromResponse(response, "content.endDate")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.additionalDetails"), additionalDetails, String
				.format("Expected additional Details was '%s' and actual was '%s'", additionalDetails,restUtils.getValueFromResponse(response, "content.additionalDetails")));
		
		List<Integer> actualTripPassengerIds = (List<Integer>)restUtils.getValueFromResponse(response, "content.tripPassengersIds");
		List<Integer> expectedTripPassengerIds = Arrays.stream(tripPassengersIds).boxed().collect(Collectors.toList());
		softAssert.assertEquals(actualTripPassengerIds, expectedTripPassengerIds, String
				.format("Expected TripPassengerIds ids were '%s' and actual were '%s'", expectedTripPassengerIds.toString(),actualTripPassengerIds.toString()));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.startLocation"), startLocation, String
				.format("Expected start Location was '%s' and actual was '%s'", startLocation,restUtils.getValueFromResponse(response, "content.startLocation")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.endLocation"), endLocation, String
				.format("Expected end Location was '%s' and actual was '%s'", endLocation,restUtils.getValueFromResponse(response, "content.endLocation")));
		
		
		softAssert.assertAll();
	}
	
	/**
	 * This test method will test the add Train Service
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	@Test(priority = 4,dependsOnMethods = "addInsuranceService")
	public void addTrainService() throws Exception
	{
		
		String startDate = Utilities.getCurrentDateAndTime("yyyy-MM-dd")+"T00:00:00";
		String endDate = Utilities.getFutureDate("yyyy-MM-dd", 10)+"T00:00:00";
		String serviceType = "TRAIN";
		String additionalDetails = "automation - train services";
		String ticketType="ROUND_TRIP";
		body.put("ticketType",ticketType);
		body.put("type", serviceType);		
	    body.put("startDate",startDate);
	    body.put("endDate", endDate);
	    body.put("additionalDetails", additionalDetails);
	    int[] tripPassengersIds = {tripPassengerId};
	    body.put("tripPassengersIds", tripPassengersIds);
	    
	    int startLocation = 1234;
	    int endLocation = 4321;
	    body.put("startLocation", startLocation);
	    body.put("endLocation", endLocation);
	    Response response = testHelper.addService(body, tripId);	    
	    validateResponseToContinueTest(response, 200, "Unable to add service to trip.", true);
	    String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus, String
				.format("Expected status was '%s' and actual was '%s'", expectedStatus,restUtils.getValueFromResponse(response, "status")));
		
		String expectedMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedMessage, String
				.format("Expected message for add service was '%s' and actual was '%s'", expectedMessage,restUtils.getValueFromResponse(response, "message")));
		
		softAssert.assertTrue(String.valueOf(restUtils.getValueFromResponse(response, "content.id"))!=null);
		softAssert.assertTrue(String.valueOf(restUtils.getValueFromResponse(response, "content.serviceRequired"))!=null);
		
		softAssert.assertEquals(Integer.parseInt(String.valueOf(restUtils.getValueFromResponse(response, "content.tripId"))), tripId, String
				.format("Expected tripId was '%d' and actual was '%s'", tripId,Integer.parseInt(String.valueOf(restUtils.getValueFromResponse(response, "content.tripId")))));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.type"), serviceType, String
				.format("Expected service Type was '%s' and actual was '%s'", serviceType,restUtils.getValueFromResponse(response, "content.type")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.startDate"), startDate, String
				.format("Expected start date was '%s' and actual was '%s'", startDate,restUtils.getValueFromResponse(response, "content.startDate")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.endDate"), endDate, String
				.format("Expected end Date was '%s' and actual was '%s'", endDate,restUtils.getValueFromResponse(response, "content.endDate")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.additionalDetails"), additionalDetails, String
				.format("Expected additional Details was '%s' and actual was '%s'", additionalDetails,restUtils.getValueFromResponse(response, "content.additionalDetails")));
		
		List<Integer> actualTripPassengerIds = (List<Integer>)restUtils.getValueFromResponse(response, "content.tripPassengersIds");
		List<Integer> expectedTripPassengerIds = Arrays.stream(tripPassengersIds).boxed().collect(Collectors.toList());
		softAssert.assertEquals(actualTripPassengerIds, expectedTripPassengerIds, String
				.format("Expected TripPassengerIds ids were '%s' and actual were '%s'", expectedTripPassengerIds.toString(),actualTripPassengerIds.toString()));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.startLocation"), startLocation, String
				.format("Expected start Location was '%s' and actual was '%s'", startLocation,restUtils.getValueFromResponse(response, "content.startLocation")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.endLocation"), endLocation, String
				.format("Expected end Location was '%s' and actual was '%s'", endLocation,restUtils.getValueFromResponse(response, "content.endLocation")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.ticketType"), ticketType, String
				.format("Expected Ticket Type was '%s' and actual was '%s'", ticketType,restUtils.getValueFromResponse(response, "content.ticketType")));
		
		
		softAssert.assertAll();
	}

	/**
	 * This test method will test the add Bus Service
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	@Test(priority = 5,dependsOnMethods = "addInsuranceService")
	public void addBusService() throws Exception
	{
		String startDate = Utilities.getCurrentDateAndTime("yyyy-MM-dd")+"T00:00:00";
		String endDate = Utilities.getFutureDate("yyyy-MM-dd", 10)+"T00:00:00";
		String serviceType = "BUS";
		String additionalDetails = "automation - bus services";
		String ticketType="ROUND_TRIP";
		body.put("ticketType",ticketType);
		body.put("type", serviceType);		
	    body.put("startDate",startDate);
	    body.put("endDate", endDate);
	    body.put("additionalDetails", additionalDetails);
	    int[] tripPassengersIds = {tripPassengerId};
	    body.put("tripPassengersIds", tripPassengersIds);
	    
	    int startLocation = 1234;
	    int endLocation = 4321;
	    body.put("startLocation", startLocation);
	    body.put("endLocation", endLocation);
	    Response response = testHelper.addService(body, tripId);	    
	    validateResponseToContinueTest(response, 200, "Unable to add service to trip.", true);
	    String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus, String
				.format("Expected status was '%s' and actual was '%s'", expectedStatus,restUtils.getValueFromResponse(response, "status")));
		
		String expectedMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedMessage, String
				.format("Expected message for add service was '%s' and actual was '%s'", expectedMessage,restUtils.getValueFromResponse(response, "message")));
		
		softAssert.assertTrue(String.valueOf(restUtils.getValueFromResponse(response, "content.id"))!=null);
		softAssert.assertTrue(String.valueOf(restUtils.getValueFromResponse(response, "content.serviceRequired"))!=null);
		
		softAssert.assertEquals(Integer.parseInt(String.valueOf(restUtils.getValueFromResponse(response, "content.tripId"))), tripId, String
				.format("Expected tripId was '%d' and actual was '%s'", tripId,Integer.parseInt(String.valueOf(restUtils.getValueFromResponse(response, "content.tripId")))));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.type"), serviceType, String
				.format("Expected service Type was '%s' and actual was '%s'", serviceType,restUtils.getValueFromResponse(response, "content.type")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.startDate"), startDate, String
				.format("Expected start date was '%s' and actual was '%s'", startDate,restUtils.getValueFromResponse(response, "content.startDate")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.endDate"), endDate, String
				.format("Expected end Date was '%s' and actual was '%s'", endDate,restUtils.getValueFromResponse(response, "content.endDate")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.additionalDetails"), additionalDetails, String
				.format("Expected additional Details was '%s' and actual was '%s'", additionalDetails,restUtils.getValueFromResponse(response, "content.additionalDetails")));
		
		List<Integer> actualTripPassengerIds = (List<Integer>)restUtils.getValueFromResponse(response, "content.tripPassengersIds");
		List<Integer> expectedTripPassengerIds = Arrays.stream(tripPassengersIds).boxed().collect(Collectors.toList());
		softAssert.assertEquals(actualTripPassengerIds, expectedTripPassengerIds, String
				.format("Expected TripPassengerIds ids were '%s' and actual were '%s'", expectedTripPassengerIds.toString(),actualTripPassengerIds.toString()));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.startLocation"), startLocation, String
				.format("Expected start Location was '%s' and actual was '%s'", startLocation,restUtils.getValueFromResponse(response, "content.startLocation")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.endLocation"), endLocation, String
				.format("Expected end Location was '%s' and actual was '%s'", endLocation,restUtils.getValueFromResponse(response, "content.endLocation")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.ticketType"), ticketType, String
				.format("Expected Ticket Type was '%s' and actual was '%s'", ticketType,restUtils.getValueFromResponse(response, "content.ticketType")));
		
		
		softAssert.assertAll();
	}
	/**
	 * This test method will test the add Ferry Service
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	@Test(priority = 6,dependsOnMethods = "addInsuranceService")
	public void addFerryService() throws Exception
	{
		String startDate = Utilities.getCurrentDateAndTime("yyyy-MM-dd")+"T00:00:00";
		String endDate = Utilities.getFutureDate("yyyy-MM-dd", 10)+"T00:00:00";
		String serviceType = "FERRY";
		String additionalDetails = "automation - ferry services";
		String ticketType="ROUND_TRIP";
		body.put("ticketType",ticketType);
		body.put("type", serviceType);		
	    body.put("startDate",startDate);
	    body.put("endDate", endDate);
	    body.put("additionalDetails", additionalDetails);
	    int[] tripPassengersIds = {tripPassengerId};
	    body.put("tripPassengersIds", tripPassengersIds);
	    
	    int startLocation = 1234;
	    int endLocation = 4321;
	    body.put("startLocation", startLocation);
	    body.put("endLocation", endLocation);
	    Response response = testHelper.addService(body, tripId);	    
	    validateResponseToContinueTest(response, 200, "Unable to add service to trip.", true);
	    String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus, String
				.format("Expected status was '%s' and actual was '%s'", expectedStatus,restUtils.getValueFromResponse(response, "status")));
		
		String expectedMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedMessage, String
				.format("Expected message for add service was '%s' and actual was '%s'", expectedMessage,restUtils.getValueFromResponse(response, "message")));
		
		softAssert.assertTrue(String.valueOf(restUtils.getValueFromResponse(response, "content.id"))!=null);
		softAssert.assertTrue(String.valueOf(restUtils.getValueFromResponse(response, "content.serviceRequired"))!=null);
		
		softAssert.assertEquals(Integer.parseInt(String.valueOf(restUtils.getValueFromResponse(response, "content.tripId"))), tripId, String
				.format("Expected tripId was '%d' and actual was '%s'", tripId,Integer.parseInt(String.valueOf(restUtils.getValueFromResponse(response, "content.tripId")))));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.type"), serviceType, String
				.format("Expected service Type was '%s' and actual was '%s'", serviceType,restUtils.getValueFromResponse(response, "content.type")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.startDate"), startDate, String
				.format("Expected start date was '%s' and actual was '%s'", startDate,restUtils.getValueFromResponse(response, "content.startDate")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.endDate"), endDate, String
				.format("Expected end Date was '%s' and actual was '%s'", endDate,restUtils.getValueFromResponse(response, "content.endDate")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.additionalDetails"), additionalDetails, String
				.format("Expected additional Details was '%s' and actual was '%s'", additionalDetails,restUtils.getValueFromResponse(response, "content.additionalDetails")));
		
		List<Integer> actualTripPassengerIds = (List<Integer>)restUtils.getValueFromResponse(response, "content.tripPassengersIds");
		List<Integer> expectedTripPassengerIds = Arrays.stream(tripPassengersIds).boxed().collect(Collectors.toList());
		softAssert.assertEquals(actualTripPassengerIds, expectedTripPassengerIds, String
				.format("Expected TripPassengerIds ids were '%s' and actual were '%s'", expectedTripPassengerIds.toString(),actualTripPassengerIds.toString()));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.startLocation"), startLocation, String
				.format("Expected start Location was '%s' and actual was '%s'", startLocation,restUtils.getValueFromResponse(response, "content.startLocation")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.endLocation"), endLocation, String
				.format("Expected end Location was '%s' and actual was '%s'", endLocation,restUtils.getValueFromResponse(response, "content.endLocation")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.ticketType"), ticketType, String
				.format("Expected Ticket Type was '%s' and actual was '%s'", ticketType,restUtils.getValueFromResponse(response, "content.ticketType")));
		
		
		softAssert.assertAll();
	}
	
	/**
	 * This test method will test the add Shuttle Service
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	@Test(priority = 7,dependsOnMethods = "addInsuranceService")
	public void addShuttleService() throws Exception
	{
		
		String startDate = Utilities.getCurrentDateAndTime("yyyy-MM-dd")+"T00:00:00";
		String endDate = Utilities.getFutureDate("yyyy-MM-dd", 10)+"T00:00:00";
		String serviceType = "SHUTTLE";
		String additionalDetails = "automation - shuttle services";
		String ticketType="ROUND_TRIP";
		body.put("ticketType",ticketType);
		body.put("type", serviceType);		
	    body.put("startDate",startDate);
	    body.put("endDate", endDate);
	    body.put("additionalDetails", additionalDetails);
	    int[] tripPassengersIds = {tripPassengerId};
	    body.put("tripPassengersIds", tripPassengersIds);
	    
	    int startLocation = 1234;
	    int endLocation = 4321;
	    body.put("startLocation", startLocation);
	    body.put("endLocation", endLocation);
	    
	    String dropLocationDetails = "drop";
	    String pickUpLocationDetails="pickup";
	    String pickUpLocationType = "AIRPORT";
	    String dropLocationType="HOTEL";
	    int pickUpLocationTypeId=1;
	    int dropLocationTypeId = 2;
	    body.put("dropLocationDetails", dropLocationDetails);
	    body.put("pickUpLocationDetails", pickUpLocationDetails);
	    body.put("pickUpLocationType", pickUpLocationType);
	    body.put("dropLocationType", dropLocationType);
	    body.put("pickUpLocationTypeId", pickUpLocationTypeId);
	    body.put("dropLocationTypeId", dropLocationTypeId);
	    
	    
	    Response response = testHelper.addService(body, tripId);	    
	    validateResponseToContinueTest(response, 200, "Unable to add service to trip.", true);
	    String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus, String
				.format("Expected status was '%s' and actual was '%s'", expectedStatus,restUtils.getValueFromResponse(response, "status")));
		
		String expectedMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedMessage, String
				.format("Expected message for add service was '%s' and actual was '%s'", expectedMessage,restUtils.getValueFromResponse(response, "message")));
		
		softAssert.assertTrue(String.valueOf(restUtils.getValueFromResponse(response, "content.id"))!=null);
		softAssert.assertTrue(String.valueOf(restUtils.getValueFromResponse(response, "content.serviceRequired"))!=null);
		
		softAssert.assertEquals(Integer.parseInt(String.valueOf(restUtils.getValueFromResponse(response, "content.tripId"))), tripId, String
				.format("Expected tripId was '%d' and actual was '%s'", tripId,Integer.parseInt(String.valueOf(restUtils.getValueFromResponse(response, "content.tripId")))));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.type"), serviceType, String
				.format("Expected service Type was '%s' and actual was '%s'", serviceType,restUtils.getValueFromResponse(response, "content.type")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.startDate"), startDate, String
				.format("Expected start date was '%s' and actual was '%s'", startDate,restUtils.getValueFromResponse(response, "content.startDate")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.endDate"), endDate, String
				.format("Expected end Date was '%s' and actual was '%s'", endDate,restUtils.getValueFromResponse(response, "content.endDate")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.additionalDetails"), additionalDetails, String
				.format("Expected additional Details was '%s' and actual was '%s'", additionalDetails,restUtils.getValueFromResponse(response, "content.additionalDetails")));
		
		List<Integer> actualTripPassengerIds = (List<Integer>)restUtils.getValueFromResponse(response, "content.tripPassengersIds");
		List<Integer> expectedTripPassengerIds = Arrays.stream(tripPassengersIds).boxed().collect(Collectors.toList());
		softAssert.assertEquals(actualTripPassengerIds, expectedTripPassengerIds, String
				.format("Expected TripPassengerIds ids were '%s' and actual were '%s'", expectedTripPassengerIds.toString(),actualTripPassengerIds.toString()));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.startLocation"), startLocation, String
				.format("Expected start Location was '%s' and actual was '%s'", startLocation,restUtils.getValueFromResponse(response, "content.startLocation")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.endLocation"), endLocation, String
				.format("Expected end Location was '%s' and actual was '%s'", endLocation,restUtils.getValueFromResponse(response, "content.endLocation")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.ticketType"), ticketType, String
				.format("Expected Ticket Type was '%s' and actual was '%s'", ticketType,restUtils.getValueFromResponse(response, "content.ticketType")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.dropLocationDetails"), dropLocationDetails, String
				.format("Expected dropLocationDetails was '%s' and actual was '%s'", dropLocationDetails,restUtils.getValueFromResponse(response, "content.dropLocationDetails")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.pickUpLocationDetails"), pickUpLocationDetails, String
				.format("Expected pickUpLocationDetails was '%s' and actual was '%s'", pickUpLocationDetails,restUtils.getValueFromResponse(response, "content.pickUpLocationDetails")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.pickUpLocationType"), pickUpLocationType, String
				.format("Expected pickUpLocationType was '%s' and actual was '%s'", pickUpLocationType,restUtils.getValueFromResponse(response, "content.pickUpLocationType")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.dropLocationType"), dropLocationType, String
				.format("Expected dropLocationType was '%s' and actual was '%s'", dropLocationType,restUtils.getValueFromResponse(response, "content.dropLocationType")));
		
		softAssert.assertEquals(Integer.parseInt(String.valueOf(restUtils.getValueFromResponse(response, "content.pickUpAirportId"))), pickUpLocationTypeId, String
				.format("Expected pickUpAirportId was '%d' and actual was '%d'", pickUpLocationTypeId,Integer.parseInt(String.valueOf(restUtils.getValueFromResponse(response, "content.pickUpAirportId")))));
		
		softAssert.assertEquals(Integer.parseInt(String.valueOf(restUtils.getValueFromResponse(response, "content.dropHotelId"))), dropLocationTypeId, String
				.format("Expected dropHotelId was '%d' and actual was '%d'", dropLocationTypeId,Integer.parseInt(String.valueOf(restUtils.getValueFromResponse(response, "content.dropHotelId")))));
				
		softAssert.assertAll();
	}
	/**
	 * This test method will test the get services from trip api
	 * @throws Exception
	 */
	@Test(priority=8)
	public void getServicesAddedToTrip() throws Exception
	{
		createTripForTest();
		String startDate = Utilities.getCurrentDateAndTime("yyyy-MM-dd")+"T00:00:00";
		String endDate = Utilities.getFutureDate("yyyy-MM-dd", 10)+"T00:00:00";
		String serviceType = "TRAVEL_ASSISTANCE";
		String additionalDetails = "automation services";
		body.put("type", serviceType);		
	    body.put("startDate",startDate);
	    body.put("endDate", endDate);
	    body.put("additionalDetails", additionalDetails);
	    int[] countries = {1};
	    body.put("countries", countries);
	    int[] tripPassengersIds = {tripPassengerId};
	    body.put("tripPassengersIds", tripPassengersIds);
	    Response addServiceResponse = testHelper.addService(body, tripId);
	    	    
	    validateResponseToContinueTest(addServiceResponse, 200, "Unable to add service to trip.", true);
	    
	    int serviceRequiredId = Integer.parseInt(String.valueOf(restUtils.getValueFromResponse(addServiceResponse, "content.serviceRequired")));
	    
	    Response response = testHelper.getServicesOfTrip(tripId);
	    validateResponseToContinueTest(response, 200, "Unable to fetch services added to trip.", true);
	    
	    String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus, String
				.format("Expected status was '%s' and actual was '%s'", expectedStatus,restUtils.getValueFromResponse(response, "status")));
		
		String expectedMessage = "Execution Successful";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedMessage, String
				.format("Expected message for add service was '%s' and actual was '%s'", expectedMessage,restUtils.getValueFromResponse(response, "message")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.tripServices[0].type"), serviceType, String
				.format("Expected service Type was '%s' and actual was '%s'", serviceType,restUtils.getValueFromResponse(response, "content.tripServices[0].type")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.tripServices[0].startDate"), startDate, String
				.format("Expected start date was '%s' and actual was '%s'", startDate,restUtils.getValueFromResponse(response, "content.tripServices[0].startDate")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.tripServices[0].endDate"), endDate, String
				.format("Expected end Date was '%s' and actual was '%s'", endDate,restUtils.getValueFromResponse(response, "content.tripServices[0].endDate")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "content.tripServices[0].additionalDetails"), additionalDetails, String
				.format("Expected additional Details was '%s' and actual was '%s'", additionalDetails,restUtils.getValueFromResponse(response, "content.tripServices[0].additionalDetails")));
		
		int actualRequiredServiceId = (int) restUtils.getValueFromResponse(response, "content.tripServices[0].serviceRequiredId");
		softAssert.assertEquals(actualRequiredServiceId, serviceRequiredId, String
				.format("Expected serviceRequiredId was '%d' and actual was '%d'", serviceRequiredId,actualRequiredServiceId));
		
		int actualTripPassengerIds = (int) restUtils.getValueFromResponse(response, "content.tripServices[0].passengerDTOs[0].tripPassengerId");
		List<Integer> expectedTripPassengerIds = Arrays.stream(tripPassengersIds).boxed().collect(Collectors.toList());
		softAssert.assertTrue(expectedTripPassengerIds.contains(actualTripPassengerIds),String
				.format("Actual Trip passenger id %d was not present in the expected list %s",actualTripPassengerIds,expectedTripPassengerIds.toString()));
		
		softAssert.assertAll();
	}
	/**
	 * This method will verify the negative scenarios for add sevices api
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority = 9,dataProvider = "addServicesWithInvalidData", dataProviderClass = ServicesDataproviders.class)
	public void addServiceWithInvalidData(Map<Object, Object> dtoMap) throws Exception
	{
		if(isTripCreatedForNegativeAddService==false)
		{
			createTripForTest();
			isTripCreatedForNegativeAddService=true;
		}
		String testScenario = (String) dtoMap.get("dataScenario");
		test.log(Status.INFO,
				String.format("Validating add Services for '%s'", testScenario));
		
		
		body.putAll(dtoMap);
		body.put("additionalDetails", "Adding service");
		body.remove("dataScenario");
		body.remove("expectedStatus");
		body.remove("expectedMessage");
		if(testScenario.equals("Empty Passenger Id"))
		{
			int[] tripPassengersIds = {};
			body.put("tripPassengersIds",tripPassengersIds );
		}
		else if(testScenario.equals("Invalid Passenger Id"))
		{
			int[] tripPassengersIds = {9999999};
			body.put("tripPassengersIds",tripPassengersIds );
		}else {
			int[] tripPassengersIds = {tripPassengerId};
		    body.put("tripPassengersIds", tripPassengersIds);
		}
		
		if(testScenario.startsWith("Missing "))
		{
			body = testHelper.removeParameterInvalidValidation(testScenario, body);
		}
		
		Response response = testHelper.addService(body, tripId);	    
	    validateResponseToContinueTest(response, 200, "Unable to add service to trip.", true);
	    String expectedStatus = dtoMap.get("expectedStatus").toString();
	    String expectedMessage = dtoMap.get("expectedMessage").toString();
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus, String
				.format("Expected status was '%s' and actual was '%s'", expectedStatus,restUtils.getValueFromResponse(response, "status")));
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), expectedMessage, String
				.format("Expected message was '%s' and actual was '%s'", expectedMessage,restUtils.getValueFromResponse(response, "message")));
		
		softAssert.assertAll();
		test.log(Status.PASS, "Add Services Negative verification was successful.");
		
	}

}
