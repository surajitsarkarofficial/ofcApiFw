package tests.testhelpers.myTeam.features;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import com.aventstack.extentreports.Status;

import endpoints.myTeam.features.EditAssignmentEndpoints;
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

public class EditAssignmentHelper extends MyTeamTestHelper {
	
	int projectId;
	int globerId;	
	
	public EditAssignmentHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will return required test data to edit assignment
	 * 
	 * @param userName
	 * @param userId
	 * @param endDate
	 * @return paramValues 
	 * @throws Exception
	 */
	public List<Object> getRequiredTestDataForEditAssignment(String userName, String userId,String endDate) throws Exception {
		projectId=getProjectIdOfGlober(userName,userId);
		globerId=getProjectMemberIdByProjectId(projectId);
		
		List<Object> paramValues = Arrays.asList(projectId, globerId,endDate);
		return paramValues;
	}
	
	/**
	 * This method will edit assignment date for glober
	 * 
	 * @param requestParams
	 * @return response 
	 * @throws Exception
	 */
	public Response editAssignment(JSONObject requestParams) throws Exception {
		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId).contentType(ContentType.JSON).body(requestParams.toString());
		String url = MyTeamBaseTest.baseUrl+EditAssignmentEndpoints.editAssignment;
		ExtentHelper.test.log(Status.INFO, "Request URL : "+url);
		Response response = restUtils.executePOST(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "post edit assignment method executed successfully");
		return response;
	}
	
	/***
	 * This will return the assignments
	 * 
	 * @return Response
	 * @throws Exception
	 */
	public Response getAssignment() throws Exception {
		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl + String.format(EditAssignmentEndpoints.getAssignments);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch Assignments list results",
				true);
		ExtentHelper.test.log(Status.INFO, "get Assignment method executed successfully");
		return response;
	}
	
}
