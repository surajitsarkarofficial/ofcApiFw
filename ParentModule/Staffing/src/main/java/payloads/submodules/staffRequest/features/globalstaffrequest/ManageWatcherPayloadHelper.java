package payloads.submodules.staffRequest.features.globalstaffrequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import payloads.submodules.staffRequest.StaffRequestPayloadHelper;
import utils.Utilities;

public class ManageWatcherPayloadHelper extends StaffRequestPayloadHelper{

	Map<Object, Object> mapPositionDetails = new HashMap<Object, Object>();
	
	public String manageWatcherPayload(String positionId,ArrayList<Integer> watcherIds) {
		mapPositionDetails.put("positionId", positionId);
		mapPositionDetails.put("watcherIdList",watcherIds);
		
		String json = Utilities.convertJavaObjectToJson(mapPositionDetails);
		return json;
	}
}
