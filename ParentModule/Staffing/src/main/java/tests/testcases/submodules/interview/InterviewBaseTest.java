package tests.testcases.submodules.interview;

import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;

import constants.submodules.gatekeepers.GatekeepersConstants;
import constants.submodules.interview.InterviewConstants;
import io.restassured.RestAssured;
import properties.glow7.InquistorProperties;
import tests.testcases.StaffingBaseTest;

/**
 * 
 * @author deepakkumar.hadiya
 *
 */

public class InterviewBaseTest extends StaffingBaseTest implements InterviewConstants,GatekeepersConstants{

	@BeforeTest(alwaysRun = true)
	public void beforeSuite(ITestContext context) throws Exception {
		subModuleTest=extent.createTest("Interview");
		RestAssured.useRelaxedHTTPSValidation();
		new InquistorProperties().configureProperties();
	}
	
}
