package tests.testcases.submodules.manage.features.approverManagement;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.manage.features.ApproverManagementDataProviders;
import dto.submodules.manage.approverManagement.postMassiveApproverCeco.PostMassiveApproverCecoResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.manage.features.approverManagement.massiveApproverCeco.MassiveApproverCecoParameters;
import payloads.submodules.manage.features.approverManagement.massiveApproverCeco.IsEntireCecos;
import payloads.submodules.manage.features.approverManagement.massiveApproverCeco.MassiveApproverCecoPayLoadHelper;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.approverManagement.PostMassiveApproverCecoTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class PostMassiveApproverCecoTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("PostMassiveApproverCecoTests");
	}

	/**
	 * Check that is feasible to add an approver to multiples cecos.
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "massiveApproverCeco", dataProviderClass = ApproverManagementDataProviders.class, 
			groups = { ExeGroups.NotAvailableInPreProd })
	public void addAnApproverToMultiplesCecos(MassiveApproverCecoParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		MassiveApproverCecoPayLoadHelper payload = new MassiveApproverCecoPayLoadHelper(parameters);
		Response response = new PostMassiveApproverCecoTestHelper(parameters.getUsername()).postMassiveApproverCeco(payload);
		validateResponseToContinueTest(response, 200, "add an approver to multiples cecos api call was not successful.", true);
		PostMassiveApproverCecoResponseDTO approver = response.as(PostMassiveApproverCecoResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(approver.getStatus(), "Success", "getStatus issue");
		softAssert.assertEquals(approver.getMessage(), "OK", "getMessage issue");
		softAssert.assertEquals(approver.getContent().getTotalItemsUpdated(), "1", "getTotalItemsUpdated issue");
		softAssert.assertAll();
	}

	/**
	 * Check that is feasible to add an approver to all cecos.
	 * @param parameters
	 * @param isEntireCecos
	 * @throws Exception
	 */
	@Test(dataProvider = "massiveApproverCeco", dataProviderClass = ApproverManagementDataProviders.class, 
			groups = { ExeGroups.NotAvailableInPreProd })
	public void addAnApproverToAllCecos(MassiveApproverCecoParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		MassiveApproverCecoPayLoadHelper payload = new MassiveApproverCecoPayLoadHelper(parameters, new IsEntireCecos());
		Response response = new PostMassiveApproverCecoTestHelper(parameters.getUsername()).postMassiveApproverCeco(payload);
		validateResponseToContinueTest(response, 200, "add an approver to multiples cecos api call was not successful.", true);
		PostMassiveApproverCecoResponseDTO approver = response.as(PostMassiveApproverCecoResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(approver.getStatus(), "Success", "getStatus issue");
		softAssert.assertEquals(approver.getMessage(), "OK", "getMessage issue");
		softAssert.assertTrue((Double.valueOf(approver.getContent().getTotalItemsUpdated())>1), "getTotalItemsUpdated issue");
		softAssert.assertAll();
	}

}
