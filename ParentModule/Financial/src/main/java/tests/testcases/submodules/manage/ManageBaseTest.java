package tests.testcases.submodules.manage;

import org.testng.annotations.BeforeTest;

import tests.testcases.FinancialBaseTest;

/**
 * @author german.massello
 *
 */
public class ManageBaseTest extends FinancialBaseTest {
	
	@BeforeTest(alwaysRun = true)
	public void beforeTest()
	{
		subModuleTest = moduleTest.createNode("Manage");
	}

}
