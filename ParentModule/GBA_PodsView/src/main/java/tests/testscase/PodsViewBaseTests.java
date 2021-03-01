package tests.testscase;
/**
 * @author rachana.jadhav
 *
 */
import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;

import io.restassured.RestAssured;
import properties.PodsViewBaseProperties;
import tests.GlowBaseTest;

public class PodsViewBaseTests extends GlowBaseTest{
	public static String firebaseUrl,schemaValidationBaseUrl;
	
	@BeforeSuite(alwaysRun = true)
	public void beforeSuite(ITestContext context) throws Exception {
		moduleTest=extent.createTest("GBA_PodsView");
		RestAssured.useRelaxedHTTPSValidation();
		new PodsViewBaseProperties().configureProperties();
		baseUrl = PodsViewBaseProperties.baseURL;
		firebaseUrl=PodsViewBaseProperties.firebaseUrl;
		schemaValidationBaseUrl=PodsViewBaseProperties.schemaValidationBaseUrl;
	}
}