package parameters.submodules.manage.features.approverManagement.putSwapApprovers;

import java.sql.SQLException;

import database.submodules.manage.ManageDBHelper;
import parameters.submodules.manage.features.ApproverManagementParameters;

/**
 * @author german.massello
 *
 */
public class PutSwapApproversParameters extends ApproverManagementParameters {

	/**
	 * Default constructor.
	 * @throws SQLException
	 */
	public PutSwapApproversParameters() throws SQLException {
		super();
		this.cecoNumber=new ManageDBHelper().getRandomCeco();
	}

}
