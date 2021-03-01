package tests.testhelpers.submodules.staffRequest.features.globalstaffrequest;

import java.util.Random;

import database.submodules.staffRequest.features.globalstaffrequest.Top3MatchingGloberDBHelper;
import endpoints.submodules.staffRequest.features.globalstaffrequest.Top3MatchingGlobersEndPoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.StaffingBaseTest;
import tests.testcases.submodules.openpositions.OpenPositionBaseTest;
import tests.testhelpers.submodules.staffRequest.StaffRequestTestHelper;

public class Top3MatchingGlobersTestHelper extends StaffRequestTestHelper{

	String positionId = null;
	Top3MatchingGloberDBHelper machingGloberDb;
	public Top3MatchingGlobersTestHelper(String userName) throws Exception {
		super(userName);
		machingGloberDb = new Top3MatchingGloberDBHelper();
	}

	/**
	 * This method will perform GET to check active plan of matching glober
	 * 
	 * @param response
	 * @return {@link Response}
	 * @author shadab.waikar
	 * @throws Exception 
	 */
	public Response getGloberActivePlan(Response response) throws Exception {
	
		String isGloberAvailable = "true";
		int count = 0,globerCount = 0;
		Response globerResponse = null;	
		int i = new Random().nextInt(50);
		String srNumber = restUtils.getValueFromResponse(response, "details.positionDTOList[" + i + "].srNumber").toString();
		positionId = machingGloberDb.getPositionId(srNumber);

		String url = StaffingBaseTest.baseUrl+ String.format(Top3MatchingGlobersEndPoints.getTop3MatchingGlobersUrl, positionId, isGloberAvailable);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(OpenPositionBaseTest.sessionId);
		globerResponse = restUtils.executeGET(requestSpecification, url);
		validateResponseToContinueTest(globerResponse, 200, "Unable to load top 3 matching globers", true);
		do {
			if(count!=0) {
				globerResponse = checkMatchingGlobersForSr(response);
			}
			globerCount = Integer.parseInt(restUtils.getValueFromResponse(globerResponse, "details.totalCount").toString());
			count++;
		}while(globerCount==0);
		
		return globerResponse;
		
	}
	
	/**
	 * This method will perform GET to fetch top 3 matching globers
	 * 
	 * @param response
	 * @return {@link Response}
	 * @author shadab.waikar
	 * @throws Exception 
	 */
	public Response checkMatchingGlobersForSr(Response response) throws Exception {
		String isGloberAvailable = "true";
		
		int i = new Random().nextInt(40);
		String srNumber = restUtils.getValueFromResponse(response, "details.positionDTOList[" + i + "].srNumber").toString();
		positionId = machingGloberDb.getPositionId(srNumber);
		String url = StaffingBaseTest.baseUrl+ String.format(Top3MatchingGlobersEndPoints.getTop3MatchingGlobersUrl, positionId, isGloberAvailable);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(OpenPositionBaseTest.sessionId);
		Response globerResponse = restUtils.executeGET(requestSpecification, url);
		validateResponseToContinueTest(globerResponse, 200, "Unable to load top 3 matching globers", true);
		
		return globerResponse;
	}
	
	/**
	 * This method will positionId of staff request
	 * @return {@link String}
	 * @author shadab.waikar
	*/
	public String getPositionId() {
		return positionId;
	}
}
