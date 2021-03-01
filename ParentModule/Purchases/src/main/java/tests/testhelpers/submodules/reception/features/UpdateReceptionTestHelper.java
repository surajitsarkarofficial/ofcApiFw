package tests.testhelpers.submodules.reception.features;

import com.aventstack.extentreports.Status;

import endpoints.submodules.ReceptionEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.reception.ReceptionPayLoadHelper;
import tests.testcases.submodules.reception.ReceptionBaseTest;
import tests.testhelpers.PurchasesTestHelper;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class UpdateReceptionTestHelper extends PurchasesTestHelper {

	public UpdateReceptionTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a PUT in order to update a reception.
	 * @param payLoad
	 * @return response
	 */
	public Response updateReception (ReceptionPayLoadHelper payLoad) {
		String requestURL = ReceptionBaseTest.baseUrl + ReceptionEndpoints.putReception;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ReceptionBaseTest.sessionId).contentType(ContentType.JSON).body(payLoad);
		Response response = restUtils.executePUT(requestSpecification, requestURL);
		ReceptionBaseTest.test.log(Status.INFO, "PUT - User: " + userName);
		ReceptionBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		ReceptionBaseTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(payLoad));
		return response;
	}
	
}
