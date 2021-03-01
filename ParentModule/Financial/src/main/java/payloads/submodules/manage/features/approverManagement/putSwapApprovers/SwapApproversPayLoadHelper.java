
package payloads.submodules.manage.features.approverManagement.putSwapApprovers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import parameters.submodules.manage.features.approverManagement.getCecoDetails.GetCecoDetailsParameters;
import parameters.submodules.manage.features.approverManagement.putSwapApprovers.PutSwapApproversParameters;
import tests.testhelpers.submodules.manage.features.approverManagement.GetAvailableApproversTestHelper;
import tests.testhelpers.submodules.manage.features.approverManagement.GetCecoDetailsTestHelper;

/**
 * @author german.massello
 *
 */
public class SwapApproversPayLoadHelper {

	private String allCecos;
	private List<String> cecos = new ArrayList<String>();
	private String currentApproverId;
	private String newApproverId;

	/**
	 * Swap one approver
	 * @param parameters
	 * @throws SQLException
	 * @throws Exception
	 */
	public SwapApproversPayLoadHelper(PutSwapApproversParameters parameters) throws SQLException, Exception {
		this.allCecos = "false";
		cecos.add(parameters.getCecoNumber());
		this.currentApproverId = new GetCecoDetailsTestHelper(parameters.getUsername()).getRandomApprover(new GetCecoDetailsParameters(parameters.getCecoNumber()));
		this.newApproverId = new GetAvailableApproversTestHelper(parameters.getUsername()).getAvailableApprover(parameters.getCecoNumber()).getId();
	}

	/**
	 * Swap one approver tear down.
	 * @param payload
	 * @throws SQLException
	 * @throws Exception
	 */
	public SwapApproversPayLoadHelper(SwapApproversPayLoadHelper payload) throws SQLException, Exception {
		this.allCecos = "false";
		cecos.add(payload.getCecos().get(0));
		this.currentApproverId = payload.getNewApproverId();
		this.newApproverId = payload.getCurrentApproverId();
	}

	/**
	 * Swap one approver in all cecos.
	 * @param parameters
	 * @param allCecos
	 * @throws SQLException
	 * @throws Exception
	 */
	public SwapApproversPayLoadHelper(PutSwapApproversParameters parameters, AllCecos allCecos) throws SQLException, Exception {
		this.allCecos = "true";
		this.currentApproverId = new GetCecoDetailsTestHelper(parameters.getUsername()).getRandomApprover(new GetCecoDetailsParameters(parameters.getCecoNumber()));
		this.newApproverId = new GetAvailableApproversTestHelper(parameters.getUsername()).getAvailableApprover(parameters.getCecoNumber()).getId();
	}

	/**
	 * Swap one approver in all cecos tear down.
	 * @param payload
	 * @param allCecos
	 * @throws SQLException
	 * @throws Exception
	 */
	public SwapApproversPayLoadHelper(SwapApproversPayLoadHelper payload, AllCecos allCecos) throws SQLException, Exception {
		this.allCecos = "true";
		this.currentApproverId = payload.getNewApproverId();
		this.newApproverId = payload.getCurrentApproverId();
	}

	public String getAllCecos() {
	return allCecos;
	}

	public void setAllCecos(String allCecos) {
	this.allCecos = allCecos;
	}

	public List<String> getCecos() {
	return cecos;
	}

	public void setCecos(List<String> cecos) {
	this.cecos = cecos;
	}

	public String getCurrentApproverId() {
	return currentApproverId;
	}

	public void setCurrentApproverId(String currentApproverId) {
	this.currentApproverId = currentApproverId;
	}

	public String getNewApproverId() {
	return newApproverId;
	}

	public void setNewApproverId(String newApproverId) {
	this.newApproverId = newApproverId;
	}

}
