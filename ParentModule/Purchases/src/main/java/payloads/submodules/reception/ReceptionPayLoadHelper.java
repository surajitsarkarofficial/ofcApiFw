
package payloads.submodules.reception;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import dto.submodules.purchaseOrder.get.Content_;
import dto.submodules.reception.post.ReceptionResponseDTO;

/**
 * @author german.massello
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "receptionNumber",
    "comments",
    "itemsList",
    "sendToSap"
})
public class ReceptionPayLoadHelper {

    @JsonProperty("receptionNumber")
    private Object receptionNumber;
    @JsonProperty("comments")
    private String comments;
    @JsonProperty("itemsList")
    private List<ItemsList> itemsList = null;
    @JsonProperty("sendToSap")
    private String sendToSap;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    
    /**
     * This constructor will create a payload in order to create a reception.
     * @param purchaseOrder
     */
    public ReceptionPayLoadHelper(Content_ purchaseOrder) {
    	this.comments="comments for purchaseOrder:"+purchaseOrder.getPoNumber();
    	this.sendToSap="false";
    	this.itemsList= new LinkedList<>();
    	this.itemsList.add(new ItemsList(purchaseOrder));
	}

    /**
     * This constructor will create a payload in order to update a reception.
     * @param purchaseOrder
     */
    public ReceptionPayLoadHelper(ReceptionResponseDTO reception) {
    	this.comments="<b>automation/b> comments for reception number:"+reception.getContent().getReceptionNum();
    	this.receptionNumber=reception.getContent().getReceptionNum();
	}
    
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

    @JsonProperty("sendToSap")
    public String getSendToSap() {
        return sendToSap;
    }

    @JsonProperty("sendToSap")
    public void setSendToSap(String sendToSap) {
        this.sendToSap = sendToSap;
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
