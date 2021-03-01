
package dto.submodules.expense.currency;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrencyDTOList {

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
    public CurrencyDTOList() {
    }

    /**
     * 
     * @param message
     * @param content
     * @param status
     */
    public CurrencyDTOList(String status, String message, Content content) {
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
