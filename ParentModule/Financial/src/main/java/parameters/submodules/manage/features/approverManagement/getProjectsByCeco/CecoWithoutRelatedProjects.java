package parameters.submodules.manage.features.approverManagement.getProjectsByCeco;

import java.sql.SQLException;

import database.submodules.manage.features.approverManagement.CecoDBHelper;

/**
 * @author german.massello
 *
 */
public class CecoWithoutRelatedProjects {

	private String cecoNumber;

	
	/**
	 * Default constructor.
	 * @throws SQLException 
	 */
	public CecoWithoutRelatedProjects() throws SQLException {
		this.cecoNumber= new CecoDBHelper().getRandomCecoNumberWithoutRelatedProjects();
	}

	public String getCecoNumber() {
		return cecoNumber;
	}

	public void setCecoNumber(String cecoNumber) {
		this.cecoNumber = cecoNumber;
	}
	
}
