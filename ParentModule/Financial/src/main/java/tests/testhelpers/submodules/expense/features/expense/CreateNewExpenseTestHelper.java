package tests.testhelpers.submodules.expense.features.expense;

import database.submodules.expense.features.CreateNewExpenseDBHelper;
import dto.submodules.expense.expense.post.Content;
import dto.submodules.expense.expense.post.ExpenseDTO;
import dto.submodules.expense.receipt.ReceiptForExpense;
import dto.submodules.expense.receipt.post.ReceiptDTO;
import endpoints.submodules.expense.features.expense.CreateExpenseEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.expense.features.expense.ExpensePayload;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.ExpenseTestHelper;
import tests.testhelpers.submodules.expense.features.category.CategoryTestHelper;
import tests.testhelpers.submodules.expense.features.currency.CurrencyTestHelper;
import tests.testhelpers.submodules.expense.features.receipt.EditReceiptTestHelper;
import tests.testhelpers.submodules.expense.features.receipt.GetReceiptTestHelper;
import tests.testhelpers.submodules.expense.features.receipt.UploadReceiptTestHelper;
import utils.RestUtils;
import utils.Utilities;
import utils.UtilsBase;

/**
 * @author german.massello
 *
 */
public class CreateNewExpenseTestHelper extends ExpenseTestHelper {

	private String userName;
	
	public CreateNewExpenseTestHelper(String userName) throws Exception {
		super(userName);
		this.userName = userName;
	}

	/**
	 * This method will create a payload in order to create an expense.
	 * 
	 * @return
	 * @throws Exception
	 */
	public ExpensePayload createBasicExpensePayload() throws Exception {
		CurrencyTestHelper currencyTestHelper = new CurrencyTestHelper(userName);
		CategoryTestHelper categoryTestHelper = new CategoryTestHelper(userName);
		ExpensePayload basicExpensePayload = new ExpensePayload();
		basicExpensePayload.setCurrencyId(currencyTestHelper.getRandomCurrencyId());
		basicExpensePayload.setCategoryId(categoryTestHelper.getRandomCategoryId());
		return basicExpensePayload;
	}
	
	/**
	 * This method will perform a POST in order to create an expense.
	 * 
	 * @param expensePayload
	 * @return
	 * @throws Exception
	 */
	public ExpenseDTO createExpense(ExpensePayload expensePayload) throws Exception {
		RestUtils restUtils = new RestUtils();
		String requestURL = ExpenseBaseTest.baseUrl + CreateExpenseEndpoints.expense;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ExpenseBaseTest.sessionId).contentType(ContentType.JSON).body(expensePayload);
		Response response = restUtils.executePOST(requestSpecification, requestURL);
		new ExpenseBaseTest().validateResponseToContinueTest(response, 201,
				"Create New ExpenseReport api call was not successful.", true);
		UtilsBase.log("User: " + userName);
		UtilsBase.log("RequestURL: " + requestURL);
		UtilsBase.log("Payload: " + Utilities.convertJavaObjectToJson(expensePayload));
		UtilsBase.log("The expense was created successful.");
		ExpenseDTO createdExpense = response.as(ExpenseDTO.class, ObjectMapperType.GSON);
		createdExpense.setExpensePayload(expensePayload);
		if (expensePayload.getPaymentType()!="Cash") transformToCreditCardExpense(expensePayload.getPaymentType(), createdExpense);
		return createdExpense;
	}
	
	/**
	 * This method will convert a payload in to an ExpenseDTO.
	 * 
	 * @param expensePayload
	 * @return
	 */
	public ExpenseDTO convertAPayloadIntoAnExpense(ExpensePayload expensePayload) {
		ExpenseDTO expenseDTOList = new ExpenseDTO();
		Content content = new Content();
		content.setAmount(expensePayload.getAmount());
		content.setCategoryId(expensePayload.getCategoryId());
		content.setCurrencyId(expensePayload.getCurrencyId());
		content.setProvider(expensePayload.getProvider());
		content.setTitle(expensePayload.getTitle());
		content.setPaymentType("Cash");
		expenseDTOList.setContent(content);
		return expenseDTOList;
	}
	
	/**
	 * This method will create a Expense from scratch.
	 * 
	 * @return 
	 * @throws Exception
	 */
	public ExpenseDTO createBasicExpense() throws Exception {
		ExpensePayload expensePayload = createBasicExpensePayload();
		ExpenseDTO expenseDTO = createExpense(expensePayload);
		return expenseDTO;
	}
	
	/**
	 * This method will transform a cash expense into a credit card expense.
	 * 
	 * @param creditCardName
	 * @param expenseDTO
	 * @return
	 * @throws Exception
	 */
	private ExpenseDTO transformToCreditCardExpense(String creditCardName, ExpenseDTO expenseDTO ) throws Exception {
		CreateNewExpenseDBHelper createNewExpenseDBHelper = new CreateNewExpenseDBHelper();
		createNewExpenseDBHelper.transformToCreditCardExpense(creditCardName, expenseDTO.getContent().getId());
		return expenseDTO;
	}

	/**
	 * This method with create a expense with a attached receipt
	 * @return ExpenseDTO
	 * @throws Exception
	 */
	public ExpenseDTO createExpenseWithReceipt() throws Exception  {
		UploadReceiptTestHelper uploadReceiptTestHelper = new UploadReceiptTestHelper(userName);
		GetReceiptTestHelper getReceiptTestHelper = new GetReceiptTestHelper(userName);
		EditReceiptTestHelper editReceiptTestHelper = new EditReceiptTestHelper(userName);
		ReceiptDTO uploadedReceipt = uploadReceiptTestHelper.uploadReceipt();
		ReceiptForExpense fetchedReceiptForExpense = getReceiptTestHelper.getReceipt(uploadedReceipt);
		ExpensePayload expensePayload = createBasicExpensePayload();
		expensePayload.setReceiptId(fetchedReceiptForExpense.getReceiptId());
		expensePayload.setUrlImage(fetchedReceiptForExpense.getUrlImage());
		ExpenseDTO expenseDTO = createExpense(expensePayload);
		editReceiptTestHelper.markReceiptAsUsed(fetchedReceiptForExpense);
		return expenseDTO;
	}
	
}
