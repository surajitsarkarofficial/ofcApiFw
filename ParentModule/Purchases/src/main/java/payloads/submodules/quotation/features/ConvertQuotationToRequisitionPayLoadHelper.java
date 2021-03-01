package payloads.submodules.quotation.features;

import java.util.LinkedList;

import dto.submodules.quotation.post.QuotationResponseDTO;
import payloads.submodules.quotation.Item;
import payloads.submodules.quotation.QuotationPayLoadHelper;

/**
 * @author german.massello
 *
 */
public class ConvertQuotationToRequisitionPayLoadHelper extends QuotationPayLoadHelper {
	
	private String quotationId;
	
    /**
     * This method will create a payload in order to convert a quotation into a requisition.
     * @param response
     * @return payload
     * @author german.massello
     */
    public ConvertQuotationToRequisitionPayLoadHelper convertToRequisitionPayLoad(QuotationResponseDTO response) {
    	ConvertQuotationToRequisitionPayLoadHelper payload = new ConvertQuotationToRequisitionPayLoadHelper();
    	payload.setTitle(response.getContent().getTitle());
    	payload.setRequesterId(response.getContent().getRequesterId());
    	payload.setType(response.getContent().getType());
    	payload.setTotalAmount(response.getContent().getItems().get(0).getUnitCost());
    	payload.setStatus("DRAFT");
    	payload.setState("DRAFT");    	
    	payload.setCountryId(response.getContent().getCountryId());
    	payload.setSymbolized(response.getContent().getSymbolized());
    	payload.setBaseProjectId(response.getContent().getBaseProjectId());
    	payload.setGroupId(response.getContent().getGroupId());
    	payload.setGroupName(response.getContent().getGroupName());
    	payload.setQuotationId(response.getContent().getNumber());
    	payload.setItems(new LinkedList<>());
    	Item item = new Item();
    	item.setSupplyId(response.getContent().getItems().get(0).getSupplyId());
    	item.setQuantity(response.getContent().getItems().get(0).getQuantity());
    	item.setCurrencyId(response.getContent().getItems().get(0).getCurrencyId());
    	item.setUnitCost(response.getContent().getItems().get(0).getUnitCost());
    	item.setLocalCurrencySubtotal(response.getContent().getItems().get(0).getUnitCost());
    	item.setUsdRateConversion(response.getContent().getItems().get(0).getUsdRateConversion());
    	item.setUsdRateConversion(response.getContent().getItems().get(0).getUsdSubtotal());
    	item.setDeliveryPlaceId(response.getContent().getItems().get(0).getDeliveryPlaceId());
    	item.setExpectedDeliveryDate(response.getContent().getItems().get(0).getExpectedDeliveryDate());
    	item.setTechnicalSpecifications(response.getContent().getItems().get(0).getTechnicalSpecifications());
    	item.setProjectId(response.getContent().getItems().get(0).getProjectId());
    	payload.getItems().add(item);
     	return payload;
    }

	public String getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(String quotationId) {
		this.quotationId = quotationId;
	}
    
}
