package endpoints.submodules.reports.features.handlerandhandlerteam;

import endpoints.submodules.staffRequest.StaffRequestEndpoints;

public class HandlerAndHandlerTeamAPIEndPoints extends StaffRequestEndpoints{

	public static String getSTGsEmail = "/proxy/glow/globerservice/globers/details/%s";
	
	public static String changeHandler = "/proxy/glow/staffingorchestraservice/handlers/staffrequest-glober-handlers";
	
	public static String changeHandlerFilter = "/v1/positions?viewId=%d&userId=%s&parentViewId=%d&offset=%d&limit=%d&sorting=%s&srPosition=%s&location=%s";
	
	public static String SrWatcher = "/proxy/glow/positionservice/watchers/recomendedwatchers?positionId=%s";
	
	public static String chnageHandlerFromSrGrid = "/proxy/glow/positionservice/positions/staffrequest/handlers";
	
	public static String changeHandlerTeam = "/proxy/glow/positionservice/handlers/handlerteam-mappings";
	
	public static String changeGloberHandler = "/proxy/glow/staffingorchestraservice/globers/handlers";
	
	public static String changeHandlerFilters = "/proxy/glow/managehandlerorchestraservice/handlers/staffrequest-glober-counts?positionRoleIds=%s&locationNames=%s&handlerId=%s&userId=%s";
	
}
