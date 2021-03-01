package tests.testscase.submodules.homepageTestcases.features;
/**
 * @author rachana.jadhav
 *
 */
import static org.testng.Assert.assertEquals;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.PodsViewBaseDataProviders;
import io.restassured.response.Response;
import tests.testhelper.submodules.homepage.features.ContactTestHelper;
import tests.testscase.submodules.homepageTestcases.HomePageBaseTest;
import utils.RestUtils;
public class ContactsTest extends HomePageBaseTest{
	String firebaseMessage, glowMessage= null;
	Boolean successMessage=null;
	
	RestUtils restUtils = new RestUtils();
	SoftAssert softAssert = new SoftAssert();

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("ContactsTest");
	}

	/** This test will verify Contact details like contact ID,contact name, contact email,contact role from Firebase and Glow are same
	 * @throws Exception
	 *  @param name
	 * @param id
	 * @param position
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "userData", dataProviderClass = PodsViewBaseDataProviders.class)
	public void verifyContactDetails(String name, String id, String position) throws Exception{

		ContactTestHelper ContactsHelper = new ContactTestHelper();
		Response glowResponse =ContactsHelper.getGlowContactDetails(name,id,position);
		Response firebaseResponse =ContactsHelper.getFirebaseContactDetails(name,id);
		firebaseMessage = (String) restUtils.getValueFromResponse(firebaseResponse, "message");
		glowMessage = (String) restUtils.getValueFromResponse(glowResponse, "message");
		softAssert.assertTrue(firebaseMessage.contains("Success"),"Failed in validating message");
		softAssert.assertTrue(glowMessage.contains("success"),"Failed in validating message");

		List<String> firebaseContactId = (List<String>) restUtils.getValueFromResponse(firebaseResponse, "data..contacts..clientId");
		List<String> glowContactId = (List<String>) restUtils.getValueFromResponse(glowResponse, "details..contacts.[*].id");
		softAssert.assertEquals(glowContactId, firebaseContactId,String.format("Expected data was '%s' and actual was '%s'", firebaseContactId,glowContactId));

		List<String> firebaseContactName = (List<String>) restUtils.getValueFromResponse(firebaseResponse,"data..contacts..clientName");
		List<String> glowContactName = (List<String>) restUtils.getValueFromResponse(glowResponse,"details..contacts.[*].name");
		softAssert.assertEquals(glowContactName, firebaseContactName,String.format("Expected data was '%s' and actual was '%s'", firebaseContactName,glowContactName));

		List<String> firebaseContactRole = (List<String>) restUtils.getValueFromResponse(firebaseResponse,"data..contacts..contactRole");
		List<String> glowContactRole = (List<String>) restUtils.getValueFromResponse(glowResponse,"details..contacts..role");
		softAssert.assertEquals(glowContactRole, firebaseContactRole,String.format("Expected data was '%s' and actual was '%s'", firebaseContactRole,glowContactRole));

		List<String> firebaseContactEmail = (List<String>) restUtils.getValueFromResponse(firebaseResponse,"data..contacts..contactEmail");
		List<String> glowContactEmail = (List<String>) restUtils.getValueFromResponse(glowResponse,"details..contacts..email");
		assertEquals(glowContactEmail, firebaseContactEmail,String.format("Expected data was '%s' and actual was '%s'", firebaseContactEmail,glowContactEmail));
		softAssert.assertAll();

	}

	/** This test will validate Contact schema response is as expected 
	 * @throws Exception
	 *  @param name
	 * @param id
	 * @param token
	 */
	@Test(dataProvider = "schemaData", dataProviderClass = PodsViewBaseDataProviders.class)
	public void verifyContactSchema(String name, String id, String token) throws Exception{

		ContactTestHelper ContactTestHelper = new ContactTestHelper();
		Response firebaseResponse = ContactTestHelper.contactSchemaValidation(name, id, token);
		successMessage = (Boolean) restUtils.getValueFromResponse(firebaseResponse, "success");
		softAssert.assertTrue(Boolean.TRUE,"Failed in validating Success message");
		softAssert.assertAll();
	}    
}
