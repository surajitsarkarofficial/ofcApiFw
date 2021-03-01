package tests.testhelpers.submodules.emergencyContacts;

import java.util.List;
import java.util.Map;

import endpoints.submodules.emergencyContacts.EmergencyContactsEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.TravelMobileBaseTest;
import tests.testcases.submodules.emergencyContacts.EmergencyContactsTests;
import tests.testhelpers.TravelMobileBaseHelper;
import tests.testhelpers.submodules.myTrips.features.PassengerTestsHelper;
import utils.RestUtils;
import utils.Utilities;

public class EmergencyContactsTestHelper extends TravelMobileBaseHelper{
	
	/**
	 * This method will add emergency Contact
	 * @param dtoBodyMap
	 * @param passengerId
	 * @return
	 */
	public Response addEmergencyContact(Map<Object,Object> dtoBodyMap,String passengerId)
	{
		String jsonBody = Utilities.createJsonBodyFromMap(dtoBodyMap);
		String endpointURL = TravelMobileBaseTest.baseUrl+
				String.format(EmergencyContactsEndpoints.addEmergencyContactEndpoint,passengerId);
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token).
				header("content-type","application/json").body(jsonBody);		
		Response response =new RestUtils().executePOST(requestSpec, endpointURL);
		return response;		
	}
	/**
	 * This method will update emergency Contact
	 * @param dtoBodyMap
	 * @param emergencyContactId
	 * @param passengerId
	 * @return
	 */
	public Response updateEmergencyContact(Map<Object,Object> dtoBodyMap,String emergencyContactId,String passengerId)
	{
		String jsonBody = Utilities.createJsonBodyFromMap(dtoBodyMap);
		String endpointURL = TravelMobileBaseTest.baseUrl+
				String.format(EmergencyContactsEndpoints.updateEmergencyContactEndpoint,emergencyContactId,passengerId);
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token).
				header("content-type","application/json").body(jsonBody);		
		Response response =new RestUtils().executePUT(requestSpec, endpointURL);
		return response;		
	}
	/**
	 * This method will delete emergency contact
	 * @param eContactId
	 * @return
	 */
	public Response deleteEmergencyContact(String eContactId)
	{
		String endpointURL = TravelMobileBaseTest.baseUrl+
				String.format(EmergencyContactsEndpoints.deleteEmergencyContactEndpoint,eContactId);
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token).
				header("content-type","application/json");		
		Response response =new RestUtils().executeDELETE(requestSpec, endpointURL);
		return response;		
	}
	/**
	 * This method will construct the dto for missing dto emergency contact tests
	 * @param dtoBodyMap
	 * @param testScenario
	 * @return
	 */
	public Map<Object,Object> constructDTOForMissingDTOEmergencyContactTests(Map<Object,Object> dtoBodyMap,String testScenario)
	{
		if(testScenario.contains("Missing 'name' tag"))
		{
			dtoBodyMap.remove("name");
		}else if(testScenario.contains("Missing 'relationship' tag"))
		{
			dtoBodyMap.remove("relationship");
		}else if(testScenario.contains("Missing 'phoneNumber' tag"))
		{
			dtoBodyMap.remove("phoneNumber");
		}
		else if(testScenario.contains("Missing 'isoCountryCode' tag"))
		{
			dtoBodyMap.remove("isoCountryCode");
		}
		else if(testScenario.contains("Missing 'address' tag"))
		{
			dtoBodyMap.remove("address");
		}else if(testScenario.contains("Missing 'id' tag"))
		{
			dtoBodyMap.remove("id");
		}
		return dtoBodyMap;
	}
	/**
	 * This method will construct the dto for invalid data add and update emergency contact tests
	 * @param dtoBodyMap
	 * @param testScenario
	 * @return
	 */
	public Map<Object,Object> constructDTOForInvalidDataAddAndUpdateEmergencyContactTests(Map<Object,Object> dtoBodyMap,String testScenario)
	{
		if(testScenario.contains("Empty Id"))
		{
			dtoBodyMap.put("id", "");
		}
		else if(testScenario.contains("Empty Name"))
		{
			dtoBodyMap.put("name", "");
		}else if(testScenario.contains("Empty Relationship"))
		{
			dtoBodyMap.put("relationship", "");
		}
		else if(testScenario.contains("Empty PhoneNumber"))
		{
			dtoBodyMap.put("phoneNumber", "");
		}
		else if(testScenario.contains("Empty IsoCountryCode"))
		{
			dtoBodyMap.put("isoCountryCode", "");
		}
		else if(testScenario.contains("Empty Address"))
		{
			dtoBodyMap.put("address", "");
		}
		return dtoBodyMap;
	}
	
	/**
	 * This method will create the dto for update emergency contact api for valid data
	 * @param dtoBodyMap
	 * @param testScenario
	 * @return
	 */
	public Map<Object,Object> constructDTOForUpdateEmergencyContactTests(Map<Object,Object> dtoBodyMap,String testScenario)
	{
		Map<Object,Object> dtoMap = dtoBodyMap;
		if(testScenario.contains("Update name"))
		{
			dtoMap.put("name", "Auto "+Utilities.getCurrentDateAndTime("MMddyyhhmmss"));
		}else if(testScenario.contains("Update relationship"))
		{
			dtoMap.put("relationship","Gurdian");
		}else if(testScenario.contains("Update phone number"))
		{
			dtoMap.put("phoneNumber",Utilities.generateRandomPhoneNumber());
		}else if(testScenario.contains("Update address"))
		{
			dtoMap.put("address","Pune - " +Utilities.getRandomNumberBetween(100000, 999999));
		}
		return dtoMap;
	}
	
	
	/**
	 * This method will return the list of emergency contacts ids for a specific passenger 
	 * @param passengerEmail
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getListOfEmergencyContactIds(String passengerEmail) throws Exception
	{
		Response passengerInfoResponse = new PassengerTestsHelper().getUserInfo(passengerEmail);
		new EmergencyContactsTests().validateResponseToContinueTest(passengerInfoResponse, 200, 
				"Unable to fetch passenger info for user with email id - "+passengerEmail, true);
		@SuppressWarnings("unchecked")
		List<Integer> eContactIds = (List<Integer>)new RestUtils().getValueFromResponse(passengerInfoResponse, "content.emergencyContactDTOs[*].id");
		return eContactIds;
	}
	/**
	 * This method will return the list of the emergency contacts
	 * @param passengerEmail
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getListOfEmegerencyContactIds (String passengerEmail) throws Exception
	{
		Response userResponse = getUserInfo(passengerEmail);
		List<Integer> emergencyContactIds = (List<Integer>) new RestUtils().getValueFromResponse(userResponse, "content.emergencyContactDTOs[*].id");
		return emergencyContactIds;
	}
	/**
	 * This method will delete all emergency contact for specified passenger email
	 * @param passengerEmail
	 * @throws Exception
	 */
	public void deleteAllEmergencyContacts(String passengerEmail) throws Exception
	{
		List<Integer> emergencyContactIds = getListOfEmegerencyContactIds(passengerEmail);
		for(int econtactId : emergencyContactIds)
		{
			Response response = deleteEmergencyContact(String.valueOf(econtactId));
			validateResponseToContinueTest(response, 200, "Unable to Delete Emergency Contact id " + econtactId,
					true);

			String expectedStatus = "SUCCESS";
			String actualStatus = String.valueOf(new RestUtils().getValueFromResponse(response, "status"));
			if(!expectedStatus.equals(actualStatus))
			{
				throw new Exception ("Unable to delete Emergency contact.");
			}
		}
		
	}
}
