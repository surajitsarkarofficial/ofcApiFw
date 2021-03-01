package test.testcases.submodules.myPods;

import org.testng.annotations.BeforeTest;

import test.testcases.GlobantPodsTest;

/**
 * @author ankita.manekar
 *
 */
public class MyPodsTest extends GlobantPodsTest {

	@BeforeTest(alwaysRun = true)
	public void beforeTest() {
		subModuleTest = moduleTest.createNode("GlobantPods");
	}

}
