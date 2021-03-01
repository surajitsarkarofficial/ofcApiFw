
package dto.submodules.purchaseOrder.get;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author german.massello
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "totalItems",
    "requisitionItemsPoPage"
})
public class Content {

    @JsonProperty("totalItems")
    private String totalItems;
    @JsonProperty("requisitionItemsPoPage")
    private RequisitionItemsPoPage requisitionItemsPoPage;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("totalItems")
    public String getTotalItems() {
        return totalItems;
    }

    @JsonProperty("totalItems")
    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    @JsonProperty("requisitionItemsPoPage")
    public RequisitionItemsPoPage getRequisitionItemsPoPage() {
        return requisitionItemsPoPage;
    }

    @JsonProperty("requisitionItemsPoPage")
    public void setRequisitionItemsPoPage(RequisitionItemsPoPage requisitionItemsPoPage) {
        this.requisitionItemsPoPage = requisitionItemsPoPage;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
