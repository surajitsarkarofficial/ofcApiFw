package endpoints.submodules.flights;

import endpoints.TravelMobileEndpoints;

public class FlightsEndpoints extends TravelMobileEndpoints{
	
	public static String airportSearchEndpoint = "/travel/orchestra/locations/flight-location?limit=10&locationName=%s";
	public static String citySearchEndpoint = "/travel/orchestra/locations/cities?query=%s&limit=10";
	public static String countrySearchEndpoint="/travel/orchestra/locations/countries?countryName=%s&limit=10";

}
