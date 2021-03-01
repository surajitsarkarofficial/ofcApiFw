package tests.testhelpers.submodules.manage.features.configureAmount;

import com.aventstack.extentreports.Status;

import endpoints.submodules.manage.features.ConfigureAmountEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.manage.features.configureAmount.rol.RolPayLoadHelper;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class PutSafeMatrixRolTestHelper extends ManageTestHelper {

	public PutSafeMatrixRolTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a PUT in order to update a safe matrix rol.
	 * @param payLoad
	 * @return response
	 */
	public Response putRol(RolPayLoadHelper payLoad) {
		String requestURL = ManageBaseTest.baseUrl + String.format(ConfigureAmountEndpoints.putRol);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ManageBaseTest.sessionId).contentType(ContentType.JSON).body(payLoad);
		Response response = restUtils.executePUT(requestSpecification, requestURL);
		ManageBaseTest.test.log(Status.INFO, "PUT - User: " + userName);
		ManageBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		ManageBaseTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(payLoad));
		return response;
	}
	
}
