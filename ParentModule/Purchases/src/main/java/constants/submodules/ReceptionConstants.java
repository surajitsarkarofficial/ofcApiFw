package constants.submodules;

import constants.PurchasesConstants;

/**
 * @author german.massello
 *
 */
public interface ReceptionConstants extends PurchasesConstants {

	public String RECEPTION_COMPLETED_DB = "Completed";
	public String RECEPTION_COMPLETED_REST = "COMPLETED";
	
	public String RECEPTION_IN_PROGRESS_DB = "Progress";
	public String RECEPTION_IN_PROGRESS_REST = "PROGRESS";
	
	public String EMPTY_STATUS_REST = "";
}
