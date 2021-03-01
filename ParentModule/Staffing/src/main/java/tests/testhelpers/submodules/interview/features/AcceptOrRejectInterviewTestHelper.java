package tests.testhelpers.submodules.interview.features;

import com.aventstack.extentreports.Status;

import endpoints.submodules.interview.features.AcceptOrRejectInterviewEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.submodules.interview.features.AcceptInterviewTest;
import tests.testhelpers.submodules.interview.InterviewTestHelper;
import utils.ExtentHelper;

/**
 * @author deepakkumar.hadiya
 */


public class AcceptOrRejectInterviewTestHelper extends InterviewTestHelper {

	/**
	 * This Method is used to get response for accepting or rejecting an interview request
	 * @param interviewId
	 * @param interviewerId
	 * @param headerKey
	 * @param headerValue
	 * @param acceptOrRejctInterview
	 * @param discardReason
	 * @return
	 * @throws Exception
	 */
	public Response acceptOrRejectInterview(String interviewId, String interviewerId, String headerKey, String headerValue, boolean acceptOrRejctInterview, String... discardReason)
			throws Exception {
		RequestSpecification rs = RestAssured.with().header(headerKey, headerValue).with()
				.contentType(ContentType.JSON).and().body("{}");
		rs.queryParam(INTERVIEW_id, interviewId);
		rs.queryParam(INTERVIEWER_id, interviewerId);
		rs.queryParam(ACCEPTED, acceptOrRejctInterview);
		if(discardReason.length>0) {
			rs.queryParam(DISCARD_REASON, discardReason[0]);
		}
		String url = AcceptInterviewTest.baseUrl + AcceptOrRejectInterviewEndpoints.acceptOrRejectInterview;
		String actionForInterviewRequest = acceptOrRejctInterview?"accepting":"rejecting";
		ExtentHelper.test.log(Status.INFO, "Request URL for "+actionForInterviewRequest+" an interview request : " + url);
		Response response = restUtils.executePUT(rs, url);
		ExtentHelper.test.log(Status.INFO, "Put method is executed successfully for "+actionForInterviewRequest+" an interview request");
		return response;
	}
	
	/**
	 * This Method is used to get response for accepting or rejecting an interview request with invalid URL
	 * @param interviewId
	 * @param interviewerId
	 * @param headerKey
	 * @param headerValue
	 * @param acceptOrRejctInterview
	 * @return
	 * @throws Exception
	 */
	public Response acceptOrRejectInterview_With_InvalidUrl(String interviewId, String interviewerId, String headerKey, String headerValue, boolean acceptOrRejctInterview)
			throws Exception {
		RequestSpecification rs = RestAssured.with().header(headerKey, headerValue).with()
				.contentType(ContentType.JSON).and().body("{}");
		rs.queryParam(INTERVIEW_id, interviewId);
		rs.queryParam(INTERVIEWER_id, interviewerId);
		rs.queryParam(ACCEPTED, acceptOrRejctInterview);
		String url = AcceptInterviewTest.baseUrl + AcceptOrRejectInterviewEndpoints.acceptOrRejectInterview.replace("acceptance", "acceptace");
		String actionForInterviewRequest = acceptOrRejctInterview?"accepting":"rejecting";
		ExtentHelper.test.log(Status.INFO, "Request URL for "+actionForInterviewRequest+" an interview request : " + url);
		Response response = restUtils.executePUT(rs, url);
		ExtentHelper.test.log(Status.INFO, "Put method is executed successfully for "+actionForInterviewRequest+" an interview request with invalid URL");
		return response;
	}
	
	/**
	 * This Method is used to get response for accepting or rejecting an interview request with invalid request method
	 * @param interviewId
	 * @param interviewerId
	 * @param headerKey
	 * @param headerValue
	 * @param acceptOrRejctInterview
	 * @return
	 * @throws Exception
	 */
	public Response acceptOrRejectInterview_With_InvalidRequestMethod(String interviewId, String interviewerId, String headerKey, String headerValue, boolean acceptOrRejctInterview)
			throws Exception {
		RequestSpecification rs = RestAssured.with().header(headerKey, headerValue).with()
				.contentType(ContentType.JSON).and().body("{}");
		rs.queryParam(INTERVIEW_id, interviewId);
		rs.queryParam(INTERVIEWER_id, interviewerId);
		rs.queryParam(ACCEPTED, acceptOrRejctInterview);
		String url = AcceptInterviewTest.baseUrl + AcceptOrRejectInterviewEndpoints.acceptOrRejectInterview;
		String actionForInterviewRequest = acceptOrRejctInterview?"accepting":"rejecting";
		ExtentHelper.test.log(Status.INFO, "Request URL for "+actionForInterviewRequest+" an interview request : " + url);
		Response response = restUtils.executePOST(rs, url);
		ExtentHelper.test.log(Status.INFO, "POST method (which is invalid) is executed successfully for "+actionForInterviewRequest+" an interview request");
		return response;
	}

}
