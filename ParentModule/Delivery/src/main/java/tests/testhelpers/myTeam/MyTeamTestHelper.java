package tests.testhelpers.myTeam;

import java.util.List;

import org.testng.SkipException;

import com.aventstack.extentreports.Status;

import endpoints.GlowEndpoints;
import endpoints.myTeam.MyTeamEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.GlowBaseTest;
import tests.testcases.myTeam.MyTeamBaseTest;
import tests.testhelpers.DeliveryTestHelper;
import utils.ExtentHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * @author imran.khan
 *
 */

public class MyTeamTestHelper extends DeliveryTestHelper {

	public MyTeamTestHelper(String userName) throws Exception {
		new MyTeamBaseTest().createSession(userName);
	}

	List<Integer> globerIds;
	int globerId;
	List<Integer> podIds,clientIds,srNumbers;
	int podId,clientId,srNumber;
	List<String> projectNames,podNames,positionNames,senioritys;
	String projectName,podName,positionName,seniority;
	
	MyTeamBaseTest myTeamBaseTest = new MyTeamBaseTest();
	RestUtils restUtils = new RestUtils();
	RequestSpecification requestSpecification;
	Response response;
	String url,globerName;

	/**
	 * This method will return the globerId for given projectId
	 * 
	 * @param projectId
	 * @return globerId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getProjectMemberIdByProjectId(int projectId) throws Exception {
		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		url = MyTeamBaseTest.baseUrl + String.format(MyTeamEndpoints.getProjectMembers,projectId,true,true,"projects",true);
		response = restUtils.executeGET(requestSpecification, url);
		myTeamBaseTest.validateResponseToContinueTest(response, 200, "Unable to fetch Glober Details.", true);
		MyTeamBaseTest.test.log(Status.INFO, "Glober details fetched successfully.");
		globerIds = (List<Integer>) restUtils.getValueFromResponse(response, "$.details[*].globerId");
		globerId = Utilities.getRandomIdFromList(globerIds);
		ExtentHelper.test.log(Status.INFO, "GloberId for which we are working is : " + globerId);
		return globerId;

	}

	/**
	 * This method will return the podId for given projectId
	 * 
	 * @param projectId
	 * @return podId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getPodIdByProjectId(int projectId) throws Exception {
		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		url = MyTeamBaseTest.baseUrl + String.format(MyTeamEndpoints.getPodList,projectId);
		response = restUtils.executeGET(requestSpecification, url);
		myTeamBaseTest.validateResponseToContinueTest(response, 200, "Unable to fetch Pod List.", true);
		MyTeamBaseTest.test.log(Status.INFO, "Pod list fetched successfully.");
		podIds = (List<Integer>) restUtils.getValueFromResponse(response, "$.[*].podId");
		podId = Utilities.getRandomIdFromList(podIds);
		if (podId == 0 && podIds.size() > 1) {
			podId = Utilities.getRandomIdFromList(podIds);
		} else if (podId == 0 && podIds.size() == 1) {
			throw new SkipException("There is only default pod available");
		}
		ExtentHelper.test.log(Status.INFO, "PodId for which we are working is : " + podId);
		return podId;
	}

	/**
	 * This method will return the globerId for given podId
	 * 
	 * @param projectId
	 * @param podId
	 * @return globerId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getPodMemberIdByPodId(int projectId,int podId) throws Exception {
		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		url = MyTeamBaseTest.baseUrl + String.format(MyTeamEndpoints.getPodMembers,podId,projectId,true);
		ExtentHelper.test.log(Status.INFO, "Request URL : "+url);
		response = restUtils.executeGET(requestSpecification, url);
		myTeamBaseTest.validateResponseToContinueTest(response, 200, "Unable to fetch pod member List.", true);
		MyTeamBaseTest.test.log(Status.INFO, "Pod member list fetched successfully.");
		globerIds = (List<Integer>) restUtils.getValueFromResponse(response, "$.details[*].globerId");
		globerId = Utilities.getRandomIdFromList(globerIds);
		ExtentHelper.test.log(Status.INFO, "GloberId for which we are working is : " + globerId);
		return globerId;
	}
	
	/**
	 * This method will return the client list for given project Id
	 * 
	 * @param userId
	 * @param projectId
	 * @return clientId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getClientIdByProjectId(String userId,int projectId) throws Exception {
		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		url = MyTeamBaseTest.baseUrl + MyTeamEndpoints.getClientList + "?globerId=" + userId + "&projectId=" + projectId;
		ExtentHelper.test.log(Status.INFO, "Request URL : "+url);
		response = restUtils.executeGET(requestSpecification, url);
		myTeamBaseTest.validateResponseToContinueTest(response, 200, "Unable to fetch client List.", true);
		MyTeamBaseTest.test.log(Status.INFO, "client list fetched successfully.");
		clientIds = (List<Integer>) restUtils.getValueFromResponse(response, "$.details[*].id");
		clientId = clientIds.get(0);
		ExtentHelper.test.log(Status.INFO, "Client Id for which we are working is : " + clientId);
		return clientId;
	}
	
	/**
	 * This method will return the glober Name for given projectId
	 * @param projectId
	 * @return globerName
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getGloberNameByProjectId(int projectId) throws Exception {
		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl+String.format(MyTeamEndpoints.getProjectMembers,projectId,true,true,"projects",true);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200,"Unable to fetch Glober Details.",true);
		MyTeamBaseTest.test.log(Status.INFO,"Glober details fetched successfully.");
		List<List<String>> globerNames = (List<List<String>>) restUtils.getValueFromResponse(response, "$.details[*].globerName");
		if (globerNames.size() == 0) {
			throw new SkipException(" THERE IS NO GLOBER FOUND FOR PROJECT ID: '" + projectId + "'");
		}
		int index = Utilities.getRandomNumberBetween(0, globerNames.size());
		globerName = globerNames.get(index).get(0) + " " + globerNames.get(index).get(1);
		ExtentHelper.test.log(Status.INFO, "Glober Name for which we are working is : " + globerName);
		return globerName;

	}
	
	/**
	 * This method will return the project name for given glober Id
	 * 
	 * @param userId
	 * @return projectName
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getProjectNameOfGlober(String userId) throws Exception {
		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		url = MyTeamBaseTest.baseUrl + String.format(MyTeamEndpoints.getprojectList,userId);
		ExtentHelper.test.log(Status.INFO, "Request URL : "+url);
		response = restUtils.executeGET(requestSpecification, url);
		myTeamBaseTest.validateResponseToContinueTest(response, 200, "Unable to fetch project List.", true);
		MyTeamBaseTest.test.log(Status.INFO, "client list fetched successfully.");
		projectNames = (List<String>) restUtils.getValueFromResponse(response, "$.details[*].projectName");
		int index = Utilities.getRandomNumberBetween(0, projectNames.size());
		projectName = projectNames.get(index);
		ExtentHelper.test.log(Status.INFO, "Project name for which we are working is : " + projectName);
		return projectName;
	}
	
	/**
	 * This method will return the pod name for given projectId
	 * 
	 * @param projectId
	 * @return podId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getPodNameByProjectId(int projectId) throws Exception {
		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		url = MyTeamBaseTest.baseUrl + String.format(MyTeamEndpoints.getPodList,projectId);
		response = restUtils.executeGET(requestSpecification, url);
		myTeamBaseTest.validateResponseToContinueTest(response, 200, "Unable to fetch Pod List.", true);
		MyTeamBaseTest.test.log(Status.INFO, "Pod list fetched successfully.");
		podNames = (List<String>) restUtils.getValueFromResponse(response, "$.[*].podName");
		int index = Utilities.getRandomNumberBetween(0, podNames.size());
		podName = podNames.get(index);
		ExtentHelper.test.log(Status.INFO, "Pod name for which we are working is : " + podName);
		return podName;
	}
	
	/**
	 * This method will return the position of glober
	 * @param projectId
	 * @return positionName
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getPositionNameByProjectId(int projectId) throws Exception {
		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl+String.format(MyTeamEndpoints.getProjectMembers,projectId,projectId,true,true,"projects",true);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200,"Unable to fetch Glober Details.",true);
		MyTeamBaseTest.test.log(Status.INFO,"Glober details fetched successfully.");
		positionNames = (List<String>) restUtils.getValueFromResponse(response, "$.details[*].position");
		int index = Utilities.getRandomNumberBetween(0, positionNames.size());
		positionName = positionNames.get(index);
		ExtentHelper.test.log(Status.INFO, "Position Name for which we are working is : " + positionName);
		return positionName;

	}
	
	/**
	 * This method will return the seniority of glober
	 * @param projectId
	 * @return seniority
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getSeniorityByProjectId(int projectId) throws Exception {
		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl+String.format(MyTeamEndpoints.getProjectMembers,projectId,projectId,true,true,"projects",true);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200,"Unable to fetch Glober Details.",true);
		MyTeamBaseTest.test.log(Status.INFO,"Glober details fetched successfully.");
		senioritys = (List<String>) restUtils.getValueFromResponse(response, "$.details[*].position");
		int index = Utilities.getRandomNumberBetween(0, senioritys.size());
		seniority = senioritys.get(index);
		ExtentHelper.test.log(Status.INFO, "Seniority for which we are working is : " + seniority);
		return seniority;

	}
	
	/**
	 * This method will return the sr number for the glober
	 * @param userId
	 * @return seniority
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public int getSrNumberByUserId(String userId) throws Exception {
		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl+String.format(MyTeamEndpoints.getSrNumbers,userId);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200,"Unable to fetch SR Details.",true);
		MyTeamBaseTest.test.log(Status.INFO,"SR details fetched successfully.");
		srNumbers = (List<Integer>) restUtils.getValueFromResponse(response, "$.details[*]..srNumber");
		int index = Utilities.getRandomNumberBetween(0, srNumbers.size());
		srNumber = srNumbers.get(index);
		ExtentHelper.test.log(Status.INFO, "SR number for which we are working is : " + srNumber);
		return srNumber;

	}
	
	/**
	 * This method will return the position of logged in user
	 * @param projectId
	 * @return positionName
	 * @throws Exception 
	 */
	public String getPositionNameByLoggedInUserId(String globerId) throws Exception {
		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = GlowBaseTest.baseUrl + GlowEndpoints.globerDetails + globerId;
		Response response = restUtils.executeGET(requestSpecification, url);
		new GlowBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch Glober Details.", true);
		GlowBaseTest.test.log(Status.INFO, "Glober details fetched successfully.");
		positionName = (String) restUtils.getValueFromResponse(response, "position");
		ExtentHelper.test.log(Status.INFO, "Position Name for which we are working is : " + positionName);
		return positionName;

	}
}
