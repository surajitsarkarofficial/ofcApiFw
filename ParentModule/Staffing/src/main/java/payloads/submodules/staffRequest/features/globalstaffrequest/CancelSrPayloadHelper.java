package payloads.submodules.staffRequest.features.globalstaffrequest;

import java.util.HashMap;
import java.util.Map;

import payloads.submodules.staffRequest.StaffRequestPayloadHelper;
import utils.Utilities;

public class CancelSrPayloadHelper extends StaffRequestPayloadHelper {

	Map<Object, Object> mapPositionDetails = new HashMap<Object, Object>();
	
	
	/**
	 * This method will create payload to cancel Staff Request
	 * 
	 * @param positionId
	 * @return {@link String}
	 * @author shadab.waikar
	 */
	public String cancelSrPayload(String positionId) {
		int cancelReasonId = 8;
		
		mapPositionDetails.put("cancelReasonId", cancelReasonId);
		mapPositionDetails.put("comments", "Testing");
		mapPositionDetails.put("positionId", positionId);
		
		String json = Utilities.convertJavaObjectToJson(mapPositionDetails);
		return json;
	}
	
}
