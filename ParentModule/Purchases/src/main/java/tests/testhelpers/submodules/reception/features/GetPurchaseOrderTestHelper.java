package tests.testhelpers.submodules.reception.features;

import com.aventstack.extentreports.Status;

import dto.submodules.purchaseOrder.get.Content_;
import dto.submodules.purchaseOrder.get.PurchaseOrderResponseDTO;
import endpoints.submodules.ReceptionEndpoints;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import parameters.submodules.purchaseOrder.PurchaseOrderParameters;
import tests.testcases.submodules.reception.ReceptionBaseTest;
import tests.testhelpers.submodules.reception.ReceptionTestHelper;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class GetPurchaseOrderTestHelper extends ReceptionTestHelper {

	public GetPurchaseOrderTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a GET in order to fetch purchase orders.
	 * @param parameters
	 * @return response
	 * @author german.massello
	 */
	public Response getPurchaseOrders(PurchaseOrderParameters parameters) {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(ReceptionBaseTest.sessionId);
		String url = ReceptionBaseTest.baseUrl + String.format(ReceptionEndpoints.getPurchaseOrders, parameters.getStatus(), parameters.getIsGlobalView(), parameters.getPageSize(), parameters.getPageNum());
		Response response = restUtils.executeGET(requestSpecification, url);
		ReceptionBaseTest.test.log(Status.INFO, "GET - User: " + userName);
		ReceptionBaseTest.test.log(Status.INFO, "RequestURL: " + url);
		return response;
	}
	
	/**
	 * This method will return the PurchaseOrderResponseDTO list.
	 * @param parameters
	 * @return purchasesOrders
	 * @author german.massello
	 */
	public PurchaseOrderResponseDTO getPurchaseOrderDTOList(PurchaseOrderParameters parameters) {
		Response response = getPurchaseOrders(parameters);
		PurchaseOrderResponseDTO purchasesOrders = response.as(PurchaseOrderResponseDTO.class, ObjectMapperType.GSON);
		return purchasesOrders;
	}
	
	/**
	 * This method will fetch a random purchase order.
	 * @return randomPurchaseOrder
	 * @throws Exception
	 * @author german.massello
	 */
	public Content_ getRandomPurchaseOrder(PurchaseOrderParameters parameters) throws Exception {
		PurchaseOrderResponseDTO purchasesOrders = getPurchaseOrderDTOList(parameters);
		return purchasesOrders.getContent().getRequisitionItemsPoPage().getContent().get(Utilities.
				getRandomNumberBetween(0, Integer.valueOf(purchasesOrders.getContent().getRequisitionItemsPoPage().getSize())));
	}
}
