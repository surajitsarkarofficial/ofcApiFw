package tests.testhelpers.submodules.profile;

import endpoints.submodules.profile.ProfileEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.TravelMobileBaseTest;
import tests.testhelpers.TravelMobileBaseHelper;
import utils.RestUtils;

public class ProfileTestHelper extends TravelMobileBaseHelper{
	
	/**
	 * This method will return the response for get Profile Image
	 * @param emailId
	 * @return Response
	 */
	public Response getProfileImage(String emailId)
	{
		String endpointURL = TravelMobileBaseTest.baseUrl+
				String.format(ProfileEndpoints.profileImageEndpoint,emailId);
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token).
				header("content-type","application/json");		
		Response response =new RestUtils().executeGET(requestSpec, endpointURL);
		return response;		
	}

}
