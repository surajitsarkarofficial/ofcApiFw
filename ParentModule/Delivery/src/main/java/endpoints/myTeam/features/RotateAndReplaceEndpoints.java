package endpoints.myTeam.features;

import endpoints.myTeam.MyTeamEndpoints;

/**
 * @author imran.khan
 *
 */

public class RotateAndReplaceEndpoints extends MyTeamEndpoints{

	public static String replacementReasons = "/proxy/glow/positionservice/positions/replacement/reasons";
	
	public static String postReplace = "/proxy/glow/positionservice/positions/replacement?notification=true";
	
	public static String getSRNumberForGlober = "/v1/positions?viewId=6&userId=%s&projectName=%s&clientName=";
	
	public static String getSowId = "/proxy/glow/sowservice/sows?projectId=%d";
	
	public static String postRotate = "/proxy/glow/assignmentswriteorchestraservice/assignments";
	
	public static String getSkills = "/proxy/glow/skillservice/skills/average?role=%s&seniority=%s";
}
