package tests.testhelpers.submodules.expense.features.mobile;

import endpoints.submodules.expense.features.mobile.MobileEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.expense.ExpenseTestHelper;
import utils.RestUtils;

/**
 * @author german.massello
 *
 */
public class MobileBannerTestHelper extends ExpenseTestHelper {

	public MobileBannerTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a POST in order record that the mobile banner was seen in glow.
	 * @return response
	 * @throws Exception 
	 */
	public Response postMobileBanner() throws Exception {
		String requestURL = ManageBaseTest.baseUrl + String.format(MobileEndpoints.postMobileBanner);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ManageBaseTest.sessionId).contentType(ContentType.JSON);
		Response response = new RestUtils().executePOST(requestSpecification, requestURL);
		validateResponseToContinueTest(response, 200, "POST mobil banner api call was not successful.", true);
		return response;
	}
	
}
