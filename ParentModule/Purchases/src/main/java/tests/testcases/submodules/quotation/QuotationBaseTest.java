package tests.testcases.submodules.quotation;

import org.testng.annotations.BeforeTest;

import tests.testcases.PurchasesBaseTest;

/**
 * @author german.massello
 *
 */
public class QuotationBaseTest extends PurchasesBaseTest {

	@BeforeTest(alwaysRun = true)
	public void beforeTest()
	{
		subModuleTest = moduleTest.createNode("Quotation");
	}
	
}
