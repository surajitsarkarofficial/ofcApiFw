package tests.testhelpers.submodules.interview.features;

import java.util.Map;

import com.aventstack.extentreports.Status;

import endpoints.submodules.interview.features.KillInterviewProcessEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.submodules.interview.features.CreateInterviewTest;
import tests.testhelpers.submodules.interview.InterviewTestHelper;
import utils.ExtentHelper;

/**
 * @author deepakkumar.hadiya
 */

public class KillInterviewProcessTestHelper extends InterviewTestHelper{

	/**
	 * This Method is used to kill interview process
	 * @param data
	 * @param interviewId
	 * @param cancelInterview
	 * @param performNotification
	 * @return {@link Response}
	 * @throws Exception
	 */
	public Response killInterviewProcess(Map<Object, Object> data,String interviewId, Object cancelInterview, Object performNotification) throws Exception {
		String headerKey=data.get("headerKey").toString();
		String headerValue=data.get("headerValue").toString();
		String body="{\""+DISCARD_REASON+"\" : \""+data.get(DISCARD_REASON).toString()+"\"}";
		RequestSpecification requestSpecification = RestAssured.with().header(headerKey,headerValue).and().contentType(ContentType.JSON).body(body);
		requestSpecification.queryParam(CANCEL_INTERVIEW, cancelInterview);
		requestSpecification.queryParam(PERFORM_NOTIFICATION, performNotification);
		String url = CreateInterviewTest.baseUrl+KillInterviewProcessEndpoints.killInterviewProcess+interviewId;
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executePUT(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "Put method for killing interview process is executed successfully");
		return response;
	}
	
	/**
	 * This Method is used to kill interview process with blank body
	 * @param data
	 * @param interviewId
	 * @param cancelInterview
	 * @param performNotification
	 * @return {@link Response}
	 * @throws Exception
	 */
	 
	public Response killInterviewProcessWithBlankBody(Map<Object, Object> data,String interviewId, boolean cancelInterview, boolean performNotification) throws Exception {
		String headerKey=data.get("headerKey").toString();
		String headerValue=data.get("headerValue").toString();
		String body="{}";
		RequestSpecification requestSpecification = RestAssured.with().header(headerKey,headerValue).and().contentType(ContentType.JSON).body(body);
		requestSpecification.queryParam(CANCEL_INTERVIEW, cancelInterview);
		requestSpecification.queryParam(PERFORM_NOTIFICATION, performNotification);
		String url = CreateInterviewTest.baseUrl+KillInterviewProcessEndpoints.killInterviewProcess+interviewId;
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executePUT(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "Put method for killing interview process is executed successfully");
		return response;
	}
	
	/**
	 * This Method is used to kill interview process with invalid parameter value
	 * @param data
	 * @param interviewId
	 * @param cancelInterview
	 * @param performNotification
	 * @return {@link Response}
	 * @throws Exception
	 */
	public Response killInterviewProcessWithBlankBody(Map<Object, Object> data,String interviewId, Object cancelInterview, Object performNotification) throws Exception {
		String headerKey=data.get("headerKey").toString();
		String headerValue=data.get("headerValue").toString();
		String body="{\""+DISCARD_REASON+"\" : \""+data.get(DISCARD_REASON).toString()+"\"}";
		RequestSpecification requestSpecification = RestAssured.with().header(headerKey,headerValue).and().contentType(ContentType.JSON).body(body);
		requestSpecification.queryParam(CANCEL_INTERVIEW, cancelInterview);
		requestSpecification.queryParam(PERFORM_NOTIFICATION, performNotification);
		String url = CreateInterviewTest.baseUrl+KillInterviewProcessEndpoints.killInterviewProcess+interviewId;
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executePUT(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "Put method for killing interview process is executed successfully");
		return response;
	}
	
	/**
	 * This Method is used to kill interview process with invalid parameter value
	 * @param data
	 * @param interviewId
	 * @return {@link Response}
	 * @throws Exception
	 */
	public Response killInterviewProcessForInvalidParameterValue(Map<Object, Object> data,String interviewId) throws Exception {
		String headerKey=data.get("headerKey").toString();
		String headerValue=data.get("headerValue").toString();
		String body="{\""+DISCARD_REASON+"\" : \""+data.get(DISCARD_REASON).toString()+"\"}";
		RequestSpecification requestSpecification = RestAssured.with().header(headerKey,headerValue).and().contentType(ContentType.JSON).body(body);
		requestSpecification.queryParam(CANCEL_INTERVIEW, data.get(CANCEL_INTERVIEW));
		requestSpecification.queryParam(PERFORM_NOTIFICATION, data.get(PERFORM_NOTIFICATION));
		String url = CreateInterviewTest.baseUrl+KillInterviewProcessEndpoints.killInterviewProcess+interviewId;
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executePUT(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "Put method for killing interview process is executed successfully");
		return response;
	}
	
	/**
	 * This Method is used to kill interview process with invalid url
	 * @param data
	 * @param interviewId
	 * @return {@link Response}
	 * @throws Exception
	 */
	public Response killInterviewProcessForInvalidUrl(Map<Object, Object> data,String interviewId) throws Exception {
		String headerKey=data.get("headerKey").toString();
		String headerValue=data.get("headerValue").toString();
		String body="{\""+DISCARD_REASON+"\" : \""+data.get(DISCARD_REASON).toString()+"\"}";
		RequestSpecification requestSpecification = RestAssured.with().header(headerKey,headerValue).and().contentType(ContentType.JSON).body(body);
		requestSpecification.queryParam(CANCEL_INTERVIEW, data.get(CANCEL_INTERVIEW));
		requestSpecification.queryParam(PERFORM_NOTIFICATION, data.get(PERFORM_NOTIFICATION));
		String url = CreateInterviewTest.baseUrl+KillInterviewProcessEndpoints.killInterviewProcess.replace("technicalInterviews", "technicalInterview")+interviewId;
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executePUT(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "Put method for killing interview process is executed successfully");
		return response;
	}

}
