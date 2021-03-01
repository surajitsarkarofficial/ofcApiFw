package payloads.submodules.expense.features.sendToApprove;

import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;

/**
 * @author german.massello
 *
 */
public class SendToApprovePayload {

	private String action;
	private String authorId;
	private ReportPayload expenseReportDTO;
	private String rejectionComment;
	
	/**
	 * This method will create a payload in order to send a report to approve. 
	 * @param reportWithExpenseDTO
	 */
	public SendToApprovePayload(ReportWithExpenseDTO reportWithExpenseDTO) {
		this.expenseReportDTO = new ReportPayload(reportWithExpenseDTO);
		this.action = "approve";
		this.authorId = reportWithExpenseDTO.getAuthorId();
	}

	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getRejectionComment() {
		return rejectionComment;
	}
	public void setRejectionComment(String rejectionComment) {
		this.rejectionComment = rejectionComment;
	}

	public ReportPayload getExpenseReportDTO() {
		return expenseReportDTO;
	}

	public void setExpenseReportDTO(ReportPayload expenseReportDTO) {
		this.expenseReportDTO = expenseReportDTO;
	}
	
}
