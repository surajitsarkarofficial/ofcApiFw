package tests.testhelpers;

import com.aventstack.extentreports.Status;

import endpoints.submodules.staffRequest.features.GlobalStaffRequestAPIEndPoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.GlowBaseTest;
import tests.GlowBaseTestHelper;
import tests.testcases.StaffingBaseTest;
import utils.ExtentHelper;
import utils.RestUtils;

public class StaffingTestHelper extends GlowBaseTestHelper{
	
	protected RestUtils restUtils;

	public StaffingTestHelper() {
		restUtils=restUtils==null?new RestUtils():this.restUtils;
	}
	
	/**
	 * This method will get SR details from grid
	 * 
	 * @param name
	 * @param globerType
	 * @return
	 * @throws Exception
	 */
	public Response getSRDetailsFromGrid(String name, String globerType) throws Exception {
		int viewId = 7, parentViewId = 7, offset = 0, limit = 50;
		String sorting = "Astage";

		String requestUrl = StaffingBaseTest.baseUrl + String.format(GlobalStaffRequestAPIEndPoints.srGrid, viewId,
				name, parentViewId, offset, limit, sorting);

		RestUtils restUtils = new RestUtils();

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId);

		Response response = restUtils.executeGET(requestSpecification, requestUrl);
		validateResponseToContinueTest(response, 200, "Issue With GET API response.", true);

		ExtentHelper.test.log(Status.INFO, "Fetched all SR Details from Grid");
		ExtentHelper.test.log(Status.INFO, "Able to load SR Grid.");
		return response;
	}
}
