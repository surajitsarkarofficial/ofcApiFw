package dataproviders.submodules.myTrips.features;


import org.testng.annotations.DataProvider;

import dataproviders.submodules.myTrips.MyTripsDataProviders;
import properties.TravelMobileProperties;

public class PassengerDataProviders extends MyTripsDataProviders {
	
	/**
	 * This data provider returns data required for adding valid passenger to a valid trip
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "addPassengerWithValidData")
	public static Object[][] addPassengerWithValidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/myTrips/features/passengers/AddPassengerWithValidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] addPassengerDataObjects= buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
		return addPassengerDataObjects;
	}
	/**
	 * This data provider returns data required for validating get Passenger info for valid scenarios
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "getPassengerInfoForValidData")
	public static Object[][] getPassengerInfoForValidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/myTrips/features/passengers/GetPassengerInfoForValidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] getTripDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);		
		return getTripDataObjects;
	}
	/**
	 * This data provider returns data required for validating add Passenger for invalid scenarios
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "addPassengerWithInvalidData")
	public static Object[][] addPassengerWithInvalidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/myTrips/features/passengers/AddPassengerWithInvalidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] getTripDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);		
		return getTripDataObjects;
	}
	/**
	 * This data provider returns data required for validating get Passenger info for invalid scenarios
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "getPassengerInfoForInvalidData")
	public static Object[][] getPassengerInfoForInvalidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/myTrips/features/passengers/GetPassengerInfoForInvalidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] getTripDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);		
		return getTripDataObjects;
	}
	/**
	 * This data provider returns data required for validating delete Passenger for invalid scenarios
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "deletePassengerWithInvalidData")
	public static Object[][] deletePassengerWithInvalidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/myTrips/features/passengers/DeletePassengerWithInvalidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] getTripDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);		
		return getTripDataObjects;
	}
	/**
	 * This data provider returns data required for validating replace Passenger info for invalid scenarios
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "getReplacePassengerInfoForInvalidData")
	public static Object[][] getReplacePassengerInfoForInvalidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/myTrips/features/passengers/ReplacePassengerWithInvalidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] getTripDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);		
		return getTripDataObjects;
	}

}
