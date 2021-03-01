package dataproviders.submodules.frequentFlyerNumbers;

import org.testng.annotations.DataProvider;

import dataproviders.TravelMobileDataProviders;
import properties.TravelMobileProperties;

public class FrequentFlyerNumbersDataproviders extends TravelMobileDataProviders {

	/**
	 * This data provider returns data to validate scenarios for add freq flyer
	 * numbers with invalid data
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "addFreqFlyerNumberForInvalidData")
	public static Object[][] addFreqFlyerNumberForInvalidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath
				+ "/frequentFlyerNumbers/AddFreqFlyerNumberWithInvalidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] freqFlyerNumberDataObjects = buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);
		return freqFlyerNumberDataObjects;
	}

	/**
	 * This data provider returns data to validate scenarios for update freq flyer
	 * numbers with invalid data
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "updateFreqFlyerNumberForInvalidData")
	public static Object[][] updateFreqFlyerNumberForInvalidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath
				+ "/frequentFlyerNumbers/UpdateFreqFlyerNumberWithInvalidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] freqFlyerNumberDataObjects = buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);
		return freqFlyerNumberDataObjects;
	}

	/**
	 * This data provider returns data to validate scenarios for add freq flyer
	 * numbers for missing dto
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "addFreqFlyerNumberWithMissingDTO")
	public static Object[][] addFreqFlyerNumberWithMissingDTO() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath
				+ "/frequentFlyerNumbers/AddFreqFlyerNumberWithMissingDTO.xml";
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
	@DataProvider(name = "updateFreqFlyerNumberWithMissingDTO")
	public static Object[][] updateFreqFlyerNumberWithMissingDTO() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath
				+ "/frequentFlyerNumbers/UpdateFreqFlyerNumberWithMissingDTO.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] freqFlyerNumberDataObjects = buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);
		return freqFlyerNumberDataObjects;
	}

	/**
	 * This data provider returns data to validate scenarios for delete freq flyer
	 * numbers for invalid data
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "deleteFreqFlyerNumberWithInvalidData")
	public static Object[][] deleteFreqFlyerNumberWithInvalidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath
				+ "/frequentFlyerNumbers/DeleteFreqFlyerNumberWithInvalidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] freqFlyerNumberDataObjects = buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);
		return freqFlyerNumberDataObjects;
	}
}
