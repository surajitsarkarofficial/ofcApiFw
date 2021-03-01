package payloads.submodules.expense.features.receipt;

/**
 * This class will build a payload in order to edit a receipt.
 * @author german.massello
 *
 */
public class EditReceiptPayload {
	private String key;
	private String value;
	private String description;
	
	public EditReceiptPayload() {
		this.key = "disabled";
		this.value = "true";
		this.description = "";
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
	
	
}
