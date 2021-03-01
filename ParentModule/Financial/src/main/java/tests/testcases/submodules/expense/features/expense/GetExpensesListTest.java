package tests.testcases.submodules.expense.features.expense;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.FinancialDataProviders;
import dto.submodules.expense.expense.post.ExpenseDTO;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.features.expense.CreateNewExpenseTestHelper;
import tests.testhelpers.submodules.expense.features.expense.GetExpensesListTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class GetExpensesListTest extends ExpenseBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetExpensesListTest");
	}
	
	/**
	 * Get expenses list
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class, groups = {ExeGroups.Sanity})
	public void getExpensesListTest(String userName) throws Exception {
		CreateNewExpenseTestHelper createNewExpenseTestHelper = new CreateNewExpenseTestHelper(userName);
		GetExpensesListTestHelper getExpensesListTestHelper = new GetExpensesListTestHelper(userName);
		ExpenseDTO expectedExpense = createNewExpenseTestHelper.createBasicExpense();
		ExpenseDTO fetchedExpense = getExpensesListTestHelper.getExpenseFromTheExpensesList(expectedExpense);
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals("OK", fetchedExpense.getStatus(), "getStatus issue");
		softAssert.assertEquals("Success", fetchedExpense.getMessage(), "getMessage issue");
		softAssert.assertEquals(fetchedExpense.getContent().getAmount(), expectedExpense.getContent().getAmount(), "getAmount issue");
		softAssert.assertEquals(fetchedExpense.getContent().getCategoryId(), expectedExpense.getContent().getCategoryId(), "getCategoryId issue");
		softAssert.assertEquals(fetchedExpense.getContent().getCurrencyId(), expectedExpense.getContent().getCurrencyId(), "getCurrencyId issue");
		softAssert.assertEquals(fetchedExpense.getContent().getProvider(), expectedExpense.getContent().getProvider(), "getProvider issue");
		softAssert.assertEquals(fetchedExpense.getContent().getTitle(), expectedExpense.getContent().getTitle(), "getTitle issue");
		softAssert.assertEquals(fetchedExpense.getContent().getPaymentType(), expectedExpense.getContent().getPaymentType(), "getPaymentType issue");
		softAssert.assertAll();
	}	
	
}
