
package dto.submodules.quotation.post;

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
    "comments",
    "lastMod",
    "number",
    "owner",
    "poNumber",
    "receptionDate",
    "receptor",
    "requisition",
    "sapNotification",
    "society",
    "status"
})
public class Reception {

    @JsonProperty("comments")
    private String comments;
    @JsonProperty("lastMod")
    private String lastMod;
    @JsonProperty("number")
    private Integer number;
    @JsonProperty("owner")
    private Boolean owner;
    @JsonProperty("poNumber")
    private Integer poNumber;
    @JsonProperty("receptionDate")
    private String receptionDate;
    @JsonProperty("receptor")
    private Integer receptor;
    @JsonProperty("requisition")
    private Integer requisition;
    @JsonProperty("sapNotification")
    private String sapNotification;
    @JsonProperty("society")
    private String society;
    @JsonProperty("status")
    private String status;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("comments")
    public String getComments() {
        return comments;
    }

    @JsonProperty("comments")
    public void setComments(String comments) {
        this.comments = comments;
    }

    @JsonProperty("lastMod")
    public String getLastMod() {
        return lastMod;
    }

    @JsonProperty("lastMod")
    public void setLastMod(String lastMod) {
        this.lastMod = lastMod;
    }

    @JsonProperty("number")
    public Integer getNumber() {
        return number;
    }

    @JsonProperty("number")
    public void setNumber(Integer number) {
        this.number = number;
    }

    @JsonProperty("owner")
    public Boolean getOwner() {
        return owner;
    }

    @JsonProperty("owner")
    public void setOwner(Boolean owner) {
        this.owner = owner;
    }

    @JsonProperty("poNumber")
    public Integer getPoNumber() {
        return poNumber;
    }

    @JsonProperty("poNumber")
    public void setPoNumber(Integer poNumber) {
        this.poNumber = poNumber;
    }

    @JsonProperty("receptionDate")
    public String getReceptionDate() {
        return receptionDate;
    }

    @JsonProperty("receptionDate")
    public void setReceptionDate(String receptionDate) {
        this.receptionDate = receptionDate;
    }

    @JsonProperty("receptor")
    public Integer getReceptor() {
        return receptor;
    }

    @JsonProperty("receptor")
    public void setReceptor(Integer receptor) {
        this.receptor = receptor;
    }

    @JsonProperty("requisition")
    public Integer getRequisition() {
        return requisition;
    }

    @JsonProperty("requisition")
    public void setRequisition(Integer requisition) {
        this.requisition = requisition;
    }

    @JsonProperty("sapNotification")
    public String getSapNotification() {
        return sapNotification;
    }

    @JsonProperty("sapNotification")
    public void setSapNotification(String sapNotification) {
        this.sapNotification = sapNotification;
    }

    @JsonProperty("society")
    public String getSociety() {
        return society;
    }

    @JsonProperty("society")
    public void setSociety(String society) {
        this.society = society;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
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
