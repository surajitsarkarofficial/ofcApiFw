package tests;

import java.util.List;
import java.util.Random;

import org.testng.SkipException;

import com.aventstack.extentreports.Status;

import endpoints.GlowEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.ExtentHelper;
import utils.RestUtils;

public class GlowBaseTestHelper{

	static List<Integer> projectIds;
	static int projectId;

	/**
	 * This method will fetch the Project Id Of the specified glober.
	 * 
	 * @param globerName
	 * @param globerId
	 * @return projectId
	 * @throws Exception
	 */
	public int getProjectIdOfGlober(String globerName, String globerId) throws Exception {
		GlowBaseTest.sessionId = new GlowBaseTest().createSession(globerName);
		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId);
		String url = GlowBaseTest.baseUrl + GlowEndpoints.globerDetails + globerId;
		Response response = restUtils.executeGET(requestSpecification, url);
		new GlowBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch Glober Details.", true);
		GlowBaseTest.test.log(Status.INFO, "Glober details fetched successfully.");
		projectIds = response.jsonPath().get("projectIds");
		if (projectIds.size() == 0) {
			throw new SkipException(
					"THERE IS NO ANY PROJECT ASSIGNED FOR THIS GLOBER '" + globerId + "' TO GET POD DETAILS ");
		}

		projectId = projectIds.get(new Random().nextInt(projectIds.size()));

		if (projectId == 1000) {
			projectId = projectIds.get(new Random().nextInt(projectIds.size()));
		}
		ExtentHelper.test.log(Status.INFO, "ProjectId for which we are working is : "+projectId);
		return projectId;

	}
	

	/**
	 * This method will validate the response and Skip test if validate failed.
	 * 
	 * @param response
	 * @param statusCode
	 * @throws Exception
	 * @author deepakkumar.hadiya
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
	
	
}
