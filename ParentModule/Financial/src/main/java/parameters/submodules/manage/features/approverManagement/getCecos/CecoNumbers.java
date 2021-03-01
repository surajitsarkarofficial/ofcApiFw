package parameters.submodules.manage.features.approverManagement.getCecos;

import java.sql.SQLException;

import database.submodules.manage.ManageDBHelper;

/**
 * @author german.massello
 *
 */
public class CecoNumbers {
	private String cecoNumberOne;
	private String cecoNumberTwo;
	
	/**
	 * Default constructor.
	 * @throws SQLException
	 */
	public CecoNumbers() throws SQLException {
		this.cecoNumberOne=new ManageDBHelper().getRandomCeco();
		this.cecoNumberTwo=new ManageDBHelper().getRandomCeco();
	}
	
	public String getCecoNumberOne() {
		return cecoNumberOne;
	}
	public String getCecoNumberTwo() {
		return cecoNumberTwo;
	}
}
