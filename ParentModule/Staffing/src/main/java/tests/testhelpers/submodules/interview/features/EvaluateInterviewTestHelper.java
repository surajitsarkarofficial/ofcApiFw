package tests.testhelpers.submodules.interview.features;

import com.aventstack.extentreports.Status;

import endpoints.submodules.interview.features.EvaluateInterviewEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.interview.features.EvaluateInterviewPayloadHelper;
import tests.testcases.submodules.interview.features.CreateInterviewTest;
import tests.testhelpers.submodules.interview.InterviewTestHelper;
import utils.ExtentHelper;

/**
 * @author deepakkumar.hadiya
 */

public class EvaluateInterviewTestHelper extends InterviewTestHelper{

	/**
	 * To evaluate accepted interview request
	 * @param interviewId
	 * @return {@link Response}
	 * @throws Exception
	 */
	public Response evaluateInterview(String interviewId) throws Exception {
		EvaluateInterviewPayloadHelper evaluateInterviewPayloadHelper=new EvaluateInterviewPayloadHelper();
		String requestPayLoad = evaluateInterviewPayloadHelper.evaluateInterviewPayload(interviewId);
		RequestSpecification requestSpecification = RestAssured.with().header(TOKEN,TOKEN_VALUE).and().contentType(ContentType.JSON).body(requestPayLoad);
		String url = CreateInterviewTest.baseUrl+EvaluateInterviewEndpoints.evaluateInterview;
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executePOST(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "Post method for evaluating interview request is executed successfully");
		return response;
	}
}
