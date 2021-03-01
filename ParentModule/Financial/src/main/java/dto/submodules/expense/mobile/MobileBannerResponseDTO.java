
package dto.submodules.expense.mobile;


/**
 * @author german.massello
 *
 */
public class MobileBannerResponseDTO {

    private String status;
    private String message;
    private Boolean content;

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

    public Boolean getContent() {
        return content;
    }

    public void setContent(Boolean content) {
        this.content = content;
    }

}
