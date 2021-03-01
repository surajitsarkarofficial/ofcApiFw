package tests.testhelpers.submodules.reports;

import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import tests.testhelpers.StaffingTestHelper;

public class ReportsTestHelper extends StaffingTestHelper{

	public ReportsTestHelper(String userName) throws Exception {
		new StaffRequestBaseTest().createSession(userName);
	}
}
