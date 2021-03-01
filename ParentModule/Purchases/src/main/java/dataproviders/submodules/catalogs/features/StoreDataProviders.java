package dataproviders.submodules.catalogs.features;

import org.testng.annotations.DataProvider;

import database.PurchasesDBHelper;
import dataproviders.submodules.catalogs.CatalogsDataProviders;

/**
 * @author german.massello
 *
 */
public class StoreDataProviders extends CatalogsDataProviders {

	/**
	 * This data provider will return a user with the service desk rol and a country name.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "storeDataProvider")
	public Object[][] storeDataProvider() throws Exception {
		Object[][] dataObject = new Object[1][2];
		dataObject [0][0] = new PurchasesDBHelper().getRandomGloberByRol("ServiceDesk");
		dataObject [0][1] = "Argentina";
		return dataObject;
	}
}
