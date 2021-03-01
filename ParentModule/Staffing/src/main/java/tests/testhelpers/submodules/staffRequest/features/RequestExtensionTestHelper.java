package tests.testhelpers.submodules.staffRequest.features;

import static org.testng.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import com.aventstack.extentreports.Status;

import database.submodules.staffRequest.StaffRequestDBHelper;
import endpoints.submodules.staffRequest.features.RequestExtensionEndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.staffRequest.features.RequestExtensionPayloadHelper;
import properties.StaffingProperties;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import tests.testhelpers.submodules.staffRequest.StaffRequestTestHelper;
import utils.RestUtils;

/**
 * 
 * @author akshata.dongare
 *
 */
public class RequestExtensionTestHelper extends StaffRequestTestHelper{
	RequestExtensionPayloadHelper requestExtensionPayloadHelper;
	RestUtils restUtils = new RestUtils();
	Response reqExtensionResponse;
	static StaffRequestDBHelper staffRequestDBHelper;
	
	public RequestExtensionTestHelper(String userName) throws Exception {
		super(userName);
		requestExtensionPayloadHelper = new RequestExtensionPayloadHelper();
		staffRequestDBHelper = new StaffRequestDBHelper();
	}
	
	/**
	 * This method will return reason of request Extension
	 * @param globerType
	 * @return response
	 * @throws Exception
	 */
	public Response putRequestExtension(String globerType) throws Exception {
		//Get staffplanId after suggestion
		String staffPlanId = getStaffPlanId(globerType);
		
		String jsonBody = requestExtensionPayloadHelper.requestExtensionValidPayload(staffPlanId, "Glober");
		
		if(jsonBody==null) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Json body is null hence can not request Extension");
		}else {
			RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId)
					.contentType(ContentType.JSON).body(jsonBody);
			String putReqExtensionUrl = StaffRequestBaseTest.baseUrl + RequestExtensionEndPoints.putRequestExtensionUrl;
			reqExtensionResponse =restUtils.executePUT(reqSpec, putReqExtensionUrl);
		}
		return reqExtensionResponse;	
	}

	/**
	 * Get mentee is after assigning mentor to mentee
	 * @param userName
	 * @return String
	 * @throws Exception 
	 */
	public String assignMentorToMentee(String userName) throws Exception {
		String jsonbody = requestExtensionPayloadHelper.assignMentorToMenteeValidPayload(userName);
		
		RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId)
				.contentType(ContentType.JSON).body(jsonbody);
		String postAssignMentorUrl = StaffingProperties.glowVIQAMs + RequestExtensionEndPoints.postAssignMentorToMenteeUrl;
		Response assigMentorToMenteeResponse = restUtils.executePOST(reqSpec, postAssignMentorUrl);
		assertEquals(restUtils.getValueFromResponse(assigMentorToMenteeResponse, "message"), "The Glober has been assigned mentor", "Assign mentor to mentee message is wrong");
		validateResponseToContinueTest(assigMentorToMenteeResponse, 201, "Assign mentor to mentee API call was not successful", true);
		JSONObject jo = new JSONObject(jsonbody);
		String menteeId = jo.getString("menteeId");
		return menteeId;
	}
	
	/**
	 * Get glober id from glober name
	 * @param userName
	 * @return String
	 * @throws SQLException
	 */
	public String getGloberId(String userName) throws SQLException {
		String globerId = staffRequestDBHelper.getGloberId(userName);
		return globerId;
	}
	
	/**
	 * Get mentor glober ids
	 * @param userName
	 * @return list
	 * @throws SQLException
	 */
	public List<String> getMentorGloberId(String globerId) throws SQLException {
		List<String> mentorIdList = new ArrayList<String>();
		String mentorIdFromMentor = staffRequestDBHelper.getMentorIdFromGlober(globerId);
		String mentorIdFromMentorLeader = staffRequestDBHelper.getMentorIdFromGloberId(globerId);
		mentorIdList.add(mentorIdFromMentor);
		mentorIdList.add(mentorIdFromMentorLeader);
		return mentorIdList;
	}
	
	/**
	 * Get mentor id from glober view
	 * @param globerId
	 * @return String
	 * @throws SQLException
	 */
	public String getMentorIdFromGloberView(String globerId) throws SQLException {
		String mentorId = staffRequestDBHelper.getMentorIdFromGloberView(globerId);
		return mentorId;
	}
	
	/**
	 * Get response of assigning mentor to invalid mentee
	 * @param userName
	 * @return String
	 * @throws SQLException
	 */
	public Response assignMentorToNullMentee(String userName) throws SQLException {
		String jsonbody = requestExtensionPayloadHelper.assignMentorToMenteeNullMentee(userName);
		
		RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId)
				.contentType(ContentType.JSON).body(jsonbody);
		String postAssignMentorUrl = StaffingProperties.glowVIQAMs + RequestExtensionEndPoints.postAssignMentorToMenteeUrl;
		Response assigMentorToMenteeResponse = restUtils.executePOST(reqSpec, postAssignMentorUrl);
		return assigMentorToMenteeResponse;
	}
	
	/**
	 * Get response of assigning invalid mentor to mentee
	 * @param userName
	 * @return String
	 * @throws SQLException
	 */
	public Response assignNullMentorToMentee(String userName) throws SQLException {
		String jsonbody = requestExtensionPayloadHelper.assignMentorToMenteeNullMentor(userName);
		
		RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId)
				.contentType(ContentType.JSON).body(jsonbody);
		String postAssignMentorUrl = StaffingProperties.glowVIQAMs + RequestExtensionEndPoints.postAssignMentorToMenteeUrl;
		Response assigMentorToMenteeResponse = restUtils.executePOST(reqSpec, postAssignMentorUrl);
		return assigMentorToMenteeResponse;
	}
	
	/**
	 * Get response of assigning invalid mentor to  mentee
	 * @param userName
	 * @return String
	 * @throws SQLException
	 */
	public Response assignMentorToMenteeHavingMentor(String userName) throws SQLException {
		String jsonbody = requestExtensionPayloadHelper.assignMentorToMenteeHavingMentorPayload(userName);
		
		RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId)
				.contentType(ContentType.JSON).body(jsonbody);
		String postAssignMentorUrl = StaffingProperties.glowVIQAMs + RequestExtensionEndPoints.postAssignMentorToMenteeUrl;
		Response assigMentorToMenteeResponse = restUtils.executePOST(reqSpec, postAssignMentorUrl);
		return assigMentorToMenteeResponse;
	}
	
	/**
	 * Get response of assigning mentor to  mentee same ids
	 * @param userName
	 * @return String
	 * @throws SQLException
	 */
	public Response assignMentorToMenteeSameIds(String userName) throws SQLException {
		String jsonbody = requestExtensionPayloadHelper.assignMentorToMenteeSameIds(userName);
		
		RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId)
				.contentType(ContentType.JSON).body(jsonbody);
		String postAssignMentorUrl = StaffingProperties.glowVIQAMs + RequestExtensionEndPoints.postAssignMentorToMenteeUrl;
		Response assigMentorToMenteeResponse = restUtils.executePOST(reqSpec, postAssignMentorUrl);
		return assigMentorToMenteeResponse;
	}
	
	/**
	 * Get response of assigning inactive mentor to  mentee
	 * @param userName
	 * @return String
	 * @throws SQLException
	 */
	public Response assignInactiveMentorToMentee(String userName) throws SQLException {
		String jsonbody = requestExtensionPayloadHelper.assignInactiveMentorToMenteePayload(userName);
		
		RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId)
				.contentType(ContentType.JSON).body(jsonbody);
		String postAssignMentorUrl = StaffingProperties.glowVIQAMs + RequestExtensionEndPoints.postAssignMentorToMenteeUrl;
		Response assigMentorToMenteeResponse = restUtils.executePOST(reqSpec, postAssignMentorUrl);
		return assigMentorToMenteeResponse;
	}
}
