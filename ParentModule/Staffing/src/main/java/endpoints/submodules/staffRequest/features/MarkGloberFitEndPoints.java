package endpoints.submodules.staffRequest.features;

import endpoints.submodules.staffRequest.StaffRequestEndpoints;

public class MarkGloberFitEndPoints extends StaffRequestEndpoints {

	/**
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-{envName}/proxy/glow/positionservice/positions/staffplaninterviewreasons?reasonType=FIT
	 */
	public static String markGloberReasons = "/proxy/glow/positionservice/positions/staffplaninterviewreasons?reasonType=%s";

	/**
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-{envName}/proxy/glow/staffingorchestraservice/staffplans/interviews
	 */
	public static String postMarkFitGlober = "/proxy/glow/staffingorchestraservice/staffplans/interviews";
	
	/**
	 * View fit interview feedback
	 * https://backendportal-in-dev.corp.globant.com/BackendPortal-{envName}/proxy/glow/staffingorchestraservice/staffplans?staffPlanId={staffPlanId}&globerId={globerId}&globerType={globerType}&suggestionDetailsPage={true}
	 */
	public static String getViewFitInterviewFeedback = "/proxy/glow/staffingorchestraservice/staffplans?staffPlanId=%s&globerId=%s&globerType=%s&suggestionDetailsPage=%s";

	/**
	 * post fit interview confirmation
	 * http://10.220.150.174:8629/globers/endorsements?staffPlanId=169297415&isConfirmed=true
	 */
	public static String postFitInterviewConfirmation = "/globers/endorsements?staffPlanId=%s&isConfirmed=%s";
}
