package tests.testcases.submodules.gatekeepers;

import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;

import constants.submodules.gatekeepers.GatekeepersConstants;
import io.restassured.RestAssured;
import properties.glow7.InquistorProperties;
import tests.testcases.StaffingBaseTest;

/**
 * @author deepakkumar.hadiya
 */

public class GatekeepersBaseTest extends StaffingBaseTest implements GatekeepersConstants{

	@BeforeTest(alwaysRun = true)
	public void beforeSuite(ITestContext context) throws Exception {
		subModuleTest=extent.createTest("Gatekeepers");
		RestAssured.useRelaxedHTTPSValidation();
		new InquistorProperties().configureProperties();
	}
	
}
