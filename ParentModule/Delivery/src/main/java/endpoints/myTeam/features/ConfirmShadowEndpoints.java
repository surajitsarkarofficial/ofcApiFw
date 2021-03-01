package endpoints.myTeam.features;

import endpoints.myTeam.MyTeamEndpoints;

/**
 * @author pooja.kakade
 *
 */

public class ConfirmShadowEndpoints extends MyTeamEndpoints{
	
	public static String confirmShadow = "/proxy/glow/assignmentswriteorchestraservice/assignments";
	
	public static String getOpenPositionDetails = "/proxy/glow/positionservice/positions/openings?limit=%s&offset=%s&sorting=AstartDate&client=%s&projectId=%s";
	
	public static String getShadowDetails = "/proxy/glow/projectorchestraservice/globerprojects/members/%s?fetchProjectRole=%s&fetchGloberType=%s&type=projects&startDate=&endDate=&isNewService=%s";

	public static String getPostionListDetails = "/proxy/glow/positionservice/positions?positionId=%d";
	
	public static String getPostionSkills = "/proxy/glow/positionservice/positions/skills/%d";
	
	public static String replicateShadow = "/proxy/glow/positionservice/positions/replicateshadowstaffrequestsbysr";
}
