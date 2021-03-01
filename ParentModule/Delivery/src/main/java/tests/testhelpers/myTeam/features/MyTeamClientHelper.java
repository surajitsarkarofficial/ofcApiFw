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

public class MyTeamClientHelper extends MyTeamTestHelper {

	String startDate = null;
	String endDate = null;
	int projectId;
	int globerId;
	RequestSpecification requestSpecification;
	RestUtils restUtils = new RestUtils();
	MyTeamDBHelper myTeamDBHelper;
	List<Integer> projectIds;

	public MyTeamClientHelper(String userName) throws Exception {
		super(userName);
		myTeamDBHelper = new MyTeamDBHelper();
	}

	/**
	 * To getClient members under DD
	 * 
	 * @param userId
	 * @return Response
	 * @throws Exception
	 */
	public Response getClientListForUser(String userId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl
				+ String.format(MyTeamEndpoints.getClientListForUser, "ON_GOING", "MY_VIEW", userId);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch Clients list results",
				true);
		ExtentHelper.test.log(Status.INFO, "get Client List method executed successfully");
		return response;

	}

	/***
	 * To get Client Details For Glober From DB
	 * 
	 * @param userId
	 * @return Map<String, List<String>>
	 * @throws SQLException
	 */
	public Map<String, List<String>> getClientDetailsForGlober(String userId) throws SQLException {
		Map<String, List<String>> DBclientListForDD = myTeamDBHelper.getClientDetailsForGlober(userId);
		return DBclientListForDD;
	}

}
