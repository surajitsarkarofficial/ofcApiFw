package tests.testhelpers.submodules.interview;

import static utils.ExtentHelper.test;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import constants.submodules.LeaveConstants.LeaveConstants;
import constants.submodules.interview.InterviewConstants;
import database.LocationDBHelper;
import database.submodules.interview.InterviewDBHelper;
import database.submodules.leave.LeaveDBHelper;
import dto.submodules.interview.InterviewRequestDTO;
import io.restassured.response.Response;
import tests.testhelpers.StaffingTestHelper;
import tests.testhelpers.submodules.gatekeepers.features.SearchGatekeepersTestHelper;
import tests.testhelpers.submodules.interview.features.AcceptOrRejectInterviewTestHelper;
import tests.testhelpers.submodules.interview.features.CreateInterviewTestHelper;
import tests.testhelpers.submodules.interview.features.EvaluateInterviewTestHelper;
import tests.testhelpers.submodules.leave.LeaveTestHelper;
import utils.StaffingUtilities;
import utils.Utilities;

/**
 * @author deepakkumar.hadiya
 */

public class InterviewTestHelper extends StaffingTestHelper implements InterviewConstants, LeaveConstants{

	private int gatekeeperVerifiedForWaitTimeCompletedValue=0;
	private int gkNumber=0;
	InterviewDBHelper dbHelper;
	SoftAssert softAssert;
	
	public InterviewTestHelper() {
		dbHelper=dbHelper==null?new InterviewDBHelper():dbHelper;
	}
	
	/**
	 * Method to ignore all interview request
	 * @param interviewId
	 * @param gkList
	 * @param waitTimeCompletedVerificationPassMsgRequired
	 * @param waitAfterLastRerun
	 * @param acceptOneInterviewRequestRandomly
	 * @return {@link Map}
	 * @throws Exception
	 */
	public Map<String,Object> ignoreAllInterviewRequest(String interviewId, List<Integer> gkList,boolean waitTimeCompletedVerificationPassMsgRequired, boolean waitAfterLastRerun,boolean acceptOneInterviewRequestRandomly) throws Exception{
		Map<String,String> actionDetails=new LinkedHashMap<>();
		Map<String,Object> returnData=new LinkedHashMap<String, Object>();
		int totalGKCount =gkList.size();
		int rerun=totalGKCount/TOTAL_GK_FOR_RE_RUN;
		int lastRerunGkCount=totalGKCount%TOTAL_GK_FOR_RE_RUN;
		int totalRerunCount = lastRerunGkCount>0 ? rerun+1 : rerun;
		lastRerunGkCount=lastRerunGkCount==0?TOTAL_GK_FOR_RE_RUN:lastRerunGkCount;
		int expectedGKInDbAfterEachReRun=0;
		int gkNumberWhoAcceptInterviewRequest=totalGKCount>0?Utilities.getRandomNumberBetween(gkNumber, totalGKCount-1):0;
		int gkCountTillAcceptingInterviewRequest=0;
		List<String> gkListWithRequestSentStatus=null;
		String waitTimeCompletedValueAfterIgnoringRequest=TrueInDBFormat;
		
		Thread.sleep(8000);
		List<InterviewRequestDTO> interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
		if(acceptOneInterviewRequestRandomly) {	
			test.log(Status.INFO, "Gatekeeper = "+(gkNumberWhoAcceptInterviewRequest+1)+" accepting the interview request and remaining gatekeepers are ignoring interview request");
		}else {
			test.log(Status.INFO, "All gatekeepers are rejecting the interview request");
		}
		stopRerun:
		for (int i = 1; i <= totalRerunCount; i++) {
			test.log(Status.INFO,
					"All gatekeepers of batch = '"+i+"' ignore the interview requests");
			gkListWithRequestSentStatus=new ArrayList<String>();
			for (int k=0; k<interviewRequestDetailsList.size();k++) {
				InterviewRequestDTO interviewRequestDetails = interviewRequestDetailsList.get(k);
				String interviewerName = interviewRequestDetails.getInterviewerName();
				String interviewerId =interviewRequestDetails.getInterviewerId();
					if(gkNumber == k) {	
						verifyWaitTimeCompletedValueBeforePerformingActionOnInterviewRequestForEachGKInDB(interviewRequestDetails, waitTimeCompletedVerificationPassMsgRequired);
								if (acceptOneInterviewRequestRandomly && gkNumber == gkNumberWhoAcceptInterviewRequest) {
									interviewRequestAction(interviewId, interviewerId, interviewerName, ACCEPT);
									actionDetails.put(interviewerId, REQUEST_ACCEPTED);
									gkCountTillAcceptingInterviewRequest=interviewRequestDetailsList.size();
									break stopRerun;
								}
								interviewRequestAction(interviewId, interviewerId, interviewerName, IGNORE);
							actionDetails.put(interviewerId, REQUEST_SENT);
							gkListWithRequestSentStatus.add(interviewerId);
							gkNumber++;
						}
			}
			
			expectedGKInDbAfterEachReRun=totalRerunCount != i?expectedGKInDbAfterEachReRun + TOTAL_GK_FOR_RE_RUN:expectedGKInDbAfterEachReRun+lastRerunGkCount;
			verifyGatekeeperCountForBatch(gkNumber, expectedGKInDbAfterEachReRun,i);
			if (totalRerunCount != i) {
				waitForNextRerunAfterIgnoringInterviewRequest(interviewId,i);
			}else {
				if(waitAfterLastRerun) {
					waitForNextRerunAfterIgnoringInterviewRequest(interviewId,i);
				}else {
					waitTimeCompletedValueAfterIgnoringRequest=FalseInDBFormat;
					Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
				}
			}	
			interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
			verifyWaitTimeCompletedValueAfterIgnoringInterviewRequestForEachBatchInDB(interviewRequestDetailsList,waitTimeCompletedValueAfterIgnoringRequest, waitTimeCompletedVerificationPassMsgRequired, i);
			}
		if(!acceptOneInterviewRequestRandomly) {
			verifyGatekeeperCountForBatch(interviewRequestDetailsList.size(), totalGKCount);
		}
		returnData.put("actionDetails", actionDetails);
		returnData.put("totalRerunCount", totalRerunCount);
		returnData.put("gkCountTillAcceptingInterviewRequest", gkCountTillAcceptingInterviewRequest);
		returnData.put("gkListWithRequestSentStatus", gkListWithRequestSentStatus);
		return returnData;
	}
	
	/**
	 * Method to reject all interview request with validation
	 * @param interviewId
	 * @param gkList
	 * @param waitTimeCompletedVerificationPassMsgRequired
	 * @param acceptOneInterviewRequestRandomly
	 * @param waitTimeBeforeRejectingInterviewRequest
	 * @return {@link Map}
	 * @throws Exception
	 */
	public Map<String,Object> rejectAllInterviewRequest(String interviewId, List<Integer> gkList, boolean waitTimeCompletedVerificationPassMsgRequired,boolean acceptOneInterviewRequestRandomly, int... waitTimeBeforeRejectingInterviewRequest) throws Exception{
		Map<String,String> actionDetails=new LinkedHashMap<>();
		Map<String,Object> returnData=new LinkedHashMap<String, Object>();
		int totalGKCount =gkList.size();
		int rerun=totalGKCount/TOTAL_GK_FOR_RE_RUN;
		int lastRerunGkCount=totalGKCount%TOTAL_GK_FOR_RE_RUN;
		int totalRerunCount = lastRerunGkCount>0 ? rerun+1 : rerun;
		lastRerunGkCount=lastRerunGkCount==0?TOTAL_GK_FOR_RE_RUN:lastRerunGkCount;
		int expectedGKInDbAfterEachReRun=0;
		int gkNumberWhoAcceptInterviewRequest=totalGKCount>0?Utilities.getRandomNumberBetween(gkNumber, totalGKCount-1):0;
		int gkCountTillAcceptingInterviewRequest=0;
		
		List<InterviewRequestDTO> interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
			if(acceptOneInterviewRequestRandomly) {	
				test.log(Status.INFO, "Gatekeeper = "+(gkNumberWhoAcceptInterviewRequest+1)+" accepting the interview request and remaining gatekeepers are rejecting interview request");
			}else {
				test.log(Status.INFO, "All gatekeepers are rejecting the interview request");
			}
		stopRerun:	
		for (int i = 1; i <= totalRerunCount; i++) {
				for (int k=0; k<interviewRequestDetailsList.size();k++) {
					InterviewRequestDTO interviewRequestDetails = interviewRequestDetailsList.get(k);
					String interviewerName = interviewRequestDetails.getInterviewerName();
					String interviewerId =interviewRequestDetails.getInterviewerId();
						if(gkNumber == k) {
							verifyWaitTimeCompletedValueBeforePerformingActionOnInterviewRequestForEachGKInDB(interviewRequestDetails, waitTimeCompletedVerificationPassMsgRequired);
								if (acceptOneInterviewRequestRandomly && gkNumber == gkNumberWhoAcceptInterviewRequest) {
									interviewRequestAction(interviewId, interviewerId, interviewerName, ACCEPT);
									actionDetails.put(interviewerId, REQUEST_ACCEPTED);
									gkCountTillAcceptingInterviewRequest=interviewRequestDetailsList.size();
									break stopRerun;
								}
								if(waitTimeBeforeRejectingInterviewRequest.length>0 && k==interviewRequestDetailsList.size()-1) {
									int waitTimeBeforeRejectingInMin=Utilities.getRandomNumberBetween(waitTimeBeforeRejectingInterviewRequest[0], WAIT_TIME_FOR_RERUN_IN_MIN-1);
									test.log(Status.INFO, "Waiting for '"+waitTimeBeforeRejectingInMin+"' minute and then rejecting interview request");
									Thread.sleep(waitTimeBeforeRejectingInMin*60*1000);
								}
								interviewRequestAction(interviewId, interviewerId, interviewerName, REJECT);
								actionDetails.put(interviewerId, REQUEST_REJECTED);
								gkNumber++;
						}
				}
				if (totalRerunCount != i) {
					expectedGKInDbAfterEachReRun = expectedGKInDbAfterEachReRun + TOTAL_GK_FOR_RE_RUN;
				} else {
					expectedGKInDbAfterEachReRun = expectedGKInDbAfterEachReRun + lastRerunGkCount;
				}
				verifyGatekeeperCountForBatch(gkNumber, expectedGKInDbAfterEachReRun,i);
				waitForNextRerunAfterRejectingInterviewRequest(interviewId,i);
				interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
				verifyWaitTimeCompletedValueAfterIgnoringInterviewRequestForEachBatchInDB(interviewRequestDetailsList,TrueInDBFormat, waitTimeCompletedVerificationPassMsgRequired, i);
				}
			if(!acceptOneInterviewRequestRandomly) {
				verifyGatekeeperCountForBatch(interviewRequestDetailsList.size(), totalGKCount);
			}	
			returnData.put("actionDetails", actionDetails);
			returnData.put("totalRerunCount", totalRerunCount);
			returnData.put("gkCountTillAcceptingInterviewRequest", gkCountTillAcceptingInterviewRequest);
			return returnData;
	}
	
	/**
	 * Method to reject first interview request from each batch and ignore remaining interview request from each batch
	 * @param interviewId
	 * @param gkList
	 * @param waitTimeCompletedVerificationPassMsgRequired
	 * @param waitAfterLastRerun
	 * @return {@link Map}
	 * @throws Exception
	 */
	public Map<String,Object> firstGkRejectAndRemainingGkIgnoreInterviewRequest(String interviewId, List<Integer> gkList,boolean waitTimeCompletedVerificationPassMsgRequired, boolean waitAfterLastRerun) throws Exception{
		Map<String,String> actionDetails=new LinkedHashMap<>();
		Map<String,Object> returnData=new LinkedHashMap<String, Object>();
		int totalGKCount =gkList.size();
		int rerun=totalGKCount/TOTAL_GK_FOR_RE_RUN;
		int lastRerunGkCount=totalGKCount%TOTAL_GK_FOR_RE_RUN;
		int totalRerunCount = lastRerunGkCount>0 ? rerun+1 : rerun;
		lastRerunGkCount=lastRerunGkCount==0?TOTAL_GK_FOR_RE_RUN:lastRerunGkCount;
		int expectedGKInDbAfterEachReRun=0;
		String waitTimeCompletedValueAfterIgnoringRequest = TrueInDBFormat;
		
		Thread.sleep(8000);
		List<InterviewRequestDTO> interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
		 
		for (int i = 1; i <= totalRerunCount; i++) {
			test.log(Status.INFO,
					"First gatekeeper of batch = '"+i+"' rejects the interview requests and remaining gatekeepers ignore the interview request");
			for (int k=0; k<interviewRequestDetailsList.size();k++) {
				InterviewRequestDTO interviewRequestDetails = interviewRequestDetailsList.get(k);
				String interviewerName = interviewRequestDetails.getInterviewerName();
				String interviewerId =interviewRequestDetails.getInterviewerId();
					if(gkNumber == k) {	
						verifyWaitTimeCompletedValueBeforePerformingActionOnInterviewRequestForEachGKInDB(interviewRequestDetails, waitTimeCompletedVerificationPassMsgRequired);
								if ((gkNumber+1) % TOTAL_GK_FOR_RE_RUN == 1) {
									interviewRequestAction(interviewId, interviewerId, interviewerName, REJECT);
									actionDetails.put(interviewerId, REQUEST_REJECTED);
								} else {
									interviewRequestAction(interviewId, interviewerId, interviewerName, IGNORE);
									actionDetails.put(interviewerId, REQUEST_SENT);
								} 
							gkNumber++;
					}
			}
			
			if (totalRerunCount != i) {
				expectedGKInDbAfterEachReRun = expectedGKInDbAfterEachReRun + TOTAL_GK_FOR_RE_RUN;
				waitForNextRerunAfterIgnoringInterviewRequest(interviewId,i);

			}else {
				expectedGKInDbAfterEachReRun=expectedGKInDbAfterEachReRun+lastRerunGkCount;
				if(waitAfterLastRerun) {
					waitForNextRerunAfterIgnoringInterviewRequest(interviewId,i);
				}else {
					waitTimeCompletedValueAfterIgnoringRequest=FalseInDBFormat;
				}
			}	
			verifyGatekeeperCountForBatch(gkNumber, expectedGKInDbAfterEachReRun,i);
			interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
			verifyWaitTimeCompletedValueAfterIgnoringInterviewRequestForEachBatchInDB(interviewRequestDetailsList, waitTimeCompletedValueAfterIgnoringRequest, waitTimeCompletedVerificationPassMsgRequired, i);
			}
		verifyGatekeeperCountForBatch(interviewRequestDetailsList.size(), totalGKCount);
		returnData.put("actionDetails", actionDetails);
		returnData.put("totalRerunCount", totalRerunCount);
		return returnData;
	}

	/**
	 * Method to verify gatekeepers count for one batch or all batch
	 * @param actual
	 * @param expected
	 * @param batchNumber
	 */
	private void verifyGatekeeperCountForBatch(int actual, int expected, int... batchNumber) {
		
		String message=batchNumber.length>0?"batch no = "+batchNumber[0]:"all batch";
		
		if(actual==expected) {
			test.log(Status.PASS,"Gatekeepers count is correct after sending interview request to "+message);
		}else {
			String failMessage="Gatekeepers count is incorrect after sending interview request to "+message+" Expected : ["+expected+"] Actual : ["+actual+"]";
			test.log(Status.FAIL,failMessage);
			Assert.fail(failMessage);
		}
	}
	
	/**
	 * Method to verify 'wait_time_completed' flag in database after ignoring interview request by gatekeeper   
	 * @param interviewRequestDetailsList
	 * @param waitTimeCompletedValueAfterIgnoringRequest
	 * @param waitTimeCompletedVerificationPassMsgRequired
	 * @param batchNo
	 */
	private void verifyWaitTimeCompletedValueAfterIgnoringInterviewRequestForEachBatchInDB(List<InterviewRequestDTO> interviewRequestDetailsList,String waitTimeCompletedValueAfterIgnoringRequest,boolean waitTimeCompletedVerificationPassMsgRequired, int batchNo) {
		test.log(Status.INFO, "Verifying 'wait_time_completed' is updated in data base after rejecting/ignoring interview request for batch no = "+batchNo);
		for(int p=0;p<gkNumber;p++) {
			if(p==gatekeeperVerifiedForWaitTimeCompletedValue) {
				InterviewRequestDTO interviewRequestDetails = interviewRequestDetailsList.get(gatekeeperVerifiedForWaitTimeCompletedValue);
				if(interviewRequestDetails.getWaitTimeCompleted().equals(waitTimeCompletedValueAfterIgnoringRequest)) {
					if(waitTimeCompletedVerificationPassMsgRequired)
					test.log(Status.PASS,"'Wait_time_completed' value is correct in DB after rejecting/ignoring interview request by gatekeeper '"+(gatekeeperVerifiedForWaitTimeCompletedValue+1)+"' = "+interviewRequestDetails.getInterviewerId()+" : "+interviewRequestDetails.getInterviewerName());
				}else {
					String message="'Wait_time_completed' value is incorrect in DB after rejecting/ignoring interview request by gatekeeper '"+(gatekeeperVerifiedForWaitTimeCompletedValue+1)+"' = "+interviewRequestDetails.getInterviewerId()+" : "+interviewRequestDetails.getInterviewerName();
					test.log(Status.FAIL,message);
					softAssert.fail(message);
				}
				gatekeeperVerifiedForWaitTimeCompletedValue++;
			}
		}
		test.log(Status.PASS, "Successfully verified that 'wait_time_completed' is updated in data base after ignoring interview request for batch no = "+batchNo);
	}
	
	/**
	 * Method to verify 'wait_time_completed' flag in database before accepting, rejecting or ignoring interview request by gatekeeper
	 * @param interviewRequestDetails
	 * @param waitTimeCompletedVerificationPassMsgRequired
	 */
	private void verifyWaitTimeCompletedValueBeforePerformingActionOnInterviewRequestForEachGKInDB(InterviewRequestDTO interviewRequestDetails, boolean waitTimeCompletedVerificationPassMsgRequired) {
		if(interviewRequestDetails.getWaitTimeCompleted().equals(FalseInDBFormat)) {
			if(waitTimeCompletedVerificationPassMsgRequired) 
			test.log(Status.PASS,"'Wait_time_completed' value is correct in DB before accepting/rejecting/ignoring an interview request by gatekeeper '"+(gkNumber+1)+"' = "+interviewRequestDetails.getInterviewerId()+" : "+interviewRequestDetails.getInterviewerName());
		}else {
			String failMessage="'Wait_time_completed' value is incorrect in DB before accepting/rejecting/ignoring an interview request by gatekeeper '"+(gkNumber+1)+"' = "+interviewRequestDetails.getInterviewerId()+" : "+interviewRequestDetails.getInterviewerName();
			test.log(Status.FAIL,failMessage);
			softAssert.fail(failMessage);
		}
	}
	
	/**
	 * Method to wait for rerun after ignoring interview request
	 * @param interviewId
	 * @param batchNumber
	 * @throws Exception
	 */
	private void waitForNextRerunAfterIgnoringInterviewRequest(String interviewId, int batchNumber) throws Exception {
		test.log(Status.INFO,"Waiting for maximum '"+(WAIT_TIME_FOR_RERUN_IN_MIN+WAIT_TIME_FREQUENCY_FOR_RERUN_IN_MIN)+" minutes "+BUFFER_TIME_IN_SECONDS+" seconds' to ignore interview requests of batch no = "+ batchNumber);
		Thread.sleep(WAIT_TIME_FOR_RERUN_IN_MILLI_SECONDS);
		long waitTime=0;
		while(waitTime<WAIT_TIME_FREQUENCY_FOR_RERUN_IN_MILLI_SECONDS+BUFFER_TIME_IN_MILLI_SECONDS) {
			List<InterviewRequestDTO> interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
			if(interviewRequestDetailsList.size()>gkNumber) {
				break;
			}
			Thread.sleep(BUFFER_TIME_IN_MILLI_SECONDS);
			waitTime=waitTime+BUFFER_TIME_IN_MILLI_SECONDS;
		}
		waitTime=waitTime+WAIT_TIME_FOR_RERUN_IN_MILLI_SECONDS;
		test.log(Status.PASS,
				"Successfully waited for '"+(waitTime/60000)+" minutes "+((waitTime%60000)/1000)+" seconds' to ignore interview requests of batch no = "+ batchNumber);
	}
	
	/**
	 * Method to wait for rerun after rejecting interview request
	 * @param batchNo
	 * @param interviewId
	 * @throws Exception
	 */
	private void waitForNextRerunAfterRejectingInterviewRequest(String interviewId,int batchNo) throws Exception {
		test.log(Status.INFO,"Waiting for maximum '"+BUFFER_TIME_IN_SECONDS+" seconds' after rejecting interview request of batch no = "+ (batchNo));
		
		long waitTime=BUFFER_TIME_IN_MILLI_SECONDS/20;
		long bufferTime=waitTime;
		
		Thread.sleep(waitTime);
		
		while(waitTime<BUFFER_TIME_IN_MILLI_SECONDS) {
			List<InterviewRequestDTO> interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
			if(interviewRequestDetailsList.size()>gkNumber) {
				break;
			}
			Thread.sleep(bufferTime);
			waitTime=waitTime+bufferTime;
		}
	
		test.log(Status.PASS,
				"Successfully waited for '"+(waitTime/60000)+" minutes "+((waitTime%60000)/1000)+" seconds' after rejecting interview request of batch no = "+ batchNo);
		
	}

	
	/**
	 * Method to create new interview request
	 * @param data
	 * @return {@link String}
	 * @throws Exception
	 */
	public String createInterviewRequest(Map<Object, Object> data, SoftAssert softAssert) throws Exception {
		this.softAssert=softAssert;
		Reporter.log("Creating a new interview request for position = "+data.get(APPLY_POSITION)+", seniority = "+data.get(APPLY_SENIORITY)+" and location = "+data.get(LOCATION),true);
		test.log(Status.INFO, "Recruiter is creating a new interview request");
		CreateInterviewTestHelper createInterviewTestHelper = new CreateInterviewTestHelper();
		Response apiResponse = createInterviewTestHelper.createInterview(data);
		validateResponseToContinueTest(apiResponse, 201, "Unable to create interview request with valid test data",
				true);
		String interviewId = restUtils.getValueFromResponse(apiResponse, "$.details.interviewId").toString();
		test.log(Status.PASS, "Recruiter has successfully created new interview request = "+interviewId);
		Reporter.log("Created interview id = "+interviewId,true);
		return interviewId;
	}
	
	/**
	 * Method to get gatekeepers list for specified position, location and seniority 
	 * @param data
	 * @return {@link List}
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getGatekeepersList(Map<Object, Object> data) throws Exception {
		test.log(Status.INFO, "Searching gatekeepers for position = "+data.get(APPLY_POSITION)+", seniority = "+data.get(APPLY_SENIORITY)+" and location = "+data.get(LOCATION));
		SearchGatekeepersTestHelper gkHelper = new SearchGatekeepersTestHelper();
		Response responseForGk = gkHelper.getGatekeepersList(data);
		validateResponseToContinueTest(responseForGk, 200, "Unable to search gatekeepers with valid test data", true);
		List<Integer> gkList = (List<Integer>)restUtils.getValueFromResponse(responseForGk, "$.details[*].globarid");
		test.log(Status.PASS, "Total = "+gkList.size()+" gatekeepers identified : "+gkList);
		return gkList;
	}
	
	/**
	 * Method to accept, reject or ignore interview request
	 * @param interviewId
	 * @param interviewerId
	 * @param interviewerName
	 * @param action
	 * @throws Exception
	 */
	public void interviewRequestAction(String interviewId, String interviewerId, String interviewerName, String action) throws Exception {
		if(action.equalsIgnoreCase(ACCEPT)) {
			AcceptOrRejectInterviewTestHelper acceptInterviewTestHelper = new AcceptOrRejectInterviewTestHelper();
			Response response = acceptInterviewTestHelper.acceptOrRejectInterview(interviewId,
					interviewerId, TOKEN, TOKEN_VALUE, true);
			validateResponseToContinueTest(response, 200, "Gatekeeper '" + interviewerName + " - "
					+ interviewerId + "'is not able to accept interview request = " + interviewId,
					true);
			test.log(Status.PASS, "Interview request is successfully accepted by gatekeeper = '"+ interviewerName + " - " + interviewerId + "'");
		}else if(action.equalsIgnoreCase(REJECT)){
			AcceptOrRejectInterviewTestHelper acceptOrRejectInterviewTestHelper=new AcceptOrRejectInterviewTestHelper();
			Response response = acceptOrRejectInterviewTestHelper.acceptOrRejectInterview(interviewId, interviewerId,
					TOKEN, TOKEN_VALUE, false);
			validateResponseToContinueTest(response, 200,
					"Gatekeeper '" + interviewerName+" - "+interviewerId
							+ "'is not able to reject interview request = " + interviewId,
					true);
			test.log(Status.PASS, "Interview request is successfully rejected by gatekeeper = '"+interviewerName+" - "+interviewerId+"'");
		}else if(action.equalsIgnoreCase(IGNORE)){
			test.log(Status.PASS, "Interview request is successfully ignored by gatekeeper = '"+interviewerName+" - "+interviewerId+"'");
		}else {
			Assert.fail("Please select proper action for interview request");
		}	
	}

	/**
	 * Method to apply for leave
	 * @param gatekeepersWithLeaveDetails
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void applyForLeave(Map<Integer,String> gatekeepersWithLeaveDetails) throws Exception {
			LeaveTestHelper leaveTestHelper = new LeaveTestHelper();
			LeaveDBHelper dbHelper=new LeaveDBHelper();
			for(Integer gatekeeper:gatekeepersWithLeaveDetails.keySet()) {
				Map dataForApi=new HashMap<>();
				String[] leaveDate=gatekeepersWithLeaveDetails.get(gatekeeper).split(":");
				String dateFrom=leaveDate[0];
				String dateTo=leaveDate[1];
				String globalId=dbHelper.getGlobalIdFromGloberId(gatekeeper.toString());
				dataForApi.put(DATE_FROM, dateFrom);
				dataForApi.put(DATE_TO, dateTo);
				dataForApi.put(GLOBAL_ID, globalId);
				dataForApi.put(HEADER_KEY,TOKEN);
				dataForApi.put(HEADER_VALUE, TOKEN_VALUE);
				dataForApi.put(LABEL, "Automation "+RandomStringUtils.randomAlphabetic(Utilities.getRandomNumberBetween(5, 20)));
				dataForApi.put(STATUS, APPLIED);
				test.log(Status.INFO, "Gatekeeper = "+gatekeeper+" & GlobalId = "+globalId+" is applying for a leave from "+dateFrom+" to "+dateTo);
				Response response = leaveTestHelper.applyForLeave(dataForApi);
				validateResponseToContinueTest(response, 201,"Leave is not applied successfully for gatekeeper = "+gatekeeper ,true);
				String message="Leave from "+dateFrom+" to "+dateTo+" is applied successfully for gatekeeper = "+gatekeeper+" and globalId = "+globalId;
				Reporter.log(message, true);
				test.log(Status.PASS, message);
			}
	}
	
	/**
	 * Method to cancel leave
	 * @param gatekeepersWithLeaveDetails
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void cancelLeave(Map<Integer,String> gatekeepersWithLeaveDetails) throws Exception {
			LeaveTestHelper leaveTestHelper = new LeaveTestHelper();
			LeaveDBHelper dbHelper=new LeaveDBHelper();
			for(Integer gatekeeper:gatekeepersWithLeaveDetails.keySet()) {
				Map dataForApi=new HashMap<>();
				String[] leaveDate=gatekeepersWithLeaveDetails.get(gatekeeper).split(":");
				String dateFrom=leaveDate[0];
				String dateTo=leaveDate[1];
				String globalId=dbHelper.getGlobalIdFromGloberId(gatekeeper.toString());
				dataForApi.put(DATE_FROM, dateFrom);
				dataForApi.put(DATE_TO, dateTo);
				dataForApi.put(GLOBAL_ID, globalId);
				dataForApi.put(HEADER_KEY,TOKEN);
				dataForApi.put(HEADER_VALUE, TOKEN_VALUE);
				test.log(Status.INFO, "Gatekeeper = "+gatekeeper+" & GlobalId = "+globalId+" is cancelling a leave for date from "+dateFrom+" to "+dateTo);
				Response response = leaveTestHelper.cancelLeave(dataForApi);
				validateResponseToContinueTest(response, 204,"Leave is not cancelled for gatekeeper = "+gatekeeper ,true);
				String message="Leave from "+dateFrom+" to "+dateTo+" is cancelled successfully for gatekeeper = "+gatekeeper+" and globalId = "+globalId;
				Reporter.log(message, true);
				test.log(Status.PASS, message);
			}
	}
	/**
	 * Reject all interview request without verification
	 * @param interviewId
	 * @param gkList
	 * @return {@link List}
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List rejectAllInterviewRequest(String interviewId, List<Integer> gkList) throws Exception{
		int totalGKCount =gkList.size();
		int rerun=totalGKCount/TOTAL_GK_FOR_RE_RUN;
		int lastRerunGkCount=totalGKCount%TOTAL_GK_FOR_RE_RUN;
		int totalRerunCount = lastRerunGkCount>0 ? rerun+1 : rerun;
		lastRerunGkCount=lastRerunGkCount==0?TOTAL_GK_FOR_RE_RUN:lastRerunGkCount;
		int expectedGKInDbAfterEachReRun=0;
		List<Integer> gatekeepers= new ArrayList<Integer>();
		List<InterviewRequestDTO> interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
		for (int i = 1; i <= totalRerunCount; i++) {
				for (int k=0; k<interviewRequestDetailsList.size();k++) {
					InterviewRequestDTO interviewRequestDetails = interviewRequestDetailsList.get(k);
					String interviewerName = interviewRequestDetails.getInterviewerName();
					String interviewerId =interviewRequestDetails.getInterviewerId();
						if(gkNumber == k) {
								interviewRequestAction(interviewId, interviewerId, interviewerName, REJECT);
								gkNumber++;
								gatekeepers.add(Integer.valueOf(interviewerId));
						}
				}
				if (totalRerunCount != i) {
					expectedGKInDbAfterEachReRun = expectedGKInDbAfterEachReRun + TOTAL_GK_FOR_RE_RUN;
				} else {
					expectedGKInDbAfterEachReRun = expectedGKInDbAfterEachReRun + lastRerunGkCount;
				}
				waitForNextRerunAfterRejectingInterviewRequest(interviewId,i);
				interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
				}
			
			return gatekeepers;
	}
	
	/**
	 * Method to wait till interview date gets passed (maximum waiting time is 13 min)
	 * @param interviewDate
	 * @param dateFormat
	 * @throws Exception
	 * @author deepakkumar.hadiya 
	 */
	public void waitTillInterviewDateGetPassed(String interviewDate,String dateFormat) throws Exception {
		long waitTime=0;
		while(waitTime<WAIT_TIME_FOR_RERUN_IN_MILLI_SECONDS+WAIT_TIME_FREQUENCY_FOR_RERUN_IN_MILLI_SECONDS) {
			String currentDate=StaffingUtilities.getCurrentDateWithTimeZone(dateFormat, DATABASE_TIMEZONE);
			if(StaffingUtilities.firstDateIsAfterSecondDate(dateFormat, currentDate, interviewDate)) {
				break;
			}
			Thread.sleep(BUFFER_TIME_IN_MILLI_SECONDS);
			waitTime=waitTime+BUFFER_TIME_IN_MILLI_SECONDS;
		}
		test.log(Status.PASS,
				"Successfully waited for '"+(waitTime/60000)+" minutes "+((waitTime%60000)/1000)+" seconds' to pass the interview date");
	}
	
	/** Get date in millisecond for mentioned day of the week
	 * @apiNote DAY : MONDAY_OF_PREVIOUS_WEEK, SUNDAY_OF_CURRENT_WEEK, MONDAY_OF_CURRENT_WEEK, SUNDAY_OF_NEXT_WEEK
	 * @param day
	 * @return
	 */
	public long getDateInMilliSecForDay(int day) {
	Calendar cal = Calendar.getInstance();
	cal.setTimeZone(TimeZone.getTimeZone("UTC"));
	int mondayNo = cal.get(Calendar.DAY_OF_MONTH) - cal.get(Calendar.DAY_OF_WEEK) + day;
	cal.set(Calendar.DAY_OF_MONTH, mondayNo);
	return cal.toInstant().atZone(ZoneOffset.UTC)
	.withHour(0)
	.withMinute(0)
	.withSecond(0)
	.withNano(0)
	.toInstant().toEpochMilli();
	}
	
	/** Get current day of the week
	 * @param day
	 * @return
	 */
	public int getCurrentDay() {
	Calendar cal = Calendar.getInstance();
	cal.setTimeZone(TimeZone.getTimeZone("UTC"));
	return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * To accept interview request for specific gatekeeper
	 * @param interviewId
	 * @param interviewerId
	 * @param gkList
	 * @return {@link Map}
	 * @throws Exception
	 */
	public Map<String,Object> acceptInterviewRequest(String interviewId, String interviewerId ,List<Integer> gkList) throws Exception{
		Map<String,String> actionDetails=new LinkedHashMap<>();
		Map<String,Object> returnData=new LinkedHashMap<String, Object>();
		int totalGKCount =gkList.size();
		int rerun=totalGKCount/TOTAL_GK_FOR_RE_RUN;
		int lastRerunGkCount=totalGKCount%TOTAL_GK_FOR_RE_RUN;
		int totalRerunCount = lastRerunGkCount>0 ? rerun+1 : rerun;
		lastRerunGkCount=lastRerunGkCount==0?TOTAL_GK_FOR_RE_RUN:lastRerunGkCount;
		int expectedGKInDbAfterEachReRun=0;
		int gkCountTillAcceptingInterviewRequest=0;
		gkNumber=0;
		List<InterviewRequestDTO> interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
		stopRerun:	
		for (int i = 1; i <= totalRerunCount; i++) {
				for (int k=0; k<interviewRequestDetailsList.size();k++) {
					InterviewRequestDTO interviewRequestDetails = interviewRequestDetailsList.get(k);
					String interviewerName = interviewRequestDetails.getInterviewerName();
					String interviewerIdFromDB =interviewRequestDetails.getInterviewerId();
						if(gkNumber == k) {
								if (interviewerIdFromDB.equals(interviewerId)) {
									interviewRequestAction(interviewId, interviewerIdFromDB, interviewerName, ACCEPT);
									actionDetails.put(interviewerIdFromDB, REQUEST_ACCEPTED);
									gkCountTillAcceptingInterviewRequest=interviewRequestDetailsList.size();
									break stopRerun;
								}
								interviewRequestAction(interviewId, interviewerIdFromDB, interviewerName, REJECT);
								actionDetails.put(interviewerIdFromDB, REQUEST_REJECTED);
								gkNumber++;
						}
				}
				if (totalRerunCount != i) {
					expectedGKInDbAfterEachReRun = expectedGKInDbAfterEachReRun + TOTAL_GK_FOR_RE_RUN;
				} else {
					expectedGKInDbAfterEachReRun = expectedGKInDbAfterEachReRun + lastRerunGkCount;
				}
				verifyGatekeeperCountForBatch(gkNumber, expectedGKInDbAfterEachReRun,i);
				waitForNextRerunAfterRejectingInterviewRequest(interviewId,i);
				interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
				}	
			returnData.put("actionDetails", actionDetails);
			returnData.put("totalRerunCount", totalRerunCount);
			returnData.put("gkCountTillAcceptingInterviewRequest", gkCountTillAcceptingInterviewRequest);
			return returnData;
	}
	
	/**
	 * To evaluate accepted interview request
	 * @param interviewId
	 * @param interviewerId
	 * @throws Exception
	 */
	public void evaluateInterview(String interviewId,String interviewerId) throws Exception {
		EvaluateInterviewTestHelper evaluateInterviewTestHelper=new EvaluateInterviewTestHelper();
		Response response = evaluateInterviewTestHelper.evaluateInterview(interviewId);
		validateResponseToContinueTest(response, 201, "Interviewer '"+interviewerId+"' is not able to evaluate interview request with valid data for interview id = "+interviewId, true);
	}
	
	/**
	 * Update interview date if given interview date is on public holiday
	 * @param data
	 * @return {@link String}
	 * @throws Exception
	 */
	public String updateInterviewDateIfItIsOnHoliday(Map<Object,Object> data) throws Exception {
		String interviewDateInMilliSec = data.get(INTERVIEW_DATE).toString();
		String interviewDate = StaffingUtilities.convertMilliSecondIntoDateWithTimeZone(interviewDateInMilliSec, DATABASE_DATE_FORMAT, DATABASE_TIMEZONE);
		LocationDBHelper locationDBHelper=new InterviewDBHelper();
		List<String> holidayList = locationDBHelper.getHolidayListForLocation(data.get("location").toString());
		while(holidayList.contains(interviewDate.split(" ")[0]+" 00:00:00")) {
			interviewDate=Utilities.getFutureDate(DATABASE_DATE_FORMAT, Utilities.getRandomNumberBetween(1, 180));
		}
		data.put(INTERVIEW_DATE, StaffingUtilities.convertDateIntoMilliSecond(interviewDate, DATABASE_DATE_FORMAT));
		return interviewDate;
	}
	
}


