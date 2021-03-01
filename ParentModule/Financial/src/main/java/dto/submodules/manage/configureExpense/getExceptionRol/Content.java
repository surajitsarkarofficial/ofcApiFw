
package dto.submodules.manage.configureExpense.getExceptionRol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "totalItems",
    "roleList"
})
public class Content {

    @JsonProperty("totalItems")
    private String totalItems;
    @JsonProperty("roleList")
    private List<RoleList> roleList = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("totalItems")
    public String getTotalItems() {
        return totalItems;
    }

    @JsonProperty("totalItems")
    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    @JsonProperty("roleList")
    public List<RoleList> getRoleList() {
        return roleList;
    }

    @JsonProperty("roleList")
    public void setRoleList(List<RoleList> roleList) {
        this.roleList = roleList;
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
