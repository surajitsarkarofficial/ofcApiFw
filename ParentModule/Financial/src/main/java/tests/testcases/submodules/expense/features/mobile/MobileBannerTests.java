package tests.testcases.submodules.expense.features.mobile;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.expense.features.MobileBannerDataProviders;
import dto.submodules.expense.mobile.MobileBannerResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.features.mobile.MobileBannerTestHelper;

/**
 * @author german.massello
 *
 */
public class MobileBannerTests extends ExpenseBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("MobileBannerTests");
	}

	/**
	 * Check that the mobile banner is showing just once.
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "globerWithoutMobileExpenses", dataProviderClass = MobileBannerDataProviders.class)
	   public void checkMobileBanner (String user) throws Exception {
	      SoftAssert softAssert = new SoftAssert();
	      MobileBannerTestHelper helper = new MobileBannerTestHelper(user);
	      Response response = helper.postMobileBanner();
	      MobileBannerResponseDTO mobileBanner = response.as(MobileBannerResponseDTO.class, ObjectMapperType.GSON);
	      softAssert.assertEquals(mobileBanner.getStatus(), "OK", "getStatus issue");
	      softAssert.assertEquals(mobileBanner.getMessage(), "OK", "getMessage issue");
	      softAssert.assertFalse(mobileBanner.getContent(), "expected false because the banner is showing at first time");
	      response = helper.postMobileBanner();
	      mobileBanner = response.as(MobileBannerResponseDTO.class, ObjectMapperType.GSON);
	      softAssert.assertTrue(mobileBanner.getContent(), "expected true because the banner is showing at second time");
	      softAssert.assertAll();    
	   }

}
