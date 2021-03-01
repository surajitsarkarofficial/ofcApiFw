package tests.testhelpers.submodules.myTrips.features;

import endpoints.submodules.myTrips.features.TripStepperEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.TravelMobileBaseTest;
import tests.testhelpers.submodules.myTrips.MyTripsTestHelper;
import utils.RestUtils;

public class TripStepperInfoTestsHelper extends MyTripsTestHelper{
	
	/**
	 * This method will fetch trip stepper info
	 * @param dtoBodyMap
	 * @return
	 * @throws Exception 
	 */
	public Response getTripStepperInfo(String tripId, String stepParameter) throws Exception
	{
		String endpointURL = String.format(TravelMobileBaseTest.baseUrl+TripStepperEndpoints.getTripStepperInfoEndpoint,tripId,stepParameter);
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token).
				header("content-type","application/json");		
		Response response =new RestUtils().executeGET(requestSpec, endpointURL);
		return response;		
	}

}
