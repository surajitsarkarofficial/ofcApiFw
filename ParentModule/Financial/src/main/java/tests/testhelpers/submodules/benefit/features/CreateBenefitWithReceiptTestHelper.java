package tests.testhelpers.submodules.benefit.features;

import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;
import dto.submodules.expense.receipt.ReceiptForExpense;
import dto.submodules.expense.receipt.post.ReceiptDTO;
import payloads.submodules.expense.features.expense.EditReceiptPayload;
import tests.testhelpers.submodules.benefit.BenefitTestHelper;
import tests.testhelpers.submodules.expense.features.expense.EditExpenseTestHelper;
import tests.testhelpers.submodules.expense.features.receipt.EditReceiptTestHelper;
import tests.testhelpers.submodules.expense.features.receipt.GetReceiptTestHelper;
import tests.testhelpers.submodules.expense.features.receipt.UploadReceiptTestHelper;

/**
 * @author german.massello
 *
 */
public class CreateBenefitWithReceiptTestHelper extends BenefitTestHelper {

	private String userName;
	
	public CreateBenefitWithReceiptTestHelper(String userName) throws Exception {
		super(userName);
		this.userName=userName;
	}
	
	/**
	 * This method will create a benefit with a receipt attached.
	 * @return ReportWithExpenseDTO
	 * @throws Exception
	 */
	public ReportWithExpenseDTO createABenefitWithReceiptFromScratch() throws Exception {
		ReportWithExpenseDTO myBenefit = new GetMyBenefitTestHelper(userName).getMyBenefitFromScratch();
		ReceiptDTO uploadedReceiptOne = new UploadReceiptTestHelper(userName).uploadReceipt();
		ReceiptForExpense receiptOne = new GetReceiptTestHelper(userName).getReceipt(uploadedReceiptOne);
		new EditExpenseTestHelper(userName).editExpense(new EditReceiptPayload(myBenefit, receiptOne, 0));
		new EditReceiptTestHelper(userName).markReceiptAsUsed(receiptOne);

		ReceiptDTO uploadedReceiptTwo = new UploadReceiptTestHelper(userName).uploadReceipt();
		ReceiptForExpense receiptTwo = new GetReceiptTestHelper(userName).getReceipt(uploadedReceiptTwo);
		new EditExpenseTestHelper(userName).editExpense(new EditReceiptPayload(myBenefit, receiptTwo, 1));
		new EditReceiptTestHelper(userName).markReceiptAsUsed(receiptTwo);
		
		return myBenefit;
	}

}
