package dataproviders.submodules.interview.features;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;

import constants.submodules.interview.InterviewConstants;
import database.submodules.gatekeepers.features.SearchGatekeepersDBHelper;
import database.submodules.interview.InterviewDBHelper;
import dataproviders.submodules.interview.InterviewDataProvider;
import utils.StaffingUtilities;
import utils.Utilities;

/**
 * 
 * @author deepakkumar.hadiya
 *
 */

public class CreateInterviewDataProvider extends InterviewDataProvider implements InterviewConstants{

	/**
	 * This data provider can be used in positive scenario for creating interview request
	 * @return {@link Object}
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@DataProvider(name="validTestData")
	public Object[][] getTestDataForPositiveScenario() throws Exception{
		Object[][] dataFromXml = null;
		
		String xmlFilePath=interviewDataProviderPath+"/CreateInterviewData.xml";
		String xpathExpression = ("//dataObject[@type='positive']");
		dataFromXml=buildDataProviderObjectFromXmlAsMap(xmlFilePath, xpathExpression);
		for(int j=0;j<dataFromXml.length;j++) {
			((Map<Object,Object>)dataFromXml[j][0]).put(JOB_APPLICATION_ID, RandomStringUtils.random(15, false, true));
			String date=Utilities.getFutureDate(DATABASE_DATE_FORMAT, Utilities.getRandomNumberBetween(1, 180));
			((Map<Object,Object>)dataFromXml[j][0]).put(INTERVIEW_DATE, StaffingUtilities.convertDateIntoMilliSecond(date, DATABASE_DATE_FORMAT));
			String randomLastName=RandomStringUtils.random(Utilities.getRandomNumberBetween(5,15),true, false);
			((Map<Object,Object>)dataFromXml[j][0]).put(CANDIDATE_NAME, "Automation "+randomLastName);
			((Map<Object,Object>)dataFromXml[j][0]).put(CANDIDATE_EMAIL, "hadiyadeepak5050@gmail.com");
		}
		int totalDataFromDB=7;
		
		InterviewDBHelper interviewDBHelper=new InterviewDBHelper(); 
		List<String> recruiterIds = interviewDBHelper.getGloberIds(totalDataFromDB);
		List<String> applyPositions = interviewDBHelper.getPositionIds(totalDataFromDB);
		List<String> locations = interviewDBHelper.getLocationNames(totalDataFromDB);
		List<Map<Object,Object>> dataList=new ArrayList<Map<Object,Object>>();
		for(int i=0;i<totalDataFromDB;i++) {
			String applySeniority=interviewDBHelper.getSeniorityId(applyPositions.get(i));
			if(!applySeniority.contains("not available")) {
			Map<Object,Object> dataMap=new HashMap<>();
			dataMap.put(RECRUITER_ID, recruiterIds.get(i));
			dataMap.put(RECRUITER_USERNAME, interviewDBHelper.getGloberUsername(recruiterIds.get(i)));
			dataMap.put(CANDIDATE_GLOBAL_ID, Utilities.getRandomNumberBetween(0, 10000));
			dataMap.put(HR_FEEDBACK, RandomStringUtils.random(Utilities.getRandomNumberBetween(20, 150), true, false));
			dataMap.put(APPLY_POSITION, applyPositions.get(i));
			dataMap.put(APPLY_SENIORITY, applySeniority);
			String dateFormat="E MMMM dd yyyy HH:mm:ss",date=Utilities.getFutureDate(dateFormat, Utilities.getRandomNumberBetween(0, 180));
			dataMap.put(INTERVIEW_DATE, StaffingUtilities.convertDateIntoMilliSecond(date, dateFormat));
			dataMap.put(LOCATION, locations.get(i));
			String randomLastName=RandomStringUtils.random(Utilities.getRandomNumberBetween(5,15),true, false);
			dataMap.put(CANDIDATE_NAME, "Automation "+randomLastName);
			dataMap.put(INTERVIEW_TYPE, interviewDBHelper.getInterviewType());
			dataMap.put(CURRICULUM, "http://ssff.com/"+Utilities.getRandomNumberBetween(1, 10000));
			dataMap.put(ENGLISH_LEVEL, interviewDBHelper.getSkillLevel());
			dataMap.put(CANDIDATE_EMAIL, "hadiyadeepak5050@gmail.com"); 
			dataMap.put(DISCARD_REASON, RandomStringUtils.random(Utilities.getRandomNumberBetween(0,20),true, false));
			dataMap.put(HEADER_KEY, TOKEN);
			dataMap.put(HEADER_VALUE, "ankita.jog");
			dataMap.put(JOB_APPLICATION_ID, RandomStringUtils.random(15, false, true));
			dataList.add(dataMap);
			}
		}
		
		Object[][] dataFromDB = new Object[dataList.size()][1];
		for(int j=0;j<dataFromDB.length;j++) {
			dataFromDB[j][0]=dataList.get(j);
		}
		return combineDataOfXmlAndDataBase(dataFromXml, dataFromDB);
	}
	
	/**
	 * This data provider can be used in negative scenario for creating interview request with valid test data
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@DataProvider(name="valid_TestData_With_Single_Iteration")
	public Object[][] getValidTestDataForNegativeScenario() throws Exception{
		String xmlFilePath=interviewDataProviderPath+"/CreateInterviewData.xml";
		String xpathExpression = ("//dataObject[@type='negative' and @data='valid']");
		Object[][] dataFromXml = buildDataProviderObjectFromXmlAsMap(xmlFilePath, xpathExpression);
		for(int j=0;j<dataFromXml.length;j++) {
			((Map<Object,Object>)dataFromXml[j][0]).put(JOB_APPLICATION_ID, RandomStringUtils.random(15, false, true));
		}
		return dataFromXml;
	}
	
	/**
	 * This data provider can be used in negative scenario for creating interview request using json body with invalid parameter key
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@DataProvider(name="invalidKey_for_JSON_Body_Parameter")
	public Object[][] getInvalidKeyNameOfJsonBodyParameterForNegativeScenario() throws Exception{
		String xmlFilePath=interviewDataProviderPath+"/CreateInterviewData.xml";
		String xpathExpression = ("//dataObject[@type='negative' and @data='invalidBodyParameterKey']");
		Object[][] dataFromXml = buildDataProviderObjectFromXmlAsMap(xmlFilePath, xpathExpression);
		for(int j=0;j<dataFromXml.length;j++) {
			((Map<Object,Object>)dataFromXml[j][0]).put(JOB_APPLICATION_ID, RandomStringUtils.random(15, false, true));
		}
		return dataFromXml;
	}
	
	/**
	 * This data provider can be used in negative scenario for creating interview request using json body with invalid parameter value 
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@DataProvider(name="invalidParameterValue_for_JSON_Body")
	public Object[][] getInvalidValueOfJsonBodyParameterKeyForNegativeScenario() throws Exception{
		String xmlFilePath=interviewDataProviderPath+"/CreateInterviewData.xml";
		String xpathExpression = ("//dataObject[@type='negative' and @data='invalidBodyParameterValue']");
		Object[][] dataFromXml = buildDataProviderObjectFromXmlAsMap(xmlFilePath, xpathExpression);
		for(int j=0;j<dataFromXml.length;j++) {
			((Map<Object,Object>)dataFromXml[j][0]).put(JOB_APPLICATION_ID, RandomStringUtils.random(15, false, true));
		}
		return dataFromXml;
	}
	
	/**
	 * This data provider can be used in negative scenario with invalid header key
	 * for creating interview request
	 * 
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	@DataProvider(name = "invalidHeaderKey")
	public Object[][] getTestDataFor_invalidHeaderKey() throws Exception {
		String xmlFilePath = interviewDataProviderPath+"/CreateInterviewData.xml";
		String xpathExpression = ("//dataObject[@type='negative' and @invalidProperty='HeaderKey']");
		Object[][] dataFromXml = buildDataProviderObjectFromXmlAsMap(xmlFilePath, xpathExpression);
		for(int j=0;j<dataFromXml.length;j++) {
			((Map<Object,Object>)dataFromXml[j][0]).put(JOB_APPLICATION_ID, RandomStringUtils.random(15, false, true));
		}
		return dataFromXml;
	}

	
	/**
	 * This data provider can be used in negative scenario with invalid header value
	 * for creating interview request
	 * 
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	@DataProvider(name = "invalidHeaderValue")
	public Object[][] getTestDataFor_invalidHeaderValue() throws Exception {
		String xmlFilePath = interviewDataProviderPath+"/CreateInterviewData.xml";
		String xpathExpression = ("//dataObject[@type='negative' and @invalidProperty='HeaderValue']");
		Object[][] dataFromXml = buildDataProviderObjectFromXmlAsMap(xmlFilePath, xpathExpression);
		for(int j=0;j<dataFromXml.length;j++) {
			((Map<Object,Object>)dataFromXml[j][0]).put(JOB_APPLICATION_ID, RandomStringUtils.random(15, false, true));
		}
		return dataFromXml;
	}
	
	/**
	 * This data provider can be used in positive scenario for creating interview request for all locations
	 * @return Object
	 * @throws Exception
	 */
	@DataProvider(name="validTestDataForAllLocations")
	public Object[][] getTestDataForAllLoctions() throws Exception{
		
		SearchGatekeepersDBHelper db = new SearchGatekeepersDBHelper();
		List<String> positions = db.getPositionIds("ALL");
		List<String> locations = db.getLocationNames("ALL");
		Object[][] dataFromDB = new Object[locations.size()][1];	
		System.out.println(locations.size());
		InterviewDBHelper interviewDBHelper=new InterviewDBHelper(); 
		List<String> recruiterIds = interviewDBHelper.getGloberIds(locations.size());
		List<Map<Object,Object>> dataList=new ArrayList<Map<Object,Object>>();
		for(int i=0;i<locations.size();i++) {
			System.out.println(i);
			String position = positions.get(Utilities.getRandomNumberBetween(0, positions.size()-1));
			String applySeniority=interviewDBHelper.getSeniorityId(position);
			Map<Object,Object> dataMap=new HashMap<>();
			dataMap.put(RECRUITER_ID, recruiterIds.get(i));
			dataMap.put(RECRUITER_USERNAME, interviewDBHelper.getGloberUsername(recruiterIds.get(i)));
			dataMap.put(CANDIDATE_GLOBAL_ID, Utilities.getRandomNumberBetween(0, 10000));
			dataMap.put(HR_FEEDBACK, RandomStringUtils.random(Utilities.getRandomNumberBetween(20, 150), true, false));
			dataMap.put(APPLY_POSITION, position);
			dataMap.put(APPLY_SENIORITY, applySeniority);
			String dateFormat="E MMMM dd yyyy HH:mm:ss",date=Utilities.getFutureDate(dateFormat, Utilities.getRandomNumberBetween(0, 180));
			dataMap.put(INTERVIEW_DATE, StaffingUtilities.convertDateIntoMilliSecond(date, dateFormat));
			dataMap.put(LOCATION, locations.get(i));
			String randomLastName=RandomStringUtils.random(Utilities.getRandomNumberBetween(5,15),true, false);
			dataMap.put(CANDIDATE_NAME, "Automation "+randomLastName);
			dataMap.put(INTERVIEW_TYPE, interviewDBHelper.getInterviewType());
			dataMap.put(CURRICULUM, "http://ssff.com/"+Utilities.getRandomNumberBetween(1, 10000));
			dataMap.put(ENGLISH_LEVEL, interviewDBHelper.getSkillLevel());
			dataMap.put(CANDIDATE_EMAIL, "hadiyadeepak5050@gmail.com"); 
			dataMap.put(DISCARD_REASON, RandomStringUtils.random(Utilities.getRandomNumberBetween(0,20),true, false));
			dataMap.put(HEADER_KEY, TOKEN);
			dataMap.put(HEADER_VALUE, "ankita.jog");
			dataMap.put(JOB_APPLICATION_ID, RandomStringUtils.random(15, false, true));
			dataList.add(dataMap);
			dataFromDB[i][0]=dataMap;
		}
		return dataFromDB;
	}
	
	/**
	 * This data provider can be used in positive scenario for creating interview request for all positions
	 * @return Object
	 * @throws Exception
	 */
	@DataProvider(name="validTestDataForAllPositions")
	public Object[][] getTestDataForAllPositions() throws Exception{
		
		SearchGatekeepersDBHelper db = new SearchGatekeepersDBHelper();
		List<String> positions = db.getPositionIds("ALL");
		List<String> locations = db.getLocationNames("ALL");
		Object[][] dataFromDB = new Object[positions.size()][1];	

		InterviewDBHelper interviewDBHelper=new InterviewDBHelper(); 
		List<String> recruiterIds = interviewDBHelper.getGloberIds(positions.size());
		List<Map<Object,Object>> dataList=new ArrayList<Map<Object,Object>>();
		for(int i=0;i<positions.size();i++) {
			System.out.println(i);
			String location = locations.get(Utilities.getRandomNumberBetween(0, locations.size()-1));
			String position = positions.get(i);
			String applySeniority=interviewDBHelper.getSeniorityId(position);
			Map<Object,Object> dataMap=new HashMap<>();
			dataMap.put(RECRUITER_ID, recruiterIds.get(i));
			dataMap.put(RECRUITER_USERNAME, interviewDBHelper.getGloberUsername(recruiterIds.get(i)));
			dataMap.put(CANDIDATE_GLOBAL_ID, Utilities.getRandomNumberBetween(0, 10000));
			dataMap.put(HR_FEEDBACK, RandomStringUtils.random(Utilities.getRandomNumberBetween(20, 150), true, false));
			dataMap.put(APPLY_POSITION, position);
			dataMap.put(APPLY_SENIORITY, applySeniority);
			String dateFormat="E MMMM dd yyyy HH:mm:ss",date=Utilities.getFutureDate(dateFormat, Utilities.getRandomNumberBetween(0, 180));
			dataMap.put(INTERVIEW_DATE, StaffingUtilities.convertDateIntoMilliSecond(date, dateFormat));
			dataMap.put(LOCATION, location);
			String randomLastName=RandomStringUtils.random(Utilities.getRandomNumberBetween(5,15),true, false);
			dataMap.put(CANDIDATE_NAME, "Automation "+randomLastName);
			dataMap.put(INTERVIEW_TYPE, interviewDBHelper.getInterviewType());
			dataMap.put(CURRICULUM, "http://ssff.com/"+Utilities.getRandomNumberBetween(1, 10000));
			dataMap.put(ENGLISH_LEVEL, interviewDBHelper.getSkillLevel());
			dataMap.put(CANDIDATE_EMAIL, "hadiyadeepak5050@gmail.com"); 
			dataMap.put(DISCARD_REASON, RandomStringUtils.random(Utilities.getRandomNumberBetween(0,20),true, false));
			dataMap.put(HEADER_KEY, TOKEN);
			dataMap.put(HEADER_VALUE, "ankita.jog");
			dataMap.put(JOB_APPLICATION_ID, RandomStringUtils.random(15, false, true));
			dataList.add(dataMap);
			dataFromDB[i][0]=dataMap;
		}
		return dataFromDB;
	}
}
