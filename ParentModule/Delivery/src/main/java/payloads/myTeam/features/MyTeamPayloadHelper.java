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

public class MyTeamPayloadHelper extends MyTeamPayLoadHelper {

	/**
	 * It will create Payload for Create New POD
	 * 
	 * @param paramValues	
	 * @return JSON Object having created payload
	 */
	public JSONObject createPODPayload(List<Object> paramValues) {

		List<Integer> loop = Stream.iterate(0, n -> n + 1).limit(paramValues.size()).collect(Collectors.toList());
		List<String> paramNames = Arrays.asList("podName", "podTypeId", "purpose", "projectId");

		Map<String, Object> payloadMap = loop.stream().collect(Collectors.toMap(paramNames::get, paramValues::get));

		JSONObject requestParams = new JSONObject(payloadMap);
		ExtentHelper.test.log(Status.INFO, "Request Payload for checkout : " + requestParams.toString());

		return requestParams;
	}
	
	/**
	 * It will create Payload for Update POD
	 * 
	 * @param paramValues
	 * @return JSON Object having created payload
	 */
	public JSONObject updatePODPayload(List<Object> paramValues) {

		List<Integer> loop = Stream.iterate(0, n -> n + 1).limit(paramValues.size()).collect(Collectors.toList());
		List<String> paramNames = Arrays.asList("podName", "podTypeId", "purpose", "projectId","podId");

		Map<String, Object> payloadMap = loop.stream().collect(Collectors.toMap(paramNames::get, paramValues::get));

		JSONObject requestParams = new JSONObject(payloadMap);
		ExtentHelper.test.log(Status.INFO, "Request Payload for checkout : " + requestParams.toString());

		return requestParams;
	}

	/***
	 * This will create Payload for Assign Role To Glober
	 * 
	 * @param paramValues
	 * @return JSONObject
	 */
	public JSONObject assignRoleToGloberPayload(List<Object> paramValues) {

		List<Integer> loop = Stream.iterate(0, n -> n + 1).limit(paramValues.size()).collect(Collectors.toList());
		List<String> paramNames = Arrays.asList("roleStartDate", "roleEndDate", "globerId", "projectId", "projectRole");

		Map<String, Object> payloadMap = loop.stream().collect(Collectors.toMap(paramNames::get, paramValues::get));

		JSONObject requestParams = new JSONObject(payloadMap);
		ExtentHelper.test.log(Status.INFO, "Request Payload for checkout : " + requestParams.toString());

		return requestParams;
	}

	/***
	 * This will create Payload for Manage Assignement
	 * @return JSONObject
	 */
	public JSONObject manageAssignmentPayload(List<Object> paramValues) {

		List<Integer> loop = Stream.iterate(0, n -> n + 1).limit(paramValues.size()).collect(Collectors.toList());
		List<String> paramNames = Arrays.asList("globerId", "projectId", "assignmentLoad");

		Map<String, Object> payloadMap = loop.stream().collect(Collectors.toMap(paramNames::get, paramValues::get));

		JSONObject requestParams = new JSONObject(payloadMap);
		ExtentHelper.test.log(Status.INFO, "Request Payload for checkout : " + requestParams.toString());

		return requestParams;
	}

	/***
	 * This will create Payload for Extend Assignment
	 * @param paramValues
	 * @return JSONObject
	 */
	public JSONObject extendAssignmentPayload(List<Object> paramValues) {

		List<Integer> loop = Stream.iterate(0, n -> n + 1).limit(paramValues.size()).collect(Collectors.toList());
		List<String> paramNames = Arrays.asList("projectId","globerId", "assignmentEndDate");
		Map<String, Object> payloadMap = loop.stream().collect(Collectors.toMap(paramNames::get, paramValues::get));

		JSONObject requestParams = new JSONObject(payloadMap);
		ExtentHelper.test.log(Status.INFO, "Request Payload for checkout : " + requestParams.toString());

		return requestParams;
	}
	
	
	/**
	 * This will create payload to manage POD
	 * 
	 * @param paramValues
	 * @return JSON Object having created payload
	 */
	public JSONObject managePODPayload(List<Object> paramValues) {

		List<Integer> loop = Stream.iterate(0, n -> n + 1).limit(paramValues.size()).collect(Collectors.toList());
		List<String> paramNames = Arrays.asList("projectId", "sourcePodId", "targetPodId","duplicatePod","loggedInUserId");

		Map<String, Object> updateDurationDetailsPayloadMap = loop.stream()
				.collect(Collectors.toMap(paramNames::get, paramValues::get));
		JSONObject requestParams = new JSONObject(updateDurationDetailsPayloadMap);
		ExtentHelper.test.log(Status.INFO, "Request Payload for edit assignment : " + requestParams.toString());
		return requestParams;
	}	
	
	/**
	 * This will create payload for ADD to POD
	 * 
	 * @param paramValues
	 * @return JSON Object having created payload
	 */
	public JSONObject addToPODPODPayload(List<Object> paramValues) {

		List<Integer> loop = Stream.iterate(0, n -> n + 1).limit(paramValues.size()).collect(Collectors.toList());
		List<String> paramNames = Arrays.asList("projectId", "sourcePodId", "targetPodId","duplicatePod");

		Map<String, Object> updateDurationDetailsPayloadMap = loop.stream()
				.collect(Collectors.toMap(paramNames::get, paramValues::get));
		JSONObject requestParams = new JSONObject(updateDurationDetailsPayloadMap);
		ExtentHelper.test.log(Status.INFO, "Request Payload for edit assignment : " + requestParams.toString());
		return requestParams;
	}	

}
