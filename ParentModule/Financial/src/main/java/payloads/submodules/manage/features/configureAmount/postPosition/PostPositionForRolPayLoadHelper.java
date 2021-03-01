
package payloads.submodules.manage.features.configureAmount.postPosition;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import dto.submodules.manage.configureAmount.getPosition.Content;

/**
 * @author german.massello
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "role",
    "position",
    "maxAmount"
})
public class PostPositionForRolPayLoadHelper {

    @JsonProperty("role")
    private String role;
    @JsonProperty("position")
    private String position;
    @JsonProperty("maxAmount")
    private String maxAmount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private String id;
    
    /**
     * This method will create a payload in order to add a position to a safe matix rol.
     * @param position
     * @param rol
     */
    public PostPositionForRolPayLoadHelper(Content position, dto.submodules.manage.configureAmount.getSafeMatrixRoles.Content rol) {
    	this.id=rol.getId();
    	this.role=rol.getDescription();
    	this.position=position.getPosition();
    	this.maxAmount=rol.getMaxAmount();
	}

	@JsonProperty("role")
    public String getRole() {
        return role;
    }

    @JsonProperty("role")
    public void setRole(String role) {
        this.role = role;
    }

    @JsonProperty("position")
    public String getPosition() {
        return position;
    }

    @JsonProperty("position")
    public void setPosition(String position) {
        this.position = position;
    }

    @JsonProperty("maxAmount")
    public String getMaxAmount() {
        return maxAmount;
    }

    @JsonProperty("maxAmount")
    public void setMaxAmount(String maxAmount) {
        this.maxAmount = maxAmount;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
