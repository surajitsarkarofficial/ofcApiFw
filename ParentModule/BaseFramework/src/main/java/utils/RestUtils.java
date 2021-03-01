package utils;

import java.io.PrintStream;
import java.io.StringWriter;

import org.apache.commons.io.output.WriterOutputStream;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.jayway.jsonpath.JsonPath;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestUtils {

	private StringWriter requestWriter;
	private StringWriter errorWriter;
	private StringWriter responseWriter;
	private PrintStream requestCapture, errorCapture, responseCapture;
	private Response response = null;
	private RequestSpecification requestSpec;
	int requestTimeOut = 15;

	/**
	 * This method will initialise the variables to capture the logs for request and
	 * set timeout.
	 */
	private void initRequestProcessing() {

		RestAssuredConfig config = configureRequestTimeout(requestTimeOut);
		requestSpec.config(config);
		requestWriter = new StringWriter();
		requestCapture = new PrintStream(new WriterOutputStream(requestWriter), true);
		responseWriter = new StringWriter();
		responseCapture = new PrintStream(new WriterOutputStream(responseWriter), true);
		errorWriter = new StringWriter();
		errorCapture = new PrintStream(new WriterOutputStream(errorWriter), true);
		requestSpec.filter(new ErrorLoggingFilter(errorCapture)).filter(new RequestLoggingFilter(requestCapture))
				.filter(new ResponseLoggingFilter(responseCapture));

	}

	/**
	 * This method will log the details if an error has occurred while executing the
	 * request
	 */
	private void logDetails() {
		ExtentTest extest = ExtentHelper.getExtentTest();
		extest.log(Status.INFO,
				MarkupHelper.createCodeBlock("Request Details are as follows -\n" + requestWriter.toString()));
		if (!errorWriter.toString().isEmpty()) {
			extest.log(Status.INFO,
					MarkupHelper.createCodeBlock("Response Details are as follows -\n" + errorWriter.toString()));

		} else {
			extest.log(Status.INFO,
					MarkupHelper.createCodeBlock("Response Details are as follows -\n" + responseWriter.toString()));
		}

	}

	/**
	 * This method with execute the GET request and return the response.
	 * 
	 * @param url
	 * @return
	 */
	public Response executeGET(String url) {
		requestSpec = RestAssured.with();
		initRequestProcessing();
		response = requestSpec.get(url);
		logDetails();
		return response;
	}

	/**
	 * This method with execute the GET request and return the response.
	 * 
	 * @param rs
	 * @param url
	 * @return
	 */
	public Response executeGET(RequestSpecification rs, String url) {
		requestSpec = rs;
		initRequestProcessing();
		response = requestSpec.get(url);
		logDetails();
		return response;
	}

	/**
	 * This method will set the Request Timeout
	 * 
	 * @param timeOutInSeconds
	 * @return
	 */
	public RestAssuredConfig configureRequestTimeout(int timeOutInSeconds) {
		int timeout = timeOutInSeconds * 1000;
		RestAssuredConfig config = RestAssured.config()
				.httpClient(HttpClientConfig.httpClientConfig().setParam("CONNECTION_MANAGER_TIMEOUT", timeout));
		return config;
	}

	/**
	 * This method will execute the POST request and return the response.
	 * 
	 * @param rs
	 * @param url
	 * @return
	 */
	public Response executePOST(RequestSpecification rs, String url) {
		requestSpec = rs;
		initRequestProcessing();
		response = requestSpec.post(url);
		logDetails();
		return response;
	}

	/**
	 * This method will execute the PUT request and return the response.
	 * 
	 * @param rs
	 * @param url
	 * @return
	 */
	public Response executePUT(RequestSpecification rs, String url) {
		requestSpec = rs;
		initRequestProcessing();
		response = requestSpec.put(url);
		logDetails();
		return response;
	}

	/**
	 * This method will execute the DELETE request and return the response.
	 * 
	 * @param rs
	 * @param url
	 * @return
	 */
	public Response executeDELETE(RequestSpecification rs, String url) {
		requestSpec = rs;
		initRequestProcessing();
		response = requestSpec.delete(url);
		logDetails();
		return response;
	}

	/**
	 * This method will fetch the value of the specified key from the response
	 * object
	 * 
	 * @param response
	 * @param key
	 * @return Object
	 * @throws Exception
	 */
	public Object getValueFromResponse(Response response, String key) throws Exception {
		ExtentTest extest = ExtentHelper.getExtentTest();
		Object value = null;
		if (response.asString() == null || response.asString().equals("")) {
			extest.log(Status.FAIL, MarkupHelper.createCodeBlock(
					String.format("Response string is NULL. Hence unable to fetch the value for key %s", key)));
			throw new Exception("Response String was null");
		}
		try {

			value = JsonPath.read(response.asString(), key);
		} catch (Exception e) {
			extest.log(Status.FAIL, MarkupHelper.createCodeBlock(
					String.format("Unable to fetch '%s' key from the response - \n%s", key, response.prettyPrint())));
			throw e;
		}
		if (value == null) {
			extest.log(Status.FAIL, MarkupHelper.createCodeBlock(String
					.format("'%s' key returned Null value from the response. \n%s", key, response.prettyPrint())));
			throw new Exception(key + " key returned Null value from the response");
		}
		return value;
	}
	
}
