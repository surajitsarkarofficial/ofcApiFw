package tests.testhelpers.submodules.manage.features.approverManagement;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.testng.SkipException;

import dto.submodules.manage.approverManagement.getCecoDetails.Approver;
import dto.submodules.manage.approverManagement.getCecoDetails.GetCecoDetailsResponseDTO;
import endpoints.submodules.manage.features.ApproverManagementEndpoints;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import parameters.submodules.manage.features.approverManagement.getCecoDetails.GetCecoDetailsParameters;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;

/**
 * @author german.massello
 *
 */
public class GetCecoDetailsTestHelper extends ManageTestHelper {

	public GetCecoDetailsTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a GET in order to obtain the ceco details.
	 * @param parameters
	 * @return response
	 * @throws Exception 
	 */
	public Response getCecoDetails(GetCecoDetailsParameters parameters) throws Exception {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(ManageBaseTest.sessionId);
		String url = ManageBaseTest.baseUrl + String.format(ApproverManagementEndpoints.getCecoDetails, parameters.getCecoNumber());
		Response response = restUtils.executeGET(requestSpecification, url);
		validateResponseToContinueTest(response, 200, "Get ceco details api call was not successful.", true);
		return response;
	}
	
	/**
	 * This method will return GetCecoDetailsResponseDTO
	 * @param parameters
	 * @return cecoDetails
	 * @throws Exception 
	 */
	public GetCecoDetailsResponseDTO getCecoDetailsResponseDTO(GetCecoDetailsParameters parameters) throws Exception {
		Response response = getCecoDetails(parameters);
		GetCecoDetailsResponseDTO cecoDetails = response.as(GetCecoDetailsResponseDTO.class, ObjectMapperType.GSON);
		return cecoDetails;
	}
	
	/**
	 * This method check if the approver is present in the ceco details.
	 * @param approverId
	 * @param cecoDetails
	 * @param flag
	 * @return Boolean
	 */
	public Boolean isApproverPresent (String approverId, GetCecoDetailsResponseDTO cecoDetails, String flag) {
		for (Approver approver :cecoDetails.getContent().getApprovers()) 
			if (approver.getOwner().getGloberId().equals(approverId)&&(approver.getCecoApprover().equals(flag))) return true;
		return false;
	}
	
	/**
	 * This method check if the cross approver is present in the ceco details.
	 * @param parameters
	 * @param cecoDetails
	 * @return Boolean
	 */
	public Boolean isCrossApproverPresent (GetCecoDetailsParameters parameters, GetCecoDetailsResponseDTO cecoDetails) {
		for (Approver approver :cecoDetails.getContent().getApprovers()) {
			if (approver.getOwner().getGloberId().equals(parameters.getCrossApproverId()) && approver.getCrossApprover().equals("true")) return true;
		}
		return false;
	}
	
	/**
	 * This method will return a random approver.
	 * @param parameters
	 * @return String
	 * @throws Exception
	 */
	public String getRandomApprover (GetCecoDetailsParameters parameters) throws Exception {
		GetCecoDetailsResponseDTO getCecoDetailsResponseDTO = getCecoDetailsResponseDTO(parameters);
		List<Approver> allApprovers = getCecoDetailsResponseDTO.getContent().getApprovers();
		List<Approver> approvers = new LinkedList<>();
		for (Approver approver : allApprovers) {
			if (approver.getCrossApprover().equals("false") && (!approver.getOwner().getUsername().equals("martin.migoya"))) {
				approvers.add(approver);
			}
		}
		Collections.shuffle(approvers);
		if (approvers.size()==0) throw new SkipException("no random glober available");
		return approvers.get(0).getOwner().getGloberId();
	}

}
