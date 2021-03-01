package endpoints.submodules.staffRequest.features.globalstaffrequest;

import endpoints.submodules.staffRequest.StaffRequestEndpoints;

/*
 * @author shadab.waikar
 */
public class ManageWatcherEndPoints extends StaffRequestEndpoints{

	public static String manageWatcherUrl = "/proxy/glow/positionservice/watchers";
	
	public static String getWatchers = "/proxy/glow/positionservice/watchers/recomendedwatchers?positionId=%s";
}
