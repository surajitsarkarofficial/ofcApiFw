package parameters.submodules.expense.features.getReports;

/**
 * @author german.massello
 *
 */
public class GetMyReportsStatusFilter {
	private String username;
	private String reportId;
	private String status;
	private String isBenefit;
	private String type;
	private String title;
	private String isGlobalView;
	
	public GetMyReportsStatusFilter(String username, String reportId, String status, String isBenefit, String type, String title, String isGlobalView) {
		this.username = username;
		this.reportId = reportId;
		this.status = status;
		this.isBenefit = isBenefit;
		this.type = type;
		this.title = title;
		this.isGlobalView = isGlobalView;
	}
	
	public GetMyReportsStatusFilter() {
		this.reportId ="";
		this.type = "glober";
		this.status = "DRAFT";
		this.isGlobalView = "false";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsBenefit() {
		return isBenefit;
	}

	public void setIsBenefit(String isBenefit) {
		this.isBenefit = isBenefit;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsGlobalView() {
		return isGlobalView;
	}

	public void setIsGlobalView(String isGlobalView) {
		this.isGlobalView = isGlobalView;
	}
	
}
