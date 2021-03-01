package dataproviders.submodules.hotelLoyaltyPrograms;

import org.testng.annotations.DataProvider;

import dataproviders.TravelMobileDataProviders;
import properties.TravelMobileProperties;

public class HotelLoyaltyProgramsDataproviders extends TravelMobileDataProviders {

	/**
	 * This data provider returns data to validate scenarios for add hotel loyalty
	 * programs for missing dto
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "addHotelLoyaltyProgramWithMissingDTO")
	public static Object[][] addHotelLoyaltyProgramWithMissingDTO() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath
				+ "/hotelLoyaltyPrograms/AddHotelLoyaltyProgramWithMissingDTO.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] freqFlyerNumberDataObjects = buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);
		return freqFlyerNumberDataObjects;
	}
	
	/**
	 * This data provider returns data to validate scenarios for add hotel loyalty
	 * program with invalid data
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "addHotelLoyaltyProgramForInvalidData")
	public static Object[][] addHotelLoyaltyProgramForInvalidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath
				+ "/hotelLoyaltyPrograms/AddHotelLoyaltyProgramWithInvalidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] freqFlyerNumberDataObjects = buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);
		return freqFlyerNumberDataObjects;
	}
	
	/**
	 * This data provider returns data to validate scenarios for update freq flyer
	 * numbers for missing dto
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "updateHotelLoyaltyProgramWithMissingDTO")
	public static Object[][] updateHotelLoyaltyProgramWithMissingDTO() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath
				+ "/hotelLoyaltyPrograms/UpdateHotelLoyaltyProgramWithMissingDTO.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] freqFlyerNumberDataObjects = buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);
		return freqFlyerNumberDataObjects;
	}

	/**
	 * This data provider returns data to validate scenarios for update hotel loyalty
	 * program with invalid data
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "updateHotelLoyaltyProgramForInvalidData")
	public static Object[][] updateHotelLoyaltyProgramForInvalidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath
				+ "/hotelLoyaltyPrograms/UpdateHotelLoyaltyProgramWithInvalidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] freqFlyerNumberDataObjects = buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);
		return freqFlyerNumberDataObjects;
	}
	
	/**
	 * This data provider returns data to validate scenarios for delete hotel loyalty
	 * program for invalid data
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "deleteHotelLoyaltyProgramWithInvalidData")
	public static Object[][] deleteHotelLoyaltyProgramWithInvalidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath
				+ "/hotelLoyaltyPrograms/DeleteHotelLoyaltyProgramWithInvalidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] freqFlyerNumberDataObjects = buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);
		return freqFlyerNumberDataObjects;
	}

	
}
