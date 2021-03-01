package dataproviders.submodules.staffRequest.features;

import org.testng.annotations.DataProvider;

import dataproviders.submodules.staffRequest.StaffRequestDataProviders;
import properties.StaffingProperties;

public class CreateNewPositionDataProviders extends StaffRequestDataProviders{

	/**
	 * This data provider returns the User lists with TL role
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "userListWithTL")
	public Object[][] userListWithTL() throws Exception {
		String xmlInputStream = StaffingProperties.dataProviderPath + "/staffRequest/features/userListWithTL.xml";
		String xpathExpression = "/dataObjects/dataObject";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}
	
	/**
	 * This data provider will return valid users for create SR
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "CreateSRUsingValidUsers")
	public Object[][] getCreateSRData() throws Exception {
		String xmlInputStream = StaffingProperties.dataProviderPath +"/staffRequest/features/createSRValidUsers.xml";
		String xpathExpression = "/dataObjects/dataObject";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}
}