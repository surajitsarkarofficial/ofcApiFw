package tests.testhelpers.submodules.benefit.features;

import database.submodules.benefit.BenefitDBHelper;
import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;
import payloads.submodules.benefit.features.post.PostBenefitPayLoadHelper;
import tests.testhelpers.submodules.benefit.BenefitTestHelper;

/**
 * @author german.massello
 *
 */
public class GetMyBenefitTestHelper extends BenefitTestHelper{

	private String userName;
	
	public GetMyBenefitTestHelper(String userName) throws Exception {
		super(userName);
		this.userName=userName;
	}
	
	/**
	 * This method will create a new benefit and return it.
	 * @return ReportWithExpenseDTO
	 * @throws Exception
	 */
	public ReportWithExpenseDTO getMyBenefitFromScratch() throws Exception {
		PostBenefitPayLoadHelper benefitPayload = new PostBenefitTestHelper().createBenefitFromScratch(userName);
		ReportWithExpenseDTO myBenefit = new BenefitDBHelper().getReportWithExpenseDTOFromABenefit(benefitPayload);
		return myBenefit;
	}

}
