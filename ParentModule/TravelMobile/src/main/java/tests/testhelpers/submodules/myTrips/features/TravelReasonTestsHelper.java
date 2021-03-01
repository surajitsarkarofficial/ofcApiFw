package tests.testhelpers.submodules.myTrips.features;

import java.util.LinkedHashMap;

import endpoints.submodules.myTrips.features.TravelReasonEndpoint;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONArray;
import tests.testcases.TravelMobileBaseTest;
import tests.testcases.submodules.myTrips.features.TravelReasonTests;
import tests.testhelpers.submodules.myTrips.MyTripsTestHelper;
import utils.RestUtils;
import utils.Utilities;

public class TravelReasonTestsHelper extends MyTripsTestHelper{
	
	/**
	 * This method will return the travel reasons
	 * @return Response
	 */
	public Response executeGetTravelReasonsAPI()
	{
		String endpointURL = TravelMobileBaseTest.baseUrl+TravelReasonEndpoint.getTravelReasonEndpoint;
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token);		
		Response response =new RestUtils().executeGET(requestSpec, endpointURL);
		return response;		
	}
	
	/**
	 * This method will return a travel reason data set
	 * @return LinkedHashMap<String,Object>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap<String,Object> getTravelReason() throws Exception
	{
		LinkedHashMap<String,Object> programData=null;
		
		Response response=executeGetTravelReasonsAPI();
		
		new TravelReasonTests().validateResponseToContinueTest(response,200,"Unable to fetch travel reason data.",true);
		
		JSONArray content =(JSONArray) new RestUtils().getValueFromResponse(response, "content");
		
		int len=content.size();
		if(len>0)
		{
			int index = Utilities.getRandomNumberBetween(0, len);
			programData = (LinkedHashMap<String,Object>) content.get(index);
			
		}else {
			throw new Exception("Travel Reason Data was empty.");
		}
		return programData;	
		
	}

}
