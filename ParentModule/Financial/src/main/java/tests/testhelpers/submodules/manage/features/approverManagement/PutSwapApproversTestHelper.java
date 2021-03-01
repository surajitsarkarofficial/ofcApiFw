package tests.testhelpers.submodules.manage.features.approverManagement;

import com.aventstack.extentreports.Status;

import endpoints.submodules.manage.features.ApproverManagementEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.manage.features.approverManagement.putSwapApprovers.SwapApproversPayLoadHelper;
import properties.FinancialProperties;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class PutSwapApproversTestHelper extends ManageTestHelper {

	public PutSwapApproversTestHelper(String userName) throws Exception {
		super(userName);
		this.userName=userName;
	}

	/**
	 * This method will perform a PUT in order to replace an approver.
	 * @param payload
	 * @return Response
	 * @throws Exception
	 */
	public Response putSwapApprovers(SwapApproversPayLoadHelper payload) throws Exception {
		String requestURL = FinancialProperties.baseURL + String.format(ApproverManagementEndpoints.putSwapApprover);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ManageBaseTest.sessionId).contentType(ContentType.JSON).body(payload);		
		Response response = new RestUtils().executePUT(requestSpecification, requestURL);
		ManageBaseTest.test.log(Status.INFO, "User: " + this.userName);
		ManageBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		ManageBaseTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(payload));
		return response;
	}

}
