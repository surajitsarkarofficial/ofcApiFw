package dataproviders.submodules.myTrips.features;


import org.testng.annotations.DataProvider;

import dataproviders.submodules.myTrips.MyTripsDataProviders;
import properties.TravelMobileProperties;

public class WatchersDataProvider extends MyTripsDataProviders{
	
	
	
	/**
	 * This data provider returns data required for validating add watcher to trip with invalid data
	 * 
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "addWatcherWithInvalidData")
	public static Object[][] addWatcherWithInvalidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/myTrips/features/trips/AddWatcherWithInvalidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] getTripDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);		
		return getTripDataObjects;
	}
	/**
	 * This data provider returns data required for validating remove watcher with invalid data
	 * 
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "removeWatcherWithInvalidData")
	public static Object[][] removeWatcherWithInvalidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/myTrips/features/trips/RemoveWatcherWithInvalidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] getTripDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);		
		return getTripDataObjects;
	}

}
