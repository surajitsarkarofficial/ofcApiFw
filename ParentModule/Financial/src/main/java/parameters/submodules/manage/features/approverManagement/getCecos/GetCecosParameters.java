package parameters.submodules.manage.features.approverManagement.getCecos;

import java.sql.SQLException;

import parameters.submodules.manage.features.ApproverManagementParameters;

/**
 * @author german.massello
 *
 */
public class GetCecosParameters extends ApproverManagementParameters{

	private String searchValue;
	
	/**
	 * Default constructor.
	 */
	public GetCecosParameters() throws SQLException {
		super();
		this.searchValue="";
	}

	/**
	 * This constructor will return a object where there are two ceco numbers separated by a comma.
	 * @param cecoNumbers
	 */
	public GetCecosParameters(CecoNumbers cecoNumbers) throws SQLException {
		super();
		this.searchValue=cecoNumbers.getCecoNumberOne()+","+cecoNumbers.getCecoNumberTwo();
	}

	/**
	 * This constructor will return a object where there is an incomplete ceco number.
	 * @param ceco
	 */
	public GetCecosParameters(IncompleteCecoNumber ceco) throws SQLException {
		super();
		this.searchValue=ceco.getIncompleteCecoNumber();
	}
	
	/**
	 * This constructor will return a object where there is a non existent ceco number.
	 * @param ceco
	 */
	public GetCecosParameters(NonExistentCecoNumber ceco) throws SQLException {
		super();
		super.setStatusCode(404);
		this.searchValue=ceco.getNonExistentCecoNumber();
	}
	
	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	
}
