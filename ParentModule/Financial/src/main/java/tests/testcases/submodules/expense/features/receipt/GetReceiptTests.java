package tests.testcases.submodules.expense.features.receipt;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.submodules.expense.ExpenseDataProviders;
import dto.submodules.expense.receipt.ReceiptForExpense;
import dto.submodules.expense.receipt.post.ReceiptDTO;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.features.receipt.GetReceiptTestHelper;
import tests.testhelpers.submodules.expense.features.receipt.UploadReceiptTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class GetReceiptTests extends ExpenseBaseTest {
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetReceiptTests");
	}
	
	/**
	 * Goal: Check that is feasible to fetch receipts available.
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = ExpenseDataProviders.class, groups = {ExeGroups.Sanity})
	public void getReceiptTest(String userName) throws Exception {
		UploadReceiptTestHelper uploadReceiptTestHelper = new UploadReceiptTestHelper(userName);
		GetReceiptTestHelper getReceiptTestHelper = new GetReceiptTestHelper(userName);
		//Given
		ReceiptDTO uploadedReceipt = uploadReceiptTestHelper.uploadReceipt();
		//When
		ReceiptForExpense fetchedReceiptForExpense = getReceiptTestHelper.getReceipt(uploadedReceipt);
		//Then
		Assert.assertTrue(fetchedReceiptForExpense.getUrlImage().contains("/glow/filemanagerservice"), "getUrlImage issue");
	}
}
