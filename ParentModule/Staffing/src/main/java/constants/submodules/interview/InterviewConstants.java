package constants.submodules.interview;

import constants.StaffingConstants;
import properties.GlowProperties;

/**
 * 
 * @author deepakkumar.hadiya
 *
 */

public interface InterviewConstants extends StaffingConstants {

	String RECRUITER_ID = "recruiterId";
	String RECRUITER_USERNAME = "recruiterUsername";
	String CANDIDATE_GLOBAL_ID="candidateGlobalId";
	String HR_FEEDBACK="hrFeedback";
	String APPLY_POSITION="applyPosition";
	String APPLY_SENIORITY="applySeniority";
	String CANDIDATE_NAME="candidateName";
	String INTERVIEW_TYPE="interviewType";
	String ENGLISH_LEVEL="englishLevel";
	String CURRICULUM="curriculum";
	String CANDIDATE_EMAIL="candidateEmail";
	String DISCARD_REASON="discardReason";
	String INTERVIEW_ID="interviewId";
	String INTERVIEW_id="interviewid";
	String INTERVIEWER_ID="interviewerId";
	String INTERVIEWER_id="interviewerid";
	String TOKEN_VALUE="shreyas.gokodikar";
	String REQUEST_ACCEPTED="request_accepted";
	String REQUEST_REJECTED="request_rejected";
	String REQUEST_SENT="request_sent";
	String REQUEST_CANCELLED="request_cancelled";
	String REQUEST_IGNORED="request_ignored";
	String FIRST_RUN="first_run";
	String RERUN="rerun";
	String ALL_REJECT="all_reject";
	String ACCEPT="accept";
	String REJECT="reject";
	String IGNORE="ignore";
	String ACCEPTED="accepted";
	String JOB_APPLICATION_ID="jobApplicationId";
	int WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS=10000;
	int WAIT_TIME_FOR_RERUN_IN_MIN=GlowProperties.executionEnvironment.equals("PREPROD")?(48*60):15;
	int WAIT_TIME_FREQUENCY_FOR_RERUN_IN_MIN=GlowProperties.executionEnvironment.equals("PREPROD")?(6*60):3;
	long WAIT_TIME_FREQUENCY_FOR_RERUN_IN_MILLI_SECONDS=1000*60*WAIT_TIME_FREQUENCY_FOR_RERUN_IN_MIN;
	long WAIT_TIME_FOR_RERUN_IN_MILLI_SECONDS=1000*60*WAIT_TIME_FOR_RERUN_IN_MIN;
	long BUFFER_TIME_IN_SECONDS=90;
	long BUFFER_TIME_IN_MILLI_SECONDS=BUFFER_TIME_IN_SECONDS*1000;
	int TOTAL_GK_FOR_RE_RUN=GlowProperties.executionEnvironment.equals("PREPROD")?5:3;
	String DATABASE_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
	String DATABASE_DATE_FORMAT_WITHOUT_TIME="yyyy-MM-dd";
	String DATABASE_DATE_FORMAT_WITH_MILLI_SEC="yyyy-MM-dd HH:mm:ss.SSS";
	String DATABASE_TIMEZONE="GMT+0";
	String INDIA_TIME_ZONE="GMT+05:30";
	
	String DATE_FORMAT_FOR_12_HOUR_FORMAT_WITHOUT_SEC="MMM dd, yyyy  hh:mma";
	String TIME_SLOT="timeSlot";
	String POSITION_NAME="positionName";
	String SENIORITY_NAME="seniorityName";
	String RECRUITER_NAME="recruiterName";
	String REQUEST_ID="requestId";
	String ACTION="action";
	String INTERVIEWER_NAME="interviewerName";
	String INTERVIEWER_EMAIL="interviewerEmail";
	String WAIT_TIME_COMPLETED="waitTimeCompleted";
	String INTERVIEW_DATE_AS_PER_TIME_ZONE="interviewDateAsPerTimezone";
	String CREATION_DATE="creationDate";
	String LAST_MODIFIED="lastModified";
	
	String PAGE_SIZE="pageSize";
	String PAGE_NUM="pageNum";
	
	int MONDAY_OF_PREVIOUS_WEEK=-5;
	int SUNDAY_OF_CURRENT_WEEK=8;
	int MONDAY_OF_CURRENT_WEEK=2;
	int SUNDAY_OF_NEXT_WEEK=7+8;

	String THIN="THIN";
	String FAT="FAT";
	String GATEKEEPER_IDS="gatekeeperIds";
	
	String CANCEL_INTERVIEW="cancelInterview";
	String PERFORM_NOTIFICATION="performNotification";
}
