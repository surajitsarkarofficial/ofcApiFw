package dto.submodules.tripCreation.tripDetail;

import java.util.HashMap;
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
        "projectId",
        "projectName",
        "clientId",
        "clientName",
        "tag",
        "sapOrder",
        "society",
        "approverGlowId",
        "score"
})
public class Project {

    @JsonProperty("projectId")
    private Integer projectId;
    @JsonProperty("projectName")
    private String projectName;
    @JsonProperty("clientId")
    private Integer clientId;
    @JsonProperty("clientName")
    private String clientName;
    @JsonProperty("tag")
    private String tag;
    @JsonProperty("sapOrder")
    private Object sapOrder;
    @JsonProperty("society")
    private Object society;
    @JsonProperty("approverGlowId")
    private Object approverGlowId;
    @JsonProperty("score")
    private Integer score;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("projectId")
    public Integer getProjectId() {
        return projectId;
    }

    @JsonProperty("projectId")
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @JsonProperty("projectName")
    public String getProjectName() {
        return projectName;
    }

    @JsonProperty("projectName")
    public void setProjectName(String projectName) {
        this.projectName = projectName;
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
    public Object getSapOrder() {
        return sapOrder;
    }

    @JsonProperty("sapOrder")
    public void setSapOrder(Object sapOrder) {
        this.sapOrder = sapOrder;
    }

    @JsonProperty("society")
    public Object getSociety() {
        return society;
    }

    @JsonProperty("society")
    public void setSociety(Object society) {
        this.society = society;
    }

    @JsonProperty("approverGlowId")
    public Object getApproverGlowId() {
        return approverGlowId;
    }

    @JsonProperty("approverGlowId")
    public void setApproverGlowId(Object approverGlowId) {
        this.approverGlowId = approverGlowId;
    }

    @JsonProperty("score")
    public Integer getScore() {
        return score;
    }

    @JsonProperty("score")
    public void setScore(Integer score) {
        this.score = score;
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