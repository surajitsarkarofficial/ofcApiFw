package tests.testhelpers.submodules.requisition.features;

import endpoints.submodules.RequisitionEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.quotation.QuotationPayLoadHelper;
import tests.testcases.submodules.requisition.RequisitionBaseTest;
import tests.testhelpers.submodules.requisition.RequisitionTestHelper;
import utils.Utilities;
import utils.UtilsBase;

/**
 * @author german.massello
 *
 */
public class PostQuotationTestHelper extends RequisitionTestHelper {

	public PostQuotationTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * Create quotation.
	 * @param payLoad
	 * @return Response
	 */
	public Response createQuotation (QuotationPayLoadHelper payLoad) {
		String requestURL = RequisitionBaseTest.baseUrl + RequisitionEndpoints.postRequisitions;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(RequisitionBaseTest.sessionId).contentType(ContentType.JSON).body(payLoad);
		Response response = restUtils.executePOST(requestSpecification, requestURL);
		UtilsBase.log("User: " + userName);
		UtilsBase.log("POST - Create requisition: " + payLoad.getStatus() + " State: " + payLoad.getState());
		UtilsBase.log("RequestURL: " + requestURL);
		UtilsBase.log("Payload: " + Utilities.convertJavaObjectToJson(payLoad));
		UtilsBase.log("----------------------------------------------------------------------------------------------------------------------------------");
		return response;
	}

}
