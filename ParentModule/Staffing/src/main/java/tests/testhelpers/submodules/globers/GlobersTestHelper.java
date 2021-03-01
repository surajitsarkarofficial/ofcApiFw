package tests.testhelpers.submodules.globers;

import java.sql.SQLException;

import database.submodules.globers.features.SoonTobeGloberDBHelper;
import endpoints.submodules.globers.features.SoonTobeGlobersEndPoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.submodules.globers.GlobersBaseTest;
import utils.RestUtils;
import utils.Utilities;

public class GlobersTestHelper {

	SoonTobeGloberDBHelper stgDBHelper;
	RestUtils restUtils = new RestUtils();
	
	public GlobersTestHelper(String userName) throws Exception {
		new GlobersBaseTest().createSession(userName);
		stgDBHelper = new SoonTobeGloberDBHelper();
	}
	
	/**
	 * This test will verify list of globers type in filters based on talent pool
	 * data
	 * 
	 * @param response
	 * @param name
	 * @throws Exception
	 */
	public Response getFiltersSoonToBeGlobersTalenPoolGrids(Response response, String name) throws Exception {
		String minDate = Utilities.getTodayDate("dd-MM-yyyy");
		String maxDate = Utilities.getFutureDate("dd-MM-yyyy", 30);
		String userId = stgDBHelper.getGloberId(name);

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String requestUrl = GlobersBaseTest.baseUrl
				+ String.format(SoonTobeGlobersEndPoints.filtersoonToBeGloberTalenPoolGrids, userId, "&benchDateRange=",
						minDate, maxDate);

		response = restUtils.executeGET(requestSpecification, requestUrl);
		return response;
	}
	
	/**
	 * This method will return gatekeeper name from Db
	 * 
	 * @param candidateId
	 * @return String
	 * @throws SQLException
	 */
	public String getGateKeeperNameDb(String candidateId) throws SQLException {
		String gateKeeperName = stgDBHelper.getGateKeeperName(candidateId);
		return gateKeeperName;
	}
	
	/**
	 * This method will get gatekeeper name with recruiter and return response
	 * 
	 * @param response
	 * @param candiadteId
	 * @return
	 */
	public Response getGateKeeperNameAndRecruiter(Response response, String candiadteId) {
		String evaluationResult = "true";
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String gateKeeperRecruiterDetailUrl = GlobersBaseTest.baseUrl
				+ String.format(SoonTobeGlobersEndPoints.gateKeeperRecruiterDetailUrl, candiadteId, evaluationResult);

		response = restUtils.executeGET(requestSpecification, gateKeeperRecruiterDetailUrl);

		return response;
	}
}
