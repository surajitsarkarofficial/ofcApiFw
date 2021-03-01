package tests.testhelpers.submodules.manage;

import tests.testhelpers.FinancialTestHelper;

public class ManageTestHelper extends FinancialTestHelper {

	protected String userName;

	public ManageTestHelper(String userName) throws Exception {
		super(userName);
		this.userName = userName;
	}
	
}
