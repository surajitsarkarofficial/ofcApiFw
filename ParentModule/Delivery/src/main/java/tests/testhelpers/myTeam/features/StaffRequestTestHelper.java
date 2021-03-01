package tests.testhelpers.myTeam.features;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONObject;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import database.myTeam.features.StaffRequestDBHelper;
import endpoints.myTeam.features.RotateAndReplaceEndpoints;
import endpoints.myTeam.features.StaffRequestEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONArray;
import tests.GlowBaseTest;
import tests.testcases.myTeam.MyTeamBaseTest;
import tests.testhelpers.myTeam.MyTeamTestHelper;
import utils.ExtentHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * @author dnyaneshwar.waghmare
 *
 */

public class StaffRequestTestHelper extends MyTeamTestHelper {

	String message = null;
	SoftAssert soft_assert;
	int projectId;
	int globerId;
	String startDate = null;
	String endDate = null;
	RequestSpecification requestSpecification;
	RestUtils restUtils = new RestUtils();

	public StaffRequestTestHelper(String userName) throws Exception {
		super(userName);
		soft_assert = new SoftAssert();
	}

	/***
	 * This method will return required test data to Staff Request
	 * 
	 * @param userName
	 * @param userId
	 * @return List<Object>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getRequiredTestDataForStaffRequest(String userName, String userId) throws Exception {
		MyTeamProjectHelper myTeamProjectHelper = new MyTeamProjectHelper(userName);
		Response response = myTeamProjectHelper.getProjectListUnderPM(userId);
		List<Integer> projectIds = response.jsonPath().get("details.projectId");
		List<Integer> clientIds = response.jsonPath().get("details.clientId");
		projectId = projectIds.get(0);
		int clientId = clientIds.get(0);

		response = getPositionDTOList(userId, projectId, clientId);
		List<Integer> positionIds = (List<Integer>) restUtils.getValueFromResponse(response,
				"$.details.positionDTOList[*].positionId");
		int positionId = positionIds.get(0);
		List<String> positionRoleId = (List<String>) restUtils.getValueFromResponse(response,
				"$.details.positionDTOList[?(@.positionId==" + positionId + ")].positionRoleId");
		List<String> positionSeniorityId = (List<String>) restUtils.getValueFromResponse(response,
				"$.details.positionDTOList[?(@.positionId==" + positionId + ")].positionSeniorityId");
		List<String> sowId = (List<String>) restUtils.getValueFromResponse(response,
				"$.details.positionDTOList[?(@.positionId==" + positionId + ")].sowId");
		List<String> positionName = (List<String>) restUtils.getValueFromResponse(response,
				"$.details.positionDTOList[?(@.positionId==" + positionId + ")].srDescription[0]");

		response = getStudioList();
		List<Integer> studioIds = (List<Integer> ) restUtils.getValueFromResponse(response,
				"$.details[*].id");
		int studioId = studioIds.get(0);
		List<String> studio = (List<String>) restUtils.getValueFromResponse(response,
				"$.details[?(@.id==" + studioId + ")].name");

		response = getLocationList();
		List<Integer> locationIds= (List<Integer>) restUtils.getValueFromResponse(response,
				"$.details[*].id");
		int locationId = locationIds.get(0);

		startDate = Utilities.getFutureDate("dd-MM-yyyy", 1);
		endDate = Utilities.getFutureDate("dd-MM-yyyy", 15);

		List<Object> paramValaues = Arrays.asList(projectId, clientId, positionRoleId.get(0), positionSeniorityId.get(0), sowId.get(0),
				positionName.get(0), true, "Automation Testing", startDate, endDate, true, studio.get(0), studioId, 1, false, false,
				"CONFIRMED", "Confirmed", 100, true, true, locationId, "DAYS",3);
		return paramValaues;
	}

	/***
	 * This method will return required test data to Staff Request internal positions
	 * 
	 * @param userName
	 * @param userId
	 * @return List<Object>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getRequiredTestDataForStaffRequestInternalPositions(String userName, String userId) throws Exception {
		String url=null;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId);
		
		MyTeamProjectHelper myTeamProjectHelper = new MyTeamProjectHelper(userName);
		Response response = myTeamProjectHelper.getProjectListUnderPM(userId);
		List<Integer> projectIds = response.jsonPath().get("details.projectId");
		List<Integer> clientIds = response.jsonPath().get("details.clientId");
		projectId = projectIds.get(0);
		int clientId = clientIds.get(0);

		response = getPositionDTOList(userId, projectId, clientId);
		List<Integer> positionIds = (List<Integer>) restUtils.getValueFromResponse(response,
				"$.details.positionDTOList[*].positionId");
		int positionId = positionIds.get(16); // Fetch 15th record from array having QC Analyst position
		List<String> positionRoleId = (List<String>) restUtils.getValueFromResponse(response,
				"$.details.positionDTOList[?(@.positionId==" + positionId + ")].positionRoleId");
		List<String> positionSeniorityId = (List<String>) restUtils.getValueFromResponse(response,
				"$.details.positionDTOList[?(@.positionId==" + positionId + ")].positionSeniorityId");
		List<String> sowId = (List<String>) restUtils.getValueFromResponse(response,
				"$.details.positionDTOList[?(@.positionId==" + positionId + ")].sowId");
		List<String> positionName = (List<String>) restUtils.getValueFromResponse(response,
				"$.details.positionDTOList[?(@.positionId==" + positionId + ")].srDescription[0]");

		startDate = Utilities.getFutureDate("dd-MM-yyyy", 1);
		endDate = Utilities.getFutureDate("dd-MM-yyyy", 15);
		
		url = GlowBaseTest.baseUrl + String.format(StaffRequestEndpoints.getSkills,"Developer", "SSr");
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
			item.put("competencyId", skillIds.get(i));
			item.put("competencyName", skillNames.get(i));
			item.put("importance", "2");
			item.put("importanceName", "Required");
			item.put("evidenceValue", "4");
			positionNeedDTOList.add(item);
		}

		List<Object> paramValaues = Arrays.asList(projectId, clientId, positionRoleId.get(0),
				positionSeniorityId.get(0), sowId.get(0), positionName.get(0), false, startDate, endDate, false, 1,
				false, true, "CONFIRMED", "Confirmed", 100, true, true, 1, "DAYS", 2, "QC Analyst", "SSr", 0, false,
				true, true, "null", "null", "null", positionNeedDTOList);
		return paramValaues;
	}

	/***
	 * This method will return required test data for clicking create Staff Request
	 * Link
	 * 
	 * @param userName
	 * @param userId
	 * @return int
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getRequiredTestDataForCreateStaffRequestLink(String userName, String userId) throws Exception {

		MyTeamProjectHelper myTeamProjectHelper = new MyTeamProjectHelper(userName);
		Response response = myTeamProjectHelper.getProjectListUnderPM(userId);
		List<Integer> projectIds = response.jsonPath().get("details.projectId");
		List<Integer> clientIds = response.jsonPath().get("details.clientId");
		projectId = projectIds.get(0);
		int clientId = clientIds.get(0);

		response = getPositionDTOList(userId, projectId, clientId);
		List<Integer> positionIds = (List<Integer>) restUtils.getValueFromResponse(response,
				"$.details.positionDTOList[*].positionId");
		int positionId = positionIds.get(0);
		List<Integer> positionRoleIds = (List<Integer>) restUtils.getValueFromResponse(response,
				"$.details.positionDTOList[?(@.positionId==" + positionId + ")].positionRoleId");

		// Return 1st not null positionRoleId from list
		int positionRoleId = positionRoleIds.stream().filter(Objects::nonNull).findFirst().orElse(-1);
		return positionRoleId;
	}

	/***
	 * This method will create a Staff Request
	 * 
	 * @param requestParams
	 * @return Response
	 * @throws Exception
	 */
	public Response createStaffRequest(JSONObject requestParams) throws Exception {
		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId).contentType(ContentType.JSON).body(requestParams.toString());
		String url = MyTeamBaseTest.baseUrl + StaffRequestEndpoints.postStaffRequest;
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executePOST(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 201, "Unable to create staff request.", true);
		ExtentHelper.test.log(Status.INFO, "post Staff Request method executed successfully");
		return response;
	}

	/***
	 * This method will click on create Staff Request link
	 * 
	 * @param dataForStaffRequestLink
	 * @return Response
	 * @throws Exception
	 */
	public Response clickOnCreateStaffRequestLink(int dataForStaffRequestLink) throws Exception {
		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId).contentType(ContentType.JSON).body(dataForStaffRequestLink);
		String url = MyTeamBaseTest.baseUrl
				+ String.format(StaffRequestEndpoints.getCreateStaffRequestLink, dataForStaffRequestLink);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200,
				"Unable to click on create staff request link.", true);
		ExtentHelper.test.log(Status.INFO, "click On Staff Request link method executed successfully");
		return response;
	}

	/***
	 * This method will return the position dto list for glober
	 * 
	 * @param userId
	 * @param projectId
	 * @param clientId
	 * @return Response
	 * @throws Exception
	 */
	public Response getPositionDTOList(String userId, int projectId, int clientId) throws Exception {
		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl
				+ String.format(StaffRequestEndpoints.getPositionDTOList, userId, 6, 6, projectId, clientId);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200,
				"Unable to fetch Position DTO List list results", true);
		ExtentHelper.test.log(Status.INFO, "get Position DTO List method executed successfully");
		return response;
	}

	/***
	 * This method will return the Studio list for glober
	 * 
	 * @return Response
	 * @throws Exception
	 */
	public Response getStudioList() throws Exception {
		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl + String.format(StaffRequestEndpoints.getStudioList);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch Studio list results", true);
		ExtentHelper.test.log(Status.INFO, "get Studio List method executed successfully");
		return response;
	}

	/***
	 * This method will return the Location list
	 * 
	 * @return Response
	 * @throws Exception
	 */
	public Response getLocationList() throws Exception {
		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl + String.format(StaffRequestEndpoints.getLocations);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch location list results",
				true);
		ExtentHelper.test.log(Status.INFO, "get Location List method executed successfully");
		return response;
	}

	/**
	 * This method will return id's and name's from db
	 * 
	 * @param postionRoleId
	 * @return Map<Integer, String>
	 * @throws Exception
	 */
	public Map<Integer, String> getIdNamesByPositionRoleIdFromDB(int postionRoleId) throws SQLException {
		StaffRequestDBHelper searchHelper = new StaffRequestDBHelper();
		Map<Integer, String> idNames = searchHelper.getIdNamesByPositionRoleId(postionRoleId);
		return idNames;
	}
	
	/***
	 * This method will return required test data for editing Staff Request
	 * 
	 * @param userName
	 * @param userId
	 * @return List<Object>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getRequiredTestDataForEditingStaffRequest(String userName, String userId) throws Exception {
		String url=null;
		MyTeamProjectHelper myTeamProjectHelper = new MyTeamProjectHelper(userName);
		Response response = myTeamProjectHelper.getProjectListUnderPM(userId);
		List<Integer> projectIds = response.jsonPath().get("details.projectId");
		List<Integer> clientIds = response.jsonPath().get("details.clientId");
		projectId = projectIds.get(0);
		int clientId = clientIds.get(0);

		response = getPositionDTOList(userId, projectId, clientId);
		List<Integer> positionIds = (List<Integer>) restUtils.getValueFromResponse(response,
				"$.details.positionDTOList[*].positionId");
		int positionId = positionIds.get(0);
		List<String> positionRoleId = (List<String>) restUtils.getValueFromResponse(response,
				"$.details.positionDTOList[?(@.positionId==" + positionId + ")].positionRoleId");
		List<String> positionSeniorityId = (List<String>) restUtils.getValueFromResponse(response,
				"$.details.positionDTOList[?(@.positionId==" + positionId + ")].positionSeniorityId");
		List<String> sowId = (List<String>) restUtils.getValueFromResponse(response,
				"$.details.positionDTOList[?(@.positionId==" + positionId + ")].sowId");
		List<String> positionName = (List<String>) restUtils.getValueFromResponse(response,
				"$.details.positionDTOList[?(@.positionId==" + positionId + ")].srDescription[0]");
		List<String> srPosition = (List<String>) restUtils.getValueFromResponse(response,
				"$.details.positionDTOList[?(@.positionId==" + positionId + ")].srPosition");

		response = getStudioList();
		List<Integer> studioIds = (List<Integer> ) restUtils.getValueFromResponse(response,
				"$.details[*].id");
		int studioId = studioIds.get(0);

		response = getLocationList();
		List<Integer> locationIds= (List<Integer>) restUtils.getValueFromResponse(response,
				"$.details[*].id");
		int locationId = locationIds.get(0);

		startDate = Utilities.getFutureDate("dd-MM-yyyy", 1);
		endDate = Utilities.getFutureDate("dd-MM-yyyy", 90);
		
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
			item.put("importanceName", "Required");
			item.put("evidenceValue", "4");
			positionNeedDTOList.add(item);
		}

		int secondaryLocationId[] = {};
		List<Object> paramValaues = Arrays.asList("null", positionRoleId.get(0), srPosition.get(0) ,
				positionSeniorityId.get(0), "Sr", "DAYS", true, 0, 100, false, locationId, secondaryLocationId,
				startDate, endDate, true, true, 811, 20, positionName.get(0) , clientId, projectId,
				positionId, sowId.get(0), true, 1, studioId, positionNeedDTOList);
		return paramValaues;
	}
	
	/***
	 * This method will edit a Staff Request
	 * 
	 * @param requestParams
	 * @return Response
	 * @throws Exception
	 */
	public Response putStaffRequest(JSONObject requestParams) throws Exception {
		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId).contentType(ContentType.JSON).body(requestParams.toString());
		System.out.println(requestParams.toString());
		String url = MyTeamBaseTest.baseUrl + StaffRequestEndpoints.postStaffRequest;
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executePUT(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to edit staff request.", true);
		ExtentHelper.test.log(Status.INFO, "put Staff Request method executed successfully");
		return response;
	}
	
	/**
	 * This method will fetch SR details
	 * 
	 * @param globerId
	 * @param projectId
	 * @param positionId
	 * @return ArrayList<Object>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Object> getPositionDtoDetails(Object globerId, Object projectId, Object positionId) throws Exception {
		List<Object> responsePositionDtoList;
		ArrayList<Object> dataForVerification = new ArrayList<Object>();

		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId);
		String url = GlowBaseTest.baseUrl
				+ String.format(RotateAndReplaceEndpoints.getSRNumberForGlober, globerId, projectId);
		Response response = restUtils.executeGET(requestSpecification, url);
		new GlowBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch SR number for Glober.", true);
		responsePositionDtoList = (List<Object>) restUtils.getValueFromResponse(response, "$.details.positionDTOList[*]");

		for (int i = 0; i < responsePositionDtoList.size(); i++) {
			Object responsePositionId = (Object) response.jsonPath().getMap("details.positionDTOList[" + i + "]")
					.get("positionId");
			if (responsePositionId.equals(positionId)) {
				dataForVerification
						.add(response.jsonPath().getMap("details.positionDTOList[" + i + "]").get("srNumber"));
				dataForVerification
						.add(response.jsonPath().getMap("details.positionDTOList[" + i + "]").get("clientAndProject"));
				dataForVerification.add(
						response.jsonPath().getMap("details.positionDTOList[" + i + "]").get("positionAndSeniority"));
			}
		}
		return dataForVerification;
	}
}
