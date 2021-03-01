package test.testcases.submodules.myPods.features;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.GlobantPodsDataproviders;
import io.restassured.response.Response;
import test.testcases.submodules.myPods.MyPodsTest;
import test.testhelpers.submodules.myPods.features.PodListTestHelper;
import utils.RestUtils;

/**
 * @author ankita.manekar
 *
 */
public class PodListTest extends MyPodsTest {
	String firebaseMessage, glowMessage = null;
	Boolean successMessage = null;
	RestUtils restUtil;
	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("PodListTest");
	}

	/**
	 * This Test will verify Pod List Details like Pod ID, Pod Name, Project Id,
	 * Project Name for Firebase and Glow are same
	 * 
	 * @param token
	 * @param authorization
	 * @param podListEmail
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "userData", dataProviderClass = GlobantPodsDataproviders.class, priority = 0)
	public void getPodListDetails(String token, String authorization, String podListEmail) throws Exception {
		restUtil = new RestUtils();
		softAssert = new SoftAssert();
		PodListTestHelper podList = new PodListTestHelper();
		Response firebaseResponse = podList.getFirebasePodList(authorization);
		validateResponseToContinueTest(firebaseResponse, 200, "Failed in validating Firebase response", true);
		Response glowResponse = podList.getGlowPodList(podListEmail, token);
		validateResponseToContinueTest(glowResponse, 200, "Failed in validating Glow response", true);

		firebaseMessage = (String) restUtil.getValueFromResponse(firebaseResponse, "message");
		glowMessage = (String) restUtil.getValueFromResponse(glowResponse, "message");

		softAssert.assertTrue(firebaseMessage.contains("Success"), "Failed in validating Firebase message");
		softAssert.assertTrue(glowMessage.contains("success"), "Failed in validating glow message");

		List<Long> firebasePodIDList = (List<Long>) restUtil.getValueFromResponse(firebaseResponse,
				"data..pods..podId");
		Collections.sort(firebasePodIDList);
		
		List<Long> glowPodIDList = (List<Long>) restUtil.getValueFromResponse(glowResponse,
				"details..assignedPods.[?(@.enable==true)].id");
		Collections.sort(glowPodIDList);
	
		softAssert.assertEquals(glowPodIDList, firebasePodIDList,
				String.format("Expected data was '%s' and actual was '%s'", glowPodIDList, firebasePodIDList));

		List<String> firebasePodName = (List<String>) restUtil.getValueFromResponse(firebaseResponse,
				"data..pods..podName");
		Collections.sort(firebasePodName);

		List<String> glowPodName = (List<String>) restUtil.getValueFromResponse(glowResponse,
				"details..assignedPods.[?(@.enable==true)].name");
		Collections.sort(glowPodName);

		softAssert.assertEquals(glowPodName, firebasePodName,
				String.format("Expected data was '%s' and actual was '%s'", glowPodName, firebasePodName));

		softAssert.assertAll();
	}

	/**
	 * This Test will verify Pod Area Details like area description, area id, area
	 * name for Firebase and Glow are same depending upon assessment Id
	 * 
	 * @param token
	 * @param authorization
	 * @param podListEmail
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test(dataProvider = "userData", dataProviderClass = GlobantPodsDataproviders.class, priority = 1)
	public void getPodAreaDetails(String token, String authorization, String podListEmail) throws Exception {
		restUtil = new RestUtils();
		softAssert = new SoftAssert();
		PodListTestHelper podList = new PodListTestHelper();
		Response firebaseResponse = podList.getFirebasePodList(authorization);
		validateResponseToContinueTest(firebaseResponse, 200, "Failed in validating Firebase response", true);

		List<Long> firebasePodIDList = (List<Long>) restUtil.getValueFromResponse(firebaseResponse,
				"data..pods..[?(@.isMember==true)]");
		HashMap<String, Long> hash_map = new HashMap<String, Long>();
		
		Iterator iter = firebasePodIDList.iterator();
		while (iter.hasNext()) {
			hash_map = (HashMap<String, Long>) iter.next();
			Long PodIds = Long.parseLong(String.valueOf(hash_map.get("podId")));
			Long AssesmentId = Long.parseLong(String.valueOf(hash_map.get("assessmentId")));
			
			if (AssesmentId > 0) {

				List<Long> firebaseAreaIds = (List<Long>) restUtil.getValueFromResponse(firebaseResponse,
						"data..pods[?(@.podId==" + PodIds + ")]..areas..areaId");
				Collections.sort(firebaseAreaIds);

				List<Long> firebaseAreaName = (List<Long>) restUtil.getValueFromResponse(firebaseResponse,
						"data..pods[?(@.podId==" + PodIds + ")]..areas..areaName");
				Collections.sort(firebaseAreaName);

				List<String> firebaseAreaDescription = (List<String>) restUtil.getValueFromResponse(firebaseResponse,
						"data..pods[?(@.podId==" + PodIds + ")]..areas..areaDescription");
				Collections.sort(firebaseAreaDescription);

				Response glowAssesmentResponse = podList.getGlowAssesmentAreasDetails(PodIds, token);
				validateResponseToContinueTest(glowAssesmentResponse, 200,
						"Failed in validating Glow Assesment response", true);

				glowMessage = (String) restUtil.getValueFromResponse(glowAssesmentResponse, "message");
				softAssert.assertTrue(glowMessage.contains("success"), "Failed in validating GlowAssesment message");

				List<Long> glowAreaIds = (List<Long>) restUtil.getValueFromResponse(glowAssesmentResponse,
						"details..areas..areaId");
				Collections.sort(glowAreaIds);

				List<String> glowAreaNameDetails = (List<String>) restUtil.getValueFromResponse(glowAssesmentResponse,
						"details..areas..areaName");
				Collections.sort(glowAreaNameDetails);

				List<String> glowAreaDescription = (List<String>) restUtil.getValueFromResponse(glowAssesmentResponse,
						"details..areas..description");
				Collections.sort(glowAreaDescription);

				softAssert.assertEquals(glowAreaIds, firebaseAreaIds,
						String.format("Expected data was '%s' and actual was '%s'", glowAreaIds, firebaseAreaIds));
				softAssert.assertEquals(glowAreaNameDetails, firebaseAreaName, String
						.format("Expected data was '%s' and actual was '%s'", glowAreaNameDetails, firebaseAreaName));
				softAssert.assertEquals(glowAreaDescription, firebaseAreaDescription, String.format(
						"Expected data was '%s' and actual was '%s'", glowAreaDescription, firebaseAreaDescription));
				softAssert.assertAll();

			} else {
				List<Long> firebaseAreaIds = (List<Long>) restUtil.getValueFromResponse(firebaseResponse,
						"data..pods[?(@.podId==" + PodIds + ")]..areas..areaId");
				Collections.sort(firebaseAreaIds);

				List<String> firebaseAreaName = (List<String>) restUtil.getValueFromResponse(firebaseResponse,
						"data..pods[?(@.podId==" + PodIds + ")]..areas..areaName");
				Collections.sort(firebaseAreaName);

				List<String> firebaseAreaDescription = (List<String>) restUtil.getValueFromResponse(firebaseResponse,
						"data..pods[?(@.podId==" + PodIds + ")]..areas..areaDescription");
				Collections.sort(firebaseAreaDescription);

				Response glowConstitutionResponse = podList.getGlowPodConstitutionDetails(PodIds, token);
				validateResponseToContinueTest(glowConstitutionResponse, 200,
						"Failed in validating Glow Constitution response", true);

				glowMessage = (String) restUtil.getValueFromResponse(glowConstitutionResponse, "message");
				softAssert.assertTrue(glowMessage.contains("success"), "Failed in validating GlowConstitution message");

				List<Long> glowAreaIds = (List<Long>) restUtil.getValueFromResponse(glowConstitutionResponse,
						"details..A..areaId");
				Collections.sort(glowAreaIds);

				List<String> glowAreaNameDetails = (List<String>) restUtil
						.getValueFromResponse(glowConstitutionResponse, "details..A..areaName");
				Collections.sort(glowAreaNameDetails);

				List<String> glowAreaDescription = (List<String>) restUtil
						.getValueFromResponse(glowConstitutionResponse, "details..A..description");
				Collections.sort(glowAreaDescription);

				softAssert.assertEquals(glowAreaIds, firebaseAreaIds,
						String.format("Expected data was '%s' and actual was '%s'", glowAreaIds, firebaseAreaIds));
				softAssert.assertEquals(glowAreaNameDetails, firebaseAreaName, String
						.format("Expected data was '%s' and actual was '%s'", glowAreaNameDetails, firebaseAreaName));
				softAssert.assertEquals(glowAreaDescription, firebaseAreaDescription, String.format(
						"Expected data was '%s' and actual was '%s'", glowAreaDescription, firebaseAreaDescription));
				softAssert.assertAll();
			}
		}
	}

	/**
	 * This test will validate PodList schema response is as expected
	 * 
	 * @param authorization
	 * @param token
	 * @throws Exception
	 */
	@Test(dataProvider = "schemaData", dataProviderClass = GlobantPodsDataproviders.class, priority = 2)
	public void verifyPodListSchema(String authorization, String token) throws Exception {
		restUtil = new RestUtils();
		softAssert = new SoftAssert();
		PodListTestHelper podList = new PodListTestHelper();
		Response firebaseResponse = podList.podListSchemaValidation(authorization, token);
		validateResponseToContinueTest(firebaseResponse, 200, "Failed in validating Firebase response", true);
		successMessage = (Boolean) restUtil.getValueFromResponse(firebaseResponse, "success");
		softAssert.assertTrue(Boolean.TRUE, "Failed in validating Success message");
		softAssert.assertAll();
	}
}
