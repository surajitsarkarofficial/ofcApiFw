
package dto.submodules.quotation.post;

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
    "baseProjectId",
    "baseProjectName",
    "comments",
    "countryId",
    "countryName",
    "groupId",
    "groupName",
    "handler",
    "handlerId",
    "items",
    "lastModification",
    "number",
    "reception",
    "requester",
    "requesterId",
    "requisitionDate",
    "requisitionId",
    "societyId",
    "societyName",
    "state",
    "status",
    "symbolized",
    "title",
    "totalAmount",
    "type"
})
public class Content {

    @JsonProperty("baseProjectId")
    private String baseProjectId;
    @JsonProperty("baseProjectName")
    private String baseProjectName;
    @JsonProperty("comments")
    private String comments;
    @JsonProperty("countryId")
    private String countryId;
    @JsonProperty("countryName")
    private String countryName;
    @JsonProperty("groupId")
    private String groupId;
    @JsonProperty("groupName")
    private String groupName;
    @JsonProperty("handler")
    private String handler;
    @JsonProperty("handlerId")
    private String handlerId;
    @JsonProperty("items")
    private List<Item> items = null;
    @JsonProperty("lastModification")
    private String lastModification;
    @JsonProperty("number")
    private String number;
    @JsonProperty("reception")
    private Reception reception;
    @JsonProperty("requester")
    private String requester;
    @JsonProperty("requesterId")
    private String requesterId;
    @JsonProperty("requisitionDate")
    private String requisitionDate;
    @JsonProperty("requisitionId")
    private String requisitionId;
    @JsonProperty("societyId")
    private String societyId;
    @JsonProperty("societyName")
    private String societyName;
    @JsonProperty("state")
    private String state;
    @JsonProperty("status")
    private String status;
    @JsonProperty("symbolized")
    private String symbolized;
    @JsonProperty("title")
    private String title;
    @JsonProperty("totalAmount")
    private String totalAmount;
    @JsonProperty("type")
    private String type;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("baseProjectId")
    public String getBaseProjectId() {
        return baseProjectId;
    }

    @JsonProperty("baseProjectId")
    public void setBaseProjectId(String baseProjectId) {
        this.baseProjectId = baseProjectId;
    }

    @JsonProperty("baseProjectName")
    public String getBaseProjectName() {
        return baseProjectName;
    }

    @JsonProperty("baseProjectName")
    public void setBaseProjectName(String baseProjectName) {
        this.baseProjectName = baseProjectName;
    }

    @JsonProperty("comments")
    public String getComments() {
        return comments;
    }

    @JsonProperty("comments")
    public void setComments(String comments) {
        this.comments = comments;
    }

    @JsonProperty("countryId")
    public String getCountryId() {
        return countryId;
    }

    @JsonProperty("countryId")
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    @JsonProperty("countryName")
    public String getCountryName() {
        return countryName;
    }

    @JsonProperty("countryName")
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @JsonProperty("groupId")
    public String getGroupId() {
        return groupId;
    }

    @JsonProperty("groupId")
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @JsonProperty("groupName")
    public String getGroupName() {
        return groupName;
    }

    @JsonProperty("groupName")
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @JsonProperty("handler")
    public String getHandler() {
        return handler;
    }

    @JsonProperty("handler")
    public void setHandler(String handler) {
        this.handler = handler;
    }

    @JsonProperty("handlerId")
    public String getHandlerId() {
        return handlerId;
    }

    @JsonProperty("handlerId")
    public void setHandlerId(String handlerId) {
        this.handlerId = handlerId;
    }

    @JsonProperty("items")
    public List<Item> getItems() {
        return items;
    }

    @JsonProperty("items")
    public void setItems(List<Item> items) {
        this.items = items;
    }

    @JsonProperty("lastModification")
    public String getLastModification() {
        return lastModification;
    }

    @JsonProperty("lastModification")
    public void setLastModification(String lastModification) {
        this.lastModification = lastModification;
    }

    @JsonProperty("number")
    public String getNumber() {
        return number;
    }

    @JsonProperty("number")
    public void setNumber(String number) {
        this.number = number;
    }

    @JsonProperty("reception")
    public Reception getReception() {
        return reception;
    }

    @JsonProperty("reception")
    public void setReception(Reception reception) {
        this.reception = reception;
    }

    @JsonProperty("requester")
    public String getRequester() {
        return requester;
    }

    @JsonProperty("requester")
    public void setRequester(String requester) {
        this.requester = requester;
    }

    @JsonProperty("requesterId")
    public String getRequesterId() {
        return requesterId;
    }

    @JsonProperty("requesterId")
    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    @JsonProperty("requisitionDate")
    public String getRequisitionDate() {
        return requisitionDate;
    }

    @JsonProperty("requisitionDate")
    public void setRequisitionDate(String requisitionDate) {
        this.requisitionDate = requisitionDate;
    }

    @JsonProperty("requisitionId")
    public String getRequisitionId() {
        return requisitionId;
    }

    @JsonProperty("requisitionId")
    public void setRequisitionId(String requisitionId) {
        this.requisitionId = requisitionId;
    }

    @JsonProperty("societyId")
    public String getSocietyId() {
        return societyId;
    }

    @JsonProperty("societyId")
    public void setSocietyId(String societyId) {
        this.societyId = societyId;
    }

    @JsonProperty("societyName")
    public String getSocietyName() {
        return societyName;
    }

    @JsonProperty("societyName")
    public void setSocietyName(String societyName) {
        this.societyName = societyName;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("symbolized")
    public String getSymbolized() {
        return symbolized;
    }

    @JsonProperty("symbolized")
    public void setSymbolized(String symbolized) {
        this.symbolized = symbolized;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("totalAmount")
    public String getTotalAmount() {
        return totalAmount;
    }

    @JsonProperty("totalAmount")
    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
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
