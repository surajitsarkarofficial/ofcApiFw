package dataproviders.submodules.staffRequest.features.globalstaffrequest;

import org.testng.annotations.DataProvider;

import dataproviders.submodules.staffRequest.StaffRequestDataProviders;
import properties.StaffingProperties;

public class GlobalStaffRequestDataProvider extends StaffRequestDataProviders{
	/**
	 * This data provider returns the User lists with All role with type Glober
	 * @return {@link Object}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@DataProvider(name = "globerAssignmnentAccessRoleUsers")
	public Object[][] globerAssignmnentAccessRoleUsers() throws Exception {
		String xmlInputStream = StaffingProperties.dataProviderPath + "/globerAssignmnentAccessRoleUsers.xml";
		String xpathExpression = "/dataObjects/globerAssignmentRole/dataObject[@for = 'SuggestAndAssign']";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}
	
	/**
	 * This data provider returns the User lists with All role with type STGs 
	 * @return {@link Object}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@DataProvider(name = "stgAssignmnentAccessRoleUsers")
	public Object[][] stgAssignmnentAccessRoleUsers() throws Exception {
		String xmlInputStream = StaffingProperties.dataProviderPath + "/globerAssignmnentAccessRoleUsers.xml";
		String xpathExpression = "/dataObjects/stgAssignmentRoles/dataObject";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}
	
	/**
	 * This data provider returns the User lists with All role with timeline filters 
	 * @return {@link Object}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@DataProvider(name = "timelineAccessRoleUsers")
	public Object[][] timelineAccessRoleUsers() throws Exception {
		String xmlInputStream = StaffingProperties.dataProviderPath + "/globerAssignmnentAccessRoleUsers.xml";
		String xpathExpression = "/dataObjects/globerAssignmentRole/dataObject[@for = 'Timeline']";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}
	
}
