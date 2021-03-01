package tests.testcases.submodules.globers.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import database.submodules.globers.features.GloberHistoryDBHelper;
import dataproviders.submodules.globers.GlobersDataProvider;
import io.restassured.response.Response;
import tests.testcases.submodules.globers.GlobersBaseTest;
import tests.testhelpers.submodules.globers.features.GloberHistoryTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;

/**
 * 
 * @author akshata.dongare
 *
 */
public class GloberHistoryTests extends GlobersBaseTest{
	RestUtils restUtils = new RestUtils();
	GloberHistoryDBHelper globerHistoryDBHelper = new GloberHistoryDBHelper();
	Response globerHistoryResponse;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("Glober history");
	}
	
	/**
	 * This method is used to verify bench start date history
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "staffingUser", dataProviderClass = GlobersDataProvider.class, enabled = true,groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void globerBenchStartDateHistory(String user) throws Exception {
		GloberHistoryTestHelper globerHistoryTestHelper = new GloberHistoryTestHelper(user);
		String globalId = globerHistoryDBHelper.getGlobalIdOfTPGlober("Glober");
	
		globerHistoryResponse = globerHistoryTestHelper.getHistoryResponseFromGlobalId("Glober", globalId);
		validateResponseToContinueTest(globerHistoryResponse, 200, "Glober history page is not available", true);
		
		net.minidev.json.JSONArray benchStartDatesResponse = com.jayway.jsonpath.JsonPath.read(globerHistoryResponse.asString(),"details.staffingdetails.positionChangeHistoryList.[?(@.projectName=='Talent Pool' && @.action=='ASSIGNMENT_START')].date");
		List<String> listOfBenchStartDatesDb = globerHistoryTestHelper.getBenchStartDateDb(globalId);
		
		assertEquals(benchStartDatesResponse.size(), listOfBenchStartDatesDb.size(),"Mismatch in db and response bench start date count");
		assertTrue(listOfBenchStartDatesDb.containsAll(benchStartDatesResponse),"Mismatch in db and response bench start date list");
		test.log(Status.PASS, "Bench start date history verified successfully.");
	}
	
	/**
	 * This method is used to verify out of company date history
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "staffingUser", dataProviderClass = GlobersDataProvider.class, enabled = true,groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void globerOutCompanyHistory(String user) throws Exception {
		GloberHistoryTestHelper globerHistoryTestHelper = new GloberHistoryTestHelper(user);
		String globerId = globerHistoryDBHelper.getOutOfCompanyGloberId();
		
		globerHistoryResponse = globerHistoryTestHelper.getHistoryResponseFromGloberId("Glober", globerId);
		validateResponseToContinueTest(globerHistoryResponse, 200, "Glober history page is not available", true);
		
		net.minidev.json.JSONArray OutOfCompanyDatesResponse = com.jayway.jsonpath.JsonPath.read(globerHistoryResponse.asString(),"details.staffingdetails.positionChangeHistoryList.[?(@.projectName=='OUT OF COMPANY')].date");
		List<String> listOfOutOfCompanyDatesDb = globerHistoryTestHelper.getOutOfCompanyDateDB(globerId);
		
		assertEquals(OutOfCompanyDatesResponse.size(),listOfOutOfCompanyDatesDb.size(), "Mismatch in db and response out of company date count");
		assertTrue(listOfOutOfCompanyDatesDb.containsAll(OutOfCompanyDatesResponse),"Mismatch in db and response out of company date list");
		test.log(Status.PASS, "Out of company date history verified successfully.");
	}
	
	/**
	 * This method is used to verify training history
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "staffingUser", dataProviderClass = GlobersDataProvider.class, enabled = true, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void globerTrainingHistory(String user) throws Exception {
		GloberHistoryTestHelper globerHistoryTestHelper = new GloberHistoryTestHelper(user);
		String globerId = globerHistoryDBHelper.getTrainingCompletedGloberId();
		
		globerHistoryResponse = globerHistoryTestHelper.getHistoryResponseFromGloberId("Glober", globerId);
		validateResponseToContinueTest(globerHistoryResponse, 200, "Glober history page is not available", true);
		
		net.minidev.json.JSONArray trainingDatesResponse = com.jayway.jsonpath.JsonPath.read(globerHistoryResponse.asString(),"details.staffingdetails.positionChangeHistoryList.[?(@.action=='COMPLETED_TRAININGS')].date");
		List<String> listOfGloberTrainingDatesDb = globerHistoryTestHelper.getGloberTrainingDatesDB(globerId);
		
		assertEquals(trainingDatesResponse.size(),listOfGloberTrainingDatesDb.size(), "Mismatch in db and response trainings date count");	
		test.log(Status.PASS, "Training date history verified successfully.");
	}
	
	/**
	 * This method is used to verify globant academy training history
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "staffingUser", dataProviderClass = GlobersDataProvider.class, enabled = true, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void globerGlobantAcademyTrainingHistory(String user) throws Exception {
		GloberHistoryTestHelper globerHistoryTestHelper = new GloberHistoryTestHelper(user);
		String globerId = globerHistoryDBHelper.getGlobantAcademyTrainingGloberId();

		globerHistoryResponse = globerHistoryTestHelper.getHistoryResponseFromGloberId("Glober", globerId);
		validateResponseToContinueTest(globerHistoryResponse, 200, "Glober history page is not available", true);
		
		net.minidev.json.JSONArray globantAcademytTrainingDatesResponse = com.jayway.jsonpath.JsonPath.read(globerHistoryResponse.asString(),"details.staffingdetails.positionChangeHistoryList.[?(@.action=='GLOBANT_ACADEMY')].date");
		List<String> listOfGlobantAcademyTrainingDb = globerHistoryTestHelper.getGloberAcademyTrainingDatesDb(globerId);
		
		assertEquals(globantAcademytTrainingDatesResponse.size(), listOfGlobantAcademyTrainingDb.size(),"Mismatch in db and response trainings date count");
		assertTrue(listOfGlobantAcademyTrainingDb.containsAll(globantAcademytTrainingDatesResponse),"Mismatch in db and response trainings date list");
		test.log(Status.PASS, "Globant academy training date history verified successfully.");
	}
		
	/**
	 * This method is used to verify glober goals defined dates
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "staffingUser", dataProviderClass = GlobersDataProvider.class, enabled = true, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void globerGoaldDefinedHistory(String user) throws Exception {
		GloberHistoryTestHelper globerHistoryTestHelper = new GloberHistoryTestHelper(user);
		String globerId = globerHistoryDBHelper.getGloberIdAccordingToAction("GOALS_DEFINED");
		
		globerHistoryResponse = globerHistoryTestHelper.getHistoryResponseFromGloberId("Glober", globerId);
		validateResponseToContinueTest(globerHistoryResponse, 200, "Glober history page is not available", true);
		
		net.minidev.json.JSONArray goalsDefinedDatesResponse = com.jayway.jsonpath.JsonPath.read(globerHistoryResponse.asString(),"details.staffingdetails.positionChangeHistoryList.[?(@.action=='GOALS_DEFINED')].date");
		List<String> listOfGoalsDefinedDatesDb = globerHistoryTestHelper.getDbDates(globerId,"GOALS_DEFINED");
		
		assertEquals(goalsDefinedDatesResponse.size(),listOfGoalsDefinedDatesDb.size(),"Mismatch in db and response goals defined date count");
		assertTrue(listOfGoalsDefinedDatesDb.containsAll(goalsDefinedDatesResponse),"Mismatch in db and response goals defined date list");
		test.log(Status.PASS, "Goals defined date history verified successfully.");
	}
	
	/**
	 * This method is used to verify glober goals setting dates
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "staffingUser", dataProviderClass = GlobersDataProvider.class, enabled = true, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void globerGoalsSettingHistory(String user) throws Exception {
		GloberHistoryTestHelper globerHistoryTestHelper = new GloberHistoryTestHelper(user);
		String globerId = globerHistoryDBHelper.getGloberIdAccordingToAction("GOALS_SETTING");
		
		globerHistoryResponse = globerHistoryTestHelper.getHistoryResponseFromGloberId("Glober", globerId);
		validateResponseToContinueTest(globerHistoryResponse, 200, "Glober history page is not available", true);
		
		net.minidev.json.JSONArray goalsSettingDatesResponse = com.jayway.jsonpath.JsonPath.read(globerHistoryResponse.asString(),"details.staffingdetails.positionChangeHistoryList..[?(@.action=='GOALS_SETTING')].date");
		List<String> listOfGoalsSettingDatesDb = globerHistoryTestHelper.getDbDates(globerId,"GOALS_SETTING");
		
		assertEquals(goalsSettingDatesResponse.size(),listOfGoalsSettingDatesDb.size(), "Mismatch in db and response goals setting date count");
		assertTrue(listOfGoalsSettingDatesDb.containsAll(goalsSettingDatesResponse),"Mismatch in db and response goals setting date list");
		test.log(Status.PASS, "Goals setting date history verified successfully.");
	}
		
	/**
	 * This method is used to verify glober replied dates history
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "staffingUser", dataProviderClass = GlobersDataProvider.class, enabled = true, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void globerRepliedHistory(String user) throws Exception {
		GloberHistoryTestHelper globerHistoryTestHelper = new GloberHistoryTestHelper(user);
		String globerId = globerHistoryDBHelper.getGloberIdAccordingToAction("GLOBER_REPLIED");
		
		globerHistoryResponse = globerHistoryTestHelper.getHistoryResponseFromGloberId("Glober", globerId);
		validateResponseToContinueTest(globerHistoryResponse, 200, "Glober history page is not available", true);
		
		net.minidev.json.JSONArray globerRepliedDatesResponse = com.jayway.jsonpath.JsonPath.read(globerHistoryResponse.asString(),"details.staffingdetails.positionChangeHistoryList.[?(@.action=='GLOBER_REPLIED')].date");
		List<String> listOfGloberRepliedDbDates = globerHistoryTestHelper.getDbDates(globerId,"GLOBER_REPLIED");
		
		assertEquals(globerRepliedDatesResponse.size(),listOfGloberRepliedDbDates.size(), "Mismatch in db and response glober replied date count" );
		assertTrue(listOfGloberRepliedDbDates.containsAll(globerRepliedDatesResponse), "Mismatch in db and response glober replied date list");
		test.log(Status.PASS, "Glober replied date history verified successfully.");
	}
			
	/**
	 * This method is used to verify glober autoassessment dates history
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "staffingUser", dataProviderClass = GlobersDataProvider.class, enabled = true, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void globerAutoassessmentHistory(String user) throws Exception {
		GloberHistoryTestHelper globerHistoryTestHelper = new GloberHistoryTestHelper(user);
		String globerId = globerHistoryDBHelper.getGloberIdAccordingToAction("GLOBER_AUTOASSESSMENT");
	
		globerHistoryResponse = globerHistoryTestHelper.getHistoryResponseFromGloberId("Glober", globerId);
		validateResponseToContinueTest(globerHistoryResponse, 200, "Glober history page is not available", true);
		
		net.minidev.json.JSONArray globerAutoassessmentDatesResponse = com.jayway.jsonpath.JsonPath.read(globerHistoryResponse.asString(),"details.staffingdetails.positionChangeHistoryList.[?(@.action=='GLOBER_AUTOASSESSMENT')].date");
		List<String> listOfGloberAutoassessmentDbDates = globerHistoryTestHelper.getDbDates(globerId,"GLOBER_AUTOASSESSMENT");
		
		assertEquals(globerAutoassessmentDatesResponse.size(),listOfGloberAutoassessmentDbDates.size(), "Mismatch in db and response glober autoassessment date count" );
		assertTrue(listOfGloberAutoassessmentDbDates.containsAll(globerAutoassessmentDatesResponse), "Mismatch in db and response glober autoassessment date list");
		test.log(Status.PASS, "Glober autoassessment date history verified successfully.");
	}
		
	/**
	 * This method is used to verify glober pending notification dates history
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "staffingUser", dataProviderClass = GlobersDataProvider.class, enabled = true, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void globerPendingNotificationHistory(String user) throws Exception {
		GloberHistoryTestHelper globerHistoryTestHelper = new GloberHistoryTestHelper(user);
		String globerId = globerHistoryDBHelper.getGloberIdAccordingToAction("PENDING_GLOBER_NOTIFICATION");
		globerHistoryResponse = globerHistoryTestHelper.getHistoryResponseFromGloberId("Glober", globerId);
		validateResponseToContinueTest(globerHistoryResponse, 200, "Glober history page is not available", true);
		
		net.minidev.json.JSONArray pendingGloberNotificationDatesResponse = com.jayway.jsonpath.JsonPath.read(globerHistoryResponse.asString(),"details.staffingdetails.positionChangeHistoryList.[?(@.action=='PENDING_GLOBER_NOTIFICATION')].date");
		List<String> listOfGloberPendingNotificationDbDates = globerHistoryTestHelper.getDbDates(globerId,"PENDING_GLOBER_NOTIFICATION");
		
		assertEquals(pendingGloberNotificationDatesResponse.size(),listOfGloberPendingNotificationDbDates.size(), "Mismatch in db and response glober pending notification date count" );
		assertTrue(listOfGloberPendingNotificationDbDates.containsAll(pendingGloberNotificationDatesResponse), "Mismatch in db and response glober pending notification date list");
		test.log(Status.PASS, "Glober pending notification history verified successfully.");
	}
	
	/**
	 * This method is used to verify glober manager assessment dates history
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "staffingUser", dataProviderClass = GlobersDataProvider.class, enabled = true, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void globerManagerAssessmentHistory(String user) throws Exception {
		GloberHistoryTestHelper globerHistoryTestHelper = new GloberHistoryTestHelper(user);
		String globerId = globerHistoryDBHelper.getGloberIdAccordingToAction("MANAGER_ASSESSMENT");
		
		globerHistoryResponse = globerHistoryTestHelper.getHistoryResponseFromGloberId("Glober", globerId);
		validateResponseToContinueTest(globerHistoryResponse, 200, "Glober history page is not available", true);
		
		net.minidev.json.JSONArray managerAssessmentDatesResponse = com.jayway.jsonpath.JsonPath.read(globerHistoryResponse.asString(),"details.staffingdetails.positionChangeHistoryList.[?(@.action=='MANAGER_ASSESSMENT')].date");
		List<String> listOfGloberManagerAssessmentDbDates = globerHistoryTestHelper.getDbDates(globerId,"MANAGER_ASSESSMENT");
		
		assertEquals(managerAssessmentDatesResponse.size(),listOfGloberManagerAssessmentDbDates.size(), "Mismatch in db and response glober manager assessment date count" );
		assertTrue(listOfGloberManagerAssessmentDbDates.containsAll(managerAssessmentDatesResponse), "Mismatch in db and response glober manager assessmentdate list");
		test.log(Status.PASS, "Glober manager assessment date history verified successfully.");
	}
	
	/**
	 * This method is used to verify glober join dates history
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "staffingUser", dataProviderClass = GlobersDataProvider.class, enabled = true, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void globerJoinDateHistory(String user) throws Exception {
		GloberHistoryTestHelper globerHistoryTestHelper = new GloberHistoryTestHelper(user);
		String globerId = globerHistoryDBHelper.getRandomGloberId();
		
		globerHistoryResponse = globerHistoryTestHelper.getHistoryResponseFromGloberId("Glober", globerId);
		validateResponseToContinueTest(globerHistoryResponse, 200, "Glober history page is not available", true);
		
		net.minidev.json.JSONArray GloberJoinDatesResponse = com.jayway.jsonpath.JsonPath.read(globerHistoryResponse.asString(),"details.staffingdetails.positionChangeHistoryList.[?(@.action=='GLOBER_JOINED')].date");
		List<String> listOfGloberJoiningDbDates = globerHistoryTestHelper.getGloberJoiningDatesDb(globerId);
		
		assertEquals(GloberJoinDatesResponse.size(),listOfGloberJoiningDbDates.size(), "Mismatch in db and response glober joining date count" );
		assertTrue(listOfGloberJoiningDbDates.containsAll(GloberJoinDatesResponse), "Mismatch in db and response glober joining list");
		test.log(Status.PASS, "Glober joining date history verified successfully.");
	}
	
	/**
	 * This method is used to verify glober project feedback dates history
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "staffingUser", dataProviderClass = GlobersDataProvider.class, enabled = true, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void globerProjectFeedbackDateHistory(String user) throws Exception {
		GloberHistoryTestHelper globerHistoryTestHelper = new GloberHistoryTestHelper(user);
		String globerId = globerHistoryDBHelper.getProjectFeedbackGloberId();
		
		globerHistoryResponse = globerHistoryTestHelper.getHistoryResponseFromGloberId("Glober", globerId);
		validateResponseToContinueTest(globerHistoryResponse, 200, "Glober history page is not available", true);
		
		net.minidev.json.JSONArray projectFeedbackDatesResponse = com.jayway.jsonpath.JsonPath.read(globerHistoryResponse.asString(),"details.staffingdetails.positionChangeHistoryList.[?(@.action=='PROJECT_FEEDBACK')].date");
		List<String> listOfGloberProjectFeedbackDbDates = globerHistoryTestHelper.getGloberProjectFeedbackDb(globerId);
		
		assertTrue(listOfGloberProjectFeedbackDbDates.containsAll(projectFeedbackDatesResponse), "Mismatch in db and response project feedback list");
		test.log(Status.PASS, "Glober project feedback date history verified successfully.");
	}
	
	/**
	 * This method is used to verify glober performance assessment dates history
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "staffingUser", dataProviderClass = GlobersDataProvider.class, enabled = true, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void globerPerformaceAssessmentDateHistory(String user) throws Exception {
		GloberHistoryTestHelper globerHistoryTestHelper = new GloberHistoryTestHelper(user);
		String globerId = globerHistoryDBHelper.getPerformanceAssessmentGloberId();
		
		globerHistoryResponse = globerHistoryTestHelper.getHistoryResponseFromGloberId("Glober", globerId);
		validateResponseToContinueTest(globerHistoryResponse, 200, "Glober history page is not available", true);
		
		net.minidev.json.JSONArray performanceAssessmentDatesResponse = com.jayway.jsonpath.JsonPath.read(globerHistoryResponse.asString(),"details.staffingdetails.positionChangeHistoryList.[?(@.action=='ASSESSMENT_CLOSED')].date");
		List<String> listOfPerformanceAssessmentDbDates = globerHistoryTestHelper.getPerformanceAssessmentDbDates(globerId);
		
		assertEquals(performanceAssessmentDatesResponse.size(),listOfPerformanceAssessmentDbDates.size(), "Mismatch in db and response glober performance assessment date count" );
		assertTrue(listOfPerformanceAssessmentDbDates.containsAll(performanceAssessmentDatesResponse), "Mismatch in db and response performance assessment list");
		test.log(Status.PASS, "Glober performance assessment date history verified successfully.");
	}
	
	/**
	 * This method is used to verify Recategorization dates history
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "staffingUser", dataProviderClass = GlobersDataProvider.class, enabled = true, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void globerRecategorizationDateHistory(String user) throws Exception {
		GloberHistoryTestHelper globerHistoryTestHelper = new GloberHistoryTestHelper(user);
		String globerId  = globerHistoryDBHelper.getRandomGloberId();
		String recategorizationId = globerHistoryDBHelper.getRecategorizationId(globerId);
		if(recategorizationId==null) {
			test.log(Status.PASS, "Recategorization of the glober is not happened");
		}else {
			globerHistoryResponse = globerHistoryTestHelper.getHistoryResponseFromGloberId("Glober", globerId);
			validateResponseToContinueTest(globerHistoryResponse, 200, "Glober history page is not available", true);
			
			net.minidev.json.JSONArray recategorizationDatesResponse = com.jayway.jsonpath.JsonPath.read(globerHistoryResponse.asString(),"details.staffingdetails.positionChangeHistoryList.[?(@.action=='RECATEGORISATION')].date");
			List<String> listOfRecategorizationDbDates = globerHistoryTestHelper.getRecategorizationDatesDb(recategorizationId);
			
			assertTrue(listOfRecategorizationDbDates.containsAll(recategorizationDatesResponse), "Mismatch in db and response Recategorization date list");
			test.log(Status.PASS, "Glober Recategorization date history verified successfully.");
		}
	}
}
