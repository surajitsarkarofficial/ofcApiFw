/**
 * 
 */
package tests.testhelpers.submodules.expense.features.receipt;

import com.aventstack.extentreports.Status;

import dto.submodules.expense.receipt.ReceiptForExpense;
import dto.submodules.expense.receipt.get.Content;
import dto.submodules.expense.receipt.get.GetReceiptResponse;
import dto.submodules.expense.receipt.get.Metadatum;
import dto.submodules.expense.receipt.post.ReceiptDTO;
import endpoints.submodules.expense.features.receipt.ReceiptEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.ExpenseTestHelper;
import utils.RestUtils;

/**
 * @author german.massello
 *
 */
public class GetReceiptTestHelper extends ExpenseTestHelper {

	private String userName;
	
	public GetReceiptTestHelper(String userName) throws Exception {
		super(userName);
		this.userName = userName;
	}

	/**
	 * This method will perform a GET and fetch a receipt for an expense.
	 * @param uploadedReceipt
	 * @return
	 * @throws Exception
	 */
	public ReceiptForExpense getReceipt(ReceiptDTO uploadedReceipt) throws Exception {
		RestUtils restUtils = new RestUtils();
		String requestURL = ExpenseBaseTest.baseUrl + ReceiptEndpoints.getReceipt;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ExpenseBaseTest.sessionId).contentType(ContentType.JSON);
		Response response = restUtils.executeGET(requestSpecification, requestURL);
		new ExpenseBaseTest().validateResponseToContinueTest(response, 200,
				"Get Receipt List api call was not successful.", true);
		
		GetReceiptResponse getReceiptResponse = response.as(GetReceiptResponse.class, ObjectMapperType.GSON);
		ReceiptForExpense receiptForExpense = new ReceiptForExpense();
		
		for (Content content : getReceiptResponse.getContent()) {
			for (Metadatum metadatum : content.getMetadata()) {
				if (metadatum.getKey().equals("disabled")) {
					if (metadatum.getValue().contains("false")) {
						receiptForExpense.setReceiptId(content.getId());
						receiptForExpense.setUrlImage(content.getUrl());
						receiptForExpense.setMetadataId(metadatum.getId());
					}
				}
			}
		}
		ExpenseBaseTest.test.log(Status.INFO, "User: " + userName);
		ExpenseBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		ExpenseBaseTest.test.log(Status.INFO,"Receipt was fetched successfully.");
		return receiptForExpense;
	}
}
