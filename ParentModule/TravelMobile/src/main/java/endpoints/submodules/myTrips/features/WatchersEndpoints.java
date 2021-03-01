package endpoints.submodules.myTrips.features;

import endpoints.submodules.myTrips.MyTripsEndpoints;

public class WatchersEndpoints extends MyTripsEndpoints{
	
	public static String addWatcherToATripEndpoint = "/travel/orchestra/trips/%s/watchers";
	public static String removeWatcherFromTripEndpoint = "/travel/orchestra/trips/%s/watchers?email=%s ";

}
