package tests;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.aventstack.extentreports.Status;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import listeners.PdfReportListener;
import properties.GlowProperties;
import utils.ExtentHelper;
import utils.RestUtils;

@Listeners({ PdfReportListener.class })
public class GlowBaseTest extends ExtentHelper {
	public static String sessionId;
	public static List<String> sessions = new LinkedList<String>();
	public static String baseUrl;
	public static String executionEnvironment;

	@BeforeSuite(alwaysRun = true)
	public void beforeSuiteSetup() {
		executionEnvironment = GlowProperties.setEnvironment();
		extent = ExtentHelper.configureExtentReport(executionEnvironment);
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(Method method, Object[] testData) {
		baseUrl = GlowProperties.baseURL;
		test = featureTest.createNode(createTestName(method, testData));
		test.log(Status.INFO, "Inside Method - " + method.getName());
		Reporter.log("Executing Method - " + method.getName(), true);
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult result) throws IOException {
		Reporter.log("Executed Method - " + result.getName(), true);
		Reporter.log(RandomStringUtils.random(130, "-"), true);
		if (result.getStatus() == ITestResult.FAILURE) {
			test.fail(result.getThrowable().getMessage());
		} else if (result.getStatus() == ITestResult.SKIP)
			test.skip(result.getThrowable());
		else
			test.pass(result.getName() + " Test passed");
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		closeSessions();
		extent.flush();
	}

	/**
	 * This method will create the Test name by appending the data provider data at
	 * runtime.
	 * 
	 * @param method
	 * @param testData
	 * @return
	 */
	public String createTestName(Method method, Object[] testData) {
		StringBuffer testName = new StringBuffer("<b>" + method.getName() + "</b>");
		if (testData.length > 0) {
			testName.append("<br><br>(");
			for (int i = 0; i < testData.length; i++) {
				if (i == 0) {
					testName.append(testData[i]);
				} else {
					testName.append("," + testData[i]);
				}
			}
			testName.append(")");
		}
		return testName.toString();

	}

	/**
	 * This method will validate the response and Skip test if validate failed.
	 * 
	 * @param response
	 * @param statusCode
	 * @throws Exception
	 */
	public void validateResponseToContinueTest(Response response, int statusCode, String message, boolean isFail)
			throws Exception {
		if (response.getStatusCode() != statusCode) {
			String failedValueMsg = "Expected Status Code was " + statusCode + " and Actual Status code was "
					+ response.getStatusCode();

			if (isFail) {
				throw new Exception(failedValueMsg + "\n" + message + "\n" + response.prettyPrint());
			} else {
				throw new SkipException(failedValueMsg + "\n" + message + "\n" + response.prettyPrint());
			}

		}
	}

	/**
	 * This method will create the session and return the session id
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String createSession(String userName) throws Exception {
		String endPointURL = baseUrl + endpoints.GlowEndpoints.fakeLogin + userName;
		Response response = new RestUtils().executeGET(endPointURL);
		validateResponseToContinueTest(response, 200, "Unable to create session", false);
		sessionId = response.getSessionId();
		sessions.add(sessionId);
		return sessionId;
	}

	/**
	 * This method will close all sessions that were created in this suite
	 * execution.
	 */
	public void closeSessions() {
		RestUtils restUtils = new RestUtils();
		String url = GlowBaseTest.baseUrl + endpoints.GlowEndpoints.logout;

		for (String session : sessions) {
			RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(session);
			Response response = restUtils.executeGET(requestSpecification, url);
			try {
				new GlowBaseTest().validateResponseToContinueTest(response, 200,
						"Unable to perform the logout for the session: " + session, true);
			} catch (Exception e) {
				test.log(Status.INFO, e.getMessage());
			}
		}
	}
}
