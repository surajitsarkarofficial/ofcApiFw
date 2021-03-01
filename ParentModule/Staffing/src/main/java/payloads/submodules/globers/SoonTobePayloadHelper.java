package payloads.submodules.globers;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import database.ClientDBHelper;
import database.LocationDBHelper;
import database.ProjectDBHelper;
import database.SowDBHelper;
import database.StudioDBHelper;
import database.submodules.globers.features.SoonTobeGloberDBHelper;
import dto.submodules.staffRequest.features.PositionNeedDTOList;
import io.restassured.response.Response;
import utils.Utilities;

public class SoonTobePayloadHelper implements SowDBHelper, ProjectDBHelper, StudioDBHelper, LocationDBHelper, ClientDBHelper {

	private static Gson gson;
	SoonTobeGloberDBHelper soonTobeGloberDBHelper = new SoonTobeGloberDBHelper();
	Map<Object, Object> payLoadMap = new HashMap<Object, Object>();
	Map<Object, Object> mapOfDbData = new HashMap<Object, Object>();

	/**
	 * This method will return payload of adding new comment for STG
	 * 
	 * @param role
	 * @param payLoadStgType
	 * @param soonToBeGloberId
	 * @return String
	 * @throws SQLException
	 */
	public String addCommentJson(String role, String payLoadStgType, String soonToBeGloberId) throws SQLException {
		String commentPost = "Adding New Comment For " + payLoadStgType + " Soon To Be Glober";
		Map<Object, Object> mapOfComment = new HashMap<Object, Object>();
		String globalId = soonTobeGloberDBHelper.getGlobalIdForAddingComment(payLoadStgType);
		mapOfComment.put("globerId", soonTobeGloberDBHelper.getGloberIdFromGlobalId(globalId));
		mapOfComment.put("authorRole", role);
		mapOfComment.put("comments", commentPost);
		mapOfComment.put("type", payLoadStgType);
		mapOfComment.put("globalId", globalId);
		gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(mapOfComment);
		return json;
	}

	/**
	 * This method will return payload for position
	 * 
	 * @param mapOfDbData
	 * @return String
	 * @throws SQLException
	 */
	public String getPositionPayloadSR(Map<Object, Object> mapOfDbData) throws SQLException {
		payLoadMap.put("sendMeCopy", false);
		payLoadMap.put("index", "3");

		payLoadMap.put("positionName", "Java Developer");
		payLoadMap.put("selectedPositionName", "SR Creation for Java Developer");
		payLoadMap.put("positionRoleId", "1");
		payLoadMap.put("positionSeniorityId", "5");
		payLoadMap.put("travelPeriodType", "DAYS");
		payLoadMap.put("locationOffshore", true);

		payLoadMap.put("travelPeriodLength", "0");
		payLoadMap.put("load", 100);
		payLoadMap.put("locationId", mapOfDbData.get("locationId"));

		payLoadMap.put("sowId", mapOfDbData.get("sowId"));
		payLoadMap.put("projectId", mapOfDbData.get("projectId"));
		payLoadMap.put("clientId", mapOfDbData.get("clientId"));
		payLoadMap.put("positionType", "SHADOW");
		payLoadMap.put("clientInterviewRequired", false);
		payLoadMap.put("englishRequired", true);
		payLoadMap.put("opening", true);
		payLoadMap.put("replacement", false);
		payLoadMap.put("comments", "New SR for Test Automation");
		payLoadMap.put("startDate", Utilities.getTomorrow("dd-MM-yyyy")); // .getTomorrow("dd-MM-yyyy")
		payLoadMap.put("endDate", Utilities.getFutureDate("dd-MM-yyyy", 30));
		payLoadMap.put("positionTypeName", "Shadow");
		payLoadMap.put("studioId", mapOfDbData.get("studioId"));
		payLoadMap.put("projectStudioName", mapOfDbData.get("projectStudioName"));
		payLoadMap.put("sfdcFlow", false);
		payLoadMap.put("quantity", "1");
		payLoadMap.put("positionSeniorityName", mapOfDbData.get("positionSeniorityName"));
		payLoadMap.put("positionNeedDTOList", mapOfDbData.get("positionNeedDTOList"));
		gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(payLoadMap);
		return json;
	}

	/**
	 * This method will return payload for position from Db
	 * 
	 * @param jsonpath
	 * @return Map<Object, Object>
	 * @throws SQLException
	 */
	public Map<Object, Object> getPositionDbPayloadSR(Response jsonpath) throws SQLException {
		String projectId = getProjectId();
		String sowId = getSowId(projectId);
		String studioId = getStudioId(projectId);

		List<PositionNeedDTOList> listOfAlberthaSkills = soonTobeGloberDBHelper.getPositionDTOList(jsonpath);

		mapOfDbData.put("locationId", getLocationId());

		mapOfDbData.put("sowId", sowId);
		mapOfDbData.put("projectId", projectId);
		mapOfDbData.put("clientId", getClientId(projectId));
		mapOfDbData.put("projectStudioName", getProjectStudioName(studioId));
		mapOfDbData.put("positionSeniorityName", soonTobeGloberDBHelper.getPositionSeniority());
		mapOfDbData.put("positionNeedDTOList", listOfAlberthaSkills);

		return mapOfDbData;
	}

	/**
	 * This method will return payload for position with wrong SR start date
	 * 
	 * @param mapOfDbData
	 * @return Map<Object, Object>
	 * @throws SQLException
	 */
	public Map<Object, Object> getPositionPayloadSRWithWrongSRStartDate(Map<Object, Object> mapOfDbData)
			throws SQLException {
		mapOfDbData.put("startDate", Utilities.getDateInPast("dd-MM-yyyy", 3)); // .getTomorrow("dd-MM-yyyy")
		return mapOfDbData;
	}

	/**
	 * This method will return payload for position with invalid position type
	 * 
	 * @param mapOfDbData
	 * @return Map<Object, Object>
	 * @throws SQLException
	 */
	public Map<Object, Object> getPositionPayloadSRWithInvalidPositionType(Map<Object, Object> mapOfDbData)
			throws SQLException {
		mapOfDbData.put("positionType", "");
		return mapOfDbData;
	}

	/**
	 * This method will return payload for assign STG
	 * 
	 * @param globerType
	 * @param positionId
	 * @return String
	 * @throws SQLException
	 * @throws ParseException
	 */
	public String assignStg(String globerType, String positionId) throws SQLException, ParseException {
		Map<Object, Object> payLoadMap = new HashMap<Object, Object>();
		String globalId = soonTobeGloberDBHelper.getGlobalId(globerType);
		String globerId = soonTobeGloberDBHelper.getGloberIdFromGlobalId(globalId);
		String assignmentStrDt = soonTobeGloberDBHelper.assignmntStrtDt(positionId);
		String assignEndDt = soonTobeGloberDBHelper.assignmentEndDt(positionId);
		payLoadMap.clear();
		payLoadMap.put("action", "hired");
		payLoadMap.put("assignmentEndDate", Utilities.changeDateFormat(assignEndDt));
		payLoadMap.put("assignmentStartDate", Utilities.changeDateFormat(assignmentStrDt));
		payLoadMap.put("candidateName", soonTobeGloberDBHelper.getStgName(globerId));
		payLoadMap.put("comments", null);
		payLoadMap.put("effectiveHiringDate", Utilities.futureDtWithRespectToProvideDate(assignmentStrDt, -2));
		payLoadMap.put("globerId", globerId);
		payLoadMap.put("globerType", globerType);
		payLoadMap.put("load", 100);
		payLoadMap.put("positionId", positionId);

		gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(payLoadMap);

		return json;
	}

	/**
	 * This method will return payload for STG new suggestion via PM
	 * 
	 * @param mapOfDbData
	 * @param planType
	 * @return String
	 * @throws SQLException
	 */
	public String newSuggestViaPM(Map<Object, Object> mapOfDbData, String planType) throws SQLException {
		payLoadMap.put("globalId", mapOfDbData.get("globalId"));
		payLoadMap.put("globerId", mapOfDbData.get("globerId"));
		payLoadMap.put("planType", planType);
		payLoadMap.put("planStartDate", mapOfDbData.get("planStartDate"));
		payLoadMap.put("suggestionType", "MANUAL");
		payLoadMap.put("comments", "SUGGEST GLOBER");
		payLoadMap.put("type", mapOfDbData.get("type"));
		payLoadMap.put("staffRequestId", mapOfDbData.get("staffRequestId"));

		gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(payLoadMap);

		return json;
	}

	/**
	 * This method will return Db payload for STG new suggestion via PM
	 * 
	 * @param globerType
	 * @return Map<Object, Object>
	 * @throws SQLException
	 */
	public Map<Object, Object> newSuggestViaPMDbPositivePayload(String globerType) throws SQLException {
		String staffingDate = Utilities.getFutureDate("dd-MM-yyyy", 2);
		String futureDate = Utilities.getFutureDate("yyyy-MM-dd", 30);
		String srNumber = soonTobeGloberDBHelper.getSrNumber(futureDate);
		String globalId = soonTobeGloberDBHelper.getGlobalId(globerType);
		String globerId = soonTobeGloberDBHelper.getGloberIdFromGlobalId(globalId);

		mapOfDbData.put("globalId", globalId);
		mapOfDbData.put("globerId", globerId);
		mapOfDbData.put("planStartDate", staffingDate);
		mapOfDbData.put("type", globerType);
		mapOfDbData.put("staffRequestId", srNumber);
		return mapOfDbData;
	}

	/**
	 * This method will return payload for STG new suggestion via PM with same SR
	 * 
	 * @param mapOfDbData
	 * @param srNumber
	 * @return Map<Object, Object>
	 * @throws SQLException
	 */
	public Map<Object, Object> newSuggestionWithSameSr(Map<Object, Object> mapOfDbData, String srNumber)
			throws SQLException {
		mapOfDbData.put("staffRequestId", srNumber);
		return mapOfDbData;
	}

	/**
	 * This method will return payload for STG new suggestion via PM with wrong SR
	 * id
	 *
	 * @param mapOfDbData
	 * @return Map<Object, Object>
	 * @throws SQLException
	 */
	public Map<Object, Object> newSuggestViaPMwithWrongSrId(Map<Object, Object> mapOfDbData) throws SQLException {
		mapOfDbData.put("staffRequestId", "1010");
		return mapOfDbData;
	}

	/**
	 * This method will return payload for STG new suggestion via PM with null SR id
	 * 
	 * @param mapOfDbData
	 * @return Map<Object, Object>
	 * @throws SQLException
	 */
	public Map<Object, Object> newSuggestViaPMWithNullSrId(Map<Object, Object> mapOfDbData) throws SQLException {
		mapOfDbData.put("staffRequestId", "");
		return mapOfDbData;
	}

	/**
	 * This method will return payload for STG new suggestion via PM with invalid
	 * plan start id
	 * 
	 * @param mapOfDbData
	 * @return Map<Object, Object>
	 * @throws SQLException
	 */
	public Map<Object, Object> newSuggestViaPMWithInvalidPlanStartDate(Map<Object, Object> mapOfDbData)
			throws SQLException {
		String staffingDate = Utilities.getFutureDate("dd-MM-yyyy", 5);
		mapOfDbData.put("planStartDate", staffingDate);
		mapOfDbData.put("staffRequestId", "1010");
		return mapOfDbData;
	}

	/**
	 * This method will return payload for post change handler
	 * 
	 * @param globerType
	 * @return String
	 * @throws SQLException
	 */
	public String postChangeHandler(String globerType) throws SQLException {
		Map<Object, Object> payLoadMap = new HashMap<Object, Object>();

		String globalId = soonTobeGloberDBHelper.getGlobalId(globerType);
		String globerId = soonTobeGloberDBHelper.getGloberIdFromGlobalId(globalId);

		payLoadMap.put("globerId", globerId);
		payLoadMap.put("globerType", globerType);
		payLoadMap.put("handlerId", soonTobeGloberDBHelper.getHandlerId(globerId));

		gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(payLoadMap);

		return json;
	}

}
