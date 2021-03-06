
package dto.submodules.manage.approverManagement.crossApprovers.getCrossApprovers;


/**
 * @author german.massello
 *
 */
public class GetCrossApproversResponseDTO {

    private String status;
    private String message;
    private Content content;

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

}
