package tests.testhelpers.submodules.benefit.features;

import endpoints.submodules.benefit.BenefitEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.benefit.features.post.PostBenefitPayLoadHelper;
import properties.FinancialProperties;
import tests.testcases.submodules.benefit.BenefitBaseTest;
import utils.RestUtils;
import utils.Utilities;
import utils.UtilsBase;

/**
 * @author german.massello
 *
 */
public class PostBenefitTestHelper {

	/**
	 * This method will perform a POST in order to create a new benefit.
	 * @param payLoad
	 * @return response
	 * @throws Exception 
	 */
	public Response postBenefit(PostBenefitPayLoadHelper payLoad) throws Exception {
		String requestURL = FinancialProperties.microserviceBaseURL + String.format(BenefitEndpoints.postBenefit);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.contentType(ContentType.JSON).body(payLoad);
		Response response = new RestUtils().executePOST(requestSpecification, requestURL);
		UtilsBase.log("RequestURL: " + requestURL);
		UtilsBase.log("Payload: " + Utilities.convertJavaObjectToJson(payLoad));
		return response;
	}
	
	/**
	 * This method will create a benefit from scratch.
	 * @param username
	 * @return PostBenefitResponseDTO
	 * @throws Exception
	 */
	public PostBenefitPayLoadHelper createBenefitFromScratch(String username) throws Exception {
      PostBenefitPayLoadHelper payload = new PostBenefitPayLoadHelper(username);
      Response response = postBenefit(payload);
      new BenefitBaseTest().validateResponseToContinueTest(response, 200, "Post ceco api call was not successful.", true);
      return payload;
	}
	
}
