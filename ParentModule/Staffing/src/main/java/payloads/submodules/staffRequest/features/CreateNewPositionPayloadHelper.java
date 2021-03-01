package payloads.submodules.staffRequest.features;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aventstack.extentreports.Status;

import database.submodules.staffRequest.features.CreateNewPositionDBHelper;
import dto.submodules.staffRequest.features.PositionNeedDTOList;
import dto.submodules.staffRequest.features.PositionNeedDTOListSrRevamp;
import dto.submodules.staffRequest.features.PositionSeniorityMappingDTO;
import dto.submodules.staffRequest.features.TopMatchingGlobersSkillDTO;
import io.restassured.path.json.JsonPath;
import tests.testhelpers.submodules.staffRequest.features.CreateNewPositionsTestHelper;
import utils.ExtentHelper;
import utils.Utilities;

public class CreateNewPositionPayloadHelper {
	
	CreateNewPositionDBHelper createNewPositionDBHelper = new CreateNewPositionDBHelper();
	String json = null;
	Map<Object, Object> mapOfPosition = new HashMap<Object, Object>();
	
	/**
	 * This method is used to generate payload for create SR of shadow position
	 * @param jsonpath
	 * @param 
	 * @return json
	 * @throws Exception 
	 */
	public String getPositionPayload(JsonPath jsonPathData, String userName, String positionType) throws Exception {
		json = null;
		String anywhereLocationId = createNewPositionDBHelper.getLocationIdFromLocationName("Anywhere");
		String projectId = createNewPositionDBHelper.getProjectId();
		String sowId = createNewPositionDBHelper.getSowId(projectId);
		String studioId = createNewPositionDBHelper.getStudioId(projectId);
		String projectStudioName = createNewPositionDBHelper.getProjectStudioName(studioId);
		String clientId = createNewPositionDBHelper.getClientId(projectId);
		String positionSeniorityName = new CreateNewPositionsTestHelper(userName).getPositionSenioritySSr();
		String locationId = createNewPositionDBHelper.getLocationIdExceptParticularId(anywhereLocationId);
		String startDate = Utilities.getTomorrow("dd-MM-yyyy");
		String endYearSplit[] = startDate.split("-");
		int endYear = Integer.parseInt(endYearSplit[2]) + 1;
		String endDate = Utilities.getFutureDate("dd-MM-yyyy", Utilities.getRandomDay(), Utilities.getRandomMonth(),
				endYear);
		
		List<PositionNeedDTOList> listOfAlberthaSkills = new CreateNewPositionsTestHelper(userName).getPositionDTOList(jsonPathData);

		boolean skillList = listOfAlberthaSkills.isEmpty();
		if (skillList == true) {
			ExtentHelper.getExtentTest().log(Status.INFO, "Skill list is null");
		} else {
			mapOfPosition.put("sendMeCopy", false);
			mapOfPosition.put("index", 3);
			mapOfPosition.put("positionName", "TAE");
			mapOfPosition.put("positionRoleId", 1);
			mapOfPosition.put("selectedPositionName", "Test Automation Engineer");
			mapOfPosition.put("positionSeniorityId", 5);
			mapOfPosition.put("travelPeriodType", "DAYS");
			mapOfPosition.put("locationOffshore", true);
			mapOfPosition.put("travelPeriodLength", 0);
			mapOfPosition.put("load", 100);
			mapOfPosition.put("sowId", sowId);
			mapOfPosition.put("locationId", locationId);
			mapOfPosition.put("projectId", projectId);
			mapOfPosition.put("clientId", clientId);
			mapOfPosition.put("positionType", positionType);
			mapOfPosition.put("clientInterviewRequired", false);
			mapOfPosition.put("englishRequired", true);
			mapOfPosition.put("opening", true);
			mapOfPosition.put("replacement", false);
			mapOfPosition.put("comments", "New SR for Test Automation");
			mapOfPosition.put("startDate", startDate);
			mapOfPosition.put("endDate", endDate);
			mapOfPosition.put("positionTypeName", positionType);
			mapOfPosition.put("studioId", studioId);
			mapOfPosition.put("projectStudioName", projectStudioName);
			mapOfPosition.put("sfdcFlow", false);
			mapOfPosition.put("quantity", "1");
			mapOfPosition.put("positionSeniorityName", positionSeniorityName);
			mapOfPosition.put("positionNeedDTOList", listOfAlberthaSkills);
			json = Utilities.createJsonBodyFromMap(mapOfPosition);
		}
		return json;
	}
	
	/**
	 * This method will create payload for create SR
	 * @param data
	 * @return String
	 */
	public String getPositionPayloadSRRevamp(Map<Object, Object> data) {
		mapOfPosition.put("sendMeCopy",false);
		mapOfPosition.put("quantity",1);
		mapOfPosition.put("travelPeriodType","DAYS");
		mapOfPosition.put("locationOffshore",true);
		mapOfPosition.put("travelPeriodLength",0);
		mapOfPosition.put("load",data.get("load"));
		mapOfPosition.put("positionName",data.get("positionName"));
		mapOfPosition.put("selectedPositionName",data.get("selectedPositionName"));
		mapOfPosition.put("positionSeniorityName",data.get("positionSeniorityName"));
		mapOfPosition.put("positionSeniorityId",data.get("positionSeniorityId"));
		mapOfPosition.put("positionRoleId",data.get("positionRoleId"));
		mapOfPosition.put("locationId",data.get("locationId"));
		mapOfPosition.put("clientId",data.get("clientId"));
		mapOfPosition.put("sowId",data.get("sowId"));
		mapOfPosition.put("projectId",data.get("projectId"));
		mapOfPosition.put("secondaryLocationId",data.get("secondaryLocationId"));
		mapOfPosition.put("positionType",data.get("positionType"));
		mapOfPosition.put("clientInterviewRequired",true);
		mapOfPosition.put("englishRequired",false);
		mapOfPosition.put("opening",true);
		mapOfPosition.put("replacement",false);
		mapOfPosition.put("positionNeedDTOList",data.get("positionNeedDTOList"));
		mapOfPosition.put("startDate",data.get("startDate"));
		mapOfPosition.put("endDate",data.get("endDate"));
		mapOfPosition.put("positionTypeName",data.get("positionTypeName"));
		mapOfPosition.put("sfdcOpeningId",null);
		mapOfPosition.put("sfdcFlow",false);
		mapOfPosition.put("openingId",null);
		mapOfPosition.put("opportunityId",null);	
		mapOfPosition.put("newSrFlow",true);
		mapOfPosition.put("validateAll",true);
		mapOfPosition.put("index",2);
		json = Utilities.createJsonBodyFromMap(mapOfPosition);
		return json;
	}

	/**
	 * This method will create map of db values
	 * @param userName
	 * @param jsonpath
	 * @param positionType
	 * @return map
	 * @throws Exception
	 */
	public Map<Object, Object> getDbPositiveValuesPayload(Map<Object,Object> mapData) throws Exception {
		String anywhereLocationId = createNewPositionDBHelper.getLocationIdFromLocationName("Anywhere");;
		PositionSeniorityMappingDTO positionSeniorityMappingDTO = createNewPositionDBHelper.getPositionAndSeniority();

		String projectId = createNewPositionDBHelper.getProjectId();
		String sowId = createNewPositionDBHelper.getSowId(projectId);
		String clientId = createNewPositionDBHelper.getClientId(projectId);
		int positionRoleId = positionSeniorityMappingDTO.getPositionRoleId();
		String selectedPositionName = positionSeniorityMappingDTO.getPositionName();
		int positionSeniorityId = positionSeniorityMappingDTO.getSeniorityId();
		String positionSeniorityName = positionSeniorityMappingDTO.getSeniorityName();
		String locationId = createNewPositionDBHelper.getLocationIdExceptParticularId(anywhereLocationId);
		List<String> secondaryLocationIds = createNewPositionDBHelper.getSecondaryLocationIdExceptPrimaryLocation(locationId,anywhereLocationId);
		String userName = (String) mapData.get("userName");
		JsonPath jsonpath = (JsonPath) mapData.get("jsonpath");
		List<PositionNeedDTOListSrRevamp> listOfAlberthaSkills = new CreateNewPositionsTestHelper(userName).getPositionSRRevampDTOList(jsonpath);
		String startDate = Utilities.getTomorrow("dd-MM-yyyy");
		String endDate = Utilities.getFutureDate("dd-MM-yyyy", 30);
		
		mapOfPosition.put("load",100);
		mapOfPosition.put("positionName",selectedPositionName);
		mapOfPosition.put("selectedPositionName",selectedPositionName);
		mapOfPosition.put("positionSeniorityName",positionSeniorityName);
		mapOfPosition.put("positionSeniorityId",positionSeniorityId);
		mapOfPosition.put("positionRoleId",positionRoleId);
		mapOfPosition.put("locationId",locationId);
		mapOfPosition.put("clientId",clientId);
		mapOfPosition.put("sowId",sowId);
		mapOfPosition.put("projectId",projectId);
		mapOfPosition.put("secondaryLocationId",secondaryLocationIds);
		mapOfPosition.put("positionType",mapData.get("positionType"));
		mapOfPosition.put("positionNeedDTOList",listOfAlberthaSkills);
		mapOfPosition.put("totalGlobersCount", mapData.get("totalGlobersCount"));
		mapOfPosition.put("availableGlobersCount", mapData.get("availableGlobersCount"));
		mapOfPosition.put("startDate",startDate);
		mapOfPosition.put("endDate",endDate);
		mapOfPosition.put("positionTypeName",mapData.get("positionType"));
		return mapOfPosition;
	}
	
	/**
	 * This method will create payload for valid values
	 * @param userName
	 * @param jsonpath
	 * @param positionType
	 * @return String 
	 * @throws Exception
	 */
	public String createSRValidValuesPayload(Map<Object, Object> mapData) throws Exception {
		mapOfPosition = getDbPositiveValuesPayload(mapData);
		return getPositionPayloadSRRevamp(mapOfPosition);
	}
	
	/**
	 * This method will create Sr using variable load values
	 * @param userName
	 * @param jsonpath
	 * @param positionType
	 * @return String
	 * @throws Exception
	 */
	public String createSrVariableLoadPayload(Map<Object, Object> mapData) throws Exception {
		mapOfPosition = getDbPositiveValuesPayload(mapData);
		mapOfPosition.put("positionName","Business Analyst");
		mapOfPosition.put("selectedPositionName","Business Analyst");
		mapOfPosition.put("positionSeniorityName","SSr");
		mapOfPosition.put("positionSeniorityId",5);
		mapOfPosition.put("positionRoleId",106);
		mapOfPosition.put("load", 60);
		return getPositionPayloadSRRevamp(mapOfPosition);
	}
	
	/**
	 * This method will create payload for invalid template
	 * @param userName
	 * @param jsonpath
	 * @param positionType
	 * @return String
	 * @throws Exception
	 */
	public String invalidTemplatePayload(Map<Object, Object> mapData) throws Exception {
		mapOfPosition = getDbPositiveValuesPayload(mapData);
		mapOfPosition.put("positionName","Business Analyst");
		mapOfPosition.put("positionRoleId",106);
		mapOfPosition.put("positionSeniorityId",53914449);
		mapOfPosition.put("positionSeniorityName","Sr Level 1");
		return getPositionPayloadSRRevamp(mapOfPosition);
		
	}
	
	/**
	 * This method will generate payload for top matching glober count
	 * @param listOfSkills
	 * @return
	 * @throws Exception 
	 */
	public String topMatchingGloberPayload(JsonPath jsonPath, String userName) throws Exception {
		PositionSeniorityMappingDTO positionSeniorityMappingDTO = createNewPositionDBHelper.getPositionAndSeniority();
		String position = positionSeniorityMappingDTO.getPositionName();
		String seniority = positionSeniorityMappingDTO.getSeniorityName();
		String startDate = Utilities.getTomorrow("yyyy-MM-dd");
		String endDate = Utilities.getFutureDate("yyyy-MM-dd", 365);

		List<TopMatchingGlobersSkillDTO> listOfAlberthaSkills = new CreateNewPositionsTestHelper(userName).getTopMatchingGloberSkillsDTOList(jsonPath);
		Map<Object, Object> mapOfTopMatchingGloberCount = new HashMap<Object, Object>();

		mapOfTopMatchingGloberCount.put("position", position);
		mapOfTopMatchingGloberCount.put("seniority", seniority);
		mapOfTopMatchingGloberCount.put("start_date", startDate);
		mapOfTopMatchingGloberCount.put("end_date", endDate);
		mapOfTopMatchingGloberCount.put("skills", listOfAlberthaSkills);
		mapOfTopMatchingGloberCount.put("secondary_locations", null);
		json = Utilities.createJsonBodyFromMap(mapOfTopMatchingGloberCount);
		return json;
	}
}
