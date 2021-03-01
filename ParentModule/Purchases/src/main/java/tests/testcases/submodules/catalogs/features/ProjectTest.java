package tests.testcases.submodules.catalogs.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.PurchasesDataProviders;
import dto.submodules.project.ProjectDTOList;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import tests.testHelper.submodules.project.ProjectTestHelper;
import tests.testcases.submodules.catalogs.CatalogsBaseTest;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class ProjectTest extends CatalogsBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("ProjectTest");
	}

	/**
	 * Goal: Check that is feasible to perform a GET projects. 
	 * @param userName
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(groups = {ExeGroups.Sanity}, dataProvider = "serviceDeskUser", dataProviderClass = PurchasesDataProviders.class)
	public void getProjectTest(String userName) throws Exception {
		Response response = new ProjectTestHelper(userName).getProjects();
		validateResponseToContinueTest(response, 200, "Get project api call was not successful.", true);
		ProjectDTOList projectDTOList = response.as(ProjectDTOList.class, ObjectMapperType.GSON);
		assertEquals(projectDTOList.getStatus(), "success", "getStatus issue");
		assertNotNull(projectDTOList.getDetails(), "getDetails issue");
	}
}
