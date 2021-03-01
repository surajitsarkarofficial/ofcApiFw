package tests.testcases.submodules.reports.features.handlerandhandlerteam;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import database.submodules.reports.features.handlerandhandlerteam.HandlerAndHandlerTeamDBHelper;
import dataproviders.submodules.reports.features.handlerandhandlerteam.HandlerAndHandlerTeamDataProvider;
import io.restassured.response.Response;
import payloads.submodules.reports.features.handlerandhandlerteam.HandlerAndHandlerTeamPayloadHelper;
import tests.testcases.submodules.reports.ReportsBaseTest;
import tests.testhelpers.submodules.reports.features.handlerandhandlerteam.HandlerAndHandlerTeamTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;
import utils.Utilities;

public class HandlerAndHandlerTeamTest  extends ReportsBaseTest{
	
	//handler payload input
	ArrayList<String> position_role_id = new ArrayList<String>();
	ArrayList<String> location = new ArrayList<String>();

	RestUtils restUtils;
	HandlerAndHandlerTeamTestHelper handlerAndHandlerTeam;
	HandlerAndHandlerTeamDBHelper handlerDb;
	HandlerAndHandlerTeamPayloadHelper handlerPayload;
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {

		featureTest = subModuleTest.createNode("Handler and HandlerTeam");

	}
	
	public HandlerAndHandlerTeamTest() {
		restUtils = new RestUtils();
	}
	
	/**
	 * This Test will validate handler by editing Sr by location
	 * 
	 * @param username
	 * @param location
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "globerAssignmnentAccessRoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Regression,"Glow-II"})
	public void validateHandlerAccordingToLocationByEditingSr(String userName,String location) throws Exception {
		handlerAndHandlerTeam = new HandlerAndHandlerTeamTestHelper(userName);
		Response response = handlerAndHandlerTeam.updateSrWithProvidedLocation(userName,location);
		String handlerName = restUtils.getValueFromResponse(response, "details.positionDTOList[0].handler").toString();
		String handlerTeamName = restUtils.getValueFromResponse(response, "details.positionDTOList[0].handlerTeam").toString();
		
		Assert.assertEquals(handlerName, HandlerAndHandlerTeamTestHelper.getHandlerName(),String.format("Expected handler is %s and Actual handler is %s", HandlerAndHandlerTeamTestHelper.getHandlerName(),handlerName));
		Assert.assertEquals(handlerTeamName, HandlerAndHandlerTeamTestHelper.getHandlerTeamName(),String.format("Expected handler is %s and Actual handler is %s", HandlerAndHandlerTeamTestHelper.getHandlerTeamName(),handlerTeamName));
	}
	
	/**
	 * This Test will validate handler email by assigning STGs with PM and TD roles
	 * 
	 * @param userName
	 * @param type
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaPMTDRoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Regression,"Glow-II"})
	public void validateHandlerEmailWhileAssigningSTGsViaPMTD(String userName,String type) throws Exception {
		handlerAndHandlerTeam = new HandlerAndHandlerTeamTestHelper(userName);
		RestUtils restUtils = new RestUtils();
		handlerDb = new HandlerAndHandlerTeamDBHelper();
		
		Response response = handlerAndHandlerTeam.getHandlerData(userName, type);
		String handlerInfo[] = handlerDb.getHandlerIdWithEmail(type);
		String handlerId = handlerInfo[0];
		String handlerEmailFromDb = handlerInfo[1];
		
		Response handlerData = handlerAndHandlerTeam.getHandlerEmailWhileAssigningSTG(response, userName, type,handlerId);
		validateResponseToContinueTest(handlerData, 200, "Issue With GET API response.", true);
		String handlerEmail = restUtils.getValueFromResponse(handlerData, "email").toString();
		handlerAndHandlerTeam.updatePlanStatusOfSTGPlan(userName);
		Assert.assertEquals(handlerEmail, handlerEmailFromDb,String.format("Expected handler mail is %s and Actual handler mail is %s", handlerEmailFromDb,handlerEmail));
		
	}
	
	/**
	 * This Test will validate Sr handler with providing position and location
	 * 
	 * @param userName
	 * @param type
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaSARARoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void validateSrHandlerWithPositionAndLocation(String userName,String type) throws Exception {
		int TIMEOUT = 1;
		RestUtils restUtils = new RestUtils();
		handlerAndHandlerTeam = new HandlerAndHandlerTeamTestHelper(userName);
		handlerPayload = new HandlerAndHandlerTeamPayloadHelper();
		handlerDb = new HandlerAndHandlerTeamDBHelper();
		
		Response changeHandlerResponse = handlerAndHandlerTeam.performChangeHandler(userName, type);
		validateResponseToContinueTest(changeHandlerResponse, 200, "Unable to change the handler.", true);
		String status = restUtils.getValueFromResponse(changeHandlerResponse, "status").toString();
		String changeHandlerMsg = restUtils.getValueFromResponse(changeHandlerResponse, "message").toString();
		
		Assert.assertEquals(status, "success","Failed in handler response...Status Mismatch");
		Assert.assertEquals(changeHandlerMsg, "We are processing the request, will notify in mail once updates are completed",
				String.format("Expected message is We are processing the request, will notify and Actual message is %s",changeHandlerMsg));
		TimeUnit.MINUTES.sleep(TIMEOUT);
		
		Response handlerFilterData = handlerAndHandlerTeam.getChangedHandlerDetails(userName);
		validateResponseToContinueTest(handlerFilterData, 200, "Unable to apply filter.", true);
		String handlerName = restUtils.getValueFromResponse(handlerFilterData, "details.positionDTOList[0].handler").toString();
		String srNumber = restUtils.getValueFromResponse(handlerFilterData, "details.positionDTOList[0].srNumber").toString();
		String positionId = restUtils.getValueFromResponse(handlerFilterData, "details.positionDTOList[0].positionId").toString();
		System.out.println(srNumber);
		
		Assert.assertEquals(handlerName, handlerPayload.getHandlerName(),String.format("Expected handler is %s and Actual handler is %s", handlerPayload.getHandlerName(),handlerName));
	
		Response srWatcherData = handlerAndHandlerTeam.getSrHandlerWatcher(positionId);
		validateResponseToContinueTest(srWatcherData, 200, "Unable to get Sr watcher..Issue with GET API", true);
		io.restassured.path.json.JsonPath js = srWatcherData.jsonPath();
		String handler = ((io.restassured.path.json.JsonPath) js).getString("details.srHandler");
		
		String watcherNameFromDb =handlerDb.getWatcherNameByPosition(srNumber);
		
		if(handler == null) {
			String[] names = watcherNameFromDb.split(" ");
			String watcherState = handlerDb.getGloberInternalAssignTypeByWatcherName(names);
			Assert.assertEquals(watcherState, "OUT_OF_COMPANY",String.format("Expected watcher state is OUT_OF_COMPANY and Actual watcher state is %s",watcherState));
			test.log(Status.PASS, "Watcher status is validated.");
		}
		else {
			String watcher_name = restUtils.getValueFromResponse(srWatcherData, "details.srHandler.watcherName").toString();
			Assert.assertEquals(watcher_name, watcherNameFromDb,String.format("Expected watcher : %s and Actual watcher : %s", watcherNameFromDb,watcher_name));
			test.log(Status.PASS, "Watcher is Validated");
		}		
	}
	
	/**
	 * This Test will validate watcher of Sr
	 * 
	 * @param userName
	 * @param type
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaSARARoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Regression,"Glow-II"})
	public void validateWatcherFromSrGrid(String userName,String type) throws Exception {
		
		RestUtils restUtils = new RestUtils();
		handlerAndHandlerTeam = new HandlerAndHandlerTeamTestHelper(userName);
		handlerDb = new HandlerAndHandlerTeamDBHelper();
		
		Response response = handlerAndHandlerTeam.getHandlerData(userName,type);
			
		Response changeHandlerFromSrGrig = handlerAndHandlerTeam.getWatcherFromSrGRid(userName, type,response);
		validateResponseToContinueTest(changeHandlerFromSrGrig, 200, "Unable to change handler from Sr grid.", true);
		String msg = restUtils.getValueFromResponse(changeHandlerFromSrGrig, "message").toString();
		
		Assert.assertEquals(msg, "Handler changed successfully","Failed to change handler...Message : "+msg);
		test.log(Status.PASS, "Handler changed successfully.");
		
		Response watcherData = handlerAndHandlerTeam.getHandlerWatcherDetails(userName, type);
		validateResponseToContinueTest(watcherData, 200, "Unable to get Sr watcher..Issue with GET API response", true);
		String watcherNameFromDb = handlerDb.getWatcherNameByPosition(handlerAndHandlerTeam.getSrNumber());
		io.restassured.path.json.JsonPath js = watcherData.jsonPath();
		String handler = js.getString("details.srHandler");
		
		if(handler == null) {
			String[] names = watcherNameFromDb.split(" ");
			String watcherState = handlerDb.getGloberInternalAssignTypeByWatcherName(names);
			Assert.assertEquals(watcherState, "OUT_OF_COMPANY",String.format("Expected watcher state is OUT_OF_COMPANY and Actual watcher state is %s",watcherState));
			test.log(Status.PASS, "Watcher status is validated.");
		}
		else {
			String watcherName = restUtils.getValueFromResponse(watcherData, "details.srHandler.watcherName").toString();
			Assert.assertEquals(watcherName, watcherNameFromDb,String.format("Expected watcher state is %s and Actual watcher state is %s",watcherNameFromDb,watcherName));
			test.log(Status.PASS, "Watcher is Validated");
		}
	}
	
	/**
	 * This Test will validate watcher of Sr with wrong payload with SA and RA users
	 * 
	 * @param userName
	 * @param type
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaSARARoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Regression,"Glow-II"})
	public void verifyErrorMsgWhileGetWatcherFromSRGridWithWrongPayload(String userName,String type) throws Exception {
		RestUtils restUtils = new RestUtils();
		handlerAndHandlerTeam = new HandlerAndHandlerTeamTestHelper(userName);
		
		Response response = handlerAndHandlerTeam.performChangeHandlerWithWrongHandlerId(userName, type);
		validateResponseToContinueTest(response, 500, "Unable to POST request.", true);
		String msg = restUtils.getValueFromResponse(response, "message").toString();
		Assert.assertEquals(msg, "Invalid Input.","Expected message is : Invalid position id and Actual message is : "+msg);
		test.log(Status.PASS, "Handler payload is Validated");
	}
	
	/**
	 * This Test will validate error message while get watcher with from posiitonId
	 * 
	 * @param userName
	 * @param type
	 * @throws Exception
	 */
	@Test(dataProvider = "changeHandlerViaSARARoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Regression,"Glow-II"})
	public void verifyErrorMsgForGetWatcherFromSRGridWithInvalidPositionID(String userName,String type) throws Exception {
		RestUtils restUtils = new RestUtils();
		handlerAndHandlerTeam = new HandlerAndHandlerTeamTestHelper(userName);
		
		Response response = handlerAndHandlerTeam.performChangeHandlerWithWrongPositionId(userName, type);
		validateResponseToContinueTest(response, 400, "Unable to POST request.", true);	
		String msg = restUtils.getValueFromResponse(response, "message").toString();
		Assert.assertEquals(msg, "Invalid position id","Expected message is : Invalid position id and Actual message is : "+msg);
		test.log(Status.PASS, "Handler payload is Validated");
	}
	
	/**
	 * This Test will validate handler for active globers
	 * 
	 * @param userName
	 * @param type
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaSARARoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void validateHandlerForActiveGlobers(String userName,String type) throws Exception {
		ArrayList<String> location = new ArrayList<String>();
		ArrayList<String> position_role_id = new ArrayList<String>();
		int TIMEOUT = 1;
		RestUtils restUtils = new RestUtils();
		handlerPayload = new HandlerAndHandlerTeamPayloadHelper();
		handlerAndHandlerTeam = new HandlerAndHandlerTeamTestHelper(userName);
		handlerDb = new HandlerAndHandlerTeamDBHelper();
		
		Response allActiveGlobers = handlerAndHandlerTeam.changeHandlerforActiveGlobers(userName, type);
		validateResponseToContinueTest(allActiveGlobers, 200, "Unable to get all globers.", true);
		
		int i = new Random().nextInt(15);
		String locationId = restUtils.getValueFromResponse(allActiveGlobers, "details.globerDTOList[" + i + "].locationId").toString();
		String locationName = restUtils.getValueFromResponse(allActiveGlobers, "details.globerDTOList[" + i + "].location").toString();
		String positionName = restUtils.getValueFromResponse(allActiveGlobers, "details.globerDTOList[" + i + "].position").toString();
		String handlerId = restUtils.getValueFromResponse(allActiveGlobers, "details.globerDTOList[" + i + "].handler.handlerId").toString();

	    String concatedPosition = positionName.toLowerCase().replaceAll(" ", "%20");
	    position_role_id.add(handlerDb.getPositionRoleIdAccordingToPositionRolName(positionName));
	    location.add(locationName);
	
	    Response getActiveGloberHandlerResponse =  handlerAndHandlerTeam.changeHandlerOfActiveGlobers(handlerId, location, position_role_id);
	    validateResponseToContinueTest(allActiveGlobers, 200, "Unable to change the handler of active globers.", true);   
	    String status = restUtils.getValueFromResponse(getActiveGloberHandlerResponse, "status").toString();
	    String msg = restUtils.getValueFromResponse(getActiveGloberHandlerResponse, "message").toString();
	    
	    Assert.assertEquals(status, "success","Failed to change handler of active glober...Message : "+msg);
	    test.log(Status.PASS, "Active globers handler changed successfully.");
	    
	    TimeUnit.MINUTES.sleep(TIMEOUT);
	    
	    Response getAllFilteredGlober = handlerAndHandlerTeam.getAllActiveGlobersByApplyingFilter(userName, type, concatedPosition, locationId);
	    validateResponseToContinueTest(getAllFilteredGlober, 200, "Unable to apply filter for active globers.", true);
	    
	    String actualHandler = restUtils.getValueFromResponse(getAllFilteredGlober, "details.globerDTOList[0].handler.handlerName").toString();
	    Assert.assertEquals(actualHandler, handlerPayload.getHandlerName(),String.format("Expected handler is %s and Actual handler is %s",handlerPayload.getHandlerName(),actualHandler));
	    test.log(Status.PASS, "Active glober handler validated");
	}
	
	/**
	 * This Test will validate glober handler according to position and location
	 * 
	 * @param userName
	 * @param type
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaSARARoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyGloberHandlerAccordingToPositionLocation(String userName,String type) throws Exception {
		
		int i = new Random().nextInt(15);
		handlerAndHandlerTeam = new HandlerAndHandlerTeamTestHelper(userName);
		handlerDb = new HandlerAndHandlerTeamDBHelper();
		
		Response allActiveGlobersResponse = handlerAndHandlerTeam.checkGloberHandlerIsChangedViaPMTD(userName, type);
		validateResponseToContinueTest(allActiveGlobersResponse, 200, "Unable to fetch all globers", true);
		
		String glober_type = restUtils.getValueFromResponse(allActiveGlobersResponse, "details.globerDTOList[" + i + "].type").toString();
		String OldHandlerId = restUtils.getValueFromResponse(allActiveGlobersResponse, "details.globerDTOList[" + i + "].handler.handlerId").toString();
		String oldHandlerName = restUtils.getValueFromResponse(allActiveGlobersResponse, "details.globerDTOList[" + i + "].handler.handlerName").toString();
		String activeGloberId = restUtils.getValueFromResponse(allActiveGlobersResponse, "details.globerDTOList[" + i + "].globerId").toString();
		
		Response globerHandlerResponse = handlerAndHandlerTeam.changeGloberHandler(activeGloberId, glober_type, OldHandlerId);
		validateResponseToContinueTest(globerHandlerResponse, 200, "Unable to change glober handler", true);
		String status = restUtils.getValueFromResponse(globerHandlerResponse, "status").toString();
		String globerHandlerMsg = restUtils.getValueFromResponse(globerHandlerResponse, "message").toString();
		
		Assert.assertEquals(status, "success","Failed to change glober handler..Actual message : "+globerHandlerMsg);
		test.log(Status.PASS, "Glober handler is changed successfully.");
		
		String globerAction = handlerDb.getGloberHandlerAction(activeGloberId);
		Response getGlober = handlerAndHandlerTeam.getGlober(userName, activeGloberId);
		validateResponseToContinueTest(getGlober, 200, "Unable to change glober handler", true);
		
		String newHandlerName = restUtils.getValueFromResponse(getGlober, "details.globerDTOList[0].handler.handlerName").toString();	
		String globerNewAndOldHandler[] = handlerDb.getGloberHandlerHistoryById(activeGloberId);
		
		Assert.assertEquals(newHandlerName , globerNewAndOldHandler[1],String.format("Expected new handler is %s and Actual new handler is %s",globerNewAndOldHandler[1],newHandlerName));
		test.log(Status.PASS, "Glober new handler is validated.");
		
		Assert.assertEquals(oldHandlerName, globerNewAndOldHandler[0],String.format("Expected old handler is %s and Actual old handler is %s",globerNewAndOldHandler[0],oldHandlerName));
		test.log(Status.PASS, "Glober old handler is validated.");
			
		Assert.assertEquals(globerAction, "UPDATE_HANDLER",String.format("Expected glober action is UPDATE_HANDLER and Actual old handler is %s",globerAction));
		test.log(Status.PASS, "Glober action is validated.");
	}
	
	/**
	 * This Test will validate Sr handler by editing an Sr
	 * 
	 * @param userName
	 * @param location
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "globerAssignmnentAccessRoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Regression,"Glow-II"})
	public void verifyHandlerByEditingSR(String userName,String location) throws Exception {
		
		handlerAndHandlerTeam = new HandlerAndHandlerTeamTestHelper(userName);
		handlerDb = new HandlerAndHandlerTeamDBHelper();
		
		Response srResponse = handlerAndHandlerTeam.getHandlerDataToEditSr(userName, location);
		validateResponseToContinueTest(srResponse, 200, "Unable to get Sr Data.", true);	
		String status = restUtils.getValueFromResponse(srResponse, "status").toString();
		String srNumber = restUtils.getValueFromResponse(srResponse, "details.positionDTOList[0].srNumber").toString();
		
		Assert.assertEquals(status, "success",String.format("Expected status : success and Actual status : %s", status));
		test.log(Status.PASS, "Sr data fetch successfully.");
		
		Response editSrResponse = handlerAndHandlerTeam.getHandlerByEditSr(userName,srNumber);
		validateResponseToContinueTest(editSrResponse, 200, "Unable to update Sr", true);		
		String editSrStatus = restUtils.getValueFromResponse(editSrResponse, "status").toString();
		
		Assert.assertEquals(editSrStatus, "success",String.format("Expected status : success and Actual status : %s", editSrStatus));
		test.log(Status.PASS, "Edit an Sr successfully.");
		
		Response getSrResponse = handlerAndHandlerTeam.getSrBySearchingOnSrGrid(srNumber);
		validateResponseToContinueTest(getSrResponse, 200, "Unable to search Sr on SrGrid", true);
		
		String handlerId = restUtils.getValueFromResponse(getSrResponse, "details.positionDTOList[0].handlerId").toString();
		String handlerName = restUtils.getValueFromResponse(getSrResponse, "details.positionDTOList[0].handler").toString();		
		String newHandlerName[] = handlerDb.getHandlerNameFromId(handlerId);
	    
	    Assert.assertEquals(handlerName, newHandlerName[0]+" "+newHandlerName[1],String.format("Expected handler name is %s and Actual old handler name is %s",newHandlerName[0]+" "+newHandlerName[1],handlerName));
		test.log(Status.PASS, "New handler is validated.");
	
	}
	
	/**
	 * This Test will validate Sr handler with location as Anywhere 
	 * 
	 * @param userName
	 * @param type
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaSARARoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Regression,"Glow-II"})
	public void verifyHandlersWithLocationAsAnywhere(String userName,String type) throws Exception {
		
		handlerAndHandlerTeam = new HandlerAndHandlerTeamTestHelper(userName);
		handlerDb = new HandlerAndHandlerTeamDBHelper();
		
		Response getHandlerResponse = handlerAndHandlerTeam.getHandlerWithLocationAsAnywhere(userName, type);
		validateResponseToContinueTest(getHandlerResponse, 200, "Unable to get glober's count for location handler.", true);
		String changedGloberCount = restUtils.getValueFromResponse(getHandlerResponse, "details.globerCount").toString();
		
		Assert.assertEquals(changedGloberCount, "0",String.format("Expected changed glober count is 0 and Actual old handler name is %s",changedGloberCount));
		test.log(Status.PASS, "Glober count is validated.");
		
	}
	
	/**
	 * This Test will validate Sr handler team history without creating Sr. 
	 * 
	 * @param userName
	 * @param type
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaSARARoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Regression,"Glow-II"})
	public void verifyHandlerTeamHistoryWithoutCreatingSR(String userName,String type) throws Exception {
		
		handlerAndHandlerTeam = new HandlerAndHandlerTeamTestHelper(userName);
		handlerDb = new HandlerAndHandlerTeamDBHelper();
		
		Response handlerTeamData = handlerAndHandlerTeam.getHandlerTeam(userName, type);
		validateResponseToContinueTest(handlerTeamData, 201, "Unable to change the handler team.", true);		
		String handlerTeamId = restUtils.getValueFromResponse(handlerTeamData, "details.id").toString();
		String msg = restUtils.getValueFromResponse(handlerTeamData, "message").toString();		
		String newHandlerTeam = handlerDb.getHandlerTeamNameById(handlerTeamId);
		
		Assert.assertEquals(msg, "Handler team updated successfully.The change will be reflected for future SRs.","Expected Msg : Handler team updated successfully. and Actual Msg : "+msg);
		test.log(Status.PASS, "Handler Team changed successfully.");
		
		String handlerTeamHistory[] = handlerDb.getHandlerTeamHistoryWithOldAndNewName(handlerAndHandlerTeam.getSrNumber());
	
		Assert.assertNotEquals(newHandlerTeam, handlerTeamHistory[1],String.format("Expected new handler :"+handlerTeamHistory[1]+" Actual handler : ", newHandlerTeam));
		test.log(Status.PASS, "Old handler team validated");
	}
	
	/**
	 * This Test will validate user permission to check PM and TD has access to change the handler of Sr
	 * 
	 * @param userName
	 * @param type
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaPMTDRoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Regression,"Glow-II"})
	public void verifyWeatherSRHandlerIsChangingWithPMTD(String userName,String type) throws Exception {
		
		handlerAndHandlerTeam = new HandlerAndHandlerTeamTestHelper(userName);	
		Response handlerResponse = handlerAndHandlerTeam.checkSrHandlerIsChangedViaPMTD(userName, type);		
		String status = restUtils.getValueFromResponse(handlerResponse, "status").toString();
		String msg = restUtils.getValueFromResponse(handlerResponse, "message").toString();
		
		Assert.assertEquals(status, "403",String.format("Expected statusCode 403 and Actual statusCode", status));
		Assert.assertEquals(msg, "User is not having valid permission or role",String.format("Expected message is User is not having valid permission or role and Actual message is %s",msg));
		test.log(Status.PASS, "While performing change handler PM and TD roles validated!");
	}
	
	/**
	 * This Test will validate user permission to check PM and TD has access to change the handler of glober
	 * 
	 * @param userName
	 * @param type
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaPMTDRoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Regression,"Glow-II"})
	public void verifyWeatherGloberHandlerIsChangedWithPMTD(String userName,String type) throws Exception {
		
		int i = Utilities.getRandomDay();
		handlerAndHandlerTeam = new HandlerAndHandlerTeamTestHelper(userName);
		
		Response allGlober = handlerAndHandlerTeam.checkGloberHandlerIsChangedViaPMTD(userName, type);
		validateResponseToContinueTest(allGlober, 200, "Unable to get all globers", true);
		
		String glober_type = restUtils.getValueFromResponse(allGlober, "details.globerDTOList[" + i + "].type").toString();
		String handlerId = restUtils.getValueFromResponse(allGlober, "details.globerDTOList[" + i + "].handler.handlerId").toString();
		String id = restUtils.getValueFromResponse(allGlober, "details.globerDTOList[" + i + "].globerId").toString();
	
		Response globerResponse = handlerAndHandlerTeam.changeGloberHandler(id,glober_type,handlerId);	
		validateResponseToContinueTest(globerResponse, 403, "Unable to get all globers", true);
		String responseMsg = restUtils.getValueFromResponse(globerResponse, "message").toString();
		
		Assert.assertEquals(responseMsg, "User is not having valid permission or role",String.format("Expected message is User is not having valid permission or role and Actual message is : ",responseMsg));
		
	}
	
	
	/**
	 * This Test will validate user permission to check PM and TD has access to change the handler team of Sr
	 * 
	 * @param userName
	 * @param type
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaPMTDRoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Regression,"Glow-II"})
	public void verifyWeatherSRHandlerTeamIsChangesWithPMTD(String userName,String type) throws Exception {
		
		handlerAndHandlerTeam = new HandlerAndHandlerTeamTestHelper(userName);		
		Response getSrHandlerTeamResponse = handlerAndHandlerTeam.changeSrHandlerTeamByPMTD(userName, type);
		validateResponseToContinueTest(getSrHandlerTeamResponse, 403, "Issue with POST request API response.", true);
		String responseMsg = restUtils.getValueFromResponse(getSrHandlerTeamResponse, "message").toString();	
		Assert.assertEquals(responseMsg, "User is not having valid permission or role",String.format("Expected message is : User is not having valid permission or role and Actual message is : ",responseMsg));
	
	}
	
	/**
	 * This Test will validate error message while changing Sr handler team with wrong handlerId 
	 * 
	 * @param userName
	 * @param type
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaSARARoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Regression,"Glow-II"})
	public void verifyErrorMsgOfWeatherSRHandlerTeamIsChangesWithSARAWithWrongHandlerID(String userName,String type) throws Exception {
		
		handlerAndHandlerTeam = new HandlerAndHandlerTeamTestHelper(userName);		
		Response handlerTeamResponse = handlerAndHandlerTeam.getErrorMsgWithHandlerTeamByWrongHandlerId(userName, type);		
		validateResponseToContinueTest(handlerTeamResponse, 400, "Issue with POST request API response.", true);
		String responseMsg = restUtils.getValueFromResponse(handlerTeamResponse, "message").toString();	
		Assert.assertEquals(responseMsg, "Invalid handler id",String.format("Expected message is : Invalid handler id and Actual message is : ",responseMsg));
	}
	
	/**
	 * This Test will validate error message while changing Sr handler team with wrong handlerTeamId
	 * 
	 * @param userName
	 * @param type
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaSARARoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Regression,"Glow-II"})
	public void verifyErrorMsgOfWeatherSRHandlerTeamIsChangesWithPMTDWithWrongHandlerTeamID(String userName,String type) throws Exception {
		
		handlerAndHandlerTeam = new HandlerAndHandlerTeamTestHelper(userName);	
		Response handlerTeamResponse = handlerAndHandlerTeam.getErrorMsgOfWeatherSRHandlerTeamIsChangesWithSARAWithWrongHandlerTeamID(userName, type);		
		validateResponseToContinueTest(handlerTeamResponse, 400, "Issue with POST request API response.", true);
		String responseMsg = restUtils.getValueFromResponse(handlerTeamResponse, "message").toString();	
		Assert.assertEquals(responseMsg, "Invalid handlerteam id",String.format("Expected message is : Invalid handlerteam id and Actual message is : ",responseMsg));
		
	}
	
	/**
	 * This Test will validate error message while changing glober handler with wrong glober type.
	 * 
	 * @param userName
	 * @param type
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaSARARoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Regression,"Glow-II"})
	public void verifyErrorMsgForGloberHandlerAccordingToPositionLocationWithWrongType(String userName,String type) throws Exception {
		
		handlerAndHandlerTeam = new HandlerAndHandlerTeamTestHelper(userName);		
		Response globerHandlerResponse = handlerAndHandlerTeam.getErrorMsgForGloberHandlerAccordingToPositionLocationWithWrongType(userName, type);		
		validateResponseToContinueTest(globerHandlerResponse, 400, "Issue with POST request API response.", true);
		String responseMsg = restUtils.getValueFromResponse(globerHandlerResponse, "message").toString();	
		Assert.assertEquals(responseMsg, "Candidate ID not found in Glow DB.",String.format("Expected message is : Candidate ID not found in Glow DB. and Actual message is : ",responseMsg));
		
	}
	
	/**
	 * This Test will validate error message while changing glober handler with wrong globerId
	 * 
	 * @param userName
	 * @param type
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaSARARoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Regression,"Glow-II"})
	public void verifyErrorMsgForGloberHandlerAccordingToPositionLocationWithGloberID(String userName,String type) throws Exception {
		
		handlerAndHandlerTeam = new HandlerAndHandlerTeamTestHelper(userName);		
		Response globerHandlerResponse = handlerAndHandlerTeam.verifyErrorMsgForGloberHandlerAccordingToPositionLocationWithInvalidGloberID(userName, type);	
		validateResponseToContinueTest(globerHandlerResponse, 400, "Issue with POST request API response.", true);
		String responseMsg = restUtils.getValueFromResponse(globerHandlerResponse, "message").toString();		
		Assert.assertEquals(responseMsg, "Glober id required",String.format("Expected message is  :Glober id required and Actual message is : ",responseMsg));
		
	}
}
