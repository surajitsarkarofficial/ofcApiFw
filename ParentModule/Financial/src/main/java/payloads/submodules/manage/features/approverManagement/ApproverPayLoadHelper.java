package payloads.submodules.manage.features.approverManagement;

import database.submodules.manage.ManageDBHelper;
import tests.testhelpers.submodules.manage.features.approverManagement.GetAvailableApproversTestHelper;

/**
 * @author german.massello
 *
 */
public class ApproverPayLoadHelper {
	
	private String approverId;
	private String cecoNumber;
	
	/**
	 * This method will create a default body in order to add an approver.
	 * @param user
	 * @throws Exception
	 */
	public ApproverPayLoadHelper(String user) throws Exception {
		this.cecoNumber=new ManageDBHelper().getRandomCeco();
		this.approverId=new GetAvailableApproversTestHelper(user).getAvailableApprover(this.cecoNumber).getId();
	}
	
	/**
	 * This method will create a body in order to add an approver without an assigned rol.
	 * @param user
	 * @param approver
	 * @throws Exception
	 */
	public ApproverPayLoadHelper(String user, ApproverWithoutRol approver) throws Exception {
		this.cecoNumber=approver.getCecoNumber();
		this.approverId=new GetAvailableApproversTestHelper(user).getAvailableApproverWithoutAssignedRol(approver.getCecoNumber()).getId();
	}
	
	public String getApproverId() {
		return approverId;
	}
	public ApproverPayLoadHelper setApproverId(String approverId) {
		this.approverId = approverId;
		return this;
	}
	public String getCecoNumber() {
		return cecoNumber;
	}
	public ApproverPayLoadHelper setCecoNumber(String cecoNumber) {
		this.cecoNumber = cecoNumber;
		return this;
	}
	
}
