package tests.testcases.submodules.expense.features.expense;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.FinancialDataProviders;
import dto.submodules.expense.expense.post.ExpenseDTO;
import payloads.submodules.expense.features.expense.ExpensePayload;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.features.expense.CreateNewExpenseTestHelper;
import tests.testhelpers.submodules.expense.features.expense.EditExpenseTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class EditExpenseTests extends ExpenseBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode(" EditExpenseTests");
	}
	
	/**
	 * Edit an expense
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class, groups = {ExeGroups.Sanity})
	public void editExpenseTest(String userName) throws Exception {
		CreateNewExpenseTestHelper createNewExpenseTestHelper = new CreateNewExpenseTestHelper(userName);
		EditExpenseTestHelper editExpenseTestHelper = new EditExpenseTestHelper(userName);
		ExpenseDTO editedExpenseDTO = createNewExpenseTestHelper.createBasicExpense();
		ExpensePayload expensePayload = editedExpenseDTO.getExpensePayload();
		expensePayload.setTitle(expensePayload.getTitle()+" Edited");
		expensePayload.setId(editedExpenseDTO.getContent().getId());
		expensePayload.setExpenseType("REPORTABLE_REBILLABLE");
		ExpenseDTO expectedExpense = createNewExpenseTestHelper.convertAPayloadIntoAnExpense(expensePayload);
		ExpenseDTO editedExpense = editExpenseTestHelper.editExpense(expensePayload);
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals("OK", editedExpense.getStatus(), "getStatus issue");
		softAssert.assertEquals("Updated", editedExpense.getMessage(), "getMessage issue");
		softAssert.assertEquals(editedExpense.getContent().getAmount(), expectedExpense.getContent().getAmount(), "getAmount issue");
		softAssert.assertEquals(editedExpense.getContent().getCategoryId(), expectedExpense.getContent().getCategoryId(), "getCategoryId issue");
		softAssert.assertEquals(editedExpense.getContent().getCurrencyId(), expectedExpense.getContent().getCurrencyId(), "getCurrencyId issue");
		softAssert.assertEquals(editedExpense.getContent().getProvider(), expectedExpense.getContent().getProvider(), "getProvider issue");
		softAssert.assertEquals(editedExpense.getContent().getTitle(), expectedExpense.getContent().getTitle(), "getTitle issue");
		softAssert.assertEquals(editedExpense.getContent().getPaymentType(), expectedExpense.getContent().getPaymentType(), "getPaymentType issue");
		softAssert.assertAll();
	}
}
