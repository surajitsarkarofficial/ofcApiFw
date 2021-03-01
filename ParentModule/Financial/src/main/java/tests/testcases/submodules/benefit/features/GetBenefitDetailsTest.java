package tests.testcases.submodules.benefit.features;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.benefit.features.GetMyBenefitDataProviders;
import dto.submodules.expense.report.getDetails.GetExpenseReportDetailsResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.expense.features.getReports.GetExpenseReportDetailsParameters;
import tests.testcases.submodules.benefit.BenefitBaseTest;
import tests.testhelpers.submodules.expense.features.report.GetExpenseReportDetailsTestHelper;

/**
 * @author german.massello
 *
 */
public class GetBenefitDetailsTest extends BenefitBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetBenefitDetailsTest");
	}

	/**
	 * Check that is feasible to perform a GET a benefit report details. 
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "getMyReportDetails", dataProviderClass = GetMyBenefitDataProviders.class)
	public void getBenefitReportDetailsTest(GetExpenseReportDetailsParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		GetExpenseReportDetailsTestHelper helper = new GetExpenseReportDetailsTestHelper(parameters.getUserName());
		Response response = helper.getExpenseReportDetails(parameters);
		GetExpenseReportDetailsResponseDTO reportDetails = response.as(GetExpenseReportDetailsResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(reportDetails.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(reportDetails.getMessage(), "Success", "getMessage issue");
		softAssert.assertEquals(reportDetails.getContent().getClientName(), parameters.getClientName(), "getClientName issue");
		softAssert.assertAll();
	}

}
