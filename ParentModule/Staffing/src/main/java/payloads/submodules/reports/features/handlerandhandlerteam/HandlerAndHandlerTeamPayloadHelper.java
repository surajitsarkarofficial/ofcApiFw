package payloads.submodules.reports.features.handlerandhandlerteam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import database.GlowDBHelper;
import database.submodules.reports.features.handlerandhandlerteam.HandlerAndHandlerTeamDBHelper;
import database.submodules.staffRequest.features.EditSRDBHelpers;
import database.submodules.staffRequest.features.globalstaffrequest.QuickSuggestAndAssignDBHelper;
import payloads.submodules.staffRequest.StaffRequestPayloadHelper;
import utils.Utilities;

public class HandlerAndHandlerTeamPayloadHelper extends StaffRequestPayloadHelper{

	static Gson gson;
	private ArrayList<String> locationsId = new ArrayList<String>();
	Map<Object, Object> mapPositionDetails = new HashMap<Object, Object>();
	HandlerAndHandlerTeamDBHelper handlerAndHandlerTeamDb;
	GlowDBHelper glowDbHelper;
	QuickSuggestAndAssignDBHelper suggestAndAssign;
	
	//handler and handlerTeam class members
	private static String handlerIdFromDb;
	private static String handlerTeamIdFromDb;
	
	public HandlerAndHandlerTeamPayloadHelper() {
		handlerAndHandlerTeamDb = new HandlerAndHandlerTeamDBHelper();
		suggestAndAssign = new QuickSuggestAndAssignDBHelper();
		glowDbHelper = new GlowDBHelper();
	}
	
	/**
	 * This method will create payload to suggest SoonToBeGlobers Via PM and TD role
	 * 
	 * @param type
	 * @param SrNumber
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String suggestSTGViaPMTD(String srNumber,String type) throws SQLException {
		Map<Object, Object> mapPositionDetails = new HashMap<Object, Object>();
		EditSRDBHelpers editSr = new EditSRDBHelpers();

		String stgInfo[] = suggestAndAssign.getTalentPoolStgs(type);
		mapPositionDetails.put("positionId", editSr.getPositionId(srNumber));
		mapPositionDetails.put("globerId", stgInfo[0]);
		mapPositionDetails.put("globalId", glowDbHelper.getGlobalIdFromGloberId(stgInfo[0]));
		mapPositionDetails.put("planType","High");
		mapPositionDetails.put("planStartDate", Utilities.getTodayDate("dd-MM-yyyy"));
		mapPositionDetails.put("comments","");
		mapPositionDetails.put("suggestionType","MANUAL");
		mapPositionDetails.put("srGridPage", true);
		mapPositionDetails.put("type",type);
		mapPositionDetails.put("staffingReportType", 1);
		mapPositionDetails.put("validateCreateStaffPlan",false);                                           
		
		gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(mapPositionDetails);
			
		return json;		
	}
	
	/**
	 * This method will create Sr handler payload
	 * 
	 * @param handlerId
	 * @param locationId
	 * @param positionRoleId 
	 * @return {@link String}
	 * @author shadab.waikar
	 */
	public String SrHandlerPayloadHelper(String handlerId,ArrayList<String> locationId,ArrayList<String> positionRoleId) {		
		mapPositionDetails.put("handlerId", handlerIdFromDb); 
		mapPositionDetails.put("locationNames",locationId); 
		mapPositionDetails.put("positionRoleIds", positionRoleId);
		String json = Utilities.convertJavaObjectToJson(mapPositionDetails);
		return json;
	}
	
	/**
	 * This method will create Sr handler team payload
	 * 
	 * @param handlerId
	 * @param handlerTeamId
	 * @return {@link String}
	 * @author shadab.waikar
	 */
	public String SrHandlerTeamPayloadHelper(String handlerId,String handlerTeamId) {	
		mapPositionDetails.put("handlerId", handlerId); 
		mapPositionDetails.put("id", handlerTeamId); 
		mapPositionDetails.put("name", "null");
		String json = Utilities.convertJavaObjectToJson(mapPositionDetails);
		return json;
	}
	
	/**
	 * This method will create glober handler payload
	 * 
	 * @param glober_id
	 * @param glober_type
	 * @param handlerId 
	 * @return {@link String}
	 * @author shadab.waikar
	 */
	public String globerHandlerPayloadHelper(String glober_id,String glober_type,String handlerId) {
		mapPositionDetails.put("globerId", glober_id);
		mapPositionDetails.put("globerType", glober_type);
		mapPositionDetails.put("handlerId", handlerIdFromDb); 	
		String json = Utilities.convertJavaObjectToJson(mapPositionDetails);
		return json;
	}
	
	/**
	 * This method will create Sr grid change handler payload
	 * 
	 * @param handlerId
	 * @param positionId
	 * @return {@link String}
	 * @author shadab.waikar
	 */
	public String srGridHandlerPayloadHelper(String handlerId,String positionId) {
		mapPositionDetails.put("positionId", positionId);
		mapPositionDetails.put("handlerId", handlerId); 	
		String json = Utilities.convertJavaObjectToJson(mapPositionDetails);
		return json;
	}
	
	/**
	 * This method will create payload to change handler of Sr and/or glober
	 * 
	 * @param handlerId
	 * @param locationId
	 * @param positionRoleId 
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String changeHandlerPaylaod(String handlerId,String locationId,ArrayList<String> positionRoleId) throws SQLException {
		handlerIdFromDb = handlerAndHandlerTeamDb.getHandlerIdExceptProvidedId(handlerId);
		locationsId.add(glowDbHelper.getLocationNameFromId(locationId));
		return SrHandlerPayloadHelper(handlerId, locationsId, positionRoleId);
		
	}
	
	/**
	 * This method will return handlerName
	 *
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String getHandlerName() throws SQLException {
		String name[]=new String[2];
		name = handlerAndHandlerTeamDb.getHandlerNameFromId(handlerIdFromDb);
		return name[0]+" "+name[1];
	}
	
	/**
	 * This method will create payload to change handler of Sr and/or glober
	 * 
	 * @param handlerId
	 * @param positionId
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String changeHandlerFromSrGridPayload(String handlerId,String positionId) throws SQLException {
		handlerIdFromDb = handlerAndHandlerTeamDb.getHandlerIdExceptProvidedId(handlerId);
		return srGridHandlerPayloadHelper(handlerIdFromDb, positionId);
	}
	
	/**
	 * This method will create payload to change handler of Sr and/or glober
	 * 
	 * @param handlerId
	 * @param positionId
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String getWrongChangeHandlerFromSrGridPayload(String handlerId,String positionId) throws SQLException {
		return srGridHandlerPayloadHelper(handlerId, positionId);
	}

	/**
	 * This method will create payload to change handler of all active globers
	 * 
	 * @param handlerId
	 * @param location_name
	 * @param position_role
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String getChangeActiveHandlerPayload(String handlerId,ArrayList<String> location_name,ArrayList<String> position_role) throws SQLException {
		handlerIdFromDb = handlerAndHandlerTeamDb.getHandlerIdExceptProvidedId(handlerId);	
		return SrHandlerPayloadHelper(handlerIdFromDb, location_name, position_role);
	}
	
	/**
	 * This method will create payload to change handler team of handler
	 * 
	 * @param handlerId
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String getHandlerTeamPayload(String handlerId) throws SQLException {
		handlerTeamIdFromDb =  handlerAndHandlerTeamDb.getHandlerTeamId(handlerId);
		return SrHandlerTeamPayloadHelper(handlerId,handlerTeamIdFromDb);
	}
	
	/**
	 * This method will create payload to change handler of individual glober
	 * 
	 * @param glober_id
	 * @param glober_type
	 * @param handlerId
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String getChangeGloberHandlerPayload(String glober_id,String glober_type,String handlerId) throws SQLException {
		handlerIdFromDb =  handlerAndHandlerTeamDb.getHandlerIdExceptProvidedId(handlerId);
		return globerHandlerPayloadHelper(glober_id, glober_type, handlerIdFromDb);	
	}
	
	/**
	 * This method will create wrong payload to change glober handler
	 * 
	 * @param glober_id
	 * @param glober_type
	 * @param handlerId
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String getChangeGloberHandlerPayloadWithWrongData(String glober_id,String glober_type,String handlerId) throws SQLException {
		handlerIdFromDb =  handlerAndHandlerTeamDb.getHandlerIdExceptProvidedId(handlerId);
		return globerHandlerPayloadHelper(glober_id, glober_type, handlerIdFromDb);	
	}
	
	/**
	 * This method will create payload with wrong handler Id
	 * 
	 * @param handlerId
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String getHandlerTeamPayloadWithWrongHandler(String handlerId) throws SQLException {
		return SrHandlerTeamPayloadHelper(handlerId,"14");
	}
	
	/**
	 * This method will create payload with wrong handler team Id
	 * 
	 * @param handlerId
	 * @param handlerTeamId
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String getHandlerTeamPayloadWithWrongHandlerTeam(String handlerId,String handlerTeamId) throws SQLException {
		handlerIdFromDb =  handlerAndHandlerTeamDb.getHandlerIdExceptProvidedId(handlerId);
		return SrHandlerTeamPayloadHelper(handlerIdFromDb, handlerTeamId);
	}
}
