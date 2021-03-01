package parameters.submodules.manage.features.approverManagement.crossApprovers;

import java.sql.SQLException;
import parameters.submodules.manage.features.ApproverManagementParameters;

/**
 * @author german.massello
 *
 */
public class GetAvailableCrossApproversParameters extends ApproverManagementParameters {

	private String usernameToSearch;

	/**
	 * Default constructor.
	 * @throws SQLException
	 */
	public GetAvailableCrossApproversParameters() throws SQLException {
		super();
		this.usernameToSearch="ger";
		this.limit="5";
	}

	public String getUsernameToSearch() {
		return usernameToSearch;
	}

	public void setUsernameToSearch(String usernameToSearch) {
		this.usernameToSearch = usernameToSearch;
	}
	
}
