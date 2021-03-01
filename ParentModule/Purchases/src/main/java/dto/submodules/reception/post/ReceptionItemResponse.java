
package dto.submodules.reception.post;

import java.util.HashMap;
import java.util.List;
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
    "receptionNumber",
    "comments",
    "itemsList"
})
public class ReceptionItemResponse {

    @JsonProperty("receptionNumber")
    private Object receptionNumber;
    @JsonProperty("comments")
    private String comments;
    @JsonProperty("itemsList")
    private List<ItemsList> itemsList = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("receptionNumber")
    public Object getReceptionNumber() {
        return receptionNumber;
    }

    @JsonProperty("receptionNumber")
    public void setReceptionNumber(Object receptionNumber) {
        this.receptionNumber = receptionNumber;
    }

    @JsonProperty("comments")
    public String getComments() {
        return comments;
    }

    @JsonProperty("comments")
    public void setComments(String comments) {
        this.comments = comments;
    }

    @JsonProperty("itemsList")
    public List<ItemsList> getItemsList() {
        return itemsList;
    }

    @JsonProperty("itemsList")
    public void setItemsList(List<ItemsList> itemsList) {
        this.itemsList = itemsList;
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
