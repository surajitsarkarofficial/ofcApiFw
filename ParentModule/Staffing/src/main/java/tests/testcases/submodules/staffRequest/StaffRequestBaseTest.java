package tests.testcases.submodules.staffRequest;

import org.testng.annotations.BeforeTest;

import tests.testcases.StaffingBaseTest;

public class StaffRequestBaseTest extends StaffingBaseTest{
	@BeforeTest(alwaysRun = true)
	public void beforeTest()
	{
		subModuleTest = moduleTest.createNode("StaffRequest");
	}

}
