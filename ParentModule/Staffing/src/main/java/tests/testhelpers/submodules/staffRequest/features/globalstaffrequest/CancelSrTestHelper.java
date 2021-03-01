package tests.testhelpers.submodules.staffRequest.features.globalstaffrequest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import com.aventstack.extentreports.Status;

import database.submodules.staffRequest.features.globalstaffrequest.CancelSrDBHelper;
import endpoints.submodules.staffRequest.features.GlobalStaffRequestAPIEndPoints;
import endpoints.submodules.staffRequest.features.globalstaffrequest.CancelSrEndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.staffRequest.features.globalstaffrequest.CancelSrPayloadHelper;
import tests.GlowBaseTest;
import tests.testcases.StaffingBaseTest;
import tests.testcases.submodules.openpositions.OpenPositionBaseTest;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import tests.testhelpers.submodules.staffRequest.StaffRequestTestHelper;
import utils.ExtentHelper;
import utils.RestUtils;

/**
 * @author shadab.waikar
 */

public class CancelSrTestHelper extends StaffRequestTestHelper {

	static String positionState;
	CancelSrDBHelper cancelSrDb;
	CancelSrPayloadHelper cancelSrPayload;
	
	public CancelSrTestHelper(String userName) throws Exception {
		super(userName);
		cancelSrDb = new CancelSrDBHelper();
		cancelSrPayload = new CancelSrPayloadHelper();
	}

	/**
	 * This method will get SR details from grid
	 * 
	 * @param name
	 * @param globerType
	 * @return {@link Response}
	 * @throws Exception
	 */
	public Response getSRDetailsFromMySrGrid(String name, String globerType) throws Exception {
		int viewId = 6, parentViewId = 6, offset = 0, limit = 50;
		String sorting = "Astage";

		String requestUrl = StaffingBaseTest.baseUrl + String.format(GlobalStaffRequestAPIEndPoints.srGrid, viewId,
				name, parentViewId, offset, limit, sorting);

		RestUtils restUtils = new RestUtils();

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId);

		Response response = restUtils.executeGET(requestSpecification, requestUrl);
		validateResponseToContinueTest(response, 200, "Issue With GET API response.", true);

		ExtentHelper.test.log(Status.INFO, "Fetched all SR Details from Grid");
		ExtentHelper.test.log(Status.INFO, "Able to load SR Grid.");
		return response;
	}
	
	/**
	 * This method will perform PUT to cancel Staff Request
	 * @param response
	 * @return {@link Response}
	 * @throws Exception
	 */
	public Response cancelStaffRequest(Response response) throws Exception {
		int i = new Random().nextInt(3);
		String srNumber = restUtils.getValueFromResponse(response, "details.positionDTOList[" + i + "].srNumber").toString();
		positionState = cancelSrDb.getPositionStateFromPosition(srNumber);
		String positionId = cancelSrDb.getPositionId(srNumber);
		String payload = cancelSrPayload.cancelSrPayload(positionId);
		
		String requestUrl = StaffingBaseTest.baseUrl + CancelSrEndPoints.cancelSrUrl;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(payload);
		response = restUtils.executePUT(requestSpecification, requestUrl);
		
		return response;
	}
	
	/**
	 * This method will update position state
	 * @param srNumber
	 * @return {@link Integer}
	 * @throws SQLException
	 */
	public Integer updatePositionState(String srNumber) throws SQLException {
		return cancelSrDb.updatePositionState(positionState,srNumber);
	}
	
	/**
	 * This method will perform GET to fetch reasons to cancel staff request
	 * {@link Response}
	 */
	public Response getCancelSrReasons() {	
		String url = StaffingBaseTest.baseUrl + CancelSrEndPoints.cancelSrReasonsUrl;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(OpenPositionBaseTest.sessionId);	
		Response cancelSrReasons = restUtils.executeGET(requestSpecification,url);	
		return cancelSrReasons;		
	}
	
	/**
	 * This method will fetch reasons from database
	 * @param response
	 * @return {@link ArrayList}
	 * @throws SQLException
	 */
	public ArrayList<String> getReasonsFromDb(Response response) throws SQLException{
		ArrayList<String> reasons = new ArrayList<String>();
		io.restassured.path.json.JsonPath js = response.jsonPath();
		ArrayList<Integer> ids = js.get("details.id");
		
		for(int id : ids) {
			String reason = cancelSrDb.getPositionCancelReasonById(id);
			reasons.add(reason);
		}
		return reasons;
	}
}
