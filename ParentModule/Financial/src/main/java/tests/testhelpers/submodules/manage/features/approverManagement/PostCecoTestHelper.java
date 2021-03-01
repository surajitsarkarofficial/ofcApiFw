package tests.testhelpers.submodules.manage.features.approverManagement;

import dto.submodules.manage.approverManagement.postCeco.PostCecoResponseDTO;
import endpoints.submodules.manage.features.ApproverManagementEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.manage.features.approverManagement.CecoPayLoadHelper;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;

/**
 * @author german.massello
 *
 */
public class PostCecoTestHelper extends ManageTestHelper {

	public PostCecoTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a POST in order to create a new ceco.
	 * @param payLoad
	 * @return response
	 * @throws Exception 
	 */
	public Response postCeco(CecoPayLoadHelper payLoad) throws Exception {
		String requestURL = ManageBaseTest.baseUrl + String.format(ApproverManagementEndpoints.postCeco);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ManageBaseTest.sessionId).contentType(ContentType.JSON).body(payLoad);
		Response response = restUtils.executePOST(requestSpecification, requestURL);
		return response;
	}
	
	/**
	 * This method will create a ceco from scratch.
	 * @return PostCecoResponseDTO
	 * @throws Exception
	 */
	public PostCecoResponseDTO postCecoFromScratch() throws Exception {
	      CecoPayLoadHelper payload = new CecoPayLoadHelper();
	      Response response = postCeco(payload);
	      validateResponseToContinueTest(response, 201, "Post ceco api call was not successful.", true);
	      PostCecoResponseDTO ceco = response.as(PostCecoResponseDTO.class, ObjectMapperType.GSON);
	      return ceco;
	}

}
