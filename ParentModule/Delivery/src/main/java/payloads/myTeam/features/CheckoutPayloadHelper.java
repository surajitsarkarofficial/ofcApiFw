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
 * @author imran.khan
 *
 */

public class CheckoutPayloadHelper extends MyTeamPayLoadHelper{
	
	/**
	 * It will read json file to read param names and store as a key and will read
	 * data which is received as a parameter as a value in map and create a JSON
	 * Object from that map
	 * 
	 * @param paramValues
	 * @return JSON Object having created payload
	 */
	public JSONObject createCheckoutPayload(List<Object> paramValues) {
		
		List<Integer> loop = Stream.iterate(0, n -> n + 1).limit(paramValues.size()).collect(Collectors.toList());
		List<String> paramNames = Arrays.asList("checkoutDate", "comment", "globerId", "notificationDate", "projectId",
				"retain", "submitterId","notificationDateInDate","checkoutDateInDate");
		
		Map<String, Object> payloadMap = loop.stream().collect(Collectors.toMap(paramNames::get, paramValues::get));
		
		JSONObject requestParams = new JSONObject(payloadMap);
		ExtentHelper.test.log(Status.INFO, "Request Payload for checkout : "+requestParams.toString());
		
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
	public JSONObject addSkillsCreateCheckoutPayload(List<Object> paramValues) {
		
		List<Integer> loop = Stream.iterate(0, n -> n + 1).limit(paramValues.size()).collect(Collectors.toList());
		List<String> paramNames = Arrays.asList("checkoutDate", "comment", "globerId", "notificationDate", "projectId",
				"retain", "submitterId");
		
		Map<String, Object> payloadMap = loop.stream().collect(Collectors.toMap(paramNames::get, paramValues::get));
		
		JSONObject requestParams = new JSONObject(payloadMap);
		ExtentHelper.test.log(Status.INFO, "Request Payload for adding skills in checkout with Replacement flow: "+requestParams.toString());

		return requestParams;
	}

}
