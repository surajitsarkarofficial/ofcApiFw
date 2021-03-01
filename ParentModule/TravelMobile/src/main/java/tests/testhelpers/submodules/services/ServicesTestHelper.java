package tests.testhelpers.submodules.services;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import endpoints.submodules.services.ServicesEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.TravelMobileBaseTest;
import tests.testhelpers.TravelMobileBaseHelper;
import utils.RestUtils;

public class ServicesTestHelper extends TravelMobileBaseHelper{
	
	/**
	 * This method will hit the get other services api and return the response.
	 * @return Response
	 * @throws Exception
	 */
	public Response getOtherServices() throws Exception
	{
		String endpointURL = TravelMobileBaseTest.baseUrl+ServicesEndpoints.getOtherServices;
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token);		
		Response response =new RestUtils().executeGET(requestSpec, endpointURL);
		return response;		
	}
	
	/**
	 * This method will provide the map of additional services with ids
	 * @return HashMap<Integer,String>
	 */
	public HashMap<Integer,String> getOtherServicesExpectedData()
	{
			
		HashMap<Integer,String> otherServices = new HashMap<>();
		otherServices.put(1, "Early Check in");
		otherServices.put(2, "Late Check out");
		otherServices.put(3, "Luggage");
		otherServices.put(4, "Crib");
		otherServices.put(5, "Seats");
		otherServices.put(6, "Low Cost Flights");	
		
		return otherServices;
		
		
	}
	
	/**
	 * This method will hit the add services api
	 * @param dtoBodyMap
	 * @param tripId
	 * @return Response
	 */
	public Response addService(Map<Object,Object> dtoBodyMap,int tripId)
	{
		String endpointURL = TravelMobileBaseTest.baseUrl+
				String.format(ServicesEndpoints.addServices,tripId);
		JSONObject jsonBody = new JSONObject(dtoBodyMap);
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token).
				header("content-type","application/json").body(jsonBody.toString());		
		Response response =new RestUtils().executePOST(requestSpec, endpointURL);
		response.then();
		return response;		
	}
	
	/**
	 * This method will hit the get services present in a trip api and return the response.
	 * @param tripId
	 * @return Response
	 * @throws Exception
	 */
	public Response getServicesOfTrip(int tripId) throws Exception
	{
		String endpointURL = String.format(TravelMobileBaseTest.baseUrl+ServicesEndpoints.getServices,tripId);
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token);		
		Response response =new RestUtils().executeGET(requestSpec, endpointURL);
		return response;		
	}
	
	/**
	 * This method will return the body by removing the specified property from the body
	 * @param testScenario
	 * @param body
	 * @return HashMap<Object, Object>
	 */
	public HashMap<Object, Object> removeParameterInvalidValidation(String testScenario,HashMap<Object, Object> body)
	{
		switch(testScenario)
		{
		case "Missing type" : body.remove("type");break;
		case "Missing tripPassengersIds" : body.remove("tripPassengersIds");break;
		case "Missing startDate" : body.remove("startDate");break;
		case "Missing endDate" : body.remove("endDate");break;
		case "Missing startLocation" : body.remove("startLocation");break;
		case "Missing endLocation" : body.remove("endLocation");break;
		case "Missing additionalDetails" : body.remove("additionalDetails");break;
		}
		
		return body;
		
	}

}
