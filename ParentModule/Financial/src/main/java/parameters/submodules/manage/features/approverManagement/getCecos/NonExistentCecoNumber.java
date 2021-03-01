package parameters.submodules.manage.features.approverManagement.getCecos;

import java.sql.SQLException;

import database.submodules.manage.ManageDBHelper;

/**
 * @author german.massello
 *
 */
public class NonExistentCecoNumber {
	
	private String nonExistentCecoNumber;

	/**
	 * Default constructor
	 * @throws SQLException
	 */
	public NonExistentCecoNumber() throws SQLException {
		this.nonExistentCecoNumber=new ManageDBHelper().getNonExistentCecoNumber();
	}

	public String getNonExistentCecoNumber() {
		return nonExistentCecoNumber;
	}
	
}
