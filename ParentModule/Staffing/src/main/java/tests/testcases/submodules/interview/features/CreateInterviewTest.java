package tests.testcases.submodules.interview.features;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import database.submodules.interview.InterviewDBHelper;
import dataproviders.submodules.interview.features.CreateInterviewDataProvider;
import dto.submodules.interview.InterviewDTO;
import endpoints.submodules.interview.features.CreateInterviewEndpoints;
import io.restassured.response.Response;
import tests.testcases.submodules.interview.InterviewBaseTest;
import tests.testhelpers.submodules.interview.features.CreateInterviewTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * 
 * @author deepakkumar.hadiya
 *
 */

public class CreateInterviewTest extends InterviewBaseTest{

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("Recruiter creates a new interview request");
	}

	/**
	 * This test is to verify Create interview request api is returning interview id
	 * @param data
	 * @throws Exception
	 */
	@Test(priority=0, dataProvider = "validTestData", dataProviderClass = CreateInterviewDataProvider.class, groups = {
			ExeGroups.Regression, ExeGroups.Sanity})
	public void createInterviewRequest_PositiveScenario(Map<Object, Object> data) throws Exception {

		CreateInterviewTestHelper createInterviewTestHelper=new CreateInterviewTestHelper();
		Response apiResponse = createInterviewTestHelper.createInterview(data);
		
		validateResponseToContinueTest(apiResponse, 201, "Unable to create interview request with valid test data", true);
		test.log(Status.PASS, "Interview Id is created successfully");
		
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(restUtils.getValueFromResponse(apiResponse, "message"), "Success","Response message is incorrect for valid data");
		String interviewId = restUtils.getValueFromResponse(apiResponse, "$.details.interviewId").toString();
		InterviewDBHelper helper=new InterviewDBHelper();
		InterviewDTO interviewDetailsInDB = helper.getInterviewDetails(interviewId);
		softAssert.assertEquals(interviewDetailsInDB.getRecruiterId(),data.get(RECRUITER_ID) ,"Recruiter id is not updated in database");
		softAssert.assertEquals(interviewDetailsInDB.getCandidateName(), data.get(CANDIDATE_NAME) ,"Candidate name is not updated in database");
		softAssert.assertEquals(interviewDetailsInDB.getLocation(), data.get(LOCATION) ,"Location name is not updated in database");
		softAssert.assertEquals(interviewDetailsInDB.getAppliedSenority(), data.get(APPLY_SENIORITY) ,"Applied Seniority value is not updated in database");
		softAssert.assertEquals(interviewDetailsInDB.getHrFeedback(), data.get(HR_FEEDBACK) ,"HrFeedback is not updated in database");
		softAssert.assertEquals(interviewDetailsInDB.getAppliedPosition(),  data.get(APPLY_POSITION),"ApplyPosition is not updated in database");
		softAssert.assertEquals(interviewDetailsInDB.getCandidateGlobalId(), data.get(CANDIDATE_GLOBAL_ID).toString() ,"CandidateGlobalId is not updated in database");
		softAssert.assertEquals(interviewDetailsInDB.getInterviewtype(),  data.get(INTERVIEW_TYPE),"InterviewType is not updated in database");
		softAssert.assertEquals(interviewDetailsInDB.getCurriculum(), data.get(CURRICULUM) ,"Curriculum is not updated in database");
		softAssert.assertEquals(interviewDetailsInDB.getCandidateEmail(), data.get(CANDIDATE_EMAIL) ,"CandidateEmail is not updated in database");
		softAssert.assertAll();
		test.log(Status.PASS, "Details of Interview id ="+interviewId+" is successfully verified in database");

	}
	
	/**
	 * This test is to verify Create interview request api is returning appropriate response on entering wrong url
	 * @param data
	 * @throws Exception
	 */
	@Test(priority=1, dataProvider = "valid_TestData_With_Single_Iteration", dataProviderClass = CreateInterviewDataProvider.class, groups = {
			ExeGroups.Regression})
	public void createInterviewRequest_With_InvalidUrl(Map<Object, Object> data) throws Exception {

		CreateInterviewTestHelper createInterviewTestHelper=new CreateInterviewTestHelper();
		Response apiResponse = createInterviewTestHelper.createInterview_With_InvalidUrl(data);
		validateResponseToContinueTest(apiResponse, 403, "Status code is incorrect for invalid Url", true);
		
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(restUtils.getValueFromResponse(apiResponse, "error"), "Forbidden","Response error is incorrect for invalid url");
		softAssert.assertEquals(restUtils.getValueFromResponse(apiResponse, "message"), "User is not having valid permission or role","Response message is incorrect for invalid url");
		softAssert.assertEquals(restUtils.getValueFromResponse(apiResponse, "path"), "/glow/interviewservice/technicalInterview/","Path in response body is incorrect for invalid url");
		softAssert.assertAll();
		test.log(Status.PASS, "Response is successfully verified for invalid Url");

	}	

	/**
	 * This test is to verify Create interview request api is returning appropriate error message for wrong parameter key in json body 
	 * @param data
	 * @throws Exception
	 */
	@Test(priority=2, dataProvider = "invalidKey_for_JSON_Body_Parameter", dataProviderClass = CreateInterviewDataProvider.class, groups = {
			ExeGroups.Regression})
	public void createInterviewRequest_Using_JsonBody_With_InvalidParameterKey(Map<Object, Object> data) throws Exception {
		
		CreateInterviewTestHelper createInterviewTestHelper=new CreateInterviewTestHelper();
		
		Response apiResponse = createInterviewTestHelper.createInterview_With_InvalidParamterKeyInJsonBody(data);
		String invalidKeyName=data.get("invalidParameterKey").toString();
		validateResponseToContinueTest(apiResponse, 400, "Status code is incorrect for invalid parameter key '"+invalidKeyName+"' in json body", true);
		
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(restUtils.getValueFromResponse(apiResponse, "status"), "Bad Request","Response status is incorrect for invalid parameter key '"+invalidKeyName+"' in json body");
		softAssert.assertEquals(restUtils.getValueFromResponse(apiResponse, "message"), data.get("message"),"Response message is incorrect for invalid parameter key '"+invalidKeyName+"' in json body");
		softAssert.assertAll();
		test.log(Status.PASS, "Response is successfully verified for invalid parameter key '"+invalidKeyName+"' in json body");

	}	

	/**
	 * This test is to verify Create interview request api is returning appropriate error message for wrong parameter value in json body
	 * @param data
	 * @throws Exception
	 */
	@Test(priority=3, dataProvider = "invalidParameterValue_for_JSON_Body", dataProviderClass = CreateInterviewDataProvider.class, groups = {
			ExeGroups.Regression})
	public void createInterviewRequest_Using_JsonBody_With_InvalidParameterValue(Map<Object, Object> data) throws Exception {
		
		CreateInterviewTestHelper createInterviewTestHelper=new CreateInterviewTestHelper();
		
		Response apiResponse = createInterviewTestHelper.createInterview_With_InvalidValueOfParameterInRequestBody(data);
		String parameterKey=data.get("parameterKey").toString();
		Object parameterValue=data.get("parameterValue");
		Object invalidValueOfParameterKey=parameterValue==null?"":parameterValue;
		validateResponseToContinueTest(apiResponse, 400, "Status code is incorrect for parameter '"+parameterKey+"' and invalid value '"+invalidValueOfParameterKey+"' in json body", true);
		
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(restUtils.getValueFromResponse(apiResponse, "status"), "Bad Request","Response status is incorrect for parameter '"+parameterKey+"' and  invalid value '"+invalidValueOfParameterKey+"' in json body");
		softAssert.assertEquals(restUtils.getValueFromResponse(apiResponse, "message"), data.get(MESSAGE).toString().replace("_", " ")+invalidValueOfParameterKey,"Response message is incorrect for parameter '"+parameterKey+"' and  invalid value '"+invalidValueOfParameterKey+"' in json body");
		softAssert.assertAll();
		test.log(Status.PASS, "Response is successfully verified for parameter '"+parameterKey+"' and invalid value '"+invalidValueOfParameterKey+"'");

	}
	
	/**
	 * This test is to verify Create interview request api is returning appropriate response for invalid header key
	 * @param data 
	 * @throws Exception
	 */
	@Test(priority=4, dataProvider = "invalidHeaderKey", dataProviderClass = CreateInterviewDataProvider.class, groups = {
			ExeGroups.Regression})
	public void verifyCreateInterviewRequest_with_invalidHeaderKey(Map<Object, Object> data) throws Exception {

		CreateInterviewTestHelper createInterviewTestHelper = new CreateInterviewTestHelper();
		Response response =createInterviewTestHelper.createInterview(data);

		validateResponseToContinueTest(response, 401, "Status code is incorrect for invalid header key", true);

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "error"), "Unauthorized","Response status is incorrect for invalid header key");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"),
				"Token not found","Response message is incorrect for invalid header key");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "path"), CreateInterviewEndpoints.createInterview,"Path in response body is incorrect for invalid header key");
		softAssert.assertAll();

		test.log(Status.PASS, "Create interview Request api is returning appropriate response for invalid header key");
	}

	/**
	 * This test is to verify Create interview request api is returning appropriate response for invalid header value
	 * @param data
	 * @throws Exception
	 */
	@Test(priority=5, dataProvider = "invalidHeaderValue", dataProviderClass = CreateInterviewDataProvider.class, groups = {
			ExeGroups.Regression})
	public void verifyCreateInterviewRequest_with_invalidHeaderValue(Map<Object, Object> data)
			throws Exception {

		CreateInterviewTestHelper createInterviewTestHelper = new CreateInterviewTestHelper();
		Response response =createInterviewTestHelper.createInterview(data);

		validateResponseToContinueTest(response, 403, "Status code is incorrect for invalid header value", true);

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "error"), "Forbidden","Response status is incorrect for invalid header value");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "message"), "User is not having valid permission or role","Response message is incorrect for invalid header value");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "path"), CreateInterviewEndpoints.createInterview,"Path in response body is incorrect for invalid header value");
		softAssert.assertAll();

		test.log(Status.PASS, "Create interview Request api is returning appropriate response for invalid header value");
	}
	
	/**
	 * This test is to verify Create interview request api for all locations
	 * @param data
	 * @throws Exception
	 */
	@Test(priority=0, dataProvider = "validTestDataForAllLocations", dataProviderClass = CreateInterviewDataProvider.class)
	public void createInterviewRequest_ForAllLocations(Map<Object, Object> data) throws Exception {

		CreateInterviewTestHelper createInterviewTestHelper=new CreateInterviewTestHelper();
		Response apiResponse = createInterviewTestHelper.createInterview(data);
		
		validateResponseToContinueTest(apiResponse, 201, "Unable to create interview request with valid test data", true);
		test.log(Status.PASS, "Interview Id is created successfully");
		
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(restUtils.getValueFromResponse(apiResponse, "message"), "Success","Response message is incorrect for valid data");
		String interviewId = restUtils.getValueFromResponse(apiResponse, "$.details.interviewId").toString();
		InterviewDBHelper helper=new InterviewDBHelper();
		InterviewDTO interviewDetailsInDB = helper.getInterviewDetails(interviewId);
		softAssert.assertEquals(interviewDetailsInDB.getRecruiterId(),data.get(RECRUITER_ID) ,"Recruiter id is not updated in database");
		softAssert.assertEquals(interviewDetailsInDB.getCandidateName(), data.get(CANDIDATE_NAME) ,"Candidate name is not updated in database");
		softAssert.assertEquals(interviewDetailsInDB.getLocation(), data.get(LOCATION) ,"Location name is not updated in database");
		softAssert.assertEquals(interviewDetailsInDB.getAppliedSenority(), data.get(APPLY_SENIORITY) ,"Applied Seniority value is not updated in database");
		softAssert.assertEquals(interviewDetailsInDB.getHrFeedback(), data.get(HR_FEEDBACK) ,"HrFeedback is not updated in database");
		softAssert.assertEquals(interviewDetailsInDB.getAppliedPosition(),  data.get(APPLY_POSITION),"ApplyPosition is not updated in database");
		softAssert.assertEquals(interviewDetailsInDB.getCandidateGlobalId(), data.get(CANDIDATE_GLOBAL_ID).toString() ,"CandidateGlobalId is not updated in database");
		softAssert.assertEquals(interviewDetailsInDB.getInterviewtype(),  data.get(INTERVIEW_TYPE),"InterviewType is not updated in database");
		softAssert.assertEquals(interviewDetailsInDB.getCurriculum(), data.get(CURRICULUM) ,"Curriculum is not updated in database");
		softAssert.assertEquals(interviewDetailsInDB.getCandidateEmail(), data.get(CANDIDATE_EMAIL) ,"CandidateEmail is not updated in database");
		softAssert.assertAll();
		test.log(Status.PASS, "Details of Interview id ="+interviewId+" is successfully verified in database");

	}
	
	/**
	 * This test is to verify Create interview request api for all positions
	 * @param data
	 * @throws Exception
	 */
	@Test(priority=0, dataProvider = "validTestDataForAllPositions", dataProviderClass = CreateInterviewDataProvider.class)
	public void createInterviewRequest_ForAllPositions(Map<Object, Object> data) throws Exception {

		CreateInterviewTestHelper createInterviewTestHelper=new CreateInterviewTestHelper();
		Response apiResponse = createInterviewTestHelper.createInterview(data);
		
		validateResponseToContinueTest(apiResponse, 201, "Unable to create interview request with valid test data", true);
		test.log(Status.PASS, "Interview Id is created successfully");
		
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(restUtils.getValueFromResponse(apiResponse, "message"), "Success","Response message is incorrect for valid data");
		String interviewId = restUtils.getValueFromResponse(apiResponse, "$.details.interviewId").toString();
		InterviewDBHelper helper=new InterviewDBHelper();
		InterviewDTO interviewDetailsInDB = helper.getInterviewDetails(interviewId);
		softAssert.assertEquals(interviewDetailsInDB.getRecruiterId(),data.get(RECRUITER_ID) ,"Recruiter id is not updated in database");
		softAssert.assertEquals(interviewDetailsInDB.getCandidateName(), data.get(CANDIDATE_NAME) ,"Candidate name is not updated in database");
		softAssert.assertEquals(interviewDetailsInDB.getLocation(), data.get(LOCATION) ,"Location name is not updated in database");
		softAssert.assertEquals(interviewDetailsInDB.getAppliedSenority(), data.get(APPLY_SENIORITY) ,"Applied Seniority value is not updated in database");
		softAssert.assertEquals(interviewDetailsInDB.getHrFeedback(), data.get(HR_FEEDBACK) ,"HrFeedback is not updated in database");
		softAssert.assertEquals(interviewDetailsInDB.getAppliedPosition(),  data.get(APPLY_POSITION),"ApplyPosition is not updated in database");
		softAssert.assertEquals(interviewDetailsInDB.getCandidateGlobalId(), data.get(CANDIDATE_GLOBAL_ID).toString() ,"CandidateGlobalId is not updated in database");
		softAssert.assertEquals(interviewDetailsInDB.getInterviewtype(),  data.get(INTERVIEW_TYPE),"InterviewType is not updated in database");
		softAssert.assertEquals(interviewDetailsInDB.getCurriculum(), data.get(CURRICULUM) ,"Curriculum is not updated in database");
		softAssert.assertEquals(interviewDetailsInDB.getCandidateEmail(), data.get(CANDIDATE_EMAIL) ,"CandidateEmail is not updated in database");
		softAssert.assertAll();
		test.log(Status.PASS, "Details of Interview id ="+interviewId+" is successfully verified in database");

	}	
}
