package tests.testhelpers.myTeam.features;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.aventstack.extentreports.Status;

import database.myTeam.MyTeamDBHelper;
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

public class MyTeamStudioHelper extends MyTeamTestHelper {

	String startDate = null;
	String endDate = null;
	int projectId;
	int globerId;
	RequestSpecification requestSpecification;
	RestUtils restUtils = new RestUtils();
	MyTeamDBHelper myTeamDBHelper;
	List<Integer> projectIds;

	public MyTeamStudioHelper(String userName) throws Exception {
		super(userName);
		myTeamDBHelper = new MyTeamDBHelper();
	}

	/**
	 * To get Studio List For User
	 * 
	 * @param userId
	 * @return Response
	 * @throws Exception
	 */
	public Response getStudioListForUser(String userId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl
				+ String.format(MyTeamEndpoints.getStudioListForUser, "MY_VIEW", "ON_GOING", userId);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch Studio list results", true);
		ExtentHelper.test.log(Status.INFO, "get Studio List method executed successfully");
		return response;

	}

	/**
	 * To get Studio List For Invalid User
	 * 
	 * @param userId
	 * @return Response
	 * @throws Exception
	 */
	public Response getStudioListForInvalidUser(String userId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl
				+ String.format(MyTeamEndpoints.getStudioListForUser, "MY_VIEW", "ON_GOING", userId);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "get Studio List method executed successfully");
		return response;
	}

	/***
	 * To get the Studios List For Glober From DB
	 * 
	 * @param userId
	 * @return Map<String, List<String>>
	 * @throws SQLException
	 */
	public Map<String, List<String>> getStudiosListForGlober(String userId) throws SQLException {
		Map<String, List<String>> DBStudioListForDD = myTeamDBHelper.getStudiosListForGlober(userId);
		return DBStudioListForDD;
	}

}
