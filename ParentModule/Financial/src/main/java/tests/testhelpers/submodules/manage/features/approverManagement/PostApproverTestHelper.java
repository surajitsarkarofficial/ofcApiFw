package tests.testhelpers.submodules.manage.features.approverManagement;

import java.util.List;

import dto.submodules.manage.approverManagement.postApprover.Approver;
import dto.submodules.manage.approverManagement.postApprover.ApproversResponseDTO;
import endpoints.submodules.manage.features.ApproverManagementEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.manage.features.approverManagement.ApproverPayLoadHelper;
import payloads.submodules.manage.features.approverManagement.ApproverWithoutRol;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;

/**
 * @author german.massello
 *
 */
public class PostApproverTestHelper extends ManageTestHelper {
	
	private String userName;
	
	public PostApproverTestHelper(String userName) throws Exception {
		super(userName);
		this.userName=userName;
	}

	/**
	 * This method will perform a POST in order to add a approver to a ceco.
	 * @param payLoad
	 * @return response
	 * @throws Exception 
	 */
	public Response postApprover(ApproverPayLoadHelper payLoad) throws Exception {
		String requestURL = ManageBaseTest.baseUrl + String.format(ApproverManagementEndpoints.postApprover);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ManageBaseTest.sessionId).contentType(ContentType.JSON).body(payLoad);
		Response response = restUtils.executePOST(requestSpecification, requestURL);
		validateResponseToContinueTest(response, 200, "Post approver api call was not successful.", true);
		return response;
	}

	/**
	 * This method will return true if the approver exist in the response.
	 * @param payload
	 * @param approvers
	 * @return Boolean
	 */
	public Boolean isApproverPresent (String approverId, List<Approver> approvers) {
		for (Approver approver :approvers) if (approver.getGloberId().equals(approverId)) return true;
		return false;
	}
	
	/**
	 * This method will add a approver to a ceco from scratch.
	 * @return approvers
	 * @throws Exception
	 */
	public ApproversResponseDTO postApproverFromScratch() throws Exception {
	      ApproverPayLoadHelper payload = new ApproverPayLoadHelper(userName);
	      Response response = postApprover(payload);
	      validateResponseToContinueTest(response, 200, "post approver api call was not successful.", true);
	      ApproversResponseDTO approvers = response.as(ApproversResponseDTO.class, ObjectMapperType.GSON);
	      approvers.setNewApproverId(payload.getApproverId());
	      return approvers;
	}
	
	/**
	 * This method will add a approver without a rol to a ceco from scratch.
	 * @return approvers
	 * @throws Exception
	 */
	public ApproversResponseDTO postApproverWithoutRolFromScratch() throws Exception {
	      ApproverPayLoadHelper payload = new ApproverPayLoadHelper(userName, new ApproverWithoutRol());
	      Response response = postApprover(payload);
	      ApproversResponseDTO approvers = response.as(ApproversResponseDTO.class, ObjectMapperType.GSON);
	      approvers.setNewApproverId(payload.getApproverId());
	      return approvers;
	}
}
