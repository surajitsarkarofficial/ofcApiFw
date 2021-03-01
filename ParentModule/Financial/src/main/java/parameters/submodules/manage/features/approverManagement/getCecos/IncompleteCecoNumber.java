package parameters.submodules.manage.features.approverManagement.getCecos;

import java.sql.SQLException;

import database.submodules.manage.ManageDBHelper;

/**
 * @author german.massello
 *
 */
public class IncompleteCecoNumber {
	private String incompleteCecoNumber;
	
	/**
	 * Default constructor.
	 * @throws SQLException
	 */
	public IncompleteCecoNumber() throws SQLException {
		this.incompleteCecoNumber = new ManageDBHelper().getRandomCeco().substring(0, 3);
	}

	public String getIncompleteCecoNumber() {
		return incompleteCecoNumber;
	}
	
}
