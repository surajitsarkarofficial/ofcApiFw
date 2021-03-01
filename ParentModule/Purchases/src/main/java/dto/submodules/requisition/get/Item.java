
package dto.submodules.requisition.get;

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
    "requisitionId",
    "id",
    "supplyId",
    "quantity",
    "supplyDescription",
    "unitCost",
    "localCurrencySubtotal",
    "currencyId",
    "currencyCode",
    "usdSubtotal",
    "expectedDeliveryDate",
    "technicalSpecifications",
    "suggestedVendorId",
    "vendor",
    "usdRateConversion",
    "conversionDate",
    "deliveryPlaceId",
    "deliveryPlaceName",
    "deliveryPlaceSpecial",
    "clarificationNotes",
    "requesterComments",
    "itemsReceived",
    "requisitionItemAttachments",
    "status",
    "projectId",
    "projectName",
    "projectClientName",
    "handler",
    "staffDepartment",
    "rejectionComments",
    "unitId",
    "unitName",
    "poNumber"
})
public class Item {

    @JsonProperty("requisitionId")
    private String requisitionId;
    @JsonProperty("id")
    private String id;
    @JsonProperty("supplyId")
    private Object supplyId;
    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("supplyDescription")
    private String supplyDescription;
    @JsonProperty("unitCost")
    private Double unitCost;
    @JsonProperty("localCurrencySubtotal")
    private Double localCurrencySubtotal;
    @JsonProperty("currencyId")
    private String currencyId;
    @JsonProperty("currencyCode")
    private String currencyCode;
    @JsonProperty("usdSubtotal")
    private String usdSubtotal;
    @JsonProperty("expectedDeliveryDate")
    private String expectedDeliveryDate;
    @JsonProperty("technicalSpecifications")
    private String technicalSpecifications;
    @JsonProperty("suggestedVendorId")
    private Object suggestedVendorId;
    @JsonProperty("vendor")
    private Object vendor;
    @JsonProperty("usdRateConversion")
    private String usdRateConversion;
    @JsonProperty("conversionDate")
    private String conversionDate;
    @JsonProperty("deliveryPlaceId")
    private String deliveryPlaceId;
    @JsonProperty("deliveryPlaceName")
    private String deliveryPlaceName;
    @JsonProperty("deliveryPlaceSpecial")
    private String deliveryPlaceSpecial;
    @JsonProperty("clarificationNotes")
    private Object clarificationNotes;
    @JsonProperty("requesterComments")
    private String requesterComments;
    @JsonProperty("itemsReceived")
    private String itemsReceived;
    @JsonProperty("requisitionItemAttachments")
    private Object requisitionItemAttachments;
    @JsonProperty("status")
    private String status;
    @JsonProperty("projectId")
    private String projectId;
    @JsonProperty("projectName")
    private String projectName;
    @JsonProperty("projectClientName")
    private String projectClientName;
    @JsonProperty("handler")
    private String handler;
    @JsonProperty("staffDepartment")
    private String staffDepartment;
    @JsonProperty("rejectionComments")
    private Object rejectionComments;
    @JsonProperty("unitId")
    private String unitId;
    @JsonProperty("unitName")
    private String unitName;
    @JsonProperty("poNumber")
    private Object poNumber;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("requisitionId")
    public String getRequisitionId() {
        return requisitionId;
    }

    @JsonProperty("requisitionId")
    public void setRequisitionId(String requisitionId) {
        this.requisitionId = requisitionId;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("supplyId")
    public Object getSupplyId() {
        return supplyId;
    }

    @JsonProperty("supplyId")
    public void setSupplyId(Object supplyId) {
        this.supplyId = supplyId;
    }

    @JsonProperty("quantity")
    public String getQuantity() {
        return quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @JsonProperty("supplyDescription")
    public String getSupplyDescription() {
        return supplyDescription;
    }

    @JsonProperty("supplyDescription")
    public void setSupplyDescription(String supplyDescription) {
        this.supplyDescription = supplyDescription;
    }

    @JsonProperty("unitCost")
    public Double getUnitCost() {
        return unitCost;
    }

    @JsonProperty("unitCost")
    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

    @JsonProperty("localCurrencySubtotal")
    public Double getLocalCurrencySubtotal() {
        return localCurrencySubtotal;
    }

    @JsonProperty("localCurrencySubtotal")
    public void setLocalCurrencySubtotal(Double localCurrencySubtotal) {
        this.localCurrencySubtotal = localCurrencySubtotal;
    }

    @JsonProperty("currencyId")
    public String getCurrencyId() {
        return currencyId;
    }

    @JsonProperty("currencyId")
    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    @JsonProperty("currencyCode")
    public String getCurrencyCode() {
        return currencyCode;
    }

    @JsonProperty("currencyCode")
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @JsonProperty("usdSubtotal")
    public String getUsdSubtotal() {
        return usdSubtotal;
    }

    @JsonProperty("usdSubtotal")
    public void setUsdSubtotal(String usdSubtotal) {
        this.usdSubtotal = usdSubtotal;
    }

    @JsonProperty("expectedDeliveryDate")
    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    @JsonProperty("expectedDeliveryDate")
    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    @JsonProperty("technicalSpecifications")
    public String getTechnicalSpecifications() {
        return technicalSpecifications;
    }

    @JsonProperty("technicalSpecifications")
    public void setTechnicalSpecifications(String technicalSpecifications) {
        this.technicalSpecifications = technicalSpecifications;
    }

    @JsonProperty("suggestedVendorId")
    public Object getSuggestedVendorId() {
        return suggestedVendorId;
    }

    @JsonProperty("suggestedVendorId")
    public void setSuggestedVendorId(Object suggestedVendorId) {
        this.suggestedVendorId = suggestedVendorId;
    }

    @JsonProperty("vendor")
    public Object getVendor() {
        return vendor;
    }

    @JsonProperty("vendor")
    public void setVendor(Object vendor) {
        this.vendor = vendor;
    }

    @JsonProperty("usdRateConversion")
    public String getUsdRateConversion() {
        return usdRateConversion;
    }

    @JsonProperty("usdRateConversion")
    public void setUsdRateConversion(String usdRateConversion) {
        this.usdRateConversion = usdRateConversion;
    }

    @JsonProperty("conversionDate")
    public String getConversionDate() {
        return conversionDate;
    }

    @JsonProperty("conversionDate")
    public void setConversionDate(String conversionDate) {
        this.conversionDate = conversionDate;
    }

    @JsonProperty("deliveryPlaceId")
    public String getDeliveryPlaceId() {
        return deliveryPlaceId;
    }

    @JsonProperty("deliveryPlaceId")
    public void setDeliveryPlaceId(String deliveryPlaceId) {
        this.deliveryPlaceId = deliveryPlaceId;
    }

    @JsonProperty("deliveryPlaceName")
    public String getDeliveryPlaceName() {
        return deliveryPlaceName;
    }

    @JsonProperty("deliveryPlaceName")
    public void setDeliveryPlaceName(String deliveryPlaceName) {
        this.deliveryPlaceName = deliveryPlaceName;
    }

    @JsonProperty("deliveryPlaceSpecial")
    public String getDeliveryPlaceSpecial() {
        return deliveryPlaceSpecial;
    }

    @JsonProperty("deliveryPlaceSpecial")
    public void setDeliveryPlaceSpecial(String deliveryPlaceSpecial) {
        this.deliveryPlaceSpecial = deliveryPlaceSpecial;
    }

    @JsonProperty("clarificationNotes")
    public Object getClarificationNotes() {
        return clarificationNotes;
    }

    @JsonProperty("clarificationNotes")
    public void setClarificationNotes(Object clarificationNotes) {
        this.clarificationNotes = clarificationNotes;
    }

    @JsonProperty("requesterComments")
    public String getRequesterComments() {
        return requesterComments;
    }

    @JsonProperty("requesterComments")
    public void setRequesterComments(String requesterComments) {
        this.requesterComments = requesterComments;
    }

    @JsonProperty("itemsReceived")
    public String getItemsReceived() {
        return itemsReceived;
    }

    @JsonProperty("itemsReceived")
    public void setItemsReceived(String itemsReceived) {
        this.itemsReceived = itemsReceived;
    }

    @JsonProperty("requisitionItemAttachments")
    public Object getRequisitionItemAttachments() {
        return requisitionItemAttachments;
    }

    @JsonProperty("requisitionItemAttachments")
    public void setRequisitionItemAttachments(Object requisitionItemAttachments) {
        this.requisitionItemAttachments = requisitionItemAttachments;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("projectId")
    public String getProjectId() {
        return projectId;
    }

    @JsonProperty("projectId")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @JsonProperty("projectName")
    public String getProjectName() {
        return projectName;
    }

    @JsonProperty("projectName")
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @JsonProperty("projectClientName")
    public String getProjectClientName() {
        return projectClientName;
    }

    @JsonProperty("projectClientName")
    public void setProjectClientName(String projectClientName) {
        this.projectClientName = projectClientName;
    }

    @JsonProperty("handler")
    public String getHandler() {
        return handler;
    }

    @JsonProperty("handler")
    public void setHandler(String handler) {
        this.handler = handler;
    }

    @JsonProperty("staffDepartment")
    public String getStaffDepartment() {
        return staffDepartment;
    }

    @JsonProperty("staffDepartment")
    public void setStaffDepartment(String staffDepartment) {
        this.staffDepartment = staffDepartment;
    }

    @JsonProperty("rejectionComments")
    public Object getRejectionComments() {
        return rejectionComments;
    }

    @JsonProperty("rejectionComments")
    public void setRejectionComments(Object rejectionComments) {
        this.rejectionComments = rejectionComments;
    }

    @JsonProperty("unitId")
    public String getUnitId() {
        return unitId;
    }

    @JsonProperty("unitId")
    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    @JsonProperty("unitName")
    public String getUnitName() {
        return unitName;
    }

    @JsonProperty("unitName")
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @JsonProperty("poNumber")
    public Object getPoNumber() {
        return poNumber;
    }

    @JsonProperty("poNumber")
    public void setPoNumber(Object poNumber) {
        this.poNumber = poNumber;
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
