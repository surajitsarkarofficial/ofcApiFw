package tests.testcases.submodules.requisition;

import org.testng.annotations.BeforeTest;

import tests.testcases.PurchasesBaseTest;

/**
 * @author german.massello
 *
 */
public class RequisitionBaseTest extends PurchasesBaseTest {

	@BeforeTest(alwaysRun = true)
	public void beforeTest()
	{
		subModuleTest = moduleTest.createNode("Requisition");
	}
	
}
