package payloads.submodules.expense.features.sendToApprove;

import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;

/**
 * @author german.massello
 *
 */
public class ReportPayload {

	private String id;
	private String projectId;
	
	/**
	 * This method will create a object with an id and projectId.
	 * This object is going to be used in the SendToApprovePayload constructor.
	 * @param reportWithExpenseDTO
	 * @return reportPayload
	 */
	public ReportPayload(ReportWithExpenseDTO reportWithExpenseDTO) {
		this.id = reportWithExpenseDTO.getContent().getId();
		this.projectId = reportWithExpenseDTO.getContent().getOriginId();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
}
