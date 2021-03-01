package tests.testhelpers.submodules.requisition.features;

import dto.submodules.requisition.get.GetRequisitionResponseDTO;
import endpoints.submodules.RequisitionEndpoints;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import parameters.submodules.requisition.GetRequisitionParameters;
import tests.testcases.PurchasesBaseTest;
import tests.testhelpers.submodules.requisition.RequisitionTestHelper;
import utils.UtilsBase;

/**
 * @author german.massello
 *
 */
public class GetRequisitionTestHelper extends RequisitionTestHelper {

	public GetRequisitionTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a GET in order to fetch requisitions.
	 * @param GetRequisitionParameters
	 * @return GetRequisitionResponseDTO
	 * @author german.massello
	 * @throws Exception 
	 */
	public GetRequisitionResponseDTO getRequisitions(GetRequisitionParameters parameters) throws Exception {
		long startTime = System.currentTimeMillis();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(PurchasesBaseTest.sessionId);
		String url = PurchasesBaseTest.baseUrl + String.format(RequisitionEndpoints.getRequisitions, 
				parameters.getPageNum(), parameters.getPageSize(), parameters.getIsPurchaserView(), parameters.getStatus(), parameters.getState(), parameters.getIsApprovalView()
				, parameters.getSapApprovalView(), parameters.getIsGlobalView(), parameters.getRequisition().getGroupId(), parameters.getRequisition().getRequesterId());
		Response response = restUtils.executeGET(requestSpecification, url);
		long endTime = System.currentTimeMillis();
		UtilsBase.log("GET - User: " + userName);
		UtilsBase.log("RequestURL: " + url);
		UtilsBase.log("Elapsed time in ms: " + (endTime-startTime));
		validateResponseToContinueTest(response, 200, "Get requisitions api call was not successful.", true);
		GetRequisitionResponseDTO getRequisitionResponseDTO = response.as(GetRequisitionResponseDTO.class, ObjectMapperType.GSON);
		UtilsBase.log("Records quantities: " + getRequisitionResponseDTO.getContent().getRequisitionList().size());
		UtilsBase.log("Total records quantities: " + getRequisitionResponseDTO.getContent().getTotalItems());
		return getRequisitionResponseDTO;
	}
}
