package tests.testhelpers.submodules.manage.features.approverManagement;

import com.aventstack.extentreports.Status;

import endpoints.submodules.manage.features.ApproverManagementEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.manage.features.approverManagement.massiveApproverCeco.MassiveApproverCecoPayLoadHelper;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class DeleteMassiveApproverCecoTestHelper extends ManageTestHelper {

	public DeleteMassiveApproverCecoTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a DELETE in order to delete an approver from multiples cecos.
	 * @param payLoad
	 * @return Response
	 * @throws Exception
	 */
	public Response deleteMassiveApproverCeco(MassiveApproverCecoPayLoadHelper payLoad) throws Exception {
		String requestURL = ManageBaseTest.baseUrl + String.format(ApproverManagementEndpoints.deleteMassiveApproverCeco);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ManageBaseTest.sessionId).contentType(ContentType.JSON).body(payLoad);
		Response response = restUtils.executePOST(requestSpecification, requestURL);
		ManageBaseTest.test.log(Status.INFO, "User: " + this.userName);
		ManageBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		ManageBaseTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(payLoad));
		return response;
	}

}
