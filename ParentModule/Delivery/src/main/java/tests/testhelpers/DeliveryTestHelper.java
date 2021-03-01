package tests.testhelpers;


import com.aventstack.extentreports.Status;

import endpoints.DeliveryEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.GlowBaseTest;
import tests.GlowBaseTestHelper;
import utils.RestUtils;


/**
 * @author imran.khan
 *
 */

public class DeliveryTestHelper extends GlowBaseTestHelper{
	
	
	
	
	/**
	 * This method will return the project members for given projectId
	 * @param globerName
	 * @param projectId
	 * @return response of getProjectMemberDetailsWithPagination
	 * @throws Exception 
	 */
	public Response getProjectMemberDetailsWithPagination(String globerName, int projectId) throws Exception {
		GlowBaseTest glowBaseTest = new GlowBaseTest();
		GlowBaseTest.sessionId = new GlowBaseTest().createSession(globerName);
		RestUtils restUtils = new RestUtils();	
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(GlowBaseTest.sessionId);
		String url = GlowBaseTest.baseUrl+String.format(DeliveryEndpoints.projectMembersForProjectWithPaginationtest, projectId, "true", 1);
		Response response = restUtils.executeGET(requestSpecification, url);
		glowBaseTest.validateResponseToContinueTest(response, 200,"Unable to fetch Glober Details.",true);
		GlowBaseTest.test.log(Status.INFO,"Glober details fetched successfully.");
		return response;

	}
	

}
