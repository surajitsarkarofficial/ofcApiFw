package tests.testcases.myTeam.features;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import dataproviders.myTeam.features.MarkAndUnmarkDataProviders;
import io.restassured.response.Response;
import payloads.myTeam.features.MarkUnmarkTlPayloadHelper;
import tests.testcases.myTeam.MyTeamBaseTest;
import tests.testhelpers.myTeam.features.MarkAndUnmarkTlTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;

/**
 * @author imran.khan
 *
 */

public class MarkAndUnmarkTlTests extends MyTeamBaseTest {
	String message, status = null;
	int podId, projectId;
	String[] data;
	
	RestUtils restUtils = new RestUtils();

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("MarkAndUnmarkTests");
	}
	
	/**
	 * This test will mark and unmark glober as TL
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsOcampo", dataProviderClass = MarkAndUnmarkDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void markAndUnmarkTL(String userName, String userId) throws Exception {

		MarkAndUnmarkTlTestHelper markUnmarkHelper = new MarkAndUnmarkTlTestHelper(userName);
		String projectIdAndPodIdOfGlober = markUnmarkHelper.getProjectIdAndPodId(userName, userId);
		data = projectIdAndPodIdOfGlober.split(":");
		projectId = Integer.parseInt(data[0]);
		podId = Integer.parseInt(data[1]);
		List<Object> dataForMarkUnmarkGlober = markUnmarkHelper.getRequiredTestDataForMarkUnmarkTL(projectId, podId);
		JSONObject requestParams = new MarkUnmarkTlPayloadHelper().createMarkUnmarkTlPayload(dataForMarkUnmarkGlober);
		Response response = markUnmarkHelper.markTechLead(requestParams,podId);
		status = (String) restUtils.getValueFromResponse(response, "status");
		
		assertTrue(status.contains("success"),"The message is not success");
		
		response = markUnmarkHelper.unmarkTechLead(requestParams,podId);
		status = (String) restUtils.getValueFromResponse(response, "status");
		message = (String) restUtils.getValueFromResponse(response, "message");
		
		assertTrue(status.contains("success"),"The message is not success");
		assertTrue(message.contains("success"),"The message is not success");
		test.log(Status.PASS, "Mark and Unmark glober as TL successfully");
		
	}
	
	/**
	 * This test will mark a glober as TL and verify at pod view
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsOcampo", dataProviderClass = MarkAndUnmarkDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void markTlAndVerifyAtPodView(String userName, String userId) throws Exception {

		MarkAndUnmarkTlTestHelper markUnmarkHelper = new MarkAndUnmarkTlTestHelper(userName);
		String projectIdAndPodIdOfGlober = markUnmarkHelper.getProjectIdAndPodId(userName, userId);
		data = projectIdAndPodIdOfGlober.split(":");
		projectId = Integer.parseInt(data[0]);
		podId = Integer.parseInt(data[1]);
		List<Object> dataForMarkUnmarkGlober = markUnmarkHelper.getRequiredTestDataForMarkUnmarkTL(projectId, podId);
		JSONObject requestParams = new MarkUnmarkTlPayloadHelper().createMarkUnmarkTlPayload(dataForMarkUnmarkGlober);
		markUnmarkHelper.markTechLead(requestParams,podId);
		Boolean markStatusAtPod=markUnmarkHelper.getGloberTlStatusInPod(projectId,dataForMarkUnmarkGlober.get(1).toString(),podId);
		
		assertTrue(markStatusAtPod,"The TL is not marked at pod view");
		test.log(Status.PASS, "Marked TL is showing at Pod View Successfully");
		
	}
	
	/**
	 * This test will mark a glober as TL and verify at project view
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsOcampo", dataProviderClass = MarkAndUnmarkDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void markTlAndVerifyAtProjectView(String userName, String userId) throws Exception {

		MarkAndUnmarkTlTestHelper markUnmarkHelper = new MarkAndUnmarkTlTestHelper(userName);
		String projectIdAndPodIdOfGlober = markUnmarkHelper.getProjectIdAndPodId(userName, userId);
		data = projectIdAndPodIdOfGlober.split(":");
		projectId = Integer.parseInt(data[0]);
		podId = Integer.parseInt(data[1]);
		List<Object> dataForMarkUnmarkGlober = markUnmarkHelper.getRequiredTestDataForMarkUnmarkTL(projectId, podId);
		JSONObject requestParams = new MarkUnmarkTlPayloadHelper().createMarkUnmarkTlPayload(dataForMarkUnmarkGlober);
		markUnmarkHelper.markTechLead(requestParams,podId);
		Boolean markStatusAtPod=markUnmarkHelper.getGloberTlStatusInProject(projectId,dataForMarkUnmarkGlober.get(1).toString());
		
		assertTrue(markStatusAtPod,"The TL is not marked at project view");
		test.log(Status.PASS, "Marked TL is showing at Pod View Successfully");
		
	}
	
	/**
	 * This test will mark a glober as TL and verify at client view
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "ddRolDetailsWithId", dataProviderClass = MarkAndUnmarkDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void markTlAndVerifyAtClientView(String userName, String userId) throws Exception {

		MarkAndUnmarkTlTestHelper markUnmarkHelper = new MarkAndUnmarkTlTestHelper(userName);
		String projectIdAndPodIdOfGlober = markUnmarkHelper.getProjectIdAndPodId(userName, userId);
		data = projectIdAndPodIdOfGlober.split(":");
		projectId = Integer.parseInt(data[0]);
		podId = Integer.parseInt(data[1]);
		List<Object> dataForMarkUnmarkGlober = markUnmarkHelper.getRequiredTestDataForMarkUnmarkTL(projectId, podId);
		JSONObject requestParams = new MarkUnmarkTlPayloadHelper().createMarkUnmarkTlPayload(dataForMarkUnmarkGlober);
		markUnmarkHelper.markTechLead(requestParams,podId);
		int clientId = markUnmarkHelper.getClientIdByProjectId(userId, projectId);
		Boolean markStatusAtPod=markUnmarkHelper.getGloberTlStatusInClient(clientId,projectId,dataForMarkUnmarkGlober.get(1).toString());
		
		assertTrue(markStatusAtPod,"The TL is not marked at client view");
		test.log(Status.PASS, "Marked TL is showing at client view Successfully");
		
	}
	
	/**
	 * This test will mark a glober as TL for wrong project Id
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsOcampo", dataProviderClass = MarkAndUnmarkDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void markTlForWrongProjectId(String userName, String userId) throws Exception {

		MarkAndUnmarkTlTestHelper markUnmarkHelper = new MarkAndUnmarkTlTestHelper(userName);
		String projectIdAndPodIdOfGlober = markUnmarkHelper.getProjectIdAndPodId(userName, userId);
		data = projectIdAndPodIdOfGlober.split(":");
		projectId = Integer.parseInt(data[0]);
		podId = Integer.parseInt(data[1]);
		List<Object> dataForMarkUnmarkGlober = markUnmarkHelper.getRequiredTestDataForMarkUnmarkTL(projectId, podId);
		dataForMarkUnmarkGlober.set(0, -1);
		JSONObject requestParams = new MarkUnmarkTlPayloadHelper().createMarkUnmarkTlPayload(dataForMarkUnmarkGlober);
		Response response = markUnmarkHelper.markTechLead(requestParams,podId);
		status = (String) restUtils.getValueFromResponse(response, "status");
		
		assertTrue(status.contains("Invalid Project Id"), "Invalid Project Id message should come");
		test.log(Status.PASS, "Invalid Project Id message coming Successfully");
		
	}
	
	/**
	 * This test will mark a glober as TL for wrong glober Id
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsOcampo", dataProviderClass = MarkAndUnmarkDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void markTlForWrongGloberId(String userName, String userId) throws Exception {

		MarkAndUnmarkTlTestHelper markUnmarkHelper = new MarkAndUnmarkTlTestHelper(userName);
		String projectIdAndPodIdOfGlober = markUnmarkHelper.getProjectIdAndPodId(userName, userId);
		data = projectIdAndPodIdOfGlober.split(":");
		projectId = Integer.parseInt(data[0]);
		podId = Integer.parseInt(data[1]);
		List<Object> dataForMarkUnmarkGlober = markUnmarkHelper.getRequiredTestDataForMarkUnmarkTL(projectId, podId);
		dataForMarkUnmarkGlober.set(1, 21);
		JSONObject requestParams = new MarkUnmarkTlPayloadHelper().createMarkUnmarkTlPayload(dataForMarkUnmarkGlober);
		Response response = markUnmarkHelper.markTechLead(requestParams,podId);
		status = (String) restUtils.getValueFromResponse(response, "status");
		message = (String) restUtils.getValueFromResponse(response, "message");
		
		assertTrue(status.contains("fail"), "The status should be fail");
		assertTrue(message.contains("${glober.exist.failure}"), "globerId does not exist message should come");
		test.log(Status.PASS, "glober does not exist message coming Successfully");
		
	}
	
	/**
	 * This test will mark a glober as TL for wrong pod Id
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsOcampo", dataProviderClass = MarkAndUnmarkDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void markTlForWrongPodId(String userName, String userId) throws Exception {

		MarkAndUnmarkTlTestHelper markUnmarkHelper = new MarkAndUnmarkTlTestHelper(userName);
		String projectIdAndPodIdOfGlober = markUnmarkHelper.getProjectIdAndPodId(userName, userId);
		data = projectIdAndPodIdOfGlober.split(":");
		projectId = Integer.parseInt(data[0]);
		podId = Integer.parseInt(data[1]);
		List<Object> dataForMarkUnmarkGlober = markUnmarkHelper.getRequiredTestDataForMarkUnmarkTL(projectId, podId);
		JSONObject requestParams = new MarkUnmarkTlPayloadHelper().createMarkUnmarkTlPayload(dataForMarkUnmarkGlober);
		Response response = markUnmarkHelper.markTechLead(requestParams,1);
		status = (String) restUtils.getValueFromResponse(response, "status");
		message = (String) restUtils.getValueFromResponse(response, "message");
		
		assertTrue(status.contains("fail"), "The status should be fail");
		assertTrue(message.contains("Pod doesn't exist"), "PodId does not exist message should come");
		test.log(Status.PASS, "Invalid Pod Id message coming Successfully");
		
	}
	
	/**
	 * This test will mark a glober as TL no project Id
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsOcampo", dataProviderClass = MarkAndUnmarkDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void markTlForNoProjectId(String userName, String userId) throws Exception {

		MarkAndUnmarkTlTestHelper markUnmarkHelper = new MarkAndUnmarkTlTestHelper(userName);
		String projectIdAndPodIdOfGlober = markUnmarkHelper.getProjectIdAndPodId(userName, userId);
		data = projectIdAndPodIdOfGlober.split(":");
		projectId = Integer.parseInt(data[0]);
		podId = Integer.parseInt(data[1]);
		List<Object> dataForMarkUnmarkGlober = markUnmarkHelper.getRequiredTestDataForMarkUnmarkTL(projectId, podId);
		dataForMarkUnmarkGlober.set(0,"");
		JSONObject requestParams = new MarkUnmarkTlPayloadHelper().createMarkUnmarkTlPayload(dataForMarkUnmarkGlober);
		Response response = markUnmarkHelper.markTechLead(requestParams,podId);
		status = (String) restUtils.getValueFromResponse(response, "status");
		
		assertTrue(status.contains("Invalid Project Id"), "Invalid Project Id message should come");
		test.log(Status.PASS, "Invalid Project Id message coming Successfully");
		
	}
	
	/**
	 * This test will mark a glober as TL for no glober Id
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsOcampo", dataProviderClass = MarkAndUnmarkDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void markTlForNoGloberId(String userName, String userId) throws Exception {

		MarkAndUnmarkTlTestHelper markUnmarkHelper = new MarkAndUnmarkTlTestHelper(userName);
		String projectIdAndPodIdOfGlober = markUnmarkHelper.getProjectIdAndPodId(userName, userId);
		data = projectIdAndPodIdOfGlober.split(":");
		projectId = Integer.parseInt(data[0]);
		podId = Integer.parseInt(data[1]);
		List<Object> dataForMarkUnmarkGlober = markUnmarkHelper.getRequiredTestDataForMarkUnmarkTL(projectId, podId);
		dataForMarkUnmarkGlober.set(1, "");
		JSONObject requestParams = new MarkUnmarkTlPayloadHelper().createMarkUnmarkTlPayload(dataForMarkUnmarkGlober);
		Response response = markUnmarkHelper.markTechLead(requestParams,podId);
		status = (String) restUtils.getValueFromResponse(response, "status");
		
		assertTrue(status.contains("Invalid Glober Id"), "Invalid glober Id message should come");
		test.log(Status.PASS, "Invalid glober Id message coming Successfully");
		
	}
	
	/**
	 * This test will mark a glober as TL for no pod Id
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsOcampo", dataProviderClass = MarkAndUnmarkDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void markTlForNoPodId(String userName, String userId) throws Exception {

		MarkAndUnmarkTlTestHelper markUnmarkHelper = new MarkAndUnmarkTlTestHelper(userName);
		String projectIdAndPodIdOfGlober = markUnmarkHelper.getProjectIdAndPodId(userName, userId);
		data = projectIdAndPodIdOfGlober.split(":");
		projectId = Integer.parseInt(data[0]);
		podId = Integer.parseInt(data[1]);
		List<Object> dataForMarkUnmarkGlober = markUnmarkHelper.getRequiredTestDataForMarkUnmarkTL(projectId, podId);
		JSONObject requestParams = new MarkUnmarkTlPayloadHelper().createMarkUnmarkTlPayload(dataForMarkUnmarkGlober);
		Response response = markUnmarkHelper.markTechLeadForInvalidPodData(requestParams,"");
		int status = (Integer) restUtils.getValueFromResponse(response, "status");
		message = (String) restUtils.getValueFromResponse(response, "message");
		
		assertTrue(status==403, "The 403 status code should come");
		assertTrue(message.contains("User is not having valid permission or role"), "User is not having valid permission or role message should come");
		test.log(Status.PASS, "Invalid glober Id message coming Successfully");
		
	}
	
	/**
	 * This test will mark a glober as TL for invalid project Id
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsOcampo", dataProviderClass = MarkAndUnmarkDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void markTlForInvalidProjectId(String userName, String userId) throws Exception {

		MarkAndUnmarkTlTestHelper markUnmarkHelper = new MarkAndUnmarkTlTestHelper(userName);
		String projectIdAndPodIdOfGlober = markUnmarkHelper.getProjectIdAndPodId(userName, userId);
		data = projectIdAndPodIdOfGlober.split(":");
		projectId = Integer.parseInt(data[0]);
		podId = Integer.parseInt(data[1]);
		List<Object> dataForMarkUnmarkGlober = markUnmarkHelper.getRequiredTestDataForMarkUnmarkTL(projectId, podId);
		dataForMarkUnmarkGlober.set(0,"%&*");
		JSONObject requestParams = new MarkUnmarkTlPayloadHelper().createMarkUnmarkTlPayload(dataForMarkUnmarkGlober);
		Response response = markUnmarkHelper.markTechLeadForInvalidData(requestParams,podId);
		int status = (Integer) restUtils.getValueFromResponse(response, "status");
		message = (String) restUtils.getValueFromResponse(response, "message");
		
		assertTrue(status==400, "The bad request message should come");
		assertTrue(message.contains("JSON parse error"), "The message is not Json parse error");
		test.log(Status.PASS, "Invalid glober Id message coming Successfully");
		
	}
	
	/**
	 * This test will mark a glober as TL for invalid glober Id
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsOcampo", dataProviderClass = MarkAndUnmarkDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void markTlForInvalidGloberId(String userName, String userId) throws Exception {

		MarkAndUnmarkTlTestHelper markUnmarkHelper = new MarkAndUnmarkTlTestHelper(userName);
		String projectIdAndPodIdOfGlober = markUnmarkHelper.getProjectIdAndPodId(userName, userId);
		data = projectIdAndPodIdOfGlober.split(":");
		projectId = Integer.parseInt(data[0]);
		podId = Integer.parseInt(data[1]);
		List<Object> dataForMarkUnmarkGlober = markUnmarkHelper.getRequiredTestDataForMarkUnmarkTL(projectId, podId);
		dataForMarkUnmarkGlober.set(1,"%&*");
		JSONObject requestParams = new MarkUnmarkTlPayloadHelper().createMarkUnmarkTlPayload(dataForMarkUnmarkGlober);
		Response response = markUnmarkHelper.markTechLeadForInvalidData(requestParams,podId);
		int status = (Integer) restUtils.getValueFromResponse(response, "status");
		message = (String) restUtils.getValueFromResponse(response, "message");
		
		assertTrue(status==400, "The bad request message should come");
		assertTrue(message.contains("JSON parse error"), "The message is not Json parse error");
		test.log(Status.PASS, "Invalid glober Id message coming Successfully");
		
	}
	
	/**
	 * This test will mark a glober as TL for invalid pod Id
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsOcampo", dataProviderClass = MarkAndUnmarkDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void markTlForInvalidPodId(String userName, String userId) throws Exception {

		MarkAndUnmarkTlTestHelper markUnmarkHelper = new MarkAndUnmarkTlTestHelper(userName);
		String projectIdAndPodIdOfGlober = markUnmarkHelper.getProjectIdAndPodId(userName, userId);
		data = projectIdAndPodIdOfGlober.split(":");
		projectId = Integer.parseInt(data[0]);
		podId = Integer.parseInt(data[1]);
		List<Object> dataForMarkUnmarkGlober = markUnmarkHelper.getRequiredTestDataForMarkUnmarkTL(projectId, podId);
		JSONObject requestParams = new MarkUnmarkTlPayloadHelper().createMarkUnmarkTlPayload(dataForMarkUnmarkGlober);
		Response response = markUnmarkHelper.markTechLeadForInvalidPodData(requestParams,"-0");
		int status = (Integer) restUtils.getValueFromResponse(response, "status");
		message = (String) restUtils.getValueFromResponse(response, "message");
		
		assertTrue(status==403, "The 403 status code should come");
		assertTrue(message.contains("User is not having valid permission or role"), "User is not having valid permission or role message should come");
		test.log(Status.PASS, "Invalid glober Id message coming Successfully");
		
	}
}
