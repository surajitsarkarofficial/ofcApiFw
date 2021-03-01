package tests.testhelpers.submodules.staffRequest.features;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;

import database.submodules.staffRequest.features.CreateNewPositionDBHelper;
import dto.submodules.staffRequest.features.PositionNeedDTOList;
import dto.submodules.staffRequest.features.TopMatchingGlobersSkillDTO;
import endpoints.StaffingEndpoints;
import endpoints.submodules.staffRequest.features.CreateNewPositionEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.staffRequest.features.CreateNewPositionPayloadHelper;
import properties.StaffingProperties;
import tests.GlowBaseTest;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import tests.testhelpers.submodules.staffRequest.StaffRequestTestHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * 
 * @author akshata.dongare
 *
 */
public class CreateNewPositionsTestHelper extends StaffRequestTestHelper {
	
	CreateNewPositionDBHelper createNewPositionDBHelper;
	int skillCount=0;
	Response createPositionResponse=null;
	RestUtils restUtils;

	public CreateNewPositionsTestHelper(String userName) throws Exception {
		super(userName);
		createNewPositionDBHelper = new CreateNewPositionDBHelper();
		restUtils = new RestUtils();
	}

	/**
	 * Get position seniority
	 * 
	 * @return {@link String}
	 * @throws SQLException
	 */
	public String getPositionSenioritySSr(){
		String seniority = "SSr";
		return seniority;
	}

	/**
	 * This method will create shadow position
	 * 
	 * @throws Exception
	 * @throws ParseException
	 * @throws IOException
	 * @throws JSONException
	 */

	public Response createSRForShadowPosition(String userName,String positionType) throws Exception {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(StaffRequestBaseTest.sessionId);
		String alberthaUrl = StaffRequestBaseTest.baseUrl + String.format(StaffingEndpoints.getAlberthaSkills, "Test%20Automation%20Engineer", "SSr");
		Response response = restUtils.executeGET(requestSpecification, alberthaUrl);
		new StaffRequestBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch Albertha Skills.", true);
		GlowBaseTest.test.log(Status.INFO, "Albertha skills fetched successfully.");
		JsonPath jsonPathData = response.jsonPath();
		String jsonBody = new CreateNewPositionPayloadHelper().getPositionPayload(jsonPathData, userName,positionType);
		if (jsonBody == null) {

			StaffRequestBaseTest.test.log(Status.SKIP, "Json body is null hence can not create SR");
		} else {
			RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(jsonBody);
			String requestURL = StaffRequestBaseTest.baseUrl + CreateNewPositionEndpoints.positionsUrl;
			createPositionResponse = restUtils.executePOST(reqSpec, requestURL);
			new StaffRequestBaseTest().validateResponseToContinueTest(createPositionResponse, 201,
					"Create New Position api call was not successful.", true);
			}
		return createPositionResponse;
		}
	/**
	 * Get Albertha skills for existing SR 
	 * @param jsonpath
	 * @return listOfSkills
	 */

	public List<PositionNeedDTOList> getPositionDTOList(JsonPath jsonpath) {
		skillCount = 5;
		PositionNeedDTOList positionDtoList;
		List<PositionNeedDTOList> listOfSkills = new ArrayList<PositionNeedDTOList>();
			for (int i = 0; i < skillCount; i++) {
				int alberthaId = jsonpath.get("data[" + i + "].skill.id");
				String competencyId = null;
				String competencyName = jsonpath.get("data[" + i + "].skill.name");
				String importance = "2";
				String importanceName = "Required";
				positionDtoList = new PositionNeedDTOList(alberthaId, competencyId, competencyName, importance,
						importanceName);
				listOfSkills.add(positionDtoList);
			}
		return listOfSkills;
	}
	
	/**
	 * This method will return map of values needed in creating a position
	 * @param userName
	 * @param skillsJsonPathData
	 * @param positionType
	 * @param map
	 * @return map
	 */
	public Map<Object, Object> mapOfPosition(String userName, JsonPath skillsJsonPathData, String positionType, Map<Object, Object> globerCountMap){
		Map<Object, Object> mapOfPosition = new HashMap<Object, Object>();
		mapOfPosition.put("userName", userName);
		mapOfPosition.put("jsonpath", skillsJsonPathData);
		mapOfPosition.put("positionType", positionType);
		mapOfPosition.put("totalGlobersCount", globerCountMap.get("totalGlobersCount"));
		mapOfPosition.put("availableGlobersCount", globerCountMap.get("availableGlobersCount"));
		return mapOfPosition;
	}
	
	/**
	 * This method will create map of glober count
	 * @param topMatchingGloberJsonBody
	 * @return map
	 * @throws Exception
	 */
	public Map<Object, Object> globerMatchingCount(String topMatchingGloberJsonBody) throws Exception{
		RequestSpecification globerMatchingRequestSpecification = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(topMatchingGloberJsonBody);
		String topMatchingGloberUrl = StaffRequestBaseTest.baseUrl + CreateNewPositionEndpoints.topMatchingGloberCountUrl;
		Response topMatchingGloberResponse = restUtils.executePOST(globerMatchingRequestSpecification, topMatchingGloberUrl);
		validateResponseToContinueTest(topMatchingGloberResponse, 200, "Top matching glober API call failed", true);
		int availableGlobersCount = (int) restUtils.getValueFromResponse(topMatchingGloberResponse, "$.details.tpMatchingGlobersCount");
		int totalGlobersCount = (int) restUtils.getValueFromResponse(topMatchingGloberResponse, "$.details.matchingGlobersCount");
		
		Map<Object, Object> globerCountMap = new HashMap<Object, Object>();
		globerCountMap.put("availableGlobersCount", availableGlobersCount);
		globerCountMap.put("totalGlobersCount", totalGlobersCount);
		
		return globerCountMap;
	}
	
	/**
	 * This method will get skill jsonpPath
	 * @return jsonPath
	 * @throws Exception
	 */
	public JsonPath getSkillsJsonPathData() throws Exception {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(StaffRequestBaseTest.sessionId);
		String getSkillsUrl = StaffRequestBaseTest.baseUrl + CreateNewPositionEndpoints.cachedSkillsUrl;
		Response response = restUtils.executeGET(requestSpecification, getSkillsUrl);
		validateResponseToContinueTest(response, 200, "Unable to fetch Skills", true);
		StaffRequestBaseTest.test.log(Status.INFO, "Skills fetched successfully.");
		JsonPath skillsJsonPathData = response.jsonPath();
		return skillsJsonPathData;
	}
	/**
	 * This method will create SR using new flow for shadow, globant staff position
	 * @param userName
	 * @param positionType
	 * @return response
	 * @throws Exception
	 */
	public Response createSRUsingNewFlow(String userName,String positionType) throws Exception {
		JsonPath skillsJsonPathData = getSkillsJsonPathData();
		
		//Get top matching glober count
		String topMatchingGloberJsonBody = new CreateNewPositionPayloadHelper().topMatchingGloberPayload(skillsJsonPathData, userName);
		
		//Get glober count
		Map<Object, Object> globerCountMap = globerMatchingCount(topMatchingGloberJsonBody);
		
		//Values from map to create position
		Map<Object, Object> mapOfPosition= mapOfPosition(userName,skillsJsonPathData, positionType, globerCountMap);
		
		//Create SR Payload
		String jsonBody = new CreateNewPositionPayloadHelper().createSRValidValuesPayload(mapOfPosition);
		Reporter.log("jsonBody :"+ jsonBody,true);
		if (jsonBody == null) {
			GlowBaseTest.test.log(Status.SKIP, "Json body is null hence can not create SR");
		}else {
			RequestSpecification createSRReqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON)
					.body(jsonBody);
			String createSrRequestURL = StaffRequestBaseTest.baseUrl + CreateNewPositionEndpoints.srRevampPositionsUrl;
			createPositionResponse = restUtils.executePOST(createSRReqSpec, createSrRequestURL);
		}
		return createPositionResponse;
	}
	
	/**
	 * This method will create SR using new flow for shadow, globant staff position for variable load
	 * @param userName
	 * @param positionType
	 * @return response
	 * @throws Exception
	 */
	public Response createSRUsingNewFlowVariableLoad(String userName,String positionType) throws Exception {
		JsonPath skillsJsonPathData = getSkillsJsonPathData();
		
		//Get top matching glober count
		String topMatchingGloberJsonBody = new CreateNewPositionPayloadHelper().topMatchingGloberPayload(skillsJsonPathData, userName);
				
		//Get glober count
		Map<Object, Object> globerCountMap = globerMatchingCount(topMatchingGloberJsonBody);
		
		//Map of values needed to create position
		Map<Object, Object> mapOfPosition= mapOfPosition(userName,skillsJsonPathData, positionType,globerCountMap);
		
		String jsonBody = new CreateNewPositionPayloadHelper().createSrVariableLoadPayload(mapOfPosition);
		if (jsonBody == null) {
			GlowBaseTest.test.log(Status.SKIP, "Json body is null hence can not create SR");
		}else {
			RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON)
					.body(jsonBody);
			String createSrRequestURL = StaffRequestBaseTest.baseUrl + CreateNewPositionEndpoints.srRevampPositionsUrl;
			createPositionResponse = restUtils.executePOST(reqSpec, createSrRequestURL);
		}
		return createPositionResponse;
	}

	/**
	 * This method will return SR number from DB by providing position id
	 * @param positionId
	 * @return
	 * @throws SQLException
	 */
	public String getSRNumberFromDB(String positionId) throws SQLException {
		String dbSRNumber = createNewPositionDBHelper.getSRNumberFromPositionId(positionId);
		return dbSRNumber;
	}

	/**
	 * This method will return response for invalid position, seniority template
	 * @return response
	 * @throws Exception 
	 */
	public Response selectInvalidPositionSeniorityTemplate(String userName,String positionType) throws Exception {
		JsonPath skillsJsonPathData = getSkillsJsonPathData();
		
		//Get top matching glober count
		String topMatchingGloberJsonBody = new CreateNewPositionPayloadHelper().topMatchingGloberPayload(skillsJsonPathData, userName);
				
		//Get glober count
		Map<Object, Object> globerCountMap = globerMatchingCount(topMatchingGloberJsonBody);		
		
		//Map of values needed to create position
		Map<Object, Object> mapOfPosition= mapOfPosition(userName,skillsJsonPathData, positionType, globerCountMap);
		
		String jsonBody = new CreateNewPositionPayloadHelper().invalidTemplatePayload(mapOfPosition);
		if (jsonBody == null) {
			GlowBaseTest.test.log(Status.SKIP, "Json body is null hence can not create SR");
		}else {
			RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON)
					.body(jsonBody);
			String createSrRequestURL = StaffRequestBaseTest.baseUrl + CreateNewPositionEndpoints.srRevampPositionsUrl;
			createPositionResponse = restUtils.executePOST(reqSpec, createSrRequestURL);
		}
		return createPositionResponse;
	}

	/**
	 * Get skill id and star for top matching glober payload
	 * @param jsonpath
	 * @return
	 */
	public List<TopMatchingGlobersSkillDTO> getTopMatchingGloberSkillsDTOList(JsonPath jsonpath){
		skillCount = 25;
		TopMatchingGlobersSkillDTO topMatchingGlobersSkillDTO;
		List<TopMatchingGlobersSkillDTO> listOfSkills = new ArrayList<TopMatchingGlobersSkillDTO>();
		for(int i=20; i<skillCount; i++) {
			String skillId = jsonpath.getString("data[" + i + "].id");
			String stars = "2";
			topMatchingGlobersSkillDTO = new TopMatchingGlobersSkillDTO(skillId,stars);
			listOfSkills.add(topMatchingGlobersSkillDTO);
		}
		return listOfSkills;
	}

	/**
	 * This method will return top matching glober count from albertha API and glow API
	 * @param userName
	 * @return list Of Count
	 * @throws Exception
	 */
	public List<Integer> getAlberthaTopMatchingGloberCount(String userName) throws Exception {
		int alberthaTopMatchingGloberCount = 0;
		List<Integer> list = new ArrayList<Integer>();

		//Get skill response
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(StaffRequestBaseTest.sessionId);
		String getSkillsUrl = StaffRequestBaseTest.baseUrl + CreateNewPositionEndpoints.cachedSkillsUrl;
		Response response = restUtils.executeGET(requestSpecification, getSkillsUrl);
		new StaffRequestBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch Skills", true);
		StaffRequestBaseTest.test.log(Status.INFO, "Skills fetched successfully.");
		JsonPath skillsJsonPathData = response.jsonPath();

		String jsonBody = new CreateNewPositionPayloadHelper().topMatchingGloberPayload(skillsJsonPathData, userName);
		
		if(jsonBody == null) {
			GlowBaseTest.test.log(Status.SKIP, "Json body is null hence can not match globers");
		}else {
			RequestSpecification reqSpec = RestAssured.with().auth().basic("username", "password").contentType(ContentType.JSON).body(jsonBody);
			
			String topMatchingGloberAlberthaUrl = StaffingProperties.alberthaBaseURL+CreateNewPositionEndpoints.alberthaTopMatchingGlobantCountUrl;
			Response alberthaTopMatchingGloberResponse = restUtils.executePOST(reqSpec, topMatchingGloberAlberthaUrl);
			new StaffRequestBaseTest().validateResponseToContinueTest(alberthaTopMatchingGloberResponse, 200, "Top matching glober albertha API call failed", true);
			
			@SuppressWarnings("unchecked")
			List<Integer> alberthaTopMatchingGloberCountIdList = (List<Integer>) restUtils.getValueFromResponse(alberthaTopMatchingGloberResponse, "$.details..tpResults..id");
			alberthaTopMatchingGloberCount = alberthaTopMatchingGloberCountIdList.size();
			
			RequestSpecification globerMatchingRequestSpecification = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(jsonBody);
			String topMatchingGloberUrl = StaffRequestBaseTest.baseUrl + CreateNewPositionEndpoints.topMatchingGloberCountUrl;
			Response topMatchingGloberResponse = restUtils.executePOST(globerMatchingRequestSpecification, topMatchingGloberUrl);
			new StaffRequestBaseTest().validateResponseToContinueTest(topMatchingGloberResponse, 200, "Top matching glober API call failed", true);
			int topMatchingGloberCount = (int) restUtils.getValueFromResponse(topMatchingGloberResponse, "$.details.tpMatchingGlobersCount");
			
			list.add(alberthaTopMatchingGloberCount);
			list.add(topMatchingGloberCount);
		}
		return list;
	}
	
	/**
	 * This method will get response from location API
	 * @return response
	 * @author akshata.dongare
	 */
	public Response getLocationListResponse() {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(StaffRequestBaseTest.sessionId);
		String getLocationUrl = StaffRequestBaseTest.baseUrl + CreateNewPositionEndpoints.getLocationsUrl;
		Response response = restUtils.executeGET(requestSpecification, getLocationUrl);
		return response;
	}
	
	/**
	 * Get location Name from id
	 * @param locationId
	 * @return String
	 * @author akshata.dongare
	 * @throws SQLException 
	 */
	public String getLocationNameFromId(String locationId) throws SQLException {
		String locationName = createNewPositionDBHelper.getLocationNameFromId(locationId);
		return locationName;
	}
	
	/**
	 * Get client API response
	 * @return response
	 * @author akshata.dongare
	 */
	public Response getClientListResponse() {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(StaffRequestBaseTest.sessionId);
		String getClientUrl = StaffRequestBaseTest.baseUrl + String.format(CreateNewPositionEndpoints.getClientUrl,"true");
		Response response = restUtils.executeGET(requestSpecification, getClientUrl);
		return response;
	}
	
	/**
	 * Get client name from db
	 * @param clientId
	 * @return String
	 * @throws SQLException 
	 */
	public String getClientNameDb(String clientId) throws SQLException {
		String clientName = createNewPositionDBHelper.getClientNameFromClientId(clientId);
		return clientName;
	}
	
	/**
	 * Get project list API response
	 * @return response
	 * @throws Exception
	 */
	public Response getProjectListResponse() throws Exception {
		int i = Utilities.getRandomDay();
		String clientId = restUtils.getValueFromResponse(getClientListResponse(), "details.["+i+"].id").toString();
		
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(StaffRequestBaseTest.sessionId);
		String getProjectListUrl = StaffRequestBaseTest.baseUrl + String.format(CreateNewPositionEndpoints.getProjectUrl,clientId,"true");
		Response response = restUtils.executeGET(requestSpecification, getProjectListUrl);
		return response;
	}
	
	/**
	 * Get project name db
	 * @param projectId
	 * @return String
	 * @throws SQLException 
	 */
	public String getProjectNameDb(String projectId) throws SQLException {
		String projectName = createNewPositionDBHelper.getProjectNameFromProjectId(projectId);
		return projectName;
	}
	
	/**
	 * Get position list API response
	 * @return response
	 */
	public Response getPositionResponse() {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(StaffRequestBaseTest.sessionId);
		String getPositionUrl = StaffRequestBaseTest.baseUrl + String.format(CreateNewPositionEndpoints.getPositionRoleUrl,"false");
		Response response = restUtils.executeGET(requestSpecification, getPositionUrl);
		return response;
	}
	
	/**
	 * Get position name db
	 * @param positionId
	 * @return String
	 * @throws SQLException 
	 */
	public String getPositionNameDb(String positionRoleId) throws SQLException {
		String positionName = createNewPositionDBHelper.getPositionRoleNameAccordingToPositionRolId(positionRoleId);
		return positionName;
	}
	
	/**
	 * Get sow id API response
	 * @return response
	 * @throws Exception
	 */
	public Response getSowIdResponse() throws Exception {
		String projectId = restUtils.getValueFromResponse(getProjectListResponse(), "details.[0].projectId").toString();
		
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(StaffRequestBaseTest.sessionId);
		String getSowIdUrl = StaffRequestBaseTest.baseUrl + String.format(CreateNewPositionEndpoints.getSowIdUrl,projectId);
		Response response = restUtils.executeGET(requestSpecification, getSowIdUrl);
		return response;
	}
	
	/**
	 * Get sow id and name db
	 * @param projectId
	 * @return list
	 * @throws SQLException
	 */
	public List<String> getSowIdAndName(String projectId) throws SQLException {
		List<String> getSowInfo = new ArrayList<String>();
		String sowId = createNewPositionDBHelper.getSowId(projectId);
		String sowIdName = createNewPositionDBHelper.getSowName(projectId).get(0);
		getSowInfo.add(sowId);
		getSowInfo.add(sowIdName);
		return getSowInfo;
	}
	
}
