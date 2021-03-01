package tests.testcases.submodules.manage.features.configureAmount;



import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.manage.features.ConfigureAmountDataProviders;
import dto.submodules.manage.configureAmount.rol.NonExistingRol;
import dto.submodules.manage.configureAmount.rol.RolResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.configureAmount.DeleteRolTestHelper;
import tests.testhelpers.submodules.manage.features.configureAmount.PostSafeMatrixRolTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class DeleteRolTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("DeleteRolTests");
	}

	/**
	 * Check that is feasible delete a rol.
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "safeMatrixAdminRol", dataProviderClass = ConfigureAmountDataProviders.class, groups = {ExeGroups.Sanity})
	public void deleteRolTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		RolResponseDTO rol = new PostSafeMatrixRolTestHelper(user).postSafeMatrixRolFromScratch();
		Response response = new DeleteRolTestHelper(user).deleteRol(rol);
		validateResponseToContinueTest(response, 200, "Delete role api call was not successful.", true);
		RolResponseDTO role = response.as(RolResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(role.getStatus(), "Success", "getStatus issue");
		softAssert.assertEquals(role.getMessage(), "OK", "getMessage issue");
		softAssert.assertEquals(role.getContent().getStatus(), "INACTIVE", "rol status issue");
		softAssert.assertAll();
	}

	/**
	 * Check the message when we try to delete an non-existing rol.
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "safeMatrixAdminRol", dataProviderClass = ConfigureAmountDataProviders.class)
	public void deleteAnUnExistingRol(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		RolResponseDTO rol = new RolResponseDTO(new NonExistingRol());
		Response response = new DeleteRolTestHelper(user).deleteRol(rol);
		validateResponseToContinueTest(response, 400, "Delete role api call was not successful.", true);
		RolResponseDTO role = response.as(RolResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(role.getStatus(), "Bad Request", "getStatus issue");
		softAssert.assertEquals(role.getMessage(), "The selected Role doesn't exist: {}", "getMessage issue");
		softAssert.assertAll();
	}

	/**
	 * Check the message when we try to delete the same rol twice.
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "safeMatrixAdminRol", dataProviderClass = ConfigureAmountDataProviders.class)
	public void deleteTheSameRolTwiceTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		RolResponseDTO rol = new PostSafeMatrixRolTestHelper(user).postSafeMatrixRolFromScratch();
		Response response = new DeleteRolTestHelper(user).deleteRol(rol);
		response = new DeleteRolTestHelper(user).deleteRol(rol);
		validateResponseToContinueTest(response, 400, "Delete role api call was not successful.", true);
		RolResponseDTO role = response.as(RolResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(role.getStatus(), "Bad Request", "getStatus issue");
		softAssert.assertEquals(role.getMessage(), "The selected Role doesn't exist: {}", "getMessage issue");
		softAssert.assertTrue((role.getContent()==null), "rol content issue");
		softAssert.assertAll();
	}
}
