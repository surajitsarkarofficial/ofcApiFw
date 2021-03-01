package dataproviders.submodules.interview.features;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;

import constants.submodules.gatekeepers.GatekeepersConstants;
import constants.submodules.interview.InterviewConstants;
import database.submodules.interview.InterviewDBHelper;
import dataproviders.submodules.interview.InterviewDataProvider;
import dto.submodules.interview.TimezoneDTO;
import utils.StaffingUtilities;
import utils.Utilities;

/**
 * 
 * @author deepakkumar.hadiya
 *
 */

public class InterviewE2EDataProvider extends InterviewDataProvider implements InterviewConstants, GatekeepersConstants{

	/**
	 * This data provider can be used in positive scenario for creating interview request
	 * @return Object
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@DataProvider(name="valid_TestData_For_Interview_Requests_E2E_Scenario")
	public Object[][] getTestDataForPositiveScenario() throws Exception{
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
			
		}
		return dataFromXml;
	}
	
	
	/**
	 * This data provider can be used in positive scenario for creating interview request
	 * Here gatekeepers count for interview is more than the count of one batch
	 * @return Object
	 * @throws Exception
	 * @author deepakkumar.hadiya
	 */
	@SuppressWarnings("unchecked")
	@DataProvider(name="valid_TestData_For_Interview_Requests_E2E_Rerun_Scenario")
	public Object[][] getTestDataForPositiveRerunScenario() throws Exception{
		String xmlFilePath=interviewDataProviderPath+"/InterviewE2EData.xml";
		String xpathExpression = ("//dataObject[totalGK>"+TOTAL_GK_FOR_RE_RUN+"]");
		Object[][] dataFromXml=buildDataProviderObjectFromXmlAsMap(xmlFilePath, xpathExpression);
		for(int j=0;j<dataFromXml.length;j++) {
			((Map<Object,Object>)dataFromXml[j][0]).put(JOB_APPLICATION_ID, RandomStringUtils.random(15, false, true));
			String date=Utilities.getFutureDate(DATABASE_DATE_FORMAT, Utilities.getRandomNumberBetween(1, 180));
			((Map<Object,Object>)dataFromXml[j][0]).put(INTERVIEW_DATE, StaffingUtilities.convertDateIntoMilliSecond(date, DATABASE_DATE_FORMAT));
			String randomLastName="Automation "+RandomStringUtils.random(Utilities.getRandomNumberBetween(5,8),true, false);
			((Map<Object,Object>)dataFromXml[j][0]).put(CANDIDATE_NAME, randomLastName);
			((Map<Object,Object>)dataFromXml[j][0]).put(CANDIDATE_EMAIL, "hadiyadeepak5050@gmail.com");
			
		}
		return dataFromXml;
	}
	
	/**
	 * This data provider can be used in re-run scenario for creating interview request
	 * @return Object
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@DataProvider(name="rerun_Scenario_Data_For_Interview_Requests")
	public Object[][] getValidTestDataForRerunScenario() throws Exception{
		Object[][] dataFromXml = null;
		
		String xmlFilePath=interviewDataProviderPath+"/InterviewE2EData.xml";
		String xpathExpression = ("//dataObject[@type='positive' and @data='valid' and @rerun='true']");
		dataFromXml=buildDataProviderObjectFromXmlAsMap(xmlFilePath, xpathExpression);
		for(int j=0;j<dataFromXml.length;j++) {
			((Map<Object,Object>)dataFromXml[j][0]).put(JOB_APPLICATION_ID, RandomStringUtils.random(15, false, true));
			String date=Utilities.getFutureDate(DATABASE_DATE_FORMAT, Utilities.getRandomNumberBetween(1, 180));
			((Map<Object,Object>)dataFromXml[j][0]).put(INTERVIEW_DATE, StaffingUtilities.convertDateIntoMilliSecond(date, DATABASE_DATE_FORMAT));
			((Map<Object,Object>)dataFromXml[j][0]).put(CANDIDATE_NAME, "Automation "+RandomStringUtils.random(Utilities.getRandomNumberBetween(5,8),true, false));
		}
		return dataFromXml;
	}
	
	/**
	 * This data provider can be used in positive scenario for creating interview request with random data
	 * @return
	 * @throws SQLException
	 */
	@DataProvider(name="random_TestData_For_Interview_Requests_E2E_Scenario")
	public Object[][] getRandomTestData() throws SQLException{
		int totalDataFromDB=1;
		InterviewDBHelper interviewDBHelper=new InterviewDBHelper(); 
		List<String> recruiterIds = interviewDBHelper.getGloberIds(totalDataFromDB);
		List<String> applyPositions = interviewDBHelper.getPositionIds(totalDataFromDB);
		List<String> locations = interviewDBHelper.getLocationNames(totalDataFromDB);
		List<Map<Object,Object>> dataList=new ArrayList<Map<Object,Object>>();
		for(int i=0;i<totalDataFromDB;i++) {
			String applySeniority=interviewDBHelper.getSeniorityId(applyPositions.get(i));
			if(!applySeniority.contains("not available")) {
			Map<Object,Object> dataMap=new HashMap<>();
			dataMap.put(APPLY_POSITION, applyPositions.get(i));
			dataMap.put(APPLY_SENIORITY, applySeniority);
			dataMap.put(LOCATION, locations.get(i));
			dataMap.put(POSITION, applyPositions.get(i));
			dataMap.put(SENIORITY, applySeniority);
			dataMap.put(RECRUITER_ID, recruiterIds.get(i));
			dataMap.put(RECRUITER_USERNAME, interviewDBHelper.getGloberUsername(recruiterIds.get(i)));
			dataMap.put(CANDIDATE_GLOBAL_ID, Utilities.getRandomNumberBetween(0, 100));
			dataMap.put(HR_FEEDBACK, RandomStringUtils.random(Utilities.getRandomNumberBetween(5, 10), true, false));
			String date=Utilities.getFutureDate(DATABASE_DATE_FORMAT, Utilities.getRandomNumberBetween(1, 180));
			dataMap.put(INTERVIEW_DATE, StaffingUtilities.convertDateIntoMilliSecond(date, DATABASE_DATE_FORMAT));
			String randomLastName="Automation "+RandomStringUtils.random(Utilities.getRandomNumberBetween(5,8),true, false);
			dataMap.put(CANDIDATE_NAME, randomLastName);
			dataMap.put(INTERVIEW_TYPE, interviewDBHelper.getInterviewType());
			dataMap.put(CURRICULUM, "http://ssff.com/"+Utilities.getRandomNumberBetween(1, 10));
			dataMap.put(ENGLISH_LEVEL, interviewDBHelper.getSkillLevel());
			dataMap.put(CANDIDATE_EMAIL, "hadiyadeepak5050@gmail.com");
			dataMap.put(DISCARD_REASON, RandomStringUtils.random(Utilities.getRandomNumberBetween(0,5),true, false));
			dataMap.put(HEADER_KEY, TOKEN);
			dataMap.put(HEADER_VALUE, "ankita.jog");
			dataMap.put(CANDIDATE_SKILLS, "1");
			dataMap.put(DTO_TYPE, "FAT");
			dataMap.put(JOB_APPLICATION_ID, RandomStringUtils.random(15, false, true));
			dataList.add(dataMap);
			}
		}
		Object[][] dataFromDB = new Object[dataList.size()][1];
		for(int j=0;j<dataFromDB.length;j++) {
			dataFromDB[j][0]=dataList.get(j);
		}
		return dataFromDB;
	}
	
	/**
	 * This data provider can be used in positive scenario for creating interview request with random data
	 * @return {@link Object}
	 * @throws Exception 
	 */
	@DataProvider(name="random_TestData_For_Day_Light_Saving")
	public Object[][] getRandomTestData_For_Day_Light_Saving() throws Exception {
		InterviewDBHelper interviewDBHelper=new InterviewDBHelper(); 
		int applyPosition = 1;
		int applySeniority=3;
		List<TimezoneDTO> locations = interviewDBHelper.getDSTLocations(10);
		int totalLocations=locations.size();
		List<String> recruiterIds = interviewDBHelper.getGloberIds(totalLocations);
		List<Map<Object,Object>> dataList=new ArrayList<Map<Object,Object>>();
		
		for(int i=0;i<totalLocations;i++) {
			TimezoneDTO locationDetails = locations.get(i);
			Map<Object,Object> dataMap=new HashMap<>();
			dataMap.put(APPLY_POSITION, applyPosition);
			dataMap.put(APPLY_SENIORITY, applySeniority);
			dataMap.put(LOCATION, locationDetails.getSiteName());
			dataMap.put(POSITION, applyPosition);
			dataMap.put(SENIORITY, applySeniority);
			dataMap.put(RECRUITER_ID, recruiterIds.get(i));
			dataMap.put(RECRUITER_USERNAME, interviewDBHelper.getGloberUsername(recruiterIds.get(i)));
			dataMap.put(CANDIDATE_GLOBAL_ID, Utilities.getRandomNumberBetween(0, 100));
			dataMap.put(HR_FEEDBACK, RandomStringUtils.random(Utilities.getRandomNumberBetween(5, 10), true, false));

			//	This block can be used to create interview date for BVA
				int hoursToAdd=0;
				String dateToConsider=null;
				if(i%2==0) {
					hoursToAdd=StaffingUtilities.getRandomNumberBetween(12, 90);
					dateToConsider = locationDetails.getDstStartDate();
				}else {
					hoursToAdd=StaffingUtilities.getRandomNumberBetween(12, 90)*-1;
					dateToConsider=locationDetails.getDstEndDate();
				}
			String date=StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT, Calendar.HOUR, hoursToAdd, dateToConsider);
			long dateInMilliSec = StaffingUtilities.convertDateIntoMilliSecond(date, DATABASE_DATE_FORMAT,locationDetails.getTimeZone());
			dataMap.put(INTERVIEW_DATE, dateInMilliSec);
			Reporter.log("Interview date "+StaffingUtilities.convertMilliSecondIntoDateWithTimeZone(Long.toString(dateInMilliSec), DATABASE_DATE_FORMAT, locationDetails.getTimeZone()),true);
			String randomLastName="Automation DST"+RandomStringUtils.random(Utilities.getRandomNumberBetween(5,8),true, false);
			dataMap.put(CANDIDATE_NAME, randomLastName);
			dataMap.put(INTERVIEW_TYPE, interviewDBHelper.getInterviewType());
			dataMap.put(CURRICULUM, "http://ssff.com/"+Utilities.getRandomNumberBetween(1, 10));
			dataMap.put(CANDIDATE_EMAIL, "hadiyadeepak5050@gmail.com");
			dataMap.put(DISCARD_REASON, RandomStringUtils.random(Utilities.getRandomNumberBetween(0,5),true, false));
			dataMap.put(HEADER_KEY, TOKEN);
			dataMap.put(HEADER_VALUE, "ankita.jog");
			dataMap.put(CANDIDATE_SKILLS, "1");
			dataMap.put(DTO_TYPE, "FAT");
			dataMap.put(JOB_APPLICATION_ID, RandomStringUtils.random(15, false, true));
			dataList.add(dataMap);
		}
		Object[][] dataFromDB = new Object[dataList.size()][1];
		for(int j=0;j<dataFromDB.length;j++) {
			dataFromDB[j][0]=dataList.get(j);
		}
		return dataFromDB;
	}
}
