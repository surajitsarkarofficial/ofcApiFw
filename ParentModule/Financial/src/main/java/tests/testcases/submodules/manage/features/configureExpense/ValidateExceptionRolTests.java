package tests.testcases.submodules.manage.features.configureExpense;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.FinancialDataProviders;
import dto.submodules.manage.configureExpense.validateExceptionRol.ValidateExceptionRolResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.configureExpense.ValidateExceptionRolTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class ValidateExceptionRolTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("ValidateExceptionRolTests");
	}

	/**
	 * Goal: Check the json schema of the GET validate exception rol.
	 * @param userName
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(groups = {ExeGroups.Sanity}, dataProvider = "expensesExceptionsAdminRol", dataProviderClass = FinancialDataProviders.class)
	public void validateExceptionRolTest(String userName) throws Exception {
		ValidateExceptionRolTestHelper validateExceptionRolTestHelper = new ValidateExceptionRolTestHelper(userName);
		Response response = validateExceptionRolTestHelper.validateExceptionRol();
		new ManageBaseTest().validateResponseToContinueTest(response, 200, "Get validate exception rol api call was not successful.", true);
		ValidateExceptionRolResponseDTO validateExceptionRolResponseDTO = response.as(ValidateExceptionRolResponseDTO.class, ObjectMapperType.GSON);
		assertEquals(validateExceptionRolResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(validateExceptionRolResponseDTO.getMessage(), "Success", "getMessage issue");
		assertNotNull(validateExceptionRolResponseDTO.getContent(), "getContent issue");
	}
	
}
