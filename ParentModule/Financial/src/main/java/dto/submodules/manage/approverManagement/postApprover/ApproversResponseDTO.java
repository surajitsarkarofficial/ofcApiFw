
package dto.submodules.manage.approverManagement.postApprover;

/**
 * @author german.massello
 *
 */
public class ApproversResponseDTO {

    private String status;
    private String message;
    private Content content;
    private String newApproverId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

	public String getNewApproverId() {
		return newApproverId;
	}

	public void setNewApproverId(String newApproverId) {
		this.newApproverId = newApproverId;
	}

}
