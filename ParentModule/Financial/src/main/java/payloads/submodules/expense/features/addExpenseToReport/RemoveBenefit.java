package payloads.submodules.expense.features.addExpenseToReport;

import java.util.LinkedList;
import java.util.List;

import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;

/**
 * @author german.massello
 *
 */
public class RemoveBenefit {
	private String reportId;
	private String updateAction;
	private List<Expense> expenses;
	
	
	/**
	 * Default constructor
	 * @param benefit
	 */
	public RemoveBenefit(ReportWithExpenseDTO benefit) {
		this.reportId=benefit.getContent().getId();
		this.updateAction="1";
		List<Expense> expenses = new LinkedList<>();
		Expense expense = new Expense(benefit.getContent().getExpenses().get(0).getId());
		expenses.add(expense);
		this.expenses=expenses;
	}

	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getUpdateAction() {
		return updateAction;
	}
	public void setUpdateAction(String updateAction) {
		this.updateAction = updateAction;
	}
	public List<Expense> getExpenses() {
		return expenses;
	}
	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}
	
}
