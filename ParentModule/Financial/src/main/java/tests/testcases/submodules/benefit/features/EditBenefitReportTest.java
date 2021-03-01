package tests.testcases.submodules.benefit.features;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import database.GlowDBHelper;
import dataproviders.submodules.benefit.features.GetMyBenefitDataProviders;
import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;
import dto.submodules.expense.report.post.ReportDTO;
import parameters.submodules.expense.features.getReports.GetExpenseReportParameters;
import payloads.submodules.expense.features.report.ReportPayload;
import tests.testcases.submodules.benefit.BenefitBaseTest;
import tests.testhelpers.submodules.expense.features.report.CreateReportTestHelper;
import tests.testhelpers.submodules.expense.features.report.EditReportTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class EditBenefitReportTest extends BenefitBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("EditBenefitReportTest");
	}

	/**
	 * Check that is feasible to edit a draft report benefit.
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "getMyDraftReport", dataProviderClass = GetMyBenefitDataProviders.class
			, groups = {ExeGroups.NotAvailableInPreProd})
	public void editBenefitReportTest(GetExpenseReportParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		ReportWithExpenseDTO benefitReport = new ReportWithExpenseDTO(parameters);
		ReportPayload  payload = new ReportPayload(benefitReport); 
		ReportDTO expectedBenefitReport = new CreateReportTestHelper(parameters.getUsername()).convertAPayloadIntoAnReportDTO(payload);
		ReportDTO editedBenefitReport = new EditReportTestHelper(parameters.getUsername()).editReport(payload);
		softAssert.assertEquals("OK", editedBenefitReport.getStatus(), "getStatus issue");
		softAssert.assertEquals("Updated", editedBenefitReport.getMessage(), "getMessage issue");
		softAssert.assertEquals(editedBenefitReport.getContent().getOriginId(), expectedBenefitReport.getContent().getOriginId(), "getOriginId issue");
		softAssert.assertEquals(editedBenefitReport.getContent().getTitle(), expectedBenefitReport.getContent().getTitle(), "getTitle issue");
		softAssert.assertEquals(editedBenefitReport.getContent().getStatus(), expectedBenefitReport.getContent().getStatus(), "getStatus issue");
		softAssert.assertEquals(editedBenefitReport.getContent().getClientName(), new GlowDBHelper().getClientNameFromProjectId(expectedBenefitReport.getContent().getOriginId()), "getClientName issue");
		softAssert.assertAll();	
	}

}
