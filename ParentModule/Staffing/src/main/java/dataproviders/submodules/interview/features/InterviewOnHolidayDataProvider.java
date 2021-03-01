package dataproviders.submodules.interview.features;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;

import constants.submodules.gatekeepers.GatekeepersConstants;
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

public class InterviewOnHolidayDataProvider extends InterviewDataProvider implements InterviewConstants,GatekeepersConstants{
	
	/**
	 * This data provider can be used to create interview request on holiday for each country
	 * @return Object
	 * @throws Exception
	 */
	@DataProvider(name="validTestDataForAllLocations")
	public Object[][] getTestDataForAllLoctions() throws Exception{
		
		SearchGatekeepersDBHelper db = new SearchGatekeepersDBHelper();
		List<String> positions = db.getPositionIds("ALL");
		List<String> locations = db.getLocationForEachCountry();
		Object[][] dataFromDB = new Object[locations.size()][1];
		InterviewDBHelper interviewDBHelper=new InterviewDBHelper(); 
		List<String> recruiterIds = interviewDBHelper.getGloberIds(locations.size());
		List<Map<Object,Object>> dataList=new ArrayList<Map<Object,Object>>();
		for(int i=0;i<locations.size();i++) {
			String position = positions.get(Utilities.getRandomNumberBetween(0, positions.size()-1));
			String applySeniority=interviewDBHelper.getSeniorityId(position);
			Map<Object,Object> dataMap=new HashMap<>();
			dataMap.put(RECRUITER_ID, recruiterIds.get(i));
			dataMap.put(RECRUITER_USERNAME, interviewDBHelper.getGloberUsername(recruiterIds.get(i)));
			dataMap.put(CANDIDATE_GLOBAL_ID, Utilities.getRandomNumberBetween(0, 10000));
			dataMap.put(HR_FEEDBACK, RandomStringUtils.random(Utilities.getRandomNumberBetween(20, 150), true, false));
			dataMap.put(APPLY_POSITION, position);
			dataMap.put(APPLY_SENIORITY, applySeniority);
			dataMap.put(POSITION, position);
			dataMap.put(SENIORITY, applySeniority);
			List<String> holidays = interviewDBHelper.getUpcomingHolidayListForLocation(locations.get(i));
			String interviewDay=null;
			if(holidays.size()>0) {
			interviewDay = holidays.get(Utilities.getRandomNumberBetween(0, holidays.size()-1));
			String date=StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT, Calendar.MINUTE, StaffingUtilities.getRandomNumberBetween(0, 1439), interviewDay);
			dataMap.put(INTERVIEW_DATE, StaffingUtilities.convertDateIntoMilliSecond(date, DATABASE_DATE_FORMAT));
			}else {
				String dateFormat="E MMMM dd yyyy HH:mm:ss",date=Utilities.getFutureDate(dateFormat, Utilities.getRandomNumberBetween(0, 180));
				dataMap.put(INTERVIEW_DATE, StaffingUtilities.convertDateIntoMilliSecond(date, dateFormat));	
			}
			dataMap.put(LOCATION, locations.get(i));
			String randomLastName=RandomStringUtils.random(Utilities.getRandomNumberBetween(2,4),true, false);
			dataMap.put(CANDIDATE_NAME, "Automation HolidayTest"+randomLastName);
			dataMap.put(INTERVIEW_TYPE, interviewDBHelper.getInterviewType());
			dataMap.put(CURRICULUM, "http://ssff.com/"+Utilities.getRandomNumberBetween(1, 10000));
			dataMap.put(ENGLISH_LEVEL, interviewDBHelper.getSkillLevel());
			dataMap.put(CANDIDATE_EMAIL, "hadiyadeepak5050@gmail.com"); 
			dataMap.put(DISCARD_REASON, RandomStringUtils.random(Utilities.getRandomNumberBetween(0,20),true, false));
			dataMap.put(HEADER_KEY, TOKEN);
			dataMap.put(HEADER_VALUE, "ankita.jog");
			dataMap.put(JOB_APPLICATION_ID, RandomStringUtils.random(15, false, true));
			dataMap.put(DTO_TYPE, FAT);
			dataList.add(dataMap);
			dataFromDB[i][0]=dataMap;
		}
		return dataFromDB;
	}
}
