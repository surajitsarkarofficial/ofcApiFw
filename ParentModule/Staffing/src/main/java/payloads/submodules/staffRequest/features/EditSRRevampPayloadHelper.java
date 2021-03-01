package payloads.submodules.staffRequest.features;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aventstack.extentreports.Status;

import database.submodules.staffRequest.features.EditSRDBHelpers;
import dto.submodules.staffRequest.features.PositionNeedDTOListSrRevamp;
import dto.submodules.staffRequest.features.PositionSeniorityMappingDTO;
import io.restassured.path.json.JsonPath;
import tests.testhelpers.submodules.staffRequest.StaffRequestTestHelper;
import utils.ExtentHelper;
import utils.Utilities;

/**
 * 
 * @author akshata.dongare
 *
 */
public class EditSRRevampPayloadHelper {

	EditSRDBHelpers editSRDBHelpers = new EditSRDBHelpers();
	Map<Object, Object> mapOfEditSR = new HashMap<Object, Object>();
	String json = null;

	/**
	 * This method is used to generate payload for edit SR for project details
	 * 
	 * @param responseData
	 * @param dbData
	 * @return String
	 * @throws Exception
	 */
	public String getEditSrProjectDetailsPayload(Map<Object, Object> responseData, Map<Object, Object> dbData)
			throws Exception {
		mapOfEditSR.put("quantity", 1);
		mapOfEditSR.put("sowId", responseData.get("sowId"));
		mapOfEditSR.put("clientId", responseData.get("clientId"));
		mapOfEditSR.put("isEditSR", true);
		mapOfEditSR.put("projectId", responseData.get("projectId"));
		mapOfEditSR.put("positionId", responseData.get("positionId"));
		mapOfEditSR.put("index", 1);
		mapOfEditSR.put("selectedPositionName", dbData.get("selectedPositionName"));
		mapOfEditSR.put("positionName", dbData.get("selectedPositionName") + " position");
		mapOfEditSR.put("load", dbData.get("load"));
		mapOfEditSR.put("positionRoleId", dbData.get("positionRoleId"));
		mapOfEditSR.put("positionSeniorityId", dbData.get("positionSeniorityId"));
		mapOfEditSR.put("positionSeniorityName", "Sr Adv");
		mapOfEditSR.put("startDate", dbData.get("startDate"));
		mapOfEditSR.put("endDate", dbData.get("endDate"));
		mapOfEditSR.put("locationId", dbData.get("locationId"));
		mapOfEditSR.put("secondaryLocationId", dbData.get("secondaryLocationId"));
		mapOfEditSR.put("locationOffshore", true);
		mapOfEditSR.put("travelPeriodType", "DAYS");
		mapOfEditSR.put("travelPeriodLength", 0);
		mapOfEditSR.put("positionNeedDTOList", dbData.get("positionNeedDTOList"));
		mapOfEditSR.put("clientInterviewRequired", false);
		mapOfEditSR.put("newSrFlow", true);
		mapOfEditSR.put("validateAll", true);
		mapOfEditSR.put("studioId", dbData.get("studioId"));
		json = Utilities.createJsonBodyFromMap(mapOfEditSR);
		return json;
	}

	/**
	 * This method will create map of positive data with db values
	 * 
	 * @param data
	 * @param jsonpath
	 * @param userName
	 * @return map
	 * @throws Exception
	 */
	public Map<Object, Object> getDbPositiveValuePayload(Map<Object, Object> responseData, JsonPath jsonpath,
			String userName) throws Exception {
		boolean skillList = false;
		String positionRoleIdTAE = editSRDBHelpers.getPositionRoleIdAccordingToPositionRolName("Test Automation Engineer");
		String positionSeniorityIdSrAdv = editSRDBHelpers.getSeniorityIdByName("Sr Adv");
		PositionSeniorityMappingDTO positionSeniorityMappingDTO = editSRDBHelpers.getPositionAndSeniority();
		String selectedPositionName = positionSeniorityMappingDTO.getPositionName();
		String startDate = Utilities.getTomorrow("dd-MM-yyyy");
		String endDate = Utilities.getFutureDate("dd-MM-yyyy", 30);
		String anywhereLocationId = editSRDBHelpers.getLocationIdFromLocationName("Anywhere");
		String locationId = editSRDBHelpers.getLocationIdExceptParticularId(anywhereLocationId);
		List<String> secondaryLocationIds = editSRDBHelpers.getSecondaryLocationIdExceptPrimaryLocation(locationId,
				anywhereLocationId);
		String studioId;
		if (responseData.get("projectId") == null) {
			studioId = editSRDBHelpers.getRandomStudioId();
		} else {
			studioId = editSRDBHelpers.getStudioId(responseData.get("projectId").toString());
		}
		List<PositionNeedDTOListSrRevamp> listOfAlberthaSkills = new StaffRequestTestHelper(userName)
				.getPositionSRRevampDTOList(jsonpath);
		skillList = listOfAlberthaSkills.isEmpty();

		if (skillList == true) {
			ExtentHelper.getExtentTest().log(Status.INFO, "Skill list is null");
		} else {
			mapOfEditSR.put("load", 100);
			mapOfEditSR.put("positionRoleId", positionRoleIdTAE);
			mapOfEditSR.put("positionSeniorityId", positionSeniorityIdSrAdv);
			mapOfEditSR.put("selectedPositionName", selectedPositionName);
			mapOfEditSR.put("positionName", selectedPositionName + " position");
			mapOfEditSR.put("startDate", startDate);
			mapOfEditSR.put("endDate", endDate);
			mapOfEditSR.put("locationId", locationId);
			mapOfEditSR.put("secondaryLocationId", secondaryLocationIds);
			mapOfEditSR.put("positionNeedDTOList", listOfAlberthaSkills);
			mapOfEditSR.put("studioId", studioId);
		}

		return mapOfEditSR;
	}

	/**
	 * This method will create payload for positive scenarios
	 * 
	 * @param responseData
	 * @param jsonpath
	 * @param userName
	 * @return {@link String}
	 * @throws Exception
	 */
	public String editSrProjectDetailsValidValuesPayload(Map<Object, Object> responseData, JsonPath jsonpath,
			String userName) throws Exception {
		mapOfEditSR = getDbPositiveValuePayload(responseData, jsonpath, userName);
		return getEditSrProjectDetailsPayload(responseData, mapOfEditSR);
	}

	/**
	 * This method is used to generate payload for edit SR for project details for
	 * variable load position
	 * 
	 * @param responseData
	 * @param jsonpath
	 * @param userName
	 * @return String
	 * @throws Exception
	 */
	public String getEditSrProjectDetailsVariableLoadPayload(Map<Object, Object> responseData, JsonPath jsonpath,
			String userName) throws Exception {
		String positionRoleIdBA = editSRDBHelpers.getPositionRoleIdAccordingToPositionRolName("Business Analyst");;
		mapOfEditSR = getDbPositiveValuePayload(responseData, jsonpath, userName);
		mapOfEditSR.put("positionRoleId", positionRoleIdBA);
		mapOfEditSR.put("load", 60);
		return getEditSrProjectDetailsPayload(responseData, mapOfEditSR);
	}

	/**
	 * This method is used to generate payload for edit SR for project details with
	 * null position role id
	 * 
	 * @param responseData
	 * @param jsonpath
	 * @param userName
	 * @return String
	 * @throws Exception
	 */
	public String getEditSrProjectDetailsNullPositionPayload(Map<Object, Object> responseData, JsonPath jsonpath,
			String userName) throws Exception {
		String positionRoleIdTAE = null;
		mapOfEditSR = getDbPositiveValuePayload(responseData, jsonpath, userName);
		mapOfEditSR.put("positionRoleId", positionRoleIdTAE);
		return getEditSrProjectDetailsPayload(responseData, mapOfEditSR);
	}

	/**
	 * This method is used to generate payload for edit SR for project details with
	 * null seniority role id
	 * 
	 * @param responseData
	 * @param jsonpath
	 * @param userName
	 * @return String
	 * @throws Exception
	 */
	public String getEditSrProjectDetailsNullSeniorityPayload(Map<Object, Object> responseData, JsonPath jsonpath,
			String userName) throws Exception {
		String positionSeniorityIdSrAdv = null;
		mapOfEditSR = getDbPositiveValuePayload(responseData, jsonpath, userName);
		mapOfEditSR.put("positionSeniorityId", positionSeniorityIdSrAdv);
		return getEditSrProjectDetailsPayload(responseData, mapOfEditSR);
	}

	/**
	 * This method is used to generate payload for edit SR project details for Null
	 * Start Date
	 * 
	 * @param responseData
	 * @param jsonpath
	 * @param userName
	 * @return String
	 * @throws Exception
	 */
	public String getEditSrProjectDetailsNullStartDatePayload(Map<Object, Object> responseData, JsonPath jsonpath,
			String userName) throws Exception {
		String startDate = null;
		mapOfEditSR = getDbPositiveValuePayload(responseData, jsonpath, userName);
		mapOfEditSR.put("startDate", startDate);
		return getEditSrProjectDetailsPayload(responseData, mapOfEditSR);
	}

	/**
	 * This method is used to generate payload for for Null End Date
	 * 
	 * @param responseData
	 * @param jsonpath
	 * @param userName
	 * @return String
	 * @throws Exception
	 */
	public String getEditSrProjectDetailsNullEndDatePayload(Map<Object, Object> responseData, JsonPath jsonpath,
			String userName) throws Exception {
		String endDate = null;
		mapOfEditSR = getDbPositiveValuePayload(responseData, jsonpath, userName);
		mapOfEditSR.put("endDate", endDate);
		return getEditSrProjectDetailsPayload(responseData, mapOfEditSR);
	}

	/**
	 * This method is used to generate payload for edit SR for project details for
	 * past end date
	 * 
	 * @param responseData
	 * @param jsonpath
	 * @param userName
	 * @return String
	 * @throws Exception
	 */
	public String getEditSrProjectDetailsPastEndDatePayload(Map<Object, Object> responseData, JsonPath jsonpath,
			String userName) throws Exception {
		String endDate = Utilities.getDateInPast("dd-MM-yyyy", 2);
		mapOfEditSR = getDbPositiveValuePayload(responseData, jsonpath, userName);
		mapOfEditSR.put("endDate", endDate);
		return getEditSrProjectDetailsPayload(responseData, mapOfEditSR);
	}

	/**
	 * This method is used to generate payload for edit SR project details for End
	 * Date Greater Than Start Date
	 * 
	 * @param responseData
	 * @param jsonpath
	 * @param userName
	 * @return String
	 * @throws Exception
	 */
	public String getEditSrProjectDetailsEndDateGreaterThanStartDatePayload(Map<Object, Object> responseData,
			JsonPath jsonpath, String userName) throws Exception {
		String startDate = Utilities.getFutureDate("dd-MM-yyyy", 30);
		String endDate = Utilities.getTomorrow("dd-MM-yyyy");
		mapOfEditSR = getDbPositiveValuePayload(responseData, jsonpath, userName);
		mapOfEditSR.put("startDate", startDate);
		mapOfEditSR.put("endDate", endDate);
		return getEditSrProjectDetailsPayload(responseData, mapOfEditSR);
	}

	/**
	 * This method is used to generate payload for edit SR project details with null
	 * location
	 * 
	 * @param responseData
	 * @param jsonpath
	 * @param userName
	 * @return String
	 * @throws Exception
	 */
	public String getEditSrProjectDetailsNullLocationPayload(Map<Object, Object> responseData, JsonPath jsonpath,
			String userName) throws Exception {
		String locationId = null;
		mapOfEditSR = getDbPositiveValuePayload(responseData, jsonpath, userName);
		mapOfEditSR.put("locationId", locationId);
		return getEditSrProjectDetailsPayload(responseData, mapOfEditSR);
	}

	/**
	 * This method is used to generate payload for edit SR assignment details
	 * 
	 * @param responseData
	 * @param jsonpath
	 * @param userName
	 * @return String
	 * @throws Exception
	 */
	public String getEditSrAssignmentDetailsPayload(Map<Object, Object> responseData, JsonPath jsonpath,
			String userName) throws Exception {
		mapOfEditSR = getDbPositiveValuePayload(responseData, jsonpath, userName);
		mapOfEditSR.remove("load");
		mapOfEditSR.remove("positionNeedDTOList");
		mapOfEditSR.remove("studioId");
		mapOfEditSR.put("isEditSR", true);
		mapOfEditSR.put("positionId", responseData.get("positionId"));
		mapOfEditSR.put("positionSeniorityName", "Sr Adv");
		mapOfEditSR.put("index", 0);
		mapOfEditSR.put("quantity", 1);
		mapOfEditSR.put("locationOffshore", true);
		mapOfEditSR.put("travelPeriodType", "DAYS");
		mapOfEditSR.put("travelPeriodLength", 2);
		mapOfEditSR.put("travelRequired", false);
		mapOfEditSR.put("sfdcFlow", false);
		mapOfEditSR.put("opening", true);
		mapOfEditSR.put("newSrFlow", true);
		mapOfEditSR.put("validateAll", true);
		json = Utilities.createJsonBodyFromMap(mapOfEditSR);
		return json;
	}
	
	/**
	 * This method generates payload to create new skill
	 * @param skillName
	 * @param userName
	 * @return String
	 * @throws SQLException
	 */
	public String addSkillPayload(String skillName,String userName) throws SQLException {
		Map<Object, Object> addSkillMap = new HashMap<Object, Object>();
		String globerId = editSRDBHelpers.getGloberId(userName);
		addSkillMap.put("area", "");
		addSkillMap.put("creator_id", globerId);
		addSkillMap.put("description", "Random new skill");
		addSkillMap.put("id", 0);
		addSkillMap.put("name", skillName);
		addSkillMap.put("prettyname", skillName);
		addSkillMap.put("parent_id", 0);
		addSkillMap.put("sekey", "");
		addSkillMap.put("uncleanable", true);
		json = Utilities.createJsonBodyFromMap(addSkillMap);
		return json;
	}
	
	/**
	 * This method will edit SR with newly added skill which will be blacklisted
	 * @param responseData
	 * @param jsonpath
	 * @param listOfAlberthaSkills
	 * @param userName
	 * @return String
	 * @throws Exception
	 */
	public String editSrProjectDetailsWithNewAddedSkillPayload(Map<Object, Object> responseData, JsonPath jsonpath,List<PositionNeedDTOListSrRevamp> listOfAlberthaWithNewSkills, String userName) throws Exception {
		mapOfEditSR = getDbPositiveValuePayload(responseData, jsonpath, userName);
		mapOfEditSR.remove("positionNeedDTOList");
		mapOfEditSR.put("positionNeedDTOList", listOfAlberthaWithNewSkills);
		return getEditSrProjectDetailsPayload(responseData, mapOfEditSR);
	}

}