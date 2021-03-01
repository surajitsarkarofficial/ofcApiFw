package dto.submodules.tripCreation.tripDetail;

import com.fasterxml.jackson.annotation.*;
import java.util.HashMap;
import java.util.Map;

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
        "travelReason",
        "status"
})
public class TripCreationRequestDTO {
    @JsonProperty("tripId")
    private Integer tripId;
    @JsonProperty("tripName")
    private String tripName;
    @JsonProperty("purposeAndGoals")
    private String purposeAndGoals;
    @JsonProperty("creatorMail")
    private String creatorMail;
    @JsonProperty("travelReason")
    private TravelReason travelReason;
    @JsonProperty("status")
    private String status;
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

    @JsonProperty("travelReason")
    public TravelReason getTravelReason() {
        return travelReason;
    }

    @JsonProperty("travelReason")
    public void setTravelReason(TravelReason travelReason) {
        this.travelReason = travelReason;
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
