package payloads.myTeam.features;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONObject;

import com.aventstack.extentreports.Status;

import utils.ExtentHelper;

/**
 * @author poonam.kadam
 *
 */
public class SuggestAndAssignPayloadHelper {

	/**
	 * It will read json file to read param names and store as a key and will read
	 * data which is received as a parameter as a value in map and create a JSON
	 * Object from that map
	 * 
	 * @param paramValues
	 * @return JSON Object having created payload
	 */
	public JSONObject createSuggestAndAssignPayload(List<Object> paramValues) {

		List<Integer> loop = Stream.iterate(0, n -> n + 1).limit(paramValues.size()).collect(Collectors.toList());
		List<String> paramNames = Arrays.asList("type", "suggestionType", "srGridPage", "staffingReportType",
				"globalId", "positionId", "globerId", "planType", "planStartDate", "assignmentStartDate",
				"assignmentEndDate", "load", "comments");
		Map<String, Object> payloadMap = loop.stream().collect(Collectors.toMap(paramNames::get, paramValues::get));

		JSONObject requestParams = new JSONObject(payloadMap);
		ExtentHelper.test.log(Status.INFO,
				"Request Payload for quick suggest and assign : " + requestParams.toString());

		return requestParams;
	}
}