package parameters.submodules.manage.features.approverManagement;

import java.sql.SQLException;

import database.submodules.manage.ManageDBHelper;
import parameters.submodules.manage.features.ApproverManagementParameters;

/**
 * @author german.massello
 *
 */
public class GetAvailableApproversParameters extends ApproverManagementParameters {
	private String userCriteria;
	
	/**
	 * Default constructor
	 * @throws SQLException
	 */
	public GetAvailableApproversParameters() throws SQLException {
		this.cecoNumber=new ManageDBHelper().getRandomCeco();
		this.userCriteria="pab";
		this.limit="5";
	}
	
	/**
	 * This constructor receive a cecoNumber in order to create the object.
	 * @param cecoNumber
	 * @throws SQLException
	 */
	public GetAvailableApproversParameters(String cecoNumber) throws SQLException {
		this.cecoNumber=cecoNumber;
		this.userCriteria="pab";
		this.limit="5";
	}
	
	public String getUserCriteria() {
		return userCriteria;
	}
	
	public GetAvailableApproversParameters setUserCriteria(String userName) {
		this.userCriteria = userName;
		return this;
	}
}
