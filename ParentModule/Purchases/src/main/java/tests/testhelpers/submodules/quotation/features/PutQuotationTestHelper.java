package tests.testhelpers.submodules.quotation.features;

import endpoints.submodules.QuotationEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.quotation.QuotationPayLoadHelper;
import tests.testcases.submodules.quotation.QuotationBaseTest;
import tests.testhelpers.submodules.quotation.QuotationTestHelper;
import utils.Utilities;
import utils.UtilsBase;

/**
 * @author german.massello
 *
 */
public class PutQuotationTestHelper extends QuotationTestHelper {

	public PutQuotationTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a PUT in order to update a quotation.
	 * @param payload
	 * @return response
	 * @author german.massello
	 */
	public Response putQuotation (QuotationPayLoadHelper payload) {
		String requestURL = QuotationBaseTest.baseUrl + String.format(QuotationEndpoints.putQuotation);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(QuotationBaseTest.sessionId).contentType(ContentType.JSON).body(payload);
		Response response = restUtils.executePUT(requestSpecification, requestURL);
		UtilsBase.log("User: " + userName);
		UtilsBase.log("PUT - quotation - to status: " + payload.getStatus() + " State: "+payload.getState());
		UtilsBase.log("RequestURL: " + requestURL);
		UtilsBase.log("Payload: " + Utilities.convertJavaObjectToJson(payload));
		UtilsBase.log("----------------------------------------------------------------------------------------------------------------------------------");
		return response;
	}

}
