package tests.testcases.submodules.expense.features.expense;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.FinancialDataProviders;
import dto.submodules.expense.expense.delete.DeleteExpenseDTO;
import dto.submodules.expense.expense.post.ExpenseDTO;
import payloads.submodules.expense.features.expense.DeleteExpensePayload;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.features.expense.CreateNewExpenseTestHelper;
import tests.testhelpers.submodules.expense.features.expense.DeleteExpenseTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class DeleteExpenseTests extends ExpenseBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("DeleteExpenseTests");
	}
	
	/**
	 * Goal: Check that is feasible delete an expense.
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class, groups = {ExeGroups.Sanity})
	public void deleteExpenseTest(String userName) throws Exception {
		CreateNewExpenseTestHelper createNewExpenseTestHelper = new CreateNewExpenseTestHelper(userName);
		DeleteExpenseTestHelper deleteExpenseTestHelper = new DeleteExpenseTestHelper(userName);
		//Given
		ExpenseDTO createdExpense = createNewExpenseTestHelper.createBasicExpense();
		//When
		DeleteExpensePayload deleteExpensePayload = new DeleteExpensePayload(createdExpense.getContent().getId());
		DeleteExpenseDTO deleteExpenseDTO = deleteExpenseTestHelper.deleteExpense(deleteExpensePayload);
		//Then
		Assert.assertEquals(deleteExpenseDTO.getMessage(), "Deleted");
		Assert.assertEquals(deleteExpenseDTO.getStatus(), "OK");
	}
	
}
