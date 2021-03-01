package payloads.submodules.expense.features.approveAsFinance;

/**
 * @author german.massello
 *
 */
public class ExpensesFlag {
	
	private String expenseNumber;
	private String rejectableFlag;
	private String outOfPolicyFlag;
	private String fixedAssetFlag;
	
	/**
	 * This method will create ExpensesFlag object.
	 * This object is going to be used in the payload for approve o reject by finance.
	 * @param expenseId
	 */
	public ExpensesFlag(String expenseId) {
		this.expenseNumber = expenseId;
		this.rejectableFlag = "false";
		this.outOfPolicyFlag = "false";
		this.fixedAssetFlag = "true";
	}
	
	public String getExpenseNumber() {
		return expenseNumber;
	}
	
	public void setExpenseNumber(String expenseNumber) {
		this.expenseNumber = expenseNumber;
	}
	
	public String getRejectableFlag() {
		return rejectableFlag;
	}
	
	public void setRejectableFlag(String flag) {
		this.rejectableFlag = flag;
	}
	
	public String getOutOfPolicyFlag() {
		return outOfPolicyFlag;
	}
	
	public void setOutOfPolicyFlag(String outOfPolicyFlag) {
		this.outOfPolicyFlag = outOfPolicyFlag;
	}
	
	public String getFixedAssetFlag() {
		return fixedAssetFlag;
	}
	
	public void setFixedAssetFlag(String fixedAssetFlag) {
		this.fixedAssetFlag = fixedAssetFlag;
	}
	
}
