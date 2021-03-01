package tests.testcases.submodules.myTrips;

import org.testng.annotations.BeforeTest;

import tests.testcases.TravelMobileBaseTest;

public class MyTripsBaseTest extends TravelMobileBaseTest{
	@BeforeTest(alwaysRun = true)
	public void beforeTest()
	{
		subModuleTest = moduleTest.createNode("MyTrips");
	}

}
