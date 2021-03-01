package payloads.submodules.expense.features.expense;

import java.util.ArrayList;

public class DeleteExpensePayload {
	private ArrayList<String> ids;

	public DeleteExpensePayload() {
		this.ids = new ArrayList<String>();
	}
	
	public DeleteExpensePayload(String expenseId) {
		this.ids = new ArrayList<String>();
		this.ids.add(expenseId);
	}

	public ArrayList<String> getIds() {
		return ids;
	}

	public void setIds(ArrayList<String> ids) {
		this.ids = ids;
	}
}
