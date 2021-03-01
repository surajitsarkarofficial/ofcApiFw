package dto.submodules.tripCreation.tripDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import dto.submodules.tripCreation.billability.Billability;

/**
 * @author josealberto.gomez
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "tripId",
    "tripName",
    "purposeAndGoals",
    "creatorMail",
    "status",
    "currentState",
    "purpose",
    "createdBy",
    "unreadCommentCount",
    "displayText",
    "travelReason",
    "billability",
    "owner",
    "watchers",
    "rejected",
    "trpStartDate",
    "trpEndDate"
})
public class Trip {
    @JsonProperty("tripId")
    private Integer tripId;
    @JsonProperty("tripName")
    private String tripName;
    @JsonProperty("purposeAndGoals")
    private String purposeAndGoals;
    @JsonProperty("creatorMail")
    private String creatorMail;
    @JsonProperty("status")
    private String status;
    @JsonProperty("currentState")
    private String currentState;
    @JsonProperty("purpose")
    private String purpose;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("unreadCommentCount")
    private Integer unreadCommentCount;
    @JsonProperty("displayText")
    private String displayText;
    @JsonProperty("travelReason")
    private TravelReason travelReason;
    @JsonProperty("billability")
    private Billability billability;
    @JsonProperty("owner")
    private Owner owner;
    @JsonProperty("watchers")
    private List<Object> watchers = null;
    @JsonProperty("rejected")
    private Boolean rejected;
    @JsonProperty("trpStartDate")
    private String trpStartDate;
    @JsonProperty("trpEndDate")
    private String trpEndDate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("tripId")
    public Integer getTripId() {
        return tripId;
    }

    @JsonProperty("tripId")
    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    @JsonProperty("tripName")
    public String getTripName() {
        return tripName;
    }

    @JsonProperty("tripName")
    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    @JsonProperty("purposeAndGoals")
    public String getPurposeAndGoals() {
        return purposeAndGoals;
    }

    @JsonProperty("purposeAndGoals")
    public void setPurposeAndGoals(String purposeAndGoals) {
        this.purposeAndGoals = purposeAndGoals;
    }

    @JsonProperty("creatorMail")
    public String getCreatorMail() {
        return creatorMail;
    }

    @JsonProperty("creatorMail")
    public void setCreatorMail(String creatorMail) {
        this.creatorMail = creatorMail;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("currentState")
    public String getCurrentState() {
        return currentState;
    }

    @JsonProperty("currentState")
    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    @JsonProperty("purpose")
    public String getPurpose() {
        return purpose;
    }

    @JsonProperty("purpose")
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    @JsonProperty("createdBy")
    public String getCreatedBy() {
        return createdBy;
    }

    @JsonProperty("createdBy")
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @JsonProperty("unreadCommentCount")
    public Integer getUnreadCommentCount() {
        return unreadCommentCount;
    }

    @JsonProperty("unreadCommentCount")
    public void setUnreadCommentCount(Integer unreadCommentCount) {
        this.unreadCommentCount = unreadCommentCount;
    }

    @JsonProperty("displayText")
    public String getDisplayText() {
        return displayText;
    }

    @JsonProperty("displayText")
    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    @JsonProperty("travelReason")
    public TravelReason getTravelReason() {
        return travelReason;
    }

    @JsonProperty("travelReason")
    public void setTravelReason(TravelReason travelReason) {
        this.travelReason = travelReason;
    }

    @JsonProperty("billability")
    public Billability getBillability() {
        return billability;
    }

    @JsonProperty("billability")
    public void setBillability(Billability billability) {
        this.billability = billability;
    }

    @JsonProperty("owner")
    public Owner getOwner() {
        return owner;
    }

    @JsonProperty("owner")
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @JsonProperty("watchers")
    public List<Object> getWatchers() {
        return watchers;
    }

    @JsonProperty("watchers")
    public void setWatchers(List<Object> watchers) {
        this.watchers = watchers;
    }

    @JsonProperty("rejected")
    public Boolean getRejected() {
        return rejected;
    }

    @JsonProperty("rejected")
    public void setRejected(Boolean rejected) {
        this.rejected = rejected;
    }

    @JsonProperty("trpStartDate")
    public String getTrpStartDate() {
        return trpStartDate;
    }

    @JsonProperty("trpStartDate")
    public void setTrpStartDate(String trpStartDate) {
        this.trpStartDate = trpStartDate;
    }

    @JsonProperty("trpEndDate")
    public String getTrpEndDate() {
        return trpEndDate;
    }

    @JsonProperty("trpEndDate")
    public void setTrpEndDate(String trpEndDate) {
        this.trpEndDate = trpEndDate;
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
