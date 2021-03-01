package tests.testhelpers.submodules.ktn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import endpoints.submodules.ktn.KtnEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.TravelMobileBaseTest;
import tests.testhelpers.TravelMobileBaseHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * 
 * @author surajit.sarkar
 *
 */

public class KtnTestHelper extends TravelMobileBaseHelper{
	
	/**
	 * This method will add the KTN for the specified Passenger
	 * @param passengerId
	 * @param ktn
	 * @return Response
	 * @throws Exception
	 */
	public Response addKTN(String passengerId,String ktn) throws Exception
	{
		String endpointURL = TravelMobileBaseTest.baseUrl+
				String.format(KtnEndpoints.ktnOperations,passengerId,ktn);
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token);		
		Response response =new RestUtils().executePOST(requestSpec, endpointURL);
		return response;		
	}
	/**
	 * This method will edit the KTN for the specified Passenger
	 * @param passengerId
	 * @param ktn
	 * @return Response
	 * @throws Exception
	 */
	public Response editKTN(String passengerId,String ktn) throws Exception
	{
		String endpointURL = TravelMobileBaseTest.baseUrl+
				String.format(KtnEndpoints.ktnOperations,passengerId,ktn);
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token);		
		Response response =new RestUtils().executePUT(requestSpec, endpointURL);
		return response;		
	}
	/**
	 * This method will delete the KTN for the specified Passenger
	 * @param passengerId
	 * @param ktn
	 * @return Response
	 * @throws Exception
	 */
	public Response deleteKTN(String passengerId,String ktn) throws Exception
	{
		String endpointURL = TravelMobileBaseTest.baseUrl+
				String.format(KtnEndpoints.ktnOperations,passengerId,ktn);
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token);		
		Response response =new RestUtils().executeDELETE(requestSpec, endpointURL);
		return response;		
	}
	/**
	 * This method will fetch the KTN for the specified Passenger
	 * @param passengerId
	 * @return Response
	 * @throws Exception
	 */
	public Response getKTN(String passengerId) throws Exception
	{
		String endpointURL = TravelMobileBaseTest.baseUrl+
				String.format(KtnEndpoints.getKtn,passengerId);
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token);		
		Response response =new RestUtils().executeGET(requestSpec, endpointURL);
		return response;		
	}
	/**
	 * This method will return the list of the Hotel Loyalty Prog ids
	 * 
	 * @param passengerEmail
	 * @return List<Integer
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getKTNF(String passengerEmail) throws Exception {
		Response userResponse = getUserInfo(passengerEmail);
		List<Integer> hotelLoyaltyProgIds = (List<Integer>) new RestUtils().getValueFromResponse(userResponse,
				"content.hotelLoyaltyDTOs[*].frequentId");
		return hotelLoyaltyProgIds;
	}

	/**
	 * This method will delete KTN for specified passenger if exists
	 * email
	 * 
	 * @param passengerEmail
	 * @throws Exception
	 */
	public void deleteKtnIfExists(String passengerId) throws Exception {
		Response response = getKTN(passengerId);
			validateResponseToContinueTest(response, 200, "Unable to fetch KTN details for Passenger with id " + passengerId,
					true);
			Object content = null;
			try{
				content = new RestUtils().getValueFromResponse(response, "content");
			}catch(Exception e)
			{
				if(e.getMessage().contains("key returned Null value from the response"))
				{
					content = null;
				}else {
					throw e;
				}
			}
			if(content!=null)
			{
				String ktn = String.valueOf(new RestUtils().getValueFromResponse(response, "content.knownTravelerNumber"));
				response =deleteKTN(passengerId,ktn);
				validateResponseToContinueTest(response, 200, "Unable to detelet KTN details for Passenger with id " + passengerId,
						true);
			}

	}
	
	/**
	 * This method will fetch the KTN details from profile
	 * @param tripId
	 * @return Response
	 * @throws Exception
	 */
	public Response getKtnFromProfile(String tripId) throws Exception
	{
		String endpointURL = TravelMobileBaseTest.baseUrl+
				String.format(KtnEndpoints.getKtnFromProfile,tripId);
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token);		
		Response response =new RestUtils().executeGET(requestSpec, endpointURL);
		return response;		
	}
	/**
	 * This method will construct the ktn for invalid data
	 * @param dtoMap
	 * @return Map<Object, Object>
	 */
	public Map<Object, Object> constructKtnForAddKtnWithInvalidData(Map<Object, Object> dtoMap)
	{
		if(dtoMap.get("dataScenario").equals("Empty KTN"))
		{
			dtoMap.put("ktn", "");
		}else
			if(dtoMap.get("dataScenario").equals("KTN greater than 15 characters"))
			{
				String ktn = Utilities.generateRandomAlphaNumericString(16);
				dtoMap.put("ktn",ktn);
			}
		
		return dtoMap;
	}
	/**
	 * This method will associate Ktn with trip
	 * @param tripId
	 * @param tripPassengerId
	 * @param ktn
	 * @return Response
	 * @throws Exception
	 */
	public Response associateKtnToTrip(int tripId,int tripPassengerId,String ktn) throws Exception
	{
		String body = "{"
				+"\"knownTravelerNumbers\":["
				+constructKtnBody(tripPassengerId,ktn).toString()
				+ "]}";
		
		String endpointURL = TravelMobileBaseTest.baseUrl+
				String.format(KtnEndpoints.associateKtnToTrip,tripId);
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token)
				.header("content-type","application/json").body(body);		
		Response response =new RestUtils().executePOST(requestSpec, endpointURL);
		return response;
	}
	
	/**
	 * This method will construct the ktn body 
	 * @param tripPassengerId
	 * @param ktn
	 * @return JSONObject
	 * @throws Exception
	 */
	public JSONObject constructKtnBody(int tripPassengerId,String ktn) throws Exception
	{
		HashMap<Object, Object> body = new HashMap<Object, Object>();
		body.put("id", 0);
		body.put("knownTravelerNumber", ktn);
		body.put("tripPassengerId", tripPassengerId);
		JSONObject obj = new JSONObject(body);
		return obj;
	}
	/**
	 * This method will fetch the KTN details associated to trip
	 * @param tripId
	 * @return Response
	 * @throws Exception
	 */
	public Response getKtnAssociatedToTrip(int tripId) throws Exception
	{
		String endpointURL = TravelMobileBaseTest.baseUrl+
				String.format(KtnEndpoints.getKtnAssociatedToTrip,tripId);
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token);		
		Response response =new RestUtils().executeGET(requestSpec, endpointURL);
		return response;		
	}
}
