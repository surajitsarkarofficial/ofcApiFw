package parameters.submodules.manage.features.approverManagement;

import java.sql.SQLException;

import dto.submodules.manage.approverManagement.postApprover.ApproversResponseDTO;
import parameters.submodules.manage.features.ApproverManagementParameters;

/**
 * @author german.massello
 *
 */
public class DeleteApproverParameters extends ApproverManagementParameters {
	private String idApprover;
	
	/**
	 * This method will create the parameters in order to delete an approver.
	 * @param approver
	 * @throws SQLException 
	 */
	public DeleteApproverParameters(ApproversResponseDTO approver) throws SQLException {
		super();
		this.cecoNumber=approver.getContent().getCecoNumber();
		this.idApprover=approver.getNewApproverId();
	}

	public String getIdApprover() {
		return idApprover;
	}

	public void setIdApprover(String idApprover) {
		this.idApprover = idApprover;
	}

}
