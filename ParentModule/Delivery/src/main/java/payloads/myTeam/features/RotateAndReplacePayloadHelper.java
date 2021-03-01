package payloads.myTeam.features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONObject;

import com.aventstack.extentreports.Status;

import payloads.myTeam.MyTeamPayLoadHelper;
import properties.myTeam.MyTeamProperties;
import utils.ExtentHelper;
import utils.JsonHelper;


/**
 * @author imran.khan
 *
 */

public class RotateAndReplacePayloadHelper extends MyTeamPayLoadHelper{
	
	/**
	 * It will read json file to read param names and store as a key and will read
	 * data which is received as a parameter as a value in map and create a JSON
	 * Object from that map
	 * 
	 * @param paramValues
	 * @return JSON Object having created payload
	 */
	public JSONObject createReplacePayload(List<Object> paramValues) {
		Map<String, Object> mapObject = JsonHelper.readJsonFields(MyTeamProperties.replacePayload);

		List<Integer> loop = Stream.iterate(0, n -> n + 1).limit(paramValues.size()).collect(Collectors.toList());

		List<String> paramNames = new ArrayList<String>(mapObject.keySet());
	
		Map<String, Object> payloadMap = loop.stream().collect(Collectors.toMap(paramNames::get, paramValues::get));
		
		JSONObject requestParams = new JSONObject(payloadMap);
		ExtentHelper.test.log(Status.INFO, "Request Payload: "+requestParams.toString());
		
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
	
	public JSONObject createRotatePayload(List<Object> paramValues) {
		Map<String, Object> mapObject = JsonHelper.readJsonFields(MyTeamProperties.rotatePayload);

		List<Integer> loop = Stream.iterate(0, n -> n + 1).limit(paramValues.size()).collect(Collectors.toList());

		List<String> paramNames = new ArrayList<String>(mapObject.keySet());
	
		Map<String, Object> payloadMap = loop.stream().collect(Collectors.toMap(paramNames::get, paramValues::get));
		
		JSONObject rotateAssignmentDetails = new JSONObject(payloadMap);
		JSONObject requestParams = new JSONObject();
		requestParams.put("action", "ROTATE_ASSIGNMENT");
		requestParams.put("rotateAssignmentDetails", rotateAssignmentDetails);
		
		ExtentHelper.test.log(Status.INFO, "Request Payload to rotate: "+requestParams.toString());
		
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
	public JSONObject createReplaceWithSRPayload(List<Object> paramValues) {
		
		List<Integer> loop = Stream.iterate(0, n -> n + 1).limit(paramValues.size()).collect(Collectors.toList());
		List<String> paramNames = Arrays.asList("endDate", "globerId", "isOpening", "positionId", "positionName",
				"projectId", "replacementReason", "startDate", "validateDates","positionNeedDTOList");
		
		Map<String, Object> payloadMap = loop.stream().collect(Collectors.toMap(paramNames::get, paramValues::get));
		
		JSONObject requestParams = new JSONObject(payloadMap);
		ExtentHelper.test.log(Status.INFO, "Request Payload for replace glober with SR : "+requestParams.toString());
		
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
	public JSONObject addSkillsForCheckoutWithReplacePayload(List<Object> paramValues) {

		List<Integer> loop = Stream.iterate(0, n -> n + 1).limit(paramValues.size()).collect(Collectors.toList());
		List<String> paramNames = Arrays.asList("positionName", "positionId", "globerId", "projectId", "startDate",
				"newAssignmentEndDate", "replacementReason", "isOpening", "validateDates", "positionNeedDTOList");

		Map<String, Object> payloadMap = loop.stream().collect(Collectors.toMap(paramNames::get, paramValues::get));

		JSONObject requestParams = new JSONObject(payloadMap);
		ExtentHelper.test.log(Status.INFO, "Request Payload for adding skills in Checkout with replace flow : "
				+ requestParams.toString());

		return requestParams;
	}

}
