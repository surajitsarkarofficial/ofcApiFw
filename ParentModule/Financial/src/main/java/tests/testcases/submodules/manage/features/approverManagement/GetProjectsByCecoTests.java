package tests.testcases.submodules.manage.features.approverManagement;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.manage.features.ApproverManagementDataProviders;
import dto.submodules.manage.approverManagement.getProjectsByCeco.GetProjectsByCecoResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.manage.features.approverManagement.getCecos.NonExistentCecoNumber;
import parameters.submodules.manage.features.approverManagement.getProjectsByCeco.CecoWithoutRelatedProjects;
import parameters.submodules.manage.features.approverManagement.getProjectsByCeco.GetProjectsByCecoParameters;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.approverManagement.GetProjectsByCecoTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class GetProjectsByCecoTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetProjectsByCecoTests");
	}

	/**
	 * Check that is feasible to perform a GET projects by ceco. 
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "randomRol", dataProviderClass = ApproverManagementDataProviders.class, groups = {ExeGroups.Sanity})
	public void getProjectsByCecoTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		GetProjectsByCecoTestHelper helper = new GetProjectsByCecoTestHelper(user);
		GetProjectsByCecoParameters parameters = new GetProjectsByCecoParameters();
		Response response = helper.getProjectsByCeco(parameters);
		validateResponseToContinueTest(response, 200, "Get projects by ceco api call was not successful.", true);
		GetProjectsByCecoResponseDTO cecos = response.as(GetProjectsByCecoResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(cecos.getStatus(), "Success", "getStatus issue");
		softAssert.assertEquals(cecos.getMessage(), "OK", "getMessage issue");
		softAssert.assertEquals(cecos.getContent().getCecoProjects().get(0).getStatus(), "ON_GOING", "getProjectStatus issue");
		softAssert.assertNotNull(cecos.getContent(), "getContent issue");
		softAssert.assertAll();
	}
	
	/**
	 * Perform a GET ceco without related projects and check the message.
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "randomRol", dataProviderClass = ApproverManagementDataProviders.class)
	public void getCecoWithoutRelatedProjectTest(String user) throws Exception {
		GetProjectsByCecoTestHelper helper = new GetProjectsByCecoTestHelper(user);
		GetProjectsByCecoParameters parameters = new GetProjectsByCecoParameters(new CecoWithoutRelatedProjects());
		Response response = helper.getProjectsByCeco(parameters);
		validateResponseToContinueTest(response, 204, "Get projects by ceco api call was not successful.", true);
	}

	/**
	 * Perform a GET non existent ceco and check the message. 
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "randomRol", dataProviderClass = ApproverManagementDataProviders.class)
	public void getProjectsByNonExistentCecoTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		GetProjectsByCecoTestHelper helper = new GetProjectsByCecoTestHelper(user);
		GetProjectsByCecoParameters parameters = new GetProjectsByCecoParameters(new NonExistentCecoNumber());
		Response response = helper.getProjectsByCeco(parameters);
		validateResponseToContinueTest(response, 400, "Get projects by ceco api call was not successful.", true);
		GetProjectsByCecoResponseDTO cecos = response.as(GetProjectsByCecoResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(cecos.getStatus(), "Bad Request", "getStatus issue");
		softAssert.assertEquals(cecos.getMessage(), "CECO not found by number: "+parameters.getCecoNumber(), "getMessage issue");
		softAssert.assertNull(cecos.getContent(), "getContent issue");
		softAssert.assertAll();
	}

}
