package parameters.submodules.manage.features.approverManagement.crossApprovers;

import java.sql.SQLException;
import database.submodules.manage.features.approverManagement.CrossApproverDBHelper;
import parameters.submodules.manage.features.ApproverManagementParameters;

/**
 * @author german.massello
 *
 */
public class PostCrossApproverParameters extends ApproverManagementParameters {
	private String newCrossApproverId;

	/**
	 * Default constructor.
	 * @throws SQLException
	 */
	public PostCrossApproverParameters() throws SQLException {
		super();
		this.newCrossApproverId = new CrossApproverDBHelper().getNewCrossApproverId();
	}

	public String getNewCrossApproverId() {
		return newCrossApproverId;
	}

	public void setNewCrossApproverId(String newCrossApproverId) {
		this.newCrossApproverId = newCrossApproverId;
	}
	
}
