package tests.testhelpers.submodules.billabilities;

import endpoints.submodules.billabilities.BillabilitiesEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.TravelMobileBaseTest;
import tests.testhelpers.TravelMobileBaseHelper;
import utils.RestUtils;

/**
 * 
 * @author surajit.sarkar
 *
 */
public class BillabilitiesTestHelper extends TravelMobileBaseHelper{
	
	/**
	 * This will return the response of get billabilities api
	 * @return Response
	 */
	public Response getBillabilities()
	{
		String endpointURL = TravelMobileBaseTest.baseUrl+BillabilitiesEndpoints.billabilitiesEndpoint;
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token).
				header("content-type","application/json");		
		Response response =new RestUtils().executeGET(requestSpec, endpointURL);
		return response;		
	}
}
