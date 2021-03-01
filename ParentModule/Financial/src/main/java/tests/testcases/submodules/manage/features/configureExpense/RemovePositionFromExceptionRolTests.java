package tests.testcases.submodules.manage.features.configureExpense;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.FinancialDataProviders;
import dto.submodules.manage.configureExpense.createExceptionRol.ExceptionRolResponseDTO;
import dto.submodules.manage.configureExpense.removePositionFromExceptionRol.RemovePositionFromExceptionRolResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import payloads.submodules.manage.features.configureExpense.PositionExceptionRolPayLoadHelper;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.configureExpense.AddPositionToExceptionRolTestHelper;
import tests.testhelpers.submodules.manage.features.configureExpense.CreateExceptionRolTestHelper;
import tests.testhelpers.submodules.manage.features.configureExpense.RemovePositionFromExceptionRolTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class RemovePositionFromExceptionRolTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("RemovePositionFromExceptionRolTests");
	}

	/**
	 * Goal: Check that is feasible to remove a position from an exception rol.
	 * @param userName
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(groups = {ExeGroups.Sanity}, dataProvider = "expensesExceptionsAdminRol", dataProviderClass = FinancialDataProviders.class)
	public void removePositionFromExceptionRolTest(String userName) throws Exception {
		CreateExceptionRolTestHelper createExceptionRolTestHelper = new CreateExceptionRolTestHelper(userName);
		RemovePositionFromExceptionRolTestHelper removePositionFromExceptionRolTestHelper = new RemovePositionFromExceptionRolTestHelper(userName);
		AddPositionToExceptionRolTestHelper addPositionToExceptionRolTestHelper = new AddPositionToExceptionRolTestHelper(userName);
		ExceptionRolResponseDTO createExceptionRolFromScratch = createExceptionRolTestHelper.createExceptionRolFromScratch();
		PositionExceptionRolPayLoadHelper positionExceptionRolPayLoad = new PositionExceptionRolPayLoadHelper(createExceptionRolFromScratch.getContent().getId());
		addPositionToExceptionRolTestHelper.addPositionToExceptionRol(positionExceptionRolPayLoad);
		Response response = removePositionFromExceptionRolTestHelper.removePositionFromExceptionRol(positionExceptionRolPayLoad);
		new ManageBaseTest().validateResponseToContinueTest(response, 200, "Remove position from Exception Rol api call was not successful.", true);
		RemovePositionFromExceptionRolResponseDTO removePositionFromExceptionRolResponseDTO = response.as(RemovePositionFromExceptionRolResponseDTO.class, ObjectMapperType.GSON);
		assertEquals(removePositionFromExceptionRolResponseDTO.getStatus(), "Success", "getStatus issue");
		assertEquals(removePositionFromExceptionRolResponseDTO.getMessage(), "OK", "getMessage issue");
		assertEquals(removePositionFromExceptionRolResponseDTO.getContent(), "DELETED", "getContent issue");
	}
}
