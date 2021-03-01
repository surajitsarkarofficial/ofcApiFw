package tests.testhelpers.submodules.expense.features.expense;

 import dto.submodules.expense.expense.post.ExpenseDTO;
import endpoints.submodules.expense.features.expense.CreateExpenseEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.expense.features.expense.ExpensePayload;
import payloads.submodules.expense.features.expense.EditReceiptPayload;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.ExpenseTestHelper;
import utils.Utilities;
import utils.UtilsBase;

/**
 * @author german.massello
 *
 */
public class EditExpenseTestHelper extends ExpenseTestHelper {

	private String userName;
	
	public EditExpenseTestHelper(String userName) throws Exception {
		super(userName);
		this.userName = userName;
	}
	
	/**
	 * This method will perform a PUT in order to update a expense.
	 * @param requestSpecification
	 * @return ExpenseDTO
	 * @throws Exception
	 */
	private ExpenseDTO editExpense(RequestSpecification requestSpecification) throws Exception {
		String requestURL = ExpenseBaseTest.baseUrl + CreateExpenseEndpoints.expense;
		Response response = restUtils.executePUT(requestSpecification, requestURL);
		new ExpenseBaseTest().validateResponseToContinueTest(response, 200,
				"Edit New Expense api call was not successful.", true);
		UtilsBase.log("User: " + userName);
		UtilsBase.log("RequestURL: " + requestURL);
		UtilsBase.log("The expense was edited successful.");
		ExpenseDTO editedExpense = response.as(ExpenseDTO.class, ObjectMapperType.GSON);
		return editedExpense;
	}
	
	/**
	 * This method will create a RequestSpecification from a ExpensePayload.
	 * @param payload
	 * @return ExpenseDTO
	 * @throws Exception
	 */
	public ExpenseDTO editExpense(ExpensePayload payload) throws Exception {
		UtilsBase.log("Payload: " + Utilities.convertJavaObjectToJson(payload));
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ExpenseBaseTest.sessionId).contentType(ContentType.JSON).body(payload);
		return editExpense(requestSpecification);
	}

	/**
	 * This method will create a RequestSpecification from a EditReceiptPayload.
	 * @param payload
	 * @return ExpenseDTO
	 * @throws Exception
	 */
	public ExpenseDTO editExpense(EditReceiptPayload payload) throws Exception {
		UtilsBase.log("Payload: " + Utilities.convertJavaObjectToJson(payload));
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ExpenseBaseTest.sessionId).contentType(ContentType.JSON).body(payload);
		return editExpense(requestSpecification);
	}

}
