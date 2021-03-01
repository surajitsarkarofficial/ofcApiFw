package test.testcases.submodules.myPods.features;

import java.util.Collections;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.GlobantPodsDataproviders;
import io.restassured.response.Response;
import test.testcases.submodules.myPods.MyPodsTest;
import test.testhelpers.submodules.myPods.features.PodConstitutionTestHelper;
import utils.RestUtils;

/**
 * @author ankita.manekar
 *
 */
public class PodConstitutionTest extends MyPodsTest {
	String firebaseMessage, glowMessage = null;
	Boolean successMessage = null;
	RestUtils restUtils;
	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("PodConstitutionTest");
	}

	/**
	 * This Test will verify the Pod Constitution Area Details like Area Id, Area
	 * Name, Area Description for Firebase and Glow are same
	 * 
	 * @param token
	 * @param authorization
	 * @param podListEmail
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "userData", dataProviderClass = GlobantPodsDataproviders.class, priority = 0)
	public void getPodConstitutionAreaDetails(String token, String authorization, String podListEmail)
			throws Exception {
		restUtils = new RestUtils();
		softAssert = new SoftAssert();
		PodConstitutionTestHelper podConstitution = new PodConstitutionTestHelper();
		Response firebaseResponse = podConstitution.getFirebasePodConstitutionDetails(authorization);
		validateResponseToContinueTest(firebaseResponse, 200, "Failed in validating Firebase response", true);
		Response glowResponse = podConstitution.getGlowPodConstitutionDetails(authorization, token);
		validateResponseToContinueTest(glowResponse, 200, "Failed in validating Glow response", true);

		firebaseMessage = (String) restUtils.getValueFromResponse(firebaseResponse, "message");
		glowMessage = (String) restUtils.getValueFromResponse(glowResponse, "message");

		softAssert.assertTrue(firebaseMessage.contains("Success"), "Failed in validating Firebase message");
		softAssert.assertTrue(glowMessage.contains("success"), "Failed in validating Glow message");

		List<Long> firebasePodConstitutionAreaId = (List<Long>) restUtils.getValueFromResponse(firebaseResponse,
				"data..constitution..areaId");
		Collections.sort(firebasePodConstitutionAreaId);

		List<Long> glowPodConstitutionAreaId = (List<Long>) restUtils.getValueFromResponse(glowResponse,
				"details..A..areaId");
		Collections.sort(glowPodConstitutionAreaId);

		softAssert.assertEquals(glowPodConstitutionAreaId, firebasePodConstitutionAreaId,
				String.format("Expected data was '%s' and actual was '%s'", glowPodConstitutionAreaId,
						firebasePodConstitutionAreaId));

		List<String> firebasePodConstitutionAreaName = (List<String>) restUtils.getValueFromResponse(firebaseResponse,
				"data..constitution..areaName");
		Collections.sort(firebasePodConstitutionAreaName);

		List<String> glowPodConstitutionAreaName = (List<String>) restUtils.getValueFromResponse(glowResponse,
				"details..A..areaName");
		Collections.sort(glowPodConstitutionAreaName);

		softAssert.assertEquals(glowPodConstitutionAreaName, firebasePodConstitutionAreaName,
				String.format("Expected data was '%s' and actual was '%s'", glowPodConstitutionAreaName,
						firebasePodConstitutionAreaName));

		List<String> firebasePodConstitutionAreaDescription = (List<String>) restUtils
				.getValueFromResponse(firebaseResponse, "data..constitution..areaDescription");
		Collections.sort(firebasePodConstitutionAreaDescription);

		List<String> glowPodConstitutionAreaDescription = (List<String>) restUtils.getValueFromResponse(glowResponse,
				"details..A..description");
		Collections.sort(glowPodConstitutionAreaDescription);

		softAssert.assertEquals(glowPodConstitutionAreaDescription, firebasePodConstitutionAreaDescription,
				String.format("Expected data was '%s' and actual was '%s'", glowPodConstitutionAreaDescription,
						firebasePodConstitutionAreaDescription));
		softAssert.assertAll();
	}

	/**
	 * This Test will verify the Pod's Role Details like Role Name and Member Email
	 * for Firebase and Glow are same
	 * 
	 * @param token
	 * @param authorization
	 * @param podListEmail
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "userData", dataProviderClass = GlobantPodsDataproviders.class, priority = 1)
	public void getPodConstitutionRoleDetails(String token, String authorization, String podListEmail)
			throws Exception {
		restUtils = new RestUtils();
		softAssert = new SoftAssert();
		PodConstitutionTestHelper podConstitution = new PodConstitutionTestHelper();
		Response firebaseResponse = podConstitution.getFirebasePodConstitutionDetails(authorization);
		validateResponseToContinueTest(firebaseResponse, 200, "Failed in validating Firebase response", true);
		Response glowRoleResponse = podConstitution.getGlowPodRoleDetails(authorization, token);
		validateResponseToContinueTest(glowRoleResponse, 200, "Failed in validating Glow Role response", true);

		glowMessage = (String) restUtils.getValueFromResponse(glowRoleResponse, "message");

		softAssert.assertTrue(glowMessage.contains("success"), "Failed in validating GlowPodRole message");

		List<Integer> PodConstitutionMembersCount = (List<Integer>) restUtils.getValueFromResponse(firebaseResponse,
				"data..membersCount");
		int memberCount = PodConstitutionMembersCount.get(0);

		if (memberCount > 0) {

			List<String> firebasePodConstitutionRoleName = (List<String>) restUtils
					.getValueFromResponse(firebaseResponse, "data..roles[*].name");
			Collections.sort(firebasePodConstitutionRoleName);

			List<String> glowPodConstitutionRoleName = (List<String>) restUtils.getValueFromResponse(glowRoleResponse,
					"details..[?(@.userDefined==false)]..name");
			Collections.sort(glowPodConstitutionRoleName);

			softAssert.assertEquals(glowPodConstitutionRoleName, firebasePodConstitutionRoleName,
					String.format("Expected data was '%s' and actual was '%s'", glowPodConstitutionRoleName,
							firebasePodConstitutionRoleName));

			List<String> firebasePodConstitutionMemberEmail = (List<String>) restUtils
					.getValueFromResponse(firebaseResponse, "data..roles..[*].email");
			Collections.sort(firebasePodConstitutionMemberEmail);

			List<String> glowPodConstitutionMemberEmail = (List<String>) restUtils
					.getValueFromResponse(glowRoleResponse, "details..[?(@.userDefined==false)]..email");
			Collections.sort(glowPodConstitutionMemberEmail);

			softAssert.assertEquals(glowPodConstitutionMemberEmail, firebasePodConstitutionMemberEmail,
					String.format("Expected data was '%s' and actual was '%s'", glowPodConstitutionMemberEmail,
							firebasePodConstitutionMemberEmail));
			softAssert.assertAll();
		}

	}

	/**
	 * This Test will verify Pod's Principle, Value and Purpose for Firebase and
	 * Glow are same
	 * 
	 * @param token
	 * @param authorization
	 * @param podListEmail
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "userData", dataProviderClass = GlobantPodsDataproviders.class, priority = 2)
	public void getPodPrinciple_Value_PurposeDetails(String token, String authorization, String podListEmail)
			throws Exception {
		restUtils = new RestUtils();
		softAssert = new SoftAssert();
		PodConstitutionTestHelper podConstitution = new PodConstitutionTestHelper();
		Response firebaseResponse = podConstitution.getFirebasePodConstitutionDetails(authorization);
		validateResponseToContinueTest(firebaseResponse, 200, "Failed in validating Firebase response", true);
		Response glowPodValuesResponse = podConstitution.getGlowPodValuesUrl(authorization, token);
		validateResponseToContinueTest(glowPodValuesResponse, 200, "Failed in validating Glow Pod values response",
				true);

		glowMessage = (String) restUtils.getValueFromResponse(glowPodValuesResponse, "message");

		softAssert.assertTrue(glowMessage.contains("success"), "Failed in validating GlowPodValues message");

		List<String> firebasePodConstitutionPrinciple = (List<String>) restUtils.getValueFromResponse(firebaseResponse,
				"data..principles");
		Collections.sort(firebasePodConstitutionPrinciple);

		List<String> glowPodConstitutionPrinciple = (List<String>) restUtils.getValueFromResponse(glowPodValuesResponse,
				"details..principles");
		Collections.sort(glowPodConstitutionPrinciple);

		softAssert.assertEquals(glowPodConstitutionPrinciple, firebasePodConstitutionPrinciple,
				String.format("Expected data was '%s' and actual was '%s'", glowPodConstitutionPrinciple,
						firebasePodConstitutionPrinciple));

		List<String> firebasePodConstitutionPurpose = (List<String>) restUtils.getValueFromResponse(firebaseResponse,
				"data..purpose");
		Collections.sort(firebasePodConstitutionPurpose);

		List<String> glowPodConstitutionPurpose = (List<String>) restUtils.getValueFromResponse(glowPodValuesResponse,
				"details..purpose");
		Collections.sort(glowPodConstitutionPurpose);

		softAssert.assertEquals(glowPodConstitutionPurpose, firebasePodConstitutionPurpose,
				String.format("Expected data was '%s' and actual was '%s'", glowPodConstitutionPurpose,
						firebasePodConstitutionPurpose));

		List<String> firebasePodConstitutionValues = (List<String>) restUtils.getValueFromResponse(firebaseResponse,
				"data..values");
		Collections.sort(firebasePodConstitutionValues);

		List<String> glowPodConstitutionValues = (List<String>) restUtils.getValueFromResponse(glowPodValuesResponse,
				"details..values");
		Collections.sort(glowPodConstitutionValues);

		softAssert.assertEquals(glowPodConstitutionValues, firebasePodConstitutionValues,
				String.format("Expected data was '%s' and actual was '%s'", glowPodConstitutionValues,
						firebasePodConstitutionValues));
		softAssert.assertAll();
	}

	/**
	 * This test will validate PodConstitution schema response is as expected
	 * 
	 * @param authorization
	 * @param token
	 * @throws Exception
	 */
	@Test(dataProvider = "schemaData", dataProviderClass = GlobantPodsDataproviders.class, priority = 3)
	public void verifyPodConstitutionSchema(String authorization, String token) throws Exception {
		restUtils = new RestUtils();
		softAssert = new SoftAssert();
		PodConstitutionTestHelper podConstitution = new PodConstitutionTestHelper();
		Response firebaseResponse = podConstitution.podConstitutionSchemaValidation(authorization, token);
		validateResponseToContinueTest(firebaseResponse, 200, "Failed in validating Firebase response", true);
		successMessage = (Boolean) restUtils.getValueFromResponse(firebaseResponse, "success");
		softAssert.assertTrue(Boolean.TRUE, "Failed in validating Success message");
		softAssert.assertAll();
	}

}
