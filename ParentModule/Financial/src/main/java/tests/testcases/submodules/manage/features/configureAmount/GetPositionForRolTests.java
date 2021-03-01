package tests.testcases.submodules.manage.features.configureAmount;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.manage.features.ConfigureAmountDataProviders;
import dto.submodules.manage.configureAmount.getPosition.GetPositionForSafeMatrixRolResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.configureAmount.GetPositionForRolTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class GetPositionForRolTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetPositionForSafeMatrixRolTests");
	}
	
	/**
	 * Goal: Check that is feasible to perform a GET positions for safe matrix rol. 
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "safeMatrixAdminRol", dataProviderClass = ConfigureAmountDataProviders.class, groups = {ExeGroups.Sanity})
	public void getPositionForSafeMatrixRolTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		GetPositionForRolTestHelper helper = new GetPositionForRolTestHelper(user);
		Response response = helper.getPosition();
		validateResponseToContinueTest(response, 200, "Get safe matrix role api call was not successful.", true);
		GetPositionForSafeMatrixRolResponseDTO positions = response.as(GetPositionForSafeMatrixRolResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(positions.getStatus(), "Success", "getStatus issue");
		softAssert.assertEquals(positions.getMessage(), "OK", "getMessage issue");
		softAssert.assertNotNull(positions.getContent(), "getContent issue");
		softAssert.assertAll();
	}
	
}
