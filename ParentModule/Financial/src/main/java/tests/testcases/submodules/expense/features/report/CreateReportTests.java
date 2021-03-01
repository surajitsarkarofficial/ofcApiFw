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
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class CreateReportTests extends ExpenseBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("CreateReportTests");
	}
	
	/**
	 * Goal: Check that is feasible to create a new report.
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class, groups = {ExeGroups.Sanity})
	public void createReportTest(String userName) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		CreateReportTestHelper createNewReportTestHelper = new CreateReportTestHelper(userName);
		//Given
		ReportPayload reportPayload = createNewReportTestHelper.createBasicReportPayload();
		ReportDTO expectedReport = createNewReportTestHelper.convertAPayloadIntoAnReportDTO(reportPayload);
		//When
		ReportDTO createdReport = createNewReportTestHelper.createReport(reportPayload);
		//Then
		softAssert.assertEquals("OK", createdReport.getStatus(), "getStatus issue");
		softAssert.assertEquals("Created", createdReport.getMessage(), "getMessage issue");
		softAssert.assertEquals(createdReport.getContent().getOriginId(), expectedReport.getContent().getOriginId(), "getOriginId issue");
		softAssert.assertEquals(createdReport.getContent().getTitle(), expectedReport.getContent().getTitle(), "getTitle issue");
		softAssert.assertEquals(createdReport.getContent().getStatus(), expectedReport.getContent().getStatus(), "getStatus issue");
		softAssert.assertEquals(createdReport.getContent().getClientName(), new GlowDBHelper().getClientNameFromProjectId(expectedReport.getContent().getOriginId()), "getClientName issue");
		softAssert.assertAll();
	}	
}
