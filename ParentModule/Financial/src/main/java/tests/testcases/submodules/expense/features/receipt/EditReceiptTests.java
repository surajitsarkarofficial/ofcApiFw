package tests.testcases.submodules.expense.features.receipt;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.submodules.expense.ExpenseDataProviders;
import dto.submodules.expense.receipt.ReceiptForExpense;
import dto.submodules.expense.receipt.post.ReceiptDTO;
import dto.submodules.expense.receipt.put.EditReceiptResponse;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.features.receipt.EditReceiptTestHelper;
import tests.testhelpers.submodules.expense.features.receipt.GetReceiptTestHelper;
import tests.testhelpers.submodules.expense.features.receipt.UploadReceiptTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * This class contains test cases related to receipts edition.
 * @author german.massello
 *
 */
public class EditReceiptTests extends ExpenseBaseTest{

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("EditReceiptTests");
	}
	
	/**
	 * Goal: Check that is feasible mark a receipt as a used.
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = ExpenseDataProviders.class, groups = {ExeGroups.Sanity})
	public void markReceiptAsUsedTest(String userName) throws Exception {
		UploadReceiptTestHelper uploadReceiptTestHelper = new UploadReceiptTestHelper(userName);
		GetReceiptTestHelper getReceiptTestHelper = new GetReceiptTestHelper(userName);
		EditReceiptTestHelper editReceiptTestHelper = new EditReceiptTestHelper(userName);
		//Given
		ReceiptDTO uploadedReceipt = uploadReceiptTestHelper.uploadReceipt();
		ReceiptForExpense fetchedReceiptForExpense = getReceiptTestHelper.getReceipt(uploadedReceipt);
		//When
		EditReceiptResponse editReceiptResponse = editReceiptTestHelper.markReceiptAsUsed(fetchedReceiptForExpense);
		//Then
		Assert.assertEquals(fetchedReceiptForExpense.getMetadataId(), editReceiptResponse.getContent(), "getMetadataId() issue");
	}
	
}
