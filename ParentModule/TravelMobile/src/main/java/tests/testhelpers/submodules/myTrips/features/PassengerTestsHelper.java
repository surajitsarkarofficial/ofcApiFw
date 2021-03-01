package tests.testhelpers.submodules.myTrips.features;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import endpoints.submodules.myTrips.features.PassengerEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.TravelMobileBaseTest;
import tests.testhelpers.submodules.myTrips.MyTripsTestHelper;
import utils.RestUtils;
import utils.Utilities;

public class PassengerTestsHelper extends MyTripsTestHelper{
	
	/**
	 * This method will add passenger to a trip
	 * @param tripId
	 * @param passengerEmailIdList
	 * @return
	 */
	public Response addPassenger(String tripId, List<String> passengerEmailIdList)
	{
		String endpointURL = TravelMobileBaseTest.baseUrl+
				String.format(PassengerEndpoints.addPassengerEndpoint,tripId);
		Map<Object, Object> bodyMap = new HashMap<Object, Object>();
		bodyMap.put("emailIds", passengerEmailIdList);
		String jsonBody = Utilities.createJsonBodyFromMap(bodyMap);
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token).
				header("content-type","application/json").body(jsonBody);		
		Response response =new RestUtils().executePOST(requestSpec, endpointURL);
		return response;
				
	}
	/**
	 * This method will fetch info whether specified passenger  is a part of the trip
	 * @param tripId
	 * @param passengerEmailId
	 * @return
	 */
	public Response getPassengerInfo(String tripId, String passengerEmailId)
	{
		String endpointURL = TravelMobileBaseTest.baseUrl+
				String.format(PassengerEndpoints.getPassengerInfoEndpoint,tripId,passengerEmailId);
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token).
				header("content-type","application/json");		
		Response response =new RestUtils().executeGET(requestSpec, endpointURL);
		return response;		
	}
	/**
	 * This method will delete passenger from a trip
	 * @param tripPassengerId
	 * @return
	 */
	public Response deletePassenger(String tripPassengerId)
	{
		String endpointURL = TravelMobileBaseTest.baseUrl+
				String.format(PassengerEndpoints.deletePassengerEndpoint,tripPassengerId);
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token).
				header("content-type","application/json");		
		Response response =new RestUtils().executeDELETE(requestSpec, endpointURL);
		return response;		
	}
	
	/**
	 * This method will replace passenger from a trip
	 * @param tripPassengerId
	 * @return
	 */
	public Response replacePassenger(String tripPassengerIdTobeReplaced,String newPassengerEmail)
	{
		String endpointURL = TravelMobileBaseTest.baseUrl+
				String.format(PassengerEndpoints.replacePassenger,tripPassengerIdTobeReplaced,newPassengerEmail);
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token).
				header("content-type","application/json");		
		Response response =new RestUtils().executePUT(requestSpec, endpointURL);
		return response;		
	}

}
