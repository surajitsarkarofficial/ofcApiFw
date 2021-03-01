package tests.testcases.submodules.expense;

import org.testng.annotations.BeforeTest;

import tests.testcases.FinancialBaseTest;

public class ExpenseBaseTest extends FinancialBaseTest {

	@BeforeTest(alwaysRun = true)
	public void beforeTest()
	{
		subModuleTest = moduleTest.createNode("Expense");
	}
	
}
