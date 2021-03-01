
package dto.submodules.reception.post;

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
    "itemNumber",
    "receivedItems",
    "poNumber",
    "uploadedImages"
})
public class ItemsList {

    @JsonProperty("itemNumber")
    private String itemNumber;
    @JsonProperty("receivedItems")
    private String receivedItems;
    @JsonProperty("poNumber")
    private String poNumber;
    @JsonProperty("uploadedImages")
    private String uploadedImages;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("itemNumber")
    public String getItemNumber() {
        return itemNumber;
    }

    @JsonProperty("itemNumber")
    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    @JsonProperty("receivedItems")
    public String getReceivedItems() {
        return receivedItems;
    }

    @JsonProperty("receivedItems")
    public void setReceivedItems(String receivedItems) {
        this.receivedItems = receivedItems;
    }

    @JsonProperty("poNumber")
    public String getPoNumber() {
        return poNumber;
    }

    @JsonProperty("poNumber")
    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    @JsonProperty("uploadedImages")
    public String getUploadedImages() {
        return uploadedImages;
    }

    @JsonProperty("uploadedImages")
    public void setUploadedImages(String uploadedImages) {
        this.uploadedImages = uploadedImages;
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
