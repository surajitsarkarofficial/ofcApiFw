package dataproviders.submodules.reports.features.handlerandhandlerteam;

import org.testng.annotations.DataProvider;

import dataproviders.submodules.reports.ReportsDataProviders;
import properties.StaffingProperties;

public class HandlerAndHandlerTeamDataProvider extends ReportsDataProviders{

	/**
	 * This data provider returns the User lists with All role
	 * @return {@link ObjectArray}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@DataProvider(name = "globerAssignmnentAccessRoleUsers")
	public Object[][] changeHandlerRoleUsers() throws Exception {
		String xmlInputStream = StaffingProperties.dataProviderPath + "/globerAssignmnentAccessRoleUsers.xml";
		String xpathExpression = "/dataObjects/globerAssignmentRole/dataObject[@for = 'Location']";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}
	
	/**
	 * This data provider returns the User lists with PM and TD role
	 * @return {@link ObjectArray}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@DataProvider(name = "changeHandlerViaPMTDRoleUsers")
	public Object[][] changeHandlerViaPMTDRoleUsers() throws Exception {
		String xmlInputStream = StaffingProperties.dataProviderPath + "/globerAssignmnentAccessRoleUsers.xml";
		String xpathExpression = "/dataObjects/globerAssignmentRole/dataObject[@type = 'PMAndTD']";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}
	
	/**
	 * This data provider returns the User lists with SA and RA role
	 * @return {@link ObjectArray}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@DataProvider(name = "changeHandlerViaSARARoleUsers")
	public Object[][] changeHandlerViaSARARoleUsers() throws Exception {
		String xmlInputStream = StaffingProperties.dataProviderPath + "/globerAssignmnentAccessRoleUsers.xml";
		String xpathExpression = "/dataObjects/globerAssignmentRole/dataObject[@type = 'StaffingAndRecruiting']";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}
}
