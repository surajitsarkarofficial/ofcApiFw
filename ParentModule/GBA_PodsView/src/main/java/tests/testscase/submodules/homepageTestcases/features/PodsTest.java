package tests.testscase.submodules.homepageTestcases.features;
/**
 * @author rachana.jadhav
 *
 */
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.PodsViewBaseDataProviders;
import io.restassured.response.Response;
import tests.testhelper.submodules.homepage.features.PodTestHelper;
import tests.testscase.submodules.homepageTestcases.HomePageBaseTest;
import utils.RestUtils;
public class PodsTest extends HomePageBaseTest{
	String firebaseMessage, glowMessage= null;
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("PodsTest");
	}
	
	/** This test will verify Pod details like pod ID,pod name, project name from Firebase and Glow are same
	 * @throws Exception
	 *  @param name
	 * @param id
	 * @param position
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "userData", dataProviderClass = PodsViewBaseDataProviders.class)
	public void verifyPodDetails(String name, String id, String position) throws Exception{
		
		RestUtils restUtils = new RestUtils();
		SoftAssert softAssert = new SoftAssert();
		PodTestHelper PodsHelper = new PodTestHelper();
		Response firebaseResponse =PodsHelper.getFirebasePodDetails(name, id);
		Response glowResponse =PodsHelper.getGlowPodDetails(name,id,position);
		firebaseMessage = (String) restUtils.getValueFromResponse(firebaseResponse, "message");
		glowMessage = (String) restUtils.getValueFromResponse(glowResponse, "message");
		softAssert.assertTrue(firebaseMessage.contains("Success"),"Failed in validating message");
		softAssert.assertTrue(glowMessage.contains("success"),"Failed in validating message");
	
		List<String> firebasePodId = (List<String>) restUtils.getValueFromResponse(firebaseResponse, "data..pods..id");
		List<String> glowPodId = (List<String>) restUtils.getValueFromResponse(glowResponse, "details..pods.[*].id");
		softAssert.assertEquals(glowPodId, firebasePodId,String.format("Expected data was '%s' and actual was '%s'", firebasePodId,glowPodId));
		
		List<String> firebasePodName = (List<String>) restUtils.getValueFromResponse(firebaseResponse, "data..pods..name");
		List<String> glowPodName = (List<String>) restUtils.getValueFromResponse(glowResponse, "details..pods.[*].name");
		softAssert.assertEquals(glowPodName, firebasePodName,String.format("Expected data was '%s' and actual was '%s'", firebasePodName,glowPodName));
		softAssert.assertAll();

	}
}
