package payloads.submodules.globers;

import java.util.HashMap;
import java.util.Map;

import payloads.submodules.StaffingPayloadHelper;
import utils.Utilities;

/**
 * @author shadab.waikar
 * */

public class TBDGloberPayloadHelper extends StaffingPayloadHelper{

	Map<Object, Object> mapOfComment = new HashMap<Object, Object>();
	
	/**
	 * This method will return payloadto search TBD glober via dynamic search
	 * @param positionName
	 * @param seniority
	 * @return {@link String}
	 */
	public String searchTbdGloberViaDynamicSearchPayload(String positionName,String seniority) {
	    String skills[] = new String[0];
		mapOfComment.put("location", null);
		mapOfComment.put("position", positionName);
		mapOfComment.put("secondaryLocation", null);
		mapOfComment.put("seniority", seniority);
		mapOfComment.put("skil_ids", skills);
		String json = Utilities.convertJavaObjectToJson(mapOfComment);
		return json;
	}
	
	/**
	 * This method will return payload to mark glober as TBD glober
	 * @param authorId
	 * @param globerId
	 * @return {@link String}
	 */
	public String markGloberAsTbdPayload(String authorId,String globerId) {
		mapOfComment.put("authorId", authorId);
		mapOfComment.put("globerId", globerId);
		mapOfComment.put("isActive", true);
		mapOfComment.put("statusType", "TBD");
		mapOfComment.put("statusTypeId", 4);
		String json = Utilities.convertJavaObjectToJson(mapOfComment);
		return json;
	}
}
