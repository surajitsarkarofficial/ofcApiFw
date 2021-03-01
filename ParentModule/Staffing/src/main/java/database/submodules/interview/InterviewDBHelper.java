package database.submodules.interview;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Reporter;

import constants.submodules.interview.InterviewConstants;
import database.StaffingDBHelper;
import dto.submodules.interview.GatekeepersSearchResultDTO;
import dto.submodules.interview.InterviewDTO;
import dto.submodules.interview.InterviewRequestDTO;
import dto.submodules.interview.TimezoneDTO;
import utils.StaffingUtilities;

/**
 * 
 * @author deepakkumar.hadiya
 *
 */

public class InterviewDBHelper extends StaffingDBHelper implements InterviewConstants{

	public InterviewDBHelper() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method returns interview details
	 * @param interviewId
	 * @return {@link InterviewDTO}
	 * @throws Exception 
	 * @author deepakkumar.hadiya
	 */
	
	public InterviewDTO getInterviewDetails(String interviewId) throws Exception {
		InterviewDTO dto=new InterviewDTO();
		String query=String.format("SELECT * FROM technical_interviews WHERE id=%s",interviewId);
		try {
			ResultSet resultSet = getResultSet(query);
			if(resultSet.next()) {
				dto.setInterviewId(resultSet.getString("id"));
				dto.setHrFeedback(resultSet.getString("hr_feedback"));
				dto.setCurriculum(resultSet.getString("curriculum"));
				dto.setInterviewerId(resultSet.getString("interviewer_id"));
				dto.setRecruiterId(resultSet.getString("recruiter_id"));
				dto.setLocation(resultSet.getString("location"));
				dto.setInterviewDate(resultSet.getString("interview_date"));
				dto.setCandidateId(resultSet.getString("candidate_id"));
				dto.setCandidateGlobalId(resultSet.getString("candidate_global_id"));
				dto.setCandidateName(resultSet.getString("candidate_name"));
				dto.setCandidateEmail(resultSet.getString("candidate_email"));
				dto.setDiscardReason(resultSet.getString("discard_reason"));
				dto.setEnglishLevel(resultSet.getString("english_level_fk"));
				dto.setInterviewtype(resultSet.getString("interview_type_fk"));
				dto.setAppliedPosition(resultSet.getString("applied_position_fk"));
				dto.setAppliedSenority(resultSet.getString("applied_seniority_fk"));
				dto.setStatusFK(resultSet.getString("status_fk"));
				
			}else {
				throw new Exception("Record is not found for interview id = "+interviewId+" in 'technical_interviews' table");
			}
			return dto;
		}finally {
			endConnection();
		}
	}
	
	/**
	 * This method returns the details regarding email sent to recruiter for specified interview id
	 * @param interviewId
	 * @return {@link GatekeepersSearchResultDTO}
	 * @author deepakkumar.hadiya
	 * @throws Exception 
	 */
	public GatekeepersSearchResultDTO getGatekeepersSearchResult(String interviewId) throws Exception {
		GatekeepersSearchResultDTO dto=new GatekeepersSearchResultDTO();
		String query=String.format("SELECT * FROM gatekeepers_search_result WHERE technical_interview_fk=%s",interviewId);
		try {
			ResultSet resultSet = getResultSet(query);
			if(resultSet.next()) {
				dto.setGkSearchResultId(resultSet.getString("id"));
				dto.setEmailSentToRecruiter(resultSet.getString("email_sent_to_recruiter"));
				dto.setOccouredWhile(resultSet.getString("occoured_while"));
				dto.setGkSearchResultCreationDate(resultSet.getString("creation_date"));
			}else {
				throw new Exception("Record is not found for interview id = "+interviewId+" in 'gatekeepers_search_result' table"); 
			}
			return dto;
		}finally {
			endConnection();
		}
	}
	
	/**
	 * This method returns random interview details which was not responded by any gatekeeper
	 * @return {@link Map}
	 * @throws SQLException
	 * @author deepakkumar.hadiya
	 */
	
	public Map<String,String> getRandomInterviewDetails(int totalData) throws SQLException {
		Map<String,String> data=new HashMap<>();
		String query="SELECT * FROM technical_interview_requests WHERE id IN (SELECT MIN(tir.id) FROM technical_interview_requests tir,technical_interviews ti WHERE tir.candidate_name LIKE 'Automation%' AND tir.interview_date=ti.interview_date AND ti.id=tir.technical_interview_fk AND tir.interview_date>timezone('GMT+0', NOW()) AND tir.technical_interview_fk NOT IN (SELECT technical_interview_fk FROM technical_interview_requests WHERE action NOT LIKE'request_sent') GROUP BY tir.technical_interview_fk) ORDER BY random() LIMIT "+totalData;
		try {
			ResultSet resultSet = getResultSet(query);
			resultSet.next();
			data.put(INTERVIEW_ID, resultSet.getString("technical_interview_fk"));
			data.put(INTERVIEWER_ID, resultSet.getString("interviewer_fk"));
			return data;
		}finally {
			endConnection();
		}
	}

	/**
	 * This method returns random interviewerId details for mentioned interviewId
	 * @param interviewId
	 * @return {@link String}
	 * @throws SQLException
	 * @author deepakkumar.hadiya
	 */
	
	public String getRandomInterviewerDetails(String interviewId) throws SQLException {
		String query="SELECT * FROM technical_interview_requests WHERE technical_interview_fk = "+interviewId+" AND action='request_sent' ORDER BY Random() LIMIT 1";
		try {
			ResultSet resultSet = getResultSet(query);
			resultSet.next();
			return resultSet.getString("interviewer_fk");
		}finally {
			endConnection();
		}
	}
	
	/**
	 * This method returns interview request details
	 * @param interviewId
	 * @param interviewerId
	 * @return {@link InterviewRequestDTO}
	 * @throws SQLException
	 * @author deepakkumar.hadiya
	 */
	
	public InterviewRequestDTO getInterviewRequestDetails(String interviewId, String interviewerId) throws SQLException {
		InterviewRequestDTO dto=new InterviewRequestDTO();
		String query=String.format("SELECT * FROM technical_interview_requests WHERE technical_interview_fk=%s AND interviewer_fk=%s;",interviewId,interviewerId);
		try {
			ResultSet resultSet = getResultSet(query);
			resultSet.next();
			dto.setInterviewRequestId(resultSet.getString("id"));
			dto.setInterviewId(resultSet.getString("technical_interview_fk"));
			dto.setInterviewerId(resultSet.getString("interviewer_fk"));
			dto.setRecruiterId(resultSet.getString("recruiter_fk"));
			dto.setInterviewRequestAction(resultSet.getString("action"));
			dto.setInterviewDate(resultSet.getString("interview_date"));
			dto.setCandidateName(resultSet.getString("candidate_name"));
			dto.setCandidateEmail(resultSet.getString("candidate_email"));
			dto.setInterviewerName(resultSet.getString("interviewer_name"));
			dto.setInterviewerEmail(resultSet.getString("interviewer_email"));
			dto.setInterviewCreationDate(resultSet.getString("creation_date"));
			dto.setInterviewLastModificationDate(resultSet.getString("last_modified"));
			dto.setWaitTimeCompleted(resultSet.getString("wait_time_completed"));
			dto.setLastResponded(resultSet.getString("last_responded"));
			dto.setDiscardReason(resultSet.getString("discard_reason"));
			return dto;
		}finally {
			endConnection();
		}
	}
	
	/**
	 * This method returns interview request details
	 * @param interviewId
	 * @return {@link List}
	 * @throws Exception 
	 * @author deepakkumar.hadiya
	 */
	
	public List<InterviewRequestDTO> getInterviewRequestDetails(String interviewId) throws Exception {
		
		String query=String.format("SELECT * FROM technical_interview_requests WHERE technical_interview_fk=%s ORDER BY creation_date, id",interviewId);
		try {
			List<InterviewRequestDTO> interviewRequests=new ArrayList<InterviewRequestDTO>();
			boolean isRecordFound=false;
			long maxWaitTime=WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS*12;
			long retryInterval=WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS/2;
			long waitTime=0;
			while(waitTime <= maxWaitTime && !isRecordFound) {
				ResultSet resultSet = getResultSet(query);
				while(resultSet.next()) {
				InterviewRequestDTO dto=new InterviewRequestDTO();
				dto.setInterviewRequestId(resultSet.getString("id"));
				dto.setInterviewId(resultSet.getString("technical_interview_fk"));
				dto.setInterviewerId(resultSet.getString("interviewer_fk"));
				dto.setRecruiterId(resultSet.getString("recruiter_fk"));
				dto.setInterviewRequestAction(resultSet.getString("action"));
				dto.setInterviewDate(resultSet.getString("interview_date"));
				dto.setCandidateName(resultSet.getString("candidate_name"));
				dto.setCandidateEmail(resultSet.getString("candidate_email"));
				dto.setInterviewerName(resultSet.getString("interviewer_name"));
				dto.setInterviewerEmail(resultSet.getString("interviewer_email"));
				dto.setInterviewCreationDate(resultSet.getString("creation_date"));
				dto.setInterviewLastModificationDate(resultSet.getString("last_modified"));
				dto.setWaitTimeCompleted(resultSet.getString("wait_time_completed"));
				dto.setLastResponded(resultSet.getString("last_responded"));
				interviewRequests.add(dto);
				isRecordFound=true;
				}
				if(!isRecordFound) {
					Thread.sleep(retryInterval);
					waitTime=waitTime+retryInterval;
				}
			}
			String waitedTime="'"+(waitTime/60000)+" minutes "+((waitTime%60000)/1000)+" seconds'";
			if(!isRecordFound) {
				throw new Exception("Interview requests are not found for interview id = "+interviewId+" in 'technical_interview_requests' table even after waiting for "+waitedTime);
			}
			return interviewRequests;
		}finally {
			endConnection();
		}
	}
	
	/**
	 * This method returns all interview request for specified gatekeeper
	 * @param interviewerId
	 * @return {@link List}
	 * @throws Exception
	 * @author deepakkumar.hadiya 
	 */
	
	public List<InterviewRequestDTO> getAllInterviewRequestsForGatekeeper(String interviewerId) throws Exception {
		String query="(SELECT pr.NAME positionName, ps.NAME seniorityName, ss.city_name city, cs.NAME country, gbr.first_name AS " + 
				"recruiter_firstName, gbr.last_name recruiter_lastName, tir.id requestId, tir.interviewer_fk interviewerId, tir.recruiter_fk AS " + 
				"recruiterId, tir.technical_interview_fk interviewId, tir.action, tir.interview_date interviewDate, tir.candidate_name " + 
				"candidateName, tir.candidate_email candidateEmail, tir.interviewer_name interviewerName, tir.interviewer_email " + 
				"interviewerEmail, tir.wait_time_completed waitTimeCompleted, tir.creation_date creationDate, tir.last_modified " + 
				"lastModified FROM technical_interview_requests tir, globers gbr, position_roles pr, technical_interviews ti, " + 
				"position_seniorities ps, sites ss, countries cs WHERE interviewer_fk = "+interviewerId+" AND action LIKE 'request_sent' " + 
				"AND tir.technical_interview_fk = ti.id AND ti.applied_position_fk = pr.id AND ti.applied_seniority_fk = ps.id AND ti.location " + 
				"= ss.NAME AND ss.country_fk = cs.id AND gbr.id = ti.recruiter_id ORDER BY tir.interview_date DESC) UNION ALL (SELECT " + 
				"pr.NAME positionName, ps.NAME seniorityName, ss.city_name city, cs.NAME country, gbr.first_name AS " + 
				"recruiter_firstName, gbr.last_name recruiter_lastName, tir.id requestId, tir.interviewer_fk interviewerId, tir.recruiter_fk " + 
				"recruiterId, tir.technical_interview_fk interviewId, tir.action, tir.interview_date interviewDate, tir.candidate_name " + 
				"candidateName, tir.candidate_email candidateEmail, tir.interviewer_name interviewerName, tir.interviewer_email " + 
				"interviewerEmail, tir.wait_time_completed waitTimeCompleted, tir.creation_date creationDate, tir.last_modified " + 
				"lastModified FROM technical_interview_requests tir, globers gbr, position_roles pr, technical_interviews ti, " + 
				"position_seniorities ps, sites ss, countries cs WHERE interviewer_fk = "+interviewerId+" AND action NOT LIKE 'request_sent' " + 
				"AND tir.technical_interview_fk = ti.id AND ti.applied_position_fk = pr.id AND ti.applied_seniority_fk = ps.id AND " + 
				"ti.location = ss.NAME AND ss.country_fk = cs.id AND gbr.id = ti.recruiter_id ORDER BY tir.interview_date DESC);";
		try {
			ResultSet resultSet = getResultSet(query);
			List<InterviewRequestDTO> interviewRequests=new ArrayList<InterviewRequestDTO>();
			TimezoneDTO timeZoneDTO=getTimeZoneAndDSTForGloberLocation(interviewerId);
			String timeZoneOfInterviewer=timeZoneDTO.getTimeZone();
			String dstStartDate=timeZoneDTO.getDstStartDate();
			String dstEndDate=timeZoneDTO.getDstEndDate();
			while(resultSet.next()) {
			String interviewDateInDbFormat = resultSet.getString("interviewDate");
			String dateFormat=getDateFormatWithMilliSec(interviewDateInDbFormat, DATABASE_DATE_FORMAT);
			String interviewDateInMilliSecWithoutDST=Long.toString(StaffingUtilities.convertDateIntoMilliSecond(interviewDateInDbFormat, dateFormat, DATABASE_TIMEZONE));
			String interviewDateInDbFormatWithDST=getInterviewDateWithDSTTime(dateFormat,interviewDateInDbFormat, dstStartDate, dstEndDate);
			String interviewDateInMilliSecWithDST=Long.toString(StaffingUtilities.convertDateIntoMilliSecond(interviewDateInDbFormatWithDST, dateFormat, DATABASE_TIMEZONE));;
			String interviewDateStartTimeForGk=StaffingUtilities.convertMilliSecondIntoDateWithTimeZone(interviewDateInMilliSecWithDST, DATE_FORMAT_FOR_12_HOUR_FORMAT_WITHOUT_SEC, timeZoneOfInterviewer);
			String interviewDateEndTimeForGk=StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATE_FORMAT_FOR_12_HOUR_FORMAT_WITHOUT_SEC, Calendar.HOUR, 1, interviewDateStartTimeForGk);
			String interviewDateAsPerTimezone=interviewDateStartTimeForGk.split("  ")[0];
			String timeSlot=(interviewDateStartTimeForGk.split("  ")[1]+" to "+interviewDateEndTimeForGk.split("  ")[1]).toLowerCase();
			InterviewRequestDTO dto=new InterviewRequestDTO();
			dto.setTimeSlot(timeSlot);
			dto.setPositionName(resultSet.getString("positionName"));
			dto.setSeniorityName(resultSet.getString("seniorityName"));
			dto.setRecruiterName(resultSet.getString("recruiter_firstName")+" "+resultSet.getString("recruiter_lastName"));
			dto.setLocation(resultSet.getString("city")+", "+resultSet.getString("country"));
			dto.setInterviewRequestId(resultSet.getString("requestId"));
			dto.setInterviewerId(resultSet.getString("interviewerId"));
			dto.setRecruiterId(resultSet.getString("recruiterId"));
			dto.setInterviewId(resultSet.getString("interviewId"));
			dto.setInterviewRequestAction(resultSet.getString("action"));
			dto.setInterviewDate(interviewDateInMilliSecWithoutDST);
			dto.setCandidateName(resultSet.getString("candidateName"));
			dto.setCandidateEmail(resultSet.getString("candidateEmail"));
			dto.setInterviewerName(resultSet.getString("interviewerName"));
			dto.setInterviewerEmail(resultSet.getString("interviewerEmail"));
			String creationDate = resultSet.getString("creationDate");
			dateFormat=getDateFormatWithMilliSec(creationDate, DATABASE_DATE_FORMAT);
			dto.setInterviewCreationDate(Long.toString(StaffingUtilities.convertDateIntoMilliSecond(creationDate, dateFormat, DATABASE_TIMEZONE)));
			String lastModificationDate=resultSet.getString("lastModified");
			dateFormat=getDateFormatWithMilliSec(lastModificationDate, DATABASE_DATE_FORMAT);
			dto.setInterviewLastModificationDate(Long.toString(StaffingUtilities.convertDateIntoMilliSecond(lastModificationDate, dateFormat, DATABASE_TIMEZONE)));
			dto.setWaitTimeCompleted(resultSet.getString("waitTimeCompleted").equals(TrueInDBFormat)?"true":"false");
			dto.setInterviewDateAsPerTimezone(interviewDateAsPerTimezone);
			interviewRequests.add(dto);
			}
			return interviewRequests;
		}finally {
			endConnection();
		}
		
	}
	
	/**
	 * Filter the records by pageNo, pageSize and candidateName
	 * @param interviewRequests
	 * Note : If pageSize=0 then it will consider defaultSize=100
	 * @param pageNo
	 * @param pageSize
	 * @param candidateName
	 * @return {@link List}
	 * @author deepakkumar.hadiya
	 */
	public List<InterviewRequestDTO> filterRecords(List<InterviewRequestDTO> interviewRequests,int pageNo,int pageSize,String... candidateName){
		int totalRequests=interviewRequests.size();
		Reporter.log("Total interviews = "+totalRequests+" pageNo = "+pageNo+" pageSize = "+pageSize,true);
		pageSize=pageSize==0?100:pageSize;
		int recordStartIndex=pageNo==0?0:pageNo*pageSize;
		List<InterviewRequestDTO> interviewRequestsForPage=new ArrayList<InterviewRequestDTO>();
		if(recordStartIndex<totalRequests && pageSize<101) {
				for(int i=recordStartIndex;i<recordStartIndex+pageSize;i++) {
					try {
					interviewRequestsForPage.add(interviewRequests.get(i));
					}catch (IndexOutOfBoundsException e) {
						break;
					}
				}
			return interviewRequestsForPage;
		}else if(recordStartIndex>totalRequests){
			return interviewRequestsForPage;
		}
		if (candidateName.length>0) {
			for(int i=recordStartIndex;i<recordStartIndex+pageSize;i++) {
				try {
				InterviewRequestDTO record = interviewRequests.get(i);
				if(!record.getCandidateName().toLowerCase().contains(candidateName[0].toLowerCase())) {
					interviewRequestsForPage.remove(record);
				}
				}catch (IndexOutOfBoundsException e) {
					break;
				}
			}
		}
		return interviewRequests;
	}
	
	/**
	 * Get given date format with milli second as per the given date
	 * @param date
	 * @param dateFormat
	 * @return {@link String}
	 * @author deepakkumar.hadiya
	 */
	private String getDateFormatWithMilliSec(String date, String dateFormat) {
		return date.contains(".")?dateFormat+"."+RandomStringUtils.random(date.split("\\.")[1].length(), "S"):dateFormat;
	}

	/**
	 * To update interview date of the existing interview request
	 * @param interviewId
	 * @param dateInMilliSec
	 * @throws SQLException
	 */
	public void updateInterviewDate(String interviewId, long dateInMilliSec) throws SQLException {
		String interviewDate = StaffingUtilities.convertMilliSecondIntoDateWithTimeZone(Long.toString(dateInMilliSec), DATABASE_DATE_FORMAT, DATABASE_TIMEZONE);
		String query="UPDATE technical_interviews SET interview_date='"+interviewDate+"' WHERE id="+interviewId;
		try {
			int records = executeUpdateQuery(query);
			Reporter.log("Total '"+records+"' records are updated for interview date "+interviewDate+" in technical_interviews table",true);
		}finally {
			endConnection();
		}
	}
	
	/**
	 * To update interview date of the all interview request having interview date between -15 days to +15 days of current date
	 * @param position
	 * @param dateInMilliSec
	 * @throws SQLException
	 */
	public void updateInterviewDateForPosition(String position, long dateInMilliSec) throws SQLException {
		String interviewDate = StaffingUtilities.convertMilliSecondIntoDateWithTimeZone(Long.toString(dateInMilliSec), DATABASE_DATE_FORMAT, DATABASE_TIMEZONE);
		String query="UPDATE technical_interviews SET interview_date='"+interviewDate+"' WHERE interview_date<=Timezone('GMT+0', now())+interval '15days' " + 
				"AND interview_date>=timezone('GMT+0', now())-interval '15days' AND applied_position_fk = "+position+" AND interviewer_id IS NOT null";
		try {
			int records = executeUpdateQuery(query);
			Reporter.log("Total '"+records+"' records are updated with interview date "+interviewDate+" in technical_interviews table",true);
		}finally {
			endConnection();
		}
	}
	
	/**
	 * To update the status of existing interview request
	 * @apiNote 3030 - evaluated, 3010 - new request
	 * @param interviewId
	 * @param statusCode
	 * @throws SQLException
	 */
	public void updateInterviewStatus(String interviewId, int statusCode) throws SQLException {
		String query="UPDATE technical_interviews SET status_fk='"+statusCode+"' where id="+interviewId;
		try {
			int records = executeUpdateQuery(query);
			Reporter.log("Total '"+records+"' records are updated for interview status in technical_interviews table",true);
		}finally {
			endConnection();
		}
	}
	
	/**
	 * Update the mentioned date by -1 hour if it falls in the day light saving range 
	 * @param dateFormat
	 * @param interviewDateWithMilliSec
	 * @param dstStartDate
	 * @param dstEndDate
	 * @return {@link String}}
	 * @throws Exception
	 */
	public String getInterviewDateWithDSTTime(String dateFormat,String interviewDateWithMilliSec,String dstStartDate,String dstEndDate) throws Exception {
		if(dstStartDate!=null && dstEndDate != null) {
			if(StaffingUtilities.dateIsBetweenTwoDate(dateFormat, interviewDateWithMilliSec, dstStartDate, dstEndDate)){
				interviewDateWithMilliSec=StaffingUtilities.getFutureDateAndTimeForMentionedUnit(dateFormat, Calendar.HOUR, -1, interviewDateWithMilliSec);
			}
		}
		return interviewDateWithMilliSec; 
	}
	
	/**
	 * Get list of day light saving locations
	 * 
	 * @param totalLocationNames
	 * @return {@link List}
	 * @throws SQLException
	 */

	public List<TimezoneDTO> getDSTLocations(Object totalLocationNames) throws SQLException {
		try {
			String query = "SELECT s.name AS location,st.timezone AS timezone, st.daylight_saving_start_date AS dstStartDate, st.daylight_saving_end_date AS dstEndDate FROM " + 
					"site_timezones st, sites s WHERE st.site_fk = s.id AND daylight_saving_start_date IS NOT NULL AND ( s.site_details_fk " + 
					"!= 0 OR site_details_fk IS NULL ) ORDER BY Random() LIMIT "
					+ totalLocationNames;
			ResultSet resultSet = getResultSet(query);
			List<TimezoneDTO> locations = new ArrayList<>();
			while (resultSet.next()) {
				TimezoneDTO dto=new TimezoneDTO();
				dto.setSiteName(resultSet.getString("location"));
				dto.setTimeZone(resultSet.getString("timezone"));
				dto.setDstStartDate(resultSet.getString("dstStartDate"));
				dto.setDstEndDate(resultSet.getString("dstEndDate"));
				locations.add(dto);
			}
			return locations;
		} finally {
			endConnection();
		}
	}
}
