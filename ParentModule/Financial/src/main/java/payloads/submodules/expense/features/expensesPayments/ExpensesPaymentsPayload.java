package payloads.submodules.expense.features.expensesPayments;

/**
 * @author german.massello
 *
 */
public class ExpensesPaymentsPayload {
	private String[] reportIds = new String[1];
	private String status;
	
	public ExpensesPaymentsPayload(PreparingPayments parameters) {
		this.status="PREPARING_PAYMENT";
		this.reportIds[0]=parameters.getReportId();
	}
	
	public ExpensesPaymentsPayload(Paid parameters) {
		this.status="PAID";
		this.reportIds[0]=parameters.getReportId();
	}
	
	public String[] getReportIds() {
		return reportIds;
	}
	
	public void setReportIds(String[] reportIds) {
		this.reportIds = reportIds;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
