package dataproviders.submodules.services;



import org.testng.annotations.DataProvider;

import dataproviders.TravelMobileDataProviders;
import properties.TravelMobileProperties;

public class ServicesDataproviders extends TravelMobileDataProviders {
	
	/**
	 * This data provider returns data required for validating add services for invalid scenarios
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "addServicesWithInvalidData")
	public static Object[][] addServicesWithInvalidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/services/AddServicesWithInvalidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] getTripDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);		
		return getTripDataObjects;
	}

}
