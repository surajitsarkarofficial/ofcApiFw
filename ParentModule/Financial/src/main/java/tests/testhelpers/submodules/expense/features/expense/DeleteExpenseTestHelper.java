package tests.testhelpers.submodules.expense.features.expense;

import com.aventstack.extentreports.Status;

import dto.submodules.expense.expense.delete.DeleteExpenseDTO;
import endpoints.submodules.expense.features.expense.DeleteExpenseEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.expense.features.expense.DeleteExpensePayload;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.ExpenseTestHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class DeleteExpenseTestHelper extends ExpenseTestHelper {

	private String userName;
	
	public DeleteExpenseTestHelper(String userName) throws Exception {
		super(userName);
		this.userName = userName;
	}

	/**
	 * This method will perform a PUT in order to delete a expense.
	 * 
	 * @param deleteExpensePayload
	 * @return
	 * @throws Exception
	 */
	public DeleteExpenseDTO deleteExpense(DeleteExpensePayload deleteExpensePayload) throws Exception {
		RestUtils restUtils = new RestUtils();
		String requestURL = ExpenseBaseTest.baseUrl + DeleteExpenseEndpoints.deleteExpense;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ExpenseBaseTest.sessionId).contentType(ContentType.JSON).body(deleteExpensePayload);
		Response response = restUtils.executePUT(requestSpecification, requestURL);
		new ExpenseBaseTest().validateResponseToContinueTest(response, 200,
				"Delete Expense api call was not successful.", true);
		ExpenseBaseTest.test.log(Status.INFO, "User: " + userName);
		ExpenseBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		ExpenseBaseTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(deleteExpensePayload));		
		ExpenseBaseTest.test.log(Status.INFO, "The expense was deleted.");
		DeleteExpenseDTO deleteExpenseDTO = response.as(DeleteExpenseDTO.class, ObjectMapperType.GSON);
		return deleteExpenseDTO;
	}
}
