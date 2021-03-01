package dataproviders.submodules.flights;

import org.testng.annotations.DataProvider;

import dataproviders.TravelMobileDataProviders;
import properties.TravelMobileProperties;

/**
 * 
 * @author surajit.sarkar
 *
 */
public class FlightsDataproviders extends TravelMobileDataProviders{
	
	/**
	 * This data provider will return data for search airport with valid data
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "searchAirportWithValidData")
	public static Object[][] searchAirportWithValidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/flights/SearchAirportWithValidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] getTripDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);		
		return getTripDataObjects;
	}
	/**
	 * This data provider will return data for search airport with in valid data
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "searchAirportWithInValidData")
	public static Object[][] searchAirportWithInValidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/flights/SearchAirportWithInValidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] getTripDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);		
		return getTripDataObjects;
	}
	/**
	 * This data provider will return data for search city with valid data
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "searchCityWithValidData")
	public static Object[][] searchCityWithValidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/flights/SearchCityWithValidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] getTripDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);		
		return getTripDataObjects;
	}
	/**
	 * This data provider will return data for search City with in valid data
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "searchCityWithInValidData")
	public static Object[][] searchCityWithInValidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/flights/SearchCityWithInValidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] getTripDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);		
		return getTripDataObjects;
	}
	/**
	 * This data provider will return data for search Country with valid data
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "searchCountryWithValidData")
	public static Object[][] searchCountryWithValidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/flights/SearchCountryWithValidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] getTripDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);		
		return getTripDataObjects;
	} 
	/**
	 * This data provider will return data for search Country with In valid data
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "searchCountryWithInValidData")
	public static Object[][] searchCountryWithInValidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/flights/SearchCountryWithInValidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] getTripDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);		
		return getTripDataObjects;
	}


}
