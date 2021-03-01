package tests.testcases.submodules.manage.features.configureAmount;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.manage.features.ConfigureAmountDataProviders;
import dto.submodules.manage.configureAmount.getSafeMatrixRolDetails.SafeMatrixRolDetailsResponseDTO;
import dto.submodules.manage.configureAmount.getSafeMatrixRoles.Content;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.configureAmount.GetRolDetailTestHelper;
import tests.testhelpers.submodules.manage.features.configureAmount.GetSafeMatrixRolTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class GetSafeMatrixRolDetailTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetSafeMatrixRolDetailTests");
	}

	/**
	 * Goal: Check that is feasible to perform a GET safe matrix rol details. 
	 * @param user
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(dataProvider = "safeMatrixAdminRol", dataProviderClass = ConfigureAmountDataProviders.class, groups = {ExeGroups.Sanity})
	public void getSafeMatrixRoleDetailTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		GetSafeMatrixRolTestHelper rolHelper = new GetSafeMatrixRolTestHelper(user);
		Content rol = rolHelper.getRandomRol();
		GetRolDetailTestHelper detailHelper = new GetRolDetailTestHelper(user);
		Response response = detailHelper.getRolDetail(rol);
		validateResponseToContinueTest(response, 200, "Get safe matrix role api call was not successful.", true);
		SafeMatrixRolDetailsResponseDTO rolDetails = response.as(SafeMatrixRolDetailsResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(rolDetails.getStatus(), "Success", "getStatus issue");
		softAssert.assertEquals(rolDetails.getMessage(), "OK", "getMessage issue");
		softAssert.assertEquals(rolDetails.getContent().getRole().getId(), rol.getId(), "getSafeMatrixRolId issue");
		softAssert.assertAll();
	}
	
}
