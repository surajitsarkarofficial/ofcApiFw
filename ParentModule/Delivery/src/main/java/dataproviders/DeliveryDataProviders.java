package dataproviders;

import org.testng.annotations.DataProvider;

import properties.DeliveryProperties;

/**
 * @author imran.khan
 *
 */

public class DeliveryDataProviders extends GlowDataProviders{
	/**
	 * This data provider will return data for PM roles with Id
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "pmRolDetailsWithId")
	public Object[][] pmRolDetailsWithId() throws Exception {
		String xmlInputStream = DeliveryProperties.dataProviderPath + "pmRolDetailsWithId.xml";
		String xpathExpression = "/dataObjects/dataObject";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}
	
	/**
	 * This data provider will return data for DD roles with Id and UserName
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "ddRolDetailsWithId")
	public Object[][] ddRolDetailsWithId() throws Exception {
		String xmlInputStream = DeliveryProperties.dataProviderPath + "ddRolDetailsWithId.xml";
		String xpathExpression = "/dataObjects/dataObject";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}
	
	/**
	 * This data provider will return data for PM and DD roles with Id and UserName
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "pmDdRolDetailsWithId")
	public Object[][] pmDdRolDetailsWithId() throws Exception {
		String xmlInputStream = DeliveryProperties.dataProviderPath + "pmDdRolDetailsWithId.xml";
		String xpathExpression = "/dataObjects/dataObject";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}
	
	/***
	 * This data provider will return data for PM Rajat Mittal with Id and UserName
	 * 
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "pmRolDetailsRajatMittal")
	public Object[][] pmRolDetailsRajatMittal() throws Exception {
		String xmlInputStream = DeliveryProperties.dataProviderPath + "pmRolDetailsRajatMittal.xml";
		String xpathExpression = "/dataObjects/dataObject";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}
	
	/***
	 * This data provider will return data for DD Carla Veneziano with Id and UserName
	 * 
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "ddRolDetailsCarlaVeneziano")
	public Object[][] ddRolDetailsCarlaVeneziano() throws Exception {
		String xmlInputStream = DeliveryProperties.dataProviderPath + "ddRolDetailsCarlaVeneziano.xml";
		String xpathExpression = "/dataObjects/dataObject";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}
	
	/***
	 * This data provider will return data for DD Carolina Risotto with Id and UserName
	 * 
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "ddRolDetailsCarolinaRisotto")
	public Object[][] ddRolDetailsCarolinaRisotto() throws Exception {
		String xmlInputStream = DeliveryProperties.dataProviderPath + "ddRolDetailsCarolinaRisotto.xml";
		String xpathExpression = "/dataObjects/dataObject";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}	
}
