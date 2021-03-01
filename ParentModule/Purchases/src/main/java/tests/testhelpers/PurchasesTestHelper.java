package tests.testhelpers;

import tests.GlowBaseTestHelper;
import tests.testcases.PurchasesBaseTest;
import utils.RestUtils;

/**
 * @author german.massello
 *
 */
public class PurchasesTestHelper extends GlowBaseTestHelper {

	protected RestUtils restUtils = new RestUtils();
	protected String userName;
	
	public PurchasesTestHelper(String userName) throws Exception {
		new PurchasesBaseTest().createSession(userName);
		this.userName = userName;
	}
	
}
