package tests.testhelpers.submodules.manage.features.approverManagement;

import com.aventstack.extentreports.Status;

import dto.submodules.manage.approverManagement.getApprovers.Approver;
import dto.submodules.manage.approverManagement.getApprovers.GetApproversResponseDTO;
import endpoints.submodules.manage.features.ApproverManagementEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import parameters.submodules.manage.features.approverManagement.GetApproversParameters;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;

/**
 * @author german.massello
 *
 */
public class GetApproversTestHelper extends ManageTestHelper {

	public GetApproversTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a GET in order to fetch approvers.
	 * @param parameters
	 * @return response
	 */
	public Response getApprovers(GetApproversParameters parameters) {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(ManageBaseTest.sessionId);
		String url = ManageBaseTest.baseUrl + String.format(ApproverManagementEndpoints.getApprovers, parameters.getModule(), parameters.getProjectId(), parameters.getAmount());
		Response response = restUtils.executeGET(requestSpecification, url);
		ManageBaseTest.test.log(Status.INFO, "GET - User: " + userName);
		ManageBaseTest.test.log(Status.INFO, "RequestURL: " + url);
		return response;
	}
	
	/**
	 * This method will check if an approver is present in the safe matrix.
	 * @param approvers
	 * @param approverId
	 * @return Boolean
	 */
	public Boolean isApproverPresentInTheSafeMatrix(GetApproversResponseDTO approvers, String approverId) {
		for (Approver approver: approvers.getContent().getApprovers()) {
			if (approver.getOwner().getGloberId().equals(approverId)) return true;
		}
		return false;
	}
	
}
