package payloads.submodules.openpositions;

import java.util.HashMap;
import java.util.Map;

import dto.submodules.openpositions.StaffingPlanDTO;
import payloads.submodules.StaffingPayloadHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * @author shadab.waikar
 */
public class OpenPositionsPayloadHelper extends StaffingPayloadHelper{

	Map<Object, Object> mapPositionDetails = new HashMap<Object, Object>();
	RestUtils restUtils;
	
	public OpenPositionsPayloadHelper(String userName) {
		restUtils = new RestUtils();
	}
	
	/**
	 * Get glober having applied to maximun opsen position/open position limit
	 * @param positionId
	 * @param globerId
	 * @param globalId
	 * @param score
	 * @return {@link String}
	 */

	public String openPositionPayload(String positionId,String globerId,String globalId,String score) {
		mapPositionDetails.put("comment", ""); 
		mapPositionDetails.put("globerMatchingPercentage",score); 
		mapPositionDetails.put("reasonId", "2");
		StaffingPlanDTO openPositionPlan = new StaffingPlanDTO(positionId,globerId,globalId,"true","Glober");
		mapPositionDetails.put("staffingPlanDto", openPositionPlan);
		String json = Utilities.convertJavaObjectToJson(mapPositionDetails);
		return json;
	}
	
}
