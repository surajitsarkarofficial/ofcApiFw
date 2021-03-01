package tests.testCases.submodules.myGrowth;

import org.testng.annotations.BeforeTest;

import properties.myGrowth.MyGrowthProperties;
import tests.testCases.TalentDevelopmentBaseTest;

public class MyGrowthBaseTest extends TalentDevelopmentBaseTest {
    @BeforeTest(alwaysRun = true)
    public void beforeTest() throws Exception {
        subModuleTest = moduleTest.createNode("MyGrowth");
        new MyGrowthProperties().configureProperties();
    }
}