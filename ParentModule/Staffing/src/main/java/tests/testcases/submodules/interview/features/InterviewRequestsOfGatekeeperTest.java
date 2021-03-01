package tests.testcases.submodules.interview.features;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import constants.submodules.interview.InterviewConstants;
import database.submodules.interview.InterviewDBHelper;
import dataproviders.submodules.interview.features.InterviewersDataProviders;
import dto.submodules.interview.InterviewRequestDTO;
import endpoints.submodules.interview.features.AcceptOrRejectInterviewEndpoints;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import tests.testcases.submodules.interview.InterviewBaseTest;
import tests.testhelpers.submodules.interview.features.InterviewRequestsOfGatekeeperTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.StaffingUtilities;

/**
 * 
 * @author deepakkumar.hadiya
 *
 */
public class InterviewRequestsOfGatekeeperTest extends InterviewBaseTest implements InterviewConstants{

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("Interview requests of Gatekeeper");
	}
	
	/**
	 * To validate all interview requests with details for specific gatekeeper
	 * @param data
	 * @throws Exception
	 *  
	 */
	@SuppressWarnings("rawtypes")
	@Test(priority = 1, dataProvider = "interviewers", dataProviderClass = InterviewersDataProviders.class, groups = {ExeGroups.Sanity,ExeGroups.Regression })
	public void verifyAllInterviewRequestsDetailsForGatekeeper(Object data) throws Exception {
		String interviewerId=data.toString();
		SoftAssert softAssert = new SoftAssert();
		
		InterviewRequestsOfGatekeeperTestHelper testHelper=new InterviewRequestsOfGatekeeperTestHelper();
		Response response = testHelper.interviewRequestsForGk(TOKEN, TOKEN_VALUE, interviewerId);
		validateResponseToContinueTest(response, 200,"Interview requests are not fetched for gatekeeper = "+interviewerId , true);
		JSONArray apriRecords = (JSONArray) restUtils.getValueFromResponse(response, "$.details.interviews[*]");
		
		InterviewDBHelper dbHelper=new InterviewDBHelper();
		Reporter.log("Total records = "+apriRecords.size()+" Time zone = "+dbHelper.getTimeZoneAndDSTForGloberLocation(interviewerId).getTimeZone(),true);
		List<InterviewRequestDTO> dbRecords = dbHelper.getAllInterviewRequestsForGatekeeper(interviewerId);
		
		Reporter.log("Verifying total interviews for a gatekeeper = "+interviewerId,true);
		Assert.assertEquals(apriRecords.size(), dbRecords.size(),"Interview records are incorrect in api response");
		
		test.log(Status.INFO, "Verifying details of all interview request for gatekeeper = "+interviewerId);
		for(int i=0;i<dbRecords.size();i++) {
			Map actualValue=(Map)apriRecords.get(i);
			InterviewRequestDTO dbRecord = dbRecords.get(i);
			String interviewRequestIdInAPI=actualValue.get("requestId").toString();
			String interviewRequestIdInDB=dbRecord.getInterviewRequestId();
			test.log(Status.INFO, "Verifying details of interview request id = "+dbRecord.getInterviewRequestId()+" for record no = "+(i+1));
			if(interviewRequestIdInAPI.equals(interviewRequestIdInDB)) {
				softAssert.assertEquals(actualValue.get(TIME_SLOT).toString(), dbRecord.getTimeSlot(),"Time slot is wrong in api response for interview request id "+interviewRequestIdInAPI);
				softAssert.assertEquals(actualValue.get(POSITION_NAME).toString(), dbRecord.getPositionName(),"Position name is wrong in api response for interview request id "+interviewRequestIdInAPI);
				softAssert.assertEquals(actualValue.get(SENIORITY_NAME).toString(), dbRecord.getSeniorityName(),"Seniority name is wrong in api response for interview request id "+interviewRequestIdInAPI);
				softAssert.assertEquals(actualValue.get(RECRUITER_NAME).toString(), dbRecord.getRecruiterName(),"Recruiter name is wrong in api response for interview request id "+interviewRequestIdInAPI);
				softAssert.assertEquals(actualValue.get(LOCATION).toString(), dbRecord.getLocation(),"Location is wrong in api response for interview request id "+interviewRequestIdInAPI);
				softAssert.assertEquals(actualValue.get(INTERVIEWER_ID).toString(), dbRecord.getInterviewerId(),"Interviewer id is wrong in api response for interview request id "+interviewRequestIdInAPI);
				softAssert.assertEquals(actualValue.get(RECRUITER_ID).toString(), dbRecord.getRecruiterId(),"Recruiter id is wrong in api response for interview request id "+interviewRequestIdInAPI);
				softAssert.assertEquals(actualValue.get(INTERVIEW_ID).toString(), dbRecord.getInterviewId(),"Interview id is wrong in api response for interview request id "+interviewRequestIdInAPI);
				softAssert.assertEquals(actualValue.get(ACTION).toString(), dbRecord.getInterviewRequestAction(),"Action is wrong in api response for interview request id "+interviewRequestIdInAPI);
				softAssert.assertEquals(actualValue.get(CANDIDATE_NAME).toString(), dbRecord.getCandidateName(),"Candidate name is wrong in api response for interview request id "+interviewRequestIdInAPI);
				softAssert.assertEquals(actualValue.get(CANDIDATE_EMAIL), dbRecord.getCandidateEmail(),"Candidate email is wrong in api response for interview request id "+interviewRequestIdInAPI);
				softAssert.assertEquals(actualValue.get(INTERVIEWER_NAME).toString(), dbRecord.getInterviewerName(),"Interviewer name is wrong in api response for interview request id "+interviewRequestIdInAPI);
				softAssert.assertEquals(actualValue.get(INTERVIEWER_EMAIL).toString(), dbRecord.getInterviewerEmail(),"Interviewer email is wrong in api response for interview request id "+interviewRequestIdInAPI);
				softAssert.assertEquals(actualValue.get(WAIT_TIME_COMPLETED).toString(), dbRecord.getWaitTimeCompleted(),"Wait time completed is wrong in api response for interview request id "+interviewRequestIdInAPI);
				softAssert.assertEquals(actualValue.get(INTERVIEW_DATE_AS_PER_TIME_ZONE).toString(), dbRecord.getInterviewDateAsPerTimezone(),"Interview date is not as per time zone in api response for interview request id "+interviewRequestIdInAPI);
				softAssert.assertEquals(actualValue.get(INTERVIEW_DATE).toString(), dbRecord.getInterviewDate(),"Interview date is wrong in api response for interview request id "+interviewRequestIdInAPI);
				String creationDateInAPI=actualValue.get(CREATION_DATE).toString(),creationDateInDB=dbRecord.getInterviewCreationDate();
				softAssert.assertEquals(creationDateInAPI,creationDateInDB,"Creation date is wrong in api response for interview request id "+interviewRequestIdInAPI);
				String lastModifiedInApi=actualValue.get(LAST_MODIFIED).toString(),lastModifiedInDB=dbRecord.getInterviewLastModificationDate();
				softAssert.assertEquals(lastModifiedInApi, lastModifiedInDB,"Last modified is wrong in api response for interview request id "+interviewRequestIdInAPI);
				test.log(Status.PASS, "Successfully verified details of interview request id = "+dbRecord.getInterviewRequestId()+" for record no = "+(i+1));
			}else {
				softAssert.fail("Interview request id is wrong for record no = "+(i+1)+" [requestId in api : "+interviewRequestIdInAPI+", requestId in DB : "+interviewRequestIdInDB+"]" );
				test.log(Status.FAIL, "Interview request id is wrong for record no = "+(i+1)+" requestId in api : "+interviewRequestIdInAPI+", requestId in DB : "+interviewRequestIdInDB );
			}
		}
		softAssert.assertAll();
		test.log(Status.PASS, "Successfully verified details of all interview request for interviwer = "+interviewerId);
	}
	
	/**
	 * To validate all interview requests with details for specific gatekeeper by pageSize=15
	 * @param data
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@Test(priority = 2, dataProvider = "interviewers", dataProviderClass = InterviewersDataProviders.class,  groups = {ExeGroups.Regression })
	public void verifyAllInterviewRequestsDetailsForGatekeeper_By_Page_Size(Object data) throws Exception {
		
		String interviewerId=data.toString();
		InterviewDBHelper dbHelper=new InterviewDBHelper();
		List<InterviewRequestDTO> dbRecords = dbHelper.getAllInterviewRequestsForGatekeeper(interviewerId);
		int pageSize=15;
		int totalInterviewsInDB=dbRecords.size();
		int totalPages=totalInterviewsInDB/pageSize;
		int lastPageInterviews=totalInterviewsInDB%pageSize;
		totalPages=lastPageInterviews==0?totalPages:totalPages+1;
		lastPageInterviews=lastPageInterviews==0?pageSize:lastPageInterviews;
		SoftAssert softAssert = new SoftAssert();
		int dbRecordIndex=0;
		
		InterviewRequestsOfGatekeeperTestHelper testHelper=new InterviewRequestsOfGatekeeperTestHelper();
		for(int i=0;i<totalPages;i++) {
		Response response = testHelper.interviewRequestsForGkByPageNo_PageSizeAndCandidateName(TOKEN, TOKEN_VALUE, interviewerId,pageSize,i);
		validateResponseToContinueTest(response, 200,"Interview requests are not fetched for gatekeeper = "+interviewerId +" and page No = "+i, true);
		JSONArray apriRecords = (JSONArray) restUtils.getValueFromResponse(response, "$.details.interviews[*]");
		int pageRecords=i==totalPages-1?lastPageInterviews:pageSize;
		Assert.assertEquals(apriRecords.size(), pageRecords,"Interview records are incorrect in api response for page No = "+i);
		test.log(Status.INFO, "Verifying details of all interview request for gatekeeper = "+interviewerId+" and page No = "+i);
		for(int j=0;j<apriRecords.size();j++) {
			Map actualValue=(Map)apriRecords.get(j);
			InterviewRequestDTO dbRecord = dbRecords.get(dbRecordIndex++);
			String interviewRequestIdInAPI=actualValue.get("requestId").toString();
			String interviewRequestIdInDB=dbRecord.getInterviewRequestId();
			test.log(Status.INFO, "Verifying details of interview request id = "+dbRecord.getInterviewRequestId()+" for record no = "+j);
			if(interviewRequestIdInAPI.equals(interviewRequestIdInDB)) {
				softAssert.assertEquals(actualValue.get(TIME_SLOT).toString(), dbRecord.getTimeSlot(),"Time slot is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+i);
				softAssert.assertEquals(actualValue.get(POSITION_NAME).toString(), dbRecord.getPositionName(),"Position name is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+i);
				softAssert.assertEquals(actualValue.get(SENIORITY_NAME).toString(), dbRecord.getSeniorityName(),"Seniority name is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+i);
				softAssert.assertEquals(actualValue.get(RECRUITER_NAME).toString(), dbRecord.getRecruiterName(),"Recruiter name is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+i);
				softAssert.assertEquals(actualValue.get(LOCATION).toString(), dbRecord.getLocation(),"Location is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+i);
				softAssert.assertEquals(actualValue.get(INTERVIEWER_ID).toString(), dbRecord.getInterviewerId(),"Interviewer id is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+i);
				softAssert.assertEquals(actualValue.get(RECRUITER_ID).toString(), dbRecord.getRecruiterId(),"Recruiter id is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+i);
				softAssert.assertEquals(actualValue.get(INTERVIEW_ID).toString(), dbRecord.getInterviewId(),"Interview id is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+i);
				softAssert.assertEquals(actualValue.get(ACTION).toString(), dbRecord.getInterviewRequestAction(),"Action is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+i);
				softAssert.assertEquals(actualValue.get(CANDIDATE_NAME).toString(), dbRecord.getCandidateName(),"Candidate name is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+i);
				softAssert.assertEquals(actualValue.get(CANDIDATE_EMAIL), dbRecord.getCandidateEmail(),"Candidate email is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+i);
				softAssert.assertEquals(actualValue.get(INTERVIEWER_NAME).toString(), dbRecord.getInterviewerName(),"Interviewer name is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+i);
				softAssert.assertEquals(actualValue.get(INTERVIEWER_EMAIL).toString(), dbRecord.getInterviewerEmail(),"Interviewer email is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+i);
				softAssert.assertEquals(actualValue.get(WAIT_TIME_COMPLETED).toString(), dbRecord.getWaitTimeCompleted(),"Wait time completed is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+i);
				softAssert.assertEquals(actualValue.get(INTERVIEW_DATE_AS_PER_TIME_ZONE).toString(), dbRecord.getInterviewDateAsPerTimezone(),"Interview date is not as per time zone in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+i);
				softAssert.assertEquals(actualValue.get(INTERVIEW_DATE).toString(), dbRecord.getInterviewDate(),"Interview date is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+i);
				String creationDateInAPI=actualValue.get(CREATION_DATE).toString(),creationDateInDB=dbRecord.getInterviewCreationDate();
				softAssert.assertEquals(creationDateInAPI,creationDateInDB,"Creation date is wrong in api response for interview request id "+interviewRequestIdInAPI);
				String lastModifiedInApi=actualValue.get(LAST_MODIFIED).toString(),lastModifiedInDB=dbRecord.getInterviewLastModificationDate();
				softAssert.assertEquals(lastModifiedInApi, lastModifiedInDB,"Last modified is wrong in api response for interview request id "+interviewRequestIdInAPI);
				test.log(Status.PASS, "Successfully verified details of interview request id = "+dbRecord.getInterviewRequestId()+" for record no = "+j);
			}else {
				softAssert.fail("Interview request id is wrong for record no = "+j+" [requestId in api : "+interviewRequestIdInAPI+", requestId in DB : "+interviewRequestIdInDB+"]" );
				test.log(Status.FAIL, "Interview request id is wrong for record no = "+j+" requestId in api : "+interviewRequestIdInAPI+", requestId in DB : "+interviewRequestIdInDB );
			}
		}
	}
		softAssert.assertAll();
		test.log(Status.PASS, "Successfully verified details of all interview request for interviwer = "+interviewerId);
	}

	/**
	 * To validate interview requests with details for specific gatekeeper by random page size and random page number
	 * @param data
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@Test(priority = 3, dataProvider = "interviewers", dataProviderClass = InterviewersDataProviders.class,  groups = {ExeGroups.Regression })
	public void verifyAllInterviewRequestsDetailsForGatekeeper_By_Page_Size_And_Page_No_Randomly(Object data) throws Exception {
		
		String interviewerId=data.toString();
		InterviewDBHelper dbHelper=new InterviewDBHelper();
		List<InterviewRequestDTO> dbRecords = dbHelper.getAllInterviewRequestsForGatekeeper(interviewerId);
		int pageSize=StaffingUtilities.getRandomNumberBetween(0, 100);
		int handledPazeSizeForZero=pageSize==0?100:pageSize;
		int totalInterviewsInDB=dbRecords.size();
		int totalPages=totalInterviewsInDB/handledPazeSizeForZero;
		int lastPageInterviews=totalInterviewsInDB%handledPazeSizeForZero;
		totalPages=lastPageInterviews==0?totalPages:totalPages+1;
		int randomPageNo=StaffingUtilities.getRandomNumberBetween(0, totalPages);
		int pageNoByIndex=randomPageNo==0?0:randomPageNo-1;
		SoftAssert softAssert = new SoftAssert();
		int dbRecordIndex=0;
		dbRecords=dbHelper.filterRecords(dbRecords, pageNoByIndex, pageSize);
		
		InterviewRequestsOfGatekeeperTestHelper testHelper=new InterviewRequestsOfGatekeeperTestHelper();

		Response response = testHelper.interviewRequestsForGkByPageNo_PageSizeAndCandidateName(TOKEN, TOKEN_VALUE, interviewerId,pageSize,pageNoByIndex);
		validateResponseToContinueTest(response, 200,"Interview requests are not fetched for gatekeeper = "+interviewerId +" and page No = "+pageNoByIndex, true);
		JSONArray apriRecords = (JSONArray) restUtils.getValueFromResponse(response, "$.details.interviews[*]");
		Assert.assertEquals(apriRecords.size(), dbRecords.size(),"Interview records are incorrect in api response for page No = "+pageNoByIndex);
		test.log(Status.INFO, "Verifying details of all interview request for gatekeeper = "+interviewerId+" and page No = "+pageNoByIndex);
		for(int j=0;j<apriRecords.size();j++) {
			Map actualValue=(Map)apriRecords.get(j);
			InterviewRequestDTO dbRecord = dbRecords.get(dbRecordIndex++);
			String interviewRequestIdInAPI=actualValue.get("requestId").toString();
			String interviewRequestIdInDB=dbRecord.getInterviewRequestId();
			test.log(Status.INFO, "Verifying details of interview request id = "+dbRecord.getInterviewRequestId()+" for record no = "+j);
			if(interviewRequestIdInAPI.equals(interviewRequestIdInDB)) {
				softAssert.assertEquals(actualValue.get(TIME_SLOT).toString(), dbRecord.getTimeSlot(),"Time slot is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(POSITION_NAME).toString(), dbRecord.getPositionName(),"Position name is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(SENIORITY_NAME).toString(), dbRecord.getSeniorityName(),"Seniority name is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(RECRUITER_NAME).toString(), dbRecord.getRecruiterName(),"Recruiter name is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(LOCATION).toString(), dbRecord.getLocation(),"Location is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(INTERVIEWER_ID).toString(), dbRecord.getInterviewerId(),"Interviewer id is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(RECRUITER_ID).toString(), dbRecord.getRecruiterId(),"Recruiter id is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(INTERVIEW_ID).toString(), dbRecord.getInterviewId(),"Interview id is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(ACTION).toString(), dbRecord.getInterviewRequestAction(),"Action is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(CANDIDATE_NAME).toString(), dbRecord.getCandidateName(),"Candidate name is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(CANDIDATE_EMAIL), dbRecord.getCandidateEmail(),"Candidate email is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(INTERVIEWER_NAME).toString(), dbRecord.getInterviewerName(),"Interviewer name is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(INTERVIEWER_EMAIL).toString(), dbRecord.getInterviewerEmail(),"Interviewer email is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(WAIT_TIME_COMPLETED).toString(), dbRecord.getWaitTimeCompleted(),"Wait time completed is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(INTERVIEW_DATE_AS_PER_TIME_ZONE).toString(), dbRecord.getInterviewDateAsPerTimezone(),"Interview date is not as per time zone in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(INTERVIEW_DATE).toString(), dbRecord.getInterviewDate(),"Interview date is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				String creationDateInAPI=actualValue.get(CREATION_DATE).toString(),creationDateInDB=dbRecord.getInterviewCreationDate();
				softAssert.assertEquals(creationDateInAPI,creationDateInDB,"Creation date is wrong in api response for interview request id "+interviewRequestIdInAPI);
				String lastModifiedInApi=actualValue.get(LAST_MODIFIED).toString(),lastModifiedInDB=dbRecord.getInterviewLastModificationDate();
				softAssert.assertEquals(lastModifiedInApi, lastModifiedInDB,"Last modified is wrong in api response for interview request id "+interviewRequestIdInAPI);
				test.log(Status.PASS, "Successfully verified details of interview request id = "+dbRecord.getInterviewRequestId()+" for record no = "+j);
			}else {
				softAssert.fail("Interview request id is wrong for record no = "+j+" [requestId in api : "+interviewRequestIdInAPI+", requestId in DB : "+interviewRequestIdInDB+"]" );
				test.log(Status.FAIL, "Interview request id is wrong for record no = "+j+" requestId in api : "+interviewRequestIdInAPI+", requestId in DB : "+interviewRequestIdInDB );
			}
		}

		softAssert.assertAll();
		test.log(Status.PASS, "Successfully verified details of all interview request for interviwer = "+interviewerId);
	}

	/**
	 * To validate interview requests with details for specific gatekeeper by random page size, random page number and candidate name
	 * @param data
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@Test(priority = 4, dataProvider = "interviewers", dataProviderClass = InterviewersDataProviders.class,  groups = {ExeGroups.Regression })
	public void verifyAllInterviewRequestsDetailsForGatekeeper_By_CandidateName(Object data) throws Exception {
		
		String interviewerId=data.toString();
		InterviewDBHelper dbHelper=new InterviewDBHelper();
		List<InterviewRequestDTO> dbRecords = dbHelper.getAllInterviewRequestsForGatekeeper(interviewerId);
		int pageSize=StaffingUtilities.getRandomNumberBetween(0, 100);
		int handledPazeSizeForZero=pageSize==0?100:pageSize;
		int totalInterviewsInDB=dbRecords.size();
		int totalPages=totalInterviewsInDB/handledPazeSizeForZero;
		int lastPageInterviews=totalInterviewsInDB%handledPazeSizeForZero;
		totalPages=lastPageInterviews==0?totalPages:totalPages+1;
		int randomPageNo=StaffingUtilities.getRandomNumberBetween(0, totalPages);
		int pageNoByIndex=randomPageNo==0?0:randomPageNo-1;
		SoftAssert softAssert = new SoftAssert();
		int dbRecordIndex=0;
		String candidateName="automation";
		dbRecords=dbHelper.filterRecords(dbRecords, pageNoByIndex, pageSize, candidateName);
		
		InterviewRequestsOfGatekeeperTestHelper testHelper=new InterviewRequestsOfGatekeeperTestHelper();

		Response response = testHelper.interviewRequestsForGkByPageNo_PageSizeAndCandidateName(TOKEN, TOKEN_VALUE, interviewerId,pageSize,pageNoByIndex);
		validateResponseToContinueTest(response, 200,"Interview requests are not fetched for gatekeeper = "+interviewerId +" and page No = "+pageNoByIndex, true);
		JSONArray apriRecords = (JSONArray) restUtils.getValueFromResponse(response, "$.details.interviews[*]");
		Assert.assertEquals(apriRecords.size(), dbRecords.size(),"Interview records are incorrect in api response for page No = "+pageNoByIndex);
		test.log(Status.INFO, "Verifying details of all interview request for gatekeeper = "+interviewerId+" and page No = "+pageNoByIndex);
		for(int j=0;j<apriRecords.size();j++) {
			Map actualValue=(Map)apriRecords.get(j);
			InterviewRequestDTO dbRecord = dbRecords.get(dbRecordIndex++);
			String interviewRequestIdInAPI=actualValue.get("requestId").toString();
			String interviewRequestIdInDB=dbRecord.getInterviewRequestId();
			test.log(Status.INFO, "Verifying details of interview request id = "+dbRecord.getInterviewRequestId()+" for record no = "+j);
			if(interviewRequestIdInAPI.equals(interviewRequestIdInDB)) {
				softAssert.assertEquals(actualValue.get(TIME_SLOT).toString(), dbRecord.getTimeSlot(),"Time slot is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(POSITION_NAME).toString(), dbRecord.getPositionName(),"Position name is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(SENIORITY_NAME).toString(), dbRecord.getSeniorityName(),"Seniority name is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(RECRUITER_NAME).toString(), dbRecord.getRecruiterName(),"Recruiter name is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(LOCATION).toString(), dbRecord.getLocation(),"Location is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(INTERVIEWER_ID).toString(), dbRecord.getInterviewerId(),"Interviewer id is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(RECRUITER_ID).toString(), dbRecord.getRecruiterId(),"Recruiter id is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(INTERVIEW_ID).toString(), dbRecord.getInterviewId(),"Interview id is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(ACTION).toString(), dbRecord.getInterviewRequestAction(),"Action is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(CANDIDATE_NAME).toString(), dbRecord.getCandidateName(),"Candidate name is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(CANDIDATE_EMAIL), dbRecord.getCandidateEmail(),"Candidate email is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(INTERVIEWER_NAME).toString(), dbRecord.getInterviewerName(),"Interviewer name is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(INTERVIEWER_EMAIL).toString(), dbRecord.getInterviewerEmail(),"Interviewer email is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(WAIT_TIME_COMPLETED).toString(), dbRecord.getWaitTimeCompleted(),"Wait time completed is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(INTERVIEW_DATE_AS_PER_TIME_ZONE).toString(), dbRecord.getInterviewDateAsPerTimezone(),"Interview date is not as per time zone in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				softAssert.assertEquals(actualValue.get(INTERVIEW_DATE).toString(), dbRecord.getInterviewDate(),"Interview date is wrong in api response for interview request id "+interviewRequestIdInAPI+" and page no = "+pageNoByIndex);
				String creationDateInAPI=actualValue.get(CREATION_DATE).toString(),creationDateInDB=dbRecord.getInterviewCreationDate();
				softAssert.assertEquals(creationDateInAPI,creationDateInDB,"Creation date is wrong in api response for interview request id "+interviewRequestIdInAPI);
				String lastModifiedInApi=actualValue.get(LAST_MODIFIED).toString(),lastModifiedInDB=dbRecord.getInterviewLastModificationDate();
				softAssert.assertEquals(lastModifiedInApi, lastModifiedInDB,"Last modified is wrong in api response for interview request id "+interviewRequestIdInAPI);
				test.log(Status.PASS, "Successfully verified details of interview request id = "+dbRecord.getInterviewRequestId()+" for record no = "+j);
			}else {
				softAssert.fail("Interview request id is wrong for record no = "+j+" [requestId in api : "+interviewRequestIdInAPI+", requestId in DB : "+interviewRequestIdInDB+"]" );
				test.log(Status.FAIL, "Interview request id is wrong for record no = "+j+" requestId in api : "+interviewRequestIdInAPI+", requestId in DB : "+interviewRequestIdInDB );
			}
		}

		softAssert.assertAll();
		test.log(Status.PASS, "Successfully verified details of all interview request for interviwer = "+interviewerId);
	}

	
	/**
	 * To validate all interview requests of gatekeeper api response for specific gatekeeper by exceeding page size
	 * @param data
	 * @throws Exception
	 */
	@Test(priority = 5, dataProvider = "single_interviewer", dataProviderClass = InterviewersDataProviders.class,  groups = {ExeGroups.Regression })
	public void verifyAllInterviewRequestsDetailsForGatekeeper_By_Exceeding_PageSize(Object data) throws Exception {
		
		String interviewerId=data.toString();
		InterviewRequestsOfGatekeeperTestHelper testHelper=new InterviewRequestsOfGatekeeperTestHelper();
		Response response = testHelper.interviewRequestsForGkByPageNo_PageSizeAndCandidateName(TOKEN, TOKEN_VALUE, interviewerId,101,0);
		validateResponseToContinueTest(response, 400,"Invalid status code for exceeding page size(>100) of gatekeeper = "+interviewerId , true);
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(restUtils.getValueFromResponse(response, STATUS), "Bad Request","Response status is incorrect for exceeding page size of gatekeeper = "+interviewerId );
		softAssert.assertEquals(restUtils.getValueFromResponse(response, MESSAGE),
				"Param pageSize cannot be greater than 100","Response message is incorrect for exceeding page size of gatekeeper = "+interviewerId );
		softAssert.assertAll();
		test.log(Status.PASS, "Api response is successfully verified for gatekeeper "+interviewerId+" by exceeding page size");
	}
	
	/**
	 * To validate all interview requests of gatekeeper api response for invalid header value
	 * @param data
	 * @throws Exception
	 */
	@Test(priority = 6, dataProvider = "single_interviewer", dataProviderClass = InterviewersDataProviders.class,  groups = {ExeGroups.Regression })
	public void verifyAllInterviewRequestsDetailsForGatekeeper_With_Invalid_Header_Value(Object data) throws Exception {
		
		String interviewerId=data.toString();
		InterviewRequestsOfGatekeeperTestHelper testHelper=new InterviewRequestsOfGatekeeperTestHelper();
		String headerValue=TOKEN_VALUE+"a";
		Response response = testHelper.interviewRequestsForGkByPageNo_PageSizeAndCandidateName(TOKEN, headerValue, interviewerId,0,0);
		validateResponseToContinueTest(response, 403,"Status code is wrong for invalid header value = "+headerValue +" and gatekeeper = "+interviewerId , true);
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(restUtils.getValueFromResponse(response, ERROR), "Forbidden","Response error is incorrect for invalid header value = "+headerValue +" and gatekeeper = "+interviewerId);
		softAssert.assertEquals(restUtils.getValueFromResponse(response, MESSAGE),
				"User is not having valid permission or role","Response message is incorrect for invalid header value = "+headerValue +" and gatekeeper = "+interviewerId);
		softAssert.assertEquals(restUtils.getValueFromResponse(response, PATH),AcceptOrRejectInterviewEndpoints.interviewRequests.split("\\?")[0],"Response details is incorrect for exceeding page size of gatekeeper = "+interviewerId );
		softAssert.assertAll();
		test.log(Status.PASS, "Api response is successfully verified for invalid header value = "+headerValue +" and gatekeeper = "+interviewerId);
	}
}
