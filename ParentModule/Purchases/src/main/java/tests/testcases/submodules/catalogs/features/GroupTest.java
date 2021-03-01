package tests.testcases.submodules.catalogs.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.PurchasesDataProviders;
import dto.submodules.catalogs.group.GroupResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import tests.testcases.submodules.catalogs.CatalogsBaseTest;
import tests.testhelpers.submodules.catalogs.features.GroupTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class GroupTest extends CatalogsBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("CountryTest");
	}
	
	/**
	 * Goal: Check that is feasible to perform a GET groups. 
	 * @param userName
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(groups = {ExeGroups.Sanity}, dataProvider = "serviceDeskUser", dataProviderClass = PurchasesDataProviders.class)
	public void getGroupTest(String userName) throws Exception {
		Response response = new GroupTestHelper(userName).getGroups();
		validateResponseToContinueTest(response, 200, "Get groups api call was not successful.", true);
		GroupResponseDTO groupResponseDTO = response.as(GroupResponseDTO.class, ObjectMapperType.GSON);
		assertEquals(groupResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(groupResponseDTO.getMessage(), "OK", "getMessage issue");
		assertNotNull(groupResponseDTO.getContent(), "getContent issue");
	}

}
