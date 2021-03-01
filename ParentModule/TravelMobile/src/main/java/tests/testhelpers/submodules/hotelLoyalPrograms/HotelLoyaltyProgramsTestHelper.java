package tests.testhelpers.submodules.hotelLoyalPrograms;



import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import endpoints.submodules.hotelLoyaltyPrograms.HotelLoyaltyProgramsEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.TravelMobileBaseTest;
import tests.testhelpers.TravelMobileBaseHelper;
import utils.RestUtils;
import utils.Utilities;

public class HotelLoyaltyProgramsTestHelper extends TravelMobileBaseHelper{
	
	/**
	 * This method will add the Hotel Loyalty Program 
	 * @param passengerId
	 * @param jsonBody
	 * @return Response
	 * @throws Exception
	 */
	public Response addHotelLoyaltyProgram(String passengerId,String jsonBody) throws Exception
	{
		String endpointURL = TravelMobileBaseTest.baseUrl+
				String.format(HotelLoyaltyProgramsEndpoints.addHotelLoyaltyProgramEnpoint,passengerId);
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token).
				header("content-type","application/json").body(jsonBody);		
		Response response =new RestUtils().executePOST(requestSpec, endpointURL);
		return response;		
	}
	/**
	 * This method will construct the json body for add hotel loyalty program api
	 * @param hotelDetails
	 * @param name
	 * @return String
	 * @throws Exception
	 */
	public String createBodyForHotelLoyaltyProgram(Map<String, Object> hotelDetails,String name) throws Exception
	{
		String progType = "HOTEL";
		Map<String,Object> programTypeDetails = new LinkedHashMap<String, Object>();
		programTypeDetails.put("description", progType);
		programTypeDetails.put("id", 2);
		
		Map<Object,Object> frequentFlyerBody = new LinkedHashMap<Object, Object>();
		
		frequentFlyerBody.put("frequentCategory", hotelDetails);
		frequentFlyerBody.put("frequentNumType", programTypeDetails);
		frequentFlyerBody.put("frequentNumber", name);
		
		String jsonBody = Utilities.createJsonBodyFromMap(frequentFlyerBody);
		
		return jsonBody;
		
		
	}
	/**
	 * This method will create json body for Edit Hotel Loyalty Program
	 * @param hotelDetails
	 * @param hotelLoyaltyProgId
	 * @param name
	 * @return String
	 * @throws Exception
	 */
	public String createBodyForEditHotelLoyaltyProgram(Map<String, Object> hotelDetails,String hotelLoyaltyProgId,String name) throws Exception
	{
		String progType = "HOTEL";
		Map<String,Object> programTypeDetails = new LinkedHashMap<String, Object>();
		programTypeDetails.put("description", progType);
		programTypeDetails.put("id", 2);
		
		Map<Object,Object> frequentFlyerBody = new LinkedHashMap<Object, Object>();
		frequentFlyerBody.put("frequentId", Integer.parseInt(hotelLoyaltyProgId));
		frequentFlyerBody.put("frequentCategory", hotelDetails);
		frequentFlyerBody.put("frequentNumType", programTypeDetails);
		frequentFlyerBody.put("frequentNumber", name);
		
		String jsonBody = Utilities.createJsonBodyFromMap(frequentFlyerBody);
		
		return jsonBody;
		
		
	}
	
	/**
	 * This method will edit the Hotel Loyalty Program
	 * @param passengerId
	 * @param hotelLoyaltyProgId
	 * @param jsonBody
	 * @return Response
	 * @throws Exception
	 */
	public Response editHotelLoyaltyProgram(String passengerId,String hotelLoyaltyProgId,String jsonBody) throws Exception
	{
		String endpointURL = TravelMobileBaseTest.baseUrl+
				String.format(HotelLoyaltyProgramsEndpoints.editHotelLoyaltyProgramEnpoint
						,passengerId,hotelLoyaltyProgId);
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token).
				header("content-type","application/json").body(jsonBody);		
		Response response =new RestUtils().executePUT(requestSpec, endpointURL);
		return response;		
	}
	
	/**
	 * This method will delete the Hotel Loyalty Program
	 * @param passengerId
	 * @param hotelLoyaltyProgId
	 * @return Response
	 * @throws Exception
	 */
	public Response deleteHotelLoyaltyProgram(String passengerId,String hotelLoyaltyProgId) throws Exception
	{
		String endpointURL = TravelMobileBaseTest.baseUrl+
				String.format(HotelLoyaltyProgramsEndpoints.deleteHotelLoyaltyProgramEnpoint
						,passengerId,hotelLoyaltyProgId);
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token).
				header("content-type","application/json");		
		Response response =new RestUtils().executeDELETE(requestSpec, endpointURL);
		return response;		
	}
	
	/**
	 * This method will construct the body for missing dto in hotel loyalty programs
	 * tests
	 * 
	 * @param dtoBodyMap
	 * @param testScenario
	 * @return
	 */
	public Map<Object, Object> constructBodyForMissingDTOHotelLoyaltyProgramsTests(Map<Object, Object> dtoBodyMap,
			String testScenario) {
		if (testScenario.contains("Missing Frequent Id")) {
			dtoBodyMap.remove("frequentId");
		} else if (testScenario.contains("Missing Frequent Category")) {
			dtoBodyMap.remove("frequentCategory");
		} else if (testScenario.contains("Missing Frequent Number Type")) {
			dtoBodyMap.remove("frequentNumType");
		} else if (testScenario.contains("Missing Frequent Number")) {
			dtoBodyMap.remove("frequentNumber");
		}
		return dtoBodyMap;
	}
	
	/**
	 * This method will construct the body for invalid data hotel loyalty program
	 * tests
	 * 
	 * @param dtoBodyMap
	 * @param testScenario
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<Object, Object> constructBodyForHotelLoyaltyProgramWithInvalidData(Map<Object, Object> dtoBodyMap,
			String testScenario) {
		Map<String, Object> map = null;

		if (testScenario.contains("Empty Frequent Category Description")) {
			map = (Map<String, Object>) dtoBodyMap.get("frequentCategory");
			map.put("description", "");
			dtoBodyMap.put("frequentCategory", map);
		} else if (testScenario.contains("Empty Frequent Category id")) {
			map = (Map<String, Object>) dtoBodyMap.get("frequentCategory");
			map.put("id", "");
			dtoBodyMap.put("frequentCategory", map);
		} else if (testScenario.contains("Empty Frequent Number Type Description")) {
			map = (Map<String, Object>) dtoBodyMap.get("frequentNumType");
			map.put("description", "");
			dtoBodyMap.put("frequentNumType", map);
		} else if (testScenario.contains("Empty Frequent Number Type id")) {
			map = (Map<String, Object>) dtoBodyMap.get("frequentNumType");
			map.put("id", "");
			dtoBodyMap.put("frequentNumType", map);
		} else if (testScenario.contains("Empty Frequent Number")) {
			dtoBodyMap.put("frequentNumber", "");
		} else if (testScenario.contains("Empty Frequent id")) {
			dtoBodyMap.put("frequentId", "");
		}
		return dtoBodyMap;
	}
	/**
	 * This method will return the list of the Hotel Loyalty Prog ids
	 * 
	 * @param passengerEmail
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getListOfHotelLoyaltyProgramIds(String passengerEmail) throws Exception {
		Response userResponse = getUserInfo(passengerEmail);
		List<Integer> hotelLoyaltyProgIds = (List<Integer>) new RestUtils().getValueFromResponse(userResponse,
				"content.hotelLoyaltyDTOs[*].frequentId");
		return hotelLoyaltyProgIds;
	}

	/**
	 * This method will delete all Hotal Loyalty Programs for specified passenger
	 * email
	 * 
	 * @param passengerEmail
	 * @throws Exception
	 */
	public void deleteAllHotelLoyaltyPrograms(String passengerEmail) throws Exception {
		List<Integer> hotelLoyaltyProgIds = getListOfHotelLoyaltyProgramIds(passengerEmail);
		Response userResponse = getUserInfo(passengerEmail);
		String passengerId = String.valueOf(new RestUtils().getValueFromResponse(userResponse, "content.passengerId"));
		for (int freFlyerId : hotelLoyaltyProgIds) {
			Response response = deleteHotelLoyaltyProgram(passengerId, String.valueOf(freFlyerId));
			validateResponseToContinueTest(response, 200, "Unable to Delete Hotel loyalty program with id " + freFlyerId,
					true);

			String expectedStatus = "SUCCESS";
			String actualStatus = String.valueOf(new RestUtils().getValueFromResponse(response, "status"));
			if (!expectedStatus.equals(actualStatus)) {
				throw new Exception("Unable to delete Hotel Loyalty Program.");
			}
		}

	}
	
}
