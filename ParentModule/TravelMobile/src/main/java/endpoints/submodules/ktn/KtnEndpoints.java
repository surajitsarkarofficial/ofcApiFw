package endpoints.submodules.ktn;

import endpoints.TravelMobileEndpoints;

public class KtnEndpoints extends TravelMobileEndpoints{
	
	public static String getKtn = "/travel/orchestra/passengers/%s/knownTravelerNumbers";
	public static String ktnOperations = "/travel/orchestra/passengers/%s/knownTravelerNumbers/%s";
	public static String getKtnFromProfile = "/travel/orchestra/trips/%s/profile-known-traveler-numbers";
	public static String associateKtnToTrip = "/travel/orchestra/trips/%d/trip-known-traveler-numbers";	
	public static String getKtnAssociatedToTrip = "/travel/orchestra/trips/%d/trip-known-traveler-numbers";	

}
