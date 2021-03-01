package tests.testCases;

import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;

import io.restassured.RestAssured;
import tests.GlowBaseTest;

public class TalentDevelopmentBaseTest extends GlowBaseTest {
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(ITestContext context) throws Exception {
        moduleTest=extent.createTest("Talent Development");
        RestAssured.useRelaxedHTTPSValidation();
    }
}