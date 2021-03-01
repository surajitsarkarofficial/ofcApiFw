package dto.submodules.tripCreation.costAllocation;

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
 * @author josealberto.gomez
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "costAllocationId",
        "projectName",
        "percentage",
        "tripId",
        "clientId",
        "clientName",
        "tag",
        "sapOrder",
        "society",
        "projectId",
        "custEmail",
        "approverGlowId",
        "approverList"
})
public class CostAllocation {

    @JsonProperty("costAllocationId")
    private Integer costAllocationId;
    @JsonProperty("projectName")
    private String projectName;
    @JsonProperty("percentage")
    private Double percentage;
    @JsonProperty("tripId")
    private Integer tripId;
    @JsonProperty("clientId")
    private Integer clientId;
    @JsonProperty("clientName")
    private String clientName;
    @JsonProperty("tag")
    private String tag;
    @JsonProperty("sapOrder")
    private String sapOrder;
    @JsonProperty("society")
    private String society;
    @JsonProperty("projectId")
    private Integer projectId;
    @JsonProperty("custEmail")
    private Object custEmail;
    @JsonProperty("approverGlowId")
    private Object approverGlowId;
    @JsonProperty("approverList")
    private List<Approver> approverList = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("costAllocationId")
    public Integer getCostAllocationId() {
        return costAllocationId;
    }

    @JsonProperty("costAllocationId")
    public void setCostAllocationId(Integer costAllocationId) {
        this.costAllocationId = costAllocationId;
    }

    @JsonProperty("projectName")
    public String getProjectName() {
        return projectName;
    }

    @JsonProperty("projectName")
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @JsonProperty("percentage")
    public Double getPercentage() {
        return percentage;
    }

    @JsonProperty("percentage")
    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    @JsonProperty("tripId")
    public Integer getTripId() {
        return tripId;
    }

    @JsonProperty("tripId")
    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    @JsonProperty("clientId")
    public Integer getClientId() {
        return clientId;
    }

    @JsonProperty("clientId")
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @JsonProperty("clientName")
    public String getClientName() {
        return clientName;
    }

    @JsonProperty("clientName")
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @JsonProperty("tag")
    public String getTag() {
        return tag;
    }

    @JsonProperty("tag")
    public void setTag(String tag) {
        this.tag = tag;
    }

    @JsonProperty("sapOrder")
    public String getSapOrder() {
        return sapOrder;
    }

    @JsonProperty("sapOrder")
    public void setSapOrder(String sapOrder) {
        this.sapOrder = sapOrder;
    }

    @JsonProperty("society")
    public String getSociety() {
        return society;
    }

    @JsonProperty("society")
    public void setSociety(String society) {
        this.society = society;
    }

    @JsonProperty("projectId")
    public Integer getProjectId() {
        return projectId;
    }

    @JsonProperty("projectId")
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @JsonProperty("custEmail")
    public Object getCustEmail() {
        return custEmail;
    }

    @JsonProperty("custEmail")
    public void setCustEmail(Object custEmail) {
        this.custEmail = custEmail;
    }

    @JsonProperty("approverGlowId")
    public Object getApproverGlowId() {
        return approverGlowId;
    }

    @JsonProperty("approverGlowId")
    public void setApproverGlowId(Object approverGlowId) {
        this.approverGlowId = approverGlowId;
    }

    @JsonProperty("approverList")
    public List<Approver> getApproverList() {
        return approverList;
    }

    @JsonProperty("approverList")
    public void setApproverList(List<Approver> approverList) {
        this.approverList = approverList;
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