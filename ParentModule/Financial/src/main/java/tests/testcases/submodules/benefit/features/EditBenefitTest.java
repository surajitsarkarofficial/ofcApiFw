package tests.testcases.submodules.benefit.features;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.FinancialDataProviders;
import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;
import dto.submodules.expense.expense.post.ExpenseDTO;
import payloads.submodules.expense.features.expense.EditReceiptPayload;
import tests.testcases.submodules.benefit.BenefitBaseTest;
import tests.testhelpers.submodules.benefit.features.GetMyBenefitTestHelper;
import tests.testhelpers.submodules.expense.features.expense.EditExpenseTestHelper;

/**
 * @author german.massello
 *
 */
public class EditBenefitTest extends BenefitBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("EditBenefitTest");
	}

	/**
	 * Check that is feasible 
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class, invocationCount = 1)
	public void editBenefitTest(String userName) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		ReportWithExpenseDTO myBenefit = new GetMyBenefitTestHelper(userName).getMyBenefitFromScratch();
		EditReceiptPayload payload = new EditReceiptPayload(myBenefit);
		ExpenseDTO myEditedBenefit = new EditExpenseTestHelper(userName).editExpense(payload);
		softAssert.assertEquals(myEditedBenefit.getContent().getAmount(), payload.getAmount(), "getAmount issue");
		softAssert.assertEquals(myEditedBenefit.getContent().getProvider(), payload.getProvider(), "getProvider issue");
		softAssert.assertAll();
	}
}
