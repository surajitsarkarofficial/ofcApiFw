package tests.testcases;

import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;

import io.restassured.RestAssured;
import properties.DeliveryProperties;
import tests.GlowBaseTest;


/**
 * @author imran.khan
 *
 */


public class DeliveryBaseTest extends GlowBaseTest{
	
	@BeforeSuite(alwaysRun = true)
	public void beforeSuite(ITestContext context) throws Exception {
		moduleTest=extent.createTest("Delivery");
		RestAssured.useRelaxedHTTPSValidation();
		new DeliveryProperties().configureProperties();
	}


}
