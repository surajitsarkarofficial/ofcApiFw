package endpoints.submodules.expense.features.report;

import endpoints.submodules.expense.ExpenseEndpoints;

/**
 * @author german.massello
 *
 */
public class GetReportReportEndpoints extends ExpenseEndpoints {
	public static String getReports = "/proxy/glow/ticketsservice/tickets/reports?status=%s&isEditable=%s&isApprovalView=%s&isGlobalView=%s"
			+ "&pageSize=%s&pageNum=%s&type=%s&sortAscending=%s&sortCriteria=%s&usdFrom=%s&usdTo=%s&isBenefit=%s&reportId=%s";
	public static String getReportDetails = "/proxy/glow/ticketsservice/tickets/%s";
	public static String getReportsAmounts = "/proxy/glow/ticketsservice/tickets/amounts?status=%s&isEditable=%s&type=%s&pageSize=%s&pageNum=%s"
			+ "&sortAscending=%s&sortCriteria=%s&reportId=%s&isBenefit=%s&usdFrom=%s&usdTo=%s";
}
