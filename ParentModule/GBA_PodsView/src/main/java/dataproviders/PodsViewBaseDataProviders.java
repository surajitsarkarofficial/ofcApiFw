package dataproviders;
/**
 * @author rachana.jadhav
 */
import org.testng.annotations.DataProvider;

import properties.PodsViewBaseProperties;

public class PodsViewBaseDataProviders extends GlowDataProviders{
	
	@DataProvider(name = "userData")
	public Object[][] userData() throws Exception {
		String xmlInputStream = new PodsViewBaseProperties().dataProviderPath + "/features/PodsViewUserData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}
	
	@DataProvider(name = "schemaData")
	public Object[][] schemaData() throws Exception {
		String xmlInputStream = new PodsViewBaseProperties().dataProviderPath + "/features/SchemaValidation.xml";
		String xpathExpression = "/dataObjects/dataObject";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}
}
