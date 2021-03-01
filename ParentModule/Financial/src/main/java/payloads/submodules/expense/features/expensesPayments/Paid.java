package payloads.submodules.expense.features.expensesPayments;

/**
 * @author german.massello
 *
 */
public class Paid {
	private String reportId;

	public Paid(String reportId) {
		this.reportId = reportId;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	
}
