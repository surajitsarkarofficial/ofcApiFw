package endpoints.submodules.homepage.features;
/**
 * @author rachana.jadhav
 *
 */
import endpoints.submodules.homepage.HomePageBaseEndpoints;
/**
 * Endpoints for Glow and Firebase Pods api
 *
 */
public class PodsEndpoints extends HomePageBaseEndpoints{
	
	public static String glowPodsOfContacts = "/glow/podorchestraservice/podsvieworchestra/account/%d/contacts/%d/pods";
	public static String firebasePodsOfContacts = "/accounts/%d/contacts/%d/pods";
	
}
