package payloads.submodules.manage.features.approverManagement;

import java.sql.SQLException;

import database.submodules.manage.features.approverManagement.CecoDBHelper;

/**
 * @author german.massello
 *
 */
public class CecoPayLoadHelper {
	private String cecoNumber;

	/**
	 * This constructor return a body with a new ceco number
	 * @throws SQLException
	 */
	public CecoPayLoadHelper() throws SQLException {
		this.cecoNumber = new CecoDBHelper().getNewCecoNumber();
	}
	
	/**
	 * This constructor return a body with a existing ceco number
	 * @throws SQLException
	 */
	public CecoPayLoadHelper(ExistingCecoNumber existingCecoNumber) throws SQLException {
		this.cecoNumber = new CecoDBHelper().getExistingCecoNumber();
	}	
	
	public String getCecoNumber() {
		return cecoNumber;
	}

	public void setCecoNumber(String cecoNumber) {
		this.cecoNumber = cecoNumber;
	}

}
