package dataproviders.submodules.catalogs.features;

import java.util.Random;

import org.testng.annotations.DataProvider;

import database.PurchasesDBHelper;
import dataproviders.submodules.catalogs.CatalogsDataProviders;

/**
 * @author german.massello
 *
 */
public class VendorDataProviders extends CatalogsDataProviders {

	/**
	 * This data provider will return a user with the service desk rol, a store name and a country name.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "vendorDataProvider")
	public Object[][] vendorDataProvider() throws Exception {
		Object[][] dataObject = new Object[1][3];
		dataObject [0][0] = new PurchasesDBHelper().getRandomGloberByRol("ServiceDesk");
		dataObject [0][1] = storeOptions[new Random().nextInt(storeOptions.length)];
		dataObject [0][2] = "Argentina";
		return dataObject;
	}
}
