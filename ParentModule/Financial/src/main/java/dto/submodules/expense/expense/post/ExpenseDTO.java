
package dto.submodules.expense.expense.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import payloads.submodules.expense.features.expense.ExpensePayload;

public class ExpenseDTO {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("content")
    @Expose
    private Content content;
    transient ExpensePayload expensePayload;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ExpenseDTO() {
    }

    /**
     * 
     * @param message
     * @param content
     * @param status
     */
    public ExpenseDTO(String status, String message, Content content) {
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

	public ExpensePayload getExpensePayload() {
		return expensePayload;
	}

	public void setExpensePayload(ExpensePayload expensePayload) {
		this.expensePayload = expensePayload;
	}

}
