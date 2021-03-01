package tests.testhelpers.submodules.frequentFlyerNumbers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import endpoints.submodules.frequentFlyerNumbers.FrequentFlyerNumberEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.TravelMobileBaseTest;
import tests.testhelpers.TravelMobileBaseHelper;
import utils.RestUtils;
import utils.Utilities;

public class FrequentFlyerNumbersTestHelper extends TravelMobileBaseHelper {

	/**
	 * This method will add the frequent Flyer Number
	 * 
	 * @param passengerId
	 * @param jsonBody
	 * @return Response
	 * @throws Exception
	 */
	public Response addFrequentFlyerNumber(String passengerId, String jsonBody) throws Exception {
		String endpointURL = TravelMobileBaseTest.baseUrl
				+ String.format(FrequentFlyerNumberEndpoints.addFrequentFlyerNumberEnpoint, passengerId);
		RequestSpecification requestSpec = RestAssured.with().header("token", TravelMobileBaseTest.token)
				.header("content-type", "application/json").body(jsonBody);
		Response response = new RestUtils().executePOST(requestSpec, endpointURL);
		return response;
	}

	/**
	 * This method will construct the json body for add freqent flyer number api
	 * 
	 * @param airlineDetails
	 * @param name
	 * @return String
	 * @throws Exception
	 */
	public String createBodyForFrequentFlyerNumber(Map<String, Object> airlineDetails, String name) throws Exception {
		String progType = "AIRLINE";
		Map<String, Object> programTypeDetails = new LinkedHashMap<String, Object>();
		programTypeDetails.put("description", progType);
		programTypeDetails.put("id", 1);

		Map<Object, Object> frequentFlyerBody = new LinkedHashMap<Object, Object>();

		frequentFlyerBody.put("frequentCategory", airlineDetails);
		frequentFlyerBody.put("frequentNumType", programTypeDetails);
		frequentFlyerBody.put("frequentNumber", name);

		String jsonBody = Utilities.createJsonBodyFromMap(frequentFlyerBody);

		return jsonBody;

	}

	/**
	 * This method will create json body for Edit Freq Flyer Number
	 * 
	 * @param airlineDetails
	 * @param freqFlyerId
	 * @param name
	 * @return String
	 * @throws Exception
	 */
	public String createBodyForEditFrequentFlyerNumber(Map<String, Object> airlineDetails, String freqFlyerId,
			String name) throws Exception {
		String progType = "AIRLINE";
		Map<String, Object> programTypeDetails = new LinkedHashMap<String, Object>();
		programTypeDetails.put("description", progType);
		programTypeDetails.put("id", 1);

		Map<Object, Object> frequentFlyerBody = new LinkedHashMap<Object, Object>();
		frequentFlyerBody.put("frequentId", Integer.parseInt(freqFlyerId));
		frequentFlyerBody.put("frequentCategory", airlineDetails);
		frequentFlyerBody.put("frequentNumType", programTypeDetails);
		frequentFlyerBody.put("frequentNumber", name);

		String jsonBody = Utilities.createJsonBodyFromMap(frequentFlyerBody);

		return jsonBody;

	}

	/**
	 * This method will edit the Frequent Flyer Number
	 * 
	 * @param passengerId
	 * @param freqFlyerId
	 * @param jsonBody
	 * @return Response
	 * @throws Exception
	 */
	public Response editFrequentFlyerNumber(String passengerId, String freqFlyerId, String jsonBody) throws Exception {
		String endpointURL = TravelMobileBaseTest.baseUrl
				+ String.format(FrequentFlyerNumberEndpoints.editFrequentFlyerNumberEnpoint, passengerId, freqFlyerId);
		RequestSpecification requestSpec = RestAssured.with().header("token", TravelMobileBaseTest.token)
				.header("content-type", "application/json").body(jsonBody);
		Response response = new RestUtils().executePUT(requestSpec, endpointURL);
		return response;
	}

	/**
	 * This method will delete the Frequent flyer number
	 * 
	 * @param passengerId
	 * @param freqFlyerId
	 * @return Response
	 * @throws Exception
	 */
	public Response deleteFrequentFlyerNumber(String passengerId, String freqFlyerId) throws Exception {
		String endpointURL = TravelMobileBaseTest.baseUrl + String
				.format(FrequentFlyerNumberEndpoints.deleteFrequentFlyerNumberEnpoint, passengerId, freqFlyerId);
		RequestSpecification requestSpec = RestAssured.with().header("token", TravelMobileBaseTest.token)
				.header("content-type", "application/json");
		Response response = new RestUtils().executeDELETE(requestSpec, endpointURL);
		return response;
	}

	/**
	 * This method will construct the body for missing dto in frequent flyer number
	 * tests
	 * 
	 * @param dtoBodyMap
	 * @param testScenario
	 * @return
	 */
	public Map<Object, Object> constructBodyForMissingDTOFreqFlyerNumberTests(Map<Object, Object> dtoBodyMap,
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
	 * This method will construct the body for invalid data frequent flyer number
	 * tests
	 * 
	 * @param dtoBodyMap
	 * @param testScenario
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<Object, Object> constructBodyForFreqFlyerNumberWithInvalidData(Map<Object, Object> dtoBodyMap,
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
	 * This method will return the list of the Frequent Flyer ids
	 * 
	 * @param passengerEmail
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getListOfFrequentFlyerIds(String passengerEmail) throws Exception {
		Response userResponse = getUserInfo(passengerEmail);
		List<Integer> freqFlyerIds = (List<Integer>) new RestUtils().getValueFromResponse(userResponse,
				"content.frequentFlyerDTOs[*].frequentId");
		return freqFlyerIds;
	}

	/**
	 * This method will delete all Frequent Flyer Number for specified passenger
	 * email
	 * 
	 * @param passengerEmail
	 * @throws Exception
	 */
	public void deleteAllFrequentFlyerNumbers(String passengerEmail) throws Exception {
		List<Integer> freqFlyerIds = getListOfFrequentFlyerIds(passengerEmail);
		Response userResponse = getUserInfo(passengerEmail);
		String passengerId = String.valueOf(new RestUtils().getValueFromResponse(userResponse, "content.passengerId"));
		for (int freFlyerId : freqFlyerIds) {
			Response response = deleteFrequentFlyerNumber(passengerId, String.valueOf(freFlyerId));
			validateResponseToContinueTest(response, 200, "Unable to Delete Freq Flyer Number with id " + freFlyerId,
					true);

			String expectedStatus = "SUCCESS";
			String actualStatus = String.valueOf(new RestUtils().getValueFromResponse(response, "status"));
			if (!expectedStatus.equals(actualStatus)) {
				throw new Exception("Unable to delete Frequent Flyer Number.");
			}
		}

	}
}
