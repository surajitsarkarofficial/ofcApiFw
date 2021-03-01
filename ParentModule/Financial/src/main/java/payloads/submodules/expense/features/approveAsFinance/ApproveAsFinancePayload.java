package payloads.submodules.expense.features.approveAsFinance;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author german.massello
 *
 */
public class ApproveAsFinancePayload {

	private List<ItemsExpenseReport> expenseReports;
	private String approvalAction;

	/**
	 * This method will create a payload in order to approve or reject by finance a report.
	 * @param action
	 * @param reportId
	 * @param expenseId
	 */
	public ApproveAsFinancePayload(String action, String reportId, String expenseId) {
		this.expenseReports = new LinkedList<>();
		ItemsExpenseReport itemObject = new ItemsExpenseReport(action, reportId);
		ArrayList<ExpensesFlag> expensesFlagList= new ArrayList<ExpensesFlag>();
		expensesFlagList.add(new ExpensesFlag(expenseId));
		itemObject.setExpensesFlagList(expensesFlagList);
		this.expenseReports.add(itemObject);
		this.approvalAction = action;
	}

	/**
	 * This method will create a payload in order to approve or reject by finance a report with two benefits.
	 * @param action
	 * @param reportId
	 * @param expenseIdOne
	 * @param expenseIdTwo
	 */
	public ApproveAsFinancePayload(String action, String reportId, String expenseIdOne, String expenseIdTwo) {
		this.expenseReports = new LinkedList<>();
		ItemsExpenseReport itemObject = new ItemsExpenseReport(action, reportId);
		ArrayList<ExpensesFlag> expensesFlagList= new ArrayList<ExpensesFlag>();
		expensesFlagList.add(new ExpensesFlag(expenseIdOne));
		expensesFlagList.add(new ExpensesFlag(expenseIdTwo));
		itemObject.setExpensesFlagList(expensesFlagList);
		this.expenseReports.add(itemObject);
		this.approvalAction = action;
	}
	
	public List<ItemsExpenseReport> getExpenseReports() {
		return expenseReports;
	}

	public void setExpenseReports(List<ItemsExpenseReport> expenseReports) {
		this.expenseReports = expenseReports;
	}

	public String getApprovalAction() {
		return approvalAction;
	}

	public void setApprovalAction(String approvalAction) {
		this.approvalAction = approvalAction;
	}
	

}
