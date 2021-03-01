package parameters.submodules.manage.features.approverManagement.massiveApproverCeco;

import java.sql.SQLException;

import database.submodules.manage.features.approverManagement.CecoDBHelper;
import parameters.submodules.manage.features.ApproverManagementParameters;

/**
 * @author german.massello
 *
 */
public class MassiveApproverCecoParameters extends ApproverManagementParameters {

	/**
	 * Default constructor
	 * @throws SQLException
	 */
	public MassiveApproverCecoParameters() throws SQLException {
		super();
		this.cecoNumbers.add(new CecoDBHelper().getExistingCecoNumber());
	}

}
