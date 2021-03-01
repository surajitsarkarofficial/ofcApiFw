package tests.testhelpers.submodules.leave;

import java.sql.SQLException;
import java.util.Map;

import com.aventstack.extentreports.Status;

import constants.submodules.LeaveConstants.LeaveConstants;
import endpoints.submodules.leave.LeaveEndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.leave.LeavePayloadHelper;
import tests.testcases.submodules.gatekeepers.features.SearchGatekeepersTest;
import tests.testhelpers.StaffingTestHelper;
import utils.ExtentHelper;

/**
 * 
 * @author deepakkumar.hadiya
 *
 */
public class LeaveTestHelper extends StaffingTestHelper implements LeaveConstants{

	/**
	 * This method is used to apply for leave
	 * @param data
	 * @return {@link Response}
	 * @throws SQLException
	 */
	public Response applyForLeave(Map<Object, Object> data) throws SQLException {
		String headerKey=data.get(HEADER_KEY).toString();
		Object headerValue=data.get(HEADER_VALUE).toString();
		LeavePayloadHelper leavePayloadHelper=new LeavePayloadHelper();
		String requestPayLoad = leavePayloadHelper.getCreateInterviewPayload(data);
		RequestSpecification requestSpecification = RestAssured.with().header(headerKey,headerValue).and().contentType(ContentType.JSON).body(requestPayLoad);
		String url = SearchGatekeepersTest.baseUrl+LeaveEndPoints.leaveService;
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executePOST(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "Post method for applying leave is executed successfully");
		return response;
	}
	
	/**
	 * This method is used to cancel applied leave
	 * @param data
	 * @return {@link Response}
	 * @throws SQLException
	 */
	public Response cancelLeave(Map<Object, Object> data) throws SQLException {
		String headerKey=data.get(HEADER_KEY).toString();
		Object headerValue=data.get(HEADER_VALUE).toString();
		RequestSpecification requestSpecification = RestAssured.with().header(headerKey,headerValue).and().contentType(ContentType.JSON);
		requestSpecification.queryParam(GLOBAL_iD, data.get(GLOBAL_ID).toString());
		requestSpecification.queryParam(DATE_FROM, data.get(DATE_FROM).toString());
		requestSpecification.queryParam(DATE_TO, data.get(DATE_TO).toString());
		String url = SearchGatekeepersTest.baseUrl+LeaveEndPoints.leaveService;
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeDELETE(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "Delete method for cancelling leave is executed successfully");
		return response;
	}
}
