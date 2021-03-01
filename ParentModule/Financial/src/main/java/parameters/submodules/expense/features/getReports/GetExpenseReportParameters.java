package parameters.submodules.expense.features.getReports;

import java.sql.SQLException;
import java.util.Random;

import database.submodules.expense.ExpenseDBHelper;
import parameters.submodules.expense.ExpenseParameters;
import parameters.submodules.expense.features.getReportsAmount.GetReportsAmountStatusFilter;

/**
 * @author german.massello
 *
 */
public class GetExpenseReportParameters extends ExpenseParameters {
	private String status="APPROVED_BY_MANAGER";
	private String isEditable="false";
	private String isApprovalView="false";
	private String isGlobalView="true";
	private String type="apandexpensesanalyst";
	private String sortCriteria="lastModified";
	private String usdFrom="";
	private String usdTo="";
	private String reportId="";
	private String username="";
	private String isBenefit="";
	private String title;
	
	/**
	 * Default constructor
	 * @throws SQLException
	 */
	public GetExpenseReportParameters() throws SQLException {
		String [] roleNameOptions = {"Finance", "APAndExpensesAnalyst"};
		this.pageSize="200";
		this.pageNum="0";
		this.sortAscending="false";
		this.username=new ExpenseDBHelper().getRandomGloberByRol(roleNameOptions[new Random().nextInt(roleNameOptions.length)]);
	}
	
	/**
	 * This constructor will complete the parameters in order to validate the USD filter. 
	 * @param filter
	 * @throws SQLException
	 */
	public GetExpenseReportParameters(GetExpenseReportUsdFilter filter) throws SQLException {
		this();
		this.usdFrom=filter.getUsdFrom();
		this.usdTo=filter.getUsdTo();
		this.reportId=filter.getReportId();
	}

	/**
	 * This constructor will complete the parameters in order to validate the is benefit filter.
	 * @param filter
	 * @throws SQLException
	 */
	public GetExpenseReportParameters(GetExpenseReportIsBenefitFilter filter) throws SQLException {
		this();
		this.isBenefit="true";
		this.reportId=filter.getReportId();
	}
	
	/**
	 * This constructor will complete the parameters in order to validate get my reports.
	 * @param filter
	 * @throws SQLException
	 */
	public GetExpenseReportParameters(GetMyReportsStatusFilter filter) throws SQLException {
		this.status=filter.getStatus();
		this.type=filter.getType();
		this.username=filter.getUsername();
		this.reportId=filter.getReportId();
		this.title = filter.getTitle();
		this.isGlobalView = filter.getIsGlobalView();
	}
	
	/**
	 * This constructor will complete the parameters in order to validate get reports amounts.
	 * @param filter
	 * @throws SQLException
	 */
	public GetExpenseReportParameters(GetReportsAmountStatusFilter filter) throws SQLException {
		this.status=filter.getStatus();
		this.type=filter.getType();
		this.username=filter.getUsername();
		this.reportId=filter.getReportId();
		this.isBenefit=filter.getIsBenefit();
	}
	
	

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIsEditable() {
		return isEditable;
	}
	public void setIsEditable(String isEditable) {
		this.isEditable = isEditable;
	}
	public String getIsApprovalView() {
		return isApprovalView;
	}
	public void setIsApprovalView(String isApprovalView) {
		this.isApprovalView = isApprovalView;
	}
	public String getIsGlobalView() {
		return isGlobalView;
	}
	public void setIsGlobalView(String isGlobalView) {
		this.isGlobalView = isGlobalView;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSortCriteria() {
		return sortCriteria;
	}
	public void setSortCriteria(String sortCriteria) {
		this.sortCriteria = sortCriteria;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIsBenefit() {
		return isBenefit;
	}

	public void setIsBenefit(String isBenefit) {
		this.isBenefit = isBenefit;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}