package tests.testcases.submodules.openpositions;

import org.testng.annotations.BeforeTest;

import tests.testcases.StaffingBaseTest;

public class OpenPositionBaseTest extends StaffingBaseTest{

	@BeforeTest(alwaysRun = true)
	public void beforeTest()
	{
		subModuleTest = moduleTest.createNode("OpenPosition");
	}
}
