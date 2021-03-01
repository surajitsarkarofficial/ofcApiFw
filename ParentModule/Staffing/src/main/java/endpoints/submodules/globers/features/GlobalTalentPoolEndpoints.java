package endpoints.submodules.globers.features;

import endpoints.submodules.globers.GlobersEndpoints;

public class GlobalTalentPoolEndpoints extends GlobersEndpoints {

	/**
	 * Sample url for global talent pool count
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-{envName}/v1/globers?viewId=2&userId={userId}&parentViewId=2&offset=0&limit=50&sorting=Aposition&benchDateRange={startDate},{EndDate}&isTotalCount=true
	 */
	public static String globalTalentPoolCountUrl = "/v1/globers?viewId=%d&userId=%s&parentViewId=%d&offset=%d&limit=%d&sorting=%s&benchDateRange=%s,%s&isTotalCount=%s";
}