package tests.testhelpers.submodules.quotation.features;

import com.aventstack.extentreports.Status;

import dto.submodules.quotation.post.QuotationResponseDTO;
import endpoints.submodules.QuotationEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import parameters.submodules.quotation.QuotationParameters;
import payloads.submodules.quotation.QuotationPayLoadHelper;
import tests.testcases.submodules.quotation.QuotationBaseTest;
import tests.testhelpers.submodules.quotation.QuotationTestHelper;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class CreateQuotationTestHelper extends QuotationTestHelper {

	public CreateQuotationTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a POST in order to create a quotation.
	 * @param payLoad
	 * @return response
	 * @author german.massello
	 */
	public Response createQuotation (QuotationPayLoadHelper payLoad) {
		String requestURL = QuotationBaseTest.baseUrl + QuotationEndpoints.postQuotation;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(QuotationBaseTest.sessionId).contentType(ContentType.JSON).body(payLoad);
		Response response = restUtils.executePOST(requestSpecification, requestURL);
		QuotationBaseTest.test.log(Status.INFO, "POST - User: " + userName);
		QuotationBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		QuotationBaseTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(payLoad));
		return response;
	}
	
	/**
	 * This method will create a quotation from scratch.
	 * @param countryName
	 * @param groupName
	 * @return postQuotationResponseDTO
	 * @throws Exception 
	 * @author german.massello
	 */
	public QuotationResponseDTO createQuotationFromScratch (QuotationParameters parameters) throws Exception {
		QuotationPayLoadHelper payLoad = new QuotationPayLoadHelper(userName, parameters.getCountryName(), parameters.getGroupName());
		Response response = new CreateQuotationTestHelper(userName).createQuotation(payLoad);
		QuotationResponseDTO quotation = response.as(QuotationResponseDTO.class, ObjectMapperType.GSON);
		quotation.setQuotationPayLoad(payLoad);
		return quotation;
	}
	
}
