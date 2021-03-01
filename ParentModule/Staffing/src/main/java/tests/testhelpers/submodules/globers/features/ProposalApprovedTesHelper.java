package tests.testhelpers.submodules.globers.features;

import endpoints.submodules.globers.features.TdcAPIEndPoints;
import endpoints.submodules.staffRequest.features.SuggestGloberEndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.globers.ProposalApprovedPayloadHelper;
import tests.testcases.StaffingBaseTest;
import tests.testcases.submodules.globers.GlobersBaseTest;
import tests.testhelpers.submodules.globers.GlobersTestHelper;
import tests.testhelpers.submodules.staffRequest.features.globalstaffrequest.QuickSuggestAndAssignTestHelper;
import utils.RestUtils;

/**
 * 
 * @author shadab.waikar
 *
 */
public class ProposalApprovedTesHelper extends GlobersTestHelper{

	QuickSuggestAndAssignTestHelper suggestHelper;
	ProposalApprovedPayloadHelper stgDbHelper;
	RestUtils utils;
	public ProposalApprovedTesHelper(String userName) throws Exception {
		super(userName);
		utils = new RestUtils();
		suggestHelper = new QuickSuggestAndAssignTestHelper(userName);	
		stgDbHelper = new ProposalApprovedPayloadHelper();
	}

	/**
	 * This method will create plan for proposal approved stg glober.
	 * @param response
	 * @param username
	 * @param globerType
	 * @return {@link Response}
	 * @throws Exception 
	 */
	public Response suggestProposalApprovedSTG(Response response, String username, String globerType) throws Exception {		
		String requestUrl = null,json = null;
		RequestSpecification requestSpecification;
		suggestHelper.getSrDateDetails(response);
		suggestHelper.checkIfPlansPerSrMoreThanThree(response, username, globerType);
		
		json = stgDbHelper.suggestSTGPayload(QuickSuggestAndAssignTestHelper.srNumber, globerType);
		requestUrl = StaffingBaseTest.baseUrl + SuggestGloberEndPoints.suggestGloberUrl;
		requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId).contentType(ContentType.JSON).body(json);
		response = utils.executePOST(requestSpecification, requestUrl);
		return response;
	}
	
	/**
	 * This method will filter STGs 
	 * @param username
	 * @param globerType
	 * @return {@link Response}
	 */
	public Response filterProposalApprovedSTG(String username,String globerType) {
		int viewId = 5,offset = 0,limit = 50,parentViewId = 5;
		String sorting = "Aposition";
				
		String requestUrl = GlobersBaseTest.baseUrl
				+ String.format(TdcAPIEndPoints.getAllActiveGlobers,viewId,username,parentViewId,offset,limit,sorting,globerType.replaceAll(" ", "%20"));
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);
		Response allGloberResponse = utils.executeGET(requestSpecification, requestUrl);
		return allGloberResponse;
	}
}
