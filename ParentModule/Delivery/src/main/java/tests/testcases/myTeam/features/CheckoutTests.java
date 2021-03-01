package tests.testcases.myTeam.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import dataproviders.myTeam.features.CheckoutDataProviders;
import endpoints.myTeam.features.CheckoutEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.myTeam.features.CheckoutPayloadHelper;
import payloads.myTeam.features.EditAssignmentPayloadHelper;
import payloads.myTeam.features.RotateAndReplacePayloadHelper;
import tests.testcases.myTeam.MyTeamBaseTest;
import tests.testhelpers.myTeam.features.CheckoutTestHelper;
import tests.testhelpers.myTeam.features.EditAssignmentHelper;
import tests.testhelpers.myTeam.features.RotateAndReplaceTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;
import utils.Utilities;

/**
 * @author imran.khan
 *
 */

public class CheckoutTests extends MyTeamBaseTest {
	String message, status = null;
	String podId;
	
	RestUtils restUtils = new RestUtils();

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("CheckoutTests");
	}
	
	/**
	 * This test will create a checkout for a glober
	 * 
	 * @param UserName and userId
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = CheckoutDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void createCheckoutTest(String userName, String userId) throws Exception {

		CheckoutTestHelper checkoutHelper = new CheckoutTestHelper(userName);
		List<Object> dataForCheckoutGlober = checkoutHelper.getRequiredTestDataForCheckout(userName, userId);
		JSONObject requestParams = new CheckoutPayloadHelper().createCheckoutPayload(dataForCheckoutGlober);
		Response response = checkoutHelper.createCheckout(requestParams);
		validateResponseToContinueTest(response, 200, "Checkout not created successfully", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		
		assertTrue(message.contains("Success: Checkout Request submitted. Please contact your People Champion.") ||
				message.contains("Checkout Request has already been created.") ||message.contains("Checkout request submitted and a status email is on its way. For further details, please contact your People Champion."),
				"The message is not success");
		test.log(Status.PASS, "Checkout Request created successfully");
	}

	/**
	 * This test will create a checkout for a glober with release option
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsOcampo", dataProviderClass = CheckoutDataProviders.class, enabled = true, priority = 0, groups = ExeGroups.Regression)
	public void createCheckoutTestWithReleaseOption(String userName, String userId) throws Exception {

		CheckoutTestHelper checkoutHelper = new CheckoutTestHelper(userName);
		List<Object> dataForCheckoutGlober = checkoutHelper.getRequiredTestDataForCheckout(userName, userId);
		dataForCheckoutGlober.set(5, false);
		JSONObject requestParams = new CheckoutPayloadHelper().createCheckoutPayload(dataForCheckoutGlober);
		Response response = checkoutHelper.createCheckout(requestParams);
		validateResponseToContinueTest(response, 200, "Checkout not created successfully", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		
		assertTrue(message.contains("Success: Checkout Request submitted. Please contact your People Champion.") ||
				message.contains("Checkout Request has already been created.") || message.contains("Checkout request submitted and a status email is on its way. For further details, please contact your People Champion."),
				"The message is not success");
		test.log(Status.PASS, "Checkout Request created successfully");
	}
	
	/**
	 * This test will create a checkout for a glober with checkout Date as back date
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsOcampo", dataProviderClass = CheckoutDataProviders.class, enabled = true, priority = 0, groups = ExeGroups.Regression)
	public void createCheckoutTestWithCheckouDateAsBackDate(String userName, String userId) throws Exception {

		CheckoutTestHelper checkoutHelper = new CheckoutTestHelper(userName);
		List<Object> dataForCheckoutGlober = checkoutHelper.getRequiredTestDataForCheckout(userName, userId);
		dataForCheckoutGlober.set(0, "01-05-2020");
		JSONObject requestParams = new CheckoutPayloadHelper().createCheckoutPayload(dataForCheckoutGlober);
		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId).contentType(ContentType.JSON).body(requestParams.toString());
		String url = MyTeamBaseTest.baseUrl+CheckoutEndpoints.createCheckout+requestParams.get("globerId");
		Response response = restUtils.executePOST(requestSpecification, url);
		validateResponseToContinueTest(response, 500, "Checkout created successfully", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		
		assertTrue(message.contains("Checkout Request is not created'. Contact Glow Admin")||
				message.contains("Checkout Request has already been created.") || message.contains("Checkout request submitted and a status email is on its way. For further details, please contact your People Champion."),
				"The message is not failure");
		test.log(Status.PASS, "Checkout Request failed as expected");
	}
	
	/**
	 * This test will create a checkout for a glober without comment 
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsOcampo", dataProviderClass = CheckoutDataProviders.class, enabled = true, priority = 0, groups = ExeGroups.Regression)
	public void createCheckoutTestWithoutComment(String userName, String userId) throws Exception {

		CheckoutTestHelper checkoutHelper = new CheckoutTestHelper(userName);
		List<Object> dataForCheckoutGlober = checkoutHelper.getRequiredTestDataForCheckout(userName, userId);
		dataForCheckoutGlober.set(1, "");
		JSONObject requestParams = new CheckoutPayloadHelper().createCheckoutPayload(dataForCheckoutGlober);
		Response response = checkoutHelper.createCheckout(requestParams);
		validateResponseToContinueTest(response, 200, "Checkout not created successfully", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		
		assertTrue(message.contains("Success: Checkout Request submitted. Please contact your People Champion.") ||
				message.contains("Checkout Request has already been created.") || message.contains("Checkout request submitted and a status email is on its way. For further details, please contact your People Champion."),
				"The message is not success");
		test.log(Status.PASS, "Checkout Request created successfully");
	}
	
	/**
	 * This test will create a checkout for a glober with special character in comment 
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsOcampo", dataProviderClass = CheckoutDataProviders.class, enabled = true, priority = 0, groups = ExeGroups.Regression)
	public void createCheckoutTestWithSpecialCharacterInComment(String userName, String userId) throws Exception {

		CheckoutTestHelper checkoutHelper = new CheckoutTestHelper(userName);
		List<Object> dataForCheckoutGlober = checkoutHelper.getRequiredTestDataForCheckout(userName, userId);
		dataForCheckoutGlober.set(1, "!@#$%^");
		JSONObject requestParams = new CheckoutPayloadHelper().createCheckoutPayload(dataForCheckoutGlober);
		Response response = checkoutHelper.createCheckout(requestParams);
		validateResponseToContinueTest(response, 200, "Checkout not created successfully", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		
		assertTrue(message.contains("Success: Checkout Request submitted. Please contact your People Champion.") ||
				message.contains("Checkout Request has already been created.") || message.contains("Checkout request submitted and a status email is on its way. For further details, please contact your People Champion."),
				"The message is not success");
		test.log(Status.PASS, "Checkout Request created successfully");
	}
	
	/**
	 * This test will create a checkout for a glober with notification date after checkout date
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsOcampo", dataProviderClass = CheckoutDataProviders.class, enabled = true, priority = 0, groups = ExeGroups.Regression)
	public void createCheckoutTestWithNotificationDateAfterCheckoutDate(String userName, String userId) throws Exception {

		CheckoutTestHelper checkoutHelper = new CheckoutTestHelper(userName);
		List<Object> dataForCheckoutGlober = checkoutHelper.getRequiredTestDataForCheckout(userName, userId);
		dataForCheckoutGlober.set(0, Utilities.getTodayDate("dd-MM-yyyy"));
		dataForCheckoutGlober.set(3, Utilities.getFutureDate("dd-MM-yyyy", 4));
		JSONObject requestParams = new CheckoutPayloadHelper().createCheckoutPayload(dataForCheckoutGlober);
		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId).contentType(ContentType.JSON).body(requestParams.toString());
		String url = MyTeamBaseTest.baseUrl+CheckoutEndpoints.createCheckout+requestParams.get("globerId");
		Response response = restUtils.executePOST(requestSpecification, url);
		validateResponseToContinueTest(response, 500, "Checkout created successfully", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		
		assertTrue(message.contains("Checkout Request is not created'. Contact Glow Admin")||
				message.contains("Checkout Request has already been created.") || message.contains("Checkout request submitted and a status email is on its way. For further details, please contact your People Champion."),
				"The message is not failure");
		test.log(Status.PASS, "Checkout Request failed as expected");
	}
	
	/**
	 * This test will create a checkout with replacement for a glober
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = CheckoutDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void createCheckoutWithReplacmentTest(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForReplaceGlober = rotateAndReplaceHelper.getReplaceGloberWithSRTestData(userName, userId);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createReplaceWithSRPayload(dataForReplaceGlober);
		Response response = rotateAndReplaceHelper.postReplaceGlober(requestParams);
		validateResponseToContinueTest(response, 201, "Not Created Replace with SR Payload successfully", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertTrue(message.contains("Success! You have successfully created Staff Request"),
				"Failed in validating message");
		assertEquals(status, "success", "Failed in validating Status");
		test.log(Status.PASS, "You have successfully created Staff Request");

		int srNumberToVerify = rotateAndReplaceHelper.verifyCreatedSRForReplacedGlober(requestParams.get("globerId"),
				requestParams.get("projectId"));

		assertTrue(message.contains(String.valueOf(srNumberToVerify)),
				String.format("Excpected SRNumber %d was not Present in the message '%s'.", srNumberToVerify, message));
		test.log(Status.PASS, "Excpected SRNumber " + srNumberToVerify + " was Present in the message");
		
		CheckoutTestHelper checkoutHelper = new CheckoutTestHelper(userName);
		List<Object> dataForCheckoutGlober = checkoutHelper.getRequiredTestDataForCheckoutWithReplacement(userId,dataForReplaceGlober.get(5).toString(),
				dataForReplaceGlober.get(1).toString());
		requestParams = new CheckoutPayloadHelper().createCheckoutPayload(dataForCheckoutGlober);
		response = checkoutHelper.createCheckout(requestParams);
		validateResponseToContinueTest(response, 200, "Checkout not created successfully", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		
		assertTrue(message.contains("Success: Checkout Request submitted. Please contact your People Champion.") ||
				message.contains("Checkout Request has already been created.") || message.contains("Checkout request submitted and a status email is on its way. For further details, please contact your People Champion."),
				"The message is not success");
		test.log(Status.PASS, "Checkout Request created successfully");
	}
	
	/**
	 * This test will create a checkout with replacement for a shadow glober
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = CheckoutDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void createCheckoutWithReplacmentTestWithShadow(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForReplaceGlober = rotateAndReplaceHelper.getReplaceGloberTestDataForShadowWithSR(userName, userId);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createReplaceWithSRPayload(dataForReplaceGlober);
		Response response = rotateAndReplaceHelper.postReplaceGlober(requestParams);
		validateResponseToContinueTest(response, 201, "Staff request not created successfully successfully", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertTrue(message.contains("Success! You have successfully created Staff Request"),
				"Failed in validating message");
		assertEquals(status, "success", "Failed in validating Status");
		test.log(Status.PASS, "You have successfully created Staff Request");

		int srNumberToVerify = rotateAndReplaceHelper.verifyCreatedSRForReplacedGlober(requestParams.get("globerId"),
				requestParams.get("projectId"));

		assertTrue(message.contains(String.valueOf(srNumberToVerify)),
				String.format("Excpected SRNumber %d was not Present in the message '%s'.", srNumberToVerify, message));
		test.log(Status.PASS, "Excpected SRNumber " + srNumberToVerify + " was Present in the message");
		
		CheckoutTestHelper checkoutHelper = new CheckoutTestHelper(userName);
		List<Object> dataForCheckoutGlober = checkoutHelper.getRequiredTestDataForCheckoutWithReplacement(userId,dataForReplaceGlober.get(5).toString(),
				dataForReplaceGlober.get(1).toString());
		requestParams = new CheckoutPayloadHelper().createCheckoutPayload(dataForCheckoutGlober);
		response = checkoutHelper.createCheckout(requestParams);
		validateResponseToContinueTest(response, 200, "Checkout not created successfully", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		
		assertTrue(message.contains("Success: Checkout Request submitted. Please contact your People Champion.") ||
				message.contains("Checkout Request has already been created.") || message.contains("Checkout request submitted and a status email is on its way. For further details, please contact your People Champion."),
				"The message is not success");
		test.log(Status.PASS, "Checkout Request created successfully");
		
		EditAssignmentHelper editAssignmentHelper = new EditAssignmentHelper(userName);
		List<Object> dataForEditAssignment = Arrays.asList(requestParams.get("projectId"), requestParams.get("globerId"),requestParams.get("notificationDate"));
		requestParams = new EditAssignmentPayloadHelper().editAssignmentPayload(dataForEditAssignment);
		response = editAssignmentHelper.editAssignment(requestParams);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 201, "Unable to edit assignment.", true);
		message = (String) restUtils.getValueFromResponse(response, "message");

		assertEquals(message, "Assignment end date updated successfully.", "Error with user name " + userName);
		test.log(Status.PASS, "Edit assignment date successfully");
	}
	
	/**
	 * This test will create a checkout with replacement with creating SR for shadow glober
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = CheckoutDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void createCheckoutWithReplacmentTestCreatingSRWithShadow(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForReplaceGlober = rotateAndReplaceHelper.getReplaceGloberTestDataForShadowWithSR(userName, userId);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createReplaceWithSRPayload(dataForReplaceGlober);
		Response response = rotateAndReplaceHelper.postReplaceGlober(requestParams);
		validateResponseToContinueTest(response, 201, "Staff request not created successfully successfully", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertTrue(message.contains("Success! You have successfully created Staff Request"),
				"Failed in validating message");
		assertEquals(status, "success", "Failed in validating Status");
		test.log(Status.PASS, "You have successfully created Staff Request");

		int srNumberToVerify = rotateAndReplaceHelper.verifyCreatedSRForReplacedGlober(requestParams.get("globerId"),
				requestParams.get("projectId"));

		assertTrue(message.contains(String.valueOf(srNumberToVerify)),
				String.format("Excpected SRNumber %d was not Present in the message '%s'.", srNumberToVerify, message));
		test.log(Status.PASS, "Excpected SRNumber " + srNumberToVerify + " was Present in the message");
		
		CheckoutTestHelper checkoutHelper = new CheckoutTestHelper(userName);
		List<Object> dataForCheckoutGlober = checkoutHelper.getRequiredTestDataForCheckoutWithReplacement(userId,dataForReplaceGlober.get(5).toString(),
				dataForReplaceGlober.get(1).toString());
		requestParams = new CheckoutPayloadHelper().createCheckoutPayload(dataForCheckoutGlober);
		response = checkoutHelper.createCheckout(requestParams);
		validateResponseToContinueTest(response, 200, "Checkout not created successfully", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		
		assertTrue(message.contains("Success: Checkout Request submitted. Please contact your People Champion.") ||
				message.contains("Checkout Request has already been created.") || message.contains("Checkout request submitted and a status email is on its way. For further details, please contact your People Champion."),
				"The message is not success");
		test.log(Status.PASS, "Checkout Request created successfully");
		
		EditAssignmentHelper editAssignmentHelper = new EditAssignmentHelper(userName);
		List<Object> dataForEditAssignment = Arrays.asList(requestParams.get("projectId"), requestParams.get("globerId"),requestParams.get("notificationDate"));
		requestParams = new EditAssignmentPayloadHelper().editAssignmentPayload(dataForEditAssignment);
		response = editAssignmentHelper.editAssignment(requestParams);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 201, "Unable to edit assignment.", true);
		message = (String) restUtils.getValueFromResponse(response, "message");

		assertEquals(message, "Assignment end date updated successfully.", "Error with user name " + userName);
		test.log(Status.PASS, "Edit assignment date successfully");
	}
	
	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = CheckoutDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression})
	public void createCheckoutWithReplacmentTestWitCheckoutDateAsBackDate(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForReplaceGlober = rotateAndReplaceHelper.getReplaceGloberWithSRTestData(userName, userId);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createReplaceWithSRPayload(dataForReplaceGlober);
		Response response = rotateAndReplaceHelper.postReplaceGlober(requestParams);
		validateResponseToContinueTest(response, 201, "Staff Request not created successfully", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertTrue(message.contains("Success! You have successfully created Staff Request"),
				"Failed in validating message");
		assertEquals(status, "success", "Failed in validating Status");
		test.log(Status.PASS, "You have successfully created Staff Request");

		int srNumberToVerify = rotateAndReplaceHelper.verifyCreatedSRForReplacedGlober(requestParams.get("globerId"),
				requestParams.get("projectId"));

		assertTrue(message.contains(String.valueOf(srNumberToVerify)),
				String.format("Excpected SRNumber %d was not Present in the message '%s'.", srNumberToVerify, message));
		test.log(Status.PASS, "Excpected SRNumber " + srNumberToVerify + " was Present in the message");
		
		CheckoutTestHelper checkoutHelper = new CheckoutTestHelper(userName);
		List<Object> dataForCheckoutGlober = checkoutHelper.getRequiredTestDataForCheckoutWithReplacement(userId,dataForReplaceGlober.get(5).toString(),
				dataForReplaceGlober.get(1).toString());
		dataForCheckoutGlober.set(0, "01-05-2020");
		requestParams = new CheckoutPayloadHelper().createCheckoutPayload(dataForCheckoutGlober);
		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId).contentType(ContentType.JSON).body(requestParams.toString());
		String url = MyTeamBaseTest.baseUrl+CheckoutEndpoints.createCheckout+requestParams.get("globerId");
		response = restUtils.executePOST(requestSpecification, url);
		validateResponseToContinueTest(response, 500, "Checkout Request is created", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		
		assertTrue(message.contains("Checkout Request is not created'. Contact Glow Admin")||
				message.contains("Checkout Request has already been created."),
				"The message is not failure");
		test.log(Status.PASS, "Checkout Request failed as expected");
	}
	
	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = CheckoutDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression})
	public void createCheckoutWithReplacmentTestWithoutComment(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForReplaceGlober = rotateAndReplaceHelper.getReplaceGloberWithSRTestData(userName, userId);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createReplaceWithSRPayload(dataForReplaceGlober);
		Response response = rotateAndReplaceHelper.postReplaceGlober(requestParams);
		validateResponseToContinueTest(response, 201, "Staff Request not created successfully", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertTrue(message.contains("Success! You have successfully created Staff Request"),
				"Failed in validating message");
		assertEquals(status, "success", "Failed in validating Status");
		test.log(Status.PASS, "You have successfully created Staff Request");

		int srNumberToVerify = rotateAndReplaceHelper.verifyCreatedSRForReplacedGlober(requestParams.get("globerId"),
				requestParams.get("projectId"));

		assertTrue(message.contains(String.valueOf(srNumberToVerify)),
				String.format("Excpected SRNumber %d was not Present in the message '%s'.", srNumberToVerify, message));
		test.log(Status.PASS, "Excpected SRNumber " + srNumberToVerify + " was Present in the message");
		
		CheckoutTestHelper checkoutHelper = new CheckoutTestHelper(userName);
		List<Object> dataForCheckoutGlober = checkoutHelper.getRequiredTestDataForCheckoutWithReplacement(userId,dataForReplaceGlober.get(5).toString(),
				dataForReplaceGlober.get(1).toString());
		dataForCheckoutGlober.set(1, "");
		requestParams = new CheckoutPayloadHelper().createCheckoutPayload(dataForCheckoutGlober);
		response = checkoutHelper.createCheckout(requestParams);
		validateResponseToContinueTest(response, 200, "Checkout not created successfully", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		
		assertTrue(message.contains("Success: Checkout Request submitted. Please contact your People Champion.") ||
				message.contains("Checkout Request has already been created.") || message.contains("Checkout request submitted and a status email is on its way. For further details, please contact your People Champion."),
				"The message is not success");
		test.log(Status.PASS, "Checkout Request created successfully");
	}
	
	/**
	 * This test will create a checkoutWithReplacement for a glober with special character in comment 
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = CheckoutDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression})
	public void createCheckoutWithReplacmentTestWithSpecialCharacterInComment(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForReplaceGlober = rotateAndReplaceHelper.getReplaceGloberWithSRTestData(userName, userId);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createReplaceWithSRPayload(dataForReplaceGlober);
		Response response = rotateAndReplaceHelper.postReplaceGlober(requestParams);
		validateResponseToContinueTest(response, 201, "Staff Request not created successfully", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertTrue(message.contains("Success! You have successfully created Staff Request"),
				"Failed in validating message");
		assertEquals(status, "success", "Failed in validating Status");
		test.log(Status.PASS, "You have successfully created Staff Request");

		int srNumberToVerify = rotateAndReplaceHelper.verifyCreatedSRForReplacedGlober(requestParams.get("globerId"),
				requestParams.get("projectId"));

		assertTrue(message.contains(String.valueOf(srNumberToVerify)),
				String.format("Excpected SRNumber %d was not Present in the message '%s'.", srNumberToVerify, message));
		test.log(Status.PASS, "Excpected SRNumber " + srNumberToVerify + " was Present in the message");
		
		CheckoutTestHelper checkoutHelper = new CheckoutTestHelper(userName);
		List<Object> dataForCheckoutGlober = checkoutHelper.getRequiredTestDataForCheckoutWithReplacement(userId,dataForReplaceGlober.get(5).toString(),
				dataForReplaceGlober.get(1).toString());
		dataForCheckoutGlober.set(1, "!@#$%^");
		requestParams = new CheckoutPayloadHelper().createCheckoutPayload(dataForCheckoutGlober);
		response = checkoutHelper.createCheckout(requestParams);
		validateResponseToContinueTest(response, 200, "Checkout not created successfully", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		
		assertTrue(message.contains("Success: Checkout Request submitted. Please contact your People Champion.") ||
				message.contains("Checkout Request has already been created.") || message.contains("Checkout request submitted and a status email is on its way. For further details, please contact your People Champion."),
				"The message is not success");
		test.log(Status.PASS, "Checkout Request created successfully");
	}
	
	/**
	 * This test will create a checkoutWithReplacement for glober with notification date after checkout date
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = CheckoutDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression})
	public void createCheckoutWithReplacmentTestWithNotificationDateAfterCheckoutDate(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForReplaceGlober = rotateAndReplaceHelper.getReplaceGloberWithSRTestData(userName, userId);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createReplaceWithSRPayload(dataForReplaceGlober);
		Response response = rotateAndReplaceHelper.postReplaceGlober(requestParams);
		validateResponseToContinueTest(response, 201, "Staff Request not created successfully", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertTrue(message.contains("Success! You have successfully created Staff Request"),
				"Failed in validating message");
		assertEquals(status, "success", "Failed in validating Status");
		test.log(Status.PASS, "You have successfully created Staff Request");

		int srNumberToVerify = rotateAndReplaceHelper.verifyCreatedSRForReplacedGlober(requestParams.get("globerId"),
				requestParams.get("projectId"));

		assertTrue(message.contains(String.valueOf(srNumberToVerify)),
				String.format("Excpected SRNumber %d was not Present in the message '%s'.", srNumberToVerify, message));
		test.log(Status.PASS, "Excpected SRNumber " + srNumberToVerify + " was Present in the message");
		
		CheckoutTestHelper checkoutHelper = new CheckoutTestHelper(userName);
		List<Object> dataForCheckoutGlober = checkoutHelper.getRequiredTestDataForCheckoutWithReplacement(userId,dataForReplaceGlober.get(5).toString(),
				dataForReplaceGlober.get(1).toString());
		dataForCheckoutGlober.set(0, "01-05-2020");
		requestParams = new CheckoutPayloadHelper().createCheckoutPayload(dataForCheckoutGlober);
		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId).contentType(ContentType.JSON).body(requestParams.toString());
		String url = MyTeamBaseTest.baseUrl+CheckoutEndpoints.createCheckout+requestParams.get("globerId");
		response = restUtils.executePOST(requestSpecification, url);
		validateResponseToContinueTest(response, 500, "Checkout Request is created", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		
		assertTrue(message.contains("Checkout Request is not created'. Contact Glow Admin")||
				message.contains("Checkout Request has already been created."),
				"The message is not failure");
		test.log(Status.PASS, "Checkout Request failed as expected");
	}

	/**
	 * This test will add skills when creating a checkout with replacement
	 * 
	 * @param userName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "ddRolDetailsCarlaVeneziano", dataProviderClass = CheckoutDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void addSkillsWhenCreatingCheckoutWithReplacement(String userName, String userId) throws Exception {

		int requestGloberId, requestSubmitterId, requestProjectId, reponseGloberId, reponseSubmitterId,
				reponseProjectId, srNumberToVerify, responseStatusCode, responseResultCode;
		String responseNotificationDate, responseCheckoutDate, statusCode, responseComment, requestNotificationDate,
				requestCheckoutDate, requestComment = null;
		boolean responseRetain, requestRetain, validData = false;

		// Perform Replace
		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForReplaceGlober = rotateAndReplaceHelper.addSkillsCheckoutWithReplaceGloberTestData(userName,
				userId);
		JSONObject requestParams = new RotateAndReplacePayloadHelper()
				.addSkillsForCheckoutWithReplacePayload(dataForReplaceGlober);
		Response response = rotateAndReplaceHelper.postReplaceGlober(requestParams);

		// Validate Response
		validateResponseToContinueTest(response, 201, "Staff request not created successfully successfully", true);
		statusCode = (String) restUtils.getValueFromResponse(response, "statusCode");
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");
		validData = (boolean) restUtils.getValueFromResponse(response, "validData");

		assertEquals(statusCode, "CREATED", "Failed in validating Status Code");
		assertEquals(status, "success", "Failed in validating Status");
		assertTrue(message.contains("Success! You have successfully created Staff Request"),
				"Failed in validating message");
		assertEquals(validData, true, "Failed in validating validData");
		test.log(Status.PASS, "You have successfully created Staff Request");

		srNumberToVerify = rotateAndReplaceHelper.verifyCreatedSRForReplacedGlober(requestParams.get("globerId"),
				requestParams.get("projectId"));
		assertTrue(message.contains(String.valueOf(srNumberToVerify)),
				String.format("Excpected SRNumber %d was not Present in the message '%s'.", srNumberToVerify, message));
		test.log(Status.PASS, "Excpected SRNumber " + srNumberToVerify + " was Present in the message");

		// Perform Checkout
		CheckoutTestHelper checkoutHelper = new CheckoutTestHelper(userName);
		List<Object> dataForCheckoutGlober = checkoutHelper.getRequiredTestDataForCheckoutWithReplacement(userId,
				dataForReplaceGlober.get(3).toString(), dataForReplaceGlober.get(2).toString());
		JSONObject requestParamsCheckout = new CheckoutPayloadHelper()
				.addSkillsCreateCheckoutPayload(dataForCheckoutGlober);
		response = checkoutHelper.createCheckout(requestParamsCheckout);

		// Validate Response
		validateResponseToContinueTest(response, 200, "Checkout not created successfully", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		assertTrue(message.contains("Success: Checkout Request submitted. Please contact your People Champion.")
				|| message.contains("Checkout Request has already been created.")
				|| message.contains(
						"Checkout request submitted and a status email is on its way. For further details, please contact your People Champion."),
				"The message is not success");
		test.log(Status.PASS, "Checkout Request created successfully");

		responseStatusCode = (int) restUtils.getValueFromResponse(response, "statusCode");
		status = (String) restUtils.getValueFromResponse(response, "status");
		validData = (boolean) restUtils.getValueFromResponse(response, "validData");
		reponseGloberId = (int) restUtils.getValueFromResponse(response, "$.details.globerId");
		reponseSubmitterId = (int) restUtils.getValueFromResponse(response, "$.details.submitterId");
		responseNotificationDate = (String) restUtils.getValueFromResponse(response, "$.details.notificationDate");
		responseCheckoutDate = (String) restUtils.getValueFromResponse(response, "$.details.checkoutDate");
		responseComment = (String) restUtils.getValueFromResponse(response, "$.details.comment");
		reponseProjectId = (int) restUtils.getValueFromResponse(response, "$.details.projectId");
		responseRetain = (boolean) restUtils.getValueFromResponse(response, "$.details.retain");

		requestGloberId = Integer.valueOf((String) requestParamsCheckout.get("globerId"));
		requestSubmitterId = Integer.valueOf((String) requestParamsCheckout.get("submitterId"));
		requestProjectId = Integer.valueOf((String) requestParamsCheckout.get("projectId"));
		requestNotificationDate = (String) requestParamsCheckout.get("notificationDate");
		requestCheckoutDate = (String) requestParamsCheckout.get("checkoutDate");
		requestComment = (String) requestParamsCheckout.get("comment");
		requestRetain = (boolean) requestParamsCheckout.get("retain");

		assertEquals(responseStatusCode, 200, "Failed in validating Status Code");
		assertEquals(status, "success", "Failed in validating Status");
		assertEquals(validData, true, "Failed in validating validData");
		assertEquals(requestGloberId, reponseGloberId, "Failed in validating Glober Id");
		assertEquals(requestSubmitterId, reponseSubmitterId, "Failed in validating Submitter Id");
		assertEquals(requestNotificationDate, responseNotificationDate, "Failed in validating Notification Date");
		assertEquals(requestCheckoutDate, responseCheckoutDate, "Failed in validating Checkout Date");
		assertEquals(requestComment, responseComment, "Failed in validating Comment");
		assertEquals(requestProjectId, reponseProjectId, "Failed in validating Project Id");
		assertEquals(requestRetain, responseRetain, "Failed in validating Retain");

		// Edit Assignment Date
		EditAssignmentHelper editAssignmentHelper = new EditAssignmentHelper(userName);
		List<Object> dataForEditAssignment = Arrays.asList(requestParams.get("projectId"),
				requestParams.get("globerId"), requestParams.get("newAssignmentEndDate"));
		requestParams = new EditAssignmentPayloadHelper().editAssignmentPayload(dataForEditAssignment);
		response = editAssignmentHelper.editAssignment(requestParams);

		// Validate Response
		validateResponseToContinueTest(response, 201, "Unable to edit assignment.", true);
		responseResultCode = (int) restUtils.getValueFromResponse(response, "resultCode");
		message = (String) restUtils.getValueFromResponse(response, "message");

		assertEquals(responseResultCode, 201, "Failed in validating Result Code");
		assertEquals(message, "Assignment end date updated successfully.", "Error with user name " + userName);
		test.log(Status.PASS, "Edited assignment date successfully");

	}
}
