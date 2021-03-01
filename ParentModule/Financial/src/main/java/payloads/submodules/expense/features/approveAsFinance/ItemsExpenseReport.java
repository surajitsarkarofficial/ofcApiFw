package payloads.submodules.expense.features.approveAsFinance;

import java.util.ArrayList;

/**
 * @author german.massello
 *
 */
public class ItemsExpenseReport {

	private String comment;
	private String expenseReport;
	private ArrayList<ExpensesFlag> expensesFlagList;
	
	/**
	 * This method will create a ItemsExpenseReport object.
	 * This object is going to be used in the payload for approve o reject by finance.
	 * @param action
	 * @param reportId
	 */
	public ItemsExpenseReport(String action, String reportId) {
		this.comment = action + " by Finance";
		this.expenseReport = reportId;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getExpenseReport() {
		return expenseReport;
	}
	
	public void setExpenseReport(String expenseReport) {
		this.expenseReport = expenseReport;
	}

	public ArrayList<ExpensesFlag> getExpensesFlagList() {
		return expensesFlagList;
	}

	public void setExpensesFlagList(ArrayList<ExpensesFlag> rejectableExpensesFlagList) {
		this.expensesFlagList = rejectableExpensesFlagList;
	}
}
