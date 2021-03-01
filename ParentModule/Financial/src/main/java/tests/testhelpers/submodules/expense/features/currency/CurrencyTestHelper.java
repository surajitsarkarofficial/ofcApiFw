package tests.testhelpers.submodules.expense.features.currency;

import org.testng.SkipException;

import com.aventstack.extentreports.Status;

import dto.submodules.expense.currency.CurrencyDTOList;
import endpoints.submodules.expense.features.currency.CurrencyEndpoints;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.ExpenseTestHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class CurrencyTestHelper extends ExpenseTestHelper {

	public CurrencyTestHelper(String userName) throws Exception {
		super(userName);
	}
	
	/**
	 * This method will fetch all available currencies in order to create a new expense.
	 * 
	 * @return
	 * @throws Exception
	 */
	public CurrencyDTOList getCurrencies() throws Exception {
		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ExpenseBaseTest.sessionId);
		String url = ExpenseBaseTest.baseUrl + CurrencyEndpoints.availableCurrencies;
		Response response = restUtils.executeGET(requestSpecification, url);
		new ExpenseBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch Available Currencies Details.", true);
		ExpenseBaseTest.test.log(Status.INFO, "Available Currencies details fetched successfully.");
		
		CurrencyDTOList currencyDTOList = response.as(CurrencyDTOList.class, ObjectMapperType.GSON);
		if (currencyDTOList.getContent().getTotalItems() == 0) {
			throw new SkipException("THERE IS NO ANY CURRENCY AVAILABLE ");}
		return currencyDTOList;
	}
	
	/**
	 * This method will fetch a random currency in order to create a new expense.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getRandomCurrencyId() throws Exception {
		CurrencyDTOList currencyDTOList = getCurrencies();
		return currencyDTOList.getContent().getCurrencyList().get(Utilities.
				getRandomNumberBetween(0, currencyDTOList.getContent().getTotalItems())).getId();
	}
	
}
