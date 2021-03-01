package dataproviders.submodules.requisition;


import org.testng.annotations.DataProvider;

import dataproviders.PurchasesDataProviders;
import parameters.submodules.requisition.GetRequisitionParameters;

/**
 * @author german.massello
 *
 */
public class ForPurchasesDataProviders extends PurchasesDataProviders {

	/**
	 * Rejected by purchaser
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "rejectedByPurchaser")
	public Object[][] rejectedByPurchaser() throws Exception {
		setRequisition();
		GetRequisitionParameters parameters = (GetRequisitionParameters) new GetRequisitionParameters(requisition, REJECTED_BY_PURCHASER_STATUS_REST, EMPTY_REQUISITION_STATE, ADVANCE_FILTERS_FALSE).setIsPurchaserView("true");
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = parameters;
		return dataObject;
	}

	/**
	 * Purchase order created  
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "purchaseOrderCreated")
	public Object[][] purchaseOrderCreated() throws Exception {
		setRequisition();
		GetRequisitionParameters parameters = (GetRequisitionParameters) new GetRequisitionParameters(requisition, PO_NUMBER_CREATED_STATUS_REST, EMPTY_REQUISITION_STATE, ADVANCE_FILTERS_FALSE).setIsPurchaserView("true");
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = parameters;
		return dataObject;
	}

	/**
	 * Reception pending
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "receptionPending")
	public Object[][] receptionPending() throws Exception {
		setRequisition();
		GetRequisitionParameters parameters = (GetRequisitionParameters) new GetRequisitionParameters(requisition, RECEPTION_PENDING_STATUS_REST, EMPTY_REQUISITION_STATE, ADVANCE_FILTERS_FALSE).setIsPurchaserView("true");
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = parameters;
		return dataObject;
	}
	
	/**
	 * Reception completed
	 * @return Object[][] 
	 * @throws Exception
	 */
	@DataProvider(name = "receptionCompleted")
	public Object[][] receptionCompleted() throws Exception {
		setRequisition();
		GetRequisitionParameters parameters = (GetRequisitionParameters) new GetRequisitionParameters(requisition, RECEPTION_COMPLETE_STATUS_REST, EMPTY_REQUISITION_STATE, ADVANCE_FILTERS_FALSE).setIsPurchaserView("true");
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = parameters;
		return dataObject;
	}

	/**
	 * Global view
	 * @return Object[][] 
	 * @throws Exception
	 */
	@DataProvider(name = "globalView")
	public Object[][] globalView() throws Exception {
		setRequisition();
		GetRequisitionParameters parameters = (GetRequisitionParameters) new GetRequisitionParameters(requisition, EMPTY_REQUISITION_STATUS, EMPTY_REQUISITION_STATE, ADVANCE_FILTERS_FALSE).setIsPurchaserView("true").setIsGlobalView("true");
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = parameters;
		return dataObject;
	}

}
