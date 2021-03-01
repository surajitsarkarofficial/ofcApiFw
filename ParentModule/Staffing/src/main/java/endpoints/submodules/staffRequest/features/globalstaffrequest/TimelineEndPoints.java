package endpoints.submodules.staffRequest.features.globalstaffrequest;

import endpoints.submodules.staffRequest.StaffRequestEndpoints;

public class TimelineEndPoints extends StaffRequestEndpoints{

	public static String dismissGlober = "/proxy/glow/positionservice/positions/dismiss/suggestions";
	
	public static String getOpenPositionsUrl = "/proxy/glow/openpositionsorchestraservice/open-position?globalId=%s&pageNumber=%d&pageSize=%d&isSkillRequired=%s";
	
	public static String getAvailableGlobersUrl = "/proxy/glow/staffingorchestraservice/globers/suggestions?positionId=%s&isAvailableGlober=%s";

	public static String getSrSkillsMatchingTopThrityGlobers = "/proxy/glow/staffingorchestraservice/globers/suggestions?positionId=%s&noOfDay=%s";
}
