package endpoints.myTeam.features;

import endpoints.myTeam.MyTeamEndpoints;

/**
 * @author imran.khan
 *
 */

public class UserPreferencesEndpoints extends MyTeamEndpoints{

	public static String getUserPreferences = "/proxy/glow/userpreferencesservice/userpreferences?globerId=%s&componentKey=%s";
	
}
