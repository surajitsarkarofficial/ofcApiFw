package tests.testcases.submodules.manage.features.approverManagement;



import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import database.submodules.manage.ManageDBHelper;
import dataproviders.submodules.manage.features.ApproverManagementDataProviders;
import dto.submodules.manage.approverManagement.getAvailableApprovers.AvailableApprover;
import dto.submodules.manage.approverManagement.getAvailableApprovers.GetAvailableApproversResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.manage.features.approverManagement.GetAvailableApproversParameters;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.approverManagement.GetAvailableApproversTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class GetAvailableApproversTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetAvailableApproversTests");
	}

	/**
	 * Check that is feasible to perform a GET available approvers. 
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "randomRol", dataProviderClass = ApproverManagementDataProviders.class, groups = {ExeGroups.Sanity})
	public void getAvailableApproversTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		GetAvailableApproversTestHelper helper = new GetAvailableApproversTestHelper(user);
		GetAvailableApproversParameters parameters = new GetAvailableApproversParameters();
		Response response = helper.getAvailableApprovers(parameters);
		GetAvailableApproversResponseDTO approvers = response.as(GetAvailableApproversResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(approvers.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(approvers.getMessage(), "Success", "getMessage issue");
		softAssert.assertNotNull(approvers.getContent(), "getContent issue");
		softAssert.assertAll();
	}

	/**
	 * Check that the searcher is including globers without an assigned rol.
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "randomRol", dataProviderClass = ApproverManagementDataProviders.class)
	public void getAvailableApproversWithoutAssignedRolTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		GetAvailableApproversTestHelper helper = new GetAvailableApproversTestHelper(user);
		AvailableApprover approver = helper.getAvailableApproverWithoutAssignedRol(new ManageDBHelper().getRandomCeco());
		softAssert.assertEquals(approver.getMaxAmount(), "0", "getMaxAmount issue");
		softAssert.assertEquals(approver.getRole(), "Non Role Assigned", "getMessage issue");
		softAssert.assertNotNull(approver.getPosition(), "getPosition issue");
		softAssert.assertNotNull(approver.getUserName(), "getUserName issue");
		softAssert.assertNotNull(approver.getCompleteName(), "getCompleteName issue");
		softAssert.assertAll();
	}
	
	/**
	 * Check that is feasible to perform a GET available approvers sending a empty ceco number.
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "randomRol", dataProviderClass = ApproverManagementDataProviders.class
			, groups = { ExeGroups.NotAvailableInPreProd })
	public void getAvailableApproversForAllCecosTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		GetAvailableApproversTestHelper helper = new GetAvailableApproversTestHelper(user);
		GetAvailableApproversParameters parameters = new GetAvailableApproversParameters();
		parameters.setCecoNumber("");
		Response response = helper.getAvailableApprovers(parameters);
		GetAvailableApproversResponseDTO approvers = response.as(GetAvailableApproversResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(approvers.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(approvers.getMessage(), "Success", "getMessage issue");
		softAssert.assertNotNull(approvers.getContent(), "getContent issue");
		softAssert.assertAll();
	}
}
