package tests.testcases.submodules.manage.features.approverManagement;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.manage.features.ApproverManagementDataProviders;
import dto.submodules.manage.approverManagement.putSwapApprovers.PutSwapApproversResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.manage.features.approverManagement.putSwapApprovers.PutSwapApproversParameters;
import payloads.submodules.manage.features.approverManagement.putSwapApprovers.AllCecos;
import payloads.submodules.manage.features.approverManagement.putSwapApprovers.SwapApproversPayLoadHelper;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.approverManagement.PutSwapApproversTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class PutSwapApproversTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("PutSwapApproversTests");
	}

	/**
	 * Replace an approver.
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "swapApprovers", dataProviderClass = ApproverManagementDataProviders.class, groups = { ExeGroups.NotAvailableInPreProd }
			, invocationCount = 1)
	public void putSwapApproversTest(PutSwapApproversParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		SwapApproversPayLoadHelper payload = new SwapApproversPayLoadHelper(parameters);
		Response response = new PutSwapApproversTestHelper(parameters.getUsername()).putSwapApprovers(payload);
		validateResponseToContinueTest(response, 200, "replace an approver api call was not successful.", true);
		PutSwapApproversResponseDTO approvers = response.as(PutSwapApproversResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(approvers.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(approvers.getMessage(), "Success", "getMessage issue");
		softAssert.assertEquals(approvers.getContent().getResponse().get(0).getCecoNumber(), payload.getCecos().get(0), "getCecoNumber issue");
		softAssert.assertEquals(approvers.getContent().getResponse().get(0).getMessage(), "OK", "getResponse().getMessage() issue");
		SwapApproversPayLoadHelper tearDownPayload = new SwapApproversPayLoadHelper(payload);
		response = new PutSwapApproversTestHelper(parameters.getUsername()).putSwapApprovers(tearDownPayload);
		validateResponseToContinueTest(response, 200, "tear down - replace an approver api call was not successful.", true);
		approvers = response.as(PutSwapApproversResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(approvers.getStatus(), "OK", "tear down - getStatus issue");
		softAssert.assertEquals(approvers.getMessage(), "Success", "tear down - getMessage issue");
		softAssert.assertEquals(approvers.getContent().getResponse().get(0).getCecoNumber(), payload.getCecos().get(0), "tear down - getCecoNumber issue");
		softAssert.assertEquals(approvers.getContent().getResponse().get(0).getMessage(), "OK", "getResponse().getMessage() issue");
		softAssert.assertAll();
	}

	/**
	 * Replace an approver in all cecos.
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "swapApprovers", dataProviderClass = ApproverManagementDataProviders.class, groups = { ExeGroups.NotAvailableInPreProd }
			, invocationCount = 1)
	public void putSwapApproversInAllCecosTest(PutSwapApproversParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		SwapApproversPayLoadHelper payload = new SwapApproversPayLoadHelper(parameters, new AllCecos());
		Response response = new PutSwapApproversTestHelper(parameters.getUsername()).putSwapApprovers(payload);
		validateResponseToContinueTest(response, 200, "replace an approver api call was not successful.", true);
		PutSwapApproversResponseDTO approvers = response.as(PutSwapApproversResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(approvers.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(approvers.getMessage(), "Success", "getMessage issue");
		softAssert.assertEquals(approvers.getContent().getResponse().get(0).getMessage(), "OK", "getResponse().getMessage() issue");
		SwapApproversPayLoadHelper tearDownPayload = new SwapApproversPayLoadHelper(payload, new AllCecos());
		response = new PutSwapApproversTestHelper(parameters.getUsername()).putSwapApprovers(tearDownPayload);
		validateResponseToContinueTest(response, 200, "tear down - replace an approver api call was not successful.", true);
		approvers = response.as(PutSwapApproversResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(approvers.getStatus(), "OK", "tear down - getStatus issue");
		softAssert.assertEquals(approvers.getMessage(), "Success", "tear down - getMessage issue");
		softAssert.assertEquals(approvers.getContent().getResponse().get(0).getMessage(), "OK", "getResponse().getMessage() issue");
		softAssert.assertAll();
	}

}
