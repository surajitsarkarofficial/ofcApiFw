package tests.testcases.submodules.manage.features.configureExpense;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.FinancialDataProviders;
import dto.submodules.manage.configureExpense.createExceptionRol.ExceptionRolResponseDTO;
import dto.submodules.manage.configureExpense.deleteExceptionRol.DeleteExceptionRolResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.configureExpense.CreateExceptionRolTestHelper;
import tests.testhelpers.submodules.manage.features.configureExpense.DeleteExceptionRolTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class DeleteExceptionRolTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("DeleteExceptionRolTests");
	}

	/**
	 * Goal: Check that is feasible to delete an exception rol.
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(groups = {ExeGroups.Sanity}, dataProvider = "expensesExceptionsAdminRol", dataProviderClass = FinancialDataProviders.class)
	public void deleteExceptionRolTest(String userName) throws Exception {
		CreateExceptionRolTestHelper createExceptionRolTestHelper = new CreateExceptionRolTestHelper(userName);
		DeleteExceptionRolTestHelper deleteExceptionRolTestHelper = new DeleteExceptionRolTestHelper(userName);
		ExceptionRolResponseDTO createExceptionRolFromScratch = createExceptionRolTestHelper.createExceptionRolFromScratch();
		Response response = deleteExceptionRolTestHelper.deleteExceptionRol(createExceptionRolFromScratch);
		new ManageBaseTest().validateResponseToContinueTest(response, 200, "Delete Exception api call was not successful.", true);
		DeleteExceptionRolResponseDTO deleteExceptionRolResponseDTO = response.as(DeleteExceptionRolResponseDTO.class, ObjectMapperType.GSON);
		assertEquals(deleteExceptionRolResponseDTO.getStatus(), "Success", "getStatus issue");
		assertEquals(deleteExceptionRolResponseDTO.getMessage(), "OK", "getMessage issue");
		assertEquals(deleteExceptionRolResponseDTO.getContent(), "DELETED", "getContent issue");
	}	
}
