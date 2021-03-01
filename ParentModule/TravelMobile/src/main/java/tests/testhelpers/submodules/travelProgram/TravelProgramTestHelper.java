package tests.testhelpers.submodules.travelProgram;



import java.util.LinkedHashMap;

import endpoints.submodules.travelProgram.TravelProgramEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONArray;
import tests.testcases.TravelMobileBaseTest;
import tests.testcases.submodules.travelProgram.TravelProgramTests;
import tests.testhelpers.TravelMobileBaseHelper;
import utils.RestUtils;
import utils.Utilities;

public class TravelProgramTestHelper extends TravelMobileBaseHelper{
	
	/**
	 * This method will fetch the travel program details
	 * @param programType
	 * @return Response
	 */
	public Response getTravelProgramDetails(String programType)
	{
		String endpointURL = TravelMobileBaseTest.baseUrl+
				String.format(TravelProgramEndpoints.travelProgramEndpoint,programType);
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token).
				header("content-type","application/json");		
		Response response =new RestUtils().executeGET(requestSpec, endpointURL);
		return response;		
	}
	/**
	 * This method will return a Set of Travel Program Data
	 * @param programType
	 * @return LinkedHashMap<String,Object>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap<String,Object> getTravelProgramData(String programType) throws Exception
	{
		LinkedHashMap<String,Object> programData=null;
		
		Response response=getTravelProgramDetails(programType);
		
		new TravelProgramTests().validateResponseToContinueTest(response,200,"Unable to fetch travel program data.",true);
		
		JSONArray content =(JSONArray) new RestUtils().getValueFromResponse(response, "content");
		
		int len=content.size();
		if(len>0)
		{
			int index = Utilities.getRandomNumberBetween(0, len);
			programData = (LinkedHashMap<String,Object>) content.get(index);
			
		}else {
			throw new Exception("Program Data was empty.");
		}
		return programData;	
		
	}
	
}
