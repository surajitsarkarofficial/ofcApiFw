package tests.testhelpers.submodules.staffRequest.features.globalstaffrequest;

import java.sql.SQLException;
import org.testng.Reporter;

import database.submodules.staffRequest.StaffRequestDBHelper;
import database.submodules.staffRequest.features.globalstaffrequest.MatchingSummaryDBHelper;
import endpoints.submodules.staffRequest.StaffRequestEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import tests.testhelpers.submodules.staffRequest.StaffRequestTestHelper;

/**
 * @author akshata.dongare
 */
public class MatchingSummaryTestHelper extends StaffRequestTestHelper {
	
	StaffRequestDBHelper staffRequestDBHelper;
	
	public MatchingSummaryTestHelper(String userName) throws Exception {
		super(userName);
		staffRequestDBHelper = new StaffRequestDBHelper();
	}

	/**
	 * This method returns response of a column names of gloabl and my staff request grid APIs
	 * @return response
	 */
	public Response gridColumnName() {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(StaffRequestBaseTest.sessionId);
		String gridCoumnNameUrl = StaffRequestBaseTest.baseUrl + String.format(StaffRequestEndpoints.gridColumnNamesUrl, 7);
		Response gridColumnNameResponse = restUtils.executeGET(requestSpecification, gridCoumnNameUrl);
		return gridColumnNameResponse;
	}
	
	/**
	 * This method returns response of SR having NA record in the availableMatching glober
	 * @param userName
	 * @return String
	 * @throws Exception
	 */
	public String checkNAResponse(String userName) throws Exception{
		String srWithNA = null;
		int viewId = 7, parentViewId = 7, offset = 0;
		String globerId = staffRequestDBHelper.getGloberId(userName);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(StaffRequestBaseTest.sessionId);
		String gridCountUrl = StaffRequestBaseTest.baseUrl + String.format(StaffRequestEndpoints.gridTotalCount, 7, globerId, 7, 0, 50, "Astage", true);
		Response gridTotalCountResponse = restUtils.executeGET(requestSpecification, gridCountUrl);
		validateResponseToContinueTest(gridTotalCountResponse, 200, "Grid total count API call was not successful", true);
		
		String gridCountJsonPath = "details.totalCount";
		int gridTotalCount = (int) restUtils.getValueFromResponse(gridTotalCountResponse, gridCountJsonPath);
		
		String gridDataUrl = StaffRequestBaseTest.baseUrl + String.format(StaffRequestEndpoints.srGrid, viewId, globerId, parentViewId, offset, gridTotalCount, "Astage");
		Response gridDataResponse = restUtils.executeGET(requestSpecification, gridDataUrl);
		validateResponseToContinueTest(gridDataResponse, 200, "Grid data API call was not successful", true);
		net.minidev.json.JSONArray SRsWithNAResponse = com.jayway.jsonpath.JsonPath.read(gridDataResponse.asString(),"details.[?(@.availableMatchingGlobers=='N/A')].srNumber");
		
		if(SRsWithNAResponse.size()==0) {
			Reporter.log("0 SRs with NA records",true);
		}else {
			srWithNA = SRsWithNAResponse.get(0).toString();	
		}
		return srWithNA;
	}
	
	/**
	 * Check sr with NA response from db
	 * @param sr
	 * @return String
	 * @throws SQLException
	 */
	public String gridNASrFromDb(String sr) throws SQLException {
		MatchingSummaryDBHelper matchingSummaryDBHelper = new MatchingSummaryDBHelper();
		String srWithNAResponse = matchingSummaryDBHelper.getSrRecordForNAResponse(sr);
		return srWithNAResponse;
	}
}
