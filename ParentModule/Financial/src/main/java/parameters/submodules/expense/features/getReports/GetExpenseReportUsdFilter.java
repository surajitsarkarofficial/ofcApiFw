package parameters.submodules.expense.features.getReports;

/**
 * @author german.massello
 *
 */
public class GetExpenseReportUsdFilter {
	private String usdFrom;
	private String usdTo;
	private String reportId;
	
	public GetExpenseReportUsdFilter() {
	}
	
	public String getUsdFrom() {
		return usdFrom;
	}
	
	public void setUsdFrom(String usdFrom) {
		this.usdFrom = usdFrom;
	}
	
	public String getUsdTo() {
		return usdTo;
	}
	
	public void setUsdTo(String usdTo) {
		this.usdTo = usdTo;
	}
	
	public String getReportId() {
		return reportId;
	}
	
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

}
