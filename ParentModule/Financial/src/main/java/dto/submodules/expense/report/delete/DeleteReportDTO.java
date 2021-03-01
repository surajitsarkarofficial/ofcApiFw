
package dto.submodules.expense.report.delete;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteReportDTO {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("content")
    @Expose
    private String content;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DeleteReportDTO() {
    }

    /**
     * 
     * @param message
     * @param content
     * @param status
     */
    public DeleteReportDTO(String status, String message, String content) {
        super();
        this.status = status;
        this.message = message;
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DeleteReportDTO withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DeleteReportDTO withMessage(String message) {
        this.message = message;
        return this;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DeleteReportDTO withContent(String content) {
        this.content = content;
        return this;
    }

}
