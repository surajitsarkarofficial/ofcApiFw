package dataproviders.myTeam;

import org.testng.annotations.DataProvider;

import dataproviders.DeliveryDataProviders;
import properties.DeliveryProperties;

/**
 * @author imran.khan
 *
 */

public class MyTeamDataProviders extends DeliveryDataProviders{
	
	/**
	 * This data provider will return data for PM Ocampo with Id and UserName
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "pmRolDetailsOcampo")
	public Object[][] pmRolDetailsOcampo() throws Exception {
		String xmlInputStream = DeliveryProperties.dataProviderPath + "pmRolDetailsOcampo.xml";
		String xpathExpression = "/dataObjects/dataObject";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}
	
	/**
	 * This data provider will return data for PM Jayant with Id and UserName
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "pmRolDetailsJayantP")
	public Object[][] pmRolDetailsJayantP() throws Exception {
		String xmlInputStream = DeliveryProperties.dataProviderPath + "pmRolDetailsJayantP.xml";
		String xpathExpression = "/dataObjects/dataObject";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}
}
