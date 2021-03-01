
package dto.submodules.catalogs.rate;

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
    "currencyFromId",
    "currencyToId",
    "quotationDate",
    "factor"
})
public class Content {

    @JsonProperty("currencyFromId")
    private String currencyFromId;
    @JsonProperty("currencyToId")
    private String currencyToId;
    @JsonProperty("quotationDate")
    private String quotationDate;
    @JsonProperty("factor")
    private String factor;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("currencyFromId")
    public String getCurrencyFromId() {
        return currencyFromId;
    }

    @JsonProperty("currencyFromId")
    public void setCurrencyFromId(String currencyFromId) {
        this.currencyFromId = currencyFromId;
    }

    @JsonProperty("currencyToId")
    public String getCurrencyToId() {
        return currencyToId;
    }

    @JsonProperty("currencyToId")
    public void setCurrencyToId(String currencyToId) {
        this.currencyToId = currencyToId;
    }

    @JsonProperty("quotationDate")
    public String getQuotationDate() {
        return quotationDate;
    }

    @JsonProperty("quotationDate")
    public void setQuotationDate(String quotationDate) {
        this.quotationDate = quotationDate;
    }

    @JsonProperty("factor")
    public String getFactor() {
        return factor;
    }

    @JsonProperty("factor")
    public void setFactor(String factor) {
        this.factor = factor;
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
