package tests.testcases.submodules.manage.features.approverManagement;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.manage.features.ApproverManagementDataProviders;
import dto.submodules.manage.approverManagement.deleteCeco.DeleteCecoResponseDTO;
import dto.submodules.manage.approverManagement.postCeco.PostCecoResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.manage.features.approverManagement.DeleteCecoParameters;
import parameters.submodules.manage.features.approverManagement.getCecos.NonExistentCecoNumber;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.approverManagement.DeleteCecoTestHelper;
import tests.testhelpers.submodules.manage.features.approverManagement.PostCecoTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class DeleteCecoTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("DeleteCecoTests");
	}

	/**
	 * Check that is feasible to perform a DELETE a ceco.
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "randomRol", dataProviderClass = ApproverManagementDataProviders.class, groups = {ExeGroups.Sanity})
	public void deleteCecoTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		PostCecoResponseDTO ceco = new PostCecoTestHelper(user).postCecoFromScratch();
		DeleteCecoParameters parameters = new DeleteCecoParameters(ceco);
		Response response = new DeleteCecoTestHelper(user).deleteCeco(parameters);
		validateResponseToContinueTest(response, 200, "Delete ceco api call was not successful.", true);
		DeleteCecoResponseDTO approvers = response.as(DeleteCecoResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(approvers.getStatus(), "Success", "getStatus issue");
		softAssert.assertEquals(approvers.getMessage(), "OK", "getMessage issue");
		softAssert.assertEquals(approvers.getContent().getCecoNumber(), parameters.getCecoNumber(), "getCecoNumber issue");
		softAssert.assertAll(); 
	}

	/**
	 * Perform a DELETE ceco twice and check the message.
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "randomRol", dataProviderClass = ApproverManagementDataProviders.class)
	public void deleteCecoTwiceTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		PostCecoResponseDTO ceco = new PostCecoTestHelper(user).postCecoFromScratch();
		DeleteCecoParameters parameters = new DeleteCecoParameters(ceco);
		Response response = new DeleteCecoTestHelper(user).deleteCeco(parameters);
		response = new DeleteCecoTestHelper(user).deleteCeco(parameters);
		validateResponseToContinueTest(response, 400, "Delete ceco api call was not successful.", true);
		DeleteCecoResponseDTO approvers = response.as(DeleteCecoResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(approvers.getStatus(), "Bad Request", "getStatus issue");
		softAssert.assertEquals(approvers.getMessage(), "CECO is already inactivate", "getMessage issue");
		softAssert.assertNull(approvers.getContent(), "getContent issue");
		softAssert.assertAll(); 
	}
	
	/**
	 * Perform a DELETE non-existent ceco and check the message.
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "randomRol", dataProviderClass = ApproverManagementDataProviders.class)
	public void deleteNonExistentCecoTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		NonExistentCecoNumber ceco = new NonExistentCecoNumber();
		DeleteCecoParameters parameters = new DeleteCecoParameters(ceco);
		Response response = new DeleteCecoTestHelper(user).deleteCeco(parameters);
		validateResponseToContinueTest(response, 400, "Delete ceco api call was not successful.", true);
		DeleteCecoResponseDTO approvers = response.as(DeleteCecoResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(approvers.getStatus(), "Bad Request", "getStatus issue");
		softAssert.assertEquals(approvers.getMessage(), "CECO not found by number: "+ceco.getNonExistentCecoNumber(), "getMessage issue");
		softAssert.assertNull(approvers.getContent(), "getContent issue");
		softAssert.assertAll(); 
	}

}
