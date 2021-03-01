package tests.testcases.submodules.reception;

import org.testng.annotations.BeforeTest;

import tests.testcases.PurchasesBaseTest;

/**
 * @author german.massello
 *
 */
public class ReceptionBaseTest extends PurchasesBaseTest {

	@BeforeTest(alwaysRun = true)
	public void beforeTest()
	{
		subModuleTest = moduleTest.createNode("PurchaseOrder");
	}
	
}
