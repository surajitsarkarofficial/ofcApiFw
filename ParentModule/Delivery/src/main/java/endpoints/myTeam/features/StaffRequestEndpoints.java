package endpoints.myTeam.features;

import endpoints.myTeam.MyTeamEndpoints;

/**
 * @author dnyaneshwar.waghmare
 *
 */

public class StaffRequestEndpoints extends MyTeamEndpoints{

	public static String getPositionDTOList = "/v1/positions?userId=%s&viewId=%s&parentViewId=%s&projectName=%s&clientName=%s";
	
	public static String getStudioList = "/v1/studios/technicalstudios";
	
	public static String getLocations = "/proxy/glow/positionservice/positions/filters/locations";
	
	public static String postStaffRequest = "/proxy/glow/positionservice/positions";
	
	public static String getSkills = "/proxy/glow/skillservice/skills/average?role=%s&seniority=%s";
	
	public static String getCreateStaffRequestLink = "/proxy/glow/positionservice/positions/seniorities/%s";

}
