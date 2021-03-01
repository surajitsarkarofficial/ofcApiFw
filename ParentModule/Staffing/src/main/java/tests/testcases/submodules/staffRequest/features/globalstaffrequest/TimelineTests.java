package tests.testcases.submodules.staffRequest.features.globalstaffrequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import database.submodules.staffRequest.features.globalstaffrequest.TimelineDBHelper;
import dataproviders.submodules.staffRequest.features.globalstaffrequest.GlobalStaffRequestDataProvider;
import io.restassured.response.Response;
import payloads.submodules.staffRequest.features.globalstaffrequest.QuickSuggestAndAssignPayloadHelper;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import tests.testhelpers.StaffingTestHelper;
import tests.testhelpers.submodules.staffRequest.features.globalstaffrequest.TimelineTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.StaffingUtilities;

public class TimelineTests extends StaffRequestBaseTest{

	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	ArrayList<String> stgData = new ArrayList<>(Arrays.asList("In Pipe", "Candidate", "New Hire", "Proposal Approved"));
	TimelineTestHelper timelineHelper;
	QuickSuggestAndAssignPayloadHelper suggestAndAssignHelper;
	TimelineDBHelper timelineDb;
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("GlobalStaffRequest");
	}
	
	/**
	 * This Test will suggest glober via Timeline Page
	 * 
	 * @param username
	 * @param globerType
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "globerAssignmnentAccessRoleUsers", dataProviderClass = GlobalStaffRequestDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifySuggestGloberViaTimelinePage(String username,String globerType) throws Exception {
		timelineHelper = new TimelineTestHelper(username);
		suggestAndAssignHelper = new QuickSuggestAndAssignPayloadHelper(username);
		
		Response response = new StaffingTestHelper().getSRDetailsFromGrid(username, globerType);
		validateResponseToContinueTest(response, 200, "Unable to load SR Grid.", true);
		
		Response suggestGloberResponse = timelineHelper.suggestGlober(response, username);
		validateResponseToContinueTest(suggestGloberResponse, 201, "Unable to Suggest Glober.", true);
		
		String msg = restUtils.getValueFromResponse(suggestGloberResponse, "message").toString();
		Assert.assertTrue(msg.contains("Success"), "Failed to create plan.");
		test.log(Status.PASS, "Glober Suggested Successfully.");
		
		String staffPlanId = restUtils.getValueFromResponse(suggestGloberResponse, "details[0].staffPlanId").toString();
		suggestAndAssignHelper.updateCreatedPlanStatus(staffPlanId);
	}
	
	/**
	 * This Test will dismiss glober from Timeline Page
	 * 
	 * @param username
	 * @param globerType
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "globerAssignmnentAccessRoleUsers", dataProviderClass = GlobalStaffRequestDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyDismissGloberViaTimeline(String username,String globerType) throws Exception {
		timelineHelper = new TimelineTestHelper(username);
		
		Response response = new StaffingTestHelper().getSRDetailsFromGrid(username, globerType);
		validateResponseToContinueTest(response, 200, "Unable to load SR Grid.", true);
		
		Response dismissGloberRsponse = timelineHelper.dismissGlober(response);
		validateResponseToContinueTest(dismissGloberRsponse, 201, "Unable to Dismiss Glober.", true);
		
		String status = restUtils.getValueFromResponse(dismissGloberRsponse, "status").toString();
		
		Assert.assertEquals(status,"success", "Failed to Dismiss Glober.");
		test.log(Status.PASS, "Glober Dismissed Successfully.");
	}
	
	/**
	 * This Test will verify maximum 3 suggestions per SR from Timeline Page
	 * 
	 * @param username
	 * @param globerType
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(enabled = true, groups = {ExeGroups.Regression,"Glow-II"})
	public void verifyMaxSuggestionsLimintViaTimeline() throws Exception {
		String username = "anand.datir";
		timelineHelper = new TimelineTestHelper(username);
		
		Response suggestGloberResponse = timelineHelper.suggestGloberThreeTimesToVerifyWarnings(username);
		validateResponseToContinueTest(suggestGloberResponse, 400, "Issue with suggesting glober over timeline.", true);
	   
		String msg = restUtils.getValueFromResponse(suggestGloberResponse, "message").toString();
		Assert.assertTrue(msg.contains("Sorry, max 3 Suggestions per SR."), "Issue with max. suggestion per SR via timeline.");
		test.log(Status.PASS, "Glober Suggestion Warning Message is Validated.");
	}
	
	/**
	 * This Test will verify available globers on timeline
	 * 
	 * @param username
	 * @param globerType
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "globerAssignmnentAccessRoleUsers", dataProviderClass = GlobalStaffRequestDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyAvailableGlobersOnTimelinePage(String username,String globerType) throws Exception {
		int i = 0;
		
		timelineHelper = new TimelineTestHelper(username);
		timelineDb = new TimelineDBHelper();
		
		Response response = new StaffingTestHelper().getSRDetailsFromGrid(username, globerType);
		validateResponseToContinueTest(response, 200, "Unable to load SR Grid.", true);
		
		Response availableGloberResponse = timelineHelper.getTopAvailableGlobersOnTimeline(response);
		validateResponseToContinueTest(availableGloberResponse, 200, "Unable to Fetch Available Globers from Timeline.", true);
		
		String status = restUtils.getValueFromResponse(availableGloberResponse, "status").toString();
		Assert.assertEquals(status,"success", "Issue with timeline available globers response.");
		test.log(Status.PASS, "Available Globers Fetch Successfully.");
		String globerCount = restUtils.getValueFromResponse(availableGloberResponse, "details.totalCount").toString();
		
		
		Date expectedGloberDate = StaffingUtilities.getFutureDateInDateFormat(30);
		List<String> globerDates = timelineHelper.getResponseDateInDateForamt(availableGloberResponse);
		
		if(globerDates.size() == 0) {
			Assert.assertEquals(globerCount,"0", "Issue with StaffRequest : "+timelineHelper.srNumber+"Response : "+availableGloberResponse.prettyPrint());
		}else {
			for(String date : globerDates) {
				if(date == null) {
					String globerId = restUtils.getValueFromResponse(availableGloberResponse, "details.asGloberList[" + i + "].globerId").toString();
				    String type = timelineDb.getGloberTypeByGloberId(globerId);
				    Assert.assertTrue(stgData.contains(type), "stgTyes doesn't contain :"+type);
				    test.log(Status.PASS, "STGs are Validated.");
				}else {
					Date actualGloberDate = sdf.parse(date);
					Assert.assertTrue(actualGloberDate.before(expectedGloberDate),
							String.format("Actual Date : %s is not before Expected Date  : %s", sdf.format(actualGloberDate),sdf.format(expectedGloberDate)));
					 test.log(Status.PASS, "Available Glober Dates are Validated.");
				}
				i++;
			}
		}
	}
	
	/**
	 * This Test will verify available globers on timeline with one,two and four months filters
	 * 
	 * @param username
	 * @param no_of_days
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "timelineAccessRoleUsers", dataProviderClass = GlobalStaffRequestDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyTop30MatchingGloberWithFilters(String username,String no_of_days) throws Exception {
	
		int i = 0;
		timelineHelper = new TimelineTestHelper(username);
		timelineDb = new TimelineDBHelper();
		
		Response response = new StaffingTestHelper().getSRDetailsFromGrid(username, no_of_days);
		validateResponseToContinueTest(response, 200, "Unable to load SR Grid.", true);
		
		Response top30GlobersResponse = timelineHelper.getTopAvailableGlobersOnTimelineWithFilters(response, no_of_days);
		validateResponseToContinueTest(top30GlobersResponse, 200, "Unable to load top 30 globers with filters: "+no_of_days, true);
	
		String status = restUtils.getValueFromResponse(top30GlobersResponse, "status").toString();
		Assert.assertEquals(status,"success", "Issue with timeline available globers response.");
		test.log(Status.PASS, "Available Globers Fetch Successfully.");
		String globerCount = restUtils.getValueFromResponse(top30GlobersResponse, "details.totalCount").toString();
		
		Date expectedGloberDate = StaffingUtilities.getFutureDateInDateFormat(Integer.parseInt(no_of_days));
		List<String> globerDates = timelineHelper.getResponseDateInDateForamt(top30GlobersResponse);
		
		if(globerDates == null) {
			Assert.assertEquals(globerCount,"0", "Issue with StaffRequest : "+timelineHelper.srNumber+"Response : "+top30GlobersResponse.prettyPrint());
		}else {
			for(String date : globerDates) {
				if(date == null) {
					String globerType = restUtils.getValueFromResponse(top30GlobersResponse, "details.asGloberList[" + i + "].type").toString();
				    Assert.assertTrue(stgData.contains(globerType), "stgTyes doesn't contain globerType :"+globerType);
				    test.log(Status.PASS, "STGs are Validated.");
				}else {
					 Date actualGloberDate = sdf.parse(date);
						if (actualGloberDate.before(expectedGloberDate))
							Assert.assertTrue(actualGloberDate.before(expectedGloberDate),
									String.format("Actual Date : %s is not before Expected Date  : %s", sdf.format(actualGloberDate),sdf.format(expectedGloberDate)));
						else
							Assert.assertTrue(actualGloberDate.after(expectedGloberDate),
									String.format("Actual Date : %s and Expected Date  : %s", sdf.format(actualGloberDate),sdf.format(expectedGloberDate)));
					test.log(Status.PASS, "Top 30 Glober Dates are Validated.");
				}
				i++;
			}
		}
	}
}
