package tests.testcases.submodules.manage.features.configureAmount;



import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.manage.features.ConfigureAmountDataProviders;
import dto.submodules.manage.configureAmount.getPosition.Content;
import dto.submodules.manage.configureAmount.rol.RolResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import payloads.submodules.manage.features.configureAmount.rol.RolPayLoadHelper;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.configureAmount.GetPositionForRolTestHelper;
import tests.testhelpers.submodules.manage.features.configureAmount.PostSafeMatrixRolTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class PostSafeMatrixRoleTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("PostSafeMatrixRoleTests");
	}

	/**
	 * Goal: Check that is feasible to create a safe matrix role. 
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "safeMatrixAdminRol", dataProviderClass = ConfigureAmountDataProviders.class, groups = {ExeGroups.Sanity})
	public void postSafeMatrixRol(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		GetPositionForRolTestHelper positionHelper = new GetPositionForRolTestHelper(user);
		Content position = positionHelper.getRandomPosition();
		RolPayLoadHelper payload = new RolPayLoadHelper(position);
		PostSafeMatrixRolTestHelper postHelper = new PostSafeMatrixRolTestHelper(user);
		Response response = postHelper.postSafeMatrixRol(payload);
		validateResponseToContinueTest(response, 201, "Post rol api call was not successful.", true);
		RolResponseDTO rol = response.as(RolResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(rol.getStatus(), "Success", "getStatus issue");
		softAssert.assertEquals(rol.getMessage(), "OK", "getMessage issue");
		softAssert.assertEquals(rol.getContent().getName(), payload.getName(), "getName issue");
		softAssert.assertEquals(rol.getContent().getMaxAmount(), payload.getMaxAmount(), "getMaxAmount issue");
		softAssert.assertEquals(rol.getContent().getListPositions().get(0).getRole(), payload.getName(), "getRoleName issue");
		softAssert.assertEquals(rol.getContent().getListPositions().get(0).getPosition(), payload.getListPositions().get(0).getPosition(), "getPosition issue");
		softAssert.assertAll();		
	}
	
	
}
