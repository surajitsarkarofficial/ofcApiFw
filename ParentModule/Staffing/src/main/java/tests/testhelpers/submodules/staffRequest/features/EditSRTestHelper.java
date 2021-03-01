package tests.testhelpers.submodules.staffRequest.features;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;
import com.google.common.base.CharMatcher;
import com.google.gson.Gson;

import database.submodules.staffRequest.features.EditSRDBHelpers;
import dto.submodules.staffRequest.features.PositionNeedDTOList;
import endpoints.StaffingEndpoints;
import endpoints.submodules.staffRequest.StaffRequestEndpoints;
import endpoints.submodules.staffRequest.features.GlobalStaffRequestAPIEndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.staffRequest.features.EditSRPayloadHelper;
import tests.GlowBaseTest;
import tests.testcases.StaffingBaseTest;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import tests.testhelpers.submodules.staffRequest.StaffRequestTestHelper;
import utils.ExtentHelper;
import utils.RestUtils;
import utils.Utilities;

public class EditSRTestHelper extends StaffRequestTestHelper {

	String message = null;
	boolean b = false;
	Gson gson;
	SoftAssert soft_assert;
	EditSRDBHelpers editSRDBHelpers;
	private String srNumber = null;

	public EditSRTestHelper(String userName) throws Exception {
		super(userName);
		soft_assert = new SoftAssert();
		editSRDBHelpers = new EditSRDBHelpers();
	}

	/**
	 * This method will update project details
	 * 
	 * @param response
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Response getAndUpdateProjectDetails(Response response, String name) throws Exception {
		int i = Utilities.getRandomDay();
		RestUtils restUtils = new RestUtils();
		EditSRPayloadHelper editSRPayloadHelper = new EditSRPayloadHelper(name);

		String jsonPathForSRNumber = "details.positionDTOList[" + i + "].srNumber";
		String srNumber = restUtils.getValueFromResponse(response, jsonPathForSRNumber).toString();

		String jsonPathForSRProjectID = "details.positionDTOList[" + i + "].projectId";
		String srProjectID = restUtils.getValueFromResponse(response, jsonPathForSRProjectID).toString();

		String jsonBody = editSRPayloadHelper.editSRProjectDetailsPage(srProjectID, srNumber);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId).contentType(ContentType.JSON).body(jsonBody);

		String requestURL = StaffingBaseTest.baseUrl + StaffRequestEndpoints.positionsUrl;
		response = restUtils.executePUT(requestSpecification, requestURL);
		ExtentHelper.test.log(Status.INFO, "Able to update Project details.");
		return response;
	}

	/**
	 * This method will update project details with null project id
	 * 
	 * @param response
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Response editProjectDetailsWithNullProjectId(Response response, String name) throws Exception {
		int i = Utilities.getRandomDay();
		RestUtils restUtils = new RestUtils();
		EditSRPayloadHelper editSRPayloadHelper = new EditSRPayloadHelper(name);

		String jsonPathForSRNumber = "details.positionDTOList[" + i + "].srNumber";
		String srNumber = restUtils.getValueFromResponse(response, jsonPathForSRNumber).toString();

		String jsonPathForSRProjectID = "details.positionDTOList[" + i + "].projectId";
		String srProjectID = restUtils.getValueFromResponse(response, jsonPathForSRProjectID).toString();

		String jsonBody = editSRPayloadHelper.editSRProjectDetailsPageWithNullProjectId(srProjectID, srNumber);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId).contentType(ContentType.JSON).body(jsonBody);
		String requestURL = StaffingBaseTest.baseUrl + StaffRequestEndpoints.positionsUrl;
		response = restUtils.executePUT(requestSpecification, requestURL);
		ExtentHelper.test.log(Status.INFO, "unable to edit Project Details with Null ProjectID.");
		return response;
	}

	/**
	 * This method will update project details with wrong client id
	 * 
	 * @param response
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Response editProjectDetailWwrongClientId(Response response, String name) throws Exception {
		int i = Utilities.getRandomDay();
		RestUtils restUtils = new RestUtils();
		EditSRPayloadHelper editSRPayloadHelper = new EditSRPayloadHelper(name);

		String jsonPathForSRNumber = "details.positionDTOList[" + i + "].srNumber";
		String srNumber = restUtils.getValueFromResponse(response, jsonPathForSRNumber).toString();

		String jsonPathForSRProjectID = "details.positionDTOList[" + i + "].projectId";
		String srProjectID = restUtils.getValueFromResponse(response, jsonPathForSRProjectID).toString();

		String jsonBody = editSRPayloadHelper.editSRProjectDetailsPageWithWrongClientId(srProjectID, srNumber);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId).contentType(ContentType.JSON).body(jsonBody);
		String requestURL = StaffingBaseTest.baseUrl + StaffRequestEndpoints.positionsUrl;
		response = restUtils.executePUT(requestSpecification, requestURL);
		ExtentHelper.test.log(Status.INFO, "unable to edit ProjectDetail with wrong ClientID.");
		return response;
	}

	/**
	 * This method will update position details
	 * 
	 * @param response
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Response editPositionDetails(Response response, String name) throws Exception {
		int i = Utilities.getRandomDay();
		RestUtils restUtils = new RestUtils();
		EditSRPayloadHelper editSRPayloadHelper = new EditSRPayloadHelper(name);

		String jsonPathForSRNumber = "details.positionDTOList[" + i + "].srNumber";
		String srNumber = restUtils.getValueFromResponse(response, jsonPathForSRNumber).toString();

		String jsonPathForSRProjectID = "details.positionDTOList[" + i + "].projectId";
		String srProjectID = restUtils.getValueFromResponse(response, jsonPathForSRProjectID).toString();

		String jsonPathForStartDt = "details.positionDTOList[" + i + "].startDate";
		String stDt = restUtils.getValueFromResponse(response, jsonPathForStartDt).toString();

		String jsonPathForEndDt = "details.positionDTOList[" + i + "].assignmentEndDate";
		String endDt = restUtils.getValueFromResponse(response, jsonPathForEndDt).toString();

		String jsonBody = editSRPayloadHelper.editSRPositionDetails(srProjectID, srNumber, stDt, endDt);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId).contentType(ContentType.JSON).body(jsonBody);
		String requestURL = StaffingBaseTest.baseUrl + StaffRequestEndpoints.positionsUrl;
		response = restUtils.executePUT(requestSpecification, requestURL);
		ExtentHelper.test.log(Status.INFO, "able to edit Position Details.");
		return response;
	}

	/**
	 * This method will update position details with wrong end date
	 * 
	 * @param response
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Response editPositionDetailsWwrongEndDateData1(Response response, String name) throws Exception {
		int i = Utilities.getRandomDay();
		RestUtils restUtils = new RestUtils();
		EditSRPayloadHelper editSRPayloadHelper = new EditSRPayloadHelper(name);

		String jsonPathForSRNumber = "details.positionDTOList[" + i + "].srNumber";
		String srNumber = restUtils.getValueFromResponse(response, jsonPathForSRNumber).toString();

		String jsonPathForSRProjectID = "details.positionDTOList[" + i + "].projectId";
		String srProjectID = restUtils.getValueFromResponse(response, jsonPathForSRProjectID).toString();

		String jsonBody = editSRPayloadHelper.editSRPositionDetailsWithWrongEndDateData1(srProjectID, srNumber);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId).contentType(ContentType.JSON).body(jsonBody);
		String requestURL = StaffingBaseTest.baseUrl + StaffRequestEndpoints.positionsUrl;
		response = restUtils.executePUT(requestSpecification, requestURL);
		ExtentHelper.test.log(Status.INFO, "unable to edit Position Details with wrong End Date.");
		return response;
	}

	/**
	 * This method will update position details with wrong end date
	 * 
	 * @param response
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Response editPositionDetailsWwrongEndDateData2(Response response, String name) throws Exception {
		int i = Utilities.getRandomDay();
		RestUtils restUtils = new RestUtils();
		EditSRPayloadHelper editSRPayloadHelper = new EditSRPayloadHelper(name);

		String jsonPathForSRNumber = "details.positionDTOList[" + i + "].srNumber";
		String srNumber = restUtils.getValueFromResponse(response, jsonPathForSRNumber).toString();

		String jsonPathForSRProjectID = "details.positionDTOList[" + i + "].projectId";
		String srProjectID = restUtils.getValueFromResponse(response, jsonPathForSRProjectID).toString();

		String jsonPathForStartDt = "details.positionDTOList[" + i + "].startDate";
		String stDt = restUtils.getValueFromResponse(response, jsonPathForStartDt).toString();

		String jsonPathForEndDt = "details.positionDTOList[" + i + "].assignmentEndDate";
		String endDt = restUtils.getValueFromResponse(response, jsonPathForEndDt).toString();

		String jsonBody = editSRPayloadHelper.editSRPositionDetailsWithWrongEndDateData2(srProjectID, srNumber, stDt,
				endDt);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId).contentType(ContentType.JSON).body(jsonBody);
		String requestURL = StaffingBaseTest.baseUrl + StaffRequestEndpoints.positionsUrl;
		response = restUtils.executePUT(requestSpecification, requestURL);
		ExtentHelper.test.log(Status.INFO, "unable to edit Position Details with wrong End Date.");
		return response;
	}

	/**
	 * This method will update position details with wrong end date
	 * 
	 * @param response
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Response editPositionDetailsWithWrongDateData1(Response response, String name) throws Exception {
		int i = Utilities.getRandomDay();
		RestUtils restUtils = new RestUtils();
		EditSRPayloadHelper editSRPayloadHelper = new EditSRPayloadHelper(name);

		String jsonPathForSRNumber = "details.positionDTOList[" + i + "].srNumber";
		String srNumber = restUtils.getValueFromResponse(response, jsonPathForSRNumber).toString();

		String jsonPathForSRProjectID = "details.positionDTOList[" + i + "].projectId";
		String srProjectID = restUtils.getValueFromResponse(response, jsonPathForSRProjectID).toString();

		String jsonPathForStartDt = "details.positionDTOList[" + i + "].startDate";
		String stDt = restUtils.getValueFromResponse(response, jsonPathForStartDt).toString();

		String jsonPathForEndDt = "details.positionDTOList[" + i + "].assignmentEndDate";
		String endDt = restUtils.getValueFromResponse(response, jsonPathForEndDt).toString();
		
		String jsonBody = editSRPayloadHelper.editSRPositionDetailsWithWrongDateData1(srProjectID, srNumber, stDt,
				endDt);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId).contentType(ContentType.JSON).body(jsonBody);
		String requestURL = StaffingBaseTest.baseUrl + StaffRequestEndpoints.positionsUrl;
		response = restUtils.executePUT(requestSpecification, requestURL);
		ExtentHelper.test.log(Status.INFO, "unable to edit Position Details With Wrong Date.");
		return response;
	}

	/**
	 * This method will update locations
	 * 
	 * @param response
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Response editLocations(Response response, String name) throws Exception {
		int i = Utilities.getRandomDay();
		RestUtils restUtils = new RestUtils();
		EditSRPayloadHelper editSRPayloadHelper = new EditSRPayloadHelper(name);

		String jsonPathForSRNumber = "details.positionDTOList[" + i + "].srNumber";
		String srNumber = restUtils.getValueFromResponse(response, jsonPathForSRNumber).toString();

		String jsonPathForSRProjectID = "details.positionDTOList[" + i + "].projectId";
		String srProjectID = restUtils.getValueFromResponse(response, jsonPathForSRProjectID).toString();

		String jsonPathForStartDt = "details.positionDTOList[" + i + "].startDate";
		String stDt = restUtils.getValueFromResponse(response, jsonPathForStartDt).toString();

		String jsonPathForEndDt = "details.positionDTOList[" + i + "].assignmentEndDate";
		String endDt = restUtils.getValueFromResponse(response, jsonPathForEndDt).toString();
		
		String jsonBody = editSRPayloadHelper.editSRLocationsDetails(srProjectID, srNumber, stDt, endDt);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId).contentType(ContentType.JSON).body(jsonBody);
		String requestURL = StaffingBaseTest.baseUrl + StaffRequestEndpoints.positionsUrl;
		response = restUtils.executePUT(requestSpecification, requestURL);
		ExtentHelper.test.log(Status.INFO, "able to edit Locations successfully.");
		return response;
	}

	/**
	 * This Test will update Locations with As Anywhere
	 * 
	 * @param response
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Response editLocationsWithSecondLocationAsAnywhere(Response response, String name) throws Exception {
		int i = Utilities.getRandomDay();
		RestUtils restUtils = new RestUtils();
		EditSRPayloadHelper editSRPayloadHelper = new EditSRPayloadHelper(name);

		String jsonPathForSRNumber = "details.positionDTOList[" + i + "].srNumber";
		String srNumber = restUtils.getValueFromResponse(response, jsonPathForSRNumber).toString();

		String jsonPathForSRProjectID = "details.positionDTOList[" + i + "].projectId";
		String srProjectID = restUtils.getValueFromResponse(response, jsonPathForSRProjectID).toString();

		String jsonPathForStartDt = "details.positionDTOList[" + i + "].startDate";
		String stDt = restUtils.getValueFromResponse(response, jsonPathForStartDt).toString();

		String jsonPathForEndDt = "details.positionDTOList[" + i + "].assignmentEndDate";
		String endDt = restUtils.getValueFromResponse(response, jsonPathForEndDt).toString();

		String jsonBody = editSRPayloadHelper.editSRLocationsDetails(srProjectID, srNumber, stDt, endDt);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId).contentType(ContentType.JSON).body(jsonBody);
		String requestURL = StaffingBaseTest.baseUrl + StaffRequestEndpoints.positionsUrl;
		response = restUtils.executePUT(requestSpecification, requestURL);
		ExtentHelper.test.log(Status.INFO, "able to edit Locations with Second Location As Anywhere.");
		return response;
	}

	/**
	 * This Test will update Locations without primary location
	 * 
	 * @param response
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Response editLocationsWithoutPrimaryLocation(Response response, String name) throws Exception {
		int i = Utilities.getRandomDay();
		RestUtils restUtils = new RestUtils();
		EditSRPayloadHelper editSRPayloadHelper = new EditSRPayloadHelper(name);

		String jsonPathForSRNumber = "details.positionDTOList[" + i + "].srNumber";
		String srNumber = restUtils.getValueFromResponse(response, jsonPathForSRNumber).toString();

		String jsonPathForSRProjectID = "details.positionDTOList[" + i + "].projectId";
		String srProjectID = restUtils.getValueFromResponse(response, jsonPathForSRProjectID).toString();

		String jsonPathForStartDt = "details.positionDTOList[" + i + "].startDate";
		String stDt = restUtils.getValueFromResponse(response, jsonPathForStartDt).toString();

		String jsonPathForEndDt = "details.positionDTOList[" + i + "].assignmentEndDate";
		String endDt = restUtils.getValueFromResponse(response, jsonPathForEndDt).toString();

		String jsonBody = editSRPayloadHelper.editSRLocationsDetailsWithoutPrimarylocation(srProjectID, srNumber, stDt,
				endDt);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId).contentType(ContentType.JSON).body(jsonBody);
		String requestURL = StaffingBaseTest.baseUrl + StaffRequestEndpoints.positionsUrl;
		response = restUtils.executePUT(requestSpecification, requestURL);
		ExtentHelper.test.log(Status.INFO, "unable to edit Locations without Primary Location.");
		return response;
	}

	/**
	 * This Test will update Locations with primary location as Anywhere value
	 * 
	 * @param response
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Response editLocationsWithPrimayLocationAsAnywhereValue(Response response, String name) throws Exception {
		int i = Utilities.getRandomDay();
		RestUtils restUtils = new RestUtils();
		EditSRPayloadHelper editSRPayloadHelper = new EditSRPayloadHelper(name);

		String jsonPathForSRNumber = "details.positionDTOList[" + i + "].srNumber";
		String srNumber = restUtils.getValueFromResponse(response, jsonPathForSRNumber).toString();

		String jsonPathForSRProjectID = "details.positionDTOList[" + i + "].projectId";
		String srProjectID = restUtils.getValueFromResponse(response, jsonPathForSRProjectID).toString();

		String jsonPathForStartDt = "details.positionDTOList[" + i + "].startDate";
		String stDt = restUtils.getValueFromResponse(response, jsonPathForStartDt).toString();

		String jsonPathForEndDt = "details.positionDTOList[" + i + "].assignmentEndDate";
		String endDt = restUtils.getValueFromResponse(response, jsonPathForEndDt).toString();
		
		String jsonBody = editSRPayloadHelper.editSRLocationsDetailsWithoutPrimarylocation(srProjectID, srNumber, stDt,
				endDt);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId).contentType(ContentType.JSON).body(jsonBody);
		String requestURL = StaffingBaseTest.baseUrl + StaffRequestEndpoints.positionsUrl;
		response = restUtils.executePUT(requestSpecification, requestURL);
		ExtentHelper.test.log(Status.INFO, "unable to edit Locations with Primay Location As Anywhere Value.");
		return response;
	}

	/**
	 * This Test will update Locations with same value
	 * 
	 * @param response
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Response editLocationsWithBothLocationAsSameValue(Response response, String name) throws Exception {
		int i = Utilities.getRandomDay();
		RestUtils restUtils = new RestUtils();
		EditSRPayloadHelper editSRPayloadHelper = new EditSRPayloadHelper(name);

		String jsonPathForSRNumber = "details.positionDTOList[" + i + "].srNumber";
		String srNumber = restUtils.getValueFromResponse(response, jsonPathForSRNumber).toString();

		String jsonPathForSRProjectID = "details.positionDTOList[" + i + "].projectId";
		String srProjectID = restUtils.getValueFromResponse(response, jsonPathForSRProjectID).toString();

		String jsonPathForStartDt = "details.positionDTOList[" + i + "].startDate";
		String stDt = restUtils.getValueFromResponse(response, jsonPathForStartDt).toString();

		String jsonPathForEndDt = "details.positionDTOList[" + i + "].assignmentEndDate";
		String endDt = restUtils.getValueFromResponse(response, jsonPathForEndDt).toString();

		String jsonBody = editSRPayloadHelper.editSRLocationsDetailsWithDataSet3(srProjectID, srNumber, stDt, endDt);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId).contentType(ContentType.JSON).body(jsonBody);
		String requestURL = StaffingBaseTest.baseUrl + StaffRequestEndpoints.positionsUrl;
		response = restUtils.executePUT(requestSpecification, requestURL);
		ExtentHelper.test.log(Status.INFO, "unable to edit Locations with both Location As Same Value.");
		return response;
	}

	/**
	 * This Test will update Locations with Anywhere value
	 * 
	 * @param response
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Response editLocationsWithBothHavingAnywhereValue(Response response, String name) throws Exception {
		int i = Utilities.getRandomDay();
		RestUtils restUtils = new RestUtils();
		EditSRPayloadHelper editSRPayloadHelper = new EditSRPayloadHelper(name);

		String jsonPathForSRNumber = "details.positionDTOList[" + i + "].srNumber";
		String srNumber = restUtils.getValueFromResponse(response, jsonPathForSRNumber).toString();

		String jsonPathForSRProjectID = "details.positionDTOList[" + i + "].projectId";
		String srProjectID = restUtils.getValueFromResponse(response, jsonPathForSRProjectID).toString();

		String jsonPathForStartDt = "details.positionDTOList[" + i + "].startDate";
		String stDt = restUtils.getValueFromResponse(response, jsonPathForStartDt).toString();

		String jsonPathForEndDt = "details.positionDTOList[" + i + "].assignmentEndDate";
		String endDt = restUtils.getValueFromResponse(response, jsonPathForEndDt).toString();

		String jsonBody = editSRPayloadHelper.editSRLocationsDetailsWithDataSet2(srProjectID, srNumber, stDt, endDt);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId).contentType(ContentType.JSON).body(jsonBody);
		String requestURL = StaffingBaseTest.baseUrl + StaffRequestEndpoints.positionsUrl;
		response = restUtils.executePUT(requestSpecification, requestURL);
		ExtentHelper.test.log(Status.INFO, "unable to edit Locations with both Having Anywhere Value.");
		return response;
	}

	/**
	 * This Test will update Load field
	 * 
	 * @param response
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Response editLoadField(Response response, String name) throws Exception {
		int i = Utilities.getRandomDay();
		RestUtils restUtils = new RestUtils();
		EditSRPayloadHelper editSRPayloadHelper = new EditSRPayloadHelper(name);

		String jsonPathForSRNumber = "details.positionDTOList[" + i + "].srNumber";
		String srNumber = restUtils.getValueFromResponse(response, jsonPathForSRNumber).toString();

		String jsonPathForSRProjectID = "details.positionDTOList[" + i + "].projectId";
		String srProjectID = restUtils.getValueFromResponse(response, jsonPathForSRProjectID).toString();

		String jsonPathForStartDt = "details.positionDTOList[" + i + "].startDate";
		String stDt = restUtils.getValueFromResponse(response, jsonPathForStartDt).toString();

		String jsonPathForEndDt = "details.positionDTOList[" + i + "].assignmentEndDate";
		String endDt = restUtils.getValueFromResponse(response, jsonPathForEndDt).toString();

		String jsonBody = editSRPayloadHelper.editSRLocationsDetailsWithoutload(srProjectID, srNumber, stDt, endDt);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId).contentType(ContentType.JSON).body(jsonBody);
		String requestURL = StaffingBaseTest.baseUrl + StaffRequestEndpoints.positionsUrl;
		response = restUtils.executePUT(requestSpecification, requestURL);
		ExtentHelper.test.log(Status.INFO, "nable to edit SR Locations Details without load.");
		return response;
	}

	/**
	 * This Test will update update travel period
	 * 
	 * @param response
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Response editTravelPeriodLength(Response response, String name) throws Exception {
		int i = Utilities.getRandomDay();
		RestUtils restUtils = new RestUtils();
		EditSRPayloadHelper editSRPayloadHelper = new EditSRPayloadHelper(name);

		String jsonPathForSRNumber = "details.positionDTOList[" + i + "].srNumber";
		String srNumber = restUtils.getValueFromResponse(response, jsonPathForSRNumber).toString();

		String jsonPathForSRProjectID = "details.positionDTOList[" + i + "].projectId";
		String srProjectID = restUtils.getValueFromResponse(response, jsonPathForSRProjectID).toString();

		String jsonPathForStartDt = "details.positionDTOList[" + i + "].startDate";
		String stDt = restUtils.getValueFromResponse(response, jsonPathForStartDt).toString();

		String jsonPathForEndDt = "details.positionDTOList[" + i + "].assignmentEndDate";
		String endDt = restUtils.getValueFromResponse(response, jsonPathForEndDt).toString();

		String jsonBody = editSRPayloadHelper.editSRTravelPeriodLength(srProjectID, srNumber, stDt, endDt);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId).contentType(ContentType.JSON).body(jsonBody);
		String requestURL = StaffingBaseTest.baseUrl + StaffRequestEndpoints.positionsUrl;
		response = restUtils.executePUT(requestSpecification, requestURL);
		ExtentHelper.test.log(Status.INFO, "Unable to edit travel period.");
		return response;
	}

	/**
	 * This Test will update Edit SR skills
	 * 
	 * @param response
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Response editSRSkills(Response response, String name) throws Exception {
		int i = Utilities.getRandomDay();
		RestUtils restUtils = new RestUtils();
		EditSRPayloadHelper editSRPayloadHelper = new EditSRPayloadHelper(name);
		
		String jsonPathForSRNumber = "details.positionDTOList[" + i + "].srNumber";
		String srNumber = restUtils.getValueFromResponse(response, jsonPathForSRNumber).toString();

		String jsonPathForSRProjectID = "details.positionDTOList[" + i + "].projectId";
		String srProjectID = restUtils.getValueFromResponse(response, jsonPathForSRProjectID).toString();

		String jsonPathForStartDt = "details.positionDTOList[" + i + "].startDate";
		String stDt = restUtils.getValueFromResponse(response, jsonPathForStartDt).toString();

		String jsonPathForEndDt = "details.positionDTOList[" + i + "].assignmentEndDate";
		String endDt = restUtils.getValueFromResponse(response, jsonPathForEndDt).toString();
		
		JsonPath js = response.jsonPath();
		String srSkills = js.getString("details.positionDTOList[" + i + "].skills").replaceAll("\\[|\\]", "");

		List<Object> arr = genericSkills(srSkills, srNumber, response);

		String jsonBody = editSRPayloadHelper.editSRSkills(srProjectID, srNumber, arr, stDt, endDt);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId).contentType(ContentType.JSON).body(jsonBody);
		String requestURL = StaffingBaseTest.baseUrl + StaffRequestEndpoints.positionsUrl;
		response = restUtils.executePUT(requestSpecification, requestURL);
		ExtentHelper.test.log(Status.INFO, "able to EDIT for SR skills successfully");
		return response;
	}

	/**
	 * This Test will fetch Statement of work for project
	 * 
	 * @param response
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Response editButton(Response response, String name) throws Exception {
		
		int i = Utilities.getRandomDay();
		RestUtils restUtils = new RestUtils();

		String jsonPathForSRProjectID = "details.positionDTOList[" + i + "].projectId";
		String srProjectID = restUtils.getValueFromResponse(response, jsonPathForSRProjectID).toString();

		ArrayList<String> sDBSowName = editSRDBHelpers.getSowName(srProjectID);

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId);

		String requestURL = StaffingBaseTest.baseUrl + String.format(GlobalStaffRequestAPIEndPoints.checkEditButton,srProjectID);
		response = restUtils.executeGET(requestSpecification, requestURL);

		JsonPath js = response.jsonPath();
		System.out.println(js.prettyPrint());
		String actualSowName = js.getString("details[0].name").replaceAll("\\[|\\]", "");

		String[] arrRespncSOWName;
		for (String str : sDBSowName) {
			if (actualSowName.contains(",")) {
				arrRespncSOWName = actualSowName.split(",");
				for (String actualstr : arrRespncSOWName) {
					if (str.equalsIgnoreCase(actualstr)) {
						b = true;
						break;
					}
				}
			} else if (str.equals(actualSowName)) {
				b = true;
				break;
			}
		}
		ExtentHelper.test.log(Status.INFO, "able to EDIT for SR Number successfully");
		return response;
	}
	
	/**
	 * This method return SowName Status
	 * @return {@link Boolean}
	 * @author shadab.waikar
	 */
	public boolean getEditButtonStatus() {
		return b;
	}

	/**
	 * This Test will get SR history
	 * 
	 * @param response
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Response editSRHistory(Response response, String name) throws Exception {
		int i = Utilities.getRandomDay();
		String isRotatedPosition = "true";
		RestUtils restUtils = new RestUtils();

		String jsonPathForSRNumber = "details.positionDTOList[" + i + "].srNumber";
		String srNumber = restUtils.getValueFromResponse(response, jsonPathForSRNumber).toString();

		String jsonPathForSRPositionID = "details.positionDTOList[" + i + "].positionId";
		String srPositionID = restUtils.getValueFromResponse(response, jsonPathForSRPositionID).toString();
		
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId);
		String requestURL = StaffingBaseTest.baseUrl + String.format(GlobalStaffRequestAPIEndPoints.historyPosition, isRotatedPosition,srPositionID);
		response = restUtils.executeGET(requestSpecification, requestURL);
		JsonPath jPath = response.jsonPath();
		JSONObject jObj = null;
		jObj = new JSONObject(response.asString());
		JSONObject j = jObj.getJSONObject("details");
		JSONArray jArr = j.getJSONArray("positionChangeHistoryList");
		String sdatabaseId = editSRDBHelpers.getEditSrHistoryId(srPositionID); // fetch latest created Row ID From
																				// Database.
		for (int k = 0; k < jArr.length(); k++) {
			String id = jPath.getString("details.positionChangeHistoryList[" + k + "].id");
			if (id.equals(sdatabaseId)) {
				List<Object> changesArr = jPath.getList("details.positionChangeHistoryList[" + k + "].changes");
				if (changesArr.isEmpty()) {
					break;
				}
				ArrayList<String> IdofEachChange = editSRDBHelpers.getEditSrHistoryChangesId(sdatabaseId);
				int counter = 0;
				for (String str : IdofEachChange) {
					ArrayList<String> arrlst = editSRDBHelpers.getEditSrHistoryDetailsWithId(str);
					String property = jPath.getString(
							"details.positionChangeHistoryList[" + k + "].changes[" + counter + "].property");
					String oldValue = jPath.getString(
							"details.positionChangeHistoryList[" + k + "].changes[" + counter + "].oldValue");
					String newValue = jPath.getString(
							"details.positionChangeHistoryList[" + k + "].changes[" + counter + "].newValue");

					if (oldValue.contains("oldSkills")) {
						JSONArray previousExistSkill = null;
						JSONArray RemovedSkill = null;

						if (newValue.contains("newSkills")) {
							JSONObject p = new JSONObject(newValue);
							previousExistSkill = p.getJSONArray("oldSkills");
							RemovedSkill = p.getJSONArray("removedSkills");
						}

						soft_assert.assertTrue(!previousExistSkill.toString().contains(RemovedSkill.toString()),
								"SR Number is " + srNumber);
						soft_assert.assertEquals(arrlst.get(0), property, "SR Number is " + srNumber);
						soft_assert.assertEquals(arrlst.get(1), oldValue, "SR Number is " + srNumber);
						break;
					}
					soft_assert.assertEquals(arrlst.get(0), property, "SR Number is " + srNumber);
					soft_assert.assertEquals(arrlst.get(1), oldValue, "SR Number is " + srNumber);
					soft_assert.assertEquals(arrlst.get(2), newValue, "SR Number is " + srNumber);
					counter++;
				}
			}
		}
		return response;
	}

	/**
	 * This method will edit an Sr according to provided location
	 * 
	 * @param name
	 * @param location
	 * @return {@link Response}
	 * @throws Exception
	 */
	public Response editSrWithProvidedLocation(String name, String location) throws Exception {
		String json = null;
		String positionRole = "java%20developer";
		String positionSceniority = "SSr";
		RestUtils restUtils = new RestUtils();
		EditSRPayloadHelper editSRPayloadHelper = new EditSRPayloadHelper(name);

		// Albertha Skills
		String getAlberthaSkills = String.format(
				StaffingBaseTest.baseUrl + StaffingEndpoints.getAlberthaSkills, positionRole,
				positionSceniority);
		RequestSpecification getAlberthRequestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId);
		Response alberthResponse = restUtils.executeGET(getAlberthRequestSpecification, getAlberthaSkills);

		// Cache Skills
		String getCacheSkills = String.format(StaffingBaseTest.baseUrl + StaffRequestEndpoints.cachedSkillsUrl);
		RequestSpecification getCacheRequestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId);
		Response cacheResponse = restUtils.executeGET(getCacheRequestSpecification, getCacheSkills);

		String id = restUtils.getValueFromResponse(alberthResponse, "data[0].skill.id").toString();
		if (id == null) {
			json = editSRPayloadHelper.getEditSrLocationPayload(cacheResponse, location, positionSceniority);
		} else {
			json = editSRPayloadHelper.getEditSrLocationPayload(alberthResponse, location, positionSceniority);
		}

		String requestUrl = StaffingBaseTest.baseUrl + StaffRequestEndpoints.positionsUrl;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(json);
		Response editSrResponse = restUtils.executePOST(requestSpecification, requestUrl);
		new StaffingBaseTest().validateResponseToContinueTest(editSrResponse, 201, "Unable to edit Sr", true);

		srNumber = CharMatcher.inRange('0', '9')
				.retainFrom(restUtils.getValueFromResponse(editSrResponse, "message").toString());

		String getSrUrl = String.format(StaffingBaseTest.baseUrl + GlobalStaffRequestAPIEndPoints.srNumberPositionsUrl, srNumber);
		RequestSpecification getSrRequestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId);
		Response response = restUtils.executeGET(getSrRequestSpecification, getSrUrl);
		new StaffingBaseTest().validateResponseToContinueTest(response, 200, "Isuee with GET API response", true);
		return response;
	}

	/**
	 * This method will fetch skill from Albertha
	 * 
	 * @param responseSkills
	 * @param SrNumber
	 * @param response
	 * @return
	 */
	public static List<Object> genericSkills(String responseSkills, String SrNumber, Response response) {
		JsonPath jsAlbertha = null;
		ArrayList<Object> arrContainsSkillsDetails = new ArrayList<Object>();
		String[] arrResponseSkills = responseSkills.split(",");
		RestUtils restUtils = new RestUtils();

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId);
		String requestUrl = StaffingBaseTest.baseUrl + StaffRequestEndpoints.cachedSkillsUrl;
		response = restUtils.executeGET(requestSpecification, requestUrl);
		for (String eachSkill : arrResponseSkills) {
			List<PositionNeedDTOList> listOfAlberthaSkills = getPositionDTOList(response, jsAlbertha, eachSkill);
			arrContainsSkillsDetails.addAll(listOfAlberthaSkills);
		}
		return arrContainsSkillsDetails;
	}

	/**
	 * This method will get position dto list
	 * 
	 * @param rs
	 * @param jsonpath
	 * @param skillName
	 * @return
	 */
	public static List<PositionNeedDTOList> getPositionDTOList(Response rs, JsonPath jsonpath, String skillName) {
		PositionNeedDTOList positionDtoList;
		List<PositionNeedDTOList> listOfSkills = new ArrayList<PositionNeedDTOList>();
		JSONObject jObj = null;
		JSONArray arrData = null;
		jsonpath = rs.jsonPath();
		jObj = new JSONObject(rs.asString());
		arrData = jObj.getJSONArray("data");

		for (int i = 0; i < arrData.length(); i++) {
			int alberthaId = jsonpath.get("data[" + i + "].id");
			String competencyId = null;
			String competencyName = jsonpath.get("data[" + i + "].name");
			String importance = "2";
			String importanceName = "Required";
			positionDtoList = new PositionNeedDTOList(alberthaId, competencyId, competencyName, importance,
					importanceName);
			if (skillName.trim().equalsIgnoreCase(competencyName)) {
				listOfSkills.add(positionDtoList);
				break;
			}
		}
		return listOfSkills;
	}
}
