package payloads.submodules.staffRequest.features.globalstaffrequest;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import database.GlowDBHelper;
import database.submodules.staffRequest.features.EditSRDBHelpers;
import payloads.submodules.staffRequest.StaffRequestPayloadHelper;
import utils.Utilities;

public class TimelinePayloadHelper extends StaffRequestPayloadHelper {

	static Gson gson;
	Map<Object, Object> mapPositionDetails = new HashMap<Object, Object>();
	GlowDBHelper glowDBHelper;
	EditSRDBHelpers editSrDb;
	
	public TimelinePayloadHelper() {
		glowDBHelper = new GlowDBHelper();
		editSrDb = new EditSRDBHelpers();
	}
	
	/**
	 * This method will create payload to suggest glober
	 * 
	 * @param globerId
	 * @param SrNumber
	 * @param type
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String suggestGlober(String globerId, String srNumber, String type)
			throws SQLException {
		mapPositionDetails.put("positionId", editSrDb.getPositionId(srNumber));
		mapPositionDetails.put("globerId", globerId);
		mapPositionDetails.put("globalId", glowDBHelper.getGlobalIdFromGloberId(globerId));
		mapPositionDetails.put("planType", type);
		mapPositionDetails.put("planStartDate", Utilities.getTomorrow("dd-MM-yyyy"));
		mapPositionDetails.put("comments", "");
		mapPositionDetails.put("suggestionType", "MANUAL");
		mapPositionDetails.put("srGridPage", true);
		mapPositionDetails.put("type", "Glober");
		
		String json = Utilities.convertJavaObjectToJson(mapPositionDetails);
		return json;
	}
	
	/**
	 * This method will create payload to dismiss glober
	 * 
	 * @param globerId
	 * @param srNumber
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String dismissGloberPayload(String globerId,String srNumber) throws SQLException {
		mapPositionDetails.put("positionId", editSrDb.getPositionId(srNumber));
		mapPositionDetails.put("globalId", glowDBHelper.getGlobalIdFromGloberId(globerId));
		String json = Utilities.convertJavaObjectToJson(mapPositionDetails);
		return json;
	}
}
