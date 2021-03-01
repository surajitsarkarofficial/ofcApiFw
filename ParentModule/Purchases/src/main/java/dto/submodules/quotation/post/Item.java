
package dto.submodules.quotation.post;

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
    "clarificationNotes",
    "conversionDate",
    "currencyCode",
    "currencyId",
    "deliveryPlaceId",
    "deliveryPlaceName",
    "deliveryPlaceSpecial",
    "expectedDeliveryDate",
    "id",
    "itemsReceived",
    "localCurrencySubtotal",
    "poNumber",
    "projectClientName",
    "projectId",
    "projectName",
    "quantity",
    "rejectionComments",
    "requesterComments",
    "requisitionId",
    "requisitionItemAttachments",
    "staffDepartment",
    "status",
    "suggestedVendorId",
    "supplyDescription",
    "supplyId",
    "technicalSpecifications",
    "unitCost",
    "unitId",
    "unitName",
    "usdRateConversion",
    "usdSubtotal",
    "vendor"
})
public class Item {

    @JsonProperty("clarificationNotes")
    private List<ClarificationNote> clarificationNotes = null;
    @JsonProperty("conversionDate")
    private String conversionDate;
    @JsonProperty("currencyCode")
    private String currencyCode;
    @JsonProperty("currencyId")
    private String currencyId;
    @JsonProperty("deliveryPlaceId")
    private String deliveryPlaceId;
    @JsonProperty("deliveryPlaceName")
    private String deliveryPlaceName;
    @JsonProperty("deliveryPlaceSpecial")
    private String deliveryPlaceSpecial;
    @JsonProperty("expectedDeliveryDate")
    private String expectedDeliveryDate;
    @JsonProperty("id")
    private String id;
    @JsonProperty("itemsReceived")
    private String itemsReceived;
    @JsonProperty("localCurrencySubtotal")
    private String localCurrencySubtotal;
    @JsonProperty("poNumber")
    private String poNumber;
    @JsonProperty("projectClientName")
    private String projectClientName;
    @JsonProperty("projectId")
    private String projectId;
    @JsonProperty("projectName")
    private String projectName;
    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("rejectionComments")
    private String rejectionComments;
    @JsonProperty("requesterComments")
    private String requesterComments;
    @JsonProperty("requisitionId")
    private String requisitionId;
    @JsonProperty("requisitionItemAttachments")
    private List<RequisitionItemAttachment> requisitionItemAttachments = null;
    @JsonProperty("staffDepartment")
    private String staffDepartment;
    @JsonProperty("status")
    private String status;
    @JsonProperty("suggestedVendorId")
    private String suggestedVendorId;
    @JsonProperty("supplyDescription")
    private String supplyDescription;
    @JsonProperty("supplyId")
    private String supplyId;
    @JsonProperty("technicalSpecifications")
    private String technicalSpecifications;
    @JsonProperty("unitCost")
    private String unitCost;
    @JsonProperty("unitId")
    private String unitId;
    @JsonProperty("unitName")
    private String unitName;
    @JsonProperty("usdRateConversion")
    private String usdRateConversion;
    @JsonProperty("usdSubtotal")
    private String usdSubtotal;
    @JsonProperty("vendor")
    private String vendor;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("clarificationNotes")
    public List<ClarificationNote> getClarificationNotes() {
        return clarificationNotes;
    }

    @JsonProperty("clarificationNotes")
    public void setClarificationNotes(List<ClarificationNote> clarificationNotes) {
        this.clarificationNotes = clarificationNotes;
    }

    @JsonProperty("conversionDate")
    public String getConversionDate() {
        return conversionDate;
    }

    @JsonProperty("conversionDate")
    public void setConversionDate(String conversionDate) {
        this.conversionDate = conversionDate;
    }

    @JsonProperty("currencyCode")
    public String getCurrencyCode() {
        return currencyCode;
    }

    @JsonProperty("currencyCode")
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @JsonProperty("currencyId")
    public String getCurrencyId() {
        return currencyId;
    }

    @JsonProperty("currencyId")
    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
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

    @JsonProperty("expectedDeliveryDate")
    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    @JsonProperty("expectedDeliveryDate")
    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("itemsReceived")
    public String getItemsReceived() {
        return itemsReceived;
    }

    @JsonProperty("itemsReceived")
    public void setItemsReceived(String itemsReceived) {
        this.itemsReceived = itemsReceived;
    }

    @JsonProperty("localCurrencySubtotal")
    public String getLocalCurrencySubtotal() {
        return localCurrencySubtotal;
    }

    @JsonProperty("localCurrencySubtotal")
    public void setLocalCurrencySubtotal(String localCurrencySubtotal) {
        this.localCurrencySubtotal = localCurrencySubtotal;
    }

    @JsonProperty("poNumber")
    public String getPoNumber() {
        return poNumber;
    }

    @JsonProperty("poNumber")
    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    @JsonProperty("projectClientName")
    public String getProjectClientName() {
        return projectClientName;
    }

    @JsonProperty("projectClientName")
    public void setProjectClientName(String projectClientName) {
        this.projectClientName = projectClientName;
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

    @JsonProperty("quantity")
    public String getQuantity() {
        return quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @JsonProperty("rejectionComments")
    public String getRejectionComments() {
        return rejectionComments;
    }

    @JsonProperty("rejectionComments")
    public void setRejectionComments(String rejectionComments) {
        this.rejectionComments = rejectionComments;
    }

    @JsonProperty("requesterComments")
    public String getRequesterComments() {
        return requesterComments;
    }

    @JsonProperty("requesterComments")
    public void setRequesterComments(String requesterComments) {
        this.requesterComments = requesterComments;
    }

    @JsonProperty("requisitionId")
    public String getRequisitionId() {
        return requisitionId;
    }

    @JsonProperty("requisitionId")
    public void setRequisitionId(String requisitionId) {
        this.requisitionId = requisitionId;
    }

    @JsonProperty("requisitionItemAttachments")
    public List<RequisitionItemAttachment> getRequisitionItemAttachments() {
        return requisitionItemAttachments;
    }

    @JsonProperty("requisitionItemAttachments")
    public void setRequisitionItemAttachments(List<RequisitionItemAttachment> requisitionItemAttachments) {
        this.requisitionItemAttachments = requisitionItemAttachments;
    }

    @JsonProperty("staffDepartment")
    public String getStaffDepartment() {
        return staffDepartment;
    }

    @JsonProperty("staffDepartment")
    public void setStaffDepartment(String staffDepartment) {
        this.staffDepartment = staffDepartment;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("suggestedVendorId")
    public String getSuggestedVendorId() {
        return suggestedVendorId;
    }

    @JsonProperty("suggestedVendorId")
    public void setSuggestedVendorId(String suggestedVendorId) {
        this.suggestedVendorId = suggestedVendorId;
    }

    @JsonProperty("supplyDescription")
    public String getSupplyDescription() {
        return supplyDescription;
    }

    @JsonProperty("supplyDescription")
    public void setSupplyDescription(String supplyDescription) {
        this.supplyDescription = supplyDescription;
    }

    @JsonProperty("supplyId")
    public String getSupplyId() {
        return supplyId;
    }

    @JsonProperty("supplyId")
    public void setSupplyId(String supplyId) {
        this.supplyId = supplyId;
    }

    @JsonProperty("technicalSpecifications")
    public String getTechnicalSpecifications() {
        return technicalSpecifications;
    }

    @JsonProperty("technicalSpecifications")
    public void setTechnicalSpecifications(String technicalSpecifications) {
        this.technicalSpecifications = technicalSpecifications;
    }

    @JsonProperty("unitCost")
    public String getUnitCost() {
        return unitCost;
    }

    @JsonProperty("unitCost")
    public void setUnitCost(String unitCost) {
        this.unitCost = unitCost;
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

    @JsonProperty("usdRateConversion")
    public String getUsdRateConversion() {
        return usdRateConversion;
    }

    @JsonProperty("usdRateConversion")
    public void setUsdRateConversion(String usdRateConversion) {
        this.usdRateConversion = usdRateConversion;
    }

    @JsonProperty("usdSubtotal")
    public String getUsdSubtotal() {
        return usdSubtotal;
    }

    @JsonProperty("usdSubtotal")
    public void setUsdSubtotal(String usdSubtotal) {
        this.usdSubtotal = usdSubtotal;
    }

    @JsonProperty("vendor")
    public String getVendor() {
        return vendor;
    }

    @JsonProperty("vendor")
    public void setVendor(String vendor) {
        this.vendor = vendor;
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
