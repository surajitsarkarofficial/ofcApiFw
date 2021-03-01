package tests.testhelpers.myTeam.features;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import com.aventstack.extentreports.Status;

import database.myTeam.MyTeamDBHelper;
import dto.myTeam.features.FilterObject;
import endpoints.myTeam.MyTeamEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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

public class MyTeamPodHelper extends MyTeamTestHelper {

	String startDate = null;
	String endDate = null;
	int projectId;
	int globerId;
	RequestSpecification requestSpecification;
	RestUtils restUtils = new RestUtils();
	MyTeamDBHelper myTeamDBHelper;
	List<Integer> projectIds;

	public MyTeamPodHelper(String userName) throws Exception {
		super(userName);
		myTeamDBHelper = new MyTeamDBHelper();
	}

	/**
	 * To get POD List For Project
	 * 
	 * @param userId
	 * @return Response
	 * @throws Exception
	 */
	public Response getPODListForProject(int projectId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl + String.format(MyTeamEndpoints.getPodList, projectId);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch POD list results", true);
		ExtentHelper.test.log(Status.INFO, "get POD List For Project method executed successfully");
		return response;
	}

	/**
	 * To get POD List For Incorrect Project
	 * 
	 * @param userId
	 * @return Response
	 * @throws Exception
	 */
	public Response getPODListForIncorrectProject(String projectId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl + String.format(MyTeamEndpoints.getPodList, projectId);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "get POD List For Project method executed successfully");
		return response;
	}

	/**
	 * To get All POD Types
	 * 
	 * @param userId
	 * @return Response
	 * @throws Exception
	 */
	public Response getAllPODTypes() throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl + String.format(MyTeamEndpoints.getAllPODTypes);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch POD Types list results",
				true);
		ExtentHelper.test.log(Status.INFO, "get List Of All POD Types method executed successfully");
		return response;
	}

	/**
	 * To get All PODs For Glober
	 * 
	 * @param userId
	 * @return Response
	 * @throws Exception
	 */
	public Response getAllPODsForGlober(String userId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl + String.format(MyTeamEndpoints.getAllPodsForGlober, userId);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch POD Types list results",
				true);
		ExtentHelper.test.log(Status.INFO, "get List Of All POD Types method executed successfully");
		return response;
	}

	/**
	 * To get All Empty PODs
	 * 
	 * @param userId
	 * @return Response
	 * @throws Exception
	 */
	public Response getAllEmptyPODs(int projectId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl + String.format(MyTeamEndpoints.getAllEmptyPODS, projectId, true);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200,
				"Unable to fetch Empty POD Types list results", true);
		ExtentHelper.test.log(Status.INFO, "get List Of All POD Types method executed successfully");
		return response;
	}

	/**
	 * To create New POD Under Project
	 * 
	 * @param userId
	 * @return Response
	 * @throws Exception
	 */
	public Response createNewPODUnderProject(String userId, String podName, String podTypeId, String purpose,
			String projectId) throws Exception {

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
	 * This method will return required test data to Create POD
	 * 
	 * @param userName
	 * @param userId
	 * @return List<Object>
	 * @throws Exception
	 */
	public List<Object> getRequiredTestDataForPOD(String userName, String userId) throws Exception {
		projectId = getProjectIdOfGlober(userName, userId);
		globerId = getProjectMemberIdByProjectId(projectId);
		List<Object> paramValaues = Arrays.asList("autoPod", "1", "Created for automation Testing", projectId);
		return paramValaues;
	}

	/**
	 * This method will return required test data to Update POD
	 * 
	 * @param userName
	 * @param userId
	 * @param podId
	 * @return List<Object>
	 * @throws Exception
	 */
	public List<Object> getRequiredTestDataForUpdatePOD(String userName, String userId, int podId) throws Exception {
		projectId = getProjectIdOfGlober(userName, userId);
		globerId = getProjectMemberIdByProjectId(projectId);
		List<Object> paramValaues = Arrays.asList("NewAutoPod", "2", "Updated for automation Testing", projectId,
				podId);
		return paramValaues;
	}

	/**
	 * To get Pods For Glober
	 * 
	 * @param userId
	 * @return Response
	 * @throws Exception
	 */
	public Response getPodsForGlober(String userId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl + String.format(MyTeamEndpoints.getPodsForGlober, userId);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200,
				"Unable to fetch Pods for Glober list results", true);
		ExtentHelper.test.log(Status.INFO, "get Pods For Glober method executed successfully");
		return response;
	}

	/***
	 * This will verify the POD is deleted from DB
	 * 
	 * @param podId
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean isPODDeletedFromDB(String podId) throws SQLException {
		List<FilterObject> DBPODListForProject = myTeamDBHelper.getPODDateFromDB(podId);
		if (DBPODListForProject != null && DBPODListForProject.get(0).getPodName().contains(podId + "_DELETED")) {
			return true;
		}
		return false;
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
	 * To get the POD Details For Project From DB
	 * 
	 * @param projectId
	 * @return List<FilterObject>
	 * @throws SQLException
	 */
	public List<FilterObject> getPODDetailsForProject(int projectId) throws SQLException {
		List<FilterObject> DBPODListForProject = myTeamDBHelper.getProjectStatesListForGlober(projectId);
		return DBPODListForProject;
	}

	/***
	 * To get POD Details For Project from DB
	 * 
	 * @param projectId
	 * @param podName
	 * @return List<FilterObject>
	 * @throws SQLException
	 */
	public List<FilterObject> getPODDetailsForProject(int projectId, String podName) throws SQLException {
		List<FilterObject> DBPODListForProject = myTeamDBHelper.getProjectStatesListForGlober(projectId, podName);
		return DBPODListForProject;
	}

	/***
	 * To get the All Types of PODs from DB
	 * 
	 * @return List<FilterObject>
	 * @throws SQLException
	 */
	public List<FilterObject> getAllTypesOfPODSFromDB() throws SQLException {
		List<FilterObject> DBPODListForProject = myTeamDBHelper.getAllTypesOfPODS();
		return DBPODListForProject;
	}

	/***
	 * To get the ALL PODs under Glober fromDB
	 * 
	 * @param userName
	 * @return List<FilterObject>
	 * @throws SQLException
	 */
	public List<FilterObject> getAllPODsUnderGlober(String userName) throws SQLException {
		List<FilterObject> DBPODListForProject = myTeamDBHelper.getAllPODsUnderGlober(userName);
		return DBPODListForProject;
	}

	/***
	 * To get All Empty PODs Under Project from DB
	 * 
	 * @param projectId
	 * @return List<FilterObject>
	 * @throws SQLException
	 */
	public List<FilterObject> getAllEmptyPODsUnderProject(int projectId) throws SQLException {
		List<FilterObject> DBPODListForProject = myTeamDBHelper.getAllEmptyPODsUnderProject(projectId);
		return DBPODListForProject;
	}

	/***
	 * To get the POD Details For Glober from DB
	 * 
	 * @param podId
	 * @return List<FilterObject>
	 * @throws SQLException
	 */
	public List<FilterObject> getPODDetailsForGlober(int podId) throws SQLException {
		List<FilterObject> DBPODListForProject = myTeamDBHelper.getPODDetailsForGlober(podId);
		return DBPODListForProject;
	}
	/**

	 * This method will return required test data to checkout a glober
	 * 
	 * @param userName
	 * @param userId
	 * @return List<Object>
	 * @throws Exception
	 */
	public List<Object> getRequiredTestDataForManagePOD(String userName, String userId) throws Exception {
		projectId = getProjectIdOfGlober(userName, userId);
		Response response = getAllPODsForGlober(userId);
		List<Integer> podIds = response.jsonPath().get("podId");
		int sourcePodId = podIds.get(0);
		List<Object> paramValaues = Arrays.asList(projectId, sourcePodId, sourcePodId, "false", userId);
		return paramValaues;
	}

	/**
	 * This method will return required test data to checkout a glober
	 * 
	 * @param userName
	 * @param userId
	 * @return List<Object>
	 * @throws Exception
	 */
	public List<Object> getRequiredTestDataForManagePODNamedToDefault(String userName, String userId) throws Exception {
		projectId = getProjectIdOfGlober(userName, userId);
		Response response = getAllPODsForGlober(userId);
		List<Integer> podIds = response.jsonPath().get("podId");
		int sourcePodId = podIds.get(0);
		List<Object> paramValaues = Arrays.asList(projectId, sourcePodId, 0, "false", userId);
		return paramValaues;
	}

	/**
	 * This method will return required test data to checkout a glober
	 * 
	 * @param userName
	 * @param userId
	 * @return List<Object>
	 * @throws Exception
	 */
	public List<Object> getRequiredTestDataForManagePODDefaultToNamed(String userName, String userId) throws Exception {
		projectId = getProjectIdOfGlober(userName, userId);
		Response response = getAllPODsForGlober(userId);
		List<Integer> podIds = response.jsonPath().get("podId");
		int targetPodId = podIds.get(0);
		List<Object> paramValaues = Arrays.asList(projectId, 0, targetPodId, "false", userId);
		return paramValaues;
	}

	/**
	 * This method will create POD
	 * 
	 * @param requestParams
	 * @return Response
	 * @throws Exception
	 */
	public Response createPOD(JSONObject requestParams) throws Exception {
		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId).contentType(ContentType.JSON).body(requestParams.toString());
		String url = MyTeamBaseTest.baseUrl + MyTeamEndpoints.postPODUnderProject;
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executePOST(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 201, "Unable to create pod.", true);
		ExtentHelper.test.log(Status.INFO, "post create pod method executed successfully");
		return response;
	}

	/**
	 * This method will update the POD
	 * 
	 * @param requestParams
	 * @param podId
	 * @return Response
	 * @throws Exception
	 */
	public Response updatePOD(JSONObject requestParams, int podId) throws Exception {
		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId).contentType(ContentType.JSON).body(requestParams.toString());
		String url = MyTeamBaseTest.baseUrl + String.format(MyTeamEndpoints.putPodDetails, podId);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executePUT(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to update pod.", true);
		ExtentHelper.test.log(Status.INFO, "update pod method executed successfully");
		return response;
	}

	/**
	 * This will delete the POD
	 * 
	 * @param podId
	 * @return Response
	 * @throws Exception
	 */
	public Response deletePOD(int podId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl + String.format(MyTeamEndpoints.deletePod, podId, null);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeDELETE(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch GloberDetails list results",
				true);
		ExtentHelper.test.log(Status.INFO, "get List Of Glober Details method executed successfully");
		return response;
	}
	
	
	/**
	 * To get PODs Details
	 * 
	 * @return Response
	 * @throws Exception
	 */
	public Response getPODDetails() throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl + String.format(MyTeamEndpoints.getPODDetails);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch POD Details results",
				true);
		ExtentHelper.test.log(Status.INFO, "get POD Details method executed successfully");
		return response;
	}
	
	
	/**
	 * To check if the glober has an existing repalcement
	 * 
	 * @return Response
	 * @throws Exception
	 */
	public Response getGloberHasExistingReplacement(String userId,int positionId,int projectId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl + String.format(MyTeamEndpoints.getGloberHasExistingReplacement,positionId,userId,projectId);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Glober does not have an existing repalcement",
				true);
		ExtentHelper.test.log(Status.INFO, "get Glober Has Existing Replacement method executed successfully");
		return response;
	}
	
	/**
	 * To get the POD Assessment
	 * 
	 * @return Response
	 * @throws Exception
	 */
	public Response getPODAssessment() throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl + String.format(MyTeamEndpoints.getPODAssessment,"8","1");
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "POD Assessment does not return",
				true);
		ExtentHelper.test.log(Status.INFO, "get POD Assessment method executed successfully");
		return response;
	}
	
	/**
	 * To get Assignment of Glober Or POD
	 * 
	 * @param userId
	 * @return Response
	 * @throws Exception
	 */
	public Response getAssignmentOfGloberOrPOD(String userId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl + String.format(MyTeamEndpoints.getAssignmentOfGloberOrPOD, userId);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch Assignment of Glober Or POD results", true);
		ExtentHelper.test.log(Status.INFO, "get Assignment Of Glober Or POD method executed successfully");
		return response;
	}
	
	/***
	 * To get the project and client details for glober
	 * 
	 * @param podId
	 * @return List<FilterObject>
	 * @throws SQLException
	 */
	public List<FilterObject> getProjectAndClientForGlober(String userId) throws SQLException {
		List<FilterObject> projectAndClientDetailsForGlober = myTeamDBHelper.getProjectAndClientForGlober(userId);
		return projectAndClientDetailsForGlober;
	}
	
	/***
	 * To get the project and client details for glober
	 * 
	 * @param podId
	 * @return List<FilterObject>
	 * @throws SQLException
	 */
	public List<FilterObject> getPODsForGlober(String userId) throws SQLException {
		List<FilterObject> podsForGlober = myTeamDBHelper.getPODsForGlober(userId);
		return podsForGlober;
	}
	
	
	
}
