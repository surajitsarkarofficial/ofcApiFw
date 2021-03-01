package tests.testcases.myTeam.features;

import java.util.List;

import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import dataproviders.myTeam.MyTeamDataProviders;
import io.restassured.response.Response;
import payloads.myTeam.features.SuggestAndAssignPayloadHelper;
import tests.testcases.myTeam.MyTeamBaseTest;
import tests.testhelpers.myTeam.features.SuggestAndAssignTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;

/**
 * @author poonam.kadam
 *
 */

public class SuggestAndAssignTests extends MyTeamBaseTest {

	RestUtils restUtils = new RestUtils();
	String message, status, statusCode = null;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("SuggestAndAssignTests");
	}

	/**
	 * This test will do quick suggest and assign
	 * 
	 * @param userName
	 * @param userId
	 * @throws Exception
	 */

	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = MyTeamDataProviders.class, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void suggestAndAssign(String userName, String userId) throws Exception {
		int globalId, positionId, globerId;
		SoftAssert softAssert = new SoftAssert();
		SuggestAndAssignTestHelper suggestAndAssignHelper = new SuggestAndAssignTestHelper(userName);
		
		// Fetch test data
		List<Object> dataForSuggestAndAssign = suggestAndAssignHelper.getRequiredTestDataForSuggestAndAssign(userName,
				userId);
		globalId = (int) dataForSuggestAndAssign.get(4);
		positionId = (int) dataForSuggestAndAssign.get(5);
		globerId = (int) dataForSuggestAndAssign.get(6);

		// If needed test data is empty fail TC
		if ((globalId != 0) && (positionId != 0) && (globerId != 0)) {
			JSONObject requestParams = new SuggestAndAssignPayloadHelper()
					.createSuggestAndAssignPayload(dataForSuggestAndAssign);

			Response response = suggestAndAssignHelper.postSuggestAndAssign(requestParams);
			new MyTeamBaseTest().validateResponseToContinueTest(response, 201, "Unable to post suggest and assign .",
					true);

			statusCode = (String) restUtils.getValueFromResponse(response, "statusCode");
			status = (String) restUtils.getValueFromResponse(response, "status");
			message = (String) restUtils.getValueFromResponse(response, "message");
			boolean validData = (boolean) restUtils.getValueFromResponse(response, "validData");

			// Validate response
			softAssert.assertEquals(status, "success", "Failed in validating status");
			softAssert.assertEquals(statusCode, "CREATED", "Failed in validating statusCode");
			softAssert.assertTrue(message.contains("Assignment created successfully."), "Failed in validating message");
			softAssert.assertEquals(validData, true, "Failed in validating validData");
			softAssert.assertAll();
			test.log(Status.PASS, "Successfully performed quick suggest and assign");
		} else {	
			test.log(Status.FAIL, "No open positions found for performing quick suggest and assign");
			throw new Exception("No open positions found for performing quick suggest and assign");
		}
	}
}
