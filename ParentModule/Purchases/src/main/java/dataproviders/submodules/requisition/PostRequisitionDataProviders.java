package dataproviders.submodules.requisition;

import org.testng.annotations.DataProvider;

import database.PurchasesDBHelper;
import dataproviders.PurchasesDataProviders;
import parameters.submodules.requisition.PostRequisitionParameters;

/**
 * @author german.massello
 *
 */
public class PostRequisitionDataProviders extends PurchasesDataProviders {

	/**
	 * Create requisition
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "createRequisition")
	public Object[][] createRequisition() throws Exception {
		PostRequisitionParameters parameters = (PostRequisitionParameters) new PostRequisitionParameters().
				setUserName(new PurchasesDBHelper().getRandomGloberByRol("Facilities"));
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = parameters;
		return dataObject;
	}

}
