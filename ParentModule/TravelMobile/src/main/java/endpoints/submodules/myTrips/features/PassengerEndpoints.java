package endpoints.submodules.myTrips.features;

import endpoints.submodules.myTrips.MyTripsEndpoints;

public class PassengerEndpoints extends MyTripsEndpoints{

	public static String deletePassengerEndpoint="/travel/orchestra/trip-passenger/%s";
	public static String addPassengerEndpoint="/travel/orchestra/trips/%s/trip-passengers";
	public static String getPassengerInfoEndpoint ="/travel/orchestra/passengers/tripPassengersInfo?tripId=%s&email=%s";
	public static String replacePassenger = "/travel/orchestra/trip-passengers/%s?newPassengerEmail=%s";
	
}
