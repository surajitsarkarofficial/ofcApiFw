package tests.testhelpers.myTeam.features;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;
import com.google.gson.Gson;

import endpoints.myTeam.MyTeamEndpoints;
import endpoints.myTeam.features.MarkAndUnmarkTlEndpoints;
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

public class MarkAndUnmarkTlTestHelper extends MyTeamTestHelper {

	String message, url = null;
	Gson gson;
	SoftAssert soft_assert;
	int projectId, globerId, podId;

	RestUtils restUtils = new RestUtils();
	RequestSpecification requestSpecification;
	Response response;

	public MarkAndUnmarkTlTestHelper(String userName) throws Exception {
		super(userName);
		soft_assert = new SoftAssert();
	}

	/**
	 * This method will return projectId and PodId
	 * 
	 * @param userName
	 * @param userId
	 * @return String
	 * @throws Exception
	 */
	public String getProjectIdAndPodId(String userName, String userId) throws Exception {
		projectId = getProjectIdOfGlober(userName, userId);
		podId = getPodIdByProjectId(projectId);

		/* it contains projectId and podId sequentially, split this by : */
		String projectIdPodIdOfGlober = projectId + ":" + podId;
		return projectIdPodIdOfGlober;
	}

	/**
	 * This method will return required test data to mark and unmark a glober
	 * 
	 * @param projectId
	 * @param podId
	 * @return paramValaues
	 * @throws Exception
	 */
	public List<Object> getRequiredTestDataForMarkUnmarkTL(int projectId, int podId) throws Exception {
		globerId = getPodMemberIdByPodId(projectId, podId);

		List<Object> paramValaues = Arrays.asList(projectId, globerId, "Tech Lead");
		return paramValaues;
	}

	/**
	 * This method will mark a glober as tech lead
	 * 
	 * @param requestParams
	 * @param podId
	 * @return response
	 * @throws Exception
	 */
	public Response markTechLead(JSONObject requestParams, int podId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId)
				.contentType(ContentType.JSON).body(requestParams.toString());
		url = MyTeamBaseTest.baseUrl + MarkAndUnmarkTlEndpoints.markAndUnmarkTL + podId + "/techlead?action=mark";
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executePOST(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to mark a glober.", true);
		ExtentHelper.test.log(Status.INFO, "post markTl method executed successfully");
		return response;
	}

	/**
	 * This method will unmark a glober as tech lead
	 * 
	 * @param requestParams
	 * @param podId
	 * @return response
	 * @throws Exception
	 */
	public Response unmarkTechLead(JSONObject requestParams, int podId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId)
				.contentType(ContentType.JSON).body(requestParams.toString());
		url = MyTeamBaseTest.baseUrl + MarkAndUnmarkTlEndpoints.markAndUnmarkTL + podId + "/techlead?action=unmark";
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executePOST(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to unmark a glober.", true);
		ExtentHelper.test.log(Status.INFO, "post unmarkTl method executed successfully");
		return response;
	}

	/**
	 * This method will return the global Tech Lead Status in Pod view
	 * 
	 * @param projectId
	 * @param globerId
	 * @param podId
	 * @return Boolean
	 * @throws Exception
	 */
	public Boolean getGloberTlStatusInPod(int projectId, String globerId, int podId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		url = MyTeamBaseTest.baseUrl + String.format(MyTeamEndpoints.getPodMembers,podId,projectId,true)
				+ "&globerId=" + globerId;
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch TL status for glober.",
				true);
		@SuppressWarnings("unchecked")
		List<Boolean> responsePodView = (List<Boolean>) restUtils.getValueFromResponse(response, "$.details[*].isTl");
		Boolean markingStatus = responsePodView.get(0);
		MyTeamBaseTest.test.log(Status.INFO, "get TL status at pod method executed successfully");
		return markingStatus;
	}

	/**
	 * This method will return the global Tech Lead Status in Project view
	 * 
	 * @param projectId
	 * @param globerId
	 * @return Boolean
	 * @throws Exception
	 */
	public Boolean getGloberTlStatusInProject(int projectId, String globerId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		url=MyTeamBaseTest.baseUrl+String.format(MyTeamEndpoints.getProjectMembers,projectId,projectId,true,true,"projects",true)+"&globerId=" + globerId;
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch TL status for glober.",
				true);
		@SuppressWarnings("unchecked")
		List<Boolean> responsePodView = (List<Boolean>) restUtils.getValueFromResponse(response, "$.details[*].isTl");
		Boolean markingStatus = responsePodView.get(0);
		MyTeamBaseTest.test.log(Status.INFO, "get TL status at project method executed successfully");
		return markingStatus;
	}

	/**
	 * This method will return the global Tech Lead Status in Client view
	 * 
	 * @param clientId
	 * @param projectId
	 * @param globerId
	 * @return Boolean
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Boolean getGloberTlStatusInClient(int clientId, int projectId, String globerId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		url = MyTeamBaseTest.baseUrl + MyTeamEndpoints.getClientMembers + clientId
				+ "?type=client&fetchProjectRole=true&sortBy=POSTION&sortOrder=ASC&pageSize=100&pageNumber=1"
				+ "&projectId=" + projectId + "&globerId=" + globerId;
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch client members.",
				true);
		List<Integer> globerIdsList = (List<Integer>) restUtils.getValueFromResponse(response, "$.details[*].globerId");
		int index=-1;
		for(int iTemp=0;iTemp<globerIdsList.size();iTemp++)
		{
			if(globerId.equals(Integer.toString(globerIdsList.get(iTemp))))
			{
				index=iTemp;
			}
		}
		List<Boolean> responsePodView = (List<Boolean>) restUtils.getValueFromResponse(response, "$.details[*].isTl");
		Boolean markingStatus = responsePodView.get(index);
		MyTeamBaseTest.test.log(Status.INFO, "get TL status at project method executed successfully");
		return markingStatus;
	}
	
	/**
	 * This method will mark a glober as tech lead for invalid data
	 * 
	 * @param requestParams
	 * @param podId
	 * @return response
	 * @throws Exception
	 */
	public Response markTechLeadForInvalidData(JSONObject requestParams, int podId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId)
				.contentType(ContentType.JSON).body(requestParams.toString());
		url = MyTeamBaseTest.baseUrl + MarkAndUnmarkTlEndpoints.markAndUnmarkTL + podId + "/techlead?action=mark";
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executePOST(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "post markTl method executed successfully");
		return response;
	}
	
	/**
	 * This method will mark a glober as tech lead for invalid pod data
	 * 
	 * @param requestParams
	 * @param podId
	 * @return response
	 * @throws Exception
	 */
	public Response markTechLeadForInvalidPodData(JSONObject requestParams, String podId) throws Exception {

		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId)
				.contentType(ContentType.JSON).body(requestParams.toString());
		url = MyTeamBaseTest.baseUrl + MarkAndUnmarkTlEndpoints.markAndUnmarkTL + podId + "/techlead?action=mark";
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executePOST(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "post markTl method executed successfully");
		return response;
	}
}
