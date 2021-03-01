package tests.testhelpers.myTeam.features;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import com.aventstack.extentreports.Status;

import endpoints.myTeam.features.MainPMEndpoints;
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
 * @author pooja.kakade
 *
 */

public class MainPMTestHelper extends MyTeamTestHelper{
	
	RequestSpecification requestSpecification;
	RestUtils restUtils = new RestUtils();
	
	public MainPMTestHelper(String userName) throws Exception {
		super(userName);
	}
	
	/**
	 * @author pooja.kakade
	 * 
	 * This method will return required test data to Main PM
	 * @param userName
	 * @param userId
	 * @return String 
	 * @throws Exception
	 */
	public List<Object> getRequiredTestDataForMainPM(String userName, String userId) throws Exception {
		
		int projectId=getProjectIdOfGlober(userName,userId);
		int globerId=getProjectMemberIdByProjectId(projectId);
		String roleStartDate = Utilities.getTodayDate("MM-dd-yyyy");
		String roleEndDate = Utilities.getFutureDate("MM-dd-yyyy", 10);
	
		List<Object> paramValaues = Arrays.asList(globerId,projectId,roleStartDate,roleEndDate, "Main Project Manager", "Main PM");
		return paramValaues;
	}
	
	/**
	 * @author pooja.kakade
	 * 
	 * This method will assign a Main PM
	 * 
	 * @param requestParams
	 * @return response 
	 * @throws Exception
	 */
	public Response postMainPM(JSONObject requestParams) throws Exception {
		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId).contentType(ContentType.JSON).body(requestParams.toString());
		
		String url = MyTeamBaseTest.baseUrl+MainPMEndpoints.postMainPM;
		
		ExtentHelper.test.log(Status.INFO, "Request URL : "+url);
		Response response = restUtils.executePOST(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to assign Main PM .", true);
		ExtentHelper.test.log(Status.INFO, "post Main PM method executed successfully");
		return response;
	}
}
