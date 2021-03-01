package tests.testhelpers.myTeam.features;

import java.util.Arrays;
import java.util.List;

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
import utils.Utilities;

/**
 * @author dnyaneshwar.waghmare
 *
 */

public class MyTeamHelper extends MyTeamTestHelper {

	String startDate = null;
	String endDate = null;
	int projectId;
	int globerId;
	RequestSpecification requestSpecification;
	RestUtils restUtils = new RestUtils();
	MyTeamDBHelper myTeamDBHelper;
	List<Integer> projectIds;

	public MyTeamHelper(String userName) throws Exception {
		super(userName);
		myTeamDBHelper = new MyTeamDBHelper();
	}

	/**
	 * To get List Of Glober Details
	 * 
	 * @param userId
	 * @return Response
	 * @throws Exception
	 */
	public Response getListOfGloberDetails(String userId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl + String.format(MyTeamEndpoints.getGloberDetails, userId);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch GloberDetails list results",
				true);
		ExtentHelper.test.log(Status.INFO, "get List Of Glober Details method executed successfully");
		return response;
	}

	/**
	 * This method will return required test data to for Role
	 * 
	 * @param projectIds
	 * @param globerIds
	 * @return List<Object>
	 * @throws Exception
	 */
	public List<Object> getRequiredTestDataForRole(int projectIds, String globerIds) throws Exception {
		globerId = getProjectMemberIdByProjectId(projectId);
		startDate = Utilities.getTodayDate("dd-MM-yyyy");
		endDate = Utilities.getFutureDate("dd-MM-yyyy", 30);
		List<Object> paramValaues = Arrays.asList(startDate, endDate, globerIds, projectIds);
		return paramValaues;
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
	 * To get sum of assignment Percentage values from list
	 * 
	 * @param list
	 * @return int
	 */
	public static int sumOfAssignmentPercentage(List<Integer> list) {
		int sum = 0;

		for (int i : list) {
			sum += i;
		}
		return sum;
	}
	
	/***
	 * To get How It Works Link Details
	 * 
	 * @return Response
	 * @throws Exception
	 */
	public Response getHowItWorks() throws Exception {
		
		requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl+String.format(MyTeamEndpoints.getHowItWorks);
		ExtentHelper.test.log(Status.INFO, "Request URL : "+url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch How It Works results", true);
		ExtentHelper.test.log(Status.INFO, "get How It Works method executed successfully");
		return response;
	}
	
	/***
	 * To get Brief About My Team Module
	 * 
	 * @param userName as String
	 * @param userId as String
	 * @return Response
	 * @throws Exception
	 */
	public Response getBriefAboutMYTeamModule(String userName, String userId) throws Exception {
		
		requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl+String.format(MyTeamEndpoints.getBriefAboutMyTeamModule,userId,"tourMyTeamForPMCnt");
		ExtentHelper.test.log(Status.INFO, "Request URL : "+url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "User is (PM/PA) not able to get the brief about MY Team Module", true);
		ExtentHelper.test.log(Status.INFO, "get Brief About MY Team Module method executed successfully");
		return response;
	}
	
	/***
	 * To get Search Result
	 * 
	 * @return Response
	 * @throws Exception
	 */
	public Response getSearchResult() throws Exception {
		
		requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl+String.format(MyTeamEndpoints.getSearchResult,"dtc");
		ExtentHelper.test.log(Status.INFO, "Request URL : "+url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "User is (PM/PA) not able to search", true);
		ExtentHelper.test.log(Status.INFO, "get Search Result method executed successfully");
		return response;
	}
	
	/***
	 * To get Menu List for User
	 * 
	 * @return Response
	 * @throws Exception
	 */
	public Response getMenuListForUser() throws Exception {
		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl + String.format(MyTeamEndpoints.getMenuListForUser,"myteam");
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200,
				"Unable to fetch Menu List for Glober", true);
		ExtentHelper.test.log(Status.INFO, "get Menu List For User method executed successfully");
		return response;
	}
}
