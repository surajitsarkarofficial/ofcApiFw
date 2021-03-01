package test.testcases;

import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;

import io.restassured.RestAssured;
import properties.GlobantPodsProperties;
import tests.GlowBaseTest;

/**
 * @author ankita.manekar
 *
 */
public class GlobantPodsTest extends GlowBaseTest {
	public static String firebaseUrl, schemaValidationBaseUrl,generateTokenBaseUrl;

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite(ITestContext context) throws Exception {
		moduleTest = extent.createTest("GBA_GlobantPods");
		RestAssured.useRelaxedHTTPSValidation();
		new GlobantPodsProperties().configureProperties();
		baseUrl = GlobantPodsProperties.baseURL;
		firebaseUrl = GlobantPodsProperties.firebaseUrl;
		schemaValidationBaseUrl = GlobantPodsProperties.schemaValidationBaseUrl;
		generateTokenBaseUrl = GlobantPodsProperties.generateTokenBaseUrl;
	}

}
