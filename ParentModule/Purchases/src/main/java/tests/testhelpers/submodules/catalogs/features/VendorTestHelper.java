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
public class VendorTestHelper extends CatalogsTestHelper {

	public VendorTestHelper(String userName) throws Exception {
		super(userName);
	}
	
	/**
	 * This method will perform a GET in order to fetch a vendors list.
	 * @param storeName
	 * @return response
	 * @throws Exception 
	 * @author german.massello
	 */
	public Response getVendors(String storeName, String countryName) throws Exception {
		String society = new StoreTestHelper(userName).getSelectedStore(storeName, countryName).getSociety();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(PurchasesBaseTest.sessionId);
		String url = PurchasesBaseTest.baseUrl + String.format(CatalogsEndpoints.vendors, society, "an");
		Response response = restUtils.executeGET(requestSpecification, url);
		PurchasesBaseTest.test.log(Status.INFO, "GET - User: " + userName);
		PurchasesBaseTest.test.log(Status.INFO, "RequestURL: " + url);
		return response;
	}
	
}
