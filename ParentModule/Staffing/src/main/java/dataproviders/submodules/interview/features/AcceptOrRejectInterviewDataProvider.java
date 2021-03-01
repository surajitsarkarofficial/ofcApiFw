package dataproviders.submodules.interview.features;

import org.testng.annotations.DataProvider;

import database.submodules.interview.InterviewDBHelper;
import dataproviders.submodules.interview.InterviewDataProvider;

/**
 * 
 * @author deepakkumar.hadiya
 *
 */

public class AcceptOrRejectInterviewDataProvider extends InterviewDataProvider{

	/**
	 * This data provider can be used in positive scenario for accepting or rejecting an interview request
	 * request
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "validTestData")
	public Object[][] getTestDataForPositiveScenario() throws Exception {

		InterviewDBHelper interviewDBHelper = new InterviewDBHelper();
		int totalDataFromDB = 2;
		Object[][] data = new Object[totalDataFromDB][1];
		for (int i = 0; i < totalDataFromDB; i++) {
			data[i][0] = interviewDBHelper.getRandomInterviewDetails(totalDataFromDB);
		}
		return data;
	}
	
	/**
	 * This data provider can be used in negative scenario for accepting or rejecting an interview request
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "validTestdataForNegativeScenario")
	public Object[][] getValidTestDataFor_NegativeScenario() throws Exception {
		InterviewDBHelper interviewDBHelper = new InterviewDBHelper();
		int totalDataFromDB = 1;
		Object[][] data = new Object[1][1];
		data[0][0] = interviewDBHelper.getRandomInterviewDetails(totalDataFromDB);
	
		return data;
	}
}
