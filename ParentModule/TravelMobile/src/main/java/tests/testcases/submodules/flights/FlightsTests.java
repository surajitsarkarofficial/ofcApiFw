package tests.testcases.submodules.flights;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import dataproviders.submodules.flights.FlightsDataproviders;
import io.restassured.response.Response;
import tests.testcases.TravelMobileBaseTest;
import tests.testhelpers.submodules.flights.FlightsTestHelper;
import utils.RestUtils;

/**
 * 
 * @author surajit.sarkar
 *
 */
public class FlightsTests extends TravelMobileBaseTest {

	@BeforeTest(alwaysRun = true)
	public void beforeTest() {
		subModuleTest = moduleTest.createNode("Flights Tests");
	}

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("Flights Tests");
	}
	
	/**
	 * This method will verify the Search Airport API for valid data
	 * @param dtoMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(priority = 0, dataProvider = "searchAirportWithValidData", dataProviderClass = FlightsDataproviders.class)
	public void verifyAirportSearchWithValidData(Map<Object, Object> dtoMap) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		FlightsTestHelper testHelper = new FlightsTestHelper();
		RestUtils restUtils = new RestUtils();
		String testScenario = dtoMap.get("dataScenario").toString();
		test.log(Status.INFO, String.format("Validating Airport Search API for '%s'", testScenario));

		String query = (String) dtoMap.get("location");

		Response response = testHelper.searchAriport(query);

		validateResponseToContinueTest(response, 200,
				String.format("Unable to perform project search for query '%s'.", query), true);

		String expectedStatus = (String) dtoMap.get("expectedStatus");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = (String) dtoMap.get("expectedMessage");;
		softAssert.assertTrue(
				restUtils.getValueFromResponse(response, "message").toString().contains(expectedResponseMessage),
				String.format("Actual message '%s'  does not contains expected message '%s'",
						restUtils.getValueFromResponse(response, "message"), expectedResponseMessage));

		List<LinkedHashMap<String, Object>> airportDetailsList = (List<LinkedHashMap<String, Object>>) restUtils
				.getValueFromResponse(response, "content");

		int totalProjectDetailsCount = airportDetailsList.size();

		softAssert.assertTrue(totalProjectDetailsCount>0,"Aiport details was not fetched.");

		// verify details for each airport detail
		for (LinkedHashMap<String, Object> airportDetail : airportDetailsList) {

			String iataCode = (String) airportDetail.get("iataCode");
			String cityName = (String) airportDetail.get("city");
			String airportName = (String) airportDetail.get("airportName");
			
			softAssert.assertTrue(!String.valueOf(airportDetail.get("country")).isEmpty(),
					String.format("Country name for airport with iata code '%s' was empty or null.", iataCode));

			softAssert.assertTrue(!String.valueOf(airportDetail.get("fullName")).isEmpty(),
					String.format("Full name for airport with iata code '%s' was empty or null.", iataCode));
			
			softAssert.assertTrue(!String.valueOf(airportDetail.get("engCity")).isEmpty(),
					String.format("English City name for airport with iata code '%s' was empty or null.", iataCode));
			
			softAssert.assertTrue(!String.valueOf(airportDetail.get("engAirportName")).isEmpty(),
					String.format("English Airport name for airport with iata code '%s' was empty or null.", iataCode));

			boolean isMatched = testHelper.isLocationNameMatchingAirportNameFound(iataCode.toLowerCase(),
					cityName.toLowerCase(), airportName.toLowerCase(), query.toLowerCase());

			softAssert.assertTrue(isMatched,
					String.format("Location String '%s' dit not match IATA Code - '%s' or City name - '%s' or Airport name - '%s'.",
							query, iataCode, cityName,airportName));

		}

		softAssert.assertAll();

		test.log(Status.PASS, "Airport Search API verification for valid data was successful.");

	}
	/**
	 * This method will verify the Airport Search API for invalid data
	 * @param dtoMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(priority = 1, dataProvider = "searchAirportWithInValidData", dataProviderClass = FlightsDataproviders.class)
	public void verifyAirportSearchWithInValidData(Map<Object, Object> dtoMap) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		FlightsTestHelper testHelper = new FlightsTestHelper();
		RestUtils restUtils = new RestUtils();
		String testScenario = dtoMap.get("dataScenario").toString();
		test.log(Status.INFO, String.format("Validating Airport Search API for '%s'", testScenario));

		String query = (String) dtoMap.get("location");

		Response response = testHelper.searchAriport(query);

		validateResponseToContinueTest(response, 200,
				String.format("Unable to perform project search for query '%s'.", query), true);

		String expectedStatus = (String) dtoMap.get("expectedStatus");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = (String) dtoMap.get("expectedMessage");;
		softAssert.assertTrue(
				restUtils.getValueFromResponse(response, "message").toString().contains(expectedResponseMessage),
				String.format("Actual message '%s'  does not contains expected message '%s'",
						restUtils.getValueFromResponse(response, "message"), expectedResponseMessage));
		if(!testScenario.contains("Search String is less than 3 characters"))
		{
		List<LinkedHashMap<String, Object>> airportDetailsList = (List<LinkedHashMap<String, Object>>) restUtils
				.getValueFromResponse(response, "content");

		int totalProjectDetailsCount = airportDetailsList.size();

		softAssert.assertTrue(totalProjectDetailsCount==0,"Aiport details was fetched for invalid data.");
		}
		softAssert.assertAll();

		test.log(Status.PASS, "Airport Search API verification for in-valid data was successful.");

	}
	/**
	 * This method will verify the Search City API for valid data
	 * @param dtoMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(priority = 0, dataProvider = "searchCityWithValidData", dataProviderClass = FlightsDataproviders.class)
	public void verifyCitySearchWithValidData(Map<Object, Object> dtoMap) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		FlightsTestHelper testHelper = new FlightsTestHelper();
		RestUtils restUtils = new RestUtils();
		String testScenario = dtoMap.get("dataScenario").toString();
		test.log(Status.INFO, String.format("Validating City Search API for '%s'", testScenario));

		String query = (String) dtoMap.get("city");

		Response response = testHelper.searchCity(query);

		validateResponseToContinueTest(response, 200,
				String.format("Unable to perform City search for query '%s'.", query), true);

		String expectedStatus = (String) dtoMap.get("expectedStatus");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = (String) dtoMap.get("expectedMessage");;
		softAssert.assertTrue(
				restUtils.getValueFromResponse(response, "message").toString().contains(expectedResponseMessage),
				String.format("Actual message '%s'  does not contains expected message '%s'",
						restUtils.getValueFromResponse(response, "message"), expectedResponseMessage));

		List<LinkedHashMap<String, Object>> cityDetailsList = (List<LinkedHashMap<String, Object>>) restUtils
				.getValueFromResponse(response, "content");

		int totalCityDetailsCount = cityDetailsList.size();

		softAssert.assertTrue(totalCityDetailsCount>0,"City details was not fetched.");

		// verify details for each city detail
		for (LinkedHashMap<String, Object> cityDetail : cityDetailsList) {

			String name = (String) cityDetail.get("name");
			String fullName = (String) cityDetail.get("fullName");
			
			LinkedHashMap<String,Object> countryDetail = (LinkedHashMap<String, Object>) cityDetail.get("country");
			
			softAssert.assertTrue(!String.valueOf(cityDetail.get("id")).isEmpty(),
					String.format("City Id for city '%s' was empty or null.", name));
			
			softAssert.assertTrue(!fullName.isEmpty(),
					String.format("Full name for city '%s' was empty or null.", name));

			softAssert.assertTrue(!String.valueOf(countryDetail.get("id")).isEmpty(),
					String.format("Country Id for city '%s' was empty or null.", name));
			
			softAssert.assertTrue(!String.valueOf(countryDetail.get("code")).isEmpty(),
					String.format("Country Code for city '%s' was empty or null.", name));
			
			softAssert.assertTrue(!String.valueOf(countryDetail.get("name")).isEmpty(),
					String.format("Country Name for city '%s' was empty or null.", name));

			boolean isMatched = name.toLowerCase().contains(query.toLowerCase());

			softAssert.assertTrue(isMatched,
					String.format("City String '%s' did not match city name - '%s'.",
							query, name));

		}

		softAssert.assertAll();

		test.log(Status.PASS, "City Search API verification for valid data was successful.");

	}
	/**
	 * This method will verify the Search City API for invalid data
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority = 0, dataProvider = "searchCityWithInValidData", dataProviderClass = FlightsDataproviders.class)
	public void verifyCitySearchWithInValidData(Map<Object, Object> dtoMap) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		FlightsTestHelper testHelper = new FlightsTestHelper();
		RestUtils restUtils = new RestUtils();
		String testScenario = dtoMap.get("dataScenario").toString();
		test.log(Status.INFO, String.format("Validating City Search API for '%s'", testScenario));

		String query = (String) dtoMap.get("city");

		Response response = testHelper.searchCity(query);

		validateResponseToContinueTest(response, 200,
				String.format("Unable to perform City search for query '%s'.", query), true);

		String expectedStatus = (String) dtoMap.get("expectedStatus");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = (String) dtoMap.get("expectedMessage");;
		softAssert.assertTrue(
				restUtils.getValueFromResponse(response, "message").toString().contains(expectedResponseMessage),
				String.format("Actual message '%s'  does not contains expected message '%s'",
						restUtils.getValueFromResponse(response, "message"), expectedResponseMessage));

		softAssert.assertAll();

		test.log(Status.PASS, "City Search API verification for invalid data was successful.");

	}
	
	/**
	 * This method will verify the Search Country API for valid data
	 * @param dtoMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(priority = 0, dataProvider = "searchCountryWithValidData", dataProviderClass = FlightsDataproviders.class)
	public void verifyCountrySearchWithValidData(Map<Object, Object> dtoMap) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		FlightsTestHelper testHelper = new FlightsTestHelper();
		RestUtils restUtils = new RestUtils();
		String testScenario = dtoMap.get("dataScenario").toString();
		test.log(Status.INFO, String.format("Validating Country Search API for '%s'", testScenario));

		String query = (String) dtoMap.get("country");

		Response response = testHelper.searchCountry(query);

		validateResponseToContinueTest(response, 200,
				String.format("Unable to perform Country search for query '%s'.", query), true);

		String expectedStatus = (String) dtoMap.get("expectedStatus");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = (String) dtoMap.get("expectedMessage");;
		softAssert.assertTrue(
				restUtils.getValueFromResponse(response, "message").toString().contains(expectedResponseMessage),
				String.format("Actual message '%s'  does not contains expected message '%s'",
						restUtils.getValueFromResponse(response, "message"), expectedResponseMessage));

		List<LinkedHashMap<String, Object>> countryDetailsList = (List<LinkedHashMap<String, Object>>) restUtils
				.getValueFromResponse(response, "content");

		int totalCountryDetailsCount = countryDetailsList.size();

		softAssert.assertTrue(totalCountryDetailsCount>0,"Country details was not fetched.");

		// verify details for each city detail
		for (LinkedHashMap<String, Object> countryDetail : countryDetailsList) {

			String name = (String) countryDetail.get("name");
			
			softAssert.assertTrue(!String.valueOf(countryDetail.get("id")).isEmpty(),
					String.format("Country Id for country '%s' was empty or null.", name));
			
			softAssert.assertTrue(!String.valueOf(countryDetail.get("code")).isEmpty(),
					String.format("Country Code for country '%s' was empty or null.", name));

			
			boolean isMatched = name.toLowerCase().contains(query.toLowerCase());

			softAssert.assertTrue(isMatched,
					String.format("Country String '%s' did not match Country name - '%s'.",
							query, name));

		}

		softAssert.assertAll();

		test.log(Status.PASS, "Country Search API verification for valid data was successful.");

	}
	/**
	 * This method will verify the Search Country API for invalid data
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority = 0, dataProvider = "searchCountryWithInValidData", dataProviderClass = FlightsDataproviders.class)
	public void verifyCountrySearchWithInValidData(Map<Object, Object> dtoMap) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		FlightsTestHelper testHelper = new FlightsTestHelper();
		RestUtils restUtils = new RestUtils();
		String testScenario = dtoMap.get("dataScenario").toString();
		test.log(Status.INFO, String.format("Validating Country Search API for '%s'", testScenario));

		String query = (String) dtoMap.get("country");

		Response response = testHelper.searchCountry(query);

		validateResponseToContinueTest(response, 200,
				String.format("Unable to perform Country search for query '%s'.", query), true);

		String expectedStatus = (String) dtoMap.get("expectedStatus");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = (String) dtoMap.get("expectedMessage");;
		softAssert.assertTrue(
				restUtils.getValueFromResponse(response, "message").toString().contains(expectedResponseMessage),
				String.format("Actual message '%s'  does not contains expected message '%s'",
						restUtils.getValueFromResponse(response, "message"), expectedResponseMessage));

		softAssert.assertAll();

		test.log(Status.PASS, "Country Search API verification for invalid data was successful.");

	}

}
