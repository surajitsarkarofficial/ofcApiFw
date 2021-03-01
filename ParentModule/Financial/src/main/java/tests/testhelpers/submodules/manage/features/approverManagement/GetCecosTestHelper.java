package tests.testhelpers.submodules.manage.features.approverManagement;

import dto.submodules.manage.approverManagement.getCecos.GetCecosResponseDTO;
import dto.submodules.manage.approverManagement.getCecos.ViewCecoApproverList;
import endpoints.submodules.manage.features.ApproverManagementEndpoints;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import parameters.submodules.manage.features.approverManagement.getCecos.CecoNumbers;
import parameters.submodules.manage.features.approverManagement.getCecos.GetCecosParameters;
import parameters.submodules.manage.features.approverManagement.getCecos.IncompleteCecoNumber;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class GetCecosTestHelper extends ManageTestHelper {

	public GetCecosTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a GET in order to fetch cecos.
	 * @param parameters
	 * @return response
	 * @throws Exception 
	 */
	public Response getCecos(GetCecosParameters parameters) throws Exception {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(ManageBaseTest.sessionId);
		String url = ManageBaseTest.baseUrl + String.format(ApproverManagementEndpoints.getCecos, parameters.getPageSize(), parameters.getPageNum(), parameters.getSortAscending(), parameters.getSearchValue());
		Response response = restUtils.executeGET(requestSpecification, url);
		validateResponseToContinueTest(response, parameters.getStatusCode(), "Get cecos api call was not successful.", true);
		return response;
	}
	
	/**
	 * This method will return the GetCecosResponseDTO
	 * @param parameters
	 * @return cecosResponseDTO
	 * @throws Exception 
	 */
	public GetCecosResponseDTO getCecosResponseDTO(GetCecosParameters parameters) throws Exception { 
		Response response = getCecos(parameters);
		GetCecosResponseDTO cecosResponseDTO = response.as(GetCecosResponseDTO.class, ObjectMapperType.GSON);
		return cecosResponseDTO;
	}
	
	/**
	 * This method will return a random ceco.
	 * @param parameters
	 * @return randomCeco
	 * @throws Exception 
	 */
	public ViewCecoApproverList getRandomCeco(GetCecosParameters parameters) throws Exception { 
		GetCecosResponseDTO cecosResponseDTO = getCecosResponseDTO(parameters);
		return cecosResponseDTO.getContent().getViewCecoApproverList().get(Utilities.getRandomNumberBetween(0, cecosResponseDTO.getContent().getViewCecoApproverList().size()));
	}
	
	/**
	 * This method will check if both ceco numbers are present in the response.
	 * @param cecoNumbers
	 * @param cecos
	 * @return Boolean
	 */
	public Boolean areCecosNumbersPresent(CecoNumbers cecoNumbers, GetCecosResponseDTO cecos) {
		Boolean isPresentCecoNumberOne=false;
		Boolean isPresentCecoNumberTwo=false;
		for (ViewCecoApproverList cecosList:cecos.getContent().getViewCecoApproverList()) if (cecosList.getCecoNumber().equals(cecoNumbers.getCecoNumberOne())) isPresentCecoNumberOne=true;
		for (ViewCecoApproverList cecosList:cecos.getContent().getViewCecoApproverList()) if (cecosList.getCecoNumber().equals(cecoNumbers.getCecoNumberTwo())) isPresentCecoNumberTwo=true;
		return (isPresentCecoNumberOne&isPresentCecoNumberTwo);
	}

	/**
	 * This method will check that the incompleted ceco number is present in all cecos numbers that are available in the response.
	 * @param incompletedCeco
	 * @param cecosResponse
	 * @return Boolean
	 */
	public Boolean isIncompleteCecoNumberPresent(IncompleteCecoNumber incompletedCeco, GetCecosResponseDTO cecosResponse) {
		Boolean isPresent=false;
		for (ViewCecoApproverList cecosList:cecosResponse.getContent().getViewCecoApproverList()) {
			if (!cecosList.getCecoNumber().contains(incompletedCeco.getIncompleteCecoNumber())) return false; 
			else isPresent=true;
		}
		return isPresent;
	}
}
