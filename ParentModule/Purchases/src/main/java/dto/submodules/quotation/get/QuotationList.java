
package dto.submodules.quotation.get;

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
    "number",
    "title",
    "requisitionDate",
    "lastModification",
    "requesterId",
    "requester",
    "type",
    "status",
    "state",
    "totalAmount",
    "countryId",
    "countryName",
    "symbolized",
    "societyId",
    "societyName",
    "groupId",
    "groupName",
    "comments",
    "items",
    "reception",
    "baseProjectId",
    "baseProjectName",
    "handlerId",
    "handler",
    "requisitionId"
})
public class QuotationList {

    @JsonProperty("number")
    private String number;
    @JsonProperty("title")
    private String title;
    @JsonProperty("requisitionDate")
    private String requisitionDate;
    @JsonProperty("lastModification")
    private String lastModification;
    @JsonProperty("requesterId")
    private String requesterId;
    @JsonProperty("requester")
    private String requester;
    @JsonProperty("type")
    private String type;
    @JsonProperty("status")
    private String status;
    @JsonProperty("state")
    private String state;
    @JsonProperty("totalAmount")
    private Object totalAmount;
    @JsonProperty("countryId")
    private String countryId;
    @JsonProperty("countryName")
    private String countryName;
    @JsonProperty("symbolized")
    private Boolean symbolized;
    @JsonProperty("societyId")
    private Object societyId;
    @JsonProperty("societyName")
    private Object societyName;
    @JsonProperty("groupId")
    private String groupId;
    @JsonProperty("groupName")
    private String groupName;
    @JsonProperty("comments")
    private Object comments;
    @JsonProperty("items")
    private Object items;
    @JsonProperty("reception")
    private Object reception;
    @JsonProperty("baseProjectId")
    private String baseProjectId;
    @JsonProperty("baseProjectName")
    private String baseProjectName;
    @JsonProperty("handlerId")
    private Object handlerId;
    @JsonProperty("handler")
    private Object handler;
    @JsonProperty("requisitionId")
    private Object requisitionId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("number")
    public String getNumber() {
        return number;
    }

    @JsonProperty("number")
    public void setNumber(String number) {
        this.number = number;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("requisitionDate")
    public String getRequisitionDate() {
        return requisitionDate;
    }

    @JsonProperty("requisitionDate")
    public void setRequisitionDate(String requisitionDate) {
        this.requisitionDate = requisitionDate;
    }

    @JsonProperty("lastModification")
    public String getLastModification() {
        return lastModification;
    }

    @JsonProperty("lastModification")
    public void setLastModification(String lastModification) {
        this.lastModification = lastModification;
    }

    @JsonProperty("requesterId")
    public String getRequesterId() {
        return requesterId;
    }

    @JsonProperty("requesterId")
    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    @JsonProperty("requester")
    public String getRequester() {
        return requester;
    }

    @JsonProperty("requester")
    public void setRequester(String requester) {
        this.requester = requester;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    @JsonProperty("totalAmount")
    public Object getTotalAmount() {
        return totalAmount;
    }

    @JsonProperty("totalAmount")
    public void setTotalAmount(Object totalAmount) {
        this.totalAmount = totalAmount;
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

    @JsonProperty("symbolized")
    public Boolean getSymbolized() {
        return symbolized;
    }

    @JsonProperty("symbolized")
    public void setSymbolized(Boolean symbolized) {
        this.symbolized = symbolized;
    }

    @JsonProperty("societyId")
    public Object getSocietyId() {
        return societyId;
    }

    @JsonProperty("societyId")
    public void setSocietyId(Object societyId) {
        this.societyId = societyId;
    }

    @JsonProperty("societyName")
    public Object getSocietyName() {
        return societyName;
    }

    @JsonProperty("societyName")
    public void setSocietyName(Object societyName) {
        this.societyName = societyName;
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

    @JsonProperty("comments")
    public Object getComments() {
        return comments;
    }

    @JsonProperty("comments")
    public void setComments(Object comments) {
        this.comments = comments;
    }

    @JsonProperty("items")
    public Object getItems() {
        return items;
    }

    @JsonProperty("items")
    public void setItems(Object items) {
        this.items = items;
    }

    @JsonProperty("reception")
    public Object getReception() {
        return reception;
    }

    @JsonProperty("reception")
    public void setReception(Object reception) {
        this.reception = reception;
    }

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

    @JsonProperty("handlerId")
    public Object getHandlerId() {
        return handlerId;
    }

    @JsonProperty("handlerId")
    public void setHandlerId(Object handlerId) {
        this.handlerId = handlerId;
    }

    @JsonProperty("handler")
    public Object getHandler() {
        return handler;
    }

    @JsonProperty("handler")
    public void setHandler(Object handler) {
        this.handler = handler;
    }

    @JsonProperty("requisitionId")
    public Object getRequisitionId() {
        return requisitionId;
    }

    @JsonProperty("requisitionId")
    public void setRequisitionId(Object requisitionId) {
        this.requisitionId = requisitionId;
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
