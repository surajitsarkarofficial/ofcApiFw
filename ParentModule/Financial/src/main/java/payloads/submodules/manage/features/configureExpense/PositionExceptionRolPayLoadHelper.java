package payloads.submodules.manage.features.configureExpense;

import payloads.submodules.manage.ManagePayLoadHelper;

/**
 * @author german.massello
 *
 */
public class PositionExceptionRolPayLoadHelper extends ManagePayLoadHelper {

	private String positionId;
	private String roleId;
	
	/**
	 * This method will construct a payload in order to add or remove a position to an Exception Rol.
	 * @param roleId
	 * @author german.massello
	 */
	public PositionExceptionRolPayLoadHelper(String roleId) {
		this.roleId = roleId;
		this.positionId = "1";
	}

	public String getPositionId() {
		return positionId;
	}
	
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	
	public String getRoleId() {
		return roleId;
	}
	
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
