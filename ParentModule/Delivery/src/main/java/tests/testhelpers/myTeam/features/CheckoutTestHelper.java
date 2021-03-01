package tests.testhelpers.myTeam.features;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;
import com.google.gson.Gson;

import endpoints.myTeam.features.CheckoutEndpoints;
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
 * @author imran.khan
 *
 */

public class CheckoutTestHelper extends MyTeamTestHelper {

	String message = null;
	Gson gson;
	SoftAssert soft_assert;
	int projectId;
	int globerId;	
	String notificationDate=null;
	String checkoutDate=null;
	
	public CheckoutTestHelper(String userName) throws Exception {
		super(userName);
		soft_assert = new SoftAssert();
	}

	/**
	 * This method will return required test data to checkout a glober
	 * 
	 * @param userName
	 * @param userId
	 * @return String 
	 * @throws Exception
	 */
	public List<Object> getRequiredTestDataForCheckout(String userName, String userId) throws Exception {
		projectId=getProjectIdOfGlober(userName,userId);
		globerId=getProjectMemberIdByProjectId(projectId);
		notificationDate = Utilities.getTodayDate("dd-MM-yyyy");
		checkoutDate = Utilities.getFutureDate("dd-MM-yyyy", 4);
		
		List<Object> paramValaues = Arrays.asList(checkoutDate,"automationTest", globerId,
				notificationDate,projectId, true, userId);
		return paramValaues;
	}
	
	/**
	 * This method will create a checkout for glober
	 * 
	 * @param requestParams
	 * @param globerId
	 * @return response 
	 * @throws Exception
	 */
	public Response createCheckout(JSONObject requestParams) throws Exception {
		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId).contentType(ContentType.JSON).body(requestParams.toString());
		String url = MyTeamBaseTest.baseUrl+CheckoutEndpoints.createCheckout+requestParams.get("globerId");
		ExtentHelper.test.log(Status.INFO, "Request URL : "+url);
		Response response = restUtils.executePOST(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to create checkout.", true);
		ExtentHelper.test.log(Status.INFO, "post Checkout method executed successfully");
		return response;
	}
	
	/**
	 * This method will return required test data to checkout a glober with replacement
	 * 
	 * @param userName
	 * @param userId
	 * @return String 
	 * @throws Exception
	 */
	public List<Object> getRequiredTestDataForCheckoutWithReplacement(String userId,String projectId,String globerId) throws Exception {
		notificationDate = Utilities.getTodayDate("dd-MM-yyyy");
		checkoutDate = Utilities.getFutureDate("dd-MM-yyyy", 4);
		
		List<Object> paramValaues = Arrays.asList(checkoutDate,"automationTest", globerId,
				notificationDate,projectId, true, userId);
		return paramValaues;
	}
}
