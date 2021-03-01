package parameters.submodules.expense.features.getReports;

import java.util.Map;

import parameters.submodules.expense.ExpenseParameters;

/**
 * @author german.massello
 *
 */
public class GetExpenseReportDetailsParameters extends ExpenseParameters {

	private String reportId;
	private String userName;
	private String clientName;

	public GetExpenseReportDetailsParameters(Map<String,String> reportAndUsernameAndClient) {
		this.reportId = reportAndUsernameAndClient.get("reportId");
		this.userName = reportAndUsernameAndClient.get("username");
		this.clientName = reportAndUsernameAndClient.get("clientName");
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
}
