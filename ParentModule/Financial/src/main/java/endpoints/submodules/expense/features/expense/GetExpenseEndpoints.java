package endpoints.submodules.expense.features.expense;

import endpoints.submodules.expense.ExpenseEndpoints;

public class GetExpenseEndpoints extends ExpenseEndpoints {
	public static String getExpensesList = "/proxy/glow/ticketsservice/tickets/detail?sortAscending=%s"
			+ "&costType=%s&linkedToReport=%s&pageSize=%s&pageNum=%s";
	public static String getExpense = "/proxy/glow/ticketsservice/tickets/detail/%s";
}
