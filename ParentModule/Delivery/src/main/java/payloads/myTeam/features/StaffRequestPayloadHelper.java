package payloads.myTeam.features;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONObject;

import com.aventstack.extentreports.Status;

import payloads.myTeam.MyTeamPayLoadHelper;
import utils.ExtentHelper;

/**
 * @author dnyaneshwar.waghmare
 *
 */

public class StaffRequestPayloadHelper extends MyTeamPayLoadHelper {

	/**
	 * It will read json file to read param names and store as a key and will read
	 * data which is received as a parameter as a value in map and create a JSON
	 * Object from that map
	 * 
	 * @param paramValues
	 * @return JSON Object having created payload
	 */
	public JSONObject createStaffRequestPayload(List<Object> paramValues) {

		List<Integer> loop = Stream.iterate(0, n -> n + 1).limit(paramValues.size()).collect(Collectors.toList());
		List<String> paramNames = Arrays.asList("projectId", "clientId", "positionRoleId", "positionSeniorityId",
				"sowId", "positionName", "clientInterviewRequired", "comments", "startDate", "endDate",
				"englishRequired", "studio", "studioId", "quantity", "replacement", "sendMeCopy", "positionType",
				"positionTypeName", "load", "locationOffshore", "opening", "locationId", "travelPeriodType","index");

		Map<String, Object> payloadMap = loop.stream().collect(Collectors.toMap(paramNames::get, paramValues::get));

		JSONObject requestParams = new JSONObject(payloadMap);
		ExtentHelper.test.log(Status.INFO, "Request Payload for Staff Request : " + requestParams.toString());

		return requestParams;
	}
	
	/**
	 * It will read json file to read param names and store as a key and will read
	 * data which is received as a parameter as a value in map and create a JSON
	 * Object from that map
	 * 
	 * @param paramValues
	 * @return JSON Object having created payload
	 */
	public JSONObject createStaffRequestPayloadInternalPositions(List<Object> paramValues) {

		List<Integer> loop = Stream.iterate(0, n -> n + 1).limit(paramValues.size()).collect(Collectors.toList());
		List<String> paramNames = Arrays.asList("projectId", "clientId", "positionRoleId", "positionSeniorityId",
				"sowId", "positionName", "clientInterviewRequired", "startDate", "endDate", "englishRequired",
				"quantity", "replacement", "sendMeCopy", "positionType", "positionTypeName", "load", "locationOffshore",
				"opening", "locationId", "travelPeriodType", "index", "selectedPositionName", "positionSeniorityName",
				"travelPeriodLength", "sfdcFlow", "newSrFlow", "validateAll", "sfdcOpeningId", "openingId", "opportunityId", "positionNeedDTOList");

		Map<String, Object> payloadMap = loop.stream().collect(Collectors.toMap(paramNames::get, paramValues::get));

		JSONObject requestParams = new JSONObject(payloadMap);
		ExtentHelper.test.log(Status.INFO, "Request Payload for Staff Request : " + requestParams.toString());

		return requestParams;
	}
	
	/**
	 * It will read json file to read param names and store as a key and will read
	 * data which is received as a parameter as a value in map and create a JSON
	 * Object from that map
	 * 
	 * @param paramValues
	 * @return JSON Object having created payload
	 */
	public JSONObject editStaffRequestPayload(List<Object> paramValues) {

		List<Integer> loop = Stream.iterate(0, n -> n + 1).limit(paramValues.size()).collect(Collectors.toList());
		List<String> paramNames = Arrays.asList("quantity", "positionRoleId", "selectedPositionName",
				"positionSeniorityId", "positionSeniorityName", "travelPeriodType", "locationOffshore",
				"travelPeriodLength", "load", "clientInterviewRequired", "locationId", "secondaryLocationId",
				"startDate", "endDate", "newSrFlow", "validateAll", "totalGlobersCount", "availableGlobersCount",
				"positionName", "clientId", "projectId", "positionId", "sowId", "isEditSR", "index", "studioId",
				"positionNeedDTOList");

		Map<String, Object> payloadMap = loop.stream().collect(Collectors.toMap(paramNames::get, paramValues::get));

		JSONObject requestParams = new JSONObject(payloadMap);
		ExtentHelper.test.log(Status.INFO, "Request Payload for editing Staff Request : " + requestParams.toString());

		return requestParams;
	}

}
