package tests.testhelpers.submodules.catalogs.features;

import com.aventstack.extentreports.Status;

import dto.submodules.catalogs.currency.CurrencyResponseDTO;
import endpoints.submodules.CatalogsEndpoints;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.PurchasesBaseTest;
import tests.testhelpers.submodules.catalogs.CatalogsTestHelper;

/**
 * @author german.massello
 *
 */
public class CurrencyTestHelper extends CatalogsTestHelper {

	public CurrencyTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a GET in order to fetch all currencies.
	 * @return response
	 * @author german.massello
	 * @throws Exception 
	 */
	public Response getCurrencies() throws Exception {
		String countryId = new CountryTestHelper(userName).getCountryDTOList().getContent().getEntries().get(0).getId();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(PurchasesBaseTest.sessionId);
		String url = PurchasesBaseTest.baseUrl + String.format(CatalogsEndpoints.currencies, countryId);
		Response response = restUtils.executeGET(requestSpecification, url);
		PurchasesBaseTest.test.log(Status.INFO, "GET - User: " + userName);
		PurchasesBaseTest.test.log(Status.INFO, "RequestURL: " + url);
		return response;
	}

	/**
	 * This method will return the CurrencyResponseDTO list.
	 * @return currencyResponseDTO
	 * @author german.massello
	 * @throws Exception 
	 */
	public CurrencyResponseDTO getCurrencyDTOList() throws Exception {
		Response response = getCurrencies();
		CurrencyResponseDTO currencyResponseDTO = response.as(CurrencyResponseDTO.class, ObjectMapperType.GSON);
		return currencyResponseDTO;
	}

}
