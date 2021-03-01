package tests.testcases.submodules.expense.features.report;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import database.GlowDBHelper;
import dataproviders.FinancialDataProviders;
import dto.submodules.expense.report.post.ReportDTO;
import payloads.submodules.expense.features.report.ReportPayload;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.features.report.CreateReportTestHelper;
import tests.testhelpers.submodules.expense.features.report.EditReportTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class EditReportTests extends ExpenseBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("EditReportTests");
	}
	
	/**
	 * Goal: Check that is feasible to edit a report.
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class, groups = {ExeGroups.Sanity})
	public void editReportTest(String userName) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		CreateReportTestHelper createNewReportTestHelper = new CreateReportTestHelper(userName);
		EditReportTestHelper editReportTestHelper = new EditReportTestHelper(userName);
		//Given
		ReportDTO createdReport = createNewReportTestHelper.createBasicReport();
		//When
		ReportPayload reportPayload = createdReport.getReportPayload();
		reportPayload.setTitle(reportPayload.getTitle()+" Edited");
		reportPayload.setId(createdReport.getContent().getId());
		ReportDTO expectedReport = createNewReportTestHelper.convertAPayloadIntoAnReportDTO(reportPayload);
		ReportDTO editedReport = editReportTestHelper.editReport(reportPayload);
		//Then
		softAssert.assertEquals("OK", editedReport.getStatus(), "getStatus issue");
		softAssert.assertEquals("Updated", editedReport.getMessage(), "getMessage issue");
		softAssert.assertEquals(editedReport.getContent().getOriginId(), expectedReport.getContent().getOriginId(), "getOriginId issue");
		softAssert.assertEquals(editedReport.getContent().getTitle(), expectedReport.getContent().getTitle(), "getTitle issue");
		softAssert.assertEquals(editedReport.getContent().getStatus(), expectedReport.getContent().getStatus(), "getStatus issue");
		softAssert.assertEquals(editedReport.getContent().getClientName(), new GlowDBHelper().getClientNameFromProjectId(expectedReport.getContent().getOriginId()), "getClientName issue");
		softAssert.assertAll();
	}
}
