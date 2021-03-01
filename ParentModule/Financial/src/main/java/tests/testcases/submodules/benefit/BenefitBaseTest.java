package tests.testcases.submodules.benefit;

import org.testng.annotations.BeforeTest;

import tests.testcases.FinancialBaseTest;

/**
 * @author german.massello
 *
 */
public class BenefitBaseTest extends FinancialBaseTest {

	@BeforeTest(alwaysRun = true)
	public void beforeTest()
	{
		subModuleTest = moduleTest.createNode("BenefitBaseTest");
	}

}
