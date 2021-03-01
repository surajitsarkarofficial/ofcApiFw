package tests.testcases.submodules.reports;

import org.testng.annotations.BeforeTest;

import tests.testcases.StaffingBaseTest;

public class ReportsBaseTest extends StaffingBaseTest{

	@BeforeTest(alwaysRun = true)
	public void beforeTest()
	{
		subModuleTest = moduleTest.createNode("Reports");
	}
}
