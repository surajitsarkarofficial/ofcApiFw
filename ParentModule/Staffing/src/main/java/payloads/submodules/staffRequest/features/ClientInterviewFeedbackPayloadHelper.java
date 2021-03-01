package payloads.submodules.staffRequest.features;

import java.util.HashMap;
import java.util.Map;

import utils.Utilities;

/**
 * 
 * @author akshata.dongare
 *
 */
public class ClientInterviewFeedbackPayloadHelper {

	/**
	 * This method creates payload for client interview feedback
	 * @param staffPlanId
	 * @param clientInterviewReason
	 * @param clientInterviewStatus
	 * @param clientInterviewComment
	 * @return String
	 */
	public String clientInterviewFeedbackPayload(String staffPlanId, String clientInterviewReason, String clientInterviewStatus, String clientInterviewComment) {
		String json = null;
		String interviewFeedbackType = "CLIENTINTERVIEW";
		Map<Object, Object> mapOfClientInterviewFeedback = new HashMap<Object, Object>();

		mapOfClientInterviewFeedback.put("bookingStatus", null);
		mapOfClientInterviewFeedback.put("status", clientInterviewStatus);
		mapOfClientInterviewFeedback.put("clientInterviewReason", clientInterviewReason);
		mapOfClientInterviewFeedback.put("clientInterviewStatus", clientInterviewStatus);
		mapOfClientInterviewFeedback.put("clientInterviewComment", clientInterviewComment);
		mapOfClientInterviewFeedback.put("interviewFeedbackType", interviewFeedbackType);
		mapOfClientInterviewFeedback.put("staffPlanId", staffPlanId);
		mapOfClientInterviewFeedback.put("type", "Glober");

		json = Utilities.createJsonBodyFromMap(mapOfClientInterviewFeedback);
		return json;
	}
}