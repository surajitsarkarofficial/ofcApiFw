package tests.testhelpers.submodules.openpositions;

import tests.testcases.submodules.openpositions.OpenPositionBaseTest;
import tests.testhelpers.StaffingTestHelper;

public class OpenPositionBaseTestHelper extends StaffingTestHelper{

	public OpenPositionBaseTestHelper(String userName) throws Exception {
		new OpenPositionBaseTest().createSession(userName);
	}
}
