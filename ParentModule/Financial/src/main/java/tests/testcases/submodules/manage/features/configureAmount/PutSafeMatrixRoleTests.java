package tests.testcases.submodules.manage.features.configureAmount;



import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.manage.features.ConfigureAmountDataProviders;
import dto.submodules.manage.configureAmount.rol.RolResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import payloads.submodules.manage.features.configureAmount.rol.RolPayLoadHelper;
import payloads.submodules.manage.features.configureAmount.rol.SameAmount;
import payloads.submodules.manage.features.configureAmount.rol.SameName;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.configureAmount.PostSafeMatrixRolTestHelper;
import tests.testhelpers.submodules.manage.features.configureAmount.PutSafeMatrixRolTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class PutSafeMatrixRoleTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("PutSafeMatrixRoleTests");
	}

	/**
	 * Check that is feasible update a role. 
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "safeMatrixAdminRol", dataProviderClass = ConfigureAmountDataProviders.class, groups = {ExeGroups.Sanity})
	public void editRolTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		RolResponseDTO rol = new PostSafeMatrixRolTestHelper(user).postSafeMatrixRolFromScratch();
		RolPayLoadHelper payload = new RolPayLoadHelper(rol);
		Response response = new PutSafeMatrixRolTestHelper(user).putRol(payload);
		validateResponseToContinueTest(response, 200, "Put safe matrix role api call was not successful.", true);
		RolResponseDTO role = response.as(RolResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(role.getStatus(), "Success", "getStatus issue");
		softAssert.assertEquals(role.getMessage(), "OK", "getMessage issue");
		softAssert.assertEquals(role.getContent().getMaxAmount(), payload.getMaxAmount(), "getMaxAmount issue");
		softAssert.assertEquals(role.getContent().getName(), payload.getName(), "getName issue");
		softAssert.assertAll();
	}
	
	/**
	 * Check that is not feasible update a role with an existing name.
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "safeMatrixAdminRol", dataProviderClass = ConfigureAmountDataProviders.class)
	public void editRolSameNameTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		RolResponseDTO rol = new PostSafeMatrixRolTestHelper(user).postSafeMatrixRolFromScratch();
		RolPayLoadHelper payload = new RolPayLoadHelper(rol, new SameName());
		Response response = new PutSafeMatrixRolTestHelper(user).putRol(payload);
		validateResponseToContinueTest(response, 400, "Put safe matrix role api call was not successful.", true);
		RolResponseDTO role = response.as(RolResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(role.getStatus(), "Bad Request", "getStatus issue");
		softAssert.assertEquals(role.getMessage(), "The name already exists.", "getMessage issue");
		softAssert.assertNull(role.getContent(), "getContent issue");
		softAssert.assertAll();
	}
	
	/**
	 * Check that is not feasible update a role with an existing amount.
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "safeMatrixAdminRol", dataProviderClass = ConfigureAmountDataProviders.class)
	public void editRolSameAmountTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		RolResponseDTO rol = new PostSafeMatrixRolTestHelper(user).postSafeMatrixRolFromScratch();
		RolPayLoadHelper payload = new RolPayLoadHelper(rol, new SameAmount());
		Response response = new PutSafeMatrixRolTestHelper(user).putRol(payload);
		validateResponseToContinueTest(response, 400, "Put safe matrix role api call was not successful.", true);
		RolResponseDTO role = response.as(RolResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(role.getStatus(), "Bad Request", "getStatus issue");
		softAssert.assertEquals(role.getMessage(), "The amount already exists.", "getMessage issue");
		softAssert.assertNull(role.getContent(), "getContent issue");
		softAssert.assertAll();
	}
	
}
