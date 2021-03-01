package tests.testcases.myTeam.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import dataproviders.myTeam.MyTeamDataProviders;
import dto.myTeam.features.ConfirmShadowPositionDTOList;
import endpoints.myTeam.features.ConfirmShadowEndpoints;
import endpoints.myTeam.features.RotateAndReplaceEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.myTeam.features.ConfirmShadowPayloadHelper;
import tests.GlowBaseTest;
import tests.testcases.myTeam.MyTeamBaseTest;
import tests.testhelpers.myTeam.features.ConfirmShadowHelper;
import tests.testhelpers.myTeam.features.RotateAndReplaceTestHelper;
import utils.ExtentHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;

/**
 * @author pooja.kakade
 *
 */

public class ConfirmShadowTests extends MyTeamBaseTest {

	String message, status, statusCode = null;

	RestUtils restUtils = new RestUtils();

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("ConfirmShadowTests");
	}
	
	/***
	 * @author pooja.kakade
	 *  
	 * This test will confirm shadow resource
	 * 
	 * @param userName
	 * @param userId
	 * @throws Exception
	 *
	 */

	@Test(dataProvider = "ddRolDetailsCarolinaRisotto", dataProviderClass = MyTeamDataProviders.class, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void confirmShadow(String userName, String userId) throws Exception {
				
		ConfirmShadowHelper confirmShadowHelper = new ConfirmShadowHelper(userName);
		List<Object> dataForConfirmShadow = confirmShadowHelper.getRequiredTestDataForConfirmShadow(userName, userId);
		// No test data found
		if(dataForConfirmShadow == null)
		{
			test.log(Status.FAIL, "No Sfdc openings available for used test account");
			Assert.fail("No Sfdc openings available for used test account");
		}
		
		JSONObject innerPayload = new ConfirmShadowPayloadHelper().createInnerPayLoad(dataForConfirmShadow);
		JSONObject requestParams = new JSONObject();
		requestParams.put("action", "CONFIRM_SHADOW");
		requestParams.put("confirmShadowDetails", innerPayload);
		
		Response response = confirmShadowHelper.postConfirmShadow(requestParams);
		int resultCode = (int) restUtils.getValueFromResponse(response, "resultCode");
		String message = (String) restUtils.getValueFromResponse(response, "message");
		
		assertEquals(resultCode, 201);
		assertEquals(message,"Shadow position is confirmed successfully");
	}

	/***
	 * This test will confirm shadow through SFDC Opening without creating SR for the Shadow Glober
	 * 
	 * @param userName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "ddRolDetailsCarolinaRisotto", dataProviderClass = MyTeamDataProviders.class, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void confirmShadowThroughSfdcWithoutCreatingSr(String userName, String userId) throws Exception {
		Response response = null;
		ConfirmShadowHelper confirmShadowHelper = new ConfirmShadowHelper(userName);

		// Fetch Position List
		List<Object> positionIdObj = confirmShadowHelper.getRequiredTestDataForConfirmShadow(userName, userId);
		// No test data found
		if(positionIdObj == null)
		{
			test.log(Status.FAIL, "No Sfdc openings available for used test account");
			Assert.fail("No Sfdc openings available for used test account");
		}
		
		int positionId = (int) positionIdObj.get(0);

		response = confirmShadowHelper.getPositionListForConfirmShadowThroughSfdcWithoutSr(positionId);
		validateResponseToContinueTest(response, 200,
				"Position List for Confirming Shadow for Sfdc without SR not working", true);

		// Fetch DB data to validate Response
		List<ConfirmShadowPositionDTOList> dbResponse = confirmShadowHelper
				.getPositionListFromDbForConfirmShadow(positionId);

		// If DB results are not returned fail TC
		if ((dbResponse.size() <= 0) || (dbResponse.isEmpty()) || (dbResponse == null)) {
			test.log(Status.FAIL, "Failed to fetch DB result for validating response");
			assertFalse(((dbResponse.size() <= 0) || (dbResponse.isEmpty()) || (dbResponse == null)),
					"Failed to fetch DB result for validating confirm shadow response");
		}
		
		// If DB result size not matching with API response size fail TC
		@SuppressWarnings("unchecked")
		List<Object> responsePositionDtoListArray = (List<Object>) restUtils.getValueFromResponse(response,
				"$.details.positionDTOList[*]");
		assertEquals(responsePositionDtoListArray.size(), dbResponse.size(),
				"DB Response not matching with API Response");

		// Validate Response
		statusCode = (String) restUtils.getValueFromResponse(response, "statusCode");
		status = (String) restUtils.getValueFromResponse(response, "status");

		@SuppressWarnings("unchecked")
		List<Object> responseSkillsPositionId = (List<Object>) restUtils.getValueFromResponse(response,
				"$.details.positionDTOList[0].skills[*]");
		JSONArray skillsArray = new JSONArray(responseSkillsPositionId);

		assertEquals(statusCode, "OK", "Failed in validating Status Code");
		assertEquals(status, "success", "Failed in validating Status");

		validatePositionServiceResponse(response, dbResponse, skillsArray);
		
		test.log(Status.PASS, "Successfully fetched Position List for Confirming Shadow through SFDC Opening without creating SR for the Shadow Glober");
		
		// Confirm Shadow
		JSONObject innerPayload = new ConfirmShadowPayloadHelper().createInnerPayLoad(positionIdObj);
		JSONObject requestParams = new JSONObject();
		requestParams.put("action", "CONFIRM_SHADOW");
		requestParams.put("confirmShadowDetails", innerPayload);
		response = confirmShadowHelper.postConfirmShadow(requestParams);

		// Validate Response
		int resultCode = (int) restUtils.getValueFromResponse(response, "resultCode");
		message = (String) restUtils.getValueFromResponse(response, "message");
		
		assertEquals(resultCode, 201, "Failed in validating Result Code");
		assertEquals(message, "Shadow position is confirmed successfully", "Failed in validating message");

		test.log(Status.PASS, "Successfully Confirmed Shadow through SFDC Opening without creating SR for the Shadow Glober");
	}
		
	/***
	 * This test will confirm shadow through SFDC Opening with creating SR for the Shadow Glober
	 * 
	 * @param userName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "ddRolDetailsCarolinaRisotto", dataProviderClass = MyTeamDataProviders.class, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void confirmShadowThroughSfdcWithCreatingSr(String userName, String userId) throws Exception {

		Response response = null, confirmShadowResponse = null, positionSkillsResponse = null, replicateSrResponse = null;
		boolean validData = false;
		int responseSrNumber = 0, responseProjectId = 0;
		ConfirmShadowHelper confirmShadowHelper = new ConfirmShadowHelper(userName);
		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);

		// Fetch Position List
		List<Object> positionIdObj = confirmShadowHelper.getRequiredTestDataForConfirmShadow(userName, userId);
		// No test data found
		if(positionIdObj == null)
		{
			test.log(Status.FAIL, "No Sfdc openings available for used test account");
			Assert.fail("No Sfdc openings available for used test account");
		}
		
		int positionId = (int) positionIdObj.get(0);

		response = confirmShadowHelper.getPositionListForConfirmShadowThroughSfdcWithoutSr(positionId);
		validateResponseToContinueTest(response, 200,
				"Position List for Confirming Shadow for Sfdc with SR not working", true);

		// Fetch DB data to validate Response
		List<ConfirmShadowPositionDTOList> dbResponse = confirmShadowHelper
				.getPositionListFromDbForConfirmShadow(positionId);

		// If DB results are not returned fail TC
		if ((dbResponse.size() <= 0) || (dbResponse.isEmpty()) || (dbResponse == null)) {
			test.log(Status.FAIL, "Failed to fetch DB result for validating response");
			assertFalse(((dbResponse.size() <= 0) || (dbResponse.isEmpty()) || (dbResponse == null)),
					"Failed to fetch DB result for validating confirm shadow response");
		}

		// If DB result size not matching with API response size fail TC
		@SuppressWarnings("unchecked")
		List<Object> responsePositionDtoListArray = (List<Object>) restUtils.getValueFromResponse(response,
				"$.details.positionDTOList[*]");
		assertEquals(responsePositionDtoListArray.size(), dbResponse.size(),
				"DB Response not matching with API Response");

		// Validate Response
		statusCode = (String) restUtils.getValueFromResponse(response, "statusCode");
		status = (String) restUtils.getValueFromResponse(response, "status");

		@SuppressWarnings("unchecked")
		List<Object> responseSkillsPositionId = (List<Object>) restUtils.getValueFromResponse(response,
				"$.details.positionDTOList[0].skills[*]");
		JSONArray skillsArray = new JSONArray(responseSkillsPositionId);

		validatePositionServiceResponse(response, dbResponse, skillsArray);
		test.log(Status.PASS,
				"Successfully fetched Position List for Confirming Shadow through SFDC Opening with creating SR for the Shadow Glober");

		// Fetch Position Skills
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl + String.format(ConfirmShadowEndpoints.getPostionSkills, positionId);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		positionSkillsResponse = restUtils.executeGET(requestSpecification, url);

		// Validate Response
		validateResponseToContinueTest(positionSkillsResponse, 200,
				"Unable to fetch position skills for confirming shadow", true);
		status = (String) restUtils.getValueFromResponse(positionSkillsResponse, "status");
		statusCode = (String) restUtils.getValueFromResponse(positionSkillsResponse, "statusCode");
		validData = (boolean) restUtils.getValueFromResponse(positionSkillsResponse, "validData");

		assertEquals(status, "success", "Failed in validating status");
		assertEquals(statusCode, "OK", "Failed in validating statusCode");
		assertEquals(validData, true, "Failed in validating validData");

		test.log(Status.PASS,
				"Successfully fetched Position Skills list for confirming Shadow through SFDC Opening with creating SR for the Shadow Glober");

		// Fetch Skills
		url = GlowBaseTest.baseUrl + String.format(RotateAndReplaceEndpoints.getSkills, "Project%20Analyst", "SSr");
		Response skillResponse = restUtils.executeGET(requestSpecification, url);

		// Validate Response
		validateResponseToContinueTest(skillResponse, 200, "Unable to fetch Skills.", true);
		status = (String) restUtils.getValueFromResponse(skillResponse, "status");
		message = (String) restUtils.getValueFromResponse(skillResponse, "message");

		assertEquals(status, "200", "Failed in validating status");
		assertTrue(message.contains("Successful operation"), "Failed in validating message");

		test.log(Status.PASS,
				"Successfully fetched Skills list for confirming Shadow through SFDC Opening with creating SR for the Shadow Glober");

		// Confirm Shadow
		JSONObject innerPayload = new ConfirmShadowPayloadHelper().createInnerPayLoad(positionIdObj);
		JSONObject confirmShadowRequestParams = new JSONObject();
		confirmShadowRequestParams.put("action", "CONFIRM_SHADOW");
		confirmShadowRequestParams.put("confirmShadowDetails", innerPayload);
		confirmShadowResponse = confirmShadowHelper.postConfirmShadow(confirmShadowRequestParams);

		// Validate Response
		int resultCode = (int) restUtils.getValueFromResponse(confirmShadowResponse, "resultCode");
		message = (String) restUtils.getValueFromResponse(confirmShadowResponse, "message");

		assertEquals(resultCode, 201, "Failed in validating Result Code");
		assertEquals(message, "Shadow position is confirmed successfully", "Failed in validating message");

		test.log(Status.PASS,
				"Successfully Confirmed Shadow through SFDC Opening with creating SR for the Shadow Glober");

		// Replicate Shadow SR
		@SuppressWarnings("unchecked")
		List<Object> positionNeedDtoList = (List<Object>) restUtils.getValueFromResponse(positionSkillsResponse,
				"$.details[*]");

		JSONArray positionNeedDtoListPayload = new ConfirmShadowPayloadHelper()
				.createReplicateShadowPayLoad(positionNeedDtoList);
		responseSrNumber = (int) restUtils.getValueFromResponse(response, "$.details.positionDTOList[0].srNumber");
		responseProjectId = (int) restUtils.getValueFromResponse(response, "$.details.positionDTOList[0].projectId");

		JSONObject replicateShadowRequestParams = new JSONObject();
		replicateShadowRequestParams.put("srNumber", responseSrNumber);
		replicateShadowRequestParams.put("projectId", responseProjectId);
		replicateShadowRequestParams.put("positionNeedDTOList", positionNeedDtoListPayload);

		replicateSrResponse = confirmShadowHelper.replicateShadow(replicateShadowRequestParams);

		// Validate Response
		validateResponseToContinueTest(replicateSrResponse, 200,
				"Unable to Replicate Shadow SR for Confirming Shadow with SR", true);
		status = (String) restUtils.getValueFromResponse(replicateSrResponse, "status");
		message = (String) restUtils.getValueFromResponse(replicateSrResponse, "message");
		validData = (boolean) restUtils.getValueFromResponse(replicateSrResponse, "validData");

		assertEquals(status, "success", "Failed in validating status");
		assertTrue(message.contains("Success! You have successfully created Staff Request"),
				"Failed in validating message");
		assertEquals(validData, true, "Failed in validating validData");
		test.log(Status.PASS,
				"Successfully replicated shadow for confirming Shadow through SFDC Opening with creating SR for the Shadow Glober");

		int srNumberToVerify = rotateAndReplaceHelper.verifyCreatedSRForReplacedGlober(userId, responseProjectId);
		assertTrue(message.contains(String.valueOf(srNumberToVerify)),
				String.format("Excpected SRNumber %d was not Present in the message '%s'.", srNumberToVerify, message));
		test.log(Status.PASS, "Excpected SRNumber " + srNumberToVerify + " was Present in the message");

	}
	
	/**
	 * Common position service response validation method created for confirming shadow with/without SR
	 * 
	 * @param response
	 * @param dbResponse
	 * @param skillsArray
	 */
	public void validatePositionServiceResponse(Response response, List<ConfirmShadowPositionDTOList> dbResponse,
			JSONArray skillsArray) {
		Boolean responseReplacementFlag = false;
		String responseStartDate = null, responsePositionName = null, responseEndDate = null, responseSrType = null,
				responseSubmitterId = null, responseInterviewReq = null, responseLastUpdateDate = null,
				responsePositionState = null, responseEnglishReq = null, responseCandidateType = null,
				responseHiredStaffDate = null, responseSubmitDate = null, dbStartDate = null, dbEndDate = null,
				dbLastUpdateDate = null, dbHiredStaffDate = null, dbSubmitDate = null, responseProjectName = null,
				responseClientName = null, responsePositionStudio = null, responseHandlerEmail = null,
				responseSeniority = null, responseLocation = null;
		int responsePositionId = 0, responseProjectId = 0, responseHandlerTeamId = 0, responseSrNo = 0,
				responseSowId = 0, responsePositionRoleId = 0, responsePositionSeniorityId = 0,
				responsePositionStudioId = 0, responseClientId = 0, responseLocationId = 0, responseClientBuId = 0;

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		responsePositionName = (String) response.jsonPath().getMap("details.positionDTOList[0]").get("positionName");
		responseStartDate = (String) response.jsonPath().getMap("details.positionDTOList[0]").get("startDate");
		responseEndDate = (String) response.jsonPath().getMap("details.positionDTOList[0]").get("assignmentEndDate");
		responseSrType = (String) response.jsonPath().getMap("details.positionDTOList[0]").get("srType");
		responseSubmitterId = (String) response.jsonPath().getMap("details.positionDTOList[0]").get("submitterId");
		responseInterviewReq = (String) response.jsonPath().getMap("details.positionDTOList[0]")
				.get("clientInterviewRequired");
		responseReplacementFlag = (Boolean) response.jsonPath().getMap("details.positionDTOList[0]")
				.get("replacementFlag");
		responseLastUpdateDate = (String) response.jsonPath().getMap("details.positionDTOList[0]")
				.get("lastUpdatedDate");
		responsePositionState = (String) response.jsonPath().getMap("details.positionDTOList[0]").get("positionState");
		responsePositionStudioId = (int) response.jsonPath().getMap("details.positionDTOList[0]")
				.get("positionStudioId");
		responseEnglishReq = (String) response.jsonPath().getMap("details.positionDTOList[0]").get("englishRequired");
		responseCandidateType = (String) response.jsonPath().getMap("details.positionDTOList[0]").get("candidateType");
		responseHiredStaffDate = (String) response.jsonPath().getMap("details.positionDTOList[0]")
				.get("hiredOrStaffedDate");
		responseSubmitDate = (String) response.jsonPath().getMap("details.positionDTOList[0]")
				.get("staffRequestSubmitDate");
		responseProjectName = (String) response.jsonPath().getMap("details.positionDTOList[0]").get("projectName");
		responseClientName = (String) response.jsonPath().getMap("details.positionDTOList[0]").get("clientName");
		responsePositionStudio = (String) response.jsonPath().getMap("details.positionDTOList[0]")
				.get("positionStudio");
		responseHandlerEmail = (String) response.jsonPath().getMap("details.positionDTOList[0]").get("handlerEmail");
		responseSeniority = (String) response.jsonPath().getMap("details.positionDTOList[0]").get("seniority");
		responseLocation = (String) response.jsonPath().getMap("details.positionDTOList[0]").get("location");

		for (ConfirmShadowPositionDTOList dbData : dbResponse) {
			// If date non-empty then format
			dbStartDate = dbData.getStartDate() == null ? null : formatter.format(dbData.getStartDate());
			dbEndDate = dbData.getAssignmentEndDate() == null ? null : formatter.format(dbData.getAssignmentEndDate());
			dbLastUpdateDate = dbData.getLastUpdatedDate() == null ? null
					: formatter.format(dbData.getLastUpdatedDate());
			dbHiredStaffDate = dbData.getHiredOrStaffedDate() == null ? null
					: formatter.format(dbData.getHiredOrStaffedDate());
			dbSubmitDate = dbData.getStaffRequestSubmitDate() == null ? null
					: formatter.format(dbData.getStaffRequestSubmitDate());

			for (int i = 0; i < skillsArray.length(); i++) {
				JSONObject jsonObject = skillsArray.getJSONObject(i);
				assertEquals(jsonObject.getInt("positionId"), dbData.getPositionId(),
						"Failed in validating Skills{" + i + "} Position Id");
			}

			assertEquals(responsePositionName, dbData.getPositionName(), "Failed in validating Position Name");
			assertEquals(responseStartDate, dbStartDate, "Failed in validating Start Date");
			assertEquals(responseEndDate, dbEndDate, "Failed in validating Assignment End Date");
			assertEquals(responseSrType.toUpperCase(), dbData.getSrType().toUpperCase(),
					"Failed in validating Sr Type");
			assertEquals(responseSubmitterId, dbData.getSubmitterId(), "Failed in validating Submitter Id");
			assertEquals(responseInterviewReq, dbData.getClientInterviewRequired(),
					"Failed in validating Client Interview Required");
			assertEquals(responseReplacementFlag, dbData.getReplacementFlag(), "Failed in validating Replacement Flag");
			assertEquals(responseLastUpdateDate, dbLastUpdateDate, "Failed in validating Last Update Date");
			assertEquals(responsePositionState, dbData.getPositionState(), "Failed in validating Position State");
			assertEquals(responsePositionStudioId, dbData.getPositionStudioId(),
					"Failed in validating Position Studio Id");
			assertEquals(responseEnglishReq, dbData.getEnglishRequired(), "Failed in validating English Required");
			assertEquals(responseCandidateType, dbData.getCandidateType(), "Failed in validating Candidate Type");
			assertEquals(responseHiredStaffDate, dbHiredStaffDate, "Failed in validating Hired Staff Date");
			assertEquals(responseSubmitDate, dbSubmitDate, "Failed in validating SR Submit Date");
			assertEquals(responseProjectName, dbData.getProjectName(), "Failed in validating Project Name");
			assertEquals(responseClientName, dbData.getClientName(), "Failed in validating Client Name");
			assertEquals(responsePositionStudio, dbData.getPositionStudio(), "Failed in validating Posittion Studio");
			assertEquals(responseHandlerEmail, dbData.getHandlerEmail(), "Failed in validating Handler Email");
			assertEquals(responseSeniority, dbData.getSeniority(), "Failed in validating Seniority");
			assertEquals(responseLocation, dbData.getLocation(), "Failed in validating Location");

			// Validate only if values are non-empty
			if (response.jsonPath().getMap("details.positionDTOList[0]").get("positionId") != null) {
				responsePositionId = (int) response.jsonPath().getMap("details.positionDTOList[0]").get("positionId");
				assertEquals(responsePositionId, dbData.getPositionId(), "Failed in validating Position Id");
			}

			if (response.jsonPath().getMap("details.positionDTOList[0]").get("handlerTeamId") != null) {
				responseHandlerTeamId = (int) response.jsonPath().getMap("details.positionDTOList[0]")
						.get("handlerTeamId");
				assertEquals(responseHandlerTeamId, dbData.getHandlerTeamId(), "Failed in validating Handler Team Id");
			}

			if (response.jsonPath().getMap("details.positionDTOList[0]").get("projectId") != null) {
				responseProjectId = (int) response.jsonPath().getMap("details.positionDTOList[0]").get("projectId");
				assertEquals(responseProjectId, dbData.getProjectId(), "Failed in validating Project Id");
			}

			if (response.jsonPath().getMap("details.positionDTOList[0]").get("srNumber") != null) {
				responseSrNo = (int) response.jsonPath().getMap("details.positionDTOList[0]").get("srNumber");
				assertEquals(responseSrNo, dbData.getSrNumber(), "Failed in validating SR Number");
			}

			if (response.jsonPath().getMap("details.positionDTOList[0]").get("sowId") != null) {
				responseSowId = (int) response.jsonPath().getMap("details.positionDTOList[0]").get("sowId");
				assertEquals(responseSowId, dbData.getSowId(), "Failed in validating Sow Id");
			}

			if (response.jsonPath().getMap("details.positionDTOList[0]").get("positionRoleId") != null) {
				responsePositionRoleId = (int) response.jsonPath().getMap("details.positionDTOList[0]")
						.get("positionRoleId");
				assertEquals(responsePositionRoleId, dbData.getPositionRoleId(),
						"Failed in validating Position Role Id");
			}

			if (response.jsonPath().getMap("details.positionDTOList[0]").get("positionSeniorityId") != null) {
				responsePositionSeniorityId = (int) response.jsonPath().getMap("details.positionDTOList[0]")
						.get("positionSeniorityId");
				assertEquals(responsePositionSeniorityId, dbData.getPositionSeniorityId(),
						"Failed in validating Position Seniority Id");
			}

			if (response.jsonPath().getMap("details.positionDTOList[0]").get("clientId") != null) {
				responseClientId = (int) response.jsonPath().getMap("details.positionDTOList[0]").get("clientId");
				assertEquals(responseClientId, dbData.getClientId(), "Failed in validating Client Id");
			}

			if (response.jsonPath().getMap("details.positionDTOList[0]").get("clientBUId") != null) {
				responseClientBuId = (int) response.jsonPath().getMap("details.positionDTOList[0]").get("clientBUId");
				assertEquals(responseClientBuId, dbData.getClientBuId(), "Failed in validating Client Bu Id");
			}

			if (response.jsonPath().getMap("details.positionDTOList[0]").get("locationId") != null) {
				responseLocationId = (int) response.jsonPath().getMap("details.positionDTOList[0]").get("locationId");
				assertEquals(responseLocationId, dbData.getLocationId(), "Failed in validating Location Id");
			}
		}
	}
}