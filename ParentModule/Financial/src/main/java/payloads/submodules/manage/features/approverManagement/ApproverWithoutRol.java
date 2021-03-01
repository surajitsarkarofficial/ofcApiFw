package payloads.submodules.manage.features.approverManagement;

import java.sql.SQLException;

import database.submodules.manage.ManageDBHelper;

/**
 * @author german.massello
 *
 */
public class ApproverWithoutRol {
	private String cecoNumber;

	/**
	 * Default constructor
	 * @throws SQLException
	 */
	public ApproverWithoutRol() throws SQLException {
		this.cecoNumber=new ManageDBHelper().getRandomCeco();
	}

	public String getCecoNumber() {
		return cecoNumber;
	}

}
