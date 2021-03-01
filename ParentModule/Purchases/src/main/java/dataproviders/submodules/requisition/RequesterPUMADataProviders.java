package dataproviders.submodules.requisition;

import java.util.Map;

import org.testng.annotations.DataProvider;

import database.submodules.requisition.features.GetRequisitionDBHelper;
import dataproviders.PurchasesDataProviders;
import parameters.submodules.requisition.GetRequisitionParameters;

/**
 * @author german.massello
 *
 */
public class RequesterPUMADataProviders extends PurchasesDataProviders {

	/**
	 * Reception completed
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "receptionCompleted")
	public Object[][] receptionCompleted() throws Exception {
		Map<String,String> requisition = new GetRequisitionDBHelper().getPumaRequester(RECEPTION_COMPLETE_STATUS_DB, REQUISITION_STATE);
		GetRequisitionParameters parameters = new GetRequisitionParameters(requisition, RECEPTION_COMPLETE_STATUS_REST, EMPTY_REQUISITION_STATE, ADVANCE_FILTERS_FALSE);
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = parameters;
		return dataObject;
	}
	
	/**
	 * Draft requisitions
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "draftRequisitions")
	public Object[][] draftRequisitions() throws Exception {
		Map<String,String> requisition = new GetRequisitionDBHelper().getPumaRequester(EMPTY_REQUISITION_STATUS, DRAFT_REQUISITION_STATE);
		GetRequisitionParameters parameters = new GetRequisitionParameters(requisition, EMPTY_REQUISITION_STATUS, DRAFT_REQUISITION_STATE, ADVANCE_FILTERS_FALSE);
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = parameters;
		return dataObject;
	}
	
	/**
	 * Partially pending
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "partiallyPending")
	public Object[][] partiallyPending() throws Exception {
		Map<String,String> requisition = new GetRequisitionDBHelper().getPumaRequester(PARTIALLY_PENDING_STATUS_DB, REQUISITION_STATE);
		GetRequisitionParameters parameters = new GetRequisitionParameters(requisition, PARTIALLY_PENDING_STATUS_REST, EMPTY_REQUISITION_STATE, ADVANCE_FILTERS_FALSE);
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = parameters;
		return dataObject;
	}
	
	/**
	 * Partially pending draft
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "partiallyPendingDraft")
	public Object[][] partiallyPendingDraft() throws Exception {
		Map<String,String> requisition = new GetRequisitionDBHelper().getPumaRequester(PARTIALLY_PENDING_STATUS_DB, DRAFT_REQUISITION_STATE);
		GetRequisitionParameters parameters = new GetRequisitionParameters(requisition, PARTIALLY_PENDING_STATUS_REST, EMPTY_REQUISITION_STATE, ADVANCE_FILTERS_FALSE);
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = parameters;
		return dataObject;
	}
	
	/**
	 * Pending approval
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "pendingApproval")
	public Object[][] pendingApproval() throws Exception {
		Map<String,String> requisition = new GetRequisitionDBHelper().getPumaRequester(PENDING_APPROVAL_STATUS_DB, REQUISITION_STATE);
		GetRequisitionParameters parameters = new GetRequisitionParameters(requisition, PENDING_APPROVAL_STATUS_REST, EMPTY_REQUISITION_STATE, ADVANCE_FILTERS_FALSE);
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = parameters;
		return dataObject;
	}
	
	/**
	 * Approved
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "approved")
	public Object[][] approved() throws Exception {
		Map<String,String> requisition = new GetRequisitionDBHelper().getPumaRequester(APPROVED_STATUS_DB, REQUISITION_STATE);
		GetRequisitionParameters parameters = new GetRequisitionParameters(requisition, APPROVED_STATUS_REST, EMPTY_REQUISITION_STATE, ADVANCE_FILTERS_FALSE);
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = parameters;
		return dataObject;
	}
	
	/**
	 * Partially rejected
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "partiallyRejected")
	public Object[][] partiallyRejected() throws Exception {
		Map<String,String> requisition = new GetRequisitionDBHelper().getPumaRequester(PARTIALY_REJECTED_STATUS_DB, REQUISITION_STATE);
		GetRequisitionParameters parameters = new GetRequisitionParameters(requisition, PARTIALY_REJECTED_STATUS_REST, EMPTY_REQUISITION_STATE, ADVANCE_FILTERS_FALSE);
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = parameters;
		return dataObject;
	}

	/**
	 * Rejected by approver
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "rejectedByApprover")
	public Object[][] rejectedByApprover() throws Exception {
		Map<String,String> requisition = new GetRequisitionDBHelper().getPumaRequester(REJECTED_BY_APPROVER_STATUS_DB, REQUISITION_STATE);
		GetRequisitionParameters parameters = new GetRequisitionParameters(requisition, REJECTED_BY_APPROVER_STATUS_REST, EMPTY_REQUISITION_STATE, ADVANCE_FILTERS_FALSE);
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = parameters;
		return dataObject;
	}

	/**
	 * Rejected by purchaser
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "rejectedByPurchaser")
	public Object[][] rejectedByPurchaser() throws Exception {
		Map<String,String> requisition = new GetRequisitionDBHelper().getPumaRequester(REJECTED_BY_PURCHASER_STATUS_DB, REQUISITION_STATE);
		GetRequisitionParameters parameters = new GetRequisitionParameters(requisition, REJECTED_BY_PURCHASER_STATUS_REST, EMPTY_REQUISITION_STATE, ADVANCE_FILTERS_FALSE);
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
		Map<String,String> requisition = new GetRequisitionDBHelper().getPumaRequester(PO_NUMBER_CREATED_STATUS_DB, REQUISITION_STATE);
		GetRequisitionParameters parameters = new GetRequisitionParameters(requisition, PO_NUMBER_CREATED_STATUS_REST, EMPTY_REQUISITION_STATE, ADVANCE_FILTERS_FALSE);
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
		Map<String,String> requisition = new GetRequisitionDBHelper().getPumaRequester(RECEPTION_PENDING_STATUS_DB, REQUISITION_STATE);
		GetRequisitionParameters parameters = new GetRequisitionParameters(requisition, RECEPTION_PENDING_STATUS_REST, EMPTY_REQUISITION_STATE, ADVANCE_FILTERS_FALSE);
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
		Map<String,String> requisition = new GetRequisitionDBHelper().getPumaRequester(RECEPTION_COMPLETE_STATUS_DB, REQUISITION_STATE);
		GetRequisitionParameters parameters = (GetRequisitionParameters) new GetRequisitionParameters(requisition, EMPTY_REQUISITION_STATUS, EMPTY_REQUISITION_STATE, ADVANCE_FILTERS_FALSE)
				.setIsGlobalView("true");
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = parameters;
		return dataObject;
	}
}
