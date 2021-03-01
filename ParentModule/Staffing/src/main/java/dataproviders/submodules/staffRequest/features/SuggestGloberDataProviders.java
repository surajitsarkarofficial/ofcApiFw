package dataproviders.submodules.staffRequest.features;

import org.testng.annotations.DataProvider;

import dataproviders.StaffingDataProviders;
import properties.StaffingProperties;

public class SuggestGloberDataProviders extends StaffingDataProviders {
	
	/**
	 * This dataprovider will get valid users for assigning STG
	 * @return Object
	 * @throws Exception
	 * @author akshata.dongare
	 */
	@DataProvider(name = "AssignSTGValidUser")
	public Object[][] getAssignSTGValidData() throws Exception {
		String xmlInputStream = StaffingProperties.dataProviderPath +"/staffRequest/features/suggestGlober/assignSTGValidUser.xml";
		String xpathExpression = "/dataObjects/dataObject";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}

	/**
	 * This dataprovider will get valid users for assigning STG
	 * @return Object
	 * @throws Exception
	 * @author akshata.dongare
	 */
	@DataProvider(name = "AssignSTGInvalidUser")
	public Object[][] getAssignSTGInvalidData() throws Exception {
		String xmlInputStream = StaffingProperties.dataProviderPath +"/staffRequest/features/suggestGlober/assignSTGInvalidUser.xml";
		String xpathExpression = "/dataObjects/dataObject";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}

}
