package tests.testhelpers.submodules.reception.features;

import dto.submodules.reception.get.GetReceptionResponseDTO;
import endpoints.submodules.ReceptionEndpoints;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import parameters.submodules.reception.GetReceptionParameters;
import tests.testcases.PurchasesBaseTest;
import tests.testhelpers.submodules.reception.ReceptionTestHelper;
import utils.UtilsBase;

/**
 * @author german.massello
 *
 */
public class GetReceptionTestHelper extends ReceptionTestHelper {

	public GetReceptionTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * Get reception
	 * @param parameters
	 * @return GetReceptionResponseDTO
	 * @throws Exception
	 */
	public GetReceptionResponseDTO getReception(GetReceptionParameters parameters) throws Exception {
		String url;
		long startTime = System.currentTimeMillis();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(PurchasesBaseTest.sessionId);
		if (parameters.getStatus().isEmpty()) {
			url = PurchasesBaseTest.baseUrl + String.format(ReceptionEndpoints.getReceptionGlobalView, parameters.getPageNum(), parameters.getPageSize(), 
					parameters.getIsGlobalView(), parameters.getIsPurchaserView());
		} else {
			url = PurchasesBaseTest.baseUrl + String.format(ReceptionEndpoints.getReception, parameters.getPageNum(), parameters.getPageSize(),
					parameters.getStatus(), parameters.getIsGlobalView(), parameters.getIsPurchaserView());
		}
		Response response = restUtils.executeGET(requestSpecification, url);
		long endTime = System.currentTimeMillis();
		UtilsBase.log("GET - User: " + userName);
		UtilsBase.log("RequestURL: " + url);
		UtilsBase.log("Elapsed time in ms: " + (endTime-startTime));
		validateResponseToContinueTest(response, 200, "Get receptions api call was not successful.", true);
		GetReceptionResponseDTO responseDTO = response.as(GetReceptionResponseDTO.class, ObjectMapperType.GSON);
		UtilsBase.log("Total records quantities: " + responseDTO.getContent().getTotalItems());
		return responseDTO;
	}

}
