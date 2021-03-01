package payloads.submodules.globers;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import database.GlowDBHelper;
import database.submodules.staffRequest.features.EditSRDBHelpers;
import database.submodules.staffRequest.features.globalstaffrequest.QuickSuggestAndAssignDBHelper;
import payloads.submodules.StaffingPayloadHelper;
import utils.Utilities;

/**
 * 
 * @author shadab.waikar
 *
 */
public class ProposalApprovedPayloadHelper extends StaffingPayloadHelper{

	Map<Object, Object> mapPositionDetails = new HashMap<Object, Object>();
	QuickSuggestAndAssignDBHelper suggestAndAssignDb;
	EditSRDBHelpers editSrDb;
	GlowDBHelper glowDBHelper;
	
	public ProposalApprovedPayloadHelper() {
		suggestAndAssignDb = new QuickSuggestAndAssignDBHelper();
		editSrDb = new EditSRDBHelpers();
		glowDBHelper = new GlowDBHelper();
	}
	
	/**
	 * This method will make payload to create plan for proposal approved stg glober.
	 * @param srNumber
	 * @param globerType
	 * @return {@link String}
	 * @throws SQLException 
	 * @throws Exception 
	 */
	public String suggestSTGPayload(String srNumber,String globerType) throws SQLException {
		String globerIdWithStgsName[] = suggestAndAssignDb.getTalentPoolStgs(globerType);
		mapPositionDetails.put("positionId", editSrDb.getPositionId(srNumber));
		mapPositionDetails.put("globerId", globerIdWithStgsName[0]);
		mapPositionDetails.put("globalId", glowDBHelper.getGlobalIdFromGloberId(globerIdWithStgsName[0]));

		mapPositionDetails.put("planType", "High");
		mapPositionDetails.put("planStartDate", Utilities.getTomorrow("dd-MM-yyyy"));
		mapPositionDetails.put("comments", "");
		mapPositionDetails.put("suggestionType", "MANUAL");
		mapPositionDetails.put("srGridPage", true);
		mapPositionDetails.put("type", globerType);
		mapPositionDetails.put("staffingReportType", 1);
		mapPositionDetails.put("validateCreateStaffPlan", false);

		String json = Utilities.convertJavaObjectToJson(mapPositionDetails);
		return json;
	}
}
