package tests.testhelpers.myTeam.features;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import com.aventstack.extentreports.Status;

import database.myTeam.features.UserPreferencesDbHelper;
import dto.myTeam.features.UserPreferencesDtoList;
import endpoints.myTeam.features.UserPreferencesEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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

public class UserPreferencesHelper extends MyTeamTestHelper {
	
	UserPreferencesDbHelper userPreferencesDbHelper;
	RestUtils restUtils = new RestUtils();
	
	public UserPreferencesHelper(String userName) throws Exception {
		super(userName);
		userPreferencesDbHelper = new UserPreferencesDbHelper();
	}

	/**
	 * This method will return user preferences 
	 * 
	 * @param userId
	 * @return paramValues 
	 * @throws Exception
	 */
	public Response getUserPreferencesForUser(String userId) throws Exception {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl+String.format(UserPreferencesEndpoints.getUserPreferences,userId,"viewProjectList");
		ExtentHelper.test.log(Status.INFO, "Request URL : "+url);
		Response response = restUtils.executeGET(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "get user preferences method executed successfully");
		return response;
	}

	/**
	 * This method will return user preferences from database
	 * 
	 * @param userId
	 * @return userPreferences 
	 * @throws Exception
	 */
	public List<UserPreferencesDtoList> getUserPreferencesFromDatabase(String userId) throws Exception {
		List<UserPreferencesDtoList> userPreferencesFromDb = userPreferencesDbHelper.getuserPreferencesforUser(userId);
		return userPreferencesFromDb;
	}
	
	/**
	 * This method will return the required test data to create user preference
	 * 
	 * @param globerId
	 * @return paramValaues
	 * @throws Exception
	 */
	public List<Object> geUserPreferencesTestData(String globerId) throws Exception {

		String componentKey = "viewProjectList";
		String value="[{\"id\":4,\"name\":\"status\",\"label\":\"Status\",\"selectedCriterias\":[{\"id\":\"ON_GOING\",\"name\":\"On Going\",\"isSelected\":true}]}]";
		List<Object> paramValues = Arrays.asList(globerId, componentKey,value,true);
		ExtentHelper.test.log(Status.INFO, "All the test data is ready to execute the test");
		return paramValues;
	}
	
	/**
	 * This method will create user preferences 
	 * 
	 * @param userId
	 * @param requestParams
	 * @return response 
	 * @throws Exception
	 */
	public Response postUserPreferencesForUser(String userId,JSONObject requestParams) throws Exception {
		userPreferencesDbHelper.deleteUserPreferenceForUserIfExist(userId);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId).contentType(ContentType.JSON).body(requestParams.toString());
		String url = MyTeamBaseTest.baseUrl+String.format(UserPreferencesEndpoints.getUserPreferences,userId,"viewProjectList");
		ExtentHelper.test.log(Status.INFO, "Request URL : "+url);
		Response response = restUtils.executePOST(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "post user preferences method executed successfully");
		return response;
	}
	
	/**
	 * This method will modify user preferences 
	 * 
	 * @param userId
	 * @param requestParams
	 * @return paramValues 
	 * @throws Exception
	 */
	public Response putUserPreferencesForUser(String userId,JSONObject requestParams) throws Exception {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId).contentType(ContentType.JSON).body(requestParams.toString());
		String url = MyTeamBaseTest.baseUrl+String.format(UserPreferencesEndpoints.getUserPreferences,userId,"viewProjectList");
		ExtentHelper.test.log(Status.INFO, "Request URL : "+url);
		Response response = restUtils.executePUT(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "post user preferences method executed successfully");
		return response;
	}
}
