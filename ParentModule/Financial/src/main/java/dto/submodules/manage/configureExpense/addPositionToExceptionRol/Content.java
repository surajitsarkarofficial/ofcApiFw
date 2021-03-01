
package dto.submodules.manage.configureExpense.addPositionToExceptionRol;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "rolePositionId",
    "positionName",
    "positionId",
    "roleId",
    "roleName"
})
public class Content {

    @JsonProperty("rolePositionId")
    private String rolePositionId;
    @JsonProperty("positionName")
    private String positionName;
    @JsonProperty("positionId")
    private String positionId;
    @JsonProperty("roleId")
    private String roleId;
    @JsonProperty("roleName")
    private String roleName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("rolePositionId")
    public String getRolePositionId() {
        return rolePositionId;
    }

    @JsonProperty("rolePositionId")
    public void setRolePositionId(String rolePositionId) {
        this.rolePositionId = rolePositionId;
    }

    @JsonProperty("positionName")
    public String getPositionName() {
        return positionName;
    }

    @JsonProperty("positionName")
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    @JsonProperty("positionId")
    public String getPositionId() {
        return positionId;
    }

    @JsonProperty("positionId")
    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    @JsonProperty("roleId")
    public String getRoleId() {
        return roleId;
    }

    @JsonProperty("roleId")
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @JsonProperty("roleName")
    public String getRoleName() {
        return roleName;
    }

    @JsonProperty("roleName")
    public void setRoleName(String roleName) {
        this.roleName = roleName;
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
