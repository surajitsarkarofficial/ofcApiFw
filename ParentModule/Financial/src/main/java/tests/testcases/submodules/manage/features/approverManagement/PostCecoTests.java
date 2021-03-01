package tests.testcases.submodules.manage.features.approverManagement;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.manage.features.ApproverManagementDataProviders;
import dto.submodules.manage.approverManagement.postCeco.PostCecoResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import payloads.submodules.manage.features.approverManagement.CecoPayLoadHelper;
import payloads.submodules.manage.features.approverManagement.ExistingCecoNumber;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.approverManagement.PostCecoTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class PostCecoTests extends ManageBaseTest {
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("PostCecoTests");
	}

	 /**
	 * Check that is feasible to create a new ceco. 
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "randomRol", dataProviderClass = ApproverManagementDataProviders.class, groups = {ExeGroups.Sanity})
	   public void createCecoTest(String user) throws Exception {
	      SoftAssert softAssert = new SoftAssert();
	      CecoPayLoadHelper payload = new CecoPayLoadHelper();
	      PostCecoTestHelper postHelper = new PostCecoTestHelper(user);
	      Response response = postHelper.postCeco(payload);
	      validateResponseToContinueTest(response, 201, "Post ceco api call was not successful.", true);
	      PostCecoResponseDTO ceco = response.as(PostCecoResponseDTO.class, ObjectMapperType.GSON);
	      softAssert.assertEquals(ceco.getStatus(), "Success", "getStatus issue");
	      softAssert.assertEquals(ceco.getMessage(), "OK", "getMessage issue");
	      softAssert.assertEquals(ceco.getContent().getCecoNumber(), payload.getCecoNumber(), "getCecoNumber issue");
	      softAssert.assertAll();    
	   }
	
	 /**
	 * Check that is not feasible to create a new ceco with an existing ceco number. 
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "randomRol", dataProviderClass = ApproverManagementDataProviders.class)
	   public void createExistingCecoTest(String user) throws Exception {
	      SoftAssert softAssert = new SoftAssert();
	      CecoPayLoadHelper payload = new CecoPayLoadHelper(new ExistingCecoNumber());
	      PostCecoTestHelper postHelper = new PostCecoTestHelper(user);
	      Response response = postHelper.postCeco(payload);
	      PostCecoResponseDTO ceco = response.as(PostCecoResponseDTO.class, ObjectMapperType.GSON);
	      validateResponseToContinueTest(response, 400, "Post ceco api call was not successful.", true);
	      softAssert.assertEquals(ceco.getStatus(), "Bad Request", "getStatus issue");
	      softAssert.assertEquals(ceco.getMessage(), "This CECO already exists.", "getMessage issue");
	      softAssert.assertAll();    
	   }
}
