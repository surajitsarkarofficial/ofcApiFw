       package tests.testhelpers.submodules.quotation.features;

import com.aventstack.extentreports.Status;

import endpoints.submodules.QuotationEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import parameters.submodules.quotation.GetQuotationParameters;
import tests.testcases.submodules.quotation.QuotationBaseTest;
import tests.testhelpers.submodules.quotation.QuotationTestHelper;

/**
 * @author german.massello
 *
 */
public class GetQuotationTestHelper extends QuotationTestHelper {

	public GetQuotationTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a GET in order to fetch quotations.
	 * @param GetQuotationParameters
	 * @return response
	 * @author german.massello
	 */
	public Response getQuotations(GetQuotationParameters parameters) {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(QuotationBaseTest.sessionId);
		String url = QuotationBaseTest.baseUrl + String.format(QuotationEndpoints.getQuotations, parameters.getPageNum(), parameters.getPageSize(), parameters.getState(), parameters.getStatus(), parameters.getIsGlobalView());
		Response response = restUtils.executeGET(requestSpecification, url);
		QuotationBaseTest.test.log(Status.INFO, "GET - User: " + userName);
		QuotationBaseTest.test.log(Status.INFO, "RequestURL: " + url);
		return response;
	}
}
