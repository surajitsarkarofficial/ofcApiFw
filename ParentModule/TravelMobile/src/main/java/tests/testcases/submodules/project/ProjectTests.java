package tests.testcases.submodules.project;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import dataproviders.submodules.project.ProjectDataproviders;
import io.restassured.response.Response;
import tests.testcases.TravelMobileBaseTest;
import tests.testhelpers.submodules.project.TNProjectTestHelper;
import utils.RestUtils;

/**
 * 
 * @author surajit.sarkar
 *
 */
public class ProjectTests extends TravelMobileBaseTest {

	@BeforeTest(alwaysRun = true)
	public void beforeTest() {
		subModuleTest = moduleTest.createNode("Project Tests");
	}

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("Project Tests");
	}
	
	/**
	 * This method will verify the Search Project API for valid data
	 * @param dtoMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(priority = 0, dataProvider = "searchProjectWithValidData", dataProviderClass = ProjectDataproviders.class)
	public void verifyProjectSearchWithValidData(Map<Object, Object> dtoMap) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		TNProjectTestHelper testHelper = new TNProjectTestHelper();
		RestUtils restUtils = new RestUtils();
		String testScenario = dtoMap.get("dataScenario").toString();
		test.log(Status.INFO, String.format("Validating Project Search API for '%s'", testScenario));

		String query = (String) dtoMap.get("projectName");

		Response response = testHelper.getProjectSearch(query);

		validateResponseToContinueTest(response, 200,
				String.format("Unable to perform project search for query '%s'.", query), true);

		String expectedStatus = (String) dtoMap.get("expectedStatus");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = (String) dtoMap.get("expectedMessage");
		softAssert.assertTrue(
				restUtils.getValueFromResponse(response, "message").toString().contains(expectedResponseMessage),
				String.format("Actual message '%s'  does not contains expected message '%s'",
						restUtils.getValueFromResponse(response, "message"), expectedResponseMessage));

		List<LinkedHashMap<String, Object>> projectDetailsList = (List<LinkedHashMap<String, Object>>) restUtils
				.getValueFromResponse(response, "content");

		int totalResultCount = Integer.parseInt(String.valueOf(restUtils.getValueFromResponse(response, "totalCount")));
		int totalProjectDetailsCount = projectDetailsList.size();

		softAssert.assertTrue(totalResultCount == totalProjectDetailsCount,
				String.format("Total Count displayed is %d and total project details displayed is %d.",
						totalResultCount, totalProjectDetailsCount));

		// verify details for each project
		for (LinkedHashMap<String, Object> projectDetail : projectDetailsList) {

			String projectName = (String) projectDetail.get("projectName");
			String clientName = (String) projectDetail.get("clientName");

			softAssert.assertTrue(!String.valueOf(projectDetail.get("projectId")).isEmpty(),
					String.format("Project Id for Project Name '%s' was empty or null.", projectName));

			softAssert.assertTrue(!String.valueOf(projectDetail.get("clientId")).isEmpty(),
					String.format("Client Id for Project Name '%s' was empty or null.", projectName));

			softAssert.assertTrue(!((String) projectDetail.get("tag")).isEmpty(),
					String.format("Tag for Project '%s' was empty or null.", projectName));

			boolean isMatched = testHelper.isQueryMatchingProjectOrClientNameFound(projectName.toLowerCase(),
					clientName.toLowerCase(), query.toLowerCase());

			softAssert.assertTrue(isMatched,
					String.format("Query String '%s' neither matched Project Name - '%s', nor Client name - '%s'.",
							query, projectName, clientName));

		}

		softAssert.assertAll();

		test.log(Status.PASS, "Project Search API verification for valid data was successful.");

	}
	/**
	 * This method will verify the Search Project API for invalid data
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority = 1, dataProvider = "searchProjectWithInValidData", dataProviderClass = ProjectDataproviders.class)
	public void verifyProjectSearchWithInValidData(Map<Object, Object> dtoMap) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		TNProjectTestHelper testHelper = new TNProjectTestHelper();
		RestUtils restUtils = new RestUtils();
		String testScenario = dtoMap.get("dataScenario").toString();
		test.log(Status.INFO, String.format("Validating Project Search API for '%s'", testScenario));

		String query = (String) dtoMap.get("projectName");

		Response response = testHelper.getProjectSearch(query);

		validateResponseToContinueTest(response, 200,
				String.format("Unable to perform project search for query '%s'.", query), true);

		String expectedStatus = (String) dtoMap.get("expectedStatus");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = (String) dtoMap.get("expectedMessage");
		softAssert.assertTrue(
				restUtils.getValueFromResponse(response, "message").toString().contains(expectedResponseMessage),
				String.format("Actual message '%s'  does not contains expected message '%s'",
						restUtils.getValueFromResponse(response, "message"), expectedResponseMessage));

		int totalResultCount = Integer.parseInt(String.valueOf(restUtils.getValueFromResponse(response, "totalCount")));
		int expectedResultCount = 0;

		softAssert.assertTrue(totalResultCount == expectedResultCount, String.format(
				"Total Count displayed is %d and expected count is %d.", totalResultCount, expectedResultCount));

		softAssert.assertAll();

		test.log(Status.PASS, "Project Search API verification for in-valid data was successful.");

	}

}
