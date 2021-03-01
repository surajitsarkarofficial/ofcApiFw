package dataproviders.submodules.interview.features;

import java.util.List;

import org.testng.annotations.DataProvider;

import constants.submodules.gatekeepers.GatekeepersConstants;
import database.submodules.gatekeepers.features.SearchGatekeepersDBHelper;
import dataproviders.submodules.interview.InterviewDataProvider;

/**
 * @author deepakkumar.hadiya
 */

public class InterviewersDataProviders extends InterviewDataProvider implements GatekeepersConstants{

	/**
	 * This data provider can be used to get gatekeepers
	 * @return {@link Object}
	 * @throws Exception
	 * @author deepakkumar.hadiya
	 */
	@DataProvider(name = "interviewers")
	public Object[][] getTestDataForPositiveScenario() throws Exception {
		String xmlFilePath = interviewDataProviderPath + "/Interviewers.xml";
		String xpathExpression = ("//dataObject[@type='positive']");
		Object[][] dataFromXml = buildDataProviderObjectFromXML(xmlFilePath, xpathExpression);
		int totalDBValue = 20;
		
		SearchGatekeepersDBHelper db = new SearchGatekeepersDBHelper();
		List<String> gatekeepers = db.getGatekeepers(totalDBValue);
		
		Object[][] dataFromDB = new Object[gatekeepers.size()][1];
		for(int j=0;j<dataFromDB.length;j++) {
			dataFromDB[j][0]=gatekeepers.get(j);
		}
		return combineDataOfXmlAndDataBase(dataFromXml, dataFromDB);
	}
	
	/**
	 * This data provider can be used to get only one gatekeeper
	 * @return {@link Object}
	 * @throws Exception
	 * @author deepakkumar.hadiya
	 */
	@DataProvider(name = "single_interviewer")
	public Object[][] getSingleTestDataForPositiveScenario() throws Exception {
		String xmlFilePath = interviewDataProviderPath + "/Interviewers.xml";
		String xpathExpression = ("//dataObject[@type='positive' and @for='single_iteration']");
		return buildDataProviderObjectFromXML(xmlFilePath, xpathExpression);
		}
}
