package tests.testscase.submodules.homepageTestcases;
/**
 * @author rachana.jadhav
 *
 */

import org.testng.annotations.BeforeTest;

import tests.testscase.PodsViewBaseTests;

public class HomePageBaseTest extends PodsViewBaseTests{
	@BeforeTest(alwaysRun = true)
	public void beforeTest()
	{
		subModuleTest = moduleTest.createNode("PodsView");
	}
	

}
