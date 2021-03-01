
package dto.submodules.expense.report.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import dto.submodules.expense.addExpenseToReport.FromBenefitToExpense;
import dto.submodules.expense.report.get.GetReportDTOList;
import payloads.submodules.expense.features.report.ReportPayload;

/**
 * @author german.massello
 *
 */
public class ReportDTO {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("content")
    @Expose
    private Content content;
    transient ReportPayload reportPayload;
    transient GetReportDTOList getReportDTOList;

    public ReportDTO() {
    }
 
    /**
     * This constructor will initialize the content.
     * @param parameter
     */
    public ReportDTO(FromBenefitToExpense parameter) {
    	Content content = new Content();
    	this.content=content;
    }

    /**
     * This constructor will define status, message and the content.
     * @param message
     * @param content
     * @param status
     */
    public ReportDTO(String status, String message, Content content) {
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

    public ReportDTO withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ReportDTO withMessage(String message) {
        this.message = message;
        return this;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public ReportDTO withContent(Content content) {
        this.content = content;
        return this;
    }

	public ReportPayload getReportPayload() {
		return reportPayload;
	}

	public void setReportPayload(ReportPayload reportPayload) {
		this.reportPayload = reportPayload;
	}

	public GetReportDTOList getGetReportDTOList() {
		return getReportDTOList;
	}

	public void setGetReportDTOList(GetReportDTOList getReportDTOList) {
		this.getReportDTOList = getReportDTOList;
	}

}
