package tests.testcases.myTeam.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.myTeam.MyTeamDataProviders;
import io.restassured.response.Response;
import payloads.myTeam.features.MainPMPayloadHelper;
import tests.testcases.myTeam.MyTeamBaseTest;
import tests.testhelpers.myTeam.features.MainPMTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;

/**
 * @author pooja.kakade
 *
 */

public class MainPMTests extends MyTeamBaseTest{
		
	RestUtils restUtils = new RestUtils();

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("MainPMTests");
	}
	
	/***
	 * @author pooja.kakade
	 *  
	 * This test will assign Main PM 
	 * 
	 * @param userName
	 * @param userId
	 * @throws Exception
	 *
	 */

	@Test(dataProvider = "pmRolDetailsOcampo", dataProviderClass = MyTeamDataProviders.class, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void assignMainPM(String userName, String userId) throws Exception {
		
		MainPMTestHelper mainPMHelper = new MainPMTestHelper(userName);
		List<Object> dataForMainPM = mainPMHelper.getRequiredTestDataForMainPM(userName, userId);
		JSONObject requestParams = new MainPMPayloadHelper().createMainPMPayload(dataForMainPM);
		Response response = mainPMHelper.postMainPM(requestParams);
		String status = (String) restUtils.getValueFromResponse(response, "status");
		String message = (String) restUtils.getValueFromResponse(response, "message");
		assertEquals(status, "success");
		assertTrue(message.contains("Is Successfully Marked As Main Project Manager"));
	}
}