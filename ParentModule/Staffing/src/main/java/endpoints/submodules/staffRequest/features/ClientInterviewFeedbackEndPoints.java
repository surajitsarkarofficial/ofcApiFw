package endpoints.submodules.staffRequest.features;

import endpoints.submodules.staffRequest.StaffRequestEndpoints;

public class ClientInterviewFeedbackEndPoints extends StaffRequestEndpoints{

	/**
	 * Get staff plan Id from position Id
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-{envName}/proxy/glow/staffingorchestraservice/staffplans?positionId={positionId}&suggestionDetailsPage=true
	 */
	public static String getStaffPlanId = "/proxy/glow/staffingorchestraservice/staffplans?positionId=%s&suggestionDetailsPage=%s";

	/**
	 * Post client interview comment 
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-{envName}/proxy/glow/staffingorchestraservice/staffplans/interviews
	 */
	public static String postClientInterviewFeedback = "/proxy/glow/staffingorchestraservice/staffplans/interviews";

	/**
	 * Get client interview comment
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-{envName}/proxy/glow/staffingorchestraservice/staffplans?positionId={positionId}&suggestionDetailsPage=true
	 */
	public static String getClientInterviewFeedback = "/proxy/glow/staffingorchestraservice/staffplans?positionId=%s&suggestionDetailsPage=%s";

	/**
	 * Select client interview accept/reject reason from dropdown
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-{envName}/proxy/glow/positionservice/positions/staffplaninterviewreasons?reasonType={accept/reject}
	 */
	public static String getClientInterviewReasons="/proxy/glow/positionservice/positions/staffplaninterviewreasons?reasonType=%s";

}