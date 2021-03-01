package tests.testcases.submodules.manage.features.configureExpense;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.FinancialDataProviders;
import dto.submodules.manage.configureExpense.createExceptionRol.ExceptionRolResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import payloads.submodules.manage.features.configureExpense.ExceptionRolPayLoadHelper;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.configureExpense.CreateExceptionRolTestHelper;
import tests.testhelpers.submodules.manage.features.configureExpense.EditExceptionRolTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class EditExceptionRolTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("EditExceptionRolTests");
	}

	/**
	 * Goal: Check that is feasible to edit an exception rol.
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(groups = {ExeGroups.Sanity}, dataProvider = "expensesExceptionsAdminRol", dataProviderClass = FinancialDataProviders.class)
	public void editExceptionRolTest(String userName) throws Exception {
		CreateExceptionRolTestHelper createExceptionRolTestHelper = new CreateExceptionRolTestHelper(userName);
		EditExceptionRolTestHelper editExceptionRolTestHelper = new EditExceptionRolTestHelper(userName);
		ExceptionRolResponseDTO createExceptionRolFromScratch = createExceptionRolTestHelper.createExceptionRolFromScratch();
		ExceptionRolPayLoadHelper editExceptionRolPayLoad = new ExceptionRolPayLoadHelper(createExceptionRolFromScratch.getContent().getId());
		Response response = editExceptionRolTestHelper.editExceptionRol(editExceptionRolPayLoad);
		new ManageBaseTest().validateResponseToContinueTest(response, 200, "Edit Exception Rol api call was not successful.", true);
		ExceptionRolResponseDTO editExceptionRolResponseDTO = response.as(ExceptionRolResponseDTO.class, ObjectMapperType.GSON);
		assertEquals(editExceptionRolResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(editExceptionRolResponseDTO.getMessage(), "Updated", "getMessage issue");
		assertEquals(editExceptionRolResponseDTO.getContent().getAmount(), editExceptionRolPayLoad.getAmount(), "getAmount issue");
		assertEquals(editExceptionRolResponseDTO.getContent().getName(), editExceptionRolPayLoad.getName(), "getName issue");
	}
}
