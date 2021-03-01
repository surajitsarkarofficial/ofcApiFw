package endpoints.submodules.openpositions;

import endpoints.StaffingEndpoints;

public class OpenPositionsEndPoints extends StaffingEndpoints{

	public static String getTotalOpenPositionCount = "/proxy/glow/openpositionsorchestraservice/open-position?globalId=%s&pageNumber=%d&pageSize=%d";
	
	public static String applyToOpenPositions = "/proxy/glow/staffingorchestraservice/open-position/applies";
	
	public static String openPositionsFilters = "/proxy/glow/openpositionsorchestraservice/open-position?globalId=%s&pageNumber=%d&pageSize=%d&isSkillRequired=%s&%s=%s";
}
