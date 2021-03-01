package payloads.submodules.staffRequest.features;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.asserts.SoftAssert;

import com.google.gson.Gson;

import database.submodules.staffRequest.features.EditSRDBHelpers;
import dto.submodules.staffRequest.features.PositionNeedDTOList;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import payloads.submodules.staffRequest.StaffRequestPayloadHelper;
import tests.GlowBaseTest;
import utils.Utilities;

public class EditSRPayloadHelper extends StaffRequestPayloadHelper {

	static Gson gson;
	Map<Object, Object> mapPositionDetails = new HashMap<Object, Object>();
	SoftAssert soft_assert;
	EditSRDBHelpers editSRDBHelpers;
	EditSRPayloadHelper editSRPayloadHelper;

	public EditSRPayloadHelper(String userName) throws Exception {
		new GlowBaseTest().createSession(userName);
		soft_assert = new SoftAssert();
		editSRDBHelpers = new EditSRDBHelpers();
	}

	/**
	 * This method will change Sr dates according to requirements
	 * 
	 * @param strDt
	 * @param endDt
	 * @return String
	 * @throws SQLException
	 */
	private ArrayList<String> checkSrDates(String strDt,String endDt) throws ParseException{
		ArrayList<String> srDates = new ArrayList<String>();
		SimpleDateFormat obj = new SimpleDateFormat("dd-MM-yyyy");
		String sTodayDate = Utilities.getTodayDate("dd-MM-yyyy");
		
		if (endDt.equalsIgnoreCase("N/A")) {
			String current_date = Utilities.getTodayDate("dd-MM-yyyy");
			endDt = Utilities.futureDtWithRespectToProvideDate(current_date, 1);
		}
		Date dEndDate = obj.parse(endDt);
		Date dStartDate = obj.parse(strDt);
		Date dTodayDate = obj.parse(sTodayDate);
		// SDate is past then endDate will work with today
		if (dEndDate.before(dStartDate)) { // SDate is today then endDate will work with today
			endDt = Utilities.getTodayDate("dd-MM-yyyy"); // SDate is future then endDate will work ????
		} else if (dStartDate.after(dTodayDate)) {
			if (dEndDate.before(dStartDate)) {
				endDt = Utilities.futureDtWithRespectToProvideDate(dStartDate.toString(), 1);
			}
		}
		if (dStartDate.before(dTodayDate))
			endDt = Utilities.getTodayDate("dd-MM-yyyy");
		if (dEndDate.before(dTodayDate))
			endDt = Utilities.getTodayDate("dd-MM-yyyy");
		
		srDates.add(strDt);
		srDates.add(endDt);
		return srDates;
	}
	
	/**
	 * This method will return editSr payload with wrong data
	 * 
	 * @param projectId
	 * @param SrNumber
	 * @return String
	 * @throws SQLException
	 */
	private void editSrPayloadHelperWithWrongData(String projectId, String srNumber) throws SQLException {
		mapPositionDetails.put("index", "0");
		mapPositionDetails.put("isEditSR", true);
		mapPositionDetails.put("positionId", editSRDBHelpers.getPositionId(srNumber));
		mapPositionDetails.put("sowId", editSRDBHelpers.getSowId(projectId));
	}

	/**
	 * This method will return editSr project details payload
	 * 
	 * @param projectId
	 * @param SrNumber
	 * @return String
	 * @throws SQLException
	 */
	public String editSRProjectDetailsPage(String projectId, String srNumber) throws SQLException {
		mapPositionDetails.put("clientId", editSRDBHelpers.getClientId(projectId));
		mapPositionDetails.put("projectId", projectId);
		editSrPayloadHelperWithWrongData(projectId, srNumber);
		return Utilities.convertJavaObjectToJson(mapPositionDetails);
	}

	/**
	 * This method will return editSr project details payload with null project Id
	 * 
	 * @param projectId
	 * @param SrNumber
	 * @return String
	 * @throws SQLException
	 */
	public String editSRProjectDetailsPageWithNullProjectId(String projectId, String srNumber) throws SQLException {
		mapPositionDetails.put("clientId", editSRDBHelpers.getClientId(projectId));
		mapPositionDetails.put("projectId", "");
		editSrPayloadHelperWithWrongData(projectId, srNumber);
		return Utilities.convertJavaObjectToJson(mapPositionDetails);
	}

	/**
	 * This method will return editSr project details payload with wrong client Id
	 * 
	 * @param projectId
	 * @param SrNumber
	 * @return String
	 * @throws SQLException
	 */
	public String editSRProjectDetailsPageWithWrongClientId(String projectId, String srNumber) throws SQLException {
		mapPositionDetails.put("clientId", "");
		mapPositionDetails.put("projectId", projectId);
		editSrPayloadHelperWithWrongData(projectId, srNumber);
		return Utilities.convertJavaObjectToJson(mapPositionDetails);
	}

	/**
	 * This method will return editSr payload with valid data
	 * 
	 * @param index
	 * @param projectId
	 * @param SrNumber
	 * @param strDt
	 * @param endDt
	 * @throws SQLException
	 */
	private void editSrPayloadHelperWithValidData(String index, String projectId, String srNumber, String strDt,
			String endDt) throws SQLException {
		mapPositionDetails.put("clientId", editSRDBHelpers.getClientId(projectId));
		mapPositionDetails.put("index", index);
		mapPositionDetails.put("isEditSR", true);
		mapPositionDetails.put("positionId", editSRDBHelpers.getPositionId(srNumber));
		mapPositionDetails.put("projectId", projectId);
		mapPositionDetails.put("sowId", editSRDBHelpers.getSowId(projectId));
		mapPositionDetails.put("endDate", endDt); 
		mapPositionDetails.put("startDate", strDt);
		mapPositionDetails.put("studioId", editSRDBHelpers.getStudioIDFromPosition(srNumber));
		mapPositionDetails.put("positionName", editSRDBHelpers.getSowId(projectId));
		mapPositionDetails.put("positionRoleId", editSRDBHelpers.getPositionRoleIdByPosition(srNumber));
		mapPositionDetails.put("positionSeniorityId", editSRDBHelpers.getSeniorityIdByPosition(srNumber));
	}

	/**
	 * This method will return editSr Location payload data
	 * 
	 * @param load
	 * @param srNumber
	 * @param locationId
	 * @param arr
	 * @param travelPeriodLength
	 * @throws SQLException
	 */
	private void editSrLocationPayloadHelper(String load, String srNumber, String locationId, Object[] arr,
			String travelPeriodLength) throws SQLException {
		mapPositionDetails.put("load", load);
		mapPositionDetails.put("locationId", locationId);
		mapPositionDetails.put("secondaryLocationId", arr);
		mapPositionDetails.put("locationOffshore", false);
		mapPositionDetails.put("travelPeriodType", "DAYS");
		mapPositionDetails.put("travelPeriodLength", travelPeriodLength);
	}

	/**
	 * This method will return editSr position payload data
	 * 
	 * @param projectId
	 * @param SrNumber
	 * @param strDt
	 * @param endDt
	 * @throws ParseException
	 * @throws SQLException
	 */
	public String editSRPositionDetails(String projectId, String srNumber, String strDt, String endDt)
			throws ParseException, SQLException {
		String index = "1";
		ArrayList<String> srDates = checkSrDates(strDt, endDt);
		strDt = srDates.get(0);
		endDt = srDates.get(1);
		editSrPayloadHelperWithValidData(index, projectId, srNumber, strDt, endDt);
		return Utilities.convertJavaObjectToJson(mapPositionDetails);
	}

	/**
	 * This method will return editSr position payload with wrong end date
	 * 
	 * @param projectId
	 * @param SrNumber
	 * @throws ParseException
	 * @throws SQLException
	 */
	public String editSRPositionDetailsWithWrongEndDateData1(String projectId, String srNumber)
			throws ParseException, SQLException {
		String sYesterdayDate = Utilities.getYesterday("dd-MM-yyyy");
		String index = "1";
		String today = Utilities.changeDateFormat(editSRDBHelpers.getStartDateFromPosition(srNumber));
		editSrPayloadHelperWithValidData(index, projectId, srNumber, today, sYesterdayDate);
		return Utilities.convertJavaObjectToJson(mapPositionDetails);
	}

	/**
	 * This method will return editSr position payload with wrong date type
	 * 
	 * @param projectId
	 * @param srNumber
	 * @param strDt
	 * @param endDt
	 * @throws ParseException
	 * @throws SQLException
	 */
	public String editSRPositionDetailsWithWrongEndDateData2(String projectId, String srNumber, String strDt,
			String endDt) throws ParseException, SQLException {
		String index = "1";
		ArrayList<String> srDates = checkSrDates(strDt, endDt);
		strDt = srDates.get(0);
		endDt = Utilities.futureDtWithRespectToProvideDate(strDt, -1);
		editSrPayloadHelperWithValidData(index, projectId, srNumber, strDt, endDt);
		return Utilities.convertJavaObjectToJson(mapPositionDetails);
	}

	/**
	 * This method will return editSr position payload with wrong dates
	 * 
	 * @param projectId
	 * @param srNumber
	 * @param strDt
	 * @param endDt
	 * @throws ParseException
	 * @throws SQLException
	 */
	public String editSRPositionDetailsWithWrongDateData1(String projectId, String srNumber, String strDt, String endDt)
			throws ParseException, SQLException {
		strDt = Utilities.futureDtWithRespectToProvideDate(Utilities.getTodayDate("dd-MM-yyyy"), 1);
		endDt = Utilities.getTodayDate("dd-MM-yyyy");
		String index = "1";
		editSrPayloadHelperWithValidData(index, projectId, srNumber, strDt, endDt);
		return Utilities.convertJavaObjectToJson(mapPositionDetails);
	}

	/**
	 * This method will return editSr location payload data
	 * 
	 * @param projectId
	 * @param srNumber
	 * @param strDt
	 * @param endDt
	 * @throws ParseException
	 * @throws SQLException
	 */
	public String editSRLocationsDetails(String projectId, String srNumber, String strDt, String endDt)
			throws ParseException, SQLException {
		Object[] arr = new Object[1];
		String index = "2";
		ArrayList<String> srDates = checkSrDates(strDt, endDt);
		strDt = srDates.get(0);
		endDt = srDates.get(1);
		String load = editSRDBHelpers.getLoadFromPosition(srNumber);
		String travelLength = "0";
		String location = editSRDBHelpers.getLocationFromPosition(srNumber);
		editSrPayloadHelperWithValidData(index, projectId, srNumber, strDt, endDt);
		editSrLocationPayloadHelper (load, srNumber, location, arr, travelLength);
		return Utilities.convertJavaObjectToJson(mapPositionDetails);
	}
	
	/**
	 * This method will return editSr location payload without primary location 
	 * 
	 * @param projectId
	 * @param srNumber
	 * @param strDt
	 * @param endDt
	 * @throws ParseException
	 * @throws SQLException
	 */
	public String editSRLocationsDetailsWithoutPrimarylocation(String projectId, String srNumber, String strDt,
			String endDt) throws ParseException, SQLException {
		Object[] arr = new Object[1];		
		String index = "2";
		ArrayList<String> srDates = checkSrDates(strDt, endDt);
		strDt = srDates.get(0);
		endDt = srDates.get(1);
		String load = editSRDBHelpers.getLoadFromPosition(srNumber);
		String travelLength = "0";
		editSrPayloadHelperWithValidData(index, projectId, srNumber, strDt, endDt);
		editSrLocationPayloadHelper (load, srNumber, "", arr, travelLength);
		return Utilities.convertJavaObjectToJson(mapPositionDetails);
	}

	/**
	 * This method will return editSr location payload except anywhere location
	 * 
	 * @param projectId
	 * @param srNumber
	 * @param strDt
	 * @param endDt
	 * @throws ParseException
	 * @throws SQLException
	 */
	public String editSRLocationsDetailsWithDataSet3(String projectId, String srNumber, String strDt, String endDt)
			throws ParseException, SQLException {
		Object[] arr = new Object[] { editSRDBHelpers.getLocationIdExceptAnywhereLocationFromPosition(srNumber) };
		String location = editSRDBHelpers.getLocationIdExceptAnywhereLocationFromPosition(srNumber);
		String index = "2";
		ArrayList<String> srDates = checkSrDates(strDt, endDt);
		strDt = srDates.get(0);
		endDt = srDates.get(1);
		String load = editSRDBHelpers.getLoadFromPosition(srNumber);
		String travelLength = "0";

		editSrPayloadHelperWithValidData(index, projectId, srNumber, strDt, endDt);
		editSrLocationPayloadHelper (load, srNumber, location, arr, travelLength);
		return Utilities.convertJavaObjectToJson(mapPositionDetails);
	}

	/**
	 * This method will return editSr location payload with anywhere location 
	 * 
	 * @param projectId
	 * @param srNumber
	 * @param strDt
	 * @param endDt
	 * @throws ParseException
	 * @throws SQLException
	 */
	public String editSRLocationsDetailsWithDataSet2(String projectId, String srNumber, String strDt, String endDt)
			throws ParseException, SQLException {
		String locationId = editSRDBHelpers.getLocationIdFromLocationName("Anywhere");
		Object[] arr = new Object[] { locationId };
		String index = "2";
		ArrayList<String> srDates = checkSrDates(strDt, endDt);
		strDt = srDates.get(0);
		endDt = srDates.get(1);
		String load = editSRDBHelpers.getLoadFromPosition(srNumber);
		String travelLength = "0";
		editSrPayloadHelperWithValidData(index, projectId, srNumber, strDt, endDt);
		editSrLocationPayloadHelper (load, srNumber, locationId, arr, travelLength);
		return Utilities.convertJavaObjectToJson(mapPositionDetails);
	}

	/**
	 * This method will return editSr location payload without load 
	 * 
	 * @param projectId
	 * @param srNumber
	 * @param strDt
	 * @param endDt
	 * @throws ParseException
	 * @throws SQLException
	 */
	public String editSRLocationsDetailsWithoutload(String projectId, String srNumber, String strDt, String endDt)
			throws ParseException, SQLException {
		Object[] arr = new Object[1];
		String location = editSRDBHelpers.getLocationFromPosition(srNumber);
		String index = "2";
		String load = "";
		String travelLength = "0";
		ArrayList<String> srDates = checkSrDates(strDt, endDt);
		strDt = srDates.get(0);
		endDt = srDates.get(1);
		editSrPayloadHelperWithValidData(index, projectId, srNumber, strDt, endDt);
		editSrLocationPayloadHelper (load, srNumber, location, arr, travelLength);
		return Utilities.convertJavaObjectToJson(mapPositionDetails);
	}

	/**
	 * This method will return editSr location payload with wrong travel length period
	 * 
	 * @param projectId
	 * @param srNumber
	 * @param strDt
	 * @param endDt
	 * @throws ParseException
	 * @throws SQLException
	 */
	public String editSRTravelPeriodLength(String projectId, String srNumber, String strDt, String endDt)
			throws ParseException, SQLException {
		Object[] arr = new Object[1];
		String location = editSRDBHelpers.getLocationFromPosition(srNumber);
		String index = "2";
		String travelLength = "-1";
		ArrayList<String> srDates = checkSrDates(strDt, endDt);
		strDt = srDates.get(0);
		endDt = srDates.get(1);
		String load = editSRDBHelpers.getLoadFromPosition(srNumber);	
		editSrPayloadHelperWithValidData(index, projectId, srNumber, strDt, endDt);
		editSrLocationPayloadHelper (load, srNumber, location, arr, travelLength);
		return Utilities.convertJavaObjectToJson(mapPositionDetails);
	}

	/**
	 * This method will return Sr skills
	 * 
	 * @param projectId
	 * @param srNumber
	 * @param skills
	 * @param strDt
	 * @param endDt
	 * @throws ParseException
	 * @throws SQLException
	 */
	public String editSRSkills(String projectId, String srNumber, List<?> skills, String strDt, String endDt)
			throws ParseException, SQLException {
		Object[] arr = new Object[1];
		String location = editSRDBHelpers.getLocationFromPosition(srNumber);
		String index = "3";
		ArrayList<String> srDates = checkSrDates(strDt, endDt);
		strDt = srDates.get(0);
		endDt = srDates.get(1);
		String load = editSRDBHelpers.getLoadFromPosition(srNumber);
		String travelLength = "0";
		editSrPayloadHelperWithValidData(index, projectId, srNumber, strDt, endDt);
		editSrLocationPayloadHelper (load, srNumber, location, arr, travelLength);
		mapPositionDetails.put("positionNeedDTOList", skills);
		mapPositionDetails.put("clientInterviewRequired", true);
		return Utilities.convertJavaObjectToJson(mapPositionDetails);
	}

	/**
	 * This method will get payload w.r.t provided location
	 * 
	 * @param response
	 * @param location
	 * @param sceniority
	 * @return {@link String}
	 * @throws SQLException
	 */
	public String getEditSrLocationPayload(Response response, String location, String sceniority) throws SQLException {
		String projectId = null;
		String sowId = null;
		String studioId = null;
		projectId = editSRDBHelpers.getProjectId();
		sowId = editSRDBHelpers.getSowId(projectId);
		studioId = editSRDBHelpers.getStudioId(projectId);
		List<PositionNeedDTOList> listOfAlberthaSkills = getGloberSkillsAccordingToPosition(response);

		mapPositionDetails.put("sendMeCopy", false);
		mapPositionDetails.put("index", "3");

		mapPositionDetails.put("positionName", "Java Developer");
		mapPositionDetails.put("selectedPositionName", "SR Creation for Java Developer");
		mapPositionDetails.put("positionRoleId", "1");
		mapPositionDetails.put("positionSeniorityId", "5");
		mapPositionDetails.put("travelPeriodType", "DAYS");
		mapPositionDetails.put("locationOffshore", true);
		mapPositionDetails.put("travelPeriodLength", "0");
		mapPositionDetails.put("load", 100);
		mapPositionDetails.put("locationId", editSRDBHelpers.getLocationIdFromLocationName(location));
		mapPositionDetails.put("sowId", sowId);
		mapPositionDetails.put("projectId", projectId);
		mapPositionDetails.put("clientId", editSRDBHelpers.getClientId(projectId));
		mapPositionDetails.put("positionType", "SHADOW");
		mapPositionDetails.put("clientInterviewRequired", false);
		mapPositionDetails.put("englishRequired", true);
		mapPositionDetails.put("opening", true);
		mapPositionDetails.put("replacement", false);
		mapPositionDetails.put("comments", "New SR for Test Automation");
		mapPositionDetails.put("startDate", Utilities.getTomorrow("dd-MM-yyyy"));
		mapPositionDetails.put("endDate", Utilities.getFutureDate("dd-MM-yyyy", 30));
		mapPositionDetails.put("positionTypeName", "Shadow");
		mapPositionDetails.put("studioId", studioId); //
		mapPositionDetails.put("projectStudioName", editSRDBHelpers.getProjectStudioName(studioId));
		mapPositionDetails.put("sfdcFlow", false);
		mapPositionDetails.put("quantity", "1");
		mapPositionDetails.put("positionSeniorityName", sceniority);
		mapPositionDetails.put("positionNeedDTOList", listOfAlberthaSkills);
		return Utilities.convertJavaObjectToJson(mapPositionDetails);
	}

	/**
	 * This method will get the skills for a position
	 * 
	 * @param response
	 * @return List<PositionNeedDTOList>
	 */
	public static List<PositionNeedDTOList> getGloberSkillsAccordingToPosition(Response response) {
		int skillsCount = 8;
		JsonPath jsonpath = response.jsonPath();
		PositionNeedDTOList positionDtoList;
		List<PositionNeedDTOList> listOfSkills = new ArrayList<PositionNeedDTOList>();
		for (int i = 0; i < skillsCount; i++) {
			int alberthaId = jsonpath.get("data[" + i + "].skill.id");
			String competencyId = null;
			String competencyName = jsonpath.get("data[" + i + "].skill.name");
			String importance = "2";
			String importanceName = "Required";
			positionDtoList = new PositionNeedDTOList(alberthaId, competencyId, competencyName, importance,
					importanceName);
			listOfSkills.add(positionDtoList);
		}
		return listOfSkills;

	}
	
	/**
	 * This method will get payload to editSr to validate handler team
	 * 
	 * @param editSrData
	 * @param jsonPathData
	 * @return {@link String}
	 * @throws SQLException
	 * @throws ParseException
	 */
	public String getEditSrPayloadToValidateHandler(Map<Object, Object> editSrData, Response jsonPathData) throws SQLException, ParseException {
		List<PositionNeedDTOList> listOfAlberthaSkills = getGloberSkillsAccordingToPosition(jsonPathData);
		ArrayList<String> list = null;
		String sStartDate = Utilities.changeDateFormat(editSRDBHelpers.getStartDateFromPosition(editSrData.get("srNumber").toString()));
		String sEndDate = Utilities.changeDateFormat(editSRDBHelpers.getEndDateFromPosition(editSrData.get("srNumber").toString()));
		ArrayList<String> srDates = checkSrDates(sStartDate, sEndDate);

		mapPositionDetails.put("index", "3");
		mapPositionDetails.put("isEditSR", true);
		mapPositionDetails.put("positionId", editSRDBHelpers.getPositionId(editSrData.get("srNumber").toString()));
		mapPositionDetails.put("positionName", editSrData.get("positionName").toString());
		mapPositionDetails.put("positionRoleId", editSrData.get("positionId").toString());
		mapPositionDetails.put("positionSeniorityId", editSRDBHelpers.getSeniorityIdByPosition(editSrData.get("srNumber").toString()));
		mapPositionDetails.put("travelPeriodType", "DAYS");
		mapPositionDetails.put("locationOffshore", false);
		mapPositionDetails.put("travelPeriodLength", "0");
		mapPositionDetails.put("load", editSRDBHelpers.getLoadFromPosition(editSrData.get("srNumber").toString()));
		mapPositionDetails.put("locationId", editSRDBHelpers.getLocationIdExceptProvidedLocation(editSrData.get("locationName").toString()));
		mapPositionDetails.put("secondaryLocationId", list);
		mapPositionDetails.put("sowId", editSRDBHelpers.getSowId(editSrData.get("projectId").toString()));
		mapPositionDetails.put("projectId", editSrData.get("projectId").toString());
		mapPositionDetails.put("clientId", editSRDBHelpers.getClientId(editSrData.get("projectId").toString()));
		mapPositionDetails.put("clientInterviewRequired", false);
		mapPositionDetails.put("comments", "New SR for " + editSrData.get("positionName").toString());
		mapPositionDetails.put("startDate", srDates.get(0));
		mapPositionDetails.put("endDate", srDates.get(1));
		mapPositionDetails.put("studioId", editSRDBHelpers.getStudioId(editSrData.get("projectId").toString()));
		mapPositionDetails.put("positionSeniorityName",
				editSRDBHelpers.getSeniorityNameById(editSRDBHelpers.getSeniorityIdByName("SSr")));
		mapPositionDetails.put("positionNeedDTOList", listOfAlberthaSkills);
		return Utilities.convertJavaObjectToJson(mapPositionDetails);
	}
}
