package endpoints.submodules.staffRequest.features;
import endpoints.submodules.staffRequest.StaffRequestEndpoints;

public class CreateNewPositionEndpoints extends StaffRequestEndpoints{
	/**
	 * Get location url
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-{envName}/proxy/glow/positionservice/positions/filters/locations
	 */
	public static String getLocationsUrl = "/proxy/glow/positionservice/positions/filters/locations"; 

	/**
	 * Get position role url 
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-{envName}/proxy/glow/positionservice/positions/positionroles?isRotation=false
	 */
	public static String getPositionRoleUrl = "/proxy/glow/positionservice/positions/positionroles?isRotation=%s";

	/**
	 * Get client url
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-{envName}/proxy/glow/positionservice/positions/clients?isExternalClients=true
	 */
	public static String getClientUrl="/proxy/glow/positionservice/positions/clients?isExternalClients=%s";

	/**
	 * Get project id from client id
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-{envName}/v1/projects?clientId={clientId}&isSRRevampFlow=true
	 */
	public static String getProjectUrl="/v1/projects?clientId=%s&isSRRevampFlow=%s";

	/**
	 * Get sow id from project id url
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-{envName}/proxy/glow/sowservice/sows?projectId={projectId}
	 */
	public static String getSowIdUrl = "/proxy/glow/sowservice/sows?projectId=%s";

	/**
	 * Get position name for which load can vary
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-{envName}/proxy/glow/positionservice/positions/position-roles-for-variable-load
	 */
	public static String getVariableLoadPositionName = "/proxy/glow/positionservice/positions/position-roles-for-variable-load";

	/**
	 * 
	 * Get position and seniority template
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-{envName}/proxy/glow/positionservice/positions
	 */
	public static String postPositionSeniorityTemplate = "/proxy/glow/positionservice/positions";

	/**
	 * Get top matching glober count
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-{envName}/proxy/glow/staffingorchestraservice/globers/topMatchingGlobersCounts
	 */
	public static String topMatchingGloberCountUrl = "/proxy/glow/staffingorchestraservice/globers/topMatchingGlobersCounts";

	/**
	 * Get top matching glober count from albertha
	 */
	public static String alberthaTopMatchingGlobantCountUrl = "/search/matchers";

}