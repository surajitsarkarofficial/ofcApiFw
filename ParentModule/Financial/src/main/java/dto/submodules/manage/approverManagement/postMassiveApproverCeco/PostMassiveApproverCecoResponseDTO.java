
package dto.submodules.manage.approverManagement.postMassiveApproverCeco;


/**
 * @author german.massello
 *
 */
public class PostMassiveApproverCecoResponseDTO {

    private Content content;
    private String message;
    private String status;

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
