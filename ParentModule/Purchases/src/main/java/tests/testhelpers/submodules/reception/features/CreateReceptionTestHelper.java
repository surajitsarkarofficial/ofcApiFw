package tests.testhelpers.submodules.reception.features;

import com.aventstack.extentreports.Status;

import dto.submodules.reception.post.ReceptionResponseDTO;
import endpoints.submodules.ReceptionEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import parameters.submodules.purchaseOrder.PurchaseOrderParameters;
import payloads.submodules.reception.ReceptionPayLoadHelper;
import tests.testcases.submodules.reception.ReceptionBaseTest;
import tests.testhelpers.PurchasesTestHelper;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class CreateReceptionTestHelper extends PurchasesTestHelper {

	public CreateReceptionTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a POST in order to create a reception.
	 * @param payLoad
	 * @return response
	 * @author german.massello
	 */
	public Response createReception (ReceptionPayLoadHelper payLoad) {
		String requestURL = ReceptionBaseTest.baseUrl + ReceptionEndpoints.postReception;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReceptionBaseTest.sessionId).contentType(ContentType.JSON).body(payLoad);
		Response response = restUtils.executePOST(requestSpecification, requestURL);
		ReceptionBaseTest.test.log(Status.INFO, "POST - User: " + userName);
		ReceptionBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		ReceptionBaseTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(payLoad));
		return response;
	}
	
	/**
	 * This method will create a reception from scratch.
	 * @param parameters
	 * @return reception
	 * @throws Exception
	 * @author german.massello
	 */
	public ReceptionResponseDTO createReceptionFromScratch(PurchaseOrderParameters parameters) throws Exception {
		ReceptionPayLoadHelper payload = new ReceptionPayLoadHelper(new GetPurchaseOrderTestHelper(parameters.getUserName()).getRandomPurchaseOrder(parameters));
		ReceptionResponseDTO reception = createReception(payload).as(ReceptionResponseDTO.class, ObjectMapperType.GSON);
		return reception;
	}
}
