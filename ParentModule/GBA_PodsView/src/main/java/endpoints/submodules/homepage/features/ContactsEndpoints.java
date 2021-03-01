package endpoints.submodules.homepage.features;
/**
 * @author rachana.jadhav
 *
 */
import endpoints.submodules.homepage.HomePageBaseEndpoints;
/**
 * Endpoints for Glow and Firebase Contact api
 *
 */
public class ContactsEndpoints extends HomePageBaseEndpoints{

	public static String glowContacts = "/glow/podorchestraservice/podsvieworchestra/account/%d/contacts-pods";
	public static String firebaseContacts ="/accounts/%d/contacts?pageStart=0&pageEnd=0";
}
