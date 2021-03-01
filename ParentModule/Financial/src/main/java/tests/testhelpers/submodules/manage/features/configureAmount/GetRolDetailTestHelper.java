package tests.testhelpers.submodules.manage.features.configureAmount;

import com.aventstack.extentreports.Status;

import dto.submodules.manage.configureAmount.getSafeMatrixRoles.Content;
import endpoints.submodules.manage.features.ConfigureAmountEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;

/**
 * @author german.massello
 *
 */
public class GetRolDetailTestHelper extends ManageTestHelper {

	public GetRolDetailTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a GET in order to fetch safe matrix rol details.
	 * @param rol
	 * @return response
	 */
	public Response getRolDetail(Content rol) {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(ManageBaseTest.sessionId);
		String url = ManageBaseTest.baseUrl + String.format(ConfigureAmountEndpoints.getSafeMatrixRolDetail, rol.getId());
		Response response = restUtils.executeGET(requestSpecification, url);
		ManageBaseTest.test.log(Status.INFO, "GET - User: " + userName);
		ManageBaseTest.test.log(Status.INFO, "RequestURL: " + url);
		return response;
	}
	
}
