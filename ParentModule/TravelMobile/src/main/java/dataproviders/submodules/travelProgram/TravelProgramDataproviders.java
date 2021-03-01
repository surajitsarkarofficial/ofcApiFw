package dataproviders.submodules.travelProgram;


import org.testng.annotations.DataProvider;

import dataproviders.TravelMobileDataProviders;
import properties.TravelMobileProperties;

public class TravelProgramDataproviders extends TravelMobileDataProviders{
	
	/**
	 * This data provider returns data required for validating get travel program with valid data
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "getTravelProgramWithValidData")
	public static Object[][] getTravelProgramWithValidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/travelProgram/GetTravelProgramWithValidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] getTripDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);		
		return getTripDataObjects;
	}
	/**
	 * This data provider returns data required for validating get travel program with invalid data
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "getTravelProgramWithInvalidData")
	public static Object[][] getTravelProgramWithInvalidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/travelProgram/GetTravelProgramWithInvalidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] getTripDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);		
		return getTripDataObjects;
	}
	
}
