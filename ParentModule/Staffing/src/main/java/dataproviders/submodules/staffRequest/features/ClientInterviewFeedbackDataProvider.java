package dataproviders.submodules.staffRequest.features;

import org.testng.annotations.DataProvider;

import dataproviders.StaffingDataProviders;
import properties.StaffingProperties;

/**
 * 
 * @author akshata.dongare
 *
 */
public class ClientInterviewFeedbackDataProvider extends StaffingDataProviders{
	/**
	 * This data provider will return invalid user for client Interview feedback
	 * @return {@link Object}
	 * @throws Exception
	 */
	@DataProvider(name = "clientInterviewFeedbackInvalidUser")
	public Object[][] getClientInterviewFeedbackInvalidData() throws Exception {
		String xmlInputStream = StaffingProperties.dataProviderPath +"/staffRequest/features/clientInterviewFeedbackInvalidUser.xml";
		String xpathExpression = "/dataObjects/dataObject";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}
	
	/**
	 * This data provider will return valid user for client Interview feedback
	 * @return {@link Object}
	 * @throws Exception
	 */
	@DataProvider(name = "clientInterviewFeedbackValidUser")
	public Object[][] getClientInterviewFeedbackValidData() throws Exception {
		String xmlInputStream = StaffingProperties.dataProviderPath +"/staffRequest/features/clientInterviewFeedbackValidUsers.xml";
		String xpathExpression = "/dataObjects/dataObject";
		return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
	}
}
