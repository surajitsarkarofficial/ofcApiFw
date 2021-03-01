
package dto.submodules.expense.expense.get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpenseDTOList {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("content")
    @Expose
    private Content content;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ExpenseDTOList() {
    }

    /**
     * 
     * @param message
     * @param content
     * @param status
     */
    public ExpenseDTOList(String status, String message, Content content) {
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

    public ExpenseDTOList withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ExpenseDTOList withMessage(String message) {
        this.message = message;
        return this;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public ExpenseDTOList withContent(Content content) {
        this.content = content;
        return this;
    }

}
