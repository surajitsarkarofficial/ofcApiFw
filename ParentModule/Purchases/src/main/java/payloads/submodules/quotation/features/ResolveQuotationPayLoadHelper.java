package payloads.submodules.quotation.features;

import java.util.LinkedList;

import dto.submodules.quotation.post.QuotationResponseDTO;
import payloads.submodules.quotation.Item;
import payloads.submodules.quotation.QuotationPayLoadHelper;

/**
 * @author german.massello
 *
 */
public class ResolveQuotationPayLoadHelper extends QuotationPayLoadHelper {
    
    /**
     * This method will create a payload in order to resolved a quotation.
     * @param response
     * @return payload
     * @author german.massello
     */
    public ResolveQuotationPayLoadHelper resolveQuotationPayLoad(QuotationResponseDTO response) {
    	ResolveQuotationPayLoadHelper payload = new ResolveQuotationPayLoadHelper();
    	payload.setNumber(response.getContent().getNumber());
    	payload.setTitle(response.getContent().getTitle());
    	payload.setRequisitionDate(response.getContent().getRequisitionDate());
    	payload.setLastModification(response.getContent().getLastModification());
    	payload.setRequesterId(response.getContent().getRequesterId());
    	payload.setRequester(response.getContent().getRequester());
    	payload.setType(response.getContent().getType());
    	payload.setStatus("RESOLVED");
    	payload.setState(response.getContent().getState());
    	payload.setCountryId(response.getContent().getCountryId());
    	payload.setSymbolized(response.getContent().getSymbolized());
    	payload.setGroupId(response.getContent().getGroupId());
    	payload.setGroupName(response.getContent().getGroupName());
    	payload.setItems(new LinkedList<>());
    	Item item = new Item();
    	item.setRequisitionId(response.getContent().getItems().get(0).getRequisitionId());
    	item.setId(response.getContent().getItems().get(0).getId());
    	item.setSupplyId(response.getContent().getItems().get(0).getSupplyId());
    	item.setQuantity(response.getContent().getItems().get(0).getQuantity());
    	item.setUnitCost("99");
    	item.setCurrencyId("116200322");
    	item.setUsdSubtotal("99");
    	item.setExpectedDeliveryDate(response.getContent().getItems().get(0).getExpectedDeliveryDate());
    	item.setTechnicalSpecifications(response.getContent().getItems().get(0).getTechnicalSpecifications());
    	item.setUsdRateConversion("1");
    	item.setConversionDate(response.getContent().getItems().get(0).getConversionDate());
    	item.setDeliveryPlaceId(response.getContent().getItems().get(0).getDeliveryPlaceId());
  
    	item.setStatus(response.getContent().getItems().get(0).getStatus());
    	item.setProjectId(response.getContent().getItems().get(0).getProjectId());
    	item.setStaffDepartment(response.getContent().getItems().get(0).getStaffDepartment());
    	item.setDeliveryPlaceId(response.getContent().getItems().get(0).getDeliveryPlaceId());
    	payload.getItems().add(item);
    	payload.setBaseProjectId(response.getContent().getBaseProjectId());
    	payload.setHandlerId(response.getContent().getHandlerId());
     	return payload;
    }
}
