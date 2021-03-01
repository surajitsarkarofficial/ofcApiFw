package tests.testhelpers.myTeam.features;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.aventstack.extentreports.Status;

import database.myTeam.features.MyTeamViewTabforDDDBHelper;
import endpoints.myTeam.features.MyTeamViewTabforDDEndpoints;
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

public class DDAccessLevelHelper extends MyTeamTestHelper {

	RequestSpecification requestSpecification;
	RestUtils restUtils = new RestUtils();
	MyTeamViewTabforDDDBHelper myTeamViewTabforDDDBHelper;

	public DDAccessLevelHelper(String userName) throws Exception {
		super(userName);
		myTeamViewTabforDDDBHelper = new MyTeamViewTabforDDDBHelper();
	}

	/**
	 * To get Client members under DD
	 * 
	 * @param userId
	 * @return Response
	 * @throws Exception
	 */
	public Response getClientListUnderDD(String userId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl
				+ String.format(MyTeamViewTabforDDEndpoints.getClientListForDD, userId, "ON_GOING", "MY_VIEW");
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch Clients list results",
				true);
		ExtentHelper.test.log(Status.INFO, "get Client List method executed successfully");
		return response;
	}

	/**
	 * To get Client members under DD
	 * 
	 * @param userId
	 * @return Response
	 * @throws Exception
	 */
	public Response getClientMemberListUnderDD(int clientId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl + String.format(MyTeamViewTabforDDEndpoints.getClientMemberListForDD,
				clientId, "client", true, "POSTION", "ASC", "100", "1", "MY_VIEW");
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch Clients list results",
				true);
		ExtentHelper.test.log(Status.INFO, "get Client List method executed successfully");
		return response;
	}

	/**
	 * To get Projects list under DD
	 * 
	 * @param userId
	 * @return Response
	 * @throws Exception
	 */
	public Response getProjectListUnderDD(String userId) throws Exception {
		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl + String.format(MyTeamViewTabforDDEndpoints.getProjectListForDD, "1", "20",
				userId, "ON_GOING", "MY_VIEW");
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch Project list results",
				true);
		ExtentHelper.test.log(Status.INFO, "get Project List method executed successfully");
		return response;

	}

	/***
	 * Get Active clients Under DD From DB
	 * 
	 * @param userId
	 * @return Map<String, List<String>>
	 * @throws SQLException
	 */
	public Map<String, List<String>> getActiveClientUnderDDFromDB(String userId) throws SQLException {
		Map<String, List<String>> DBclientListForDD = myTeamViewTabforDDDBHelper.getActiveClientsUnderDD(userId);
		return DBclientListForDD;
	}

	/***
	 * This will get Active Client Members Under DD From DB
	 * 
	 * @param userId
	 * @param clientId
	 * @return Map<String, List<String>>
	 * @throws SQLException
	 */
	public Map<String, List<String>> getActiveClientMembersUnderDDFromDB(String userId, int clientId)
			throws SQLException {
		Map<String, List<String>> DBclientListForDD = myTeamViewTabforDDDBHelper.getActiveClientsMembersUnderDD(userId,
				clientId);
		return DBclientListForDD;
	}

	/***
	 * This will get the Active Project Under DD from Db
	 * 
	 * @param userId
	 * @return Map<String, List<String>>
	 * @throws SQLException
	 */
	public Map<String, List<String>> getActiveProjectUnderDDFromDB(String userId) throws SQLException {
		Map<String, List<String>> DBclientListForDD = myTeamViewTabforDDDBHelper.getActiveProjectsUnderDD(userId);
		return DBclientListForDD;
	}
}
