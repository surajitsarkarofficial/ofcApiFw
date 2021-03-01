package endpoints.submodules.tripCreation;

import endpoints.TravelNextEndpoints;

/**
 * @author josealberto.gomez
 *
 */
public class TripCreationEndpoints extends TravelNextEndpoints {
    public static String postCreateTrip = "/proxy/glow/tripservice/trips/v2";
    public static String putCreateTrip = "/proxy/glow/tripservice/trips/v2";
    public static String getTripDetail = "/proxy/glow/tripservice/trips-detail?tripId=%s";
    public static String deleteTrip = "/proxy/glow/travelorchestra/travel/orchestra/trip/%s/v2";
    public static String getWatchers = "/proxy/glow/tripservice/trips/%s/watchers";
    public static String getProjects = "/proxy/glow/tripservice/projects?query=%s";
    public static String getTravelReasons = "/proxy/glow/tripservice/travelReasons";
}
