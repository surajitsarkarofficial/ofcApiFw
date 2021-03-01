package tests.testhelpers.submodules.manage.features.configureAmount;

import com.aventstack.extentreports.Status;

import dto.submodules.manage.configureAmount.getSafeMatrixRoles.Content;
import dto.submodules.manage.configureAmount.getSafeMatrixRoles.SafeMatrixRolesResponseDTO;
import endpoints.submodules.manage.features.ConfigureAmountEndpoints;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class GetSafeMatrixRolTestHelper extends ManageTestHelper {

	public GetSafeMatrixRolTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a GET in order to fetch safe matrix roles.
	 * @return response
	 */
	public Response getRoles() {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(ManageBaseTest.sessionId);
		String url = ManageBaseTest.baseUrl + String.format(ConfigureAmountEndpoints.getSafeMatrixRoles);
		Response response = restUtils.executeGET(requestSpecification, url);
		ManageBaseTest.test.log(Status.INFO, "GET - User: " + userName);
		ManageBaseTest.test.log(Status.INFO, "RequestURL: " + url);
		return response;
	}

	/**
	 * This method will return the SafeMatrixRolesResponseDTO list.
	 * @return roles
	 * @author german.massello
	 */
	public SafeMatrixRolesResponseDTO getRolesDTOList() {
		Response response = getRoles();
		SafeMatrixRolesResponseDTO roles = response.as(SafeMatrixRolesResponseDTO.class, ObjectMapperType.GSON);
		return roles;
	}

	/**
	 * This method will return a random safe matrix rol
	 * @return rol
	 * @author german.massello
	 */
	public Content getRandomRol() {
		SafeMatrixRolesResponseDTO roles = getRolesDTOList();
		Content rol;
		do rol = roles.getContent().get(Utilities.getRandomNumberBetween(0, roles.getContent().size()));
			while (rol.getDescription().equals("Unlimited Amount approvers"));
		return rol;
	}
}
