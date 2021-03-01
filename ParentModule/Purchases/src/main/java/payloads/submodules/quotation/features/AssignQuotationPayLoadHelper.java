package payloads.submodules.quotation.features;

import dto.submodules.quotation.post.QuotationResponseDTO;
import payloads.submodules.quotation.QuotationPayLoadHelper;

/**
 * @author german.massello
 *
 */
public class AssignQuotationPayLoadHelper extends QuotationPayLoadHelper {
	
	/**
	* This method will create a payload in order to assigned a quotation.
	* @param quotation
	* @return payload
	* @author german.massello
	*/
	public AssignQuotationPayLoadHelper assignQuotationPayLoad(QuotationResponseDTO quotation) {
		AssignQuotationPayLoadHelper payload = new AssignQuotationPayLoadHelper();
		payload.setState("QUOTATION").setStatus("PENDING_TO_RESOLVE").setNumber(quotation.getContent().getNumber());
	return payload;
	}
	
}
