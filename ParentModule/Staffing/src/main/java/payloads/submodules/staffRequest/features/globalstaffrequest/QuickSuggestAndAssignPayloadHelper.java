package payloads.submodules.staffRequest.features.globalstaffrequest;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import database.GlowDBHelper;
import database.submodules.staffRequest.features.EditSRDBHelpers;
import database.submodules.staffRequest.features.globalstaffrequest.QuickSuggestAndAssignDBHelper;
import payloads.submodules.staffRequest.StaffRequestPayloadHelper;
import utils.Utilities;

public class QuickSuggestAndAssignPayloadHelper extends StaffRequestPayloadHelper {

	static Gson gson;
	Map<Object, Object> mapPositionDetails = new HashMap<Object, Object>();
	QuickSuggestAndAssignDBHelper suggestAndAssignDb;
	GlowDBHelper glowDBHelper;
	EditSRDBHelpers editSrDb;

	public QuickSuggestAndAssignPayloadHelper(String username) {
		suggestAndAssignDb = new QuickSuggestAndAssignDBHelper();
		glowDBHelper = new GlowDBHelper();
		editSrDb = new EditSRDBHelpers();
	}

	/**
	 * This method will create payload to suggest glober
	 * 
	 * @param globerId
	 * @param todayPlus30
	 * @param SrNumber
	 * @param type
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	private String suggestGloberPayloadHelper(String globerId, String todayPlus30, String srNumber, String type)
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
		mapPositionDetails.put("staffingReportType", 1);
		mapPositionDetails.put("validateCreateStaffPlan", false);

		String json = Utilities.convertJavaObjectToJson(mapPositionDetails);
		return json;
	}

	/**
	 * This method will create payload to suggest glober one way
	 * 
	 * @param todayPlus30
	 * @param SrNumber
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String suggestDetailPage(String todayPlus30, String SrNumber) throws SQLException {
		String globerId = suggestAndAssignDb.getGloberNothavingAnyPlanOneWay(todayPlus30);
		return suggestGloberPayloadHelper(globerId, todayPlus30, SrNumber, "High");
	}

	/**
	 * This method will create payload to suggest glober second way
	 * 
	 * @param SRStrtDt
	 * @param SrNumber
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String suggestDetailPageWithSecondWay(String srStrtDt, String srNumber) throws SQLException {
		String globerId = suggestAndAssignDb.getGloberNotHavingPlanSecondWay(srStrtDt);
		return suggestGloberPayloadHelper(globerId, srStrtDt, srNumber, "High");
	}

	/**
	 * This method will update createdPlan status
	 * 
	 * @param StaffPlanId
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public void updateCreatedPlanStatus(String staffPlanId) throws SQLException {
		suggestAndAssignDb.updateCreatedPlanStatus(staffPlanId);
	}

	/**
	 * This method will return payload to suggest glober.
	 * 
	 * @param globerId
	 * @param date
	 * @param SrNumber
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String suggestDetailPageWithValidData(String globerId, String date, String srNumber) throws SQLException {
		return suggestGloberPayloadHelper(globerId, date, srNumber, "High");
	}

	/**
	 * This method will return payload to suggest glober.
	 * 
	 * @param username
	 * @param date
	 * @param SrNumber
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String globerCreateNewPlan(String username, String date, String srNumber) throws SQLException {
		String user = "hazel.fernandes";
		String globerId = suggestAndAssignDb.getTalentPoolGloberOneWay(date);
		String planType = username.equalsIgnoreCase(user) ? "Low" : "High";
		return suggestGloberPayloadHelper(globerId, date, srNumber, planType);
	}

	/**
	 * This method will return payload to assign any glober
	 * 
	 * @param srNumber
	 * @param globerId
	 * @param type
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	private void assignAnyGloberPayloadHelper(String srNumber, String globerId, String type) throws SQLException {
		mapPositionDetails.put("positionId", editSrDb.getPositionId(srNumber));
		mapPositionDetails.put("globerId", globerId);
		mapPositionDetails.put("globalId", glowDBHelper.getGlobalIdFromGloberId(globerId));
		mapPositionDetails.put("planType", "High");
		mapPositionDetails.put("planStartDate", Utilities.getTomorrow("dd-MM-yyyy"));
		mapPositionDetails.put("comments", "");
		mapPositionDetails.put("suggestionType", "AUTO");
		mapPositionDetails.put("srGridPage", true);
		mapPositionDetails.put("type", type);
		mapPositionDetails.put("staffingReportType", 2);
	}

	/**
	 * This method will return payload to assign already assinged glober
	 * 
	 * @param globerId
	 * @param StrtDt
	 * @param EndDt
	 * @param todayDt
	 * @param SrNumber
	 * @return {@link String}
	 * @throws SQLException,ParseException
	 * @author shadab.waikar
	 */
	public String createAssignGloberPayload(String globerId, String strtDt, String endDt, String todayDt,
			String srNumber) throws SQLException, ParseException {
		assignAnyGloberPayloadHelper(srNumber, globerId, "Glober");
		mapPositionDetails.put("validateCreateStaffPlan", false);
		mapPositionDetails.put("assignmentStartDate", strtDt);
		mapPositionDetails.put("assignmentEndDate", endDt);
		mapPositionDetails.put("load", 100);
		gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(mapPositionDetails);
		return json;
	}

	/**
	 * This method will return payload to assign Soon To Be Globers
	 * 
	 * @param SrNumber
	 * @param STGType
	 * @param user
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String assignSTGs(String srNumber, String stgType, String user) throws SQLException {
		String globerIdWithStgsName[] = suggestAndAssignDb.getTalentPoolStgs(stgType);
		String globerId = globerIdWithStgsName[0];
		assignAnyGloberPayloadHelper(srNumber, globerId, stgType);
		mapPositionDetails.put("validateCreateStaffPlan", true);
		mapPositionDetails.put("candidateName", globerIdWithStgsName[1]);
		mapPositionDetails.put("assignmentStartDate", Utilities.getTomorrow("dd-MM-yyyy"));
		mapPositionDetails.put("assignmentEndDate", Utilities.getFutureDate("dd-MM-yyyy", 10));
		mapPositionDetails.put("effectiveHiringDate", Utilities.getTodayDate("dd-MM-yyyy"));
		mapPositionDetails.put("load", 100);
		if (!user.equals("hazel.fernandes"))
			mapPositionDetails.put("action", "hired");
		gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(mapPositionDetails);
		return json;

	}

	/**
	 * This method will return payload to assign role specific Soon To Be Globers
	 * 
	 * @param SrNumber
	 * @param STGType
	 * @param user
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String assignTypeSpecificSTGs(String srNumber, String stgType, String user) throws SQLException {
		String globerIdWithStgsName[] = suggestAndAssignDb.getTalentPoolStgs(stgType);
		String stgNameWithMail[] = suggestAndAssignDb.getSoonToBeGloberDetails(user);
		String globerId = globerIdWithStgsName[0];
		assignAnyGloberPayloadHelper(srNumber, globerId, stgType);

		mapPositionDetails.put("bookGloberFlow", true);
		mapPositionDetails.put("userCompleteName", stgNameWithMail[0] + " " + stgNameWithMail[1]);
		mapPositionDetails.put("userEmail", stgNameWithMail[2]);
		mapPositionDetails.put("quickSuggestAndAssignForSTG", true);
		gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(mapPositionDetails);
		return json;
	}

	/**
	 * This method will return payload to assign talent pool Globers
	 * 
	 * @param SrNumber
	 * @param dt
	 * @param user
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String assignTalentPoolGlober(String user, String dt, String srNumber) throws SQLException {
		String globerType = "Glober";
		String globerId = suggestAndAssignDb.getTalentPoolGloberOneWay(dt);
		assignAnyGloberPayloadHelper(srNumber, globerId, globerType);

		mapPositionDetails.put("assignmentStartDate", Utilities.getTomorrow("dd-MM-yyyy"));
		mapPositionDetails.put("assignmentEndDate", Utilities.getFutureDate("dd-MM-yyyy", 10));
		mapPositionDetails.put("load", 100);
		gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(mapPositionDetails);
		return json;
	}
}
