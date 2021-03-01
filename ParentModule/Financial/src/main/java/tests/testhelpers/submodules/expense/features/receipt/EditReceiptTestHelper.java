package tests.testhelpers.submodules.expense.features.receipt;

import com.aventstack.extentreports.Status;

import dto.submodules.expense.receipt.ReceiptForExpense;
import dto.submodules.expense.receipt.put.EditReceiptResponse;
import endpoints.submodules.expense.features.receipt.ReceiptEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.expense.features.receipt.EditReceiptPayload;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.ExpenseTestHelper;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class EditReceiptTestHelper extends ExpenseTestHelper {

	private String userName;
	
	public EditReceiptTestHelper(String userName) throws Exception {
		super(userName);
		this.userName = userName;
	}

	/**
	 * This method will perform a PUT in order to mark a receipt as used.

	 * @param receiptForExpense
	 * @return editReceiptResponse
	 * @throws Exception
	 */
	public EditReceiptResponse markReceiptAsUsed(ReceiptForExpense receiptForExpense) throws Exception {
		String requestURL = ExpenseBaseTest.baseUrl + String.format(ReceiptEndpoints.editReceipt,receiptForExpense.getMetadataId());
		EditReceiptPayload editReceiptPayload = new EditReceiptPayload();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ExpenseBaseTest.sessionId).contentType(ContentType.JSON).body(editReceiptPayload);
		Response response = restUtils.executePUT(requestSpecification, requestURL);
		new ExpenseBaseTest().validateResponseToContinueTest(response, 200,
				"Edit receipt api call was not successful.", true);
		ExpenseBaseTest.test.log(Status.INFO, "User: " + userName);
		ExpenseBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		ExpenseBaseTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(editReceiptPayload));		
		ExpenseBaseTest.test.log(Status.INFO, "The receipt was marked as used.");
		EditReceiptResponse editReceiptResponse = response.as(EditReceiptResponse.class, ObjectMapperType.GSON);
		return editReceiptResponse;
	}
}
