package test.testcases.submodules.myPods.features;

import java.util.Collections;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.GlobantPodsDataproviders;
import io.restassured.response.Response;
import test.testcases.submodules.myPods.MyPodsTest;
import test.testhelpers.submodules.myPods.features.PodMemberTestHelper;
import utils.RestUtils;

/**
 * @author ankita.manekar
 *
 */
public class PodMemberTest extends MyPodsTest {
	String firebaseMessage, glowMessage = null;
	Boolean successMessage = null;
	RestUtils restUtil;
	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("PodMemberTest");
	}

	/**
	 * This Test will verify Pod member details like Member Name, Member Email for
	 * Firebase and Glow are same
	 * 
	 * @param token
	 * @param authorization
	 * @param podListEmail
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "userData", dataProviderClass = GlobantPodsDataproviders.class, priority = 0)
	public void getPodMembersDetails(String token, String authorization, String podListEmail) throws Exception {
		restUtil = new RestUtils();
		softAssert = new SoftAssert();
		PodMemberTestHelper podMember = new PodMemberTestHelper();
		Response glowResponse = podMember.getGlowPodMembersDetails(token, authorization);
		validateResponseToContinueTest(glowResponse, 200, "Failed in validating Glow response", true);
		Response firebaseResponse = podMember.getFirebasePodMembersDetails(authorization);
		validateResponseToContinueTest(firebaseResponse, 200, "Failed in validating Firebase response", true);

		firebaseMessage = (String) restUtil.getValueFromResponse(firebaseResponse, "message");
		glowMessage = (String) restUtil.getValueFromResponse(glowResponse, "message");

		softAssert.assertTrue(firebaseMessage.contains("Success"), "Failed in validating Firebase message");
		softAssert.assertTrue(glowMessage.contains("success"), "Failed in validating Glow message");

		List<String> firebasePodMemberNameList = (List<String>) restUtil.getValueFromResponse(firebaseResponse,
				"data..members[*].name");
		Collections.sort(firebasePodMemberNameList);

		List<String> glowPodMemberNameList = (List<String>) restUtil.getValueFromResponse(glowResponse,
				"details..globerName");
		Collections.sort(glowPodMemberNameList);

		softAssert.assertEquals(glowPodMemberNameList, firebasePodMemberNameList, String.format(
				"Expected data was '%s' and actual was '%s'", glowPodMemberNameList, firebasePodMemberNameList));

		List<String> firebasePodMemberEmailList = (List<String>) restUtil.getValueFromResponse(firebaseResponse,
				"data..members..email");
		Collections.sort(firebasePodMemberEmailList);

		List<String> glowPodMemberEmailList = (List<String>) restUtil.getValueFromResponse(glowResponse,
				"details..email");
		Collections.sort(glowPodMemberEmailList);

		softAssert.assertEquals(glowPodMemberEmailList, firebasePodMemberEmailList, String.format(
				"Expected data was '%s' and actual was '%s'", glowPodMemberEmailList, firebasePodMemberEmailList));
		softAssert.assertAll();
	}

	/**
	 * This test will validate PodMember schema response is as expected
	 * 
	 * @param authorization
	 * @param token
	 * @throws Exception
	 */
	@Test(dataProvider = "schemaData", dataProviderClass = GlobantPodsDataproviders.class, priority = 1)
	public void verifyPodMemberSchema(String authorization, String token) throws Exception {
		restUtil = new RestUtils();
		softAssert = new SoftAssert();
		PodMemberTestHelper podMember = new PodMemberTestHelper();
		Response firebaseResponse = podMember.podMemberSchemaValidation(authorization, token);
		validateResponseToContinueTest(firebaseResponse, 200, "Failed in validating Firebase response", true);
		successMessage = (Boolean) restUtil.getValueFromResponse(firebaseResponse, "success");
		softAssert.assertTrue(Boolean.TRUE, "Failed in validating Success message");
		softAssert.assertAll();
	}
}
