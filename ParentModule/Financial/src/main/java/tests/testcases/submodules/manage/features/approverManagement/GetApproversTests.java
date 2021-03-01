package tests.testcases.submodules.manage.features.approverManagement;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.manage.features.ApproverManagementDataProviders;
import dto.submodules.manage.approverManagement.getApprovers.GetApproversResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.manage.features.approverManagement.GetApproversParameters;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.approverManagement.GetApproversTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class GetApproversTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetApproversTests");
	}

	/**
	 * Goal: Check that is feasible to perform a GET approvers. 
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "globerRol", dataProviderClass = ApproverManagementDataProviders.class, groups = {ExeGroups.Sanity})
	public void getApproversTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		GetApproversTestHelper helper = new GetApproversTestHelper(user);
		GetApproversParameters parameters = new GetApproversParameters();
		Response response = helper.getApprovers(parameters);
		validateResponseToContinueTest(response, 200, "Get approvers api call was not successful.", true);
		GetApproversResponseDTO approvers = response.as(GetApproversResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(approvers.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(approvers.getMessage(), "Success", "getMessage issue");
		softAssert.assertEquals(approvers.getContent().getProjectId(), parameters.getProjectId(), "getProjectId issue");
		softAssert.assertAll();
	}
	
}
