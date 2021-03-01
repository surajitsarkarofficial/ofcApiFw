
package dto.submodules.reception.post;

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
    "receptionNum",
    "modDate",
    "receptionItemResponse",
    "sapReceptionResponse"
})
public class Content {

    @JsonProperty("receptionNum")
    private String receptionNum;
    @JsonProperty("modDate")
    private String modDate;
    @JsonProperty("receptionItemResponse")
    private ReceptionItemResponse receptionItemResponse;
    @JsonProperty("sapReceptionResponse")
    private Object sapReceptionResponse;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("receptionNum")
    public String getReceptionNum() {
        return receptionNum;
    }

    @JsonProperty("receptionNum")
    public void setReceptionNum(String receptionNum) {
        this.receptionNum = receptionNum;
    }

    @JsonProperty("modDate")
    public String getModDate() {
        return modDate;
    }

    @JsonProperty("modDate")
    public void setModDate(String modDate) {
        this.modDate = modDate;
    }

    @JsonProperty("receptionItemResponse")
    public ReceptionItemResponse getReceptionItemResponse() {
        return receptionItemResponse;
    }

    @JsonProperty("receptionItemResponse")
    public void setReceptionItemResponse(ReceptionItemResponse receptionItemResponse) {
        this.receptionItemResponse = receptionItemResponse;
    }

    @JsonProperty("sapReceptionResponse")
    public Object getSapReceptionResponse() {
        return sapReceptionResponse;
    }

    @JsonProperty("sapReceptionResponse")
    public void setSapReceptionResponse(Object sapReceptionResponse) {
        this.sapReceptionResponse = sapReceptionResponse;
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
