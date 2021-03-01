package dto.submodules.tripCreation.tripDetail;

import com.fasterxml.jackson.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author josealberto.gomez
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "status",
    "message",
    "statusCode",
    "totalCount",
    "totalPages",
    "validData",
    "content"
})
public class GetTravelReasonsResponseDTO {
    @JsonProperty("status")
    private String status;
    @JsonProperty("message")
    private String message;
    @JsonProperty("statusCode")
    private Object statusCode;
    @JsonProperty("totalCount")
    private Integer totalCount;
    @JsonProperty("totalPages")
    private Integer totalPages;
    @JsonProperty("validData")
    private Boolean validData;
    @JsonProperty("content")
    private List<TravelReason> content;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("statusCode")
    public Object getStatusCode() {
        return statusCode;
    }

    @JsonProperty("statusCode")
    public void setStatusCode(Object statusCode) {
        this.statusCode = statusCode;
    }

    @JsonProperty("totalCount")
    public Integer getTotalCount() {
        return totalCount;
    }

    @JsonProperty("totalCount")
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @JsonProperty("totalPages")
    public Integer getTotalPages() {
        return totalPages;
    }

    @JsonProperty("totalPages")
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    @JsonProperty("validData")
    public Boolean getValidData() {
        return validData;
    }

    @JsonProperty("validData")
    public void setValidData(Boolean validData) {
        this.validData = validData;
    }

    @JsonProperty("content")
    public List<TravelReason> getContent() {
        return content;
    }

    @JsonProperty("content")
    public void setContent(List<TravelReason> content) {
        this.content = content;
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
