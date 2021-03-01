/**
 * 
 */
package dto.submodules.expense.receipt;

import dto.FinancialDTO;

/**
 * @author german.massello
 *
 */
public class ReceiptForExpense extends FinancialDTO {

	private String receiptId;
	private String urlImage;
	private String metadataId;

	public ReceiptForExpense() {
	}
	
	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public void setMetadataId(String metadataId) {
		this.metadataId = metadataId;
	}

	public String getReceiptId() {
		return receiptId;
	}
	public String getUrlImage() {
		return urlImage;
	}
	public String getMetadataId() {
		return metadataId;
	}
}
