package parameters.submodules.manage.features.approverManagement.crossApprovers;

import java.sql.SQLException;
import parameters.submodules.manage.features.ApproverManagementParameters;

/**
 * @author german.massello
 *
 */
public class GetCrossApproversParameters extends ApproverManagementParameters {

	/**
	 * Default constructor.
	 * @throws SQLException
	 */
	public GetCrossApproversParameters() throws SQLException {
		super();
		this.pageSize="15";
	}
}
