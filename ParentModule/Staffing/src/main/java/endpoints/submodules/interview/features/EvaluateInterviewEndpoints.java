package endpoints.submodules.interview.features;

import endpoints.submodules.interview.InterviewEndPoints;

/**
 * 
 * @author deepakkumar.hadiya
 *
 */

public class EvaluateInterviewEndpoints extends InterviewEndPoints{

	/**
	 * @apiNote Api endpoint to evaluate accepted interview request
	 */
	
	public static String evaluateInterview=interviewService+"/technicalInterviews/technicalInterviewResult";
}
