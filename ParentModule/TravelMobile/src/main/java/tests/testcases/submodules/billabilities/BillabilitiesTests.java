package tests.testcases.submodules.billabilities;

import java.util.LinkedHashMap;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import io.restassured.response.Response;
import tests.testcases.TravelMobileBaseTest;
import tests.testhelpers.submodules.billabilities.BillabilitiesTestHelper;
import utils.RestUtils;

public class BillabilitiesTests extends TravelMobileBaseTest {

	@BeforeTest(alwaysRun = true)
	public void beforeTest() {
		subModuleTest = moduleTest.createNode("Billabilities Tests");
	}

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("Billabilities Tests");
	}
	
	/**
	 * This method will verify the get billabilities api
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void verifyGetBillabilities() throws Exception
	{
		SoftAssert softAssert = new SoftAssert();
		BillabilitiesTestHelper testHelper = new BillabilitiesTestHelper();
		RestUtils restUtils = new RestUtils();
		
		Response response = testHelper.getBillabilities();
		
		validateResponseToContinueTest(response, 200,"Unable to fetch details for get Billabilities api.", true);
		
		String expectedStatus = "SUCCESS";
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'", expectedStatus,
						restUtils.getValueFromResponse(response, "status")));

		String expectedResponseMessage = "Execution Successful";
		softAssert.assertTrue(
				restUtils.getValueFromResponse(response, "message").toString().contains(expectedResponseMessage),
				String.format("Actual message '%s'  does not contains expected message '%s'",
						restUtils.getValueFromResponse(response, "message"), expectedResponseMessage));

		List<LinkedHashMap<String, Object>> billabilitiesDetailsList = (List<LinkedHashMap<String, Object>>) restUtils
				.getValueFromResponse(response, "content");

		int totalResultCount = Integer.parseInt(String.valueOf(restUtils.getValueFromResponse(response, "totalCount")));
		int totalBillabilitiesDetailsCount = billabilitiesDetailsList.size();

		softAssert.assertTrue(totalResultCount == totalBillabilitiesDetailsCount,
				String.format("Total Count displayed is %d and total billabilities details displayed is %d.",
						totalResultCount, totalBillabilitiesDetailsCount));

		LinkedHashMap<String, Object> billabilityDetail = billabilitiesDetailsList.get(0);
			
			
		String actualId = String.valueOf(billabilityDetail.get("id"));
		String expectedId = "1";
		softAssert.assertEquals(actualId,expectedId,
				String.format("Expected Id was '%s' was actual was '%s'.", expectedId,actualId));

		String actualName = billabilityDetail.get("name").toString();
		String expectedName = "billable";
		softAssert.assertEquals(actualName,expectedName,
				String.format("Expected Name was '%s' was actual was '%s'.", expectedName,actualName));

		String actualDescription = billabilityDetail.get("description").toString();
		String expectedDescription = "Bill to Client/Project";
		softAssert.assertEquals(actualDescription,expectedDescription,
				String.format("Expected Description was '%s' was actual was '%s'.", expectedDescription,actualDescription));

		billabilityDetail = billabilitiesDetailsList.get(1);
		
		
		actualId = String.valueOf(billabilityDetail.get("id"));
		expectedId = "2";
		softAssert.assertEquals(actualId,expectedId,
				String.format("Expected Id was '%s' was actual was '%s'.", expectedId,actualId));

		actualName = billabilityDetail.get("name").toString();
		expectedName = "nobillable";
		softAssert.assertEquals(actualName,expectedName,
				String.format("Expected Name was '%s' was actual was '%s'.", expectedName,actualName));

		actualDescription = billabilityDetail.get("description").toString();
		expectedDescription = "Bill to Globant";
		softAssert.assertEquals(actualDescription,expectedDescription,
				String.format("Expected Description was '%s' was actual was '%s'.", expectedDescription,actualDescription));
	

		softAssert.assertAll();

		test.log(Status.PASS, "Get Billabilities API verification was successful.");
		
	}

}
