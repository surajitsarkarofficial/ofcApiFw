package tests.testhelpers.submodules.interview.features;

import com.aventstack.extentreports.Status;

import endpoints.submodules.interview.features.AcceptOrRejectInterviewEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.submodules.interview.InterviewBaseTest;
import tests.testhelpers.submodules.interview.InterviewTestHelper;
import utils.ExtentHelper;

/**
 * 
 * @author deepakkumar.hadiya
 *
 */

public class InterviewRequestsOfGatekeeperTestHelper extends InterviewTestHelper{

	/**
	 * Get list of all interview requests of specified gatekeeper
	 * @param interviewerId
	 * @param headerKey
	 * @param headerValue
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Response interviewRequestsForGk(String headerKey, String headerValue, String interviewerId) {
		RequestSpecification requestSpecification = RestAssured.with().header(headerKey, headerValue).and().queryParam(INTERVIEWER_ID, interviewerId);
		String url = InterviewBaseTest.baseUrl+AcceptOrRejectInterviewEndpoints.interviewRequests;
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "Get interview requests for gatekeeper method is executed successfully");
		return response;
	}
	
	/**
	 * Get list of all interview requests of specified gatekeeper
	 * @param interviewerId
	 * @param headerKey
	 * @param headerValue
	 * @return {@link Response}
	 */
	public Response interviewRequestsForGkByPageNo_PageSizeAndCandidateName(String headerKey, String headerValue, String interviewerId, int pageSize, int pageNumber, String... candidateName) {
		RequestSpecification requestSpecification = RestAssured.with().header(headerKey, headerValue);
		requestSpecification=candidateName.length>0?requestSpecification.queryParam("key", candidateName[0]):requestSpecification;
		requestSpecification.queryParam(INTERVIEWER_ID, interviewerId);
		requestSpecification.queryParam(PAGE_SIZE, pageSize);
		requestSpecification.queryParam(PAGE_NUM, pageNumber);
		String url = InterviewBaseTest.baseUrl+AcceptOrRejectInterviewEndpoints.interviewRequests;
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "Get interview requests for gatekeeper method is executed successfully");
		return response;
	}
}
