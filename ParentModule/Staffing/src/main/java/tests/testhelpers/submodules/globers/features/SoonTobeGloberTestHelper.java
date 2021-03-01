package tests.testhelpers.submodules.globers.features;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import database.LocationDBHelper;
import database.PositionDBHelper;
import database.SoonTobeGloberDbHelper;
import database.StudioDBHelper;
import database.submodules.globers.features.SoonTobeGloberDBHelper;
import dto.submodules.globers.AgeingFilterObjects;
import dto.submodules.globers.CommentsSoonToBeGlobersObjects;
import dto.submodules.globers.GloberAvailabilityObjects;
import dto.submodules.globers.HandlersObjects;
import dto.submodules.globers.LeadersObjects;
import dto.submodules.globers.LocationsObjects;
import dto.submodules.globers.PositionsObjects;
import dto.submodules.globers.SeniorityFilterSoonToBeGloberObjects;
import dto.submodules.globers.SoonToBeGloberDetailsObjects;
import dto.submodules.globers.StatusGridSoonToBeGloberObjects;
import dto.submodules.globers.StudiosObjects;
import dto.submodules.globers.soonTobeGlobers;
import endpoints.StaffingEndpoints;
import endpoints.submodules.globers.features.SoonTobeGlobersEndPoints;
import endpoints.submodules.staffRequest.StaffRequestEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.globers.SoonTobePayloadHelper;
import tests.testcases.submodules.globers.GlobersBaseTest;
import tests.testhelpers.submodules.globers.GlobersTestHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * 
 * @author pornima.chakurkar
 *
 */

public class SoonTobeGloberTestHelper extends GlobersTestHelper implements LocationDBHelper, StudioDBHelper, PositionDBHelper, SoonTobeGloberDbHelper {

	SoftAssert softAssert;
	SoonTobeGloberDBHelper stgDBHelper;
	RestUtils restUtils = new RestUtils();
	String pipe = "In%20Pipe", hire = "New%20Hire";
	int viewId = 2;

	public SoonTobeGloberTestHelper(String userName) throws Exception {
		super(userName);
		softAssert = new SoftAssert();
		stgDBHelper = new SoonTobeGloberDBHelper();
	}

	/**
	 * This method apply for STG
	 * 
	 * @param response
	 * @param name
	 * @return Response
	 * @throws Exception
	 */
	public Response applySoonToBeGloberFilter(Response response, String name) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		Map<Object, Object> suggesGloberMap = new HashMap<Object, Object>();
		suggesGloberMap = getSoonTobeGlobersData(name);

		response = getFiltersSoonToBeGlobersTalenPoolGrids(response, name);
		new GlobersBaseTest().validateResponseToContinueTest(response, 200,
				"Unable to get globers list from talent pool.", true);

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), "success",
				"API is not giving 200 response");

		String applySoonToBeGloberUrl = GlobersBaseTest.baseUrl + String.format(
				SoonTobeGlobersEndPoints.applySoonToBeGlobersTypeUrl, viewId, suggesGloberMap.get("userId"),
				suggesGloberMap.get("parentViewId"), suggesGloberMap.get("sorting"), suggesGloberMap.get("type"), pipe,
				hire, "&benchDateRange=", suggesGloberMap.get("minDate"), suggesGloberMap.get("maxDate"));

		response = restUtils.executeGET(requestSpecification, applySoonToBeGloberUrl);

		return response;
	}

	/**
	 * This method will return list of soon to be globers types from DB
	 * 
	 * @return List
	 * @throws SQLException
	 */
	public List<soonTobeGlobers> getListOfSoonToBeGloberTypeDb() throws SQLException {
		List<soonTobeGlobers> listOfSoonToBeGloberTypeDb = stgDBHelper.getSoonToBeGloberType();
		return listOfSoonToBeGloberTypeDb;
	}

	/**
	 * This method will Get list of positions available based on glober tp grid data
	 * and return response
	 * 
	 * @param response
	 * @param name
	 * @return Response
	 * @throws Exception
	 */
	public Response getSoonToBeGloberDataFilterPosition(Response response, String name) throws Exception {
		Map<Object, Object> suggesGloberMap = new HashMap<Object, Object>();
		suggesGloberMap = getSoonTobeGlobersData(name);

		// Get list of positions available based on glober tp grid data
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String requestUrl = GlobersBaseTest.baseUrl + String.format(SoonTobeGlobersEndPoints.filterPositionsUrl,
				viewId, suggesGloberMap.get("userId"), suggesGloberMap.get("type"), pipe, hire, "&benchDateRange=",
				suggesGloberMap.get("minDate"), suggesGloberMap.get("maxDate"));

		response = restUtils.executeGET(requestSpecification, requestUrl);

		return response;
	}

	/**
	 * This method will Get list of globers available based on postion filter and
	 * return response
	 * 
	 * @param response
	 * @param positionNameDb
	 * @param name
	 * @return Response
	 * @throws SQLException
	 */
	public Response getListofGlobersPositionFliter(Response response, String positionNameDb, String name)
			throws SQLException {
		Map<Object, Object> suggesGloberMap = new HashMap<Object, Object>();
		suggesGloberMap = getSoonTobeGlobersData(name);
		
		positionNameDb = positionNameDb.replaceAll("\\s", "%20").replaceAll("\\&", "%26").replaceAll("\\++", "%2B%2B");
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String requestUrl = GlobersBaseTest.baseUrl + String.format(SoonTobeGlobersEndPoints.applyPositionFilterUrl, viewId,
				suggesGloberMap.get("userId"), suggesGloberMap.get("parentViewId"), suggesGloberMap.get("sorting"),
				suggesGloberMap.get("type"), pipe, hire, positionNameDb, "&benchDateRange=",
				suggesGloberMap.get("minDate"), suggesGloberMap.get("maxDate"));

		response = restUtils.executeGET(requestSpecification, requestUrl);

		return response;
	}

	/**
	 * This method will return list of globers available based on postion filter
	 * from Db
	 * 
	 * @param positionNameDb
	 * @return List
	 * @throws SQLException
	 */
	public List<PositionsObjects> getSoonToBeGloberPositionsDb(String positionNameDb) throws SQLException {
		List<PositionsObjects> listOfSoonToBeGloberPositions = stgDBHelper
				.getSoonToBeGlobersForPositionFilter(positionNameDb);
		return listOfSoonToBeGloberPositions;
	}

	/**
	 * This method will Get list of locations available based on glober tp grid data
	 * and return response
	 * 
	 * @param response
	 * @param name
	 * @return Response
	 * @throws Exception
	 */
	public Response getSoonToBeGloberDataFilterLocation(Response response, String name) throws Exception {
		Map<Object, Object> suggesGloberMap = new HashMap<Object, Object>();
		suggesGloberMap = getSoonTobeGlobersData(name);
		
		// Get list of locations available based on glober tp grid data-
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);
		String locationFilterUrl = GlobersBaseTest.baseUrl + String.format(
				SoonTobeGlobersEndPoints.locationFilterGlobantTalentPoolGrid, viewId, suggesGloberMap.get("userId"),
				suggesGloberMap.get("parentViewId"), suggesGloberMap.get("sorting"), suggesGloberMap.get("type"), pipe,
				hire, "&benchDateRange=", suggesGloberMap.get("minDate"), suggesGloberMap.get("maxDate"));

		response = restUtils.executeGET(requestSpecification, locationFilterUrl);

		return response;
	}

	/**
	 * This method will return location name from Db
	 * 
	 * @param locationId
	 * @return String
	 * @throws SQLException
	 */
	public String getLocationNameDb(String locationId) throws SQLException {
		String locationName = getLocationNameFromId(locationId);
		return locationName;
	}

	/**
	 * This method will Get list of globers available based on location filter and
	 * return response
	 * 
	 * @param response
	 * @param name
	 * @param locationId
	 * @return Response
	 * @throws SQLException
	 */
	public Response getSoonToBeGloberFilterApplyLocation(Response response, String name, String locationId)
			throws SQLException {
		Map<Object, Object> suggesGloberMap = new HashMap<Object, Object>();
		suggesGloberMap = getSoonTobeGlobersData(name);
		
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String applyLocFilterStgUrl = GlobersBaseTest.baseUrl + String.format(
				SoonTobeGlobersEndPoints.applyLocationFilterStgFilterUrl, viewId, suggesGloberMap.get("userId"),
				suggesGloberMap.get("parentViewId"), suggesGloberMap.get("sorting"), suggesGloberMap.get("type"), pipe,
				hire, locationId, "&benchDateRange=", suggesGloberMap.get("minDate"), suggesGloberMap.get("maxDate"));

		response = restUtils.executeGET(requestSpecification, applyLocFilterStgUrl);

		return response;
	}

	/**
	 * This method will return list of globers based on location filter from Db
	 * 
	 * @param locationId
	 * @return List
	 * @throws SQLException
	 */
	public List<LocationsObjects> getSoonToBeGloberLocationFilterDb(String locationId) throws SQLException {
		List<LocationsObjects> listOfGlobersForLocationFilterDb = stgDBHelper
				.getSoonToBeGlobersForLocationFilter(locationId);
		return listOfGlobersForLocationFilterDb;
	}

	/**
	 * This method will Get list of seniorities available based on glober tp grid
	 * data and return response
	 * 
	 * @param response
	 * @param name
	 * @return Response
	 * @throws Exception
	 */
	public Response getSoonToBeGloberDataFilterSeniority(Response response, String name) throws Exception {
		Map<Object, Object> suggesGloberMap = new HashMap<Object, Object>();
		suggesGloberMap = getSoonTobeGlobersData(name);
		
		// Get list of seniorities available based on glober talent pool grid data
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);
		String seniorityFilterUrl = GlobersBaseTest.baseUrl + String.format(
				SoonTobeGlobersEndPoints.seniorityFilterGlobantTalentPoolGrid, viewId, suggesGloberMap.get("userId"),
				suggesGloberMap.get("parentViewId"), suggesGloberMap.get("sorting"), suggesGloberMap.get("type"), pipe,
				hire, "&benchDateRange=", suggesGloberMap.get("minDate"), suggesGloberMap.get("maxDate"));

		response = restUtils.executeGET(requestSpecification, seniorityFilterUrl);
		return response;
	}

	/**
	 * This method will return list of globers based on Seniority filter and return
	 * response
	 * 
	 * @param response
	 * @param name
	 * @param seniorityName
	 * @return Response
	 * @throws SQLException
	 */
	public Response getSoonToBeGloberFilterApplySeniority(Response response, String name, String seniorityName)
			throws SQLException {
		Map<Object, Object> suggesGloberMap = new HashMap<Object, Object>();
		suggesGloberMap = getSoonTobeGlobersData(name);
		
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String applySeniorityFilterStgUrl = GlobersBaseTest.baseUrl
				+ String.format(SoonTobeGlobersEndPoints.applySenioriyFilterStgFilterUrl, viewId, suggesGloberMap.get("userId"),
						suggesGloberMap.get("parentViewId"), suggesGloberMap.get("sorting"),
						suggesGloberMap.get("type"), pipe, hire, seniorityName, "&benchDateRange=",
						suggesGloberMap.get("minDate"), suggesGloberMap.get("maxDate"));

		response = restUtils.executeGET(requestSpecification, applySeniorityFilterStgUrl);

		return response;
	}

	/**
	 * This method will return list of globers based on Seniority filter from Db
	 * 
	 * @param locationId
	 * @return List
	 * @throws SQLException
	 */
	public List<SeniorityFilterSoonToBeGloberObjects> getSoonToBeGloberSeniorityFilterDb(String locationId)
			throws SQLException {
		List<SeniorityFilterSoonToBeGloberObjects> listOfGlobersForSeniorityFilterDb = stgDBHelper
				.getSoonToBeGlobersForSeniorityFilter(locationId);
		return listOfGlobersForSeniorityFilterDb;
	}

	/**
	 * This method will Get list of handlers available based on glober talent pool
	 * grid data and return response
	 * 
	 * @param response
	 * @param name
	 * @return Response
	 * @throws Exception
	 */
	public Response getSoonToBeGloberDataFilterHandler(Response response, String name) throws Exception {
		Map<Object, Object> suggesGloberMap = new HashMap<Object, Object>();
		suggesGloberMap = getSoonTobeGlobersData(name);
		
		// Get list of handlers available based on glober talent pool grid data
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);
		String handlerFilterUrl = GlobersBaseTest.baseUrl + String.format(
				SoonTobeGlobersEndPoints.handlerFilterGlobantTalentPoolGrid, viewId, suggesGloberMap.get("userId"),
				suggesGloberMap.get("parentViewId"), suggesGloberMap.get("sorting"), suggesGloberMap.get("type"), pipe,
				hire, "&benchDateRange=", suggesGloberMap.get("minDate"), suggesGloberMap.get("maxDate"));

		response = restUtils.executeGET(requestSpecification, handlerFilterUrl);

		return response;
	}

	/**
	 * This method will return handler name
	 * 
	 * @param handlerId
	 * @return String
	 * @throws SQLException
	 */
	public String getHandlerNameDb(String handlerId) throws SQLException {
		String handlerName = stgDBHelper.getHandlerName(handlerId);
		return handlerName;
	}

	/**
	 * This method will return list of globers based on Handler filter and return
	 * response
	 * 
	 * @param response
	 * @param name
	 * @param handlerId
	 * @return Response
	 * @throws SQLException
	 */
	public Response getSoonToBeGloberFilterApplyHandler(Response response, String name, String handlerId)
			throws SQLException {
		Map<Object, Object> suggesGloberMap = new HashMap<Object, Object>();
		suggesGloberMap = getSoonTobeGlobersData(name);

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String applyHandlerFilterStgUrl = GlobersBaseTest.baseUrl + String.format(
				SoonTobeGlobersEndPoints.applyHandlerFilterStgFilterUrl, viewId, suggesGloberMap.get("userId"),
				suggesGloberMap.get("parentViewId"), suggesGloberMap.get("sorting"), suggesGloberMap.get("type"), pipe,
				hire, handlerId, "&benchDateRange=", suggesGloberMap.get("minDate"), suggesGloberMap.get("maxDate"));

		response = restUtils.executeGET(requestSpecification, applyHandlerFilterStgUrl);

		return response;
	}

	/**
	 * This method will return list of globers based on Handler filter from Db
	 * 
	 * @param handlerId
	 * @return List
	 * @throws SQLException
	 */
	public List<HandlersObjects> getSoonToBeGloberHandlerFilterDb(String handlerId) throws SQLException {
		List<HandlersObjects> listOfGlobersForHandlerFilterDb = stgDBHelper
				.getSoonToBeGlobersForHandlerFilter(handlerId);
		return listOfGlobersForHandlerFilterDb;
	}

	/**
	 * This method will Get list of Leader available based on glober talent pool
	 * grid data and return response
	 * 
	 * @param response
	 * @param name
	 * @return Response
	 * @throws Exception
	 */
	public Response getSoonToBeGloberDataFilterLeader(Response response, String name) throws Exception {
		Map<Object, Object> suggesGloberMap = new HashMap<Object, Object>();
		suggesGloberMap = getSoonTobeGlobersData(name);

		// Get list of handlers available based on glober talent pool grid data
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);
		String leaderFilterUrl = GlobersBaseTest.baseUrl + String.format(
				SoonTobeGlobersEndPoints.leaderFilterGlobantTalentPoolGrid, viewId, suggesGloberMap.get("userId"),
				suggesGloberMap.get("parentViewId"), suggesGloberMap.get("sorting"), suggesGloberMap.get("type"), pipe,
				hire, "&benchDateRange=", suggesGloberMap.get("minDate"), suggesGloberMap.get("maxDate"));

		response = restUtils.executeGET(requestSpecification, leaderFilterUrl);

		return response;
	}

	/**
	 * This method will return list of globers based on Leader filter from Db
	 * 
	 * @return List
	 * @throws SQLException
	 */
	public List<LeadersObjects> getSoonToBeGloberLeadersDb() throws SQLException {
		List<LeadersObjects> listOfLeadersDb = stgDBHelper.getAvailableLeaderForSoonToBeGlobers();
		return listOfLeadersDb;
	}

	/**
	 * This method will Get list of globers with filter Availability based on glober
	 * talent pool grid data and return response
	 * 
	 * @param response
	 * @param name
	 * @return Response
	 * @throws Exception
	 */
	public Response getSoonToBeGloberDataFilterAvailability(Response response, String name) throws Exception {
		Map<Object, Object> suggesGloberMap = new HashMap<Object, Object>();
		suggesGloberMap = getSoonTobeGlobersData(name);
		int min_availability = 0,max_availability = 100;

		// Get list of handlers available based on glober talent pool grid data
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);
		String availabilityFilterUrl = GlobersBaseTest.baseUrl + String.format(
				SoonTobeGlobersEndPoints.applyAvailabilityFilterGlobantTalentPoolGrid, viewId, suggesGloberMap.get("userId"),
				suggesGloberMap.get("parentViewId"), suggesGloberMap.get("sorting"), suggesGloberMap.get("type"), pipe,
				hire, min_availability,max_availability, suggesGloberMap.get("minDate"),
				suggesGloberMap.get("maxDate"));

		response = restUtils.executeGET(requestSpecification, availabilityFilterUrl);

		return response;
	}

	/**
	 * This method will return list of globers based on Availability filter from Db
	 * 
	 * @return List
	 * @throws SQLException
	 */
	public List<GloberAvailabilityObjects> getSoonToBeGloberAvailabilityFilterDb() throws SQLException {
		List<GloberAvailabilityObjects> listOfGloberForAvailabilityDb = stgDBHelper
				.getSoonToBeGlobersForAvailabilityFilter();
		return listOfGloberForAvailabilityDb;
	}

	/**
	 * This method will Get list of globers with Ageing filter available based on
	 * glober talent pool grid data and return response
	 * 
	 * @param response
	 * @param name
	 * @return Response
	 * @throws Exception
	 */
	public Response getSoonToBeGloberDataFilterAgeing(Response response, String name) throws Exception {
		Map<Object, Object> suggesGloberMap = new HashMap<Object, Object>();
		suggesGloberMap = getSoonTobeGlobersData(name);

		// Get list of handlers available based on glober talent pool grid data
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);
		String ageingFilterUrl = GlobersBaseTest.baseUrl + String.format(
				SoonTobeGlobersEndPoints.applyAvailabilityFilterGlobantTalentPoolGrid, viewId, suggesGloberMap.get("userId"),
				suggesGloberMap.get("parentViewId"), suggesGloberMap.get("sorting"), suggesGloberMap.get("type"), pipe,
				hire, suggesGloberMap.get("ageing"), "&benchDateRange=", suggesGloberMap.get("minDate"),
				suggesGloberMap.get("maxDate"));

		response = restUtils.executeGET(requestSpecification, ageingFilterUrl);

		return response;
	}

	/**
	 * This method will return list of globers based on Ageing filter from Db
	 * 
	 * @return List
	 * @throws SQLException
	 * @throws ParseException
	 */
	public List<AgeingFilterObjects> getSoonToBeGloberAgingFilterDb() throws SQLException, ParseException {
		List<AgeingFilterObjects> listOfGloberForAgingDb = stgDBHelper.getFilterAgeing();
		return listOfGloberForAgingDb;
	}

	public Map<Object, Object> getSoonTobeGlobersData(String name) throws SQLException {
		Map<Object, Object> suggesGloberMap = new HashMap<Object, Object>();
		String minDate = Utilities.getTodayDate("dd-MM-yyyy");
		String maxDate = Utilities.getFutureDate("dd-MM-yyyy", 30);
		String userId = stgDBHelper.getGloberId(name);

		int parentViewId = 2;
		String sorting = "Aposition", type = "Candidate";
		int ageing = 0;

		suggesGloberMap.put("minDate", minDate);
		suggesGloberMap.put("maxDate", maxDate);
		suggesGloberMap.put("userId", userId);
		suggesGloberMap.put("parentViewId", parentViewId);
		suggesGloberMap.put("sorting", sorting);
		suggesGloberMap.put("type", type);
		suggesGloberMap.put("ageing", ageing);

		return suggesGloberMap;
	}

	/**
	 * This method will get list of globers after applying glober studio filter and
	 * return response
	 * 
	 * @param response
	 * @param name
	 * @param studioId
	 * @return Response
	 * @throws Exception
	 */
	public Response getSoonToBeGloberFilterApplyStudio(Response response, String name, String studioId)
			throws Exception {
		Map<Object, Object> suggesGloberMap = new HashMap<Object, Object>();
		suggesGloberMap = getSoonTobeGlobersData(name);

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String applyStudioFilterStgUrl = GlobersBaseTest.baseUrl + String.format(
				SoonTobeGlobersEndPoints.applyGloberStudioFilterGlobantTalentPoolGrid, viewId, suggesGloberMap.get("userId"),
				suggesGloberMap.get("parentViewId"), suggesGloberMap.get("sorting"), suggesGloberMap.get("type"), pipe,
				hire, studioId, "&benchDateRange=", suggesGloberMap.get("minDate"), suggesGloberMap.get("maxDate"));

		response = restUtils.executeGET(requestSpecification, applyStudioFilterStgUrl);

		return response;
	}

	/**
	 * This method will Get list of Glober Studio available based on glober talent
	 * pool grid data and return response
	 * 
	 * @param response
	 * @param name
	 * @return Response
	 * @throws Exception
	 */
	public Response getSoonToBeGloberDataFilterGloberStudio(Response response, String name) throws Exception {
		Map<Object, Object> suggesGloberMap = new HashMap<Object, Object>();
		suggesGloberMap = getSoonTobeGlobersData(name);

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String globerStudioUrl = GlobersBaseTest.baseUrl + String.format(
				SoonTobeGlobersEndPoints.globerStudioFilterGlobantTalentPoolGrid, viewId, suggesGloberMap.get("userId"),
				suggesGloberMap.get("parentViewId"), suggesGloberMap.get("sorting"), suggesGloberMap.get("type"), pipe,
				hire, "&benchDateRange=", suggesGloberMap.get("minDate"), suggesGloberMap.get("maxDate"));

		response = restUtils.executeGET(requestSpecification, globerStudioUrl);

		return response;
	}

	/**
	 * This method will return name of the studio
	 * 
	 * @param studioId
	 * @return String
	 * @throws SQLException
	 */
	public String getStudioNameDb(String studioId) throws SQLException {
		String studioName = getStudioName(studioId);
		return studioName;
	}

	/***
	 * This method will return list of globers after applying glober studio filter
	 * from Db
	 * 
	 * @param studioId
	 * @return List
	 * @throws SQLException
	 * @throws ParseException
	 */
	public List<StudiosObjects> getSoonToBeGloberStudioFilterDb(String studioId) throws SQLException, ParseException {
		List<StudiosObjects> listOfGloberForStudioDb = stgDBHelper.getSoonToBeGlobersForGloberStudioFilter(studioId);
		return listOfGloberForStudioDb;
	}

	/**
	 * This method will Get list of Skills available based on glober talent pool
	 * grid data and return response
	 * 
	 * @param response
	 * @param name
	 * @return Response
	 * @throws Exception
	 */
	public Response getSoonToBeGloberDataFilterSkills(Response response, String name) throws Exception {
		Map<Object, Object> suggesGloberMap = new HashMap<Object, Object>();
		suggesGloberMap = getSoonTobeGlobersData(name);

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String globerSkillsUrl = GlobersBaseTest.baseUrl
				+ String.format(SoonTobeGlobersEndPoints.skillsFilterGlobantTalentPoolGrid, viewId,
						suggesGloberMap.get("userId"), suggesGloberMap.get("type"), pipe, hire, "&benchDateRange=",
						suggesGloberMap.get("minDate"), suggesGloberMap.get("maxDate"));

		response = restUtils.executeGET(requestSpecification, globerSkillsUrl);

		return response;
	}

	/**
	 * This method will Get list of Status available based on glober talent pool
	 * grid data and return response
	 * 
	 * @param response
	 * @param name
	 * @return Response
	 * @throws Exception
	 */
	public Response getSoonToBeGloberDataFilterStatus(Response response, String name) throws Exception {
		Map<Object, Object> suggesGloberMap = new HashMap<Object, Object>();
		suggesGloberMap = getSoonTobeGlobersData(name);

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String globerStatusUrl = GlobersBaseTest.baseUrl
				+ String.format(SoonTobeGlobersEndPoints.statusFilterGlobantTalentPoolGrid, viewId,
						suggesGloberMap.get("userId"), suggesGloberMap.get("type"), pipe, hire, "&benchDateRange=",
						suggesGloberMap.get("minDate"), suggesGloberMap.get("maxDate"));

		response = restUtils.executeGET(requestSpecification, globerStatusUrl);

		return response;
	}

	/**
	 * This method will return status name
	 * 
	 * @param statusId
	 * @return String
	 * @throws SQLException
	 */
	public String getStatusNameDb(String statusId) throws SQLException {
		String statusName = stgDBHelper.getStatusName(statusId);
		return statusName;
	}

	/**
	 * This method will Get list of globers after applying Status filter and return
	 * response
	 * 
	 * @param response
	 * @param name
	 * @param statusId
	 * @return Response
	 * @throws SQLException
	 */
	public Response getSoonToBeGloberFilterApplyStatus(Response response, String name, String statusId)
			throws SQLException {
		Map<Object, Object> suggesGloberMap = new HashMap<Object, Object>();
		suggesGloberMap = getSoonTobeGlobersData(name);

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String applyStudioFilterStgUrl = GlobersBaseTest.baseUrl + String.format(
				SoonTobeGlobersEndPoints.applyStatusFilterGlobantTalentPoolGrid, viewId, suggesGloberMap.get("userId"),
				suggesGloberMap.get("parentViewId"), suggesGloberMap.get("sorting"), suggesGloberMap.get("type"), pipe,
				hire, statusId, suggesGloberMap.get("minDate"), suggesGloberMap.get("maxDate"));

		response = restUtils.executeGET(requestSpecification, applyStudioFilterStgUrl);

		return response;
	}

	/**
	 * This method will return list of globers based on Status filter from Db
	 * 
	 * @param statusName
	 * @return List
	 * @throws SQLException
	 */
	public List<StatusGridSoonToBeGloberObjects> getGloberStatusFilterDb(String statusName) throws SQLException {
		List<StatusGridSoonToBeGloberObjects> listOfGlobersStatusDb = stgDBHelper
				.getGloberStatusGridDataSoonToBeGlobers(statusName);
		return listOfGlobersStatusDb;
	}

	/**
	 * This method will return glober id for specified type from Db
	 * 
	 * @param type
	 * @return String
	 * @throws SQLException
	 */
	public String getGloberIdwithCatagoryDb(String type) throws SQLException {
		String globerId = stgDBHelper.getGloberIdwithCatagory(type);
		return globerId;
	}

	/**
	 * This method will get soon to be globers for New hire catagory and return
	 * response
	 * 
	 * @param response
	 * @param globerId
	 * @return Response
	 */
	public Response getCatsStatusSoonToBeGloberWithType(Response response, String globerId, String type) {
		int viewId = 5;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String catsStgNewHireUrl = GlobersBaseTest.baseUrl
				+ String.format(SoonTobeGlobersEndPoints.getCatsStatusSoonToBeGlobers, viewId, globerId, type);

		response = restUtils.executeGET(requestSpecification, catsStgNewHireUrl);

		return response;
	}

	/**
	 * This method will get soon to be globers status for all catagory from Db
	 * 
	 * @param globerId
	 * @return String
	 * @throws SQLException
	 */
	public String getCatsStatusForSoonToBeGloberDb(String globerId) throws SQLException {
		String catsStatus = stgDBHelper.getCatsStatusForSoonToBeGlober(globerId);
		return catsStatus;
	}

	/**
	 * This method will get soon to be globers for New hire catagory with Location
	 * filter and return response
	 * 
	 * @param response
	 * @param globerId
	 * @return Response
	 */
	public Response getCatsLocationSoonToBeGloberWithType(Response response, String globerId, String type) {
		int viewId = 5;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String catsLocationStgUrl = GlobersBaseTest.baseUrl
				+ String.format(SoonTobeGlobersEndPoints.getCatsLocationForSoonToBeGlober, viewId, globerId, type);

		response = restUtils.executeGET(requestSpecification, catsLocationStgUrl);
		return response;
	}

	/**
	 * This method will get soon to be globers Locations from Db
	 * 
	 * @param globerId
	 * @return String
	 * @throws SQLException
	 */
	public String getCatsLocationForSoonToBeGloberDb(String globerId) throws SQLException {
		String catsLocation = stgDBHelper.getCatsLocationForSoonToBeGlober(globerId);
		return catsLocation;
	}

	/**
	 * This method will get soon to be globers for candidate catagory with Location
	 * filter and return response
	 * 
	 * @param response
	 * @param globerId
	 * @return Response
	 */
	public Response getCatsLocationSoonToBeGloberCandidate(Response response, String globerId) {
		String type = "Candidate";
		int viewId = 5;

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String catsLocationStgUrl = GlobersBaseTest.baseUrl
				+ String.format(SoonTobeGlobersEndPoints.getCatsLocationForSoonToBeGlober,viewId, globerId, type);

		response = restUtils.executeGET(requestSpecification, catsLocationStgUrl);
		return response;
	}

	/**
	 * This method will get list of soon to be globers basic information from Db
	 * 
	 * @param globerId
	 * @return List
	 * @throws SQLException
	 */
	public List<SoonToBeGloberDetailsObjects> getSoonToBeGloberBasicInformationDb(String globerId) throws SQLException {
		List<SoonToBeGloberDetailsObjects> stgInfoList = stgDBHelper.getSoonToBeGloberBasicInformation(globerId);
		return stgInfoList;
	}

	/**
	 * This method return candidate id for soon to be glober from Db
	 * 
	 * @param STGType
	 * @return String
	 * @throws SQLException
	 */
	public String getStgCandidateIdDb(String stgType) throws SQLException {
		String stgCandidateId = getCandidateIdFromType(stgType);
		return stgCandidateId;
	}

	/**
	 * This method return candidate interview id for soon to be glober from Db
	 * 
	 * @param candidateId
	 * @return String
	 * @throws SQLException
	 */
	public String getCandidateInterviewIdDb(String candidateId) throws SQLException {
		String interviewId = stgDBHelper.getCandidateInterviewId(candidateId);
		return interviewId;
	}

	/**
	 * This method will get details of soon to be globers feedback and return
	 * response
	 * 
	 * @param response
	 * @param interviewId
	 * @return Response
	 */
	public Response getStgDetailsRecruitmentFeedback(Response response, String interviewId) {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String catsLocationStgUrl = GlobersBaseTest.baseUrl
				+ String.format(SoonTobeGlobersEndPoints.showRecruitmentFeedback, interviewId);

		response = restUtils.executeGET(requestSpecification, catsLocationStgUrl);

		return response;
	}

	/**
	 * This method return list of technical interview skills for soon to be glober
	 * from Db
	 * 
	 * @param candidateId
	 * @return ArrayList
	 * @throws SQLException
	 */
	public ArrayList<String> getTechnicalInterviewSkillsDb(String candidateId) throws SQLException {
		ArrayList<String> techSkillsList = stgDBHelper.getTechnicalInterviewSkills(candidateId);
		return techSkillsList;
	}

	/**
	 * This method will return global id for adding comment for STG from Db
	 * 
	 * @param STGType
	 * @return String
	 * @throws SQLException
	 */
	public String getGlobalIdForAddingCommentDb(String stgType) throws SQLException {
		String globalId = stgDBHelper.getGlobalIdForAddingComment(stgType);
		return globalId;
	}

	/**
	 * This test will add new comment for STG on talent pool grid
	 * 
	 * @param response
	 * @param requestParams
	 * @param name
	 * @param globalId
	 * @param comment
	 * @throws Exception
	 * @return Response
	 */
	public Response postAddCommentSoonToBeGlober(Response response, String requestParams, String name, String globalId,
			String comment) throws Exception {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId).contentType(ContentType.JSON).body(requestParams);

		if (name.equals("hazel.fernandes") || name.equals("neha.deshpande")) {

			String requestUrl = GlobersBaseTest.baseUrl + SoonTobeGlobersEndPoints.globerFeedbackApiUrl;

			response = restUtils.executePOST(requestSpecification, requestUrl);

			String globerFeedbackUrl = GlobersBaseTest.baseUrl
					+ String.format(SoonTobeGlobersEndPoints.globerFeedbackApiUrl + "/" + globalId);

			response = restUtils.executeGET(requestSpecification, globerFeedbackUrl);
			
			new GlobersBaseTest().validateResponseToContinueTest(response, 200, "Unable to add new comment for STG on talent pool grid.", true);

		} else {

			String requestUrl = GlobersBaseTest.baseUrl + SoonTobeGlobersEndPoints.globerFeedbackApiUrl;

			response = restUtils.executePOST(requestSpecification, requestUrl);
			
			new GlobersBaseTest().validateResponseToContinueTest(response, 403, "Unable to add new comment for STG on talent pool grid.", true);
		}
		return response;
	}
	
	/**
	 * This method will return list of feedback comments for STG from Db
	 * 
	 * @param globalId
	 * @return
	 * @throws SQLException
	 */
	public List<CommentsSoonToBeGlobersObjects> getSoonToBeGloberFeedbackCommentsDb(String globalId)
			throws SQLException {
		List<CommentsSoonToBeGlobersObjects> globalIdDb = stgDBHelper.getSoonToBeGloberFeedbackComments(globalId);
		return globalIdDb;
	}
	
	/**
	 * This test will get the feedback comments for STG on Talent pool grid
	 * 
	 * @param response
	 * @param jsonBody
	 * @param type
	 * @param globerId
	 * @throws Exception 
	 */
	public Response postToGetAddCommentSoonToBeGlober(Response response, String jsonBody, String type,
			String globerId) throws Exception {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId).contentType(ContentType.JSON).body(jsonBody);

		String postCommentStgUrl = GlobersBaseTest.baseUrl + SoonTobeGlobersEndPoints.globerFeedbackApiUrl;

		response = restUtils.executePOST(requestSpecification, postCommentStgUrl);

		new GlobersBaseTest().validateResponseToContinueTest(response, 200, "Unable to get the feedback comments for STG on Talent pool grid.", true);
		
		RequestSpecification requSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String commentStgUrl = GlobersBaseTest.baseUrl
				+ String.format(SoonTobeGlobersEndPoints.globerFeedbackApiUrl, "/", globerId, type);

		response = restUtils.executeGET(requSpecification, commentStgUrl);

		return response;
	}
	
	/**
	 * This test is used to get the full name of soon to be globers for SA/RA
	 * 
	 * @param response
	 * @param name
	 * @param stgType
	 * @param globerId
	 * @return
	 * @throws SQLException
	 */
	public Response getSoonToBeGloberFullName(Response response, String name, String stgType, String globerId)
			throws SQLException {
		String userId = stgDBHelper.getGloberId(name);
		String sSTGFullNameRequired = "true";
		String viewId = "5";

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String stgDetailPageUrl = GlobersBaseTest.baseUrl + String.format(SoonTobeGlobersEndPoints.getStgDetailsPageUrl, viewId,
				globerId, stgType, userId, sSTGFullNameRequired);

		response = restUtils.executeGET(requestSpecification, stgDetailPageUrl);

		return response;
	}
	
	/**
	 * This method will return full name of STG from Db
	 * 
	 * @param globerId
	 * @return String
	 * @throws SQLException
	 */
	public String getSoonToBeGloberFullNameDb(String globerId) throws SQLException {
		String stgFullNameDb = getSoonToBeGloberFullName(globerId);
		return stgFullNameDb;
	}

	/**
	 * This method will return first name for STG by catagory from Db
	 * 
	 * @param type
	 * @return String
	 * @throws SQLException
	 */
	public String getFirstNameForStgwithType(String type) throws SQLException {
		String globerName = stgDBHelper.getFirstNameForStgwithType(type);
		return globerName;
	}
	
	/**
	 * This test will check if the name of the glober populates after a user search
	 * for a STG on TP grid
	 * 
	 * @param response
	 * @param name
	 * @param globerName
	 * @param validateName
	 * @return
	 * @throws SQLException
	 */
	public Response getSearchResultsForSoonToBeGlober(Response response, String name, String globerName) throws SQLException {
		String userId = stgDBHelper.getGloberId(name);
		String roles = "Glober";
		int viewId = 2;
		String minDate = Utilities.getTodayDate("dd-MM-yyyy");
		String maxDate = Utilities.getFutureDate("dd-MM-yyyy", 30);

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);//.contentType(ContentType.JSON);//.body(validateName);

		String searchGloberUrl = GlobersBaseTest.baseUrl
				+ String.format(SoonTobeGlobersEndPoints.searchGloberUrl, viewId, userId,minDate,maxDate, globerName, roles);

		response = restUtils.executeGET(requestSpecification, searchGloberUrl);

		return response;
	}
	
	/**
	 * This method will return full name of glober
	 * 
	 * @param name
	 * @return String
	 * @throws SQLException
	 */
	public String getFullNamefromGloberFNameDb(String name) throws SQLException {
		String fName = getFullNamefromGloberFName(name);
		return fName;
	}
	
	/**
	 * This method will return glober firstName from DB
	 * 
	 * @param name
	 * @return String
	 * @throws SQLException
	 */
	public String getGloberFirstNameFromDb(String name) throws SQLException {
		String fName = getFullNamefromGloberFName(name);
		return fName;
	}
	
	/**
	 * This test will check if the name of the glober populates after a user search
	 * for a STG using suggest directly
	 * 
	 * @param response
	 * @param name
	 * @param stgType
	 * @return
	 * @throws SQLException
	 */
	public Response getSearchResultsForSoonToBeGlober_SuggestDirectly(Response response, String name, String stgType)
			throws SQLException {
		String globerName = null, validateName = null;
		String glober = "Glober";
		int viewId = 2, offset = 0, limit = 5;
				
		if (stgType.equals("Candidate")) {
			globerName = stgDBHelper.getFirstNameForStgwithType("Candidate").replaceAll(" ", "%20");
			validateName = globerName.replaceAll("%20", " ");
		} else if (stgType.equals("In Pipe")) {
			globerName = stgDBHelper.getFirstNameForStgwithType("In Pipe").replaceAll(" ", "%20");
			validateName = globerName.replaceAll("%20", " ");
		} else {
			globerName = stgDBHelper.getFirstNameForStgwithType("New Hire").replaceAll(" ", "%20");
			validateName = globerName.replaceAll("%20", " ");
		}

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId).contentType(ContentType.JSON).body(validateName);

		String searchGloberUrl = GlobersBaseTest.baseUrl + String
				.format(SoonTobeGlobersEndPoints.searchGloberSuggestdirectlyUrl, viewId, name, stgType, globerName, offset, limit, glober);

		response = restUtils.executeGET(requestSpecification, searchGloberUrl);

		return response;
	}
	
	/**
	 * This method will get new position skills and return response
	 * 
	 * @param response
	 * @return
	 * @throws SQLException
	 */
	public Response getNewPostionSkills(Response response) throws SQLException {
		String role = stgDBHelper.getRoleForPosition();
		String serniority = stgDBHelper.getPositionSeniority();

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String alberthaSkillsUrl = GlobersBaseTest.baseUrl
				+ String.format(StaffingEndpoints.getAlberthaSkills, role, serniority);

		response = restUtils.executeGET(requestSpecification, alberthaSkillsUrl);

		return response;
	}
	
	/**
	 * This method will return payload for position
	 * @param response
	 * @return String
	 * @throws SQLException
	 */
	public String getPositionPayloadSR(Response response) throws SQLException {
		Map<Object, Object> mapOfDbData = new SoonTobePayloadHelper().getPositionDbPayloadSR(response);
		String jsonBody = new SoonTobePayloadHelper().getPositionPayloadSR(mapOfDbData);
		return jsonBody;
	}
	
	/**
	 * This method will post request for STG positions and return response
	 * 
	 * @param payLoadBody
	 * @return
	 */
	public Response getPostPositionRequest(String payLoadBody) {

		RequestSpecification positionRequest = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId).contentType(ContentType.JSON).body(payLoadBody);

		String staffingPositionUrl = GlobersBaseTest.baseUrl + StaffRequestEndpoints.positionsUrl;

		Response positionJsonPath = restUtils.executePOST(positionRequest, staffingPositionUrl);

		return positionJsonPath;
	}
	
	/**
	 * This method will get SR details and return response
	 * 
	 * @param srNumber
	 * @return Response
	 */
	public Response getSrDetailsPath(String srNumber) {

		RequestSpecification positionRequest = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String srNumberPositionsUrl = GlobersBaseTest.baseUrl
				+ String.format(StaffRequestEndpoints.srNumberPositionsUrl, srNumber);

		Response srDetailsPath = restUtils.executeGET(positionRequest, srNumberPositionsUrl);

		return srDetailsPath;
	}
	
	/**
	 * This method will return payload for position with wrong SR start date
	 * @param response
	 * @return String
	 * @throws SQLException
	 */
	public String getPositionPayloadSRWithWrongSRStartDate(Response response) throws SQLException {
		Map<Object, Object> mapOfDbData = new SoonTobePayloadHelper().getPositionDbPayloadSR(response);
		mapOfDbData = new SoonTobePayloadHelper().getPositionPayloadSRWithWrongSRStartDate(mapOfDbData);
		String jsonBody = new SoonTobePayloadHelper().getPositionPayloadSR(mapOfDbData);
		return jsonBody;
	}
	
	/**
	 * This method will return payload for position with invalid Position type
	 * @param response
	 * @return String
	 * @throws SQLException
	 */
	public String getPositionPayloadSRWithInvalidPositionType(Response response) throws SQLException {
		Map<Object, Object> mapOfDbData = new SoonTobePayloadHelper().getPositionDbPayloadSR(response);
		mapOfDbData = new SoonTobePayloadHelper().getPositionPayloadSRWithInvalidPositionType(mapOfDbData);
		String jsonBody = new SoonTobePayloadHelper().getPositionPayloadSR(mapOfDbData);
		return jsonBody;
	}
	
	/**
	 * This method will return candidate id from glober id
	 * 
	 * @param globerId
	 * @return
	 * @throws SQLException
	 */
	public String getCandidateIdFromGloberIdDb(String globerId) throws SQLException {
		String candidateId = getCandidateIdFromGloberId(globerId);
		return candidateId;
	}
	
	/**
	 * This method will return recruiter name from Db
	 * 
	 * @param candidateId
	 * @return String
	 * @throws SQLException
	 */
	public String getRecruiterNameDb(String candidateId) throws SQLException {
		String recruiterName = stgDBHelper.getRecruiterName(candidateId);
		return recruiterName;
	}
	
	/**
	 * This method will return soon to be glober details from Db
	 * 
	 * @return ArrayList
	 * @throws SQLException
	 */
	public ArrayList<String> getStgDetailDb() throws SQLException {
		ArrayList<String> stgCandidateNames = stgDBHelper.getStgDetails();
		return stgCandidateNames;
	}

	/**
	 * This method will get partial name for soon to be glober and return response
	 * 
	 * @param response
	 * @param name
	 * @return Response
	 * @throws SQLException
	 */
	public Response getPartialNameForStg(Response response, String name) throws SQLException {
		Map<Object, Object> suggesGloberMap = new HashMap<Object, Object>();
		int offset = 0;
		suggesGloberMap = getSoonTobeGlobersData(name);
		
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String getPartialNameForStgUrl = GlobersBaseTest.baseUrl
				+ String.format(SoonTobeGlobersEndPoints.getPartialNameWithTypeForStgUrl, viewId, suggesGloberMap.get("userId"), suggesGloberMap.get("parentViewId"), offset,
						suggesGloberMap.get("sorting"), suggesGloberMap.get("type"), pipe, hire, suggesGloberMap.get("minDate"), suggesGloberMap.get("maxDate"));

		response = restUtils.executeGET(requestSpecification, getPartialNameForStgUrl);

		return response;
	}
	
	/**
	 * This method will get partial name for soon to be glober for all globers and
	 * return response
	 * 
	 * @param response
	 * @param name
	 * @return Response
	 * @throws SQLException
	 */
	public Response getPartialNameForStgForAllGloberPage(Response response, String name) throws SQLException {
		Map<Object, Object> suggesGloberMap = new HashMap<Object, Object>();
		suggesGloberMap = getSoonTobeGlobersData(name);
		
		int viewId = 5,offset = 0;

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String getPartialNameForAllGloberStgUrl = GlobersBaseTest.baseUrl
				+ String.format(SoonTobeGlobersEndPoints.getPartialNameForStgAllGloberPageUrl, viewId,suggesGloberMap.get("userId"), suggesGloberMap.get("parentViewId"),
						offset, suggesGloberMap.get("sorting"), suggesGloberMap.get("type"), pipe, hire);

		response = restUtils.executeGET(requestSpecification, getPartialNameForAllGloberStgUrl);

		return response;
	}
	
	/**
	 * This method will get soon to be globers name without process status
	 * 
	 * @return ArrayList
	 * @throws SQLException
	 */
	public ArrayList<String> getStgNameWithoutProcessStatusDb() throws SQLException {
		ArrayList<String> candidateNamesDb = stgDBHelper.getStgNameWithoutProcessStatus();
		return candidateNamesDb;
	}
	
	/**
	 * This method will get soon to be globers recruiting status
	 * 
	 * @param response
	 * @param name
	 * @return Response
	 * @throws SQLException
	 */
	public Response getRecrutingStatusOfStg(Response response, String name) throws SQLException {
		Map<Object, Object> suggesGloberMap = new HashMap<Object, Object>();
		suggesGloberMap = getSoonTobeGlobersData(name);
		
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String getPartialNameForStgUrl = GlobersBaseTest.baseUrl
				+ String.format(SoonTobeGlobersEndPoints.getPartialNameForStgUrl, viewId, suggesGloberMap.get("userId"), suggesGloberMap.get("parentViewId"), suggesGloberMap.get("offset"), suggesGloberMap.get("sorting"),
						suggesGloberMap.get("minDate"), suggesGloberMap.get("maxDate"));

		response = restUtils.executeGET(requestSpecification, getPartialNameForStgUrl);

		return response;
	}
	
	/**
	 * This method will get soon to be globers details and return response
	 * 
	 * @param response
	 * @param type
	 * @param name
	 * @return Response
	 * @throws SQLException
	 */
	public Response getDetailsOfStg(Response response, String type, String name) throws SQLException {
		String globerId = getGloberIdFromGlobalIdDb(type);
		String userId = stgDBHelper.getGloberId(name);
		String isSTGFullNameRequired = "true";
		int viewId = 5;
		
		if (type.equals("In Pipe"))
			type = type.replace("In Pipe", "In%20Pipe");
		else if (type.equals("New Hire"))
			type = type.replace("New Hire", "New%20Hire");

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String getFullNameForStgAllGloberPageUrl = GlobersBaseTest.baseUrl
				+ String.format(SoonTobeGlobersEndPoints.getFullNameForStgAllGloberPageUrl, viewId, globerId, type, userId,
						isSTGFullNameRequired);

		response = restUtils.executeGET(requestSpecification, getFullNameForStgAllGloberPageUrl);

		return response;
	}
	
	/**
	 * This method will get glober id from global id from Db
	 * 
	 * @param type
	 * @return String
	 * @throws SQLException
	 */
	public String getGloberIdFromGlobalIdDb(String type) throws SQLException {
		String globalId = stgDBHelper.getGlobalId(type);
		String globerId = stgDBHelper.getGloberIdFromGlobalId(globalId);
		return globerId;
	}
	
	/**
	 * This method will get seniority of soon to be glober from Db
	 * 
	 * @param stgName
	 * @return String
	 * @throws SQLException
	 */
	public String getSeniorityofStgDb(String stgName) throws SQLException {
		String stgNameDb = stgDBHelper.getSeniorityofStg(stgName);
		return stgNameDb;
	}
	
	/**
	 * This method will get position of soon to be glober from Db
	 * 
	 * @param globerId
	 * @return String
	 * @throws SQLException
	 */
	public String getPositionofStgDb(String globerId) throws SQLException {
		String globerIdDb = stgDBHelper.getPositionofStg(globerId);
		return globerIdDb;
	}
	
	/**
	 * This method will get handler name of soon to be glober from Db
	 * 
	 * @param globerId
	 * @return String
	 * @throws SQLException
	 */
	public String getHandlernameofStgDb(String globerId) throws SQLException {
		String globerIdDb = stgDBHelper.getHandlernameofStg(globerId);
		return globerIdDb;
	}
	
	/**
	 * This method will return payload for STG new suggestion via PM with wrong SR
	 * id
	 * @param globerType
	 * @param planType
	 * @return String
	 * @throws SQLException
	 */
	public String newSuggestViaPMwithWrongSrId(String globerType, String planType) throws SQLException {
		Map<Object, Object> mapOfDbData = new SoonTobePayloadHelper().newSuggestViaPMDbPositivePayload(globerType);
		mapOfDbData = new SoonTobePayloadHelper().newSuggestViaPMwithWrongSrId(mapOfDbData);
		String jsonBody = new SoonTobePayloadHelper().newSuggestViaPM(mapOfDbData, planType);
		return jsonBody;
	}
	
	/**
	 * This method will post suggested glober and return response
	 * 
	 * @param response
	 * @param jsonBody
	 * @param i
	 * @param name
	 * @return Response
	 */
	public Response postSuggestStgNotMoreThanOnceViaPMandTDWithValidations(Response response, String jsonBody, int i,
			String name) {
		String suggestGloberUrl;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId).contentType(ContentType.JSON).body(jsonBody);

		if (i == 0) {
			suggestGloberUrl = GlobersBaseTest.baseUrl + SoonTobeGlobersEndPoints.suggestGloberUrl;

			response = restUtils.executePOST(requestSpecification, suggestGloberUrl);
		} else if (name.equals("hazel.fernandes") || name.equals("neha.deshpande")) {
			suggestGloberUrl = GlobersBaseTest.baseUrl + SoonTobeGlobersEndPoints.suggestGloberUrl;

			response = restUtils.executePOST(requestSpecification, suggestGloberUrl);
		} else {
			suggestGloberUrl = GlobersBaseTest.baseUrl + SoonTobeGlobersEndPoints.suggestGloberUrl;

			response = restUtils.executePOST(requestSpecification, suggestGloberUrl);
		}
		return response;
	}
	
	/**
	 * This method will return payload for STG new suggestion via PM with null SR id
	 * @param globerType
	 * @param planType
	 * @return String
	 * @throws SQLException
	 */
	public String newSuggestViaPMWithNullSrId(String globerType, String planType) throws SQLException {
		Map<Object, Object> mapOfDbData = new SoonTobePayloadHelper().newSuggestViaPMDbPositivePayload(globerType);
		mapOfDbData = new SoonTobePayloadHelper().newSuggestViaPMWithNullSrId(mapOfDbData);
		String jsonBody = new SoonTobePayloadHelper().newSuggestViaPM(mapOfDbData, planType);
		return jsonBody;
	}
	
	/**
	 * This method will return payload for STG new suggestion via PM with invalid
	 * plan start id
	 * @param globerType
	 * @param planType
	 * @return String
	 * @throws SQLException
	 */
	public String getPayloadNewSuggestViaPMWithInvalidStDt(String globerType, String planType) throws SQLException{
		Map<Object, Object> mapOfDbData = new SoonTobePayloadHelper().newSuggestViaPMDbPositivePayload(globerType);
		mapOfDbData = new SoonTobePayloadHelper().newSuggestViaPMWithInvalidPlanStartDate(mapOfDbData);
		String jsonBody = new SoonTobePayloadHelper().newSuggestViaPM(mapOfDbData, planType);
		return jsonBody;
	}
	
	/**
	 * This method will return payload for STG new suggestion via PM
	 * @param globerType
	 * @param planType
	 * @return String
	 * @throws SQLException
	 */
	public String getPayloadNewSuggestViaPM(String globerType, String planType) throws SQLException {
		Map<Object, Object> mapOfDbData = new SoonTobePayloadHelper().newSuggestViaPMDbPositivePayload(globerType);
		String jsonBody = new SoonTobePayloadHelper().newSuggestViaPM(mapOfDbData, planType);
		return jsonBody;
	}
	
	/**
	 * This method will post Notification while changing Handler for STG
	 * 
	 * @param response
	 * @param jsonBody
	 * @return Response
	 */
	public Response postNotification(Response response, String jsonBody) {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId).contentType(ContentType.JSON).body(jsonBody);

		String globerUrl = GlobersBaseTest.baseUrl + SoonTobeGlobersEndPoints.globerUrl;

		response = restUtils.executePOST(requestSpecification, globerUrl);

		return response;
	}
	
	/**
	 * This method will get SR date from Db
	 * 
	 * @param srNumber
	 * @return String
	 * @throws SQLException
	 */
	public String getCandidateIdDb() throws SQLException {
		String candidateId = stgDBHelper.getCandidateId();
		return candidateId;
	}

	/**
	 * This method will get skills for soon to be glober and return response
	 * 
	 * @param response
	 * @param name
	 * @param candidateId
	 * @return Response
	 */
	public Response showSkillsForSoonToBeGlobers(Response response, String name, String candidateId) {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String showSkillsForSoonToBeGloberUrl = GlobersBaseTest.baseUrl
				+ String.format(SoonTobeGlobersEndPoints.showSkillsForSoonToBeGloberUrl, candidateId);

		response = restUtils.executeGET(requestSpecification, showSkillsForSoonToBeGloberUrl);

		return response;
	}
	
	/**
	 * This method will get technical skill for candidate from Db
	 * 
	 * @param candidateId
	 * @return String
	 * @throws SQLException
	 */
	public String getTechnicalSkillsForCandidateDb(String candidateId) throws SQLException {
		String techSkillsStgDb = stgDBHelper.getTechnicalSkillsForCandidate(candidateId);
		return techSkillsStgDb;
	}
	
	/**
	 * This method will get SR number from Db
	 * 
	 * @return String
	 * @throws SQLException
	 */
	public String getSrNumberDb() throws SQLException {
		String futureDate = Utilities.getFutureDate("yyyy-MM-dd", 30);
		String srNumber = stgDBHelper.getSrNumber(futureDate);
		return srNumber;
	}
	
	/**
	 * This method will return new suggestion for glober with provided SR
	 * @param globerType
	 * @param planType
	 * @param srNumber
	 * @return String
	 * @throws SQLException
	 */
	public String newSuggestionWithSameSr(String globerType, String planType, String srNumber) throws SQLException {
		Map<Object, Object> mapOfDbData = new SoonTobePayloadHelper().newSuggestViaPMDbPositivePayload(globerType);
		mapOfDbData = new SoonTobePayloadHelper().newSuggestionWithSameSr(mapOfDbData, srNumber);
		String jsonBody = new SoonTobePayloadHelper().newSuggestViaPM(mapOfDbData, planType);
		return jsonBody;
	}
	
	/**
	 * This method will get
	 * 
	 * @param response
	 * @param payLoadBody
	 * @param sPositionId
	 * @return String
	 * @throws Exception
	 */
	public String getRecruiterName(Response response, String payLoadBody, String sPositionId) throws Exception {
		String type = null;
		String Leader = null;
		String globerId = null;
		String suggestionDetailsPage = "true";

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId).contentType(ContentType.JSON).body(payLoadBody);

		String suggestGloberUrl = GlobersBaseTest.baseUrl + SoonTobeGlobersEndPoints.suggestGloberUrl;

		response = restUtils.executePOST(requestSpecification, suggestGloberUrl);

		RequestSpecification requestSpec = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String suggestGloberDeltailUrl = GlobersBaseTest.baseUrl
				+ String.format(SoonTobeGlobersEndPoints.suggestGloberUrl, sPositionId, suggestionDetailsPage);

		response = restUtils.executeGET(requestSpec, suggestGloberDeltailUrl);

		type = (String) restUtils.getValueFromResponse(response, "details.globerType");
		Leader = (String) restUtils.getValueFromResponse(response, "details.globerLeader");
		globerId = ((String) restUtils.getValueFromResponse(response, "details.globerId")).replaceAll("\\[|\\]", "");

		String[] aType = type.replaceAll("\\[|\\]", "").split(",");
		String[] aLeader = Leader.replaceAll("\\[|\\]", "").split(",");

		for (int i = 0; i < aType.length; i++) {
			if (aType[i].equals("In Pipe") || aType[i].equals("Candidate") || aType[i].equals("New Hire"))
				Assert.assertEquals("Suggested Card should have Leader Name : " + aLeader[i],
						"Suggested Card should have Leader Name : N/A");
			else
				Assert.assertNotEquals("Suggested Card should not have leader Name : " + aLeader[i],
						"Suggested Card should not have leader Name : N/A");
		}
		return globerId;
	}
	
	/**
	 * This method will get suggested soon to be globers details and return response
	 * 
	 * @param response
	 * @param name
	 * @param globerType
	 * @return ArrayList
	 * @throws Exception
	 */
	public ArrayList<String> getDetailsWithSuggestionStg(Response response, String name, String globerType)
			throws Exception {
		ArrayList<String> arrlst = new ArrayList<String>();
		String globerName = null;
		String srNumber = getSrNumberDb();
		String positionId = getPositionId(srNumber);
		String payload;

		if (name.equalsIgnoreCase("hazel.fernandes") || name.equalsIgnoreCase("neha.deshpande")) {
			payload = newSuggestionWithSameSr(globerType, "Low", srNumber);

			globerName = getRecruiterName(response, payload, positionId);
		} else {
			payload = newSuggestionWithSameSr(globerType, "High", srNumber);

			globerName = getRecruiterName(response, payload, positionId);
		}
		String ids[] = new String[2];
		if (globerName.contains(",")) {
			ids = globerName.split(",");
		} else {
			ids[0] = globerName;
		}

		arrlst.add(srNumber);
		arrlst.add(ids[0]);

		return arrlst;
	}
	
	/**
	 * This method will get position id from Db
	 * 
	 * @param srNumber
	 * @return String
	 * @throws SQLException
	 */
	public String getPositionIdDb(String srNumber) throws SQLException {
		String positionId = getPositionId(srNumber);
		return positionId;
	}
	
	/**
	 * This method will post Assignment via SA
	 * 
	 * @param response
	 * @param payLoadBody
	 * @param positionId
	 * @return Response
	 */
	public Response postAssignViaSA(Response response, String payLoadBody, String positionId) {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId).contentType(ContentType.JSON).body(payLoadBody);

		String globerUrl = GlobersBaseTest.baseUrl + StaffingEndpoints.globerUrl;

		response = restUtils.executePOST(requestSpecification, globerUrl);

		return response;
	}
	
	/**
	 * This will get soon to be globers name with New hire status from Db
	 * 
	 * @return ArrayList
	 * @throws SQLException
	 */
	public ArrayList<String> getNameofStgWithNewHireStatusDb() throws SQLException {
		ArrayList<String> stgName = stgDBHelper.getNameOfSTGWithNewHireStatus();
		return stgName;
	}
}
