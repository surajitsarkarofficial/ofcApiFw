package tests.testcases.submodules.manage.features.configureAmount;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.manage.features.ConfigureAmountDataProviders;
import dto.submodules.manage.configureAmount.getPosition.Content;
import dto.submodules.manage.configureAmount.postPosition.PostPositionForSafeMatrixRolResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import payloads.submodules.manage.features.configureAmount.postPosition.PostPositionForRolPayLoadHelper;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.configureAmount.GetPositionForRolTestHelper;
import tests.testhelpers.submodules.manage.features.configureAmount.GetSafeMatrixRolTestHelper;
import tests.testhelpers.submodules.manage.features.configureAmount.PostPositionForRolTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class PostPositionForRolTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("PostPositionForSafeMatrixRolTests");
	}

	/**
	 * Goal: Check that is feasible add a position into a safe matrix role. 
	 * @param user
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(dataProvider = "safeMatrixAdminRol", dataProviderClass = ConfigureAmountDataProviders.class, groups = {ExeGroups.Sanity})
	public void postPositionForSafeMatrixRolTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		GetPositionForRolTestHelper positionHelper = new GetPositionForRolTestHelper(user);
		Content position = positionHelper.getRandomPosition();
		GetSafeMatrixRolTestHelper rolHelper = new GetSafeMatrixRolTestHelper(user);
		dto.submodules.manage.configureAmount.getSafeMatrixRoles.Content rol = rolHelper.getRandomRol();
		PostPositionForRolPayLoadHelper payload = new PostPositionForRolPayLoadHelper(position, rol);
		PostPositionForRolTestHelper postHelper = new PostPositionForRolTestHelper(user);
		Response response = postHelper.postPosition(payload);
		validateResponseToContinueTest(response, 200, "Post position for safe matrix role api call was not successful.", true);
		PostPositionForSafeMatrixRolResponseDTO role = response.as(PostPositionForSafeMatrixRolResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(role.getStatus(), "Success", "getStatus issue");
		softAssert.assertEquals(role.getMessage(), "OK", "getMessage issue");
		softAssert.assertEquals(role.getContent().getPosition(), payload.getPosition(), "getPosition issue");
		softAssert.assertAll();		
	}
}
