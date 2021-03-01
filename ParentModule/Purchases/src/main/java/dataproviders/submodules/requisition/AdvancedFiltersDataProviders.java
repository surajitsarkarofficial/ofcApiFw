package dataproviders.submodules.requisition;

import org.testng.annotations.DataProvider;

import dataproviders.PurchasesDataProviders;
import parameters.submodules.requisition.GetRequisitionParameters;

/**
 * @author german.massello
 *
 */
public class AdvancedFiltersDataProviders extends PurchasesDataProviders {

	/**
	 * Filter by group
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "filterByGroup")
	public Object[][] filterByGroup() throws Exception {
		setRequisition();
		GetRequisitionParameters parameters = (GetRequisitionParameters) new GetRequisitionParameters(requisition, EMPTY_REQUISITION_STATUS, EMPTY_REQUISITION_STATE, ADVANCE_FILTERS_GROUP)
				.setIsGlobalView("true").setIsPurchaserView("true");
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = parameters;
		return dataObject;
	}

	/**
	 * Filter by requester
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "filterByRequester")
	public Object[][] filterByRequester() throws Exception {
		setRequisition();
		GetRequisitionParameters parameters = (GetRequisitionParameters) new GetRequisitionParameters(requisition, EMPTY_REQUISITION_STATUS, EMPTY_REQUISITION_STATE, ADVANCE_FILTERS_REQUESTER)
				.setIsGlobalView("true").setIsPurchaserView("true");
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = parameters;
		return dataObject;
	}

}
