package endpoints.submodules.globers.features;

import endpoints.submodules.globers.GlobersEndpoints;

/**
 * @author shadab.waikar
 * */
public class TBDGloberEndPoints extends GlobersEndpoints{

	public static String getTBDGloberSearchResult = "/v1/globerfilters/globernames?viewId=%d&userId=%s&isExcludeTBDGlobers=%s&key=%s&offset=%d&limit=5&roles=%s";
	
	public static String getTBDGloberSearchResultInDynamicSearch = "/proxy/glow/staffingorchestraservice/globers/dynamicSearch";
	
	public static String getTBDGloberSearchResultInReverseSearch = "/proxy/glow/staffingorchestraservice/globers?searchString=%s";

	public static String suggestTBDGlober = "/proxy/glow/globerservice/globers/tbd-statuses?globerIds=%s&isTBDGloberWarningRequired=%s";
	
	public static String markGloberAsTbd = "/proxy/glow/globerservice/globers/status";
	
}
