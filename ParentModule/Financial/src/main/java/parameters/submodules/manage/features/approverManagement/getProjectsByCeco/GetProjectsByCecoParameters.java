package parameters.submodules.manage.features.approverManagement.getProjectsByCeco;

import java.sql.SQLException;

import database.submodules.manage.features.approverManagement.CecoDBHelper;
import parameters.submodules.manage.features.approverManagement.getCecos.NonExistentCecoNumber;

/**
 * @author german.massello
 *
 */
public class GetProjectsByCecoParameters {
	private String cecoNumber;
	
	/**
	 * This constructor will create an object with a ceco number with related projects.
	 * @throws SQLException 
	 */
	public GetProjectsByCecoParameters() throws SQLException {
		this.cecoNumber= new CecoDBHelper().getRandomCecoNumberWithRelatedProjects();
	}

	/**
	 * This constructor will create an object with a ceco number without related projects.
	 * @param parameter
	 * @throws SQLException
	 */
	public GetProjectsByCecoParameters(CecoWithoutRelatedProjects parameter) throws SQLException {
		this.cecoNumber= parameter.getCecoNumber();
	}	
	
	/**
	 * This constructor will create an object with a non existent number.
	 * @param parameter
	 * @throws SQLException
	 */
	public GetProjectsByCecoParameters(NonExistentCecoNumber parameter) throws SQLException {
		this.cecoNumber= parameter.getNonExistentCecoNumber();
	}	
	
	public String getCecoNumber() {
		return cecoNumber;
	}

	public void setCecoNumber(String cecoNumber) {
		this.cecoNumber = cecoNumber;
	}
	
	
}
