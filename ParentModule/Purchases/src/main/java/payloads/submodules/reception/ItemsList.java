
package payloads.submodules.reception;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import dto.submodules.purchaseOrder.get.Content_;

/**
 * @author german.massello
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "itemNumber",
    "poNumber",
    "receivedItems",
    "uploadedImages"
})
public class ItemsList {

    @JsonProperty("itemNumber")
    private String itemNumber;
    @JsonProperty("poNumber")
    private String poNumber;
    @JsonProperty("receivedItems")
    private String receivedItems;
    @JsonProperty("uploadedImages")
    private String uploadedImages;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    
    /**
     * This constructor will create a ItemsList object.
     * @param purchaseOrder
     */
    public ItemsList(Content_ purchaseOrder) {
    	this.itemNumber=purchaseOrder.getItemNumber();
    	this.poNumber=purchaseOrder.getPoNumber();
    	this.receivedItems="0";
    	this.uploadedImages="0";
	}

	@JsonProperty("itemNumber")
    public String getItemNumber() {
        return itemNumber;
    }

    @JsonProperty("itemNumber")
    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    @JsonProperty("poNumber")
    public String getPoNumber() {
        return poNumber;
    }

    @JsonProperty("poNumber")
    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    @JsonProperty("receivedItems")
    public String getReceivedItems() {
        return receivedItems;
    }

    @JsonProperty("receivedItems")
    public void setReceivedItems(String receivedItems) {
        this.receivedItems = receivedItems;
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
