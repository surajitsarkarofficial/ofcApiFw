package dataproviders.submodules.project;

import org.testng.annotations.DataProvider;

import dataproviders.TravelMobileDataProviders;
import properties.TravelMobileProperties;

/**
 * 
 * @author surajit.sarkar
 *
 */
public class ProjectDataproviders extends TravelMobileDataProviders{
	
	/**
	 * This data provider will return data for search project with valid data
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "searchProjectWithValidData")
	public static Object[][] searchProjectWithValidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/project/SearchProjectWithValidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] getTripDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);		
		return getTripDataObjects;
	}
	/**
	 * This data provider will return data for search project with in valid data
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "searchProjectWithInValidData")
	public static Object[][] searchProjectWithInValidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/project/SearchProjectWithInValidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] getTripDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);		
		return getTripDataObjects;
	}


}
