package tests.testhelpers.submodules.myTrips.features;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import endpoints.submodules.myTrips.features.WatchersEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.TravelMobileBaseTest;
import tests.testhelpers.submodules.myTrips.MyTripsTestHelper;
import utils.RestUtils;
import utils.Utilities;

public class WatchersTestsHelper extends MyTripsTestHelper{
	
	/**
	 * This method will add watchers a trip
	 * @param tripId
	 * @param emailIds
	 * @return Response
	 * @throws Exception
	 */
	public Response addWatcherToTrip(String tripId,List<String> emailIds) throws Exception
	{
		String endpointURL = TravelMobileBaseTest.baseUrl+
				String.format(WatchersEndpoints.addWatcherToATripEndpoint,tripId);
		Map<Object, Object> bodyMap = new HashMap<Object, Object>();
		bodyMap.put("emailIds", emailIds);
		String jsonBody = Utilities.createJsonBodyFromMap(bodyMap);
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token).
				header("content-type","application/json").body(jsonBody);		
		Response response =new RestUtils().executePOST(requestSpec, endpointURL);
		return response;		
	}
	
	/**
	 * This method will remove watcher form trip
	 * @param tripId
	 * @param emailId
	 * @return Response
	 * @throws Exception
	 */
	public Response removeWatcherFromTrip(String tripId,String emailId) throws Exception
	{
		String endpointURL = TravelMobileBaseTest.baseUrl+
				String.format(WatchersEndpoints.removeWatcherFromTripEndpoint,tripId,emailId.trim());
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token).
				header("content-type","application/json");		
		Response response =new RestUtils().executeDELETE(requestSpec, endpointURL.trim());
		return response;		
	}

}
