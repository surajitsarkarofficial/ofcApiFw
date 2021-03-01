package tests.testcases.submodules.catalogs;

import org.testng.annotations.BeforeTest;

import tests.testcases.PurchasesBaseTest;

/**
 * @author german.massello
 *
 */
public class CatalogsBaseTest extends PurchasesBaseTest {

	@BeforeTest(alwaysRun = true)
	public void beforeTest()
	{
		subModuleTest = moduleTest.createNode("Catalogs");
	}
	
}
