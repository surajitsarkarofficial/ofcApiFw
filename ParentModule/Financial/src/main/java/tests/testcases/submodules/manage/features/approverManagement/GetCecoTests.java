package tests.testcases.submodules.manage.features.approverManagement;



import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.manage.features.ApproverManagementDataProviders;
import dto.submodules.manage.approverManagement.getCecos.GetCecosResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.manage.features.approverManagement.getCecos.CecoNumbers;
import parameters.submodules.manage.features.approverManagement.getCecos.GetCecosParameters;
import parameters.submodules.manage.features.approverManagement.getCecos.IncompleteCecoNumber;
import parameters.submodules.manage.features.approverManagement.getCecos.NonExistentCecoNumber;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.approverManagement.GetCecosTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class GetCecoTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetCecoTests");
	}

	/**
	 * Check that is feasible to perform a GET cecos. 
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "randomRol", dataProviderClass = ApproverManagementDataProviders.class)
	public void getCecosTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		GetCecosTestHelper helper = new GetCecosTestHelper(user);
		GetCecosParameters parameters = new GetCecosParameters();
		Response response = helper.getCecos(parameters);
		GetCecosResponseDTO cecos = response.as(GetCecosResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(cecos.getStatus(), "Success", "getStatus issue");
		softAssert.assertEquals(cecos.getMessage(), "OK", "getMessage issue");
		softAssert.assertTrue(Integer.valueOf(cecos.getContent().getTotalItems())>0, "getTotalItems issue");
		softAssert.assertNotNull(cecos.getContent(), "getContent issue");
		softAssert.assertAll();
	}
	
	/**
	 * Check that the filter by ceco numbers is working fine.
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "randomRol", dataProviderClass = ApproverManagementDataProviders.class, groups = {ExeGroups.Sanity})
	public void getCecosFilterByCecoNumbersTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		GetCecosTestHelper helper = new GetCecosTestHelper(user);
		CecoNumbers cecoNumbers = new CecoNumbers();
		GetCecosParameters parameters = new GetCecosParameters(cecoNumbers);
		Response response = helper.getCecos(parameters);
		GetCecosResponseDTO cecosResponse = response.as(GetCecosResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertTrue(helper.areCecosNumbersPresent(cecoNumbers, cecosResponse), "cecoNumbers are not present in the response");
		softAssert.assertAll();
	}
	
	/**
	 * Check that is feasible to search cecos using a incomplete ceco number.
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "randomRol", dataProviderClass = ApproverManagementDataProviders.class)
	public void getCecosFilterByIncompletedCecoNumberTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		GetCecosTestHelper helper = new GetCecosTestHelper(user);
		IncompleteCecoNumber incompleteCecoNumber = new IncompleteCecoNumber();
		GetCecosParameters parameters = new GetCecosParameters(incompleteCecoNumber);
		Response response = helper.getCecos(parameters);
		GetCecosResponseDTO cecosResponse = response.as(GetCecosResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertTrue(helper.isIncompleteCecoNumberPresent(incompleteCecoNumber, cecosResponse), "incomplete ceco number is not present");
		softAssert.assertAll();
	}
	
	/**
	 * Check the message when we try to search a non existent ceco number.
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "randomRol", dataProviderClass = ApproverManagementDataProviders.class)
	public void getNotExistentCecoTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		GetCecosTestHelper helper = new GetCecosTestHelper(user);
		NonExistentCecoNumber nonExistentCecoNumber = new NonExistentCecoNumber();
		GetCecosParameters parameters = new GetCecosParameters(nonExistentCecoNumber);
		Response response = helper.getCecos(parameters);
		GetCecosResponseDTO cecosResponse = response.as(GetCecosResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(cecosResponse.getStatus(), "Not Found", "getStatus issue");
		softAssert.assertEquals(cecosResponse.getMessage(), "The CECO identifier(s) not exist", "getMessage issue");
		softAssert.assertAll();
	}

}
