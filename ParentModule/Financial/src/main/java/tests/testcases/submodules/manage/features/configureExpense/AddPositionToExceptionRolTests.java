package tests.testcases.submodules.manage.features.configureExpense;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.FinancialDataProviders;
import dto.submodules.manage.configureExpense.addPositionToExceptionRol.AddPositionToExceptionRolResponseDTO;
import dto.submodules.manage.configureExpense.createExceptionRol.ExceptionRolResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import payloads.submodules.manage.features.configureExpense.PositionExceptionRolPayLoadHelper;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.configureExpense.AddPositionToExceptionRolTestHelper;
import tests.testhelpers.submodules.manage.features.configureExpense.CreateExceptionRolTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class AddPositionToExceptionRolTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("AddPositionToExceptionRolTests");
	}

	/**
	 * Goal: Check that is feasible to add a position to an exception rol.
	 * @param userName
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(groups = {ExeGroups.Sanity}, dataProvider = "expensesExceptionsAdminRol", dataProviderClass = FinancialDataProviders.class)
	public void addPositionToExceptionRolTest(String userName) throws Exception {
		CreateExceptionRolTestHelper createExceptionRolTestHelper = new CreateExceptionRolTestHelper(userName);
		AddPositionToExceptionRolTestHelper addPositionToExceptionRolTestHelper = new AddPositionToExceptionRolTestHelper(userName);
		ExceptionRolResponseDTO createExceptionRolFromScratch = createExceptionRolTestHelper.createExceptionRolFromScratch();
		PositionExceptionRolPayLoadHelper addPositionToExceptionRolPayLoad = new PositionExceptionRolPayLoadHelper(createExceptionRolFromScratch.getContent().getId());
		Response response = addPositionToExceptionRolTestHelper.addPositionToExceptionRol(addPositionToExceptionRolPayLoad);
		new ManageBaseTest().validateResponseToContinueTest(response, 200, "Add position to Exception Rol api call was not successful.", true);
		AddPositionToExceptionRolResponseDTO addPositionToExceptionRolResponseDTO = response.as(AddPositionToExceptionRolResponseDTO.class, ObjectMapperType.GSON);
		assertEquals(addPositionToExceptionRolResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(addPositionToExceptionRolResponseDTO.getMessage(), "Updated", "getMessage issue");
		assertEquals(addPositionToExceptionRolResponseDTO.getContent().getRoleId(), addPositionToExceptionRolPayLoad.getRoleId(), "getRoleId issue");
		assertEquals(addPositionToExceptionRolResponseDTO.getContent().getPositionId(), addPositionToExceptionRolPayLoad.getPositionId(), "getPositionId issue");
	}

}
