
package dto.submodules.catalogs.material;

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
    "id",
    "code",
    "description",
    "groupId",
    "society",
    "currencyId",
    "priceUnit",
    "movingPrice",
    "stdPr",
    "baseUnit"
})
public class Entry {

    @JsonProperty("id")
    private String id;
    @JsonProperty("code")
    private String code;
    @JsonProperty("description")
    private String description;
    @JsonProperty("groupId")
    private String groupId;
    @JsonProperty("society")
    private String society;
    @JsonProperty("currencyId")
    private String currencyId;
    @JsonProperty("priceUnit")
    private String priceUnit;
    @JsonProperty("movingPrice")
    private String movingPrice;
    @JsonProperty("stdPr")
    private String stdPr;
    @JsonProperty("baseUnit")
    private String baseUnit;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("groupId")
    public String getGroupId() {
        return groupId;
    }

    @JsonProperty("groupId")
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @JsonProperty("society")
    public String getSociety() {
        return society;
    }

    @JsonProperty("society")
    public void setSociety(String society) {
        this.society = society;
    }

    @JsonProperty("currencyId")
    public String getCurrencyId() {
        return currencyId;
    }

    @JsonProperty("currencyId")
    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    @JsonProperty("priceUnit")
    public String getPriceUnit() {
        return priceUnit;
    }

    @JsonProperty("priceUnit")
    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    @JsonProperty("movingPrice")
    public String getMovingPrice() {
        return movingPrice;
    }

    @JsonProperty("movingPrice")
    public void setMovingPrice(String movingPrice) {
        this.movingPrice = movingPrice;
    }

    @JsonProperty("stdPr")
    public String getStdPr() {
        return stdPr;
    }

    @JsonProperty("stdPr")
    public void setStdPr(String stdPr) {
        this.stdPr = stdPr;
    }

    @JsonProperty("baseUnit")
    public String getBaseUnit() {
        return baseUnit;
    }

    @JsonProperty("baseUnit")
    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
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
