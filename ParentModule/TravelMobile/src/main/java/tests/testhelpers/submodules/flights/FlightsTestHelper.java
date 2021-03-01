package tests.testhelpers.submodules.flights;

import endpoints.submodules.flights.FlightsEndpoints;
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
public class FlightsTestHelper extends TravelMobileBaseHelper {

	/**
	 * This method will return the response of Search Airport API
	 * @param locationName
	 * @return Response
	 * @throws Exception
	 */
	public Response searchAriport(String locationName) throws Exception {
		String endpointURL = TravelMobileBaseTest.baseUrl
				+ String.format(FlightsEndpoints.airportSearchEndpoint, locationName);
		RequestSpecification requestSpec = RestAssured.with().header("token", TravelMobileBaseTest.token)
				.header("content-type", "application/json");
		Response response = new RestUtils().executeGET(requestSpec, endpointURL);
		return response;
	}
	
	/**
	 * This method will check if the query string is matching the project name or the client name
	 * @param projectName
	 * @param clientName
	 * @param query
	 * @return boolean
	 */
	public boolean isLocationNameMatchingAirportNameFound(String iataCode, String city,String airport,String locationName)
	{
		String[] queryString = locationName.split(" ");
		
		//verify if the query string matches either the iataCode,city or airport name.
		
		boolean isMatched = true;		
		isMatched=isQueryMatchingActualString(queryString, iataCode);
		if(!isMatched)//if the query string did not match the IATA code then verify for city name
		{
			isMatched=isQueryMatchingActualString(queryString, city);
		}
		if(!isMatched)//if the query string did not match the City then verify for airport name
		{
			isMatched=isQueryMatchingActualString(queryString, airport);
		}
		return isMatched;
	}
	
	/**
	 * Will check if the query string matches the actual String
	 * @param queryString
	 * @param actualString
	 * @return boolean
	 */
	public boolean isQueryMatchingActualString(String[] queryString,String actualString)
	{
		int prevIndex=0;
		boolean isMatched=true;
		for(String str : queryString)
		{
			int currIndex= actualString.indexOf(str, prevIndex);
			if(!(currIndex>=prevIndex))
			{
				isMatched = false;
				break;
			}
			prevIndex=currIndex;
			
		}
		return isMatched;
	}
	/**
	 * This method will return the response of Search City API
	 * @param cityName
	 * @return Response
	 * @throws Exception
	 */
	public Response searchCity(String cityName) throws Exception {
		String endpointURL = TravelMobileBaseTest.baseUrl
				+ String.format(FlightsEndpoints.citySearchEndpoint, cityName);
		RequestSpecification requestSpec = RestAssured.with().header("token", TravelMobileBaseTest.token)
				.header("content-type", "application/json");
		Response response = new RestUtils().executeGET(requestSpec, endpointURL);
		return response;
	}
	/**
	 * This method will return the response of Search Country API
	 * @param countryName
	 * @return Response
	 * @throws Exception
	 */
	public Response searchCountry(String countryName) throws Exception {
		String endpointURL = TravelMobileBaseTest.baseUrl
				+ String.format(FlightsEndpoints.countrySearchEndpoint, countryName);
		RequestSpecification requestSpec = RestAssured.with().header("token", TravelMobileBaseTest.token)
				.header("content-type", "application/json");
		Response response = new RestUtils().executeGET(requestSpec, endpointURL);
		return response;
	}

}
