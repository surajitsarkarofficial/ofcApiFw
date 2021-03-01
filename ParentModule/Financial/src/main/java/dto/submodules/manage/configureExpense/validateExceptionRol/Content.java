
package dto.submodules.manage.configureExpense.validateExceptionRol;

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
    "skipException",
    "maxAmount"
})
public class Content {

    @JsonProperty("skipException")
    private Boolean skipException;
    @JsonProperty("maxAmount")
    private Integer maxAmount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("skipException")
    public Boolean getSkipException() {
        return skipException;
    }

    @JsonProperty("skipException")
    public void setSkipException(Boolean skipException) {
        this.skipException = skipException;
    }

    @JsonProperty("maxAmount")
    public Integer getMaxAmount() {
        return maxAmount;
    }

    @JsonProperty("maxAmount")
    public void setMaxAmount(Integer maxAmount) {
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

}
