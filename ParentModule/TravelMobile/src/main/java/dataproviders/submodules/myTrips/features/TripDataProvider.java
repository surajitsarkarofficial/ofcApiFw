package dataproviders.submodules.myTrips.features;

import java.util.Map;

import org.testng.annotations.DataProvider;

import dataproviders.submodules.myTrips.MyTripsDataProviders;
import properties.TravelMobileProperties;
import utils.RestUtils;
import utils.Utilities;

public class TripDataProvider extends MyTripsDataProviders{
	
	/**
	 * This data provider returns data required for valid trip creation
	 * 
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "createTripWithValidData")
	public static Object[][] createTripWithValidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/myTrips/features/trips/CreateTripWithValidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] createTripDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);
		int noOfDataSets = createTripDataObjects.length;
		Object[][] newTripDataObjects = new Object[noOfDataSets][1];
		
		//append trip name with time stamp
		for(int i=0;i<noOfDataSets;i++)
		{
			@SuppressWarnings("unchecked")
			Map<Object,Object> tripData = (Map<Object, Object>) createTripDataObjects[i][0];
			String timeStamp = Utilities.getCurrentDateAndTime("MMddyyhhmmss");
			String tripName =tripData.get("tripName")+timeStamp+Utilities.getRandomNumberBetween(10000, 99999);
			tripData.put("tripName",tripName);
			newTripDataObjects[i][0]=tripData;
		} 
		return newTripDataObjects;
	}
	/**
	 * This data provider returns data to validate invalid scenarios for trip creation
	 * 
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "createTripWithInvalidData")
	public static Object[][] createTripWithInvalidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/myTrips/features/trips/CreateTripWithInvalidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] createTripDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);
		int noOfDataSets = createTripDataObjects.length;
		Object[][] newTripDataObjects = new Object[noOfDataSets][1];
		
		//append trip name with time stamp
		String tripName;
		for(int i=0;i<noOfDataSets;i++)
		{
			@SuppressWarnings("unchecked")
			Map<Object,Object> tripData = (Map<Object, Object>) createTripDataObjects[i][0];
			tripName=(String) tripData.get("tripName");
			if(!tripData.get("dataScenario").toString().toLowerCase().contains("empty trip name"))
			{
			String timeStamp = Utilities.getCurrentDateAndTime("MMddyyhhmmss");
			tripName =tripName+timeStamp;
			}
			tripData.put("tripName",tripName);
			newTripDataObjects[i][0]=tripData;
		} 
		return newTripDataObjects;
	}
	/**
	 * This data provider returns data required for validating get trip details negative scenarios
	 * 
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "getTripDetailsWithInvalidData")
	public static Object[][] getTripDetailsWithInvalidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/myTrips/features/trips/GetTripDetailsWithInvalidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] getTripDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);		
		return getTripDataObjects;
	}
	/**
	 * This data provider returns data required for validating get trip details negative scenarios
	 * 
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "updateTripWithInvalidData")
	public static Object[][] updateTripWithInvalidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/myTrips/features/trips/UpdateTripWithInvalidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] getTripDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);		
		return getTripDataObjects;
	}
	
	/**
	 * This data provider returns data required for validating delete trip negative scenarios
	 * 
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "deleteTripWithInvalidData")
	public static Object[][] deleteTripWithInvalidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/myTrips/features/trips/DeleteTripWithInvalidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] getTripDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);		
		return getTripDataObjects;
	}

}
