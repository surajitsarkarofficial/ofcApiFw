package tests.testcases.submodules.globers;

import org.testng.annotations.BeforeTest;

import tests.testcases.StaffingBaseTest;

public class GlobersBaseTest extends StaffingBaseTest{

	@BeforeTest(alwaysRun = true)
	public void beforeTest()
	{
		subModuleTest = moduleTest.createNode("Globers");
	}
}
