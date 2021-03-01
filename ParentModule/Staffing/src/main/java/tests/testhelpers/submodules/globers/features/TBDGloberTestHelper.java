package tests.testhelpers.submodules.globers.features;

import java.sql.SQLException;
import java.util.Random;

import database.submodules.globers.features.TBDGloberDBHelper;
import endpoints.submodules.globers.features.TBDGloberEndPoints;
import endpoints.submodules.globers.features.TdcAPIEndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.globers.TBDGloberPayloadHelper;
import tests.testcases.StaffingBaseTest;
import tests.testcases.submodules.globers.GlobersBaseTest;
import tests.testcases.submodules.reports.ReportsBaseTest;
import tests.testhelpers.submodules.globers.GlobersTestHelper;
import utils.RestUtils;

/**
 * @author shadab.waikar
 * */
public class TBDGloberTestHelper extends GlobersTestHelper{

	String activeTbdGlober;
	String globerId;
	TBDGloberDBHelper tbdGlober;
	TBDGloberPayloadHelper tbdGloberPayload;
	RestUtils restUtils;
	
	public TBDGloberTestHelper(String userName) throws Exception {
		super(userName);
		restUtils = new RestUtils();
		tbdGlober = new TBDGloberDBHelper();
		tbdGloberPayload = new TBDGloberPayloadHelper();
	}

	/**
	 * This method will perform GET to search TBD glober while quick suggest and assign
	 * 
	 * @param response
	 * @return {@link Response}
	 * @throws SQLException 
	 */
	public Response suggestTBDGloberWithPMTD(String username) throws SQLException {
		int viewId = 5,offset = 0,limit = 5;
		String type = "Glober",isExcludeGlober = "true";
		activeTbdGlober = tbdGlober.getActiveTBDGlober();
		String key = tbdGlober.getGloberFullName(activeTbdGlober);
		
		String requestUrl = GlobersBaseTest.baseUrl
				+ String.format(TBDGloberEndPoints.getTBDGloberSearchResult,viewId,username,isExcludeGlober,key.replaceAll(" ", "%20"),offset,limit,type);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);
		Response response = restUtils.executeGET(requestSpecification, requestUrl);
		return response;
	}
	
	/**
	 * This method will perform POST to validate tbd glober in dynamic search
	 * @return {@link Response}
	 * @throws SQLException 
	 */
	public Response validateTbdGloberInDynamicSearch() throws SQLException {
		activeTbdGlober = tbdGlober.getActiveTBDGlober();
		String tbdGloberData[] = tbdGlober.getTBDGloberDataForDynamicSearchById(activeTbdGlober);
		
		String json = tbdGloberPayload.searchTbdGloberViaDynamicSearchPayload(tbdGloberData[0], tbdGloberData[1]);
		String requestUrl = StaffingBaseTest.baseUrl + TBDGloberEndPoints.getTBDGloberSearchResultInDynamicSearch;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId).contentType(ContentType.JSON).body(json);
	    Response tbdGloberDynamicSearchResponse = restUtils.executePOST(requestSpecification, requestUrl);
		return tbdGloberDynamicSearchResponse;
	}
	
	/**
	 * This method will perform POST to validate tbd glober in reverse search
	 * @return {@link Response}
	 * @throws SQLException 
	 */
	public Response validateTbdGloberResultInReverseSearch() throws SQLException {
		activeTbdGlober = tbdGlober.getActiveTBDGlober();
		String key = tbdGlober.getGloberFullName(activeTbdGlober);
		
		String requestUrl = StaffingBaseTest.baseUrl + String.format(TBDGloberEndPoints.getTBDGloberSearchResultInReverseSearch,key.replaceAll(" ","%20"));
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);
		Response reverseSearchResponse = restUtils.executeGET(requestSpecification, requestUrl);
		return reverseSearchResponse;
	}
	
	/**
	 * This method will return actibe TBD Glober
	 * @return {@link String}
	 * @throws SQLException 
	 */
	public String getActiveTbdGlober() {
		return activeTbdGlober;
	}
	
	/**
	 * This method will perform GET to check TBD glober in timeline search
	 * @param username
	 * @return {@link Response}
	 * @throws SQLException 
	 */
	public Response validateTbdGloberIntimelineSearch(String username) throws SQLException {
		Response tbdGloberTimelineSearchResponse = suggestTBDGloberWithPMTD(username);
		return tbdGloberTimelineSearchResponse;
	}
	
	/**
	 * This method will perform POST to validate warning msg while suggesting Tbd Glober from srGrid
	 * @return {@link Response}
	 * @throws SQLException 
	 */
	public Response suggestTbdGloberFromSrGrid() throws SQLException {
		String isTbdGloberWaringMsgRequired = "true";
		activeTbdGlober = tbdGlober.getActiveTBDGlober();
		String requestUrl = GlobersBaseTest.baseUrl
				+ String.format(TBDGloberEndPoints.suggestTBDGlober,activeTbdGlober,isTbdGloberWaringMsgRequired);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);
		Response response = restUtils.executeGET(requestSpecification, requestUrl);
		return response;
	}
	
	/**
	 * This method will perform POST to mark glober as Tbd
	 * @param username
	 * @return {@link Response}
	 * @throws Exception 
	 * @throws SQLException 
	 */
	public Response markGloberAsTbd(String username) throws Exception {
		int viewId = 5,offset = 0,limit = 50,parentViewId = 5;
		String type = "Glober",sorting = "Aposition";
		int i = new Random().nextInt(15);
				
		String requestUrl = GlobersBaseTest.baseUrl
				+ String.format(TdcAPIEndPoints.getAllActiveGlobers,viewId,username,parentViewId,offset,limit,sorting,type);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);
		Response allGloberResponse = restUtils.executeGET(requestSpecification, requestUrl);
		new GlobersBaseTest().validateResponseToContinueTest(allGloberResponse, 200, "Unable to load All Globers Page.", true);
		
		globerId = restUtils.getValueFromResponse(allGloberResponse, "details.globerDTOList["+i+"].globerId").toString();
		System.out.println("GloberId : "+globerId);
		String authorId = tbdGlober.getGloberId(username);
			
		String json = tbdGloberPayload.markGloberAsTbdPayload(authorId, globerId);
		String markTbdUrl = StaffingBaseTest.baseUrl + TBDGloberEndPoints.markGloberAsTbd;
		RequestSpecification markTbdRequestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId).contentType(ContentType.JSON).body(json);
	    Response tbdGloberDynamicSearchResponse = restUtils.executePOST(markTbdRequestSpecification, markTbdUrl);
		return tbdGloberDynamicSearchResponse;
	}
	
	/**
	 * This method will update TBd Glober status
	 * @return {@link Integer} 
	 * @throws SQLException 
	 */
	public Integer updateTbdGloberStatus() throws SQLException {
		return tbdGlober.updateTbdGloberStatus(globerId);
	}
}
