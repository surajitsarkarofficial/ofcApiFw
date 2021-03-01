package tests.testhelpers.submodules.globers.features;

import java.sql.SQLException;
import java.util.List;

import database.submodules.globers.features.GlobalTalentPoolDBHelper;
import endpoints.submodules.globers.features.GlobalTalentPoolEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import properties.StaffingProperties;
import tests.testcases.submodules.globers.GlobersBaseTest;
import tests.testhelpers.submodules.globers.GlobersTestHelper;
import utils.RestUtils;
import utils.Utilities;

public class GlobalTalentPoolTestHelper extends GlobersTestHelper {
	String startDate=null;
	String endDate=null;
	String globerId=null;
	GlobalTalentPoolDBHelper globalTalentPoolDBHelper;
	String dbName = StaffingProperties.database;
	String dbUserName = StaffingProperties.dbUser;
	String password = StaffingProperties.dbPassword;

	public GlobalTalentPoolTestHelper(String username) throws Exception {
		super(username);
		globalTalentPoolDBHelper = new GlobalTalentPoolDBHelper();
	}


	/**
	 * This method will response of count of talent pool globers
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public Response getGlobalTalentPoolCountRespone(String username) throws Exception {
		RestUtils restUtils = new RestUtils();
		Response globalTPCountResponse = null;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(GlobersBaseTest.sessionId);

		startDate = Utilities.getTodayDate("dd-MM-yyyy");
		endDate = Utilities.getFutureDate("dd-MM-yyyy", 30);
		String globerId = globalTalentPoolDBHelper.getGloberId(username);

		String globalTPCountUrl = GlobersBaseTest.baseUrl + String.format(GlobalTalentPoolEndpoints.globalTalentPoolCountUrl, 2,globerId, 2,0,50,"Aposition",startDate, endDate,"true");
		globalTPCountResponse = restUtils.executeGET(requestSpecification, globalTPCountUrl);
		return globalTPCountResponse;
	}

	/**
	 * This method will return total count of glober talent pool from database
	 * @return
	 * @throws SQLException
	 */
	public int getGlobalTalentPoolCountDBResponse() throws SQLException {
		List<String> globalTPDBCountList = globalTalentPoolDBHelper.getGlobalTalentPoolCount();
		int globalTPDBCount = globalTPDBCountList.size();
		return globalTPDBCount;
	}
}