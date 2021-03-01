package endpoints.submodules.staffRequest;

import endpoints.StaffingEndpoints;

public class StaffRequestEndpoints extends StaffingEndpoints{
	
	/**
	 * Url of create SR
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-{envName}/proxy/glow/staffingorchestraservice/positions
	 */
	public static String positionsUrl = "/proxy/glow/staffingorchestraservice/positions";

	/**
	 * Url to create SR using new flow
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-{envName}/proxy/glow/positionservice/positions
	 */
	public static String srRevampPositionsUrl = "/proxy/glow/positionservice/positions";

	/**
	 * Get required skills
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-{envName}/proxy/glow//skillservice/skills/cachedskills
	 */
	public static String cachedSkillsUrl = "/proxy/glow//skillservice/skills/cachedskills";
	
	/**
	 * GET STAFF PLAN ID
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-{envName}/proxy/glow/staffingorchestraservice/staffplans?positionId={positionId}&suggestionDetailsPage=true
	 */
	public static String getStaffPlanId = "/proxy/glow/staffingorchestraservice/staffplans?positionId=%s&suggestionDetailsPage=%s";
	
	public static String srNumberPositionsUrl = "/proxy/glow/staffingorchestraservice/positions?srNumber=%s";

	public static String srGrid = "/v1/positions/?viewId=%d&userId=%s/&parentViewId=%s&offset=%d&limit=%d&sorting=%s";
	
	/**
	 * Assign glober to SR
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-{envName}/proxy/glow/staffingorchestraservice/assignments
	 */
	public static String assignGlober = "/proxy/glow/staffingorchestraservice/assignments";
	
	/**
	 * Add new skill
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-{envName}/proxy/glow/skillservice/curation/skill
	 */
	public static String addSkillUrl = "/proxy/glow/skillservice/curation/skill";
	
	/**
	 * Black listing skill url
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-{envName}/proxy/glow/positionservice/positions/skill/blacklist?alberthaSkillIdList={skillId}
	 */
	public static String blackListSkillUrl = "/proxy/glow/positionservice/positions/skill/blacklist?alberthaSkillIdList=%s";
	
	/**
	 * Get added skill details
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-{envName}/proxy/glow/skillservice/curation/skill?skillName={skillName}
	 */
	public static String getSkillDetails = "/proxy/glow/skillservice/curation/skill?skillName=%d";
	
	/**
	 * Get global staff request url for position filter
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-{envName}/v1/positions?viewId=7&userId=387731020&parentViewId=7&offset=0&limit=50&sorting=Astage&srPosition=java%20developer
	 */
	public static String getSrGridPositionFilterUrl = "/v1/positions?viewId=%d&userId=%d&parentViewId=%d&offset=%d&limit=%d&sorting=%d&srPosition=%d";

	/**
	 * accept/reject,mark fit/unfit glober url
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-{envName}/proxy/glow/staffingorchestraservice/staffplans/interviews
	 */
	public static String acceptRejectGloberUrl = "/proxy/glow/staffingorchestraservice/staffplans/interviews";
	
	/**
	 * Request Assignment STG url
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-ui14/proxy/glow/staffingorchestraservice/staffplans/bookings
	 */
	public static String requestAssignmentSTGUrl = "/proxy/glow/staffingorchestraservice/staffplans/bookings";
	
	/**
	 * Global staff and my staff request columns url
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-ui14/v1/staffingcolumns/positions?viewId=7
	 */
	public static String gridColumnNamesUrl = "/v1/staffingcolumns/positions?viewId=%s";
	
	/**
	 * Global staff request total count
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-ui14/v1/positions?viewId=7&userId=387731020&parentViewId=7&offset=0&limit=50&sorting=Astage&isTotalCount=true
	 */
	public static String gridTotalCount = "/v1/positions?viewId=%s&userId=%s&parentViewId=%s&offset=%s&limit=%s&sorting=%s&isTotalCount=%s";
}