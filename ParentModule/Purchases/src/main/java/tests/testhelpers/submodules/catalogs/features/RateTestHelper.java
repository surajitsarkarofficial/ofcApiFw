package tests.testhelpers.submodules.catalogs.features;

import com.aventstack.extentreports.Status;

import endpoints.submodules.CatalogsEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.PurchasesBaseTest;
import tests.testhelpers.submodules.catalogs.CatalogsTestHelper;

/**
 * @author german.massello
 *
 */
public class RateTestHelper extends CatalogsTestHelper {

	public RateTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a GET in order to fetch a rate.
	 * @return response
	 * @author german.massello
	 * @throws Exception 
	 */
	public Response getRate() throws Exception {
		String currencyId = new CurrencyTestHelper(userName).getCurrencyDTOList().getContent().getEntries().get(0).getId();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(PurchasesBaseTest.sessionId);
		String url = PurchasesBaseTest.baseUrl + String.format(CatalogsEndpoints.rates, currencyId, currencyId);
		Response response = restUtils.executeGET(requestSpecification, url);
		PurchasesBaseTest.test.log(Status.INFO, "GET - User: " + userName);
		PurchasesBaseTest.test.log(Status.INFO, "RequestURL: " + url);
		return response;
	}
}
