package parameters.submodules.expense.features.getReportsAmount;

import java.sql.SQLException;
import java.util.Random;

import database.submodules.expense.ExpenseDBHelper;
import parameters.submodules.expense.ExpenseParameters;

/**
 * @author german.massello
 *
 */
public class GetReportsAmountStatusFilter extends ExpenseParameters {
	private String status;
	private String type;
	private String reportId;
	private String isBenefit;
	private String username;
	private String usdFrom;
	private String usdTo;

	public GetReportsAmountStatusFilter(String status, String reportId, String isBenefit, String usdFrom, String usdTo) throws SQLException {
		String [] roleNameOptions = {"Finance", "APAndExpensesAnalyst"};
		this.status = status;
		this.type = "glober";
		this.reportId = reportId;
		this.isBenefit = isBenefit;
		this.username = new ExpenseDBHelper().getRandomGloberByRol(roleNameOptions[new Random().nextInt(roleNameOptions.length)]);
		this.usdFrom = usdFrom;
		this.usdTo = usdTo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getIsBenefit() {
		return isBenefit;
	}

	public void setIsBenefit(String isBenefit) {
		this.isBenefit = isBenefit;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
	
}
