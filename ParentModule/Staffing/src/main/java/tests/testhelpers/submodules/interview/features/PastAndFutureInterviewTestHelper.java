package tests.testhelpers.submodules.interview.features;

import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.Status;

import endpoints.submodules.interview.features.PastAndFutureInterviewEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.submodules.interview.features.PastAndFutureInterviewTest;
import tests.testhelpers.submodules.interview.InterviewTestHelper;
import utils.ExtentHelper;

/**
 * @author deepakkumar.hadiya
 */

public class PastAndFutureInterviewTestHelper extends InterviewTestHelper{

	/**
	 * To get count of evaluated interviews for previous/current week and accepted interviews for current/next week 
	 * @param dtoType
	 * @param gatekeeperId
	 * @return {@link Response}
	 * @throws Exception
	 */
	public Response getPastAndFutureInterviewDetails(String dtoType, String... gatekeeperId) throws Exception {
		Map<String,Object> queryParams=new HashMap<>();
		for(String gkId:gatekeeperId) {
			queryParams.put(GATEKEEPER_IDS, gkId);
		}
		RequestSpecification requestSpecification = RestAssured.with().header(TOKEN,TOKEN_VALUE).and().queryParams(queryParams);
		String url = PastAndFutureInterviewTest.baseUrl+PastAndFutureInterviewEndpoints.pastAndFutureInterview;
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "Get method for past and future interview count is executed successfully");
		return response;
	}
}
