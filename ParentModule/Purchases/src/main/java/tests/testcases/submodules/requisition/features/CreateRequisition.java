package tests.testcases.submodules.requisition.features;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.requisition.PostRequisitionDataProviders;
import dto.submodules.requisition.post.PostRequisitionResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.requisition.PostRequisitionParameters;
import payloads.submodules.requisition.RequisitioPayLoadHelper;
import tests.testcases.submodules.requisition.RequisitionBaseTest;
import tests.testhelpers.submodules.requisition.features.PostRequisitionTestHelper;
import utils.Utilities;
import utils.UtilsBase;

/**
 * @author german.massello
 *
 */
public class CreateRequisition extends RequisitionBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("CreateRequisition");
	}

	/**
	 * Create draft requisition
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "createRequisition", dataProviderClass = PostRequisitionDataProviders.class)
	public void createDraftRequisition(PostRequisitionParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		RequisitioPayLoadHelper payload = new RequisitioPayLoadHelper(parameters);
		PostRequisitionTestHelper helper = new PostRequisitionTestHelper(parameters.getUserName());
		Response response = helper.createRequisition(payload);
		validateResponseToContinueTest(response, 201, "POST requisition api call was not successful.", true);
		PostRequisitionResponseDTO requisition = response.as(PostRequisitionResponseDTO.class, ObjectMapperType.GSON);
		UtilsBase.log("Created requisition: " + Utilities.convertJavaObjectToJson(requisition));
		softAssert.assertEquals(requisition.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(requisition.getMessage(), "Created", "getMessage issue");
		softAssert.assertAll();
	}

}
