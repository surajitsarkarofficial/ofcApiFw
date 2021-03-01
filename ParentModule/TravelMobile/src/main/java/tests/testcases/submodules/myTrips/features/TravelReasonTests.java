package tests.testcases.submodules.myTrips.features;

import java.util.LinkedHashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import tests.testcases.submodules.myTrips.MyTripsBaseTest;
import tests.testhelpers.submodules.myTrips.features.TravelReasonTestsHelper;
import utils.RestUtils;

public class TravelReasonTests extends MyTripsBaseTest{
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("TravelReasonTests");
	}
	
	@Test()
	public void verifyGetTravelReason() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		TravelReasonTestsHelper testHelper = new TravelReasonTestsHelper();

		RestUtils restUtils = new RestUtils();

		Response response = testHelper.executeGetTravelReasonsAPI();

		validateResponseToContinueTest(response, 200, "Unable to fetch Travel Reason.", true);

		String expectedResponseMessage = "Travel Reasons";
		String expectedStatus = "SUCCESS";
		String actualResponseMessage = restUtils.getValueFromResponse(response, "message").toString();
		String actualStatus= restUtils.getValueFromResponse(response, "status").toString();
		softAssert.assertEquals(actualResponseMessage, expectedResponseMessage, String.format(
				"Expected message was '%s' and actual was '%s'", expectedResponseMessage, actualResponseMessage));
		softAssert.assertEquals(actualStatus, expectedStatus, String.format(
				"Expected status was '%s' and actual was '%s'", expectedStatus, actualStatus));
		JSONArray content =(JSONArray) restUtils.getValueFromResponse(response, "content");
		int len=content.size();
		
		softAssert.assertTrue(len>0,"Travel Reason was fetched successfully.");
		
		if(len>0)
		{
			@SuppressWarnings("unchecked")
			LinkedHashMap<String,Object> object = (LinkedHashMap<String, Object>) content.get(0);
			String id  = String.valueOf(object.get("id"));
			String code = String.valueOf(object.get("code"));
			String description = (String) object.get("description");
			boolean isActive = (boolean) object.get("active");
			softAssert.assertTrue(id!=null && id!="","'id' was not present for travel reason.");
			softAssert.assertTrue(code!=null && id!="","'code' was not present for travel reason.");
			softAssert.assertTrue(description!=null && description!="","'description' was not present for travel reason.");
			softAssert.assertTrue(isActive==true,"'active' was not set to TRUE for travel reason.");
			
		}
		
		softAssert.assertAll();
		test.log(Status.PASS, "All data was fetched successfully.Get Trip Reason verification was successful.");

	}
}
