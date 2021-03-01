package tests.testhelpers.myTeam.features;

import java.sql.SQLException;
import java.util.List;

import com.aventstack.extentreports.Status;

import database.myTeam.MyTeamDBHelper;
import dto.myTeam.features.FilterObject;
import endpoints.myTeam.MyTeamEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.myTeam.MyTeamBaseTest;
import tests.testhelpers.myTeam.MyTeamTestHelper;
import utils.ExtentHelper;
import utils.RestUtils;

/**
 * @author dnyaneshwar.waghmare
 *
 */

public class MyTeamProjectMembersHelper extends MyTeamTestHelper {

	String startDate = null;
	String endDate = null;
	int projectId;
	int globerId;
	RequestSpecification requestSpecification;
	RestUtils restUtils = new RestUtils();
	MyTeamDBHelper myTeamDBHelper;
	List<Integer> projectIds;

	public MyTeamProjectMembersHelper(String userName) throws Exception {
		super(userName);
		myTeamDBHelper = new MyTeamDBHelper();
	}

	/**
	 * To get Project Members List For Project
	 * 
	 * @param userId
	 * @return Response
	 * @throws Exception
	 */
	public Response getProjectMembersListForProject(int projectId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl
				+ String.format(MyTeamEndpoints.getProjectMembersForProject, projectId, "Projects");
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200,
				"Unable to fetch Project Member list results", true);
		ExtentHelper.test.log(Status.INFO, "get POD List For Project method executed successfully");
		return response;
	}

	/**
	 * To get Project Members For POD
	 * 
	 * @param userId
	 * @return Response
	 * @throws Exception
	 */
	public Response getProjectMembersForPOD(int podId, int projectId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl + String.format(MyTeamEndpoints.getProjectMembersForPOD, podId, projectId);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200,
				"Unable to fetch Project Member list results for POD", true);
		ExtentHelper.test.log(Status.INFO, "get List Of Glober Details method executed successfully");
		return response;
	}

	/**
	 * To getClient members under DD
	 * 
	 * @param projectId
	 * @return Response
	 * @throws Exception
	 */
	public Response getProjectMembersForProjectWithPagination(int projectId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl + String.format(MyTeamEndpoints.getProjectMembersForProjectWithPagination,
				projectId, true, true, "projects", "1", "20");
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch GloberDetails list results",
				true);
		ExtentHelper.test.log(Status.INFO, "get List Of Glober Details method executed successfully");
		return response;
	}

	/***
	 * To get the Project Member Details from DB
	 * 
	 * @param projectId
	 * @return List<FilterObject>
	 * @throws SQLException
	 */
	public List<FilterObject> getProjectMemberDetails(int projectId) throws SQLException {
		List<FilterObject> DBPODListForProject = myTeamDBHelper.getProjectMemberDetails(projectId);
		return DBPODListForProject;
	}

	/***
	 * To get the Project Members Details For POD from DB
	 * 
	 * @param projectId
	 * @return List<FilterObject>
	 * @throws SQLException
	 */
	public List<FilterObject> getProjectMembersDetailsForPOD(int projectId) throws SQLException {
		List<FilterObject> DBPODListForProject = myTeamDBHelper.getProjectMembersDetailsForPOD(projectId);
		return DBPODListForProject;
	}

	/***
	 * To get the Project Member Details from DB
	 * 
	 * @param projectId
	 * @return List<FilterObject>
	 * @throws SQLException
	 */
	public List<FilterObject> getProjectMembersDetails(int projectId) throws SQLException {
		List<FilterObject> DBPODListForProject = myTeamDBHelper.getProjectMembersDetails(projectId);
		return DBPODListForProject;
	}

}
