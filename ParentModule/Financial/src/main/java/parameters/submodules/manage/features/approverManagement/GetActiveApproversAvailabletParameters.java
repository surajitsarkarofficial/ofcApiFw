package parameters.submodules.manage.features.approverManagement;

import java.sql.SQLException;

import database.submodules.manage.features.approverManagement.ApproverDBHelper;
import parameters.submodules.manage.features.ApproverManagementParameters;

/**
 * @author german.massello
 *
 */
public class GetActiveApproversAvailabletParameters extends ApproverManagementParameters {

	/**
	 * Default constructor.
	 * @throws SQLException
	 */
	public GetActiveApproversAvailabletParameters() throws SQLException {
		super();
		this.pageNum="0";
		this.pageSize="1";
		this.searchValue= new ApproverDBHelper().getApproverFullName().replace(" ", "%20");
	}

}
