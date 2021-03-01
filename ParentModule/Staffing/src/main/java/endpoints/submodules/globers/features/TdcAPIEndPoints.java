package endpoints.submodules.globers.features;

import endpoints.submodules.globers.GlobersEndpoints;

public class TdcAPIEndPoints extends GlobersEndpoints {

	public static String getTDCFilter = "/v1/globerfilters/tdcs?viewId=%d&userId=%s&benchDateRange=%s,%s";
	
	public static String getPartialNameForStgUrl = "/v1/globers?viewId=%d&userId=%s&parentViewId=%d&offset=%d&sorting=%s&benchDateRange=%s&benchDateRange=%s,%s";
	
	public static String stgTDCHistory = "/proxy/glow/staffingorchestraservice/globers/history?Type=%s&globerId=%s&Action=%s&globalId=%s";
	
	public static String getPartialNameForAllGlobersTdc = "/v1/globers?viewId=%d&globerId=%s&type=%s&userId=%s&isSTGFullNameRequired=%s";
	
	public static String getPartialNameForAllGlobers = "/v1/globers?viewId=%d&userId=%s&parentViewId=%s&offset=%s&globerId=%s";
	
	public static String getAllActiveGlobers = "/v1/globers?viewId=%d&userId=%s&parentViewId=%d&offset=%d&limit=%d&sorting=%s&type=%s";
	
	public static String getAllActiveGlobersByFilters = "/v1/globers?viewId=%d&userId=%s&parentViewId=%d&offset=%d&limit=%d&sorting=%s&type=%s&position=%s&location=%s";

	public static String getGlober = "/v1/globers?viewId=%d&userId=%s&parentViewId=%d&offset=%d&limit=%d&sorting=%s&globerId=%s";
	
}
