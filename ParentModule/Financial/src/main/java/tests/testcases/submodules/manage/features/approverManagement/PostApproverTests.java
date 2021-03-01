package tests.testcases.submodules.manage.features.approverManagement;



import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.manage.features.ApproverManagementDataProviders;
import dto.submodules.manage.approverManagement.postApprover.ApproversResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import payloads.submodules.manage.features.approverManagement.ApproverPayLoadHelper;
import payloads.submodules.manage.features.approverManagement.ApproverWithoutRol;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.approverManagement.PostApproverTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

public class PostApproverTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("PostApproverTests");
	}

	 /**
	 * Check that is feasible to add approver to a ceco. 
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "randomRol", dataProviderClass = ApproverManagementDataProviders.class, groups = {ExeGroups.Sanity})
	   public void addApproverTest(String user) throws Exception {
	      SoftAssert softAssert = new SoftAssert();
	      ApproverPayLoadHelper payload = new ApproverPayLoadHelper(user);
	      PostApproverTestHelper postHelper = new PostApproverTestHelper(user);
	      Response response = postHelper.postApprover(payload);
	      validateResponseToContinueTest(response, 200, "post approver api call was not successful.", true);
	      ApproversResponseDTO approvers = response.as(ApproversResponseDTO.class, ObjectMapperType.GSON);
	      softAssert.assertEquals(approvers.getStatus(), "OK", "getStatus issue");
	      softAssert.assertEquals(approvers.getMessage(), "Success", "getMessage issue");
	      softAssert.assertEquals(approvers.getContent().getCecoNumber(), payload.getCecoNumber(), "getCecoNumber issue");
	      softAssert.assertTrue(postHelper.isApproverPresent(payload.getApproverId(), approvers.getContent().getApprovers()));
	      softAssert.assertAll();    
	   }

	/**
	 * Check that is feasible to add to a ceco an approver without an assigned rol. 
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "randomRol", dataProviderClass = ApproverManagementDataProviders.class, groups = {ExeGroups.Sanity})
	   public void addApproverWithoutRolTest(String user) throws Exception {
	      SoftAssert softAssert = new SoftAssert();
	      ApproverPayLoadHelper payload = new ApproverPayLoadHelper(user, new ApproverWithoutRol());
	      PostApproverTestHelper postHelper = new PostApproverTestHelper(user);
	      Response response = postHelper.postApprover(payload);
	      validateResponseToContinueTest(response, 200, "post approver api call was not successful.", true);
	      ApproversResponseDTO approvers = response.as(ApproversResponseDTO.class, ObjectMapperType.GSON);
	      softAssert.assertEquals(approvers.getStatus(), "OK", "getStatus issue");
	      softAssert.assertEquals(approvers.getMessage(), "Success", "getMessage issue");
	      softAssert.assertEquals(approvers.getContent().getCecoNumber(), payload.getCecoNumber(), "getCecoNumber issue");
	      softAssert.assertTrue(postHelper.isApproverPresent(payload.getApproverId(), approvers.getContent().getApprovers()));
	      softAssert.assertAll();    
	   }

}
