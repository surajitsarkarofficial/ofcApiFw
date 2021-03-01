package tests.testhelpers.submodules.gatekeepers.features;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aventstack.extentreports.Status;

import constants.submodules.gatekeepers.GatekeepersConstants;
import constants.submodules.interview.InterviewConstants;
import endpoints.submodules.gatekeepers.features.SearchGatekeepersEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.submodules.gatekeepers.features.SearchGatekeepersTest;
import tests.testhelpers.submodules.gatekeepers.GatekeepersTestHelper;
import utils.ExtentHelper;
import utils.StaffingUtilities;

/**
 * @author deepakkumar.hadiya
 */

public class SearchGatekeepersTestHelper extends GatekeepersTestHelper implements GatekeepersConstants, InterviewConstants{
	

	/**
	 * This Method is to get all available gatekeepers based on position, location and seniority using GET method
	 * @param data
	 * @return {@link Response}
	 * @throws Exception
	 */
	public Response getGatekeepersList(Map<Object, Object> data) throws Exception {
		String headerKey = data.get(HEADER_KEY).toString();
		String headerValue = data.get(HEADER_VALUE).toString();
		Map<String,Object> queryParams=new HashMap<>();
		queryParams.put(SENIORITY, data.get(SENIORITY));
		queryParams.put(POSITION, data.get(POSITION));
		queryParams.put(LOCATION, data.get(LOCATION));
		queryParams.put(CANDIDATE_SKILLS, data.get(CANDIDATE_SKILLS) );
		queryParams.put(DTO_TYPE, data.get(DTO_TYPE));
		String interviewDay = StaffingUtilities.convertMilliSecondIntoDateWithTimeZone(data.get(INTERVIEW_DATE).toString(), DATABASE_DATE_FORMAT, DATABASE_TIMEZONE);
		queryParams.put(INTERVIEW_DAY, interviewDay);
		RequestSpecification requestSpecification = RestAssured.with().header(headerKey, headerValue).queryParams(queryParams);
		String url = SearchGatekeepersTest.baseUrl+SearchGatekeepersEndpoints.searchGatekeeper;
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "Get gatekeepers method is executed successfully");
		return response;
	}

	/**
	 * This Method is to get all available gatekeepers using POST method (which is invalid for request)
	 * @param data
	 * @return {@link Response}
	 * @throws Exception
	 */
	
	public Response getGatekeepersListUsingInvalidMethod(Map<Object, Object> data) throws Exception {
		String headerKey = data.get(HEADER_KEY).toString();
		String headerValue = data.get(HEADER_VALUE).toString();
		Map<String,Object> queryParams=new HashMap<>();
		queryParams.put(SENIORITY, data.get(SENIORITY));
		queryParams.put(POSITION, data.get(POSITION));
		queryParams.put(LOCATION, data.get(LOCATION));
		queryParams.put(CANDIDATE_SKILLS, data.get(CANDIDATE_SKILLS) );
		queryParams.put(DTO_TYPE, data.get(DTO_TYPE));
		RequestSpecification requestSpecification = RestAssured.with().header(headerKey, headerValue).queryParams(queryParams);
		String url = SearchGatekeepersTest.baseUrl+SearchGatekeepersEndpoints.searchGatekeeper;
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executePOST(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "Get gatgatekeepers with post method is executed successfully");
		return response;
	}
	
	/**
	 * This Method is to get all available gatekeepers based on position, location and seniority using GET method with Invalid URL
	 * @param data
	 * @return {@link Response}
	 * @throws Exception
	 */
	public Response getGatekeepersListUsingInvalidUrl(Map<Object, Object> data) throws Exception {
		String headerKey = data.get(HEADER_KEY).toString();
		String headerValue = data.get(HEADER_VALUE).toString();
		Map<String,Object> queryParams=new HashMap<>();
		queryParams.put(SENIORITY, data.get(SENIORITY));
		queryParams.put(POSITION, data.get(POSITION));
		queryParams.put(LOCATION, data.get(LOCATION));
		queryParams.put(CANDIDATE_SKILLS, data.get(CANDIDATE_SKILLS) );
		queryParams.put(DTO_TYPE, data.get(DTO_TYPE));
		RequestSpecification requestSpecification = RestAssured.with().header(headerKey, headerValue).queryParams(queryParams);
		String url = SearchGatekeepersTest.baseUrl+SearchGatekeepersEndpoints.searchGatekeeper.replace("gatekeepers", "gatkeepers");
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "Get gatekeepers with invalid url is executed successfully");
		return response;
	}
	
	/**
	 * This method is used to get gatekeepers list with rank based on position, location and seniority
	 * @param data
	 * @return {@link Map}
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer,List<String>> getGatekeepersListWithRank(Map<Object, Object> data) throws Exception{

		Response apiResponse=getGatekeepersList(data);
		validateResponseToContinueTest(apiResponse, 200, "Unable to search gatekeepers with valid test data", true);
		
		Map<Integer, List<String>> rankDetails=new HashMap<Integer, List<String>>();
		boolean isRankAvailable=true;
		int rank=1;
		
		while(isRankAvailable) {
			List<String> globerIdList = (List<String>) restUtils.getValueFromResponse(apiResponse, "$..details[?(@.rank=="+rank+")]..globarid");
			if(globerIdList.size()>0) {
				rankDetails.put(rank++, globerIdList);
			}else {
				isRankAvailable=false;
			}
		}
		return rankDetails;
	}
}
