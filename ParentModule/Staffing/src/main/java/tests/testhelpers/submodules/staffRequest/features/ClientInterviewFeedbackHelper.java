package tests.testhelpers.submodules.staffRequest.features;

import com.aventstack.extentreports.Status;

import database.submodules.staffRequest.features.ClientInterviewFeedbackDBHelper;
import endpoints.submodules.staffRequest.features.ClientInterviewFeedbackEndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.staffRequest.features.ClientInterviewFeedbackPayloadHelper;
import tests.testcases.StaffingBaseTest;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import tests.testhelpers.submodules.staffRequest.StaffRequestTestHelper;
import utils.RestUtils;
import utils.Utilities;

public class ClientInterviewFeedbackHelper extends StaffRequestTestHelper {
	RestUtils restUtils = new RestUtils();
	ClientInterviewFeedbackDBHelper clientInterviewFeedbackDBHelper;


	public ClientInterviewFeedbackHelper(String userName) throws Exception {
		super(userName);
		clientInterviewFeedbackDBHelper = new ClientInterviewFeedbackDBHelper();
	}


	String positionId = null;
	/**
	 * This method will add feedback of client interview and return it's response
	 * @param status
	 * @param clientInterviewComment
	 * @param globerType
	 * @return response
	 * @throws Exception
	 */
	public Response postClientInterviewFeedback(String status, String clientInterviewComment, String globerType) throws Exception {
		Response getClientInterviewReasonResponse =null;
		Response clientInterviewFeedbackResponse =null;
		String startDate = Utilities.getTodayDate("yyyy-MM-dd");
		positionId = clientInterviewFeedbackDBHelper.getInProgressPositionId(startDate);
		
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(StaffRequestBaseTest.sessionId);

		//Get staff plan Id
		String staffPlanId=getStaffPlanId(globerType);
		
		if(staffPlanId==null) {
			StaffRequestBaseTest.test.log(Status.SKIP,"staff plan id details are null hence can not provide client feeedback to null staff plan id");
		}else {
			//Get Client interview accept/reject reason
			String clientInterviewReasonUrl = StaffingBaseTest.baseUrl+String.format(ClientInterviewFeedbackEndPoints.getClientInterviewReasons, status);
			getClientInterviewReasonResponse = restUtils.executeGET(requestSpecification, clientInterviewReasonUrl);
			new StaffRequestBaseTest().validateResponseToContinueTest(getClientInterviewReasonResponse, 200, "Unable to select Client interview feedback reason ", true);
			String clientInterviewReason = restUtils.getValueFromResponse(getClientInterviewReasonResponse, "details[1]..reason").toString();

			//Post client interview feedback
			String jsonBody = new ClientInterviewFeedbackPayloadHelper().clientInterviewFeedbackPayload(staffPlanId, clientInterviewReason, status, clientInterviewComment);

			if(jsonBody==null) {
				StaffRequestBaseTest.test.log(Status.SKIP, "Json body is null hence can not provide client interview feedback");
			}else {
				RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON)
						.body(jsonBody);
				String clientInterviewFeedbackUrl = StaffingBaseTest.baseUrl+ClientInterviewFeedbackEndPoints.postClientInterviewFeedback;
				clientInterviewFeedbackResponse = restUtils.executePOST(reqSpec, clientInterviewFeedbackUrl);
			}
		}
		return clientInterviewFeedbackResponse;
	}

	/**
	 * This method will get client feedback for a position
	 * @return
	 * @throws Exception
	 */
	public Response getClientInterviewFeedback() throws Exception {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(StaffRequestBaseTest.sessionId);
		String getClientInterviewFeedbackUrl = StaffRequestBaseTest.baseUrl + String.format(ClientInterviewFeedbackEndPoints.getClientInterviewFeedback, positionId,"true");
		Response getClientInterviewFeedbackResponse = restUtils.executeGET(requestSpecification, getClientInterviewFeedbackUrl);
		return getClientInterviewFeedbackResponse;
	}
}