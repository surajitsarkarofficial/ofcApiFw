package tests.testhelpers.submodules.manage.features.configureAmount;

import com.aventstack.extentreports.Status;

import dto.submodules.manage.configureAmount.rol.RolResponseDTO;
import endpoints.submodules.manage.features.ConfigureAmountEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;

/**
 * @author german.massello
 *
 */
public class DeleteRolTestHelper extends ManageTestHelper {

	public DeleteRolTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a DELETE in order to remove a rol.
	 * @param rol
	 * @return response
	 */
	public Response deleteRol(RolResponseDTO rol) {
		String requestURL = ManageBaseTest.baseUrl + String.format(ConfigureAmountEndpoints.deleteRol, rol.getContent().getId());
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ManageBaseTest.sessionId).contentType(ContentType.JSON);
		Response response = restUtils.executeDELETE(requestSpecification, requestURL);
		ManageBaseTest.test.log(Status.INFO, "DELETE - User: " + userName);
		ManageBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		return response;
	}
	
}
