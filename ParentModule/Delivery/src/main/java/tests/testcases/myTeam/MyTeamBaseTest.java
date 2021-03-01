package tests.testcases.myTeam;

import org.testng.annotations.BeforeTest;

import tests.testcases.DeliveryBaseTest;


/**
 * @author imran.khan
 *
 */


public class MyTeamBaseTest extends DeliveryBaseTest{
	@BeforeTest(alwaysRun = true)
	public void beforeTest()
	{
		subModuleTest = moduleTest.createNode("MyTeam");
	}

}
