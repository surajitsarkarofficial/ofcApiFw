package dataproviders.submodules.profile;

import org.testng.annotations.DataProvider;

import dataproviders.TravelMobileDataProviders;
import properties.TravelMobileProperties;

public class ProfileDataproviders extends TravelMobileDataProviders{
	
	/**
	 * This data provider returns data required for validating get profile Image with valid data
	 * 
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "getProfileImageWithValidData")
	public static Object[][] getProfileImageWithValidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/profile/GetProfileImageWithValidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] getTripDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);		
		return getTripDataObjects;
	}
	
	
	/**
	 * This data provider returns data required for validating get profile Image with invalid data
	 * 
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "getProfileImageWithInvalidData")
	public static Object[][] getProfileImageWithInvalidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/profile/GetProfileImageWithInvalidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] getTripDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);		
		return getTripDataObjects;
	}

}
