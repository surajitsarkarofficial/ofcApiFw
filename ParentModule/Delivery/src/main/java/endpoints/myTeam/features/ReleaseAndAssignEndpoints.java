package endpoints.myTeam.features;

import endpoints.myTeam.MyTeamEndpoints;

/**
 * @author pooja.kakade
 *
 */

public class ReleaseAndAssignEndpoints extends MyTeamEndpoints {

	public static String getGloberDetails = "/proxy/glow/projectorchestraservice/globerprojects/members/%s?fetchProjectRole=%s&fetchGloberType=%s&type=projects&startDate=&endDate=&isNewService=%s";

	public static String getSrDetails = "/v1/positions?userId=%s&viewId=%s&parentViewId=%s&projectName=%s&clientName=%s";

	public static String releaseAndAssignGlober = "/proxy/glow/staffingorchestraservice/release-assign-requests";

	public static String validateGlober = "/proxy/glow/projectorchestraservice/release-assign-requests/validate?globerId=%s&assignmentId=%s";

	public static String pendingNotificationReleaseAndAssign = "/proxy/glow/positionservice/release-assign-requests?projectId=%s&reqStatus=%s";

	public static String approveReleaseAndAssign = "/proxy/glow/staffingorchestraservice/release-assign-requests/%s";
}
