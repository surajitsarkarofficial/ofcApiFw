package tests.testcases.submodules.manage.features.configureExpense;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.FinancialDataProviders;
import dto.submodules.manage.configureExpense.createExceptionRol.ExceptionRolResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import payloads.submodules.manage.features.configureExpense.ExceptionRolPayLoadHelper;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.configureExpense.CreateExceptionRolTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class CreateExceptionRolTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("CreateExceptionRolTests");
	}
	
	/**
	 * Goal: Check that is feasible to create an exception rol.
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(groups = {ExeGroups.Sanity}, dataProvider = "expensesExceptionsAdminRol", dataProviderClass = FinancialDataProviders.class)
	public void createExceptionRolTest(String userName) throws Exception {
		CreateExceptionRolTestHelper createExceptionRolTestHelper = new CreateExceptionRolTestHelper(userName);
		ExceptionRolPayLoadHelper createExceptionRolPayLoad = new ExceptionRolPayLoadHelper();
		Response response = createExceptionRolTestHelper.createExceptionRol(createExceptionRolPayLoad);
		new ManageBaseTest().validateResponseToContinueTest(response, 201, "Create Exception api call was not successful.", true);
		ExceptionRolResponseDTO createExceptionRolResponseDTO = response.as(ExceptionRolResponseDTO.class, ObjectMapperType.GSON);
		assertEquals(createExceptionRolResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(createExceptionRolResponseDTO.getMessage(), "Created", "getMessage issue");
		assertNotNull(createExceptionRolResponseDTO.getContent(), "getContent issue");
	}
	
}
