package dataproviders;

import org.testng.annotations.DataProvider;

import properties.StaffingProperties;

public class StaffingDataProviders extends GlowDataProviders{	
	/**
	 * This data provider returns the User lists with All role
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "usersWithAllRoles")
	public Object[][] usersWithAllRoles() throws Exception {
		String xmlInputStream = StaffingProperties.dataProviderPath + "/usersWithAllRoles.xml";
		String xpathExpression = "/dataObjects/dataObject";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}
	/**
	 * This method will return the combined data of xml and Database
	 * @param dataFromXml
	 * @param dataFromDB
	 * @return
	 */
	
	protected static Object[][] combineDataOfXmlAndDataBase(Object[][] dataFromXml,Object[][] dataFromDB){
		Object[][] data = new Object[dataFromXml.length + dataFromDB.length][1];
		for (int i = 0,dataFromDBIndex = 0; i < data.length; i++) {
			if (i < dataFromXml.length) {
				data[i][0] = dataFromXml[i][0];
			} else {
				data[i][0] = dataFromDB[dataFromDBIndex++][0];
			}
		}
		return data;
	}
	
	/**
	 * This data provider will return gatekeeper valid user 
	 * @return {@link Object}
	 * @throws Exception
	 * @author akshata.dongare
	 */
	@DataProvider(name = "gatekeeperUser")
	public Object[][] getGatekeeperUserData() throws Exception {
		String xmlInputStream = StaffingProperties.dataProviderPath +"/gatekeeperUser.xml";
		String xpathExpression = "/dataObjects/dataObject";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}
	
}
