package endpoints.submodules.interview.features;

import endpoints.submodules.interview.InterviewEndPoints;

/**
 * 
 * @author deepakkumar.hadiya
 *
 */

public class AcceptOrRejectInterviewEndpoints extends InterviewEndPoints{

	/**
	 * @apiNote Api endpoint to accept or reject interview request
	 */
	
	public static String acceptOrRejectInterview=interviewService+"/technicalinterviewrequest/acceptance";
	public static String interviewRequests=interviewService+"/technicalinterviewrequest/";
	
}
