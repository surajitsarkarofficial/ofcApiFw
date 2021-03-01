package tests.testcases.submodules.manage.features.configureExpense;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.FinancialDataProviders;
import dto.submodules.manage.configureExpense.getExceptionRol.GetExceptionRolResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.configureExpense.GetExceptionRolTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class GetExceptionRolTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetExceptionRolTests");
	}

	/**
	 * Goal: Check that is feasible to perform a GET exceptions rol.
	 * This test check that response json schema is the expected.
	 * @param userName
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(groups = {ExeGroups.Sanity}, dataProvider = "expensesExceptionsAdminRol", dataProviderClass = FinancialDataProviders.class)
	public void getExceptionRolTest(String userName) throws Exception {
		GetExceptionRolTestHelper getExceptionRolTestHelper = new GetExceptionRolTestHelper(userName);
		Response response = getExceptionRolTestHelper.getExceptionRol();
		new ManageBaseTest().validateResponseToContinueTest(response, 200, "Get Exception api call was not successful.", true);
		GetExceptionRolResponseDTO getExceptionRolResponseDTO = response.as(GetExceptionRolResponseDTO.class, ObjectMapperType.GSON);
		assertEquals(getExceptionRolResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(getExceptionRolResponseDTO.getMessage(), "Success", "getMessage issue");
		assertNotNull(getExceptionRolResponseDTO.getContent(), "getContent issue");
	}
}
