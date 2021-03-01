
package dto.submodules.manage.configureAmount.getSafeMatrixRolDetails;

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
    "role",
    "maxAmount",
    "relatedPositions"
})
public class Content {

    @JsonProperty("role")
    private Role role;
    @JsonProperty("maxAmount")
    private String maxAmount;
    @JsonProperty("relatedPositions")
    private List<RelatedPosition> relatedPositions = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("role")
    public Role getRole() {
        return role;
    }

    @JsonProperty("role")
    public void setRole(Role role) {
        this.role = role;
    }

    @JsonProperty("maxAmount")
    public String getMaxAmount() {
        return maxAmount;
    }

    @JsonProperty("maxAmount")
    public void setMaxAmount(String maxAmount) {
        this.maxAmount = maxAmount;
    }

    @JsonProperty("relatedPositions")
    public List<RelatedPosition> getRelatedPositions() {
        return relatedPositions;
    }

    @JsonProperty("relatedPositions")
    public void setRelatedPositions(List<RelatedPosition> relatedPositions) {
        this.relatedPositions = relatedPositions;
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
