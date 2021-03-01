package tests.testcases.submodules.manage.features.configureAmount;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.manage.features.ConfigureAmountDataProviders;
import dto.submodules.manage.configureAmount.getSafeMatrixRoles.SafeMatrixRolesResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.configureAmount.GetSafeMatrixRolTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class GetSafeMatrixRoleTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetSafeMatrixRoleTests");
	}

	/**
	 * Goal: Check that is feasible to perform a GET safe matrix role. 
	 * @param user
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(dataProvider = "safeMatrixAdminRol", dataProviderClass = ConfigureAmountDataProviders.class, groups = {ExeGroups.Sanity})
	public void getSafeMatrixRoleTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		GetSafeMatrixRolTestHelper rolHelper = new GetSafeMatrixRolTestHelper(user);
		Response response = rolHelper.getRoles();
		validateResponseToContinueTest(response, 200, "Get safe matrix role api call was not successful.", true);
		SafeMatrixRolesResponseDTO role = response.as(SafeMatrixRolesResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(role.getStatus(), "Success", "getStatus issue");
		softAssert.assertEquals(role.getMessage(), "OK", "getMessage issue");
		softAssert.assertNotNull(role.getContent(), "getContent issue");
		softAssert.assertAll();
	}
	
}
