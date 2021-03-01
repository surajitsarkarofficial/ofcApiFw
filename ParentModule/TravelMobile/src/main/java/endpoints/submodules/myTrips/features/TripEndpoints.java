package endpoints.submodules.myTrips.features;

import endpoints.submodules.myTrips.MyTripsEndpoints;

public class TripEndpoints extends MyTripsEndpoints{
	
	public static String createTripEndpoint = "/travel/orchestra/trip";
	public static String getTripDetailsEndpoint = "/travel/orchestra/trips-detail?tripId=%s";
	public static String deleteTripEndpoint = "/travel/orchestra/trips/%s";
}
