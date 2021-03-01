package tests.testhelpers;

import endpoints.TravelMobileEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.GlowBaseTestHelper;
import tests.testcases.TravelMobileBaseTest;
import utils.RestUtils;

public class TravelMobileBaseHelper extends GlowBaseTestHelper{
	
	/**
	 * This method will fetch the user Info of the specified email Id
	 * @param userEmailId
	 * @return
	 */
	public Response getUserInfo(String userEmailId)
	{
		String endpointURL = TravelMobileBaseTest.baseUrl+
				String.format(TravelMobileEndpoints.getUserInfoEndpoint,userEmailId);
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token);
		Response response =new RestUtils().executeGET(requestSpec, endpointURL);
		return response;		
	}


}
