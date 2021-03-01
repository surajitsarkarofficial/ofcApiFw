package tests.testhelpers.submodules.staffRequest.features.globalstaffrequest;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import database.GlowDBHelper;
import database.StaffingDBHelper;
import database.submodules.staffRequest.features.globalstaffrequest.QuickSuggestAndAssignDBHelper;
import endpoints.submodules.staffRequest.features.GlobalStaffRequestAPIEndPoints;
import endpoints.submodules.staffRequest.features.SuggestGloberEndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.staffRequest.features.globalstaffrequest.QuickSuggestAndAssignPayloadHelper;
import tests.testcases.StaffingBaseTest;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import tests.testhelpers.StaffingTestHelper;
import tests.testhelpers.submodules.staffRequest.StaffRequestTestHelper;
import utils.RestUtils;
import utils.Utilities;

public class QuickSuggestAndAssignTestHelper extends StaffRequestTestHelper {

	RestUtils utils;
	QuickSuggestAndAssignPayloadHelper suggestAndAssignPayload;
	QuickSuggestAndAssignDBHelper suggestAndAssignDb;
	GlowDBHelper glowDBHelper;
	StaffingDBHelper staffingDb;

	// StaffRequest details
	public static String srNumber = null;
	public static Date srStartDate = null;
	public static Date todayPlus30Dt = null;
	public static Date todaysDt = null;
	public static String todayPlus30DtStrFormat = null;
	public static String srStartDateStrFormat = null;

	private String json = null;
	private int plans = 0;
	int i = 0;

	public QuickSuggestAndAssignTestHelper(String username) throws Exception {
		super(username);
		utils = new RestUtils();
		suggestAndAssignPayload = new QuickSuggestAndAssignPayloadHelper(username);
		suggestAndAssignDb = new QuickSuggestAndAssignDBHelper();
		glowDBHelper = new GlowDBHelper();
		staffingDb = new StaffingDBHelper();
	}

	/**
	 * This method will get dates of SR
	 * @param response
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public void getSrDateDetails(Response response) throws Exception {
		int i = Utilities.getRandomDay();
		srNumber = utils.getValueFromResponse(response, "details.positionDTOList[" + i + "].srNumber").toString();
		String srStartDt = utils.getValueFromResponse(response, "details.positionDTOList[" + i + "].startDate")
				.toString();
		SimpleDateFormat obj1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat obj2 = new SimpleDateFormat("yyyy-MM-dd");
		Date srStartDateForm = null;
		Date todayPlus30DtForm = null;

		String todayPlus30Date = "";
		todayPlus30Date = Utilities.futureDtWithRespectToProvideDate(Utilities.getTodayDate("dd-MM-yyyy"), 30);

		srStartDate = obj1.parse(srStartDt);
		todayPlus30Dt = obj1.parse(todayPlus30Date);

		String strStartDt = obj2.format(srStartDate); // Date to String as per format
		srStartDateForm = obj2.parse(strStartDt); // convert to updated dt in Date format
		srStartDateStrFormat = obj2.format(srStartDateForm); // convert to updated dt in String format

		String strTodayPlus30 = obj2.format(todayPlus30Dt);
		todayPlus30DtForm = obj2.parse(strTodayPlus30);
		todayPlus30DtStrFormat = obj2.format(todayPlus30DtForm);
	}

	/**
	 * This method will check if plans per Sr are more than 3
	 * 
	 * @param response
	 * @param username
	 * @param globerType
	 * @return {@link Integer}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public Integer checkIfPlansPerSrMoreThanThree(Response response, String username, String globerType)
			throws Exception {
		do {
			if (i != 0) {
				response = new StaffingTestHelper().getSRDetailsFromGrid(username, globerType);
			}
			getSrDateDetails(response);
			plans = staffingDb.getNoOfPlansPerSr(srNumber);
			i++;
		} while (plans >= 3);
		return plans;
	}

	/**
	 * This method will suggest glober with multiple roles
	 * 
	 * @param response
	 * @param name
	 * @param globerType
	 * @return {@link Response}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public Response getSuggestGloberWithSrDates(Response response, String name, String globerType) throws Exception {
		getSrDateDetails(response);
		checkIfPlansPerSrMoreThanThree(response, name, globerType);

		if (srStartDate.before(todayPlus30Dt)) { // SR date is less than today+30
			json = suggestAndAssignPayload.suggestDetailPage(todayPlus30DtStrFormat, srNumber);
		} else {
			json = suggestAndAssignPayload.suggestDetailPageWithSecondWay(srStartDateStrFormat, srNumber);
		}
		String requestUrl = StaffingBaseTest.baseUrl + SuggestGloberEndPoints.suggestGloberUrl;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(json);
		response = utils.executePOST(requestSpecification, requestUrl);
		return response;
	}

	/**
	 * This method will update createdPlan status
	 * 
	 * @param staffPlanId
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public void updatePlanStatus(String staffPlanId) throws SQLException {
		suggestAndAssignPayload.updateCreatedPlanStatus(staffPlanId);
	}

	/**
	 * This method will suggest glober again on same SR
	 * 
	 * @param response
	 * @param username
	 * @param globerType
	 * @return {@link Response}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public Response suggestGloberAgainOnSameSr(Response response, String username, String globerType) throws Exception {
		getSrDateDetails(response);
		checkIfPlansPerSrMoreThanThree(response, username, globerType);

		if (srStartDate.before(todayPlus30Dt)) { // SR date is less than today+30
			String globerId = suggestAndAssignDb.getTalentPoolGloberOneWay(todayPlus30DtStrFormat);
			json = suggestAndAssignPayload.suggestDetailPageWithValidData(globerId, todayPlus30DtStrFormat, srNumber);
		} else {
			String globerId = suggestAndAssignDb.getTalentPoolGloberOneWay(srStartDateStrFormat);
			json = suggestAndAssignPayload.suggestDetailPageWithValidData(globerId, srStartDateStrFormat, srNumber);
		}

		// 1st time suggest glober on Sr
		String requestUrl = StaffingBaseTest.baseUrl + SuggestGloberEndPoints.suggestGloberUrl;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(json);
		Response firstresponse = utils.executePOST(requestSpecification, requestUrl);

		// 2nd time suggest glober again on same Sr
		response = utils.executePOST(requestSpecification, requestUrl);
		updatePlanStatus(utils.getValueFromResponse(firstresponse, "details[0].staffPlanId").toString());
		return response;
	}

	/**
	 * This method will suggest high plan glober
	 * 
	 * @param response
	 * @param username
	 * @param globerType
	 * @return {@link Response}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public Response suggestGloberWithHighPlan(Response response, String username, String globerType) throws Exception {
		getSrDateDetails(response);
		checkIfPlansPerSrMoreThanThree(response, username, globerType);
		String globerId = suggestAndAssignDb.getTalentPoolGloberHavingHighPlan(todayPlus30DtStrFormat);

		json = suggestAndAssignPayload.suggestDetailPageWithValidData(globerId, todayPlus30DtStrFormat, srNumber);
		String requestUrl = StaffingBaseTest.baseUrl + SuggestGloberEndPoints.suggestGloberUrl;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(json);
		response = utils.executePOST(requestSpecification, requestUrl);
		return response;
	}

	/**
	 * This method will create plan by direct suggest glober
	 * 
	 * @param response
	 * @param username
	 * @param globerType
	 * @return {@link Response}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public Response createPlanByDirectSuggestGlober(Response response, String username, String globerType)
			throws Exception {
		getSrDateDetails(response);
		checkIfPlansPerSrMoreThanThree(response, username, globerType);

		if (srStartDate.before(todayPlus30Dt)) { // SR date is less than today+30
			json = suggestAndAssignPayload.globerCreateNewPlan(username, todayPlus30DtStrFormat, srNumber);
		} else {
			json = suggestAndAssignPayload.globerCreateNewPlan(username, srStartDateStrFormat, srNumber);
		}

		String requestUrl = StaffingBaseTest.baseUrl + SuggestGloberEndPoints.suggestGloberUrl;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(json);
		response = utils.executePOST(requestSpecification, requestUrl);
		return response;
	}

	/**
	 * This method will get assign glober payload with EndDt
	 * 
	 * @param response
	 * @param username
	 * @param globerType
	 * @return {@link Response}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public Response getPayloadWithWrongSrEndDt(Response response, String username, String globerType) throws Exception {
		getSrDateDetails(response);
		String globerId = staffingDb.getGloberIdHavingOnGoingAssignment(Utilities.getTodayDate("yyyy-MM-dd"));

		String strtDt = Utilities.getFutureDate("dd-MM-yyyy", 10);
		String endDt = Utilities.getTodayDate("dd-MM-yyyy");

		json = suggestAndAssignPayload.createAssignGloberPayload(globerId, strtDt, endDt,
				Utilities.getTodayDate("dd-MM-yyyy"), srNumber);
		String requestUrl = StaffingBaseTest.baseUrl + GlobalStaffRequestAPIEndPoints.suggestAssignmnet;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(json);
		response = utils.executePOST(requestSpecification, requestUrl);
		return response;
	}

	/**
	 * This method will assign glober on onGoing SR
	 * 
	 * @param response
	 * @param username
	 * @param globerType
	 * @return {@link Response}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public Response assignGloberOnOnGoingAssignment(Response response, String username, String globerType)
			throws Exception {
		String strtDt = null, endDt = null;
		getSrDateDetails(response);
		checkIfPlansPerSrMoreThanThree(response, username, globerType);

		String globerId = staffingDb.getGloberIdHavingOnGoingAssignment(Utilities.getTodayDate("yyyy-MM-dd"));
		String SrDates[] = staffingDb.getSrStartDtEndDtUsingGlobalId(glowDBHelper.getGlobalIdFromGloberId(globerId));
		strtDt = Utilities.futureDtWithRespectToProvideDate(SrDates[1], 2);
		endDt = Utilities.futureDtWithRespectToProvideDate(SrDates[1], 31);

		json = suggestAndAssignPayload.createAssignGloberPayload(globerId, strtDt, endDt,
				Utilities.getTodayDate("dd-MM-yyyy"), srNumber);
		String requestUrl = StaffingBaseTest.baseUrl + GlobalStaffRequestAPIEndPoints.suggestAssignmnet;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(json);
		response = utils.executePOST(requestSpecification, requestUrl);
		return response;
	}

	/**
	 * This method will get assigned glober to assign to new Sr
	 * 
	 * @param response
	 * @param username
	 * @param globerType
	 * @return {@link Response}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public Response getAssignedGloberToAssignToSr(Response response, String username, String globerType)
			throws Exception {
		String strtDt = null, endDt = null;
		getSrDateDetails(response);
		checkIfPlansPerSrMoreThanThree(response, username, globerType);

		String globerId = staffingDb.getGloberIdHavingOnGoingAssignment(Utilities.getTodayDate("yyyy-MM-dd"));
		String SrDates[] = staffingDb.getSrStartDtEndDtUsingGlobalId(glowDBHelper.getGlobalIdFromGloberId(globerId));
		strtDt = Utilities.changeDateFormat(SrDates[0]);
		endDt = Utilities.changeDateFormat(SrDates[1]);

		json = suggestAndAssignPayload.createAssignGloberPayload(globerId, strtDt, endDt,
				Utilities.getTodayDate("dd-MM-yyyy"), srNumber);
		String requestUrl = StaffingBaseTest.baseUrl + GlobalStaffRequestAPIEndPoints.suggestAssignmnet;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(json);
		response = utils.executePOST(requestSpecification, requestUrl);
		return response;
	}

	/**
	 * This method will assign glober with past date as assignment start date
	 * 
	 * @param response
	 * @param username
	 * @param globerType
	 * @return {@link Response}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public Response assignedGloberWithPastDtAsStrtDt(Response response, String username, String globerType)
			throws Exception {
		String strtDt = null, endDt = null;
		getSrDateDetails(response);
		checkIfPlansPerSrMoreThanThree(response, username, globerType);

		String globerId = staffingDb.getGloberIdHavingOnBench(Utilities.getTodayDate("yyyy-MM-dd"));
		strtDt = Utilities.getDateInPast("dd-MM-yyyy", 3);
		endDt = Utilities.getFutureDate("dd-MM-yyyy", 31);

		json = suggestAndAssignPayload.createAssignGloberPayload(globerId, strtDt, endDt,
				Utilities.getTodayDate("dd-MM-yyyy"), srNumber);
		String requestUrl = StaffingBaseTest.baseUrl + GlobalStaffRequestAPIEndPoints.suggestAssignmnet;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(json);
		response = utils.executePOST(requestSpecification, requestUrl);
		return response;
	}

	/**
	 * This method will perform POST to assign STGs
	 * 
	 * @param response
	 * @param username
	 * @param globerType
	 * @return {@link Response}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public Response assignSTGs(Response response, String username, String globerType) throws Exception {
		String requestUrl = null;
		RequestSpecification requestSpecification;
		String staffing = "bernardo.manzella";
		getSrDateDetails(response);
		checkIfPlansPerSrMoreThanThree(response, username, globerType);

		if (username.equalsIgnoreCase(staffing)) {
			json = suggestAndAssignPayload.assignSTGs(srNumber, globerType, username);
			requestUrl = StaffingBaseTest.baseUrl + GlobalStaffRequestAPIEndPoints.suggestAssignmnet;
			requestSpecification = RestAssured.with().urlEncodingEnabled(false)
					.sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(json);
			response = utils.executePOST(requestSpecification, requestUrl);
		} else {
			if (globerType.equalsIgnoreCase("Candidate") || globerType.equalsIgnoreCase("In Pipe") || globerType.equalsIgnoreCase("Proposal Approved")) {
				json = suggestAndAssignPayload.assignTypeSpecificSTGs(srNumber, globerType, username);
				requestUrl = StaffingBaseTest.baseUrl + GlobalStaffRequestAPIEndPoints.bookingAssignment;
				requestSpecification = RestAssured.with().urlEncodingEnabled(false)
						.sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(json);
				response = utils.executePOST(requestSpecification, requestUrl);
			} else {
				json = suggestAndAssignPayload.assignSTGs(srNumber, globerType, username);
				requestUrl = StaffingBaseTest.baseUrl + GlobalStaffRequestAPIEndPoints.suggestAssignmnet;
				requestSpecification = RestAssured.with().urlEncodingEnabled(false)
						.sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(json);
				response = utils.executePOST(requestSpecification, requestUrl);
			}
		}
		return response;
	}
	
	/**
	 * This method will perform POST to assign Glober
	 * 
	 * @param response
	 * @param username
	 * @return {@link Response}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public Response assignGlober(Response response, String username, String globerType) throws Exception {
		
		getSrDateDetails(response);
		checkIfPlansPerSrMoreThanThree(response, username, globerType);
		if (srStartDate.before(todayPlus30Dt)) { // SR date is less than today+30
			json = suggestAndAssignPayload.assignTalentPoolGlober(username, todayPlus30DtStrFormat, srNumber);
		} else {
			json = suggestAndAssignPayload.assignTalentPoolGlober(username, srStartDateStrFormat, srNumber);
		}

		String requestUrl = StaffingBaseTest.baseUrl + GlobalStaffRequestAPIEndPoints.suggestAssignmnet;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(json);
		response = utils.executePOST(requestSpecification, requestUrl);
		return response;
		
	}
}
