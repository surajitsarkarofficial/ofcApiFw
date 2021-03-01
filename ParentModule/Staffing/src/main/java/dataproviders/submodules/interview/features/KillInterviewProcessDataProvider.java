package dataproviders.submodules.interview.features;

import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;

import constants.submodules.gatekeepers.GatekeepersConstants;
import constants.submodules.interview.InterviewConstants;
import dataproviders.submodules.interview.InterviewDataProvider;
import utils.StaffingUtilities;
import utils.Utilities;

/**
 * 
 * @author deepakkumar.hadiya
 *
 */

public class KillInterviewProcessDataProvider extends InterviewDataProvider implements InterviewConstants, GatekeepersConstants{

	/**
	 * This data provider can be used in positive scenario for killing interview request
	 * @return Object
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@DataProvider(name="multiTestDataForPositiveScenario")
	public Object[][] getMultiTestDataForPositiveScenario() throws Exception{
		String xmlFilePath=interviewDataProviderPath+"/InterviewE2EData.xml";
		String xpathExpression = ("//dataObject[@type='positive' and @data='valid']");
		Object[][] dataFromXml=buildDataProviderObjectFromXmlAsMap(xmlFilePath, xpathExpression);
		for(int j=0;j<dataFromXml.length;j++) {
			((Map<Object,Object>)dataFromXml[j][0]).put(JOB_APPLICATION_ID, RandomStringUtils.random(15, false, true));
			String date=Utilities.getFutureDate(DATABASE_DATE_FORMAT, Utilities.getRandomNumberBetween(1, 180));
			((Map<Object,Object>)dataFromXml[j][0]).put(INTERVIEW_DATE, StaffingUtilities.convertDateIntoMilliSecond(date, DATABASE_DATE_FORMAT));
			String randomLastName="Automation "+RandomStringUtils.random(Utilities.getRandomNumberBetween(5,8),true, false);
			((Map<Object,Object>)dataFromXml[j][0]).put(CANDIDATE_NAME, randomLastName);
			((Map<Object,Object>)dataFromXml[j][0]).put(CANDIDATE_EMAIL, "hadiyadeepak5050@gmail.com");
			((Map<Object,Object>)dataFromXml[j][0]).put(DISCARD_REASON, RandomStringUtils.random(Utilities.getRandomNumberBetween(8,30),true, false));
		}
		return dataFromXml;
	}
	
	/**
	 * This data provider can be used in positive scenario for single test data
	 * @return Object
	 * @throws Exception 
	 */
	@DataProvider(name="singleTestData",  indices = {0})
	public Object[][] getSingleTestDataForPositiveScenario() throws Exception{
		return getMultiTestDataForPositiveScenario();
	}
	
	/**
	 * This data provider can be used in re-run scenario for creating interview request
	 * @return Object
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@DataProvider(name="testDataForValidParameterValue")
	public Object[][] getTestDataForValidParameterValue() throws Exception{
	
	String xmlFilePath = interviewDataProviderPath + "/KillInterviewProcessData.xml";
	String xpathExpression = ("//dataObject[@type='positive' and @data='validParameterValue']");
	Object[][] positiveParameterData = buildDataProviderObjectFromXmlAsMap(xmlFilePath, xpathExpression);
	Object[][] positiveData=getSingleTestDataForPositiveScenario();
	Object[][] updatedData=new Object[positiveParameterData.length][1];
	for(int i=0;i<updatedData.length;i++) {
		Map<Object,Object> map=new HashedMap<Object, Object>();
		map.putAll((Map<Object,Object>) positiveData[0][0]);
		map.putAll((Map<Object,Object>) positiveParameterData[i][0]);
		updatedData[i][0]=map;
	}
	return updatedData;
	}
	
	/**
	 * This data provider can be used in re-run scenario for creating interview request
	 * @return Object
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@DataProvider(name="testDataForInValidParameterValue")
	public Object[][] getTestDataForInValidParameterValue() throws Exception{
	
	String xmlFilePath = interviewDataProviderPath + "/KillInterviewProcessData.xml";
	String xpathExpression = ("//dataObject[@type='negative' and @data='invalidParameterValue']");
	Object[][] invalidParameterValue = buildDataProviderObjectFromXmlAsMap(xmlFilePath, xpathExpression);
	Object[][] positiveData=getSingleTestDataForPositiveScenario();
	Object[][] updatedData=new Object[invalidParameterValue.length][1];
	for(int i=0;i<updatedData.length;i++) {
		Map<Object,Object> map=new HashedMap<Object, Object>();
		map.put(CANCEL_INTERVIEW, true);
		map.put(PERFORM_NOTIFICATION, false);
		map.putAll((Map<Object,Object>) positiveData[0][0]);
		map.putAll((Map<Object,Object>) invalidParameterValue[i][0]);
		updatedData[i][0]=map;
	}
	return updatedData;
	}
}
