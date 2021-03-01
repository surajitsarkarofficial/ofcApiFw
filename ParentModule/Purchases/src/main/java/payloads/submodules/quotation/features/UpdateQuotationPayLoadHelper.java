package payloads.submodules.quotation.features;

import dto.submodules.quotation.post.QuotationResponseDTO;
import payloads.submodules.quotation.QuotationPayLoadHelper;

/**
 * @author german.massello
 *
 */
public class UpdateQuotationPayLoadHelper extends QuotationPayLoadHelper {

    /**
     * This method will create a payLoad in order to update a quotation.
     * @param payload
     * @param response
     * @return payload
     */
    public QuotationPayLoadHelper updateQuotationPayLoad(QuotationResponseDTO quotationResponse) {
    	QuotationPayLoadHelper payload = quotationResponse.getQuotationPayLoad();
    	payload.setNumber(quotationResponse.getContent().getNumber());
    	payload.getItems().get(0).setId(quotationResponse.getContent().getItems().get(0).getId());
    	payload.getItems().get(0).setQuantity("2");
    	payload.setTitle("Edited Quotation N:"+todayInMs);
    	return payload;
    }
    
}
