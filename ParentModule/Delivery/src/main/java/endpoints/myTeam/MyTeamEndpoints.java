package endpoints.myTeam;

import endpoints.DeliveryEndpoints;

/**
 * @author imran.khan
 *
 */

public class MyTeamEndpoints extends DeliveryEndpoints {

	public static String positionsUrl = "/proxy/glow/positionservice/positions";

	public static String getprojectList = "/proxy/glow/projectorchestraservice/globerprojects?pageNumber=1&pageSize=20&globerId=%s&projectState=ON_GOING";

	public static String getProjectMembers = "/proxy/glow/projectorchestraservice/globerprojects/members/%s?fetchProjectRole=%s&fetchGloberType=%s&type=%s&isNewService=%s";

	public static String getPodList = "/proxy/glow/assignmentservice/assignments/pods/projects/%s?areEmptyPodsRequired=true";

	public static String getPodMembers = "/proxy/glow/projectorchestraservice/globerprojects/pods/members?podId=%s&projectId=%s&isNewService=%s";

	public static String getClientList = "/proxy/glow/projectorchestraservice/globerprojects/clients";

	public static String getClientMembers = "/proxy/glow/assignmentservice/assignments/clientMembers/";

	public static String getSrNumbers = "/v1/positions?userId=%s&viewId=6";

	public static String getClientListForUser = "/proxy/glow/projectorchestraservice/globerprojects/clients?projectState=%s&viewType=%s&globerId=%s";

	public static String getStudioListForUser = "/proxy/glow/projectorchestraservice/globerprojects/myteamFilters/studios?viewType=%s&projectState=%s&globerId=%s";

	public static String getProjectStateForUser = "/proxy/glow/projectorchestraservice/globerprojects/myteamFilters/projectstates?viewType=%s&globerId=%s";

	public static String getGloberDetails = "/v1/globers/details/%s";

	public static String getProjectMembersForProject = "/proxy/glow/assignmentservice/assignments/projectMembers/%s?type=%s";

	public static String getProjectMembersForPOD = "/proxy/glow/assignmentservice/assignments/pods/members?podId=%s&projectId=%s";

	public static String getAllPODTypes = "/v1/pods/types/all";

	public static String getAllEmptyPODS = "/proxy/glow/assignmentservice/assignments/pods/projects/%s?areEmptyPodsRequired=%s";

	public static String postPODUnderProject = "/proxy/glow/podservice/pods";

	public static String putPodDetails = "/proxy/glow/podservice/pods/%s";

	public static String getPodsForGlober = "/proxy/glow/assignmentservice/assignments/pods/glober/%s";

	public static String getProjectMembersForProjectWithPagination = "/v1/glober/projects/%s/members?fetchProjectRole=%s&fetchgloberType=%s&type=%s&pageNumber=%s&pageSize=%s";

	public static String deletePod = "/proxy/glow/podservice/pods/%s?deletePurpose=%s";

	public static String getAllPodsForGlober = "/proxy/glow/assignmentservice/assignments/pods/glober/%s";

	public static String postProjectRole = "/proxy/glow/projectrolesservice/projectroles";

	public static String getProjectsForGlober = "/proxy/glow/assignmentservice/assignments/glober/%s?type=%s";

	public static String postManageAssignmentPercentage = "/proxy/glow/assignmentservice/assignments/manageAssignmentPercentage";

	public static String postExtendAssignment = "/proxy/glow/assignmentservice/assignments/manageAssignmentDuration";

	public static String getProjectDetailsAsPerFilter = "/proxy/glow/projectorchestraservice/globerprojects?pageNumber=%s&pageSize=%s&globerId=%s&clientId=&businessUnitId=&assignmentStartDate=&assignmentEndDate=&projectState=%s&projectName=";

	public static String postManagePOD = "/proxy/glow/podservice/pods/managePod";

	public static String getHowItWorks = "/proxy/glow/userpreferencesservice/userpreferences/videotourconfig/myteam";

	public static String getBriefAboutMyTeamModule = "/proxy/glow/userpreferencesservice/userpreferences?globerId=%s&componentKey=%s";

	public static String getSearchResult = "/proxy/glow/elasticsearchservice/search?query=%s&type=";

	public static String getProjectsForUser = "/proxy/glow/projectorchestraservice/globerprojects?pageNumber=%s&pageSize=%s&globerId=%s&projectId=&businessUnitId=&assignmentStartDate=&assignmentEndDate=&projectState=%s";

	public static String getProjectMembersForUser = "/proxy/glow/projectorchestraservice/globerprojects/members/%s?fetchProjectRole=%s&fetchGloberType=%s&type=%s&startDate=&endDate=";

	public static String getMenuListForUser = "/proxy/glow/projectorchestraservice/globerprojects/menu?moduleName=%s";

	public static String getUserFilterForProject = "/proxy/glow/userpreferencesservice/userpreferences?globerId=%s&componentKey=%s";

	public static String getUserFilter = "/proxy/glow/projectorchestraservice/globerprojects/myteamFilters/studios?globerId=%s&projectId=&businessUnitId=&assignmentStartDate=&assignmentEndDate=&projectState=%s";

	public static String getPODDetails = "/proxy/glow/podservice/pods/types/all";

	public static String getGloberHasExistingReplacement = "/proxy/glow/projectorchestraservice/globerprojects/replacements/sr?positionId=%s&globerId=%s&projectId=%s";

	public static String getPODAssessment = "/proxy/glow/podorchestraservice/podorchestra/assessments?pageSize=%s&pageNo=%s";

	public static String getProjectStatusFilter = "/proxy/glow/projectorchestraservice/globerprojects/myteamFilters/projectstates?globerId=%s&projectId=&businessUnitId=&assignmentStartDate=&assignmentEndDate=&projectState=";

	public static String getAssignmentOfGloberOrPOD = "/proxy/glow/assignmentservice/assignments/pods/glober/%s";
}
