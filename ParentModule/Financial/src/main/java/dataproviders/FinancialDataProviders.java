package dataproviders;

import org.testng.annotations.DataProvider;

import database.FinancialDBHelper;
import database.submodules.manage.ManageDBHelper;

public class FinancialDataProviders extends GlowDataProviders {

	/**
	 * This data provider will return a user that have an active bank account.
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "usersThatHaveABankAccount")
	public Object[][] usersThatHaveABankAccount() throws Exception {
		FinancialDBHelper financialDBHelper = new FinancialDBHelper();
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = financialDBHelper.getGloberThatHaveABankAccount();
		return dataObject;
	}

	/**
	 * This data provider will return an user that have the ExpensesExceptionsAdmin rol.
	 * One user has the rol
	 * The another user has the rol 
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "expensesExceptionsAdminRol")
	public Object[][] expensesExceptionsAdminUser() throws Exception {
		FinancialDBHelper financialDBHelper = new FinancialDBHelper();
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = financialDBHelper.getRandomGloberByRol("ExpensesExceptionsAdmin");
		return dataObject;
	}

	/**
	 * This data provider will return an user that have the Glober rol.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "globerRol")
	public Object[][] globerRol() throws Exception {
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = new ManageDBHelper().getRandomGloberByRol("Glober");
		return dataObject;
	}

	/**
	 * This data provider will return an user that have the SafeMatrixAdmin rol.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "safeMatrixAdminRol")
	public Object[][] safeMatrixAdminRol() throws Exception {
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = new ManageDBHelper().getRandomGloberByRol("SafeMatrixAdmin");
		return dataObject;
	}

	/**
	 * This data provider will return an user that have the Finance rol.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "financeRol")
	public Object[][] financeRol() throws Exception {
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = new ManageDBHelper().getRandomGloberByRol("Finance");
		return dataObject;
	}
	
	/**
	 * This data provider will return an user that have the Board rol.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "boardRol")
	public Object[][] boardRol() throws Exception {
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = new ManageDBHelper().getRandomGloberByRol("Board");
		return dataObject;
	}

}
