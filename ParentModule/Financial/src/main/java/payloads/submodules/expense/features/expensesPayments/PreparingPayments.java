package payloads.submodules.expense.features.expensesPayments;

/**
 * @author german.massello
 *
 */
public class PreparingPayments {
	private String reportId;
	
	public PreparingPayments(String reportId) {
		this.reportId = reportId;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
}
