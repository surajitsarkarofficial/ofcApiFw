package dataproviders.submodules.manage;

import java.util.Random;

import org.testng.annotations.DataProvider;

import database.submodules.manage.ManageDBHelper;
import dataproviders.FinancialDataProviders;
import properties.manage.ManageProperties;

/**
 * @author german.massello
 *
 */
public class ManageDataProviders extends FinancialDataProviders {

	/**
	 * This data provider will return an user that have a random rol
	 * Could be: SafeMatrixAdmin, Board or Finance rol.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "randomRol")
	public Object[][] randomRol() throws Exception {
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = new ManageDBHelper().getRandomGloberByRol(ManageProperties.rolOptions[new Random().nextInt(ManageProperties.rolOptions.length)]);
		return dataObject;
	}
	
}
