package tests.testhelpers.myTeam.features;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONObject;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;
import com.google.gson.Gson;

import database.myTeam.features.RotateAndReplaceDBHelper;
import dto.myTeam.features.RotateAndReplaceDTOList;
import endpoints.myTeam.features.RotateAndReplaceEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONArray;
import tests.GlowBaseTest;
import tests.testhelpers.myTeam.MyTeamTestHelper;
import utils.ExtentHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * @author imran.khan
 *
 */

public class RotateAndReplaceTestHelper extends MyTeamTestHelper {

	String message = null;
	Gson gson;
	SoftAssert soft_assert;
	RotateAndReplaceDBHelper rotateAndReplaceDBHelper;

	public RotateAndReplaceTestHelper(String userName) throws Exception {
		super(userName);
		soft_assert = new SoftAssert();
		rotateAndReplaceDBHelper = new RotateAndReplaceDBHelper();
	}

	/**
	 * This method will return required test data to replace a glober
	 * 
	 * @param response
	 * @param projectId
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	private List<Object> getRequiredTestDataForReplace(Response response, int projectId) throws Exception {
		String endDate = null;
		Integer IdOfglober = 0;
		boolean isOpening = true;
		Integer positionId = 0;
		String positionName = "TestPositionName";
		Integer projectIdParam = 0;
		Integer replacementReason = 0;
		String startDate = null;
		boolean validateDates = true;

		RestUtils restUtils = new RestUtils();
		startDate = Utilities.getTomorrow("dd-MM-yyyy");
		endDate = Utilities.getFutureDate("dd-MM-yyyy", 4);

		List<Integer> globerIds = (List<Integer>) restUtils.getValueFromResponse(response, "$.details[*].globerId");
		IdOfglober = Utilities.getRandomIdFromList(globerIds);

		String jsonPathForPositionId = "$.[*].[?(@.globerId== '" + IdOfglober + "')].positionId";
		List<Integer> positionIds = (List<Integer>) restUtils.getValueFromResponse(response, jsonPathForPositionId);
		positionId = positionIds.get(0);

		projectIdParam = projectId;

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId);
		String url = GlowBaseTest.baseUrl + RotateAndReplaceEndpoints.replacementReasons;
		Response replaceReasonResponse = restUtils.executeGET(requestSpecification, url);
		new GlowBaseTest().validateResponseToContinueTest(replaceReasonResponse, 200,
				"Unable to fetch Replacement Reasons.", true);

		List<Integer> replacementReasonIds = (List<Integer>) restUtils.getValueFromResponse(replaceReasonResponse,
				"$.details[*].id");
		replacementReason = replacementReasonIds.get(0);

		List<Object> newparamValues = Arrays.asList(endDate, IdOfglober, isOpening, positionId, positionName,
				projectIdParam, replacementReason, startDate, validateDates);

		return newparamValues;

	}

	/**
	 * This method will return required test data to replace a shadow glober
	 * 
	 * @param response
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List<Object> getRequiredTestDataForShadowReplace(List<RotateAndReplaceDTOList> replaceDTOList)
			throws Exception {
		String endDate = null;
		boolean isOpening = true;
		String positionName = "TestPositionName";
		Integer replacementReason = 0;
		String startDate = null;
		boolean validateDates = true;

		List<RotateAndReplaceDTOList> listOfReplaceObject = rotateAndReplaceDBHelper
				.getShadowUserUnderProject(replaceDTOList.get(0).getProjectId());

		startDate = Utilities.getTomorrow("dd-MM-yyyy");
		endDate = Utilities.getFutureDate("dd-MM-yyyy", 4);

		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId);
		String url = GlowBaseTest.baseUrl + RotateAndReplaceEndpoints.replacementReasons;
		Response replaceReasonResponse = restUtils.executeGET(requestSpecification, url);
		new GlowBaseTest().validateResponseToContinueTest(replaceReasonResponse, 200,
				"Unable to fetch Replacement Reasons.", true);

		List<Integer> replacementReasonIds = (List<Integer>) restUtils.getValueFromResponse(replaceReasonResponse,
				"$.details[*].id");
		replacementReason = replacementReasonIds.get(0);

		List<Object> newparamValues = Arrays.asList(endDate, listOfReplaceObject.get(0).getGloberId(), isOpening,
				listOfReplaceObject.get(0).getPositionId(), positionName, replaceDTOList.get(0).getProjectId(),
				replacementReason, startDate, validateDates);

		return newparamValues;

	}

	/**
	 * This method will replace a glober and return Response object
	 * 
	 * @param requestParams
	 * @return response
	 * @throws Exception
	 */
	public Response postReplaceGlober(JSONObject requestParams) throws Exception {
		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId).contentType(ContentType.JSON).body(requestParams.toString());
		String url = GlowBaseTest.baseUrl + RotateAndReplaceEndpoints.postReplace;
		Response response = restUtils.executePOST(requestSpecification, url);
		new GlowBaseTest().validateResponseToContinueTest(response, 201, "Unable to replace a glober.", true);
		ExtentHelper.test.log(Status.INFO, "post ReplaceGlober method executed successfully");
		return response;
	}

	/**
	 * This method will replace a glober for invalid data
	 * 
	 * @param requestParams
	 * @return response
	 * @throws Exception
	 */
	public Response postReplaceGloberInvalidData(JSONObject requestParams) throws Exception {
		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId).contentType(ContentType.JSON).body(requestParams.toString());
		String url = GlowBaseTest.baseUrl + RotateAndReplaceEndpoints.postReplace;
		Response response = restUtils.executePOST(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "post ReplaceGlober method executed successfully");
		return response;
	}

	/**
	 * This method will return the required test data to replace a glober
	 * 
	 * @param globerName
	 * @param globerId
	 * @return paramValaues
	 * @throws Exception
	 */
	public List<Object> getReplaceGloberTestData(String globerName, String globerId) throws Exception {

		int projectId = getProjectIdOfGlober(globerName, globerId);
		Response response = getProjectMemberDetailsWithPagination(globerName, projectId);
		List<Object> paramValaues = getRequiredTestDataForReplace(response, projectId);
		ExtentHelper.test.log(Status.INFO, "All the test data is ready to execute the test");
		return paramValaues;
	}

	/**
	 * This method will return the required test data to replace a shadow glober
	 * 
	 * @param globerName
	 * @param globerId
	 * @return paramValaues
	 * @throws Exception
	 */
	public List<Object> getReplaceGloberTestDataForShadow(String globerName, String globerId) throws Exception {
		List<RotateAndReplaceDTOList> listOfReplaceObject = rotateAndReplaceDBHelper.getGloberHavingShadowUsers();
		List<Object> paramValaues = getRequiredTestDataForShadowReplace(listOfReplaceObject);
		ExtentHelper.test.log(Status.INFO, "All the test data is ready to execute the test");
		return paramValaues;
	}
	
	/**
	 * This method will return the required test data to add skills and perform checkout with replacement
	 * 
	 * @param globerName
	 * @param globerId
	 * @return List<Object>
	 * @throws Exception
	 */
	public List<Object> addSkillsCheckoutWithReplaceGloberTestData(String globerName, String globerId) throws Exception {

		int projectId = getProjectIdOfGlober(globerName, globerId);
		Response response = getProjectMemberDetailsWithPagination(globerName, projectId);
		List<Object> paramValaues = addSkillsTestDataForCheckoutWithReplace(response, projectId);
		ExtentHelper.test.log(Status.INFO, "All the test data is ready to execute the test");
		return paramValaues;
	}
	
	/**
	 * This method will return the required test data to replace a glober with SR
	 * 
	 * @param globerName
	 * @param globerId
	 * @return paramValaues
	 * @throws Exception
	 */
	public List<Object> getReplaceGloberWithSRTestData(String globerName, String globerId) throws Exception {

		int projectId = getProjectIdOfGlober(globerName, globerId);
		Response response = getProjectMemberDetailsWithPagination(globerName, projectId);
		List<Object> paramValaues = getRequiredTestDataForReplaceWithSR(response, projectId);
		ExtentHelper.test.log(Status.INFO, "All the test data is ready to execute the test");
		return paramValaues;
	}
	
	/**
	 * This method will verify created SR for replaced glober
	 * 
	 * @param globerId
	 * @param projectId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int verifyCreatedSRForReplacedGlober(Object globerId, Object projectId) throws Exception {
		List<Integer> srIds;

		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId);
		String url = GlowBaseTest.baseUrl
				+ String.format(RotateAndReplaceEndpoints.getSRNumberForGlober, globerId, projectId);
		Response response = restUtils.executeGET(requestSpecification, url);
		new GlowBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch SR number for Glober.", true);
		srIds = (List<Integer>) restUtils.getValueFromResponse(response, "$.[*]..srNumber");
		int sizeOfSrIds = srIds.size() - 1;
		String jsonPathForSRNumber = String.format("details.positionDTOList[%d].srNumber", sizeOfSrIds);
		int srNumberToVerify = (int) restUtils.getValueFromResponse(response, jsonPathForSRNumber);
		ExtentHelper.test.log(Status.INFO, "Fetched SR number for the replaced glober : " + srNumberToVerify);
		return srNumberToVerify;
	}

	/**
	 * This method will return all the replacement reasons from API
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getAllReplacementReasons() throws Exception {
		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId);
		String url = GlowBaseTest.baseUrl + String.format(RotateAndReplaceEndpoints.replacementReasons);
		Response response = restUtils.executeGET(requestSpecification, url);
		new GlowBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch Replacements Reason.", true);

		List<String> replacementReasonIds = (List<String>) restUtils.getValueFromResponse(response, "$.details[*].id");
		List<String> replacementTypeNames = (List<String>) restUtils.getValueFromResponse(response,
				"$.details[*].name");

		List<Integer> loop = Stream.iterate(0, n -> n + 1).limit(replacementReasonIds.size())
				.collect(Collectors.toList());

		Map<String, Object> replacementReasonsMap = loop.stream()
				.collect(Collectors.toMap(replacementReasonIds::get, replacementTypeNames::get));
		ExtentHelper.test.log(Status.INFO, "Fetched all replacement reasons from API");
		return replacementReasonsMap;
	}

	/**
	 * This method will return all the replacement reasons from database
	 * @throws SQLException 
	 */
	public Map<String, Object> getReplacementReasonFromDB() throws SQLException {
		Map<String, Object> DBReplacementReasonsMap = rotateAndReplaceDBHelper.getAllReplacementReasons();
		ExtentHelper.test.log(Status.INFO, "Fetched all replacement reasons from Database");
		return DBReplacementReasonsMap;
	}

	/**
	 * This method will return required test data to rotate a glober
	 * 
	 * @param response
	 * @param projectId
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	public List<Object> getRequiredTestDataForRotate(String globerName, String globerId, String dateValidation)
			throws Exception {

		Integer IdOfglober = 0;
		Integer positionId = 0;
		Integer sowId = 0;
		Integer assignmentPercentage = 0;
		String rotationStartDate = null;
		String detailedReason = "AutomationTesting";
		String url = null;

		RestUtils restUtils = new RestUtils();

		int projectId = getProjectIdOfGlober(globerName, globerId);
		Response response = getProjectMemberDetailsWithPagination(globerName, projectId);
		validateResponseToContinueTest(response, 200, "Project Member Details not available", true);
		
		List<Integer> globerIds = (List<Integer>) restUtils.getValueFromResponse(response, "$.details[*].globerId");
		IdOfglober = Utilities.getRandomIdFromList(globerIds);

		String jsonPathForPositionId = "$.[*].[?(@.globerId== '" + IdOfglober + "')].positionId";
		List<Integer> positionIds = (List<Integer>) restUtils.getValueFromResponse(response, jsonPathForPositionId);
		positionId = positionIds.get(0);

		String jsonPathForAssignmentPrecentage = "$.[*].[?(@.globerId== '" + IdOfglober + "')].assignmentPercentage";
		List<Integer> assignmentPercentages = (List<Integer>) restUtils.getValueFromResponse(response,
				jsonPathForAssignmentPrecentage);
		assignmentPercentage = assignmentPercentages.get(0);

		if (dateValidation.contains("pastDate")) {
			rotationStartDate = Utilities.getYesterday("yyyy-MM-dd");
		}

		else if (dateValidation.contains("todayDate")) {
			rotationStartDate = Utilities.getTodayDate("yyyy-MM-dd");
		}

		else {
			rotationStartDate = Utilities.getTomorrow("yyyy-MM-dd");
		}

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId);
		url = GlowBaseTest.baseUrl + String.format(RotateAndReplaceEndpoints.getSowId, projectId);
		Response getSowIdsResponse = restUtils.executeGET(requestSpecification, url);
		new GlowBaseTest().validateResponseToContinueTest(getSowIdsResponse, 200, "Unable to fetch sowIds.", true);
		List<Integer> sowIds = (List<Integer>) restUtils.getValueFromResponse(getSowIdsResponse, "$.details[*].id");
		sowId = sowIds.get(0);

		List<Object> newparamValues = Arrays.asList(detailedReason, assignmentPercentage, positionId, rotationStartDate,
				sowId, IdOfglober, projectId);
		ExtentHelper.test.log(Status.INFO, "All the test data is ready to execute the test");
		return newparamValues;

	}

	/**
	 * This method will rotate a glober and return Response object
	 * 
	 * @param requestParams
	 * @return response
	 * @throws Exception
	 */
	public Response postRotateGlober(JSONObject requestParams) throws Exception {
		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId).contentType(ContentType.JSON).body(requestParams.toString());
		String url = GlowBaseTest.baseUrl + RotateAndReplaceEndpoints.postRotate;
		Response response = restUtils.executePOST(requestSpecification, url);
		new GlowBaseTest().validateResponseToContinueTest(response, 201, "Unable to rotate a glober.", true);
		ExtentHelper.test.log(Status.INFO, "post RotateGlober method executed successfully");
		return response;
	}
	
	/**
	 * This method will rotate a glober for invalid input
	 * 
	 * @param requestParams
	 * @return response
	 * @throws Exception
	 */
	public Response postRotateGloberForInvalidInput(JSONObject requestParams) throws Exception {
		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId).contentType(ContentType.JSON).body(requestParams.toString());
		String url = GlowBaseTest.baseUrl + RotateAndReplaceEndpoints.postRotate;
		Response response = restUtils.executePOST(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "post RotateGlober method executed successfully");
		return response;
	}
	
	/**
	 * This method will verify end date of rotated glober
	 * 
	 * @param globerName
	 * @param globerId
	 * @param projectId
	 * @param rotationStartDate
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String verifyEndDateOfRotatedGlober(String globerName, Object globerId, Object projectId,
			Object rotationStartDate) throws Exception {

		RestUtils restUtils = new RestUtils();
		Response response = getProjectMemberDetailsWithPagination(globerName, (Integer) projectId);
		validateResponseToContinueTest(response, 200, "Project Member Details not available", true);
		List<Long> tempAssignmentEndDates = (List<Long>) restUtils.getValueFromResponse(response,
				"$.[*].[?(@.globerId== '" + globerId + "')].tempAssignmentEndDate");

		String actualEndDate = Utilities.convertLongToFormattedDate(tempAssignmentEndDates.get(0),
				"yyyy-MM-dd");
		ExtentHelper.test.log(Status.INFO, "Fetched actual date for the rotated glober : " + actualEndDate);
		return actualEndDate;
	}
	
	/**
	 * This method will return required test data to replace a glober with SR
	 * 
	 * @param response
	 * @param projectId
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	private List<Object> getRequiredTestDataForReplaceWithSR(Response response, int projectId) throws Exception {
		String endDate = null;
		Integer IdOfglober = 0;
		boolean isOpening = true;
		Integer positionId = 0;
		String positionName = "TestPositionName";
		Integer projectIdParam = 0;
		Integer replacementReason = 0;
		String startDate = null;
		boolean validateDates = true;
		String url=null;
		
		RestUtils restUtils = new RestUtils();
		startDate = Utilities.getTomorrow("dd-MM-yyyy");
		endDate = Utilities.getFutureDate("dd-MM-yyyy", 4);

		List<Integer> globerIds = (List<Integer>) restUtils.getValueFromResponse(response, "$.details[*].globerId");
		IdOfglober = Utilities.getRandomIdFromList(globerIds);

		String jsonPathForPositionId = "$.[*].[?(@.globerId== '" + IdOfglober + "')].positionId";
		List<Integer> positionIds = (List<Integer>) restUtils.getValueFromResponse(response, jsonPathForPositionId);
		positionId = positionIds.get(0);

		projectIdParam = projectId;

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId);
		url = GlowBaseTest.baseUrl + RotateAndReplaceEndpoints.replacementReasons;
		Response replaceReasonResponse = restUtils.executeGET(requestSpecification, url);
		new GlowBaseTest().validateResponseToContinueTest(replaceReasonResponse, 200,
				"Unable to fetch Replacement Reasons.", true);

		List<Integer> replacementReasonIds = (List<Integer>) restUtils.getValueFromResponse(replaceReasonResponse,
				"$.details[*].id");
		replacementReason = replacementReasonIds.get(0);
		
		url = GlowBaseTest.baseUrl + String.format(RotateAndReplaceEndpoints.getSkills, "Developer", "Sr");
		Response skillResponse = restUtils.executeGET(requestSpecification, url);
		new GlowBaseTest().validateResponseToContinueTest(skillResponse, 200,
				"Unable to fetch Skills.", true);
		List<Integer> skillIds = (List<Integer>) restUtils.getValueFromResponse(skillResponse,
				"data[*].skill.id");
		List<String> skillNames = (List<String>) restUtils.getValueFromResponse(skillResponse,
				"data[*].skill.name");
		JSONArray positionNeedDTOList = new JSONArray();
		JSONObject item = new JSONObject();
		
		for (int i = 0; i < 5; i++) {
			item.put("alberthaId", skillIds.get(i));
			item.put("competencyId", "");
			item.put("competencyName", skillNames.get(i));
			item.put("importance", "2");
			positionNeedDTOList.add(item);
		}
		List<Object> newparamValues = Arrays.asList(endDate, IdOfglober, isOpening, positionId, positionName,
				projectIdParam, replacementReason, startDate, validateDates,positionNeedDTOList);

		return newparamValues;

	}
	
	/**
	 * This test will return required test data to replace a glober with SR for
	 * adding skills when creating a checkout with replacement
	 * 
	 * @param response
	 * @param projectId
	 * @return List<Object>
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	private List<Object> addSkillsTestDataForCheckoutWithReplace(Response response, int projectId) throws Exception {
		String endDate = null;
		int globerId = 0;
		boolean isOpening = true;
		int positionId = 0;
		String positionName = "java developer";
		String startDate = null;
		int replacementReason = 8; 		// For Checkout
		boolean validateDates = true;
		String url = null;

		RestUtils restUtils = new RestUtils();
		startDate = Utilities.getTomorrow("dd-MM-yyyy");
		endDate = Utilities.getTodayDate("dd-MM-yyyy");

		List<Integer> globerIds = (List<Integer>) restUtils.getValueFromResponse(response, "$.details[*].globerId");
		globerId = globerIds.get(1);

		String jsonPathForPositionId = "$.[*].[?(@.globerId== '" + globerId + "')].positionId";
		List<Integer> positionIds = (List<Integer>) restUtils.getValueFromResponse(response, jsonPathForPositionId);
		positionId = positionIds.get(0);

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId);
		url = GlowBaseTest.baseUrl + String.format(RotateAndReplaceEndpoints.getSkills, "Developer", "Sr");
		Response skillResponse = restUtils.executeGET(requestSpecification, url);
		new GlowBaseTest().validateResponseToContinueTest(skillResponse, 200, "Unable to fetch Skills.", true);
		List<Integer> skillIds = (List<Integer>) restUtils.getValueFromResponse(skillResponse, "data[*].skill.id");
		List<String> skillNames = (List<String>) restUtils.getValueFromResponse(skillResponse, "data[*].skill.name");
		JSONArray positionNeedDTOList = new JSONArray();
		JSONObject item = new JSONObject();

		for (int i = 0; i < 10; i++) {
			item.put("alberthaId", skillIds.get(i));
			item.put("competencyId", skillIds.get(i));
			item.put("competencyName", skillNames.get(i));
			item.put("importance", "2");
			item.put("importanceName", "Required");
			item.put("evidenceValue", "4");
			positionNeedDTOList.add(item);
		}
		List<Object> newparamValues = Arrays.asList(positionName, positionId, globerId, projectId, startDate, endDate,
				replacementReason, isOpening, validateDates, positionNeedDTOList);

		return newparamValues;

	}
	
	/**
	 * This method will return the required test data to replace a shadow glober with SR
	 * 
	 * @param globerName
	 * @param globerId
	 * @return paramValaues
	 * @throws Exception
	 */
	public List<Object> getReplaceGloberTestDataForShadowWithSR(String globerName, String globerId) throws Exception {
		List<RotateAndReplaceDTOList> listOfReplaceObject = rotateAndReplaceDBHelper.getGloberHavingShadowUsers();
		List<Object> paramValaues = getRequiredTestDataForShadowReplace(listOfReplaceObject);
		ExtentHelper.test.log(Status.INFO, "All the test data is ready to execute the test");
		return paramValaues;
	}
}
