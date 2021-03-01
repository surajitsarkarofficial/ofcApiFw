package tests.testhelpers.submodules.staffRequest.features;

import static org.testng.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aventstack.extentreports.Status;

import database.submodules.staffRequest.features.EditSRDBHelpers;
import dto.submodules.staffRequest.features.PositionNeedDTOListSrRevamp;
import endpoints.submodules.staffRequest.StaffRequestEndpoints;
import endpoints.submodules.staffRequest.features.CreateNewPositionEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.staffRequest.features.EditSRRevampPayloadHelper;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import tests.testhelpers.submodules.staffRequest.StaffRequestTestHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * 
 * @author akshata.dongare
 *
 */
public class EditSRRevampTestHelper extends StaffRequestTestHelper {

	RestUtils restUtils = new RestUtils();
	Response editSRResponse;
	EditSRDBHelpers editSRDBHelpers;
	EditSRRevampPayloadHelper editSRRevampPayloadHelper;

	public EditSRRevampTestHelper(String userName) throws Exception {
		super(userName);
		editSRDBHelpers = new EditSRDBHelpers();
		editSRRevampPayloadHelper = new EditSRRevampPayloadHelper();
	}

	/**
	 * This method will return globerId from glober name
	 * 
	 * @param globerName
	 * @return String
	 * @throws SQLException
	 */
	public String getGloberIdFromGloberName(String globerName) throws SQLException {
		String globerId = editSRDBHelpers.getGloberId(globerName);
		return globerId;

	}

	/**
	 * This method will return jsonpath of albertha skill response
	 * @return JsonPath
	 * @throws Exception
	 */
	public JsonPath getAlebrthaSkills() throws Exception {
		// Get skills
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(StaffRequestBaseTest.sessionId);
		String getSkillsUrl = StaffRequestBaseTest.baseUrl + CreateNewPositionEndpoints.cachedSkillsUrl;
		Response skillResponse = restUtils.executeGET(requestSpecification, getSkillsUrl);
		new StaffRequestBaseTest().validateResponseToContinueTest(skillResponse, 200, "Unable to fetch Skills", true);
		StaffRequestBaseTest.test.log(Status.INFO, "Skills fetched successfully.");
		JsonPath skillsJsonPathData = skillResponse.jsonPath();
		return skillsJsonPathData;
	}

	/**
	 * This method will return map of position details from global staff request
	 * 
	 * @param response
	 * @return map
	 * @throws Exception
	 */
	public Map<Object, Object> getPositionDetailsMap(Response response) throws Exception {
		int i = Utilities.getRandomDay();

		String positionIdjsonPath = "details.positionDTOList[" + i + "].positionId";
		String positionId = restUtils.getValueFromResponse(response, positionIdjsonPath).toString();

		String jsonPathForProjectId = "details.positionDTOList[" + i + "].projectId";
		String projectId = restUtils.getValueFromResponse(response, jsonPathForProjectId).toString();

		String clientIdJsonPath = "details.positionDTOList[" + i + "].clientId";
		String clientId = restUtils.getValueFromResponse(response, clientIdJsonPath).toString();

		String sowIdJsonPath = "details.positionDTOList[" + i + "].sowId";
		String sowId = restUtils.getValueFromResponse(response, sowIdJsonPath).toString();

		Map<Object, Object> mapOfEditSR = new HashMap<Object, Object>();
		mapOfEditSR.put("positionId", positionId);
		mapOfEditSR.put("projectId", projectId);
		mapOfEditSR.put("clientId", clientId);
		mapOfEditSR.put("sowId", sowId);

		return mapOfEditSR;
	}

	/**
	 * This method is used to update edit SR for project details
	 * 
	 * @param response
	 * @param userName
	 * @return response
	 * @throws Exception
	 */
	public Response updateEditSRProjectDetails(Response response, String userName) throws Exception {
		// Get Position details from global staff request response
		Map<Object, Object> mapOfEditSR = getPositionDetailsMap(response);

		// Get skills
		JsonPath skillsJsonPathData = getAlebrthaSkills();

		// Edit Sr
		String jsonBody = editSRRevampPayloadHelper.editSrProjectDetailsValidValuesPayload(mapOfEditSR,
				skillsJsonPathData, userName);
		if (jsonBody == null) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Json body is null hence can not edit SR");
		} else {
			RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId)
					.contentType(ContentType.JSON).body(jsonBody);
			String editSRUrl = StaffRequestBaseTest.baseUrl + StaffRequestEndpoints.srRevampPositionsUrl;
			editSRResponse = restUtils.executePUT(reqSpec, editSRUrl);
		}
		return editSRResponse;
	}

	/**
	 * This method is used to update edit SR for project details with variable load
	 * position
	 * 
	 * @param response
	 * @param userName
	 * @return response
	 * @throws Exception
	 */
	public Response updateEditSRProjectDetailsWithVariableLoad(Response response, String userName) throws Exception {
		// Get Position details from global staff request response
		Map<Object, Object> mapOfEditSR = getPositionDetailsMap(response);

		// Get skills
		JsonPath skillsJsonPathData = getAlebrthaSkills();

		// Edit Sr
		String jsonBody = editSRRevampPayloadHelper.getEditSrProjectDetailsVariableLoadPayload(mapOfEditSR,
				skillsJsonPathData, userName);
		if (jsonBody == null) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Json body is null hence can not edit SR");
		} else {
			RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId)
					.contentType(ContentType.JSON).body(jsonBody);
			String editSRUrl = StaffRequestBaseTest.baseUrl + StaffRequestEndpoints.srRevampPositionsUrl;
			editSRResponse = restUtils.executePUT(reqSpec, editSRUrl);
		}
		return editSRResponse;
	}

	/**
	 * This method will return list of clients ids from db and response
	 * 
	 * @param response
	 * @param userName
	 * @return list
	 * @throws Exception
	 */
	public List<String> updateEditSRProjectDetailsWithDifferentClientId(Response response, String userName)
			throws Exception {
		List<String> list = new ArrayList<String>();

		Map<Object, Object> mapOfEditSR = getPositionDetailsMap(response);

		String clientIdExceptAssociatedId = editSRDBHelpers
				.getClientIdExceptSpecificId(mapOfEditSR.get("clientId").toString());
		mapOfEditSR.put("clientId", clientIdExceptAssociatedId);

		// Get skills
		JsonPath skillsJsonPathData = getAlebrthaSkills();

		// Edit Sr
		String jsonBody = editSRRevampPayloadHelper.editSrProjectDetailsValidValuesPayload(mapOfEditSR,
				skillsJsonPathData, userName);
		if (jsonBody == null) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Json body is null hence can not edit SR");
		} else {
			RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId)
					.contentType(ContentType.JSON).body(jsonBody);
			String editSRUrl = StaffRequestBaseTest.baseUrl + StaffRequestEndpoints.srRevampPositionsUrl;
			editSRResponse = restUtils.executePUT(reqSpec, editSRUrl);
			new StaffRequestBaseTest().validateResponseToContinueTest(editSRResponse, 200,
					"Edit SR project details call was not successful", true);
		}

		// Get client id values from db and response
		String responseClientNameJsonPath = "details.clientName";

		String clientNameResponse = restUtils.getValueFromResponse(editSRResponse, responseClientNameJsonPath)
				.toString();
		String clientNameDb = editSRDBHelpers.getClientNameFromClientId(clientIdExceptAssociatedId);

		list.add(clientNameResponse);
		list.add(clientNameDb);
		return list;
	}

	/**
	 * This method will return list of project ids from db and response
	 * 
	 * @param response
	 * @param userName
	 * @return list
	 * @throws Exception
	 */
	public List<String> updateEditSRProjectDetailsWithDifferentProjectId(Response response, String userName)
			throws Exception {
		List<String> list = new ArrayList<String>();

		// put different projecy Id
		Map<Object, Object> mapOfEditSR = getPositionDetailsMap(response);
		String projectIdExceptAssociatedId = editSRDBHelpers
				.getProjectIdExceptSpecificId(mapOfEditSR.get("projectId").toString());
		mapOfEditSR.put("projectId", projectIdExceptAssociatedId);

		// Get skills
		JsonPath skillsJsonPathData = getAlebrthaSkills();

		// Edit Sr
		String jsonBody = editSRRevampPayloadHelper.editSrProjectDetailsValidValuesPayload(mapOfEditSR,
				skillsJsonPathData, userName);

		if (jsonBody == null) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Json body is null hence can not edit SR");
		} else {
			RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId)
					.contentType(ContentType.JSON).body(jsonBody);
			String editSRUrl = StaffRequestBaseTest.baseUrl + StaffRequestEndpoints.srRevampPositionsUrl;
			editSRResponse = restUtils.executePUT(reqSpec, editSRUrl);
			new StaffRequestBaseTest().validateResponseToContinueTest(editSRResponse, 200,
					"Edit SR project details call was not successful", true);
		}

		// Get project id values from db and response
		String responseProjectNameJsonPath = "details.projectName";

		String projectNameResponse = restUtils.getValueFromResponse(editSRResponse, responseProjectNameJsonPath)
				.toString();
		String projectNameDb = editSRDBHelpers.getProjectNameFromProjectId(projectIdExceptAssociatedId);

		list.add(projectNameResponse);
		list.add(projectNameDb);
		return list;
	}

	/**
	 * This method is used to update edit SR with null client id
	 * 
	 * @param response
	 * @param userName
	 * @return response
	 * @throws Exception
	 */
	public Response updateEditSRProjectDetailsWithNullClientId(Response response, String userName) throws Exception {
		// Get Position details from global staff request response
		Map<Object, Object> mapOfEditSR = getPositionDetailsMap(response);
		mapOfEditSR.put("clientId", null);

		// Get skills
		JsonPath skillsJsonPathData = getAlebrthaSkills();

		// Edit Sr
		String jsonBody = editSRRevampPayloadHelper.editSrProjectDetailsValidValuesPayload(mapOfEditSR,
				skillsJsonPathData, userName);

		if (jsonBody == null) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Json body is null hence can not edit SR");
		} else {
			RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId)
					.contentType(ContentType.JSON).body(jsonBody);
			String editSRUrl = StaffRequestBaseTest.baseUrl + StaffRequestEndpoints.srRevampPositionsUrl;
			editSRResponse = restUtils.executePUT(reqSpec, editSRUrl);
		}
		return editSRResponse;
	}

	/**
	 * This method is used to update edit SR with null project id
	 * 
	 * @param response
	 * @param userName
	 * @return response
	 * @throws Exception
	 */
	public Response updateEditSRProjectDetailsWithNullProjectId(Response response, String userName) throws Exception {
		// Get Position details from global staff request response
		Map<Object, Object> mapOfEditSR = getPositionDetailsMap(response);
		mapOfEditSR.put("projectId", null);

		// Get skills
		JsonPath skillsJsonPathData = getAlebrthaSkills();

		// Edit Sr
		String jsonBody = editSRRevampPayloadHelper.editSrProjectDetailsValidValuesPayload(mapOfEditSR,
				skillsJsonPathData, userName);

		if (jsonBody == null) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Json body is null hence can not edit SR");
		} else {
			RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId)
					.contentType(ContentType.JSON).body(jsonBody);
			String editSRUrl = StaffRequestBaseTest.baseUrl + StaffRequestEndpoints.srRevampPositionsUrl;
			editSRResponse = restUtils.executePUT(reqSpec, editSRUrl);
		}
		return editSRResponse;
	}

	/**
	 * This method is used to update edit SR for project details with null position
	 * id
	 * 
	 * @param response
	 * @param userName
	 * @return response
	 * @throws Exception
	 */
	public Response updateEditSRProjectDetailsWithNullPosition(Response response, String userName) throws Exception {
		// Get Position details from global staff request response
		Map<Object, Object> mapOfEditSR = getPositionDetailsMap(response);

		// Get skills
		JsonPath skillsJsonPathData = getAlebrthaSkills();

		// Edit Sr
		String jsonBody = editSRRevampPayloadHelper.getEditSrProjectDetailsNullPositionPayload(mapOfEditSR,
				skillsJsonPathData, userName);

		if (jsonBody == null) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Json body is null hence can not edit SR");
		} else {
			RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId)
					.contentType(ContentType.JSON).body(jsonBody);
			String editSRUrl = StaffRequestBaseTest.baseUrl + StaffRequestEndpoints.srRevampPositionsUrl;
			editSRResponse = restUtils.executePUT(reqSpec, editSRUrl);
		}
		return editSRResponse;
	}

	/**
	 * This method is used to update edit SR for project details with null seniority
	 * 
	 * @param response
	 * @param userName
	 * @return response
	 * @throws Exception
	 */
	public Response updateEditSRProjectDetailsWithNullSeniority(Response response, String userName) throws Exception {
		// Get Position details from global staff request response
		Map<Object, Object> mapOfEditSR = getPositionDetailsMap(response);

		// Get skills
		JsonPath skillsJsonPathData = getAlebrthaSkills();

		// Edit Sr
		String jsonBody = editSRRevampPayloadHelper.getEditSrProjectDetailsNullSeniorityPayload(mapOfEditSR,
				skillsJsonPathData, userName);

		if (jsonBody == null) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Json body is null hence can not edit SR");
		} else {
			RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId)
					.contentType(ContentType.JSON).body(jsonBody);
			String editSRUrl = StaffRequestBaseTest.baseUrl + StaffRequestEndpoints.srRevampPositionsUrl;
			editSRResponse = restUtils.executePUT(reqSpec, editSRUrl);
		}
		return editSRResponse;
	}

	/**
	 * This method is used to update edit SR for project details with null start
	 * date
	 * 
	 * @param response
	 * @param userName
	 * @return response
	 * @throws Exception
	 */
	public Response updateEditSRProjectDetailsWithNullStartDate(Response response, String userName) throws Exception {
		// Get Position details from global staff request response
		Map<Object, Object> mapOfEditSR = getPositionDetailsMap(response);

		// Get skills
		JsonPath skillsJsonPathData = getAlebrthaSkills();

		// Edit Sr
		String jsonBody = editSRRevampPayloadHelper.getEditSrProjectDetailsNullStartDatePayload(mapOfEditSR,
				skillsJsonPathData, userName);

		if (jsonBody == null) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Json body is null hence can not edit SR");
		} else {
			RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId)
					.contentType(ContentType.JSON).body(jsonBody);
			String editSRUrl = StaffRequestBaseTest.baseUrl + StaffRequestEndpoints.srRevampPositionsUrl;
			editSRResponse = restUtils.executePUT(reqSpec, editSRUrl);
		}
		return editSRResponse;
	}

	/**
	 * This method is used to update edit SR for project details with null end date
	 * 
	 * @param response
	 * @param userName
	 * @return response
	 * @throws Exception
	 */
	public Response updateEditSRProjectDetailsWithNullEndDate(Response response, String userName) throws Exception {
		// Get Position details from global staff request response
		Map<Object, Object> mapOfEditSR = getPositionDetailsMap(response);

		// Get skills
		JsonPath skillsJsonPathData = getAlebrthaSkills();

		// Edit Sr
		String jsonBody = editSRRevampPayloadHelper.getEditSrProjectDetailsNullEndDatePayload(mapOfEditSR,
				skillsJsonPathData, userName);

		if (jsonBody == null) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Json body is null hence can not edit SR");
		} else {
			RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId)
					.contentType(ContentType.JSON).body(jsonBody);
			String editSRUrl = StaffRequestBaseTest.baseUrl + StaffRequestEndpoints.srRevampPositionsUrl;
			editSRResponse = restUtils.executePUT(reqSpec, editSRUrl);
		}
		return editSRResponse;
	}

	/**
	 * This method is used to update edit SR for project details with Past End Date
	 * 
	 * @param response
	 * @param userName
	 * @return response
	 * @throws Exception
	 */
	public Response updateEditSRProjectDetailsWithPastEndDate(Response response, String userName) throws Exception {
		// Get Position details from global staff request response
		Map<Object, Object> mapOfEditSR = getPositionDetailsMap(response);

		// Get skills
		JsonPath skillsJsonPathData = getAlebrthaSkills();

		// Edit Sr
		String jsonBody = editSRRevampPayloadHelper.getEditSrProjectDetailsPastEndDatePayload(mapOfEditSR,
				skillsJsonPathData, userName);

		if (jsonBody == null) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Json body is null hence can not edit SR");
		} else {
			RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId)
					.contentType(ContentType.JSON).body(jsonBody);
			String editSRUrl = StaffRequestBaseTest.baseUrl + StaffRequestEndpoints.srRevampPositionsUrl;
			editSRResponse = restUtils.executePUT(reqSpec, editSRUrl);
		}
		return editSRResponse;
	}

	/**
	 * This method is used to update edit SR for project details with End Date
	 * Greater Than StartDate
	 * 
	 * @param response
	 * @param userName
	 * @return response
	 * @throws Exception
	 */
	public Response updateEditSRProjectDetailsWithEndDateGreaterThanStartDate(Response response, String userName)
			throws Exception {
		// Get Position details from global staff request response
		Map<Object, Object> mapOfEditSR = getPositionDetailsMap(response);

		// Get skills
		JsonPath skillsJsonPathData = getAlebrthaSkills();

		// Edit Sr
		String jsonBody = editSRRevampPayloadHelper
				.getEditSrProjectDetailsEndDateGreaterThanStartDatePayload(mapOfEditSR, skillsJsonPathData, userName);

		if (jsonBody == null) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Json body is null hence can not edit SR");
		} else {
			RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId)
					.contentType(ContentType.JSON).body(jsonBody);
			String editSRUrl = StaffRequestBaseTest.baseUrl + StaffRequestEndpoints.srRevampPositionsUrl;
			editSRResponse = restUtils.executePUT(reqSpec, editSRUrl);
		}
		return editSRResponse;
	}

	/**
	 * This method is used to update edit SR for project details with Null Primary
	 * Location
	 * 
	 * @param response
	 * @param userName
	 * @return response
	 * @throws Exception
	 */
	public Response updateEditSRProjectDetailsWithNullPrimaryLocation(Response response, String userName)
			throws Exception {
		// Get Position details from global staff request response
		Map<Object, Object> mapOfEditSR = getPositionDetailsMap(response);

		// Get skills
		JsonPath skillsJsonPathData = getAlebrthaSkills();

		// Edit Sr
		String jsonBody = editSRRevampPayloadHelper.getEditSrProjectDetailsNullLocationPayload(mapOfEditSR,
				skillsJsonPathData, userName);

		if (jsonBody == null) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Json body is null hence can not edit SR");
		} else {
			RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId)
					.contentType(ContentType.JSON).body(jsonBody);
			String editSRUrl = StaffRequestBaseTest.baseUrl + StaffRequestEndpoints.srRevampPositionsUrl;
			editSRResponse = restUtils.executePUT(reqSpec, editSRUrl);
		}
		return editSRResponse;
	}

	/**
	 * This method is used to update edit SR for assignment details
	 * 
	 * @param response
	 * @param userName
	 * @return response
	 * @throws Exception
	 */
	public Response updateEditSRAssignmentDetails(Response response, String userName) throws Exception {
		// Get Position details from global staff request response
		Map<Object, Object> mapOfEditSR = getPositionDetailsMap(response);

		// Get skills
		JsonPath skillsJsonPathData = getAlebrthaSkills();

		String jsonBody = editSRRevampPayloadHelper.getEditSrAssignmentDetailsPayload(mapOfEditSR, skillsJsonPathData,
				userName);

		if (jsonBody == null) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Json body is null hence can not edit SR");
		} else {
			RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId)
					.contentType(ContentType.JSON).body(jsonBody);
			String editSRURl = StaffRequestBaseTest.baseUrl + StaffRequestEndpoints.srRevampPositionsUrl;
			editSRResponse = restUtils.executePUT(reqSpec, editSRURl);
		}
		return editSRResponse;

	}
	
	/**
	 * Get Albertha skills with new added skill  
	 * @param jsonpath
	 * @param newSkillId
	 * @param skillName
	 * @return list
	 */
	public List<PositionNeedDTOListSrRevamp> getPositionDTOListForNewAddedSkill(JsonPath jsonpath,String newSkillId, String skillName){
		int skillCount = 5;
		PositionNeedDTOListSrRevamp positionNeedDTOListSrRevamp;
		PositionNeedDTOListSrRevamp positionNeedDTOListNewSkill;
		List<PositionNeedDTOListSrRevamp> listOfSkills = new ArrayList<PositionNeedDTOListSrRevamp>();
		for(int i=3; i<skillCount; i++) {
			int alberthaId = jsonpath.get("data[" + i + "].id");
			String competencyId = null;
			String competencyName = jsonpath.get("data[" + i + "].name");
			int evidenceValue = 3;
			String importance = "2";
			String importanceName = "Required";
			positionNeedDTOListSrRevamp = new PositionNeedDTOListSrRevamp(alberthaId, competencyId, competencyName, evidenceValue, importance, importanceName);
			listOfSkills.add(positionNeedDTOListSrRevamp);
		}
		int newSkillAlberthaId= Integer.parseInt(newSkillId);
		positionNeedDTOListNewSkill = new PositionNeedDTOListSrRevamp(newSkillAlberthaId, newSkillId,skillName,3,"2","Required");
		listOfSkills.add(positionNeedDTOListNewSkill);
		return listOfSkills;
	}
	
	/**
	 * Add and get skill id
	 * @param userName
	 * @return list
	 * @throws Exception
	 */
	public List<String> addAndGetSkill(String userName) throws Exception {
		List<String> skillNameId = new ArrayList<String>();
		String skillName = "random "+ Utilities.getCurrentDateAndTime("dd-MM-yyyy HH:mm:ss");
		String jsonBody = editSRRevampPayloadHelper.addSkillPayload(skillName, userName);
		RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId)
				.contentType(ContentType.JSON).body(jsonBody);
		String addSkillUrl = StaffRequestBaseTest.baseUrl + StaffRequestEndpoints.addSkillUrl;
		Response addSkillResponse = restUtils.executePOST(reqSpec, addSkillUrl);
		
		validateResponseToContinueTest(addSkillResponse, 200, "Skill is not created", true);
		String responseMsg = (String) restUtils.getValueFromResponse(addSkillResponse, "message");
		assertEquals(responseMsg, "Skill created successfully", "Skill is not created");
		String skillIdJsonPath = "data";
		String skillId = restUtils.getValueFromResponse(addSkillResponse, skillIdJsonPath).toString();
		skillNameId.add(skillName);
		skillNameId.add(skillId);
		
		return skillNameId;
	}
	
	/**
	 * This method is used to update edit SR with newly added skills
	 * @param response
	 * @param userName
	 * @return response
	 * @throws Exception
	 */
	public List<String> updateEditSRProjectDetailsForNewSkill(Response response, String userName) throws Exception {
		// Get Position details from global staff request response
		Map<Object, Object> mapOfEditSR = getPositionDetailsMap(response);

		//Get skill id and name
		List<String> skillInfo = addAndGetSkill(userName);
		
		// Get skills
		JsonPath skillsJsonPathData = getAlebrthaSkills();
		//Get skills with newly added skills
		List<PositionNeedDTOListSrRevamp> listOfAlberthaAndNewSkills = getPositionDTOListForNewAddedSkill(skillsJsonPathData, skillInfo.get(1), skillInfo.get(0));
		
		// Edit Sr
		String jsonBody = editSRRevampPayloadHelper.editSrProjectDetailsWithNewAddedSkillPayload(mapOfEditSR,skillsJsonPathData,
				listOfAlberthaAndNewSkills, userName);
		
		RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId)
					.contentType(ContentType.JSON).body(jsonBody);
		String editSRUrl = StaffRequestBaseTest.baseUrl + StaffRequestEndpoints.srRevampPositionsUrl;
		editSRResponse = restUtils.executePUT(reqSpec, editSRUrl);
		validateResponseToContinueTest(editSRResponse, 200, "Edit SR assignment details call was not successful", true);
		assertEquals(restUtils.getValueFromResponse(editSRResponse, "status"), "success", "Status is not success");
		
		//black List skill
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(StaffRequestBaseTest.sessionId);
		String postblackListSkillUrl = StaffRequestBaseTest.baseUrl + String.format(StaffRequestEndpoints.blackListSkillUrl,skillInfo.get(1));
		Response blackListskillResponse = restUtils.executePOST(requestSpecification, postblackListSkillUrl);
		validateResponseToContinueTest(blackListskillResponse, 200, "Black list skill API call is not successful", true);
		assertEquals(restUtils.getValueFromResponse(editSRResponse, "status"), "success", "Status is not success");
		
		List<String> messageSrNum = new ArrayList<String>();
		String positionId = (String) mapOfEditSR.get("positionId");
		String srNumber =  editSRDBHelpers.getSRNumberFromPositionId(positionId);
		String messageJsonPath = "details";
		String resonseMsg = restUtils.getValueFromResponse(blackListskillResponse, messageJsonPath).toString();
		messageSrNum.add(resonseMsg);
		messageSrNum.add(srNumber);
		return messageSrNum;
	}	
}