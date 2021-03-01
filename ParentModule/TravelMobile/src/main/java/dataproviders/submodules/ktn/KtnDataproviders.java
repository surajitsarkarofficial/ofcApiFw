package dataproviders.submodules.ktn;

import org.testng.annotations.DataProvider;

import dataproviders.TravelMobileDataProviders;
import properties.TravelMobileProperties;

public class KtnDataproviders extends TravelMobileDataProviders {

	/**
	 * This data provider returns data to validate scenarios for add ktn
	 * with invalid data
	 * 
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "addKtnForInvalidData")
	public static Object[][] addKtnForInvalidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath
				+ "/ktn/AddKtnWithInvalidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] freqFlyerNumberDataObjects = buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);
		return freqFlyerNumberDataObjects;
	}
	/**
	 * This data provider returns data to validate scenarios for associate ktn
	 * with invalid data
	 * 
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "associateKtnToTripForInvalidData")
	public static Object[][] associateKtnToTripForInvalidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath
				+ "/ktn/AssociateKtnToTripWithInvalidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] freqFlyerNumberDataObjects = buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);
		return freqFlyerNumberDataObjects;
	}
	
}
