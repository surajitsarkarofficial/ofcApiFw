package dataproviders.submodules.globers;

import org.testng.annotations.DataProvider;

import dataproviders.StaffingDataProviders;
import properties.StaffingProperties;

public class GlobersDataProvider extends StaffingDataProviders{
	/**
	 * This data provider returns the User lists with staffing role
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "staffingUser")
	public Object[][] staffingUser() throws Exception {
		String xmlInputStream = StaffingProperties.dataProviderPath + "/globers/features/GloberHistory/staffingUser.xml";
		String xpathExpression = "/dataObjects/dataObject";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}
}
