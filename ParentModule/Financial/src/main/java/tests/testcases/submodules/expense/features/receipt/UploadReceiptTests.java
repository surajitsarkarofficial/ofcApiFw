package tests.testcases.submodules.expense.features.receipt;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import database.submodules.expense.ExpenseDBHelper;
import dataproviders.submodules.expense.ExpenseDataProviders;
import dto.submodules.expense.receipt.post.ReceiptDTO;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.features.receipt.UploadReceiptTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class UploadReceiptTests extends ExpenseBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("UploadReceiptTests");
	}
	
	/**
	 * Goal: Check that is feasible to upload a receipt.
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = ExpenseDataProviders.class, groups = {ExeGroups.Sanity})
	public void uploadReceiptTest(String userName) throws Exception {
		UploadReceiptTestHelper uploadReceiptTestHelper = new UploadReceiptTestHelper(userName);
		ExpenseDBHelper expenseDBHelper = new ExpenseDBHelper();
		ReceiptDTO uploadedReceipt = uploadReceiptTestHelper.uploadReceipt();
		Assert.assertEquals("SUCCESS", uploadedReceipt.getStatus(), "getStatus issue");
		Assert.assertTrue(uploadedReceipt.getMessage().contains(expenseDBHelper.getGloberId(userName)), "getMessage issue");
	}
	
}
