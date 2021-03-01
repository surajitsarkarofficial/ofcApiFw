package tests.testhelpers.myTeam.features;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.testng.SkipException;

import com.aventstack.extentreports.Status;

import database.myTeam.MyTeamDBHelper;
import dto.myTeam.features.FilterObject;
import endpoints.GlowEndpoints;
import endpoints.myTeam.MyTeamEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.GlowBaseTest;
import tests.testcases.myTeam.MyTeamBaseTest;
import tests.testhelpers.myTeam.MyTeamTestHelper;
import utils.ExtentHelper;
import utils.RestUtils;

/**
 * @author dnyaneshwar.waghmare
 *
 */

public class MyTeamProjectHelper extends MyTeamTestHelper {

	String startDate = null;
	String endDate = null;
	int projectId;
	int globerId;
	RequestSpecification requestSpecification;
	RestUtils restUtils = new RestUtils();
	MyTeamDBHelper myTeamDBHelper;
	List<Integer> projectIds;

	public MyTeamProjectHelper(String userName) throws Exception {
		super(userName);
		myTeamDBHelper = new MyTeamDBHelper();
	}

	/**
	 * To get Project States List For User
	 * 
	 * @param userId
	 * @return Response
	 * @throws Exception
	 */
	public Response getProjectStatesListForUser(String userId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl + String.format(MyTeamEndpoints.getProjectStateForUser, "MY_VIEW", userId);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch Studio list results", true);
		ExtentHelper.test.log(Status.INFO, "get Studio List method executed successfully");
		return response;
	}

	/**
	 * To get Project States List For Invalid User
	 * 
	 * @param userId
	 * @return Response
	 * @throws Exception
	 */
	public Response getProjectStatesListForInvalidUser(String userId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl + String.format(MyTeamEndpoints.getProjectStateForUser, "MY_VIEW", userId);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "get Studio List method executed successfully");
		return response;
	}

	/**
	 * To get Projects For Glober
	 * 
	 * @param userId
	 * @return Response
	 * @throws Exception
	 */
	public Response getProjectsForGlober(String userId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl + String.format(MyTeamEndpoints.getProjectsForGlober, userId, "projects");
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch GloberDetails list results",
				true);
		ExtentHelper.test.log(Status.INFO, "get List Of Glober Details method executed successfully");
		return response;
	}

	/**
	 * To getClient members under DD
	 * 
	 * @param userId
	 * @return Response
	 * @throws Exception
	 */
	public Response getProjectDetailsAsPerFilter(String userId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl
				+ String.format(MyTeamEndpoints.getProjectDetailsAsPerFilter, "1", "20", userId, "ON_GOING");
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200,
				"Unable to fetch Pods for Glober list results", true);
		ExtentHelper.test.log(Status.INFO, "get Pods For Glober method executed successfully");
		return response;
	}

	/***
	 * To get Project Data As Per Filter From DB
	 * 
	 * @param globerName
	 * @return List<FilterObject>
	 * @throws SQLException
	 */
	public List<FilterObject> getProjectDataAsPerFilter(String globerName) throws SQLException {
		List<FilterObject> DBPODListForProject = myTeamDBHelper.getProjectDataAsPerFilter(globerName);
		return DBPODListForProject;
	}

	/***
	 * To get Project States List For Glober From DB
	 * 
	 * @param userId
	 * @return Map<String, List<String>>
	 * @throws SQLException
	 */
	public Map<String, List<String>> getProjectStatesListForGlober(String userId) throws SQLException {
		Map<String, List<String>> DBProjectStatesListForGlober = myTeamDBHelper.getProjectStatesListForGlober(userId);
		return DBProjectStatesListForGlober;
	}
	
	/**
	 * To get Projects list under PM
	 * 
	 * @param userId as String
	 * @return Response
	 * @throws Exception
	 */
	public Response getProjectListUnderPM(String userId) throws Exception {
		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl + String.format(MyTeamEndpoints.getProjectsForUser, "1", "20", userId, "ON_GOING");
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200,
				"Unable to fetch Projects for Glober", true);
		ExtentHelper.test.log(Status.INFO, "get Projects For User method executed successfully");
		return response;

	}
	
	/**
	 * To get Project Members For User
	 * 
	 * @param userId as String
	 * @return Response
	 * @throws Exception
	 */
	public Response getProjectMembersForUser(String userId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl
				+ String.format(MyTeamEndpoints.getProjectMembersForUser, userId, true,true,"projects");
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200,
				"Unable to fetch Project Members for Glober", true);
		ExtentHelper.test.log(Status.INFO, "get Project Members For User method executed successfully");
		return response;
	}
	
	/**
	 * To get User Filter For Project
	 * 
	 * @param userId as String
	 * @return Response
	 * @throws Exception
	 */
	public Response getUserFilterForProject(String userId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl
				+ String.format(MyTeamEndpoints.getUserFilterForProject, userId,"viewProjectList");
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 204,
				"Response has some content", true);
		ExtentHelper.test.log(Status.INFO, "get User Filter For Project method executed successfully");
		return response;
	}
	
	
	/**
	 * To get User Filter
	 * 
	 * @param userId as String
	 * @return Response
	 * @throws Exception
	 */
	public Response getUserFilter(String userId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl
				+ String.format(MyTeamEndpoints.getUserFilter, userId,"ON_GOING");
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200,
				"Unable to fetch User Filter data", true);
		ExtentHelper.test.log(Status.INFO, "get User Filter method executed successfully");
		return response;
	}
	
	/**
	 * @author pooja.kakade
	 *  
	 * This method will fetch the Project Ids Of the specified glober.
	 * 
	 * @param globerName
	 * @param globerId
	 * @return List of projectIds
	 * @throws Exception
	 */
	public List<Integer> getProjectIdsOfGlober(String globerName, String globerId) throws Exception {
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

		return projectIds;
	}
	
	/**
	 * To get Project Status Filter For User
	 * 
	 * @param userId
	 * @return Response
	 * @throws Exception
	 */
	public Response getProjectStatusFilter(String userId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl + String.format(MyTeamEndpoints.getProjectStatusFilter, userId);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch Project Status Filter results", true);
		ExtentHelper.test.log(Status.INFO, "get Project Status Filter method executed successfully");
		return response;
	}
	
	/***
	 * This method will get the Project States from DB
	 * 
	 * @return Map<String, List<String>>
	 * @throws SQLException
	 */
	public Map<String, List<String>> getProjectStates() throws SQLException {
		Map<String, List<String>> projectStatesFromBD = myTeamDBHelper.getProjectStates();
		return projectStatesFromBD;
	}
}
