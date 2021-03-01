package payloads.myTeam.features;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import com.aventstack.extentreports.Status;

import payloads.myTeam.MyTeamPayLoadHelper;
import utils.ExtentHelper;

/**
 * @author pooja.kakade
 *
 */

public class ConfirmShadowPayloadHelper extends MyTeamPayLoadHelper{
	
	/**
	 * @author pooja.kakade
	 * 
	 * It will read json file to read param names and store as a key and will read
	 * data which is received as a parameter as a value in map and create a JSON
	 * Object from that map
	 * 
	 * @param paramValues
	 * @return JSON Object having created payload
	 */
	
	public JSONObject createInnerPayLoad(List<Object> paramValues) {
		
		List<Integer> loop = Stream.iterate(0, n -> n + 1).limit(paramValues.size()).collect(Collectors.toList());
		List<String> paramNames = Arrays.asList("positionId", "openingId", "srNumber", "assignmentStartDate", "assignmentEndDate");
		
		Map<String, Object> payloadMap = loop.stream().collect(Collectors.toMap(paramNames::get, paramValues::get));
		
		JSONObject requestParams = new JSONObject(payloadMap);
		ExtentHelper.test.log(Status.INFO, "Request Inner payload for Confirm shadow : "+requestParams.toString());
		
		return requestParams;
	}	
	
	/**
	 * It will read parameter and return corresponding JSONArray
	 * 
	 * @param positionNeedDTOList
	 * @return JSONArray
	 */

	public JSONArray createReplicateShadowPayLoad(List<Object> positionNeedDtoList) {
		JSONArray positionNeedDtoListArray = new JSONArray(positionNeedDtoList);
		JSONObject jsonObject = null;
		StringBuilder positionNeedDtoListString = new StringBuilder("");

		// Remove/Append key value pairs as per required payload
		for (int i = 0; i < positionNeedDtoListArray.length(); i++) {
			jsonObject = positionNeedDtoListArray.getJSONObject(i);
			jsonObject.remove("meaning");
			jsonObject.remove("skillValue");
			jsonObject.remove("importance");
			jsonObject.put("evidenceValue", 2);
			jsonObject.put("importance", 2);
			jsonObject.put("importanceName", "Required");

			String tmpString = jsonObject.toString();
			tmpString = tmpString.replace("skillId", "competencyId");
			tmpString = tmpString.replace("skillName", "competencyName");

			if (i == (positionNeedDtoListArray.length() - 1))
				positionNeedDtoListString.append(tmpString);
			else {
				positionNeedDtoListString.append(tmpString);
				positionNeedDtoListString.append(","); // Append , after JSON Object
			}
		}
		positionNeedDtoListArray = new JSONArray("[" + positionNeedDtoListString + "]");
		return positionNeedDtoListArray;
	}
}