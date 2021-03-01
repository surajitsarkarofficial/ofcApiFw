package dataproviders.submodules.globers.features;

import org.testng.annotations.DataProvider;

import dataproviders.submodules.globers.GlobersDataProvider;
import properties.StaffingProperties;

public class SoonTobeGlobersDataProvider extends GlobersDataProvider {
	
	/**
	 * This data provider returns the User lists with SR, PM, TD, Recruiting Role
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "userListWithRoles")
	public Object[][] userListWithRoles() throws Exception {
		String xmlInputStream = StaffingProperties.dataProviderPath + "/globers/features/soonTobeGlobers/userListWithRoles.xml";
		String xpathExpression = "/dataObjects/userWithRoles/dataObject";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}
	
	/**
	 * This data provider returns the User lists with Soon to be Globers catagory - Candidate, In Pipe, New Hire
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "userListWithStgCatagory")
	public Object[][] userListWithStgCatagory() throws Exception {
		String xmlInputStream = StaffingProperties.dataProviderPath + "/globers/features/soonTobeGlobers/userListWithStgCatagory.xml";
		String xpathExpression = "/dataObjects/dataObject";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}
}