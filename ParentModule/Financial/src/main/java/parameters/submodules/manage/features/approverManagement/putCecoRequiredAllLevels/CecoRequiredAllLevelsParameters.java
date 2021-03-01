package parameters.submodules.manage.features.approverManagement.putCecoRequiredAllLevels;

import java.sql.SQLException;
import java.util.Map;

import database.submodules.manage.features.approverManagement.CecoDBHelper;
import parameters.submodules.manage.features.ApproverManagementParameters;

/**
 * @author german.massello
 *
 */
public class CecoRequiredAllLevelsParameters extends ApproverManagementParameters {
	private String cecoRequiredAllLevelsOriginalStatus;
	private String cecoRequiredAllLevelsNewStatus;
	
	/**
	 * Default constructor.
	 * @throws SQLException
	 */
	public CecoRequiredAllLevelsParameters() throws SQLException {
		super();
		Map<String,String> ceco = new CecoDBHelper().getRandomCecoNumber();
		String newStatus, originalStatus;
		if (ceco.get("requiredAllLevels").equals("t")) {
			newStatus="false";
			originalStatus="true";
		}
		else {
			newStatus = "true";
			originalStatus="false";
		}
		this.cecoNumber = ceco.get("cecoNumber");
		this.cecoRequiredAllLevelsOriginalStatus = originalStatus;
		this.cecoRequiredAllLevelsNewStatus = newStatus;
	}

	public String getCecoRequiredAllLevelsOriginalStatus() {
		return cecoRequiredAllLevelsOriginalStatus;
	}
	
	public void setCecoRequiredAllLevelsOriginalStatus(String cecoRequiredAllLevelsOriginalStatus) {
		this.cecoRequiredAllLevelsOriginalStatus = cecoRequiredAllLevelsOriginalStatus;
	}
	
	public String getCecoRequiredAllLevelsNewStatus() {
		return cecoRequiredAllLevelsNewStatus;
	}
	
	public void setCecoRequiredAllLevelsNewStatus(String cecoRequiredAllLevelsNewStatus) {
		this.cecoRequiredAllLevelsNewStatus = cecoRequiredAllLevelsNewStatus;
	}

}
