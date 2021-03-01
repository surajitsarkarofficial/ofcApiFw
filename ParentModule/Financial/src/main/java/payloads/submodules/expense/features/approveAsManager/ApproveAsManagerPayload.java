package payloads.submodules.expense.features.approveAsManager;

/**
 * @author german.massello
 *
 */
public class ApproveAsManagerPayload {

	private String notificationId;
	private String action;
	private String comments;

	/**
	 * This method will create a payload and it is going to be use
	 * by the safe manager in order to approve or reject a report.
	 */
	public ApproveAsManagerPayload(String action, String notificationId) {
		this.action = action;
		this.comments = action + " by manager";
		this.notificationId = notificationId;
	}
	
	public String getNotificationId() {
		return notificationId;
	}
	
	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getComments() {
		return comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
}
