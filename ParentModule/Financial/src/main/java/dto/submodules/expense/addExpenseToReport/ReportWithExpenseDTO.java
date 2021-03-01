
package dto.submodules.expense.addExpenseToReport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import parameters.submodules.expense.features.getReports.GetExpenseReportParameters;
import payloads.submodules.expense.features.addExpenseToReport.AddRemoveExpenseToReportPayload;

/**
 * @author german.massello
 *
 */
public class ReportWithExpenseDTO {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("content")
    @Expose
    private Content content;
    transient AddRemoveExpenseToReportPayload addExpenseToReportPayload; 
    transient String authorId; 

    public ReportWithExpenseDTO() {
    }

    /**
     * This constructor will initialize the content.
     * @param GetExpenseReportParameters
     */
    public ReportWithExpenseDTO(GetExpenseReportParameters parameter) {
    	Content content = new Content(parameter);
    	this.content=content;
    }
   
    /**
     * This constructor will initialize the content.
     * @param FromBenefitToExpense
     */
    public ReportWithExpenseDTO(FromBenefitToExpense parameter) {
    	Content content = new Content(parameter);
    	this.content=content;
    }
    
    /**
     * This constructor will define status, message and the content.
     * @param message
     * @param content
     * @param status
     */
    public ReportWithExpenseDTO(String status, String message, Content content) {
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

	public AddRemoveExpenseToReportPayload getAddExpenseToReportPayload() {
		return addExpenseToReportPayload;
	}

	public void setAddExpenseToReportPayload(AddRemoveExpenseToReportPayload addExpenseToReportPayload) {
		this.addExpenseToReportPayload = addExpenseToReportPayload;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

}
