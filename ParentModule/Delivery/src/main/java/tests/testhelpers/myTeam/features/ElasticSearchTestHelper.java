package tests.testhelpers.myTeam.features;

import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Set;

import com.aventstack.extentreports.Status;

import database.myTeam.features.ElasticSearchDBHelper;
import endpoints.myTeam.features.ElasticSearchEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.myTeam.MyTeamBaseTest;
import tests.testhelpers.myTeam.MyTeamTestHelper;
import utils.ExtentHelper;
import utils.RestUtils;

/**
 * @author imran.khan
 *
 */

public class ElasticSearchTestHelper extends MyTeamTestHelper {

	int projectId;
	int globerId;
	String globerName,projectName,podName,positionName,seniority;
	
	ElasticSearchDBHelper elasticSearchDBHelper;
	RestUtils restUtils = new RestUtils();
	RequestSpecification requestSpecification;
	Response response;
	
	public ElasticSearchTestHelper(String userName) throws Exception {
		super(userName);
		elasticSearchDBHelper = new ElasticSearchDBHelper();
	}
	
	/**
	 * This method will return globerName
	 * 
	 * @param userName
	 * @param userId
	 * @return globerName 
	 * @throws Exception
	 */
	public String getGloberFullName(String userName, String userId) throws Exception {
		projectId=getProjectIdOfGlober(userName,userId);
		globerName=getGloberNameByProjectId(projectId);
		return globerName;
	}
	
	/**
	 * This method will return positionName
	 * 
	 * @param userName
	 * @param userId
	 * @return positionName 
	 * @throws Exception
	 */
	public String getPositionName(String userName, String userId) throws Exception {
		projectId=getProjectIdOfGlober(userName,userId);
		positionName=getPositionNameByProjectId(projectId);
		return positionName;
	}
	
	/**
	 * This method will return seniority
	 * 
	 * @param userName
	 * @param userId
	 * @return seniority 
	 * @throws Exception
	 */
	public String getSeniority(String userName, String userId) throws Exception {
		projectId=getProjectIdOfGlober(userName,userId);
		seniority=getSeniorityByProjectId(projectId);
		return seniority;
	}
	
	/**
	 * This method will return podName
	 * 
	 * @param userName
	 * @param userId
	 * @return podName 
	 * @throws Exception
	 */
	public String getPodName(String userName, String userId) throws Exception {
		projectId=getProjectIdOfGlober(userName,userId);
		podName=getPodNameByProjectId(projectId);
		return podName;
	}
	
	/**
	 * This method will search a glober using glober name
	 * 
	 * @param requestParams
	 * @param globerId
	 * @return response 
	 * @throws Exception
	 */
	public Response searchGloberByGivenData(String searchedData) throws Exception {
		requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl+String.format(ElasticSearchEndpoints.searchGlober,URLEncoder.encode(searchedData,"UTF-8"));
		ExtentHelper.test.log(Status.INFO, "Request URL : "+url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch elastic search results", true);
		ExtentHelper.test.log(Status.INFO, "get elastic search method executed successfully");
		return response;
	}
	
	/**
	 * This method will return glober name by db
	 * 
	 * @param userId
	 * @return globerName 
	 * @throws Exception
	 */
	public String getGloberNameByDb(String userId) throws Exception {
		globerName=elasticSearchDBHelper.getGloberFullName(userId);
		return globerName;
	}
	
	/**
	 * This method will return position name
	 * 
	 * @param userName
	 * @param userId
	 * @return positionName 
	 * @throws Exception
	 */
	public String getPositionNameForLoggedInUser(String positionName){
		String dbPositionName="";
		if(positionName.contains("Delivery Director") || positionName.contains("Technical Director") ) {
			dbPositionName="delivery_director_id";
		}
		else if(positionName.contains("Project Manager")) {
			dbPositionName="project_manager_id";
		}
		else if(positionName.contains("Associate Project Manager")) {
			dbPositionName="associate_project_manager_id";
		}
		return dbPositionName;
	}
	
	/**
	 * This method will return project name from db
	 * 
	 * @param userId
	 * @param positionName
	 * @param projectName
	 * @return projectNames 
	 * @throws Exception
	 */
	public Set<String> getProjectNameByUserIdFromDb(String userId,String positionName,String projectName) throws SQLException {
		String dbPositionName=getPositionNameForLoggedInUser(positionName);
		Set<String> projectNames = elasticSearchDBHelper.getProjectNameByUserId(userId,dbPositionName,projectName);
		return projectNames;
	}
	
	/**
	 * This method will return pod name from db
	 * 
	 * @param userId
	 * @param positionName
	 * @param podName
	 * @return podNames 
	 * @throws Exception
	 */
	public Set<String> getPodNameByUserIdFromDb(String userId,String positionName,String podName) throws SQLException  {
		String dbPositionName=getPositionNameForLoggedInUser(positionName);
		Set<String> podNames = elasticSearchDBHelper.getPodNameByUserId(userId,dbPositionName,podName);
		return podNames;
	}

	/**
	 * This method will return pod name by db
	 * 
	 * @param userId
	 * @param positionName
	 * @param podName
	 * @return podNames 
	 * @throws Exception
	 */
	public Set<String> getSRNumberBySeniority(String userId, String positionName, String seniority) throws SQLException {
		String dbPositionName=getPositionNameForLoggedInUser(positionName);
		Set<String> srNumbersFromDB = elasticSearchDBHelper.getSRNumberBySeniority(userId,dbPositionName,seniority);
		return srNumbersFromDB;
	}
	
	/**
	 * This method will return sr number 
	 * 
	 * @param userId
	 * @param positionName
	 * @param podName
	 * @return podNames 
	 * @throws Exception
	 */
	public Set<String> getSRNumberByPosition(String userId, String positionName, String position) throws SQLException {
		String dbPositionName=getPositionNameForLoggedInUser(positionName);
		Set<String> srNumbersFromDB = elasticSearchDBHelper.getSRNumberByPosition(userId,dbPositionName,position);
		return srNumbersFromDB;
	}
	
	/**
	 * This method will return project name from db
	 * 
	 * @param userId
	 * @param positionName
	 * @param projectName
	 * @return projectNames 
	 * @throws Exception
	 */
	public Set<String> getProjectNameByKeyFromDb(String userId,String positionName,String key) throws SQLException {
		String dbPositionName=getPositionNameForLoggedInUser(positionName);
		Set<String> projectNames = elasticSearchDBHelper.getProjectsNameByKey(userId,dbPositionName,key);
		return projectNames;
	}
	
	/**
	 * This method will return pod name from db
	 * 
	 * @param userId
	 * @param positionName
	 * @param podName
	 * @return podNames 
	 * @throws Exception
	 */
	public Set<String> getPodNameByKeyFromDb(String userId,String positionName,String key) throws SQLException  {
		String dbPositionName=getPositionNameForLoggedInUser(positionName);
		Set<String> podNames = elasticSearchDBHelper.getPodsNameByKey(userId,dbPositionName,key);
		return podNames;
	}
	
	/**
	 * This method will return glober name from db
	 * 
	 * @param userId
	 * @param positionName
	 * @param podName
	 * @return globerNames 
	 * @throws Exception
	 */
	public Set<String> getGloberNameByKeyFromDb(String userId,String positionName,String key) throws SQLException  {
		String dbPositionName=getPositionNameForLoggedInUser(positionName);
		Set<String> globerNames = elasticSearchDBHelper.getGloberNameByKey(userId, dbPositionName, key);
		return globerNames;
	}
}
