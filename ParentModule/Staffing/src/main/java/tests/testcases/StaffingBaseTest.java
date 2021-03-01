package tests.testcases;

import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;

import io.restassured.RestAssured;
import properties.StaffingProperties;
import tests.GlowBaseTest;
import utils.RestUtils;

public class StaffingBaseTest extends GlowBaseTest{
	
	protected RestUtils restUtils;

	public StaffingBaseTest() {
		restUtils=restUtils==null?new RestUtils():this.restUtils;
	}
	
	@BeforeSuite(alwaysRun = true)
	public void beforeSuite(ITestContext context) throws Exception {
		moduleTest=extent.createTest("Staffing");
		RestAssured.useRelaxedHTTPSValidation();
		new StaffingProperties().configureProperties();
	}
}
