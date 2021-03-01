package endpoints.myTeam.features;

import endpoints.myTeam.MyTeamEndpoints;

/**
 * @author dnyaneshwar.waghmare
 *
 */

public class MyTeamViewTabforDDEndpoints extends MyTeamEndpoints{

	/**
	 * SAMPLE URL :
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-ui7/proxy/glow/projectorchestraservice/globerprojects/clients?globerId=322638634&projectId=&businessUnitId=&assignmentStartDate=&assignmentEndDate=&projectState=ON_GOING
	 */
	public static String getClientListForDD = "/proxy/glow/projectorchestraservice/globerprojects/clients?globerId=%s&projectId=&businessUnitId=&assignmentStartDate=&assignmentEndDate=&projectState=%s&viewType=%s";
	
	/**
	 * SAMPLE URL :
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-ui7/proxy/glow/projectorchestraservice/globerprojects?pageNumber=1&pageSize=20&globerId=322638634&projectId=&businessUnitId=&assignmentStartDate=&assignmentEndDate=&projectState=ON_GOING&viewType=MY_VIEW
	 */
	public static String getProjectListForDD = "/proxy/glow/projectorchestraservice/globerprojects?pageNumber=%s&pageSize=%s&globerId=%s&projectId=&businessUnitId=&assignmentStartDate=&assignmentEndDate=&projectState=%s&viewType=%s";
	

	/**
	 * SAMPLE URL :
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-ui7/proxy/glow/assignmentservice/assignments/clientMembers/{clientId}?type=client&fetchProjectRole=true&sortBy=POSTION&sortOrder=ASC&pageSize=100&pageNumber=1
	 */
	public static String getClientMemberListForDD = "/proxy/glow/assignmentservice/assignments/clientMembers/%d?type=%s&fetchProjectRole=%s&sortBy=%s&sortOrder=%s&pageSize=%s&pageNumber=%s&viewType=%s";

	
}
