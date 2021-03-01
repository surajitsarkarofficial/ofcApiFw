
package dto.submodules.purchaseOrder.get;

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
    "content",
    "pageable",
    "last",
    "totalElements",
    "totalPages",
    "first",
    "sort",
    "numberOfElements",
    "size",
    "number"
})
public class RequisitionItemsPoPage {

    @JsonProperty("content")
    private List<Content_> content = null;
    @JsonProperty("pageable")
    private Pageable pageable;
    @JsonProperty("last")
    private Boolean last;
    @JsonProperty("totalElements")
    private String totalElements;
    @JsonProperty("totalPages")
    private String totalPages;
    @JsonProperty("first")
    private Boolean first;
    @JsonProperty("sort")
    private Sort_ sort;
    @JsonProperty("numberOfElements")
    private String numberOfElements;
    @JsonProperty("size")
    private String size;
    @JsonProperty("number")
    private String number;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("content")
    public List<Content_> getContent() {
        return content;
    }

    @JsonProperty("content")
    public void setContent(List<Content_> content) {
        this.content = content;
    }

    @JsonProperty("pageable")
    public Pageable getPageable() {
        return pageable;
    }

    @JsonProperty("pageable")
    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    @JsonProperty("last")
    public Boolean getLast() {
        return last;
    }

    @JsonProperty("last")
    public void setLast(Boolean last) {
        this.last = last;
    }

    @JsonProperty("totalElements")
    public String getTotalElements() {
        return totalElements;
    }

    @JsonProperty("totalElements")
    public void setTotalElements(String totalElements) {
        this.totalElements = totalElements;
    }

    @JsonProperty("totalPages")
    public String getTotalPages() {
        return totalPages;
    }

    @JsonProperty("totalPages")
    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    @JsonProperty("first")
    public Boolean getFirst() {
        return first;
    }

    @JsonProperty("first")
    public void setFirst(Boolean first) {
        this.first = first;
    }

    @JsonProperty("sort")
    public Sort_ getSort() {
        return sort;
    }

    @JsonProperty("sort")
    public void setSort(Sort_ sort) {
        this.sort = sort;
    }

    @JsonProperty("numberOfElements")
    public String getNumberOfElements() {
        return numberOfElements;
    }

    @JsonProperty("numberOfElements")
    public void setNumberOfElements(String numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    @JsonProperty("size")
    public String getSize() {
        return size;
    }

    @JsonProperty("size")
    public void setSize(String size) {
        this.size = size;
    }

    @JsonProperty("number")
    public String getNumber() {
        return number;
    }

    @JsonProperty("number")
    public void setNumber(String number) {
        this.number = number;
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
