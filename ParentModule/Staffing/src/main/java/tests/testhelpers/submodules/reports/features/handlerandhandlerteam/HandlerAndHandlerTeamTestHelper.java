package tests.testhelpers.submodules.reports.features.handlerandhandlerteam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections4.map.HashedMap;

import database.submodules.reports.features.handlerandhandlerteam.HandlerAndHandlerTeamDBHelper;
import database.submodules.staffRequest.features.globalstaffrequest.QuickSuggestAndAssignDBHelper;
import endpoints.StaffingEndpoints;
import endpoints.submodules.globers.features.TdcAPIEndPoints;
import endpoints.submodules.reports.features.handlerandhandlerteam.HandlerAndHandlerTeamAPIEndPoints;
import endpoints.submodules.staffRequest.StaffRequestEndpoints;
import endpoints.submodules.staffRequest.features.GlobalStaffRequestAPIEndPoints;
import endpoints.submodules.staffRequest.features.SuggestGloberEndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.reports.features.handlerandhandlerteam.HandlerAndHandlerTeamPayloadHelper;
import payloads.submodules.staffRequest.features.EditSRPayloadHelper;
import payloads.submodules.staffRequest.features.globalstaffrequest.QuickSuggestAndAssignPayloadHelper;
import tests.testcases.StaffingBaseTest;
import tests.testcases.submodules.reports.ReportsBaseTest;
import tests.testhelpers.StaffingTestHelper;
import tests.testhelpers.submodules.reports.ReportsTestHelper;
import tests.testhelpers.submodules.staffRequest.features.EditSRTestHelper;
import utils.RestUtils;
import utils.Utilities;

public class HandlerAndHandlerTeamTestHelper extends ReportsTestHelper{

	EditSRTestHelper editSrHelper;
	RestUtils restUtils;
	HandlerAndHandlerTeamDBHelper handlerAndHandlerTeamDB;
	HandlerAndHandlerTeamPayloadHelper handlerPayload;
	Response suggestResponse;
	
	//handler members
	ArrayList<String> position_role_id = new ArrayList<String>();
	ArrayList<String> location_names = new ArrayList<String>();
	
	//handler's data
	private static String handlerNameFromDB[] = new String[2];
	private static String handlerTeamNameFromDB = null; 
	public static String srNumber = null; 
	private static String project_id = null; 
	private static String position_id = null; 
	private static String positionName = null; 
	private static String location = null; 
	private static String LocationId = null; 
	@SuppressWarnings("unused")
	private static String handlerName = null; 
	private static String handlerId = null; 
	private String positionRoleName = null;
	
	
	public HandlerAndHandlerTeamTestHelper(String username) throws Exception {
		super(username);
		restUtils = new RestUtils();
		editSrHelper = new EditSRTestHelper(username);
		handlerPayload = new HandlerAndHandlerTeamPayloadHelper();
		handlerAndHandlerTeamDB = new HandlerAndHandlerTeamDBHelper();
	}
	
	
	/**
	 * This method will return Sr details w.r.t change handler
	 * 
	 * @param username
	 * @param type
	 * @return {@link Response}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public Response getHandlerData(String username,String type) throws Exception {
		
		Response response = new StaffingTestHelper().getSRDetailsFromGrid(username, type);
		
		int i = Utilities.getRandomDay();
		srNumber = restUtils.getValueFromResponse(response, "details.positionDTOList[" + i + "].srNumber").toString();
		project_id = restUtils.getValueFromResponse(response, "details.positionDTOList[" + i + "].projectId").toString();
		position_id = restUtils.getValueFromResponse(response, "details.positionDTOList[" + i + "].positionId").toString();
		positionName =restUtils.getValueFromResponse(response, "details.positionDTOList[" + i + "].positionAndSeniority[0]").toString();
		location = restUtils.getValueFromResponse(response, "details.positionDTOList[" + i + "].location").toString();
		LocationId = restUtils.getValueFromResponse(response, "details.positionDTOList[" + i + "].locationId").toString();
		handlerName = restUtils.getValueFromResponse(response, "details.positionDTOList[" + i + "].handler").toString();
		handlerId = restUtils.getValueFromResponse(response, "details.positionDTOList[" + i + "].handlerId").toString();
		
		return response;
	}
	
	/**
	 * This method will return Sr with updated location
	 * 
	 * @param username
	 * @param location
	 * @return {@link Response}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public Response updateSrWithProvidedLocation(String username,String location) throws Exception {
		
		Response response = editSrHelper.editSrWithProvidedLocation(username, location);
		String srNumber = restUtils.getValueFromResponse(response, "details.positionDTOList[0].srNumber").toString();
	    handlerNameFromDB = handlerAndHandlerTeamDB.getHandlerNameWithSRnumber(srNumber);
		handlerTeamNameFromDB = handlerAndHandlerTeamDB.getHandlerTeamName(srNumber);	
		return response;
	}
	
	/**
	 * This method will handler name
	 * @return {@link String}
	 * @author shadab.waikar
	 */
	public static String getHandlerName() {
		return handlerNameFromDB[0]+" "+handlerNameFromDB[1];
	}
	
	/**
	 * This method will retrun SrNumber 
	 * @return {@link String}
	 * @author shadab.waikar
	 */
	public String getSrNumber() {
		return srNumber;
	}
	
	/**
	 * This method will handler team name
	 * @return {@link String}
	 * @author shadab.waikar
	 */
	public static String getHandlerTeamName() {
		return handlerTeamNameFromDB;
	}
	
	/**
	 * This method will suggest STG and get STGs email.
	 * 
	 * @param response
	 * @param username
	 * @param type
	 * @param handlerId
	 * @return {@link Response}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public Response getHandlerEmailWhileAssigningSTG(Response response,String username,String type,String handlerId) throws Exception
	{
		int i = 0,plans = 0;
		getHandlerData(username, type);
		
		do {
			if (i != 0) {
				response = getHandlerData(username, type);
			}
			plans = new QuickSuggestAndAssignDBHelper().getNoOfPlansPerSr(srNumber);
			i++;
		} while (plans >= 3);
		
		String json = handlerPayload.suggestSTGViaPMTD(srNumber, type);

		String requestUrl = StaffingBaseTest.baseUrl + SuggestGloberEndPoints.suggestGloberUrl;
		RequestSpecification suggestSTGRequestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId).contentType(ContentType.JSON).body(json);

	    suggestResponse = restUtils.executePOST(suggestSTGRequestSpecification, requestUrl);	
	    new StaffingBaseTest().validateResponseToContinueTest(suggestResponse, 201, "Unable to suggest STGs", true);
	    
		String getSTGEmail = String.format(StaffingBaseTest.baseUrl + HandlerAndHandlerTeamAPIEndPoints.getSTGsEmail, handlerId);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId);
		response = restUtils.executeGET(requestSpecification, getSTGEmail);   
		return response;
	}
	
	/**
	 * This method will update createdPlan status
	 * 
	 * @param username
	 * @throws Exception 
	 * @author shadab.waikar
	 */
	public void updatePlanStatusOfSTGPlan(String username) throws Exception {
		QuickSuggestAndAssignPayloadHelper suggestAndAssignPayload = new QuickSuggestAndAssignPayloadHelper(username);
		suggestAndAssignPayload.updateCreatedPlanStatus(restUtils.getValueFromResponse(suggestResponse, "details[0].staffPlanId").toString());
	}
	
	/**
	 * This method will change handler of Sr and Glober
	 * 
	 * @param userName
	 * @param type
	 * @return {@link Response}
	 * @throws Exception 
	 * @author shadab.waikar
	 */
	public Response performChangeHandler(String userName,String type) throws Exception {
		
		ArrayList<String> position_role_id = new ArrayList<String>();
		ArrayList<String> location_names = new ArrayList<String>();
		getHandlerData(userName, type);
		positionRoleName = positionName.toLowerCase().replaceAll(" ", "%20");
		String positionRole = handlerAndHandlerTeamDB.getPositionRoleIdAccordingToPositionRolName(positionName);
		
		location_names.add(LocationId);
		position_role_id.add(positionRole);
		String json = handlerPayload.changeHandlerPaylaod(handlerId, LocationId, position_role_id);
		
		String requestUrl = StaffingBaseTest.baseUrl + HandlerAndHandlerTeamAPIEndPoints.changeHandler;
		RequestSpecification suggestSTGRequestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId).contentType(ContentType.JSON).body(json);
	    Response changeHandlerResponse = restUtils.executePOST(suggestSTGRequestSpecification, requestUrl);
	    return changeHandlerResponse;
	}
	
	/**
	 * This method will get change handler details by applying filters 
	 * 
	 * @param userName
	 * @return {@link Response}
	 * @author shadab.waikar
	 */
	public Response getChangedHandlerDetails(String username) {
		int viewId = 7,parentViewId = 7,offset = 0,limit = 50;
		String sorting = "Astage";
		String changeHandlerFilterUrl = String.format(StaffingBaseTest.baseUrl + HandlerAndHandlerTeamAPIEndPoints.changeHandlerFilter, viewId,username,parentViewId,offset,limit,sorting,positionRoleName,LocationId);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId);
		Response getFilterHandlerData = restUtils.executeGET(requestSpecification, changeHandlerFilterUrl);
		return getFilterHandlerData;
	}
	
	/**
	 * This method will get watcher of Sr handler
	 * 
	 * @param positionId
	 * @return {@link Response}
	 * @author shadab.waikar
	 */
	public Response getSrHandlerWatcher(String positionId) {
		String SrWatcher = String.format(StaffingBaseTest.baseUrl + HandlerAndHandlerTeamAPIEndPoints.SrWatcher, positionId);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId);
		Response SrWatcherData = restUtils.executeGET(requestSpecification, SrWatcher);
		return SrWatcherData;
	}
	
	/**
	 * This method will get watcher of Sr from Sr grid
	 * 
	 * @param userName
	 * @param type
	 * @param response
	 * @return {@link Response}
	 * @throws Exception 
	 * @author shadab.waikar
	 */
	public Response getWatcherFromSrGRid(String username,String type,Response response) throws Exception {
		getHandlerData(username, type);
		String json = handlerPayload.changeHandlerFromSrGridPayload(handlerId, position_id);
		
		String requestUrl = StaffingBaseTest.baseUrl + HandlerAndHandlerTeamAPIEndPoints.chnageHandlerFromSrGrid;
		RequestSpecification handlerFromSrGridRequestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId).contentType(ContentType.JSON).body(json);
	    Response changeHandlerResponse = restUtils.executePOST(handlerFromSrGridRequestSpecification, requestUrl);
	    return changeHandlerResponse;
	}
	
	/**
	 * This method will get watcher details
	 * 
	 * @param userName
	 * @param type
	 * @return {@link Response}
	 * @throws Exception 
	 * @author shadab.waikar
	 */
	public Response getHandlerWatcherDetails(String username,String type) throws Exception {	
		Response SrWatcherData = getSrHandlerWatcher(position_id);  
	    return SrWatcherData;
	}
	
	/**
	 * This method will perform POST with wrong handler payload
	 * 
	 * @param userName
	 * @param type
	 * @return {@link Response}
	 * @throws Exception 
	 * @author shadab.waikar
	 */
	public Response performChangeHandlerWithWrongHandlerId(String username,String type) throws Exception {
		getHandlerData(username, type);
		String handlerId = "";
		String json = handlerPayload.getWrongChangeHandlerFromSrGridPayload(handlerId, position_id);
		
		String requestUrl = StaffingBaseTest.baseUrl + HandlerAndHandlerTeamAPIEndPoints.chnageHandlerFromSrGrid;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId).contentType(ContentType.JSON).body(json);
	    Response changeHandlerResponse = restUtils.executePOST(requestSpecification, requestUrl);
	    return changeHandlerResponse;
	}
	
	/**
	 * This method will perform POST with wrong handler payload
	 * 
	 * @param userName
	 * @param type
	 * @return {@link Response}
	 * @throws Exception 
	 * @author shadab.waikar
	 */
	public Response performChangeHandlerWithWrongPositionId(String username,String type) throws Exception {
		getHandlerData(username, type);
		String position_id = "";
		
		String requestUrl = String.format(StaffingBaseTest.baseUrl + HandlerAndHandlerTeamAPIEndPoints.SrWatcher,position_id);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId);
		Response SrWatcherData = restUtils.executeGET(requestSpecification, requestUrl);
	    return SrWatcherData;
	}
	
	/**
	 * This method will get all active globers
	 * 
	 * @param userName
	 * @param type
	 * @return {@link Response}
	 * @throws Exception 
	 * @author shadab.waikar
	 */
	public Response changeHandlerforActiveGlobers(String username,String type) throws Exception {
		new ReportsBaseTest().createSession(username);
		type = "Glober";
		int viewId = 5,parentViewId = 5,offset = 0,limit = 50;
		String sorting = "Aposition";
		
		String requestUrl = String.format(StaffingBaseTest.baseUrl + TdcAPIEndPoints.getAllActiveGlobers,viewId,username,parentViewId,offset,limit,sorting,type);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId);
		Response allActiveGlobers = restUtils.executeGET(requestSpecification, requestUrl);
		return allActiveGlobers;
	}
	

	/**
	 * This method will perform POST to change handler of all active globers
	 * 
	 * @param handlerId
	 * @param location_name
	 * @param position_role
	 * @return {@link Response}
	 * @throws SQLException 
	 * @author shadab.waikar
	 */
	public Response changeHandlerOfActiveGlobers(String handlerId,ArrayList<String> location_name,ArrayList<String> position_role) throws SQLException {
		String json = handlerPayload.getChangeActiveHandlerPayload(handlerId, location_name, position_role);
		
		String requestUrl = StaffingBaseTest.baseUrl + HandlerAndHandlerTeamAPIEndPoints.changeHandler;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId).contentType(ContentType.JSON).body(json);
	    Response changeHandlerResponse = restUtils.executePOST(requestSpecification, requestUrl);
	    return changeHandlerResponse;
	}
	
	/**
	 * This method will perform GET to get all active globers by applying filters
	 * 
	 * @param username
	 * @param type
	 * @param positionId
	 * @param locationId
	 * @return {@link Response}
	 * @author shadab.waikar
	 */
    public Response getAllActiveGlobersByApplyingFilter(String username,String type,String positionId,String locationId) {
    	type = "Glober";
    	int viewId = 5,parentViewId = 5,offset = 0,limit = 50;
		String sorting = "Aposition";
    	String requestUrl = String.format(StaffingBaseTest.baseUrl + TdcAPIEndPoints.getAllActiveGlobersByFilters,viewId,username,parentViewId,offset,limit,sorting,type,positionId,locationId);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId);
		Response allActiveGlobers = restUtils.executeGET(requestSpecification, requestUrl);
		return allActiveGlobers;
		
    }
    
    /**
	 * This method will perform POST to get change handler team
	 * 
	 * @param username
	 * @param type
	 * @return {@link Response}
	 * @throws Exception 
	 * @author shadab.waikar
	 */
    public Response getHandlerTeam(String username,String type) throws Exception {
    	getHandlerData(username, type);
    	String json = handlerPayload.getHandlerTeamPayload(handlerId);
    	
    	String requestUrl = StaffingBaseTest.baseUrl + HandlerAndHandlerTeamAPIEndPoints.changeHandlerTeam;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId).contentType(ContentType.JSON).body(json);
	    Response changeHandlerTeamResponse = restUtils.executePOST(requestSpecification, requestUrl);
	    return changeHandlerTeamResponse;
    	
    }
    
    /**
	 * This method will perform POST to check that Sr handler is changed or not.
	 * 
	 * @param username
	 * @param type
	 * @return {@link Response}
	 * @throws Exception 
	 * @author shadab.waikar
	 */
    public Response checkSrHandlerIsChangedViaPMTD(String username,String type) throws Exception {
    	getHandlerData(username, type);
    	String positionId = handlerAndHandlerTeamDB.getPositionRoleIdAccordingToPositionRolName(positionName);
    	location_names.add(LocationId);
		position_role_id.add(positionId);
		String json = handlerPayload.changeHandlerPaylaod(handlerId, LocationId, position_role_id);
		
		String requestUrl = StaffingBaseTest.baseUrl + HandlerAndHandlerTeamAPIEndPoints.changeHandler;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId).contentType(ContentType.JSON).body(json);
	    Response changeHandlerResponse = restUtils.executePOST(requestSpecification, requestUrl);
	    return changeHandlerResponse;
    }
    
	/**
	 * This method will perform POST to check that glober handler is changed or not.
	 * 
	 * @param username
	 * @param type
	 * @return {@link Response}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public Response checkGloberHandlerIsChangedViaPMTD(String username, String type) throws Exception {
		type = "Glober";
		int viewId = 5,parentViewId = 5,offset = 0,limit = 50;
		String sorting = "Aposition";
		new ReportsBaseTest().createSession(username);

		String requestUrl = String.format(StaffingBaseTest.baseUrl + TdcAPIEndPoints.getAllActiveGlobers,viewId,username,parentViewId,offset,limit,sorting, type);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId);
		Response allActiveGlobers = restUtils.executeGET(requestSpecification, requestUrl);
		return allActiveGlobers;
	}
       
	/**
	 * This method will perform GET to search glober
	 * 
	 * @param username
	 * @param globerId
	 * @return {@link Response}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public Response getGlober(String username, String globerId) throws Exception {
		int viewId = 5,parentViewId = 5,offset = 0,limit = 50;
		String sorting = "Aposition";
		String requestUrl = String.format(StaffingBaseTest.baseUrl + TdcAPIEndPoints.getGlober,viewId, username,parentViewId,offset,limit,sorting,globerId);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId);
		Response getGlober = restUtils.executeGET(requestSpecification, requestUrl);
		return getGlober;
	}

	/**
	 * This method will get handler data to edit Sr by position and location
	 * 
	 * @param username
	 * @param location
	 * @return {@link Response}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public Response getHandlerDataToEditSr(String username, String location) throws Exception {
		getHandlerData(username, location);
		positionRoleName = positionName.toLowerCase().replaceAll(" ", "%20");
		Response changedHandlerData = getChangedHandlerDetails(username);
		return changedHandlerData;
	}

	/**
	 * This method will get skills for the Sr
	 * 
	 * @return {@link Response}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public Response getSkillsToEditSr() throws Exception {

		String seniority = handlerAndHandlerTeamDB.getSeniorityNameById("5");
		positionRoleName = handlerAndHandlerTeamDB.getPositionRolNameExceptGivenPositionRol(positionName);
		String concatedPosition = positionRoleName.toLowerCase().replaceAll(" ", "%20");

		String requestUrl = String.format(StaffingBaseTest.baseUrl + StaffingEndpoints.getAlberthaSkills,concatedPosition, seniority);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId);
		Response getAlberthaSkills = restUtils.executeGET(requestSpecification, requestUrl);
		new StaffingBaseTest().validateResponseToContinueTest(getAlberthaSkills, 200,"Unable to fetch albertha skills.", true);

		String cacheSkillsUrl = String.format(StaffingBaseTest.baseUrl + StaffRequestEndpoints.cachedSkillsUrl);
		RequestSpecification cacheRequestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId);
		Response getCacheSkills = restUtils.executeGET(cacheRequestSpecification, cacheSkillsUrl);
		new StaffingBaseTest().validateResponseToContinueTest(getCacheSkills, 200, "Unable to fetch cache skills.",true);

		String id = restUtils.getValueFromResponse(getAlberthaSkills, "data").toString(); // [0].skill.id
		boolean skillsPresent = id.isEmpty();
		if (id == null || skillsPresent) {
			return getCacheSkills;
		} else {
			return getAlberthaSkills;
		}
	}

	/**
	 * This method will get handler by Edit Sr
	 * 
	 * @param username
	 * @param SrNumber
	 * @return {@link Response}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public Response getHandlerByEditSr(String username, String srNumber) throws Exception {
		EditSRPayloadHelper editSR = new EditSRPayloadHelper(username);
		Map<Object, Object> editSrData = new HashedMap<Object, Object>();
		Response skillsResponse = getSkillsToEditSr();
		
		String positionId = handlerAndHandlerTeamDB.getPositionRoleIdAccordingToPositionRolName(positionRoleName);
		editSrData.put("srNumber", srNumber.toString());
		editSrData.put("projectId", project_id.toString());
		editSrData.put("locationName", location.toString());
		editSrData.put("positionId", positionId.toString());
		editSrData.put("positionName", positionName.toString());
		String json = editSR.getEditSrPayloadToValidateHandler(editSrData,skillsResponse);

		String requestUrl = StaffingBaseTest.baseUrl + StaffRequestEndpoints.positionsUrl;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId).contentType(ContentType.JSON).body(json);
		Response editSrResponse = restUtils.executePUT(requestSpecification, requestUrl);
		return editSrResponse;
	}

	/**
	 * This method will perform GET to search Sr on SrGrid
	 * 
	 * @param SrNumber
	 * @return {@link Response}
	 * @author shadab.waikar
	 */
	public Response getSrBySearchingOnSrGrid(String srNumber) {
		String SrUrl = String.format(StaffingBaseTest.baseUrl + GlobalStaffRequestAPIEndPoints.srNumberPositionsUrl, srNumber);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId);
		Response getSrResponse = restUtils.executeGET(requestSpecification, SrUrl);
		return getSrResponse;

	}

	/**
	 * This method will perform POST to change individual glober handler
	 * 
	 * @param id
	 * @param glober_type
	 * @param handlerId
	 * @return {@link Response}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public Response changeGloberHandler(String id, String glober_type, String handlerId) throws SQLException {
		String globerId = handlerAndHandlerTeamDB.getGloberIdFromGlobersSchema(id);
		String json = handlerPayload.getChangeGloberHandlerPayload(globerId, glober_type, handlerId);

		String requestUrl = StaffingBaseTest.baseUrl + HandlerAndHandlerTeamAPIEndPoints.changeGloberHandler;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId).contentType(ContentType.JSON).body(json);
		Response globerHandlerResponse = restUtils.executePOST(requestSpecification, requestUrl);
		return globerHandlerResponse;
	}

	/**
	 * This method will perform GET to apply handler filter to change handler for
	 * Anywhere location
	 * 
	 * @param username
	 * @param glober_type
	 * @return {@link Response}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public Response getHandlerWithLocationAsAnywhere(String username, String glober_type) throws Exception {
		String location = "Anywhere";
		getHandlerData(username, glober_type);
		positionRoleName = positionName.toLowerCase().replaceAll(" ", "%20");
		String positionId = handlerAndHandlerTeamDB.getPositionRoleIdAccordingToPositionRolName(positionName);
		location_names.add("13901240");
		position_role_id.add(positionId);

		String changeHandlerUrl = String.format(
				StaffingBaseTest.baseUrl + HandlerAndHandlerTeamAPIEndPoints.changeHandlerFilters, positionId, location,
				handlerId, username);

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId);
		Response changeHandlerResponse = restUtils.executeGET(requestSpecification, changeHandlerUrl);
		new StaffingBaseTest().validateResponseToContinueTest(changeHandlerResponse, 200,
				"Unable to get glober's count for location handler.", true);

		String json = handlerPayload.changeHandlerPaylaod(handlerId, location_names.get(0), position_role_id);
		String requestUrl = StaffingBaseTest.baseUrl + HandlerAndHandlerTeamAPIEndPoints.changeHandler;
		RequestSpecification changeHAndlerRequestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId).contentType(ContentType.JSON).body(json);

		Response srHandlerResponse = restUtils.executePOST(changeHAndlerRequestSpecification, requestUrl);
		new StaffingBaseTest().validateResponseToContinueTest(srHandlerResponse, 200,
				"Unable to get glober's count for location handler.", true);
		return changeHandlerResponse;

	}

	/**
	 * This method will perform POST to check weather Sr handler is changed via PM
	 * and TD
	 * 
	 * @param username
	 * @param glober_type
	 * @return {@link Response}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public Response changeSrHandlerTeamByPMTD(String username, String glober_type) throws Exception {
		getHandlerData(username, glober_type);
		String json = handlerPayload.getHandlerTeamPayload(handlerId);

		String requestUrl = StaffingBaseTest.baseUrl + HandlerAndHandlerTeamAPIEndPoints.changeHandlerTeam;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId).contentType(ContentType.JSON).body(json);
		Response srHandlerResponse = restUtils.executePOST(requestSpecification, requestUrl);
		return srHandlerResponse;
	}

	/**
	 * This method will perform POST to get error message for wrong handler Id
	 * 
	 * @param username
	 * @param glober_type
	 * @return {@link Response}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public Response getErrorMsgWithHandlerTeamByWrongHandlerId(String username, String glober_type) throws Exception {
		getHandlerData(username, glober_type);
		String handlerId = "";
		String json = handlerPayload.getHandlerTeamPayloadWithWrongHandler(handlerId);

		String requestUrl = StaffingBaseTest.baseUrl + HandlerAndHandlerTeamAPIEndPoints.changeHandlerTeam;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId).contentType(ContentType.JSON).body(json);
		Response srHandleTeamrResponse = restUtils.executePOST(requestSpecification, requestUrl);
		return srHandleTeamrResponse;
	}

	/**
	 * This method will perform POST to get error message for wrong handler team Id
	 * 
	 * @param username
	 * @param glober_type
	 * @return {@link Response}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public Response getErrorMsgOfWeatherSRHandlerTeamIsChangesWithSARAWithWrongHandlerTeamID(String username,
			String glober_type) throws Exception {
		getHandlerData(username, glober_type);
		String handlerTeamId = "";
		String json = handlerPayload.getHandlerTeamPayloadWithWrongHandlerTeam(handlerId, handlerTeamId);

		String requestUrl = StaffingBaseTest.baseUrl + HandlerAndHandlerTeamAPIEndPoints.changeHandlerTeam;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId).contentType(ContentType.JSON).body(json);
		Response srHandleTeamrResponse = restUtils.executePOST(requestSpecification, requestUrl);
		return srHandleTeamrResponse;
	}

	/**
	 * This method will perform POST to get error message for wrong glober type
	 * while changing glober handler
	 * 
	 * @param username
	 * @param glober_type
	 * @return {@link Response}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public Response getErrorMsgForGloberHandlerAccordingToPositionLocationWithWrongType(String username,
			String glober_type) throws Exception {
		int i = new Random().nextInt(15);
		glober_type = "In Pipe";
		Response getAllActiveGlobers = checkGloberHandlerIsChangedViaPMTD(username, glober_type);
		new StaffingBaseTest().validateResponseToContinueTest(getAllActiveGlobers, 200, "Failed to fetch all globers.",
				true);
		String handlerId = restUtils
				.getValueFromResponse(getAllActiveGlobers, "details.globerDTOList[" + i + "].handler.handlerId")
				.toString();
		String id = handlerAndHandlerTeamDB.getGloberIdFromGlobersSchema(restUtils
				.getValueFromResponse(getAllActiveGlobers, "details.globerDTOList[" + i + "].globerId").toString());

		String json = handlerPayload.getChangeGloberHandlerPayloadWithWrongData(id, glober_type, handlerId);
		String requestUrl = StaffingBaseTest.baseUrl + HandlerAndHandlerTeamAPIEndPoints.changeGloberHandler;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId).contentType(ContentType.JSON).body(json);
		Response globerHandlerResponse = restUtils.executePOST(requestSpecification, requestUrl);
		return globerHandlerResponse;
	}
    
	/**
	 * This method will perform POST to get error message for wrong glober Id while
	 * changing glober handler
	 * 
	 * @param username
	 * @param glober_type
	 * @return {@link Response}
	 * @throws Exception
	 * @author shadab.waikar
	 */
	public Response verifyErrorMsgForGloberHandlerAccordingToPositionLocationWithInvalidGloberID(String username,
			String glober_type) throws Exception {
		int i = new Random().nextInt(15);
		String id = "";
		Response getAllActiveGlobers = checkGloberHandlerIsChangedViaPMTD(username, glober_type);
		new StaffingBaseTest().validateResponseToContinueTest(getAllActiveGlobers, 200, "Failed to fetch all globers.",
				true);
		String handlerId = restUtils
				.getValueFromResponse(getAllActiveGlobers, "details.globerDTOList[" + i + "].handler.handlerId")
				.toString();
		glober_type = restUtils.getValueFromResponse(getAllActiveGlobers, "details.globerDTOList[" + i + "].type")
				.toString();

		String json = handlerPayload.getChangeGloberHandlerPayloadWithWrongData(id, glober_type, handlerId);
		String requestUrl = StaffingBaseTest.baseUrl + HandlerAndHandlerTeamAPIEndPoints.changeGloberHandler;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReportsBaseTest.sessionId).contentType(ContentType.JSON).body(json);
		Response globerHandlerResponse = restUtils.executePOST(requestSpecification, requestUrl);
		return globerHandlerResponse;
	}
}
