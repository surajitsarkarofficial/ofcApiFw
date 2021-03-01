package tests.testcases;

import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;

import io.restassured.RestAssured;
import properties.FinancialProperties;
import tests.GlowBaseTest;

public class FinancialBaseTest extends GlowBaseTest {
	
	@BeforeSuite(alwaysRun = true)
	public void beforeSuite(ITestContext context) throws Exception {
		moduleTest=extent.createTest("Financial");
		RestAssured.useRelaxedHTTPSValidation();
		new FinancialProperties().configureProperties();
	}

}
