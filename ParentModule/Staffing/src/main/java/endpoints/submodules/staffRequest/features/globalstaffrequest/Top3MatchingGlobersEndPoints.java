package endpoints.submodules.staffRequest.features.globalstaffrequest;

import endpoints.submodules.staffRequest.StaffRequestEndpoints;

public class Top3MatchingGlobersEndPoints extends StaffRequestEndpoints{

	public static String getTop3MatchingGlobersUrl = "/proxy/glow/staffingorchestraservice/globers/suggestions?positionId=%s&isTopThreeMatchingGlober=%s";
	
}
