package tests.testhelpers.submodules.expense.features.expense;

import dto.submodules.expense.expense.post.ExpenseDTO;
import endpoints.submodules.expense.features.expense.GetExpenseEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.ExpenseTestHelper;
import utils.RestUtils;
import utils.UtilsBase;

/**
 * @author german.massello
 *
 */
public class GetExpenseTestHelper extends ExpenseTestHelper {

	private String userName;
	
	public GetExpenseTestHelper(String userName) throws Exception {
		super(userName);
		this.userName = userName;
	}

	/**
	 * This method will perform a GET in order to return an expense details.
	 * 
	 * @param createdExpense
	 * @return
	 * @throws Exception
	 */
	public ExpenseDTO getExpense(ExpenseDTO createdExpense) throws Exception {
		RestUtils restUtils = new RestUtils();
		String requestURL = ExpenseBaseTest.baseUrl 
				+ String.format(GetExpenseEndpoints.getExpense,createdExpense.getContent().getId());
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ExpenseBaseTest.sessionId).contentType(ContentType.JSON);
		Response response = restUtils.executeGET(requestSpecification, requestURL);
		new ExpenseBaseTest().validateResponseToContinueTest(response, 200,
				"Get Expense api call was not successful.", true);
		UtilsBase.log("User: " + userName);
		UtilsBase.log("RequestURL: " + requestURL);		
		UtilsBase.log("The expense was fetched successful.");
		ExpenseDTO fetchedExpense = response.as(ExpenseDTO.class, ObjectMapperType.GSON);
		return fetchedExpense;
	}
}
