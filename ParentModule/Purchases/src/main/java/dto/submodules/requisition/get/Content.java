
package dto.submodules.requisition.get;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "totalItems",
    "requisitionList"
})
public class Content {

    @JsonProperty("totalItems")
    private String totalItems;
    @JsonProperty("requisitionList")
    private List<RequisitionList> requisitionList = null;
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

    @JsonProperty("requisitionList")
    public List<RequisitionList> getRequisitionList() {
        return requisitionList;
    }

    @JsonProperty("requisitionList")
    public void setRequisitionList(List<RequisitionList> requisitionList) {
        this.requisitionList = requisitionList;
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
