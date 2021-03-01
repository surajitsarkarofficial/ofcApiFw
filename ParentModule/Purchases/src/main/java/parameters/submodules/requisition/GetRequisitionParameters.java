package parameters.submodules.requisition;

import java.util.Map;

import constants.submodules.RequisitionConstants;
import dto.submodules.requisition.Requisition;
import parameters.submodules.PurchasesParameters;

/**
 * @author german.massello
 *
 */
public class GetRequisitionParameters  extends PurchasesParameters implements RequisitionConstants{

	private Requisition requisition;
	
	/**
	 * Get requisition parameters object. 
	 * @param requisitionStatus
	 */
	public GetRequisitionParameters(Map<String,String> requisition, String requisitionStatus, String requisitionState, String advancedFilters) {
		super();
		this.status=requisitionStatus;
		this.state=requisitionState;
		this.userName = requisition.get("username");
		this.requisition = new Requisition(requisition, advancedFilters);
	}

	public Requisition getRequisition() {
		return requisition;
	}

	public void setRequisition(Requisition requisition) {
		this.requisition = requisition;
	}

}
