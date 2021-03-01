package endpoints.submodules.homepage.features;
/**
 * @author rachana.jadhav
 *
 */
import endpoints.submodules.homepage.HomePageBaseEndpoints;
/**
 * Endpoints for Firebase Real Search and Recent search api
 *
 */
public class SearchEndpoints extends HomePageBaseEndpoints{
	public static String recentSearchGetUrl="/search";
	public static String recentSearchPostUrl="/search";
	public static String realSearchGetUrl="/search?queryString=%s&pageStart=1&pageEnd=10";
	public static String podsOfContact="/accounts/%s/contacts/%s/pods";
	public static String contactOfPod="/search/accounts/%s/pods/%s/contacts?pageStart=1&pageEnd=10";
	public static String contactsOfAccount="/accounts/%d/contacts?pageStart=1&pageEnd=10";
}
