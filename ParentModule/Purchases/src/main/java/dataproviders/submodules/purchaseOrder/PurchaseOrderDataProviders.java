package dataproviders.submodules.purchaseOrder;

import org.testng.annotations.DataProvider;

import dataproviders.PurchasesDataProviders;
import parameters.submodules.purchaseOrder.PurchaseOrderParameters;

/**
 * @author german.massello
 *
 */
public class PurchaseOrderDataProviders extends PurchasesDataProviders {

	/**
	 * Purchase order
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "purchaseOrder")
	public Object[][] purchaseOrder() throws Exception {
		PurchaseOrderParameters parameters = new PurchaseOrderParameters();
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = parameters;
		return dataObject;
	}
	
}
