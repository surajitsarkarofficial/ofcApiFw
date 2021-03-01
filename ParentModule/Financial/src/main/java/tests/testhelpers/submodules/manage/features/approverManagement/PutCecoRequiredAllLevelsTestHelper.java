package tests.testhelpers.submodules.manage.features.approverManagement;

import com.aventstack.extentreports.Status;

import endpoints.submodules.manage.features.ApproverManagementEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import properties.FinancialProperties;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;
import utils.RestUtils;

/**
 * @author german.massello
 *
 */
public class PutCecoRequiredAllLevelsTestHelper extends ManageTestHelper {

	public PutCecoRequiredAllLevelsTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a PUT in order to update the required all levels field from a ceco.
	 * @param cecoNumber
	 * @param status
	 * @return Response
	 * @throws Exception
	 */
	public Response putCecoRequiredAllLevels(String cecoNumber, String status) throws Exception {
		String requestURL = FinancialProperties.baseURL + String.format(ApproverManagementEndpoints.putCecoRequiredAllLevels,
				cecoNumber, status);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(ManageBaseTest.sessionId).contentType(ContentType.JSON);
		Response response = new RestUtils().executePUT(requestSpecification, requestURL);
		ManageBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		ManageBaseTest.test.log(Status.INFO, "The ceco was updated successful.");
		return response;
	}
}
