package tests.testhelpers;

import tests.GlowBaseTestHelper;
import tests.testcases.FinancialBaseTest;
import utils.RestUtils;

public class FinancialTestHelper extends GlowBaseTestHelper {

	protected RestUtils restUtils = new RestUtils();

	public FinancialTestHelper(String userName) throws Exception {
		new FinancialBaseTest().createSession(userName);
	}
	
}
