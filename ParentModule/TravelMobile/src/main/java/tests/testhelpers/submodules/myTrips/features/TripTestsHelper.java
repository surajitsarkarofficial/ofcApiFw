package tests.testhelpers.submodules.myTrips.features;


import java.util.Map;

import endpoints.submodules.myTrips.features.TripEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.TravelMobileBaseTest;
import tests.testhelpers.submodules.myTrips.MyTripsTestHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * 
 * @author surajit.sarkar
 *
 */

public class TripTestsHelper extends MyTripsTestHelper{
	
	/**
	 * This method will create a trip
	 * @param dtoBodyMap
	 * @return Response
	 * @throws Exception 
	 */
	public Response createTrip(Map<Object,Object> dtoBodyMap) throws Exception
	{
		String jsonBody = Utilities.createJsonBodyFromMap(dtoBodyMap);
		String endpointURL = TravelMobileBaseTest.baseUrl+TripEndpoints.createTripEndpoint;
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token).
				header("content-type","application/json").body(jsonBody);		
		Response response =new RestUtils().executePOST(requestSpec, endpointURL);
		return response;		
	}
	/**
	 * This method will update a trip
	 * @param tripId
	 * @param dtoBodyMap
	 * @return Response
	 */
	public Response updateTrip(Map<Object,Object> dtoBodyMap)
	{
		String jsonBody = Utilities.createJsonBodyFromMap(dtoBodyMap);
		String endpointURL = TravelMobileBaseTest.baseUrl+TripEndpoints.createTripEndpoint;
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token).
				header("content-type","application/json").body(jsonBody);		
		Response response =new RestUtils().executePUT(requestSpec, endpointURL);
		return response;		
	}
	/**
	 * This method will fetch the details of the specified trip
	 * @param tripId
	 * @return Response
	 */
	public Response getTripDetails(String tripId)
	{
		String endpointURL = TravelMobileBaseTest.baseUrl+String.format(TripEndpoints.getTripDetailsEndpoint,tripId);
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token);		
		Response response =new RestUtils().executeGET(requestSpec, endpointURL);
		return response;		
	}
	/**
	 * This method will delete the specified trip id and return the response
	 * @param tripId
	 * @return Response
	 * @throws Exception
	 */
	public Response deleteTrip(String tripId) throws Exception
	{
		String endpointURL = TravelMobileBaseTest.baseUrl+String.format(TripEndpoints.deleteTripEndpoint,tripId);
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token);		
		Response response =new RestUtils().executeDELETE(requestSpec, endpointURL);
		return response;		
	}
	/**
	 * This method will create update Trip dto based on various test conditions.
	 * @param dtoMap
	 * @param testScenario
	 * @param tripName
	 * @param tripId
	 * @return Map<Object, Object>
	 */
	public Map<Object, Object> constructDtoForUpdateTripInvalidScenarios(Map<Object, Object> dtoMap,
			String testScenario,String tripName, String tripId) {
		if (testScenario.contains("Missing trip id")) {
			dtoMap.remove("tripId");
		} else if (testScenario.contains("Missing Status")) {
			dtoMap.remove("status");
		} else if (testScenario.contains("Empty trip id")) {
			dtoMap.put("tripId", "");
		} else if (testScenario.contains("Empty trip name")) {
			dtoMap.put("tripName", "");
		} else {
			dtoMap.put("tripName", tripName);
			dtoMap.put("tripId", tripId);
		}
		return dtoMap;
	}
}
