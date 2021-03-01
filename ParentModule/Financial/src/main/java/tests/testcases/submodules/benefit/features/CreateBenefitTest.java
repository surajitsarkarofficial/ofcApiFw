package tests.testcases.submodules.benefit.features;

import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.benefit.features.CreateBenefitDataProviders;
import dto.submodules.benefit.postBenefit.PostBenefitResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import payloads.submodules.benefit.features.post.PostBenefitPayLoadHelper;
import tests.testcases.submodules.benefit.BenefitBaseTest;
import tests.testhelpers.submodules.benefit.features.PostBenefitTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class CreateBenefitTest extends BenefitBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("CreateBenefitTests");
	}

	private PostBenefitTestHelper postHelper = new PostBenefitTestHelper();
	
	@Test(dataProvider = "randomGlober", dataProviderClass = CreateBenefitDataProviders.class, 
	groups = { ExeGroups.NotAvailableInPreProd }, invocationCount = 1)
	public void createBenefitTestForRandomGlober(List<Map<String, String>> globersList) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		PostBenefitPayLoadHelper payload = new PostBenefitPayLoadHelper(globersList);
		Response response = postHelper.postBenefit(payload);
		validateResponseToContinueTest(response, 200, "Post ceco api call was not successful.", true);
		PostBenefitResponseDTO benefit = response.as(PostBenefitResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(benefit.getStatusName(), "OK", "getStatusName issue"); 
		softAssert.assertEquals(benefit.getMessage(), "SUCCESS", "getMessage issue");
		softAssert.assertNotNull(benefit.getDetails().getBenefitLotId(), "getBenefitLotId issue");
	    softAssert.assertAll();
	}

	@Test(dataProvider = "talentPoolGlober", dataProviderClass = CreateBenefitDataProviders.class, 
	groups = { ExeGroups.NotAvailableInPreProd }, invocationCount = 1)
	public void createBenefitTestForTalentPoolGlober(List<Map<String, String>> globersList) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		PostBenefitPayLoadHelper payload = new PostBenefitPayLoadHelper(globersList);
		Response response = postHelper.postBenefit(payload);
		validateResponseToContinueTest(response, 200, "Post ceco api call was not successful.", true);
		PostBenefitResponseDTO benefit = response.as(PostBenefitResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(benefit.getStatusName(), "OK", "getStatusName issue"); 
		softAssert.assertEquals(benefit.getMessage(), "SUCCESS", "getMessage issue");
		softAssert.assertNotNull(benefit.getDetails().getBenefitLotId(), "getBenefitLotId issue");
	    softAssert.assertAll();
	}

}
