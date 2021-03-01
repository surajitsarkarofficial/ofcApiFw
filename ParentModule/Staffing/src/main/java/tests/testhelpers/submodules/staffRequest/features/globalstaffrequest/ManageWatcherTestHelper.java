package tests.testhelpers.submodules.staffRequest.features.globalstaffrequest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import database.submodules.staffRequest.features.globalstaffrequest.ManageWatcherDBHelper;
import endpoints.submodules.staffRequest.features.globalstaffrequest.ManageWatcherEndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.staffRequest.features.globalstaffrequest.ManageWatcherPayloadHelper;
import tests.testcases.StaffingBaseTest;
import tests.testcases.submodules.openpositions.OpenPositionBaseTest;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import tests.testhelpers.submodules.staffRequest.StaffRequestTestHelper;

public class ManageWatcherTestHelper extends StaffRequestTestHelper{

	
	ArrayList<Integer> watcherIds = new ArrayList<Integer>();
	String positionId = null; 
	ManageWatcherDBHelper manageWatcherDb;
	ManageWatcherPayloadHelper manageWatcherPayload;
	public ManageWatcherTestHelper(String userName) throws Exception {
		super(userName);
		manageWatcherDb = new ManageWatcherDBHelper();
		manageWatcherPayload = new ManageWatcherPayloadHelper();
	}

	/**
	 * This method will perform POST to add watcher for the staff request
	 * 
	 * @param response
	 * @return {@link Response}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public Response manageWatcher(Response response) throws Exception {
		
		int i = new Random().nextInt(3);
		String srNumber = restUtils.getValueFromResponse(response, "details.positionDTOList[" + i + "].srNumber").toString();
		positionId = manageWatcherDb.getPositionId(srNumber);
		watcherIds = manageWatcherDb.getGlober();
		
		String payload = manageWatcherPayload.manageWatcherPayload(positionId, watcherIds);
		
		String requestUrl = StaffingBaseTest.baseUrl + ManageWatcherEndPoints.manageWatcherUrl;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(payload);
		Response manageWatcherResponse = restUtils.executePOST(requestSpecification, requestUrl);
		
		return manageWatcherResponse;
	}
	
	/**
	 * This method will perform GET to fetch newly added Sr watchers
	 * 
	 * @param response
	 * @return {@link Response}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public Response getWatchers(Response response) throws Exception {
		String positionId = restUtils.getValueFromResponse(response, "details.positionId").toString();
		
		String url = StaffingBaseTest.baseUrl + String.format(ManageWatcherEndPoints.getWatchers, positionId);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(OpenPositionBaseTest.sessionId);	
		Response watchers = restUtils.executeGET(requestSpecification,url);	
		return watchers;		
	}
	

	/**
	 * This method will fetch watcherName from Database
	 * @return {@link ArrayList}
	 * @throws SQLException 
	 * @author shadab.waikar
	 */
	public ArrayList<String> fetchWatchers() throws SQLException {
		return manageWatcherDb.getWatchersId(positionId);
	}
	

	/**
	 * This method will get watcherId
	 * @return {@link String}
	 * @author shadab.waikar
	 */
	public String getWatcherId() {
		return watcherIds.get(0).toString();
	}
	
	/**
	 * This method will fetch watcherName from Database
	 * @return {@link ArrayList}
	 * @throws SQLException 
	 * @author shadab.waikar
	 * @throws Exception 
	 */
	public Response addAlreadyAddedWatcherOnPositions(Response response) throws Exception {
		ArrayList<Integer> watcher = new ArrayList<Integer>();
		int i = new Random().nextInt(10);
		String srNumber = restUtils.getValueFromResponse(response, "details.positionDTOList[" + i + "].srNumber").toString();
		positionId = manageWatcherDb.getPositionId(srNumber);
		
		ArrayList<String> watcherId = fetchWatchers();
		watcher.add(Integer.parseInt(watcherId.get(0)));
		String payload = manageWatcherPayload.manageWatcherPayload(positionId, watcher);
		
		String requestUrl = StaffingBaseTest.baseUrl + ManageWatcherEndPoints.manageWatcherUrl;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(payload);
		Response manageWatcherResponse = restUtils.executePOST(requestSpecification, requestUrl);
		
		return manageWatcherResponse;
	}
}
