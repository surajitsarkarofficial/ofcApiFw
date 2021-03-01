package tests.testcases.submodules.expense.features.expense;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.FinancialDataProviders;
import dto.submodules.expense.expense.post.ExpenseDTO;
import payloads.submodules.expense.features.expense.ExpensePayload;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.features.expense.CreateNewExpenseTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class CreateExpenseTests extends ExpenseBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("CreateExpenseTests");
	}
	
	/**
	 * Create custom expense
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class, invocationCount = 2
			, groups = {ExeGroups.NotAvailableInPreProd})
	public void createCustomExpense(String userName) throws Exception {
		userName="custom.username";
		CreateNewExpenseTestHelper createNewExpenseTestHelper = new CreateNewExpenseTestHelper(userName);
		ExpensePayload expensePayload = createNewExpenseTestHelper.createBasicExpensePayload();
		expensePayload.withAmount("").withPaymentType("Cash|Amex|Visa").withCurrencyId("155");
		ExpenseDTO expectedExpense = createNewExpenseTestHelper.convertAPayloadIntoAnExpense(expensePayload);
		ExpenseDTO createdExpense = createNewExpenseTestHelper.createExpense(expensePayload);
		expenseValidation(expectedExpense, createdExpense, "Created");
	}

	/**
	 * Create reportable expense
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class, groups = {ExeGroups.Sanity})
	public void createReportableExpenseTest(String userName) throws Exception {
		CreateNewExpenseTestHelper createNewExpenseTestHelper = new CreateNewExpenseTestHelper(userName);
		ExpensePayload expensePayload = createNewExpenseTestHelper.createBasicExpensePayload();
		ExpenseDTO expectedExpense = createNewExpenseTestHelper.convertAPayloadIntoAnExpense(expensePayload);
		ExpenseDTO createdExpense = createNewExpenseTestHelper.createExpense(expensePayload);
		expenseValidation(expectedExpense, createdExpense, "Created");
	}

	/**
	 * Create personal expense
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class)
	public void createPersonalExpenseTest(String userName) throws Exception {
		CreateNewExpenseTestHelper createNewExpenseTestHelper = new CreateNewExpenseTestHelper(userName);
		ExpensePayload expensePayload = createNewExpenseTestHelper.createBasicExpensePayload();
		expensePayload.setExpenseType("PERSONAL");
		ExpenseDTO expectedExpense = createNewExpenseTestHelper.convertAPayloadIntoAnExpense(expensePayload);
		ExpenseDTO createdExpense = createNewExpenseTestHelper.createExpense(expensePayload);
		expenseValidation(expectedExpense, createdExpense, "Created");
	}
	
	/**
	 * Create reportable rebillable expense
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class, groups = {ExeGroups.Sanity})
	public void createReportableRebillableExpenseTest(String userName) throws Exception {
		CreateNewExpenseTestHelper createNewExpenseTestHelper = new CreateNewExpenseTestHelper(userName);
		ExpensePayload expensePayload = createNewExpenseTestHelper.createBasicExpensePayload();
		expensePayload.setExpenseType("REPORTABLE_REBILLABLE");
		ExpenseDTO expectedExpense = createNewExpenseTestHelper.convertAPayloadIntoAnExpense(expensePayload);
		ExpenseDTO createdExpense = createNewExpenseTestHelper.createExpense(expensePayload);
		expenseValidation(expectedExpense, createdExpense, "Created");
	}
	
	/**
	 * Create visa expense
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class)
	public void createVisaExpenseTest(String userName) throws Exception {
		CreateNewExpenseTestHelper createNewExpenseTestHelper = new CreateNewExpenseTestHelper(userName);
		ExpensePayload expensePayload = createNewExpenseTestHelper.createBasicExpensePayload();
		expensePayload.setPaymentType("Visa");
		ExpenseDTO expectedExpense = createNewExpenseTestHelper.convertAPayloadIntoAnExpense(expensePayload);
		ExpenseDTO createdExpense = createNewExpenseTestHelper.createExpense(expensePayload);
		expenseValidation(expectedExpense, createdExpense, "Created");
	}
	
	/**
	 * Create amex expense
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class)
	public void createAmexExpenseTest(String userName) throws Exception {
		CreateNewExpenseTestHelper createNewExpenseTestHelper = new CreateNewExpenseTestHelper(userName);
		ExpensePayload expensePayload = createNewExpenseTestHelper.createBasicExpensePayload();
		expensePayload.setPaymentType("Amex");
		ExpenseDTO expectedExpense = createNewExpenseTestHelper.convertAPayloadIntoAnExpense(expensePayload);
		ExpenseDTO createdExpense = createNewExpenseTestHelper.createExpense(expensePayload);
		expenseValidation(expectedExpense, createdExpense, "Created");
	}
	
	/**
	 * Expenses creation validation
	 * @param expectedExpense
	 * @param createdExpense
	 * @param message
	 */
	private void expenseValidation(ExpenseDTO expectedExpense, ExpenseDTO createdExpense, String message) {
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals("OK", createdExpense.getStatus(), "getStatus issue");
		softAssert.assertEquals(message, createdExpense.getMessage(), "getMessage issue");
		softAssert.assertEquals(createdExpense.getContent().getAmount(), expectedExpense.getContent().getAmount(), "getAmount issue");
		softAssert.assertEquals(createdExpense.getContent().getCategoryId(), expectedExpense.getContent().getCategoryId(), "getCategoryId issue");
		softAssert.assertEquals(createdExpense.getContent().getCurrencyId(), expectedExpense.getContent().getCurrencyId(), "getCurrencyId issue");
		softAssert.assertEquals(createdExpense.getContent().getProvider(), expectedExpense.getContent().getProvider(), "getProvider issue");
		softAssert.assertEquals(createdExpense.getContent().getTitle(), expectedExpense.getContent().getTitle(), "getTitle issue");
		softAssert.assertEquals(createdExpense.getContent().getPaymentType(), expectedExpense.getContent().getPaymentType(), "getPaymentType issue");
		softAssert.assertAll();
	}

}
