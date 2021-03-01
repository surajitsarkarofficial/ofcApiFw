package dataproviders;

import org.testng.annotations.DataProvider;

import properties.GlobantPodsProperties;

/**
 * @author ankita.manekar
 *
 */
/**
 * This data provider will return data for Authorization details
 */
public class GlobantPodsDataproviders extends GlowDataProviders {

	/**
	 * This data provider will return data for Authorization details
	 * 
	 * @return buildDataProviderObjectFromXML
	 * @throws Exception
	 */
	@DataProvider(name = "userData")
	public Object[][] userData() throws Exception {
		String xmlInputStream = GlobantPodsProperties.dataProviderPath + "/features/GlobantPodsUserData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}

	/**
	 * This data provider will return data for Schema Validation
	 * 
	 * @return buildDataProviderObjectFromXML
	 * @throws Exception
	 */
	@DataProvider(name = "schemaData")
	public Object[][] schemaData() throws Exception {
		String xmlInputStream = GlobantPodsProperties.dataProviderPath + "/features/SchemaValidationData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);

	}
}
