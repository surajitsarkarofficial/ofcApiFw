package dataproviders.submodules.catalogs.features;

import java.util.Random;

import org.testng.annotations.DataProvider;

import database.PurchasesDBHelper;
import dataproviders.submodules.catalogs.CatalogsDataProviders;

/**
 * @author german.massello
 *
 */
public class MaterialDataProviders extends CatalogsDataProviders {

	/**
	 * This data provider will return:
	 * A user with the service desk rol, a group name and a country name.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "materialDataProvider")
	public Object[][] materialDataProvider() throws Exception {
		Object[][] dataObject = new Object[1][3];
		dataObject [0][0] = new PurchasesDBHelper().getRandomGloberByRol("ServiceDesk");
		dataObject [0][1] = groupOptions[new Random().nextInt(groupOptions.length)];
		dataObject [0][2] = "Argentina";
		return dataObject;
	}
}
