package tests.testcases.submodules.manage.features.configureAmount;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.manage.features.ConfigureAmountDataProviders;
import dto.submodules.manage.configureAmount.deletePosition.DeletePositionForSafeMatrixRolResponseDTO;
import dto.submodules.manage.configureAmount.postPosition.PostPositionForSafeMatrixRolResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.configureAmount.DeletePositionForRolTestHelper;
import tests.testhelpers.submodules.manage.features.configureAmount.PostPositionForRolTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class DeletePositionForRolTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("DeletePositionForSafeMatrixRolTests");
	}

	/**
	 * Goal: Check that is feasible delete a position from a safe matrix role. 
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "safeMatrixAdminRol", dataProviderClass = ConfigureAmountDataProviders.class, groups = {ExeGroups.Sanity})
	public void deletePositionForSafeMatrixRolTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		PostPositionForRolTestHelper postHelper = new PostPositionForRolTestHelper(user);
		PostPositionForSafeMatrixRolResponseDTO positionRol = postHelper.postPositionFromScratch();
		DeletePositionForRolTestHelper deleteHelper = new DeletePositionForRolTestHelper(user);
		Response response = deleteHelper.deletePosition(positionRol);
		validateResponseToContinueTest(response, 200, "Delete position for safe matrix role api call was not successful.", true);
		DeletePositionForSafeMatrixRolResponseDTO deleteResponse = response.as(DeletePositionForSafeMatrixRolResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(deleteResponse.getStatus(), "Success", "getStatus issue");
		softAssert.assertEquals(deleteResponse.getMessage(), "OK", "getMessage issue");
		softAssert.assertEquals(deleteResponse.getContent().getId(), positionRol.getContent().getId(), "getId issue");
		softAssert.assertAll();		
	}
	
}
