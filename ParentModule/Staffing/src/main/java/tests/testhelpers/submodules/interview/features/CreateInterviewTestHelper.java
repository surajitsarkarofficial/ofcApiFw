package tests.testhelpers.submodules.interview.features;

import java.util.Map;

import com.aventstack.extentreports.Status;

import dataproviders.submodules.interview.features.CreateInterviewDataProvider;
import endpoints.submodules.interview.features.CreateInterviewEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.interview.features.CreateInterviewPayloadHelper;
import tests.testcases.submodules.interview.features.CreateInterviewTest;
import tests.testhelpers.submodules.interview.InterviewTestHelper;
import utils.ExtentHelper;

/**
 * @author deepakkumar.hadiya
 */

public class CreateInterviewTestHelper extends InterviewTestHelper{

	/**
	 * This Method is used to get response from create interview request
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public Response createInterview(Map<Object, Object> data) throws Exception {
		CreateInterviewPayloadHelper createNewInterviewPayloadHelper=new CreateInterviewPayloadHelper();
		String headerKey=data.get("headerKey").toString();
		String headerValue=data.get("headerValue").toString();
		String requestPayLoad = createNewInterviewPayloadHelper.getCreateInterviewPayload(data);
		RequestSpecification requestSpecification = RestAssured.with().header(headerKey,headerValue).and().contentType(ContentType.JSON).body(requestPayLoad);
		String url = CreateInterviewTest.baseUrl+CreateInterviewEndpoints.createInterview;
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executePOST(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "Post method for create interview is executed successfully");
		return response;
	}
	
	/**
	 * This Method is used to get response from create interview request with invalid URL
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public Response createInterview_With_InvalidUrl(Map<Object, Object> data) throws Exception {
		CreateInterviewPayloadHelper createNewInterviewPayloadHelper=new CreateInterviewPayloadHelper();
		String headerKey=data.get("headerKey").toString();
		String headerValue=data.get("headerValue").toString();
		String requestPayLoad = createNewInterviewPayloadHelper.getCreateInterviewPayload(data);
		RequestSpecification requestSpecification = RestAssured.with().header(headerKey,headerValue).and().contentType(ContentType.JSON).body(requestPayLoad);
		String url = CreateInterviewTest.baseUrl+CreateInterviewEndpoints.createInterview.replace("technicalInterviews", "technicalInterview");
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executePOST(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "Post method for create interview with invalid Url is executed successfully");
		return response;
	}
	
	/**
	 * This Method is used to get response from create interview request with invalid request method
	 * @param data
	 * @return
	 * @throws Exception
	 */
	
	public Response createInterview_With_InvalidRequestMethod(Map<Object, Object> data) throws Exception {
		CreateInterviewPayloadHelper createNewInterviewPayloadHelper=new CreateInterviewPayloadHelper();
		String headerKey=data.get("headerKey").toString();
		String headerValue=data.get("headerValue").toString();
		String requestPayLoad = createNewInterviewPayloadHelper.getCreateInterviewPayload(data);
		RequestSpecification requestSpecification = RestAssured.with().header(headerKey,headerValue).and().contentType(ContentType.JSON).body(requestPayLoad);
		String url = CreateInterviewTest.baseUrl+CreateInterviewEndpoints.createInterview;
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "Create interview request with get method is executed successfully");
		return response;
	}
	
	/**
	 * This Method is used to get response from create interview request with invalid key in request body
	 * @param data
	 * @return
	 * @throws Exception
	 */
	
	@SuppressWarnings("unchecked")
	public Response createInterview_With_InvalidParamterKeyInJsonBody(Map<Object, Object> data) throws Exception {
		CreateInterviewPayloadHelper createNewInterviewPayloadHelper=new CreateInterviewPayloadHelper();
		String parameterName=data.get("parameterName").toString();
		String invalidParameterKey=data.get("invalidParameterKey").toString();
		Map<Object,Object> requestBodyData = (Map<Object,Object>)new CreateInterviewDataProvider().getValidTestDataForNegativeScenario()[0][0];
		String headerKey=requestBodyData.get("headerKey").toString();
		String headerValue=requestBodyData.get("headerValue").toString();
		Object parameterValue=requestBodyData.get(parameterName);
		requestBodyData.remove(parameterName);
		requestBodyData.put(invalidParameterKey, parameterValue);
		String requestPayLoad = createNewInterviewPayloadHelper.getCreateInterviewPayload(requestBodyData);
		RequestSpecification requestSpecification = RestAssured.with().header(headerKey,headerValue).and().contentType(ContentType.JSON).body(requestPayLoad);
		String url = CreateInterviewTest.baseUrl+CreateInterviewEndpoints.createInterview;
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executePOST(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "Post method for creating interview using json body with invalid parameter key '"+parameterName+"' is executed successfully");
		return response;
	}
	
	/**
	 * This Method is used to get response from create interview request with invalid parameter value of request body
	 * @param data
	 * @return
	 * @throws Exception
	 */
	
	@SuppressWarnings("unchecked")
	public Response createInterview_With_InvalidValueOfParameterInRequestBody(Map<Object, Object> data) throws Exception {
		CreateInterviewPayloadHelper createNewInterviewPayloadHelper=new CreateInterviewPayloadHelper();
		Object parameterKey=data.get("parameterKey");
		Object parameterValue=data.get("parameterValue");
		Map<Object,Object> requestBodyData = (Map<Object,Object>)new CreateInterviewDataProvider().getValidTestDataForNegativeScenario()[0][0];
		String headerKey=requestBodyData.get("headerKey").toString();
		Object headerValue=requestBodyData.get("headerValue");
		
		requestBodyData.put(parameterKey, parameterValue);
		String requestPayLoad = createNewInterviewPayloadHelper.getCreateInterviewPayload(requestBodyData);
		RequestSpecification requestSpecification = RestAssured.with().header(headerKey,headerValue).and().contentType(ContentType.JSON).body(requestPayLoad);
		String url = CreateInterviewTest.baseUrl+CreateInterviewEndpoints.createInterview;
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executePOST(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "Post method for creating interview using json body with parameter key '"+parameterKey+"' and invalid value '"+parameterValue+"'is executed successfully");
		return response;
	}
}
