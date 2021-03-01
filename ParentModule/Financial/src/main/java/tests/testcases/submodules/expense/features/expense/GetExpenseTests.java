package tests.testcases.submodules.expense.features.expense;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.FinancialDataProviders;
import dto.submodules.expense.expense.post.ExpenseDTO;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.features.expense.CreateNewExpenseTestHelper;
import tests.testhelpers.submodules.expense.features.expense.GetExpenseTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class GetExpenseTests extends ExpenseBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetExpenseTests");
	}
	
	/**
	 * Get an expense details.
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class, groups = {ExeGroups.Sanity})
	public void getExpenseDetailsTest(String userName) throws Exception {
		CreateNewExpenseTestHelper createNewExpenseTestHelper = new CreateNewExpenseTestHelper(userName);
		GetExpenseTestHelper getExpenseTestHelper = new GetExpenseTestHelper(userName);
		ExpenseDTO createdExpense = createNewExpenseTestHelper.createBasicExpense();
		ExpenseDTO fetchedExpense = getExpenseTestHelper.getExpense(createdExpense);
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals("OK", fetchedExpense.getStatus(), "getStatus issue");
		softAssert.assertEquals("Success", fetchedExpense.getMessage(), "getMessage issue");
		softAssert.assertEquals(createdExpense.getContent().getAmount(), fetchedExpense.getContent().getAmount(), "getAmount issue");
		softAssert.assertEquals(createdExpense.getContent().getCategoryId(), fetchedExpense.getContent().getCategoryId(), "getCategoryId issue");
		softAssert.assertEquals(createdExpense.getContent().getCurrencyId(), fetchedExpense.getContent().getCurrencyId(), "getCurrencyId issue");
		softAssert.assertEquals(createdExpense.getContent().getProvider(), fetchedExpense.getContent().getProvider(), "getProvider issue");
		softAssert.assertEquals(createdExpense.getContent().getTitle(), fetchedExpense.getContent().getTitle(), "getTitle issue");
		softAssert.assertEquals(createdExpense.getContent().getPaymentType(), fetchedExpense.getContent().getPaymentType(), "getPaymentType issue");
		softAssert.assertAll();
	}	
	
}
