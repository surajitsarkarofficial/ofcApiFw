package payloads.submodules.expense.features.expense;

import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;
import dto.submodules.expense.receipt.ReceiptForExpense;
import payloads.submodules.expense.ExpensePayLoadHelper;

/**
 * @author german.massello
 *
 */
public class EditReceiptPayload extends ExpensePayLoadHelper {
	private String id;
	private String urlImage;
	private String receiptId;
	private String amount;
	private String provider;
	
	/**
	 * This constructor will create a payload in order to add a receipt to an expense.
	 * @param myBenefit
	 * @param receipt
	 */
	public EditReceiptPayload(ReportWithExpenseDTO myBenefit, ReceiptForExpense receipt, int position) {
		this.id=myBenefit.getContent().getExpenses().get(position).getId();
		this.amount=myBenefit.getContent().getExpenses().get(position).getAmount();
		this.provider=myBenefit.getContent().getExpenses().get(position).getProvider();
		this.urlImage=receipt.getUrlImage();
		this.receiptId=receipt.getReceiptId();
	}
	
	/**
	 * This constructor will create a payload in order to edit a benefit.
	 * @param myBenefit
	 * @param receipt
	 */
	public EditReceiptPayload(ReportWithExpenseDTO myBenefit) {
		String amount=myBenefit.getContent().getExpenses().get(0).getAmount();
		this.id=myBenefit.getContent().getExpenses().get(0).getId();
		this.amount=amount.substring(0,1);
		this.provider="provider Number:"+myBenefit.getContent().getExpenses().get(0).getId();
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrlImage() {
		return urlImage;
	}
	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	public String getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

}
